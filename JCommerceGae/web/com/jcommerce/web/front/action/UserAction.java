package com.jcommerce.web.front.action;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.StringBufferInputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.json.JSONObject;

import com.jcommerce.core.model.CollectGood;
import com.jcommerce.core.model.Comment;
import com.jcommerce.core.model.Goods;
import com.jcommerce.core.model.ModelObject;
import com.jcommerce.core.model.OrderInfo;
import com.jcommerce.core.model.Session;
import com.jcommerce.core.model.User;
import com.jcommerce.core.model.UserAddress;
import com.jcommerce.core.model.UserRank;
import com.jcommerce.core.service.Condition;
import com.jcommerce.core.service.Criteria;
import com.jcommerce.core.util.UUIDLongGenerator;
import com.jcommerce.gwt.client.ModelNames;
import com.jcommerce.gwt.client.model.ICart;
import com.jcommerce.gwt.client.model.ICollectGood;
import com.jcommerce.gwt.client.model.IComment;
import com.jcommerce.gwt.client.model.IOrderInfo;
import com.jcommerce.gwt.client.model.IRegion;
import com.jcommerce.gwt.client.model.IUser;
import com.jcommerce.gwt.client.model.IUserRank;
import com.jcommerce.gwt.client.panels.system.IShopConfigMeta;
import com.jcommerce.gwt.client.util.URLConstants;
import com.jcommerce.web.front.action.helper.Pager;
import com.jcommerce.web.to.Affiliate;
import com.jcommerce.web.to.CollectGoodWrapper;
import com.jcommerce.web.to.CommentWrapper;
import com.jcommerce.web.to.Lang;
import com.jcommerce.web.to.OrderGoodsWrapper;
import com.jcommerce.web.to.OrderInfoWrapper;
import com.jcommerce.web.to.RegionWrapper;
import com.jcommerce.web.to.ShopConfigWrapper;
import com.jcommerce.web.to.UserAddressWrapper;
import com.jcommerce.web.to.UserWrapper;
import com.jcommerce.web.to.WrapperUtil;
import com.jcommerce.web.util.ConstantsMappingUtils;
import com.jcommerce.web.util.LibCommon;
import com.jcommerce.web.util.LibMain;
import com.jcommerce.web.util.LibOrder;
import com.jcommerce.web.util.LibTransaction;
import com.jcommerce.web.util.PrintfFormat;
import com.jcommerce.web.util.WebFormatUtils;


public class UserAction extends BaseAction {
	public void debug(String s) {
		System.out.println(" in [UserAction]: "+s );
	}
	
	@Override
	protected String getSelfURL() {
		return URLConstants.ACTION_USER;
	}
	
	public static final String RES_USER_PASSPORT = "user_passport";
	public static final String RES_USER_CLIPS = "user_clips";
	public static final String RES_IS_REGISTERED = "is_registered";
	public static final String RES_USER_TRANSACTION = "user_transaction";
	public static final String RES_CHECK_EMAIL = "check_email";
	public static final String RES_COLLECT = "collect";

	
	private String username;
	private String password;
	private String email;
	private String captcha;
	

	private Map<String, String> other;
	private String action;
	
	private InputStream isRegistered;
	private InputStream isUsed;
	private InputStream collectGoods;

	
	
