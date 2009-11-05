package com.jcommerce.gwt.client.panels.system;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.extjs.gxt.ui.client.Style.VerticalAlignment;
import com.extjs.gxt.ui.client.data.ListLoadResult;
import com.extjs.gxt.ui.client.event.ButtonEvent;
import com.extjs.gxt.ui.client.event.SelectionChangedEvent;
import com.extjs.gxt.ui.client.event.SelectionChangedListener;
import com.extjs.gxt.ui.client.event.SelectionListener;
import com.extjs.gxt.ui.client.store.ListStore;
import com.extjs.gxt.ui.client.widget.Component;
import com.extjs.gxt.ui.client.widget.Info;
import com.extjs.gxt.ui.client.widget.LayoutContainer;
import com.extjs.gxt.ui.client.widget.Text;
import com.extjs.gxt.ui.client.widget.button.Button;
import com.extjs.gxt.ui.client.widget.form.CheckBox;
import com.extjs.gxt.ui.client.widget.form.ComboBox;
import com.extjs.gxt.ui.client.widget.form.Field;
import com.extjs.gxt.ui.client.widget.form.FieldSet;
import com.extjs.gxt.ui.client.widget.form.TextField;
import com.extjs.gxt.ui.client.widget.layout.FormLayout;
import com.extjs.gxt.ui.client.widget.layout.TableData;
import com.extjs.gxt.ui.client.widget.layout.TableLayout;
import com.extjs.gxt.ui.client.widget.layout.TableRowLayout;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.jcommerce.gwt.client.ModelNames;
import com.jcommerce.gwt.client.form.BeanObject;
import com.jcommerce.gwt.client.model.IAreaRegion;
import com.jcommerce.gwt.client.model.IRegion;
import com.jcommerce.gwt.client.model.IShippingArea;
import com.jcommerce.gwt.client.panels.BaseEntityEditPanel;
import com.jcommerce.gwt.client.panels.Success;
import com.jcommerce.gwt.client.service.Condition;
import com.jcommerce.gwt.client.service.Criteria;
import com.jcommerce.gwt.client.service.ListService;
import com.jcommerce.gwt.client.service.RemoteService;
import com.jcommerce.gwt.client.util.FormUtils;
import com.jcommerce.gwt.client.util.MyListLoader;

public class ShippingAreaPanel extends BaseEntityEditPanel {
    public static class State extends BaseEntityEditPanel.State {
    	public static final String SHIPPING_ID = "SHIPPINGID";
    	public String getPageClassName() {
            return ShippingAreaPanel.class.getName();
        }
        public void setShippingId(String shippingId) {
        	setValue(SHIPPING_ID, shippingId);
        }
        public String getShippingId() {
        	return (String)getValue(SHIPPING_ID);
        }
    }
    private static ShippingAreaPanel instance;
    public static ShippingAreaPanel getInstance() {
        if(instance == null) {
            instance = new ShippingAreaPanel();
        }
        return instance;
    }
    private ShippingAreaPanel() {
    }
    private State curState = new State();
	@Override
	protected State getCurState() {
		return curState;
	}

	@Override
	protected String getEntityClassName() {
		return null;
	}

	@Override
	public void gotoSuccessPanel() {
		Success.State newState = new Success.State();
		if(getCurState().getIsEdit()) {
			newState.setMessage("编辑配送区域成功");
		}else {
			newState.setMessage("添加配送区域成功");
		}
    	
    	ShippingAreaListPanel.State choice1 = new ShippingAreaListPanel.State();
    	choice1.setShippingId(getCurState().getShippingId());
    	newState.addChoice(ShippingAreaListPanel.getInstance().getName(), choice1.getFullHistoryToken());
    	
    	newState.execute();

	}

	@Override
	protected void postSuperRefresh() {
		generateDynaFields();
	}
	
	LayoutContainer lc1 = new LayoutContainer();
	LayoutContainer lc2 = new LayoutContainer();
	LayoutContainer cgSelectedRegions;
	MyListLoader<ListLoadResult<BeanObject>> countryLoader;
	MyListLoader<ListLoadResult<BeanObject>> provinceLoader;
	MyListLoader<ListLoadResult<BeanObject>> cityLoader;
	MyListLoader<ListLoadResult<BeanObject>> districtLoader;
	ComboBox<BeanObject> cbCountry;
	String lastSelectedCountryId;
	ComboBox<BeanObject> cbProvince;
	String lastSelectedProvinceId;
	ComboBox<BeanObject> cbCity;
	String lastSelectedCityId;
	ComboBox<BeanObject> cbDistrict;

//	ListStore<BeanObject> provinceStore;
//	ListStore<BeanObject> cityStore;
//	ListStore<BeanObject> districtStore;
	
