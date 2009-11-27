package com.jcommerce.web.front.action;

import static com.jcommerce.gwt.client.panels.system.IShopConfigMeta.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.apache.struts2.ServletActionContext;
import org.json.JSONException;
import org.json.JSONObject;

import com.jcommerce.core.model.Category;
import com.jcommerce.core.model.Goods;
import com.jcommerce.core.model.User;
import com.jcommerce.core.service.Condition;
import com.jcommerce.core.service.Criteria;
import com.jcommerce.core.service.IDefaultManager;
import com.jcommerce.core.service.IWebManager;
import com.jcommerce.core.service.config.IShopConfigManager;
import com.jcommerce.core.service.payment.IPaymentMetaManager;
import com.jcommerce.core.service.shipping.IShippingMetaManager;
import com.jcommerce.core.util.IConstants;
import com.jcommerce.gwt.client.ModelNames;
import com.jcommerce.gwt.client.model.IGoods;
import com.jcommerce.gwt.client.panels.system.IShopConfigMeta;
import com.jcommerce.web.component.ComponentUrl;
import com.jcommerce.web.component.Navigator;
import com.jcommerce.web.front.action.helper.Pager;
import com.jcommerce.web.to.CategoryWrapper;
import com.jcommerce.web.to.GoodsWrapper;
import com.jcommerce.web.to.HelpCat;
import com.jcommerce.web.to.Lang;
import com.jcommerce.web.to.ShopConfigWrapper;
import com.jcommerce.web.to.WrapperUtil;
import com.jcommerce.web.to.HelpCat.HelpItem;
import com.jcommerce.web.util.LibGoods;
import com.jcommerce.web.util.LibInsert;
import com.jcommerce.web.util.LibMain;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;


public abstract class BaseAction extends ActionSupport implements IPageConstants, IWebConstants, IConstants{
//	private static Map<String, String> constants = new HashMap<String, String>();
	
	private IShippingMetaManager shippingMetaManager;
	private IPaymentMetaManager paymentMetaManager;
	private IWebManager webManager;
	private IDefaultManager defaultManager;
	private IShopConfigManager shopConfigManager;
	private Long queryStartTime = 0L;

