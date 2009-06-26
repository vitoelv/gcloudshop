/**
* Author: Bob Chen
*/

package com.jcommerce.gwt.client.panels.goods;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.extjs.gxt.ui.client.event.ButtonEvent;
import com.extjs.gxt.ui.client.event.SelectionListener;
import com.extjs.gxt.ui.client.widget.LayoutContainer;
import com.extjs.gxt.ui.client.widget.button.Button;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.jcommerce.gwt.client.ModelNames;
import com.jcommerce.gwt.client.form.BeanObject;
import com.jcommerce.gwt.client.model.IGoods;
import com.jcommerce.gwt.client.service.ReadService;
import com.jcommerce.gwt.client.widgets.FileUploader;

class GalleryPanel extends LayoutContainer {
    VerticalPanel contentPanel = new VerticalPanel();
    
    ListBox lstTypes = new ListBox();
    Map<String, BeanObject> types = new HashMap<String, BeanObject>();
    List<FileUploader> uploaders = new ArrayList<FileUploader>();
    
    GalleryPanel() {
        init();
    }
    
    void updateValues(final BeanObject goods) {
//        if (goods == null) {
//            throw new IllegalArgumentException("goods == null");
//        }
//
//        String[] ids = goods.getIDs(IGoods.GALLERIES);
//        if (ids == null || ids.length == 0) {
//            return;
//        }
//        
//        for (String id : ids) {
//            new ReadService().getBean(ModelNames.ATTRIBUTE, id, new ReadService.Listener() {
//                public void onSuccess(BeanObject bean) {
//                }
//            });
//        }
    }
    
    private void updateGoodsAttributes(BeanObject goods) {
//        String[] gattrs = goods.getIDs(IGoods.ATTRIBUTES);
//        if (gattrs != null) {
////            final ColumnPanel panel = (ColumnPanel)contentPanel.getWidget();
////            for (final String attr : gattrs) {
////                new ReadService().get(ModelNames.GOODSATTRIBUTE, attr, new ReadService.Listener() {
////                    public void onSuccess(BeanObject bean) {
////                        panel.setValue(bean.getString(IGoodsAttribute.ATTRIBUTE), bean.getString(IGoodsAttribute.VALUE));
////                    }
////                });
////            }
//        }        
    }
    
    Map<String, Object> getValues() {
        Map<String, Object> values = new HashMap<String, Object>();
        int index = lstTypes.getSelectedIndex();
        if (index >= 0) {
            String type = lstTypes.getValue(index);
            values.put(IGoods.TYPE, type);
        }
        
//        final ColumnPanel panel = (ColumnPanel)contentPanel.getWidget();
//        Map<String, Object> attrsPanel = panel.getValues();
//        
//        List<Map> attrs = new ArrayList<Map>();
//        for (Iterator it = attrsPanel.keySet().iterator(); it.hasNext();) {
//            String id = (String) it.next();
//            Map<String, Object> attr = new HashMap<String, Object>();
//            attr.put(IGoodsAttribute.ATTRIBUTE, id);
//            attr.put(IGoodsAttribute.VALUE, attrsPanel.get(id));
//            attrs.add(attr);
//        }
//        values.put(IGoods.ATTRIBUTES, attrs);
        
        return values;
    }
    
    private void init() {
        SimplePanel holder = new SimplePanel();
        add(holder);
        holder.add(contentPanel);
        
        Button link = new Button("[+]");
        final FileUploader imgUpload = new FileUploader();
        createUploader(link, imgUpload);
        
        link.addSelectionListener(new SelectionListener<ButtonEvent>() {
            public void componentSelected(ButtonEvent ce) {
                addUploader();
            }
        });
    }

    private void addUploader() {
        Button link = new Button("[-]");
        final FileUploader imgUpload = new FileUploader();
        final HorizontalPanel panel = createUploader(link, imgUpload);

        link.addSelectionListener(new SelectionListener<ButtonEvent>() {
            public void componentSelected(ButtonEvent ce) {
                contentPanel.remove(panel);
                uploaders.remove(imgUpload);
            }
        });
    }
    
    private HorizontalPanel createUploader(Button link, FileUploader imgUpload) {
        HorizontalPanel panel = new HorizontalPanel();
        
        panel.add(link);
        
        Label label = new Label(" Description");
        TextBox txt = new TextBox();
        panel.add(label);
        panel.add(txt);
        
        panel.add(new Label(" Upload File"));
        imgUpload.addAllowedTypes(new String[]{".jpg", ".gif"});
        panel.add(imgUpload);
        
        contentPanel.add(panel);
 
        uploaders.add(imgUpload);
        
        return panel;
    }
}
