package com.jcommerce.web.component;

public class ComponentUrl {
    private String url;
    private boolean opennew;
    private String name;
    private boolean active = false;
    
    public boolean isActive() {
        return active;
    }
    public void setActive(boolean active) {
        this.active = active;
    }
    public ComponentUrl(String url, String name, boolean opennew, boolean active) {
        this.name = name;
        this.url = url;
        this.opennew = opennew;
        this.active = active;
    }
    public ComponentUrl(String url, String name, boolean opennew) {
        this(url, name, opennew, false);
    }
    public String getUrl() {
        return url;
    }
    public void setUrl(String url) {
        this.url = url;
    }
    public boolean isOpennew() {
        return opennew;
    }
    public void setOpennew(boolean opennew) {
        this.opennew = opennew;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
}
