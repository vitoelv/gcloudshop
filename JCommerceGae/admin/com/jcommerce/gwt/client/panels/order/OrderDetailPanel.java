package com.jcommerce.gwt.client.panels.order;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.extjs.gxt.ui.client.data.BasePagingLoader;
import com.extjs.gxt.ui.client.event.ButtonEvent;
import com.extjs.gxt.ui.client.event.SelectionListener;
import com.extjs.gxt.ui.client.store.ListStore;
import com.extjs.gxt.ui.client.store.Record;
import com.extjs.gxt.ui.client.store.StoreEvent;
import com.extjs.gxt.ui.client.store.StoreListener;
import com.extjs.gxt.ui.client.widget.Component;
import com.extjs.gxt.ui.client.widget.ContentPanel;
import com.extjs.gxt.ui.client.widget.HorizontalPanel;
import com.extjs.gxt.ui.client.widget.button.Button;
import com.extjs.gxt.ui.client.widget.form.TextArea;
import com.extjs.gxt.ui.client.widget.grid.CheckBoxSelectionModel;
import com.extjs.gxt.ui.client.widget.grid.ColumnConfig;
import com.extjs.gxt.ui.client.widget.grid.ColumnModel;
import com.extjs.gxt.ui.client.widget.grid.Grid;
import com.extjs.gxt.ui.client.widget.toolbar.PagingToolBar;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.user.client.Element;
import com.google.gwt.user.client.ui.Label;
import com.jcommerce.gwt.client.ContentWidget;
import com.jcommerce.gwt.client.ModelNames;
import com.jcommerce.gwt.client.PageState;
import com.jcommerce.gwt.client.form.BeanObject;
import com.jcommerce.gwt.client.model.IComment;
import com.jcommerce.gwt.client.model.IOrderAction;
import com.jcommerce.gwt.client.model.IOrderGoods;
import com.jcommerce.gwt.client.model.IOrderInfo;
import com.jcommerce.gwt.client.model.IRegion;
import com.jcommerce.gwt.client.model.IUser;
import com.jcommerce.gwt.client.resources.Resources;
import com.jcommerce.gwt.client.service.Condition;
import com.jcommerce.gwt.client.service.Criteria;
import com.jcommerce.gwt.client.service.ListService;
import com.jcommerce.gwt.client.service.PagingListService;
import com.jcommerce.gwt.client.service.ReadService;
import com.jcommerce.gwt.client.service.WaitService;
import com.jcommerce.gwt.client.widgets.MyContentPanel;
import com.jcommerce.gwt.client.widgets.TotalPriceCellRenderer;

public class OrderDetailPanel  extends ContentWidget{
	
	public static interface Constants {
		String OrderDetail_title();
		String OrderDetail_baseInfo();
		String OrderDetail_otherInfo();
		String OrderDetail_consigneeInfo();
		String OrderDetail_goodsInfo();
		String OrderDetail_feeInfo();
		String OrderDetail_operationInfo();
		String OrderDetail_edit();
		String OrderDetail_payTime();
		String OrderDetail_shippingTime();
		String OrderDetail_bestTime();
		String OrderDetail_building();
		String OrderDetail_price();
		String OrderDetail_goodsNum();
		String OrderDetail_goodsAttr();
		String OrderDetail_totalPrice();
		String OrderDetail_subTotalPrice();
		String OrderDetail_invoiceNo();
		String OrderDetail_referer();
		String OrderDetail_goodsAmount();
		String OrderDetail_payFee();
		String OrderDetail_shippingFee();
		String OrderDetail_insuranceFee();
		String OrderDetail_orderAmount();
		String OrderDetail_moneyPaid();
		String OrderDetail_surplus();
		String OrderDetail_moneyShouldPay();
		String OrderDetail_selfSite();
		String OrderDetail_operationRemark();
		String OrderDetail_operableAction();
		String OrderDetail_comfirm();
		String OrderDetail_pay();
		String OrderDetail_unpay();
		String OrderDetail_prepare();
		String OrderDetail_ship();
		String OrderDetail_unship();
		String OrderDetail_receive();
		String OrderDetail_cancel();
		String OrderDetail_invalid();
		String OrderDetail_return();
		String OrderDetail_remove();
		String OrderDetail_actionUser();
		String OrderDetail_logTime();
		String OrderDetail_orderStatus();
		String OrderDetail_payStatus();
		String OrderDetail_shippingStatus();
		String OrderDetail_actionNote();
    }

