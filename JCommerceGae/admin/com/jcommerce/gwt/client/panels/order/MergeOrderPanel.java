package com.jcommerce.gwt.client.panels.order;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.extjs.gxt.ui.client.Style.HorizontalAlignment;
import com.extjs.gxt.ui.client.event.ButtonEvent;
import com.extjs.gxt.ui.client.event.MessageBoxEvent;
import com.extjs.gxt.ui.client.event.SelectionListener;
import com.extjs.gxt.ui.client.widget.HorizontalPanel;
import com.extjs.gxt.ui.client.widget.MessageBox;
import com.extjs.gxt.ui.client.widget.button.Button;
import com.extjs.gxt.ui.client.widget.form.TextField;
import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.i18n.client.Messages;
import com.google.gwt.user.client.Element;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ListBox;
import com.jcommerce.gwt.client.ContentWidget;
import com.jcommerce.gwt.client.ModelNames;
import com.jcommerce.gwt.client.PageState;
import com.jcommerce.gwt.client.form.BeanObject;
import com.jcommerce.gwt.client.model.IAreaRegion;
import com.jcommerce.gwt.client.model.IGoods;
import com.jcommerce.gwt.client.model.IOrderGoods;
import com.jcommerce.gwt.client.model.IOrderInfo;
import com.jcommerce.gwt.client.model.IPayment;
import com.jcommerce.gwt.client.model.IShipping;
import com.jcommerce.gwt.client.model.IShippingArea;
import com.jcommerce.gwt.client.model.IUser;
import com.jcommerce.gwt.client.resources.Resources;
import com.jcommerce.gwt.client.service.Condition;
import com.jcommerce.gwt.client.service.CreateService;
import com.jcommerce.gwt.client.service.Criteria;
import com.jcommerce.gwt.client.service.DeleteService;
import com.jcommerce.gwt.client.service.ListService;
import com.jcommerce.gwt.client.service.ReadService;
import com.jcommerce.gwt.client.service.RemoteService;
import com.jcommerce.gwt.client.service.UpdateService;
import com.jcommerce.gwt.client.service.WaitService;

public class MergeOrderPanel extends ContentWidget {
	public static interface Constants {
		String MergeOrder_confirmMerge();
        String MergeOrder_mainOrder();
        String MergeOrder_merge();
        String MergeOrder_select();
        String MergeOrder_selectOrder();
        String MergeOrder_subOrder();
        String MergeOrder_title();
        String MergeOrder_differentUsers();
        String MergeOrder_sameOrder();
        String MergeOrder_success();
    }
	public interface MergeOrderMessage extends Messages {
		String MergeOrder_wrongOrder(String orderSn);
	}
	
	public static class State extends PageState {
		public String getPageClassName() {
			return MergeOrderPanel.class.getName();
		}
		public String getMenuDisplayName() {
			return Resources.constants.MergeOrder_title();
		}
	}
	private static final boolean instanceOf = false;
	private State curState = new State();
	
	@Override
	public State getCurState() {
		return curState;
	}
	public void setCurState(State curState) {
		this.curState = curState;
	}
	
	private static MergeOrderPanel instance;
	private MergeOrderPanel() {
		super();
	}
	public static MergeOrderPanel getInstance(){
		if(instance == null) {
			instance = new MergeOrderPanel();
		}
		return instance;
	}	

    ListBox mainOrderList = new ListBox();
    ListBox subOrderList = new ListBox();    

    TextField<String> mainOrderField = new TextField<String>();
    TextField<String> subOrderField = new TextField<String>();      
    
    BeanObject mainOrder = null;
    BeanObject subOrder = null;
	
