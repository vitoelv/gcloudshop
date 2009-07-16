package com.jcommerce.web.component;

import java.util.ArrayList;
import java.util.List;


public class Navigator {
    List top;
    List middle;
    
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
}
