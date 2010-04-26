/**
 * Author: Bob Chen
 */

package com.jcommerce.gwt.client.panels.goods;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.extjs.gxt.ui.client.event.ButtonEvent;
import com.extjs.gxt.ui.client.event.SelectionChangedEvent;
import com.extjs.gxt.ui.client.event.SelectionChangedListener;
import com.extjs.gxt.ui.client.event.SelectionListener;
import com.extjs.gxt.ui.client.store.ListStore;
import com.extjs.gxt.ui.client.widget.Info;
import com.extjs.gxt.ui.client.widget.TabItem;
import com.extjs.gxt.ui.client.widget.button.Button;
import com.extjs.gxt.ui.client.widget.form.AdapterField;
import com.extjs.gxt.ui.client.widget.form.ComboBox;
import com.extjs.gxt.ui.client.widget.form.Field;
import com.extjs.gxt.ui.client.widget.form.HiddenField;
import com.extjs.gxt.ui.client.widget.form.LabelField;
import com.extjs.gxt.ui.client.widget.form.MultiField;
import com.extjs.gxt.ui.client.widget.form.TextArea;
import com.extjs.gxt.ui.client.widget.form.TextField;
import com.extjs.gxt.ui.client.widget.form.ComboBox.TriggerAction;
import com.google.gwt.user.client.Element;
import com.jcommerce.gwt.client.ModelNames;
import com.jcommerce.gwt.client.form.BeanObject;
import com.jcommerce.gwt.client.form.GoodsForm;
import com.jcommerce.gwt.client.model.IAttribute;
import com.jcommerce.gwt.client.model.IGoods;
import com.jcommerce.gwt.client.model.IGoodsAttr;
import com.jcommerce.gwt.client.model.IGoodsType;
import com.jcommerce.gwt.client.resources.Resources;
import com.jcommerce.gwt.client.service.Condition;
import com.jcommerce.gwt.client.service.Criteria;
import com.jcommerce.gwt.client.service.ListService;

class GoodsAttributePanel extends TabItem {
	private ListStore<BeanObject> goodsTypeList;
	String goodsTypeId;
	List<BeanObject> goodsAttributes;

	Set<Field> fieldList = new HashSet<Field>();
	boolean refreshed;
	GoodsPanel goodsPanel;
	ComboBox<BeanObject> fListTypes;
	int attCount = 0;
	String formSufix = IGoods.ATTRIBUTES;
	String goodsId = null;

	GoodsAttributePanel(GoodsPanel gp) {
		super();
		this.goodsPanel = gp;
		init();
	}

	private void init() {
		setLayout(goodsPanel.getFormLayout());

		goodsTypeList = new ListStore<BeanObject>();
		fListTypes = GoodsForm.getGoodsTypeIdField();
		fListTypes.setDisplayField(IGoodsType.CAT_NAME);
		fListTypes.setValueField(IGoodsType.PK_ID);

		fListTypes.setFieldLabel(Resources.constants.NewGoods_type());
		fListTypes.setStore(goodsTypeList);

		fListTypes.setEmptyText("Select a Type...");
		fListTypes.setWidth(150);
		fListTypes.setTypeAhead(true);
		fListTypes.setTriggerAction(TriggerAction.ALL);
		add(fListTypes, goodsPanel.sfd());

		fListTypes
				.addSelectionChangedListener(new SelectionChangedListener<BeanObject>() {
					@Override
					public void selectionChanged(
							SelectionChangedEvent<BeanObject> se) {
						BeanObject bo = se.getSelectedItem();
						if (bo == null) {
							// this happen when formPanel.clear()
							return;
						}
						// TODO is there better way to only triggering this when
						// user click it?
						// currently set the value to fListTypes with
						// populateField() will also trigger this, which is not
						// desired
						String goodsTypeId = bo.getString(IGoodsType.PK_ID);
						updateType(goodsTypeId);

					}
				});
	}

	public void debug(String s) {
		System.out.println("[GoodsAttributePanel]: " + s);
	}

	boolean goodsTypeListReady = false;
	boolean goodsAttributesReady = false;

