package com.jcommerce.gwt.server;

import com.jcommerce.core.model.AdminUser;
import com.jcommerce.core.service.Condition;
import com.jcommerce.core.service.Criteria;
import com.jcommerce.core.service.IDefaultManager;
import com.jcommerce.gwt.client.ModelNames;
import com.jcommerce.gwt.client.model.IAdminUser;
import com.jcommerce.gwt.client.panels.system.IShopConfigMeta;
import com.jcommerce.web.to.ShopConfigWrapper;
import com.jcommerce.web.util.SpringUtil;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;

public class LoginAction extends HttpServlet {
	public void init(ServletConfig config) throws ServletException {
         super.init(config);
         
	}
	public void doGet(HttpServletRequest request, HttpServletResponse response) 
	   throws ServletException, IOException {
		doPost(request,response);
    }
    public void doPost(HttpServletRequest request, HttpServletResponse response) 
      throws ServletException, IOException {
    	
      String action = request.getParameter("action");
      if("login".equals(action)){
    	  login(request,response);
      }
      else if("logout".equals(action)){
    	  logout(request,response);
      }
    }
    
    private void login(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException{
    	String username = request.getParameter("userName");
        String pwd = request.getParameter("userPwd");
        if(authenticate(username,pwd,request)){
      	  String adminUrl = "/admin.jsp";
      	  String devServer = (String)request.getSession().getAttribute(IAdminConstants.KEY_GWT_DEV_SERVER);
      	  if(StringUtils.isNotBlank(devServer)) {
      		// in dev mode
      		  adminUrl += "?"+IAdminConstants.KEY_GWT_DEV_SERVER+"="+devServer;
      	  }
      	  response.sendRedirect(adminUrl);
        }
        else {
      	  request.setAttribute("error", "用户名或密码错误");
      	  request.getRequestDispatcher("/login.jsp").forward(request, response);
//      	  response.sendRedirect("/login.jsp?error=)
        }
    }
    
    private Boolean authenticate(String name, String password, HttpServletRequest request) {
        Boolean passed = false;
        
        boolean isDefaultEnabled = SpringUtil.getShopConfigManager().isDefaultAdminEnabled();
        if (isDefaultEnabled && "admin".equals(name) && "admin".equals(password)) {
            // default admin enabled
            // TODO this is fake for demo start from empty data store
            AdminUser au = new AdminUser();
            au.setUserName("Default Administrator");
            au.setEmail("adminUser@gmail.com");
            setAdminUserInfo(request, au);
            passed = true;

            return passed;
        }
        
        
        IDefaultManager manager = (IDefaultManager)SpringUtil.getDefaultManager();
        Criteria criteria = new Criteria();
        criteria.addCondition(new Condition(IAdminUser.USER_NAME, Condition.EQUALS, name));
        criteria.addCondition(new Condition(IAdminUser.PASSWORD, Condition.EQUALS, password));
        List res = manager.getList(ModelNames.ADMINUSER, criteria);
        passed = res.isEmpty() ? false : true;
        if (passed) {
            setAdminUserInfo(request, (AdminUser)res.get(0));
        } 

        return passed;
    }
    private void setAdminUserInfo(HttpServletRequest request , AdminUser adminUser){
    	HttpSession session = request.getSession();
    	session.setAttribute(IAdminConstants.KEY_ADMIN_USERID, adminUser.getUserName());
    	session.setAttribute(IAdminConstants.KEY_ADMIN_USEREMAIL, adminUser.getEmail());
    	session.setAttribute(IAdminConstants.KEY_ADMIN_USERIP, request.getRemoteAddr());
    }
    
    public void logout(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
    	String loginURL = "/login.jsp";
    	
    	String devServer = (String)request.getSession(false).getAttribute(IAdminConstants.KEY_GWT_DEV_SERVER);
    	if(StringUtils.isNotBlank(devServer)){
    		loginURL +="?"+IAdminConstants.KEY_GWT_DEV_SERVER+"="+devServer;
    	}
    	request.getSession().invalidate();
    	response.sendRedirect(loginURL);

    }


}
