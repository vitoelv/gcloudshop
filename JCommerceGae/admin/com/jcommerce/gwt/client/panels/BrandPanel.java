package com.jcommerce.gwt.client.panels;

import com.extjs.gxt.ui.client.event.ButtonEvent;
import com.extjs.gxt.ui.client.event.Events;
import com.extjs.gxt.ui.client.event.FormEvent;
import com.extjs.gxt.ui.client.event.Listener;
import com.extjs.gxt.ui.client.event.SelectionListener;
import com.extjs.gxt.ui.client.widget.button.Button;
import com.extjs.gxt.ui.client.widget.form.CheckBox;
import com.extjs.gxt.ui.client.widget.form.FileUploadField;
import com.extjs.gxt.ui.client.widget.form.FormPanel;
import com.extjs.gxt.ui.client.widget.form.HiddenField;
import com.extjs.gxt.ui.client.widget.form.TextArea;
import com.extjs.gxt.ui.client.widget.form.TextField;
import com.google.gwt.user.client.Window;
import com.jcommerce.gwt.client.ModelNames;
import com.jcommerce.gwt.client.form.BrandForm;
import com.jcommerce.gwt.client.form.GWTHttpDynaForm;
import com.jcommerce.gwt.client.resources.Resources;

/**
 * Example file.
 */
public class BrandPanel extends BaseEntityEditPanel {    	
    
	public static interface Constants {
		String NewBrand_title();
		String NewBrand_editBrand();
		String NewBrand_brandList();
		String NewBrand_name();
		String NewBrand_site();
		String NewBrand_LOGO();
		String NewBrand_description();
		String NewBrand_order();
		String NewBrand_showOrNot();
        String NewBrand_addSuccessfully();
        String NewBrand_modifySuccessfully();
    }
	
	@Override
	public String getEntityClassName() {
		return ModelNames.BRAND; 
	}
    @Override
    public Button getShortCutButton() {
//    	return btnAdd;
      Button buttonAddClone = new Button(Resources.constants.NewBrand_brandList());
      buttonAddClone.addSelectionListener(new SelectionListener<ButtonEvent>() {
          public void componentSelected(ButtonEvent ce) {
          	onButtonListClicked();
          }
      });
      return buttonAddClone;
    }
    public void onButtonListClicked() {
		BrandListPanel.State newState = new BrandListPanel.State();
		newState.execute();
    }
    /**
     * Initialize this example.
     */
    public static BrandPanel getInstance() {
    	if(instance==null) {
    		instance = new BrandPanel();
    	}
    	return instance;
    }
    private static BrandPanel instance; 
    private BrandPanel() {
    }
    
	public static class State extends BaseEntityEditPanel.State {
		@Override
		public String getPageClassName() {
			return BrandPanel.class.getName();
		}
		
		public String getMenuDisplayName() {
			return Resources.constants.NewBrand_title();
		}
	}
	
	private State curState = new State();
	
	@Override
	public State getCurState() {
		return curState;
	}

	public void setCurState(State curState) {
		this.curState = curState;
	}
    public String getDescription() {
        return "cwBasicTextDescription";
    }

    public String getName() {
    	if(!getCurState().getIsEdit())
        	return Resources.constants.NewBrand_title();
        else
            return Resources.constants.NewBrand_editBrand(); 
    	
    }
    
//    public void setBrand(BeanObject brand) {
//        this.brand = brand;
//        editting = brand != null;
//    }
    
    HiddenField<String> idField;
    
