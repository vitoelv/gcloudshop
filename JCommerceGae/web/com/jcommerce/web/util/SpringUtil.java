package com.jcommerce.web.util;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import com.jcommerce.core.service.IDefaultManager;
import com.jcommerce.core.service.config.IShopConfigManager;

public class SpringUtil implements ApplicationContextAware {
	
    private static ApplicationContext applicationContext;
    public void setApplicationContext(ApplicationContext arg0)
        throws BeansException {
        SpringUtil.applicationContext = arg0;
    }
    public static Object getBean(String name){
        return applicationContext.getBean(name);
    }
    
    public static IShopConfigManager getShopConfigManager() {
		return (IShopConfigManager)getBean("ShopConfigManager");
	}
    public static IDefaultManager getDefaultManager() {
		return (IDefaultManager)getBean("DefaultManager");
	}
}
