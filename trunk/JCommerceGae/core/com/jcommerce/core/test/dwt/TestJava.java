package com.jcommerce.core.test.dwt;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;

import junit.framework.TestCase;

import com.jcommerce.web.to.Lang;

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
		System.out.println ("result: "+sb); 
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
	
	public void testFindLang() {
//		String in = "xxxx ${ lang.abc} \r\n yyy ${lang.xyz} \r\n zzzz";
		String in = "${payment.payName}(${lang.payFee}:${payment.formatPayFee})";
		List<String> res = new DWTConverter().findLangKeys(in);
		for(String s:res) {
			System.out.println("s: "+s);
		}
		
	}
	
	public void testExtractResource() {

		String in1 = "$_LANG['city_district'] = '城市/地区';";
		String in2 = "$_LANG['flow_js']['consignee_not_null'] = '收货人姓名不能为空！';";
		String in3 = "$_LANG['far_ext'][0] = '全部商品';";
//		String in = "$_LANG['far_ext'][FAR_ALL] = '全部商品';";
		
		String in = in1+"\r\n"+in2+"\r\n"+in3; 
		

		
		
		Map<String, Object> res = new HashMap<String, Object>();
		new DWTConverter().findLangVals(in, res);
		for(String k:res.keySet()) {
			Object v = res.get(k);
			if(v instanceof Map) {
				Map<String, String> val = (Map)v;
				for(String k2:val.keySet()) {
					System.out.println("k="+k+", k2="+k2+", v="+val.get(k2));	
				}
			} else if(v instanceof List){
				List<String> val = (List)v;
				for(int i=0;i<val.size();i++) {
					System.out.println("k="+k+", index="+i+", v="+val.get(i));	
				}			
			} else {
				System.out.println("k="+k+", v="+v);				
			}
		}
		
	}
	
	public void test888() {
		String regex = "([^\\[]*)(?:\\[([^\\]]+)\\])?";
		Pattern p = Pattern.compile(regex);

			String k1=null, k2=null;
			String key = "pager_4";
//			String key = "passportJs[emailInvalid]";
			String val = "abc";
			Matcher m = p.matcher(key);
			if(m.find()) {
				k1 = m.group(1);
				k2 = m.group(2);
			}
			System.out.println("k1="+k1+", k2="+k2);
					
	}
	public void testLang() {
		Lang lang = Lang.getInstance();
		
		System.out.println(lang.get("actTime"));
		
		System.out.println(((Map)lang.get("bookingJs")).get("bookingAmountError"));
		
		
	}
	
	
	public void testCompileIfTag() {
		try {
//			String tag = "$item.type ";
			
//			String tag = "$img_links  or $txt_links "; 
			
//			String tag = "$item.type eq \"group_buy\"";
			
//			String tag = "$pager.page eq $key";
			
//			String tag = "$profile.sex==1";
//			String tag = "$profile.sex == 1";
			
			// from user_transaction.dwt
			String tag = "$action eq order_detail";
			
			String res  = new DWTConverter().compileIfTag(tag, false);
			System.out.println("res: "+res);
		} catch (Exception ex) {
			ex.printStackTrace();
			
		}
		System.out.println("end of testCompileIfTag");

		
	}
	public void testConvertFragment() {
		try {
			
			String tag = "<a href=\"{$nav.url}\" <!-- {if $nav.opennew eq 1} --> target=\"_blank\" <!-- {/if} -->>{$nav.name}</a>";
			DWTConverter dwtConverter = new DWTConverter();
			String res  = dwtConverter.convert(tag, "abc.ftl");
			System.out.println("res="+res);
		}catch (Exception ex) {
			ex.printStackTrace();
			
		}
		System.out.println("end of testConvertFragment");
		
	}
	public void testCompileForEachStart() {
		try {
//			String tag = " from =$spec.values item=value key=key ";
//			String tag = "from=$group_buy_goods item=goods";
			
//			String tag = "from=$pager.search key= key item = item";
			
//			String tag = "from=$pager.search item=item key=key";
			String tag = "from=$city_list.$sn item=city";
			
			String res  = new DWTConverter().compileForEachStart(tag);
//			System.out.println("res: "+res);
		} catch (Exception ex) {
			ex.printStackTrace();
			
		}
		System.out.println("end of testCompileForEachStart");
		
	}
	
	public void testRegexSelect() {
		try {
//			String tag = "if !$gb_deposit";
//			String tag = "$lang.far_ext[$favourable.act_range]";
			
//			String tag = "* ECSHOP 提醒您：根据用户id来调用member_info.lbi显示不同的界面  *";
//			String tag = "insert name='member_info'";
			
//			String tag = "if !$smarty.foreach.nav_top_list.last";
			
			// page_footer.ftl
//			String tag = "<a href=\"{$nav.url}\" <!-- {if $nav.opennew eq 1} --> \r\n target=\"_blank\" <!-- {/if} -->>{$nav.name}</a>";
			
			String tag = "{$nav.url}  <!-- {if $nav.opennew eq 1} --> ";
//			String tag = "{if empty($order_query)}";
//			String tag = "{insert name='vote'}";
//			String tag = "{foreach from=$promotion_goods item=goods name=\"promotion_foreach\"}";
//			String tag = "{foreach name=nav_top_list from=$navigator_list.top item=nav}";
			
//			String LINEND = "\r\n";
//			String tag = 
//			"<script type=\"Text/Javascript\" language=\"JavaScript\">  "+ LINEND+
//			"<!--  "+LINEND+
//			"{literal} "+LINEND+
//			"function selectPage(sel) "+LINEND+
//			"{ "+LINEND+
//			"  sel.form.submit();"+LINEND+
//			" }"+LINEND+
//			" {/literal}"+LINEND+
//			" //-->"+LINEND+
//			" </script>";
			

			
			DWTConverter dwtConverter = new DWTConverter();
			dwtConverter.foreachStack.push("abc");
			String res  = dwtConverter.regexSelect(tag);
			System.out.println("res="+res);
			
		}catch (Exception ex) {
			ex.printStackTrace();
			
		}
		System.out.println("end of testRegexSelect");
	}
	
	public void testSelect() {
		try {
//			String tag = "if !$gb_deposit";
//			String tag = "$gb_deposit";
//			String tag = "$lang.pager_1";
					
//			String tag = "$lang.far_ext[$favourable.act_range]";
			
//			String tag = "* ECSHOP 提醒您：根据用户id来调用member_info.lbi显示不同的界面  *";
//			String tag = "insert name='member_info'";
			
//			String tag = "if !$smarty.foreach.nav_top_list.last";
			
//			String tag = "<a href=\"{$nav.url}\" <!-- {if $nav.opennew eq 1} --> target=\"_blank\" <!-- {/if} -->>{$nav.name}</a>";
			
//			String tag = "html_options options=$pager.array selected=$pager.page";
			// expect:
			/*
			 * <#list pager.array?keys as key>
			 * <#assign val = pager.array.get(key)>  
			 * <option value="${key}" <#if pager.page == key>selected</#if> >${val}</option>
			 * </#list>
			 * 
			 */
			
			// TODO this is tricky.  consider rewrite compileIf, 
			// 1) parsing each token accurately, with or without space
			// 2) replace PHPVar with JavaVar only for those token contains "$" 
			
//			String tag = "if $action_xxx.abc[xby] eq \"account_deposit\"";
			
			
			String tag = "insert_scripts files='utils.js,transport.js,region.js,shopping_flow.js'";
			
			DWTConverter dwtConverter = new DWTConverter();
			dwtConverter.foreachStack.push("abc");
			String res  = dwtConverter.select(tag);
			System.out.println("res: "+res);
		}catch (Exception ex) {
			ex.printStackTrace();
			
		}
		System.out.println("end of testSelect");
	}
	
	public void testReplaceVars() {
		try {
//			String tag = "$keywords";
//			String tag = "$lang.far_ext[$favourable.act_range]";
			// expect ${lang.far_ext[favourable.act_range]}
			
			String tag = "$img_links  or $txt_links";
			
			
			
			String res  = new DWTConverter().replaceVars(tag);
			System.out.println("res: "+res);
		}catch (Exception ex) {
			ex.printStackTrace();
			
		}
		System.out.println("end of testReplaceVars");
	}
	
	public void testGetPara() {
		try {
//			String tag = "$keywords";
//			String tag = "$lang.far_ext[$favourable.act_range]";
			// expect ${lang.far_ext[favourable.act_range]}
			
						
			String tag = "name='member_info'";
			
			String s = StringUtils.replaceChars(tag, "'\" ", "");
			System.out.println("s="+s);
			
			Map<String, String> res  = new DWTConverter().getPara(tag);
			System.out.println("res: "+res);
			
		}catch (Exception ex) {
			ex.printStackTrace();
			
		}
		System.out.println("end of testGetPara");
	}
}
