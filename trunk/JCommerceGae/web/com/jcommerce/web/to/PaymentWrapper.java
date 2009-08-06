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
	}
	
	public Payment getPayment() {
		return payment;
	}
	
    // for template
    public String getPaymentId() {
    	return getPayment().getId();
    }
}
