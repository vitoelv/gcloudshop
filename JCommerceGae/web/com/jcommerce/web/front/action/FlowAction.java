package com.jcommerce.web.front.action;

import java.io.InputStream;
import java.io.StringBufferInputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.struts2.ServletActionContext;
import org.json.JSONException;
import org.json.JSONObject;

import com.jcommerce.core.model.Cart;
import com.jcommerce.core.model.Constants;
import com.jcommerce.core.model.Order;
import com.jcommerce.core.model.Payment;
import com.jcommerce.core.model.Region;
import com.jcommerce.core.model.Shipping;
import com.jcommerce.core.service.Condition;
import com.jcommerce.core.service.Criteria;
import com.jcommerce.gwt.client.model.ICart;
import com.jcommerce.gwt.client.model.IRegion;
import com.jcommerce.web.to.CartWrapper;
import com.jcommerce.web.to.Consignee;
import com.jcommerce.web.to.OrderWrapper;
import com.jcommerce.web.to.PaymentWrapper;
import com.jcommerce.web.to.RegionWrapper;
import com.jcommerce.web.to.ShippingWrapper;
import com.jcommerce.web.to.Total;
import com.jcommerce.web.to.WrapperUtil;
import com.jcommerce.web.util.FormatUtils;
import com.opensymphony.xwork2.ActionContext;

public class FlowAction extends BaseAction {
	public void debug(String s) {
		System.out.println(" in [FlowAction]: "+s );
	}
	
	public static final String PAGE_ADD_TO_CART = "addToCart";
	
	public static final String KEY_STEP = "step";
	public static final String STEP_ADD_TO_CART = "add_to_cart";
	public static final String STEP_CART = "cart";
    public static final String STEP_CHECKOUT = "checkout";
    public static final String STEP_CONSIGNEE = "consignee";
    public static final String STEP_DONE = "done";
    
    
    private InputStream jsonRes;


    
    private String stepAddToCart(HttpServletRequest request) throws JSONException{
		setOrder(request);
		setShipping(request);
		setPayment(request);
		
		HttpSession session = request.getSession();
		
		String userId = (String)session.getAttribute(KEY_USER_ID);

		JSONObject goods = getReqAsJSON(request, "goods");
		String goodsId = goods.getString("goods_id");
		debug("in [addToCart]: goodsId="+goodsId);
		
		boolean suc = getWebManager().addToCart(
				goodsId , goods.getInt("number"), null, request.getSession().getId(), userId, null);
		
		JSONObject res = new JSONObject();;
		res.put("error", 0);
		res.put("content", "<B>cart info</B>");
		res.put("one_step_buy", "1");

		String out = res.toString();
		debug("in [addToCart]: out="+out);
		jsonRes = new StringBufferInputStream(out);	
    	return PAGE_ADD_TO_CART;
    }
    
