package com.jcommerce.gwt.client.panels;

import java.util.List;

import com.extjs.gxt.ui.client.event.Events;
import com.extjs.gxt.ui.client.event.FormEvent;
import com.extjs.gxt.ui.client.event.Listener;
import com.extjs.gxt.ui.client.widget.form.FormPanel;
import com.extjs.gxt.ui.client.widget.form.HiddenField;
import com.extjs.gxt.ui.client.widget.form.TextField;
import com.google.gwt.user.client.Window;
import com.jcommerce.gwt.client.ModelNames;
import com.jcommerce.gwt.client.form.BeanObject;
import com.jcommerce.gwt.client.form.ShopConfigForm;
import com.jcommerce.gwt.client.model.IShopConfig;
import com.jcommerce.gwt.client.resources.Resources;
import com.jcommerce.gwt.client.service.ListService;

public class ShopConfigPanel extends BaseEntityEditPanel {

	private static ShopConfigPanel instance = null;
	private State curState = new State();
	
	HiddenField<String> id = null;
	TextField<String> nameField = null;
	TextField<String> titleField = null;
	TextField<String> descField = null;
	TextField<String> keywordsField = null;
	TextField<String> addressField = null;

	public static ShopConfigPanel getInstance() {
		if (instance == null) {
			instance = new ShopConfigPanel();
		}
		return instance;
	}

	@Override
	protected State getCurState() {
		return curState;
	}

	@Override
	protected String getEntityClassName() {
		return ModelNames.SHOPCONFIG;
	}

	@Override
	public void gotoSuccessPanel() {
    	Success.State newState = new Success.State();
    	if(!getCurState().getIsEdit()) {
    		newState.setMessage(Resources.messages.Shop_addSuccess());
    	} else {
    		newState.setMessage(Resources.messages.Shop_modifySuccess());
    	}
    	
    	ShopConfigPanel.State choice1 = getCurState();
    	choice1.setIsEdit(true);
    	newState.addChoice(ShopConfigPanel.getInstance().getName(), choice1.getFullHistoryToken());
    	
    	newState.execute();
	}

	@Override
	protected void postSuperRefresh() {
		// TODO Auto-generated method stub

	}

	@Override
	protected void setupPanelLayout() {
		id = ShopConfigForm.getIdField();
		formPanel.add(id);
		
		nameField = ShopConfigForm.getNameField(Resources.constants.Shop_name());
		nameField.setFieldLabel(Resources.constants.Shop_name());
		formPanel.add(nameField);
		
		titleField = ShopConfigForm.getTitleField(Resources.constants.Shop_title());
		titleField.setFieldLabel(Resources.constants.Shop_title());
		formPanel.add(titleField);

		descField = ShopConfigForm.getDescField(Resources.constants.Shop_desc());
		descField.setFieldLabel(Resources.constants.Shop_desc());
		formPanel.add(descField);

		keywordsField = ShopConfigForm.getKeywordsField(Resources.constants.shop_keywords());
		keywordsField.setFieldLabel(Resources.constants.shop_keywords());
		formPanel.add(keywordsField);

		addressField = ShopConfigForm.getAddressField(Resources.constants.shop_address());
		addressField.setFieldLabel(Resources.constants.shop_address());
		formPanel.add(addressField);
		
		getInitDatafromDb();
		
        formPanel.setEncoding(FormPanel.Encoding.MULTIPART);
        formPanel.setMethod(FormPanel.Method.POST);
        formPanel.addListener(Events.Submit, new Listener<FormEvent>() {
			public void handleEvent(FormEvent be) {
				String result = be.getResultHtml();
				if("0".equals(result)) {
					gotoSuccessPanel();
				}
				else {
					Window.alert("Error: "+result);	
				}
	
			} 
        });
	}

	private void getInitDatafromDb(){
		new ListService().listBeans(getEntityClassName(), new ListService.Listener(){
			public void onSuccess(List<BeanObject> beans){
				if (beans.size() == 0){
					getCurState().setIsEdit(false);
				}
				if(beans.size() == 1){
					loadData(beans.get(0));
				}else{
					// TODO shop init fail
					//btnNew.setEnabled(false);
				}
			}
		});
	}
	
	private void loadData(BeanObject bean){
		id.setValue(bean.getString(IShopConfig.ID));
		nameField.setValue(bean.getString(IShopConfig.NAME));
		titleField.setValue(bean.getString(IShopConfig.TITLE));
		descField.setValue(bean.getString(IShopConfig.DESC));
		keywordsField.setValue(bean.getString(IShopConfig.KEYWORDS));
		addressField.setValue(bean.getString(IShopConfig.ADDRESS));
		
		getCurState().setId(bean.getString(IShopConfig.ID));
		getCurState().setIsEdit(true);
	}
	
	public void refresh(){
		try {
    		formPanel.clear();
    	} catch (Exception ex) {
    		ex.printStackTrace();
    	}
    	getInitDatafromDb();
	}
	
	@Override
	public String getDescription() {
		return "cwBasicTextDescription";
	}

	@Override
	public String getName() {
		return Resources.constants.Shop_setup();
	}

	public static class State extends BaseEntityEditPanel.State {

		public String getPageClassName() {
			return ShopConfigPanel.class.getName();
		}
		
		public String getMenuDisplayName() {
			return Resources.constants.Shop_setup();
		}
	}
	
	public static interface Message{
		String Shop_modifySuccess();
		String Shop_addSuccess();
	}
}
