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

import com.extjs.gxt.ui.client.Style.HorizontalAlignment;
import com.extjs.gxt.ui.client.event.BaseEvent;
import com.extjs.gxt.ui.client.event.ButtonEvent;
import com.extjs.gxt.ui.client.event.Events;
import com.extjs.gxt.ui.client.event.FieldEvent;
import com.extjs.gxt.ui.client.event.FormEvent;
import com.extjs.gxt.ui.client.event.Listener;
import com.extjs.gxt.ui.client.event.SelectionListener;
import com.extjs.gxt.ui.client.store.ListStore;
import com.extjs.gxt.ui.client.util.Margins;
import com.extjs.gxt.ui.client.util.Padding;
import com.extjs.gxt.ui.client.widget.LayoutContainer;
import com.extjs.gxt.ui.client.widget.TabItem;
import com.extjs.gxt.ui.client.widget.TabPanel;
import com.extjs.gxt.ui.client.widget.button.Button;
import com.extjs.gxt.ui.client.widget.form.AdapterField;
import com.extjs.gxt.ui.client.widget.form.CheckBox;
import com.extjs.gxt.ui.client.widget.form.CheckBoxGroup;
import com.extjs.gxt.ui.client.widget.form.ComboBox;
import com.extjs.gxt.ui.client.widget.form.DateField;
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
import com.extjs.gxt.ui.client.widget.layout.FitLayout;
import com.extjs.gxt.ui.client.widget.layout.FormData;
import com.extjs.gxt.ui.client.widget.layout.FormLayout;
import com.extjs.gxt.ui.client.widget.layout.VBoxLayout;
import com.extjs.gxt.ui.client.widget.layout.VBoxLayoutData;
import com.extjs.gxt.ui.client.widget.layout.VBoxLayout.VBoxLayoutAlign;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.HTML;
import com.jcommerce.gwt.client.ModelNames;
import com.jcommerce.gwt.client.form.BeanObject;
import com.jcommerce.gwt.client.form.BrandForm;
import com.jcommerce.gwt.client.form.GWTHttpDynaForm;
import com.jcommerce.gwt.client.form.GoodsForm;
import com.jcommerce.gwt.client.model.IBrand;
import com.jcommerce.gwt.client.model.IGoods;
import com.jcommerce.gwt.client.panels.BaseEntityEditPanel;
import com.jcommerce.gwt.client.panels.GoodsListPanel;
import com.jcommerce.gwt.client.panels.Success;
import com.jcommerce.gwt.client.resources.Resources;
import com.jcommerce.gwt.client.service.CreateService;
import com.jcommerce.gwt.client.service.ListService;

public class GoodsPanel extends BaseEntityEditPanel implements Listener{
    
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
    	 
    ListStore<BeanObject> brandList;
    ComboBox<BeanObject> fListBrand;
    