	protected void onRender(Element parent, int index) {
	    super.onRender(parent, index);
	    
	    HorizontalPanel mainOrderPanel = new HorizontalPanel();
	    mainOrderPanel.setHorizontalAlign(HorizontalAlignment.CENTER);
	    Label mainOrderLabel = new Label(Resources.constants.MergeOrder_mainOrder() + ":");
	    mainOrderLabel.setWidth("70");
	    mainOrderPanel.add(mainOrderLabel);
	    mainOrderPanel.add(mainOrderField);
	    mainOrderPanel.add(mainOrderList);
	    mainOrderList.addChangeHandler(new ChangeHandler() {
			public void onChange(ChangeEvent event) {
				if(mainOrderList.getSelectedIndex() > 0)
					mainOrderField.setValue(mainOrderList.getValue(mainOrderList.getSelectedIndex()));
			}
	    });
	    add(mainOrderPanel);
        
	    HorizontalPanel subOrderPanel = new HorizontalPanel();
	    subOrderPanel.setHorizontalAlign(HorizontalAlignment.CENTER);
	    Label subOrderLabel = new Label(Resources.constants.MergeOrder_subOrder() + ":");
	    subOrderLabel.setWidth("70");
	    subOrderPanel.add(subOrderLabel);
	    subOrderPanel.add(subOrderField);
	    subOrderPanel.add(subOrderList);
	    subOrderList.addChangeHandler(new ChangeHandler() {
			public void onChange(ChangeEvent event) {
				if(subOrderList.getSelectedIndex() > 0)
					subOrderField.setValue(subOrderList.getValue(subOrderList.getSelectedIndex()));
			}
	    });
	    add(subOrderPanel);
        
        HorizontalPanel buttonPanel = new HorizontalPanel();
        buttonPanel.setHorizontalAlign(HorizontalAlignment.CENTER);
        Button btnSearch = new Button(Resources.constants.MergeOrder_merge());
        buttonPanel.add(btnSearch);
        btnSearch.addSelectionListener(new SelectionListener<ButtonEvent>() {
			public void componentSelected(ButtonEvent ce) {
				MessageBox.confirm(Resources.constants.MergeOrder_confirmMerge(), Resources.constants.MergeOrder_confirmMerge(), new com.extjs.gxt.ui.client.event.Listener<MessageBoxEvent>() {  
		        	public void handleEvent(MessageBoxEvent ce) {  
		                Button btn = ce.getButtonClicked(); 
		                if ( btn.getItemId().equals("yes")){
		    				merge();		                	
		                }
		            }  
		        });
			}
		});
        add(buttonPanel);   
	}	
	
	protected void merge() {
		if(getOrder()) {
			new WaitService(new WaitService.Job() {
				public boolean isReady() {
					if(isReady < 2) {
						return false;
					}
					else {
						isReady = 0;
						return true;
					}
				}
	
				public void run() {
					if(mainOrder == null) {
						Window.alert(Resources.messages.MergeOrder_wrongOrder(mainOrderField.getValue()));
					}
					else if(subOrder == null) {
						Window.alert(Resources.messages.MergeOrder_wrongOrder(subOrderField.getValue()));
					}
					else {
						String mainOrderUser = mainOrder.get(IOrderInfo.USER_ID);
						String subOrderUser = subOrder.get(IOrderInfo.USER_ID);
						if(!mainOrderUser.equals(subOrderUser))
							Window.alert(Resources.constants.MergeOrder_differentUsers());
						else
							mergeOrder();
					}
				}
			});
		}
	}
	
