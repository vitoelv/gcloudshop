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
import com.extjs.gxt.ui.client.widget.form.TextArea;
import com.extjs.gxt.ui.client.widget.form.TextField;
import com.google.gwt.user.client.Element;
import com.google.gwt.user.client.Window;
import com.jcommerce.gwt.client.ContentWidget;
import com.jcommerce.gwt.client.ModelNames;
import com.jcommerce.gwt.client.PageState;
import com.jcommerce.gwt.client.form.BeanObject;
import com.jcommerce.gwt.client.form.GoodsTypeForm;
import com.jcommerce.gwt.client.resources.Resources;
import com.jcommerce.gwt.client.service.CreateService;
import com.jcommerce.gwt.client.service.ReadService;
import com.jcommerce.gwt.client.service.UpdateService;

public class GoodsTypePanel extends ContentWidget {
	public static interface Constants {
//		String GoodsType_Name();

	}
	
	
	public static class State extends PageState {
		public static final String ISEDIT = "isedit";
		public static final String GOODSTYPEID = "gtid";
		
		public String getPageClassName() {
			return GoodsTypePanel.class.getName();
		}
		
		public void setIsEdit(boolean isEdit){
			setValue(ISEDIT, String.valueOf(isEdit));
		}
		public boolean getIsEdit() {
			return Boolean.valueOf((String)getValue(ISEDIT)).booleanValue();
		}
		public void setGoodsTypeID(String gtid) {
			setValue(GOODSTYPEID, gtid);
		}
		public String getGoodsTypeID() {
			return (String)getValue(GOODSTYPEID);
		}
	}
	private State curState = new State();
	
	FormPanel formPanel = new FormPanel();
	
    protected void onRender(Element parent, int index) {
    	super.onRender(parent, index);
        System.out.println("----------GoodsType");
        add(formPanel);
        
        
        
        TextField<String> nameField = GoodsTypeForm.getNameField("商品类型名称：");
        nameField.setFieldLabel("商品类型名称");
        formPanel.add(nameField);
        
        TextArea agField = GoodsTypeForm.getAttributeGroupField("属性分组：");
        agField.setHeight("180px");
        agField.setWidth("100px");        
        agField.setFieldLabel("属性分组");
        formPanel.add(agField);
        
        // TODO make it english : ? 
        formPanel.setLabelSeparator("：");
        formPanel.setLabelWidth(100);

        
        Button btnNew = new Button();    
        Button btnReset = new Button();    
//        HorizontalPanel panel = new HorizontalPanel();
//        panel.setSpacing(10);
        btnNew.setText(Resources.constants.ok());        
        btnReset.setText(Resources.constants.reset());
//        panel.add(btnNew);         
//        panel.add(btnCancel);
        
        formPanel.setButtonAlign(HorizontalAlignment.CENTER);
        formPanel.addButton(btnNew);
        formPanel.addButton(btnReset);
        
        
        btnNew.addSelectionListener(new SelectionListener<ButtonEvent>() {
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
            	List<Field<?>> fields = formPanel.getFields();
            	
            	Map<String, Object> props = new HashMap<String, Object>();
            	for(Field field:fields) {
            		System.out.println("name: "+field.getName()+", value: ("+field.getValue()+")");
            		props.put(field.getName(), field.getValue());
            	}
            	BeanObject goodsType = new BeanObject(ModelNames.GOODSTYPE, props);
            	if (getCurState().getIsEdit()) {
            		String id = getCurState().getGoodsTypeID();
                  new UpdateService().updateBean(id, goodsType, new UpdateService.Listener() {
                  public synchronized void onSuccess(Boolean success) {
                	  gotoSuccessPanel();
                  }
                  });
            	}else {
                  new CreateService().createBean(goodsType, new CreateService.Listener() {
                  public synchronized void onSuccess(String id) {
                      System.out.println("new onSuccess( "+id);                            
                      getCurState().setGoodsTypeID(id);
                      gotoSuccessPanel();
                  }
                  });
            	}
            }
        });
        
        btnReset.addSelectionListener(
        		new SelectionListener<ButtonEvent>() {
        			public void componentSelected(ButtonEvent sender) {
        				formPanel.reset();
        			}
        		}
        	);
        
    }
    
	private void gotoSuccessPanel() {
		Success.State newState = new Success.State();
      	newState.setMessage("编辑商品类型成功");
      	
      	GoodsTypeListPanel.State choice1 = new GoodsTypeListPanel.State();
      	newState.addChoice(GoodsTypeListPanel.getInstance().getName(), choice1.getFullHistoryToken());
      	
      	newState.execute();
	}
	
    public void refresh() {
    	formPanel.clear();
        if(getCurState().getIsEdit()) {
        	new ReadService().getBean(ModelNames.GOODSTYPE, getCurState().getGoodsTypeID(),
				new ReadService.Listener() {
        		public void onSuccess(BeanObject bean) {
        			Map<String, Object> mapAttribute = bean.getProperties();
        			// NOTE by Leon
        			// this will not correctly set the selected value for lstGoodsType, 
        			// as the last async query for listGoodsType has not finished
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
    
    
    
	@Override
	public String getDescription() {
        return "cwBasicTextDescription";
	}

	@Override
	public String getName() {
        return "商品类型编辑版2";
	}


	public State getCurState() {
		return curState;
	}


	public void setCurState(State curState) {
		this.curState = curState;
	}
	
	

}
