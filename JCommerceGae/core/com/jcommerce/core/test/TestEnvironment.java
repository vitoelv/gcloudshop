package com.jcommerce.core.test;

import java.util.HashMap;
import java.util.Map;

import com.google.apphosting.api.ApiProxy;

class TestEnvironment implements ApiProxy.Environment {
    public String getAppId() {
        return "gcloudshop";
    }

    public String getVersionId() {
        return "2";
    }

    public void setDefaultNamespace(String s) { }

    public String getRequestNamespace() {
        return "";
    }

    public String getDefaultNamespace() { 
        return "";
    }

    public String getAuthDomain() {
        return "gmail.com";
    }

    public boolean isLoggedIn() {
        return false;
    }

    public String getEmail() {
        return "";
    }

    public boolean isAdmin() {
        return false;
    }

	public Map<String, Object> getAttributes() {
        Map<String, Object> map = new HashMap<String, Object> ();
        return map; 
	}
}
