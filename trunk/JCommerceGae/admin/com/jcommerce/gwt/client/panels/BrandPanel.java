package com.jcommerce.gwt.client.panels;

import com.extjs.gxt.ui.client.event.Events;
import com.extjs.gxt.ui.client.event.FormEvent;
import com.extjs.gxt.ui.client.event.Listener;
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

/**
 * Example file.
 */
public class BrandPanel extends BaseEntityEditPanel {    	
    
   
	@Override
	public String getEntityClassName() {
		return ModelNames.BRAND; 
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
		public static final String SELECTED_PARENT_ID = "parentId";
		@Override
		public String getPageClassName() {
			return BrandPanel.class.getName();
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
        	return "添加品牌";
        else
            return "编辑品牌"; 
    	
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
    	
        TextField<String> nameField = BrandForm.getNameField("品牌名称：");
        nameField.setFieldLabel("品牌名称");
        formPanel.add(nameField);
        
        TextField<String> siteField = BrandForm.getSiteField("品牌网址：");
        siteField.setFieldLabel("品牌网址");
        formPanel.add(siteField);
        
        FileUploadField logoField = BrandForm.getLogoField("品牌LOGO：");
        logoField.setFieldLabel("品牌LOGO");
        formPanel.add(logoField);
        
        TextArea descField = BrandForm.getDescField("品牌描述：");
        descField.setHeight("180px");
        descField.setWidth("100px");        
        descField.setFieldLabel("品牌描述");
        formPanel.add(descField);
        
        TextField<String> orderField = BrandForm.getOrderField("排序：");
        orderField.setFieldLabel("排序");
        formPanel.add(orderField);
        
        CheckBox showField = BrandForm.getShowField("排序：");
        showField.setFieldLabel("排序");
        formPanel.add(showField);
        
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
    public void refresh() {
    	super.refresh();
    	String action="com.jcommerce.gwt.server.BrandGWTAction";
    	String method="";
    	
    	if(getCurState().getIsEdit()) {
    		method = "update";
    		idField.setValue(getCurState().getId());
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
    		newState.setMessage("添加商品品牌成功");
    	} else {
    		newState.setMessage("修改商品品牌成功");
    	}
    	
    	BrandListPanel.State choice1 = new BrandListPanel.State();
    	newState.addChoice(BrandListPanel.getInstance().getName(), choice1.getFullHistoryToken());
    	
    	newState.execute();
	}
}
