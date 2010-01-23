package com.jcommerce.gwt.client.panels.order;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.extjs.gxt.ui.client.Style.HorizontalAlignment;
import com.extjs.gxt.ui.client.event.ButtonEvent;
import com.extjs.gxt.ui.client.event.SelectionChangedEvent;
import com.extjs.gxt.ui.client.event.SelectionChangedListener;
import com.extjs.gxt.ui.client.event.SelectionListener;
import com.extjs.gxt.ui.client.store.ListStore;
import com.extjs.gxt.ui.client.widget.button.Button;
import com.extjs.gxt.ui.client.widget.form.ComboBox;
import com.extjs.gxt.ui.client.widget.form.MultiField;
import com.extjs.gxt.ui.client.widget.form.TextField;
import com.extjs.gxt.ui.client.widget.form.ComboBox.TriggerAction;
import com.extjs.gxt.ui.client.widget.layout.FormLayout;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.jcommerce.gwt.client.ModelNames;
import com.jcommerce.gwt.client.PageState;
import com.jcommerce.gwt.client.form.BeanObject;
import com.jcommerce.gwt.client.form.GoodsForm;
import com.jcommerce.gwt.client.form.UserAddressForm;
import com.jcommerce.gwt.client.model.IOrderGoods;
import com.jcommerce.gwt.client.model.IOrderInfo;
import com.jcommerce.gwt.client.model.IRegion;
import com.jcommerce.gwt.client.model.IUserAddress;
import com.jcommerce.gwt.client.panels.BaseEntityEditPanel;
import com.jcommerce.gwt.client.panels.Success;
import com.jcommerce.gwt.client.panels.goods.CategoryListPanel;
import com.jcommerce.gwt.client.panels.goods.GoodsPanel;
import com.jcommerce.gwt.client.panels.order.OrderDetailPanel.State;
import com.jcommerce.gwt.client.resources.Resources;
import com.jcommerce.gwt.client.service.Condition;
import com.jcommerce.gwt.client.service.Criteria;
import com.jcommerce.gwt.client.service.DeleteService;
import com.jcommerce.gwt.client.service.ListService;
import com.jcommerce.gwt.client.service.ReadService;
import com.jcommerce.gwt.client.service.RemoteService;
import com.jcommerce.gwt.client.service.UpdateService;
import com.jcommerce.gwt.client.util.FormUtils;

public class ConsigneePanel extends BaseEntityEditPanel {
	
	public interface Constants {
		String Consignee_title();
		String Consignee_uaList();
		String Consignee_EmptyText();
		String Consignee_Consignee();
		String Consignee_Area();
		String Consignee_Email();
		String Consignee_Address();
		String Consignee_Zipcode();
		String Consignee_Tel();
		String Consignee_Mobile();
		String Consignee_SignBuilding();
		String Consignee_BestTime();
	}

	public static class State extends BaseEntityEditPanel.State {
		public static final String ORDER_ID = "orderId";
		public static final String USER_ID = "userId";
	
		public void setOrderId(String orderId) {
			setValue(ORDER_ID, orderId);
		}
		public String getOrderId() {
			return (String)getValue(ORDER_ID);
		}
		public void setUserId(String orderId) {
			setValue(USER_ID, orderId);
		}
		public String getUserId() {
			return (String)getValue(USER_ID);
		}
		public String getPageClassName() {
			return ConsigneePanel.class.getName();
		}
		public String getMenuDisplayName() {
			return Resources.constants.Consignee_title();
		}
	}
	private State curState = new State();
	public State getCurState() {
		return curState;
	}

	public void setCurState(State curState) {
		this.curState = curState;
	}

