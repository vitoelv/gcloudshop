package com.jcommerce.web.to;

import com.jcommerce.core.model.ModelObject;
import com.jcommerce.core.model.Payment;

public class PaymentWrapper extends BaseModelWrapper {


	Payment payment;
	@Override
	protected Object getWrapped() {
		return getPayment();
	}
	public PaymentWrapper(ModelObject payment) {
		super();
		this.payment = (Payment)payment;
		
		put("formatPayFee", "<span id=\"ECS_CODFEE\">"+ getPayment().getPayFee()  + "</span>");
	}
	
	public Payment getPayment() {
		return payment;
	}
	
    // for template
    public String getPayId() {
    	return getPayment().getPkId();
    }
}
