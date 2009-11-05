/**
* Author: Bob Chen
*/

package com.jcommerce.gwt.client.service;

import com.extjs.gxt.ui.client.Registry;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.ServiceDefTarget;
import com.jcommerce.gwt.client.ISpecialService;
import com.jcommerce.gwt.client.ISpecialServiceAsync;
import com.jcommerce.gwt.client.IDefaultService;
import com.jcommerce.gwt.client.IDefaultServiceAsync;

public class RemoteService {
    public final static String DEFAULTSERVICE = "DefaultService";
    public final static String SPECIALSERVICE = "SpecialService";
    
    public static void init() {
        IDefaultServiceAsync service = (IDefaultServiceAsync) GWT.create(IDefaultService.class);
        String moduleRelativeURL = "/admin/defaultService.do";
        ((ServiceDefTarget)service).setServiceEntryPoint(moduleRelativeURL);
        Registry.register(DEFAULTSERVICE, service); 

        ISpecialServiceAsync specialService = (ISpecialServiceAsync) GWT.create(ISpecialService.class);
//        moduleRelativeURL = GWT.getModuleBaseURL() + "customizedService.do";
        moduleRelativeURL = "/admin/specialService.do";
        ((ServiceDefTarget)specialService).setServiceEntryPoint(moduleRelativeURL);
        Registry.register(SPECIALSERVICE, specialService); 
    }
    
    public static IDefaultServiceAsync getDefaultService() {
    	IDefaultServiceAsync service = (IDefaultServiceAsync)Registry.get(DEFAULTSERVICE);
        if(service==null) {
            init();
            service = (IDefaultServiceAsync)Registry.get(DEFAULTSERVICE);
        }
        return service;
        
    }
    
    public static ISpecialServiceAsync getSpecialService() {
        ISpecialServiceAsync specialService = (ISpecialServiceAsync)Registry.get(SPECIALSERVICE);
        if(specialService==null) {
            init();
            specialService = (ISpecialServiceAsync)Registry.get(SPECIALSERVICE);
        }
        return specialService;
        
    }
}
