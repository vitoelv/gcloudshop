package com.jcommerce.web.util;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.google.gwt.user.client.Random;
import com.jcommerce.core.service.IDefaultManager;
import com.jcommerce.web.front.action.IWebConstants;

public class LibInsert {
	/**
	 * 调用评论信息
	 *
	 * @access  public
	 * @return  string
	 */
	public static void insertComments(Long type, String id, IDefaultManager manager, HttpServletRequest request) {
		/* 验证码相关设置 */
//		ShopConfigWrapper.getDefaultConfig().get("captcha")
		
		if(false) {
			request.setAttribute("enabledCaptcha", true);
			request.setAttribute("rand", Random.nextInt());
		}
		else {
			request.setAttribute("enabledCaptcha", false);
		}
		
		request.setAttribute("username", request.getSession().getAttribute(IWebConstants.KEY_USER_NAME));
		request.setAttribute("email", request.getSession().getAttribute(IWebConstants.KEY_USER_EMAIL));
		request.setAttribute("commentType", type);
		request.setAttribute("id", id);
		Map<String, Object> cmt = LibMain.assignComment(id, type, 1, manager);
		request.setAttribute("comments", cmt.get("comments"));
		request.setAttribute("pager", cmt.get("pager"));
		
		return;
		
	}
}
