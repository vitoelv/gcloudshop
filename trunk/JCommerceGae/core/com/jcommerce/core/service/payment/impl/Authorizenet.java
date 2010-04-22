package com.jcommerce.core.service.payment.impl;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.crypto.KeyGenerator;
import javax.crypto.Mac;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import com.jcommerce.core.model.OrderGoods;
import com.jcommerce.core.model.OrderInfo;
import com.jcommerce.core.model.Payment;
import com.jcommerce.core.service.payment.IPaymentMetaPlugin;
import com.jcommerce.core.service.payment.PaymentConfigFieldMeta;
import com.jcommerce.core.service.payment.PaymentConfigMeta;

public class Authorizenet extends BasePaymentMetaPlugin implements IPaymentMetaPlugin {
	
	private static final PaymentConfigMeta defaultPaymentConfigMeta;
    private static final Map<String, String> defaultConfigValues;
    private static final Map<String, PaymentConfigFieldMeta> fieldMetas;
    public static final String API_LOGIN_ID = "x_login";
    public static final String TRANSACTION_KEY = "x_tran_key";
    public static final String VIRTUAL_METHOD = "alipay_virtual_method";
    public static final String PARTNER = "alipay_partner";
    public static final String CARD_NUM = "x_card_num";
    public static final String LABEL = "label";
    public static final String TEST_MODE = "testMode";
    public static final String URL = "https://test.authorize.net/gateway/transact.dll";
    
    // agent code for gcshop
    public static final String AGENT="C4335319945672464113";
    
    @Override
    protected Map<String, PaymentConfigFieldMeta> getFieldMetas() {
    	return fieldMetas;
    }
    static {
        defaultConfigValues = new HashMap<String, String>();
        defaultConfigValues.put(API_LOGIN_ID, "3x2j2YJ2cz");
        defaultConfigValues.put(TRANSACTION_KEY, "38v5R22X924uHzMc");
        defaultConfigValues.put(LABEL, "Submit Payment Now!!!");
        defaultConfigValues.put(URL, "Submit Payment");
        defaultConfigValues.put(TEST_MODE, "false");
//        defaultConfigValues.put ("x_version", "3.1");
//        defaultConfigValues.put ("x_delim_data", "TRUE");
//        defaultConfigValues.put ("x_delim_char", "|");
//        defaultConfigValues.put ("x_relay_response", "FALSE");
//        defaultConfigValues.put ("x_type", "AUTH_CAPTURE");//The type of credit card transaction    format: AUTH_CAPTURE (default), AUTH_ONLY
//        defaultConfigValues.put ("x_method", "CC");
//        defaultConfigValues.put ("x_card_num", "4111111111111111");
//        defaultConfigValues.put ("x_exp_date", "0115");
        
        
        fieldMetas = new HashMap<String, PaymentConfigFieldMeta>();
        
        PaymentConfigFieldMeta m = null;
        Map<String, String> options = null;
        
        
        m = new PaymentConfigFieldMeta();
        m.setLable("API Login ID");
        m.setOptions(null);
        m.setTip(null);
        fieldMetas.put(API_LOGIN_ID, m);
        
        m = new PaymentConfigFieldMeta();
        m.setLable("Transaction Key");
        m.setOptions(null);
        m.setTip(null);
        fieldMetas.put(TRANSACTION_KEY, m);
        
        m = new PaymentConfigFieldMeta();
        m.setLable("选择支付环境");
        options = new HashMap<String, String>();
        options.put("https://test.authorize.net/gateway/transact.dll", "live environment");
        options.put("https://secure.authorize.net/gateway/transact.dll", "test environment");
        m.setOptions(options);
        m.setTip("您可以选择支付时采用的环境，不过这和Authorize.Net的帐号类型有关，具体情况请咨询Authorize.Net");
        fieldMetas.put(URL, m);
        
        m = new PaymentConfigFieldMeta();
        m.setLable("是否使用测试模式");
        options = new HashMap<String, String>();
        options.put("true", "Test Mode open");
        options.put("false", "Test Mode close");
        m.setOptions(options);
        m.setTip("只有使用真实账户时才可选择打开test mode，默认为close，具体情况请咨询Authorize.Net");
        fieldMetas.put(TEST_MODE, m);
        
        
        
        m = new PaymentConfigFieldMeta();
        m.setLable("Submit button text");
        m.setOptions(null);
        m.setTip(null);
        fieldMetas.put(LABEL, m);
        
        defaultPaymentConfigMeta = new PaymentConfigMeta();
        defaultPaymentConfigMeta.setPayCode("authorizenet");
        defaultPaymentConfigMeta.setPayName("Authorize.net");
        defaultPaymentConfigMeta.setPayFee("40");
        defaultPaymentConfigMeta.setIsOnline(true);
        defaultPaymentConfigMeta.setIsCod(false);
        defaultPaymentConfigMeta.setPayDesc(getDescription());
        defaultPaymentConfigMeta.setPayConfig(getDefaultConfig());
        defaultPaymentConfigMeta.setKeyName("_pay4");
    }
    public PaymentConfigMeta getDefaultConfigMeta() {
    	return defaultPaymentConfigMeta;
    }
    public Authorizenet() {
        super();
    }

//    public String getCode() {
//        return "AlyPay";
//    }
//
//    public String getName() {
//        return "支付宝";
//    }
//    // 支付手续费
//    public String getPayFee() {
//        return "10";
//    }
//    
//    // 在线支付
//    public Boolean isOnline() {
//        return true;
//    }
//    // 货到付款
//    public Boolean isCod() {
//        return false;
//    }
//
    public static String getDescription() {
        return "The Authorize.Net Payment Gateway can help you accept credit card and electronic check payments quickly and affordably.\n"+
        "More than 284,000 merchants trust us for payment processing and online fraud prevention solutions.";
    }

