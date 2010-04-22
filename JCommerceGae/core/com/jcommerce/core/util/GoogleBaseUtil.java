package com.jcommerce.core.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.StringTokenizer;

import com.jcommerce.core.model.Goods;
import com.jcommerce.gwt.client.util.URLConstants;

/**
 *  Add a new item to Google Base using the Google Base data API server.
 */
public class GoogleBaseUtil {
  /**
   * URL of the authenticated customer feed.
   */
  private static final String ITEMS_FEED = "http://base.google.com/base/feeds/items";

  /**
   * Insert here the developer key obtained for an "installed application" at
   * http://code.google.com/apis/base/signup.html
   */
  private static final String DEVELOPER_KEY = "ABQIAAAAdum_4yYDkId337SD8heFChQeIQHYpA6H9kmimIu2ECi1AyhB0xQelDpqayXYHPdu9okteNGwcvBoZQ";
  
  /**
   * The data item we are going to insert, in XML/Atom format.
   */
  private String DATA_ITEM = "";
  
  
  private String webSitePath ="http://gc-shop.appspot.com";

  /**
   * URL used for authenticating and obtaining an authentication token. 
   * More details about how it works:
   * <code>http://code.google.com/apis/accounts/AuthForInstalledApps.html<code>
   */
  private static final String AUTHENTICATION_URL = "https://www.google.com/accounts/ClientLogin";
  
  /**
   * Fill in your Google account email here. 
   * The account should already be set up for Google Base.
   */
  private static final String EMAIL = "jcommerce.test@gmail.com";
  
  /**
   * Fill in your Google account password here.
   */
  private static final String PASSWORD = "jcommercetest";
  
  /**
   * The main method constructs a <code>InsertExample</code> instance, obtains an 
   * authorization token and posts a new item to Google Base.
   */
  public static void main(String[] args) throws MalformedURLException, IOException {
    GoogleBaseUtil insertExample = new GoogleBaseUtil();
    String token = insertExample.authenticate();
    System.out.println("Obtained authorization token: " + token);
    insertExample.postItem(token);
  }


  /**
   * Retrieves the authentication token for the provided set of credentials.
   * @return the authorization token that can be used to access authenticated
   *         Google Base data API feeds
   */
  public String authenticate() {
    // create the login request
    String postOutput = null;
    try {
      URL url = new URL(AUTHENTICATION_URL);
      postOutput = makeLoginRequest(url);
    } catch (IOException e) {
      System.out.println("Could not connect to authentication server: " 
          + e.toString());
      System.exit(1);
    }
  
    // Parse the result of the login request. If everything went fine, the 
    // response will look like
    //      HTTP/1.0 200 OK
    //      Server: GFE/1.3
    //      Content-Type: text/plain 
    //      SID=DQAAAGgA...7Zg8CTN
    //      LSID=DQAAAGsA...lk8BBbG
    //      Auth=DQAAAGgA...dk3fA5N
    // so all we need to do is look for "Auth" and get the token that comes after it
  
    StringTokenizer tokenizer = new StringTokenizer(postOutput, "=\n ");
    String token = null;
    
    while (tokenizer.hasMoreElements()) {
      if (tokenizer.nextToken().equals("Auth")) {
        if (tokenizer.hasMoreElements()) {
          token = tokenizer.nextToken(); 
        }
        break;
      }
    }
    if (token == null) {
      System.out.println("Authentication error. Response from server:\n" + postOutput);
      System.exit(1);
    }
    return token;
  }

