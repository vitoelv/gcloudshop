package com.jcommerce.web.to;

import java.text.SimpleDateFormat;

import com.jcommerce.core.model.Feedback;
import com.jcommerce.core.model.ModelObject;
import com.jcommerce.gwt.client.model.IFeedback;

public class FeedbackWrapper extends BaseModelWrapper {

	Feedback feedback;
	@Override
	protected Object getWrapped() {
		return getFeedback();
	}
	public FeedbackWrapper(ModelObject feedback) {
		super();
		this.feedback = (Feedback)feedback;
	}
	
	public Feedback getFeedback() {
		return feedback;
	}
	
	public String getMsgType() {
		long msgType = getFeedback().getMsgType();
		if(msgType == IFeedback.TYPE_LEAVEWORD) {
			return "留言";
		} else if(msgType == IFeedback.TYPE_COMPLAINT) {
			return "投诉";
		} else if(msgType == IFeedback.TYPE_ASK) {
			return "询问";
		} else if(msgType == IFeedback.TYPE_AFTERMARKET) {
			return "售后";
		} else if(msgType == IFeedback.TYPE_BUY) {
			return "求购";
		} else {
			return getFeedback().getUserName();
		}
	}
	
	public String getMsgTime() {
		long time = getFeedback().getMsgTime();
		
		SimpleDateFormat dateformat=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return dateformat.format(time);
	}

}
