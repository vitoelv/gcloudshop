package com.jcommerce.gwt.client;

import java.util.List;

import com.extjs.gxt.ui.client.data.PagingLoadConfig;
import com.extjs.gxt.ui.client.data.PagingLoadResult;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.jcommerce.gwt.client.form.BeanObject;
import com.jcommerce.gwt.client.service.Criteria;


public interface CustomizedServiceAsync {
    public void getGoodsTypeListWithAttrCount(String modelName, Criteria criteria, 
            List<String> wantedFields, PagingLoadConfig config, AsyncCallback<PagingLoadResult<BeanObject>> callback);
    

}