	@Override
	protected void setupPanelLayout() {
//		TableLayout layout = new TableLayout(1);
//		layout.setCellSpacing(5);
//		layout.setCellPadding(5);
//		formPanel.setLayout(layout);
		

        FormLayout fl = new FormLayout();
        fl.setLabelWidth(150);
        fl.setLabelPad(50);
        lc1.setLayout(fl);
        
		TableLayout layout = new TableLayout(1);
		layout.setCellSpacing(5);
		layout.setCellPadding(5);
        lc2.setLayout(layout);
        
        FieldSet fs = new FieldSet();
        fs.setHeading("所辖地区");
        lc2.add(fs);
        
//        MultiField mfRegions = new MultiField();
//        mfRegions.setSpacing(20);
//        mfRegions.setHideLabel(true);
//        fs.add(mfRegions);
        
		final LayoutContainer lcc = new LayoutContainer();
		TableRowLayout cl = new TableRowLayout();
		cl.setWidth("100%");
		cl.setCellVerticalAlign(VerticalAlignment.TOP);
		cl.setCellSpacing(10);
		lcc.setLayout(cl);
		
		fs.add(lcc);
        
		TableData td = new TableData();
//		td.setWidth("10%");

		Text lblCountry = new Text("国家：");
//		lblCountry.setWidth(50);
		lcc.add(lblCountry, td);
		
        

        countryLoader = new ListService().getLoader(ModelNames.REGION, null, null);
        ListStore<BeanObject> countryStore = new ListStore<BeanObject>(countryLoader);
        cbCountry = new ComboBox<BeanObject>();
        cbCountry.setStore(countryStore);
        cbCountry.setDisplayField(IRegion.REGION_NAME);
        cbCountry.setValueField(IRegion.PK_ID);
        cbCountry.setWidth(100);
        cbCountry.addSelectionChangedListener(new SelectionChangedListener<BeanObject>() {
			@Override
			public void selectionChanged(SelectionChangedEvent<BeanObject> se) {
				BeanObject bo = se.getSelectedItem();
				String id = bo==null? null : bo.getString(IRegion.PK_ID);
				log("selected id="+id+", lastSelectedCountryId="+lastSelectedCountryId);
				if(isBothNullOrEqual(id, lastSelectedCountryId)) {
					return;
				}
				lastSelectedCountryId = id;
				clearProvince();
				clearCity();
				clearDistrict();
				onParentRegionSelected(id, provinceLoader);

			}
        });
        
        lcc.add(cbCountry, td);
        
		Text lblProvince = new Text("省份：");
		lcc.add(lblProvince, td);
		
		provinceLoader = new ListService().getLoader(ModelNames.REGION, null, null);
		ListStore<BeanObject> provinceStore = new ListStore<BeanObject>(provinceLoader);
        
        cbProvince = new ComboBox<BeanObject>();
        cbProvince.setStore(provinceStore);
        cbProvince.setDisplayField(IRegion.REGION_NAME);
        cbProvince.setValueField(IRegion.PK_ID);
        cbProvince.setEmptyText("请选择");
        cbProvince.setWidth(100);
        cbProvince.addSelectionChangedListener(new SelectionChangedListener<BeanObject>() {
			@Override
			public void selectionChanged(SelectionChangedEvent<BeanObject> se) {
				BeanObject bo = se.getSelectedItem();
				String id = bo==null? null : bo.getString(IRegion.PK_ID);
				log("selected id="+id+", lastSelectedProvinceId="+lastSelectedProvinceId);
				if(isBothNullOrEqual(id, lastSelectedProvinceId)) {
					return;
				}
				lastSelectedProvinceId = id;
				clearCity();
				clearDistrict();
				onParentRegionSelected(id, cityLoader);
				
			}
        });
        lcc.add(cbProvince, td);
        
		Text lblCity = new Text("城市：");
		lcc.add(lblCity, td);
		
		cityLoader = new ListService().getLoader(ModelNames.REGION, null, null);
		ListStore<BeanObject> cityStore = new ListStore<BeanObject>(cityLoader);
        cbCity = new ComboBox<BeanObject>();
        cbCity.setStore(cityStore);
        cbCity.setDisplayField(IRegion.REGION_NAME);
        cbCity.setValueField(IRegion.PK_ID);
        cbCity.setEmptyText("请选择");
        cbCity.setWidth(50);
        cbCity.addSelectionChangedListener(new SelectionChangedListener<BeanObject>() {
			@Override
			public void selectionChanged(SelectionChangedEvent<BeanObject> se) {
				BeanObject bo = se.getSelectedItem();
				String id = bo==null? null : bo.getString(IRegion.PK_ID);
				log("selected id="+id+", lastSelectedCityId="+lastSelectedCityId);
				if(isBothNullOrEqual(id, lastSelectedCityId)) {
					return;
				}
				lastSelectedCityId = id;
				clearDistrict();
				onParentRegionSelected(id, districtLoader);
				
			}
        });
        lcc.add(cbCity, td);
        
        
		Text lblDistrict = new Text("区/县：");
		lcc.add(lblDistrict, td);
		
		districtLoader = new ListService().getLoader(ModelNames.REGION, null, null);
		ListStore<BeanObject> districtStore = new ListStore<BeanObject>(districtLoader);
        cbDistrict = new ComboBox<BeanObject>();
        cbDistrict.setStore(districtStore);
        cbDistrict.setDisplayField(IRegion.REGION_NAME);
        cbDistrict.setValueField(IRegion.PK_ID);
        cbDistrict.setEmptyText("请选择");
        cbDistrict.setWidth(50);
        lcc.add(cbDistrict, td);
        
        
        Button link = new Button("[+]");
        lcc.add(link, td);
        link.addSelectionListener(new SelectionListener<ButtonEvent>() {
			@Override
			public void componentSelected(ButtonEvent ce) {
				onAddButtonClicked();
			}
        });
        
        cgSelectedRegions = new LayoutContainer();
        TableRowLayout tl = new TableRowLayout();
        cgSelectedRegions.setLayout(tl);
        fs.add(cgSelectedRegions);
        
        
//		TableData td = new TableData();
//		td.setWidth("100%");
//		formPanel.add(lc1, td);
//		formPanel.add(lc2, td);
        
        formPanel.add(lc1);
        formPanel.add(lc2);
	}
	private boolean isBothNullOrEqual(String id, String lastId) {
		boolean res = false;
		if(id==null) {
			if(lastId == null) {
				res = true;
			}
		}
		else {
			if(id.equals(lastId)) {
				res = true;
			}
		}		
		return res;
	}
	private void clearCountry() {
		lastSelectedCountryId = null;
		cbCountry.setValue(null);
		cbCountry.getStore().removeAll();
	}
	private void clearProvince() {
		lastSelectedProvinceId = null;
		cbProvince.setValue(null);
		cbProvince.getStore().removeAll();
	}
	private void clearCity() {
		lastSelectedCityId = null;
		cbCity.setValue(null);
		cbCity.getStore().removeAll();
	}
	private void clearDistrict() {
		cbDistrict.setValue(null);
		cbDistrict.getStore().removeAll();
	}
	private void onAddButtonClicked() {
//		lfDistrict.getSelection()
		Info.display("ah-oh", "clicked");
//		List<BeanObject> countries = cbCountry.getSelection();
		BeanObject selected = cbDistrict.getValue();
		if(selected == null) {
			selected = cbCity.getValue();
			if(selected == null) {
				selected = cbProvince.getValue();
				if(selected == null){
					selected = cbCountry.getValue();
				}
			}
		}
		if(selected == null) {
			Info.display("oops", "nothing is selected");
			return;
		}
		String id = selected.get(IRegion.PK_ID);
		String name = selected.get(IRegion.REGION_NAME);
		addSelectedRegion(id, name);

		List<Component> selectedRegions = (List<Component>)cgSelectedRegions.getItems();
		for(Component c:selectedRegions) {
			CheckBox cb = (CheckBox)c;
			log("cb name: "+cb.getValueAttribute()+", checked? "+cb.getValue());
		}
		cgSelectedRegions.layout();
	}