	public void debug(String s) {
		System.out.println(" in [BaseAction]: "+s );
	}
	@Override
    public String getText(String s) {
    	return s;
    	// TODO overcome the access error
//    	return super.getText(s);
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
	
//	static {
//		constants.put(KEY_KEYWORDS, "gCloudShop演示站");
//		constants.put(KEY_DESCRIPTION, "gCloudShop演示站");
//		constants.put(KEY_PAGE_TITLE, "gCloudShop演示站 - Powered by gCloudShop");
//		// TODO
//		constants.put("keywords", "gCloudShop演示站");
//		constants.put("keywords", "gCloudShop演示站");
//		constants.put("keywords", "gCloudShop演示站");
//		constants.put("keywords", "gCloudShop演示站");
//		constants.put("keywords", "gCloudShop演示站");
//		constants.put("keywords", "gCloudShop演示站");
//		constants.put("keywords", "gCloudShop演示站");
//		constants.put("keywords", "gCloudShop演示站");
//		constants.put("keywords", "gCloudShop演示站");
//	}
	
	public void setPageMeta(HttpServletRequest request) {
        request.setAttribute(KEY_KEYWORDS, getCachedShopConfig().getString(IShopConfigMeta.CFG_KEY_SHOP_KEYWORDS));
        request.setAttribute(KEY_DESCRIPTION, getCachedShopConfig().getString(IShopConfigMeta.CFG_KEY_SHOP_DESC));
        request.setAttribute(KEY_PAGE_TITLE, getCachedShopConfig().getString(IShopConfigMeta.CFG_KEY_SHOP_TITLE)+" Powered by gCloudShop");
	}
	
	public void includePageFooter(HttpServletRequest request) {
        // page_footer.ftl
		ShopConfigWrapper shopConfig = getCachedShopConfig();
        request.setAttribute("copyright", "Copyright © 2005-2009 GCSHOP 版权所有，并保留所有权利。");
        request.setAttribute("shopAddress", "Shop Address");
        request.setAttribute("shopPostcode", "Postcode:100000");
//      request.setAttribute("copryright", "Copyright"); //??
        request.setAttribute("servicePhone", "010-11110000"); 
        request.setAttribute("serviceEmail", "test@gmail.com");
        if(!shopConfig.getString(CFG_KEY_SHOP_QQ).equals("")){
        	request.setAttribute("qq", shopConfig.getString(CFG_KEY_SHOP_QQ).split(","));
        }else{
        	request.setAttribute("qq", new String[0]);
        }
        if(!shopConfig.getString(CFG_KEY_SHOP_WW).equals("")){
        	request.setAttribute("ww", shopConfig.getString(CFG_KEY_SHOP_WW).split(","));
        }else{
        	request.setAttribute("ww", new String[0]);
        }
        if(!shopConfig.getString(CFG_KEY_SHOP_YM).equals("")){
        	request.setAttribute("ym", shopConfig.getString(CFG_KEY_SHOP_YM).split(","));
        }else{
        	request.setAttribute("ym", new String[0]);
        }
        if(!shopConfig.getString(CFG_KEY_SHOP_MSN).equals("")){
        	request.setAttribute("msn", shopConfig.getString(CFG_KEY_SHOP_MSN).split(","));
        }else{
        	request.setAttribute("msn", new String[0]);
        }
        if(!shopConfig.getString(CFG_KEY_SHOP_SKYPE).equals("")){
        	request.setAttribute("skype", shopConfig.getString(CFG_KEY_SHOP_SKYPE).split(","));
        }else{
        	request.setAttribute("skype", new String[0]);
        }
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
    public void includePromotionInfo(HttpServletRequest request) {
    	// promotion_info.ftl
    	
        request.setAttribute("promotionInfo", new ArrayList());
    }


	public void includeCart() {
		String res = LibInsert.insertCartInfo(getDefaultManager(), getRequest());
		// TODO need convert insert clause to a variable during transform to .ftl
		getRequest().setAttribute("insert_cart_info", res);
	}
	public void includeCategoryTree(HttpServletRequest request) {

        List<CategoryWrapper> list = LibGoods.getCategoriesTree(null, getDefaultManager());
        request.setAttribute("categories", list);		
	}

	public void includeMemberInfo() {
		String userId = (String)getSession().getAttribute(KEY_USER_ID);
		if(userId != null) {
			getRequest().setAttribute("userInfo", LibMain.getUserInfo(userId, getDefaultManager()));
			
		}
		else {
			
		}
		
	}
	public void includeHistory(HttpServletRequest request) {
		Lang lang = getLangMap(request);
		lang.put("viewHistory", "查看历史");
	}
	public void includeComments(HttpServletRequest request) {
	}
    public void includeRecommendBest(HttpServletRequest request) {
    	setCatRecSign(request);
        request.setAttribute("bestGoods", getBestSoldGoods());
    }
    public void includeRecommendHot(HttpServletRequest request) {
    	setCatRecSign(request);
        request.setAttribute("hotGoods", getHostSoldGoods());
    }
    public void includeRecommendNew(HttpServletRequest request) {
    	setCatRecSign(request);
        request.setAttribute("newGoods", getNewlyAddedGoods());
    }
    public void setCatRecSign(HttpServletRequest request) {
    	// refer to logic at index.php line 60
        request.setAttribute("catRecSign", 0);
        // refer to logic at index.php line 121
        request.setAttribute("catRec", new String[2]);
    }
    private List<Goods> getBestSoldGoods() {
        Criteria c1 = new Criteria();
        Condition cond1 = new Condition();
        cond1.setField(IGoods.IS_BEST);
        cond1.setOperator(Condition.EQUALS);
        cond1.setValue("true");
        c1.addCondition(cond1);
        List<Goods> list = (List<Goods>)getDefaultManager().getList(ModelNames.GOODS, c1,0,5);
        return filterGoods(list);
         
    }
    private List<Goods> getHostSoldGoods() {
        Criteria c1 = new Criteria();
        Condition cond1 = new Condition();
        cond1.setField(IGoods.IS_HOT);
        cond1.setOperator(Condition.EQUALS);
        cond1.setValue("true");
        c1.addCondition(cond1);
        
        List<Goods> list = (List<Goods>)getDefaultManager().getList(ModelNames.GOODS, c1,0,5);
        return filterGoods(list);

    }
    private List<Goods> getNewlyAddedGoods() {
        Criteria c1 = new Criteria();
        Condition cond1 = new Condition();
        cond1.setField(IGoods.IS_NEW);
        cond1.setOperator(Condition.EQUALS);
        cond1.setValue("true");
        c1.addCondition(cond1);
        List<Goods> list = (List<Goods>)getDefaultManager().getList(ModelNames.GOODS, c1,0,5);
        return filterGoods(list);

    }
    private List filterGoods(List<Goods> list) {
    	return WrapperUtil.wrap(list, GoodsWrapper.class);
    }
    
    public void includeComment (Long type, String id) {
    	LibInsert.insertComments(type, id, getDefaultManager(), getRequest(), getCachedShopConfig());
    }
    
    public void includeUrHere() {
    	// provide a default value to avoid freeMarker error
    	// however this could be override by calling LibMain.assignUrHere from Action
    	
    	getRequest().setAttribute("urHere", "TODO: urHere");
    	
    }
    
	public void includeFilterAttr() {
		// TODO includeFilterAttr
	}
	public void includePriceGrade() {
		// TODO includePriceGrade
	}
	public void includeHelp(HttpServletRequest request) {
		List<HelpCat> helps = new ArrayList<HelpCat>();
		List<HelpItem> item = new ArrayList<HelpItem>();
		item.add(new HelpItem("article.action?id=1", "订购方式", "订购方式"));
		item.add(new HelpItem("article.action?id=1", "购物流程", "购物流程"));
		item.add(new HelpItem("article.action?id=1", "售后流程", "售后流程"));
		helps.add(new HelpCat("新手上路", item));
		
		item = new ArrayList<HelpItem>();
		item.add(new HelpItem("article.action?id=1", "如何分辨水货手机", "如何分辨水货手机"));
		item.add(new HelpItem("article.action?id=1", "如何享受全国联保", "如何享受全国联保"));
		item.add(new HelpItem("article.action?id=1", "如何分辨原装电池", "如何分辨原装电池"));
		helps.add(new HelpCat("手机常识", item));
		
		item = new ArrayList<HelpItem>();
		item.add(new HelpItem("article.action?id=1", "支付方式说明", "支付方式说明"));
		item.add(new HelpItem("article.action?id=1", "配送支付智能查询", "配送支付智能查询"));
		item.add(new HelpItem("article.action?id=1", "货到付款区域", "货到付款区域"));
		helps.add(new HelpCat("配送与支付", item));
		
		item = new ArrayList<HelpItem>();
		item.add(new HelpItem("article.action?id=1", "产品质量保证", "产品质量保证"));
		item.add(new HelpItem("article.action?id=1", "售后服务保证", "售后服务保证"));
		item.add(new HelpItem("article.action?id=1", "退换货原则", "退换货原则"));
		helps.add(new HelpCat("服务保证", item));
		
		item = new ArrayList<HelpItem>();
		item.add(new HelpItem("article.action?id=1", "投诉与建议", "投诉与建议"));
		item.add(new HelpItem("article.action?id=1", "选机咨询", "选机咨询"));
		item.add(new HelpItem("article.action?id=1", "网站故障报告", "网站故障报告"));
		helps.add(new HelpCat("联系我们", item));
		
		item = new ArrayList<HelpItem>();
		item.add(new HelpItem("article.action?id=1", "我的订单", "我的订单"));
		item.add(new HelpItem("article.action?id=1", "我的收藏", "我的收藏"));
		item.add(new HelpItem("article.action?id=1", "资金管理", "资金管理"));
		helps.add(new HelpCat("会员中心", item));
		
		request.setAttribute("helps", helps);
		
		
	}
	public void includePageHeader(HttpServletRequest request) {
        
		includeMemberInfo();
		// Navigator ............
		Lang lang = Lang.getInstance();

		Navigator nav = new Navigator();
        nav.addTop(new ComponentUrl("cart.action", getText(lang.getString("viewCart")), 1));
        nav.addTop(new ComponentUrl("user.action", getText(lang.getString("userCenter")), 1));
        nav.addTop(new ComponentUrl("pick_out.action", getText(lang.getString("pickOut")), 1));
        nav.addTop(new ComponentUrl("group_by.action", getText(lang.getString("groupBuy")), 1));
        nav.addTop(new ComponentUrl("snatch.action", getText(lang.getString("snatch")), 1));
        nav.addTop(new ComponentUrl("tag_cloud.action", getText(lang.getString("tagCloud")), 1));
        
        
        nav.addBottom(new ComponentUrl("article.action?id=1", getText("免责条款"), 1));
        nav.addBottom(new ComponentUrl("article.action?id=2", getText("隐私保护"), 1));
        nav.addBottom(new ComponentUrl("article.action?id=3", getText("咨询热点"), 1));
        nav.addBottom(new ComponentUrl("article.action?id=4", getText("联系我们"), 1));
        nav.addBottom(new ComponentUrl("article.action?id=5", getText("公司简介"), 1));
        nav.addBottom(new ComponentUrl("wholesale.action", getText("批发方案"), 1));
        nav.addBottom(new ComponentUrl("myship.action", getText("配送方式"), 1));
        
        
        
        nav.getConfig().put("index", 1);

//        nav.addMiddle(new ComponentUrl("home.action", getText("home_title"), true, true));
//        nav.addMiddle(new ComponentUrl("home.action", "TODOtitle", 1, 1));
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
    public HttpServletResponse getResponse() {
        ActionContext ctx = ActionContext.getContext();        
        HttpServletResponse response = (HttpServletResponse)ctx.get(ServletActionContext.HTTP_RESPONSE);        
        return response;
    }
    public HttpSession getSession() {
    	return getRequest().getSession();
    }
    public void beforeExecute(){
    	queryStartTime = System.currentTimeMillis();
    	
    	HttpServletRequest request = getRequest();
//      HttpServletResponse response = (HttpServletResponse)ctx.get(ServletActionContext.HTTP_RESPONSE); 
      
		String queryString = request.getQueryString();
		String requestURL = request.getRequestURI();
		
		String reqStr = StringUtils.isBlank(queryString) ? requestURL : requestURL+"?"+queryString;

		debug("currentQueryString=["+reqStr+"]");
		debug("lastQueryString=["+getSession().getAttribute("currentQueryString")+"]");
		getSession().setAttribute("lastQueryString", getSession().getAttribute("currentQueryString"));
		getSession().setAttribute("currentQueryString", reqStr);

		
		Object obj = getSession().getAttribute("WW_TRANS_I18N_LOCALE");
		debug("locale: "+obj);
		
      setPageMeta(request);
      includeHelp(request);
      includeUrHere();
      includePageFooter(request);
      getLangMap(request);
      includePageHeader(request);
      setSessionUser(request);
      
      // just empty
      ShopConfigWrapper shopConfig = getCachedShopConfig();
      request.setAttribute("cfg", shopConfig);
      // TODO
      request.setAttribute("shopName", shopConfig.get(CFG_KEY_SHOP_NAME));
      
//      setShowMarketplace(request);
      	assignSmartyGlobal();
    }
    
	public String execute() throws Exception {
		beforeExecute();
        String returnValue = onExecute();
        afterExecute();
		return returnValue;
	}
	public abstract String onExecute() throws Exception; 
	public void afterExecute(){
		new LibInsert().insertQueryTime(getDefaultManager(),queryStartTime,getRequest());
	}

	public void assignSmartyGlobal() {
		HttpServletRequest request = getRequest();
        Map<String, Object> smarty = new HashMap<String, Object>();

        Map<String, String> server = new HashMap<String, String>();
        server.put("PHP_SELF", getSelfURL());        
        smarty.put("server", server);
        
        Map<String, Object> session = new HashMap<String, Object>();
        session.put("userName", getSession().getAttribute(KEY_USER_NAME));
        String email = (String)getSession().getAttribute(KEY_USER_EMAIL);
        session.put("email",  email==null? "" : email);
        smarty.put("session", session);

        request.setAttribute("smarty", smarty);
	}
	
	protected String getSelfURL() {
		return "";
	}
	
//	public String getConstants(String key) {
//		return constants.get(key);
//	}
	
	public ShopConfigWrapper getCachedShopConfig() {
		return getShopConfigManager().getCachedShopConfig();
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
	public IPaymentMetaManager getPaymentMetaManager() {
		return paymentMetaManager;
	}
	public void setPaymentMetaManager(IPaymentMetaManager paymentMetaManager) {
		this.paymentMetaManager = paymentMetaManager;
	}
	public IShippingMetaManager getShippingMetaManager() {
		return shippingMetaManager;
	}
	public void setShippingMetaManager(IShippingMetaManager shippingMetaManager) {
		this.shippingMetaManager = shippingMetaManager;
	}
	public IShopConfigManager getShopConfigManager() {
		return shopConfigManager;
	}
	public void setShopConfigManager(IShopConfigManager shopConfigManager) {
		this.shopConfigManager = shopConfigManager;
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
