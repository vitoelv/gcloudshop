package com.jcommerce.web.to;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.jcommerce.core.util.Utf8ResourceBundle;

public class Lang extends BaseWrapper {
	
	public static Lang getInstance() {
		if(instance==null) {
			instance = new Lang();
		}
		return instance;
	}
	
	private static Lang instance;
	ResourceBundle rb;
	
	@Override
	public Object get(String key) {
		
		Object val = null;
		try {
			val = rb.getString(key);
		} catch (Exception e) {
		}
		if(val == null) {
			val = super.get(key);
		}
		return val;
	}
	
	
	private Lang() {
		
		// TODO add support of english
		rb = Utf8ResourceBundle.getBundle("com.jcommerce.web.resource.lang", Locale.CHINESE);
		Enumeration keys = rb.getKeys();
		String regex = "([^\\[]*)(?:\\[([^\\]]+)\\])?";
		Pattern p = Pattern.compile(regex);
		while(keys.hasMoreElements()) {
			String k1=null, k2=null;
			String key = (String)keys.nextElement();
			String val = rb.getString(key);
			Matcher m = p.matcher(key);
			if(m.find()) {
				k1 = m.group(1);
				k2 = m.group(2);
			}
			if(k2==null) {
				values.put(k1, val);
			} else {
				Integer k2number = Integer.MIN_VALUE;
				try {
					k2number = Integer.valueOf(k2);
				} catch (Exception ex) {
				}
				
				if(k2number!=Integer.MIN_VALUE) {
					continue;
					// we skip those index is number, they have to be handled specially
					// for index could be negative in PHP
				}
				
				Map<String, String> map = (Map)values.get(k1);
				if(map==null) {
					map = new HashMap<String, String>();
					values.put(k1, map);
				}
				map.put(k2, val);
			}
//			System.out.println("k1="+k1+", k2="+k2+", val="+val);
		}
		
		// TODO have to handle those PHP array style properties... especially with negative index 
        values.put("goodsJs", new String[0]);
        values.put("flowJs", new String[0]);
	}
	

}
