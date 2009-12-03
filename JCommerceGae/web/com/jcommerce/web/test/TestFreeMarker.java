package com.jcommerce.web.test;

import java.io.File;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;

import com.jcommerce.web.to.Lang;

import junit.framework.TestCase;
import freemarker.template.Configuration;
import freemarker.template.ObjectWrapper;
import freemarker.template.Template;

public class TestFreeMarker extends TestCase {
	
	public void testRender() {
		
		try {
			Map<String, Object> map = new HashMap<String, Object>();
	     	Lang lang = Lang.getInstance();             	       
			map.put("lang", lang);

			Configuration cfg = new Configuration();
			// refer to JAVADOC: Configuration.isClassicCompatible()
			cfg.setClassicCompatible(true);
			// refer to Freemarker manuual chapter: Bean wrapper
			cfg.setObjectWrapper(ObjectWrapper.BEANS_WRAPPER);
			cfg.setDirectoryForTemplateLoading(new File("D:/Jcommerce/JCommerceGae/web/com/jcommerce/web/test/"));
			Template t = cfg.getTemplate("test.ftl");
			StringWriter stringWriter = new StringWriter();
			t.process(map, stringWriter);
			String res = stringWriter.toString();
			
			System.out.println("res=");
			System.out.println(res);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
}
