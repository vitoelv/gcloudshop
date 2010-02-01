package com.jcommerce.core.service.payment.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.jcommerce.core.model.OrderGoods;
import com.jcommerce.core.model.OrderInfo;
import com.jcommerce.core.model.Payment;
import com.jcommerce.core.service.payment.IPaymentMetaPlugin;
import com.jcommerce.core.service.payment.PaymentConfigFieldMeta;
import com.jcommerce.core.service.payment.PaymentConfigMeta;

public class GoogleCheckout extends BasePaymentMetaPlugin implements IPaymentMetaPlugin {
	
	private static final PaymentConfigMeta defaultPaymentConfigMeta;
    private static final Map<String, String> defaultConfigValues;
    private static final Map<String, PaymentConfigFieldMeta> fieldMetas;
    public static final String KEY = "alipay_key";
    
    // agent code for gcshop
    public static final String MERCHANT_ID="726838326316485";
    
    @Override
    protected Map<String, PaymentConfigFieldMeta> getFieldMetas() {
    	return fieldMetas;
    }
    static {
        defaultConfigValues = new HashMap<String, String>();
        defaultConfigValues.put(KEY, MERCHANT_ID);
        
        fieldMetas = new HashMap<String, PaymentConfigFieldMeta>();
        
        PaymentConfigFieldMeta m = null;
        Map<String, String> options = null;
        
        m = new PaymentConfigFieldMeta();
        m.setLable("Checkout账号");
        m.setOptions(null);
        m.setTip(null);
        fieldMetas.put(KEY, m);
        
        defaultPaymentConfigMeta = new PaymentConfigMeta();
        defaultPaymentConfigMeta.setPayCode("checkout");
        defaultPaymentConfigMeta.setPayName("Google Checkout");
        defaultPaymentConfigMeta.setPayFee("2.2%");
        defaultPaymentConfigMeta.setIsOnline(true);
        defaultPaymentConfigMeta.setIsCod(false);
        defaultPaymentConfigMeta.setPayDesc("Google 好好找， Google Checkout 輕鬆購！");
        defaultPaymentConfigMeta.setPayConfig(getDefaultConfig());
    }
    public PaymentConfigMeta getDefaultConfigMeta() {
    	return defaultPaymentConfigMeta;
    }
    public GoogleCheckout() {
        super();
    }

//    public String getCode() {
//        return "AlyPay";
//    }
//
//    public String getName() {
//        return "支付宝";
//    }
//    // 支付手续费
//    public String getPayFee() {
//        return "10";
//    }
//    
//    // 在线支付
//    public Boolean isOnline() {
//        return true;
//    }
//    // 货到付款
//    public Boolean isCod() {
//        return false;
//    }
//
//    public String getDescription() {
//        return "支付宝，是支付宝公司针对网上交易而特别推出的安全付款服务.<br/><a href=\"https://www.alipay.com/himalayas/market.htm?type=from_agent_contract&id=C4335319945672464113\" target=\"_blank\"><font color=\"red\">点此申请免费签约接口</font></a><br/><a href=\"https://www.alipay.com/himalayas/market.htm?type=from_agent_contract&id=C4335319945674798119\" target=\"_blank\"><font color=\"red\">点此申请预付费签约接口(600包4.2万、1800包18万交易额度)</font></a>";
//    }

    /*
     *  (non-Javadoc)
_input_charset=utf-8
&agent=C4335319945672464113
&logistics_fee=0
&logistics_payment=BUYER_PAY_AFTER_RECEIVE
&logistics_type=EXPRESS
&notify_url=http%3A%2F%2Fqbd.maifou.net%2Frespond.php%3Fcode%3Dalipay
&out_trade_no=200812193564311
&partner=2088002230861529
&payment_type=1
&price=352.80
&quantity=1
&return_url=http%3A%2F%2Fqbd.maifou.net%2Frespond.php%3Fcode%3Dalipay
&seller_email=hyj_0105%40163.com
&service=create_direct_pay_by_user
&subject=2008121935643
&sign=86056fb8587d94947d89abb4d0c5a29b
&sign_type=MD5  
     * @see com.jcommerce.core.service.payment.IPaymentMetaPlugin#getCode(com.jcommerce.core.model.Order, com.jcommerce.core.model.Payment)
     */
    public String getCode(OrderInfo order, Payment payment , List<OrderGoods> orderGoods) { 
        PaymentConfigMeta configMeta = deserializeConfig(payment.getPayConfig());
        Map<String, String> values = configMeta.getFieldValues();
        
        
        
//        String service = "";
//        boolean isOrder = true;
//        if (isOrder) {
//            /* 检查订单是否全部为虚拟商品 */
//            boolean allvirtual = false;
//            if (!allvirtual) {
//                /* 订单中存在实体商品 */
//                service = "1".equals(values.get(REAL_METHOD)) ? "create_direct_pay_by_user"
//                        : "trade_create_by_buyer";
//            } else {
//                /* 订单中全部为虚拟商品 */
//                service = "1".equals(values.get(VIRTUAL_METHOD)) ? "create_direct_pay_by_user"
//                        : "create_digital_goods_trade_p";
//            }
//        } else {
//            /* 非订单方式，按照虚拟商品处理 */
//            service = "1".equals(values.get(VIRTUAL_METHOD)) ? "create_direct_pay_by_user"
//                    : "create_digital_goods_trade_p";
//        }
        StringBuffer buf = new StringBuffer();
        buf.append("<form method=\"POST\" action=\"https://checkout.google.com/api/checkout/v2/checkoutForm/Merchant/"+MERCHANT_ID+"\" accept-charset=\"utf-8\">");
        int i = 1;
        for(OrderGoods goods : orderGoods){
        	
        	buf.append("<input name=\"item_name_" + i + "\" type=\"hidden\" value=\"" + goods.getGoodsName() + "\"/>");
        	buf.append("<input name=\"item_description_" + i + "\" type=\"hidden\" value=\"" + goods.getGoodsName() + "\"/>");
        	buf.append("<input name=\"item_quantity_" + i + "\" type=\"hidden\" value=\"" + goods.getGoodsNumber() + "\"/>");
        	buf.append("<input name=\"item_price_" + i + "\" type=\"hidden\" value=\"" + goods.getGoodsPrice() + "\"/>");
        	i++;
        }
//        buf.append("<input name=\"item_name_" + i + "\" type=\"hidden\" value=\"" + order.getShippingName() + "\"/>");
//    	buf.append("<input name=\"item_description_" + i + "\" type=\"hidden\" value=\"" + order.getShippingName() + "\"/>");
//    	buf.append("<input name=\"item_quantity_" + i + "\" type=\"hidden\" value=\"" + 1 + "\"/>");
//    	buf.append("<input name=\"item_price_" + i + "\" type=\"hidden\" value=\"" + order.getShippingFee() + "\"/>");
    	
        buf.append("<input name=\"_charset_\" type=\"hidden\" value=\"utf-8\"/>");
        buf.append("<input type=\"image\" name=\"Google Checkout\" alt=\"Fast checkout through Google\" src=\"http://checkout.google.com/buttons/checkout.gif?merchant_id=726838326316485&w=180&h=46&style=white&variant=text&loc=en_US\" height=\"46\" width=\"180\" />");
        buf.append("</form>");
        return buf.toString();
        
    }
    

    private static String getDefaultConfig() {
        return serialize(defaultConfigValues);
    }



}
