package com.jcommerce.core.test;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.StringTokenizer;

import junit.framework.TestCase;

import org.apache.commons.lang.StringUtils;

import com.jcommerce.core.annotation.IsPK;
import com.jcommerce.core.model.Brand;
import com.jcommerce.core.model.Category;
import com.jcommerce.core.model.Goods;
import com.jcommerce.core.util.ConvertUtil;
import com.jcommerce.core.util.UUIDHexGenerator;

public class TestMisc extends TestCase {
	  public boolean[][] b = { { false, false }, { true, true } };
	  public String name = "Alice";
	  public List<Brand> list;
	  public List list2;
	  
	public void testReflectGenericType() {
		
	    try {
	        Class<?> c = TestMisc.class;
	        Field f = c.getField("list");
	        System.out.format("Type: %s%n", f.getType());
	        System.out.format("GenericType: %s%n", f.getGenericType());
	        Type t = f.getGenericType();
	        System.out.println(t.getClass().getName());

	        // production code should handle these exceptions more gracefully
	      } catch (Exception x) {
	        x.printStackTrace();
	      }
	      
	      Set<String> s = new HashSet<String>();
	      s.add("[a");
	      s.add("[b");
	      
	      System.out.println("tostring: "+Arrays.toString(s.toArray()));
	      
	      String newline = ",,,";
	      String[] ss = StringUtils.split(newline, ",");
	      System.out.println("ss: "+ss.length);
	      
			StringTokenizer st = new StringTokenizer(newline, ",");
			List<String> l = new ArrayList<String>();
			while(st.hasMoreTokens()) {
				String token = st.nextToken();
				l.add(token);
			}
			ss = l.toArray(new String[0]);
			System.out.println("ss: "+ss.length);
			
			ss = newline.split(",");
			System.out.println("ss: "+ss.length);
		
	}
	
	public void testSplit() {
		System.out.println("Start of testSplit ");
		String in = "aa,bbb,cccc,";
		String in2 = ",,,";
		String in3 = ",bb,,";
		
		String[] ss = ConvertUtil.split(in, ",");
		for(String s:ss) {
			System.out.println("s: "+s);
		}
		
		ss = ConvertUtil.split(in2, ",");
		for(String s:ss) {
			System.out.println("s: "+s);
		}
		
		ss = ConvertUtil.split(in3, ",");
		for(String s:ss) {
			System.out.println("s: "+s);
		}
		
	}
	public void testGenKeyName() {
		genKeyName();
		genKeyName();
		genKeyName();
	}
	
	private void genKeyName() {
		Brand brand = new Brand();
		String k1 = UUIDHexGenerator.newUUID();
		String k2 = String.valueOf(brand.hashCode());
		long nano = System.nanoTime();
		String k3 = String.valueOf(nano);
		String k4 = String.valueOf(nano%10000);
		System.out.println("k1="+k1+", k2="+k2+", k3="+k3+", k4="+k4);		
	}
	
	public void testAnnotation() {
		try {
			Field f = Goods.class.getDeclaredField("goodsTypeId");
			Annotation isPK = f.getAnnotation(IsPK.class);
			System.out.println("isPK = "+isPK);
			
			f = Category.class.getDeclaredField("parentId");
			isPK = f.getAnnotation(IsPK.class);
			System.out.println("isPK = "+isPK);
		
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

}
