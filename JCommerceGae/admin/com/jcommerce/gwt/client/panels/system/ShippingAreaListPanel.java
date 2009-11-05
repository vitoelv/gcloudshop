package com.jcommerce.gwt.client.panels.system;

import java.util.ArrayList;
import java.util.List;

import com.extjs.gxt.ui.client.Style.HorizontalAlignment;
import com.extjs.gxt.ui.client.data.BaseListLoader;
import com.extjs.gxt.ui.client.data.ListLoadConfig;
import com.extjs.gxt.ui.client.data.ListLoadResult;
import com.extjs.gxt.ui.client.data.ListLoader;
import com.extjs.gxt.ui.client.event.ButtonEvent;
import com.extjs.gxt.ui.client.event.SelectionListener;
import com.extjs.gxt.ui.client.store.ListStore;
import com.extjs.gxt.ui.client.store.Record;
import com.extjs.gxt.ui.client.store.StoreEvent;
import com.extjs.gxt.ui.client.store.StoreListener;
import com.extjs.gxt.ui.client.widget.ContentPanel;
import com.extjs.gxt.ui.client.widget.Info;
import com.extjs.gxt.ui.client.widget.button.Button;
import com.extjs.gxt.ui.client.widget.form.TextField;
import com.extjs.gxt.ui.client.widget.grid.CellEditor;
import com.extjs.gxt.ui.client.widget.grid.CheckBoxSelectionModel;
import com.extjs.gxt.ui.client.widget.grid.ColumnConfig;
import com.extjs.gxt.ui.client.widget.grid.ColumnData;
import com.extjs.gxt.ui.client.widget.grid.ColumnModel;
import com.extjs.gxt.ui.client.widget.grid.EditorGrid;
import com.extjs.gxt.ui.client.widget.grid.Grid;
import com.extjs.gxt.ui.client.widget.grid.GridCellRenderer;
import com.extjs.gxt.ui.client.widget.layout.FitLayout;
import com.google.gwt.user.client.Element;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.jcommerce.gwt.client.ContentWidget;
import com.jcommerce.gwt.client.ModelNames;
import com.jcommerce.gwt.client.PageState;
import com.jcommerce.gwt.client.form.BeanObject;
import com.jcommerce.gwt.client.model.IShippingArea;
import com.jcommerce.gwt.client.resources.Resources;
import com.jcommerce.gwt.client.service.RemoteService;
import com.jcommerce.gwt.client.util.MyRpcProxy;

public class ShippingAreaListPanel extends ContentWidget {
    public static interface Constants {        

    	String ShippingAreaList_NAME();
    	String ShippingAreaList_REGION_NAMES();
    	String ShippingAreaList_ACTION();
    }
	public static class State extends PageState {
		static final String SHIPPING_ID = "ShippingId";
        public String getPageClassName() {
            return ShippingAreaListPanel.class.getName();
        }
        public void setShippingId(String shippingId) {
        	setValue(SHIPPING_ID, shippingId);
        }
        public String getShippingId() {
        	return (String)getValue(SHIPPING_ID);
        }
    }
    private State curState = new State();
	@Override
	protected State getCurState() {
		return curState;
	}
    private static ShippingAreaListPanel instance;
    public static ShippingAreaListPanel getInstance() {
        if(instance == null) {
            instance = new ShippingAreaListPanel();
        }
        return instance;
    }
    ListLoader<ListLoadResult<BeanObject>> loader;
    
    @Override
    public Button getShortCutButton() {
      Button sButton = new Button("新建配送区域");
      sButton.addSelectionListener(new SelectionListener<ButtonEvent>() {
          public void componentSelected(ButtonEvent ce) {
          	onShortCutButtonClicked();
          }
      });
      return sButton;
    }
    private ShippingAreaListPanel() {
        super();
        initJS(this);
    }

