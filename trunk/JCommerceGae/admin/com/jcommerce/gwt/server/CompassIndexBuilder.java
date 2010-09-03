package com.jcommerce.gwt.server;

import com.jcommerce.core.dao.impl.PMF;

import java.io.IOException;
import java.util.logging.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class CompassIndexBuilder extends HttpServlet {
	private static final Logger log = Logger.getLogger(CompassIndexBuilder.class.getName());
		
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		try {
			// use warning to make sure it will print
			log.warning("hello, My cron job");
			log.info("begin compass index...");
            long beginTime = System.currentTimeMillis();
            // 重建索引.
            // 如果compass实体中定义的索引文件已存在，索引过程中会建立临时索引，
            // 索引完成后再进行覆盖.
            PMF.getCompassGps().start();
            PMF.getCompassGps().index();
            PMF.getCompassGps().stop();
            long costTime = System.currentTimeMillis() - beginTime;
            log.info("compss index finished.");
            log.info("costed " + costTime + " milliseconds");
            
                String respText = "index built successfully within "+costTime+" msec";
			resp.getOutputStream().write(respText.getBytes());
		} catch (Exception ex) {
			log.warning("exception thrown: "+ex.getMessage());
			throw new RuntimeException(ex);
		}
	
	}   
    

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		doGet(req,resp);
	}
}