	@Override
	public String onExecute() throws Exception {
		try {
			debug("in execute");
			HttpServletRequest request = getRequest();
			
			LibMain.assignUrHere(request, "", Lang.getInstance().getString("userCenter"));
			
			String userId = (String)getSession().getAttribute(KEY_USER_ID);

			action = request.getParameter("act");
			if(action==null) {
				action = "default";
			}
			else {
				action = action.trim();
			}
			// 不需要登录的操作或自己验证是否登录（如ajax处理）的act
			String[] s = new String[]{
					"login","act_login","register","act_register","act_edit_password",
						"get_password","send_pwd_email","password", "signin", 
						"add_tag", "collect", "return_to_cart", "logout", 
						"email_list", "validate_email", "send_hash_mail", 
						"order_query", "is_registered", "check_email"};

			List<String> notLoginArr = Arrays.asList(s);
			
			/* 显示页面的action列表 */
			s = new String[] {
					"register", "login", "profile", "order_list", "order_detail", "address_list", "collection_list",
					"message_list", "tag_list", "get_password", "reset_password", "booking_list", "add_booking", "account_raply",
					"account_deposit", "account_log", "account_detail", "act_account", "pay", "default", "bonus", "group_buy", "group_buy_detail", "affiliate", "comment_list","validate_email","track_packages", "transform_points"
			};
			
			List<String> uiArr = Arrays.asList(s);
			

			String backAct = "";
			/* 未登录处理 */
			if(StringUtils.isEmpty(userId)) {
				if(!notLoginArr.contains(action)) {
					if(uiArr.contains(action)) {
						 /* 如果需要登录,并是显示页面的操作，记录当前操作，用于登录后跳转到相应操作*/
						if(!StringUtils.isEmpty(request.getQueryString())) {
							backAct = (String)getSession().getAttribute("lastQueryString");
							debug("in execute: backAct="+backAct);
						}
						
						action = "login";
					} 
					else {
						
					}
				}
				
			}
			
			// TODO handle variable action in freemarker: 
			// see http://struts.apache.org/2.x/docs/freemarker.html
			// solution: see this.toString()
//			request.setAttribute("action", action);
			
			
			if("default".equals(action)) {
				
				request.setAttribute("rankName", "");
				request.setAttribute("nextRankName", "");
				UserWrapper uw = getUserDefault(userId);
				//查找userRank和nextRank
				UserRank userRank = getUserRank(uw,request);
				if(userRank != null && userRank.getMaxPoints() > 0) {
					getNextRank(uw,userRank,request);
				}
				
				uw.setManager(getDefaultManager());
				uw.put("isValidate", 1);
				uw.put("shippedOrder", null);
				
				request.setAttribute("info", uw);
				request.setAttribute("userNotice", getCachedShopConfig().get("userNotice"));
				request.setAttribute("prompt", new String[0]);
				includeUserMenu();
				return RES_USER_CLIPS;
			}
			else if("register".equals(action)) {
				request.setAttribute("enabledCaptcha", 0);
				request.setAttribute("rand", new Double(1000000*Math.random()).longValue());
				
				request.setAttribute("shopRegClosed", getCachedShopConfig().get("shopRegClosed"));
				return RES_USER_PASSPORT;
			}
			else if("check_email".equals(action)){
				if(checkEmail(getEmail())) {
					isUsed = new StringBufferInputStream("false");
				}else {
					isUsed = new StringBufferInputStream("ok");
				}
				return RES_CHECK_EMAIL;
			}
			else if("login".equals(action)) {
				request.setAttribute("backAct", backAct);
				return RES_USER_PASSPORT;
			}
			else if("act_login".equals(action) || "actLogin".equals(action)) {
				backAct = request.getParameter("back_act");
				if(backAct!=null) {
					backAct = backAct.trim();
				}
				String url = StringUtils.isEmpty(backAct) ? "user.action" : backAct;
				if(login(getUsername(), getPassword())) {
					if(url.endsWith("user.action")) {
						return LibMain.showMessage(Lang.getInstance().getString("loginSuccess"), Lang.getInstance().getString("profileLnk"), 
								"user.action", "info", false, request);
					}
					else {
						// TODO redirect to a link
						getResponse().sendRedirect(url);
						// will it be executed??
						System.out.println("!!!!!!!!!!!!!!!!!!!!!");
						return "error";
					}
				}
				else {
					return LibMain.showMessage(Lang.getInstance().getString("loginFailure"), Lang.getInstance().getString("reloginLnk"), 
							url, "error", false, request);
				}

			}
			else if("act_register".equals(action) || "actRegister".equals(action)) {
				if(getCaptcha().equals((String)getSession().getAttribute("captcha"))){
					String error = register(getUsername(), getPassword(), getEmail(), other );
					if(error == null) {
						return LibMain.showMessage( new PrintfFormat(Lang.getInstance().getString("registerSuccess")).sprintf(getUsername()), Lang.getInstance().getString("profileLnk"), 
								"user.action", "info", true, request);
					} else {
						return LibMain.showMessage(error, Lang.getInstance().getString("signUp"), 
								"user.action?act=register", "info", true, request);
					}
				}
				else{
					return LibMain.showMessage(Lang.getInstance().getString("invalidCaptcha"), Lang.getInstance().getString("signUp"), 
							"user.action?act=register", "info", true, request);
				}
			}
			else if("is_registered".equals(action) || "isRegistered".equals(action)) {
				if(checkUser(getUsername(), null)) {
					isRegistered = new StringBufferInputStream("false");
				}else {
					isRegistered = new StringBufferInputStream("true");
				}
				return RES_IS_REGISTERED;
			}
			else if("profile".equals(action)) {
				UserWrapper uw = LibTransaction.getProfile(userId, getDefaultManager());
				request.setAttribute("profile", uw);
				includeUserMenu();
				return RES_USER_TRANSACTION;
				
			} 
			else if("order_list".equals(action) || "orderList".equals(action)){
				String sPage = (String)request.getParameter("page");
				int page = (sPage!=null && Integer.valueOf(sPage)>0) ? Integer.valueOf(sPage) : 1;
				Criteria criteria = new Criteria();
				Condition cond = new Condition(IOrderInfo.USER_ID, Condition.EQUALS, userId);
				criteria.addCondition(cond);
				int recordCount = getDefaultManager().getCount(ModelNames.ORDERINFO, criteria);
				
				Map<String, Object> param = new HashMap<String, Object>();
				param.put("act", action);
				Pager pager = LibMain.getPager("user.action", param, recordCount, page, -1, getCachedShopConfig());
				
				request.setAttribute("pager", pager);
				request.setAttribute("orders", 
						LibTransaction.getUserOrders(userId, pager.getSize(), pager.getStart(), getDefaultManager()));
				
				includeUserMenu();
				return RES_USER_TRANSACTION;
			}
			/* 收货地址列表界面*/
			else if("address_list".equals(action) || "addressList".equals(action)) {
				includeUserMenu();
				String shopCountry = (String)getCachedShopConfig().get(IShopConfigMeta.CFG_KEY_SHOP_COUNTRY);
				
				/* 取得国家列表、商店所在国家、商店所在国家的省列表 */
				request.setAttribute("countryList", LibCommon.getRegion(IRegion.TYPE_COUNTRY, null,getDefaultManager()));
				request.setAttribute("shopProvinceList", LibCommon.getRegion(
						IRegion.TYPE_PROVINCE, 
						shopCountry, 
						getDefaultManager()));
				
				/* 获得用户所有的收货人信息 */
				List<UserAddressWrapper> consigneeList = LibTransaction.getConsigneeList(userId, getDefaultManager());
				

				if(consigneeList.size()<1 && getSession().getAttribute(KEY_USER_ID)!=null) {
					// we allow only one address
					/* 如果用户收货人信息的总数小于1 则增加一个新的收货人信息 */
					UserAddressWrapper holder = new UserAddressWrapper(new UserAddress());
//					holder.getUserAddress().setCountry(newCountry);
					String email = (String)getSession().getAttribute(KEY_USER_EMAIL);
					if(email == null) {
						email = "";
					}
					holder.getUserAddress().setEmail(email);
					holder.getUserAddress().setCountry(shopCountry);
					consigneeList.add(holder);
				}
				
				UserAddressWrapper uaw = consigneeList.get(0);
				request.setAttribute("consigneeList", consigneeList);
				
				 //取得国家列表，如果有收货人列表，取得省市区列表
		    	List<List<RegionWrapper>> provinceList = new ArrayList<List<RegionWrapper>>();
		    	provinceList.add(LibCommon.getRegion(IRegion.TYPE_PROVINCE,uaw.getUserAddress().getCountry(), getDefaultManager()));
		    	request.setAttribute("provinceList", provinceList);
		    	
		    	List<List<RegionWrapper>> cityList = new ArrayList<List<RegionWrapper>>();
		    	cityList.add(LibCommon.getRegion(IRegion.TYPE_CITY, uaw.getUserAddress().getProvince(), getDefaultManager()));
		    	request.setAttribute("cityList", cityList);
		    	List<List<RegionWrapper>> districtList = new ArrayList<List<RegionWrapper>>();
		    	districtList.add(LibCommon.getRegion(IRegion.TYPE_DISTRICT,uaw.getUserAddress().getCity(), getDefaultManager()));
		    	request.setAttribute("districtList", districtList);
				
		    	
		    	
		    	//赋值于模板
		    	request.setAttribute("realGoodsCount", 1);
		    	request.setAttribute("shopCountry", shopCountry);
		    	request.setAttribute("shopProvince", LibCommon.getRegion(IRegion.TYPE_PROVINCE, shopCountry, getDefaultManager()));
		    	request.setAttribute("address", "TODO address");
		    	request.setAttribute("currencyFormat", "TODO currency_format");
		    	request.setAttribute("integralScale", "TODO integral_scale");
		    	request.setAttribute("nameOfRegion", new String[]{"国家", "省", "市", "区"});
				return RES_USER_TRANSACTION;
			}
			else if("act_edit_address".equals(action)) {
				UserAddress ua = new UserAddress();
				ua.setUserId(userId);
				String addressId = request.getParameter("address_id");
				boolean isNew = true;
				if(StringUtils.isNotEmpty(addressId)) {
					ua.setPkId(addressId);
					isNew = false;
				}
				ua.setCountry(request.getParameter("country"));
				ua.setProvince(request.getParameter("province"));
				ua.setCity(request.getParameter("city"));
				ua.setDistrict(request.getParameter("district"));
				ua.setAddress(request.getParameter("address"));
				ua.setConsignee(request.getParameter("consignee"));
				ua.setEmail(request.getParameter("email"));
				ua.setTel(request.getParameter("tel"));
				ua.setMobile(request.getParameter("mobile"));
				ua.setBestTime(request.getParameter("best_time"));
				ua.setSignBuilding(request.getParameter("sign_building"));
				ua.setZipcode(request.getParameter("zipcode"));
				
				try {
					if (isNew) {
						getDefaultManager().txadd(ua);
					} else {
						getDefaultManager().txupdate(ua);
					}
					return LibMain.showMessage(
									Lang.getInstance().getString("editAddressSuccess"), 
									Lang.getInstance().getString("addressListLnk"),
									"user.action?act=address_list", null, null,
									request);
				} catch (Exception e) {
					e.printStackTrace();
					return null;
				}
				
			}
			else if("order_detail".equals(action) || "orderDetail".equals(action)) {
				
				includeUserMenu();
				String orderId = request.getParameter("order_id");
			    /* 订单详情 */
				OrderInfoWrapper ow = getOrderDetail(orderId, userId);
				if(ow==null) {
					
				}
				/* 是否显示添加到购物车 */
				if(!"group_buy".equals(ow.getOrderInfo().getExtensionCode())){ 
					request.setAttribute("allowToCart", 1);
				}
				
				/* 订单商品 */
				List<OrderGoodsWrapper> goodsList = LibOrder.orderGoods(orderId, getDefaultManager());
				for(OrderGoodsWrapper goods : goodsList) {
					goods.put("marketPrice", WebFormatUtils.priceFormat(goods.getOrderGoods().getMarketPrice()));
					goods.put("goodsPrice", WebFormatUtils.priceFormat(goods.getOrderGoods().getGoodsPrice()));
					goods.put("subtotal", WebFormatUtils.priceFormat(
							goods.getOrderGoods().getGoodsPrice() * goods.getOrderGoods().getGoodsNumber()));
				}
				
				/* 订单 支付 配送 状态语言项 */
				String str = ConstantsMappingUtils.getOrderStatus(ow.getOrderInfo().getOrderStatus());
				ow.put("orderStatus", ((Map)Lang.getInstance().get("os")).get(str));
				str = ConstantsMappingUtils.getPayStatus(ow.getOrderInfo().getPayStatus());
				ow.put("payStatus", ((Map)Lang.getInstance().get("ps")).get(str));
				str = ConstantsMappingUtils.getShippingStatus(ow.getOrderInfo().getShippingStatus());
				ow.put("shippingStatus", ((Map)Lang.getInstance().get("ss")).get(str));
				
				request.setAttribute("order", ow);
				request.setAttribute("goodsList", goodsList);
				return RES_USER_TRANSACTION;
				
			}
			else if("logout".equals(action)) {
				logout();
				return LibMain.showMessage(Lang.getInstance().getString("logout"), Lang.getInstance().getString("backHomeLnk"), 
						"home.action", "info", true, request);
			}
			//加入收藏夹
			else if("collect".equals(action)) {
				String goodsId = request.getParameter("id");
				Goods goods = (Goods) getDefaultManager().get(ModelNames.GOODS, Long.parseLong(goodsId));
				goodsId = goods.getPkId();
				
				JSONObject res = new JSONObject();
				//判断是否登录
				if(userId == null) {					
					res.put("message", Lang.getInstance().get("loginPlease"));
				}
				
				//检查是否已经存在于用户的收藏夹 
				else {
					Criteria criteria = new Criteria();
					criteria.addCondition(new Condition(ICollectGood.USER_ID, Condition.EQUALS, userId));
					criteria.addCondition(new Condition(ICollectGood.GOODS_ID, Condition.EQUALS, goodsId));
					int count = getDefaultManager().getCount(ModelNames.COLLECTGOOD, criteria);
					if(count > 0) {
						res.put("message", Lang.getInstance().get("collectExisted"));
					}
					else {
						long addTime = new Date().getTime();
						
						CollectGood collectGood = new CollectGood();
						collectGood.setUserId(userId);
						collectGood.setAddTime(addTime);
						collectGood.setGoodsId(goodsId);
						getDefaultManager().txadd(collectGood);
						res.put("message", Lang.getInstance().get("collectSuccess"));
					}
				}
				String out = res.toString();
				collectGoods = new ByteArrayInputStream(out.getBytes(IWebConstants.ENC));
				return RES_COLLECT;
			}
			
			//收藏夹列表
			else if("collection_list".equals(action)) {
				includeUserMenu();				
				int page = request.getParameter("page") == null ? 1 : Integer.parseInt(request.getParameter("page"));
				return setCollectionList(page, userId, request);				
			}
			
			//关注
			else if("add_to_attention".equals(action)) {
				includeUserMenu();	
				String id = request.getParameter("rec_id");
				setAttention(id, true);
				action = "collection_list";
				return setCollectionList(1, userId, request);				
			}
			
			//取消关注
			else if("del_attention".equals(action)) {
				includeUserMenu();	
				String id = request.getParameter("rec_id");
				setAttention(id, false);
				action = "collection_list";
				return setCollectionList(1, userId, request);				
			}
			
			//从收藏夹中删除
			else if("delete_collection".equals(action)) {
				includeUserMenu();	
				String id = request.getParameter("collection_id");
				getDefaultManager().txdelete(ModelNames.COLLECTGOOD, id);
				action = "collection_list";
				return setCollectionList(1, userId, request);
			}
			
			//评论列表
			else if("comment_list".equals(action)) {
				includeUserMenu();	
				int page = request.getParameter("page") == null ? 1 : Integer.parseInt(request.getParameter("page"));
				return setCommentList(page, userId, request);	
			}
			
			//删除评论
			else if("del_cmt".equals(action)) {
				includeUserMenu();
				String id = request.getParameter("id");
				getDefaultManager().txdelete(ModelNames.COMMENT, id);
				action = "comment_list";
				return setCommentList(1, userId, request);
			}
						
			else {
				includeUserMenu();
				return RES_USER_CLIPS;
			}
			

		} catch (Exception ex) {
			ex.printStackTrace();
			throw new RuntimeException(ex);
		}
	}

