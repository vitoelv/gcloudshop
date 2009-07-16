package com.jcommerce.core.test;

import junit.framework.TestCase;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.PostMethod;

public class TestHttpClient extends TestCase{
	
	public void testRequest() {
		
        // Create an instance of HttpClient.
        HttpClient client = new HttpClient();
        // Create a method instance.
        PostMethod method = new PostMethod("http://www.google.com");
        String name="idxxx@@T##WR#!@#@";
        String value="abc@#!(#@)!(@!";
        NameValuePair nvp = new NameValuePair(name, value);
        method.addParameter(nvp);
        method.setQueryString(new NameValuePair[]{nvp});
        String query = method.getQueryString();
        
        System.out.println("query: "+query);
	}
}
