package com.jcommerce.core.test;

import com.google.apphosting.api.ApiProxy;

class TestEnvironment implements ApiProxy.Environment {
    public String getAppId() {
        return "gcloudshop";
    }

    public String getVersionId() {
        return "1";
    }

    public void setDefaultNamespace(String s) { }

    public String getRequestNamespace() {
        return "gmail.com";
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
}
