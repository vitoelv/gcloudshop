package com.jcommerce.gwt.client.panels;

import java.util.ArrayList;
import java.util.List;

import com.extjs.gxt.ui.client.Style.HorizontalAlignment;
import com.extjs.gxt.ui.client.data.ModelData;
import com.extjs.gxt.ui.client.event.ButtonEvent;
import com.extjs.gxt.ui.client.event.SelectionListener;
import com.extjs.gxt.ui.client.store.ListStore;
import com.extjs.gxt.ui.client.store.Record;
import com.extjs.gxt.ui.client.store.StoreEvent;
import com.extjs.gxt.ui.client.store.StoreListener;
import com.extjs.gxt.ui.client.store.TreeStore;
import com.extjs.gxt.ui.client.widget.ContentPanel;
import com.extjs.gxt.ui.client.widget.HorizontalPanel;
import com.extjs.gxt.ui.client.widget.button.Button;
import com.extjs.gxt.ui.client.widget.form.TextField;
import com.extjs.gxt.ui.client.widget.grid.CellEditor;
import com.extjs.gxt.ui.client.widget.grid.CheckBoxSelectionModel;
import com.extjs.gxt.ui.client.widget.grid.CheckColumnConfig;
import com.extjs.gxt.ui.client.widget.grid.ColumnConfig;
import com.extjs.gxt.ui.client.widget.grid.ColumnModel;
import com.extjs.gxt.ui.client.widget.grid.EditorGrid;
import com.extjs.gxt.ui.client.widget.grid.Grid;
import com.extjs.gxt.ui.client.widget.layout.FitLayout;
import com.extjs.gxt.ui.client.widget.toolbar.PagingToolBar;
import com.extjs.gxt.ui.client.widget.treegrid.EditorTreeGrid;
import com.extjs.gxt.ui.client.widget.treegrid.TreeGridCellRenderer;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.user.client.Element;
import com.google.gwt.user.client.ui.Label;
import com.jcommerce.gwt.client.ContentWidget;
import com.jcommerce.gwt.client.ModelNames;
import com.jcommerce.gwt.client.PageState;
import com.jcommerce.gwt.client.form.AttributeForm;
import com.jcommerce.gwt.client.form.BeanObject;
import com.jcommerce.gwt.client.model.IArticleCat;
import com.jcommerce.gwt.client.model.ICategory;
import com.jcommerce.gwt.client.resources.Resources;
import com.jcommerce.gwt.client.service.ListService;
import com.jcommerce.gwt.client.service.PagingListService;
import com.jcommerce.gwt.client.widgets.ActionCellRenderer;

public class ArticleCatListPanel extends ContentWidget{
	public static interface Constants {

	}
	
	private ArticleCatListPanel(){
		initJS(this);
	}
	private native void initJS(ArticleCatListPanel me) /*-{
	}-*/;
	
	private static ArticleCatListPanel instance;
	public static ArticleCatListPanel getInstance(){
		if(instance == null){
			return new ArticleCatListPanel();
		}
		else return instance;
	}
    public static class State extends PageState{

