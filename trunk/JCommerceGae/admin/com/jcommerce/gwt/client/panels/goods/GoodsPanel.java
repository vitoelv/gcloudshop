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

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.extjs.gxt.ui.client.event.ButtonEvent;
import com.extjs.gxt.ui.client.event.Events;
import com.extjs.gxt.ui.client.event.FieldEvent;
import com.extjs.gxt.ui.client.event.FormEvent;
import com.extjs.gxt.ui.client.event.Listener;
import com.extjs.gxt.ui.client.event.SelectionChangedEvent;
import com.extjs.gxt.ui.client.event.SelectionChangedListener;
import com.extjs.gxt.ui.client.event.SelectionListener;
import com.extjs.gxt.ui.client.store.ListStore;
import com.extjs.gxt.ui.client.widget.Info;
import com.extjs.gxt.ui.client.widget.TabItem;
import com.extjs.gxt.ui.client.widget.TabPanel;
import com.extjs.gxt.ui.client.widget.button.Button;
import com.extjs.gxt.ui.client.widget.form.AdapterField;
import com.extjs.gxt.ui.client.widget.form.CheckBox;
import com.extjs.gxt.ui.client.widget.form.CheckBoxGroup;
import com.extjs.gxt.ui.client.widget.form.ComboBox;
import com.extjs.gxt.ui.client.widget.form.DateField;
import com.extjs.gxt.ui.client.widget.form.DateTimePropertyEditor;
import com.extjs.gxt.ui.client.widget.form.Field;
import com.extjs.gxt.ui.client.widget.form.FileUploadField;
import com.extjs.gxt.ui.client.widget.form.FormPanel;
import com.extjs.gxt.ui.client.widget.form.HiddenField;
import com.extjs.gxt.ui.client.widget.form.HtmlEditor;
import com.extjs.gxt.ui.client.widget.form.LabelField;
import com.extjs.gxt.ui.client.widget.form.ListField;
import com.extjs.gxt.ui.client.widget.form.MultiField;
import com.extjs.gxt.ui.client.widget.form.NumberField;
import com.extjs.gxt.ui.client.widget.form.TextField;
import com.extjs.gxt.ui.client.widget.form.ComboBox.TriggerAction;
import com.extjs.gxt.ui.client.widget.layout.FormLayout;
import com.google.gwt.user.client.Window;
import com.jcommerce.gwt.client.ModelNames;
import com.jcommerce.gwt.client.form.BeanObject;
import com.jcommerce.gwt.client.form.BrandForm;
import com.jcommerce.gwt.client.form.GWTHttpDynaForm;
import com.jcommerce.gwt.client.form.GoodsForm;
import com.jcommerce.gwt.client.model.IBrand;
import com.jcommerce.gwt.client.model.IGoods;
import com.jcommerce.gwt.client.panels.BaseEntityEditPanel;
import com.jcommerce.gwt.client.panels.BaseFileUploadFormPanel;
import com.jcommerce.gwt.client.panels.Success;
import com.jcommerce.gwt.client.resources.Resources;
import com.jcommerce.gwt.client.service.CreateService;
import com.jcommerce.gwt.client.service.ListService;
import com.jcommerce.gwt.client.util.GWTFormatUtils;

public class GoodsPanel extends BaseFileUploadFormPanel implements Listener<FieldEvent>{
    
    public static interface Constants {
        String NewGoods_title();
        String NewGoods_tabGeneral();
        String NewGoods_tabDetail();
        String NewGoods_tabOther();
        String NewGoods_tabProperty();
        String NewGoods_tabGallery();
        String NewGoods_tabLink();
        String NewGoods_tabAccessories();
        String NewGoods_tabArticle();
        String NewGoods_recommend();
        String NewGoods_promote();
        String NewGoods_calculateByMaretPrice();
        String NewGoods_onSaleOrNot();
        String NewGoods_imageDescription();
        String NewGoods_imageFile();
        String NewGoods_type();
        String NewGoods_tipSN();
        String NewGoods_tipGiveIntegral();
        String NewGoods_tipRankIntegral();
        String NewGoods_tipIntegral();
        String NewGoods_editGoods();
        String NewGoods_addSuccessfully();
        
    }
    	 
    ListStore<BeanObject> brandList;
    ComboBox<BeanObject> fListBrand;
    
    ListStore<BeanObject> categoryList;
    ComboBox<BeanObject> cbListCategory;
    ListField<BeanObject> lfListCategory;
    
    
    AdapterField fBrandNameAd;
    AdapterField bOkNewBrandAd;
    AdapterField bHideNewBrandAd;
    
