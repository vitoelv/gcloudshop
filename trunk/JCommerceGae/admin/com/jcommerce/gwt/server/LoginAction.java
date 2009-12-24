package com.jcommerce.gwt.server;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.jcommerce.core.service.Condition;
import com.jcommerce.core.service.Criteria;
import com.jcommerce.core.service.IDefaultManager;
import com.jcommerce.gwt.client.ModelNames;

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
    }
    
    private Boolean authenticate(String name, String password){
    	WebApplicationContext springContext = WebApplicationContextUtils.getWebApplicationContext(getServletContext());
    	Boolean isExist = false;
    	IDefaultManager manager = (IDefaultManager)springContext.getBean("DefaultManager");
    	Criteria criteria = new Criteria();
    	criteria.addCondition(new Condition("userName",0,name));
    	criteria.addCondition(new Condition("password",Condition.EQUALS,password));
    	List res = manager.getList(ModelNames.ADMIN_USER, criteria);
    	return res.isEmpty()?false:true;
    }


}
