package com.jcommerce.gwt.client.panels.system;

import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.SortedMap;

import com.extjs.gxt.ui.client.store.ListStore;
import com.extjs.gxt.ui.client.widget.TabItem;
import com.extjs.gxt.ui.client.widget.TabPanel;
import com.extjs.gxt.ui.client.widget.form.Field;
import com.extjs.gxt.ui.client.widget.form.Radio;
import com.extjs.gxt.ui.client.widget.form.TextArea;
import com.extjs.gxt.ui.client.widget.form.TextField;
import com.extjs.gxt.ui.client.widget.layout.FormLayout;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.jcommerce.gwt.client.form.BeanObject;
import com.jcommerce.gwt.client.form.SimpleOptionData;
import com.jcommerce.gwt.client.panels.BaseEntityEditPanel;
import com.jcommerce.gwt.client.panels.Success;
import com.jcommerce.gwt.client.resources.Resources;
import com.jcommerce.gwt.client.service.RemoteService;
import com.jcommerce.gwt.client.util.FormUtils;
import com.jcommerce.gwt.client.widgets.MyRadioGroup;
import com.jcommerce.gwt.client.widgets.SimpleStaticComboBox;


public class ShopConfigPanel extends BaseEntityEditPanel {
    /**
     * Initialize this example.
     */
    public static ShopConfigPanel getInstance() {
    	if(instance==null) {
    		instance = new ShopConfigPanel();
    	}
    	return instance;
    }
    private static ShopConfigPanel instance; 
    private ShopConfigPanel() {
    }
    
	public static class State extends BaseEntityEditPanel.State {
		
		public State() {
			super.setIsEdit(true);
		}
		
		@Override
		public String getPageClassName() {
			return ShopConfigPanel.class.getName();
		}
		
		public String getMenuDisplayName() {
			return Resources.constants.Shop_setup();
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
        return Resources.constants.Shop_setup(); 
    }



	@Override
	protected String getEntityClassName() {
		return null;
	}

	@Override
	public void gotoSuccessPanel() {
		Success.State newState = new Success.State();
		newState.setMessage("编辑商店配置成功");
    	
		ShopConfigPanel.State choice1 = new ShopConfigPanel.State();
    	newState.addChoice(ShopConfigPanel.getInstance().getName(), choice1.getFullHistoryToken());
    	
    	newState.execute();
	}

	@Override
	protected void postSuperRefresh() {
		// do nothing
	}
	TabPanel tabs;
	Map<String, BeanObject> formData = new HashMap<String, BeanObject>();
	
	@Override
	protected void setupPanelLayout() {
    	formPanel.setPadding(0);
    	formPanel.setHeaderVisible(false);
        tabs = new TabPanel();
        tabs.setAutoHeight(true);
        tabs.setWidth(900);
        formPanel.add(tabs);
	}



