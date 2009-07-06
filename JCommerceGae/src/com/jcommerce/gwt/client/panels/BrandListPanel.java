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
import com.extjs.gxt.ui.client.event.ButtonEvent;
import com.extjs.gxt.ui.client.event.SelectionListener;
import com.extjs.gxt.ui.client.store.ListStore;
import com.extjs.gxt.ui.client.store.Record;
import com.extjs.gxt.ui.client.store.StoreEvent;
import com.extjs.gxt.ui.client.store.StoreListener;
import com.extjs.gxt.ui.client.widget.ContentPanel;
import com.extjs.gxt.ui.client.widget.button.Button;
import com.extjs.gxt.ui.client.widget.grid.CheckColumnConfig;
import com.extjs.gxt.ui.client.widget.grid.ColumnConfig;
import com.extjs.gxt.ui.client.widget.grid.ColumnModel;
import com.extjs.gxt.ui.client.widget.grid.Grid;
import com.extjs.gxt.ui.client.widget.layout.FitLayout;
import com.extjs.gxt.ui.client.widget.toolbar.PagingToolBar;
import com.google.gwt.core.client.GWT;
import com.google.gwt.http.client.Request;
import com.google.gwt.http.client.RequestBuilder;
import com.google.gwt.http.client.RequestCallback;
import com.google.gwt.http.client.RequestException;
import com.google.gwt.http.client.Response;
import com.google.gwt.http.client.URL;
import com.google.gwt.user.client.Element;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.ListBox;
import com.jcommerce.gwt.client.ContentWidget;
import com.jcommerce.gwt.client.ModelNames;
import com.jcommerce.gwt.client.PageState;
import com.jcommerce.gwt.client.form.BeanObject;
import com.jcommerce.gwt.client.form.GWTHttpDynaForm;
import com.jcommerce.gwt.client.model.IBrand;
import com.jcommerce.gwt.client.resources.Resources;
import com.jcommerce.gwt.client.service.PagingListService;
import com.jcommerce.gwt.client.widgets.ActionCellRenderer;
import com.jcommerce.gwt.client.widgets.ColumnPanel;

/**
 * Example file.
 */
public class BrandListPanel extends ContentWidget {    
	
	ColumnPanel contentPanel = new ColumnPanel();
    ListBox b_list = new ListBox();    
    Button btnAdd = new Button("添加品牌");
    ListBox lstAction = new ListBox();
    Button btnAct = new Button("OK");
    PagingToolBar toolBar;    
    
	public static class State extends PageState {
		public String getPageClassName() {
			return BrandListPanel.class.getName();
		}
		public String getMenuDisplayName() {
			return "商品品牌";
		}
	}
	
    // leon to integrate with history-based page navigation mechnism. 
    // State should contain all info needed to render this page.
    // This is a minimum skeleton, more fields may be added, see leontest.Attribute
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
        return "商品品牌";
    }
    
    @Override
    public Button getShortCutButton() {
//    	return btnAdd;
      Button buttonAddClone = new Button("添加品牌");
      buttonAddClone.addSelectionListener(new SelectionListener<ButtonEvent>() {
          public void componentSelected(ButtonEvent ce) {
          	onButtonAddClicked();
          }
      });
      return buttonAddClone;
    }
    
    private static BrandListPanel instance;
    public static BrandListPanel getInstance() {
    	if(instance == null) {
    		instance = new BrandListPanel();
    	}
    	return instance;
    }
    /**
     * Initialize this example.
     */
    private BrandListPanel() {
        System.out.println("----------BrandListPanel");
        add(contentPanel);        
        initJS(this);         
    }
    
    protected void onRender(Element parent, int index) {
    	super.onRender(parent, index);

        BasePagingLoader loader = new PagingListService().getLoader(ModelNames.BRAND);

//        loader.load(0, 50);
    	
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
        columns.add(new ColumnConfig(IBrand.NAME, "品牌名称", 80));
        columns.add(new ColumnConfig(IBrand.SITE, "品牌网址", 150));
        columns.add(new ColumnConfig(IBrand.DESC, "品牌描述", 230));
        columns.add(new ColumnConfig(IBrand.ORDER, "排序", 50));
        columns.add(new CheckColumnConfig(IBrand.SHOW, "是否显示", 80));
        ColumnConfig actcol = new ColumnConfig("Action", "操作", 140);
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
        act.setAction("changeBrand($id)");
        act.setTooltip(Resources.constants.edit());
        render.addAction(act);
        act = new ActionCellRenderer.ActionInfo();
        act.setText(" 删除");
		act.setAction("deleteBrand($id)");
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
//        panel.setSize(750, 350);
        panel.setSize("100%", "350px");
        panel.setBottomComponent(toolBar);     
        
        panel.setButtonAlign(HorizontalAlignment.CENTER);
        btnAdd.addSelectionListener(new SelectionListener<ButtonEvent>() {
            public void componentSelected(ButtonEvent ce) {
            	onButtonAddClicked();
            }
        });
        

        panel.addButton(btnAdd);
        
        add(panel);       
    }    
    
    public void onButtonAddClicked() {
		BrandPanel.State newState = new BrandPanel.State();
		newState.setIsEdit(false);
		newState.execute();
    }
    
    private native void initJS(BrandListPanel me) /*-{
    $wnd.changeBrand = function (id) {
        me.@com.jcommerce.gwt.client.panels.BrandListPanel::modifyBrandAndRefrsh(Ljava/lang/String;)(id);
    };
    $wnd.deleteBrand = function (id) {
	    me.@com.jcommerce.gwt.client.panels.BrandListPanel::deleteBrandAndRefrsh(Ljava/lang/String;)(id);
	};
    }-*/;
    
    private void modifyBrandAndRefrsh(final String id) {    
		BrandPanel.State newState = new BrandPanel.State();
		newState.setIsEdit(true);
		newState.setId(id);
		newState.execute();
    }
    
    private void deleteBrandAndRefrsh(final String id) {
    	GWTHttpDynaForm form = new GWTHttpDynaForm();
    	form.setAction("com.jcommerce.gwt.server.BrandGWTAction");
    	form.setMethod("delete");
    	form.addParam(IBrand.ID, id);
    	form.SetListener(new GWTHttpDynaForm.Listener() {
			@Override
			public void onFailure(Throwable caught) {
				Window.alert("error: "+caught.getMessage());
			}
			@Override
			public void onSuccess(String response) {
				toolBar.refresh();
			}
    	});
    	form.submit();

    }
    public void refresh(){
    	System.out.println("refresh..");
    	toolBar.refresh();
    }
}
