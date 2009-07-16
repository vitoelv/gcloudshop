package com.jcommerce.core.action;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;



public class BeanObject implements Serializable {
    private String modelName;
    private Map<String, Object> values = new HashMap<String, Object>();
    
    public BeanObject() {
    }

    public BeanObject(String modelName) {
        this.modelName = modelName;
    }

    public BeanObject(String modelName, Map<String, Object> values) {
        this.modelName = modelName;
        setValues(values);
    }

    public String getModelName() {
        return modelName;
    }

    public void setModelName(String modelName) {
        this.modelName = modelName;
    }

    public void setValues(Map<String, Object> values) {
        this.values.clear();
        
        if (values == null) {
            return;
        }
        
        for (Iterator it = values.keySet().iterator(); it.hasNext();) {
            String name = (String) it.next();
            Object value = values.get(name);
            set(name, value);
        }
    }
    
    public String[] getIDs(String name) {
        Object value = get(name);
        if (!(value instanceof String)) {
            throw new RuntimeException("Invalid value:"+value+" name:"+name);
        }
        
        return name.split(",");
    }
    
    public String getString(String name) {
        Object value = get(name);
        if (value == null) {
            return null;
        }
        
        return value.toString();
    }
    
    public void set(String name, Object value) {
        values.put(name, value);
    }
    
    public Object get(String name) {
        return values.get(name);
    }
}
