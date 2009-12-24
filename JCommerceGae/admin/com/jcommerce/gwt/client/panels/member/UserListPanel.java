package com.jcommerce.gwt.client.panels.member;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.extjs.gxt.ui.client.Style.HorizontalAlignment;
import com.extjs.gxt.ui.client.data.BasePagingLoader;
import com.extjs.gxt.ui.client.event.ButtonEvent;
import com.extjs.gxt.ui.client.event.SelectionListener;
import com.extjs.gxt.ui.client.store.ListStore;
import com.extjs.gxt.ui.client.store.Record;
import com.extjs.gxt.ui.client.store.StoreEvent;
import com.extjs.gxt.ui.client.store.StoreListener;
import com.extjs.gxt.ui.client.widget.ContentPanel;
import com.extjs.gxt.ui.client.widget.HorizontalPanel;
import com.extjs.gxt.ui.client.widget.Info;
import com.extjs.gxt.ui.client.widget.button.Button;
import com.extjs.gxt.ui.client.widget.form.TextField;
import com.extjs.gxt.ui.client.widget.grid.CellEditor;
import com.extjs.gxt.ui.client.widget.grid.CheckBoxSelectionModel;
import com.extjs.gxt.ui.client.widget.grid.ColumnConfig;
import com.extjs.gxt.ui.client.widget.grid.ColumnModel;
import com.extjs.gxt.ui.client.widget.grid.EditorGrid;
import com.extjs.gxt.ui.client.widget.grid.Grid;
import com.extjs.gxt.ui.client.widget.layout.FitLayout;
import com.extjs.gxt.ui.client.widget.toolbar.PagingToolBar;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.Element;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.TextBox;
import com.jcommerce.gwt.client.ContentWidget;
import com.jcommerce.gwt.client.ModelNames;
import com.jcommerce.gwt.client.PageState;
import com.jcommerce.gwt.client.form.AttributeForm;
import com.jcommerce.gwt.client.form.BeanObject;
import com.jcommerce.gwt.client.form.UserForm;
import com.jcommerce.gwt.client.model.IUser;
import com.jcommerce.gwt.client.model.IUserRank;
import com.jcommerce.gwt.client.panels.goods.GoodsListPanel;
import com.jcommerce.gwt.client.panels.goods.AttributeListPanel.State;
import com.jcommerce.gwt.client.panels.order.OrderListPanel;
import com.jcommerce.gwt.client.resources.Resources;
import com.jcommerce.gwt.client.service.DeleteService;
import com.jcommerce.gwt.client.service.ListService;
import com.jcommerce.gwt.client.service.PagingListService;
import com.jcommerce.gwt.client.service.UpdateService;
import com.jcommerce.gwt.client.widgets.ActionCellRenderer;

public class UserListPanel extends ContentWidget {
	public static interface Constants {
		String UserList_title();
		String UserList_userRank();
		String UserList_minPoint();
		String UserList_maxPoint();
		String UserList_userName();
		String UserList_colUserName();
		String UserList_colUserEmail();
		String UserList_colUserMoney();
		String UserList_colFrozenMoney();
		String UserList_colRankPoint();
		String UserList_colPayPoint();
		String UserList_colRegTime();
		String UserList_btnSearchUser();
		String UserList_btnAddUser();
		String UserList_tipShippingAddress();
		String UserList_tipViewOrder();
		String UserList_tipViewAcount();
		String UserList_tipDropUser();
		String UserList_AllUserRank();
	}
	public static class State extends PageState{
		public static final String USER_RANK_ID = "urid";
		public static final String RANK_POINT_MIN = "urpmin";
		public static final String RANK_POINT_MAX = "urpmax";
		public static final String USER_NAME = "uname";
		@Override
		public String getPageClassName() {
			return UserListPanel.class.getName();
		}
		public void setUserRank(String urid){
			setValue(USER_RANK_ID,urid);
		}
		public String getUserRank(){
			return (String)getValue(USER_RANK_ID);
		}
		public void setRankPointMin(String min){
			setValue(RANK_POINT_MIN,min);
		}
		public String getRankPointMin(){
			return (String)getValue(RANK_POINT_MIN);
		}
		public void setRankPointMax(String max){
			setValue(RANK_POINT_MAX,max);
		}
		public String getRankPointMax(){
			return (String)getValue(RANK_POINT_MAX);
		}
		public void setUserName(String name){
			setValue(USER_NAME,name);
		}
		public String getUserName(){
			return (String)getValue(USER_NAME);
		}
		public String getMenuDisplayName() {
			return Resources.constants.UserList_title();
		}
	}
	private UserListPanel(){
		initJS(this);
	}
	
	private State curState = new State();
	private static UserListPanel instance;
	public static UserListPanel getInstance(){
		if(instance ==null){
			instance = new UserListPanel();
		}
		return instance;
	}
	PagingToolBar toolBar;
	BasePagingLoader loader = null;
	int pageSize = 5;
	TextBox txtUserName = new TextBox();
	Button btnSearch = new Button(Resources.constants.UserList_btnSearchUser());

