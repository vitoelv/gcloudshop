package com.jcommerce.web.util;

public class WebUtils {
	public static Long tryGetLongId(String id) {
		Long res = null;
		try {
			res = Long.valueOf(id);
		} catch (Exception ex) {
		}
		return res;
	}
}
