package com.jcommerce.gwt.client.panels;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Element;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.ClickListener;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.TextArea;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;
import com.jcommerce.gwt.client.ContentWidget;
import com.jcommerce.gwt.client.ModelNames;
import com.jcommerce.gwt.client.PageState;
import com.jcommerce.gwt.client.ValidationException;
import com.jcommerce.gwt.client.form.AttributeForm;
import com.jcommerce.gwt.client.form.BeanObject;
import com.jcommerce.gwt.client.model.IAttribute;
import com.jcommerce.gwt.client.model.IGoodsType;
import com.jcommerce.gwt.client.service.CreateService;
import com.jcommerce.gwt.client.service.ListService;
import com.jcommerce.gwt.client.service.ReadService;
import com.jcommerce.gwt.client.service.UpdateService;
import com.jcommerce.gwt.client.widgets.ChoicePanel;
import com.jcommerce.gwt.client.widgets.ColumnPanel;

public class AttributePanel extends ContentWidget {

	public static class State extends PageState {
		public static final String ISEDIT = "isedit";
		public static final String ATTR_ID = "attrid";
		public static final String SELECTED_GOODSTYPE_ID = "sgtid";
		
		public String getPageClassName() {
			return AttributePanel.class.getName();
		}
		
		public void setIsEdit(boolean isEdit){
			setValue(ISEDIT, String.valueOf(isEdit));
		}
		public boolean getIsEdit() {
			return Boolean.valueOf((String)getValue(ISEDIT)).booleanValue();
		}
		public void setAttrID(String attrid) {
			setValue(ATTR_ID, attrid);
		}
		public String getAttrID() {
			return (String)getValue(ATTR_ID);
		}
		public void setSelectedGoodsTypeID(String sgtid) {
			setValue(SELECTED_GOODSTYPE_ID, sgtid);
		}
		public String getSelectedGoodsTypeID() {
			return (String)getValue(SELECTED_GOODSTYPE_ID);
		}
	}
	public static AttributePanel getInstance(){
		if(instance == null) {
			instance = new AttributePanel();
		}
		return instance;
	}
	private static AttributePanel instance;
	private AttributePanel() {
		
	}
	private State curState = new State();
	
//	String selectedGoodsTypeId;
	private ColumnPanel contentPanel = new ColumnPanel();
//	private BeanObject attribute = null;
//	private boolean editting = false;
	
//	private Map<String, BeanObject> attributes = new HashMap<String, BeanObject>();
	private ListBox lstGoodsType = new ListBox();
	
//    public void setAttribute(BeanObject attribute) {
//        this.attribute = attribute;
//        editting = attribute != null;
//    }
    
	@Override
	public String getDescription() {
		return "cwBasicTextDescription";
	}

	@Override
	public String getName() {
		return "商品属性";
	}