	Set<String> selectedRegions = new HashSet<String>();
	private void addSelectedRegion(String regionId, String regionName) {
		if(selectedRegions.contains(regionId)) {
			// already existed
			return;
		}
		selectedRegions.add(regionId);
        CheckBox cb = new CheckBox();
        cb.setName(IShippingArea.AREA_REGIONS+selectedRegions.size());
        cb.setValueAttribute(regionId);
        cb.setBoxLabel(regionName);
        cgSelectedRegions.add(cb);
//        cb.setRawValue("true");
        cb.setValue(true);
        cb.setOriginalValue(true);
        
	}
	private void clearSelectedRegion() {
//		regionCount=0;
		selectedRegions.clear();
        cgSelectedRegions.removeAll();
        cgSelectedRegions.layout();
	}
	
	private void onParentRegionSelected(String id, MyListLoader loader) {
		if(id==null) {
			return;
		}
        Criteria criteria = new Criteria();
        criteria.addCondition(new Condition(IRegion.PARENT_ID, Condition.EQUALS, id));
        loader.setCriteria(criteria);
        loader.load();
		
	}
	
    @Override
    public String getDescription() {
        return "cwBasicTextDescription";
    }

    @Override
    public String getName() {
        return "编辑配送区域";
    }
    
    @Override
	protected void submit() {
		// default implementation is thru GWT-RPC
		Map<String, Object> props = FormUtils.getPropsFromForm(formPanel);
		List<Component> selectedRegions = (List<Component>)cgSelectedRegions.getItems();
		List<String> selectedRegionIds = new ArrayList<String>();
		for(Component c:selectedRegions) {
			CheckBox cb = (CheckBox)c;
			if(cb.getValue()) {
				selectedRegionIds.add(cb.getValueAttribute());
			}
		}
		props.put(IShippingArea.AREA_REGIONS, selectedRegionIds);
		props.put(IShippingArea.SHIPPING, getCurState().getShippingId());
    	if (getCurState().getIsEdit()) {
    		String id = getCurState().getPkId();
    		props.put(IShippingArea.PK_ID, id);
    	}
    	BeanObject form = new BeanObject(getEntityClassName(), props);

    	
		RemoteService.getSpecialService().saveShippingArea(form, new AsyncCallback<Boolean>() {
            public void onFailure(Throwable caught) {
                log("failed!!!! "+caught.getMessage());
            }
            public void onSuccess(Boolean result) {
                log("result: \n");
                log(result.toString());
                gotoSuccessPanel();
            }
             
        });
	}
    
