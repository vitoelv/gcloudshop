package com.jcommerce.web.test;

import com.opensymphony.xwork2.ActionInvocation;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import freemarker.template.TemplateModel;

import java.io.IOException;
import java.util.logging.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.views.freemarker.FreemarkerResult;

public class MyFreeMarkerResult extends FreemarkerResult {
    
    private static final Logger log = Logger.getLogger(MyFreeMarkerResult.class.getName());
    
    Long time1, time2;
    
    Long time3, time4;
    
    @Override
    protected void postTemplateProcess(
            Template template,
            TemplateModel data) throws IOException
    {
        time2 = System.currentTimeMillis();
        
        
        
    }
    
    public void doExecute(String locationArg, ActionInvocation invocation) throws IOException, TemplateException {
        time3 = System.currentTimeMillis();
        log.warning("locationArg="+locationArg);
        super.doExecute(locationArg, invocation);
        time4 = System.currentTimeMillis();
        long ts1 = time2-time1;
        long ts2 = time4-time3;
        StringBuffer buf = new StringBuffer();
        buf.append("================rendering=")
            .append(ts1).append(", s=").append(time1).append(", e=").append(time2)
            .append("<br>\r\n")
            .append(", templateall=")
            .append(ts2).append(", s=").append(time3).append(", e=").append(time4)
            .append("<br>\r\n");
        String warn = buf.toString();
//        log.warning(warn);
        
        HttpServletRequest request = ServletActionContext.getRequest();
        String perfStats = (String)request.getAttribute("perfStats");
        perfStats+=warn;
        request.setAttribute("perfStats", perfStats);
    }
    
    @Override
    protected boolean preTemplateProcess(Template template, TemplateModel data)
            throws IOException {
        
        super.preTemplateProcess(template, data);
        time1 = System.currentTimeMillis();
        return true;
        
    }
    
    
    private void postProcess(HttpServletRequest request, HttpServletResponse response)
        throws IOException, TemplateException {
        
        String path = location;
        log.info("template path: "+path);
        Configuration config = getConfiguration();
        
        if(path.endsWith("index.ftl")) {
            // home page, go on with testing
            
//            long time1 = System.currentTimeMillis();
//            path = "library/recommend_best.ftl";
//            Template template = null;
//            try {
//                template = config.getTemplate(path,
//                        deduceLocale(path, request, response));
//            } catch (FileNotFoundException e) {
//                e.printStackTrace();
//                return;
//            }
            
            
            
            
        }
        
    }
    
}
