package com.jcommerce.gwt.server.util;

import java.text.SimpleDateFormat;
import java.util.Date;

import com.jcommerce.gwt.client.util.GWTFormatUtils;

public class ServerFormatUtil {
	// used by Server
	public static Date parseShortDate(String sDate) {
		Date res = null;
		try {
			res = new SimpleDateFormat(GWTFormatUtils.SHORT_DATE_FORMAT).parse(sDate);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return res;
	}
	public static String formatShortDate(Date date) {
		return new SimpleDateFormat(GWTFormatUtils.SHORT_DATE_FORMAT).format(date);
	}
}