    /*
     *  (non-Javadoc)
_input_charset=utf-8
&agent=C4335319945672464113
&logistics_fee=0
&logistics_payment=BUYER_PAY_AFTER_RECEIVE
&logistics_type=EXPRESS
&notify_url=http%3A%2F%2Fqbd.maifou.net%2Frespond.php%3Fcode%3Dalipay
&out_trade_no=200812193564311
&partner=2088002230861529
&payment_type=1
&price=352.80
&quantity=1
&return_url=http%3A%2F%2Fqbd.maifou.net%2Frespond.php%3Fcode%3Dalipay
&seller_email=hyj_0105%40163.com
&service=create_direct_pay_by_user
&subject=2008121935643
&sign=86056fb8587d94947d89abb4d0c5a29b
&sign_type=MD5  
     * @see com.jcommerce.core.service.payment.IPaymentMetaPlugin#getCode(com.jcommerce.core.model.Order, com.jcommerce.core.model.Payment)
     */
    public String getCode(OrderInfo order, Payment payment , List<OrderGoods> orderGoods) { 
        PaymentConfigMeta configMeta = deserializeConfig(payment.getPayConfig());
        Map<String, String> values = configMeta.getFieldValues();
        
     // an invoice is generated using the date and time 
        Date myDate = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
        String invoice = dateFormat.format(myDate);
        // a sequence number is randomly generated
        Random generator = new Random();
        int sequence = generator.nextInt(1000);
        // a timestamp is generated
        long timeStamp = System.currentTimeMillis()/1000;

        //This section uses Java Cryptography functions to generate a fingerprint
        // First, the Transaction key is converted to a "SecretKey" object
        String fingerprint = null;
        try {
			KeyGenerator kg = KeyGenerator.getInstance("HmacMD5");
			
		
        	SecretKey key = new SecretKeySpec(values.get(TRANSACTION_KEY).getBytes(), "HmacMD5");
        	// A MAC object is created to generate the hash using the HmacMD5 algorithm
        	Mac mac = Mac.getInstance("HmacMD5");
        	mac.init(key);
        	String inputstring = values.get(API_LOGIN_ID) + "^" + sequence + "^" + timeStamp + "^" + order.getOrderAmount() + "^";
        	byte[] result = mac.doFinal(inputstring.getBytes());
        	// Convert the result from byte[] to hexadecimal format
        	StringBuffer strbuf = new StringBuffer(result.length * 2);
        	for(int i=0; i< result.length; i++)
        	{
        		if(((int) result[i] & 0xff) < 0x10)
        			strbuf.append("0");
        		strbuf.append(Long.toString((int) result[i] & 0xff, 16));
        	}
        	fingerprint = strbuf.toString();
        // end of fingerprint generation
        } catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvalidKeyException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

        // Create the HTML form containing necessary SIM post values
        StringBuffer buf = new StringBuffer();
        buf.append("<FORM method='post' action='" + URL + "' target='_blank' >");
    	// Additional fields can be added here as outlined in the SIM integration guide
    	// at: http://developer.authorize.net
        buf.append ("	<INPUT type='hidden' name='x_login' value='" + values.get(API_LOGIN_ID) + "' />");
        buf.append ("	<INPUT type='hidden' name='x_amount' value='" + order.getOrderAmount() + "' />");
        buf.append ("	<INPUT type='hidden' name='x_description' value='" + order.getPayName() + "' />");
        buf.append ("	<INPUT type='hidden' name='x_invoice_num' value='" + invoice + "' />");
        buf.append ("	<INPUT type='hidden' name='x_fp_sequence' value='" + sequence + "' />");
        buf.append ("	<INPUT type='hidden' name='x_fp_timestamp' value='" + timeStamp + "' />");
        buf.append ("	<INPUT type='hidden' name='x_fp_hash' value='" + fingerprint + "' />");
        buf.append ("	<INPUT type='hidden' name='x_test_request' value='" + TEST_MODE + "' />");
        buf.append ("	<INPUT type='hidden' name='x_show_form' value='PAYMENT_FORM' />");
        buf.append ("	<input type='submit' value='" + values.get(LABEL) + "' />");
        buf.append ("</FORM>");
        
        
        return buf.toString();
        
    }
    

    private static String getDefaultConfig() {
        return serialize(defaultConfigValues);
    }



}
