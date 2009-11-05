package com.jcommerce.web.util;

import com.jcommerce.core.test.dwt.DWTConverter;

public class WebFormatUtils {
	public static String priceFormat(Double price) {
		return "￥"+price+"元";
	}
	public static String priceFormat(String price) {
		return "￥"+price+"元";
	}
	public static String phpVarFromat(String var) {
		return new DWTConverter().replacePhpVars(var);
	}
}
