package com.jcommerce.gwt.client.panels.order;

import java.util.ArrayList;
import java.util.List;
import com.extjs.gxt.ui.client.Style.HorizontalAlignment;
import com.extjs.gxt.ui.client.data.BasePagingLoader;
import com.extjs.gxt.ui.client.event.ButtonEvent;
import com.extjs.gxt.ui.client.event.MessageBoxEvent;
import com.extjs.gxt.ui.client.event.SelectionChangedEvent;
import com.extjs.gxt.ui.client.event.SelectionChangedListener;
import com.extjs.gxt.ui.client.event.SelectionListener;
import com.extjs.gxt.ui.client.store.ListStore;
import com.extjs.gxt.ui.client.widget.ContentPanel;
import com.extjs.gxt.ui.client.widget.HorizontalPanel;
import com.extjs.gxt.ui.client.widget.Info;
import com.extjs.gxt.ui.client.widget.MessageBox;
import com.extjs.gxt.ui.client.widget.button.Button;
import com.extjs.gxt.ui.client.widget.grid.CheckBoxSelectionModel;
import com.extjs.gxt.ui.client.widget.grid.ColumnConfig;
import com.extjs.gxt.ui.client.widget.grid.ColumnModel;
import com.extjs.gxt.ui.client.widget.grid.Grid;
import com.extjs.gxt.ui.client.widget.layout.FitLayout;
import com.extjs.gxt.ui.client.widget.toolbar.PagingToolBar;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Element;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.TextBox;
import com.jcommerce.gwt.client.ContentWidget;
import com.jcommerce.gwt.client.ModelNames;
import com.jcommerce.gwt.client.PageState;
import com.jcommerce.gwt.client.form.BeanObject;
import com.jcommerce.gwt.client.model.IOrderInfo;
import com.jcommerce.gwt.client.resources.Resources;
import com.jcommerce.gwt.client.service.Condition;
import com.jcommerce.gwt.client.service.Criteria;
import com.jcommerce.gwt.client.service.DeleteService;
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
		String OrderStatus_select();
		String OrderStatus_complete();
		String OrderList_deleteSuccessfully();
		String OrderList_deleteFailure();
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
	
	public Button getShortCutButton() {
      Button buttonSearchOrder = new Button(Resources.constants.SearchOrder_title());
      buttonSearchOrder.addSelectionListener(new SelectionListener<ButtonEvent>() {
          public void componentSelected(ButtonEvent ce) {
          	onButtonListClicked();
          }
      });
      return buttonSearchOrder;
    }
    public void onButtonListClicked() {
		SearchOrderPanel.State newState = new SearchOrderPanel.State();
		newState.execute();
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
		public static final String CONDITION = "condition";
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
		public void setCondition(List<String> con) {
			setValue(CONDITION, con);
		}
		public List<String> getCondition() {
			return (List<String>)getValue(CONDITION);
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
	
	Button btnFind = new Button(Resources.constants.GoodsList_search());
	TextBox orderSn = new TextBox();
	TextBox consignee = new TextBox();
	ListBox state_list = new ListBox();
	
	Button btnConfirm = new Button("确认");
	Button btnInvalid = new Button("无效");
	Button btnCancel = new Button("取消");
	Button btnRemove = new Button("移除");
	
	@Override
	protected void onRender(Element parent, int index) {
		super.onRender(parent, index);		
		
		loader = new PagingListService().getLoader(ModelNames.ORDERINFO, criteria);
		//loader.load(0, 10);
		final ListStore<BeanObject> store = new ListStore<BeanObject>(loader);
		
		toolBar = new PagingToolBar(10);
		toolBar.bind(loader);

		List<ColumnConfig> columns = new ArrayList<ColumnConfig>();
		final CheckBoxSelectionModel<BeanObject> smRowSelection = new CheckBoxSelectionModel<BeanObject>();
		smRowSelection.addSelectionChangedListener(new SelectionChangedListener<BeanObject>() {
			@Override
			public void selectionChanged(SelectionChangedEvent<BeanObject> se) {
				List selection = se.getSelection();
				if(selection.size() > 0) {
					btnConfirm.setEnabled(true);
					btnInvalid.setEnabled(true);
					btnCancel.setEnabled(true);
					btnRemove.setEnabled(true);
				}
				else {
					btnConfirm.setEnabled(false);
					btnInvalid.setEnabled(false);
					btnCancel.setEnabled(false);
					btnRemove.setEnabled(false);
				}
			}
		});
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
		
		HorizontalPanel header = new HorizontalPanel();
		header.add(Resources.images.icon_search().createImage());
		header.add(new Label(Resources.constants.OrderList_orderSN()));
		header.add(orderSn);
		header.add(new Label(Resources.constants.OrderList_consignee()));
		header.add(consignee);
		header.add(new Label(Resources.constants.OrderList_state()));
		header.add(state_list);
		state_list.addItem(Resources.constants.OrderStatus_select(),"0");
		state_list.addItem(Resources.constants.OrderStatus_OS_UNCONFIRMED(),"1");
		state_list.addItem(Resources.constants.OrderStatus_PS_UNPAYED(),"2");
		state_list.addItem(Resources.constants.OrderStatus_SS_PREPARING(),"3");
		state_list.addItem(Resources.constants.OrderStatus_complete(),"4");
		state_list.addItem(Resources.constants.OrderStatus_PS_PAYING(),"5");
		state_list.addItem(Resources.constants.OrderStatus_OS_CANCELED(),"6");
		state_list.addItem(Resources.constants.OrderStatus_OS_INVALID(),"7");
		state_list.addItem(Resources.constants.OrderStatus_OS_RETURNED(),"8");
		
		header.add(btnFind);
		btnFind.addSelectionListener(new SelectionListener<ButtonEvent>() {
			public void componentSelected(ButtonEvent ce) {
				search(state_list.getSelectedIndex());
			}
		});
		
		Label unConfirmedLabel = new Label(Resources.constants.OrderStatus_OS_UNCONFIRMED());
		unConfirmedLabel.setWidth("50");
		unConfirmedLabel.setStyleName("SearchOrderLabel");
		unConfirmedLabel.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				search(1);
			}
		});
		header.add(unConfirmedLabel);
		
		Label unPayedLabel = new Label(Resources.constants.OrderStatus_PS_UNPAYED());
		unPayedLabel.setWidth("50");
		unPayedLabel.setStyleName("SearchOrderLabel");
		unPayedLabel.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				search(2);
			}
		});
		header.add(unPayedLabel);
		
		Label preparingLabel = new Label(Resources.constants.OrderStatus_SS_PREPARING());
		preparingLabel.setWidth("50");
		preparingLabel.setStyleName("SearchOrderLabel");
		preparingLabel.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				search(3);
			}
		});
		header.add(preparingLabel);
		
		add(header);
		
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

		panel.setButtonAlign(HorizontalAlignment.LEFT);
		panel.addButton(btnConfirm);
		panel.addButton(btnInvalid);
		panel.addButton(btnCancel);
		panel.addButton(btnRemove);
		
		btnConfirm.setEnabled(false);
		btnInvalid.setEnabled(false);
		btnCancel.setEnabled(false);
		btnRemove.setEnabled(false);
		
		add(panel);
	}
	
	protected void search(int selected) {
		criteria.removeAllConditions();
		
		//搜索已完成的订单
		if(selected == 4) {
			criteria.addCondition(new Condition(IOrderInfo.ORDER_STATUS, Condition.EQUALS, ((Integer)IOrderInfo.OS_CONFIRMED).toString()));
			criteria.addCondition(new Condition(IOrderInfo.PAY_STATUS, Condition.EQUALS, ((Integer)IOrderInfo.PS_PAYED).toString()));
			criteria.addCondition(new Condition(IOrderInfo.SHIPPING_STATUS, Condition.GREATERTHAN, ((Integer)IOrderInfo.SS_SHIPPED).toString()));
			criteria.addCondition(new Condition(IOrderInfo.SHIPPING_STATUS, Condition.LESSTHAN, ((Integer)IOrderInfo.SS_RECEIVED).toString()));
		}
		
		else {
			Condition condition = new Condition();
			condition.setOperator(Condition.EQUALS);		
			switch(selected) {
				case 1:
					condition.setField(IOrderInfo.ORDER_STATUS);
					condition.setValue(((Integer)IOrderInfo.OS_UNCONFIRMED).toString());
					break;
				case 2:
					condition.setField(IOrderInfo.PAY_STATUS);
					condition.setValue(((Integer)IOrderInfo.PS_UNPAYED).toString());
					break;
				case 3:
					condition.setField(IOrderInfo.SHIPPING_STATUS);
					condition.setValue(((Integer)IOrderInfo.SS_PREPARING).toString());
					break;
				case 5:
					condition.setField(IOrderInfo.PAY_STATUS);
					condition.setValue(((Integer)IOrderInfo.PS_PAYING).toString());
					break;
				case 6:
					condition.setField(IOrderInfo.ORDER_STATUS);
					condition.setValue(((Integer)IOrderInfo.OS_CANCELED).toString());
					break;
				case 7:
					condition.setField(IOrderInfo.ORDER_STATUS);
					condition.setValue(((Integer)IOrderInfo.OS_INVALID).toString());
					break;
				case 8:
					condition.setField(IOrderInfo.ORDER_STATUS);
					condition.setValue(((Integer)IOrderInfo.OS_RETURNED).toString());
					break;
			}
			if(selected != 0)
				criteria.addCondition(condition);
		}
		MyRpcProxy proxy = (MyRpcProxy)loader.getProxy();
		proxy.setCriteria(criteria);
		toolBar.refresh();
	}

	@Override
	public void refresh() {
		refreshOrderList();
		toolBar.refresh();
	}
	
	private void refreshOrderList() {
		
		Criteria criteria = new Criteria(); 
		String userId = getCurState().getPkId();
		if(userId != null) {
			criteria.addCondition(new Condition(IOrderInfo.USER_ID, Condition.EQUALS, userId));
		}
		
		List<String> con = getCurState().getCondition();
		if(con != null) {
			for(String keyValue : con) {
				int position = keyValue.indexOf(":");
				if(position != -1) {
					String key = keyValue.substring(0,position);
					String value = keyValue.substring(position + 1, keyValue.length());
					if(!key.equals("fromDate") && !key.equals("toDate")) {
						criteria.addCondition(new Condition(key, Condition.EQUALS, value));
					}
					else if(key.equals("fromDate")){
						criteria.addCondition(new Condition(IOrderInfo.ADD_TIME, Condition.GREATERTHAN, value));
					}
					else {
						criteria.addCondition(new Condition(IOrderInfo.ADD_TIME, Condition.LESSTHAN, value));
					}
				}
			}
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

	private void deleteOrder(final String id){
		MessageBox.confirm(Resources.constants.deleteConfirmTitle(), Resources.constants.deleteConfirmContent(), new com.extjs.gxt.ui.client.event.Listener<MessageBoxEvent>() {  
        	public void handleEvent(MessageBoxEvent ce) {  
                Button btn = ce.getButtonClicked(); 
                if ( btn.getItemId().equals("yes")){
                	new DeleteService().deleteBean(ModelNames.ORDERINFO, id, new DeleteService.Listener() {
            			public void onSuccess(Boolean success) {
            	        	if(success) {
            	        		Info.display(Resources.constants.OperationSuccessful(), Resources.constants.CommentList_deleteSuccessfully());
            	        	} else {
            	        		Info.display(Resources.constants.OperationFailure(), Resources.constants.CommentList_deleteFailure());
            	        	}
            	    		refresh();
            	        }
            	        public void onFailure(Throwable caught) {
            	        	Info.display(Resources.constants.OperationFailure(), Resources.constants.CommentList_deleteFailure());
            	        };
            		});
                }
              }  
            });  
	}
	
	private void viewOrder(String id){
		// TODO
	}
	
}