    NumberField nfShopPrice;
    NumberField nfMarketPrice;
    
    CheckBox cbIsPromote;
    NumberField nfPromotePrice;
    DateField dfPromoteStartDate;
    DateField dfPromoteEndDate;
    
    HiddenField<String> idField;
    GalleryPanel4 contentPanelGallery;
    GoodsAttributePanel contentPanelAttrs;
    
    
    // leon to integrate with history-based page navigation mechanism. 
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
    
	private static GoodsPanel instance;
    private GoodsPanel() {       
    }
    public static GoodsPanel getInstance() {
    	if(instance == null) {
    		instance = new GoodsPanel();
    	}
    	return instance;
    }
    
    @Override
    public void gotoSuccessPanel() {
    	Success.State newState = new Success.State();
    	//newState.setMessage("编辑商品类型成功");
    	newState.setMessage(Resources.constants.NewGoods_addSuccessfully());
    	
    	GoodsListPanel.State choice1 = new GoodsListPanel.State();
    	newState.addChoice(GoodsListPanel.getInstance().getName(), choice1.getFullHistoryToken());
    	
    	newState.execute();
    }
    @Override
    public Button getShortCutButton() {
//    	return btnAdd;
      Button buttonAddClone = new Button(Resources.constants.GoodsList_title());
      buttonAddClone.addSelectionListener(new SelectionListener<ButtonEvent>() {
          public void componentSelected(ButtonEvent ce) {
          	onButtonListClicked();
          }
      });
      return buttonAddClone;
    }
    public void onButtonListClicked() {
		GoodsListPanel.State newState = new GoodsListPanel.State();
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
        	return Resources.constants.NewGoods_editGoods();
    }
    
    public void onNewBrandClicked() {
    	fBrandNameAd.setVisible(true);
        bOkNewBrandAd.setVisible(true);
        bHideNewBrandAd.setVisible(true);
    }
    public void onHideNewBrandClicked() {
    	fBrandNameAd.setVisible(false);
        bOkNewBrandAd.setVisible(false);
        bHideNewBrandAd.setVisible(false);
    }
    
    public void onOkNewBrandClicked() {
    	TextField<String> tf = (TextField<String>)fBrandNameAd.getWidget();
    	String name = tf.getValue();
    	System.out.println("name="+name);
    	if(!tf.validate()) {
    		return;
    	}

    	
    	final BeanObject brand = new BeanObject(ModelNames.BRAND);
    	brand.set(IBrand.BRAND_NAME, name);
    	new CreateService().createBean(brand, new CreateService.Listener() {
			@Override
			public void onFailure(Throwable caught) {
				super.onFailure(caught);
				Info.display("Ooops", "Adding new brand failed. cause: "+caught.getMessage());
			}

			@Override
			public void onSuccess(String id) {
				brand.set(IBrand.PK_ID, id);
				brandList.add(brand);
				Info.display("Great", "New brand added");
			}
    	} 
    	);	

    }
    
