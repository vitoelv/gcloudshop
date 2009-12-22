package com.jcommerce.gwt.client.panels.privilege;

import java.util.ArrayList;
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
import com.extjs.gxt.ui.client.widget.Info;
import com.extjs.gxt.ui.client.widget.button.Button;
import com.extjs.gxt.ui.client.widget.grid.ColumnConfig;
import com.extjs.gxt.ui.client.widget.grid.ColumnModel;
import com.extjs.gxt.ui.client.widget.grid.Grid;
import com.extjs.gxt.ui.client.widget.layout.FitLayout;
import com.extjs.gxt.ui.client.widget.toolbar.PagingToolBar;
import com.google.gwt.user.client.Element;
import com.jcommerce.gwt.client.ContentWidget;
import com.jcommerce.gwt.client.ModelNames;
import com.jcommerce.gwt.client.form.BeanObject;
import com.jcommerce.gwt.client.model.IAdminUser;
import com.jcommerce.gwt.client.panels.BaseEntityEditPanel;
import com.jcommerce.gwt.client.panels.goods.AttributeListPanel;
import com.jcommerce.gwt.client.resources.Resources;
import com.jcommerce.gwt.client.service.DeleteService;
import com.jcommerce.gwt.client.service.PagingListService;
import com.jcommerce.gwt.client.service.UpdateService;
import com.jcommerce.gwt.client.widgets.ActionCellRenderer;

public class AdminListPanel extends ContentWidget {

	public static interface Constants {
        String AdminList_userName();
        String AdminList_email();
        String AdminList_title();
        String AdminList_addTime();
        String AdminList_lastLogin();
    }
	
	public static class State extends BaseEntityEditPanel.State {
		
		public String getPageClassName() {
			return AdminListPanel.class.getName();
		}
		public String getMenuDisplayName() {
			return Resources.constants.AdminList_title();
		}
	}
	private State curState = new State();
	public State getCurState() {
		return curState;
	}

	public void setCurState(State curState) {
		this.curState = curState;
	}
	int pageSize = 5;
	
	PagingToolBar toolBar; 
	
	public static AdminListPanel getInstance(){
		if(instance==null) {
    		instance = new AdminListPanel();
    	}
    	return instance;
	}

	private static AdminListPanel instance;
    private AdminListPanel() {
    	super();
    	initJS(this);
    }

    private native void initJS(AdminListPanel me) /*-{
    $wnd.getPrivilegeList = function (id) {
        me.@com.jcommerce.gwt.client.panels.privilege.AdminListPanel::getPrivilegeList(Ljava/lang/String;)(id);
    };
    
    $wnd.editAdminUser = function (id) {
        me.@com.jcommerce.gwt.client.panels.privilege.AdminListPanel::editAdminUser(Ljava/lang/String;)(id);
    };
    
    $wnd.deleteAdminUser = function (id) {
        me.@com.jcommerce.gwt.client.panels.privilege.AdminListPanel::deleteAdminUser(Ljava/lang/String;)(id);
    };
    }-*/;
	
    private void editAdminUser(final String id) {
		System.out.println("editAdminUser:id= "+id);		
		AdminUserPanel.State newState = new AdminUserPanel.State();
		newState.setIsEdit(true);
		newState.setPkId(id);
		newState.execute();
	}
	private void deleteAdminUser(final String id) {
		System.out.println("deleteAdminUser:id= "+id);
		
		new DeleteService().deleteBean(ModelNames.ADMINUSER, id, new DeleteService.Listener() {
	        public void onSuccess(Boolean success) {
	        	if(success) {
	        		Info.display(Resources.constants.OperationSuccessful(), Resources.constants.AdminUser_deleteSuccessfully());
	        	} else {
	        		Info.display(Resources.constants.OperationFailure(), Resources.constants.AdminUser_deleteFailure());
	        	}
	    		refresh();
	        }
	        
	        public void onFailure(Throwable caught) {
	        	Info.display(Resources.constants.OperationFailure(), Resources.constants.AdminUser_deleteFailure());
	        };
		});
		

	}

    private void getPrivilegeList(final String id) {
		System.out.println("getPrivilegeList:id= "+id);
		AttributeListPanel.State newState = new AttributeListPanel.State();
		newState.setSelectedGoodsTypeID(id);
		newState.execute();
    }

