/**
* Author: Bob Chen
*/

package com.jcommerce.gwt.client.service;

import java.util.List;

import com.extjs.gxt.ui.client.data.BasePagingLoader;
import com.extjs.gxt.ui.client.data.PagingLoadConfig;
import com.extjs.gxt.ui.client.data.RpcProxy;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.jcommerce.gwt.client.IShopServiceAsync;

public class PagingListService extends RemoteService {
	
    public BasePagingLoader getLoader(final String model, final List<String> wantedFields) {
//        if (model == null) {
//            throw new RuntimeException("model = null");
//        }
//        
//        final IShopServiceAsync service = getService();
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
    
    public BasePagingLoader getLoader(final String model,  final Criteria criteria, final List<String> wantedFields) {
        if (model == null) {
            throw new RuntimeException("model = null");
        }
        
        final IShopServiceAsync service = getService();
        MyProxy proxy = new MyProxy() {
            public void load(Object loadConfig, AsyncCallback callback) {
                service.getPagingList(model, criteria, wantedFields, (PagingLoadConfig) loadConfig, callback);
            }
        };
        proxy.setCriteria(criteria);
        
        // loader
        BasePagingLoader loader = new BasePagingLoader(proxy);
        loader.setRemoteSort(true);

        return loader;
    }
    
    public BasePagingLoader getLoader(final String model) {
//        if (model == null) {
//            throw new RuntimeException("model = null");
//        }
//        
//        final IShopServiceAsync service = getService();
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
//        final IShopServiceAsync service = getService();
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
    
    public abstract class MyProxy extends RpcProxy {
    	Criteria criteria = null;
    	public void setCriteria(Criteria criteria) {
    		this.criteria = criteria;
    	}
    }
    
}