    public void onCalShopPriceClicked() {
    	Number marketPrice = nfMarketPrice.getValue();
    	Number shopPrice = nfShopPrice.getValue();
    	
    	if(marketPrice!=null) {
    		nfShopPrice.setValue(marketPrice.doubleValue()*0.8);
    	}
    	else if(shopPrice!=null) {
    		nfMarketPrice.setValue(shopPrice.doubleValue()/0.8);
    	}
    	
    	
    }
    public void onIsPromoteClicked() {
    	boolean isChecked = cbIsPromote.getValue();
    	if(isChecked) {
    		nfPromotePrice.setEnabled(true);
    		dfPromoteStartDate.setEnabled(true);
    		dfPromoteEndDate.setEnabled(true);
    	}
    	else {
    		nfPromotePrice.setEnabled(false);
    		dfPromoteStartDate.setEnabled(false);
    		dfPromoteEndDate.setEnabled(false);
    	}
    	
    	
    }
	public void handleEvent(FieldEvent be) {
		FieldEvent fe = (FieldEvent)be;
		if( fe.getField() == cbIsPromote) {
			Boolean b1 = (Boolean)fe.getValue();
			Boolean b2 = (Boolean)fe.getOldValue();
			boolean isChecked = cbIsPromote.getValue();
			System.out.println("b1="+b1+", b2="+b2+", isChecked="+isChecked);
			onIsPromoteClicked();
		}
		
	}
	
	
    @Override
    public void setupPanelLayout() {

//        formPanel.setSize(800, 400);
    	formPanel.setPadding(0);
//    	formPanel.setFrame(false);
    	formPanel.setHeaderVisible(false);
//    	formPanel.setBodyBorder(false);

//    	formPanel.setLayout(new FitLayout());
    	
//    	formPanel.setLayout(new TableLayout(1));
    	
//        formPanel.setWidth(800);
        TabPanel tabs = new TabPanel();
//        tabs.setWidth(800);
//        tabs.setAutoHeight(true);
//        FormData fd = new FormData("100%");
//        fd.setWidth(800);
//        formPanel.add(tabs, fd);
//        formPanel.add(tabs);
//        TableData td = new TableData();
//        td.setWidth("100%");
//        formPanel.add(tabs,td);
//        formPanel.layout();
        
        formPanel.add(tabs);
        
        setupGeneralPanel(tabs);
        setupDetailPanel(tabs);
        setupOtherPanel(tabs);
        
        contentPanelGallery = new GalleryPanel4(this);     
        contentPanelGallery.setStyleAttribute("padding", "10px");
        contentPanelGallery.setText(Resources.constants.NewGoods_tabGallery());
        
        tabs.add(contentPanelGallery);
                
        contentPanelAttrs = new GoodsAttributePanel(this);
        contentPanelAttrs.setStyleAttribute("padding", "10px");
        contentPanelAttrs.setText(Resources.constants.NewGoods_tabProperty());
        tabs.add(contentPanelAttrs);
        
        // below as a sample of adding listener
//        contentPanelAttrs.addListener(Events.Select, new Listener<TabPanelEvent>(){
//        	  public void handleEvent(TabPanelEvent be)
//        	  {
//        	    MessageBox.alert("Test", be.item.getText(), null);
//        	  }
//        	});
        

     
    }
	private void setupOtherPanel(TabPanel tabs) {
		FormLayout fl = getFormLayout();
		NumberField fNum;
		TabItem contentPanelOther = new TabItem();
        contentPanelOther.setStyleAttribute("padding", "10px");
        contentPanelOther.setText(Resources.constants.NewGoods_tabOther());
        
		contentPanelOther.setLayout(fl);
//        contentPanelOther.setLayout(new FormLayout());
        
//      contentPanelOther.createPanel(IGoods.WEIGHT, Resources.constants.Goods_weight(), new TextBox());
//      contentPanelOther.createPanel(IGoods.NUMBER, Resources.constants.Goods_number(), new TextBox());
//      contentPanelOther.createPanel(IGoods.WARNNUMBER, Resources.constants.Goods_warnNumber(), new TextBox());                
//      contentPanelOther.createPanel(IGoods.IS_HOT, Resources.constants.Goods_hotsold(), new CheckBox());
//      contentPanelOther.createPanel(IGoods.IS_NEW, Resources.constants.Goods_newAdded(), new CheckBox());
//      contentPanelOther.createPanel(IGoods.IS_BEST, Resources.constants.Goods_bestSold(), new CheckBox());        
//      contentPanelOther.createPanel(IGoods.BRIEF, Resources.constants.Goods_brief(), new TextArea());
//      contentPanelOther.createPanel(IGoods.SELLERNOTE, Resources.constants.Goods_sellerNote(), new TextArea());
        fNum = GoodsForm.getNumberField(Resources.constants.Goods_number());
        fNum.setMaxLength(10);
        fNum.setFieldLabel(Resources.constants.Goods_number());
//        contentPanelOther.add(field, formData);
        contentPanelOther.add(fNum, sfd());
        
        fNum = GoodsForm.getWeightField(Resources.constants.Goods_weight());
        fNum.setMaxLength(10);
        fNum.setFieldLabel(Resources.constants.Goods_weight());
        contentPanelOther.add(fNum, sfd());
        
        CheckBoxGroup checkGroup = new CheckBoxGroup();
        checkGroup.setFieldLabel(Resources.constants.NewGoods_recommend());
        
        CheckBox box = GoodsForm.getHotSoldField();
        box.setBoxLabel(Resources.constants.Goods_hotsold());
        checkGroup.add(box);

        box = GoodsForm.getNewAddedField();
        box.setBoxLabel(Resources.constants.Goods_newAdded());
        checkGroup.add(box);
        
        box = GoodsForm.getBestSoldField();
        box.setBoxLabel(Resources.constants.Goods_bestSold());
        checkGroup.add(box);
        
		contentPanelOther.add(checkGroup, lfd());

		
        box = GoodsForm.getIsOnSaleField();
        box.setFieldLabel(Resources.constants.Goods_onSale());
        box.setBoxLabel(Resources.constants.NewGoods_onSaleOrNot());
        contentPanelOther.add(box, sfd());
		
		
        tabs.add(contentPanelOther);
	}
	
