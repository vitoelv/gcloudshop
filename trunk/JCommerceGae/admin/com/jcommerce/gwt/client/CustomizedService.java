package com.jcommerce.gwt.client;

import java.util.List;

import com.extjs.gxt.ui.client.data.PagingLoadConfig;
import com.extjs.gxt.ui.client.data.PagingLoadResult;
import com.google.gwt.user.client.rpc.RemoteService;
import com.jcommerce.gwt.client.form.BeanObject;
import com.jcommerce.gwt.client.service.Criteria;

public interface CustomizedService extends RemoteService {
    
    public PagingLoadResult<BeanObject> getGoodsTypeListWithAttrCount(String modelName, 
            Criteria criteria, List<String> wantedFields, PagingLoadConfig config);
    
    public String newOrder(BeanObject obj);
}
