package com.jcommerce.web.front.action;

import java.io.InputStream;
import java.io.StringBufferInputStream;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;

import com.jcommerce.core.model.User;
import com.jcommerce.core.service.Condition;
import com.jcommerce.core.service.Criteria;
import com.jcommerce.gwt.client.ModelNames;
import com.jcommerce.gwt.client.model.IOrderInfo;
import com.jcommerce.gwt.client.model.IUser;
import com.jcommerce.web.front.action.helper.Pager;
import com.jcommerce.web.to.Affiliate;
import com.jcommerce.web.to.Lang;
import com.jcommerce.web.to.ShopConfigWrapper;
import com.jcommerce.web.to.UserWrapper;


public class UserAction extends BaseAction {
	public void debug(String s) {
		System.out.println(" in [UserAction]: "+s );
	}
	
	public static final String RES_USER_PASSPORT = "user_passport";
	public static final String RES_USER_CLIPS = "user_clips";
	public static final String RES_IS_REGISTERED = "is_registered";
	public static final String RES_USER_TRANSACTION = "user_transaction";
	
	private String username;
	private String password;
	private String email;
	private Map<String, String> other;
	private String action;
	
	private InputStream isRegistered;
	
	@Override
	public String execute() throws Exception {
		try {
			debug("in execute");
			super.execute();
			HttpServletRequest request = getRequest();

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
				
				request.setAttribute("rankName", "注册用户");
				request.setAttribute("nextRankName", "");
				UserWrapper uw = getUserDefault(userId);
				uw.put("isValidate", 1);
				uw.put("shippedOrder", null);
				request.setAttribute("info", uw);
				request.setAttribute("userNotice", ShopConfigWrapper.getDefaultConfig().get("userNotice"));
				request.setAttribute("prompt", new String[0]);
				includeUserMenu();
				return RES_USER_CLIPS;
			}
			else if("register".equals(action)) {
				request.setAttribute("enabledCaptcha", 0);
				request.setAttribute("rand", new Double(1000000*Math.random()).longValue());
				
				request.setAttribute("shopRegClosed", ShopConfigWrapper.getDefaultConfig().get("shopRegClosed"));
				return RES_USER_PASSPORT;
			}
			else if("login".equals(action)) {
				request.setAttribute("backAct", backAct);
				return RES_USER_PASSPORT;
			}
			else if("act_login".equals(action)) {
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
			else if("act_register".equals(action)) {
				String error = register(getUsername(), getPassword(), getEmail(), other);
				if(error == null) {
					return LibMain.showMessage(Lang.getInstance().getString("registerSuccess"), Lang.getInstance().getString("profileLnk"), 
							"user.action", "info", true, request);
				} else {
					return LibMain.showMessage(error, Lang.getInstance().getString("signUp"), 
							"user.action?act=register", "info", true, request);
				}
			}
			else if("is_registered".equals(action)) {
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
			else if("order_list".equals(action)){
				String sPage = (String)request.getParameter("page");
				int page = (sPage!=null && Integer.valueOf(sPage)>0) ? Integer.valueOf(sPage) : 1;
				Criteria criteria = new Criteria();
				Condition cond = new Condition(IOrderInfo.USER_ID, Condition.EQUALS, userId);
				criteria.addCondition(cond);
				int recordCount = getDefaultManager().getCount(ModelNames.ORDERINFO, criteria);
				
				Map<String, Object> param = new HashMap<String, Object>();
				param.put("act", action);
				Pager pager = LibMain.getPager("user.action", param, recordCount, page, -1);
				
				request.setAttribute("pager", pager);
				request.setAttribute("orders", 
						LibTransaction.getUserOrders(userId, pager.getSize(), pager.getStart(), getDefaultManager()));
				
				includeUserMenu();
				return RES_USER_TRANSACTION;
			}
			else if("logout".equals(action)) {
				logout();
				return LibMain.showMessage(Lang.getInstance().getString("logout"), Lang.getInstance().getString("backHomeLnk"), 
						"home.action", "info", true, request);
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
			getSession().setAttribute(KEY_USER_ID, username);
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


	public Map<String, String> getOther() {
		return other;
	}


	public void setOther(Map<String, String> other) {
		this.other = other;
	}

	public InputStream getIsRegistered() {
		return isRegistered;
	}
	
	
	public String toString() {
		// this is a tricky hacking
		// in freemarker the action variable points to the Action class
		
		return action;
	}


}