	String shippingCodFee = null;
	List<String> regionIdList;
	//合并订单
	protected void mergeOrder() {
		long addTime = new Date().getTime();
		mainOrder.set(IOrderInfo.ADD_TIME, addTime);
		//mainOrder.set(IOrderInfo.PK_ID, "");
		double mainOrderAmount = mainOrder.get(IOrderInfo.GOODS_AMOUNT);
		double subOrderAmount = subOrder.get(IOrderInfo.GOODS_AMOUNT);
		double totalAmount = mainOrderAmount + subOrderAmount;
		mainOrder.set(IOrderInfo.GOODS_AMOUNT, totalAmount);
		
		getWeightPrice((String)subOrder.get(IOrderInfo.PK_ID), (String)mainOrder.get(IOrderInfo.PK_ID));
		
		new WaitService(new WaitService.Job() {
			public boolean isReady() {
				if(orderGoods.size() < 2) {
					return false;
				}
				else
					return true;
			}

			public void run() {
				for(Iterator iterator = orderGoods.iterator(); iterator.hasNext();) {
					BeanObject orderGood = (BeanObject)iterator.next();
					String goodsId = orderGood.get(IOrderGoods.GOODS_ID);
					new ReadService().getBean(ModelNames.GOODS, goodsId, new ReadService.Listener() {
						public void onSuccess(BeanObject bean) {
							weight += (Double)bean.get(IGoods.GOODS_WEIGHT);
						}
					});					
					
					number += (Long)(orderGood.get(IOrderGoods.GOODS_NUMBER));
					amount += (Double)(orderGood.get(IOrderGoods.GOODS_PRICE)) * (Long)(orderGood.get(IOrderGoods.GOODS_NUMBER));
				}

				String formatedWeight;
				Long newWeight = Math.round(weight);
				if( newWeight > 0 ){
					if( newWeight < 1 ){
						formatedWeight = newWeight.intValue() * 1000 + "克";
					}
					else{
						formatedWeight = newWeight.intValue() + "千克";
					}
				}
				else{
					formatedWeight = "0";
				}
				weightPrice.put("weight", new Double(weight));
				weightPrice.put("number", new Double(number) );
				weightPrice.put("amount", new Double(amount) );
				weightPrice.put("formatedWeight", formatedWeight );				

		    	regionIdList = new ArrayList<String>();
	        	regionIdList.add( String.valueOf(mainOrder.get(IOrderInfo.COUNTRY)) );
	        	regionIdList.add( String.valueOf(mainOrder.get(IOrderInfo.PROVINCE)) );
	        	regionIdList.add( String.valueOf(mainOrder.get(IOrderInfo.CITY)) );
	        	regionIdList.add( String.valueOf(mainOrder.get(IOrderInfo.DISTRICT)));
	        	
				if(mainOrder.get(IOrderInfo.SHIPPING_ID) != null) {
					//shippingAreaInfo((String)mainOrder.get(IOrderInfo.SHIPPING_ID),regionIdList);
					shippingAreaInfoIsReady = true;					
				}
				else {
					shippingAreaInfoIsReady = true;
				}
				
				new WaitService(new WaitService.Job() {
					public boolean isReady() {
						if(!shippingAreaInfoIsReady) {
							return false;
						}
						else
							return true;
					}

					public void run() {
						shippingAreaInfoIsReady = false;
						if(mainOrder.get(IOrderInfo.SHIPPING_ID) != null) {
							// 重新计算配送费用   	
				        	if(!shippingAreaInfo.isEmpty()){
				        		RemoteService.getSpecialService().calculate((String)shippingAreaInfo.get("shippingCode"),(Double)weightPrice.get("weight"),(Double)weightPrice.get("amount"),(Map<String, String>)shippingAreaInfo.get("configure"), 
				        		    new AsyncCallback<Double>(){
									public void onFailure(Throwable caught) {
										caught.printStackTrace();
										Window.alert("ERROR: "+caught.getMessage());
									}
									public void onSuccess(Double result) {
										mainOrder.set(IOrderInfo.SHIPPING_FEE, result);
									}
								});
					        	if( ( (Double)(mainOrder.get(IOrderInfo.INSURE_FEE)) != 0 ) && (Integer.parseInt((String)shippingAreaInfo.get("insure")) > 0 )){
					        		mainOrder.set(IOrderInfo.INSURE_FEE, shippingInsureFee( (Double)weightPrice.get("amount") , (String)shippingAreaInfo.get("insure")));
				        		}
					        	else{
					        		mainOrder.set(IOrderInfo.INSURE_FEE, 0.0);
				        		}
					        	if((Boolean)shippingAreaInfo.get("supportCod")){
				        			shippingCodFee = ((Double)shippingAreaInfo.get("payFee")).toString();
				        		}
				        	}
						}		
						
						// 合并余额、已付款金额
						mainOrder.set(IOrderInfo.SURPLUS, (Double)(mainOrder.get(IOrderInfo.SURPLUS)) + (Double)(subOrder.get(IOrderInfo.SURPLUS)));
						mainOrder.set(IOrderInfo.MONEY_PAID, (Double)(mainOrder.get(IOrderInfo.MONEY_PAID)) + (Double)(mainOrder.get(IOrderInfo.MONEY_PAID)));
						if(mainOrder.get(IOrderInfo.PAY_ID)!= ""){					
							payFee((String)mainOrder.get(IOrderInfo.PAY_ID), (Double)mainOrder.get(IOrderInfo.GOODS_AMOUNT),shippingCodFee);
							new WaitService(new WaitService.Job() {
								public boolean isReady() {
									if(!payFeeReady) {
										return false;
									}
									else
										return true;
								}

								public void run() {
									payFeeReady = false;
									mainOrder.set(IOrderInfo.PAY_FEE, payFee);

									mainOrder.set(IOrderInfo.ORDER_AMOUNT, (Double)(mainOrder.get(IOrderInfo.GOODS_AMOUNT)) + (Double)(mainOrder.get(IOrderInfo.SHIPPING_FEE)) 
											+ (Double)(mainOrder.get(IOrderInfo.INSURE_FEE)) + (Double)(mainOrder.get(IOrderInfo.PAY_FEE))
											- (Double)(mainOrder.get(IOrderInfo.SURPLUS)) - (Double)(mainOrder.get(IOrderInfo.MONEY_PAID)));
									System.out.println(mainOrder.get(IOrderInfo.ORDER_AMOUNT));
									
									//删除原订单
									new DeleteService().deleteBean(ModelNames.ORDERINFO, (String)mainOrder.get(IOrderInfo.PK_ID), null);
									new DeleteService().deleteBean(ModelNames.ORDERINFO, (String)subOrder.get(IOrderInfo.PK_ID), null);
									new CreateService().createBean(mainOrder, new CreateService.Listener() {
										public void onSuccess(final String id) {
											//更新订单商品
											Criteria criteria = new Criteria();
											criteria.addCondition(new Condition(IOrderGoods.ORDER_ID, Condition.EQUALS, (String)mainOrder.get(IOrderInfo.PK_ID)));
											final List<BeanObject> orderGoods = new ArrayList<BeanObject>();
											new ListService().listBeans(ModelNames.ORDERGOODS, criteria, new ListService.Listener() {
												public void onSuccess(List<BeanObject> beans) {
													orderGoods.addAll(beans);
												}
											});
											criteria.removeAllConditions();
											criteria.addCondition(new Condition(IOrderGoods.ORDER_ID, Condition.EQUALS, (String)subOrder.get(IOrderInfo.PK_ID)));
											new ListService().listBeans(ModelNames.ORDERGOODS, criteria, new ListService.Listener() {
												public void onSuccess(List<BeanObject> beans) {
													orderGoods.addAll(beans);
												}
											});
											
											new WaitService(new WaitService.Job() {
												public boolean isReady() {
													if(orderGoods.size() < 2) {
														return false;
													}
													else
														return true;
												}

												public void run() {
													for(BeanObject goods : orderGoods) {
														System.out.println(goods.get(IOrderGoods.ORDER_ID));
														goods.set(IOrderGoods.ORDER_ID, id);
														System.out.println(goods.get(IOrderGoods.ORDER_ID));
														new UpdateService().updateBean((String)goods.get(IOrderGoods.PK_ID), goods, null);
													}
													Window.alert(Resources.constants.MergeOrder_success());
													refresh();
												}
											});				
										}
									});
								}
							});	
				        }
					}
				});				
			}
		});
	}

