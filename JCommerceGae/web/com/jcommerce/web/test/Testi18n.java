package com.jcommerce.web.test;

import java.text.NumberFormat;
import java.util.Locale;

import junit.framework.TestCase;

public class Testi18n extends TestCase {
	public void testCurrency() {
		double price = 0.20;
		Locale currentLocale = Locale.CHINA;
		NumberFormat nf = NumberFormat.getCurrencyInstance(currentLocale);
		String res = nf.format(price);
		
		System.out.println("res="+res);
	}
}
