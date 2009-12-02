package com.jcommerce.web.front.action;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.apache.struts2.ServletActionContext;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.jcommerce.core.model.AreaRegion;
import com.jcommerce.core.model.Cart;
import com.jcommerce.core.model.Constants;
import com.jcommerce.core.model.Goods;
import com.jcommerce.core.model.ModelObject;
import com.jcommerce.core.model.OrderGoods;
import com.jcommerce.core.model.OrderInfo;
import com.jcommerce.core.model.Payment;
import com.jcommerce.core.model.Shipping;
import com.jcommerce.core.model.ShippingArea;
import com.jcommerce.core.model.User;
import com.jcommerce.core.model.UserAddress;
import com.jcommerce.core.service.Condition;
import com.jcommerce.core.service.Criteria;
import com.jcommerce.core.service.shipping.IShippingMetaPlugin;
import com.jcommerce.core.service.shipping.impl.BaseShippingMetaPlugin;
import com.jcommerce.gwt.client.ModelNames;
import com.jcommerce.gwt.client.model.ICart;
import com.jcommerce.gwt.client.model.IRegion;
import com.jcommerce.gwt.client.model.IUser;
import com.jcommerce.gwt.client.model.IUserAddress;
import com.jcommerce.gwt.client.panels.system.IShopConfigMeta;
import com.jcommerce.web.to.CartWrapper;
import com.jcommerce.web.to.Lang;
import com.jcommerce.web.to.OrderInfoWrapper;
import com.jcommerce.web.to.PaymentWrapper;
import com.jcommerce.web.to.RegionWrapper;
import com.jcommerce.web.to.ShippingWrapper;
import com.jcommerce.web.to.Total;
import com.jcommerce.web.to.UserAddressWrapper;
import com.jcommerce.web.to.UserWrapper;
import com.jcommerce.web.to.WrapperUtil;
import com.jcommerce.web.util.LibCommon;
import com.jcommerce.web.util.LibMain;
import com.jcommerce.web.util.LibOrder;
import com.jcommerce.web.util.PrintfFormat;
import com.opensymphony.xwork2.ActionContext;

public class FlowAction extends BaseAction {
	public void debug(String s) {
		System.out.println(" in [FlowAction]: "+s );
	}
	
	public static final String RES_ADD_TO_CART = "addToCart";
	
	public static final String KEY_STEP = "step";
	public static final String STEP_ADD_TO_CART = "add_to_cart";
	public static final String STEP_CART = "cart";
    public static final String STEP_CHECKOUT = "checkout";
    public static final String STEP_CONSIGNEE = "consignee";
    public static final String STEP_DONE = "done";
    public static final String STEP_DROP_GOODS = "drop_goods";
    public static final String STEP_CLEAR = "clear";
    public static final String STEP_UPDATE_CART = "update_cart";
    public static final String STEP_LOGIN = "login";
    
    public static final String PARA_GOODS_ID = "goods_id";
    
    
    private InputStream jsonRes;
    private String shipping; 
    private String payment;

    
    private String stepAddToCart(HttpServletRequest request) throws JSONException{
		setOrder(request);
		setShipping(request);
		setPayment(request);
		
		HttpSession session = request.getSession();
		
		String userId = (String)session.getAttribute(KEY_USER_ID);

		JSONObject goods = getReqAsJSON(request, "goods");
		Long goodsId = goods.getLong(PARA_GOODS_ID);
		debug("in [addToCart]: goodsId="+goodsId);
		
		/* 如果是一步购物，先清空购物车 */
		if((Integer)getCachedShopConfig().get("oneStepBuy")==1) {
			// TODO clearCart
			//clearCart();
		}
		
		//修改，获得商品规格
		JSONArray specArray = goods.getJSONArray("spec");
		List spec = new ArrayList();
		for(int i = 0;i < specArray.length();i++) {
			spec.add(specArray.get(i));
		}
		
		boolean suc = getWebManager().addToCart(
				goodsId , goods.getInt("number"), spec, request.getSession().getId(), userId, null);
		
		JSONObject res = new JSONObject();;
		res.put("error", 0);
		res.put("content", "<B>cart info</B>");
		res.put("one_step_buy", (Integer)getCachedShopConfig().get("oneStepBuy"));
		// 购物车确定提示:  直接进入购物车 refer to common.js addToCartResponse
		res.put("confirm_type", "3");

		String out = res.toString();
		debug("in [addToCart]: out="+out);
		try {
			jsonRes = new ByteArrayInputStream(out.getBytes(IWebConstants.ENC));
		} catch (Exception e) {
			e.printStackTrace();
		}
    	return RES_ADD_TO_CART;
    }
    