	private static ConsigneePanel instance;
    private ConsigneePanel() {       
    }
    public static ConsigneePanel getInstance() {
    	if(instance == null) {
    		instance = new ConsigneePanel();
    	}
    	return instance;
    }
	@Override
	protected String getEntityClassName() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void gotoSuccessPanel() {
		//TODO　下一步页面
		Success.State newState = new Success.State();
    	newState.setMessage(Resources.constants.Category_modifySuccessfully());
    	
    	ConsigneePanel.State choice1 = new ConsigneePanel.State();
    	newState.addChoice(ConsigneePanel.getInstance().getName(), choice1.getFullHistoryToken());
    	
    	newState.execute();

	}
	public void refresh() {
		formPanel.removeAll();
		setupPanelLayout();
		formPanel.layout();
		
		btnOk.setVisible(false);
		btnPre.setVisible(false);
		btnNext.setVisible(false);
		if(getCurState().getIsEdit()) {
			btnOk.setVisible(true);
		}
		else {
			btnPre.setVisible(true);
			btnNext.setVisible(true);			
		}
//		formPanel.repaint();
	}
	

	@Override
	protected void postSuperRefresh() {
		isChange = false;
	}
	
	Button btnOk;
	Button btnPre;
	Button btnNext;
	Button btnCancel;
	@Override
    protected void afterRender() {
//		super.afterRender();
		//TODO 添加按钮
      super.add(formPanel);
      
      FormLayout fl = new FormLayout();
      fl.setLabelWidth(150);
      fl.setLabelPad(50);
      formPanel.setLayout(fl);
      setupPanelLayout();
     
      btnOk = new Button("确定");
      btnOk.addSelectionListener(selectionListener);
      btnPre = new Button("上一步", new SelectionListener<ButtonEvent>() {
		public void componentSelected(ButtonEvent ce) {
			OrderGoodsPanel.State newState = new OrderGoodsPanel.State();
			newState.setIsEdit(false);
			newState.setUserId(getCurState().getUserId());
			newState.setPkId(getCurState().getOrderId());
			newState.execute();
		}
      });
      btnNext = new Button("下一步");
      btnNext.addSelectionListener(selectionListener);
      btnCancel = new Button("取消", new SelectionListener<ButtonEvent>() {
		public void componentSelected(ButtonEvent ce) {
			cancel();
		}
      });
      
      formPanel.setButtonAlign(HorizontalAlignment.CENTER);
      formPanel.addButton(btnOk);
      formPanel.addButton(btnPre);
      formPanel.addButton(btnNext);
      formPanel.addButton(btnCancel);
      
	}
	ComboBox<BeanObject> uaList;
	private ListStore<BeanObject> userAddressList;
	private ListStore<BeanObject> countryList;
	private ListStore<BeanObject> provinceList;
	private ListStore<BeanObject> cityList;
	private ListStore<BeanObject> districtList;
	TextField<String> fConsignee;
	ComboBox<BeanObject> cbCountry;
	ComboBox<BeanObject> cbProvince;
	ComboBox<BeanObject> cbCity;
	ComboBox<BeanObject> cbDistrict;
	TextField<String> fEmail;
	TextField<String> fAddress;
	TextField<String> fZipcode;
	TextField<String> fTel;
	TextField<String> fMobile;
	TextField<String> fSignBuilding ;
	TextField<String> fBestTime ;
	
