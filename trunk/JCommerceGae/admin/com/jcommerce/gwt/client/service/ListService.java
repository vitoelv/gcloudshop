/**
* Author: Bob Chen
*/

package com.jcommerce.gwt.client.service;

import java.util.List;
import java.util.Map;

import com.extjs.gxt.ui.client.data.BaseListLoader;
import com.extjs.gxt.ui.client.data.ListLoadResult;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.jcommerce.gwt.client.IDefaultServiceAsync;
import com.jcommerce.gwt.client.form.BeanObject;
import com.jcommerce.gwt.client.util.MyListLoader;
import com.jcommerce.gwt.client.util.MyRpcProxy;
import com.jcommerce.gwt.client.util.TreeListUtils;

public class ListService extends RemoteService {
    public void listBeans(String model, final Listener listener) {
       listBeans(model, null, listener);
    }
 
    
    public BaseListLoader getLoader(final String model) {
    	return getLoader(model, null,null);
    }
    public MyListLoader<ListLoadResult<BeanObject>> getLoader(final String model,  final Criteria criteria, final Map<String,List<String>> wantedFields) {
        if (model == null) {
            throw new RuntimeException("model = null");
        }
        
        final IDefaultServiceAsync service = getDefaultService();
        MyRpcProxy<List<BeanObject>> proxy = new MyRpcProxy<List<BeanObject>>() {
            public void load(Object loadConfig, AsyncCallback<List<BeanObject>> callback) {
                service.getList(model, getCriteria(), wantedFields, callback);
            }
        };
        proxy.setCriteria(criteria);
        
        // loader
        MyListLoader loader = new MyListLoader(proxy);
//        loader.setRemoteSort(true);

        return loader;
    }
    
    public void listBeans(final String model, Criteria criteria, final Listener listener) {
        if (model == null) {
            throw new RuntimeException("model = null");
        }
        
        final IDefaultServiceAsync service = getDefaultService();
        service.getList(model, criteria, new AsyncCallback<List<BeanObject>>() {
            public synchronized void onSuccess(List<BeanObject> result) {
                if (listener != null) {
                    listener.onSuccess(result);
                }
            }
            public synchronized void onFailure(Throwable caught) {
                System.out.println("ListService: getList onFailure(model="+model+", error="+caught);
                
                if (listener != null) {
                    listener.onFailure(caught);
                }
            }
        });        
    }
    
    public void treeListBeans(final String model, Criteria criteria,Map<String,List<String>>wantedFields, final Listener listener) {
        if (model == null) {
            throw new RuntimeException("model = null");
        }
        
        final IDefaultServiceAsync service = getDefaultService();
        service.getList(model, criteria,wantedFields, new AsyncCallback<List<BeanObject>>() {
            public synchronized void onSuccess(List<BeanObject> result) {
                if (listener != null) {
                	if(!result.isEmpty()){
                	    result = TreeListUtils.toTreeList(result);
                	}
                    listener.onSuccess(result);
                }
            }
            public synchronized void onFailure(Throwable caught) {
                System.out.println("ListService: getList onFailure(model="+model+", error="+caught);
                
                if (listener != null) {
                    listener.onFailure(caught);
                }
            }
        });        
    }
    
    public void listBeans(final String model, String fieldName, String value, final Listener listener) {
		Criteria criteria = new Criteria();
		Condition cond = new Condition();
		cond.setField(fieldName);
		cond.setOperator(Condition.EQUALS);
		cond.setValue(value);
		criteria.addCondition(cond);
		
		listBeans(model, criteria, listener);
		
    }
    
    public static abstract class Listener {
        public abstract void onSuccess(List<BeanObject> beans);
        
        public void onFailure(Throwable caught) {
        }
    }
}
