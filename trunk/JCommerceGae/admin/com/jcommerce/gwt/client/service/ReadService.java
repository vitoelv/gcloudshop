/**
* Author: Bob Chen
*/

package com.jcommerce.gwt.client.service;

import java.util.List;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.jcommerce.gwt.client.IDefaultServiceAsync;
import com.jcommerce.gwt.client.form.BeanObject;

public class ReadService extends RemoteService {
    public void getBean(String model, String id, final Listener listener) {
        if (model == null) {
            throw new RuntimeException("model = null");
        }
        
        if (id == null || id.trim().length() == 0) {
            throw new RuntimeException("id = null");
        }
        
        final IDefaultServiceAsync service = getDefaultService();
        service.getBean(model, id, new AsyncCallback<BeanObject>() {
            public synchronized void onSuccess(BeanObject result) {
                if (listener != null) {
                    listener.onSuccess(result);
                }
            }

            public synchronized void onFailure(Throwable caught) {
                System.out.println("getList onFailure("+caught);
                
                if (listener != null) {
                    listener.onFailure(caught);
                }
            }
        });
    }
    
    public void getBeans(String model, String[] ids, final Listener listener) {
        if (model == null) {
            throw new RuntimeException("model = null");
        }
        
        if (ids == null || ids.length == 0) {
            throw new RuntimeException("ids = null");
        }
        
        for (String id : ids) {
            if (id == null || id.trim().length() == 0) {
                throw new RuntimeException("id = null");
            }
        }
        
        final IDefaultServiceAsync service = getDefaultService();
        service.getBeans(model, ids, new AsyncCallback<List<BeanObject>>() {
            public synchronized void onSuccess(List<BeanObject> result) {
                if (listener != null) {
                    listener.onSuccess(result);
                    if (result.size() == 1) {
                        listener.onSuccess(result.get(0));
                    }
                }
            }

            public synchronized void onFailure(Throwable caught) {
                System.out.println("getList onFailure("+caught);
                
                if (listener != null) {
                    listener.onFailure(caught);
                }
            }
        });        
    }
    
    public static abstract class Listener {
        public void onSuccess(List<BeanObject> beans) {
        }
        
        public void onSuccess(BeanObject bean) {
        }
        
        public void onFailure(Throwable caught) {
        }
    }
}