	private String setCollectionList(int page, String userId, HttpServletRequest request) {
		Map<String, Object> cmt = LibMain.assignCollectionList(userId, page, getDefaultManager(), getCachedShopConfig());		
		List<CollectGoodWrapper> goodsList = (List<CollectGoodWrapper>) cmt.get("goodsList");
		Pager pager = (Pager) cmt.get("pager");
		
		request.setAttribute("goodsList", goodsList);
		request.setAttribute("pager", pager);
		request.setAttribute("userId", userId);
		request.setAttribute("url", "");
		
		return RES_USER_CLIPS;		
	}
	
	private String setCommentList(int page, String userId, HttpServletRequest request) {
		Map<String, Object> cmt = LibMain.assignCommentList(userId, page, getDefaultManager(), getCachedShopConfig());		
		List<CommentWrapper> commentList = (List<CommentWrapper>) cmt.get("commentList");
		Pager pager = (Pager) cmt.get("pager");
		
		request.setAttribute("commentList", commentList);
		request.setAttribute("pager", pager);
		
		return RES_USER_CLIPS;		
	}

	//设置关注状态
	private void setAttention(String id, boolean state) {
		CollectGood collectGood = (CollectGood) getDefaultManager().get(ModelNames.COLLECTGOOD, id);
		collectGood.setIsAttention(state);
		getDefaultManager().txupdate(collectGood);		
	}