	@Override
	protected void setupPanelLayout() {
		
		if( getCurState().getUserId() != null ){
			userAddressList = new ListStore<BeanObject>();
			
			uaList = UserAddressForm.getUaListField();
			uaList.setFieldLabel(Resources.constants.Consignee_uaList());
			uaList.setStore(userAddressList);
			uaList.setEmptyText(Resources.constants.Consignee_EmptyText());   
			uaList.setWidth(150);   
			uaList.setTypeAhead(true);   
			uaList.setTriggerAction(TriggerAction.ALL);  
			formPanel.add(uaList, sfd());
			
			uaList.addSelectionChangedListener(new SelectionChangedListener<BeanObject>() {        	
				@Override
				public void selectionChanged(SelectionChangedEvent<BeanObject> se) {
					BeanObject bo = se.getSelectedItem();
					if(bo==null) {
						// this happen when formPanel.clear()
						return;
					}
					changeUserAddress(bo);
					
				}
	        });
			Criteria criteria = new Criteria();
	        criteria.addCondition(new Condition(IUserAddress.USER_ID,Condition.EQUALS,getCurState().getUserId()));
	        new ListService().listBeans(ModelNames.USERADDRESS, criteria, new ListService.Listener(){

				@Override
				public void onSuccess(List<BeanObject> beans) {
					userAddressList.removeAll();
					userAddressList.add(beans);
				}
	        	
	        });
		}
		
		fConsignee = UserAddressForm.getConsigneeField(Resources.constants.Consignee_Consignee()+"：");
		fConsignee.setFieldLabel(Resources.constants.Consignee_Consignee());
        formPanel.add(fConsignee, sfd());
        
        MultiField mfArea = new MultiField();
        mfArea.setFieldLabel(Resources.constants.Consignee_Area());
        
        countryList = new ListStore<BeanObject>();
        cbCountry = UserAddressForm.getCountryField();
        
        cbCountry.setStore(countryList);
        cbCountry.setEmptyText(Resources.constants.Consignee_EmptyText());   
        cbCountry.setWidth(150);   
        cbCountry.setTypeAhead(true);   
        cbCountry.setTriggerAction(TriggerAction.ALL);  
        mfArea.add(cbCountry);
        cbCountry.addSelectionChangedListener(new SelectionChangedListener<BeanObject>() {        	
			@Override
			public void selectionChanged(SelectionChangedEvent<BeanObject> se) {
				BeanObject bo = se.getSelectedItem();
				if(bo==null) {
					// this happen when formPanel.clear()
					return;
				}
				changeCountry(bo.getString(IRegion.PK_ID));
				
			}
        });
        Criteria criteria = new Criteria();
        criteria.addCondition(new Condition(IRegion.REGION_TYPE,Condition.EQUALS, "0"));
        new ListService().listBeans(ModelNames.REGION, criteria, new ListService.Listener(){

			@Override
			public void onSuccess(List<BeanObject> beans) {
				countryList.removeAll();
				countryList.add(beans);
			}
        	
        });
        
        provinceList = new ListStore<BeanObject>();
        cbProvince = UserAddressForm.getProvinceField();
        cbProvince.setStore(provinceList);
        cbProvince.setEmptyText(Resources.constants.Consignee_EmptyText());   
        cbProvince.setWidth(150);   
        cbProvince.setTypeAhead(true);   
        cbProvince.setTriggerAction(TriggerAction.ALL);  
        mfArea.add(cbProvince);
        cbProvince.addSelectionChangedListener(new SelectionChangedListener<BeanObject>() {        	
			@Override
			public void selectionChanged(SelectionChangedEvent<BeanObject> se) {
				BeanObject bo = se.getSelectedItem();
				if(bo==null) {
					// this happen when formPanel.clear()
					return;
				}
				changeProvince(bo.getString(IRegion.PK_ID));

			}
        });
        
        cityList = new ListStore<BeanObject>();
        cbCity = UserAddressForm.getCityField();
        cbCity.setStore(cityList);
        cbCity.setEmptyText(Resources.constants.Consignee_EmptyText());   
        cbCity.setWidth(150);   
        cbCity.setTypeAhead(true);   
        cbCity.setTriggerAction(TriggerAction.ALL);  
        mfArea.add(cbCity);
        cbCity.addSelectionChangedListener(new SelectionChangedListener<BeanObject>() {        	
			@Override
			public void selectionChanged(SelectionChangedEvent<BeanObject> se) {
				BeanObject bo = se.getSelectedItem();
				if(bo==null) {
					// this happen when formPanel.clear()
					return;
				}
				changeCity(bo.getString(IRegion.PK_ID));
			}
        });
        
        districtList = new ListStore<BeanObject>();
        cbDistrict = UserAddressForm.getDistrictField(Resources.constants.Consignee_Area()+":");
        cbDistrict.setStore(districtList);
        cbDistrict.setEmptyText(Resources.constants.Consignee_EmptyText());   
        cbDistrict.setWidth(150);   
        cbDistrict.setTypeAhead(true);   
        cbDistrict.setTriggerAction(TriggerAction.ALL);  
        mfArea.add(cbDistrict);
        
        formPanel.add(mfArea, sfd());
        
        fEmail = UserAddressForm.getEmailField(Resources.constants.Consignee_Email()+"：");
        fEmail.setFieldLabel(Resources.constants.Consignee_Email());
        formPanel.add(fEmail, sfd());
        
        fAddress = UserAddressForm.getAddressField(Resources.constants.Consignee_Address()+"：");
        fAddress.setFieldLabel(Resources.constants.Consignee_Address());
        formPanel.add(fAddress, sfd());
        
        fZipcode = UserAddressForm.getZipcodeField();
        fZipcode.setFieldLabel(Resources.constants.Consignee_Zipcode());
        formPanel.add(fZipcode, sfd());
        
        fTel = UserAddressForm.getTelField(Resources.constants.Consignee_Tel()+"：");
        fTel.setFieldLabel(Resources.constants.Consignee_Tel());
        formPanel.add(fTel, sfd());
        
        fMobile = UserAddressForm.getMobileField();
        fMobile.setFieldLabel(Resources.constants.Consignee_Mobile());
        formPanel.add(fMobile, sfd());
        
        fSignBuilding = UserAddressForm.getSignBuildingField();
        fSignBuilding.setFieldLabel(Resources.constants.Consignee_SignBuilding());
        formPanel.add(fSignBuilding, sfd());
        
        fBestTime = UserAddressForm.getBestTimeField();
        fBestTime.setFieldLabel(Resources.constants.Consignee_BestTime());
        formPanel.add(fBestTime, sfd());

	}
	
