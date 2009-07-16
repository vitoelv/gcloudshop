package com.jcommerce.core.test;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;

import com.jcommerce.core.service.CustomizedManager;
import com.jcommerce.core.service.IDefaultManager;
import com.jcommerce.core.util.IConstants;

/**
 * Base class for DAO TestCases.
 * @author Matt Raible
 */
public class BaseDAOTestCase extends BaseDataStoreTestCase implements IConstants {
    protected static Log log = LogFactory.getLog(BaseDAOTestCase.class);
    protected ApplicationContext ctx = null;

    public BaseDAOTestCase() {
    }
    
    protected void setUp() throws Exception {
    	System.out.println("BaseDAOTestCase setup");
    	super.setUp();
    	try {
    	String HOME = "D:/JCommerce/JCommerceGae/war";
    	
        String[] paths = {HOME+"/WEB-INF/applicationContext.xml"};
        ctx = new FileSystemXmlApplicationContext(paths);
    	} catch (Exception e) {
    		e.printStackTrace();
    	}
        System.out.println("end of BaseDAOTestCase setup");

    }
    
    @Override
    public void tearDown() throws Exception {
    	System.out.println("BaseDAOTestCase tearDown");
        super.tearDown();
        
    }
    
    
	public IDefaultManager getDefaultManager() {
		return (IDefaultManager)ctx.getBean("DefaultManager");
	}
	public CustomizedManager getCustomizedManager() {
		return (CustomizedManager)ctx.getBean("CustomizedManager");
	}
}
