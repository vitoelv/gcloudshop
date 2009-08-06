package com.jcommerce.core.test.dwt;



import junit.framework.TestCase;

import org.apache.oro.text.regex.MalformedPatternException;
import org.apache.oro.text.regex.Pattern;
import org.apache.oro.text.regex.PatternCompiler;
import org.apache.oro.text.regex.PatternMatcher;
import org.apache.oro.text.regex.Perl5Compiler;
import org.apache.oro.text.regex.Perl5Matcher;
import org.apache.oro.text.regex.Perl5Substitution;
import org.apache.oro.text.regex.Util;

public class TestJarkataORO extends TestCase {
	
	

	
	
	public void testConvertORO() {
		try {
			String link = "<a href=\"http://widgets.acme.com/interface.html#How_To_Trade\">";
			String reg = "<\\s*a\\s+href\\s*=\\s*\"http://widgets.acme.com/interface.html#([^\"]+)\">";
			
			PatternCompiler compiler = new Perl5Compiler();
			Pattern pattern = compiler.compile(reg, Perl5Compiler.CASE_INSENSITIVE_MASK);
			
			PatternMatcher matcher = new Perl5Matcher();
			
			String res = Util.substitute(matcher, pattern, new Perl5Substitution("XXX"), link, Util.SUBSTITUTE_ALL);
			
			System.out.println("res: "+res);
			
		} catch (MalformedPatternException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}
	public void testConvertORO2() {
		try {
			String in = "<!-- #BeginLibraryItem \"/library/cart.lbi\" -->";
			
			String reg = "<!--\\s#BeginLibraryItem\\s\"/(.*?)\"\\s-->.*?<!--\\s#EndLibraryItem\\s-->";
			
			PatternCompiler compiler = new Perl5Compiler();
			Pattern pattern = compiler.compile(reg, Perl5Compiler.CASE_INSENSITIVE_MASK);
			
			PatternMatcher matcher = new Perl5Matcher();
			
			String res = Util.substitute(matcher, pattern, new Perl5Substitution("<#include \"\\1\">"), in, Util.SUBSTITUTE_ALL);
			
			System.out.println("res: "+res);
			
		} catch (MalformedPatternException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}
}