	private double shippingInsureFee(Double goodsAmount, String insure) {
		if(insure.indexOf("%") == -1){
    		return Double.parseDouble(insure);
    	}
    	else{
    		return Math.ceil( Double.parseDouble(insure.substring(0,insure.indexOf("%"))) * goodsAmount / 100 );
    	}
	}

	Map<String,Object> shippingAreaInfo = new HashMap<String,Object>();
	boolean shippingAreaInfoIsReady = false;
	private void shippingAreaInfo(String shippingId, final List<String> regionIdList) {
		Criteria criteria = new Criteria();
		Condition condition = new Condition();		

		condition.setField(IShipping.PK_ID);
		condition.setOperator(Condition.EQUALS);
		condition.setValue(shippingId);
		
		Condition condition2 = new Condition();		

		condition2.setField(IShipping.ENABLED);
		condition2.setOperator(Condition.EQUALS);
		condition2.setValue("true");
		
		criteria.addCondition(condition);
		criteria.addCondition(condition2);
		new ListService().listBeans(ModelNames.SHIPPING, criteria, new ListService.Listener() {
			public void onSuccess(List<BeanObject> beans) {
				if(beans.size() > 0) {
					final BeanObject shipping = beans.get(0);
					String[] ids = shipping.getIDs("shippingAreas");
					new ReadService().getBeans(ModelNames.SHIPPINGAREA, ids, new ReadService.Listener() {
						public void onSuccess(List<BeanObject> beans) {
							for(final BeanObject shippingArea : beans) {
								String[] areaRegionIds = shippingArea.getIDs(IShippingArea.AREA_REGIONS);
								new ReadService().getBeans(ModelNames.AREAREGION, areaRegionIds, new ReadService.Listener() {
									public void onSuccess(List<BeanObject> beans) {
										for(BeanObject areaRegion : beans) {
											if(regionIdList.contains(areaRegion.get(IAreaRegion.REGION_ID))){
												RemoteService.getSpecialService().deserialize((String)shippingArea.get(IShippingArea.CONFIGURE), new AsyncCallback<Map<String,String>>(){
													public void onFailure(Throwable caught) {
														caught.printStackTrace();
														Window.alert("ERROR: "+caught.getMessage());
													}
													@Override
													public void onSuccess(Map<String, String> shippingConfig) {

														if(shippingConfig.containsKey("payFee")){
															shippingAreaInfo.put("payFee", shippingConfig.get("payFee"));
														}
														else{
															shippingAreaInfo.put("payFee", new Double(0));
														}
														shippingAreaInfo.put("shippingCode", shipping.get(IShipping.SHIPPING_CODE));
														shippingAreaInfo.put("shippingName", shipping.get(IShipping.SHIPPING_NAME));
														shippingAreaInfo.put("shippingDesc", shipping.get(IShipping.SHIPPING_DESC));
														shippingAreaInfo.put("insure", shipping.get(IShipping.INSURE));
														shippingAreaInfo.put("supportCod", shipping.get(IShipping.SUPPORT_COD));
														shippingAreaInfo.put("configure", shippingConfig);										
													}
												});
											}
										}
										shippingAreaInfoIsReady = true;
									}
								});
							}
						}
					});
				}
				else {
					shippingAreaInfoIsReady = true;
				}
			}
		});
	}