	public void refresh(final String goodsId, final String newGtId) {
		// this will disallow any concurrent createWidget() requests triggered
		// by other events
		// not safe enough
		this.goodsId = goodsId;
		goodsTypeListReady = false;
		goodsAttributesReady = false;

		goodsTypeId = newGtId;

		debug("refresh: goodsId=" + goodsId + ", goodsTypeId=" + newGtId);
		new ListService().listBeans(ModelNames.GOODSTYPE,
				new ListService.Listener() {
					@Override
					public void onFailure(Throwable caught) {
						Info.display("Ooops", caught.getMessage());
						super.onFailure(caught);
					}

					@Override
					public void onSuccess(List<BeanObject> beans) {
						goodsTypeList.removeAll();
						goodsTypeList.add(beans);

						goodsTypeListReady = true;
						// will trigger updateType()
						goodsPanel.populateField(fListTypes);

					}

				});

		if (newGtId == null || "".equals(newGtId.trim())) {
			goodsAttributesReady = true;
			return;
		}
		Criteria criteria = new Criteria();
		criteria.addCondition(new Condition(IGoodsAttr.GOODS_ID, Condition.EQUALS,
				goodsId));
		new ListService().listBeans(ModelNames.GOODSATTR,criteria,
				new ListService.Listener() {
					public void onSuccess(final List<BeanObject> beans) {
						goodsAttributes = beans;
						goodsAttributesReady = true;
						updateType(newGtId);
					}
				});
	}

	private void updateType(final String newGtId) {
		// TODO is there better solution for concurrent call-back handling?
		if (!goodsTypeListReady || !goodsAttributesReady) {
			return;
		}
		debug("updateType: goodsTypeId=" + newGtId);
		if (newGtId == null) {
			createWidgets(null, null);
		} else {
			Criteria criteria = new Criteria();
			criteria.addCondition(new Condition(IAttribute.GOODS_TYPE,
					Condition.EQUALS, newGtId));
			new ListService().listBeans(ModelNames.ATTRIBUTE, criteria,
					new ListService.Listener() {
						public void onSuccess(final List<BeanObject> attrs) {
							if (newGtId.equals(goodsTypeId)) {
								// use the cached data
								createWidgets(attrs, goodsAttributes);
							} else {
								// always null
								createWidgets(attrs, null);
							}
						};
					});
		}

	}

	private String buildElementName(String fieldName) {
		return formSufix + "[" + attCount + "]." + fieldName;
	}

	@Override
	protected void onRender(Element parent, int index) {
		// TODO Auto-generated method stub
		System.out.println("GalleryPanel2:  [onRender]");
		super.onRender(parent, index);

	}

	public void addField(Field field, int index) {
		if(index == -1) {
			add(field);
		} else {
			insert(field, index);
		}
		fieldList.add(field);
	}

	private ArrayList idList;
	private ArrayList valueList;
	private ArrayList priceList;
	private Map<String, MultiField> existAttribute;
	
	private void createWidgets(List<BeanObject> attrs,
			List<BeanObject> goodsAttributes) {

		debug("createWidgets: 1");
		attCount = 0;
		clearFields();

//		Map<String, Map<String, String>> valueHolders = new HashMap<String, Map<String, String>>();
//		Map<String, Map<String, String>> priceHolders = new HashMap<String, Map<String, String>>();
		
		idList = new ArrayList();
		valueList = new ArrayList();
		priceList = new ArrayList();
		if (goodsAttributes != null) {
			for (BeanObject goodsAttribute : goodsAttributes) {
				String attrId = goodsAttribute.getString(IGoodsAttr.ATTR_ID);
				String value = goodsAttribute.getString(IGoodsAttr.ATTR_VALUE);
				String price = goodsAttribute.getString(IGoodsAttr.ATTR_PRICE);
				idList.add(attrId);
				valueList.add(value);
				priceList.add(price);
//				Map<String, String> idValueMap = new HashMap<String, String>();
//				Map<String, String> idPriceMap = new HashMap<String, String>();
				
//				idValueMap.put(attrId, value);
//				String price = goodsAttribute.getString(IGoodsAttr.ATTR_PRICE);
//				idPriceMap.put(attrId, price);
//				valueHolders.put(id, idValueMap);
//				priceHolders.put(id, idPriceMap);
			}
		}

		debug("createWidgets: 2");
		existAttribute = new HashMap<String, MultiField>();
		if (attrs != null) {
			for (BeanObject attr : attrs) {
				insertField(attr);
			}
		}
		
		debug("createWidgets: 3");
		layout();
		repaint();

	}

