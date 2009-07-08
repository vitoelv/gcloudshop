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

import java.util.List;

import com.extjs.gxt.ui.client.Style.HorizontalAlignment;
import com.extjs.gxt.ui.client.event.Events;
import com.extjs.gxt.ui.client.event.FormEvent;
import com.extjs.gxt.ui.client.event.Listener;
import com.extjs.gxt.ui.client.store.ListStore;
import com.extjs.gxt.ui.client.widget.TabItem;
import com.extjs.gxt.ui.client.widget.TabPanel;
import com.extjs.gxt.ui.client.widget.form.CheckBox;
import com.extjs.gxt.ui.client.widget.form.ComboBox;
import com.extjs.gxt.ui.client.widget.form.FormPanel;
import com.extjs.gxt.ui.client.widget.form.HiddenField;
import com.extjs.gxt.ui.client.widget.form.HtmlEditor;
import com.extjs.gxt.ui.client.widget.form.ListField;
import com.extjs.gxt.ui.client.widget.form.TextField;
import com.extjs.gxt.ui.client.widget.form.ComboBox.TriggerAction;
import com.extjs.gxt.ui.client.widget.layout.FitLayout;
import com.extjs.gxt.ui.client.widget.layout.FormData;
import com.extjs.gxt.ui.client.widget.layout.FormLayout;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.HTML;
import com.jcommerce.gwt.client.ModelNames;
import com.jcommerce.gwt.client.form.BeanObject;
import com.jcommerce.gwt.client.form.GWTHttpDynaForm;
import com.jcommerce.gwt.client.form.GoodsForm;
import com.jcommerce.gwt.client.panels.BaseEntityEditPanel;
import com.jcommerce.gwt.client.panels.GoodsListPanel;
import com.jcommerce.gwt.client.panels.Success;
import com.jcommerce.gwt.client.resources.Resources;
import com.jcommerce.gwt.client.service.ListService;

public class GoodsPanel extends BaseEntityEditPanel {
    
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
    
//	private ColumnPanel contentPanelGeneral = new ColumnPanel();
//    private ColumnPanel contentPanelOther = new ColumnPanel();
    private AttributePanel attrPanel = new AttributePanel();
    private GalleryPanel galleryPanel = new GalleryPanel();
	 
    ListStore<BeanObject> brandList;
    ListStore<BeanObject> catgoryList;

    HiddenField<String> idField;
    CheckBox bestSoldField;
    
    // leon to integrate with history-based page navigation mechnism. 
    // State should contain all info needed to render this page.
    // This is a minimum skeleton, more fields may be added, see leontest.Attribute
	public static class State extends BaseEntityEditPanel.State {
		
