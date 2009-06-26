/**
* Author: Bob Chen
*/

package com.jcommerce.gwt.client.panels.goods;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.extjs.gxt.ui.client.widget.LayoutContainer;
import com.google.gwt.user.client.ui.ChangeListener;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.TextArea;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;
import com.jcommerce.gwt.client.ModelNames;
import com.jcommerce.gwt.client.form.BeanObject;
import com.jcommerce.gwt.client.model.IAttribute;
import com.jcommerce.gwt.client.model.IGoods;
import com.jcommerce.gwt.client.model.IGoodsAttribute;
import com.jcommerce.gwt.client.model.IGoodsType;
import com.jcommerce.gwt.client.service.Condition;
import com.jcommerce.gwt.client.service.Criteria;
import com.jcommerce.gwt.client.service.ListService;
import com.jcommerce.gwt.client.service.ReadService;
import com.jcommerce.gwt.client.widgets.ColumnPanel;

class AttributePanel extends LayoutContainer {
    SimplePanel contentPanel = new SimplePanel();
    ListBox lstTypes = new ListBox();
    Map<String, BeanObject> types = new HashMap<String, BeanObject>();
    
    AttributePanel() {
        init();
    }
    
    void updateValues(final BeanObject goods) {
        if (goods == null) {
            lstTypes.setSelectedIndex(0);
        }
        else{
        String type = goods.getString(IGoods.TYPE);
        int size = lstTypes.getItemCount();
        for (int i = 0 ; i < size ; i++) {
            if (type.equals(lstTypes.getValue(i))) {
                lstTypes.setSelectedIndex(i);
                break;
            }
        }
        
        Criteria criteria = new Criteria();
        criteria.addCondition(new Condition(IAttribute.GOODSTYPE, Condition.EQUALS, type));
        new ListService().listBeans(ModelNames.ATTRIBUTE, criteria, new ListService.Listener() {
            public void onSuccess(List<BeanObject> attrs) {
                createWidgets(attrs);
                updateGoodsAttributes(goods);
            }
        });
        }
    }
    
    private void updateGoodsAttributes(BeanObject goods) {
//        String[] gattrs = goods.getIDs(IGoods.ATTRIBUTES);
//        if (gattrs != null) {
//            final ColumnPanel panel = (ColumnPanel)contentPanel.getWidget();
//            for (final String attr : gattrs) {
//                new ReadService().getBean(ModelNames.GOODSATTRIBUTE, attr, new ReadService.Listener() {
//                    public void onSuccess(BeanObject bean) {
//                        panel.setValue(bean.getString(IGoodsAttribute.ATTRIBUTE), bean.getString(IGoodsAttribute.VALUE));
//                    }
//                });
//            }
//        }        
    }
    
    Map<String, Object> getValues() {
        Map<String, Object> values = new HashMap<String, Object>();
        int index = lstTypes.getSelectedIndex();
        if (index >= 0) {
            String type = lstTypes.getValue(index);
            values.put(IGoods.TYPE, type);
        }
        
        final ColumnPanel panel = (ColumnPanel)contentPanel.getWidget();
        Map<String, Object> attrsPanel = panel.getValues();
        
        List<Map> attrs = new ArrayList<Map>();
        for (Iterator it = attrsPanel.keySet().iterator(); it.hasNext();) {
            String id = (String) it.next();
            Map<String, Object> attr = new HashMap<String, Object>();
            attr.put(IGoodsAttribute.ATTRIBUTE, id);
            attr.put(IGoodsAttribute.VALUE, attrsPanel.get(id));
            attrs.add(attr);
        }
//        values.put(IGoods.ATTRIBUTES, attrs);
        
        return values;
    }
    
    private void init() {
        lstTypes.addChangeListener(new ChangeListener() {
            public void onChange(Widget sender) {
                updateType(lstTypes);
            }
        });

        add(lstTypes);
        
        new ListService().listBeans(ModelNames.GOODSTYPE, new ListService.Listener() {
            public void onSuccess(List<BeanObject> beans) {
                for (BeanObject obj : beans) {
                    String id = obj.getString(IGoodsType.ID);
                    lstTypes.addItem(obj.getString(IGoodsType.NAME), id);
                    types.put(id, obj);
                }
                
                updateType(lstTypes);
            }
        });

        add(contentPanel);        
    }

    private void updateType(ListBox lstTypes) {
        int index = lstTypes.getSelectedIndex();
        if (index < 0) {
            return;
        }

        String type = lstTypes.getValue(index);
        Criteria criteria = new Criteria();
        criteria.addCondition(new Condition(IAttribute.GOODSTYPE, Condition.EQUALS, type));
        new ListService().listBeans(ModelNames.ATTRIBUTE, criteria, new ListService.Listener() {
            public void onSuccess(List<BeanObject> attrs) {
                createWidgets(attrs);
            }
        });
    }
    
    private void createWidgets(List<BeanObject> attrs) {
        ColumnPanel panel = new ColumnPanel();
        
        for (BeanObject attr : attrs) {
            String id = attr.getString(IAttribute.ID);
            String name = attr.getString(IAttribute.NAME);            
            int inputType = ((Number)attr.get(IAttribute.INPUTTYPE)).intValue();
            String values = attr.getString(IAttribute.VALUES);
            if (inputType == IAttribute.INPUT_SINGLELINETEXT) {
                panel.createPanel(id, name, new TextBox());
            } else if (inputType == IAttribute.INPUT_MULTIPLELINETEXT) {
                panel.createPanel(id, name, new TextArea());
            } else if (inputType == IAttribute.INPUT_CHOICE) {
                ListBox lst = new ListBox();
                if (values != null) {
                    String[] vs = values.split("\n");
                    for (String s : vs) {
                        lst.addItem(s);
                    }
                }
                panel.createPanel(id, name, lst);
            } else {
                throw new RuntimeException("Unknown input type: " + inputType);
            }
        }
        
        contentPanel.setWidget(panel);
    }
}
