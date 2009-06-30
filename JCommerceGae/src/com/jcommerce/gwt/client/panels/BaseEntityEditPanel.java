package com.jcommerce.gwt.client.panels;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.extjs.gxt.ui.client.Style.HorizontalAlignment;
import com.extjs.gxt.ui.client.event.ButtonEvent;
import com.extjs.gxt.ui.client.event.SelectionListener;
import com.extjs.gxt.ui.client.widget.button.Button;
import com.extjs.gxt.ui.client.widget.form.Field;
import com.extjs.gxt.ui.client.widget.form.FormPanel;
import com.google.gwt.user.client.Element;
import com.google.gwt.user.client.Window;
import com.jcommerce.gwt.client.ContentWidget;
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
	    }
	};
	
	protected void submit() {
    	List<Field<?>> fields = formPanel.getFields();
    	
    	Map<String, Object> props = new HashMap<String, Object>();
    	for(Field field:fields) {
    		System.out.println("name: "+field.getName()+", value: ("+field.getValue()+")");
    		props.put(field.getName(), field.getValue());
    	}
    	BeanObject goodsType = new BeanObject(getEntityClassName(), props);
    	if (getCurState().getIsEdit()) {
    		String id = getCurState().getId();
          new UpdateService().updateBean(id, goodsType, new UpdateService.Listener() {
          public synchronized void onSuccess(Boolean success) {
        	  gotoSuccessPanel();
          }
          });
    	}else {
          new CreateService().createBean(goodsType, new CreateService.Listener() {
          public synchronized void onSuccess(String id) {
              System.out.println("new onSuccess( "+id);                            
              getCurState().setId(id);
              gotoSuccessPanel();
          }
          });
    	}
	}
	
	protected FormPanel formPanel = new FormPanel();
	
	public abstract void gotoSuccessPanel();
	
    public void refresh() {
    	formPanel.clear();
        if(getCurState().getIsEdit()) {
        	new ReadService().getBean(getEntityClassName(), getCurState().getId(),
				new ReadService.Listener() {
        		public void onSuccess(BeanObject bean) {
        			Map<String, Object> mapAttribute = bean.getProperties();

        			List<Field<?>> fields = formPanel.getFields();
        			for(Field field:fields) {
        				String name = field.getName();
        				Object value = mapAttribute.get(name);
        				field.setOriginalValue(value);
        				field.setValue(value);
        			}
        		}
        	});
        }

    }

}
