package com.jcommerce.gwt.server;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.logging.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;


/**
 * This class has multiple usage: 
 * 1) to be accessed by the cron job and log some text as proof of living JVM
 * 2) to be accessed from a simple form and return the document at the requested URL  
 * @author yli
 *
 */

public class MyServlet extends HttpServlet {
	private static final Logger log = Logger.getLogger(MyServlet.class.getName());
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		try {
			// use warning to make sure it will print
			log.warning("hello, My cron job");
			resp.getWriter().write("hello, My cron job");
			
		} catch (Exception ex) {
			log.warning("exception thrown: "+ex.getMessage());
			throw new RuntimeException(ex);
		}
	
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		try {
			log.entering(MyServlet.class.getName(), "doPost");
			
			String urlStr = (String)req.getParameter("url");
			log.warning("preparing to fetch URL : "+urlStr);
			if(StringUtils.isEmpty(urlStr)) {
				// en empty request. probably from cron job??
				doGet(req, resp);
			}
			else {
				// URL fetching...
	            URL url = new URL(urlStr);
	            BufferedReader reader = new BufferedReader(new InputStreamReader(url.openStream()));
	            String line;
	            BufferedWriter writer = new BufferedWriter(resp.getWriter());
	            while ((line = reader.readLine()) != null) {
	            	writer.write(line+"\r\n");
	            }
	            reader.close();
	            writer.flush();
	            writer.close();
			}
			log.exiting(MyServlet.class.getName(), "doPost");
		} catch (Exception ex) {
			log.warning("exception thrown: "+ex.getMessage());
			throw new RuntimeException(ex);
		}
	}

}
