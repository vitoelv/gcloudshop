package com.jcommerce.gwt.server;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;

import com.jcommerce.core.model.AdminUser;
import com.jcommerce.core.service.Condition;
import com.jcommerce.core.service.Criteria;
import com.jcommerce.core.service.IDefaultManager;
import com.jcommerce.gwt.client.ModelNames;
import com.jcommerce.gwt.client.model.IAdminUser;
import com.jcommerce.gwt.client.model.IComment;
import com.jcommerce.web.util.SpringUtil;

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
    	
      String username = request.getParameter("userName");
      String pwd = request.getParameter("userPwd");
      // added
      if(authenticate(username,pwd,request)){
    	  String adminUrl = "/admin.jsp?gwt.codesvr=192.168.68.75:9997";
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
//    	  response.sendRedirect("/login.jsp?error=)
      }
    }
    
    private Boolean authenticate(String name, String password,HttpServletRequest request){
    	
    	Boolean isExist = false;
    	IDefaultManager manager = (IDefaultManager)SpringUtil.getDefaultManager();
    	Criteria criteria = new Criteria();
    	criteria.addCondition(new Condition(IAdminUser.USER_NAME,Condition.EQUALS,name));
    	criteria.addCondition(new Condition(IAdminUser.PASSWORD,Condition.EQUALS,password));
    	List res = manager.getList(ModelNames.ADMINUSER, criteria);
    	isExist = res.isEmpty()?false:true;
    	if(isExist){
    		setAdminUserInfo(request,(AdminUser)res.get(0));
    	}
    	else {
    		// only go to fake if it does not exist.
			// TODO this is fake for demo start from empty data store
			if ("admin".equals(name) && "admin".equals(password)) {
				AdminUser au = new AdminUser();
				au.setUserName("I am Admin");
				au.setEmail("adminUser@gmail.com");
				setAdminUserInfo(request, au);
				isExist = true;
			}
		}
    	
    	return isExist;
    }
    private void setAdminUserInfo(HttpServletRequest request , AdminUser adminUser){
    	HttpSession session = request.getSession();
    	session.setAttribute(IAdminConstants.KEY_ADMIN_USERID, adminUser.getUserName());
    	session.setAttribute(IAdminConstants.KEY_ADMIN_USEREMAIL, adminUser.getEmail());
    	session.setAttribute(IAdminConstants.KEY_ADMIN_USERIP, request.getRemoteAddr());
    }


}
