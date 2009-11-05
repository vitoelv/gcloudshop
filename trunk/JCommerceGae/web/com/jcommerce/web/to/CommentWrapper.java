package com.jcommerce.web.to;

import com.jcommerce.core.model.Comment;
import com.jcommerce.core.model.ModelObject;

public class CommentWrapper extends BaseModelWrapper {

	Comment comment;
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

}
