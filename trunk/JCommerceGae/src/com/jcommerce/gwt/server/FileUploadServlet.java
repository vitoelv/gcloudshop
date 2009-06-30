/**
 * Author: Bob Chen
 */

package com.jcommerce.gwt.server;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

public class FileUploadServlet extends HttpServlet {    
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException,
            IOException {
        response.setContentType("text/plain");
        PrintWriter writer = response.getWriter();
        List<String> names = readFiles(request);
        for (String name : names) {
            writer.write(name+";");
        }
    }

    private List<String> readFiles(HttpServletRequest request) {
        List<String> names = new ArrayList<String>();
        
        FileItemFactory factory = new DiskFileItemFactory();
        ServletFileUpload upload = new ServletFileUpload(factory);
        try {
            String today = new SimpleDateFormat("yyMM").format(new Date());

            List items = upload.parseRequest(request);
            Iterator it = items.iterator();
            while (it.hasNext()) {
                FileItem item = (FileItem) it.next();
                if (item.isFormField() == false) {     
                    // 或直接保存成文件
//                    String name = item.getName();
//                    if (name.contains("\\")) {
//                        name = name.substring(name.lastIndexOf("\\") + 1);
//                    } else if (name.contains("/")) {
//                        name = name.substring(name.lastIndexOf("/") + 1);
//                    }
                    String name = "images/"+today+"/"+UUID.randomUUID().toString();
                    File file = new File(getServletContext().getRealPath(name));
                    if (!file.getParentFile().exists()) {
                        file.getParentFile().mkdirs();
                    }
                    item.write(file);// 直接保存文件
                    
                    names.add(name);
                }
            }
        } catch (FileUploadException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        return names;
    }
}
