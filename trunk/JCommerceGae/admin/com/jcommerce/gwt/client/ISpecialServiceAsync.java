package com.jcommerce.gwt.client;

import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.SortedMap;

import com.extjs.gxt.ui.client.data.ListLoadConfig;
import com.extjs.gxt.ui.client.data.ListLoadResult;
import com.extjs.gxt.ui.client.data.PagingLoadConfig;
import com.extjs.gxt.ui.client.data.PagingLoadResult;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.jcommerce.gwt.client.form.BeanObject;
import com.jcommerce.gwt.client.panels.system.PaymentConfigMetaForm;
import com.jcommerce.gwt.client.panels.system.ShippingAreaMetaForm;
import com.jcommerce.gwt.client.panels.system.ShippingConfigMetaForm;
import com.jcommerce.gwt.client.service.Criteria;


public interface ISpecialServiceAsync {
    public void getGoodsTypeListWithAttrCount(String modelName, Criteria criteria, 
            List<String> wantedFields, PagingLoadConfig config, AsyncCallback<PagingLoadResult<BeanObject>> callback);
    
    public void newOrder(BeanObject obj, AsyncCallback<String> callback);
    
    
    public void getPaymentMetaList(ListLoadConfig config, AsyncCallback<ListLoadResult<BeanObject>> callback);
    public void getMyPaymentMetaList(ListLoadConfig config, AsyncCallback<ListLoadResult> callback);
    public void getPaymentConfigMeta(String paymentId, AsyncCallback<PaymentConfigMetaForm> callback);
    
    public void installPayment(String paymentCode, AsyncCallback<Boolean> callback);
    public void uninstallPayment(String paymentId, AsyncCallback<Boolean> callback);
    public void savePayment(Map<String, Object> props, AsyncCallback<Boolean> callback);
    
    public void getCombinedShippingMetaList(ListLoadConfig config, AsyncCallback<ListLoadResult<ShippingConfigMetaForm>> callback);
    public void getShippingConfigMeta(String shippingId, AsyncCallback<ShippingConfigMetaForm> callback);
    public void getShippingAreaMeta(String shippingAreaId, String shippingId, AsyncCallback<ShippingAreaMetaForm> callback);
    
    public void getAreaRegionListWithName(String shippingAreaId, AsyncCallback<ListLoadResult<BeanObject>> callback);
    
    public void installShipping(String shippingCode, AsyncCallback<Boolean> callback);
    public void uninstallShipping(String shippingId, AsyncCallback<Boolean> callback);
    public void saveShipping(ShippingConfigMetaForm props, AsyncCallback<Boolean> callback);
    
    public void getShippingAreaWithRegionNames(String shippingId, ListLoadConfig pgc, AsyncCallback<ListLoadResult<BeanObject>> callback);
    public void saveShippingArea(BeanObject shippingArea, AsyncCallback<Boolean> callback);
    
    public void getCombinedShopConfigMetaMap(String locale,AsyncCallback<SortedMap<Integer, List<BeanObject>>> callback);
    public void saveShopConfig(Map<String, BeanObject> formData, AsyncCallback<Boolean> callback);

    public void getAdminUserInfo(AsyncCallback<Map<String,String>> callback);
    public void getLocale(AsyncCallback<String> callback);
}
