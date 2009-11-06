package com.jcommerce.web.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.datanucleus.util.StringUtils;

import com.jcommerce.core.model.Comment;
import com.jcommerce.core.model.User;
import com.jcommerce.core.service.Condition;
import com.jcommerce.core.service.Criteria;
import com.jcommerce.core.service.IDefaultManager;
import com.jcommerce.gwt.client.ModelNames;
import com.jcommerce.gwt.client.model.IComment;
import com.jcommerce.web.front.action.IWebConstants;
import com.jcommerce.web.front.action.helper.Pager;
import com.jcommerce.web.to.CommentWrapper;
import com.jcommerce.web.to.Lang;
import com.jcommerce.web.to.Message;
import com.jcommerce.web.to.ShopConfigWrapper;
import com.jcommerce.web.to.UserWrapper;

public class LibMain {
	
	/**
	 * 查询评论内容
	 *
	 * @access  public
	 * @params  integer     $id
	 * @params  integer     $type
	 * @params  integer     $page
	 * @return  array
	 */
	public static Map<String, Object> assignComment(String id, Long type, int page, IDefaultManager manager) {
		/* 取得评论列表 */
		Criteria c = new Criteria();
		c.addCondition(new Condition(IComment.ID_VALUE, Condition.EQUALS, id));
		c.addCondition(new Condition(IComment.COMMENT_TYPE, Condition.EQUALS, type.toString()));
		c.addCondition(new Condition(IComment.STATUS, Condition.EQUALS, IComment.STATUS_ACTIVE.toString()));
		int count = manager.getCount(ModelNames.COMMENT, c);
		int size = ShopConfigWrapper.getDefaultConfig().getInt("comments_number");
		if(size<0) {
			size = 5;
		}
		int pageCount = count > 0 ? (count / size)+1 : 1;
		
		Map<String, CommentWrapper> arr = new HashMap<String, CommentWrapper>();
		List<String> ids = new ArrayList<String>();
		List<Comment> comments = manager.getList(ModelNames.COMMENT, c, (page-1)*size, size);
		
		for(Comment comment: comments) {
			String cid = comment.getPkId();
			ids.add(cid);
			CommentWrapper commentWrapper = arr.get(cid);
			if(commentWrapper == null) {
				commentWrapper = new CommentWrapper(comment);
				arr.put(cid, commentWrapper);
			}
			commentWrapper.put("id", cid);
			commentWrapper.put("email", comment.getEmail());
			commentWrapper.put("username", comment.getUserName());
			commentWrapper.put("content", StringUtils.replaceAll(
							StringUtils.replaceAll(comment.getContent(), "\r\n", "<br />")
							, "\n", "<br />"));
			commentWrapper.put("rank", comment.getCommentRank());
			String addTime = LibTime.localDate(
					ShopConfigWrapper.getDefaultConfig().getString(ShopConfigWrapper.CFG_KEY_TIME_FORMAT), 
					comment.getAddTime());
			commentWrapper.put("addTime", addTime);
			
		}
		/* 取得已有回复的评论 */
		if(ids.size()>0) {
			// TODO comment reply
			
		}
		
		Pager pager = new Pager();
		pager.setPage(page);
        pager.setSize(size);
        pager.setRecordCount(count);
        pager.setPageCount(pageCount);
		pager.setPageFirst("javascript:gotoPage(1,'"+id+"',"+type+")");
		pager.setPagePrev(page > 1 ? "javascript:gotoPage(" +(page-1)+ ",'"+id+"',"+type+")" : "javascript:;");
		pager.setPageNext(page < pageCount ? "javascript:gotoPage("+(page + 1)+",'"+id+"',"+type+")" : "javascript:;");
		pager.setPageLast(page < pageCount ? "javascript:gotoPage("+(pageCount)+",'"+id+"',"+type+")" : "javascript:;");
		
		Map<String, Object> cmt = new HashMap<String, Object>();
		cmt.put("comments",arr.values());
		cmt.put("pager", pager);
		
		return cmt;
		
	}
	
	
	
	
	/**
	 * 创建分页信息
	 *
	 * @access  public
	 * @param   string  $app            程序名称，如category
	 * @param   string  $cat            分类ID
	 * @param   string  $record_count   记录总数
	 * @param   string  $size           每页记录数
	 * @param   string  $sort           排序类型
	 * @param   string  $order          排序顺序
	 * @param   string  $page           当前页
	 * @param   string  $keywords       查询关键字
	 * @param   string  $brand          品牌
	 * @param   string  $price_min      最小价格
	 * @param   string  $price_max      最高价格
	 * @return  void
	 */
	
