package com.jcommerce.gwt.client.panels;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.extjs.gxt.ui.client.widget.Info;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Element;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.TextArea;
import com.google.gwt.user.client.ui.TextBox;
import com.jcommerce.gwt.client.ContentWidget;
import com.jcommerce.gwt.client.JCommerceGae;
import com.jcommerce.gwt.client.ModelNames;
import com.jcommerce.gwt.client.PageState;
import com.jcommerce.gwt.client.ValidationException;
import com.jcommerce.gwt.client.form.AttributeForm;
import com.jcommerce.gwt.client.form.BeanObject;
import com.jcommerce.gwt.client.form.CategoryForm;
import com.jcommerce.gwt.client.model.ICategory;
import com.jcommerce.gwt.client.service.CreateService;
import com.jcommerce.gwt.client.service.ListService;
import com.jcommerce.gwt.client.service.ReadService;
import com.jcommerce.gwt.client.service.UpdateService;
import com.jcommerce.gwt.client.widgets.ColumnPanel;

/**
 * Example file.
 */
public class CategoryPanel extends ContentWidget {    	
	private ListBox c_parent = new ListBox();
    private Button btnNew = new Button();    
    private Button btnCancel = new Button();    
    private ColumnPanel contentPanel = new ColumnPanel();
    
    private Map<String, BeanObject> categorys = new HashMap<String, BeanObject>();

    private boolean editting = false;
//    private BeanObject category = null;
    
	public static class State extends PageState {
		public static final String ISEDIT = "isedit";
		public static final String CAT_ID = "catid";
		public static final String SELECTED_PARENT_ID = "parentId";
		
		public String getPageClassName() {
			return CategoryPanel.class.getName();
		}
		
		public void setIsEdit(boolean isEdit){
			setValue(ISEDIT, String.valueOf(isEdit));
		}
		public boolean getIsEdit() {
			return Boolean.valueOf((String)getValue(ISEDIT)).booleanValue();
		}
		public void setCatID(String catid) {
			setValue(CAT_ID, catid);
		}
		public String getCatID() {
			return (String)getValue(CAT_ID);
		}
	}
	private State curState = new State();
	public State getCurState() {
		return curState;
	}

	public void setCurState(State curState) {
		this.curState = curState;
	}
    /**
     * Initialize this example.
     */
    public static CategoryPanel getInstance() {
    	if(instance==null) {
    		instance = new CategoryPanel();
    	}
    	return instance;
    }
    private static CategoryPanel instance;
    private CategoryPanel() {
    }
    
    public String getDescription() {
        return "cwBasicTextDescription";
    }

    public String getName() {
    	if (!editting)
			return "添加分类";
		else
			return "编辑分类";    	
    }
    

    
    protected void onRender(Element parent, int index) {
        super.onRender(parent, index);
        System.out.println("----------NewCategory");
        add(contentPanel);
       
        contentPanel.createPanel(ICategory.NAME, "分类名称:", new TextBox());
//        contentPanel.createPanel(ICategory.PARENT, "上级分类:", c_parent);   
        contentPanel.createPanel(ICategory.PARENTID, "上级分类:", c_parent);        
        contentPanel.createPanel(ICategory.MEASUREUNIT, "数量单位:", new TextBox());
        contentPanel.createPanel(ICategory.SORTORDER, "排序:", new TextBox());
        contentPanel.createPanel(ICategory.SHOW, "是否显示:", new CheckBox());
        contentPanel.createPanel(ICategory.SHOWINNAVIGATOR, "是否显示在导航栏:", new CheckBox());
        contentPanel.createPanel(ICategory.GRADE, "价格区间个数:", new TextBox());
        contentPanel.createPanel(ICategory.STYLE, "分类的样式表文件:", new TextBox());
        contentPanel.createPanel(ICategory.KEYWORDS, "关键字:", new TextBox());
        contentPanel.createPanel(ICategory.DESC, "分类描述:", new TextArea());
        
        HorizontalPanel panel = new HorizontalPanel();
        panel.setSpacing(10);
        btnNew.setText("确定");        
        btnCancel.setText("重置");
        panel.add(btnNew);        
        panel.add(btnCancel);
        contentPanel.createPanel(null, null, panel);      
        
        btnNew.addClickHandler(new ClickHandler() {

            public void onClick(ClickEvent event) {
            	String id = getCurState().getCatID();
                CategoryForm category = new CategoryForm(ModelNames.CATEGORY, contentPanel.getValues());
                try {
                	category.validate();
                } catch (ValidationException ex){
                	// TODO leon need a common validation handling 
                	Window.alert(ex.getMessage());
                	return;
                }
            	if(getCurState().getIsEdit()) {
            		new UpdateService().updateBean(id, category, new UpdateService.Listener() {
                        public synchronized void onSuccess(Boolean success) {
                        	Success.State newState = new Success.State();
                        	newState.setMessage("编辑商品分类成功");
                        	
                        	CategoryListPanel.State choice1 = new CategoryListPanel.State();
                        	newState.addChoice(CategoryListPanel.getInstance().getName(), choice1.getFullHistoryToken());
                        	
                        	newState.execute();
                        }
                    });
            	} else {
                    new CreateService().createBean(category, new CreateService.Listener() {
                        public synchronized void onSuccess(String id) {
                            System.out.println("new onSuccess( "+id);                            
                            getCurState().setCatID(id);
                            System.out.println("1 b_list.addItem("+id);
                            contentPanel.setValue(AttributeForm.ID, id);
                            
                        	Success.State newState = new Success.State();
                        	newState.setMessage("添加商品分类成功");
                        	
                        	CategoryListPanel.State choice1 = new CategoryListPanel.State();
                        	newState.addChoice(CategoryListPanel.getInstance().getName(), choice1.getFullHistoryToken());
                        	
                        	newState.execute();
                            
                        }
                    });
            	}
            }
        });

        btnCancel.addClickHandler(new ClickHandler() {
            public void onClick(ClickEvent event) {
                contentPanel.clearValues();
            }            
        });        
    }   
    
    public void refresh() {
    	c_parent.clear();
//    	c_parent.addItem("",null);
        
        new ListService().listBeans(ModelNames.CATEGORY, new ListService.Listener() {            
            public synchronized void onSuccess(List<BeanObject> beans) {
            	c_parent.insertItem("请选择。。。", null, 0);
            	int i=1;
                for (Iterator<BeanObject> it = beans.iterator(); it.hasNext();) {
                    BeanObject category = it.next();                    
                    c_parent.insertItem(category.getString(ICategory.NAME), category.getString(ICategory.ID), i);
                    i++;
                }      
                
                contentPanel.clearValues();
                if(getCurState().getIsEdit()) {
                	new ReadService().getBean(ModelNames.CATEGORY, getCurState().getCatID(),
        				new ReadService.Listener() {
                		public void onSuccess(BeanObject bean) {
                			Map<String, Object> mapAttribute = bean.getProperties();
                			// TODO leon
                			// this will not correctly set the selected value for lstGoodsType, 
                			// as the last async query for listGoodsType has not finished
                			contentPanel.updateValues(mapAttribute);
                		}
                	});
                }
            }
        });

//        if (this.category!=null&&this.category.getString(ICategory.ID) != null) {
//            categorys.put(this.category.getString(ICategory.ID), this.category);
//            Map<String, Object> mapCategory = category.getProperties();
//            contentPanel.updateValues(mapCategory);
//        }
//        else{
//        	contentPanel.clearValues();
//            editting = false;
//            }
    }
}
