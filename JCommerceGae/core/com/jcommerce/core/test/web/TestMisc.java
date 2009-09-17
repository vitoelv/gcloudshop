package com.jcommerce.core.test.web;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import junit.framework.TestCase;

import com.jcommerce.web.to.Lang;

public class TestMisc extends TestCase {
	
	public void testLang() {
		String regex = "([^\\[]*)(?:\\[([^\\]]+)\\])?";
		Pattern p = Pattern.compile(regex);
		Map<String, Object> values = new HashMap<String, Object>();
		
		Lang.parse("userRegInfo[5]", "axxxxx", p, values);
		
		
		Object o = values.get("userRegInfo");
		System.out.println("o : "+ (o==null? "null" : o.getClass().getName()));
		
		if(o instanceof Map) {
			Map<String, String> map = (Map)o;
			for(String key : map.keySet()) {
				System.out.println("key="+key+", val="+map.get(key));
			}
		}
		
		if(o instanceof List) {
			List<String> list = (List)o;
			for(int i=0;i<list.size();i++) {
				System.out.println("i="+i+", val="+list.get(i));
			}
		}
	}
	
	
}