	// TODO assignPager -- refactor & support of sort/order
    public static void assignPager(
    		String app, Long catLongId, int recordCount, int size, String sort, String order, int page, 
    		String keyword, String brandId, double priceMin, double priceMax,
    		String displayType, String filterAttr, String urlFormat, Map<String, Object> schArray, HttpServletRequest request) {
    	
    	int pageCount = recordCount > 0 ? (recordCount / size)+1 : 1;
    	
        Pager pager = new Pager();
        pager.getSearch().put("category", catLongId);
        pager.getSearch().put("keywords", keyword);
        pager.getSearch().put("sort", sort);
        pager.getSearch().put("order", order);
        pager.getSearch().put("cat", catLongId);
        pager.getSearch().put("brand", brandId);
        pager.getSearch().put("price_min", 0);
        pager.getSearch().put("price_max", 0);
        pager.getSearch().put("filter_attr", "");
        pager.getSearch().put("display", IWebConstants.DISPLAY_LIST);
        pager.setPage(page);
        pager.setSize(size);
        pager.setSort(sort);
        pager.setOrder(order);
        pager.setRecordCount(recordCount);
        pager.setPageCount(pageCount);
        pager.setDisplay(displayType);
        request.setAttribute("pager", pager);
        
        Map<String, Object> uriArgs = new HashMap<String, Object>();
        if(IWebConstants.APP_BRAND.equals(app)) {
        	uriArgs.put("cid", catLongId);
        	uriArgs.put("bid", brandId);
        	uriArgs.put("sort", sort);
        	uriArgs.put("order", order);
        	uriArgs.put("display", displayType);
        }        
        else if(IWebConstants.APP_CATEGORY.equals(app)) {
        	uriArgs.put("cid", catLongId);
        	uriArgs.put("bid", brandId);
        	uriArgs.put("sort", sort);
        	uriArgs.put("order", order);
        	uriArgs.put("display", displayType);
        	
        	
        }
        
        pager.setStyleid((Integer)ShopConfigWrapper.getDefaultConfig().get("pageStyle"));
        
        int pagePrev  = (page > 1) ? page - 1 : 1;
        int pageNext  = (page < pageCount) ? page + 1 : pageCount;
        
        if (pager.getStyleid() == 0) {
			pager.setPageFirst(LibCommon.buildUri(app, uriArgs, "", 1, 0));
			pager.setPagePrev(LibCommon.buildUri(app, uriArgs, "", pagePrev,0));
			pager.setPageNext(LibCommon.buildUri(app, uriArgs, "", pageNext,0));
			pager.setPageLast(LibCommon.buildUri(app, uriArgs, "", pageCount, 0));

			Map<Integer, Integer> array = pager.getArray();
			for (int i = 1; i <= pageCount; i++) {
				array.put(i, i);
			}
		}
        else {
    		// TODO pager.getStyleid()!=0
    	}
        //${smarty.server.PHP_SELF}
        

        
    }
    /**
     *  生成给pager.lbi赋值的数组
     *
     * @access  public
     * @param   string      $url        分页的链接地址(必须是带有参数的地址，若不是可以伪造一个无用参数)
     * @param   array       $param      链接参数 key为参数名，value为参数值
     * @param   int         $record     记录总数量
     * @param   int         $page       当前页数
     * @param   int         $size       每页大小
     *
     * @return  array       $pager
     */
    
    public static Pager getPager(String url, Map<String, Object> param, int recordCount, int page, int size) {
    	if(size<1) {
    		size = 10;
    	}
    	if(page<1) {
    		page = 1;
    	}
    	int pageCount = recordCount > 0 ? (recordCount / size)+1 : 1;
    	
    	if(page > pageCount) {
    		page = pageCount;
    	}
    	Pager pager = new Pager();
    	pager.setStyleid((Integer)ShopConfigWrapper.getDefaultConfig().get("pageStyle"));
        int pagePrev  = (page > 1) ? page - 1 : 1;
        int pageNext  = (page < pageCount) ? page + 1 : pageCount;
        
    	StringBuffer paramUrl = new StringBuffer("?");
    	for(String key : param.keySet()) {
    		String value = param.get(key).toString();
    		paramUrl.append(key).append("=").append(value).append("&");
    	}
    	pager.setUrl(url);
    	pager.setStart((page-1)*size);
    	pager.setPage(page);
    	pager.setSize(size);
    	pager.setRecordCount(recordCount);
    	pager.setPageCount(pageCount);
    	
    	if(pager.getStyleid()==0) {
    		pager.setPageFirst(url+paramUrl.toString()+"page=1");
    		pager.setPagePrev(url+paramUrl.toString()+"page="+pagePrev);
    		pager.setPageNext(url+paramUrl.toString()+"page="+pageNext);
    		pager.setPageLast(url+paramUrl.toString()+"page="+pageCount);
    		
			Map<Integer, Integer> array = pager.getArray();
			for (int i = 1; i <= pageCount; i++) {
				array.put(i, i);
			}
    	}
    	else {
    		// TODO pager.getStyleid()!=0
    	}
    	
    	pager.setSearch(param);
    	
    	return pager;
    }
    
    public static String showMessage(String content, String link, String href, String type, Boolean autoRedirect, HttpServletRequest request) {
    	if(type == null) {
    		type = "info";
    	}
    	if(autoRedirect==null) {
    		autoRedirect = false;
    	}
    	Message msg = new Message();
    	msg.setContent(content);
    	msg.setLink(StringUtils.isEmpty(link)? (String)Lang.getInstance().get("backUpPage"):link);
    	msg.setHref(StringUtils.isEmpty(href)?"javascript:history.back()":href);
    	msg.setType(type);
    	
    	request.setAttribute("message", msg);
    	request.setAttribute("autoRedirect", autoRedirect);
    	
    	return "message";
    	
    }
    
    public static UserWrapper getUserInfo(String userId, IDefaultManager manager) {
    	User user = (User)manager.get(ModelNames.USER, userId);
    	
    	return new UserWrapper(user);
    }
}