	private static OrderDetailPanel instance = null;
	private State curState = new State();

	@Override
	protected State getCurState() {
		return curState;
	}

	@Override
	public String getDescription() {
		return "cwBasicTextDescription";
	}

	@Override
	public String getName() {
		return Resources.constants.OrderDetail_title();
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
	
	public static OrderDetailPanel getInstance(){
		if (instance == null) {
			instance = new OrderDetailPanel();
		}
		return instance;
	}
	
	public static class State extends PageState {
		public static final String PK_ID = "pkId";
		@Override
		public String getPageClassName() {
			return OrderDetailPanel.class.getName();
		}
		@Override
		public String getMenuDisplayName() {
			return Resources.constants.OrderDetail_title();
		}
		public void setPkId(String gtid) {
			setValue(PK_ID, gtid);
		}
		public String getPkId() {
			return (String)getValue(PK_ID);
		}
	}
	
	ContentPanel basePanel = new ContentPanel();
	MyContentPanel consigneePanel = new MyContentPanel();
	MyContentPanel goodsPanel = new MyContentPanel();
	MyContentPanel feePanel = new MyContentPanel();
	ContentPanel operationPanel = new ContentPanel();
	@Override
	protected void onRender(Element parent, int index) {
		super.onRender(parent, index);		
		
		basePanel.setHeading(Resources.constants.OrderDetail_baseInfo());
		renderBasePanel();
		add(basePanel);
		
		consigneePanel.setHeading(Resources.constants.OrderDetail_consigneeInfo(), Resources.constants.OrderDetail_edit());
		renderConsigneePanel();
		add(consigneePanel);
		
		goodsPanel.setHeading(Resources.constants.OrderDetail_goodsInfo(), Resources.constants.OrderDetail_edit());
		renderGoodsPanel();
		add(goodsPanel);
		
		feePanel.setHeading(Resources.constants.OrderDetail_feeInfo(), Resources.constants.OrderDetail_edit());
		renderFeePanel();
		add(feePanel);

		operationPanel.setHeading(Resources.constants.OrderDetail_operationInfo());
		renderOperationPanel();
		add(operationPanel);
	}
	
	Label snLabel = new Label("");
	Label stateLabel = new Label("");
	Label buyerLabel = new Label("");
	Label addTimeLabel = new Label("");
	Label payMannerLabel = new Label("");
	Label payTimeLabel = new Label("");
	Label shippingMannerLabel = new Label("");
	Label shippingTimeLabel = new Label("");
	Label invoiceNoLabel = new Label("");
	Label refererLabel = new Label("");
	
	private void renderBasePanel() {
		HorizontalPanel ssPanel = new HorizontalPanel();
        HorizontalPanel snPanel = getPanel(Resources.constants.OrderList_orderSN(), snLabel);
        HorizontalPanel statePanel = getPanel(Resources.constants.OrderList_state(), stateLabel);
        ssPanel.add(snPanel);
        ssPanel.add(statePanel);
        basePanel.add(ssPanel);
        
        HorizontalPanel btPanel = new HorizontalPanel();
        HorizontalPanel buyerPanel = getPanel(Resources.constants.SearchOrder_buyer(), buyerLabel);
        HorizontalPanel addTimePanel = getPanel(Resources.constants.OrderList_addTime(), addTimeLabel);
        btPanel.add(buyerPanel);
        btPanel.add(addTimePanel);
        basePanel.add(btPanel);
        
        HorizontalPanel ptPanel = new HorizontalPanel();
        HorizontalPanel payMannerPanel = getPanel(Resources.constants.SearchOrder_payManner(), payMannerLabel);
        HorizontalPanel payTimePanel = getPanel(Resources.constants.OrderDetail_payTime(), payTimeLabel);
        ptPanel.add(payMannerPanel);
        ptPanel.add(payTimePanel);
        basePanel.add(ptPanel);
        
        HorizontalPanel stPanel = new HorizontalPanel();
        HorizontalPanel shippingMannerPanel = getPanel(Resources.constants.SearchOrder_shipManner(), shippingMannerLabel);
        HorizontalPanel shippingTimePanel = getPanel(Resources.constants.OrderDetail_shippingTime(), shippingTimeLabel);
        stPanel.add(shippingMannerPanel);
        stPanel.add(shippingTimePanel);
        basePanel.add(stPanel);
        
        HorizontalPanel irPanel = new HorizontalPanel();
        HorizontalPanel invoiceNoPanel = getPanel(Resources.constants.OrderDetail_invoiceNo(), invoiceNoLabel);
        HorizontalPanel refererPanel = getPanel(Resources.constants.OrderDetail_referer(), refererLabel);
        irPanel.add(invoiceNoPanel);
        irPanel.add(refererPanel);
        basePanel.add(irPanel);
        
	}
	
	Label consigneeLabel = new Label("");
	Label emailLabel = new Label("");
	Label addressLabel = new Label("");
	Label zipcodeLabel = new Label("");
	Label telLabel = new Label("");
	Label mobileLabel = new Label("");
	Label buildingLabel = new Label("");
	Label bestTimeLabel = new Label("");
	private void renderConsigneePanel() {
		HorizontalPanel cePanel = new HorizontalPanel();
        HorizontalPanel conPanel = getPanel(Resources.constants.OrderList_consignee(), consigneeLabel);
        HorizontalPanel emailPanel = getPanel(Resources.constants.SearchOrder_email(), emailLabel);
        cePanel.add(conPanel);
        cePanel.add(emailPanel);
        consigneePanel.add(cePanel);
        
        HorizontalPanel azPanel = new HorizontalPanel();
        HorizontalPanel addressPanel = getPanel(Resources.constants.SearchOrder_address(), addressLabel);
        HorizontalPanel zipcodePanel = getPanel(Resources.constants.SearchOrder_zipcode(), zipcodeLabel);
        azPanel.add(addressPanel);
        azPanel.add(zipcodePanel);
        consigneePanel.add(azPanel);
        
        HorizontalPanel tmPanel = new HorizontalPanel();
        HorizontalPanel telPanel = getPanel(Resources.constants.SearchOrder_phone(), telLabel);
        HorizontalPanel mobilePanel = getPanel(Resources.constants.SearchOrder_tel(), mobileLabel);
        tmPanel.add(telPanel);
        tmPanel.add(mobilePanel);
        consigneePanel.add(tmPanel);
        
        HorizontalPanel bbPanel = new HorizontalPanel();
        HorizontalPanel buildingPanel = getPanel(Resources.constants.OrderDetail_building(), buildingLabel);
        HorizontalPanel bestTimePanel = getPanel(Resources.constants.OrderDetail_bestTime(), bestTimeLabel);
        bbPanel.add(buildingPanel);
        bbPanel.add(bestTimePanel);
        consigneePanel.add(bbPanel);        
	}
	
	
	PagingToolBar goodsToolBar;	
	
	private void renderGoodsPanel() {
		Criteria criteria = new Criteria();
		criteria.addCondition(new Condition(IOrderGoods.ORDER_ID, Condition.EQUALS, getCurState().getPkId()));
		
		BasePagingLoader loader = new PagingListService().getLoader(ModelNames.ORDERGOODS, criteria);	  	
	    final ListStore<BeanObject> store = new ListStore<BeanObject>(loader);		
		goodsToolBar = new PagingToolBar(10);
		goodsToolBar.bind(loader);
		
		List<ColumnConfig> columns = new ArrayList<ColumnConfig>();
		
		ColumnConfig colGoodsName = new ColumnConfig(IOrderGoods.GOODS_NAME, Resources.constants.Goods_name(), 100);
		columns.add(colGoodsName);
		ColumnConfig colGoodsSN = new ColumnConfig(IOrderGoods.GOODS_SN, Resources.constants.Goods_SN(), 100);
		columns.add(colGoodsSN);
		ColumnConfig colGoodsPrice = new ColumnConfig(IOrderGoods.GOODS_PRICE, Resources.constants.OrderDetail_price(), 100);
		columns.add(colGoodsPrice);
		ColumnConfig colGoodsNum = new ColumnConfig(IOrderGoods.GOODS_NUMBER, Resources.constants.OrderDetail_goodsNum(), 100);
		columns.add(colGoodsNum);
		ColumnConfig colGoodsAttr = new ColumnConfig(IOrderGoods.GOODS_ATTR, Resources.constants.OrderDetail_goodsAttr(), 100);
		columns.add(colGoodsAttr);
		
//		ColumnConfig colTotalPrice = new ColumnConfig("subtotal", Resources.constants.OrderDetail_subTotalPrice(), 100);
//		colTotalPrice.setRenderer(new TotalPriceCellRenderer());
//		columns.add(colTotalPrice);
		
		ColumnModel cm = new ColumnModel(columns);		
		Grid<BeanObject> grid = new Grid<BeanObject>(store, cm);
		grid.setLoadMask(true);
		grid.setBorders(true);
		//grid.setAutoExpandColumn("subtotal");
		goodsPanel.add(grid);
		goodsPanel.setBottomComponent(goodsToolBar);
	}

	private HorizontalPanel getPanel(String label, Label dataLabel) {
		HorizontalPanel hp = new HorizontalPanel();
		Label title = new Label(label + ":");
		title.setWidth("70");
		hp.add(title);
		hp.add(dataLabel);
		hp.setWidth("500");
		return hp;
	}
	
	Label goodsAmountLabel = new Label("");
	Label shippingFeeLabel = new Label("");
	Label insuranceFeeLabel = new Label("");
	Label payFeeLabel = new Label("");
	Label orderAmountLabel = new Label("");
	Label moneyPaidLabel = new Label("");
	Label surplusLabel = new Label("");
	Label moneyShouldPayLabel = new Label("");
	private void renderFeePanel() {
		HorizontalPanel calculateTotalAmountPanel = new HorizontalPanel();
		calculateTotalAmountPanel.add(new Label(Resources.constants.OrderDetail_goodsAmount() + ":"));
		calculateTotalAmountPanel.add(goodsAmountLabel);
		calculateTotalAmountPanel.add(new Label("+" + Resources.constants.OrderDetail_shippingFee() + ":"));
		calculateTotalAmountPanel.add(shippingFeeLabel);
		calculateTotalAmountPanel.add(new Label("+" + Resources.constants.OrderDetail_insuranceFee() + ":"));
		calculateTotalAmountPanel.add(insuranceFeeLabel);
		calculateTotalAmountPanel.add(new Label("+" + Resources.constants.OrderDetail_payFee() + ":"));
		calculateTotalAmountPanel.add(payFeeLabel);
		feePanel.add(calculateTotalAmountPanel);
		
		
		HorizontalPanel totalAmountPanel = new HorizontalPanel();
		totalAmountPanel.add(new Label("=" + Resources.constants.OrderDetail_orderAmount() + ":"));
		totalAmountPanel.add(orderAmountLabel);
		feePanel.add(totalAmountPanel);
		
		HorizontalPanel calculateOrderAmountPanel = new HorizontalPanel();
		calculateOrderAmountPanel.add(new Label("-" + Resources.constants.OrderDetail_moneyPaid() + ":"));
		calculateOrderAmountPanel.add(moneyPaidLabel);
		calculateOrderAmountPanel.add(new Label("-" + Resources.constants.OrderDetail_surplus() + ":"));
		calculateOrderAmountPanel.add(surplusLabel);
		feePanel.add(calculateOrderAmountPanel);
		
		HorizontalPanel orderAmountPanel = new HorizontalPanel();
		orderAmountPanel.add(new Label("=" + Resources.constants.OrderDetail_moneyShouldPay() + ":"));
		orderAmountPanel.add(moneyShouldPayLabel);
		feePanel.add(orderAmountPanel);
	}
	
	TextArea remarkArea= new TextArea();
	HorizontalPanel buttonPanel = new HorizontalPanel();
	PagingToolBar operationToolBar;	
	private void renderOperationPanel() {
		HorizontalPanel remarkPanel = new HorizontalPanel();
		Label remarkLabel = new Label(Resources.constants.OrderDetail_operationRemark());
		remarkLabel.setWidth("100");
		remarkPanel.add(remarkLabel);
		remarkArea.setWidth("700");
		remarkPanel.add(remarkArea);
		operationPanel.add(remarkPanel);
		Component a = new Button("");
		
		Label operableLabel = new Label(Resources.constants.OrderDetail_operableAction());
		operableLabel.setWidth("100");
		buttonPanel.add(operableLabel);
		
		initOperableMap();
		for(Object key : operableMap.keySet()) {
			Button button = new Button((String)key);
			button.addSelectionListener(new SelectionListener<ButtonEvent>() {
		          public void componentSelected(ButtonEvent ce) {
		          	changeOrderState(ce);
		          }
		      });
			button.setId((String)key);
			button.setVisible(operableMap.get(key));
			buttonPanel.add(button);
		}
		
		operationPanel.add(buttonPanel);
		
		//取得订单操作记录
		Criteria criteria = new Criteria();
		criteria.addCondition(new Condition(IOrderAction.ORDER_ID, Condition.EQUALS, getCurState().getPkId()));
		
		BasePagingLoader loader = new PagingListService().getLoader(ModelNames.ORDERACTION, criteria);
		final ListStore<BeanObject> store = new ListStore<BeanObject>(loader);
		
		operationToolBar = new PagingToolBar(10);
		operationToolBar.bind(loader);
		
		List<ColumnConfig> columns = new ArrayList<ColumnConfig>();
		
		ColumnConfig colActionUser = new ColumnConfig(IOrderAction.ACTION_USER, Resources.constants.OrderDetail_actionUser(), 100);
		columns.add(colActionUser);
		ColumnConfig colLogTime = new ColumnConfig(IOrderAction.LOG_TIME, Resources.constants.OrderDetail_logTime(), 100);
		columns.add(colLogTime);
		ColumnConfig colOrderStatus = new ColumnConfig(IOrderAction.ORDER_STATUS, Resources.constants.OrderDetail_orderStatus(), 100);
		columns.add(colOrderStatus);
		ColumnConfig colPayStatus = new ColumnConfig(IOrderAction.PAY_STATUS, Resources.constants.OrderDetail_payStatus(), 100);
		columns.add(colPayStatus);
		ColumnConfig colshippingStatus = new ColumnConfig(IOrderAction.SHIPPING_STATUS, Resources.constants.OrderDetail_shippingStatus(), 100);
		columns.add(colshippingStatus);
		ColumnConfig colNote = new ColumnConfig(IOrderAction.ACTION_NOTE, Resources.constants.OrderDetail_actionNote(), 100);
		columns.add(colNote);
		
		ColumnModel cm = new ColumnModel(columns);		
		Grid<BeanObject> grid = new Grid<BeanObject>(store, cm);
		grid.setLoadMask(true);
		grid.setBorders(true);
		grid.setAutoExpandColumn(IOrderAction.ACTION_NOTE);
		operationPanel.add(grid);
		operationPanel.setBottomComponent(operationToolBar);
	}

	private void initOperableMap() {
		operableMap.put(Resources.constants.OrderDetail_comfirm(), false);
		operableMap.put(Resources.constants.OrderDetail_pay(), false);
		operableMap.put(Resources.constants.OrderDetail_unpay(), false);
		operableMap.put(Resources.constants.OrderDetail_prepare(), false);
		operableMap.put(Resources.constants.OrderDetail_ship(), false);
		operableMap.put(Resources.constants.OrderDetail_unship(), false);
		operableMap.put(Resources.constants.OrderDetail_receive(), false);
		operableMap.put(Resources.constants.OrderDetail_cancel(), false);
		operableMap.put(Resources.constants.OrderDetail_invalid(), false);
		operableMap.put(Resources.constants.OrderDetail_return(), false);
		operableMap.put(Resources.constants.OrderDetail_remove(), false);		
	}

	protected void changeOrderState(ButtonEvent ce) {
		// TODO Auto-generated method stub
		
	}

	/**
	 * 返回某个订单可执行的操作列表，包括权限判断
	 * @return  array   可执行的操作列表  confirm, pay, unpay, prepare, ship, unship, receive, cancel, invalid, return, remove
	 */
	Map<String, Boolean> operableMap = new HashMap<String, Boolean>();
	boolean getOperableReady = false;
	private void getOperableList() {
		new WaitService(new WaitService.Job() {
			public boolean isReady() {
				if(orderStatus == -1 || payStatus == -1 || shippingStatus == -1) {
					return false;
				}
				else {
					return true;
				}
			}
			public void run() {
				initOperableMap();
				/* 状态：未确认 => 未付款、未发货 */
				if(orderStatus == IOrderInfo.OS_UNCONFIRMED) {
					operableMap.put(Resources.constants.OrderDetail_comfirm(), true); // 确认
					operableMap.put(Resources.constants.OrderDetail_invalid(), true); // 无效
					operableMap.put(Resources.constants.OrderDetail_cancel(), true); // 取消
					//不是货到付款
					operableMap.put(Resources.constants.OrderDetail_pay(), true); // 付款
				}				

				/* 状态：已确认 */
				else if(orderStatus == IOrderInfo.OS_CONFIRMED) {
		            /* 状态：已确认、未付款 */
			        if (payStatus == IOrderInfo.PS_UNPAYED)
			        {
		                /* 状态：已确认、未付款、未发货（或配货中） */
			            if (IOrderInfo.SS_UNSHIPPED == shippingStatus || IOrderInfo.SS_PREPARING == shippingStatus)
			            {
			            	operableMap.put(Resources.constants.OrderDetail_cancel(), true); // 取消
			            	operableMap.put(Resources.constants.OrderDetail_invalid(), true);  // 无效
			                
			                /* 不是货到付款 */
			            	operableMap.put(Resources.constants.OrderDetail_pay(), true); // 付款
			            }
		                /* 状态：已确认、未付款、已发货或已收货 */
			            else
			            {
			            	operableMap.put(Resources.constants.OrderDetail_pay(), true); // 付款
			                if (IOrderInfo.SS_SHIPPED == shippingStatus)
			                {
			                	operableMap.put(Resources.constants.OrderDetail_receive(), true); // 收货确认
			                }
			                operableMap.put(Resources.constants.OrderDetail_unship(), true); // 设为未发货
			                operableMap.put(Resources.constants.OrderDetail_return(), true); // 退货
			            }
			        }

		            /* 状态：已确认、已付款和付款中 */
			        else
			        {
		                /* 状态：已确认、已付款和付款中、未发货（配货中） */
			            if (IOrderInfo.SS_UNSHIPPED == shippingStatus || IOrderInfo.SS_PREPARING == shippingStatus)
			            {
			                if (IOrderInfo.SS_UNSHIPPED == shippingStatus)
			                {
//			                	operableList.add(Resources.constants.OrderDetail_prepare()); // 配货
			                	operableMap.put(Resources.constants.OrderDetail_prepare(), true); // 配货
			                }
			                operableMap.put(Resources.constants.OrderDetail_ship(), true); // 发货
			                operableMap.put(Resources.constants.OrderDetail_unpay(), true); // 设为未付款
			                operableMap.put(Resources.constants.OrderDetail_cancel(), true); // 取消
			            }

		                /* 状态：已确认、已付款和付款中、已发货或已收货 */
			            else
			            {
			                if (IOrderInfo.SS_SHIPPED == shippingStatus)
			                {
			                	operableMap.put(Resources.constants.OrderDetail_receive(), true); // 收货确认
			                }
			                operableMap.put(Resources.constants.OrderDetail_unship(), true); // 设为未发货
			                operableMap.put(Resources.constants.OrderDetail_return(), true); // 退货（包括退款）
			            }
			        }
				}

		        /* 状态：取消 */
				else if (IOrderInfo.OS_CANCELED == orderStatus) {
					operableMap.put(Resources.constants.OrderDetail_comfirm(), true);
					operableMap.put(Resources.constants.OrderDetail_remove(), true);
			    }

		        /* 状态：无效 */
			    else if (IOrderInfo.OS_INVALID == orderStatus) {
			    	operableMap.put(Resources.constants.OrderDetail_comfirm(), true);
			    	operableMap.put(Resources.constants.OrderDetail_remove(), true);
			    }
		        /* 状态：退货 */
			    else if (IOrderInfo.OS_RETURNED == orderStatus)
			    {
			    	operableMap.put(Resources.constants.OrderDetail_comfirm(), true);
			    }
				getOperableReady = true;
			}
		});
	}

	@Override
	public void refresh() {
		getOrderInfo();
		refreshOperableAction();
		goodsToolBar.refresh();
		operationToolBar.refresh();
	}

	private void refreshOperableAction() {
		//获得可执行的操作列表]
		getOperableList();
		new WaitService(new WaitService.Job() {
			public boolean isReady() {
				if(!getOperableReady) {
					return false;
				}
				else {
					return true;
				}
			}
			public void run() {
				for(Object key : operableMap.keySet()) {
					Button button = (Button) buttonPanel.getItemByItemId((String) key);
					button.setVisible(operableMap.get(key));
				}
				getOperableReady = false;
			}
		});		
	}

	//地址
	String country = "";
	String province = "";
	String city = "";
	String district = "";
	int isReady = 0;
	long orderStatus;
	long payStatus;
	long shippingStatus;
	
	private void getOrderInfo() {
		String pkId = getCurState().getPkId();
		Criteria criteria = new Criteria();
		criteria.addCondition(new Condition(IOrderInfo.PK_ID, Condition.EQUALS, pkId));
		orderStatus = -1;
		payStatus = -1;
		shippingStatus = -1;
		new ReadService().getBean(ModelNames.ORDERINFO, pkId, new ReadService.Listener() {
			public void onSuccess(final BeanObject bean) {
				//基本信息
				snLabel.setText((String) bean.get(IOrderInfo.ORDER_SN));
				
				orderStatus = bean.get(IOrderInfo.ORDER_STATUS);
				payStatus = bean.get(IOrderInfo.PAY_STATUS);
				shippingStatus = bean.get(IOrderInfo.SHIPPING_STATUS);
				stateLabel.setText(getState(orderStatus, payStatus, shippingStatus));
				
				String userId = bean.get(IOrderInfo.USER_ID);
				new ReadService().getBean(ModelNames.USER, userId, new ReadService.Listener() {
					public void onSuccess(BeanObject bean) {
						buyerLabel.setText((String) bean.get(IUser.USER_NAME));
					}
				});
				
				long addTime = bean.get(IOrderInfo.ADD_TIME);				
				addTimeLabel.setText(formatTime(addTime));
				
				payMannerLabel.setText((String) bean.get(IOrderInfo.PAY_NAME));
				if(payStatus != 2) {
					payTimeLabel.setText(Resources.constants.OrderStatus_PS_UNPAYED());
				} else {
					long payTime = bean.get(IOrderInfo.PAY_TIME);
					payTimeLabel.setText(formatTime(payTime));
				}
				
				shippingMannerLabel.setText((String) bean.get(IOrderInfo.SHIPPING_NAME));
				if(shippingStatus != 2 || shippingStatus != 3)  {
					shippingTimeLabel.setText(Resources.constants.OrderStatus_SS_UNSHIPPED());
				} else {
					long shippingTime = bean.get(IOrderInfo.SHIPPING_TIME);
					shippingTimeLabel.setText(formatTime(shippingTime));
				}
				
				invoiceNoLabel.setText((String) bean.get(IOrderInfo.INVOICE_NO));
				//refererLabel.setText((String) bean.get(IOrderInfo.REFERER));
				refererLabel.setText(Resources.constants.OrderDetail_selfSite());
				
				//收货人信息
				consigneeLabel.setText((String) bean.get(IOrderInfo.CONSIGNEE));
				emailLabel.setText((String) bean.get(IOrderInfo.EMAIL));
				zipcodeLabel.setText((String) bean.get(IOrderInfo.ZIPCODE));
				telLabel.setText((String) bean.get(IOrderInfo.TEL));
				mobileLabel.setText((String) bean.get(IOrderInfo.MOBILE));
				buildingLabel.setText((String) bean.get(IOrderInfo.SIGN_BUILDING));
				bestTimeLabel.setText((String) bean.get(IOrderInfo.BEST_TIME));
				
				//费用信息
				double goodsAmount = bean.get(IOrderInfo.GOODS_AMOUNT);
				double shippingFee = bean.get(IOrderInfo.SHIPPING_FEE);
				double insuranceFee = bean.get(IOrderInfo.INSURE_FEE);
				double payFee = bean.get(IOrderInfo.PAY_FEE);
				double orderAmount = goodsAmount + shippingFee + insuranceFee + payFee;
				goodsAmountLabel.setText("￥" + goodsAmount);
				shippingFeeLabel.setText("￥" + shippingFee);
				insuranceFeeLabel.setText("￥" + insuranceFee);
				payFeeLabel.setText("￥" + payFee);
				orderAmountLabel.setText("￥" + orderAmount);
				moneyPaidLabel.setText("￥" + bean.get(IOrderInfo.MONEY_PAID));
				surplusLabel.setText("￥" + bean.get(IOrderInfo.SURPLUS));
				moneyShouldPayLabel.setText("￥" + bean.get(IOrderInfo.ORDER_AMOUNT));
				
				//查询地址
				String countryId = bean.get(IOrderInfo.COUNTRY).toString();
				String provinceId = bean.get(IOrderInfo.PROVINCE).toString();
				String cityId = bean.get(IOrderInfo.CITY).toString();
				String districtId = bean.get(IOrderInfo.DISTRICT).toString();
				criteria = new Criteria();
				condition = new Condition();
				condition.setField(IRegion.PK_ID);
				condition.setOperator(Condition.EQUALS);
				criteria.addCondition(condition);
				getRegion(countryId);
				getRegion(provinceId);
				getRegion(cityId);
				getRegion(districtId);
				
				new WaitService(new WaitService.Job() {
					public boolean isReady() {
						if(isReady < 4) {
							return false;
						}
						else {
							isReady = 0;
							return true;
						}
					}
		
					public void run() {
						addressLabel.setText("[" + country + " " + province + " "  + city + " " + district + "]" + bean.get(IOrderInfo.ADDRESS));
					}
				});
			}

			Criteria criteria;
			Condition condition;
			private void getRegion(String id) {
				if(id != null) {
					condition.setValue(id);
					new ListService().listBeans(ModelNames.REGION, criteria, new ListService.Listener() {
						public void onSuccess(List<BeanObject> beans) {
							if(beans.size() != 0) {
								BeanObject bo = beans.get(0);
								country = bo.get(IRegion.REGION_NAME);
							}
							isReady++;
						}
					});
				} else {
					isReady++;
				}
			}
		});
	}

	protected String formatTime(long time) {
		Date dateTime = new Date();
		dateTime.setTime(time);
		DateTimeFormat formatter = DateTimeFormat.getFormat("yy-MM-dd HH:mm:ss");
		String timeStr = formatter.format(dateTime);
		return timeStr;
	}

	protected String getState(Long orderStatus, Long payStatus, Long shippingStatus) {
		String orderStatusStr = null;
		switch(orderStatus.intValue()) { 
		case IOrderInfo.OS_CANCELED: 
			orderStatusStr = Resources.constants.OrderStatus_OS_CANCELED();
			break;
		case IOrderInfo.OS_CONFIRMED: 
			orderStatusStr = Resources.constants.OrderStatus_OS_CONFIRMED();
			break;
		case IOrderInfo.OS_INVALID: 
			orderStatusStr = Resources.constants.OrderStatus_OS_INVALID();
			break;
		case IOrderInfo.OS_RETURNED: 
			orderStatusStr = Resources.constants.OrderStatus_OS_RETURNED();
			break;
		case IOrderInfo.OS_UNCONFIRMED: 
			orderStatusStr = Resources.constants.OrderStatus_OS_UNCONFIRMED();
			break;
	}
	
	String payStatusStr = null;
	switch(payStatus.intValue()) {
		case IOrderInfo.PS_PAYED: 
			payStatusStr = Resources.constants.OrderStatus_PS_PAYED();
			break;
		case IOrderInfo.PS_PAYING: 
			payStatusStr = Resources.constants.OrderStatus_PS_PAYING();
			break;
		case IOrderInfo.PS_UNPAYED:
			payStatusStr = Resources.constants.OrderStatus_PS_UNPAYED();
			break;
	}
	
	String shippingStatusStr = null;
	switch(shippingStatus.intValue()) {
		case IOrderInfo.SS_PREPARING: 
			shippingStatusStr = Resources.constants.OrderStatus_SS_PREPARING(); 
			break;
		case IOrderInfo.SS_RECEIVED: 
			shippingStatusStr = Resources.constants.OrderStatus_SS_RECEIVED(); 
			break;
		case IOrderInfo.SS_SHIPPED: 
			shippingStatusStr = Resources.constants.OrderStatus_SS_SHIPPED(); 
			break;
		case IOrderInfo.SS_UNSHIPPED: 
			shippingStatusStr = Resources.constants.OrderStatus_SS_UNSHIPPED(); 
			break;
	}
	
	return orderStatusStr + "," + payStatusStr + "," + shippingStatusStr;
	}
}
