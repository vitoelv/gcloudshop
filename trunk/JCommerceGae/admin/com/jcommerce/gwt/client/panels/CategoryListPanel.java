/*
 * Copyright 2008 Google Inc.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package com.jcommerce.gwt.client.panels;

import java.util.ArrayList;
import java.util.List;

import com.extjs.gxt.ui.client.Style.HorizontalAlignment;
import com.extjs.gxt.ui.client.data.BasePagingLoader;
import com.extjs.gxt.ui.client.data.ModelData;
import com.extjs.gxt.ui.client.event.ButtonEvent;
import com.extjs.gxt.ui.client.event.SelectionListener;
import com.extjs.gxt.ui.client.store.ListStore;
import com.extjs.gxt.ui.client.store.Record;
import com.extjs.gxt.ui.client.store.StoreEvent;
import com.extjs.gxt.ui.client.store.StoreListener;
import com.extjs.gxt.ui.client.widget.ContentPanel;
import com.extjs.gxt.ui.client.widget.grid.CheckColumnConfig;
import com.extjs.gxt.ui.client.widget.grid.ColumnConfig;
import com.extjs.gxt.ui.client.widget.grid.ColumnModel;
import com.extjs.gxt.ui.client.widget.grid.Grid;
import com.extjs.gxt.ui.client.widget.layout.FitLayout;
import com.extjs.gxt.ui.client.widget.toolbar.PagingToolBar;
import com.google.gwt.user.client.Element;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.ListBox;
import com.jcommerce.gwt.client.ContentWidget;
import com.jcommerce.gwt.client.ModelNames;
import com.jcommerce.gwt.client.PageState;
import com.jcommerce.gwt.client.form.BeanObject;
import com.jcommerce.gwt.client.model.ICategory;
import com.jcommerce.gwt.client.resources.Resources;
import com.jcommerce.gwt.client.service.DeleteService;
import com.jcommerce.gwt.client.service.PagingListService;
import com.jcommerce.gwt.client.widgets.ActionCellRenderer;

/**
 * Example file.
 */
public class CategoryListPanel extends ContentWidget {    
	
//	ColumnPanel contentPanel = new ColumnPanel();
    ListBox b_list = new ListBox();    
    Button btnAdd = new Button("添加分类");
    ListBox lstAction = new ListBox();
    Button btnAct = new Button("OK");
    PagingToolBar toolBar;    
    /**
     * Initialize this example.
     */
    public static CategoryListPanel getInstance() {
    	if(instance==null) {
    		instance = new CategoryListPanel();
    	}
    	return instance;
    }
    private static CategoryListPanel instance;
    private CategoryListPanel() {
        System.out.println("----------CategoryInfo");
//        add(contentPanel);        
        initJS(this);   
    }
    // leon to integrate with history-based page navigation mechnism. 
    // State should contain all info needed to render this page.
    // This is a minimum skeleton, more fields may be added, see leontest.Attribute
	public static class State extends PageState {
		public String getPageClassName() {
			return CategoryListPanel.class.getName();
		}
		public String getMenuDisplayName() {
			return "商品分类";
		}
	}
	private State curState = new State();
	public State getCurState() {
		return curState;
	}
	public void setCurState(State curState) {
		this.curState = curState;
	}
	
    public String getDescription() {
        return "cwBasicTextDescription";
    }

    public String getName() {
        return "商品分类";
    }
    
    
    protected void onRender(Element parent, int index) {
    	super.onRender(parent, index);

        BasePagingLoader loader = new PagingListService().getLoader(ModelNames.CATEGORY);

        loader.load(0, 50);
    	
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
        columns.add(new ColumnConfig(ICategory.CAT_NAME, "分类名称", 150));
        columns.add(new ColumnConfig(ICategory.MEASURE_UNIT, "数量单位", 80));
        columns.add(new CheckColumnConfig(ICategory.SHOW_IN_NAV, "导航栏", 80) {
        	// TODO: wrap the code for Long type column into a baseclass
        	  protected String getCheckState(ModelData model, String property, int rowIndex,
        		      int colIndex) {
        		    Long v = model.get(property);
        		    // see IConstants.DBTYPE_TRUE
        		    String on = (v != null && v==1) ? "-on" : "";
        		    return on;
        		  }
        });
        columns.add(new CheckColumnConfig(ICategory.IS_SHOW, "是否显示", 80));        
        columns.add(new ColumnConfig(ICategory.GRADE, "价格分级", 60));
        columns.add(new ColumnConfig(ICategory.SORT_ORDER, "排序", 50));        
        ColumnConfig actcol = new ColumnConfig("Action", "操作", 150);
        columns.add(actcol);

        ColumnModel cm = new ColumnModel(columns);

        Grid<BeanObject> grid = new Grid<BeanObject>(store, cm);
        grid.setLoadMask(true);
        grid.setBorders(true);
        //grid.setSelectionModel(sm);
//        grid.setAutoExpandColumn("forum");


        ActionCellRenderer render = new ActionCellRenderer(grid);
        ActionCellRenderer.ActionInfo act = new ActionCellRenderer.ActionInfo();        
        act.setText("编辑 ");
        act.setAction("changeCategory($pkId)");
        render.addAction(act);
        act = new ActionCellRenderer.ActionInfo();
        act.setText(" 删除");
		act.setAction("deleteCategory($pkId)");
		act.setTooltip(Resources.constants.GoodsList_action_delete());
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
        panel.setBottomComponent(toolBar);
        
        panel.setButtonAlign(HorizontalAlignment.CENTER);
        panel.addButton(new com.extjs.gxt.ui.client.widget.button.Button("添加分类", new SelectionListener<ButtonEvent>() {
          public void componentSelected(ButtonEvent ce) {
//              JCommerceGae.getInstance().displayNewCategory();
        	  	CategoryPanel.State newState = new CategoryPanel.State();
				newState.setIsEdit(false);
				newState.execute();
          }
        }));
        
        add(panel);        
    }    
    
    private native void initJS(CategoryListPanel me) /*-{
    $wnd.changeCategory = function (id) {
        me.@com.jcommerce.gwt.client.panels.CategoryListPanel::modifyCategoryAndRefrsh(Ljava/lang/String;)(id);
    };
    $wnd.deleteCategory = function (id) {
	    me.@com.jcommerce.gwt.client.panels.CategoryListPanel::deleteCategoryAndRefrsh(Ljava/lang/String;)(id);
	};
    }-*/;
    
    private void modifyCategoryAndRefrsh(final String id) {    	
		CategoryPanel.State newState = new CategoryPanel.State();
		newState.setIsEdit(true);
		newState.setPkId(id);
//		newState.setSelectedParentID(getCurState().getSelectedGoodsTypeID());
		newState.execute();
		
//        final IDefaultServiceAsync service = getService();
//        service.getBean(ModelNames.CATEGORY, id, new AsyncCallback<BeanObject>() {
//            public synchronized void onSuccess(BeanObject result) {
//            	JCommerceGae.getInstance().displayModifyCategory(result);                               
//            }
//            public synchronized void onFailure(Throwable caught) {
//                System.out.println("list onFailure("+caught);                
//            }
//        });        
    }
    private void deleteCategoryAndRefrsh(final String id) {
        new DeleteService().deleteBean(ModelNames.CATEGORY, id,
				new DeleteService.Listener() {
					public void onSuccess(Boolean success) {
						toolBar.refresh();
					}
				});
    }
    public void refresh(){
    	toolBar.refresh();
    }

}
