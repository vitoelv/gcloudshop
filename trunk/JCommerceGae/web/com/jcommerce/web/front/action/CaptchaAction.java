package com.jcommerce.web.front.action;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.Random;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;
import org.toyz.litetext.FontUtils;

import com.google.appengine.api.images.Image;
import com.google.appengine.api.images.ImagesServiceFactory;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

@SuppressWarnings("serial")
public class CaptchaAction extends ActionSupport {

		private ServletOutputStream output = null;
	
        public ServletOutputStream getOutput() {
        	return output;
		}

		public void setOutput(ServletOutputStream output) {
			this.output = output;
		}

		public void generateCaptcha(HttpServletRequest request, HttpServletResponse response)
                        throws IOException {
 
        String fontname = request.getParameter("f");
        if (fontname == null || fontname.length() == 0) {
                fontname = "LucidaTypewriter-Bold";
        }       

        Random random = new Random();
        StringBuffer sb = new StringBuffer();

        char[] ch = "ABCDEFGHIJKLMNOPQRSTUVWXYZ23456789".toCharArray();

        int index, len = ch.length;

        for (int i = 0; i < 4; i ++) {
               index = random.nextInt(len);
               sb.append(ch[index]);
        }
        FontUtils fm = new FontUtils();
        byte[] bmp_data = fm.doRender(sb.toString(), fontname);
           
        request.getSession().setAttribute("captcha", sb.toString());
        
        com.google.appengine.api.images.ImagesService imagesService = ImagesServiceFactory.getImagesService();

                Image bmpImage = ImagesServiceFactory.makeImage(bmp_data);
        com.google.appengine.api.images.Transform flipit = ImagesServiceFactory.makeVerticalFlip();
        Image newImage = imagesService.applyTransform(flipit, bmpImage, com.google.appengine.api.images.ImagesService.OutputEncoding.JPEG);
    
                response.setContentType("image/jpeg");
                java.io.ByteArrayInputStream io = new ByteArrayInputStream(
                                newImage.getImageData());

                ServletOutputStream svout = response.getOutputStream();
                output = svout;
                int c = -1;
                while ((c = io.read()) != -1) {
                        svout.write(c);
                }
                io.close();
                svout.close();
        }
        
        public String execute(){
        	ActionContext ctx = ActionContext.getContext();        
	        HttpServletRequest request = (HttpServletRequest)ctx.get(ServletActionContext.HTTP_REQUEST);      
	        HttpServletResponse response = (HttpServletResponse)ctx.get(ServletActionContext.HTTP_RESPONSE);      
        	try {
				generateCaptcha(request,response);
			} catch (IOException e) {
				e.printStackTrace();
			}
        	
        	return SUCCESS;
        }
}
