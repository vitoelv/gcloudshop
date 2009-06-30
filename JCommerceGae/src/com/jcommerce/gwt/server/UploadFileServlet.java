package com.jcommerce.gwt.server;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItemIterator;
import org.apache.commons.fileupload.FileItemStream;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.fileupload.util.Streams;

public class UploadFileServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        super.doPost(request, response);
    }
    
    @SuppressWarnings("unchecked")
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("==========================UploadFileServlet doPost()==================================");
        
        int contentLength = request.getContentLength();
        System.out.println("contentLength: " + contentLength);
        
        boolean isMultipart = ServletFileUpload.isMultipartContent(request);
        if (contentLength < 0 || !isMultipart) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST);
            return;

        }
        
     // Create a new file upload handler
        ServletFileUpload upload = new ServletFileUpload();
        String data = null;
        try {
			// Parse the request
			FileItemIterator iter = upload.getItemIterator(request);
			while (iter.hasNext()) {
			    FileItemStream item = iter.next();
			    String name = item.getFieldName();
			    InputStream stream = item.openStream();
			    if (item.isFormField()) {
			        System.out.println("Form field " + name + " with value "
			            + Streams.asString(stream) + " detected.");
			    } else {
			        System.out.println("File field " + name + " with file name "
			            + item.getName() + " detected.");
			        // Process the input stream
			        
			        
			        ByteArrayOutputStream baos = new ByteArrayOutputStream();
                    //only write files out that are less than 1MB
                    if (contentLength < (4*1024000)) {

                        byte[] buffer = new byte[8192];
                        int bytesRead = 0;
                        while ((bytesRead = stream.read(buffer, 0, 8192)) != -1) {
                            baos.write(buffer, 0, bytesRead);
                        }
                        data = new String(baos.toByteArray());
                    }
                    else {
                        data = new String("The file is greater than 4MB, " +
                                " and has not been written to stream." +
                                " contentLength: " + contentLength + " bytes. This is a" +
                                " limitation of this particular web application, hard-coded" +
                                " in org.apache.struts.webapp.upload.UploadAction");
                    }
                    
                    System.out.println("data: "+data);
                    
			    }
			}
		} catch (FileUploadException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
		System.out.println("==========================end of UploadFileServlet doPost()==================================");
    	
		response.getWriter().print("<b>upload succeeded! content: </b></br>"+data);
    }
}
