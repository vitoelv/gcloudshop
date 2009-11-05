package com.jcommerce.web.to;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
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
	
	public String getString(String key) {
		return (String)get(key);
	}
	
	
	private Lang() {
		
		// TODO add support of english
		rb = Utf8ResourceBundle.getBundle("com.jcommerce.web.resource.lang", Locale.CHINESE);
		Enumeration keys = rb.getKeys();
		String regex = "([^\\[]*)(?:\\[([^\\]]+)\\])?";
		Pattern p = Pattern.compile(regex);
		while(keys.hasMoreElements()) {
			String key = (String)keys.nextElement();
			String val = rb.getString(key);

			parse(key, val, p, values);
		}
		
		// TODO have to handle those PHP array style properties... especially with negative index 
//        values.put("goodsJs", new String[0]);
//        values.put("flowJs", new String[0]);
        
        // TODO needed by goodsList.ftl
//        values.put("compareJs", new String[0]);
        Map<String, String> display = new HashMap<String, String>();
        display.put("list", "list");
        display.put("grid", "grid");
        display.put("text", "text");
        values.put("display", display);
	}
	
	// make it static for unit testing
	public static void parse(String key, String val, Pattern p, Map<String, Object> values) {
		
		
		String k1=null, k2=null;
		Matcher m = p.matcher(key);
		if(m.find()) {
			k1 = m.group(1);
			k2 = m.group(2);
		}
//		System.out.println("key="+key+", val="+val+", k1="+k1+", k2="+k2+", val="+val);
		if(k2==null) {
			values.put(k1, val);
		} else {
			Integer k2number = Integer.MIN_VALUE;
			try {
				k2number = Integer.valueOf(k2);
			} catch (Exception ex) {
			}
			
			if(k2number==Integer.MIN_VALUE) {
				// not a number
				Map<String, String> map = (Map)values.get(k1);
				if(map==null) {
					map = new HashMap<String, String>();
					values.put(k1, map);
				}
				map.put(k2, val);
			}
			else if(k2number<0) {
				// we skip those index is number, they have to be handled specially
				// for index could be negative in PHP
				return;
			} 
			else {
				List<String> list = (List) values.get(k1);
				if (list == null) {
					list = new ArrayList<String>();
					// maximum 20
					for (int i = 0; i < 20; i++) {
						list.add("");
					}
					values.put(k1, list);
				}
				// to avoid outofindex exception
				list.set(k2number, val);
			}
		}
		
	}

}