	/**
	 *  获取指订单的详情
	 *
	 * @access  public
	 * @param   int         $order_id       订单ID
	 * @param   int         $user_id        用户ID
	 *
	 * @return   arr        $order          订单所有信息的数组
	 */
	public OrderInfoWrapper getOrderDetail(String orderId, String userId) {
		OrderInfoWrapper ow = LibOrder.orderInfo(orderId, null, getDefaultManager());
		if(ow==null) {
			
		}
		
		//检查订单是否属于该用户
		if(userId!=null && !userId.equals(ow.getOrderInfo().getUserId())) {
			
		}
		// TODO /* 对发货号处理 */
		 
		/* 只有未确认才允许用户修改订单地址 */
		if(ow.getOrderInfo().getOrderStatus() == IOrderInfo.OS_UNCONFIRMED) {
			ow.put("allowUpdateAddress", 1);
		}else {
			ow.put("allowUpdateAddress", 0);
		}
		
		// TODO /* 获取订单中实体商品数量 */
		ow.put("existRealGoods", true);
		
		/* 如果是未付款状态，生成支付按钮 */
		if(ow.getOrderInfo().getPayStatus() == IOrderInfo.PS_UNPAYED && 
				( ow.getOrderInfo().getOrderStatus() == IOrderInfo.OS_UNCONFIRMED || 
					ow.getOrderInfo().getOrderStatus() == IOrderInfo.OS_CONFIRMED) ) {
			
	        /*
	         * 在线支付按钮
	         */
	        // TODO 支付方式信息
			
			//无效支付方式
			// if ($payment_info === false)
			ow.put("payOnline", "");
			
		}
		else {
			ow.put("payOnline", "");
		}
				
		return ow;
		
		
		
		
		
	}
	public void includeUserMenu() {
		Affiliate a = new Affiliate();
		a.setOn(0);
		getRequest().setAttribute("affiliate", a);
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
	public UserWrapper getUserDefault(String userId) {
		User user = (User)getDefaultManager().get(ModelNames.USER, userId);
		return new UserWrapper(user);
	}
	
    /**
     *  检查指定用户是否存在及密码是否正确
     *
     * @access  public
     * @param   string  $username   用户名
     *
     * @return  int
     */
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
	public boolean checkEmail(String email) {
		
		if(email != null) {
			Criteria c = new Criteria();
			Condition cond = new Condition();
			cond.setField(IUser.EMAIL);
			cond.setOperator(Condition.EQUALS);
			cond.setValue(email);
			c.addCondition(cond);
			
			List<User> res = (List<User>)getDefaultManager().getList(ModelNames.USER, c);
			return res.size() > 0 ? true : false;
		}
		
		
		return false;

		
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
	private void logout() {
		//清空该用户的购物车
		String sessionId = getSession().getId();
		Condition condition = new Condition(ICart.SESSION_ID,Condition.EQUALS,sessionId);
		Criteria criteria = new Criteria();
		criteria.addCondition(condition);
		List<ModelObject> carts = getDefaultManager().getList(ModelNames.CART, criteria);
		getDefaultManager().txdeleteall(carts);
		getSession().invalidate();
		
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
	
	public String register(String username, String password, String email, Map<String, String> other) {
		User user = new User();
		user.setUserName(username);
		user.setPassword(password);
		user.setEmail(email);
		int length = other.size();
		// TODO wrap it and return error code
		String error = null;
		try {
			getDefaultManager().txadd(user);
		} catch (Exception ex) {
			ex.printStackTrace();
			error = ex.getMessage();
			if(StringUtils.isEmpty(error)) {
				error = "UNknown reason";
			}
		}
		
		if(error==null) {
			user = getUser(username);//modified
			getSession().setAttribute(KEY_USER_ID, user.getPkId());
			getSession().setAttribute(KEY_USER_NAME, user.getUserName());
			getSession().setAttribute(KEY_USER_EMAIL, user.getEmail());
			// TODO update user's "other" properties 
			
		}
		return error;
		
		
		
	}

	public String getUsername() {
		return username == null? "" : username.trim();
	}


	public void setUsername(String username) {
		this.username = username;
	}


	public String getPassword() {
		return password == null? "" : password;
	}


	public void setPassword(String password) {
		this.password = password;
	}


	public String getEmail() {
		return email == null? "":email;
	}


	public void setEmail(String email) {
		this.email = email;
	}

	public String getCaptcha() {
		return captcha;
	}

	public void setCaptcha(String captcha) {
		this.captcha = captcha;
	}

	public Map<String, String> getOther() {
		return other;
	}


	public void setOther(Map<String, String> other) {
		this.other = other;
	}

	public InputStream getIsRegistered() {
		return isRegistered;
	}
	public InputStream getIsUsed() {
		return isUsed;
	}
	
	public String toString() {
		// this is a tricky hacking
		// in freemarker the action variable points to the Action class
		
		return WebFormatUtils.phpVarFromat(action);
	}

	//查找userRank
	public UserRank getUserRank(UserWrapper uw,HttpServletRequest request) {
		//查找用户rank
		String userRankId = uw.getUserRank()+"";
		long rankPoints = uw.getRankPoints();
		Criteria criteria = new Criteria();
		
		//当用户的rankId为0时，根据rankPoints查找，否则根据rankId查找 
		if(!userRankId.equals("0")) {
			Condition condition = new Condition(IUserRank.RANK_ID,Condition.EQUALS,userRankId);
			criteria.addCondition(condition);
		}
		List<UserRank> userRanks = getDefaultManager().getList(ModelNames.USER_RANK, criteria);
		UserRank rank = null;
		for(Iterator iterator = userRanks.iterator();iterator.hasNext();) {
			UserRank userRank = (UserRank)iterator.next();
			//rankId为0时,根据rankPoints判断rank
			if(userRankId.equals("0")) {
				//当rank的maxPoints为0时，说明是代销用户，代销用户只能根据rankId判断
				if(userRank.getMaxPoints() > 0 && rankPoints >= userRank.getMinPoints() && rankPoints <= userRank.getMaxPoints()) {
					rank = userRank;
					request.setAttribute("rankName", "您的等级是" + userRank.getRankName());
					break;
				}
			}
			//否则，根据rankId判断
			else {
				rank = userRank;
				request.setAttribute("rankName", "您的等级是" + userRank.getRankName());
			}
		}
		
		return rank;
	}
	
	//根据userRank查找nextRank
	public void getNextRank(UserWrapper uw, UserRank userRank, HttpServletRequest request) {
		
		UserRank nextRank = null;
		//首先查询有没有最低积分等于该用户所属rank的最高积分的rank，如果有则是nextRank
		Condition condition = new Condition(IUserRank.MIN_POINTS,Condition.EQUALS,userRank.getMaxPoints()+"");
		Criteria criteria = new Criteria();
		criteria.addCondition(condition);
		List<UserRank> nextUserRanks = getDefaultManager().getList(ModelNames.USER_RANK, criteria);
		
		//存在相等
		if(nextUserRanks.size() > 0) {
			nextRank = nextUserRanks.get(0);
		}
		
		//不存在，查找出所有最低积分高于该用户所属rank的最高积分的rank，从中选出最低积分最低的那个作为nextRank
		else {
			condition.setOperator(Condition.GREATERTHAN);
			criteria.removeAllCondition();
			criteria.addCondition(condition);
			nextUserRanks = getDefaultManager().getList(ModelNames.USER_RANK, criteria);

			long minPoints = -1;
			for(Iterator iterator = nextUserRanks.iterator();iterator.hasNext();) {
				UserRank rank = (UserRank)iterator.next();
				if(minPoints == -1) {
					minPoints = rank.getMinPoints();
				}
				if(rank.getMinPoints() <= minPoints) {
					nextRank = rank;
					minPoints = rank.getMinPoints();
				}
			}
		}		
		
		if(nextRank != null) {
			long points = nextRank.getMinPoints() - uw.getRankPoints(); //距离升级差的积分
			request.setAttribute("nextRankName", "您还差" +points + "积分达到" + nextRank.getRankName());
		}
	}

	public void setCollectGoods(InputStream collectGoods) {
		this.collectGoods = collectGoods;
	}

	public InputStream getCollectGoods() {
		return collectGoods;
	}
}