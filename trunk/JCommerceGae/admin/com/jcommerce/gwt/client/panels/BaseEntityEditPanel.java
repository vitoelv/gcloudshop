package com.jcommerce.gwt.client.panels;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.extjs.gxt.ui.client.Style.HorizontalAlignment;
import com.extjs.gxt.ui.client.event.ButtonEvent;
import com.extjs.gxt.ui.client.event.Events;
import com.extjs.gxt.ui.client.event.SelectionListener;
import com.extjs.gxt.ui.client.store.ListStore;
import com.extjs.gxt.ui.client.widget.button.Button;
import com.extjs.gxt.ui.client.widget.form.CheckBox;
import com.extjs.gxt.ui.client.widget.form.ComboBox;
import com.extjs.gxt.ui.client.widget.form.Field;
import com.extjs.gxt.ui.client.widget.form.FormPanel;
import com.extjs.gxt.ui.client.widget.form.HiddenField;
import com.extjs.gxt.ui.client.widget.form.HtmlEditor;
import com.extjs.gxt.ui.client.widget.form.ListField;
import com.extjs.gxt.ui.client.widget.form.TextArea;
import com.extjs.gxt.ui.client.widget.form.TextField;
import com.google.gwt.user.client.Element;
import com.google.gwt.user.client.Window;
import com.jcommerce.gwt.client.ContentWidget;
import com.jcommerce.gwt.client.Logger;
import com.jcommerce.gwt.client.PageState;
import com.jcommerce.gwt.client.form.BeanObject;
import com.jcommerce.gwt.client.resources.Resources;
import com.jcommerce.gwt.client.service.CreateService;
import com.jcommerce.gwt.client.service.ReadService;
import com.jcommerce.gwt.client.service.UpdateService;


public abstract class BaseEntityEditPanel extends ContentWidget  {
	
	public abstract static class State extends PageState {
		public static final String ISEDIT = "isedit";
		public static final String ID = "id";
		
		public abstract String getPageClassName();
		
		public void setIsEdit(boolean isEdit){
			setValue(ISEDIT, String.valueOf(isEdit));
		}
		public boolean getIsEdit() {
			return Boolean.valueOf((String)getValue(ISEDIT)).booleanValue();
		}
		public void setId(String gtid) {
			setValue(ID, gtid);
		}
		public String getId() {
			return (String)getValue(ID);
		}
	}
	
	protected abstract State getCurState();
	
	protected abstract void setupPanelLayout();
	protected abstract String getEntityClassName();
	
    Button btnNew = new Button();    
    Button btnReset = new Button();    
    protected void onRender(Element parent, int index) {
    	super.onRender(parent, index);
        System.out.println("----------GoodsType");
//        add(formPanel);
        super.add(formPanel);
//        super.add
        
        setupPanelLayout();
        
//      HorizontalPanel panel = new HorizontalPanel();
//      panel.setSpacing(10);
      btnNew.setText(Resources.constants.ok());        
      btnReset.setText(Resources.constants.reset());
//      panel.add(btnNew);         
//      panel.add(btnCancel);
      
      formPanel.setButtonAlign(HorizontalAlignment.CENTER);
      formPanel.addButton(btnNew);
      formPanel.addButton(btnReset);
      
      
      btnNew.addSelectionListener(selectionListener);
      
      btnReset.addSelectionListener(
      		new SelectionListener<ButtonEvent>() {
      			public void componentSelected(ButtonEvent sender) {
      				formPanel.reset();
      			}
      		}
      	);
    }
    
	protected SelectionListener<ButtonEvent> selectionListener = new SelectionListener<ButtonEvent>() {
	    public void componentSelected(ButtonEvent sender) {
	    	log("on Submit: formPanel="+formPanel);
	    	try {
	    	if(!formPanel.isValid()) {
	    		Window.alert("Please check input before submit!!!");
	    		return;
	    	}
	    	if(!formPanel.isDirty()) {
	    		// TODO this is optimisitic. should change it to based on a strategy configuration?
	    		Window.alert("the form is not changed!!!");
	    		gotoSuccessPanel();
	    		return;
	    	}
	    	submit();
	    	} catch (Exception e) {
	    		e.printStackTrace();
	    	}
	    }
	};
	