	public FormLayout getFormLayout() {
        FormLayout fl = new FormLayout();
        fl.setLabelWidth(150);
        fl.setLabelPad(50);
        return fl;
	}

	
	private void setupDetailPanel(TabPanel tabs) {
		
		FormLayout fl = getFormLayout();
        
		TabItem contentPanelDetail = new TabItem();
        contentPanelDetail.setStyleAttribute("padding", "10px");
        contentPanelDetail.setText(Resources.constants.NewGoods_tabDetail());
        contentPanelDetail.setLayout(fl);
        
        // Create the text area and toolbar
        HtmlEditor area = GoodsForm.getDescField(); 
        area.setHideLabel(true);
        area.setHeight(300);
        contentPanelDetail.add(area, lfd());

        tabs.add(contentPanelDetail);
	}
	private void setupGeneralPanel(TabPanel tabs) {
		FormLayout fl = getFormLayout();
		
        TabItem contentPanelGeneral = new TabItem();
        contentPanelGeneral.setStyleAttribute("padding", "10px");
        contentPanelGeneral.setText(Resources.constants.NewGoods_tabGeneral());

        contentPanelGeneral.setLayout(fl);
        
		idField = GoodsForm.getIdField();
        contentPanelGeneral.add(idField);
        
        TextField<String> fText = GoodsForm.getNameField(Resources.constants.Goods_name());
        fText.setMaxLength(20);
        fText.setFieldLabel(Resources.constants.Goods_name());
        contentPanelGeneral.add(fText, sfd());
        

        fText = GoodsForm.getSnField();
        fText.setMaxLength(20);
        fText.setFieldLabel(Resources.constants.Goods_SN());
        fText.setToolTip(Resources.constants.NewGoods_tipSN());
        
        contentPanelGeneral.add(fText, sfd());
        
        
        // category and extended categories
        categoryList = new ListStore<BeanObject>();
        
        cbListCategory = GoodsForm.getCatIdField();
        cbListCategory.setFieldLabel(Resources.constants.Goods_category());
        cbListCategory.setStore(categoryList);
        cbListCategory.setEmptyText("");
        cbListCategory.setWidth(150);
        contentPanelGeneral.add(cbListCategory);
        
        
        lfListCategory = GoodsForm.getCategoryIdsField();
        lfListCategory.setFieldLabel(Resources.constants.Goods_category_extended());
        
        lfListCategory.setStore(categoryList);
        lfListCategory.setEmptyText("Select one or more Categories...");   
        lfListCategory.setWidth(150);   
        lfListCategory.addSelectionChangedListener(new SelectionChangedListener<BeanObject>() {
        	// TODO however this won't work
			@Override
			public void selectionChanged(SelectionChangedEvent<BeanObject> se) {
				Info.display("ah-oh", "CLICKED");
				
			}
        	
        });
        contentPanelGeneral.add(lfListCategory);
        
        
        brandList = new ListStore<BeanObject>();
        fListBrand = GoodsForm.getBrandIdField();
        fListBrand.setHideLabel(true);
        fListBrand.setStore(brandList);

        MultiField mfBrand = new MultiField();
        mfBrand.setFieldLabel(Resources.constants.Goods_brand());
        
        fListBrand.setEmptyText("Select a Brand...");   
        fListBrand.setWidth(150);   
        fListBrand.setTypeAhead(true);   
        fListBrand.setTriggerAction(TriggerAction.ALL);  
        
        mfBrand.add(fListBrand);
        
        Button button = new Button(Resources.constants.NewBrand_title());
        AdapterField af = new AdapterField(button);
        af.setHideLabel(true);
        mfBrand.add(af);
        button.addSelectionListener(      		
        	new SelectionListener<ButtonEvent>() {
        		public void componentSelected(ButtonEvent sender) {
        			onNewBrandClicked();
        		}
        	});
        
        TextField<String> fBrandName = BrandForm.getNameField(Resources.constants.NewBrand_name());
        fBrandName.setMessageTarget("tooltip");
        fBrandName.setHideLabel(true);
        fBrandNameAd = new AdapterField(fBrandName);
        fBrandNameAd.setVisible(false);
        mfBrand.add(fBrandNameAd);
        
        Button bOkNewBrand = new Button(Resources.constants.ok());
        bOkNewBrandAd = new AdapterField(bOkNewBrand);
        bOkNewBrandAd.setHideLabel(true);
        bOkNewBrandAd.setVisible(false);
        mfBrand.add(bOkNewBrandAd);
        bOkNewBrand.addSelectionListener(      		
            	new SelectionListener<ButtonEvent>() {
            		public void componentSelected(ButtonEvent sender) {
            			onOkNewBrandClicked();
            		}
            	});
        
        
        Button bHideNewBrand = new Button("<<");
        bHideNewBrandAd = new AdapterField(bHideNewBrand);
        bHideNewBrandAd.setHideLabel(true);
        bHideNewBrandAd.setVisible(false);
        mfBrand.add(bHideNewBrandAd);
//        
        bHideNewBrand.addSelectionListener(      		
            	new SelectionListener<ButtonEvent>() {
            		public void componentSelected(ButtonEvent sender) {
            			onHideNewBrandClicked();
            		}
            	});
        
        contentPanelGeneral.add(mfBrand, lfd());
        

        

        
        
        MultiField mfShopPrice = new MultiField();
        mfShopPrice.setFieldLabel(Resources.constants.Goods_shopPrice());
        
        nfShopPrice = GoodsForm.getShopPriceField();
        nfShopPrice.setHideLabel(true);
        mfShopPrice.add(nfShopPrice);
        
        Button bCalShopPrice = new Button(Resources.constants.NewGoods_calculateByMaretPrice());
        AdapterField bCalShopPriceAd = new AdapterField(bCalShopPrice);
        bCalShopPriceAd.setHideLabel(true);
        mfShopPrice.add(bCalShopPriceAd);
        bCalShopPrice.addSelectionListener(      		
            	new SelectionListener<ButtonEvent>() {
            		public void componentSelected(ButtonEvent sender) {
            			onCalShopPriceClicked();
            		}
            	});
        
        contentPanelGeneral.add(mfShopPrice, lfd());
        
        nfMarketPrice = GoodsForm.getMarketPriceField();
        nfMarketPrice.setFieldLabel(Resources.constants.Goods_marketPrice());
        contentPanelGeneral.add(nfMarketPrice, sfd()); 
        
        NumberField fNum = GoodsForm.getGiveIntegralField();
        fNum.setFieldLabel("？"+Resources.constants.Goods_giveIntegral());
        fNum.setToolTip(Resources.constants.NewGoods_tipGiveIntegral());
        contentPanelGeneral.add(fNum, sfd()); 
        
        fNum = GoodsForm.getRankIntegralField();
        fNum.setFieldLabel("？"+Resources.constants.Goods_rankIntegral());
        fNum.setToolTip(Resources.constants.NewGoods_tipRankIntegral());
        contentPanelGeneral.add(fNum, sfd()); 
        
        fNum = GoodsForm.getRankIntegralField();
        fNum.setFieldLabel("？"+Resources.constants.Goods_integral());
        fNum.setToolTip(Resources.constants.NewGoods_tipIntegral());
        contentPanelGeneral.add(fNum, sfd()); 
        
//        MultiField mfPromote = new MultiField();
//        mfPromote.setFieldLabel("促销");
        
        cbIsPromote = GoodsForm.getIsPromoteField();
        cbIsPromote.setValueAttribute("true");
        cbIsPromote.addListener(Events.Change, this);
//      cbIsPromote.setHideLabel(true);
        cbIsPromote.setFieldLabel(Resources.constants.NewGoods_promote());        
//        mfPromote.add(cbIsPromote);
        contentPanelGeneral.add(cbIsPromote, tfd());
        
        
        nfPromotePrice = GoodsForm.getPromotePriceField();
        nfPromotePrice.setFieldLabel(Resources.constants.Goods_promotePrice());
        nfPromotePrice.setEnabled(false);
//        mfPromote.add(nfPromotePrice);
//        contentPanelGeneral.add(mfPromote, lfd()); 
        contentPanelGeneral.add(nfPromotePrice, tfd());
        
        MultiField mfPromote2 = new MultiField();
        mfPromote2.setFieldLabel(Resources.constants.Goods_promoteDate());
        
        dfPromoteStartDate = GoodsForm.getPromoteStartDateField();
        dfPromoteStartDate.setPropertyEditor(new DateTimePropertyEditor(GWTFormatUtils.shortDateFormat()));
        dfPromoteStartDate.setHideLabel(true);
        dfPromoteStartDate.setEnabled(false);
        mfPromote2.add(dfPromoteStartDate);
        
        mfPromote2.add(new LabelField(" - "));
        
        dfPromoteEndDate = GoodsForm.getPromoteEndDateField();
        dfPromoteEndDate.setPropertyEditor(new DateTimePropertyEditor(GWTFormatUtils.shortDateFormat()));
        dfPromoteEndDate.setHideLabel(true);
        dfPromoteEndDate.setEnabled(false);
        mfPromote2.add(dfPromoteEndDate);
        
        contentPanelGeneral.add(mfPromote2, lfd()); 
        
//        field = GoodsForm.getImageField();
//        field.setFieldLabel(Resources.constants.Goods_image());
//        contentPanelGeneral.add(field, formData);
//        
//        field = GoodsForm.getThumbField();
//        field.setFieldLabel(Resources.constants.Goods_thumb());
//        contentPanelGeneral.add(field, formData);

        tabs.add(contentPanelGeneral);
	}
    