	protected void onRender(Element parent,int index){
		super.onRender(parent,index);
		HorizontalPanel header = new HorizontalPanel();
		header.add(Resources.images.icon_search().createImage());
		header.add(new Label(Resources.constants.UserList_userName()));
		header.add(txtUserName);
		header.add(btnSearch);
		add(header);
		List<String> wantedFields = new ArrayList<String>();
		wantedFields.add(UserForm.PK_ID);
		wantedFields.add(UserForm.USER_NAME);
		wantedFields.add(UserForm.EMAIL);
		wantedFields.add(UserForm.USER_MONEY);
		wantedFields.add(UserForm.FROZEN_MONEY);
		wantedFields.add(UserForm.RANK_POINTS);
		wantedFields.add(UserForm.PAY_POINTS);
		wantedFields.add(UserForm.REG_TIME);
		loader = new PagingListService().getLoader(
		  ModelNames.USER,wantedFields);
		final ListStore<BeanObject> store= new ListStore<BeanObject>(loader);
		store.addStoreListener(new StoreListener<BeanObject>(){
			public void storeUpdate(StoreEvent<BeanObject> se){
				List<Record> changed = store.getModifiedRecords();
				for (Record rec : changed) {
					BeanObject bean = (BeanObject) rec.getModel();
					updateUser(bean);
				}
			}
		});
//		store.addStoreListener(new StoreListener<BeanObject>(){
//			public void storeRemove(StoreEvent<BeanObject> se){
//				se.getModel();
//			}
//		});
		toolBar = new PagingToolBar(pageSize);
		toolBar.bind(loader);
		
		List<ColumnConfig> columns = new ArrayList<ColumnConfig>();
		final CheckBoxSelectionModel<BeanObject> smRowSelection = new CheckBoxSelectionModel<BeanObject>();
		columns.add(smRowSelection.getColumn());

		ColumnConfig colname = new ColumnConfig(IUser.USER_NAME, Resources.constants.UserList_colUserName(), 100);
		//colname.setEditor(new CellEditor(new TextField()));
		columns.add(colname);
		ColumnConfig colemail = new ColumnConfig(IUser.EMAIL, Resources.constants.UserList_colUserEmail(),150);
		colemail. setEditor(new CellEditor(new TextField()));
		columns.add(colemail);
		ColumnConfig coluserMoney = new ColumnConfig(IUser.USER_MONEY, Resources.constants.UserList_colUserMoney(), 100);
		//coluserMoney.setEditor(new CellEditor(new TextField()));
		columns.add(coluserMoney);
		ColumnConfig colfrozenMoney = new ColumnConfig(IUser.FROZEN_MONEY, Resources.constants.UserList_colFrozenMoney(), 100);
		//colfrozenMoney.setEditor(new CellEditor(new TextField()));
		columns.add(colfrozenMoney);
		ColumnConfig colrankPoint = new ColumnConfig(IUser.RANK_POINTS, Resources.constants.UserList_colRankPoint(), 100);
		//coluserMoney.setEditor(new CellEditor(new TextField()));
		columns.add(colrankPoint);
		ColumnConfig colpayPoint = new ColumnConfig(IUser.PAY_POINTS, Resources.constants.UserList_colPayPoint(), 100);
		//colpayPoint.setEditor(new CellEditor(new TextField()));
		columns.add(colpayPoint);
		ColumnConfig colregTime = new ColumnConfig(IUser.REG_TIME, Resources.constants.UserList_colRegTime(), 100);
		//colpayPoint.setEditor(new CellEditor(new TextField()));
		columns.add(colregTime);
		ColumnConfig colaction = new ColumnConfig("Action", Resources.constants.action(), 100);
		//colpayPoint.setEditor(new CellEditor(new TextField()));
		columns.add(colaction);
		ColumnModel cm = new ColumnModel(columns);

		final Grid<BeanObject> grid = new EditorGrid<BeanObject>(store, cm);
		grid.setLoadMask(true);
		grid.setBorders(true);
		grid.setSelectionModel(smRowSelection);
		ActionCellRenderer render = new ActionCellRenderer(grid);
		ActionCellRenderer.ActionInfo act = new ActionCellRenderer.ActionInfo();
		act.setImage(GWT.getModuleBaseURL()+"icon_edit.gif");
		act.setAction("changeUser($pkId)");
		act.setTooltip(Resources.constants.edit());
		render.addAction(act);
		act = new ActionCellRenderer.ActionInfo();		
		act.setImage(GWT.getModuleBaseURL()+"book_open.gif");
		act.setAction("viewShippingAddress($pkId)");
		act.setTooltip(Resources.constants.UserList_tipShippingAddress());
		render.addAction(act);
		act = new ActionCellRenderer.ActionInfo();		
		act.setImage(GWT.getModuleBaseURL()+"icon_view.gif");
		act.setAction("viewOrder($pkId)");
		act.setTooltip(Resources.constants.UserList_tipViewOrder());
		render.addAction(act);
		act = new ActionCellRenderer.ActionInfo();		
		act.setImage(GWT.getModuleBaseURL()+"icon_account.gif");
		act.setAction("");
		act.setTooltip(Resources.constants.UserList_tipViewAcount());
		render.addAction(act);
		act = new ActionCellRenderer.ActionInfo();		
		act.setImage(GWT.getModuleBaseURL()+"icon_drop.gif");
		act.setAction("deleteUser($pkId)");
		act.setTooltip(Resources.constants.UserList_tipDropUser());
		render.addAction(act);
		colaction.setRenderer(render);
		ContentPanel panel = new ContentPanel();
		panel.setFrame(true);
		panel.setCollapsible(true);
		panel.setAnimCollapse(false);
		panel.setButtonAlign(HorizontalAlignment.CENTER);
		panel.setIconStyle("icon-table");
		//        panel.setHeading("Paging Grid");
		panel.setLayout(new FitLayout());
		panel.add(grid);
		panel.setSize(900, 350);
		panel.setBottomComponent(toolBar);
		panel.setButtonAlign(HorizontalAlignment.CENTER);
		panel.addButton(new Button("Remove User",
				new SelectionListener<ButtonEvent>() {
					public void componentSelected(ButtonEvent ce) {
						List<BeanObject> models = smRowSelection.getSelectedItems();
						removeUsers(models);
					}
				}));
		add(panel);
	}
	
