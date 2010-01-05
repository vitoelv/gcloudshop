package com.jcommerce.gwt.client.panels.order;

import java.util.ArrayList;
import java.util.List;

import javax.jdo.annotations.Persistent;

import com.extjs.gxt.ui.client.Style.HorizontalAlignment;
import com.extjs.gxt.ui.client.data.BasePagingLoader;
import com.extjs.gxt.ui.client.store.ListStore;
import com.extjs.gxt.ui.client.store.StoreEvent;
import com.extjs.gxt.ui.client.store.StoreListener;
import com.extjs.gxt.ui.client.widget.ContentPanel;
import com.extjs.gxt.ui.client.widget.grid.CheckBoxSelectionModel;
import com.extjs.gxt.ui.client.widget.grid.ColumnConfig;
import com.extjs.gxt.ui.client.widget.grid.ColumnModel;
import com.extjs.gxt.ui.client.widget.grid.Grid;
import com.extjs.gxt.ui.client.widget.layout.FitLayout;
import com.extjs.gxt.ui.client.widget.toolbar.PagingToolBar;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.Element;
import com.jcommerce.gwt.client.ContentWidget;
import com.jcommerce.gwt.client.ModelNames;
import com.jcommerce.gwt.client.PageState;
import com.jcommerce.gwt.client.form.BeanObject;
import com.jcommerce.gwt.client.model.IOrderInfo;
import com.jcommerce.gwt.client.model.IUserAddress;
import com.jcommerce.gwt.client.resources.Resources;
import com.jcommerce.gwt.client.service.Condition;
import com.jcommerce.gwt.client.service.Criteria;
import com.jcommerce.gwt.client.service.ListService;
import com.jcommerce.gwt.client.service.PagingListService;
import com.jcommerce.gwt.client.service.ReadService;
import com.jcommerce.gwt.client.util.MyRpcProxy;
import com.jcommerce.gwt.client.widgets.ActionCellRenderer;
import com.jcommerce.gwt.client.widgets.ConsigneeCellRenderer;
import com.jcommerce.gwt.client.widgets.MoneyFormatCellRenderer;
import com.jcommerce.gwt.client.widgets.OrderStateCellRenderer;
import com.jcommerce.gwt.client.widgets.OrderTimeCellRenderer;
import com.jcommerce.gwt.client.widgets.UserAddressCellRenderer;

public class OrderListPanel  extends ContentWidget{
	
	public static interface Constants {
		String OrderStatus_OS_CANCELED();
		String OrderStatus_OS_CONFIRMED();
		String OrderStatus_OS_INVALID();
		String OrderStatus_OS_RETURNED();
		String OrderStatus_OS_UNCONFIRMED();
		String OrderStatus_SS_RECEIVED();
		String OrderStatus_SS_PREPARING();
		String OrderStatus_SS_SHIPPED();
		String OrderStatus_SS_UNSHIPPED();
		String OrderStatus_PS_PAYED();
		String OrderStatus_PS_PAYING();
		String OrderStatus_PS_UNPAYED();
    }