    @Override
    public void postSuperRefresh() {
    	// let them run parallelly 
    	new ListService().listBeans(ModelNames.BRAND, new ListService.Listener() {
        	// TODO need only Id and Name, rather than all fields of Brand/Category
    		public void onSuccess(List<BeanObject> beans) {
    	    	brandList.removeAll();
    			brandList.add(beans);
    			populateField(fListBrand);
    		}
    	});
    	
		new ListService().listBeans(ModelNames.CATEGORY, new ListService.Listener() {
			@Override
			public void onSuccess(List<BeanObject> beans) {
		    	categoryList.removeAll();
				categoryList.add(beans);
				populateField(cbListCategory);
				populateField(lfListCategory);
			}
		});
		
    	String action="com.jcommerce.gwt.server.GoodsGWTAction";
    	String method="";
    	
    	if(getCurState().getIsEdit()) {
    		method = "update";
    		idField.setValue(getCurState().getPkId());
    		BeanObject bo = getEntity();
    		String goodsTypeId = bo.getString(IGoods.GOODS_TYPE_ID);
    		contentPanelGallery.refresh(getCurState().getPkId());
    		contentPanelAttrs.refresh(getCurState().getPkId(), goodsTypeId);
    	}else {
    		method = "add";
    		idField.setValue(null);
    		contentPanelGallery.refresh(null);
    		contentPanelAttrs.refresh(null, null);
    	}
    	
    	System.out.println("method="+method);
    	formPanel.setAction(GWTHttpDynaForm.constructURL(action, method));
    }

