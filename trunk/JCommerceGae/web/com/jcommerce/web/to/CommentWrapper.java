package com.jcommerce.web.to;

import java.text.SimpleDateFormat;
import java.util.List;

import com.jcommerce.core.model.Comment;
import com.jcommerce.core.model.Goods;
import com.jcommerce.core.model.ModelObject;
import com.jcommerce.core.service.Condition;
import com.jcommerce.core.service.Criteria;
import com.jcommerce.core.service.IDefaultManager;
import com.jcommerce.gwt.client.ModelNames;
import com.jcommerce.gwt.client.model.IComment;
import com.jcommerce.web.util.SpringUtil;

public class CommentWrapper extends BaseModelWrapper {

	Comment comment;
	private IDefaultManager manager  = SpringUtil.getDefaultManager();
	@Override
	protected Object getWrapped() {
		return getComment();
	}
	public CommentWrapper(ModelObject comment) {
		super();
		this.comment = (Comment)comment;
	}
	
	public Comment getComment() {
		return comment;
	}
	
	public String getCommentType() {
		return getComment().getCommentType().toString();
	}
	
//	public String getCmtName() {
//		String goodsId = getComment().getIdValue();
//		Goods goods = (Goods) manager.get(ModelNames.GOODS, goodsId);
//		return goods.getGoodsName();
//	}

	public String getFormatedAddTime() {
		Long addTime = getComment().getAddTime();
		SimpleDateFormat formatter = new SimpleDateFormat ("yyyy-MM-dd HH:mm:ss");
		return formatter.format(addTime);
	}
	
	public String getCommentId() {
		return getComment().getPkId();
	}
	
	public String getContent() {
		return getComment().getContent();
	}
	
//	public String getReplyContent() {
//		String id = getComment().getPkId();
//		Criteria criteria = new Criteria();
//		criteria.addCondition(new Condition(IComment.PARENT_ID, Condition.EQUALS, id));
//		List reply = manager.getList(ModelNames.COMMENT, criteria);
//		if(reply.size() == 0) {
//			return null;
//		}
//		else {
//			Comment comment = (Comment) reply.get(0);
//			return comment.getContent();
//		}
//	}
}