  /**
   * Makes a HTTP POST request to the provided {@code url} given the provided
   * {@code parameters}. It returns the output from the POST handler as a
   * String object.
   * 
   * @param url the URL to post the request
   * @return the output from the handler
   * @throws IOException if an I/O exception occurs while
   *           creating/writing/reading the request
   */
  private String makeLoginRequest(URL url)
      throws IOException {
    // Create a login request. A login request is a POST request that looks like
    // POST /accounts/ClientLogin HTTP/1.0
    // Content-type: application/x-www-form-urlencoded
    // Email=johndoe@gmail.com&Passwd=north23AZ&service=gbase&source=Insert Example

    // Open connection
    HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
  
    
    // Set properties of the connection
    urlConnection.setRequestMethod("POST");
    urlConnection.setDoInput(true);
    urlConnection.setDoOutput(true);
    urlConnection.setUseCaches(false);
    urlConnection.setRequestProperty("Content-Type",
                                     "application/x-www-form-urlencoded");
  
    // Form the POST parameters
    StringBuilder content = new StringBuilder();
    content.append("Email=").append(URLEncoder.encode(EMAIL, "UTF-8"));
    content.append("&Passwd=").append(URLEncoder.encode(PASSWORD, "UTF-8"));
    content.append("&source=").append(URLEncoder.encode("Google Base data API example", "UTF-8"));
    content.append("&service=").append(URLEncoder.encode("gbase", "UTF-8"));

    OutputStream outputStream = urlConnection.getOutputStream();
    outputStream.write(content.toString().getBytes("UTF-8"));
    outputStream.close();
  
    // Retrieve the output
    int responseCode = urlConnection.getResponseCode();
    InputStream inputStream;
    if (responseCode == HttpURLConnection.HTTP_OK) {
      inputStream = urlConnection.getInputStream();
    } else {
      inputStream = urlConnection.getErrorStream();
    }
    String str = toString(inputStream);
    System.out.println(str);
    return str;
  }

  /**
   * Writes the content of the input stream to a <code>String<code>.
   */
  private String toString(InputStream inputStream) throws IOException {
    String string;
    StringBuilder outputBuilder = new StringBuilder();
    if (inputStream != null) {
      BufferedReader reader =
          new BufferedReader(new InputStreamReader(inputStream,"UTF-8"));
      while (null != (string = reader.readLine())) {
        outputBuilder.append(string).append('\n');
      }
    }
    return outputBuilder.toString();
  }
  /*
   * "<?xml version=\'1.0\'?>\n" + 
        "<entry xmlns=\'http://www.w3.org/2005/Atom\' xmlns:g=\'http://base.google.com/ns/1.0\'>\n" + 
        "  <category scheme=\'http://base.google.com/categories/itemtypes\' term=\'Products\'/>\n" + 
        "  <g:item_type type=\'text\'>Products</g:item_type>\n" + 
        "  <g:condition>refurbished</g:condition>" + 
        "  <description>abcdedd my favorate phone english.</description>" +
        "  <g:id>201011980708</g:id>" + 
        "  <g:price>250000.00</g:price>" +
        "  <link href=\"http://gc-shop-en.appspot.com/web/front/goods.action?id=agpnYy1zaG9wLXpocg4LEgVHb29kcyIDX2czDA\"/>\n" +
        
        "  <title type=\'text\'>Nokia N97</title>\n" +
        "  <content type=\'xhtml\'>I love it very much.</content>\n" +
        "</entry>"
   */
  public void buildDataItem(Goods goods) throws Exception{
	  StringBuffer sb = new StringBuffer();
	  sb.append("<?xml version=\'1.0\' encoding=\"UTF-8\"?>\n" + 
		        "<entry xmlns=\'http://www.w3.org/2005/Atom\' xmlns:g=\'http://base.google.com/ns/1.0\'>\n" + 
		        "  <category scheme=\'http://base.google.com/categories/itemtypes\' term=\'Products\'/>\n" );
	  SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
	  sb.append("<published>"+sdf.format(new Date(goods.getAddTime()))+"</published>\n");
	  
	  sb.append("<g:id>"+goods.getPkId()+"</g:id>\n" +
			  "<link href='" + webSitePath + "/web/front/goods.action?id=" + goods.getPkId() + "'/>" +
			  "<updated>" + sdf.format(new Date(goods.getLastUpdate())) + "</updated>\n" +
			  		"<title type=\'text\'>" + goods.getGoodsName() + "</title>\n" );
	  if(goods.getIsPromote()){
		  sb.append("<g:expiration_date type='dateTime'> " + sdf.format(new Date(goods.getPromoteEndDate())) + "</g:expiration_date>");
		  sb.append("  <g:price>" + goods.getPromotePrice() + "</g:price>\n" );
	  }
	  else{
		  Calendar calendar = Calendar.getInstance();
		  calendar.setTime(new Date(goods.getAddTime()));
		  calendar.add(Calendar.YEAR,1);  
		  sb.append("<g:expiration_date type='dateTime'> " + sdf.format(calendar.getTime()) + "</g:expiration_date>");
		  sb.append("  <g:price>" + goods.getShopPrice() + "</g:price>\n" );
	  }
	  if( goods.getGalleries().size() > 0 ){
//		  sb.append("<g:image_link>http://gc-shop-zh.appspot.com/admin/dynaImageService.do?fileId=agpnYy1zaG9wLXpocjcLEgVHb29kcyIDX2czDAsSDEdvb2RzR2FsbGVyeSIFX2dnMTEMCxIGRFNGaWxlIgZfZHNmMTMM</g:image_link>");
//		  sb.append("<g:image_link>" + webSitePath + URLConstants.SERVLET_IMAGE + goods.getImageFileId() + "</g:image_link>");
	  }
//	  sb.append("<g:item_language>en</g:item_language>");
	  sb.append("<g:item_type type=\'text\'>Products</g:item_type>\n");
	  
//	  Country for which the item is intended. Default is US, which means that the item will appear on google.com, and not on google.co.uk for example. (ISO 3166-1-alpha-2 two-letter code)
//	  sb.append("<g:target_country>US</g:target_country>");
	  sb.append("  <g:condition>new</g:condition>\n" +
			  "<content type=\'xhtml\'><![CDATA["+goods.getGoodsDesc()+"]]></content>\n" );
	  
	  sb.append("</entry>");
	  DATA_ITEM = sb.toString();
	  
	  System.out.println(DATA_ITEM);
  }
  /**
   * Inserts <code>DATA_ITEM</code> by making a POST request to
   * <code>ITEMS_URL<code>.
   * @param token authentication token obtained using <code>authenticate</code>
   * @throws IOException if an I/O exception occurs while creating/writing/
   *         reading the request
   */
  public String postItem(String token) throws IOException {
	 return extractItemUrlFromResponse(makeHttpRequest(token, ITEMS_FEED, DATA_ITEM, "POST", 
		        HttpURLConnection.HTTP_CREATED));    
  }
  