    private native void initJS(ShippingAreaListPanel me) /*-{
       $wnd.editShippingArea = function (id) {
           me.@com.jcommerce.gwt.client.panels.system.ShippingAreaListPanel::editShippingArea(Ljava/lang/String;)(id);
       };
       $wnd.deleteShippingArea = function (id) {
           me.@com.jcommerce.gwt.client.panels.system.ShippingAreaListPanel::deleteShippingArea(Ljava/lang/String;)(id);
       };

       }-*/;
    private void onShortCutButtonClicked() {
        ShippingAreaPanel.State newState = new ShippingAreaPanel.State();
        newState.setIsEdit(false);
        newState.setShippingId(getCurState().getShippingId());
        newState.execute();
    }
    private void editShippingArea(String shippingAreaId) {
        System.out.println("editShippingArea: "+shippingAreaId);
        ShippingAreaPanel.State newState = new ShippingAreaPanel.State();
        newState.setShippingId(getCurState().getShippingId());
        newState.setIsEdit(true);
        newState.setPkId(shippingAreaId);
        newState.execute();
    }
    private void deleteShippingArea(String id) {
        System.out.println("deleteShippingArea: "+id);
        RemoteService.getDefaultService().deleteObject(ModelNames.SHIPPINGAREA, id, new AsyncCallback<Boolean>() {
            public void onFailure(Throwable caught) {
                log("error in deleteShippingArea : "+caught.getMessage());
            }
            public void onSuccess(Boolean result) {
                if(result) {
                    refresh();
                }
                else {
                    Info.display("oops", "deleteShippingArea failed!!!");
                }
            }
        });
    }
    @Override
    protected void onRender(Element parent, int index) {
        super.onRender(parent, index);
        // loader
        
        MyRpcProxy<ListLoadResult<BeanObject>> proxy = new MyRpcProxy<ListLoadResult<BeanObject>>() {
            public void load(Object loadConfig, AsyncCallback<ListLoadResult<BeanObject>> callback) {
                RemoteService.getSpecialService().getShippingAreaWithRegionNames(
                		getCurState().getShippingId(), (ListLoadConfig) loadConfig, callback);
            }
        };
        loader = new BaseListLoader<ListLoadResult<BeanObject>>(proxy);
        loader.setRemoteSort(true);
        
        final ListStore<BeanObject> store = new ListStore<BeanObject>(loader);

        store.addStoreListener(new StoreListener<BeanObject>() {
            public void storeUpdate(StoreEvent<BeanObject> se) {
                List<Record> changed = store.getModifiedRecords();
            }
        });
        
        
        List<ColumnConfig> columns = new ArrayList<ColumnConfig>();
        final CheckBoxSelectionModel<BeanObject> smRowSelection = new CheckBoxSelectionModel<BeanObject>();
        columns.add(smRowSelection.getColumn());
        ColumnConfig col = new ColumnConfig(IShippingArea.SHIPPING_AREA_NAME, Resources.constants
                .ShippingAreaList_NAME(), 50);
        col.setEditor(new CellEditor(new TextField<String>()));
        columns.add(col);
        columns.add(new ColumnConfig(IShippingArea.REGION_NAMES, Resources.constants
                .ShippingAreaList_REGION_NAMES(), 150));

        ColumnConfig actcol = new ColumnConfig("Action", Resources.constants
                .ShippingAreaList_ACTION(), 100);
        columns.add(actcol);

        ColumnModel cm = new ColumnModel(columns);

        Grid<BeanObject> grid = new EditorGrid<BeanObject>(store, cm);
        grid.setLoadMask(true);
        grid.setBorders(true);
        grid.setSelectionModel(smRowSelection);
        
        ActionCellRenderer render = new ActionCellRenderer(grid);
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
        panel.setSize(900, 350);
//        panel.setBottomComponent(toolBar);
        
        add(panel);
        
    }
    class ActionCellRenderer implements GridCellRenderer<BeanObject> {
        public ActionCellRenderer(Grid grid) {
        }

        public String render(BeanObject model, String property, ColumnData config,
                final int rowIndex, final int colIndex, ListStore<BeanObject> store, Grid<BeanObject> grid) {
            try {
            String id = model.get(IShippingArea.PK_ID);
            if(id==null) {
                id = "";
            }
            System.out.println("id: "+id);
            
            StringBuffer sb = new StringBuffer();
            
                sb.append("<a href=\"javascript:editShippingArea('");
                sb.append(id);
                sb.append("');\">").append(Resources.constants.edit()).append("</a>&nbsp;");
                sb.append("<a href=\"javascript:deleteShippingArea('");
                sb.append(id);
                sb.append("');\">").append(Resources.constants.delete()).append("</a>");
           
                return sb.toString();
            } catch (Exception e) {
            	e.printStackTrace();
            	throw new RuntimeException(e);
            }
        }


    }
	@Override
	public String getDescription() {
		return "cwBasicTextDescription";
	}

	@Override
	public String getName() {
		return "配送区域";
	}
	
	@Override 
	public void refresh() {
		loader.load();
	}

}
