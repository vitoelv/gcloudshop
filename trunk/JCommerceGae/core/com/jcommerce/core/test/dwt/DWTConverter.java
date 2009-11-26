package com.jcommerce.core.test.dwt;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Stack;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;

public class DWTConverter {
	public static interface RegexReplaceCallback {
		public String execute(String... groups);
	}
	
	// temporarily, only store "item" (PHP) or "as"(freemaker)
	public Stack<String> foreachStack=new Stack<String>();
	private String _foreachmark;
	
	private String fileName;
	public String convert(String source, String fileName) {
		debug("in [convert]: fileName="+fileName);
		

		this.fileName = fileName;
		
		String res = null;
		
		res = preCompile(source, fileName);
		
		res = regexSelect(res);

		return res;
	}
	
	public String regexSelect(String source) {
		// match with or without the html comments part
		// String tag = "<a href=\"{$nav.url}\" <!-- {if $nav.opennew eq 1} --> \r\n target=\"_blank\" <!-- {/if} -->>{$nav.name}</a>";
//		String regex = "(?:<!--.*?)?\\{([^\\}\\{\\n]*)\\}(?:[^<]*?-->)?";
//		String regex = "\\{([^\\}\\{\\n]*)\\}";
		
		String regex = "(?:<!--.*?)?\\{([^\\}\\{\\n]*)\\}(?:[^(?:<|\\r|\\n)]*?-->)?";
		
		String res = preg_replace(regex, new RegexReplaceCallback() {
			public String execute(String... groups) {
				return select(groups[0]);
			}
		}, source, Pattern.MULTILINE);
		
		return res;
		
//		Pattern p = Pattern.compile (regex); 
//		Matcher m = p.matcher (source); 
//		StringBuffer sb = new StringBuffer (); 

//		String g = "";
//		while (m.find ()) {
//			try {
//				g = m.group(1);
//				debug("in [convert]: g= "+g);
//			} catch (Exception e){
//				e.printStackTrace();
//				g = m.group();
//				debug("in [convert]: g= "+g);
//			}
//			try {
//				m.appendReplacement (sb, select(g));
//			} catch (Exception e) {
//				e.printStackTrace();
//				debug("where exception thrown: in [convert]: g="+g+", sb="+sb.toString());
//				throw new RuntimeException(e);
//			}
//		}

//		m.appendTail (sb); 		
//		System.out.println (sb); 
		
//		res = sb.toString();
//		return res;
	}
	public String preCompile (String in, String fileName ) {
		debug("in [preCompile]: ");
		String res = "";
		String fileType = fileName.substring(fileName.indexOf('.'));
		if(".dwt".equals(fileType)) {
			res = regexReplaceLibrary(in);
		}else if(".lbi".equals(fileType)) {
			String regex = "<meta\\shttp-equiv=[\"|\\']Content-Type[\"|\\']\\scontent=[\"|\\']text\\/html;\\scharset=(?:.*?)[\"|\\']>\\r?\\n?";
			res = preg_replace(regex, "", in);
			
		}
		res = regexReplacePHP(res);
		
		return res;
	}
	public String preg_replace(String regex, RegexReplaceCallback callback, String source) {
		return preg_replace(regex, callback, source, -1);
	}
	public String preg_replace(String regex, RegexReplaceCallback callback, String source, int flag) {
		
//		Pattern p = dotall? Pattern.compile (regex, Pattern.DOTALL): Pattern.compile (regex); 
		Pattern p = flag!=-1? Pattern.compile (regex, flag): Pattern.compile (regex);
		Matcher m = p.matcher (source);
		int kk = m.groupCount();
		StringBuffer sb = new StringBuffer (); 
		while(m.find()) {
			String[] args = new String[kk];
			for(int i=0;i<kk;i++) {
				args[i] = m.group(i+1);
			}
			try {
				m.appendReplacement (sb, callback.execute(args));
			} catch (Exception e) {
				e.printStackTrace();
				debug("where exception thrown: in [convert]: fileName="+fileName+", args="+Arrays.toString(args)+", sb="+sb.toString());
				throw new RuntimeException(e);
			}
		}
		
		m.appendTail (sb); 
		return sb.toString();
	}
	public String preg_replace(String regex, final String replacement, String source) {
		return preg_replace(regex, replacement, source, -1);
	}
	public String preg_replace(String regex, final String replacement, String source, int flag) {
		return preg_replace(regex, new RegexReplaceCallback() {
			public String execute(String... groups) {
				return replacement;
			}
		}, source, flag);
	}
	public List<String> findLangKeys(String in) {
		List<String> res = new ArrayList<String>();
		String regex = "\\$\\{\\s*?lang\\.([^(:?\\s|\\})]+)\\s*?\\}";
		Pattern p = Pattern.compile(regex);
		Matcher m = p.matcher (in); 
		while (m.find ()) {
			String g = m.group(1);
			res.add(g);
		}		
		return res;
	}
	public Map<String, Object>findLangVals(String in, Map<String, Object> res) {
		String k1 = null, k2=null,v=null;
//		 = new HashMap<String, Object>();
		try {
		// String regex = "\\$_LANG\\['([^(\\s|')]+)'\\]\\s*?=\\s*?'([^']+)';";
		String regex = "\\$_LANG\\['([^(?:\\s|')]+)'\\](?:\\['?([^(?:\\s|')]+)'?\\])*?\\s*?=\\s*?'([^']+)';";
		Pattern p = Pattern.compile(regex);
		Matcher m = p.matcher (in); 
		int gc = m.groupCount();
		System.out.println("gc="+gc);

		while (m.find ()) {
			k1 = replacePhpVars(m.group(1));
			k2 = m.group(2);
			v = m.group(3);
			
			if(k2==null) {
				res.put(k1, v);
			}
			else {
//				int k2number = Integer.MIN_VALUE;
//				try {
//					k2number = Integer.valueOf(k2);
//				} catch (Exception e) {
//				}
//				if(k2number==Integer.MIN_VALUE) {
					k2 = replacePhpVars(k2);
					Map<String,String> val = (Map)res.get(k1);
					if(val==null) {
						val = new HashMap<String, String>();
						res.put(k1, val);
					}
					val.put(k2, v);					
//				}
//				else {
//					List<String> val = (List)res.get(k1);
//					if(val==null) {
//						val = new ArrayList<String>();
//						res.put(k1, val);
//					}
//					val.add(k2number, v);
//				}

			}
		}		
		
		return res;
		} catch (Exception ex) {
			System.out.println("k1="+k1+",k2="+k2+",v="+v);
			ex.printStackTrace();
			throw new RuntimeException(ex);
		}
	}
	public String regexReplaceLibrary(String in) {
		String regex = "<!--\\s#BeginLibraryItem\\s\"/(.*?)\"\\s-->.*?<!--\\s#EndLibraryItem\\s-->";
		String res = preg_replace(regex, new RegexReplaceCallback() {
			public String execute(String... groups) {
				return replaceLibrary(groups[0]);
			}
		}, in, Pattern.DOTALL);
		return res;
	}
	public String regexReplacePHP(String in) {
		String res = "";
		res = in.replaceAll("\\.php", ".action");
		return res;
	}
	public Map<String, String> getDynaLibs() {
		Map<String, String> libs = new HashMap<String, String>();
//		libs.put("左边区域", )
		return libs;
	}
	public String replaceLibrary(String tag) {
		if(tag.endsWith(".lbi")) {
			tag = StringUtils.replace(tag, ".lbi", ".ftl");
		}
		String res = "<#include \""+tag+"\">";
		
		
		return res;
	}
	public String compileIfTag(String tag, boolean elseif) {
		String res = "";
//		String regex = "\\-?\\d+[\\.\\d]+|\\'[^\\'|\\s]*\\'|\"[^\"|\\s]*\"|[\\$\\w\\.]+|!==|===|==|!=|<>|<<|>>|<=|>=|&&|\\|\\||\\(|\\)|,|\\!|\\^|=|&|<|>|~|\\||\\%|\\+|\\-|\\/|\\*|\\@|\\S";
//		Pattern p = Pattern.compile (regex); 
//		Matcher m = p.matcher (tag);
//		int gc = m.groupCount();
//		System.out.println("gc: "+gc);
		
		// we adopt a simpler way
		
		// typically:  
		// from: <!-- {if $spec.attr_type eq 1} -->
		// to:  <#if spec.attrType == 1>
		// or
		// from: if !$gb_deposit
		// to: <#if 
		
		StringBuffer buf = new StringBuffer();
		if(elseif) {
			buf.append("<#elseif ");
		}else {
			buf.append("<#if ");
		}
		
		
		
//		String[] tokens = StringUtils.splitPreserveAllTokens(tag);
		String[] tokens = StringUtils.split(tag);
		int size = tokens.length;
		String[] convertedTokens = new String[size];
		for(int i=0;i<size;i++) {
			String token = tokens[i];
			token = token.toLowerCase();
//			if(StringUtils.isBlank(token)) {
//				continue;
//			}
			if("eq".equals(token)) {
//				buf.append(" == ");
				convertedTokens[i] = "==";
			}
			else if("ne".equals(token) || "neq".equals(token)) {
//				buf.append(" != ");
				convertedTokens[i] = "!=";
			}
			else if("lt".equals(token)) {
//				buf.append(" < ");
				convertedTokens[i] = "<";
			}
			else if("le".equals(token) || "lte".equals(token)) {
//				buf.append(" <= ");
				convertedTokens[i] = "<=";
			}
			else if("gt".equals(token)) {
//				buf.append(" > ");
				convertedTokens[i] = ">";
			}
			else if("ge".equals(token) || "gte".equals(token)) {
//				buf.append(" >= ");
				convertedTokens[i] = ">=";
			}
			else if("and".equals(token)) {
//				buf.append(" && ");
				convertedTokens[i] = "&&";
			}
			else if("or".equals(token)) {
//				buf.append(" || ");
				convertedTokens[i] = "||";
			}
			else if("not".equals(token)) {
//				buf.append(" !");
				convertedTokens[i] = "!";
			}
			else if("mod".equals(token)) {
//				buf.append(" % ");
				convertedTokens[i] = "%";
			}
//			else if(token.startsWith("!$")) {
//				// TODO handle this properly
//				// $ is probably a key word in Java regex replacement that will cause error
//				buf.append(token.replace("!$", "!\\$"));
//			}
//			else if(token.startsWith("$")) {
////				buf.append(token.replace("$", "\\$"));
//				buf.append(token);
//				if(i==size-1) {
//					//if $goods_list ==> <#if goods_list??>
//					// TODO this is not strict. 
//					// if $img_links  or $txt_links 
//					buf.append("??");
//				}
//			}

			else {
				// handle special cases
				// if isset($goods_list) ==> <#if goods_list??>
				Pattern p = Pattern.compile("\\s*?isset\\s*?\\(\\s*?\\$(\\S*?)\\s*?\\)\\s*?");
				Matcher m = p.matcher(token);
				if(m.matches()) {
//					buf.append(" ").append(m.group(1)).append("?? ");
					convertedTokens[i] = m.group(1)+"??";
				} else {
//						buf.append(" ").append(token).append(" ");
						convertedTokens[i] = token;
				}
			}
			
		}
		
		for(int i=0;i<size;i++ ) {
			String token = convertedTokens[i];

			if(token.contains("$")) {
				String temp = token;
				// 	handle variables
				temp = token.replace("$", "");
				boolean withLogical = false;
				if(i!=0) {
					withLogical = withLogical | "!".equals(convertedTokens[i-1]);
					String regex = ">|>=|==|!=|<|<=";
					withLogical = withLogical | Pattern.compile(regex).matcher(convertedTokens[i-1]).matches();
				}
				if(i!=size-1) {
					String regex = ">|>=|==|!=|<|<=";
					withLogical = withLogical | Pattern.compile(regex).matcher(convertedTokens[i+1]).matches();
				}
				{
					// handle like !$var
					withLogical = withLogical | token.startsWith("!"); 
					// handle like empty($var)
					withLogical = withLogical | token.startsWith("empty(");
				} 
				// TODO  {if $consignee.country eq $country.region_id} // consignee.lbi
				if(!withLogical) {
					temp = temp+"??";
				}
				token = temp;
			}
			buf.append(" ").append(token).append(" ");
		}
		buf.append(" >");
		
		
		res = buf.toString();
		
		// TODO {if $smarty.foreach.brand_foreach.index <= 11}
		// in brand.ftl
		
//		res = res.replace("$", "\\$");

		debug(" outof [compileIfTag]: res="+res);
		
		
		
		return res;
	}
	public String compileForEachStart(String tag) {
		// typically: <!--{foreach from=$group_buy_goods item=goods}-->
		// to:  <#list spec.values as value>
		// TODO  <!-- {foreach from=$spec.values item=value key=key} -->
		// {foreach name=nav_top_list from=$navigator_list.top item=nav} --> // name is no use for freemarker..
		// TODO <!-- {foreach from=$how_oos_list key=how_oos_id item=how_oos_name} -->
		// expect sth like:  <#list consigneeList?keys as sn> <#assign consignee = consigneeList[sn]> 
		
		// TODO <!-- {foreach from=$province_list.$sn item=province} -->  // consignee.lbi
		
		String res = "";
		
		/*
//		String regex = ".*?from\\s*?=\\s*?\\$(\\S*?)\\s*?(?:(?:item\\s*?=\\s*?(\\S*?))(?:\\s*?key\\s*?=\\s*?(\\S*?)\\s*?)?|(?:\\s*?name\\s*?=\\s*?([\"\\S\"]*?)\\s*?)?)";
		
		String regex = ".*?from\\s*?=\\s*?\\$(\\S*?)\\s*?item\\s*?=\\s*?(\\S*?)(?:\\s*?key\\s*?=\\s*?(\\S*?)\\s*?)?(?:\\s*?name\\s*?=\\s*?([\"\\S\"]*?)\\s*?)?";
//		String regex = "from\\s*?=\\s*?\\$(\\S*?)\\s*?item\\s*?=\\s*?(\\S*?)\\s*?";
//		String regex = "from=\\$(\\S*?)\\s*?item=(\\S*?)";
		
		Pattern p = Pattern.compile(regex);
		Matcher m = p.matcher(tag);
		int mc = m.groupCount();
		System.out.println("mc="+mc);
		String listItem = "";
		String valueItem = "";
		String keyItem = "";
		if(m.matches()) {
			listItem = m.group(1);
			valueItem = m.group(2);
			keyItem = m.group(3);
		}
		else {
			debug("in [compileForEachStart]: DO NOT MATCH!");
			String regex2 = ".*?from\\s*?=\\s*?\\$(\\S*?)\\s*?item\\s*?=\\s*?(\\S*?)(?:\\s*?key\\s*?=\\s*?(\\S*?)\\s*?)?(?:\\s*?name\\s*?=\\s*?([\"\\S\"]*?)\\s*?)?";
		}
		*/
		
		// to make it simpler
		// however getpara does not always work in case from = xxx
		Map<String, String> paras = getPara(tag);
		String listItem = StringUtils.replace(paras.get("from"), "$", "");
		String valueItem = StringUtils.replace(paras.get("item"), "$", "");
		String keyItem = StringUtils.replace(paras.get("key"), "$", "");
		
		
		if(keyItem!=null) {
			// it's iterating a map or hashtable
			res = new StringBuffer("<#list ").append(listItem).append("?keys as ").append(keyItem)
				.append("> <#assign ").append(valueItem).append(" = ").append(listItem).append(".get(").append(keyItem).append(")>")
				.toString();	
		}
		else {
			// just an list or array
			res = new StringBuffer("<#list ").append(listItem).append(" as ").append(valueItem).append(">")
				.append(" <#assign sn = ").append(valueItem).append("_index >")
				.toString();
			
			
		}
		
		debug("in [compileForEachStart]: res="+res+", key="+keyItem);
		
		foreachStack.push(valueItem);
		
		return res;
	}
	public String select(String tag) {
//		$tag = stripslashes(trim($tag));
		debug("in [select]: tag="+tag);
		String res = "";
		tag = tag.trim();
		if(StringUtils.isEmpty(tag)) {
			res = "{}";
		}
		else if(tag.charAt(0)=='*' && tag.endsWith("*")) {
			res = "";
		}
		else if(tag.charAt(0)=='$') {// 变量
//			res = "\\${"+tag.substring(1)+"}";
			res = tag;
		}
		else if(tag.charAt(0)=='/') { // 结束 tag
			tag = tag.substring(1);
			if("if".equals(tag)) {
				res =  "</#if>";
			}else if("foreach".equals(tag)) {
				if("foreachelse".equals(_foreachmark)) {
					
				}else {
					res = "</#list>";
				}
				foreachStack.pop();
			} else if("literal".equals(tag)) {
				res = "";
			
			} else {
				// TODO 
				res = "{"+tag+"}"; 
			}
		}
		else {
			String tag_sel = explode(tag);
			if("if".equals(tag_sel)) {
				res = compileIfTag(tag.substring(3), false);
			}
			else if("else".equals(tag_sel)) {
				res = "<#else>";
			}
			else if("elseif".equals(tag_sel)) {
				res = compileIfTag(tag.substring(7), true);
			}
			else if("foreachelse".equals(tag_sel)) {
				// TODO foreachelse
			}
			else if("foreach".equals(tag_sel)){
				_foreachmark = "foreach";
				res = compileForEachStart(tag.substring(8));
			}
			else if("include".equals(tag_sel)) {
				// include should have been catered for in precompile stage
			}
			
			else if("insert_scripts".equals(tag_sel)) {
				Map<String, String> m = getPara(tag.substring(15)); 
				
				res = compileInsertScripts(m);
				
			}else if("insert".equals(tag_sel)) {
				Map<String, String> m = getPara(tag.substring(7));
				
				res = compileInsert(m);
			} else if("html_options".equals(tag_sel)) {

				Map<String, String> m = getPara(tag.substring(13));
				
				res = htmlOptions(m);
			}
			else if("html_select_date".equals(tag_sel)) {
				// TODO: html_select_date CLAUSE 
				res = "TODO: html_select_date CLAUSE";
			}
			else if("html_radios".equals(tag_sel)) {
				// TODO: html_radios CLAUSE
				res = "TODO: html_radios CLAUSE";
			}
			else if("html_select_time".equals(tag_sel)) {
				// TODO: html_select_time CLAUSE
				res = "TODO: html_select_time CLAUSE";
			} else if("literal".equals(tag)) {
				res = "";
			} else {
                res = "{" + tag + "}";
			} 
		}
		
		
		debug("outof [select]: res="+res);
		res = replaceVars(res);
		
		return res;
	}

