package com.jcommerce.gwt.client.panels.system;

import com.jcommerce.gwt.client.ModelNames;
import com.jcommerce.gwt.client.form.BeanObject;
import com.jcommerce.gwt.client.model.IShipping;
import com.jcommerce.gwt.client.model.IShippingConfigMeta;

public class ShippingConfigMetaForm extends BeanObject implements IShipping, IShippingConfigMeta{
	public ShippingConfigMetaForm() {
		super(ModelNames.SHIPPING_META);
	}
	public void setPkId(String pkId) {
		set(PK_ID, pkId);
	}
	public String getPkId() {
		return get(PK_ID);
	}
	public void setShippingCode(String shippingCode) {
		set(SHIPPING_CODE, shippingCode);
	}
	public String getShippingCode() {
		return get(SHIPPING_CODE);
	}
	public void setShippingName(String shippingName) {
		set(SHIPPING_NAME, shippingName);
	}
	public String getShippingName() {
		return get(SHIPPING_NAME);
	}
	public void setShippingDesc(String shippingDesc) {
		set(SHIPPING_DESC, shippingDesc);
	}
	public String getShippingDesc() {
		return get(SHIPPING_DESC);
	}
	public void setSupportCod(boolean supportCod) {
		set(SUPPORT_COD, supportCod);
	}
	public boolean getSupportCod() {
		return (Boolean)get(SUPPORT_COD);
	}
	public void setAuthor(String author) {
		set(AUTHOR, author);
	}
	public String getAuthor() {
		return get(AUTHOR);
	}
	public void setWebsite(String website) {
		set(WEBSITE, website);
	}
	public String getWebsite() {
		return get(WEBSITE);
	}
	public void setVersion(String version) {
		set(VERSION, version);
	}
	public String getVersion() {
		return get(VERSION);
	}
	public void setInstall(boolean install) {
		set(INSTALL, install);
	}
	public boolean getInstall() {
		return (Boolean)get(INSTALL);
	}
	public void setShippingPrint(String shippingPrint) {
		set(SHIPPING_PRINT, shippingPrint);
	}
	public String getShippingPrint() {
		return get(SHIPPING_PRINT);
	}
}