	public Button getShortCutButton(){
		Button sButton = new Button(Resources.constants.UserList_btnAddUser());
	      sButton.addSelectionListener(new SelectionListener<ButtonEvent>() {
	          public void componentSelected(ButtonEvent ce) {
	          	onShortCutButtonClicked();
	          }
	      });
	     return sButton;
	}
	public void onShortCutButtonClicked() {
		UserPanel.State newState = new UserPanel.State();
		newState.setIsEdit(false);
		newState.execute();
    }
	@Override 
	public PageState getCurState() {
		// TODO Auto-generated method stub
		return curState;
	}

	@Override
	public String getDescription() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return Resources.constants.UserList_title();
	}

	private void removeUser(final BeanObject user){
		//DeleteService service = new DeleteService();
	}
	private void updateUser(final BeanObject modifiedUser){
		UpdateService service = new UpdateService();
		service.updateBean(modifiedUser.getString(UserForm.PK_ID),modifiedUser,new UpdateService.Listener(){

			@Override
			public void onSuccess(Boolean success) {
				if(success){
					Info.display("恭喜", "该用户信息更新成功");
				}
				// TODO Auto-generated method stub
				
			}
			
		});
	}
	private void removeUsers(final List<BeanObject> users){
		List<String> ids = new ArrayList<String>();
		for(BeanObject user:users){
			ids.add(user.getString(UserForm.PK_ID));
		}
		DeleteService service = new DeleteService();
		service.deleteBeans(ModelNames.USER,ids,new DeleteService.BatchDeleteListener(){
			public void onSuccess(Integer successCount){
				Info.display("恭喜", successCount+"个用户被删除");
				toolBar.refresh();
			}
			public void onFailure(Throwable caught){
				Info.display("糟糕", caught.toString());
			}
		});
		
		
	}
	
	private native void initJS(UserListPanel me)/*-{
		$wnd.changeUser = function (id) {
	        me.@com.jcommerce.gwt.client.panels.member.UserListPanel::modifyUserAndRefrsh(Ljava/lang/String;)(id);
	    };
	    $wnd.deleteUser = function (id) {
		    me.@com.jcommerce.gwt.client.panels.member.UserListPanel::deleteUserAndRefrsh(Ljava/lang/String;)(id);
		};
		$wnd.viewShippingAddress = function (id) {
		    me.@com.jcommerce.gwt.client.panels.member.UserListPanel::viewAddressAndRefrsh(Ljava/lang/String;)(id);
		};
		$wnd.viewOrder = function (id) {
		    me.@com.jcommerce.gwt.client.panels.member.UserListPanel::viewOrderAndRefrsh(Ljava/lang/String;)(id);
		};
	}-*/;
	
	private void modifyUserAndRefrsh(final String id) {
		UserPanel.State newState = new UserPanel.State();
		newState.setIsEdit(true);
		newState.setPkId(id);
		newState.execute();
	}
	
	private void deleteUserAndRefrsh(final String id) {
		new DeleteService().deleteBean(ModelNames.USER, id,
				new DeleteService.Listener() {
					public void onSuccess(Boolean success) {
						toolBar.refresh();
					}
				});
	}
	
	private void viewAddressAndRefrsh(final String id) {
		ShippingAddressPanel.State newState = new ShippingAddressPanel.State();
		newState.setPkId(id);
		newState.execute();
	}
	
	private void viewOrderAndRefrsh(final String id) {
		OrderListPanel.State newState = new OrderListPanel.State();
		newState.setPkId(id);
		newState.execute();
	}
	
	public void refresh(){
		toolBar.refresh();
	}

}
