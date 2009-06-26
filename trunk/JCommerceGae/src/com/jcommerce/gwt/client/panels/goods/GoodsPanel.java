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
package com.jcommerce.gwt.client.panels.goods;

import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.extjs.gxt.ui.client.widget.Info;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Element;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.DecoratedTabPanel;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.RichTextArea;
import com.google.gwt.user.client.ui.TextArea;
import com.google.gwt.user.client.ui.TextBox;
import com.jcommerce.gwt.client.ContentWidget;
import com.jcommerce.gwt.client.JCommerceGae;
import com.jcommerce.gwt.client.ModelNames;
import com.jcommerce.gwt.client.PageState;
import com.jcommerce.gwt.client.form.AttributeForm;
import com.jcommerce.gwt.client.form.BeanObject;
import com.jcommerce.gwt.client.model.IBrand;
import com.jcommerce.gwt.client.model.IGoods;
import com.jcommerce.gwt.client.panels.AttributeListPanel;
import com.jcommerce.gwt.client.panels.GoodsListPanel;
import com.jcommerce.gwt.client.panels.Success;
import com.jcommerce.gwt.client.resources.Resources;
import com.jcommerce.gwt.client.service.CreateService;
import com.jcommerce.gwt.client.service.ListService;
import com.jcommerce.gwt.client.service.ReadService;
import com.jcommerce.gwt.client.service.UpdateService;
import com.jcommerce.gwt.client.service.WaitService;
import com.jcommerce.gwt.client.widgets.ColumnPanel;
import com.jcommerce.gwt.client.widgets.FileUploader;
import com.jcommerce.gwt.client.widgets.MultiValueSelector;
import com.jcommerce.gwt.client.widgets.richTextBox.RichTextToolbar;

public class GoodsPanel extends ContentWidget {
    
    public static interface Constants {
        String NewGoods_title();
        String NewGoods_create();
        String NewGoods_cancel();
        String NewGoods_tabGeneral();
        String NewGoods_tabDetail();
        String NewGoods_tabOther();
        String NewGoods_tabProperty();
        String NewGoods_tabGallery();
        String NewGoods_tabLink();
        String NewGoods_tabAccessories();
        String NewGoods_tabArticle();
    }
    
	private ColumnPanel contentPanelGeneral = new ColumnPanel();
    private ColumnPanel contentPanelOther = new ColumnPanel();
    private AttributePanel attrPanel = new AttributePanel();
    private GalleryPanel galleryPanel = new GalleryPanel();
	 
    private ListBox lstBrand = new ListBox();
    private Button btnOK = new Button();
    private Button btnCancel = new Button();

//    private String goodsId = null; 
   // private HashMap<String, BeanObject> mapGoods = new HashMap<String, BeanObject>();
    
//    private boolean editting = false;
//    private BeanObject goods = null;
    
    
    // leon to integrate with history-based page navigation mechnism. 
    // State should contain all info needed to render this page.
    // This is a minimum skeleton, more fields may be added, see leontest.Attribute
	public static class State extends PageState {
		public static final String ISEDIT = "isedit";
		public static final String GOODS_ID = "gid";
		
		public String getPageClassName() {
			return GoodsPanel.class.getName();
		}
		public String getMenuDisplayName() {
			return Resources.constants.NewGoods_title();
		}
		public void setIsEdit(boolean isEdit){
			setValue(ISEDIT, String.valueOf(isEdit));
		}
		public boolean getIsEdit() {
			return Boolean.valueOf((String)getValue(ISEDIT)).booleanValue();
		}
		public String getGoodsId() {
			return (String)getValue(GOODS_ID);
		}
		public void setGoodsId(String gid) {
			setValue(GOODS_ID, gid);
		}
		
	}
	private State curState = new State();
	public State getCurState() {
		return curState;
	}
	public void setCurState(State curState) {
		this.curState = curState;
	}
    
    /**
     * Initialize this example.
     */
    public GoodsPanel() {       
    }
    
    public String getDescription() {
        return "cwBasicTextDescription";
    }

    public String getName() {
    	if(!getCurState().getIsEdit())            
    		return Resources.constants.NewGoods_title();
        else
        	return "编辑商品信息";
    }

//    public void setGoods(BeanObject goods) {
//        this.goods = goods;
//        this.goodsId = goods != null ? goods.getString(IGoods.ID) : null;
//        editting = goods != null;
//    }
    
