package com.jcommerce.web.front.action;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.struts2.ServletActionContext;
import org.json.JSONException;
import org.json.JSONObject;

import com.jcommerce.core.model.Category;
import com.jcommerce.core.model.ShopConfig;
import com.jcommerce.core.model.User;
import com.jcommerce.core.service.IDefaultManager;
import com.jcommerce.core.service.IWebManager;
import com.jcommerce.core.util.IConstants;
import com.jcommerce.gwt.client.ModelNames;
import com.jcommerce.web.component.ComponentUrl;
import com.jcommerce.web.component.Navigator;
import com.jcommerce.web.front.action.helper.Pager;
import com.jcommerce.web.to.CategoryWrapper;
import com.jcommerce.web.to.Lang;
import com.jcommerce.web.to.ShopConfigWrapper;
import com.jcommerce.web.to.WrapperUtil;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

public class BaseAction extends ActionSupport implements IPageConstants, IWebConstants, IConstants{
	private static Map<String, String> constants = new HashMap<String, String>();
	
	protected IWebManager webManager;
	protected IDefaultManager defaultManager;
	
	public void debug(String s) {
		System.out.println(" in [BaseAction]: "+s );
	}
	@Override
    public String getText(String s) {
    	return s;
    	// TODO overcome the access error
//    	return super.getText(s);
    }
	
	public ShopConfigWrapper getShopConfigWrapper() {
		ShopConfig sc =  new ShopConfig();
		return (ShopConfigWrapper)WrapperUtil.wrap(sc, ShopConfigWrapper.class);
	}
	
	public JSONObject getReqAsJSON(HttpServletRequest request, String paraName) {
		try {
//			InputStream input = request.getInputStream();
//			byte[] rawReq = IOUtils.toByteArray(input);
//			String reqStr = new String(rawReq, ENC);
			String reqStr = request.getParameter(paraName);
			debug("in [getReqAsJSON]: reqStr="+reqStr);
			JSONObject req = new JSONObject(reqStr);
			return req;
		} catch (JSONException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}		
	}
	
	static {
		constants.put(KEY_KEYWORDS, "gCloudShop演示站");
		constants.put(KEY_DESCRIPTION, "gCloudShop演示站");
		constants.put(KEY_PAGE_TITLE, "gCloudShop演示站 - Powered by gCloudShop");
		// TODO
		constants.put("keywords", "gCloudShop演示站");
		constants.put("keywords", "gCloudShop演示站");
		constants.put("keywords", "gCloudShop演示站");
		constants.put("keywords", "gCloudShop演示站");
		constants.put("keywords", "gCloudShop演示站");
		constants.put("keywords", "gCloudShop演示站");
		constants.put("keywords", "gCloudShop演示站");
		constants.put("keywords", "gCloudShop演示站");
		constants.put("keywords", "gCloudShop演示站");
		
		
		
	}
	
	public void setPageMeta(HttpServletRequest request) {
        request.setAttribute(KEY_KEYWORDS, getConstants(KEY_KEYWORDS));
        request.setAttribute(KEY_DESCRIPTION, getConstants(KEY_DESCRIPTION));
        request.setAttribute(KEY_PAGE_TITLE, getConstants(KEY_PAGE_TITLE));
	}
	
	public void includePageFooter(HttpServletRequest request) {
        // page_footer.ftl
        request.setAttribute("copyright", "Copyright");
        request.setAttribute("shopAddress", "Shop Address");
        request.setAttribute("shopPostcode", "Postcode:1000000");
        request.setAttribute("copryright", "Copyright"); //??
        request.setAttribute("servicePhone", "010-11110000"); 
        request.setAttribute("serviceEmail", "test@gmail.com");
        request.setAttribute("qq", new String[0]);
        request.setAttribute("ww", new String[0]);
        request.setAttribute("ym", new String[0]);
        request.setAttribute("msn", new String[0]);
        request.setAttribute("skype", new String[0]);
        request.setAttribute("ecsVersion", "1.0");
        request.setAttribute("licensed", "licensed");
        request.setAttribute("feedUrl", "feedUrl");
	}
	public Lang getLangMap(HttpServletRequest request) {
		Lang lang = (Lang)request.getAttribute("lang");
		if(lang == null) {
			lang = Lang.getInstance();
			request.setAttribute("lang", lang);
		}
        
		return lang;
	}

	public void includeUrHere(HttpServletRequest request) {
		// ur_here.ftl
        request.setAttribute("urHere", "urHere");		
	}
	public void includeCart(HttpServletRequest request) {
		// cart.ftl
	}
	public void includeCategoryTree(HttpServletRequest request) {
        List<Category> categories = (List<Category>)getDefaultManager().getList(ModelNames.CATEGORY, null);
        categories.size();
        List<CategoryWrapper> list = WrapperUtil.wrap(categories, CategoryWrapper.class); 
        request.setAttribute("categories", list);		
	}

