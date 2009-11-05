package com.jcommerce.gwt.client;

//import it.sauronsoftware.base64.Base64;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.gwt.http.client.URL;
import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.History;
import com.jcommerce.gwt.client.Logger;

public abstract class PageState implements Command{


	
	
	public Map<String, Object> map = new HashMap<String, Object>();
	
	public String getMenuDisplayName() {
		return "";
	}
	public abstract String getPageClassName();
	
	// will break if the value contains =&-_
	public final String EQUAL = "=";
	public final String AND = "&";
	public final String LEFT = "-";
	public final String RIGHT = "-";
	
	public final String EQUALCODE="%FFFFFF";
	public final String ANDCODE="%EEEEEE";
	public final String LEFTCODE="%CCCCC";
	public final String RIGHTCODE="%DDDDDD";
	
	public final void execute() {
		System.out.println("token: "+getFullHistoryToken());
		
		History.newItem(getFullHistoryToken());
	}
	public String getFullHistoryToken() {
		return getPageClassName()+getHistoryToken();
	}
	public String getHistoryToken() {
		StringBuffer uriBuf = new StringBuffer();
//		uriBuf.append(getPageName());
		for(String key : map.keySet()) {
			Object value = map.get(key);
			if(value==null) {
				continue;
			}
			uriBuf.append(AND).append(encode(key)).append(EQUAL);
			if(value instanceof List) {
				List<String> valueList = (List<String>)value;
				// TODO leon consider if valuelist is empty??
				uriBuf.append(LEFT);
				for(String s:valueList) {
					uriBuf.append(encode(s)).append(",");
				}
				uriBuf.append(RIGHT);
			}
			else {
				uriBuf.append(encode((String)value));
			}
		}
		return uriBuf.toString();
	}
	
	public String encode (String s){
		try {
			String res = s==null? "":s;
			
			res = res.replace(EQUAL, EQUALCODE);
			res = res.replace(AND, ANDCODE);
			res = res.replace(LEFT, LEFTCODE);
			res = res.replace(RIGHT, RIGHTCODE);
//			String res = Base64.encode(s);
			
//			byte[] ss = Base64.encode(Util.getBytes(s));
//			res = Util.toString(ss);
			
			res = URL.encode(res);
			
//			res = URLEncoder.encode(res, "UTF-8");
			
//			res = res.replace('+', '*');
//			res = res.replace('/', '-');
			Logger.getClientLogger().log("encode: before="+s+", after="+res);
			return res;
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}
	
	public String decode(String s){
		try {
			String res = s==null?"":s;
			
			res = URL.decode(res); 
			
//			res = res.replace('-', '/');
//			res = res.replace('*', '+');
			
			
//			res = URLDecoder.decode(res, "UTF-8");
			
			res = res.replace(EQUALCODE, EQUAL);
			res = res.replace(ANDCODE, AND );
			res = res.replace(LEFTCODE,LEFT);
			res = res.replace(RIGHTCODE, RIGHT);

			
//			byte[] ss = Base64.decode(Util.getBytes(s));
//			res = Util.toString(ss);
			
//			res = Base64.decode(s);
			Logger.getClientLogger().log("decode: before="+s+", after="+res);
			return res;
			
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

	public void fromHistoryToken(String uri) {
		// TODO simple version. need improve
		
		// &p1=abc&p2={opq,xyz}
		
		map.clear();
		if(uri == null || "".equals(uri)) 
			return;
		String[] ss = uri.split(AND);
		for(String s:ss) {
			// debug
			Logger.getClientLogger().log("s: "+s);
			String[] pair = s.split(EQUAL);
			String sKey = "";
			String sVal = "";
			if(pair.length==2) {
				sKey = pair[0];
				sVal = pair[1];
			}else if(pair.length == 1) {
				sKey = pair[0];
			}else {
				continue;
			}

			String key = decode(sKey);
			if (sVal.indexOf(LEFT) >= 0) {
				// p2={opq,xyz}
				String vv = sVal.substring(sVal.indexOf(LEFT) + 1, sVal.lastIndexOf(RIGHT));
				Logger.getClientLogger().log("vv: " + vv);
				String[] values = vv.split(",");
				List<String> valueList = new ArrayList<String>();
				for (String value : values) {
					valueList.add(decode(value));
				}
				map.put(key, valueList);
			} else {
				String value = decode(sVal);
				map.put(key, value);
			}

		}
	}
	
	public static String[] parseFullHistoryToken (String fullHistoryToken) {
    	String pageClassName = fullHistoryToken;
    	String stateStr = "";
    	int i=fullHistoryToken.indexOf("&");
    	if(i>=0) {
    		pageClassName = fullHistoryToken.substring(0, i);
    		stateStr = fullHistoryToken.substring(i);
    	}		
    	String[] res = new String[]{pageClassName, stateStr};
    	return res;
	}
	
	public void setValue(String key, Object value){
		map.put(key, value);
	}
	public Object getValue(String key){
		return map.get(key);
	}
	
	public static void main(String[] args) {
//		PageState ps = new PageState() {
//			public String getPageName() {
//				return "test";
//			}
//		};
//		ps.setValue("p1", "abc");
//		
//		List<String> p2 = new ArrayList<String>();
//		p2.add("张三");
//		p2.add("�?�四");
//		
//		ps.setValue("p2", p2);
//		
//		String token = ps.getHistoryToken();
//		Logger.getClientLogger().log("token: "+token);
//		
//		ps.fromHistoryToken(token);
//		Logger.getClientLogger().log("p1: "+ps.getValue("p1"));
//		Logger.getClientLogger().log("p2: ");
//		for(String value: (List<String>)(ps.getValue("p2"))) {
//			Logger.getClientLogger().log(value);
//		}
//		
//		token = ps.getHistoryToken();
//		Logger.getClientLogger().log("token again: "+token);
		
		
    	State newState = new State();
    	newState.setMessage("添加商�?类型�?功-=&");
    	
    	State choice1 = new State();
    	choice1.setValue("p1", "id1");
    	newState.addChoice("attribute", choice1.getFullHistoryToken());
    	String token = newState.getHistoryToken();
    	Logger.getClientLogger().log("token: "+token);
    	
    	newState.fromHistoryToken(token);
		Logger.getClientLogger().log("message: "+newState.getMessage());
		Logger.getClientLogger().log("choice: ");
		for(String[] value: (List<String[]>)(newState.getChoices())) {
			Logger.getClientLogger().log(value[0]+","+value[1]);
		}
    	
	}
	
	public static class State extends PageState {
		public static final String MESSAGE = "msg";
		public static final String CHOICES = "ch";
		public static final String SEPERATOR = "xxxxxx";
		
		public String getPageClassName() {
			return "mypage";
		}
		
		public void addChoice(String name, String historyToken) {
			List<String> choices = (List<String>)getValue(CHOICES);
			if(choices == null) {
				choices = new ArrayList<String>();
				setValue(CHOICES, choices);
			}
			choices.add(name+SEPERATOR+historyToken);
		}
		public List<String[]> getChoices() {
			List<String> choices = (List<String>)getValue(CHOICES);
			List<String[]> res = new ArrayList<String[]>();
			for(String choice:choices) {
				int i = choice.indexOf(SEPERATOR);
				String[] ss = new String[] {choice.substring(0, i), choice.substring(i+SEPERATOR.length())};
				res.add(ss);
			}
			return res;
		}
		public void setMessage(String message) {
			setValue(MESSAGE, message);
		}
		public String getMessage() {
			return (String)getValue(MESSAGE);
		}

	}
}