    private String stepCart(HttpServletRequest request) {
    	// this is the last else clause in flow.php
    	
    	HttpSession session = request.getSession();

		session.setAttribute(KEY_FLOW_TYPE, Constants.CART_GENERAL_GOODS);
		/* 如果是一步购物，跳到结算中心 */
		if ((Integer) getCachedShopConfig().get("oneStepBuy") == 1) {
			return stepCheckout(request);
		} else {
			
			/* 取得商品列表，计算合计 */
			Map<String, Object> cartGoods = LibOrder.getCartGoods(getSession().getId(), getDefaultManager(), getCachedShopConfig());
			request.setAttribute("goodsList", cartGoods.get("goodsList"));
			Total total = (Total)cartGoods.get("total");
			request.setAttribute("total", total);
			
			//购物车的描述的格式化
			request.setAttribute("shoppingMoney", total.getGoodsPriceFormated());
			request.setAttribute("marketPriceDesc", new PrintfFormat(Lang.getInstance().getString("thanMarketPrice")).sprintf(
					new Object[]{total.getMarketPriceFormated(), total.getSavingFormated(), total.getSaveRateFormated()}));
			


			// TODO compute_discount
			request.setAttribute("discount", 0);
			
			 /* 增加是否在购物车里显示商品图 */
			request.setAttribute("showGoodsThumb", getCachedShopConfig().get("showGoodsInCart"));		   
		    /* 增加是否在购物车里显示商品属性 */
			// do not show goodsattr
			System.out.println(getCachedShopConfig().get("showGoodsAttribute"));
			request.setAttribute("showGoodsAttribute", getCachedShopConfig().get("showGoodsAttribute"));
			
			request.setAttribute(KEY_STEP, STEP_CART);
			
			return SUCCESS;
		}
    }
    private UserAddressWrapper getConsignee(String userId) {
    	UserAddressWrapper consignee = (UserAddressWrapper)getSession().getAttribute(KEY_FLOW_CONSIGNEE);
    	if(consignee == null) {
    		if(StringUtils.isNotEmpty(userId)) {
    			 /* 如果不存在，则取得用户的默认收货人信息 */
    			Criteria c = new Criteria();
    			c.addCondition(new Condition(IUserAddress.USER_ID, Condition.EQUALS, userId));
    			List<UserAddress> list = getDefaultManager().getList(ModelNames.USERADDRESS, c);
    			// now we only have one
    			if(list.size()>0) {
    				consignee = new UserAddressWrapper(list.get(0));
    			}
    		}
    	}
		return consignee;
    	
    	
    }
    
    private boolean checkConsigneeInfo(UserAddressWrapper consignee, Long flowType) {
    	return consignee != null;
    }

    

