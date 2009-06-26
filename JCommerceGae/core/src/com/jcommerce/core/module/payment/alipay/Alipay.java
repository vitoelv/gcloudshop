/**
* Author: Bob Chen
*/

package com.jcommerce.core.module.payment.alipay;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;

import com.jcommerce.core.model.Order;
import com.jcommerce.core.model.Payment;
import com.jcommerce.core.module.payment.IPaymentModule;

public class Alipay implements IPaymentModule {
//    ResourceBundle labels = ResourceBundle.getBundle("alipay", Locale.getDefault());

    public Alipay() {
        System.out.println(""+this);
    }
    
    public String getAuthor() {
        return null;
    }

    public String getCode() {
        return "alipay";
    }

    public String getDescription() {
//        return labels.getString("alipay_desc");
        return "desc";
    }

    public String getVersion() {
        return "1.0.3r";
    }

    public URL getWebSite() {
        try {
            return new URL("http://www.alipay.com");
        } catch (MalformedURLException e) {
            return null;
        }
    }

    public boolean isCashOnDeliverySupported() {
        return false;
    }

    public boolean isOnlinePaymentSupported() {
        return true;
    }

    public String getPaymentCode(Order order, Payment payment) {
        return null;
    }

    public boolean isSuccess() {
        return false;
    }
    
    public String toString() {
        return getCode()+getVersion();
    }

    public Map<String, Object> getConfig() {
        return null;
    }
}
