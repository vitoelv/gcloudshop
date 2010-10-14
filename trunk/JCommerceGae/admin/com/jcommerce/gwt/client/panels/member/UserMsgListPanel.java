package com.jcommerce.gwt.client.panels.member;

import java.util.ArrayList;
import java.util.List;

import com.extjs.gxt.ui.client.Style.HorizontalAlignment;
import com.extjs.gxt.ui.client.data.BasePagingLoader;
import com.extjs.gxt.ui.client.store.ListStore;
import com.extjs.gxt.ui.client.widget.ContentPanel;
import com.extjs.gxt.ui.client.widget.Info;
import com.extjs.gxt.ui.client.widget.button.Button;
import com.extjs.gxt.ui.client.widget.grid.CheckBoxSelectionModel;
import com.extjs.gxt.ui.client.widget.grid.ColumnConfig;
import com.extjs.gxt.ui.client.widget.grid.ColumnModel;
import com.extjs.gxt.ui.client.widget.grid.EditorGrid;
import com.extjs.gxt.ui.client.widget.grid.Grid;
import com.extjs.gxt.ui.client.widget.layout.FitLayout;
import com.extjs.gxt.ui.client.widget.toolbar.PagingToolBar;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.Element;
import com.google.gwt.user.client.ui.TextBox;
import com.jcommerce.gwt.client.ContentWidget;
import com.jcommerce.gwt.client.ModelNames;
import com.jcommerce.gwt.client.PageState;
import com.jcommerce.gwt.client.form.BeanObject;
import com.jcommerce.gwt.client.form.UserForm;
import com.jcommerce.gwt.client.model.IFeedback;
import com.jcommerce.gwt.client.panels.order.OrderListPanel;
import com.jcommerce.gwt.client.resources.Resources;
import com.jcommerce.gwt.client.service.Condition;
import com.jcommerce.gwt.client.service.Criteria;
import com.jcommerce.gwt.client.service.DeleteService;
import com.jcommerce.gwt.client.service.PagingListService;
import com.jcommerce.gwt.client.service.UpdateService;
import com.jcommerce.gwt.client.widgets.ActionCellRenderer;
import com.jcommerce.gwt.client.widgets.MsgTypeCellRenderer;
import com.jcommerce.gwt.client.widgets.TimeCellRenderer;

public class UserMsgListPanel extends ContentWidget {

	public static interface Constants {
		String UserMsgList_menuName();
		String UserMsgList_colUserName();
		String UserMsgList_colTitle();
		String UserMsgList_colType();
		String UserMsgList_colTime();
		String UserMsgList_title();
	}
	
	public static class State extends PageState{
		@Override
		public String getPageClassName() {
			return UserMsgListPanel.class.getName();
		}
		public String getMenuDisplayName() {
			return Resources.constants.UserMsgList_menuName();
		}                          
	}
	private UserMsgListPanel(){
		initJS(this);
	}
	
	private State curState = new State();
	private static UserMsgListPanel instance;
	public static UserMsgListPanel getInstance(){
		if(instance ==null){
			instance = new UserMsgListPanel();
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
		
		Criteria criteria = new Criteria();
		criteria.addCondition(new Condition(IFeedback.PARENT_ID, Condition.EQUALS, null));
		
		loader = new PagingListService().getLoader(ModelNames.FEEDBACK, criteria);
		final ListStore<BeanObject> store= new ListStore<BeanObject>(loader);

		toolBar = new PagingToolBar(pageSize);
		toolBar.bind(loader);
		
		List<ColumnConfig> columns = new ArrayList<ColumnConfig>();
		final CheckBoxSelectionModel<BeanObject> smRowSelection = new CheckBoxSelectionModel<BeanObject>();
		columns.add(smRowSelection.getColumn());

		ColumnConfig colUserName = new ColumnConfig(IFeedback.USER_NAME, Resources.constants.UserMsgList_colUserName(), 200);
		columns.add(colUserName);
		
		ColumnConfig colTitle = new ColumnConfig(IFeedback.MSG_TITLE, Resources.constants.UserMsgList_colTitle(),200);
		columns.add(colTitle);
		
		ColumnConfig colMsgType = new ColumnConfig(IFeedback.MSG_TYPE, Resources.constants.UserMsgList_colType(), 200);
		MsgTypeCellRenderer mcr = new MsgTypeCellRenderer();
		colMsgType.setRenderer(mcr);
		columns.add(colMsgType);
		
		ColumnConfig colMsgTime = new ColumnConfig(IFeedback.MSG_TIME, Resources.constants.UserMsgList_colTime(), 100);
		TimeCellRenderer tcr =new TimeCellRenderer();
		colMsgTime.setRenderer(tcr);
		columns.add(colMsgTime);
		
		ColumnConfig colaction = new ColumnConfig("Action", Resources.constants.action(), 160);
		columns.add(colaction);
		ColumnModel cm = new ColumnModel(columns);

		final Grid<BeanObject> grid = new EditorGrid<BeanObject>(store, cm);
		grid.setLoadMask(true);
		grid.setBorders(true);
		grid.setAutoExpandColumn(IFeedback.MSG_TIME);

		grid.setSelectionModel(smRowSelection);
		ActionCellRenderer render = new ActionCellRenderer(grid);
		ActionCellRenderer.ActionInfo act = new ActionCellRenderer.ActionInfo();
		act.setImage(GWT.getModuleBaseURL()+"icon_edit.gif");
		act.setAction("reply($pkId)");
		act.setTooltip(Resources.constants.edit());
		render.addAction(act);

		act = new ActionCellRenderer.ActionInfo();		
		act.setImage(GWT.getModuleBaseURL()+"icon_drop.gif");
		act.setAction("deleteMsg($pkId)");
		act.setTooltip(Resources.constants.delete());
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
		panel.setHeight(350);
		panel.setBottomComponent(toolBar);
		
		add(panel);
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
		return Resources.constants.UserMsgList_title();
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
	
	private native void initJS(UserMsgListPanel me)/*-{
		$wnd.reply = function (id) {
	        me.@com.jcommerce.gwt.client.panels.member.UserMsgListPanel::replyMsg(Ljava/lang/String;)(id);
	    };
	    $wnd.deleteMsg = function (id) {
		    me.@com.jcommerce.gwt.client.panels.member.UserMsgListPanel::deleteMsg(Ljava/lang/String;)(id);
		};
	}-*/;
	
	private void replyMsg(final String id) {
		ReplyMsgPanel.State newState = new ReplyMsgPanel.State();
		newState.setPkId(id);
		newState.execute();
	}
	
	private void deleteMsg(final String id) {
		new DeleteService().deleteBean(ModelNames.FEEDBACK, id, new DeleteService.Listener() {
			
			public void onSuccess(Boolean success) {
				Info.display("恭喜","删除留言成功");
				toolBar.refresh();
			}
			public void onFailure(Throwable caught){
				Info.display("糟糕", caught.toString());
			}
			
		});
	}
	
	public void refresh(){
		toolBar.refresh();
	}

}