    @Override
    public void refresh() {
    	// clear
    	try {
    		List<Field<?>> fields = formPanel.getFields();
    		for(Field<?> f:fields) {
    			if(lc1.getItems().contains(f)) {
    				lc1.remove(f);
    			}
    		}
    	} catch (Exception ex) {
    		ex.printStackTrace();
    	}
    	clearSelectedRegion();
    	
    	
    	clearCountry();
    	clearProvince();
    	clearCity();
    	clearDistrict();
    	
		log("----- refresh ShippingMeta---");
        State state = getCurState();
        String shippingAreaId = state.getPkId();
        String shippingId = state.getShippingId();
        log("shippingAreaId: "+shippingAreaId+", shippingId: "+shippingId);
        
        
        
        RemoteService.getSpecialService().getShippingAreaMeta(shippingAreaId, shippingId, new AsyncCallback<ShippingAreaMetaForm>() {
            public void onFailure(Throwable caught) {
                log("failed!!!! "+caught.getMessage());
            }
            public void onSuccess(ShippingAreaMetaForm result) {
                log("result: \n");
                log(result.toString());
                
                obj = result;
                generateDynaFields();
            }
             
        });
        
        if(state.getIsEdit()) {
        	RemoteService.getSpecialService().getAreaRegionListWithName(shippingAreaId, 
        			new AsyncCallback<ListLoadResult<BeanObject>>() {
						public void onFailure(Throwable caught) {
							log("failed!!!! "+caught.getMessage());
						}
						public void onSuccess(ListLoadResult<BeanObject> result) {
			                log("result: \n");
			                log(result.toString());
			                
			                renderAreaRegions(result.getData());
						}
        	});
        }
        
        // load country list
        Criteria criteria = new Criteria();
        criteria.addCondition(new Condition(IRegion.REGION_TYPE, Condition.EQUALS, IRegion.TYPE_COUNTRY.toString()));
        countryLoader.setCriteria(criteria);
        countryLoader.load();
        
        System.out.println("----- finish PaymentMeta---");

    }
	
	@Override
	protected void retrieveEntity() {
	}
	public void generateDynaFields() {
		ShippingAreaMetaForm result = (ShippingAreaMetaForm)obj; 
			
        TextField<String> tb = new TextField<String>();
        tb.setFieldLabel("配送区域名称");
        tb.setName(ShippingAreaMetaForm.SHIPPING_AREA_NAME);
        tb.setValue(result.getName());
        tb.setOriginalValue(result.getName());
        lc1.add(tb);
        
        Map<String, ShippingAreaFieldMetaForm> fieldMetas = result.getFieldMetas();
        Map<String, String> keyValues = result.getFieldValues();
        for(String key:fieldMetas.keySet()) {
            String value = keyValues.get(key);
            System.out.println("key: "+key+", value: "+value);
            
            ShippingAreaFieldMetaForm meta = fieldMetas.get(key);

            TextField<String> textBox = new TextField<String>();
			textBox.setFieldLabel(meta.getLable());
			textBox.setName(key);
			textBox.setValue(value);
			textBox.setOriginalValue(value);
			lc1.add(textBox);

        }
        lc1.layout();
        lc1.repaint();
	}
	private void renderAreaRegions(List<BeanObject> ars ) {
		log(ars==null? "null" : "how many existing regions? "+ars.size());
		for(BeanObject ar : ars) {
			String regionId = ar.get(IAreaRegion.REGION_ID);
			String regionName = ar.get(IAreaRegion.REGION_NAME);
			addSelectedRegion(regionId, regionName);
		}
		cgSelectedRegions.layout();
	}

}
