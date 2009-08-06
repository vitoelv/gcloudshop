package com.jcommerce.core.test.dwt;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import junit.framework.TestCase;

public class TestJava extends TestCase {
	public void testConvert() {
		
		String in = "<!-- #BeginLibraryItem \"/library/cart.lbi\" --> xadfdsfdas <!-- #EndLibraryItem -->";

		String reg = ".*<!--\\s#BeginLibraryItem\\s\"/(.*?)\"\\s-->.*?<!--\\s#EndLibraryItem\\s-->";
		
		
        Pattern p = Pattern.compile(reg);
        Matcher m = p.matcher(in);
        boolean ism = m.matches();
        System.out.println("ism?"+ism);
       
        String g = m.group(1);
        System.out.println("g: "+g);
        
        String replaced = m.replaceAll("<#include \""+m.group(1)+"\">");
        
        System.out.println("replaced="+replaced);
        
        
	}
	
	public void testConvertMultiLine() {
		String in = "<!-- #BeginLibraryItem \"/library/abc.lbi\" --> ingored1 <!-- #EndLibraryItem --> Notingored1 <!-- #BeginLibraryItem \"/library/cart.lbi\" --> ingored2 <!-- #EndLibraryItem --> Notingored2";
		String g = "";
		String reg = ".*?<!--\\s#BeginLibraryItem\\s\"/(.*?)\"\\s-->.*?<!--\\s#EndLibraryItem\\s-->.*?";
		
		
        Pattern p = Pattern.compile(reg);
        Matcher m = p.matcher(in);
        boolean ism = m.matches();
        System.out.println("ism?"+ism);
        System.out.println("m.groupCount()="+m.groupCount());
        
//        m.find();
        
//        String g = m.group(1);
        System.out.println("g: "+g);
//        g = m.group(2);
//        System.out.println("g: "+g);
        g = m.group();
        System.out.println("g: "+g);
        
        String replaced = m.replaceAll("<#include \""+m.group(1)+"\">");
        
        System.out.println("replaced="+replaced);
	}
	
	
	public void testConvertMultiLine2() {
		String in = "<!-- #BeginLibraryItem \"/library/abc.lbi\" --> ingored1 <!-- #EndLibraryItem --> Notingored1 <!-- #BeginLibraryItem \"/library/cart.lbi\" --> ingored2 <!-- #EndLibraryItem --> Notingored2";
		String g = "";
		String reg = ".*?<!--\\s#BeginLibraryItem\\s\"/(.*?)\"\\s-->.*?<!--\\s#EndLibraryItem\\s-->(.*?)";
		
		
        Pattern p = Pattern.compile(reg);
        Matcher m = p.matcher(in);
        boolean ism = m.matches();
        System.out.println("ism?"+ism);
        System.out.println("m.groupCount()="+m.groupCount());
        
        g = m.group(1);
        System.out.println("g: "+g);
        g = m.group(2);
        System.out.println("g: "+g);
        g = m.group();
        System.out.println("g: "+g);
        
        String replaced = m.replaceAll("<#include \""+m.group(1)+"\">");
        
        System.out.println("replaced="+replaced);
	}
	
	public void test111() {
		
		Pattern p = Pattern.compile ("\\s(.*?)s,"); 
		Matcher m = p.matcher ("one cats, two cats, or three cats, on a fence"); 
		StringBuffer sb = new StringBuffer (); 

		while (m.find ()) 
			m.appendReplacement (sb, " $1erpillar"); 

		m.appendTail (sb); 
		System.out.println (sb); 
	}
	
	public void test222() {
		
		String in = "oipio<!-- #BeginLibraryItem \"/library/abc.lbi\" --> ingored1 \r\n<!-- #EndLibraryItem --> \r\nNotingored1 <!-- #BeginLibraryItem \"/library/cart.lbi\" --> ingored2 <!-- #EndLibraryItem --> Notingored2";
		String g = "";
		String reg = "<!--\\s#BeginLibraryItem\\s\"/(.*?)\"\\s-->.*?<!--\\s#EndLibraryItem\\s-->";
		
		Pattern p = Pattern.compile (reg, Pattern.DOTALL); 
		Matcher m = p.matcher (in); 
		StringBuffer sb = new StringBuffer (); 

		while (m.find ()) {
			g = m.group(1);
			System.out.println("g: "+g);
			m.appendReplacement (sb, "\\$<#include \\$ \"$1\">");
		}

		m.appendTail (sb); 
		System.out.println ("out: "+sb); 
	}
	
	
	public void test333() {
		String NEWLINE = "\r\n";
		String in = "{insert_scripts files='transport.js'}"+NEWLINE+
					"<div class=\"cart\" id=\"ECS_CARTINFO\">"+NEWLINE+
					"{insert name='cart_info'}"+NEWLINE+
					"</div>"+NEWLINE+
					"<div class=\"blank5\"></div>";
		
		String reg = "\\{([^\\}\\{\\n]*)\\}";
		
		Pattern p = Pattern.compile (reg); 
		Matcher m = p.matcher (in); 
		StringBuffer sb = new StringBuffer (); 

		String g = "";
		while (m.find ()) {
			g = m.group(1);
			System.out.println("g: "+g);
			m.appendReplacement (sb, select(g));
		}

		m.appendTail (sb); 
		System.out.println (sb); 
	}
	
	public String select (String s) {
		return "xxx" ;
	}
	
	public void test444() {
		String NEWLINE = "\r\n";
		String in =  "<!-- {foreach from=$list item=val} -->"+NEWLINE+
		  "<table width=\"100%\" border=\"0\" cellpadding=\"5\" cellspacing=\"1\" bgcolor=\"#dddddd\">"+NEWLINE+
		    "<tr>"+NEWLINE+
		    	"   <th bgcolor=\"#ffffff\">{$lang.label_act_name}</th>		"+NEWLINE+
		    "<td colspan=\"3\" bgcolor=\"#ffffff\">{$val.act_name}</td>";
		      
		      
				String reg = "\\{([^\\}\\{\\n]*)\\}";
				
				Pattern p = Pattern.compile (reg); 
				Matcher m = p.matcher (in); 
				StringBuffer sb = new StringBuffer (); 
				int k = m.groupCount();
				System.out.println("k: "+k);
				String g = "";
				while (m.find ()) {
					g = m.group(1);
					System.out.println("g: "+g);
					m.appendReplacement (sb, select(g));
				}

				m.appendTail (sb); 
				System.out.println (sb); 
	}
	
	public void test555() {
		String in = "";
		String reg = "(abc)*(xyz)(eee)(ewew)";
		Pattern p = Pattern.compile (reg);
		Matcher m = p.matcher (in);
		int k = m.groupCount();
		System.out.println("k: "+k);
		
	}
	
	public void test666() {
		String tag = "<";
		String regex = ">|>=|==|!=|<|<=";
		boolean ok = Pattern.compile(regex).matcher(tag).matches();
		System.out.println("ok? "+ok);
		ok = Pattern.compile(regex).matcher("xads").matches();
		System.out.println("ok? "+ok);
		
	}
	public void test777() {
		String in = "     <a href=\"javascript:;\" onclick=\"window.open('gallery.php?id=${goods.goodsId}'); return false;\">";
		String res = new DWTConverter().regexReplacePHP(in);
		System.out.println("res: "+res);
		
	}
}
