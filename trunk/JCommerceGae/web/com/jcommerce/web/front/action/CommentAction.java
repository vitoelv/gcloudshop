package com.jcommerce.web.front.action;


import com.jcommerce.core.model.Comment;
import com.jcommerce.gwt.client.model.IComment;
import com.jcommerce.gwt.client.panels.system.IShopConfigMeta;
import com.jcommerce.web.to.Lang;
import com.jcommerce.web.util.LibCommon;
import com.jcommerce.web.util.LibMain;
import com.opensymphony.xwork2.inject.Inject;
import freemarker.template.TemplateException;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.views.freemarker.FreemarkerManager;
import org.datanucleus.util.StringUtils;
import org.json.JSONObject;

public class CommentAction extends BaseAction {
	public void debug(String s) {
		System.out.println(" in [InputStream]: "+s );
	}
	
	FreemarkerManager freemarkerManager;
	

	
    private InputStream jsonRes;

	public InputStream getJsonRes() {
		return jsonRes;
	}

	public void setJsonRes(InputStream jsonRes) {
		this.jsonRes = jsonRes;
	}
    
	@Override
	public String onExecute() throws Exception {
		try {
			debug("in execute");        
	        HttpServletRequest request = getRequest();
	       
	        String act = request.getParameter("act");
	        String cmtStr = request.getParameter("cmt");
	        debug("act="+act+", cmt="+cmtStr);
	        JSONObject res = new JSONObject();;
	        res.put("error", 0);
	        res.put("message", "");
	        res.put("content", "");
	        System.out.println(act);
	        
	        String content = null;
	        if(StringUtils.isEmpty(act)) {
	            /*
	             * act 参数为空
	             * 默认为添加评论内容
	             */
	        	JSONObject cmt = getReqAsJSON(request, "cmt");
	        	int page = 1;
	        	String id = cmt.getString("id");
	        	Long type = cmt.getLong("type");
	        	String email = cmt.getString("email");
	        	debug("id="+id+", type="+type+", email="+email);
	        	if(StringUtils.isEmpty(id)) {
	        		res.put("error", 1);
	        		res.put("message", Lang.getInstance().getString("invalidComments"));
	        	}
	        	else {
	        		// TODO verify comments:  captcha and anti-rebot
	        		addComment(cmt);
	        		content = getContent(id,type,page,request);
	        	}
	        }
	        
	        /*修改，处理翻页*/
	        else if(act.equals("gotopage")) {
	        	String id = request.getParameter("id");
	        	Long type = Long.parseLong(request.getParameter("type"));
	        	int page = Integer.parseInt(request.getParameter("page"));
	        	
	        	content = getContent(id,type,page,request);
	        }
	        /*修改完*/
	        if(res.getInt("error")==0) {
	        	res.put("message", getCommentCheckConfig()>0 ? Lang.getInstance().getString("cmtSubmitWait") 
	        					: Lang.getInstance().getString("cmtSubmitDone"));
	        	
	        	res.put("content", content);
	        }
	        
			String out = res.toString();
			debug("in [execute]: out="+out);
//			jsonRes = new StringBufferInputStream(out);
			jsonRes = new ByteArrayInputStream(out.getBytes(IWebConstants.ENC));
			
	        return SUCCESS;
	        
	        
		} catch (Exception ex) {
			ex.printStackTrace();
			throw new RuntimeException(ex);
		}
	}
    
	private int getCommentCheckConfig() {
		int needCheck = getCachedShopConfig().getInt(IShopConfigMeta.CFG_KEY_COMMENT_CHECK);
//		if(Integer.MIN_VALUE == needCheck) {
//			needCheck = 0; // default we need check
//		}
		return needCheck;
	}
	/**
	 * 添加评论内容
	 *
	 * @access  public
	 * @param   object  $cmt
	 * @return  void
	 */
	public void addComment(JSONObject cmt) throws Exception{
		 /* 评论是否需要审核 */
		Comment comment = new Comment();
		int needCheck = getCommentCheckConfig();
		Long status;
		if(needCheck == 1){
			status = IComment.STATUS_INACTIVE;
		}
		else {
			status = IComment.STATUS_ACTIVE;
		}
		comment.setStatus(status);
		comment.setUserId((String)getSession().getAttribute(KEY_USER_ID));
		comment.setEmail((String)(getSession().getAttribute(KEY_USER_EMAIL) == null ? cmt.getString("email") : getSession().getAttribute(KEY_USER_EMAIL)));
		comment.setUserName((String)getSession().getAttribute(KEY_USER_NAME));
		
		comment.setCommentType(cmt.getLong("type"));
		comment.setIdValue(cmt.getString("id"));
		comment.setContent(cmt.getString("content"));
		comment.setCommentRank(cmt.getLong("rank"));
		comment.setAddTime(new Date().getTime());
		comment.setParentId(null);
		comment.setIpAddress(getRequest().getRemoteAddr());
		

		/* 保存评论内容 */
		String id = getDefaultManager().txadd(comment);
		debug("comment added: id="+id);
		
		
		
	}
	
	//返回刷新评论所需的内容
	public String getContent(String id, Long type, int page, HttpServletRequest request) throws IOException, TemplateException {
		
		//设置显示页面所需的数据
		Map<String, Object> cmt = LibMain.assignComment(id, type, page, getDefaultManager(), getCachedShopConfig());		          	
     	Map map = new HashMap();

     	Lang lang = Lang.getInstance();             	       

		map.put("lang", lang);
		map.put("comments", cmt.get("comments"));
		map.put("pager", cmt.get("pager"));
		map.put("commentType", type);
		map.put("id", id);
		map.put("enabledCaptcha", false);
		
		Map<String, Object> smarty = new HashMap<String, Object>();
        Map<String, String> server = new HashMap<String, String>();
        server.put("PHP_SELF", getSelfURL());        
        smarty.put("server", server);
        
        Map<String, Object> session = new HashMap<String, Object>();
        session.put("userName", getSession().getAttribute(KEY_USER_NAME));
        String email = (String)getSession().getAttribute(KEY_USER_EMAIL);
        session.put("email",  email==null? "" : email);
        smarty.put("session", session);
        map.put("smarty", smarty);
        
        map.put("rand", "");
        
        return LibCommon.getTempleteContent(getFreemarkerManager(), map, "comments_list.ftl");
	}

    public FreemarkerManager getFreemarkerManager() {
        return freemarkerManager;
    }
    
    @Inject
    public void setFreemarkerManager(FreemarkerManager freemarkerManager) {
        this.freemarkerManager = freemarkerManager;
    }

		
}
