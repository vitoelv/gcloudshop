package com.jcommerce.core.service.payment;

import java.util.List;
import java.util.Map;

import com.jcommerce.core.model.OrderGoods;
import com.jcommerce.core.model.OrderInfo;
import com.jcommerce.core.model.Payment;

public interface IPaymentMetaPlugin {
    
//    public String getCode();
//    public String getName();
//    public String getPayFee();
//    public Boolean isCod();
//    public Boolean isOnline();
//    public String getDescription(); 
    
	public PaymentConfigMeta getDefaultConfigMeta();
    public String getCode(OrderInfo order, Payment payment , List<OrderGoods> orderGoods);
//    public String getDefaultConfig();
    public String serializeConfig(Map<String, Object> props);
    // key -> value
    // lable -> key -> type -> (options)
    
    public PaymentConfigMeta deserializeConfig(String serializedConfig);
    
    
}
