package com.jcommerce.gwt.client.panels;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.extjs.gxt.ui.client.widget.Info;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Element;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.ClickListener;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.TextArea;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;
import com.jcommerce.gwt.client.ContentWidget;
import com.jcommerce.gwt.client.JCommerceGae;
import com.jcommerce.gwt.client.ModelNames;
import com.jcommerce.gwt.client.PageState;
import com.jcommerce.gwt.client.form.AttributeForm;
import com.jcommerce.gwt.client.form.BeanObject;
import com.jcommerce.gwt.client.model.IBrand;
import com.jcommerce.gwt.client.model.ICategory;
import com.jcommerce.gwt.client.panels.CategoryPanel.State;
import com.jcommerce.gwt.client.service.CreateService;
import com.jcommerce.gwt.client.service.ListService;
import com.jcommerce.gwt.client.service.ReadService;
import com.jcommerce.gwt.client.service.UpdateService;
import com.jcommerce.gwt.client.service.WaitService;
import com.jcommerce.gwt.client.widgets.ColumnPanel;
import com.jcommerce.gwt.client.widgets.FileUploader;

/**
 * Example file.
 */
public class BrandPanel extends ContentWidget {    	
    
    private Button btnNew = new Button();    
    private Button btnCancel = new Button();    
    private ColumnPanel contentPanel = new ColumnPanel();
    
    private Map<String, BeanObject> brands = new HashMap<String, BeanObject>();

//    private boolean editting = false;
    
    private BeanObject brand = null;
    
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
    
	public static class State extends PageState {
		public static final String ISEDIT = "isedit";
		public static final String BRAND_ID = "brandid";
		public static final String SELECTED_PARENT_ID = "parentId";
		
		public String getPageClassName() {
			return BrandPanel.class.getName();
		}
		
		public void setIsEdit(boolean isEdit){
			setValue(ISEDIT, String.valueOf(isEdit));
		}
		public boolean getIsEdit() {
			return Boolean.valueOf((String)getValue(ISEDIT)).booleanValue();
		}
		public void setBrandID(String catid) {
			setValue(BRAND_ID, catid);
		}
		public String getBrandID() {
			return (String)getValue(BRAND_ID);
		}
	}
	private State curState = new State();
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
    
    protected void onRender(Element parent, int index) {
        super.onRender(parent, index);
        
        System.out.println("----------NewBrand");
        add(contentPanel);
       
        contentPanel.createPanel(IBrand.NAME, "品牌名称:", new TextBox());
        contentPanel.createPanel(IBrand.SITE, "品牌网址:", new TextBox());
        final FileUploader logoUpload = new FileUploader();
        logoUpload.addAllowedTypes(new String[]{".jpg", ".gif"});
        contentPanel.createPanel(IBrand.LOGO, "品牌LOGO:", logoUpload);
        contentPanel.createPanel(IBrand.DESC, "品牌描述:", new TextArea());
        contentPanel.createPanel(IBrand.ORDER, "排序:", new TextBox());        
        contentPanel.createPanel(IBrand.SHOW, "是否显示:", new CheckBox());
        
        HorizontalPanel panel = new HorizontalPanel();
        panel.setSpacing(10);
        btnNew.setText("确定");        
        btnCancel.setText("重置");
        panel.add(btnNew);        
        panel.add(btnCancel);
        contentPanel.createPanel(null, null, panel);        

        btnNew.addClickHandler(new ClickHandler() {
            public void onClick(ClickEvent event) {
                if (!logoUpload.submit()) {
                    return;
                }
               
                new WaitService(new WaitService.Job() {
                    public boolean isReady() {
                        return logoUpload.isFinish();
                    }

                    public void run() {
                        brand = new BeanObject(ModelNames.BRAND, contentPanel.getValues());
                        if (getCurState().getIsEdit()) {
                            new UpdateService().updateBean(getCurState().getBrandID(), brand, new UpdateService.Listener() {
								@Override
								public void onSuccess(Boolean success) {
		                        	Success.State newState = new Success.State();
		                        	newState.setMessage("添加商品品牌成功");
		                        	
		                        	BrandListPanel.State choice1 = new BrandListPanel.State();
		                        	newState.addChoice(BrandListPanel.getInstance().getName(), choice1.getFullHistoryToken());
		                        	
		                        	newState.execute();
								}
                            	
                            });
                        } else {
                            new CreateService().createBean(brand, new CreateService.Listener() {
                                public void onSuccess(String id) {
		                        	Success.State newState = new Success.State();
		                        	newState.setMessage("修改商品品牌成功");
		                        	
		                        	BrandListPanel.State choice1 = new BrandListPanel.State();
		                        	newState.addChoice(BrandListPanel.getInstance().getName(), choice1.getFullHistoryToken());
		                        	
		                        	newState.execute();                                   
                                }
                            });
                        }
                    }
                });
            }
        });

        btnCancel.addClickHandler(new ClickHandler() {
            public void onClick(ClickEvent event) {
                contentPanel.clearValues();
            }            
        });        
    }  
    
    public void refresh() {
    
        contentPanel.clearValues();
		if (getCurState().getIsEdit()) {
			new ReadService().getBean(ModelNames.BRAND, getCurState()
					.getBrandID(), new ReadService.Listener() {
				public void onSuccess(BeanObject bean) {
					Map<String, Object> mapAttribute = bean.getProperties();
					contentPanel.updateValues(mapAttribute);
				}
			});
		}
    }
}