	public void includeHistory(HttpServletRequest request) {
		Lang lang = getLangMap(request);
		lang.put("viewHistory", "查看历史");
	}
	public void includeComments(HttpServletRequest request) {
	}
	public void includeHelp(HttpServletRequest request) {
	}
	public void includePageHeader(HttpServletRequest request) {
        // Navigator ............
        
        Navigator nav = new Navigator();
        nav.addTop(new ComponentUrl("cart.action", getText("browse_cart"), 1));
        nav.addTop(new ComponentUrl("user.action", getText("user_center"), 1));
        nav.addTop(new ComponentUrl("pick_out.action", getText("pick_out_center"), 1));
        nav.addTop(new ComponentUrl("group_by.action", getText("buy_by_group"), 1));
        nav.addTop(new ComponentUrl("snatch.action", getText("snatch"), 1));
        nav.addTop(new ComponentUrl("tag_cloud.action", getText("tag_cloud"), 1));
        
        nav.getConfig().put("index", 1);

//        nav.addMiddle(new ComponentUrl("home.action", getText("home_title"), true, true));
        nav.addMiddle(new ComponentUrl("home.action", "TODOtitle", 1, 1));
        List<Category> categoryList = defaultManager.getList(ModelNames.CATEGORY, null);
        Iterator<Category> it = categoryList.iterator();
        while (it.hasNext()) {
            Category cat = it.next();
            if (cat.getShowInNav() == DBTYPE_TRUE)
                nav.addMiddle(new ComponentUrl("category.action?id="+cat.getPkId(), cat.getCatName(), 1, 0));
        }

        request.setAttribute("navigatorList", nav);	
        
        request.setAttribute("categoryList", getCategoryList(categoryList));
        
        // Search key words ..........
        ArrayList searchKeywords = new ArrayList();
        request.setAttribute("searchkeywords", searchKeywords);
        request.setAttribute("searchKeywords", "");
	}
    private String getCategoryList(List<Category> list) {
    	StringBuffer buf = new StringBuffer();
    	int i=1;
    	for(Category category:list) {
    		buf.append("<option value=\"").append(i).append("\" >").append(category.getCatName()).append("</option>").append("\r\n");
    		i++;
    	}
    	return buf.toString();
    }
	public void setShowMarketplace(HttpServletRequest request) {
        request.setAttribute("showMarketprice", true);		
	}
	public void setNowtime(HttpServletRequest request) {
        request.setAttribute("nowTime", new Date().toString());		
	}
	
	public void setSessionUser(HttpServletRequest request) {
        User user = new User();
        user.setUserName("Guest");
        request.getSession().setAttribute("user_info", user);
	}
    protected void initPager(HttpServletRequest request) {
        Pager pager = new Pager();
        request.setAttribute("pager", pager);
    }
    
    public HttpServletRequest getRequest() {
        ActionContext ctx = ActionContext.getContext();        
        HttpServletRequest request = (HttpServletRequest)ctx.get(ServletActionContext.HTTP_REQUEST);        
        return request;
    }
    public HttpSession getSession() {
    	return getRequest().getSession();
    }
    
	public String execute() throws Exception {
		
		HttpServletRequest request = getRequest();
//        HttpServletResponse response = (HttpServletResponse)ctx.get(ServletActionContext.HTTP_RESPONSE); 
        
		Object obj = getSession().getAttribute("WW_TRANS_I18N_LOCALE");
		debug("locale: "+obj);
		
        setPageMeta(request);
        includePageFooter(request);
        getLangMap(request);
        includePageHeader(request);
        setSessionUser(request);
        
        // just empty
        ShopConfigWrapper shopConfig = getShopConfigWrapper();
        request.setAttribute("cfg", shopConfig);
        
//        setShowMarketplace(request);

        
		return null;
	}
	

	
	
	public String getConstants(String key) {
		return constants.get(key);
	}

	public IDefaultManager getDefaultManager() {
		return defaultManager;
	}

	public void setDefaultManager(IDefaultManager defaultManager) {
		this.defaultManager = defaultManager;
	}
	public IWebManager getWebManager() {
		return webManager;
	}
	public void setWebManager(IWebManager webManager) {
		this.webManager = webManager;
	}
	
	
	public Map<String, Object> getMyParameters() {
//		return parameters;
		Map parameters = ActionContext.getContext().getParameters();
		return parameters;
	}
	
	public String getMyParameter(String key) {
		String res = null;
		Map<String, Object> parameters = getMyParameters();
		Object obj = parameters.get(key);
		if(obj instanceof String[]) {
			res = ((String[])obj)[0];
		}
		else if(obj instanceof String) {
			res = (String)obj;
		}
		return res;
	}


	
	// well, probably we need not this. 
	// simply call method for each included library
//	public void executeAction(String actionName) {
//		BaseAction ba = (BaseAction)getSpringContext().getBean(actionName);
//	}
//	WebApplicationContext springContext = null;
//	public WebApplicationContext getSpringContext() {
//		if(springContext==null) {
//			ServletContext sc = ServletActionContext.getServletContext();
//			springContext = WebApplicationContextUtils.getWebApplicationContext(sc);
//		}
//		return springContext;
//	}

	
}
