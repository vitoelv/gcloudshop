package com.jcommerce.core.payment;

import java.util.Map;

import com.jcommerce.core.model.Order;
import com.jcommerce.core.model.Payment;

public interface IPaymentMetaPlugin {
    
    public String getCode();
    public String getName();
    public String getPayFee();
    public Boolean isCod();
    public Boolean isOnline();
    public String getDescription(); 
    
    public String getCode(Order order, Payment payment);
    public String getDefaultConfig();
    public String getSerializedConfig(Map<String, Object> props);
    // key -> value
    // lable -> key -> type -> (options)
    
    public PaymentConfigMeta getPaymentConfigMeta(String serializedConfig);
    
    
}