  public String updateItem(String token, String itemUrl) 
  throws MalformedURLException, IOException {
	  return makeHttpRequest(token, itemUrl, DATA_ITEM, "PUT", 
    HttpURLConnection.HTTP_OK);
  }
  
  public String deleteItem(String token, String itemUrl) 
  throws MalformedURLException, IOException {
	  return makeHttpRequest(token, itemUrl, "" , "DELETE", 
    HttpURLConnection.HTTP_OK);
  }
  private String makeHttpRequest(String token, String url, String item, 
	      String httpMethod, int expectedResponseCode) throws IOException {
	  
	  
	  HttpURLConnection connection = (HttpURLConnection)(new URL(url)).openConnection() ;
	  connection.setInstanceFollowRedirects(false) ;
	    connection.setDoInput(true);
	    connection.setDoOutput(true);
	    
	    // Set the properties of the connection: the http request method, the 
	    // content type, the authorization header and the developer key
	    connection.setRequestMethod(httpMethod);
	    connection.setRequestProperty("Content-Type", "application/atom+xml");
	    connection.setRequestProperty("Authorization", "GoogleLogin auth=" + token);
	    connection.setRequestProperty("X-Google-Key", "key=" + DEVELOPER_KEY);
//	    connection.setRequestProperty("bq", "bq=");

	    // Post the data item
	    if( !item.equals("") ){
	    	OutputStream outputStream = connection.getOutputStream();
	    	outputStream.write(item.getBytes("UTF-8"));
	    	outputStream.close();
	    }

	    // Retrieve the output
	    int responseCode = connection.getResponseCode();
	    if (responseCode == expectedResponseCode) {
	      return toString(connection.getInputStream());
	    } else {
	      throw new RuntimeException(toString(connection.getErrorStream()));
	    }
  }
  public String extractItemUrlFromResponse(String insertResponse) {
	    int startIndex = insertResponse.indexOf("<id>") + 4;
	    int endIndex = insertResponse.indexOf("</id>");
	    String itemUrl = insertResponse.substring(startIndex, endIndex);
	    return itemUrl;
  }
  
}
