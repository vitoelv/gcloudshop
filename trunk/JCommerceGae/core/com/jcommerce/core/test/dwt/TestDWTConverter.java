package com.jcommerce.core.test.dwt;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import junit.framework.TestCase;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;

public class TestDWTConverter extends TestCase {

//	Set<String> langKeys = new HashSet<String>();
	Map<String, Object> values = new HashMap<String, Object>();
	String ENC = "UTF-8";
	
	public void testConvert() {

		String sBaseDir = "D:/JCommerce/ECShop_V2.6.1_UTF8_build1208/upload/themes/default";
		String sDestDir = "D:/JCommerce/ECShop_V2.6.1_UTF8_build1208/upload/themes/freemarker";
		
		
		testConvertDir(sBaseDir, sDestDir);

		
//		List<String> list = new ArrayList<String>();
//		list.addAll(langKeys);
//		Collections.sort(list);
//		for(String langKey:list) {
//			buf.append(langKey).append(" = ").append(langKey).append("\r\n");
//		}
//		try {
//			IOUtils.write(buf.toString(), new FileOutputStream(new File(sDestPropertyDir, langFn)), "UTF-8");
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		
	}
	
	public void testPrepareResource() {
		String langFn = "lang_zh.properties";
		// this is only web resource
		String sPhpResourceDir = "D:/JCommerce/ECShop_V2.6.1_UTF8_build1208/upload/languages/zh_cn";
		String sDestPropertyDir = "D:/JCommerce/ECShop_V2.6.1_UTF8_build1208/upload/themes";


		// select lang values from php
		testExtractResource(sPhpResourceDir);
		
		List<String> keyList = new ArrayList<String>();
		keyList.addAll(values.keySet());
		Collections.sort(keyList);
		
		StringBuffer buf = new StringBuffer();
		for (String key : keyList) {
			Object obj = values.get(key);
			if(obj instanceof Map) {
				Map<String, String> map = (Map<String, String>)obj;
				for(String k1:map.keySet()) {
					buf.append(key).append("[").append(k1).append("]").append(" = ").append(map.get(k1)).append("\r\n");
				}
			}
			else if(obj instanceof List) {
				List<String> list = (List<String>)obj;
				for(int i=0;i<list.size();i++) {
					buf.append(key).append("[").append(i).append("]").append(" = ").append(list.get(i)).append("\r\n");
				}
			}
			else {
				buf.append(key).append(" = ").append(obj).append("\r\n");
			}
		}
		try {
			// this is not allowed in GAE
//			IOUtils.write(buf.toString(), new FileOutputStream(new File(
//					sDestPropertyDir, langFn)), "UTF-8");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
	}
	public void testExtractResource(String sPhpConstDir) {
		try {
			File fBaseDir = new File(sPhpConstDir);
			File[] files = fBaseDir.listFiles();
			for(File file: files) {
				String fileName = file.getName();
				if(file.isDirectory()) {
					// TODO now we do not process sub-folders
					continue;
				}
				System.out.println("processing file: "+fileName);
				String source = IOUtils.toString(new FileInputStream(file), ENC);
				
				new DWTConverter().findLangVals(source, values);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void testConvertDir(String sBaseDir, String sDestDir) {
		try {
			File destDir = new File(sDestDir);
			if(!destDir.exists()) {
				destDir.mkdir();
			}
			

			
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
					
					// select lang keys from converted ftl
//					langKeys.addAll(new DWTConverter().findLangKeys(out));
					
					// this is not allowed in GAE
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
}