	public String compileInsert(Map<String, String> para) {
		String res="";
		// TODO insert is difficult
		String name = para.get("name");
		if(para.get("name").equals("cart_info")) {
			res = "TODO cart info";
		} else if(para.get("name").equals("query_info")){
			res = "TODO query info";
		} else if(para.get("name").equals("history")){
			res = "TODO history";
		} else if(para.get("name").contains("comments")){
			//TODO {insert name='comments' type=$type id=$id}
//			 currently <#include "commentstype.ftl">
//			res = "TODO comments";			
			res = "<#include \"comments_list.ftl\">";
		}else {
			res = "<#include \""+para.get("name")+".ftl\">";
		}
		return res;
	}
	public String htmlOptions(Map<String, String> paras) {
		/* input: 
		 * String tag = "html_options options=$pager.array selected=$pager.page";
		 * 
		 * expect:
		 * <#list pager.array?keys as key>
		 * <#assign val = pager.array.get(key)>  
		 * <option value="${key}" <#if pager.page == key>selected</#if> >${val}</option>
		 * </#list>
		 * 
		 */
		// quotation.dwt options=$brand_list
		
		 StringBuffer res = new StringBuffer();
		if (paras.get("options") != null) {
			String mapName = paras.get("options").replace("$", "");
			String selected = paras.get("selected")==null? "" : paras.get("selected").replace("$", "");
			res.append("<#list ").append(mapName).append("?keys as key>").append("\r\n");
			res.append("<#assign val = ").append(mapName).append(".get(key)>").append("\r\n");
			res.append("<option value=\"${key}\" <#if ").append(selected).append(" == key>selected</#if> >${val}</option>").append("\r\n");
			res.append("</#list>").append("\r\n");
		} else if (paras.get("output") != null) {
			// TODO　 htmlOptions with output
			// flow.dwt: html_options values=$inv_content_list output=$inv_content_list selected=$order.inv_content
			res.append("TODO htmlOptions with output");
			
		}
		 return res.toString();
		
		
	}