	protected void submit() {
		// default implementation is thru GWT-RPC
    	List<Field<?>> fields = formPanel.getFields();
    	
    	Map<String, Object> props = new HashMap<String, Object>();
    	for(Field field:fields) {
    		log("name: "+field.getName()+", value: ("+field.getValue()+")");
    		props.put(field.getName(), field.getValue());
    	}
    	BeanObject form = new BeanObject(getEntityClassName(), props);
    	if (getCurState().getIsEdit()) {
    		String id = getCurState().getId();
          new UpdateService().updateBean(id, form, new UpdateService.Listener() {
          public synchronized void onSuccess(Boolean success) {
        	  gotoSuccessPanel();
          }
          });
    	}else {
          new CreateService().createBean(form, new CreateService.Listener() {
          public synchronized void onSuccess(String id) {
        	  log("new onSuccess( "+id);                            
              getCurState().setId(id);
              gotoSuccessPanel();
          }
          });
    	}
	}
	
	protected FormPanel formPanel = new FormPanel();
	
	public abstract void gotoSuccessPanel();
	
	protected abstract void postSuperRefresh();
	
    public void refresh() {
    	try {
    		formPanel.clear();
    	} catch (Exception ex) {
    		ex.printStackTrace();
    	}
        if(getCurState().getIsEdit()) {
        	new ReadService().getBean(getEntityClassName(), getCurState().getId(),
				new ReadService.Listener() {
        		public void onSuccess(BeanObject bean) {
        			obj = bean;
        			// populate those statically rendered fields
        			populateFields();
        			// sub-class should populate those "dynamic" fields including combox/list, etc 
        			postSuperRefresh();
        		}
        	});
        } else {
        	obj = new BeanObject();
        	postSuperRefresh();
        }

    }
    public void log(String s) {
    	
    	StringBuffer buf = new StringBuffer();
    	Logger.getClientLogger().log(
    			buf.append("[").append(this.getClass().getName()).append("]:").append(s).toString());
    }
    public BeanObject getEntity() {
    	return obj;
    }

    
	public void populateField(Field field) {
		Map<String, Object> mapAttribute = obj.getProperties();
			String name = field.getName();
			Object value = mapAttribute.get(name);        				
			log("name:"+name+", value:"+(value==null?"null":value.toString()+", valueclass: "+value.getClass().getName()));

			if(value==null) {
				return;
			}
			
			if(field instanceof ComboBox) {
				ComboBox<BeanObject> box = (ComboBox<BeanObject>)field;
				
				ListStore<BeanObject> store = box.getStore();
				List<BeanObject> selection = new ArrayList<BeanObject>();
				BeanObject bo = store.findModel("id", value);
				selection.add(bo);
				box.setSelection(selection);
				
				
			} else if(field instanceof ListField){
				ListField<BeanObject> lf = (ListField<BeanObject>)field;
				ListStore<BeanObject> store = lf.getStore();
				
				Collection<String> v = (Collection<String>)value;
				List<BeanObject> selection = new ArrayList<BeanObject>();
				for(String vv:v) {
					BeanObject bo = store.findModel("id", vv);
					if(bo!=null) {
						selection.add(bo);
					}
				}
				lf.setSelection(selection);
				
			} else if(field instanceof TextField || field instanceof TextArea || field instanceof HtmlEditor) {
				field.setOriginalValue(value);
				field.setValue(value);
			} else if (field instanceof CheckBox) {
				((CheckBox)field).setValue((Boolean)value);
			} else if (field instanceof HiddenField) {
				((HiddenField)field).setValue(value);
			}
	}
	
	public void populateFields() {
		List<Field<?>> fields = formPanel.getFields();
		for(Field field:fields) {
			populateField(field);
		}
	}
    private BeanObject obj;
}
