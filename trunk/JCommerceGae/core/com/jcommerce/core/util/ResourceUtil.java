package com.jcommerce.core.util;

import java.util.Locale;
import java.util.ResourceBundle;

public class ResourceUtil {
	
	public static final String BUNDLE_SHOPCONFIG = "shopConfig";
	
	public static ResourceBundle getShopConfigResource(Locale locale) {
		return ResourceBundle.getBundle(BUNDLE_SHOPCONFIG, locale);
	}
}