	public String replaceVars(String tag) {

		String res = "", res2="", res3="";
		// special to handle insert (see select())
		if(tag.contains("<#include")) {
			res3=tag;
		}
		else {
			
		String regex = "\\$([a-zA-Z0-9_\\.]+)\\s*?";
//		String regex = "\\$(\\S+)\\s*?";
		// replace single $ with blank
		// however keep ${
		res = preg_replace(regex, new RegexReplaceCallback() {
			public String execute(String... groups) {
				return "\\${"+groups[0].replace("$", "")+"}";
			}
		}, tag);	
		res = res.replace("$", "\\$");

		res = replacePhpVars(res);
		debug("in [replaceVars] after[replacePhpVars]: res = "+res);
		
		res2 = res.replace("|escape:html", "?html");
		// TODO escape URL
		res2 = res2.replace("|escape:url", "");
		// TODO what does it mean in DWT?
		res2 = res2.replace("|escape", "");
		
		// TODO what does it mean?
		res2 = res2.replace(":u8Url", "");
		
		// TODO 
//		${cfg.goodsattrStyle|default:1};
		res2 = res2.replace("|default:0", "");
		res2 = res2.replace("|default:1", "");
		
		
		//${article.shortTitle|truncate:10:"...":true}
		int t1 = res2.indexOf("|truncate");
		if(t1>=0) {
			res2 = res2.substring(0, t1)+"}";
			
		}
		
		regex = "\\!smarty\\.foreach\\.(?:\\S*?)\\.last";
		res3 = preg_replace(regex, new RegexReplaceCallback() {
			public String execute(String... groups) {
				if(!foreachStack.empty()) {
					return foreachStack.peek()+"_has_next";
				}else {
					return "TODO: sth wrong here";
				}
			}
		}, res2);
		

		regex = "empty\\s*?\\(\\s*?([^\\)]*?)\\)";
		res3 = preg_replace(regex, new RegexReplaceCallback() {
			public String execute(String... groups) {
				return "!"+groups[0]+"?has_content";
			}
		}, res3);
		
		}
		debug("in [replaceVars]: res="+res+", res2="+res2+", res3="+res3);
		return res3;
	}