    private String stepConsignee(HttpServletRequest request, boolean isSubmission) {
    	if(request.getParameter(KEY_DIRECT_SHOPPING) != null) {
    		getSession().setAttribute(KEY_DIRECT_SHOPPING, 1);
    	}
    	if(!isSubmission) {
    	if(request.getAttribute(KEY_DIRECT_SHOPPING)!=null) {
    		getSession().setAttribute(KEY_DIRECT_SHOPPING, 1);
    	}
    	
    	includeConsignee(request);
		
		
    	List<UserAddressWrapper> consigneeList = new ArrayList<UserAddressWrapper>();
    	UserAddressWrapper consignee = (UserAddressWrapper)getSession().getAttribute(KEY_FLOW_CONSIGNEE);
    	if(consignee == null) {
    		consignee = new UserAddressWrapper(new UserAddress());
//    		consignee.put("country", ShopConfigWrapper.getDefaultConfig().get(ShopConfigWrapper.CFG_KEY_SHOP_COUNTRY));
    		consignee.getUserAddress().setCountry((String)getCachedShopConfig().get(IShopConfigMeta.CFG_KEY_SHOP_COUNTRY));
    	}
		consigneeList.add(consignee);
		
    	request.setAttribute("consigneeList", consigneeList);
    	


    	
    	request.setAttribute(KEY_STEP, STEP_CONSIGNEE);
    	
    	return SUCCESS;    
    	} 
    	else {
    		UserAddressWrapper c = new UserAddressWrapper(new UserAddress());
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

	private void includeConsignee(HttpServletRequest request) {
		request.setAttribute("countryList", LibCommon.getRegion(IRegion.TYPE_COUNTRY, null, getDefaultManager()));
    	
    	List<List<RegionWrapper>> provinceList = new ArrayList<List<RegionWrapper>>();
    	provinceList.add(LibCommon.getRegion(IRegion.TYPE_PROVINCE, null, getDefaultManager()));
    	request.setAttribute("provinceList", provinceList);
    	
    	List<List<RegionWrapper>> cityList = new ArrayList<List<RegionWrapper>>();
    	cityList.add(LibCommon.getRegion(IRegion.TYPE_CITY, null, getDefaultManager()));
    	request.setAttribute("cityList", cityList);
    	List<List<RegionWrapper>> districtList = new ArrayList<List<RegionWrapper>>();
    	districtList.add(LibCommon.getRegion(IRegion.TYPE_DISTRICT, null, getDefaultManager()));
    	request.setAttribute("districtList", districtList);
    	request.setAttribute("realGoodsCount", 1);
    	request.setAttribute("nameOfRegion", new String[]{"国家", "省", "市", "区"});
	}
	
    private String stepCheckout(HttpServletRequest request) {
        /*------------------------------------------------------ */
        //-- 订单确认
        /*------------------------------------------------------ */
    	HttpSession session = request.getSession();
    	String userId = (String)session.getAttribute(KEY_USER_ID);
    	
    	//判断购物车中是否有商品,如果没有就返回首页
    	Criteria criteria = new Criteria();
    	criteria.addCondition(new Condition(ICart.SESSION_ID,Condition.EQUALS,getSession().getId()));
    	int count = getDefaultManager().getCount(ModelNames.CART, criteria);
    	if(count == 0) {
    		LibMain.showMessage(Lang.getInstance().getString("noGoodsInCart"), null, null, "info", true, request);
    		return "message";
    	}
    	
    	//判断是否登录
    	String isDirect = getSession().getAttribute(KEY_DIRECT_SHOPPING) + "";//是否不登录，直接购买
    	if(!isDirect.equals("1") && userId == null) {
    		Lang lang = Lang.getInstance();
    		Object flowLoginRegister = lang.get("flowLoginRegister");
    		List<Object> list = new ArrayList<Object>();
    		list.add(flowLoginRegister);
    		lang.put("flowLoginRegister", list);
    		
    		request.setAttribute("key","");
    		request.setAttribute("anonymousBuy", 1);
    		request.setAttribute("step", "login");
    		return SUCCESS;
    	}
    	
    	Long flowType = (Long)session.getAttribute(KEY_FLOW_TYPE);
//    	if(flowType==CART_GROUP_BUY_GOODS) 
    	
    	List<Cart> carts = cartGoods(flowType);
    	
    	UserAddressWrapper consignee = getConsignee(userId);
    	
    	if(!checkConsigneeInfo(consignee, flowType)) {
    		return stepConsignee(request, false);
    	}

    	getSession().setAttribute(KEY_FLOW_CONSIGNEE, consignee);
    	request.setAttribute("consignee", consignee);
    	request.setAttribute("goodsList", WrapperUtil.wrap(carts, CartWrapper.class));
    	
    	request.setAttribute("allowEditCart", 0);
    	request.setAttribute("config", getCachedShopConfig());
    	
        /*
         * 取得订单信息
         */
    	OrderInfo order = flowOrderInfo();
    	OrderInfoWrapper ow = new OrderInfoWrapper(order);
    	getRequest().setAttribute("order", ow);
    	

        /*
         * 计算订单的费用
         */
    	Total total = LibOrder.orderFee(order,carts, consignee);
    	
    	// debug
    	debug("in [flowcheckout]: realGoodsCount="+total.getRealGoodsCount());
    	
    	request.setAttribute("total", total);
    	request.setAttribute("shoppingMoney", total.getGoodsPriceFormated());
    	request.setAttribute("marketPriceDesc", "market Price: xxx, total saving: yyy, saving rate: zzz");
    	
    	
        /* 取得配送列表 */
    	List<ShippingWrapper> shippingList = availableShippingList(consignee.getUserAddress());
    	double goodsWeight = 0;
    	double goodsAmount = 0;
    	boolean insureDisabled = true;
    	boolean codDisabled = true;
    	for(Cart cart: carts) {
    		goodsWeight+=cart.getGoodsWeight()*cart.getGoodsNumber();
    		goodsAmount+=cart.getGoodsPrice()*cart.getGoodsNumber();
    	}
    	for(ShippingWrapper sw : shippingList) {
    		String shippingCode = sw.getShipping().getShippingCode();
    		String configure = sw.getConfigure();
			Map<String, String> configValues = BaseShippingMetaPlugin.deserialize(configure);
			double shippingFee = getShippingMetaManager().calculate(shippingCode, goodsWeight, goodsAmount, configValues);
			sw.setShippingFee(shippingFee);
			String freeMoney = configValues.get(IShippingMetaPlugin.KEY_FREE_MONEY);
			sw.setFreeMoney(Double.valueOf(freeMoney));
			/* 当前的配送方式是否支持保价 */
			if(sw.getShipping().getPkId().equals(order.getShippingId())) {
				insureDisabled = "0".equals(sw.getShipping().getInsure());
				codDisabled = sw.getShipping().getSupportCod();
			}

    	}
    	request.setAttribute("shippingList", shippingList);
    	request.setAttribute("insureDisabled", insureDisabled);
    	request.setAttribute("codDisabled", codDisabled);
    	
    	
        /* 取得支付列表 */
    	List<PaymentWrapper> paymentList = availablePaymentList();
    	request.setAttribute("paymentList", paymentList);
    	
//    	if(order.getShipping() == null || order.getPayment() == null) {
//    		
//    	}

    	getSession().setAttribute(KEY_FLOW_ORDER, order);
    	request.setAttribute(KEY_STEP, STEP_CHECKOUT);
    	return SUCCESS;
    }
    private List<Cart> cartGoods(Long flowType) {
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
    private OrderInfo flowOrderInfo(){
    	OrderInfo order = (OrderInfo)getSession().getAttribute(KEY_FLOW_ORDER);
    	if(order == null) {
    		order = new OrderInfo();
    		
//    		getSession().setAttribute(KEY_FLOW_ORDER, order);
    	}
    	/* 初始化配送和支付方式 */
    	if(order.getShippingId()==null || order.getPayId() == null) {
    		/* 如果还没有设置配送和支付 */
    		if(getSession().getAttribute(KEY_USER_ID)!=null) {
    			/* 用户已经登录了，则获得上次使用的配送和支付 */
    			
    		}
    		
    		
    	}

    	return order;
    }
    

    private List<PaymentWrapper> availablePaymentList() {
    	
//    	IPaymentMetaManager pm = getPaymentMetaManager();
//    	List<PaymentConfigMeta> list = pm.getInstalledPaymentMetaList();
    	
    	List<Payment> list = (List<Payment>)getDefaultManager().getList(ModelNames.PAYMENT, null);
    	
//    	Payment payment = new Payment();
//    	payment.setPkId("abc");
//    	payment.setPayFee("1.00");
//    	payment.setPayDesc("开通城市：北京货到付款区域：五环内");
//    	payment.setPayName("货到付款");
//    	
//    	PaymentWrapper pw = new PaymentWrapper(payment);
//    	pw.put("formatPayFee", "<span id=\"ECS_CODFEE\">"+ payment.getPayFee()  + "</span>");
//    	list.add(pw);
//    	
//    	
//    	payment = new Payment();
//    	payment.setPkId("xyz");
//    	payment.setPayFee("1.00");
//    	payment.setPayDesc("银行名称收款人信息：全称 ××× ；帐号或地址 ××× ；开户行 ×××。注意事项：办理电汇时，请在电汇单“汇款用途”一栏处注明您的订单号。");
//    	payment.setPayName("银行汇款/转帐");
//    	
//    	pw = new PaymentWrapper(payment);
//    	pw.put("formatPayFee", "<span id=\"ECS_CODFEE\">"+ payment.getPayFee()  + "</span>");
//    	list.add(pw);
    	
    	return WrapperUtil.wrap(list, PaymentWrapper.class);
    }
    
    private List<ShippingWrapper> availableShippingList(UserAddress ua) {
    	List<ShippingWrapper> list = new ArrayList<ShippingWrapper>();
    	
    	Set<String> uaRegions = new HashSet<String>();
    	uaRegions.add(ua.getCountry());
    	uaRegions.add(ua.getProvince());
    	uaRegions.add(ua.getCity());
    	uaRegions.add(ua.getDistrict());
    	

    	
    	// loop instead of using IN clause
    	List<AreaRegion> ars = getDefaultManager().getList(ModelNames.AREAREGION, null);
    	for(AreaRegion ar: ars ) {
    		if(uaRegions.contains(ar.getRegionId())) {
    			ShippingArea sa = ar.getShippingArea();
    			Shipping s = sa.getShipping();
    			String configure = sa.getConfigure();
    			if(StringUtils.isEmpty(configure)) {
    				// invalid
    				debug("Shipping with code: "+s.getShippingCode()+" has an empty configuration!!");
    				continue;
    			}
    			ShippingWrapper sw = new ShippingWrapper(s); 
    			sw.setConfigure(configure);
    			list.add(sw);
    		}
    	}

    	return list;
    }
    
    
    
    private String stepDone(HttpServletRequest request) {
    	
    	Long flowType = (Long)getSession().getAttribute(KEY_FLOW_TYPE);
    	if(flowType == null) {
    		flowType = Constants.CART_GENERAL_GOODS;
    	}
    	
    	String userId = (String)getSession().getAttribute(KEY_USER_ID);
    	UserAddressWrapper consignee = getConsignee(userId);
    	
    	if(!checkConsigneeInfo(consignee, flowType)) {
    		boolean isSubmit =false;
    		return stepConsignee(request, isSubmit);
    	}
    	
    	
    	OrderInfo order = new OrderInfo();
    	OrderInfoWrapper ow = (OrderInfoWrapper)WrapperUtil.wrap(order, OrderInfoWrapper.class);
    	order.setShippingId(shipping);
    	order.setPayId(payment);
    	debug("payment: "+payment+", shipping: "+shipping);
    	
    	order.setUserId(userId);
    	order.setAddTime(new Date().getTime());
    	order.setPayName("货到付款");
    	
    	
    	/* 订单中的商品 */
    	List<Cart> carts = cartGoods(flowType);
    	
    	/* 订单中的总额 */
    	Total total = LibOrder.orderFee(order, carts, consignee);
    	order.setGoodsAmount(total.getGoodsPrice());
    	order.setOrderAmount(total.getAmount());
    	order.setOrderSn(getOrderSn());
    	
    	// TODO /* 配送方式 */
   	
    	// TODO /* 支付方式 */
    	Payment paymentObj = null;
    	if(StringUtils.isNotEmpty(order.getPayId())) {
    		paymentObj = (Payment)getDefaultManager().get(ModelNames.PAYMENT, order.getPayId());
    		order.setPayName(paymentObj.getPayName());
    	}
    	
    	order.setPayFee(total.getPayFee());
    	
    	/* 插入订单表 */
    	
    	String orderId = getDefaultManager().txadd(order);
    	debug("in [flowDone]: orderId="+orderId);
    	
    	
    	/* 插入订单商品 */
//    	$sql = "INSERT INTO " . $ecs->table('order_goods') . "( " .
//        "order_id, goods_id, goods_name, goods_sn, goods_number, market_price, ".
//        "goods_price, goods_attr, is_real, extension_code, parent_id, is_gift) ".
//    " SELECT '$new_order_id', goods_id, goods_name, goods_sn, goods_number, market_price, ".
//        "goods_price, goods_attr, is_real, extension_code, parent_id, is_gift ".
//    " FROM " .$ecs->table('cart') .
//    " WHERE session_id = '".SESS_ID."' AND rec_type = '$flow_type'";
    	
    	// TODO no transaction protection
    	for(Cart cart: carts) {
    		OrderGoods og = new OrderGoods();
    		og.setOrderId(orderId);
    		og.setGoodsId(cart.getGoodsId());
    		og.setGoodsName(cart.getGoodsName());
    		og.setGoodsSn(cart.getGoodsSn());
    		og.setGoodsNumber(cart.getGoodsNumber());
    		og.setMarketPrice(cart.getMarketPrice());
    		og.setGoodsPrice(cart.getGoodsPrice());
    		og.setGoodsAttr(cart.getGoodsAttr());
    		og.setIsReal(cart.getIsReal());
    		og.setExtensionCode(cart.getExtensionCode());
    		og.setParentId(cart.getParentId());
    		og.setIsGift(cart.getIsGift());
    		String ogId = getDefaultManager().txadd(og);
    	}
    	
    	
        /* 清空购物车 */
        clearCart(flowType);
        /* 清除缓存，否则买了商品，但是前台页面读取缓存，商品数量不减少 */
        clearAllFiles();
    	
        /* 取得支付信息，生成支付代码 */
//        if ($order['order_amount'] > 0)
//        {
//            $payment = payment_info($order['pay_id']);//
//            $pay_obj    = new $payment['pay_code'];
//            $pay_online = $pay_obj->get_code($order, unserialize_config($payment['pay_config']));
//            $order['pay_desc'] = $payment['pay_desc'];
//            $smarty->assign('pay_online', $pay_online);
//        }
        
        if(order.getOrderAmount()>0) {
        	if(paymentObj!=null) {
        		String payOnline = getPaymentMetaManager().getCode(order.getPkId(), paymentObj.getPkId());
        		debug("payOnline: "+payOnline);
        		ow.put("payDesc", paymentObj.getPayDesc());
        		request.setAttribute("payOnline", payOnline);
        	}
        }
        
        
        /* 订单信息 */
        request.setAttribute("order", ow);
        request.setAttribute("total", total);
        request.setAttribute("goodsList", WrapperUtil.wrap(carts, CartWrapper.class));
        request.setAttribute("orderSubmitBack", "您可以返回首页或去用户中心");
        
        unset(KEY_FLOW_CONSIGNEE);// 清除session中保存的收货人信息
        unset(KEY_FLOW_ORDER);
        unset(KEY_DIRECT_SHOPPING);
        
    	request.setAttribute(KEY_STEP, STEP_DONE);
    	return SUCCESS;
    }
    
    private void unset(String key) {
    	getSession().removeAttribute(key);
    }
    private void clearCart(Long flowType) {
    	
    }
    private void clearAllFiles() {
    	
    }
    private String getOrderSn() {
    	return String.valueOf(new Date().getTime());
    }
    
	@Override
	public String onExecute() throws Exception {
		try {
			
			ActionContext ctx = ActionContext.getContext();        
	        HttpServletRequest request = (HttpServletRequest)ctx.get(ServletActionContext.HTTP_REQUEST);      
	        

//			includeOrderTotal(request);
			
			String step = request.getParameter(KEY_STEP);
			debug("step: "+step);
			if(step==null) {
				step = STEP_CART;
			}
			
			request.setAttribute("showMarketprice", getCachedShopConfig().get("showMarketprice"));
			
			
			if(STEP_ADD_TO_CART.equals(step)) {
				return stepAddToCart(request);
			} 
			else if(STEP_CART.equals(step)) {
				// this step comes from common.js addToCartResponse
				return stepCart(request);
			} 
			else if(STEP_CHECKOUT.equals(step)) {
				return stepCheckout(request);
			}
			else if(STEP_CONSIGNEE.equals(step)) {
				return stepConsignee(request, true);
			} 
			else if(STEP_DONE.equals(step)) {
				return stepDone(request);
			}
			else if(STEP_DROP_GOODS.equals(step)) {
				return dropGoods(request);
			}
			else if(STEP_CLEAR.equals(step)){
				return clear(request);
			}
			else if(STEP_UPDATE_CART.equals(step)) {
				return updateCart(request);
			}
			else if(STEP_LOGIN.equals(step)) {
				return login(request);
			}
			else {
				return SUCCESS;
			}
			
			
		} catch (Exception ex) {
			ex.printStackTrace();
			throw new RuntimeException(ex);
		}
	}
	
	//修改，用户登录，注册
	private String login(HttpServletRequest request) {
		String act = request.getParameter("act");
		//登录
		Lang lang = Lang.getInstance();
		if(act.equals("signin")) {
			if(login(request.getParameter("username"),request.getParameter("password"))) {
				return stepCheckout(request);
			}
			else {
				LibMain.showMessage(lang.getString("signinFailed"), null,null, "info", true, request);
				return "message";
			}
		}
		
		//注册
		else {
			User user = new User();
			String userName = request.getParameter("username");
			String email = request.getParameter("email");
			String password = request.getParameter("password");
			String confirmPassword = request.getParameter("confirm_password");
			
			Criteria criteriaName = new Criteria();
			criteriaName.addCondition(new Condition(IUser.USER_NAME,Condition.EQUALS,userName));
			Criteria criteriaEmail = new Criteria();
			criteriaEmail.addCondition(new Condition(IUser.EMAIL,Condition.EQUALS,email));
			
			if(getDefaultManager().getCount(ModelNames.USER, criteriaName) > 0) {
				LibMain.showMessage(new PrintfFormat(lang.getString("usernameExist")).sprintf(new Object[]{userName}), null, null, "info", true, request);
				return "message";
			}
			else if(getDefaultManager().getCount(ModelNames.USER, criteriaEmail) > 0) {
				LibMain.showMessage(lang.getString("msgEmailRegistered"), null, null, "info", true, request);
				return "message";
			}
			else {
				user.setUserName(userName);
				user.setEmail(email);
				user.setPassword(password);
				getDefaultManager().txadd(user);
				setSession(userName);
				return stepConsignee(request,false);
			}
		}
	}
	/********************用户登录，将userId存入session*********************/
	public boolean checkUser(String username, String password) {
		
		User user = getUser(username);
		if(user == null) {
			return false;
		}
		
		if(password!=null) {
			return password.equals(user.getPassword());
		}
		else {
			return true;
		}
		
	}
	private boolean login(String username, String password) {
		if(checkUser(username, password)) {
			setSession(username);
			return true;
		}
		else {
			return false;
		}
	}
	private void setSession(String username) {
		if(StringUtils.isEmpty(username)) {
			// destroy session
			getSession().setAttribute(KEY_USER_ID, null);
		}
		else {
			User user = getUser(username);
			if(user!=null) {
				//修改，放入wrapper,member_info.ftl 调用getUsername()方法
				getSession().setAttribute("userInfo", new UserWrapper(user));//modifyed
				getSession().setAttribute(KEY_USER_ID, user.getPkId());
				getSession().setAttribute(KEY_USER_NAME, username);
				getSession().setAttribute(KEY_USER_EMAIL, user.getEmail());
			}
		}
		
	}
	public User getUser(String username) {
		Criteria c = new Criteria();
		Condition cond = new Condition();
		cond.setField(IUser.USER_NAME);
		cond.setOperator(Condition.EQUALS);
		cond.setValue(username);
		c.addCondition(cond);
		
		List<User> res = (List<User>)getDefaultManager().getList(ModelNames.USER, c);
		User user = res.size()>0 ? res.get(0) : null;
		
		return user;
	}
	/*************************登录完*******************************************************/

	//修改，更新购物车,修改商品数量
	private String updateCart(HttpServletRequest request) {
		String sessionId = request.getSession().getId();
		Criteria criteria = new Criteria();
		criteria.addCondition(new Condition(ICart.SESSION_ID,Condition.EQUALS,sessionId));
		List<Cart> carts = getDefaultManager().getList(ModelNames.CART, criteria);
		for(Iterator iterator = carts.iterator();iterator.hasNext();) {
			Cart cart = (Cart)iterator.next();
			String id = cart.getPkId();
			String goodsId = cart.getGoodsId();
			//从页面获得修改后的商品数量,如果输入的不是数字，不更新商品数量
			long newGoodsNum = 0;
			try {
				newGoodsNum = Long.parseLong(request.getParameter("goods_number[" + id + "]"));
			} catch(Exception NumberFormatException) {
				newGoodsNum = cart.getGoodsNumber();
			}
			
			//对商品数量进行了修改
			Lang lang = Lang.getInstance();
			if(newGoodsNum != cart.getGoodsNumber()) {
				//判断库存是否足够
				Goods goods = (Goods) getDefaultManager().get(ModelNames.GOODS,goodsId);
				if(newGoodsNum <= goods.getGoodsNumber()) {
					cart.setGoodsNumber(newGoodsNum);
					getDefaultManager().txattach(cart);
					LibMain.showMessage(lang.getString("updateCartNotice"), lang.getString("backToCart"),"flow.action", "info", true, request);
				}
				else {
					String goodsName = goods.getGoodsName();
					long goodsNumber = goods.getGoodsNumber();
					LibMain.showMessage(new PrintfFormat(lang.getString("stockInsufficiency")).sprintf(new Object[]{goodsName,goodsNumber,goodsNumber})
							, null,null, "info", false, request);
					
				}
			}
			else {
				LibMain.showMessage(lang.getString("updateCartNotice"), lang.getString("backToCart"),"flow.action", "info", true, request);
			}
		}
		return "message";
	}

	//修改，清空购物车
	private String clear(HttpServletRequest request) {
		String sessionId = request.getSession().getId();
		Condition condition = new Condition(ICart.SESSION_ID,Condition.EQUALS,sessionId);
		Criteria criteria = new Criteria();
		criteria.addCondition(condition);
		List<ModelObject> carts = getDefaultManager().getList(ModelNames.CART, criteria);
		getDefaultManager().txdeleteall(carts);
		return stepCart(request);
	}

	//修改，从购物车中删除一个商品
	private String dropGoods(HttpServletRequest request) {		
		String recId = request.getParameter("id");
		Cart cart = (Cart) getDefaultManager().get(ModelNames.CART, recId);
		String parentId = cart.getParentId();
		//删除配件
		if(parentId == null || parentId.equals(0)){
			String goodsId = cart.getGoodsId();
			Criteria criteria = new Criteria();
			criteria.addCondition(new Condition(ICart.PARENT_ID,Condition.EQUALS,goodsId));
			List<ModelObject> deleteCart = (List<ModelObject>) getDefaultManager().getList(ModelNames.CART, criteria);
			getDefaultManager().txdeleteall(deleteCart);
		}
		getDefaultManager().txdelete(ModelNames.CART, recId);
		return stepCart(request);
	}

	public void includeOrderTotal(HttpServletRequest request) {
		// TODO
//		Total total = new Total();
//		total.setAmountFormated("20.00元");
//		request.setAttribute("total", total);
	}
	
	public void setOrder(HttpServletRequest request) {
		OrderInfo order = new OrderInfo();
		OrderInfoWrapper ow = new OrderInfoWrapper(order);
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

	public String getShipping() {
		return shipping;
	}

	public void setShipping(String shipping) {
		this.shipping = shipping;
	}

	public String getPayment() {
		return payment;
	}

	public void setPayment(String payment) {
		this.payment = payment;
	}
}
