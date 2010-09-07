package com.jcommerce.gwt.client.panels.goods;

import com.extjs.gxt.ui.client.event.ButtonEvent;
import com.extjs.gxt.ui.client.event.SelectionChangedEvent;
import com.extjs.gxt.ui.client.event.SelectionChangedListener;
import com.extjs.gxt.ui.client.event.SelectionListener;
import com.extjs.gxt.ui.client.store.ListStore;
import com.extjs.gxt.ui.client.widget.button.Button;
import com.extjs.gxt.ui.client.widget.form.ComboBox;
import com.extjs.gxt.ui.client.widget.form.TextArea;
import com.extjs.gxt.ui.client.widget.form.TextField;
import com.extjs.gxt.ui.client.widget.form.ComboBox.TriggerAction;
import com.jcommerce.gwt.client.ModelNames;
import com.jcommerce.gwt.client.form.AttributeForm;
import com.jcommerce.gwt.client.form.BeanObject;
import com.jcommerce.gwt.client.form.SimpleOptionData;
import com.jcommerce.gwt.client.model.IAttribute;
import com.jcommerce.gwt.client.model.IGoodsType;
import com.jcommerce.gwt.client.panels.BaseEntityEditPanel;
import com.jcommerce.gwt.client.panels.Success;
import com.jcommerce.gwt.client.resources.Resources;
import com.jcommerce.gwt.client.service.ListService;
import com.jcommerce.gwt.client.widgets.SimpleStaticComboBox;

import java.util.List;

public class AttributePanel extends BaseEntityEditPanel {

	public static interface Constants {
        String Attribute_name();
        String Attribute_goodsType();
        
        String Attribute_allowsearch();
        String Attribute_noSearch();
        String Attribute_keywordSearch();
        String Attribute_scopeSearch();
        
        String Attribute_relateSameGoods();

        String Attribute_inputType();
        String Attribute_valueList();
        String Attribute_yes();
        String Attribute_no();
        String Attribute_select();

        String Attribute_attributeSelection();
        String Attribute_noSelection();
        String Attribute_singleSelection();
        String Attribute_multipleSelection();
        
        String Attribute_addSuccessfully();
        String Attribute_modifySuccessfully();
        String Attribute_manualInput();
        String Attribute_listInput();
        String Attribute_textAreaInput();
    }
	
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
		return Resources.constants.AttributeList_title();
	}
    @Override
    public Button getShortCutButton() {
      Button sButton = new Button(Resources.constants.AttributeList_title());
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
        fText.setFieldLabel(Resources.constants.Attribute_name());
        fText.setName(AttributeForm.ATTR_NAME);
        formPanel.add(fText);
        
        fListGoodsType = new ComboBox<BeanObject>();
        fListGoodsType.setName(AttributeForm.GOODS_TYPE);
        fListGoodsType.setFieldLabel(Resources.constants.Attribute_goodsType());
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
        fListGoodsType.setEmptyText(Resources.constants.Attribute_select());
        fListGoodsType.setEditable(false);
        fListGoodsType.setTriggerAction(TriggerAction.ALL);             
        formPanel.add(fListGoodsType);
        
        
		ListStore<BeanObject> indexItems = new ListStore<BeanObject>();
		indexItems.add(new SimpleOptionData(Resources.constants.Attribute_noSearch(), IAttribute.INDEX_NEEDNOT));
		indexItems.add(new SimpleOptionData(Resources.constants.Attribute_keywordSearch(), IAttribute.INDEX_KEYWORD));		
		indexItems.add(new SimpleOptionData(Resources.constants.Attribute_scopeSearch(), IAttribute.INDEX_RANGE));
		SimpleStaticComboBox<BeanObject> fIndex = new SimpleStaticComboBox<BeanObject>();
		fIndex.setName(AttributeForm.ATTR_INDEX);
		fIndex.setFieldLabel(Resources.constants.Attribute_allowsearch());
		fIndex.setStore(indexItems);
		fIndex.setEditable(false);
		fIndex.setTriggerAction(TriggerAction.ALL);		
		formPanel.add(fIndex); 
        
        
		// TODO for now, hide support of link with other goods
//		ListStore<BeanObject> linkedItems = new ListStore<BeanObject>();
//		linkedItems.add(new SimpleOptionData(Resources.constants.Attribute_no(), IAttribute.ISLINKED_FALSE));
//		linkedItems.add(new SimpleOptionData(Resources.constants.Attribute_yes(), IAttribute.ISLINKED_TRUE));		
//		SimpleStaticComboBox<BeanObject> fLinked = new SimpleStaticComboBox<BeanObject>();
//		fLinked.setName(AttributeForm.IS_LINKED);
//		fLinked.setFieldLabel(Resources.constants.Attribute_relateSameGoods());
//		fLinked.setStore(linkedItems);
//		formPanel.add(fLinked);
        
		ListStore<BeanObject> typeItems = new ListStore<BeanObject>();
		typeItems.add(new SimpleOptionData(Resources.constants.Attribute_noSelection(), IAttribute.TYPE_ONLY));
		typeItems.add(new SimpleOptionData(Resources.constants.Attribute_singleSelection(), IAttribute.TYPE_SINGLE));		
		typeItems.add(new SimpleOptionData(Resources.constants.Attribute_multipleSelection(), IAttribute.TYPE_MULTIPLE));
		SimpleStaticComboBox<BeanObject> fType = new SimpleStaticComboBox<BeanObject>();
		fType.setName(AttributeForm.ATTR_TYPE);
		fType.setFieldLabel(Resources.constants.Attribute_attributeSelection());
		fType.setStore(typeItems);
		fType.setEditable(false);
		fType.setTriggerAction(TriggerAction.ALL);
		formPanel.add(fType);

		ListStore<BeanObject> inputTypeItems = new ListStore<BeanObject>();
		inputTypeItems.add(new SimpleOptionData(Resources.constants.Attribute_manualInput(), IAttribute.INPUTTYPE_SINGLELINETEXT));
		inputTypeItems.add(new SimpleOptionData(Resources.constants.Attribute_listInput(), IAttribute.INPUTTYPE_CHOICE));		
		inputTypeItems.add(new SimpleOptionData(Resources.constants.Attribute_textAreaInput(), IAttribute.INPUTTYPE_MULTIPLELINETEXT));
		SimpleStaticComboBox<BeanObject> fShowInputType = new SimpleStaticComboBox<BeanObject>();
		fShowInputType.setName(AttributeForm.ATTR_INPUT_TYPE);
		fShowInputType.setFieldLabel(Resources.constants.Attribute_inputType());
		fShowInputType.setStore(inputTypeItems);
		fShowInputType.setEditable(false);
		fShowInputType.setTriggerAction(TriggerAction.ALL);
		formPanel.add(fShowInputType);

        TextArea valuesArea = new TextArea();
        valuesArea.setHeight("180px");
        valuesArea.setWidth("100px");
        valuesArea.setName(AttributeForm.ATTR_VALUES);
        valuesArea.setFieldLabel(Resources.constants.Attribute_valueList());
        formPanel.add(valuesArea);
    }
	@Override
	public void gotoSuccessPanel() {
		Success.State newState = new Success.State();
		if(getCurState().getIsEdit()) {
			newState.setMessage(Resources.constants.Attribute_modifySuccessfully());
		}else {
			newState.setMessage(Resources.constants.Attribute_addSuccessfully());
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
