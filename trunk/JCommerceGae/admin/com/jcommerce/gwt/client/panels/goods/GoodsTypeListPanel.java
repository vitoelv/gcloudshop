package com.jcommerce.gwt.client.panels.goods;

import java.util.ArrayList;
import java.util.List;

import com.extjs.gxt.ui.client.Style.HorizontalAlignment;
import com.extjs.gxt.ui.client.data.BasePagingLoader;
import com.extjs.gxt.ui.client.data.PagingLoadConfig;
import com.extjs.gxt.ui.client.data.RpcProxy;
import com.extjs.gxt.ui.client.event.ButtonEvent;
import com.extjs.gxt.ui.client.event.SelectionListener;
import com.extjs.gxt.ui.client.store.ListStore;
import com.extjs.gxt.ui.client.store.Record;
import com.extjs.gxt.ui.client.widget.ContentPanel;
import com.extjs.gxt.ui.client.widget.Info;
import com.extjs.gxt.ui.client.widget.button.Button;
import com.extjs.gxt.ui.client.widget.grid.CellEditor;
import com.extjs.gxt.ui.client.widget.grid.CheckColumnConfig;
import com.extjs.gxt.ui.client.widget.grid.ColumnConfig;
import com.extjs.gxt.ui.client.widget.grid.ColumnModel;
import com.extjs.gxt.ui.client.widget.grid.EditorGrid;
import com.extjs.gxt.ui.client.widget.grid.Grid;
import com.extjs.gxt.ui.client.widget.layout.FitLayout;
import com.extjs.gxt.ui.client.widget.toolbar.PagingToolBar;
import com.google.gwt.user.client.Element;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.jcommerce.gwt.client.ContentWidget;
import com.jcommerce.gwt.client.ISpecialServiceAsync;
import com.jcommerce.gwt.client.ModelNames;
import com.jcommerce.gwt.client.PageState;
import com.jcommerce.gwt.client.form.BeanObject;
import com.jcommerce.gwt.client.form.GoodsTypeForm;
import com.jcommerce.gwt.client.model.IGoodsType;
import com.jcommerce.gwt.client.resources.Resources;
import com.jcommerce.gwt.client.service.DeleteService;
import com.jcommerce.gwt.client.service.RemoteService;
import com.jcommerce.gwt.client.service.UpdateService;
import com.jcommerce.gwt.client.widgets.ActionCellRenderer;

