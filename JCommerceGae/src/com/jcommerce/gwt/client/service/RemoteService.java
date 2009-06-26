/**
* Author: Bob Chen
*/

package com.jcommerce.gwt.client.service;

import com.extjs.gxt.ui.client.Registry;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.ServiceDefTarget;
import com.jcommerce.gwt.client.CustomizedService;
import com.jcommerce.gwt.client.CustomizedServiceAsync;
import com.jcommerce.gwt.client.IShopService;
import com.jcommerce.gwt.client.IShopServiceAsync;

public class RemoteService {
    public final static String CRUDSERVICE = "CURDservice";
    public final static String CUSTOMIZEDSERVICE = "Customizedservice";
    
    public static void init() {
        IShopServiceAsync service = (IShopServiceAsync) GWT.create(IShopService.class);
        String moduleRelativeURL = GWT.getModuleBaseURL() + "ishopService.do";
        ((ServiceDefTarget)service).setServiceEntryPoint(moduleRelativeURL);
        Registry.register(CRUDSERVICE, service); 

        CustomizedServiceAsync customizedService = (CustomizedServiceAsync) GWT.create(CustomizedService.class);
        moduleRelativeURL = GWT.getModuleBaseURL() + "customizedService.do";
        ((ServiceDefTarget)customizedService).setServiceEntryPoint(moduleRelativeURL);
        Registry.register(CUSTOMIZEDSERVICE, customizedService); 
    }
    
    protected IShopServiceAsync getService() {
        return (IShopServiceAsync)Registry.get(CRUDSERVICE);
    }
    
    public static CustomizedServiceAsync getCustomizedService() {
        CustomizedServiceAsync service = (CustomizedServiceAsync)Registry.get(CUSTOMIZEDSERVICE);
        if(service==null) {
            init();
            service = (CustomizedServiceAsync)Registry.get(CUSTOMIZEDSERVICE);
        }
        return service;
        
    }
}
