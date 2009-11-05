package com.jcommerce.gwt.client.panels;

import java.util.List;

import com.extjs.gxt.ui.client.event.ButtonEvent;
import com.extjs.gxt.ui.client.event.SelectionChangedEvent;
import com.extjs.gxt.ui.client.event.SelectionChangedListener;
import com.extjs.gxt.ui.client.event.SelectionListener;
import com.extjs.gxt.ui.client.store.ListStore;
import com.extjs.gxt.ui.client.widget.button.Button;
import com.extjs.gxt.ui.client.widget.form.ComboBox;
import com.extjs.gxt.ui.client.widget.form.TextArea;
import com.extjs.gxt.ui.client.widget.form.TextField;
import com.jcommerce.gwt.client.ModelNames;
import com.jcommerce.gwt.client.form.AttributeForm;
import com.jcommerce.gwt.client.form.BeanObject;
import com.jcommerce.gwt.client.form.SimpleOptionData;
import com.jcommerce.gwt.client.model.IAttribute;
import com.jcommerce.gwt.client.model.IGoodsType;
import com.jcommerce.gwt.client.service.ListService;
import com.jcommerce.gwt.client.widgets.SimpleStaticComboBox;

public class AttributePanel extends BaseEntityEditPanel {

	public static class State extends BaseEntityEditPanel.State {
		public static final String SELECTED_GOODSTYPE_ID = "sgtid";
		public String getPageClassName() {
			return AttributePanel.class.getName();
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
	private ComboBox<BeanObject> fListGoodsType;
	private ListStore<BeanObject> goodsTypeList;
    
	@Override
	public String getDescription() {
		return "cwBasicTextDescription";
	}
	@Override
	protected String getEntityClassName() {
		return ModelNames.ATTRIBUTE;
	}

	@Override
	public String getName() {
		return "商品属性";
	}
    @Override
    public Button getShortCutButton() {
      Button sButton = new Button("商品属性");
      sButton.addSelectionListener(new SelectionListener<ButtonEvent>() {
          public void componentSelected(ButtonEvent ce) {
          	onShortCutButtonClicked();
          }
      });
      return sButton;
    }
    public void onShortCutButtonClicked() {
		AttributeListPanel.State newState = new AttributeListPanel.State();
//		newState.setSelectedGoodsTypeID(sgtid)
		newState.execute();
    }
	@Override
	protected void setupPanelLayout(){
        System.out.println("----------setupPanelLayout: Attribute");
        TextField<String> fText = new TextField<String>();
        fText.setFieldLabel("属性名称");
        fText.setName(AttributeForm.ATTR_NAME);
        formPanel.add(fText);
        
        fListGoodsType = new ComboBox<BeanObject>();
        fListGoodsType.setName(AttributeForm.GOODS_TYPE);
        fListGoodsType.setFieldLabel("所属商品类型");
        fListGoodsType.setDisplayField(IGoodsType.CAT_NAME);
        fListGoodsType.setValueField(IGoodsType.PK_ID);
        fListGoodsType.addSelectionChangedListener(new SelectionChangedListener<BeanObject>(){
			@Override
			public void selectionChanged(SelectionChangedEvent<BeanObject> se) {
				// to remember which goodsType it belongs to, and use it when redirecting after successfully saved
				BeanObject selected = se.getSelectedItem();
				if(selected!=null) {
					getCurState().setSelectedGoodsTypeID((String)selected.get(IGoodsType.PK_ID));
				}
			}        	
        });
        goodsTypeList = new ListStore<BeanObject>();
        fListGoodsType.setStore(goodsTypeList);
        fListGoodsType.setEmptyText("请选择。。。");
        formPanel.add(fListGoodsType);
        
        
		ListStore<BeanObject> indexItems = new ListStore<BeanObject>();
		indexItems.add(new SimpleOptionData("无需检索", IAttribute.INDEX_NEEDNOT));
		indexItems.add(new SimpleOptionData("关键字检索", IAttribute.INDEX_KEYWORD));		
		indexItems.add(new SimpleOptionData("范围检索", IAttribute.INDEX_RANGE));
		SimpleStaticComboBox<BeanObject> fIndex = new SimpleStaticComboBox<BeanObject>();
		fIndex.setName(AttributeForm.ATTR_INDEX);
		fIndex.setFieldLabel("能否进行检索");
		fIndex.setStore(indexItems);
		formPanel.add(fIndex); 
        
        
		ListStore<BeanObject> linkedItems = new ListStore<BeanObject>();
		linkedItems.add(new SimpleOptionData("否", IAttribute.ISLINKED_FALSE));
		linkedItems.add(new SimpleOptionData("是", IAttribute.ISLINKED_TRUE));		
		SimpleStaticComboBox<BeanObject> fLinked = new SimpleStaticComboBox<BeanObject>();
		fLinked.setName(AttributeForm.IS_LINKED);
		fLinked.setFieldLabel("相同属性值的商品是否关联");
		fLinked.setStore(linkedItems);
		formPanel.add(fLinked);
        
		ListStore<BeanObject> typeItems = new ListStore<BeanObject>();
		typeItems.add(new SimpleOptionData("唯一属性", IAttribute.TYPE_ONLY));
		typeItems.add(new SimpleOptionData("单选属性", IAttribute.TYPE_SINGLE));		
		typeItems.add(new SimpleOptionData("复选属性", IAttribute.TYPE_MULTIPLE));
		SimpleStaticComboBox<BeanObject> fType = new SimpleStaticComboBox<BeanObject>();
		fType.setName(AttributeForm.ATTR_TYPE);
		fType.setFieldLabel("属性是否可选");
		fType.setStore(typeItems);
		formPanel.add(fType);

		ListStore<BeanObject> inputTypeItems = new ListStore<BeanObject>();
		inputTypeItems.add(new SimpleOptionData("手工录入", IAttribute.INPUTTYPE_SINGLELINETEXT));
		inputTypeItems.add(new SimpleOptionData("从下拉的列表中选择（一行代表一个可选值）", IAttribute.INPUTTYPE_CHOICE));		
		inputTypeItems.add(new SimpleOptionData("多行文本框", IAttribute.INPUTTYPE_MULTIPLELINETEXT));
		SimpleStaticComboBox<BeanObject> fShowInputType = new SimpleStaticComboBox<BeanObject>();
		fShowInputType.setName(AttributeForm.ATTR_INPUT_TYPE);
		fShowInputType.setFieldLabel("该属性值的录入方式");
		fShowInputType.setStore(inputTypeItems);
		formPanel.add(fShowInputType);

        TextArea valuesArea = new TextArea();
        valuesArea.setHeight("180px");
        valuesArea.setWidth("100px");
        valuesArea.setName(AttributeForm.ATTR_VALUES);
        valuesArea.setFieldLabel("可选值列表");
        formPanel.add(valuesArea);
    }
	@Override
	public void gotoSuccessPanel() {
		Success.State newState = new Success.State();
		if(getCurState().getIsEdit()) {
			newState.setMessage("编辑商品类型成功");
		}else {
			newState.setMessage("添加商品类型成功");
		}
    	
    	AttributeListPanel.State choice1 = new AttributeListPanel.State();
    	choice1.setSelectedGoodsTypeID(getCurState().getSelectedGoodsTypeID());
    	newState.addChoice(AttributeListPanel.getInstance().getName(), choice1.getFullHistoryToken());
    	
    	newState.execute();
	}
	
	@Override
	protected void postSuperRefresh() {
        new ListService().listBeans(ModelNames.GOODSTYPE, new ListService.Listener() {            
            public synchronized void onSuccess(List<BeanObject> beans) {
    	    	goodsTypeList.removeAll();
    	    	goodsTypeList.add(beans);
    			populateField(fListGoodsType);
            }
        });
    }

	public State getCurState() {
		return curState;
	}

	public void setCurState(State curState) {
		this.curState = curState;
	}

    


}