    ListStore<BeanObject> catgoryList;
    ListField<BeanObject> fListCategory;
    
    
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
			}

			@Override
			public void onSuccess(String id) {
				brand.set(IBrand.PK_ID, id);
				brandList.add(brand);
			}
    	} 
    	);	

    }
    
    public void onCalShopPriceClicked() {
    	Number marketPrice = nfMarketPrice.getValue();
    	Number shopPrice = nfShopPrice.getValue();
    	
        NumberField nfPromotePrice;
        DateField dfPromoteStartDate;
        DateField dfPromoteEndDate;
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
	public void handleEvent(BaseEvent be) {
		if(be instanceof FieldEvent) {
			FieldEvent fe = (FieldEvent)be;
			if( fe.getField() == cbIsPromote) {
				Boolean b1 = (Boolean)fe.getValue();
				Boolean b2 = (Boolean)fe.getOldValue();
				boolean isChecked = cbIsPromote.getValue();
				System.out.println("b1="+b1+", b2="+b2+", isChecked="+isChecked);
				onIsPromoteClicked();
			}
		}
		
	}
	
	
    @Override
    public void setupPanelLayout() {
        FormData formData = new FormData("90%");
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
        
        TextField<String> fText = GoodsForm.getNameField(Resources.constants.Goods_name());
        fText.setMaxLength(20);
        fText.setFieldLabel(Resources.constants.Goods_name());

        
        contentPanelGeneral.add(fText, formData);
        
        fText = GoodsForm.getSnField();
        fText.setMaxLength(20);
        fText.setFieldLabel(Resources.constants.Goods_SN());
        fText.setToolTip("如果您不输入商品货号，系统将自动生成一个唯一的货号。");
        
        contentPanelGeneral.add(fText, formData);
        
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
        
        Button button = new Button("添加品牌");
        AdapterField af = new AdapterField(button);
        af.setHideLabel(true);
        mfBrand.add(af);
        button.addSelectionListener(      		
        	new SelectionListener<ButtonEvent>() {
        		public void componentSelected(ButtonEvent sender) {
        			onNewBrandClicked();
        		}
        	});
        
        TextField<String> fBrandName = BrandForm.getNameField("品牌名称");
        fBrandName.setMessageTarget("tooltip");
        fBrandName.setHideLabel(true);
        fBrandNameAd = new AdapterField(fBrandName);
        fBrandNameAd.setVisible(false);
        mfBrand.add(fBrandNameAd);
        
        Button bOkNewBrand = new Button("确定");
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
        
        contentPanelGeneral.add(mfBrand);
        

        
        fListCategory = GoodsForm.getCategoryIdsField();
        fListCategory.setFieldLabel(Resources.constants.Goods_category());
        catgoryList = new ListStore<BeanObject>();
        fListCategory.setStore(catgoryList);
        fListCategory.setEmptyText("Select one or more Categories...");   
        fListCategory.setWidth(150);   
        contentPanelGeneral.add(fListCategory);
        
        
        MultiField mfShopPrice = new MultiField();
        mfShopPrice.setFieldLabel("ShopPrice");
        
        nfShopPrice = GoodsForm.getShopPriceField();
        nfShopPrice.setHideLabel(true);
        mfShopPrice.add(nfShopPrice);
        
        Button bCalShopPrice = new Button("根据市场价计算");
        AdapterField bCalShopPriceAd = new AdapterField(bCalShopPrice);
        bCalShopPriceAd.setHideLabel(true);
        mfShopPrice.add(bCalShopPriceAd);
        bCalShopPrice.addSelectionListener(      		
            	new SelectionListener<ButtonEvent>() {
            		public void componentSelected(ButtonEvent sender) {
            			onCalShopPriceClicked();
            		}
            	});
        
        contentPanelGeneral.add(mfShopPrice);
        
        nfMarketPrice = GoodsForm.getMarketPriceField();
        nfMarketPrice.setFieldLabel("MarketPrice");
        contentPanelGeneral.add(nfMarketPrice); 
        
        NumberField fNum = GoodsForm.getGiveIntegralField();
        fNum.setFieldLabel("？赠送消费积分数");
        fNum.setToolTip("购买该商品时赠送消费积分数,-1表示按商品价格赠送");
        contentPanelGeneral.add(fNum); 
        
        fNum = GoodsForm.getRankIntegralField();
        fNum.setFieldLabel("？赠送等级积分数");
        fNum.setToolTip("购买该商品时赠送等级积分数,-1表示按商品价格赠送");
        contentPanelGeneral.add(fNum); 
        
        fNum = GoodsForm.getRankIntegralField();
        fNum.setFieldLabel("？积分购买额度");
        fNum.setToolTip("购买该商品时最多可以使用多少钱的积分");
        contentPanelGeneral.add(fNum); 
        
        MultiField mfPromote = new MultiField();
        mfPromote.setFieldLabel("促销");
        
        cbIsPromote = GoodsForm.getIsPromoteField();
        cbIsPromote.setHideLabel(true);
        cbIsPromote.setValueAttribute("true");
        mfPromote.add(cbIsPromote);
        
        nfPromotePrice = GoodsForm.getPromotePriceField();
        nfPromotePrice.setFieldLabel("促销价格");
        nfPromotePrice.setEnabled(false);
        mfPromote.add(nfPromotePrice);
        contentPanelGeneral.add(mfPromote); 
        
        
        MultiField mfPromote2 = new MultiField();
        mfPromote2.setFieldLabel("促销日期");
        
        dfPromoteStartDate = GoodsForm.getPromoteStartDateField();
        dfPromoteStartDate.setHideLabel(true);
        dfPromoteStartDate.setEnabled(false);
        mfPromote2.add(dfPromoteStartDate);
        
        mfPromote2.add(new LabelField(" - "));
        
        dfPromoteEndDate = GoodsForm.getPromoteEndDateField();
        dfPromoteEndDate.setHideLabel(true);
        dfPromoteEndDate.setEnabled(false);
        mfPromote2.add(dfPromoteEndDate);
        
        contentPanelGeneral.add(mfPromote2); 
        
//        field = GoodsForm.getImageField();
//        field.setFieldLabel(Resources.constants.Goods_image());
//        contentPanelGeneral.add(field, formData);
//        
//        field = GoodsForm.getThumbField();
//        field.setFieldLabel(Resources.constants.Goods_thumb());
//        contentPanelGeneral.add(field, formData);
        
        
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
        
		VBoxLayout vbl1 = new VBoxLayout();
		vbl1.setPadding(new Padding(5));  
//		vbl1.setVBoxLayoutAlign(VBoxLayoutAlign.LEFT);
		vbl1.setVBoxLayoutAlign(VBoxLayoutAlign.STRETCH);  
		contentPanelOther.setLayout(vbl1);
//        contentPanelOther.setLayout(new FormLayout());
        
//      contentPanelOther.createPanel(IGoods.WEIGHT, Resources.constants.Goods_weight(), new TextBox());
//      contentPanelOther.createPanel(IGoods.NUMBER, Resources.constants.Goods_number(), new TextBox());
//      contentPanelOther.createPanel(IGoods.WARNNUMBER, Resources.constants.Goods_warnNumber(), new TextBox());                
//      contentPanelOther.createPanel(IGoods.IS_HOT, Resources.constants.Goods_hotsold(), new CheckBox());
//      contentPanelOther.createPanel(IGoods.IS_NEW, Resources.constants.Goods_newAdded(), new CheckBox());
//      contentPanelOther.createPanel(IGoods.IS_BEST, Resources.constants.Goods_bestSold(), new CheckBox());        
//      contentPanelOther.createPanel(IGoods.BRIEF, Resources.constants.Goods_brief(), new TextArea());
//      contentPanelOther.createPanel(IGoods.SELLERNOTE, Resources.constants.Goods_sellerNote(), new TextArea());
		VBoxLayoutData vbld1 = new VBoxLayoutData(new Margins(0, 0, 5, 0)); 
		VBoxLayoutData vbld2 = new VBoxLayoutData(new Margins(0));
		LayoutContainer lc1 = new LayoutContainer();
		lc1.setLayout(new FormLayout());
        fText = GoodsForm.getNumberField(Resources.constants.Goods_number());
        fText.setMaxLength(10);
        fText.setFieldLabel(Resources.constants.Goods_number());
//        contentPanelOther.add(field, formData);
        lc1.add(fText, formData);
        contentPanelOther.add(lc1, vbld1);
        
        lc1 = new LayoutContainer();
		lc1.setLayout(new FormLayout());
        fText = GoodsForm.getWeightField(Resources.constants.Goods_weight());
        fText.setMaxLength(10);
        fText.setFieldLabel(Resources.constants.Goods_weight());
//        contentPanelOther.add(field, formData);
        lc1.add(fText, formData);
        contentPanelOther.add(lc1, vbld1);
        
//        lc1 = new LayoutContainer();
//		lc1.setLayout(new RowLayout());
//        lc1.add(new Button("testxxx"));
//        contentPanelOther.add(lc1, vbld1);
        

        // TODO warnnumber
//        lc1 = new LayoutContainer();
//        HBoxLayout hbl1 = new HBoxLayout();
//        hbl1.setPadding(new Padding(5));  
//        hbl1.setHBoxLayoutAlign(HBoxLayoutAlign.MIDDLE);
//        hbl1.setPack(BoxLayoutPack.CENTER);  
//		lc1.setLayout(hbl1);
////        lc1.setLayout(new TableLayout(3));
//		HBoxLayoutData layoutData = new HBoxLayoutData(new Margins(0, 5, 0, 0)); 
//		HBoxLayoutData layoutData2 = new HBoxLayoutData(new Margins(0));
//		lc1.add(new Button("test1"), layoutData);
//		lc1.add(new Button("test2"), layoutData);
//		lc1.add(new Button("test3"), layoutData);
//		lc1.add(new Button("test4"), layoutData2);
        
//      contentPanelOther.add(new Label("加入推荐"), vbld1);

        
        LayoutContainer lc2 = new LayoutContainer();
        lc2.setLayout(new FormLayout());
        
        CheckBoxGroup checkGroup = new CheckBoxGroup();
        checkGroup.setFieldLabel("加入推荐");
        
        CheckBox box = GoodsForm.getHotSoldField();
        box.setBoxLabel(Resources.constants.Goods_hotsold());
        checkGroup.add(box);

        box = GoodsForm.getNewAddedField();
        box.setBoxLabel(Resources.constants.Goods_newAdded());
        checkGroup.add(box);
        
        box = GoodsForm.getBestSoldField();
        box.setBoxLabel(Resources.constants.Goods_bestSold());
        checkGroup.add(box);
        
        lc2.add(checkGroup, new FormData("90%"));
		contentPanelOther.add(lc2, vbld2);
		contentPanelOther.layout();

        tabs.add(contentPanelOther);
        
        
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
		    	catgoryList.removeAll();
				catgoryList.add(beans);
				populateField(fListCategory);
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
        	// mannually render it.
        	// HTMLEditor has some initilizing work to do in render
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
//                	new ReadService().getBean(ModelNames.GOODS, getCurState().getPkId(),
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