public class GoodsTypeListPanel extends ContentWidget {
	public static interface Constants {
		String GoodsTypeList_MenuName();
		String GoodsTypeList_ColumnName();
		String GoodsTypeList_ColumnGroup();
		String GoodsTypeList_ColumnNumber();
		String GoodsTypeList_ColumnState();
		String GoodsTypeList_typeList();
		String GoodsTypeList_addNewType();

	}
	public static class State extends PageState {
		public String getPageClassName() {
			return GoodsTypeListPanel.class.getName();
		}
		public String getMenuDisplayName() {
			return Resources.constants.GoodsTypeList_MenuName();
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
	private static GoodsTypeListPanel instance;
	private GoodsTypeListPanel() {
		super();
		System.out.println("----------GoodsTypeList");
		initJS(this);
	}
	public static GoodsTypeListPanel getInstance(){
		if(instance == null) {
			instance = new GoodsTypeListPanel();
		}
		return instance;
	}
	


	private native void initJS(GoodsTypeListPanel me) /*-{
    $wnd.getAttrList = function (id) {
        me.@com.jcommerce.gwt.client.panels.goods.GoodsTypeListPanel::getAttrList(Ljava/lang/String;)(id);
    };
    
    $wnd.editGoodsType = function (id) {
        me.@com.jcommerce.gwt.client.panels.goods.GoodsTypeListPanel::editGoodsType(Ljava/lang/String;)(id);
    };
    
    $wnd.deleteGoodsType = function (id) {
        me.@com.jcommerce.gwt.client.panels.goods.GoodsTypeListPanel::deleteGoodsType(Ljava/lang/String;)(id);
    };
    }-*/;
	private void newGoodsType() {
		System.out.println("newGoodsType");
//		GoodsType.State newState = new GoodsType.State();
//		newState.setIsEdit(false);
//		newState.execute();
		
		GoodsTypePanel.State newState = new GoodsTypePanel.State();
		newState.setIsEdit(false);
		newState.execute();
	}
	private void editGoodsType(final String id) {
		System.out.println("editGoodsType:id= "+id);
//		GoodsType.State newState = new GoodsType.State();
//		newState.setIsEdit(true);
//		newState.setGoodsTypeID(id);
//		newState.execute();
		
		GoodsTypePanel.State newState = new GoodsTypePanel.State();
		newState.setIsEdit(true);
		newState.setPkId(id);
		newState.execute();
	}
	private void deleteGoodsType(final String id) {
		System.out.println("deleteGoodsType:id= "+id);
		
		new DeleteService().deleteBean(ModelNames.GOODSTYPE, id, new DeleteService.Listener() {
	        public void onSuccess(Boolean success) {
	        	if(success) {
	        		Info.display("恭喜", "删除商品类型成功");
	        	} else {
	        		Info.display("糟糕", "删除商品类型失败");
	        	}
	    		refresh();
	        }
	        
	        public void onFailure(Throwable caught) {
	        	Info.display("糟糕", "删除商品类型失败");
	        };
		});
		

	}
	private void getAttrList(final String id) {
		System.out.println("getAttrList:id= "+id);
		AttributeListPanel.State newState = new AttributeListPanel.State();
		newState.setSelectedGoodsTypeID(id);
		newState.execute();
		
		
//		iShop.getInstance().displayAttributeListPanel(id);
		
//		iShop.getInstance().displayAttributePanel(goodsType)
//		
//		Criteria criteria = new Criteria();
//		Condition cond = new Condition();
//		cond.setField(IAttribute.GOODSTYPE);
//		cond.setOperator(Condition.EQUALS);
//		cond.setValue(id);
//		criteria.addCondition(cond);
//		
//        final IDefaultServiceAsync service = getService();
//        service.getList(ModelNames.ATTRIBUTE, criteria, new AsyncCallback<List<BeanObject>>() {
//            public synchronized void onSuccess(List<BeanObject> result) {
//               
//            }
//            public synchronized void onFailure(Throwable caught) {
//                System.out.println("list onFailure("+caught);
//            }
//        });        
    }
    
    public String getDescription() {
        return "cwBasicTextDescription";
    }

    public String getName() {
        return Resources.constants.GoodsTypeList_MenuName();
    }
    protected void onRender(Element parent, int index) {
    	super.onRender(parent, index);
    	
    	System.out.println("---onRender GoodsTypeList---");
        
    	final List<String> wantedFields = new ArrayList<String>();
    	wantedFields.add(GoodsTypeForm.PK_ID);
    	wantedFields.add(GoodsTypeForm.CAT_NAME);
    	wantedFields.add(GoodsTypeForm.ATTR_GROUP);
    	wantedFields.add(GoodsTypeForm.ATTRCOUNT);
    	wantedFields.add(GoodsTypeForm.ENABLED);
    	
//    	loader = new PagingListService().getLoader(ModelNames.GOODSTYPE, wantedFields);
        final ISpecialServiceAsync service = RemoteService.getSpecialService();
        RpcProxy proxy = new RpcProxy() {
            public void load(Object loadConfig, AsyncCallback callback) {
                service.getGoodsTypeListWithAttrCount(ModelNames.GOODSTYPE, null, wantedFields, (PagingLoadConfig)loadConfig, callback);
            }
        };
        // loader
        BasePagingLoader loader = new BasePagingLoader(proxy);
        loader.setRemoteSort(true);
        
        
    	
    	final ListStore<BeanObject> store = new ListStore<BeanObject>(loader);
//        store.addStoreListener(new StoreListener<BeanObject>() {
//            public void storeUpdate(StoreEvent<BeanObject> se) {
//            	System.out.println("storeUpdate-----  se: "+se.toString());
//            }
//        });
        
        toolBar = new PagingToolBar(pageSize);
        toolBar.bind(loader);
        
        List<ColumnConfig> columns = new ArrayList<ColumnConfig>();
//        columns.add(new ColumnConfig(IGoodsType.NAME, "商品类型名称", 150));
		ColumnConfig col = new ColumnConfig(GoodsTypeForm.CAT_NAME, Resources.constants.GoodsTypeList_ColumnName(), 150);
		col.setEditor(new CellEditor(GoodsTypeForm.getNameField(Resources.constants.GoodsTypeList_ColumnName())));
		columns.add(col);
		
        columns.add(new ColumnConfig(GoodsTypeForm.ATTR_GROUP, Resources.constants.GoodsTypeList_ColumnGroup(), 200));
        columns.add(new ColumnConfig(GoodsTypeForm.ATTRCOUNT, Resources.constants.GoodsTypeList_ColumnNumber(), 100));
        CheckColumnConfig ccc1 = new CheckColumnConfig(GoodsTypeForm.ENABLED, Resources.constants.GoodsTypeList_ColumnState(), 100);
        ccc1.setFixed(false);
        columns.add(ccc1);
        ColumnConfig actcol = new ColumnConfig("Action", Resources.constants
				.action(), 180);
        columns.add(actcol);
        
        ColumnModel cm = new ColumnModel(columns);

        Grid<BeanObject> grid = new EditorGrid<BeanObject>(store, cm);
        grid.setLoadMask(true);
        grid.setBorders(true);
        grid.setAutoExpandColumn(GoodsTypeForm.CAT_NAME);
//        grid.setSelectionModel(smRowSelection);
//        grid.setAutoExpandColumn("forum");


        ActionCellRenderer render = new ActionCellRenderer(grid);
        ActionCellRenderer.ActionInfo act = null;
        act = new ActionCellRenderer.ActionInfo();        
        act.setText(Resources.constants.GoodsTypeList_typeList());
        act.setAction("getAttrList($pkId)");
        render.addAction(act);
        
        act = new ActionCellRenderer.ActionInfo();
        act.setText(Resources.constants.edit());
        act.setAction("editGoodsType($pkId)");
        render.addAction(act);
        act = new ActionCellRenderer.ActionInfo();
        act.setText(Resources.constants.delete());
		act.setAction("deleteGoodsType($pkId)");
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
        panel.setHeight(350);
        panel.setBottomComponent(toolBar);
        panel.setButtonAlign(HorizontalAlignment.CENTER);
        
        
		panel.addButton(new Button(Resources.constants.reset(),
				new SelectionListener<ButtonEvent>() {
					public void componentSelected(ButtonEvent ce) {
						store.rejectChanges();
					}
				}));

		panel.addButton(new Button(Resources.constants.save(),
				new SelectionListener<ButtonEvent>() {
					public void componentSelected(ButtonEvent ce) {
		                List<Record> changed = store.getModifiedRecords();
		                // TODO transactional ?  what happen if one record failed to update?
						for (Record rec : changed) {
							BeanObject bean = (BeanObject) rec.getModel();
							update(bean, null);
						}
						// this is to remove the mark from the UI only, will not handle committing to database
						store.commitChanges();
					}
				}));
		
//        panel.addButton(new Button(Resources.constants.GoodsTypeList_addNewType(), 
//        		new SelectionListener<ButtonEvent>() {
//        	public void componentSelected(ButtonEvent ce) {
//              	newGoodsType();
//        	}
//        }));
        
        add(panel);
    }
    public Button getShortCutButton(){
    	Button sButton = new Button(Resources.constants.GoodsTypeList_addNewType());
    	sButton.addSelectionListener(new SelectionListener<ButtonEvent>(){

			@Override
			public void componentSelected(ButtonEvent ce) {
				newGoodsType();
			}
    		
    	});
    	return sButton;
    }
	private void update(BeanObject goodsType, UpdateService.Listener listener) {
		new UpdateService().updateBean(goodsType.getString(IGoodsType.PK_ID), goodsType,
				listener);
	}
	
    public void refresh() {
    	
    	System.out.println("----- refresh goodsTypeList---");
    	toolBar.refresh();
    }

}