	private static OrderListPanel instance = null;
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
		return Resources.constants.OrderList_title();
	}
	
	public static OrderListPanel getInstance(){
		if (instance == null) {
			instance = new OrderListPanel();
		}
		return instance;
	}
	
	private OrderListPanel(){
		initJS(this);
	}
	
	public static class State extends PageState {
		public static final String PK_ID = "pkId";
		@Override
		public String getPageClassName() {
			return OrderListPanel.class.getName();
		}
		@Override
		public String getMenuDisplayName() {
			return Resources.constants.OrderList_title();
		}
		public void setPkId(String gtid) {
			setValue(PK_ID, gtid);
		}
		public String getPkId() {
			return (String)getValue(PK_ID);
		}
	}

	Criteria criteria = new Criteria();
	PagingToolBar toolBar;
	BasePagingLoader loader;
	Grid<BeanObject> grid;
	ColumnModel cm;
	
	ColumnConfig addTimeCol; 
	ColumnConfig consigneeCol;
	ColumnConfig totalAmountCol;	
	ColumnConfig shouldPayAmountCol;	
	ColumnConfig orderStateCol;
	
	OrderTimeCellRenderer timeRender;
	ConsigneeCellRenderer consigneeRender;
	MoneyFormatCellRenderer moneyRender;
	OrderStateCellRenderer orderStateRender;
	
	@Override
	protected void onRender(Element parent, int index) {
		super.onRender(parent, index);
		
		String userId = getCurState().getPkId();
		if(userId != null) {
			criteria.addCondition(new Condition(IOrderInfo.USER_ID, Condition.EQUALS, userId));
		}
		
		loader = new PagingListService().getLoader(ModelNames.ORDERINFO, criteria);
		loader.load(0, 10);
		final ListStore<BeanObject> store = new ListStore<BeanObject>(loader);
		store.addStoreListener(new StoreListener<BeanObject>() {
			public void storeDataChanged(StoreEvent<BeanObject> se) {
				// TODO Auto-generated method stub
				super.storeDataChanged(se);
				List storeData = se.getStore().getModels();
				for (Object object : storeData) {
					BeanObject bean = (BeanObject) object;
					
					//下单时间
					String userId = bean.getString("userId");
					final long addTime = bean.get("addTime");					
					timeRender = new OrderTimeCellRenderer(grid);
					new ReadService().getBean(ModelNames.USER, userId, new ReadService.Listener(){
						@Override
						public void onSuccess(BeanObject bean) {
							timeRender.setAddTime(addTime);
							timeRender.setUser(bean.getString("userName"));
							addTimeCol.setRenderer(timeRender);
							grid.reconfigure(store, cm);
						}
			    	});
					
					//收货人
					String consignee = bean.getString("consignee");
					String tel = bean.getString("tel");
					String address = bean.getString("address");
					
					consigneeRender = new ConsigneeCellRenderer(grid);
					consigneeRender.setConsignee(consignee);
					consigneeRender.setTel(tel);
					consigneeRender.setAddress(address);
					consigneeCol.setRenderer(consigneeRender);
					
					//金额
					double goodsAmount = bean.get("goodsAmount");
					double shippingFee = bean.get("shippingFee");
					double payFee = bean.get("payFee");
					double totalAmount = goodsAmount + shippingFee + payFee;
					double orderAmount = bean.get("orderAmount");
					
					moneyRender = new MoneyFormatCellRenderer(grid);
					moneyRender.setMoney(totalAmount);
					totalAmountCol.setRenderer(moneyRender);
					moneyRender.setMoney(orderAmount);
					shouldPayAmountCol.setRenderer(moneyRender);
					
					
					//订单状态
					long orderStatus = bean.get("orderStatus"); 
					long shippingStatus = bean.get("shippingStatus");
					long payStatus = bean.get("payStatus"); 
					
					orderStateRender = new OrderStateCellRenderer(grid);
					orderStateRender.setOrderStatus(orderStatus);
					orderStateRender.setPayStatus(payStatus);
					orderStateRender.setShippingStatus(shippingStatus);
					orderStateCol.setRenderer(orderStateRender);					
				}				
			}    		
    	});
		
		toolBar = new PagingToolBar(10);
		toolBar.bind(loader);

		List<ColumnConfig> columns = new ArrayList<ColumnConfig>();
		final CheckBoxSelectionModel<BeanObject> smRowSelection = new CheckBoxSelectionModel<BeanObject>();
		columns.add(smRowSelection.getColumn());

		ColumnConfig col = new ColumnConfig(IOrderInfo.ORDER_SN, Resources.constants.OrderList_orderSN(), 100);
		columns.add(col);
		
		addTimeCol = new ColumnConfig(Resources.constants.OrderList_addTime(), Resources.constants.OrderList_addTime(), 100);
		columns.add(addTimeCol);
		
		consigneeCol = new ColumnConfig(Resources.constants.OrderList_consignee(), Resources.constants.OrderList_consignee(), 100);
		columns.add(consigneeCol);
		
		totalAmountCol = new ColumnConfig(Resources.constants.OrderList_totalAmount(), Resources.constants.OrderList_totalAmount(), 100);
		columns.add(totalAmountCol);
		
		shouldPayAmountCol = new ColumnConfig(Resources.constants.OrderList_shouldPay(), Resources.constants.OrderList_shouldPay(), 100);
		columns.add(shouldPayAmountCol);
		
		orderStateCol = new ColumnConfig(Resources.constants.OrderList_state(), Resources.constants.OrderList_state(), 100);
		columns.add(orderStateCol);
		
//		col = new ColumnConfig(IOrderInfo.ADD_TIME, Resources.constants.OrderList_addTime(), 100);
//		columns.add(col);
//		col = new ColumnConfig(IOrderInfo.CONSIGNEE, Resources.constants.OrderList_consignee(), 100);
//		columns.add(col);
//		col = new ColumnConfig(IOrderInfo.GOODS_AMOUNT, Resources.constants.OrderList_totalAmount(), 100);
//		columns.add(col);
//		col = new ColumnConfig(IOrderInfo.ORDER_AMOUNT, Resources.constants.OrderList_shouldPay(), 100);
//		columns.add(col);
//		col = new ColumnConfig(IOrderInfo.ORDER_STATUS, Resources.constants.OrderList_state(), 100);
//		columns.add(col);
		ColumnConfig actcol = new ColumnConfig("Action", Resources.constants.OrderList_action(), 100);
		columns.add(actcol);

		cm = new ColumnModel(columns);
		
		grid = new Grid<BeanObject>(store, cm);
		grid.setLoadMask(true);
		grid.setBorders(true);
		grid.setSelectionModel(smRowSelection);

	
		ActionCellRenderer render = new ActionCellRenderer(grid);
		ActionCellRenderer.ActionInfo act = new ActionCellRenderer.ActionInfo();
		act.setImage(GWT.getModuleBaseURL()+"icon_edit.gif");
		act.setAction("viewOrder($pkId)");
		act.setTooltip(Resources.constants.edit());
		render.addAction(act);
		act = new ActionCellRenderer.ActionInfo();		
		act.setImage(GWT.getModuleBaseURL()+"icon_trash.gif");
		act.setAction("deleteOrder($pkId)");
		act.setTooltip(Resources.constants.delete());
		render.addAction(act);

		actcol.setRenderer(render);
		
		
		ContentPanel panel = new ContentPanel();
		panel.setFrame(true);
		panel.setCollapsible(true);
		panel.setAnimCollapse(false);
		panel.setButtonAlign(HorizontalAlignment.CENTER);
		panel.setIconStyle("icon-table");
		//        panel.setHeading("Paging Grid");
		panel.setLayout(new FitLayout());
		panel.add(grid);
		panel.setSize(880, 350);
		panel.setBottomComponent(toolBar);

//		panel.setButtonAlign(HorizontalAlignment.CENTER);
//		panel.addButton(new Button(Resources.constants.GoodsList_reset(),
//				new SelectionListener<ButtonEvent>() {
//					public void componentSelected(ButtonEvent ce) {
//						store.rejectChanges();
//					}
//				}));
//
//		panel.addButton(new Button(Resources.constants.GoodsList_save(),
//				new SelectionListener<ButtonEvent>() {
//					public void componentSelected(ButtonEvent ce) {
//						store.commitChanges();
//					}
//				}));
//		
//		panel.addButton(new Button(Resources.constants.GoodsList_add_new(),
//				new SelectionListener<ButtonEvent>() {
//					public void componentSelected(ButtonEvent ce) {
//						
//						GoodsPanel.State newState = new GoodsPanel.State();
//						newState.setIsEdit(false);
//						newState.execute();
////						JCommerceGae.getInstance().displayGoodsPanel(null);
//					}
//				}));
		
		add(panel);
	}
	
	@Override
	public void refresh() {
		refreshOrderList();
		toolBar.refresh();
	}
	
	private void refreshOrderList() {
		String userId = getCurState().getPkId();
		Criteria criteria = new Criteria(); 
		if(userId != null) {
			criteria.addCondition(new Condition(IUserAddress.USER_ID, Condition.EQUALS, userId));
		}
		MyRpcProxy proxy = (MyRpcProxy)loader.getProxy();
		proxy.setCriteria(criteria);			
	}

//	private void updateOrder(BeanObject order, UpdateService.Listener listener){
//		new UpdateService().updateBean(order.getString(IOrderInfo.ID), order,
//				listener);
//	}
	
	private native void initJS(OrderListPanel me) /*-{
	   $wnd.deleteOrder = function (id) {
	       me.@com.jcommerce.gwt.client.panels.order.OrderListPanel::deleteOrder(Ljava/lang/String;)(id);
	   };
	   $wnd.viewOrder = function (id) {
	       me.@com.jcommerce.gwt.client.panels.order.OrderListPanel::viewOrder(Ljava/lang/String;)(id);
	   };
	   }-*/;

	private void deleteOrder(String id){
		// TODO
	}
	
	private void viewOrder(String id){
		// TODO
	}
}