    public void refresh() {
    	try {
        	// clear 
        	formData.clear();
    		formPanel.clear();
    		tabs.removeAll();
    	} catch (Exception ex) {
    		ex.printStackTrace();
    	}
       	retrieveEntity();
    }
    
    
    // get called when refresh(), if isEdit
    protected void retrieveEntity() {
    	RemoteService.getSpecialService().getLocale(
    		new AsyncCallback<String>() {
    			public void onFailure(Throwable caught) {
    				// TODO Auto-generated method stub
    			}
    			public void onSuccess( String result) {
    				RemoteService.getSpecialService().getCombinedShopConfigMetaMap( result ,
    		    			new AsyncCallback<SortedMap<Integer, List<BeanObject>>>() {
    							public void onFailure(Throwable caught) {
    								// TODO Auto-generated method stub
    							}
    							public void onSuccess(
    									SortedMap<Integer, List<BeanObject>> result) {
    								generateDynaFields(result);
    								layout();
    						        repaint();
    							}
    		    	});
    			}
    		}
    	);
    }
    	
    
	protected void submit() {
		Map<String, Object> props = FormUtils.getPropsFromForm(formPanel);
		for(String code : props.keySet()) {
			Object value = props.get(code);
			BeanObject bo = formData.get(code);
			bo.set(IShopConfigMeta.VALUE, value);
		}
		RemoteService.getSpecialService().saveShopConfig(formData, new AsyncCallback<Boolean>(){
			public void onFailure(Throwable caught) {
				caught.printStackTrace();
				Window.alert("ERROR: "+caught.getMessage());
			}
			public void onSuccess(Boolean result) {
				gotoSuccessPanel();
			}

		});
	}
    private void generateDynaFields(SortedMap<Integer, List<BeanObject>> metaMap){

    	int fieldCount = 0;
    	Field fValue=null;
    	for(Integer i:metaMap.keySet()) {
    		List<BeanObject> metas = metaMap.get(i);
    		if(metas.size()>0) {
    			String group = metas.get(0).getString(IShopConfigMeta.GROUP);	
    			TabItem tab = new TabItem();
    			tab.setStyleAttribute("padding", "10px");
    			tab.setText(group);
    			tabs.add(tab);
    			//generateTab(tab, metas);
    			
    	    	FormLayout fl = new FormLayout();
    			tab.setLayout(fl);
    	    	for(BeanObject meta : metas) {
    	    		fieldCount++;
    	    		String pkId = meta.getString(IShopConfigMeta.PK_ID);
    	    		String value = meta.getString(IShopConfigMeta.VALUE);
    	    		String type = meta.getString(IShopConfigMeta.TYPE);
    	    		String code = meta.getString(IShopConfigMeta.CODE);
    	    		String label = meta.getString(IShopConfigMeta.LABEL);
    	    		
    	    		BeanObject data = new BeanObject();
    	    		data.set(IShopConfigMeta.PK_ID, pkId);
    	    		data.set(IShopConfigMeta.VALUE, value);
    	    		data.set(IShopConfigMeta.CODE, code);
    	    		formData.put(code, data);
    	    		
    	    		if(IShopConfigMeta.CFG_TYPE_TEXT.equals(type)) {
    	    			fValue = new TextField<String>();
    	    		}
    	    		else if(IShopConfigMeta.CFG_TYPE_TEXTAREA.equals(type)) {
    	    			fValue = new TextArea();
    	    		}
    	    		else if(IShopConfigMeta.CFG_TYPE_SELECT.equals(type)) {
    	    			List<String[]> storeRange = (List<String[]>)meta.get(IShopConfigMeta.STORE_RANGE);
    	    			fValue = new MyRadioGroup();
    	    			((MyRadioGroup)fValue).setSelectionRequired(true);
    	    			for(String[] option : storeRange) {
    	    				String text = option[1];
    	    				String val = option[0];
    	    				Radio radio = new Radio();
    	    				radio.setName(code);
    	    				radio.setValueAttribute(val);
    	    				radio.setBoxLabel(text);
    	    				((MyRadioGroup)fValue).add(radio);
    	    			}
    	    		}
    	    		else if(IShopConfigMeta.CFG_TYPE_OPTIONS.equals(type)){
    	    			List<String[]> storeRange = (List<String[]>)meta.get(IShopConfigMeta.STORE_RANGE);
    	    			fValue = new SimpleStaticComboBox<BeanObject>();
    	    			ListStore<BeanObject> choiceStore = new ListStore<BeanObject>();
    	    			((SimpleStaticComboBox)fValue).setStore(choiceStore);
    	    			for(String[] option : storeRange) {
    	    				String text = option[1];
    	    				String val = option[0];
    	    				choiceStore.add(new SimpleOptionData(text, val));
    	    			}
    	    		}
    	    		
    	    		if(fValue!=null) {
    	    			fValue.setFieldLabel(label);
    	    			// code is unique, use it as field name
    	    			fValue.setName(code);
    	    			
    	    			tab.add(fValue, lfd());
    	    			populateField(fValue, value);
    	    		}
    	    	}
    		}
    	}

		// TODO populate field
    	// we put it after all fields are initialized, to 
//		List<Field<?>> fields = formPanel.getFields();
//		for(Field field:fields) {
//			String code = field.getName();
//			String value = formData.get(code).getString(IShopConfigMeta.VALUE);
//
//		}
    	
    }
    private void generateTab(TabItem tab, List<BeanObject> metas) {
    }
}