	public String replacePhpVars(String res) {
		// replace PHP style variables to Java style
		String res2;
		String regex = "_([a-z])";
//		String regex = "(\\s^[\\s|_|\"]*)_([a-z])";
		res2 = preg_replace(regex, new RegexReplaceCallback() {
			public String execute(String... groups) {
				return groups[0].toUpperCase();
//				return groups[0]+groups[1].toUpperCase();
			}
		}, res);
		return res2;
	}
	public String explode(String s) {
		String[] strs = StringUtils.split(s);
		return strs[0];
	}
	public Map<String, String> getPara(String str) {
		debug("in [getPara]: s="+str);
		Map<String, String> m = new HashMap<String, String>();
		// files='transport.js'
		// files='common.js,index.js'
		// options=$pager.array selected=$pager.page

		String[] keyValues = StringUtils.split(strTrim(str));
		for (String s : keyValues) {
			s = s.trim();
			int index = s.indexOf('=');
			if (index >= 0) {
				String[] ss = StringUtils.split(StringUtils.replaceChars(s,
						"'\" ", ""), "=");
				debug("in [getPara]: ss=" + Arrays.toString(ss));
				String key = ss[0];
				m.put(key, ss[1]);

			}
		}
		return m;
	}

	public String strTrim(String s) {
		String before = s;
		/* 处理'a=b c=d k = f '类字符串，返回数组 */
		while((s.indexOf("= "))>=0) {
			s = s.replace("= ", "=");
		}
		while((s.indexOf(" ="))>=0) {
			s = s.replace(" =", "=");
		}
		s = s.trim();
		debug("in [strTrim]: before="+before+", after="+s);
		return s;
	}

	public String compileInsertScripts(Map<String, String> paras) {
		StringBuffer buf = new StringBuffer();
		String ss = paras.get("files");
		String[] values = ss.split(",");
		for(String value:values) {
			buf.append("<script type=\"text/javascript\" src=\"js/").append(value).append("\"></script>").append("\r\n");
		}
		return buf.toString();
		
	}
	
	public void debug(String s) {
		System.out.println(" in [DWTConverter]: "+s);
	}
}
