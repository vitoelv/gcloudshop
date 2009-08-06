package com.jcommerce.core.test.dwt;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Map;

import junit.framework.TestCase;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;

public class TestDWTConverter extends TestCase {
	
	public void testConvert() {
		String sBaseDir = "D:/JCommerce/ECShop_V2.6.1_UTF8_build1208/upload/themes/default";
		String sDestDir = "D:/JCommerce/ECShop_V2.6.1_UTF8_build1208/upload/themes/freemarker";
		
		testConvertDir(sBaseDir, sDestDir);
		
	}
	
	public void testConvertDir(String sBaseDir, String sDestDir) {
		try {
			File destDir = new File(sDestDir);
			if(!destDir.exists()) {
				destDir.mkdir();
			}
			
			String ENC = "UTF-8";
			
			File fBaseDir = new File(sBaseDir);
			File[] files = fBaseDir.listFiles();
			for(File file: files) {
				String fileName = file.getName();
				// test only
				if(file.isDirectory()) {
					// TODO
					testConvertDir(sBaseDir+"/"+fileName, sDestDir+"/"+fileName);
				}
				else {
//					if(!fileName.equals("page_header.lbi")) {
//						continue;
//					}
				System.out.println("processing file: "+fileName);
//				String type = "";
				if(fileName.endsWith(".dwt") || fileName.endsWith(".lbi")) {
					String destFileName = fileName.replace("dwt", "ftl");
					destFileName = destFileName.replace("lbi", "ftl");
					String source = IOUtils.toString(new FileInputStream(file), ENC);
					String out = new DWTConverter().convert(source, fileName);
					
					// NOTE this is not allowed in GAE environment
//					IOUtils.write(out, new FileOutputStream(new File(sDestDir, destFileName)), ENC);
				}
				else {
					FileUtils.copyFile(file, new File(sDestDir, fileName));
				}
				}
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
	}
	
	public void testCompileIfTag() {
		try {
//			String tag = "$item.type eq \"snatch\"";
			
//			String tag = "$img_links  or $txt_links "; 
			
			String tag = "$item.type eq \"group_buy\"";
			String res  = new DWTConverter().compileIfTag(tag, true);
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
			String tag = " from =$spec.values item=value key=key ";
//			String tag = "from=$group_buy_goods item=goods";
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
			
//			String tag = "<a href=\"{$nav.url}\" <!-- {if $nav.opennew eq 1} --> \r\n target=\"_blank\" <!-- {/if} -->>{$nav.name}</a>";
//			String tag = "{$nav.url}  <!-- {if $nav.opennew eq 1} --> ";
//			String tag = "{if empty($order_query)}";
//			String tag = "{insert name='vote'}";
			String tag = "{foreach from=$promotion_goods item=goods name=\"promotion_foreach\"}";
//			String tag = "{foreach name=nav_top_list from=$navigator_list.top item=nav}";
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
//			String tag = "$lang.far_ext[$favourable.act_range]";
			
//			String tag = "* ECSHOP 提醒您：根据用户id来调用member_info.lbi显示不同的界面  *";
//			String tag = "insert name='member_info'";
			
//			String tag = "if !$smarty.foreach.nav_top_list.last";
			
			String tag = "<a href=\"{$nav.url}\" <!-- {if $nav.opennew eq 1} --> target=\"_blank\" <!-- {/if} -->>{$nav.name}</a>";
			DWTConverter dwtConverter = new DWTConverter();
			dwtConverter.foreachStack.push("abc");
			String res  = dwtConverter.select(tag);
			
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
