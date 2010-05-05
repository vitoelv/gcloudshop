package com.jcommerce.core.service.payment.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.jcommerce.core.model.OrderGoods;
import com.jcommerce.core.model.OrderInfo;
import com.jcommerce.core.model.Payment;
import com.jcommerce.core.service.payment.IPaymentMetaPlugin;
import com.jcommerce.core.service.payment.PaymentConfigFieldMeta;
import com.jcommerce.core.service.payment.PaymentConfigMeta;

public class Paypal extends BasePaymentMetaPlugin implements IPaymentMetaPlugin {
	
	private static final PaymentConfigMeta defaultPaymentConfigMeta;
    private static final Map<String, String> defaultConfigValues;
    private static final Map<String, PaymentConfigFieldMeta> fieldMetas;
    public static final String BUTTON_TEXT = "button_text";
    public static final String PAY_DESC = "paypal_desc";
    public static final String CURRENCY = "paypal_currency";
    public static final String PAY_NAME = "pay_name";
    public static final String ACCOUNT = "paypal_account";
    
    
    @Override
    protected Map<String, PaymentConfigFieldMeta> getFieldMetas() {
    	return fieldMetas;
    }
    static {
        defaultConfigValues = new HashMap<String, String>();
        defaultConfigValues.put(PAY_NAME, "");
        defaultConfigValues.put(ACCOUNT, "jcommerce.test@gmail.com");
        defaultConfigValues.put(ACCOUNT, "Pay Now!");
        
        fieldMetas = new HashMap<String, PaymentConfigFieldMeta>();
        
        PaymentConfigFieldMeta m = null;
        Map<String, String> options = null;
        
       
//        m = new PaymentConfigFieldMeta();
//        m.setLable("支付方式名称");
//        m.setOptions(null);
//        m.setTip(null);
//        fieldMetas.put(PAY_NAME, m);
//        
//        m = new PaymentConfigFieldMeta();
//        m.setLable("支付方式描述");
//        m.setOptions(null);
//        m.setTip(null);
//        fieldMetas.put(PAY_DESC, m);
        
        m = new PaymentConfigFieldMeta();
        m.setLable("Paypal Account");
        m.setOptions(null);
        m.setTip(null);
        fieldMetas.put(ACCOUNT, m);
        
        
        m = new PaymentConfigFieldMeta();
        m.setLable("Currency Code");
        options = new HashMap<String, String>();
        options.put("AUD", "澳元");
        options.put("CAD", "加元");
        options.put("EUR", "欧元");
        options.put("GBP", "英镑");
        options.put("JPY", "日元");
        options.put("USD", "美元");
        options.put("HKD", "港元");
        m.setOptions(options);
        m.setTip(null);
        fieldMetas.put(CURRENCY, m);
        
        
        m = new PaymentConfigFieldMeta();
        m.setLable("Button Text");
        m.setOptions(null);
        m.setTip(null);
        fieldMetas.put(BUTTON_TEXT, m);
        
        defaultPaymentConfigMeta = new PaymentConfigMeta();
        defaultPaymentConfigMeta.setPayCode("Paypal");
        defaultPaymentConfigMeta.setPayName("Paypal");
        defaultPaymentConfigMeta.setPayFee("0");
        defaultPaymentConfigMeta.setIsOnline(true);
        defaultPaymentConfigMeta.setIsCod(false);
        defaultPaymentConfigMeta.setPayDesc("PayPal 是在线付款解决方案的全球领导者，在全世界有超过七千一百六十万个帐户用户。PayPal 可在 56 个市场以 7 种货币（加元、欧元、英镑、美元、日元、澳元、港元）使用。（网址：http://www.paypal.com）");
        defaultPaymentConfigMeta.setPayConfig(getDefaultConfig());
        defaultPaymentConfigMeta.setKeyName("_pay5");
    }
    public PaymentConfigMeta getDefaultConfigMeta() {
    	return defaultPaymentConfigMeta;
    }
    public Paypal() {
        super();
    }
    
    public String getCode(OrderInfo order, Payment payment , List<OrderGoods> orderGoods) { 
        PaymentConfigMeta configMeta = deserializeConfig(payment.getPayConfig());
        Map<String, String> values = configMeta.getFieldValues();
        
        String dataOrderId = order.getPkId();
        Double dataAmount = order.getGoodsAmount();
        String url = "";
        String dataPayAccount = values.get(ACCOUNT);
        String payCurrencyCode = values.get(CURRENCY);
        String dataNotifyUrl = "";
      
        StringBuffer buf = new StringBuffer();
        buf.append("<br /><form style='text-align:center;' action='https://www.paypal.com/cgi-bin/webscr' method='post'>");
        buf.append("<input type='hidden' name='cmd' value='_xclick'>");
        buf.append("<input type='hidden' name='business' value='").append(dataPayAccount).append("'>");
        buf.append("<input type='hidden' name='return' value='").append(url).append("'>");
        buf.append("<input type='hidden' name='amount' value='").append(dataAmount).append("'>");
        buf.append("<input type='hidden' name='invoice' value='").append(dataOrderId).append("'>");
        buf.append("<input type='hidden' name='charset' value='").append("UTF-8").append("'>");
        buf.append("<input type='hidden' name='no_shipping' value='1'>");
        buf.append("<input type='hidden' name='no_note' value=''>");
        buf.append("<input type='hidden' name='currency_code' value='").append(payCurrencyCode).append("'>");
        buf.append("<input type='hidden' name='notify_url' value='").append(dataNotifyUrl).append("'>");
        buf.append("<input type='hidden' name='item_name' value='").append(order.getOrderSn()).append("'>");
        buf.append("<input type='submit' value='").append(values.get(BUTTON_TEXT)).append("'>");
        return buf.toString();
        
    }
    

    private static String getDefaultConfig() {
        return serialize(defaultConfigValues);
    }



}