	List<BeanObject> orderGoods = new ArrayList<BeanObject>();
	Map<String,Object> weightPrice = new HashMap<String,Object>();
	double weight = 0;
	double number = 0;
	double amount = 0;
	
	private void getWeightPrice(String mainOrderId, String subOrderId) {
		/* 获得购物车中商品的总重量 */
		
		Criteria criteria = new Criteria();
		criteria.addCondition(new Condition(IOrderGoods.ORDER_ID, Condition.EQUALS, subOrderId));
		new ListService().listBeans(ModelNames.ORDERGOODS, criteria, new ListService.Listener() {
			public void onSuccess(List<BeanObject> beans) {
				orderGoods.addAll(beans);
			}			
		});
		
		criteria.removeAllConditions();
		criteria.addCondition(new Condition(IOrderGoods.ORDER_ID, Condition.EQUALS, mainOrderId));
		new ListService().listBeans(ModelNames.ORDERGOODS, criteria, new ListService.Listener() {
			public void onSuccess(List<BeanObject> beans) {
				orderGoods.addAll(beans);
			}			
		});
	}

	double payFee = 0;
	boolean payFeeReady = false;
	private void payFee(String payId, final double amount, final String codFee) {
		new ReadService().getBean(ModelNames.PAYMENT, payId, new ReadService.Listener() {
			public void onSuccess(BeanObject bean) {
				String rate = ((Boolean)bean.get(IPayment.IS_COD) && (codFee != null )) ? codFee : (String)bean.get(IPayment.PAY_FEE);
				
				if(rate.indexOf("%") != -1){
		    		/* 支付费用是一个比例 */
		    		double val = Double.parseDouble(rate.substring(0,rate.indexOf("%"))) / 100 ;
		    		payFee = val > 0 ? amount * val / ( 1 - val ) : 0 ;
		    	}
		    	else{
		    		payFee = Double.parseDouble(rate);
		    	}
				payFeeReady = true;
			}			
		});
	}
	
