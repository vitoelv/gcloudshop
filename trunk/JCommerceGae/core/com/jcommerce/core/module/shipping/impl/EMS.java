/**
* Author: Bob Chen
*/

package com.jcommerce.core.module.shipping.impl;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;

import com.jcommerce.core.module.shipping.IShippingModule;

public class EMS implements IShippingModule {
    ResourceBundle labels = ResourceBundle.getBundle("ems", Locale.getDefault());
    
    public String getAuthor() {
        return null;
    }

    public String getCode() {
        return "ems";
    }

    public String getDescription() {
        return labels.getString("ems_express_desc");
    }

    public String getVersion() {
        return "1.0.0";
    }

    public URL getWebSite() {
        try {
            return new URL("http://www.ecshop.com");
        } catch (MalformedURLException e) {
            return null;
        }
    }

    public Map<String, Object> getConfig() {
        Map<String, Object> cfg = new HashMap<String, Object>();
        cfg.put("base_fee", 20);
        cfg.put("step_fee", 15);
        return cfg;
    }

    private float getBaseFee() {
        if (getConfig().get("base_fee") == null) {
            return 0;
        }
        
        return ((Number)getConfig().get("base_fee")).floatValue();
    }
    
    private float getStepFee() {
        if (getConfig().get("step_fee") == null) {
            return 0;
        }
        
        return ((Number)getConfig().get("step_fee")).floatValue();
    }
    
    private float getFreeMoney() {
        if (getConfig().get("free_money") == null) {
            return 0;
        }
        
        return ((Number)getConfig().get("free_money")).floatValue();
    }
    
    /**
     * 邮政快递包裹费用计算方式
     * ====================================================================================
     * 500g及500g以内                             20元
     * -------------------------------------------------------------------------------------
     * 续重每500克或其零数                        6元/9元/15元(按分区不同收费不同，具体分区方式，请寄件人拨打电话或到当地邮局营业窗口咨询，客服电话11185。)
     * -------------------------------------------------------------------------------------
     *
     */
    public float getShippingFee(float goodsWeight, float goodsTotalMoney) {
        float freeMoney = getFreeMoney();
        if (freeMoney > 0 && goodsTotalMoney >= freeMoney) {
            return 0;
        }
        
        float fee = getBaseFee();
        if (goodsWeight >= 0.5) {
            fee += (Math.ceil((goodsWeight - 0.5) / 0.5)) * getStepFee();
        }
        
        return fee;
    }

    public String getQueryForm(String invoice_sn) {
        String str = "<form style=\"margin:0px\" method=\"post\" "+
        "action=\"http://www.ems.com.cn/qcgzOutQueryAction.do\" name=\"queryForm_"+ invoice_sn + " target=\"_blank\">"+
        "<input type=\"hidden\" name=\"mailNum\" value=\""+ invoice_sn+"\" />"+
        "<a href=\"javascript:document.forms['queryForm_"+invoice_sn+"'].submit();\">"+invoice_sn+"</a>"+
        "<input type=\"hidden\" name=\"reqCode\" value=\"browseBASE\" />"+
        "<input type=\"hidden\" name=\"checknum\" value=\"0568792906411\" />"+
        "</form>";

        return str;
    }

}
