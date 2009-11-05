package com.jcommerce.gwt.client.panels.system;

import java.io.Serializable;
import java.util.Map;

import com.jcommerce.gwt.client.form.BeanObject;
import com.jcommerce.gwt.client.model.IPayment;
import com.jcommerce.gwt.client.model.IPaymentConfigMeta;

public class PaymentConfigMetaForm extends BeanObject implements Serializable, IPayment, IPaymentConfigMeta{


    
    
//    private int id;
//    private String name;
//    private String code;
//    private String description;
//    private String payFee;
//    private boolean isCod;
//    private boolean isOnline;
//    private boolean isEnabled;
    
    
//    private Map<String, PaymentConfigFieldMetaForm> fieldMetas;
//    private Map<String, String> fieldValues;
    
    public PaymentConfigMetaForm() {
        
    }
    
    public String toString() {
        StringBuffer buf = new StringBuffer();
        buf.append("pkid: "+getPkId()).append("\n");
        buf.append("name: "+getPayName()).append("\n");
        buf.append("code: "+getCode()).append("\n");
        buf.append("description: "+getDescription()).append("\n");
        buf.append("payFee: "+getPayFee()).append("\n");
        buf.append("isCod: "+isCod()).append("\n");
        buf.append("isOnline: "+isOnline()).append("\n");
        buf.append("isEnabled: "+isEnabled()).append("\n");
        
        buf.append("fieldValues: \n");
        if(getFieldValues()==null) {
            buf.append("null\n");
        }
        else {
            for(String key:getFieldValues().keySet()) {
                buf.append("key: ").append(key).append(", value: ").append(getFieldValues().get(key)).append("\n");
            }
        }
        
        buf.append("fieldMetas: \n");
        if(getFieldMetas() == null) {
            buf.append("null");
        }
        else {
            buf.append("{\n");
            for(String key:getFieldMetas().keySet()) {
                PaymentConfigFieldMetaForm fieldMeta = getFieldMetas().get(key);
                buf.append("key=").append(key).append("\n");
                buf.append("value={").append(fieldMeta.toString()).append("}");
            }
            buf.append("}\n");
            
        }
        
        return buf.toString();
    }



    public Map<String, PaymentConfigFieldMetaForm> getFieldMetas() {
        return (Map<String, PaymentConfigFieldMetaForm>)get(FIELDMETAS);
    }

    public void setFieldMetas(Map<String, PaymentConfigFieldMetaForm> fieldMetas) {
        set(FIELDMETAS, fieldMetas);
    }

    public Map<String, String> getFieldValues() {
        return (Map<String, String>)get(FIELDVALUES);
    }

    public void setFieldValues(Map<String, String> fieldValues) {
        set(FIELDVALUES, fieldValues);
    }

    
//    public String getCode() {
//        return code;
//    }
//
//    public void setCode(String code) {
//        this.code = code;
//    }
//
//    public String getDescription() {
//        return description;
//    }
//
//    public void setDescription(String description) {
//        this.description = description;
//    }
//    public int getPkId() {
//        return id;
//    }
//
//    public void setId(int id) {
//        this.id = id;
//    }
//
//    public boolean isCod() {
//        return isCod;
//    }
//
//    public void setCod(boolean isCod) {
//        this.isCod = isCod;
//    }
//
//    public boolean isEnabled() {
//        return isEnabled;
//    }
//
//    public void setEnabled(boolean isEnabled) {
//        this.isEnabled = isEnabled;
//    }
//
//    public boolean isOnline() {
//        return isOnline;
//    }
//
//    public void setOnline(boolean isOnline) {
//        this.isOnline = isOnline;
//    }
//
//    public String getName() {
//        return name;
//    }
//
//    public void setName(String name) {
//        this.name = name;
//    }
//
//    public String getPayFee() {
//        return payFee;
//    }
//
//    public void setPayFee(String payFee) {
//        this.payFee = payFee;
//    }
    

    
    
    public String getCode() {
        return getString(PAY_CODE);
    }

    public void setCode(String code) {
        set(PAY_CODE, code);
    }

    public String getDescription() {
        return getString(PAY_DESC);
    }

    public void setDescription(String description) {
        set(PAY_DESC, description);
    }

    public String getPkId() {
        return getString(PK_ID);
    }

    public void setId(String id) {
        set(PK_ID, id);
    }

    public boolean isCod() {
        return get(IS_COD)==null ? false : (Boolean)get(IS_COD);
    }

    public void setCod(boolean isCod) {
        set(IS_COD, isCod);
    }

    public boolean isEnabled() {
        return get(ENABLED) == null? false : (Boolean)get(ENABLED);
    }

    public void setEnabled(boolean isEnabled) {
        set(ENABLED, isEnabled);
    }

    public boolean isOnline() {
        return get(IS_ONLINE) == null? false : (Boolean)get(IS_ONLINE);
    }

    public void setOnline(boolean isOnline) {
        set(IS_ONLINE, isOnline);
    }

    public String getPayName() {
        return get(PAY_NAME);
    }

    public void setPayName(String name) {
        set(PAY_NAME, name);
    }

    public String getPayFee() {
        return get(PAY_FEE);
    }

    public void setPayFee(String payFee) {
        set(PAY_FEE, payFee);
    }


    
    
    
}