	int isReady = 0;
	private boolean getOrder() {	
		mainOrder = null;
		subOrder = null;
		
		String mainOrderSN = mainOrderField.getValue();
		String subOrderSN = subOrderField.getValue();
		
		if(mainOrderSN == null || subOrderSN == null) {
			Window.alert(Resources.constants.MergeOrder_selectOrder());
			return false;
		}
		else if(mainOrderSN.equals(subOrderSN)) {
			Window.alert(Resources.constants.MergeOrder_sameOrder());
			return false;
		}
		else {
			Criteria criteria = new Criteria();
			criteria.addCondition(new Condition(IOrderInfo.ORDER_SN, Condition.EQUALS, mainOrderSN));		
			new ListService().listBeans(ModelNames.ORDERINFO, criteria, new ListService.Listener() {
				public void onSuccess(List<BeanObject> beans) {
					for(Iterator<BeanObject> it = beans.iterator(); it.hasNext();) {
						BeanObject order = it.next();
						mainOrder = order;
					}
					isReady++;
				}
			});
			
			criteria.removeAllConditions();
			criteria.addCondition(new Condition(IOrderInfo.ORDER_SN, Condition.EQUALS, subOrderSN));
			new ListService().listBeans(ModelNames.ORDERINFO, criteria, new ListService.Listener() {
				public void onSuccess(List<BeanObject> beans) {
					for(Iterator<BeanObject> it = beans.iterator(); it.hasNext();) {
						BeanObject order = it.next();
						subOrder = order;
					}
					isReady++;
				}
			});	
			return true;
		}
	}
	
	public void refresh() {
		//获得可合并订单,可合并订单的条件，未付款&&未配送&&(未确认||已确认)
		Criteria criteria = new Criteria();
    	Condition unPayed = new Condition(IOrderInfo.PAY_STATUS, Condition.EQUALS, "0");
    	Condition unShipped = new Condition(IOrderInfo.SHIPPING_STATUS, Condition.EQUALS, "0");
    	Condition unConfirmed = new Condition(IOrderInfo.ORDER_STATUS, Condition.EQUALS, "0");
    	Condition confirmed = new Condition(IOrderInfo.ORDER_STATUS, Condition.EQUALS, "1");
    	criteria.addCondition(unPayed);
    	criteria.addCondition(unShipped);
    	criteria.addCondition(confirmed);
    	
    	mainOrderList.clear();
    	subOrderList.clear();
    	mainOrderList.addItem(Resources.constants.MergeOrder_select(), "0");
    	subOrderList.addItem(Resources.constants.MergeOrder_select(), "0");
    	mainOrderField.clear();
    	subOrderField.clear();
    	populateOrderList(criteria);  
    	
    	criteria.removeCondition(confirmed);
    	criteria.addCondition(unConfirmed);
    	populateOrderList(criteria);
    	
	}	
	
	private void populateOrderList(Criteria criteria) {
		new ListService().listBeans(ModelNames.ORDERINFO, criteria,
				new ListService.Listener() {
					public synchronized void onSuccess(List<BeanObject> result) {
						for (Iterator<BeanObject> it = result.iterator(); it.hasNext();) {
							final BeanObject order = it.next();
							String userId = order.getString(IOrderInfo.USER_ID);
							new ReadService().getBean(ModelNames.USER, userId, new ReadService.Listener() {
								public void onSuccess(BeanObject bean) {
									mainOrderList.addItem(order.getString(IOrderInfo.ORDER_SN) + "[" + bean.get(IUser.USER_NAME) + "]",
											order.getString(IOrderInfo.ORDER_SN));
									subOrderList.addItem(order.getString(IOrderInfo.ORDER_SN) + "[" + bean.get(IUser.USER_NAME) + "]",
											order.getString(IOrderInfo.ORDER_SN));
								}
							});
						}
					}
				});	
	}
	
	@Override
	public String getDescription() {
		return "cwBasicTextDescription";
	}
	
    @Override
    public Button getShortCutButton() {
      Button buttonOrderList = new Button(Resources.constants.OrderList_title());
      buttonOrderList.addSelectionListener(new SelectionListener<ButtonEvent>() {
          public void componentSelected(ButtonEvent ce) {
          	onButtonListClicked();
          }
      });
      return buttonOrderList;
    }
    public void onButtonListClicked() {
		OrderListPanel.State newState = new OrderListPanel.State();
		newState.execute();
    }

	@Override
	public String getName() {
		return Resources.constants.MergeOrder_title();	
	}

}