		@Override
		public String getPageClassName() {
			// TODO Auto-generated method stub
			return ArticleCatListPanel.class.getName();
		}
		public String getMenuDisplayName() {
			return "Article Category";
		}
    	
    }
    private State curState = new State();
	@Override
	protected PageState getCurState() {
		// TODO Auto-generated method stub
		return curState;
	}
	TreeStore<BeanObject> store = new TreeStore<BeanObject>();
	protected void onRender(Element parent, int index) {
    	super.onRender(parent, index);

        //BasePagingLoader loader = new PagingListService().getLoader(ModelNames.CATEGORY);

        //loader.load(0, 50);
    	
        //final ListStore<BeanObject> store = new ListStore<BeanObject>(loader);

        store.addStoreListener(new StoreListener<BeanObject>() {
            public void storeUpdate(StoreEvent<BeanObject> se) {
                List<Record> changed = store.getModifiedRecords();
            }
        });
        
        //toolBar = new PagingToolBar(50);
        //toolBar.bind(loader);

        List<ColumnConfig> columns = new ArrayList<ColumnConfig>();
        //CheckBoxSelectionModel<BeanObject> sm = new CheckBoxSelectionModel<BeanObject>();
        //columns.add(sm.getColumn()); 
        ColumnConfig colName = new ColumnConfig(IArticleCat.CAT_NAME, "文章分类名称", 150);
        colName.setRenderer(new TreeGridCellRenderer<BeanObject>());
        columns.add(colName);
        columns.add(new ColumnConfig(IArticleCat.CAT_TYPE, "文章类型", 80));
        
        columns.add(new CheckColumnConfig(IArticleCat.CAT_DESC, "描述", 80));        
        columns.add(new ColumnConfig(IArticleCat.SORT_ORDER,"排序", 50));
        columns.add(new CheckColumnConfig(IArticleCat.SHOW_IN_NAV, "是否现实在导航栏", 80) {
        	// TODO: wrap the code for Long type column into a baseclass
        	  protected String getCheckState(ModelData model, String property, int rowIndex,
        		      int colIndex) {
        		    Long v = model.get(property);
        		    // see IConstants.DBTYPE_TRUE
        		    String on = (v != null && v==1) ? "-on" : "";
        		    return on;
        		  }
        });
        ColumnConfig actcol = new ColumnConfig("Action", Resources.constants.action(), 150);
        columns.add(actcol);

        ColumnModel cm = new ColumnModel(columns);

        EditorTreeGrid<BeanObject> grid = new EditorTreeGrid<BeanObject>(store, cm);
        grid.setLoadMask(true);
        grid.setBorders(true);
        //grid.setSelectionModel(sm);
//        grid.setAutoExpandColumn("forum");


        ActionCellRenderer render = new ActionCellRenderer(grid);
        ActionCellRenderer.ActionInfo act = new ActionCellRenderer.ActionInfo();        
        act.setText(Resources.constants.edit());
        //act.setAction("changeCategory($pkId)");
        render.addAction(act);
        act = new ActionCellRenderer.ActionInfo();
        act.setText(" " + Resources.constants.delete());
		//act.setAction("deleteCategory($pkId)");
		act.setTooltip(Resources.constants.GoodsList_action_delete());
		render.addAction(act);
        actcol.setRenderer(render);        
        
        ContentPanel panel = new ContentPanel();
        panel.setFrame(true);
        panel.setCollapsible(true);
        panel.setAnimCollapse(false);
        //panel.setButtonAlign(HorizontalAlignment.CENTER);
        panel.setIconStyle("icon-table");
        panel.setHeading("Paging Grid");
        panel.setLayout(new FitLayout());
        panel.add(grid);
        panel.setSize(800, 350);
        //panel.setBottomComponent(toolBar);
        
        panel.setButtonAlign(HorizontalAlignment.CENTER);
//        panel.addButton(new com.extjs.gxt.ui.client.widget.button.Button(Resources.constants.Category_title(), new SelectionListener<ButtonEvent>() {
//          public void componentSelected(ButtonEvent ce) {
////              JCommerceGae.getInstance().displayNewCategory();
//        	  	CategoryPanel.State newState = new CategoryPanel.State();
//				newState.setIsEdit(false);
//				newState.execute();
//          }
//        }));
        
        add(panel);        
	}
	public Button getShortCutButton(){
		Button sButton = new Button("增加文章分类");
	      sButton.addSelectionListener(new SelectionListener<ButtonEvent>() {
	          public void componentSelected(ButtonEvent ce) {
	          	//onShortCutButtonClicked();
	          }
	      });
	      return sButton;
	}
	 public void refresh(){
	    	new ListService().treeListBeans(ModelNames.ARTICLE_CAT,null,new ListService.Listener(){

				@Override
				public void onSuccess(List<BeanObject> beans) {
					if(store.getChildCount()>0){
						store.removeAll();
					}
					store.add(beans, true);
				}
	    		
	    	});
	    	//toolBar.refresh();
	    }

	@Override
	public String getDescription() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return "Article Category";
	}

}