	private void insertField(final BeanObject attr) {
		attCount++;
		
		String attributeId = attr.get(IAttribute.PK_ID);
		final HiddenField<String> hf = new HiddenField<String>();
		hf.setName(buildElementName(IGoodsAttr.ATTR_ID));
		hf.setValue(attributeId + ":" + goodsId);
		addField(hf, -1);
		
		String value = null;
		String price = null;
		int index = idList.indexOf(attributeId);
		if(index != -1) {
			value = (String) valueList.get(index);
			price = (String) priceList.get(index);
			idList.remove(index);
			valueList.remove(index);
			priceList.remove(index);
		}
		String name = attr.get(IAttribute.ATTR_NAME);

		final MultiField mfField = new MultiField();
		mfField.setFieldLabel(name);
		
		long inputType = ((Number) attr.get(IAttribute.ATTR_INPUT_TYPE))
				.longValue();
		String values = attr.getString(IAttribute.ATTR_VALUES);
		if (inputType == IAttribute.INPUTTYPE_SINGLELINETEXT) {
			TextField<String> field = new TextField<String>();
			field.setName(buildElementName(IGoodsAttr.ATTR_VALUE));
			field.setValue(value);
			mfField.add(field);
			// addField2List(field);
		} else if (inputType == IAttribute.INPUTTYPE_MULTIPLELINETEXT) {
			TextArea field = new TextArea();
			field.setName(buildElementName(IGoodsAttr.ATTR_VALUE));
			field.setValue(value);
			mfField.add(field);
			// addField2List(field);
		} else if (inputType == IAttribute.INPUTTYPE_CHOICE) {
			ListStore<BeanObject> store = new ListStore<BeanObject>();
			if (values != null) {
				String[] vs = values.split("\n");
				for (String s : vs) {
					BeanObject bo = new BeanObject();
					bo.set("value", s);
					store.add(bo);
				}
			}

			ComboBox<BeanObject> field = new ComboBox<BeanObject>();
			field.setName(buildElementName(IGoodsAttr.ATTR_VALUE));
			field.setDisplayField("value");
			field.setValueField("value");
			field.setStore(store);
			// Set value
			BeanObject bo = new BeanObject();
			bo.set("value", value);
			field.setValue(bo);

			field.setEmptyText("Select a value...");
			field.setWidth(150);
			field.setTypeAhead(true);
			field.setTriggerAction(TriggerAction.ALL);
			mfField.add(field);
			// addField2List(field);
		} else {
			throw new RuntimeException("Unknown input type: "
					+ inputType);
		}

		TextField<String> priceField = new TextField<String>();
		priceField.setName(buildElementName(IGoodsAttr.ATTR_PRICE));
		priceField.setWidth(40);
		priceField.setValue(price);
		LabelField fieldName = new LabelField("属性价格:");
		fieldName.setWidth(60);
		
		mfField.add(fieldName);
		mfField.add(priceField);

		boolean isFirst = true;
		if(existAttribute.containsKey(attributeId)) {
			isFirst = false;
		}
		
		Button btnAttribute = new Button();
		if(isFirst) {
			btnAttribute.setText("[+]");
			btnAttribute.addSelectionListener(new SelectionListener<ButtonEvent>() {
				public void componentSelected(ButtonEvent ce) {
					int index = getParentIndex(mfField);
					insertField(attr);
					layout();
					repaint();
				}
			});
		} else {
			btnAttribute.setText("[-]");
			btnAttribute.addSelectionListener(new SelectionListener<ButtonEvent>() {
				public void componentSelected(ButtonEvent ce) {
					remove(mfField);
					remove(hf);
					fieldList.remove(mfField);
					fieldList.remove(hf);
				}
			});
		}
		
		AdapterField bAddAttributeAd = new AdapterField(btnAttribute);
		bAddAttributeAd.setHideLabel(true);
		mfField.add(bAddAttributeAd);
		
		long attrType = ((Number) attr.get(IAttribute.ATTR_TYPE))
				.longValue();
		if (attrType == IAttribute.TYPE_ONLY) {
			fieldName.setVisible(false);
			priceField.setVisible(false);
			btnAttribute.setVisible(false);
		}
		
		int parent = -1;
		if(!isFirst) {
			parent = getParentIndex(existAttribute.get(attributeId)) + 1;
		}
		addField(mfField,parent);
		existAttribute.put(attributeId, mfField);
		
		if(idList.indexOf(attributeId) != -1) {
			insertField(attr);
		}
	}

	public int getParentIndex(MultiField mfField) {
		return indexOf(mfField);
	}

	private void clearFields() {
		for (Field field : fieldList) {
			remove(field);
		}
		fieldList.clear();
	}
}