    @Override
    protected void submit() {
    	
        try {
        	// Manually render it.
        	// HTMLEditor has some initializing work to do in render
        	// submit without clicking into detail tab
//        	area.render(contentPanelDetail.getElement(), -1);
        } catch (Exception ex) {
        	ex.printStackTrace();
        }
    	
//    	System.out.println("before Submit(): bestSoldField="+bestSoldField+", action="+formPanel.getAction());
//    	Boolean value = bestSoldField.getValue();
//    	System.out.println("bestSoldField value: "+value);
    	
    	// before Submit
    	Set<Field<?>> clonedFields = new HashSet<Field<?>>();	
    	for(Field<?> field:dynaFields) {
    		Object val = field.getValue();
    		String valStr = val==null? "": val.toString();
    		
    		if(field instanceof FileUploadField) {
    			FileUploadField file = new FileUploadField();
    			file.setName(field.getName());
    			file.setValue(valStr);
    			clonedFields.add(file);
    		} else {
    			HiddenField<String> f1 = new HiddenField<String>();
				f1.setName(field.getName());
				f1.setValue(valStr);
				clonedFields.add(f1);
    		}
    	}
    	for(Field<?> field:clonedFields) {
    		System.out.println("name: "+field.getName()+", value: "+field.getValue());
    		formPanel.add(field);
    		
    	}
    	
    	formPanel.submit();

    	// after submit
    	for(Field<?> field:clonedFields) {
    		formPanel.remove(field);
    	}
    	dynaFields.clear();
    	clonedFields.clear();
    	
    }
    public void addDynaField(Field<?> field) {
    	dynaFields.add(field);
    }
    public void removeDynaField(Field<?> field) {
    	dynaFields.remove(field);
    }
    Set<Field<?>> dynaFields = new HashSet<Field<?>>();

}