    protected void onRender(Element parent, int index) {
    	super.onRender(parent, index);
        System.out.println("----------Attribute");
        add(contentPanel);
        
        contentPanel.createPanel(AttributeForm.NAME, "分类名称：", new TextBox());          
        lstGoodsType.addChangeHandler(new ChangeHandler() {
        	public void onChange(ChangeEvent event) {
        		getCurState().setSelectedGoodsTypeID(lstGoodsType.getValue(lstGoodsType.getSelectedIndex()));
        	}
            
        });
        contentPanel.createPanel(AttributeForm.GOODSTYPE, "所属商品类型：", lstGoodsType);       
        
		List<ChoicePanel.Item> indexItems = new ArrayList<ChoicePanel.Item>();
		indexItems.add(new ChoicePanel.Item("无需检索", "0"));
		indexItems.add(new ChoicePanel.Item("关键字检索", "1"));		
		indexItems.add(new ChoicePanel.Item("范围检索", "2"));
		ChoicePanel showIndex = new ChoicePanel("0",indexItems); 
        contentPanel.createPanel(AttributeForm.INDEX, "能否进行检索:", showIndex);
        
		List<ChoicePanel.Item> linkedItems = new ArrayList<ChoicePanel.Item>();
		linkedItems.add(new ChoicePanel.Item("否", "0"));
		linkedItems.add(new ChoicePanel.Item("是", "1"));		
		ChoicePanel showLinked = new ChoicePanel("0",indexItems); 
        contentPanel.createPanel(AttributeForm.LINKED, "相同属性值的商品是否关联？", showLinked);
        
		List<ChoicePanel.Item> typeItems = new ArrayList<ChoicePanel.Item>();
		typeItems.add(new ChoicePanel.Item("唯一属性", "0"));
		typeItems.add(new ChoicePanel.Item("可选属性", "1"));		
		typeItems.add(new ChoicePanel.Item("必选属性", "2"));
		ChoicePanel showType = new ChoicePanel("0",typeItems); 
        contentPanel.createPanel(AttributeForm.TYPE, "属性是否可选：", showType);
        
		List<ChoicePanel.Item> inputTypeItems = new ArrayList<ChoicePanel.Item>();
		inputTypeItems.add(new ChoicePanel.Item("手工录入", "0"));
		inputTypeItems.add(new ChoicePanel.Item("从下拉的列表中选择（一行代表一个可选值）", "1"));		
		inputTypeItems.add(new ChoicePanel.Item("多行文本框", "2"));
		ChoicePanel showInputType = new ChoicePanel("0",inputTypeItems); 
        contentPanel.createPanel(AttributeForm.INPUTTYPE, "该属性值的录入方式：", showInputType);
        
        
        TextArea valuesArea = new TextArea();
        valuesArea.setHeight("180px");
        valuesArea.setWidth("100px");
        contentPanel.createPanel(AttributeForm.VALUES, "可选值列表：", valuesArea);

        Button btnNew = new Button();    
        Button btnCancel = new Button();    
        HorizontalPanel panel = new HorizontalPanel();
        panel.setSpacing(10);
        btnNew.setText("确定");        
        btnCancel.setText("重置");
        panel.add(btnNew);        
        panel.add(btnCancel);
        contentPanel.createPanel(null, null, panel);      
        
        btnNew.addClickHandler(new ClickHandler() {
            public void onClick(ClickEvent event) {
                String id = getCurState().getAttrID();
                AttributeForm attribute = new AttributeForm(ModelNames.ATTRIBUTE, contentPanel.getValues());
                try {
                	attribute.validate();
                } catch (ValidationException ex){
                	// TODO leon need a common validation handling 
                	Window.alert(ex.getMessage());
                	return;
                }
                System.out.println("selected goodsType: "+attribute.get(AttributeForm.GOODSTYPE));
                
                if (getCurState().getIsEdit()) {
                    new UpdateService().updateBean(id, attribute, new UpdateService.Listener() {
                        public synchronized void onSuccess(Boolean success) {
                        	
                        	Success.State newState = new Success.State();
                        	newState.setMessage("编辑商品类型成功");
                        	
                        	AttributeListPanel.State choice1 = new AttributeListPanel.State();
                        	choice1.setSelectedGoodsTypeID(getCurState().getSelectedGoodsTypeID());
                        	newState.addChoice(AttributeListPanel.getInstance().getName(), choice1.getFullHistoryToken());
                        	
                        	newState.execute();
                        }
                    });
                    
                } else {
                    new CreateService().createBean(attribute, new CreateService.Listener() {
                        public synchronized void onSuccess(String id) {
                            System.out.println("new onSuccess( "+id);                            
                            getCurState().setAttrID(id);
                            System.out.println("1 b_list.addItem("+id);
//                            attributes.put(id, attribute);
                            contentPanel.setValue(AttributeForm.ID, id);                           
                            
                        	Success.State newState = new Success.State();
                        	newState.setMessage("添加商品类型成功");
                        	
                        	AttributeListPanel.State choice1 = new AttributeListPanel.State();
                        	choice1.setSelectedGoodsTypeID(getCurState().getSelectedGoodsTypeID());
                        	newState.addChoice(AttributeListPanel.getInstance().getName(), choice1.getFullHistoryToken());
                        	
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
        
//        refresh();
    }
    
    public void refresh() {
    	
    	lstGoodsType.clear();
        
        new ListService().listBeans(ModelNames.GOODSTYPE, new ListService.Listener() {            
            public synchronized void onSuccess(List<BeanObject> beans) {
            	lstGoodsType.insertItem("请选择。。。", null, 0);
            	int i=1;
                for (Iterator<BeanObject> it = beans.iterator(); it.hasNext();) {
                    BeanObject goodsType = it.next();                    
                    lstGoodsType.insertItem(goodsType.getString(IGoodsType.NAME), goodsType.getString(IGoodsType.ID),i);
                    
                    // if(!getCurState().getIsEdit()) {
                    // do the check no matter isedit or not
//                    if(getCurState().getSelectedGoodsTypeID()!=null && 
//                    	getCurState().getSelectedGoodsTypeID().equals(goodsType.getString(IGoodsType.ID))) {
//                    	lstGoodsType.setSelectedIndex(i);
//                    }
                    i++;

                }               
                contentPanel.clearValues();
                if(getCurState().getIsEdit()) {
                	new ReadService().getBean(ModelNames.ATTRIBUTE, getCurState().getAttrID(),
        				new ReadService.Listener() {
                		public void onSuccess(BeanObject bean) {
                			String goodsType = bean.getString(IAttribute.GOODSTYPE);
                			System.out.println("goodsType: "+goodsType);
                			Map<String, Object> mapAttribute = bean.getProperties();
                			contentPanel.updateValues(mapAttribute);
                		}
                	});
                }
            }
        });
    	
        
        

    	

        


    }

	public State getCurState() {
		return curState;
	}

	public void setCurState(State curState) {
		this.curState = curState;
	}
    
//	public void setSelectedGoodsTypeId(String selectedGoodsTypeId) {
//		this.selectedGoodsTypeId = selectedGoodsTypeId;
//	}

}