    private String stepCart(HttpServletRequest request) {

    	HttpSession session = request.getSession();
    	
    	session.setAttribute(KEY_FLOW_TYPE, Constants.CART_GENERAL_GOODS);
        /* 如果是一步购物，跳到结算中心 */
//        if ($_CFG['one_step_buy'] == '1')
    	if(true) {
    		return stepCheckout(request);
    	}else {
		JSONObject json = new JSONObject();;
		try {
			json.put("error", 0);
			json.put("content", "<B>cart info</B>");
			json.put("one_step_buy", "1");
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String out = json.toString();
		debug("in [addToCart]: out="+out);
		jsonRes = new StringBufferInputStream(out);	
    	return PAGE_ADD_TO_CART;
    	}
    }
    private Consignee getConsignee(String userId) {
    	Consignee consignee = (Consignee)getSession().getAttribute(KEY_FLOW_CONSIGNEE);
    	if(consignee != null) {
    		
    	} else {
    		
    	}
    	return consignee;
    }
    
    private boolean checkConsigneeInfo(Consignee consignee, Integer flowType) {
    	return consignee != null;
    }
    private List<RegionWrapper> getCountryList() {
    	Region r = new Region();
    	r.setId("1");
    	r.setRegionName("中国");
    	r.setRegionType(IRegion.TYPE_COUNTRY);
    	RegionWrapper rw = new RegionWrapper(r);
    	List<RegionWrapper> res = new ArrayList<RegionWrapper>();
    	res.add(rw);
    	return res;
    }
    private List<RegionWrapper> getProvinceList() {
    	List<RegionWrapper> res = new ArrayList<RegionWrapper>();
    	Region r = new Region();
    	r.setParentId("1");
    	r.setId("2");
    	r.setRegionName("北京");
    	r.setRegionType(IRegion.TYPE_PROVINCE);
    	RegionWrapper rw = new RegionWrapper(r);
    	res.add(rw);
    	
    	r = new Region();
    	r.setParentId("1");
    	r.setId("3");
    	r.setRegionName("广东");
    	r.setRegionType(IRegion.TYPE_PROVINCE);
    	rw = new RegionWrapper(r);
    	res.add(rw);
    	return res;
    }

    private List<RegionWrapper> getCityList() {
    	List<RegionWrapper> res = new ArrayList<RegionWrapper>();
    	Region r = new Region();
    	r.setParentId("3");
    	r.setId("4");
    	r.setRegionName("广州");
    	r.setRegionType(IRegion.TYPE_CITY);
    	RegionWrapper rw = new RegionWrapper(r);
    	res.add(rw);
    	
    	r = new Region();
    	r.setParentId("3");
    	r.setId("5");
    	r.setRegionName("深圳");
    	r.setRegionType(IRegion.TYPE_CITY);
    	rw = new RegionWrapper(r);
    	res.add(rw);
    	

    	return res;
    }
    

    private String stepConsignee(HttpServletRequest request, boolean isSubmission) {
    	if(!isSubmission) {
    	if(request.getAttribute(KEY_DIRECT_SHOPPING)!=null) {
    		getSession().setAttribute(KEY_DIRECT_SHOPPING, 1);
    	}
    	
		
		
		
    	List<Consignee> consigneeList = new ArrayList<Consignee>();
    	Consignee consignee = (Consignee)getSession().getAttribute(KEY_FLOW_CONSIGNEE);
    	if(consignee == null) {
    		consignee = new Consignee();
    		consignee.put("country", getShopConfigWrapper().get(CFG_KEY_SHOP_COUNTRY));
    	}
		consigneeList.add(consignee);
		
    	request.setAttribute("consigneeList", consigneeList);
    	

    	request.setAttribute("countryList", getCountryList());
    	
    	List<List<RegionWrapper>> provinceList = new ArrayList<List<RegionWrapper>>();
    	provinceList.add(getProvinceList());
    	request.setAttribute("provinceList", provinceList);
    	
    	List<List<RegionWrapper>> cityList = new ArrayList<List<RegionWrapper>>();
    	cityList.add(getCityList());
    	request.setAttribute("cityList", cityList);
    	List<List<RegionWrapper>> districtList = new ArrayList<List<RegionWrapper>>();
    	districtList.add(new ArrayList<RegionWrapper>());
    	request.setAttribute("districtList", districtList);
    	request.setAttribute("realGoodsCount", 1);
    	request.setAttribute("nameOfRegion", new String[]{"请选择国家", "请选择省", "请选择市", "请选择区"});
    	
    	request.setAttribute(KEY_STEP, STEP_CONSIGNEE);
    	
    	return SUCCESS;    
    	} 
    	else {
    		Consignee c = new Consignee();
    		c.put("addressId", 1);
    		c.put("consignee", 1);
    		c.put("country", 1);
    		c.put("province", 1);
    		c.put("city", 1);
    		c.put("district", 1);	
    		getSession().setAttribute(KEY_FLOW_CONSIGNEE, c);
    		
    		return stepCheckout(request);
    	}
    }
    private String stepCheckout(HttpServletRequest request) {
        /*------------------------------------------------------ */
        //-- 订单确认
        /*------------------------------------------------------ */
    	HttpSession session = request.getSession();
    	String userId = (String)session.getAttribute(KEY_USER_ID);
    	
    	Integer flowType = (Integer)session.getAttribute(KEY_FLOW_TYPE);
//    	if(flowType==CART_GROUP_BUY_GOODS) 
    	
    	List<Cart> carts = cartGoods(flowType);
    	
    	Consignee consignee = getConsignee(userId);
    	
    	if(!checkConsigneeInfo(consignee, flowType)) {
    		return stepConsignee(request, false);
    	}

    	request.setAttribute("consignee", consignee);
    	request.setAttribute("goodsList", WrapperUtil.wrap(carts, CartWrapper.class));
    	
    	request.setAttribute("allowEditCart", 0);
    	request.setAttribute("config", getShopConfigWrapper());
    	
        /*
         * 取得订单信息
         */
    	Order order = flowOrderInfo();
    	OrderWrapper ow = new OrderWrapper(order);
    	getRequest().setAttribute("order", ow);
    	

        /*
         * 计算订单的费用
         */
    	Total total = orderFee(order,carts, consignee);
    	
    	// debug
    	debug("in [flowcheckout]: realGoodsCount="+total.getRealGoodsCount());
    	
    	request.setAttribute("total", total);
    	request.setAttribute("shoppingMoney", total.getFormatedGoodsPrice());
    	request.setAttribute("marketPriceDesc", "market Price: xxx, total saving: yyy, saving rate: zzz");
    	
    	List<ShippingWrapper> shippingList = availableShippingList();
    	request.setAttribute("shippingList", shippingList);
    	
    	boolean insureDisabled = true;
    	boolean codDisabled = true;
    	request.setAttribute("insureDisabled", insureDisabled);
    	request.setAttribute("codDisabled", codDisabled);
    
    	List<PaymentWrapper> paymentList = availablePaymentList();
    	request.setAttribute("paymentList", paymentList);
    	
//    	if(order.getShipping() == null || order.getPayment() == null) {
//    		
//    	}

    	
    	request.setAttribute(KEY_STEP, STEP_CHECKOUT);
    	return SUCCESS;
    }
    private List<Cart> cartGoods(Integer flowType) {
    	Criteria cr = new Criteria();
    	Condition cond = new Condition();
    	cond.setField(ICart.SESSION_ID);
    	cond.setOperator(Condition.EQUALS);
    	cond.setValue(getSession().getId());
    	cr.addCondition(cond);
    	cond = new Condition();
    	cond.setField(ICart.REC_TYPE);
    	cond.setOperator(Condition.EQUALS);
    	cond.setValue(String.valueOf(flowType));
    	cr.addCondition(cond);
    	List<Cart> carts = (List<Cart>)getDefaultManager().getList(Cart.class.getName(), cr);
    	return carts;
    }
    private Order flowOrderInfo(){
    	Order order = (Order)getSession().getAttribute(KEY_FLOW_ORDER);
    	if(order == null) {
    		order = new Order();
    		getSession().setAttribute(KEY_FLOW_ORDER, order);
    	}

    	return order;
    }
    private Total orderFee(Order order, List<Cart> carts, Consignee consignee) {
    	Total total = new Total();
    	for(Cart cart:carts) {
    		total.setRealGoodsCount(total.getRealGoodsCount()+1);
    		total.setGoodsPrice(total.getGoodsPrice() + cart.getGoodsPrice());
    		total.setMarketPrice(total.getMarketPrice() + cart.getMarketPrice());
    	}
    	total.setFormatedGoodsPrice(FormatUtils.priceFormat(total.getGoodsPrice()));
    	total.setFormatedMarketPrice(FormatUtils.priceFormat(total.getMarketPrice()));
    	return total;
    }
    private List<PaymentWrapper> availablePaymentList() {
    	List<PaymentWrapper> list = new ArrayList<PaymentWrapper>();
    	Payment payment = new Payment();
    	payment.setPayFee("1.00");
    	payment.setPayDesc("开通城市：北京货到付款区域：五环内");
    	payment.setPayName("货到付款");
    	
    	PaymentWrapper pw = new PaymentWrapper(payment);
    	pw.put("formatPayFee", "<span id=\"ECS_CODFEE\">"+ payment.getPayFee()  + "</span>");
    	list.add(pw);
    	
    	
    	payment = new Payment();
    	payment.setPayFee("1.00");
    	payment.setPayDesc("银行名称收款人信息：全称 ××× ；帐号或地址 ××× ；开户行 ×××。注意事项：办理电汇时，请在电汇单“汇款用途”一栏处注明您的订单号。");
    	payment.setPayName("银行汇款/转帐");
    	
    	pw = new PaymentWrapper(payment);
    	pw.put("formatPayFee", "<span id=\"ECS_CODFEE\">"+ payment.getPayFee()  + "</span>");
    	list.add(pw);
    	
    	return list;
    }
    
    private List<ShippingWrapper> availableShippingList() {
    	List<ShippingWrapper> list = new ArrayList<ShippingWrapper>();
    	Shipping s = new Shipping();
    	s.setId("1");
    	s.setShippingName("圆通速递");
    	s.setShippingDesc("上海圆通物流（速递）有限公司经过多年的网络快速发展，在中国速递行业中一直处于领先地位。为了能更好的发展国际快件市场，加快与国际市场的接轨，强化圆通的整体实力，圆通已在东南亚、欧美、中东、北美洲、非洲等许多城市运作国际快件业务");
    	s.setInsure("0");
    	
    	ShippingWrapper sw = new ShippingWrapper(s);
    	sw.put("formatShippingFee", "1.00");
    	sw.put("shippingFee", 1.0);
    	sw.put("freeMoney", "1.0");
    	
    	
    	sw.put("insureFormated", "0");
    	// in template it's integer
    	sw.put("insure", 0);
    	
    	list.add(sw);
    	return list;
    }
    private String stepDone(HttpServletRequest request) {
    	
    	Integer flowType = (Integer)getSession().getAttribute(KEY_FLOW_TYPE);
    	if(flowType == null) {
    		flowType = Constants.CART_GENERAL_GOODS;
    	}
    	
    	String userId = (String)getSession().getAttribute(KEY_USER_ID);
    	Consignee consignee = getConsignee(userId);
    	
    	Order order = new Order();
//    	order.setShipping(shipping)
//    	order.setPayment(payment)
//    	order.setUser(user)
    	order.setAddTime(new Date());
    	order.setPayName("货到付款");
    	
    	
    	
    	List<Cart> carts = cartGoods(flowType);

    	Total total = orderFee(order, carts, consignee);
    	order.setGoodsAmount(total.getGoodsPrice());
    	order.setOrderAmount(total.getAmount());
    	order.setOrderSn(getOrderSn());
    	
    	String orderId = getDefaultManager().txadd(order);
    	debug("in [flowDone]: orderId="+orderId);
    	
        /* 清空购物车 */
        clearCart(flowType);
        /* 清除缓存，否则买了商品，但是前台页面读取缓存，商品数量不减少 */
        clearAllFiles();
    	
        request.setAttribute("order", WrapperUtil.wrap(order, OrderWrapper.class));
        request.setAttribute("total", total);
        request.setAttribute("goodsList", WrapperUtil.wrap(carts, CartWrapper.class));
        request.setAttribute("orderSubmitBack", "您可以返回首页或去用户中心");
        
        unset(KEY_FLOW_CONSIGNEE);
        unset(KEY_FLOW_ORDER);
        unset(KEY_DIRECT_SHOPPING);
        
    	request.setAttribute(KEY_STEP, STEP_DONE);
    	return SUCCESS;
    }
    
    private void unset(String key) {
    	getSession().removeAttribute(key);
    }
    private void clearCart(Integer flowType) {
    	
    }
    private void clearAllFiles() {
    	
    }
    private String getOrderSn() {
    	return String.valueOf(new Date().getTime());
    }
    
	@Override
	public String execute() throws Exception {
		try {
			super.execute();
			
			ActionContext ctx = ActionContext.getContext();        
	        HttpServletRequest request = (HttpServletRequest)ctx.get(ServletActionContext.HTTP_REQUEST);      
	        
	        includeUrHere(request);
			includeOrderTotal(request);
			includeHelp(request);
			
			String step = request.getParameter(KEY_STEP);
			debug("step: "+step);
			
			

			
			if(STEP_ADD_TO_CART.equals(step)) {
				return stepAddToCart(request);
			} 
			else if(STEP_CART.equals(step)) {
				return stepCart(request);
			} else if(STEP_CONSIGNEE.equals(step)) {
				return stepConsignee(request, true);
			} else if(STEP_DONE.equals(step)) {
				return stepDone(request);
			}
			else {
				return SUCCESS;
			}
			
			
		} catch (Exception ex) {
			ex.printStackTrace();
			throw new RuntimeException(ex);
		}
	}
	
	public void includeOrderTotal(HttpServletRequest request) {
		// TODO
		Total total = new Total();
		total.setAmountFormated("20.00元");
		request.setAttribute("total", total);
	}
	
	public void setOrder(HttpServletRequest request) {
		Order order = new Order();
		OrderWrapper ow = new OrderWrapper(order);
		request.setAttribute("order", ow);
	}
	public void setShipping(HttpServletRequest request) {
		Shipping shipping = new Shipping();
		ShippingWrapper sw = new ShippingWrapper(shipping);
		request.setAttribute("shipping", sw);
	}
	public void setPayment(HttpServletRequest request) {
		Payment payment = new Payment();
		PaymentWrapper pw = new PaymentWrapper(payment);
		request.setAttribute("payment", pw);
	}

	public InputStream getJsonRes() {
		return jsonRes;
	}
}
