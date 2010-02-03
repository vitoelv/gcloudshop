/**
* Author: Bob Chen
*/

package com.jcommerce.gwt.client.service;

import java.util.List;
import java.util.Map;

import com.extjs.gxt.ui.client.data.BasePagingLoader;
import com.extjs.gxt.ui.client.data.PagingLoadConfig;
import com.extjs.gxt.ui.client.data.PagingLoadResult;
import com.extjs.gxt.ui.client.data.RpcProxy;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.jcommerce.gwt.client.IDefaultServiceAsync;
import com.jcommerce.gwt.client.form.BeanObject;
import com.jcommerce.gwt.client.util.MyRpcProxy;

public class PagingListService extends RemoteService {
	
    public BasePagingLoader getLoader(final String model, final Map<String,List<String>> wantedFields) {
//        if (model == null) {
//            throw new RuntimeException("model = null");
//        }
//        
//        final IDefaultServiceAsync service = getService();
//        MyProxy proxy = new MyProxy() {
//            public void load(Object loadConfig, AsyncCallback callback) {
//                service.getPagingList(model, criteria, wantedFields, (PagingLoadConfig) loadConfig, callback);
//            }
//        };
//
//        // loader
//        BasePagingLoader loader = new BasePagingLoader(proxy);
//        loader.setRemoteSort(true);
//
//        return loader;
    	return getLoader(model, null, wantedFields);
    }
    
    public BasePagingLoader<PagingLoadResult<BeanObject>> getLoader(final String model,  final Criteria criteria, final Map<String,List<String>> wantedFields) {
        if (model == null) {
            throw new RuntimeException("model = null");
        }
        
        final IDefaultServiceAsync service = getDefaultService();
        MyRpcProxy<PagingLoadResult<BeanObject>> proxy = new MyRpcProxy<PagingLoadResult<BeanObject>>() {
            public void load(Object loadConfig, AsyncCallback<PagingLoadResult<BeanObject>> callback) {
                service.getPagingList(model, getCriteria(), wantedFields, (PagingLoadConfig) loadConfig, callback);
            }
        };
        proxy.setCriteria(criteria);
        
        // loader
        BasePagingLoader<PagingLoadResult<BeanObject>> loader = new BasePagingLoader<PagingLoadResult<BeanObject>>(proxy);
        loader.setRemoteSort(true);

        return loader;
    }
    
    public BasePagingLoader getLoader(final String model) {
//        if (model == null) {
//            throw new RuntimeException("model = null");
//        }
//        
//        final IDefaultServiceAsync service = getService();
//        MyProxy proxy = new MyProxy() {
//            public void load(Object loadConfig, AsyncCallback callback) {
//                service.getPagingList(model, criteria, (PagingLoadConfig) loadConfig, callback);
//            }
//        };
//
//        // loader
//        BasePagingLoader loader = new BasePagingLoader(proxy);
//        loader.setRemoteSort(true);
//
//        return loader;
    	return getLoader(model, null, null);
    }

    public BasePagingLoader getLoader(final String model, final Criteria criteria) {
//        if (model == null) {
//            throw new RuntimeException("model = null");
//        }
//        
//        final IDefaultServiceAsync service = getService();
//        MyProxy proxy = new MyProxy() {
//            public void load(Object loadConfig, AsyncCallback callback) {
//                service.getPagingList(model, criteria, (PagingLoadConfig) loadConfig, callback);
//            }
//        };
//        proxy.setCriteria(criteria);
//        
//        // loader
//        BasePagingLoader loader = new BasePagingLoader(proxy);
//        loader.setRemoteSort(true);
//
//        return loader;
    	return getLoader(model, criteria, null);
    }
    

    
}
