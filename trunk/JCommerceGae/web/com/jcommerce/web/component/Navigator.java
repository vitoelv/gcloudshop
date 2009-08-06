package com.jcommerce.web.component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class Navigator {
    List top;
    List middle;
    Map<String, Object> config = new HashMap<String, Object>();
    
    public Navigator() {
        top = new ArrayList();
        middle = new ArrayList();
    }
    public Navigator(List list) {
        top = list;
    }
    public void addTop(ComponentUrl url) {
        top.add(url);
    }
    public void addMiddle(ComponentUrl url) {
        middle.add(url);
    }
    public List getTop() {
        return top;
    }
    public void setTop(List top) {
        this.top = top;
    }
    public List getMiddle() {
        return middle;
    }
    public void setMiddle(List middle) {
        this.middle = middle;
    }
	public Map<String, Object> getConfig() {
		return config;
	}
	public void setConfig(Map<String, Object> config) {
		this.config = config;
	}


}