	private void changeUserAddress(final BeanObject ua) {
		fConsignee.setValue(ua.getString(IUserAddress.CONSIGNEE));
		for(BeanObject bo :countryList.getModels()){
			if(bo.getString(IRegion.PK_ID).equals(ua.getString(IUserAddress.COUNTRY))){
				cbCountry.setValue(bo);
				break;
			}
		}
		ListService listService = new ListService();
		Criteria criteria = new Criteria();
        criteria.addCondition(new Condition(IRegion.REGION_TYPE,Condition.EQUALS, "1"));
        criteria.addCondition(new Condition(IRegion.PARENT_ID,Condition.EQUALS, ua.getString(IUserAddress.COUNTRY) ));
        listService.listBeans(ModelNames.REGION, criteria, new ListService.Listener(){

			@Override
			public void onSuccess(List<BeanObject> beans) {
				provinceList.removeAll();
				provinceList.add(beans);
				for(BeanObject bo :provinceList.getModels()){
					if(bo.getString(IRegion.PK_ID).equals(ua.getString(IUserAddress.PROVINCE))){
						cbProvince.setValue(bo);
						break;
					}
				}
			}        	
        });
		criteria.removeAllConditions();
        criteria.addCondition(new Condition(IRegion.REGION_TYPE,Condition.EQUALS, "2"));
        criteria.addCondition(new Condition(IRegion.PARENT_ID,Condition.EQUALS, ua.getString(IUserAddress.PROVINCE) ));
        listService.listBeans(ModelNames.REGION, criteria, new ListService.Listener(){

			@Override
			public void onSuccess(List<BeanObject> beans) {
				cityList.removeAll();
				cityList.add(beans);
				for(BeanObject bo :cityList.getModels()){
					if(bo.getString(IRegion.PK_ID).equals(ua.getString(IUserAddress.CITY))){
						cbCity.setValue(bo);
						break;
					}
				}
			}
        	
        });
        
        criteria.removeAllConditions();
        criteria.addCondition(new Condition(IRegion.REGION_TYPE,Condition.EQUALS, "3"));
        criteria.addCondition(new Condition(IRegion.PARENT_ID,Condition.EQUALS, ua.getString(IUserAddress.CITY) ));
        listService.listBeans(ModelNames.REGION, criteria, new ListService.Listener(){

			@Override
			public void onSuccess(List<BeanObject> beans) {
				districtList.removeAll();
				districtList.add(beans);
			
				for(BeanObject bo :districtList.getModels()){
					if(bo.getString(IRegion.PK_ID).equals(ua.getString(IUserAddress.DISTRICT))){
						cbDistrict.setValue(bo);
						break;
					}
				}
			}
        	
        });
		
		fEmail.setValue(ua.getString(IUserAddress.EMAIL));
		fAddress.setValue(ua.getString(IUserAddress.ADDRESS));
		fZipcode.setValue(ua.getString(IUserAddress.ZIPCODE));
		fTel.setValue(ua.getString(IUserAddress.TEL));
		fMobile.setValue(ua.getString(IUserAddress.MOBILE));
		fSignBuilding.setValue(ua.getString(IUserAddress.SIGN_BUILDING));
		fBestTime.setValue(ua.getString(IUserAddress.BEST_TIME));
		
	}

