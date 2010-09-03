package com.jcommerce.core.test;

import java.io.File;

import junit.framework.TestCase;

import com.google.appengine.api.datastore.dev.LocalDatastoreService;
import com.google.appengine.tools.development.testing.LocalDatastoreServiceTestConfig;
import com.google.appengine.tools.development.testing.LocalServiceTestHelper;
//import com.google.apphosting.api.ApiProxy;

public class BaseDataStoreTestCase extends TestCase {

//    private final LocalServiceTestHelper helper =
//        new LocalServiceTestHelper(new LocalDatastoreServiceTestConfig());
	
    private LocalServiceTestHelper helper;
    
    protected void setUp() throws Exception {
    	System.out.println("BaseDataStoreTestCase setup");
//        ApiProxy.setEnvironmentForCurrentThread(new TestEnvironment());
//        String path=getDbStorePath();
//        System.out.println("initializing datastore with path: "+path);
//        ApiProxy.setDelegate(new ApiProxyLocalImpl(new File(path)){});
//        
//        if(needCleanOnStartup()) {
//        	ApiProxyLocalImpl proxy = (ApiProxyLocalImpl) ApiProxy.getDelegate();
//        	proxy.setProperty(LocalDatastoreService.NO_STORAGE_PROPERTY, Boolean.TRUE.toString());
//        	clearDS();
//        }
    	
    	LocalDatastoreServiceTestConfig config = new LocalDatastoreServiceTestConfig();
    	config.setNoStorage(false);
    	config.setBackingStoreLocation(getDbStorePath()+"/WEB-INF/appengine-generated/local_db.bin");
    	helper = new LocalServiceTestHelper(config);
    	helper.setEnvAppId("gcloudshop");
    	helper.setEnvVersionId("6");
    	helper.setEnvAuthDomain("gmail.com");
    	helper.setEnvRequestNamespace("");
    	helper.setUp();
    	
    	System.out.println("end of BaseDataStoreTestCase setup");
        

    }
    protected void clearDS() {
		System.out.println("cleanning datastore...");
//		ApiProxyLocalImpl proxy = (ApiProxyLocalImpl) ApiProxy.getDelegate();
//		LocalDatastoreService datastoreService = (LocalDatastoreService) proxy.getService("datastore_v3");
//		datastoreService.clearProfiles();
    }
    
    protected String getDbStorePath() {
    	return "D:/logs";
    }
    protected boolean needCleanOnStartup() {
    	return false;
    }
    
//    protected boolean needCleanOnStartup() {
//    	return true;
//    }
    
    @Override
    public void tearDown() throws Exception {
    	System.out.println("BaseDataStoreTestCase tearDown");
    	if(needCleanOnStartup()) {

    	}
    	
    	helper.tearDown();
    	
        // not strictly necessary to null these out but there's no harm either
//        ApiProxy.setDelegate(null);
//        ApiProxy.setEnvironmentForCurrentThread(null);
    }
}
