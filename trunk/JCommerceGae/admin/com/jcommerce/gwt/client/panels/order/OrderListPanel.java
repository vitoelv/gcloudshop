package com.jcommerce.gwt.client.panels.order;

import java.util.ArrayList;
import java.util.List;

import com.extjs.gxt.ui.client.Style.HorizontalAlignment;
import com.extjs.gxt.ui.client.data.BasePagingLoader;
import com.extjs.gxt.ui.client.store.ListStore;
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
import com.jcommerce.gwt.client.service.PagingListService;
import com.jcommerce.gwt.client.service.UpdateService;
import com.jcommerce.gwt.client.util.MyRpcProxy;
import com.jcommerce.gwt.client.widgets.ActionCellRenderer;
import com.jcommerce.gwt.client.widgets.ConsigneeCellRenderer;
import com.jcommerce.gwt.client.widgets.MoneyFormatCellRenderer;
import com.jcommerce.gwt.client.widgets.OrderStateCellRenderer;
import com.jcommerce.gwt.client.widgets.TimeCellRenderer;

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
		
		toolBar = new PagingToolBar(10);
		toolBar.bind(loader);

		List<ColumnConfig> columns = new ArrayList<ColumnConfig>();
		final CheckBoxSelectionModel<BeanObject> smRowSelection = new CheckBoxSelectionModel<BeanObject>();
		columns.add(smRowSelection.getColumn());

		ColumnConfig col = new ColumnConfig(IOrderInfo.ORDER_SN, Resources.constants.OrderList_orderSN(), 150);
		columns.add(col);
		
		addTimeCol = new ColumnConfig(IOrderInfo.ADD_TIME, Resources.constants.OrderList_addTime(), 200);
		TimeCellRenderer tcr =new TimeCellRenderer();
		tcr.setFormat("MM-dd HH:mm");
		addTimeCol.setRenderer(tcr);
		columns.add(addTimeCol);
		
		consigneeCol = new ColumnConfig(IOrderInfo.CONSIGNEE, Resources.constants.OrderList_consignee(), 100);
		consigneeCol.setRenderer(new ConsigneeCellRenderer());
		columns.add(consigneeCol);
		
		totalAmountCol = new ColumnConfig("totalAmount", Resources.constants.OrderList_totalAmount(), 100);
		totalAmountCol.setRenderer(new MoneyFormatCellRenderer());
		columns.add(totalAmountCol);
		
		shouldPayAmountCol = new ColumnConfig(IOrderInfo.ORDER_AMOUNT, Resources.constants.OrderList_shouldPay(), 100);
		shouldPayAmountCol.setRenderer(new MoneyFormatCellRenderer());
		columns.add(shouldPayAmountCol);
		
		orderStateCol = new ColumnConfig(IOrderInfo.ORDER_STATUS, Resources.constants.OrderList_state(), 100);
		orderStateCol.setRenderer(new OrderStateCellRenderer());
		columns.add(orderStateCol);
		
		ColumnConfig actcol = new ColumnConfig("Action", Resources.constants.OrderList_action(), 100);
		columns.add(actcol);

		cm = new ColumnModel(columns);
		
		grid = new Grid<BeanObject>(store, cm);
		grid.setLoadMask(true);
		grid.setBorders(true);
		grid.setSelectionModel(smRowSelection);
		grid.setAutoExpandColumn("Action");
	
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

	private void updateOrder(BeanObject order, UpdateService.Listener listener) {
		new UpdateService().updateBean(order.getString(IOrderInfo.PK_ID), order,
				listener);
	}
	
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