	private void changeCountry(String pid) {
		Criteria criteria = new Criteria();
        criteria.addCondition(new Condition(IRegion.REGION_TYPE,Condition.EQUALS, "1"));
        criteria.addCondition(new Condition(IRegion.PARENT_ID,Condition.EQUALS, pid ));
        new ListService().listBeans(ModelNames.REGION, criteria, new ListService.Listener(){

			@Override
			public void onSuccess(List<BeanObject> beans) {
				provinceList.removeAll();
				provinceList.add(beans);
			}
        	
        });
	}
	private void changeProvince(String pid) {
		Criteria criteria = new Criteria();
        criteria.addCondition(new Condition(IRegion.REGION_TYPE,Condition.EQUALS, "2"));
        criteria.addCondition(new Condition(IRegion.PARENT_ID,Condition.EQUALS, pid ));
        new ListService().listBeans(ModelNames.REGION, criteria, new ListService.Listener(){

			@Override
			public void onSuccess(List<BeanObject> beans) {
				cityList.removeAll();
				cityList.add(beans);
			}
        	
        });
	}
	private void changeCity(String pid) {
		Criteria criteria = new Criteria();
        criteria.addCondition(new Condition(IRegion.REGION_TYPE,Condition.EQUALS, "3"));
        criteria.addCondition(new Condition(IRegion.PARENT_ID,Condition.EQUALS, pid ));
        new ListService().listBeans(ModelNames.REGION, criteria, new ListService.Listener(){

			@Override
			public void onSuccess(List<BeanObject> beans) {
				districtList.removeAll();
				districtList.add(beans);
			}
        	
        });
	}
	@Override
	public String getDescription() {
		return "cwBasicTextDescription";
	}

	@Override
	public String getName() {
		return Resources.constants.Consignee_title();
	}
	
	@Override
	protected String validateForm() {
    	Map<String, Object> props = FormUtils.getPropsFromForm(formPanel);
		return UserAddressForm.validate(props);
	}
	
