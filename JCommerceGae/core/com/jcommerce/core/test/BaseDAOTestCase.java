package com.jcommerce.core.test;

import javax.jdo.PersistenceManager;
import javax.jdo.PersistenceManagerFactory;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;
import org.springframework.orm.jdo.PersistenceManagerFactoryUtils;
import org.springframework.orm.jdo.PersistenceManagerHolder;
import org.springframework.transaction.support.TransactionSynchronizationManager;

import com.jcommerce.core.dao.impl.PMF;
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
    boolean participate = false;
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

        
        // below are copied from spring OpenPersistenceManagerInViewFilter
        // this is to ensure the openPmInView behavior for DS Managers is the same even in test cases 
		PersistenceManagerFactory pmf = PMF.get();
		if (TransactionSynchronizationManager.hasResource(pmf)) {
			// Do not modify the PersistenceManager: just set the participate flag.
			participate = true;
		}
		else {
			System.out.println("Opening JDO PersistenceManager in BaseDAOTestCase");
			PersistenceManager pm = PersistenceManagerFactoryUtils.getPersistenceManager(pmf, true);
			TransactionSynchronizationManager.bindResource(pmf, new PersistenceManagerHolder(pm));
		}
        
    }
    
    @Override
    public void tearDown() throws Exception {
    	System.out.println("BaseDAOTestCase tearDown");
        super.tearDown();
        
        // below are copied from spring OpenPersistenceManagerInViewFilter
        // this is to ensure the openPmInView behavior for DS Managers is the same even in test cases 
		if (!participate) {
			PersistenceManagerHolder pmHolder = (PersistenceManagerHolder)
					TransactionSynchronizationManager.unbindResource(PMF.get());
			System.out.println("Closing JDO PersistenceManager in BaseDAOTestCase");
			PersistenceManagerFactoryUtils.releasePersistenceManager(pmHolder.getPersistenceManager(), PMF.get());
		}
    }
    
    
	public IDefaultManager getDefaultManager() {
		return (IDefaultManager)ctx.getBean("DefaultManager");
	}
	public CustomizedManager getCustomizedManager() {
		return (CustomizedManager)ctx.getBean("CustomizedManager");
	}
}
