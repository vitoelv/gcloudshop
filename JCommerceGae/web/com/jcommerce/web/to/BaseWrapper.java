package com.jcommerce.web.to;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

public class BaseWrapper {
	public void debug(String s) {
		System.out.println(" in [BaseWrapper]: "+s );
	}
	protected Map<String, Object> values = new HashMap<String, Object>();
	
	public Object get(String key) {

		Object res = null;
		if(values.containsKey(key)) {
			// can intentionally be mapped to null
			res = values.get(key);
		}
		else {
			try {
				String temp = key.substring(0, 1).toUpperCase()+key.substring(1);;
				Method method = this.getClass().getMethod("get"+temp, new Class[0]);
				res = method.invoke(this, new Object[0]);
			} catch (Exception ex) {
				debug("TODO: "+key);
				res = null;
			}
		}
		if(res == null) {
			// default behavior
			res = key;
		}

		return res;
	}
	
	public void put(String key, Object value) {
		values.put(key, value);
	}
}
