package com.jcommerce.web.front.action;

import com.google.appengine.api.images.Image;
import com.google.appengine.api.images.ImagesServiceFactory;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;
import org.toyz.litetext.FontUtils;

@SuppressWarnings("serial")
public class CaptchaAction extends ActionSupport {

		private InputStream output = null;
	
        public InputStream getOutput() {
        	return output;
		}

		public void setOutput(InputStream output) {
			this.output = output;
		}

		public void generateCaptcha(HttpServletRequest request) throws IOException {

        String fontname = request.getParameter("f");
        if (fontname == null || fontname.length() == 0) {
            fontname = "LucidaTypewriter-Bold";
        }

        Random random = new Random();
        StringBuffer sb = new StringBuffer();

        char[] ch = "ABCDEFGHIJKLMNOPQRSTUVWXYZ23456789".toCharArray();

        int index, len = ch.length;

        for (int i = 0; i < 4; i++) {
            index = random.nextInt(len);
            sb.append(ch[index]);
        }
        FontUtils fm = new FontUtils();
        byte[] bmp_data = fm.doRender(sb.toString(), fontname);

        request.getSession().setAttribute("captcha", sb.toString());

        com.google.appengine.api.images.ImagesService imagesService = ImagesServiceFactory
                .getImagesService();

        Image bmpImage = ImagesServiceFactory.makeImage(bmp_data);
        com.google.appengine.api.images.Transform flipit = ImagesServiceFactory
                .makeVerticalFlip();
        Image newImage = imagesService
                .applyTransform(
                        flipit,
                        bmpImage,
                        com.google.appengine.api.images.ImagesService.OutputEncoding.JPEG);

        // response.setContentType("image/jpeg");
        java.io.ByteArrayInputStream io = new ByteArrayInputStream(newImage
                .getImageData());

        setOutput(io);
        
    }
        
        public String execute(){
        	ActionContext ctx = ActionContext.getContext();        
	        HttpServletRequest request = (HttpServletRequest)ctx.get(ServletActionContext.HTTP_REQUEST);     
        	try {
        	    // this exception: ognl.InappropriateExpressionException: Inappropriate OGNL expression: 0.9284601025283337
        	    // will be thrown when clicking the pic(to request another one)
        	    // this is because the javascript: onClick="this.src='captcha.action?'+Math.random()" constructed a wrong URL, 
        	    // and xWork2 intercepter: ParametersInterceptor.setParameters will check and throw the exception
        	    
        	    // DO NOT fix at this point.
        	    
				generateCaptcha(request);
			} catch (IOException e) {
				e.printStackTrace();
			}
        	
        	return SUCCESS;
        }
}
