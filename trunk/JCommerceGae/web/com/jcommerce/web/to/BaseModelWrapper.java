package com.jcommerce.web.to;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

public abstract class BaseModelWrapper extends BaseWrapper {
	
	public void debug(String s) {
		System.out.println(" in [BaseModelWrapper]: "+s );
	}
	protected abstract Object getWrapped();
	

	
	@Override
	public Object get(String key) {

			Object res = null;
			if(values.containsKey(key)) {
				// can intentionally be mapped to null
				res = values.get(key);
			}
			else {
				try {
					String temp = key.substring(0, 1).toUpperCase()+key.substring(1);;
					Method method = getWrapped().getClass().getMethod("get"+temp, new Class[0]);
					res = method.invoke(getWrapped(), new Object[0]);
				} catch (Exception ex) {
					debug("TODO: "+key);
					res = null;
				}
			}
			if(res == null) {
//				 we allow a null value
				res = "isNull:"+key;
			}

			return res;
			

	}
	

	
}
