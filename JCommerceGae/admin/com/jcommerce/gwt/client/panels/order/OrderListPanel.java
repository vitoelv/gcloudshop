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
import com.jcommerce.gwt.client.resources.Resources;
import com.jcommerce.gwt.client.service.Criteria;
import com.jcommerce.gwt.client.service.PagingListService;
import com.jcommerce.gwt.client.widgets.ActionCellRenderer;

public class OrderListPanel  extends ContentWidget{

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

		@Override
		public String getPageClassName() {
			return OrderListPanel.class.getName();
		}
		@Override
		public String getMenuDisplayName() {
			return Resources.constants.OrderList_title();
		}
	}

	Criteria criteria = new Criteria();
	PagingToolBar toolBar;
	
	@Override
	protected void onRender(Element parent, int index) {
		super.onRender(parent, index);
		
		BasePagingLoader loader = new PagingListService().getLoader(ModelNames.ORDERINFO, criteria);
		loader.load(0, 10);
		final ListStore<BeanObject> store = new ListStore<BeanObject>(loader);
//		store.addStoreListener(new StoreListener<BeanObject>() {
//			public void storeUpdate(StoreEvent<BeanObject> se) {
//				List<Record> changed = store.getModifiedRecords();
//				for (Record rec : changed) {
//					BeanObject bean = (BeanObject) rec.getModel();
//					updateOrder(bean, null);
//				}
//			}
//		});
		
		toolBar = new PagingToolBar(10);
		toolBar.bind(loader);

		List<ColumnConfig> columns = new ArrayList<ColumnConfig>();
		final CheckBoxSelectionModel<BeanObject> smRowSelection = new CheckBoxSelectionModel<BeanObject>();
		columns.add(smRowSelection.getColumn());

		ColumnConfig col = new ColumnConfig(IOrderInfo.ORDER_SN, Resources.constants.OrderList_orderSN(), 100);
		columns.add(col);
		col = new ColumnConfig(IOrderInfo.ADD_TIME, Resources.constants.OrderList_addTime(), 100);
		columns.add(col);
		col = new ColumnConfig(IOrderInfo.CONSIGNEE, Resources.constants.OrderList_consignee(), 100);
		columns.add(col);
		col = new ColumnConfig(IOrderInfo.GOODS_AMOUNT, Resources.constants.OrderList_totalAmount(), 100);
		columns.add(col);
		col = new ColumnConfig(IOrderInfo.ORDER_AMOUNT, Resources.constants.OrderList_shouldPay(), 100);
		columns.add(col);
		col = new ColumnConfig(IOrderInfo.ORDER_STATUS, Resources.constants.OrderList_state(), 100);
		columns.add(col);
		ColumnConfig actcol = new ColumnConfig("Action", Resources.constants.OrderList_action(), 100);
		columns.add(actcol);

		ColumnModel cm = new ColumnModel(columns);
		
		Grid<BeanObject> grid = new Grid<BeanObject>(store, cm);
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
		toolBar.refresh();
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