    protected void onRender(Element parent, int index) {
        super.onRender(parent, index);
       
    	contentPanelGeneral.createPanel(IGoods.NAME, Resources.constants.Goods_name(), new TextBox());
    	contentPanelGeneral.createPanel(IGoods.SN, Resources.constants.Goods_SN(), new TextBox());
        
    	contentPanelGeneral.createPanel(IGoods.BRANDID, Resources.constants.Goods_brand(), lstBrand);
        
        MultiValueSelector mselector = new MultiValueSelector();
        mselector.setBean(ModelNames.CATEGORY);
        mselector.setCaption("Select Category");
        mselector.setMessage("Select Category");
        contentPanelGeneral.createPanel(IGoods.CATEGORYIDS, Resources.constants.Goods_category(), mselector);
        contentPanelGeneral.createPanel(IGoods.SHOPPRICE, Resources.constants.Goods_shopPrice(), new TextBox());
        contentPanelGeneral.createPanel(IGoods.MARKETPRICE, Resources.constants.Goods_marketPrice(), new TextBox());
        contentPanelGeneral.createPanel(IGoods.GIVEINTEGRAL, Resources.constants.Goods_giveIntegral(), new TextBox());
        contentPanelGeneral.createPanel(IGoods.INTEGRAL, Resources.constants.Goods_integral(), new TextBox());
        
        contentPanelGeneral.createPanel(IGoods.PROMOTEPRICE, Resources.constants.Goods_promotePrice(), new TextBox());
        final FileUploader imageUpload = new FileUploader();
        imageUpload.addAllowedTypes(new String[]{".jpg", ".gif"});
        contentPanelGeneral.createPanel(IGoods.IMAGE, Resources.constants.Goods_image(), imageUpload);
        final FileUploader thumbUpload = new FileUploader();
        thumbUpload.addAllowedTypes(new String[]{".jpg", ".gif"});
        contentPanelGeneral.createPanel(IGoods.THUMB, Resources.constants.Goods_thumb(), thumbUpload);
        
        contentPanelOther.createPanel(IGoods.WEIGHT, Resources.constants.Goods_weight(), new TextBox());
        contentPanelOther.createPanel(IGoods.NUMBER, Resources.constants.Goods_number(), new TextBox());
        contentPanelOther.createPanel(IGoods.WARNNUMBER, Resources.constants.Goods_warnNumber(), new TextBox());                
        contentPanelOther.createPanel(IGoods.HOTSOLD, Resources.constants.Goods_hotsold(), new CheckBox());
        contentPanelOther.createPanel(IGoods.NEWADDED, Resources.constants.Goods_newAdded(), new CheckBox());
        contentPanelOther.createPanel(IGoods.BESTSOLD, Resources.constants.Goods_bestSold(), new CheckBox());        
        contentPanelOther.createPanel(IGoods.BRIEF, Resources.constants.Goods_brief(), new TextArea());
        contentPanelOther.createPanel(IGoods.SELLERNOTE, Resources.constants.Goods_sellerNote(), new TextArea());
                
        HorizontalPanel panel = new HorizontalPanel();
        panel.setSpacing(10);
        btnOK.setText("确定");
        btnCancel.setText("重置");
        panel.add(btnOK);
        panel.add(btnCancel);    
        
        // Create a tab panel
        DecoratedTabPanel tabPanel = new DecoratedTabPanel();
        tabPanel.setWidth("100%");        
        tabPanel.setAnimationEnabled(true);
        
        
        // Add a home tab      
       
        tabPanel.add(contentPanelGeneral, Resources.constants.NewGoods_tabGeneral());

        // Create the text area and toolbar
        RichTextArea area = new RichTextArea();       
        area.setSize("100%", "14em");
        RichTextToolbar toolbar = new RichTextToolbar(area);        
        toolbar.setWidth("100%");

        //Add the components to a panel
        Grid grid = new Grid(2, 1);
        grid.setStyleName("cw-RichText");
        grid.setWidget(0, 0, toolbar);
        grid.setWidget(1, 0, area);

        // Add a detail tab   
        HTML properties2 = new HTML("properites");
        tabPanel.add( grid, Resources.constants.NewGoods_tabDetail());

        // Add a other tab        
        tabPanel.add(contentPanelOther, Resources.constants.NewGoods_tabOther());

        // Add a Properties tab
        tabPanel.add(attrPanel, Resources.constants.NewGoods_tabProperty());
        
        // Add a Pictures tab
        tabPanel.add(galleryPanel, Resources.constants.NewGoods_tabGallery());
        
        // Add a Connet other goods tab
        HTML conngoods = new HTML("connect goods");
        tabPanel.add(conngoods, Resources.constants.NewGoods_tabLink());
        
        // Add a Accessories tab
        HTML accessories = new HTML("accessories");
        tabPanel.add(accessories, Resources.constants.NewGoods_tabAccessories());
        
        // Add a Connet articles tab
        HTML articles = new HTML("articles");
        tabPanel.add(articles, Resources.constants.NewGoods_tabArticle());
        
        // Return the content
        tabPanel.selectTab(0);
        tabPanel.ensureDebugId("cwTabPanel");        
        add(tabPanel);
    	add(panel);        
       
        btnOK.addClickHandler(new ClickHandler() {
            public void onClick(ClickEvent event) {
                if (!imageUpload.submit()) {
                    return;
                }
                if (!thumbUpload.submit()) {
                    return;
                }
               
                new WaitService(new WaitService.Job() {
                    public boolean isReady() {
                        return imageUpload.isFinish() && thumbUpload.isFinish();
                    }

                    public void run() {
                    	Date currentTime = new Date();
                    	Map<String, Object> argsLeft = contentPanelGeneral.getValues();
                        Map<String, Object> argsRight = contentPanelOther.getValues();
                        Map<String, Object> argsAttrs = attrPanel.getValues();

                        argsLeft.putAll(argsRight);
                        argsLeft.putAll(argsAttrs);
                        argsLeft.put(IGoods.ADDTIME, currentTime);//addTime information
                        if(getCurState().getIsEdit()){                        	
                        	new UpdateService().updateBean(getCurState().getGoodsId(), new BeanObject(ModelNames.GOODS, argsLeft), 
                        			new UpdateService.Listener() {

										@Override
										public void onFailure(Throwable caught) {
											// TODO Auto-generated method stub
											super.onFailure(caught);
										}

										@Override
										public void onSuccess(Boolean success) {
				                        	Success.State newState = new Success.State();
				                        	newState.setMessage("编辑商品类型成功");
				                        	
				                        	GoodsListPanel.State choice1 = new GoodsListPanel.State();
				                        	newState.addChoice(GoodsListPanel.getInstance().getName(), choice1.getFullHistoryToken());
				                        	
				                        	newState.execute();
										}
                        		
                        	});
//                            editting = false;
//                            JCommerceGae.getInstance().displayGoodsList();
//                            Info.display("恭喜", "完成修改商品信息.");
                        }
                        else{
                        	new CreateService().createBean(new BeanObject(ModelNames.GOODS, argsLeft), new CreateService.Listener() {
                                public synchronized void onSuccess(String id) {
                                    System.out.println("new onSuccess( "+id);                                                   
                                    
                                	Success.State newState = new Success.State();
                                	newState.setMessage("添加商品类型成功");
                                	
                                	GoodsListPanel.State choice1 = new GoodsListPanel.State();
                                	newState.addChoice(GoodsListPanel.getInstance().getName(), choice1.getFullHistoryToken());
                                	
                                	newState.execute();
                                    
                                }
                                });
//                        	JCommerceGae.getInstance().displayGoodsList();
//                            Info.display("恭喜", "完成添加新商品.");
                        }
                    }
                });
            }            
        });
        
        btnCancel.addClickHandler(new ClickHandler() {
            public void onClick(ClickEvent event) {
            	contentPanelGeneral.clearValues(); 
            	contentPanelOther.clearValues();
            	attrPanel.updateValues(null);
            }
        });  
    }
    
 
    public void refresh() {    	
    	
    	lstBrand.clear();
        
        new ListService().listBeans(ModelNames.BRAND, new ListService.Listener() {       
            public void onSuccess(List<BeanObject> beans) {
            	lstBrand.insertItem("请选择。。。", null, 0);
            	int i=1;
                for (Iterator<BeanObject> it = beans.iterator(); it.hasNext();) {
                	BeanObject brand = it.next();              
                    lstBrand.insertItem(brand.getString(IBrand.NAME), brand.getString(IBrand.ID),i);
                    i++;

                }               
                contentPanelGeneral.clearValues();
                if(getCurState().getIsEdit()) {
                	new ReadService().getBean(ModelNames.GOODS, getCurState().getGoodsId(),
        				new ReadService.Listener() {
                		public void onSuccess(BeanObject bean) {
                			Map<String, Object> mapAttribute = bean.getProperties();
                			contentPanelGeneral.updateValues(mapAttribute);
                			contentPanelOther.updateValues(mapAttribute);
                		}
                	});
                }
            }
        });
//        if (this.goods!=null&&this.goods.getString(ICategory.ID) != null) {
//            contentPanelGeneral.updateValues(goods.getProperties());
//            contentPanelOther.updateValues(goods.getProperties());
//            attrPanel.updateValues(goods);
//            this.goods = null;
//        }
//        else{       	
//        	contentPanelGeneral.clearValues(); 
//        	contentPanelOther.clearValues();
//        	attrPanel.updateValues(null);
//        	editting = false;
//        }
    }
}
