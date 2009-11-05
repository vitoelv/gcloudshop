package com.jcommerce.core.service.payment;

import java.util.List;
import java.util.Map;

import com.jcommerce.core.model.Payment;

public interface IPaymentMetaManager {
    
    public String getCode(String orderId, String paymentId);
    
    public void install(String paymentCode);
    
    public void uninstall(String paymentId);
    
    public PaymentConfigMeta getPaymentConfigMeta(String paymentId);
    
    public void savePaymentConfig(Map<String, Object> props);
    
    public List<Map<String, Object>> getCombinedPaymentMetaList();
    
    public List<Payment> getPaymentList();
    
    public List<PaymentConfigMeta> getInstalledPaymentMetaList();
    
}