    @Override
    public void setupPanelLayout() {
    	idField = BrandForm.getIdField();
    	formPanel.add(idField);
    	
        TextField<String> nameField = BrandForm.getNameField(Resources.constants.NewBrand_name()+"：");
        nameField.setFieldLabel(Resources.constants.NewBrand_name());
        formPanel.add(nameField, sfd());
        
        TextField<String> siteField = BrandForm.getSiteField(Resources.constants.NewBrand_site()+"：");
        siteField.setFieldLabel(Resources.constants.NewBrand_site());
        formPanel.add(siteField, lfd());
        
        FileUploadField logoField = BrandForm.getLogoField(Resources.constants.NewBrand_LOGO()+"：");
        logoField.setFieldLabel(Resources.constants.NewBrand_LOGO());
        formPanel.add(logoField, sfd());
        
        TextArea descField = BrandForm.getDescField(Resources.constants.NewBrand_description()+"：");
        descField.setHeight("180px");
        descField.setWidth("100px");        
        descField.setFieldLabel(Resources.constants.NewBrand_description());
        formPanel.add(descField, lfd());
        
        TextField<String> orderField = BrandForm.getOrderField(Resources.constants.NewBrand_order()+"：");
        orderField.setFieldLabel(Resources.constants.NewBrand_order());
        formPanel.add(orderField, sfd());
        
        CheckBox showField = BrandForm.getShowField(Resources.constants.NewBrand_showOrNot()+"：");
        showField.setFieldLabel(Resources.constants.NewBrand_showOrNot());
        formPanel.add(showField, sfd());
        

        
//        formPanel.createPanel(IBrand.NAME, "品牌名称:", new TextBox());
//        formPanel.createPanel(IBrand.SITE, "品牌网址:", new TextBox());
//        final FileUploader logoUpload = new FileUploader();
//        logoUpload.addAllowedTypes(new String[]{".jpg", ".gif"});
//        formPanel.createPanel(IBrand.LOGO, "品牌LOGO:", logoUpload);
//        formPanel.createPanel(IBrand.DESC, "品牌描述:", new TextArea());
//        formPanel.createPanel(IBrand.ORDER, "排序:", new TextBox());        
//        formPanel.createPanel(IBrand.SHOW, "是否显示:", new CheckBox());
        
//        HorizontalPanel panel = new HorizontalPanel();
//        panel.setSpacing(10);

//        formPanel.setAction(GWT.getModuleBaseURL() + "uploadService?class=Brand");
        formPanel.setEncoding(FormPanel.Encoding.MULTIPART);
        formPanel.setMethod(FormPanel.Method.POST);
        formPanel.addListener(Events.Submit, new Listener<FormEvent>() {
			public void handleEvent(FormEvent be) {
				// TODO Auto-generated method stub
				String result = be.getResultHtml();
				if("0".equals(result)) {
					gotoSuccessPanel();
				}
				else {
					Window.alert("Error: "+result);	
				}
	
			} 
        });
        
        
//        btnNew.addSelectionListener(new SelectionListener<ButtonEvent>() {
//        	public void componentSelected(ButtonEvent sender) {
//        		formPanel.submit();
//        		
//            }
//        });

               
    }  
    
    @Override
    public void postSuperRefresh() {
    	String action="com.jcommerce.gwt.server.BrandGWTAction";
    	String method="";
    	
    	if(getCurState().getIsEdit()) {
    		method = "update";
    		idField.setValue(getCurState().getPkId());
    	}else {
    		method = "add";
    		idField.setValue(null);
    	}
    	formPanel.setAction(GWTHttpDynaForm.constructURL(action, method));
    }
    
    @Override
    protected void submit() {
    	// test
    	// add field on the fly
    	
    	formPanel.submit();
    }
    
	@Override
	public void gotoSuccessPanel() {
    	Success.State newState = new Success.State();
    	if(!getCurState().getIsEdit()) {
    		newState.setMessage(Resources.constants.NewBrand_addSuccessfully());
    	} else {
    		newState.setMessage(Resources.constants.NewBrand_modifySuccessfully());
    	}
    	
    	BrandListPanel.State choice1 = new BrandListPanel.State();
    	newState.addChoice(BrandListPanel.getInstance().getName(), choice1.getFullHistoryToken());
    	
    	newState.execute();
	}
}
