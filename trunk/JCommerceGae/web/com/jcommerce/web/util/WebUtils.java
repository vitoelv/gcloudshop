package com.jcommerce.web.util;

import org.datanucleus.util.StringUtils;

import com.tecnick.htmlutils.htmlentities.HTMLEntities;

public class WebUtils {
	public static Long tryGetLongId(String id) {
		Long res = null;
		try {
			res = Long.valueOf(id);
		} catch (Exception ex) {
		}
		return res;
	}
	
	public static String encodeHtml(String in) {
		if(StringUtils.isEmpty(in)) {
			return in;
		}
		else {
			return HTMLEntities.htmlentities(in);
		}
	}
}
