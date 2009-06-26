package com.jcommerce.core.payment;

import java.util.List;
import java.util.Map;

import com.jcommerce.core.model.Payment;

public interface IPaymentMetaManager {
    
    public String getCode(int orderId, int paymentId);
    
    public void install(String paymentCode);
    
    public void uninstall(int paymentId);
    
    public PaymentConfigMeta getPaymentConfigMeta(int paymentId);
    
    public void savePaymentConfig(Map<String, Object> props);
    
    public List<Map<String, Object>> getCombinedPaymentMetaList();
    
    public List<Payment> getPaymentList();
    
}