	@Override
	public String getDescription() {
		return "cwBasicTextDescription";
	}

	@Override
	public String getName() {
		return Resources.constants.AdminList_title();
	}
	
	protected void onRender(Element parent, int index) {
    	super.onRender(parent, index);
    	
    	System.out.println("---onRender AdminList---");
        
    	BasePagingLoader loader = new PagingListService().getLoader(ModelNames.ADMINUSER);

//      loader.load(0, 50);
  	
      final ListStore<BeanObject> store = new ListStore<BeanObject>(loader);

      store.addStoreListener(new StoreListener<BeanObject>() {
          public void storeUpdate(StoreEvent<BeanObject> se) {
              List<Record> changed = store.getModifiedRecords();
          }
      });
      
      toolBar = new PagingToolBar(50);
      toolBar.bind(loader);

      List<ColumnConfig> columns = new ArrayList<ColumnConfig>();
      //CheckBoxSelectionModel<BeanObject> sm = new CheckBoxSelectionModel<BeanObject>();
      //columns.add(sm.getColumn());        
      columns.add(new ColumnConfig(IAdminUser.USER_NAME, Resources.constants.AdminList_userName(), 140));
      columns.add(new ColumnConfig(IAdminUser.EMAIL, Resources.constants.AdminList_email(), 150));
      columns.add(new ColumnConfig(IAdminUser.ADD_TIME, Resources.constants.AdminList_addTime(), 150));
      columns.add(new ColumnConfig(IAdminUser.LAST_LOGIN, Resources.constants.AdminList_lastLogin(), 150));
     
      ColumnConfig actcol = new ColumnConfig("Action", Resources.constants.action(), 140);
      columns.add(actcol);


      ColumnModel cm = new ColumnModel(columns);


      Grid<BeanObject> grid = new Grid<BeanObject>(store, cm);
      grid.setLoadMask(true);
      grid.setBorders(true);
      //grid.setSelectionModel(sm);
//      grid.setAutoExpandColumn("forum");


      ActionCellRenderer render = new ActionCellRenderer(grid);
      ActionCellRenderer.ActionInfo act = new ActionCellRenderer.ActionInfo();        
      act.setText(Resources.constants.edit());
      act.setAction("editAdminUser($pkId)");
      act.setTooltip(Resources.constants.edit());
      render.addAction(act);
      act = new ActionCellRenderer.ActionInfo();
      act.setText(Resources.constants.delete());
		act.setAction("deleteAdminUser($pkId)");
		act.setTooltip(Resources.constants.delete());
		render.addAction(act);
      actcol.setRenderer(render);        
      
      ContentPanel panel = new ContentPanel();
      panel.setFrame(true);
      panel.setCollapsible(true);
      panel.setAnimCollapse(false);
      panel.setButtonAlign(HorizontalAlignment.CENTER);
      panel.setIconStyle("icon-table");
      panel.setHeading("Paging Grid");
      panel.setLayout(new FitLayout());
      panel.add(grid);
      panel.setSize(800, 350);
//      panel.setSize("100%", "350px");
      panel.setBottomComponent(toolBar);     
      
      panel.setButtonAlign(HorizontalAlignment.CENTER);
     
      add(panel);       
    }
	private void update(BeanObject adminUser, UpdateService.Listener listener) {
		new UpdateService().updateBean(adminUser.getString(IAdminUser.PK_ID), adminUser,
				listener);
	}
	
    public void refresh() {
    	
    	System.out.println("----- refresh goodsTypeList---");
    	toolBar.refresh();
    }
    
    @Override
    public Button getShortCutButton() {
//    	return btnAdd;
      Button buttonAddClone = new Button(Resources.constants.AdminUser_title());
      buttonAddClone.addSelectionListener(new SelectionListener<ButtonEvent>() {
          public void componentSelected(ButtonEvent ce) {
          	onButtonAddClicked();
          }
      });
      return buttonAddClone;
    }
    
    public void onButtonAddClicked() {
		AdminUserPanel.State newState = new AdminUserPanel.State();
		newState.setIsEdit(false);
		newState.execute();
    }



}
