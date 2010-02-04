package com.jcommerce.gwt.client.panels.article;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
import com.extjs.gxt.ui.client.widget.grid.ColumnData;
import com.extjs.gxt.ui.client.widget.grid.ColumnModel;
import com.extjs.gxt.ui.client.widget.grid.EditorGrid;
import com.extjs.gxt.ui.client.widget.grid.Grid;
import com.extjs.gxt.ui.client.widget.grid.GridCellRenderer;
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
import com.jcommerce.gwt.client.form.ArticleCatForm;
import com.jcommerce.gwt.client.form.AttributeForm;
import com.jcommerce.gwt.client.form.BeanObject;
import com.jcommerce.gwt.client.model.IArticleCat;
import com.jcommerce.gwt.client.model.ICategory;
import com.jcommerce.gwt.client.resources.Resources;
import com.jcommerce.gwt.client.service.DeleteService;
import com.jcommerce.gwt.client.service.ListService;
import com.jcommerce.gwt.client.widgets.ActionCellRenderer;

public class ArticleCatListPanel extends ContentWidget{
	public static interface Constants {
		String ArticleCat_MenuName();
		String ArticleCat_col_name();
		String ArticleCat_col_type();
		String ArticleCat_col_desc();
		String ArticleCat_col_order();
		String ArticleCat_col_showInNav();
		String ArticleCat_sbtn_add();
		String ArticleCat_title();
	}
	
	private ArticleCatListPanel(){
		initJS(this);
	}
	private native void initJS(ArticleCatListPanel me) /*-{
		$wnd.changeArticleCat = function (id) {
           me.@com.jcommerce.gwt.client.panels.article.ArticleCatListPanel::modifyArticleCatAndRefrsh(Ljava/lang/String;)(id);
        };
        $wnd.deleteArticleCat = function(id){
        	me.@com.jcommerce.gwt.client.panels.article.ArticleCatListPanel::deleteArticleCatAndRefrsh(Ljava/lang/String;)(id);
        }
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
			return Resources.constants.ArticleCat_MenuName();
		}
    	
    }
    private State curState = new State();
	@Override
	protected PageState getCurState() {
		// TODO Auto-generated method stub
		return curState;
	}
	private void modifyArticleCatAndRefrsh(final String id){
		ArticleCatPanel.State newState = new ArticleCatPanel.State();
		newState.setIsEdit(true);
		newState.setPkId(id);
		newState.execute();
	}
	private void deleteArticleCatAndRefrsh(final String id){
		new DeleteService().deleteBean(ModelNames.ARTICLE_CAT, id, new DeleteService.Listener(){
			@Override
			public void onSuccess(Boolean success) {
				refresh();
			}
			
		});
	}
	TreeStore<BeanObject> store = new TreeStore<BeanObject>();
	protected void onRender(Element parent, int index) {
    	super.onRender(parent, index);

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
        ColumnConfig colName = new ColumnConfig(IArticleCat.CAT_NAME, Resources.constants.ArticleCat_col_name(), 150);
        colName.setRenderer(new TreeGridCellRenderer<BeanObject>());
        columns.add(colName);
        ColumnConfig colcatType = new ColumnConfig(IArticleCat.CAT_TYPE, Resources.constants.ArticleCat_col_type(), 150);
        colcatType.setRenderer(new GridCellRenderer<BeanObject>(){


			public Object render(BeanObject model, String property,
					ColumnData config, int rowIndex, int colIndex,
					ListStore store, Grid grid) {
				String html = "";
				if(model.getString(IArticleCat.CAT_TYPE).equals("1")){
					html = "普通分类";
				}
				else if(model.getString(IArticleCat.CAT_TYPE).equals("2")){
					html = "系统分类";
				}
				else if(model.getString(IArticleCat.CAT_TYPE).equals("3")){
					html = "信息分类";
				}
				else if(model.getString(IArticleCat.CAT_TYPE).equals("4")){
					html = "帮助分类";
				}
				else if(model.getString(IArticleCat.CAT_TYPE).equals("5")){
					html = "网店帮助";
				}
				return html;
			}
        	
        });
        columns.add(colcatType);
        
        columns.add(new ColumnConfig(IArticleCat.CAT_DESC, Resources.constants.ArticleCat_col_desc(), 200));        
        columns.add(new ColumnConfig(IArticleCat.SORT_ORDER, Resources.constants.ArticleCat_col_order(), 80));
        columns.add(new CheckColumnConfig(IArticleCat.SHOW_IN_NAV, Resources.constants.ArticleCat_col_showInNav(), 150) {
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
        grid.setAutoExpand(true);
        grid.setAutoExpandColumn(IArticleCat.CAT_NAME);


        ActionCellRenderer render = new ActionCellRenderer(grid){
        	public Boolean filtActs(BeanObject model,ActionCellRenderer.ActionInfo act){
        		String catType = model.getString(ArticleCatForm.CAT_TYPE);
        		if((catType.equals("2")||catType.equals("3")||catType.equals("4")) && act.getText().equals(Resources.constants.delete().toString())){
        			return true;
        		}
        		else {
        			return false;
        		}
        	}
        };
        ActionCellRenderer.ActionInfo act = new ActionCellRenderer.ActionInfo();        
        act.setText(Resources.constants.edit());
        act.setAction("changeArticleCat($pkId)");
        render.addAction(act);
        act = new ActionCellRenderer.ActionInfo();
        act.setText(Resources.constants.delete());
		act.setAction("deleteArticleCat($pkId)");
		act.setTooltip(Resources.constants.GoodsList_action_delete());
		render.addAction(act);
        actcol.setRenderer(render);        
        
        ContentPanel panel = new ContentPanel();
        panel.setFrame(true);
        panel.setCollapsible(true);
        panel.setAnimCollapse(false);
        panel.setIconStyle("icon-table");
        //panel.setHeading("Paging Grid");
        panel.setLayout(new FitLayout());
        panel.add(grid);
        panel.setHeight(350);
        add(panel);        
	}
	public Button getShortCutButton(){
		Button sButton = new Button(Resources.constants.ArticleCat_sbtn_add());
	      sButton.addSelectionListener(new SelectionListener<ButtonEvent>() {
	          public void componentSelected(ButtonEvent ce) {
	          	onShortCutButtonClicked();
	          }
	      });
	      return sButton;
	}
	public void onShortCutButtonClicked() {
		ArticleCatPanel.State newState = new ArticleCatPanel.State();
		newState.setIsEdit(false);
		newState.execute();
    }
	 public void refresh(){
		    Map<String,List<String>> wantedFields = new HashMap<String,List<String>>();
	        String model = ModelNames.ARTICLE_CAT;
	        List<String> fields = new ArrayList<String>();
	        fields.add(ArticleCatForm.PK_ID);
	        fields.add(ArticleCatForm.CAT_NAME);
	        fields.add(ArticleCatForm.CAT_TYPE);
	        fields.add(ArticleCatForm.CAT_DESC);
	        fields.add(ArticleCatForm.SHOW_IN_NAV);
	        fields.add(ArticleCatForm.KEYWORDS);
	        fields.add(ArticleCatForm.PARENT_ID);
	        fields.add(ArticleCatForm.TREE_PARENT_ID);
	        wantedFields.put(model, fields);
	    	new ListService().treeListBeans(ModelNames.ARTICLE_CAT,null,wantedFields,new ListService.Listener(){

				@Override
				public void onSuccess(List<BeanObject> beans) {
					System.out.println("========ArticleCat size = "+beans.size()+"====");
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
		return Resources.constants.ArticleCat_title();
	}

}
