/**
* Author: Bob Chen
*/

package com.jcommerce.gwt.client.widgets;

import java.util.ArrayList;
import java.util.List;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FileUpload;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.FormHandler;
import com.google.gwt.user.client.ui.FormPanel;
import com.google.gwt.user.client.ui.FormSubmitCompleteEvent;
import com.google.gwt.user.client.ui.FormSubmitEvent;

public class FileUploader extends Composite {
    private FormPanel formPanel;
    private List<String> types = new ArrayList<String>();
    private String fileName;
    private boolean success = true;
    private boolean needFileName = false;
    
    public FileUploader() {
        formPanel = new FormPanel();
        formPanel.setAction(GWT.getModuleBaseURL() + "/uploadService");

        formPanel.setEncoding(FormPanel.ENCODING_MULTIPART);
        formPanel.setMethod(FormPanel.METHOD_POST);

        FlowPanel panel = new FlowPanel();
        formPanel.setWidget(panel);

        final FileUpload fileUpload = new FileUpload();
        fileUpload.setName("file");
        panel.add(fileUpload);

        formPanel.addFormHandler(new FormHandler() {
            public void onSubmit(FormSubmitEvent event) {
                if (fileUpload.getFilename().length() == 0) {
                    event.setCancelled(true);
                    success = true;
                    needFileName = false;
                } else if (!isTypeAllowed(fileUpload.getFilename())) {
                    String msg = "Acceptable file type should be ";
                    boolean first = true;
                    for (String type : types) {
                        if (!first) {
                            msg += ",";
                        }
                        first = false;
                        msg += type;
                    }
                    Window.alert(msg);
                    event.setCancelled(true);
                    success = false;
                    needFileName = false;
                } else {
                    success = true;
                    needFileName = true;
                }
            }

            public void onSubmitComplete(FormSubmitCompleteEvent event) {
                String results = event.getResults();
                fileName = results;
                System.out.println("results:"+results);
                if (fileName.contains(">")) {
                    fileName = fileName.substring(fileName.indexOf(">") +1);
                }
                if (fileName.contains("<")) {
                    fileName = fileName.substring(0, fileName.indexOf("<"));
                }
                if (fileName.endsWith(";")) {
                    fileName = fileName.substring(0, fileName.length() -1);
                }
                // Window.alert(event.getResults());
            }
        });
        
//        Image image = iShop.images.no().createImage();
//        panel.add(image);
//        
//        image.addClickListener(new ClickListener() {
//            public void onClick(Widget sender) {
//            }
//        });
        
        initWidget(formPanel);
    }
    
    public boolean submit() {
        formPanel.submit();
        return success;
    }

    public boolean isFinish() {
        if (needFileName) {
            return fileName != null;
        } else {
            return true;
        }        
    }

    public String getValue() {
        return fileName;
    }
    
    public void setValue(String value) {
        this.fileName = value;
    }

    /**
     * the type should be .jpg, .gif, etc 
     */
    public void addAllowedType(String type) {
        types.add(type.toLowerCase());
    }

    public void addAllowedTypes(String[] types) {
        if (types != null) {
            for (String type : types) {
                this.types.add(type.toLowerCase());
            }
        }
    }
    
    private boolean isTypeAllowed(String name) {
        name = name.toLowerCase();
        for (String type : types) {
            if (name.endsWith(type)) {
                return true;
            }
        }
        return false;
    }
}