	boolean isChange = false;
	boolean isIncrease = false;
	@Override
    protected void submit() {
		if(getCurState().getOrderId() == null){
			//TODO
			return;
		}
		new ReadService().getBean(ModelNames.ORDERINFO, getCurState().getOrderId(), new ReadService.Listener(){
			public void onSuccess(BeanObject bean) {
				bean.set(IOrderInfo.CONSIGNEE, fConsignee.getValue());
				bean.set(IOrderInfo.COUNTRY, cbCountry.getValue().getString(IRegion.PK_ID));
				bean.set(IOrderInfo.PROVINCE, cbProvince.getValue().getString(IRegion.PK_ID));
				bean.set(IOrderInfo.CITY, cbCity.getValue().getString(IRegion.PK_ID));
				bean.set(IOrderInfo.DISTRICT, cbDistrict.getValue().getString(IRegion.PK_ID));
				bean.set(IOrderInfo.EMAIL, fEmail.getValue());
				bean.set(IOrderInfo.ADDRESS, fAddress.getValue());
				bean.set(IOrderInfo.ZIPCODE, fZipcode.getValue());
				bean.set(IOrderInfo.TEL, fTel.getValue());
				bean.set(IOrderInfo.MOBILE, fMobile.getValue());
				bean.set(IOrderInfo.SIGN_BUILDING, fSignBuilding.getValue());
				bean.set(IOrderInfo.BEST_TIME, fBestTime.getValue());
				new UpdateService().updateBean(getCurState().getOrderId(), bean, new UpdateService.Listener(){
					@Override
					public void onSuccess(Boolean success) {
						if(getCurState().getIsEdit()) {
							new ReadService().getBean(ModelNames.ORDERINFO, getCurState().getOrderId(), new ReadService.Listener() {
								public void onSuccess(final BeanObject bean) {
									new RemoteService().getSpecialService().getOrderFee(getCurState().getPkId(), null, null, new AsyncCallback<Map<String, Object>>() {

										public void onFailure(Throwable caught) {
											caught.printStackTrace();
											Window.alert("ERROR: "+caught.getMessage());
										}


										public void onSuccess(Map<String, Object> result) {
											if(getCurState().getIsEdit()) {
												//判断金额是否改变
												if((Long)bean.get(IOrderInfo.PAY_STATUS) == IOrderInfo.PS_PAYED && (Double)bean.get(IOrderInfo.ORDER_AMOUNT) != (Double)result.get("amount")) {
													isChange = true;
													if((Double)bean.get(IOrderInfo.ORDER_AMOUNT) > (Double)result.get("amount"))
														isIncrease = false;
													else {
														isIncrease = true;
														bean.set(IOrderInfo.PAY_STATUS, IOrderInfo.PS_UNPAYED);
													}
												}
												
												bean.set(IOrderInfo.ORDER_AMOUNT, result.get("amount"));
												bean.set(IOrderInfo.SHIPPING_FEE, result.get("shippingFee"));
												bean.set(IOrderInfo.PAY_FEE, result.get("payFee"));
											}
											new UpdateService().updateBean(getCurState().getPkId(), bean, new UpdateService.Listener() {
												public void onSuccess(Boolean success) {
													if(!isChange) {
														OrderDetailPanel.State newState = new OrderDetailPanel.State();
														newState.setPkId(getCurState().getPkId());
														newState.execute();
													}
													else if(isIncrease) {
														Success.State newState = new Success.State();
												    	newState.setMessage(Resources.constants.OrderDetail_orderAmountIncrease());
												    	OrderDetailPanel.State choice1 = new OrderDetailPanel.State();
												    	choice1.setPkId(getCurState().getPkId());
												    	newState.addChoice(OrderDetailPanel.getInstance().getName(), choice1.getFullHistoryToken());
														newState.execute();
													}
													else {
														Success.State newState = new Success.State();
												    	newState.setMessage(Resources.constants.OrderDetail_orderAmountDecrease());
												    	OrderDetailPanel.State choice1 = new OrderDetailPanel.State();
												    	choice1.setPkId(getCurState().getPkId());
												    	newState.addChoice(OrderDetailPanel.getInstance().getName(), choice1.getFullHistoryToken());
														newState.execute();
													}
												}
												
											});
										}						
									});
								}
							});
						}
						else {
							OrderShippingPanel.State newState = new OrderShippingPanel.State();
							newState.setIsEdit(false);
							newState.setUserId(getCurState().getUserId());
							newState.setPkId(getCurState().getOrderId());
							newState.execute();
						}
					}					
				});
				
	        }
			public void onFailure(Throwable caught) {
				System.out.println("Order not exist");
	        }
		});
		
	}
	
	private void cancel() {
		if(getCurState().getIsEdit()) {
			OrderDetailPanel.State newState = new OrderDetailPanel.State();
			newState.setPkId(getCurState().getPkId());
			newState.execute();
		}
		else {
			//删除订单及订单商品
			new RemoteService().getSpecialService().deleteOrder(getCurState().getOrderId(), new AsyncCallback<Boolean>(){
				public void onFailure(Throwable caught) {
				}
				public void onSuccess(Boolean result) {
				}
			});
			OrderListPanel.State newState = new OrderListPanel.State();
			newState.execute();			
		}
	}

}
