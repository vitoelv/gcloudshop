package com.jcommerce.core.test.web;

import com.jcommerce.web.to.Lang;
import com.jcommerce.web.util.PrintfFormat;

import junit.framework.TestCase;

public class TestPrintfFormat extends TestCase {
	
	public void testPrintString() {
		String res = new PrintfFormat(Lang.getInstance().getString("thanMarketPrice")).sprintf(new Object[]{"1.1", "2.2", "3"});
		System.out.println("res="+res);
		
		res = new PrintfFormat(Lang.getInstance().getString("shoppingMoney")).sprintf("$5.2");
		System.out.println("res="+res);
	}
}
