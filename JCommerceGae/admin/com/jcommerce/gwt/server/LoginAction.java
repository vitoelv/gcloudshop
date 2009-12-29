package com.jcommerce.gwt.server;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.jcommerce.core.service.Condition;
import com.jcommerce.core.service.Criteria;
import com.jcommerce.core.service.IDefaultManager;
import com.jcommerce.gwt.client.ModelNames;
import com.jcommerce.gwt.client.model.IAdminUser;
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
      if(authenticate(username,pwd)){
    	  response.sendRedirect("/admin_zh.html");
      }
      else {
    	  request.setAttribute("error", "用户名或密码错误");
    	  request.getRequestDispatcher("/login.jsp").forward(request, response);
//    	  response.sendRedirect("/login.jsp?error=)
      }
    }
    
    private Boolean authenticate(String name, String password){
    	// TODO this is fake for demo start from empty data store
    	if("admin".equals(name) && "admin".equals(password)) {
    		return true;
    	}
    	
    	Boolean isExist = false;
    	IDefaultManager manager = (IDefaultManager)SpringUtil.getDefaultManager();
    	Criteria criteria = new Criteria();
    	criteria.addCondition(new Condition(IAdminUser.USER_NAME,Condition.EQUALS,name));
    	criteria.addCondition(new Condition(IAdminUser.PASSWORD,Condition.EQUALS,password));
    	List res = manager.getList(ModelNames.ADMINUSER, criteria);
    	isExist = res.isEmpty()?false:true;
    	return isExist;
    }


}