		public String getPageClassName() {
			return GoodsPanel.class.getName();
		}
		public String getMenuDisplayName() {
			return Resources.constants.NewGoods_title();
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
    
    @Override
    public void gotoSuccessPanel() {
    	Success.State newState = new Success.State();
    	newState.setMessage("编辑商品类型成功");
    	
    	GoodsListPanel.State choice1 = new GoodsListPanel.State();
    	newState.addChoice(GoodsListPanel.getInstance().getName(), choice1.getFullHistoryToken());
    	
    	newState.execute();
    }
    
    @Override
    public String getEntityClassName() {
    	return ModelNames.GOODS;
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

    
    @Override
    public void setupPanelLayout() {
        FormData formData = new FormData("100%");
        formPanel.setSize(800, 400);
    	formPanel.setPadding(0);
    	formPanel.setFrame(false);
    	formPanel.setHeaderVisible(false);
    	formPanel.setBodyBorder(false);
    	formPanel.setButtonAlign(HorizontalAlignment.CENTER);
    	formPanel.setLayout(new FitLayout());
        
        TabPanel tabs = new TabPanel();

        TabItem contentPanelGeneral = new TabItem();
        contentPanelGeneral.setStyleAttribute("padding", "10px");
        contentPanelGeneral.setText(Resources.constants.NewGoods_tabGeneral());
        contentPanelGeneral.setLayout(new FormLayout());

        idField = GoodsForm.getIdField();
        contentPanelGeneral.add(idField, formData);
        
        TextField<String> field = GoodsForm.getNameField(Resources.constants.Goods_name());
        field.setMaxLength(20);
        field.setFieldLabel(Resources.constants.Goods_name());
        
        contentPanelGeneral.add(field, formData);
        
        field = GoodsForm.getSnField();
        field.setMaxLength(20);
        field.setFieldLabel(Resources.constants.Goods_SN());
        contentPanelGeneral.add(field, formData);
        
        brandList = new ListStore<BeanObject>();
        ComboBox<BeanObject> lstBrand = GoodsForm.getBrandIdField();
        lstBrand.setFieldLabel(Resources.constants.Goods_brand());
        lstBrand.setStore(brandList);

        lstBrand.setEmptyText("Select a Brand...");   
        lstBrand.setWidth(150);   
        lstBrand.setTypeAhead(true);   
        lstBrand.setTriggerAction(TriggerAction.ALL);   
        contentPanelGeneral.add(lstBrand);
        
        
        ListField<BeanObject> listCategory = GoodsForm.getCategoryIdsField();
        listCategory.setFieldLabel(Resources.constants.Goods_category());
        catgoryList = new ListStore<BeanObject>();
        listCategory.setStore(catgoryList);
        listCategory.setEmptyText("Select one or more Categories...");   
        listCategory.setWidth(150);   
        contentPanelGeneral.add(listCategory);
        
        field = GoodsForm.getImageField();
        field.setFieldLabel(Resources.constants.Goods_image());
        contentPanelGeneral.add(field, formData);
        
        field = GoodsForm.getThumbField();
        field.setFieldLabel(Resources.constants.Goods_thumb());
        contentPanelGeneral.add(field, formData);
        
//    	contentPanelGeneral.createPanel(IGoods.NAME, Resources.constants.Goods_name(), new TextBox());
//    	contentPanelGeneral.createPanel(IGoods.SN, Resources.constants.Goods_SN(), new TextBox());
        
//    	contentPanelGeneral.createPanel(IGoods.BRANDID, Resources.constants.Goods_brand(), lstBrand);
        
//        MultiValueSelector mselector = new MultiValueSelector();
//        mselector.setBean(ModelNames.CATEGORY);
//        mselector.setCaption("Select Category");
//        mselector.setMessage("Select Category");
//        contentPanelGeneral.createPanel(IGoods.CATEGORYIDS, Resources.constants.Goods_category(), mselector);
//        contentPanelGeneral.createPanel(IGoods.SHOPPRICE, Resources.constants.Goods_shopPrice(), new TextBox());
//        contentPanelGeneral.createPanel(IGoods.MARKETPRICE, Resources.constants.Goods_marketPrice(), new TextBox());
//        contentPanelGeneral.createPanel(IGoods.GIVEINTEGRAL, Resources.constants.Goods_giveIntegral(), new TextBox());
//        contentPanelGeneral.createPanel(IGoods.INTEGRAL, Resources.constants.Goods_integral(), new TextBox());
        
//        contentPanelGeneral.createPanel(IGoods.PROMOTEPRICE, Resources.constants.Goods_promotePrice(), new TextBox());
//        final FileUploader imageUpload = new FileUploader();
//        imageUpload.addAllowedTypes(new String[]{".jpg", ".gif"});
//        contentPanelGeneral.createPanel(IGoods.IMAGE, Resources.constants.Goods_image(), imageUpload);
//        final FileUploader thumbUpload = new FileUploader();
//        thumbUpload.addAllowedTypes(new String[]{".jpg", ".gif"});
//        contentPanelGeneral.createPanel(IGoods.THUMB, Resources.constants.Goods_thumb(), thumbUpload);
        tabs.add(contentPanelGeneral);
        
        
        
        TabItem contentPanelDetail = new TabItem();
        contentPanelDetail.setStyleAttribute("padding", "10px");
        contentPanelDetail.setText(Resources.constants.NewGoods_tabDetail());
        contentPanelDetail.setLayout(new FormLayout());
        
        // Create the text area and toolbar
        HtmlEditor area = GoodsForm.getDescField(); 
        area.setFieldLabel("Descritpion");
        area.setHeight(300);
//        area.setSize("100%", "14em");
//        RichTextToolbar toolbar = new RichTextToolbar(area);        
//        toolbar.setWidth("100%");

//        contentPanelDetail.add(toolbar);
        contentPanelDetail.add(area, formData);
        
        tabs.add(contentPanelDetail);
        
        

        
        
        TabItem contentPanelOther = new TabItem();
        contentPanelOther.setStyleAttribute("padding", "10px");
        contentPanelOther.setText(Resources.constants.NewGoods_tabOther());
        contentPanelOther.setLayout(new FormLayout());
        
//      contentPanelOther.createPanel(IGoods.WEIGHT, Resources.constants.Goods_weight(), new TextBox());
//      contentPanelOther.createPanel(IGoods.NUMBER, Resources.constants.Goods_number(), new TextBox());
//      contentPanelOther.createPanel(IGoods.WARNNUMBER, Resources.constants.Goods_warnNumber(), new TextBox());                
//      contentPanelOther.createPanel(IGoods.HOTSOLD, Resources.constants.Goods_hotsold(), new CheckBox());
//      contentPanelOther.createPanel(IGoods.NEWADDED, Resources.constants.Goods_newAdded(), new CheckBox());
//      contentPanelOther.createPanel(IGoods.BESTSOLD, Resources.constants.Goods_bestSold(), new CheckBox());        
//      contentPanelOther.createPanel(IGoods.BRIEF, Resources.constants.Goods_brief(), new TextArea());
//      contentPanelOther.createPanel(IGoods.SELLERNOTE, Resources.constants.Goods_sellerNote(), new TextArea());
        
        field = GoodsForm.getWeightField(Resources.constants.Goods_weight());
        field.setMaxLength(10);
        field.setFieldLabel(Resources.constants.Goods_weight());
        contentPanelOther.add(field, formData);
        
        field = GoodsForm.getNumberField(Resources.constants.Goods_number());
        field.setMaxLength(10);
        field.setFieldLabel(Resources.constants.Goods_number());
        contentPanelOther.add(field, formData);
        
        // TODO warnnumber
        
        CheckBox box = GoodsForm.getHotSoldField();
        box.setFieldLabel(Resources.constants.Goods_hotsold());
        contentPanelOther.add(field, formData);
        
        box = GoodsForm.getNewAddedField();
        field.setFieldLabel(Resources.constants.Goods_newAdded());
        contentPanelOther.add(field, formData);
        
        bestSoldField = GoodsForm.getBestSoldField();
        bestSoldField.setFieldLabel(Resources.constants.Goods_bestSold());
        contentPanelOther.add(bestSoldField, formData);
        
        tabs.add(contentPanelOther);
        
        formPanel.add(tabs);
        
        
        formPanel.setEncoding(FormPanel.Encoding.MULTIPART);
        formPanel.setMethod(FormPanel.Method.POST);
        formPanel.addListener(Events.Submit, new Listener<FormEvent>() {
			public void handleEvent(FormEvent be) {
				// TODO Auto-generated method stub
				String result = be.getResultHtml();
				if("0".equals(result)) {
					gotoSuccessPanel();
				}
				else {
					Window.alert("Error: "+result);	
				}
	
			} 
        });
        

                
//        HorizontalPanel panel = new HorizontalPanel();
//        panel.setSpacing(10);
//        btnOK.setText("确定");
//        btnCancel.setText("重置");
//        panel.add(btnOK);
//        panel.add(btnCancel);    
        
        // Create a tab panel
//        DecoratedTabPanel tabPanel = new DecoratedTabPanel();
//        tabPanel.setWidth("100%");        
//        tabPanel.setAnimationEnabled(true);
        
        
        // Add a home tab      


//        tabPanel.add(contentPanelGeneral, );



        // Add a detail tab   
        HTML properties2 = new HTML("properites");


//        tabPanel.add( grid, Resources.constants.NewGoods_tabDetail());

        
        
        // Add a other tab        
//        tabPanel.add(contentPanelOther, Resources.constants.NewGoods_tabOther());

        // Add a Properties tab
//        tabPanel.add(attrPanel, Resources.constants.NewGoods_tabProperty());
        
        // Add a Pictures tab
//        tabPanel.add(galleryPanel, Resources.constants.NewGoods_tabGallery());
        
        // Add a Connet other goods tab
        HTML conngoods = new HTML("connect goods");
//        tabPanel.add(conngoods, Resources.constants.NewGoods_tabLink());
        
        // Add a Accessories tab
        HTML accessories = new HTML("accessories");
//        tabPanel.add(accessories, Resources.constants.NewGoods_tabAccessories());
        
        // Add a Connet articles tab
        HTML articles = new HTML("articles");
//        tabPanel.add(articles, Resources.constants.NewGoods_tabArticle());
        
        // Return the content
//        tabPanel.selectTab(0);
//        tabPanel.ensureDebugId("cwTabPanel");        
//        add(tabPanel);
//    	add(panel);        
       
//        btnOK.addClickHandler(new ClickHandler() {
//            public void onClick(ClickEvent event) {
//                if (!imageUpload.submit()) {
//                    return;
//                }
//                if (!thumbUpload.submit()) {
//                    return;
//                }
//               
//                new WaitService(new WaitService.Job() {
//                    public boolean isReady() {
//                        return imageUpload.isFinish() && thumbUpload.isFinish();
//                    }
//
//                    public void run() {
//                    	Date currentTime = new Date();
//                    	Map<String, Object> argsLeft = contentPanelGeneral.getValues();
//                        Map<String, Object> argsRight = contentPanelOther.getValues();
//                        Map<String, Object> argsAttrs = attrPanel.getValues();
//
//                        argsLeft.putAll(argsRight);
//                        argsLeft.putAll(argsAttrs);
//                        argsLeft.put(IGoods.ADDTIME, currentTime);//addTime information
//                        if(getCurState().getIsEdit()){                        	
//                        	new UpdateService().updateBean(getCurState().getGoodsId(), new BeanObject(ModelNames.GOODS, argsLeft), 
//                        			new UpdateService.Listener() {
//
//										@Override
//										public void onFailure(Throwable caught) {
//											// TODO Auto-generated method stub
//											super.onFailure(caught);
//										}
//
//										@Override
//										public void onSuccess(Boolean success) {
//				                        	Success.State newState = new Success.State();
//				                        	newState.setMessage("编辑商品类型成功");
//				                        	
//				                        	GoodsListPanel.State choice1 = new GoodsListPanel.State();
//				                        	newState.addChoice(GoodsListPanel.getInstance().getName(), choice1.getFullHistoryToken());
//				                        	
//				                        	newState.execute();
//										}
//                        		
//                        	});
////                            editting = false;
////                            JCommerceGae.getInstance().displayGoodsList();
////                            Info.display("恭喜", "完成修改商品信息.");
//                        }
//                        else{
//                        	new CreateService().createBean(new BeanObject(ModelNames.GOODS, argsLeft), new CreateService.Listener() {
//                                public synchronized void onSuccess(String id) {
//                                    System.out.println("new onSuccess( "+id);                                                   
//                                    
//                                	Success.State newState = new Success.State();
//                                	newState.setMessage("添加商品类型成功");
//                                	
//                                	GoodsListPanel.State choice1 = new GoodsListPanel.State();
//                                	newState.addChoice(GoodsListPanel.getInstance().getName(), choice1.getFullHistoryToken());
//                                	
//                                	newState.execute();
//                                    
//                                }
//                                });
////                        	JCommerceGae.getInstance().displayGoodsList();
////                            Info.display("恭喜", "完成添加新商品.");
//                        }
//                    }
//                });
//            }            
//        });
//        
//        btnCancel.addClickHandler(new ClickHandler() {
//            public void onClick(ClickEvent event) {
//            	contentPanelGeneral.clearValues(); 
//            	contentPanelOther.clearValues();
//            	attrPanel.updateValues(null);
//            }
//        });  
    }
    
 
    public void refresh() {    	
    	new ListService().listBeans(ModelNames.BRAND, new ListService.Listener() {       
    		public void onSuccess(List<BeanObject> beans) {
    	    	brandList.removeAll();
    			brandList.add(beans);
    			new ListService().listBeans(ModelNames.CATEGORY, new ListService.Listener() {
					@Override
					public void onSuccess(List<BeanObject> beans) {
				    	catgoryList.removeAll();
						catgoryList.add(beans);
						superrefresh();
					}
    			});
    		}
    	});
    }
    public void superrefresh() {
    	super.refresh();
    	String action="com.jcommerce.gwt.server.GoodsGWTAction";
    	String method="";
    	
    	if(getCurState().getIsEdit()) {
    		method = "update";
    		idField.setValue(getCurState().getId());
    	}else {
    		method = "add";
    		idField.setValue(null);
    	}
    	formPanel.setAction(GWTHttpDynaForm.constructURL(action, method));
    }
    @Override
    protected void submit() {
    	System.out.println("before Submit(): bestSoldField="+bestSoldField);
    	Boolean value = bestSoldField.getValue();
    	System.out.println("bestSoldField value: "+value);
    	formPanel.submit();
    }
//    	lstBrand.clear();
//        
//        new ListService().listBeans(ModelNames.BRAND, new ListService.Listener() {       
//            public void onSuccess(List<BeanObject> beans) {
//            	lstBrand.insertItem("请选择。。。", null, 0);
//            	int i=1;
//                for (Iterator<BeanObject> it = beans.iterator(); it.hasNext();) {
//                	BeanObject brand = it.next();              
//                    lstBrand.insertItem(brand.getString(IBrand.NAME), brand.getString(IBrand.ID),i);
//                    i++;
//
//                }               
//                contentPanelGeneral.clearValues();
//                if(getCurState().getIsEdit()) {
//                	new ReadService().getBean(ModelNames.GOODS, getCurState().getId(),
//        				new ReadService.Listener() {
//                		public void onSuccess(BeanObject bean) {
//                			Map<String, Object> mapAttribute = bean.getProperties();
//                			contentPanelGeneral.updateValues(mapAttribute);
//                			contentPanelOther.updateValues(mapAttribute);
//                		}
//                	});
//                }
//            }
//        });
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
//    }
}
