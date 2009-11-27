package com.jcommerce.gwt.client.panels;

import java.util.ArrayList;
import java.util.Iterator;
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
import com.extjs.gxt.ui.client.widget.HorizontalPanel;
import com.extjs.gxt.ui.client.widget.button.Button;
import com.extjs.gxt.ui.client.widget.form.NumberField;
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
import com.google.gwt.core.client.GWT;
import com.google.gwt.i18n.client.NumberFormat;
import com.google.gwt.user.client.Element;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.TextBox;
import com.jcommerce.gwt.client.ContentWidget;
import com.jcommerce.gwt.client.ModelNames;
import com.jcommerce.gwt.client.PageState;
import com.jcommerce.gwt.client.form.BeanObject;
import com.jcommerce.gwt.client.model.IBrand;
import com.jcommerce.gwt.client.model.ICategory;
import com.jcommerce.gwt.client.model.IGoods;
import com.jcommerce.gwt.client.panels.goods.GoodsPanel;
import com.jcommerce.gwt.client.resources.Resources;
import com.jcommerce.gwt.client.service.Condition;
import com.jcommerce.gwt.client.service.Criteria;
import com.jcommerce.gwt.client.service.DeleteService;
import com.jcommerce.gwt.client.service.ListService;
import com.jcommerce.gwt.client.service.PagingListService;
import com.jcommerce.gwt.client.service.UpdateService;
import com.jcommerce.gwt.client.service.WaitService;
import com.jcommerce.gwt.client.widgets.ActionCellRenderer;

public class GoodsListPanel extends ContentWidget {
	public static interface Constants {
		String GoodsList_title();
		String GoodsList_action();
		String GoodsList_all_brand();
		String GoodsList_all_category();
		String GoodsList_keyword();
		String GoodsList_action_prompt();
		String GoodsList_action_OK();
		String GoodsList_search();
		String GoodsList_reset();
		String GoodsList_save();
		String GoodsList_add_new();
		String GoodsList_action_edit();
		String GoodsList_action_delete();
		String GoodsList_action_set_new();
		String GoodsList_action_unset_new();
		String GoodsList_action_set_hot();
		String GoodsList_action_unset_hot();
		String GoodsList_action_set_best();
		String GoodsList_action_unset_best();
	    String Goods_name();
	    String Goods_SN();
	    String Goods_brand();
	    String Goods_category();
	    String Goods_category_extended();
	    String Goods_shopPrice();
	    String Goods_marketPrice();
	    String Goods_giveIntegral();
	    String Goods_integral();
	    String Goods_rankIntegral();
	    String Goods_promotePrice();
	    String Goods_promoteDate();
	    String Goods_weight();
	    String Goods_number();
	    String Goods_hotsold();
	    String Goods_newAdded();
	    String Goods_bestSold();
	    String Goods_onSale();
	}
	
    // leon to integrate with history-based page navigation mechnism. 
    // State should contain all info needed to render this page.
    // This is a minimum skeleton, more fields may be added, see leontest.Attribute
	public static class State extends PageState {
		public String getPageClassName() {
			return GoodsListPanel.class.getName();
		}
		public String getMenuDisplayName() {
			return Resources.constants.GoodsList_title();
		}
	}
	
	private State curState = new State();
	public State getCurState() {
		return curState;
	}

	public void setCurState(State curState) {
		this.curState = curState;
	}
	public static GoodsListPanel getInstance(){
		if(instance == null) {
			instance = new GoodsListPanel();
		}
		return instance;
	}
	private static GoodsListPanel instance;
	private GoodsListPanel() {
//		add(contentPanel);
		initJS(this);
	}
	
//	ColumnPanel contentPanel = new ColumnPanel();
	ListBox b_list = new ListBox();
	ListBox lstCategory = new ListBox();
	ListBox lstBrand = new ListBox();
	ListBox lstType = new ListBox();
	TextBox txtKeyword = new TextBox();
	Button btnFind = new Button(Resources.constants.GoodsList_search());
	ListBox lstAction = new ListBox();
	Button btnAct = new Button(Resources.constants.GoodsList_action_OK());

	Criteria criteria = new Criteria();

	PagingToolBar toolBar;



	public String getDescription() {
		return "cwBasicTextDescription";
	}

	public String getName() {
		return Resources.constants.GoodsList_title();
	}

	protected void onRender(Element parent, int index) {
		super.onRender(parent, index);
		
//    	List<String> wantedFields = new ArrayList<String>();
//    	wantedFields.add(IGoods.ID);
//    	wantedFields.add(IGoods.NAME);
//    	wantedFields.add(IGoods.SN);
//    	wantedFields.add(IGoods.SHOPPRICE);
//    	wantedFields.add(IGoods.ONSALE);
//    	wantedFields.add(IGoods.IS_HOT);
//    	wantedFields.add(IGoods.IS_BEST);
//    	wantedFields.add(IGoods.IS_NEW);
//    	wantedFields.add(IGoods.NUMBER);
		
		BasePagingLoader loader = new PagingListService().getLoader(ModelNames.GOODS, criteria);
		loader.load(0, 10);
		final ListStore<BeanObject> store = new ListStore<BeanObject>(loader);
		store.addStoreListener(new StoreListener<BeanObject>() {
			public void storeUpdate(StoreEvent<BeanObject> se) {
				List<Record> changed = store.getModifiedRecords();
				for (Record rec : changed) {
					BeanObject bean = (BeanObject) rec.getModel();
					updateGoods(bean, null);
				}
			}
		});

		toolBar = new PagingToolBar(10);
		toolBar.bind(loader);

		List<ColumnConfig> columns = new ArrayList<ColumnConfig>();
		final CheckBoxSelectionModel<BeanObject> smRowSelection = new CheckBoxSelectionModel<BeanObject>();
		columns.add(smRowSelection.getColumn());
//		columns.add(new ColumnConfig(IGoods.ID, "ID", 50));
		
		ColumnConfig col = new ColumnConfig(IGoods.GOODS_NAME, Resources.constants
				.Goods_name(), 100);
		col.setEditor(new CellEditor(new TextField()));
		columns.add(col);
		col = new ColumnConfig(IGoods.GOODS_SN, Resources.constants.Goods_SN(), 100);
		col.setEditor(new CellEditor(new TextField()));
		columns.add(col);
		col = new ColumnConfig(IGoods.SHOP_PRICE, Resources.constants
				.Goods_shopPrice(), 80);
		col.setAlignment(HorizontalAlignment.RIGHT);
		col.setNumberFormat(NumberFormat.getCurrencyFormat());
		col.setEditor(new CellEditor(new NumberField()));
		columns.add(col);
		CheckColumnConfig onsale = new CheckColumnConfig(IGoods.IS_ON_SALE,
				Resources.constants.Goods_onSale(), 80);
		columns.add(onsale);
		CheckColumnConfig hotsold = new CheckColumnConfig(IGoods.IS_HOT,
				Resources.constants.Goods_hotsold(), 80);
		columns.add(hotsold);
		CheckColumnConfig bestsold = new CheckColumnConfig(IGoods.IS_BEST,
				Resources.constants.Goods_bestSold(), 80);
		columns.add(bestsold);
		CheckColumnConfig newadd = new CheckColumnConfig(IGoods.IS_NEW,
				Resources.constants.Goods_newAdded(), 80);
		columns.add(newadd);
		col = new ColumnConfig(IGoods.GOODS_NUMBER, Resources.constants
				.Goods_number(), 80);
		col.setAlignment(HorizontalAlignment.RIGHT);
		col.setEditor(new CellEditor(new NumberField()));
		columns.add(col);
		ColumnConfig actcol = new ColumnConfig("Action", Resources.constants
				.GoodsList_action(), 100);
		columns.add(actcol);

		ColumnModel cm = new ColumnModel(columns);

		Grid<BeanObject> grid = new EditorGrid<BeanObject>(store, cm);
		grid.setLoadMask(true);
		grid.setBorders(true);
		grid.setSelectionModel(smRowSelection);
		//      grid.setAutoExpandColumn("forum");
		grid.addPlugin(onsale);
		grid.addPlugin(hotsold);
		grid.addPlugin(bestsold);
		grid.addPlugin(newadd);

		ActionCellRenderer render = new ActionCellRenderer(grid);
		ActionCellRenderer.ActionInfo act = new ActionCellRenderer.ActionInfo();
		act.setImage(GWT.getModuleBaseURL()+"icon_edit.gif");
		act.setAction("editGoods($pkId)");
		act.setTooltip(Resources.constants.GoodsList_action_edit());
		render.addAction(act);
		act = new ActionCellRenderer.ActionInfo();		
		act.setImage(GWT.getModuleBaseURL()+"icon_trash.gif");
		act.setAction("deleteGoods($pkId)");
		act.setTooltip(Resources.constants.GoodsList_action_delete());
		render.addAction(act);

		actcol.setRenderer(render);



		HorizontalPanel header = new HorizontalPanel();
		header.add(Resources.images.icon_search().createImage());
		header.add(lstCategory);
		header.add(lstBrand);
		header.add(lstType);
		header.add(new Label("  " + Resources.constants.GoodsList_keyword()));
		header.add(txtKeyword);
		header.add(btnFind);
		add(header);

		btnFind.addSelectionListener(new SelectionListener<ButtonEvent>() {
			public void componentSelected(ButtonEvent ce) {
				search();
			}
		});

		ContentPanel panel = new ContentPanel();
		panel.setFrame(true);
		panel.setCollapsible(true);
		panel.setAnimCollapse(false);
		panel.setButtonAlign(HorizontalAlignment.CENTER);
		panel.setIconStyle("icon-table");
		//        panel.setHeading("Paging Grid");
		panel.setLayout(new FitLayout());
		panel.add(grid);
		panel.setSize(880, 350);
		panel.setBottomComponent(toolBar);

		panel.setButtonAlign(HorizontalAlignment.CENTER);
		panel.addButton(new Button(Resources.constants.GoodsList_reset(),
				new SelectionListener<ButtonEvent>() {
					public void componentSelected(ButtonEvent ce) {
						store.rejectChanges();
					}
				}));

		panel.addButton(new Button(Resources.constants.GoodsList_save(),
				new SelectionListener<ButtonEvent>() {
					public void componentSelected(ButtonEvent ce) {
						store.commitChanges();
					}
				}));
		
		panel.addButton(new Button(Resources.constants.GoodsList_add_new(),
				new SelectionListener<ButtonEvent>() {
					public void componentSelected(ButtonEvent ce) {
						
						GoodsPanel.State newState = new GoodsPanel.State();
						newState.setIsEdit(false);
						newState.execute();
//						JCommerceGae.getInstance().displayGoodsPanel(null);
					}
				}));
		
		add(panel);

		lstAction.addItem(Resources.constants.GoodsList_action_prompt(), "---");
		lstAction.addItem(Resources.constants.GoodsList_action_delete(), "delete");
		lstAction.addItem(Resources.constants.GoodsList_action_set_new(), "new");
		lstAction.addItem(Resources.constants.GoodsList_action_unset_new(), "notnew");
		lstAction.addItem(Resources.constants.GoodsList_action_set_hot(), "hot");
		lstAction.addItem(Resources.constants.GoodsList_action_unset_hot(), "nothot");
		lstAction.addItem(Resources.constants.GoodsList_action_set_best(), "best");
		lstAction.addItem(Resources.constants.GoodsList_action_unset_best(), "notbest");

		btnAct.addSelectionListener(new SelectionListener<ButtonEvent>() {
			public void componentSelected(ButtonEvent ce) {
				int index = lstAction.getSelectedIndex();
				if (index == 0) {
					// it is prompt
					return;
				}

				String sel = lstAction.getValue(index);
				final List<BeanObject> items = smRowSelection
						.getSelectedItems();
				executeAction(items, sel);
			}
		});

		HorizontalPanel footer = new HorizontalPanel();
		footer.add(lstAction);
		footer.add(btnAct);
		add(footer);
	}

	private void search() {
		criteria.removeAllConditions();
		if (lstBrand.getSelectedIndex() > 0) {
			String brand = lstBrand.getValue(lstBrand.getSelectedIndex());
			Condition cond = new Condition();
			cond.setField(IGoods.BRAND_ID);
			cond.setOperator(Condition.EQUALS);
			cond.setValue(brand);
			criteria.addCondition(cond);
		}
		if (lstCategory.getSelectedIndex() > 0) {
			String cat = lstCategory.getValue(lstCategory.getSelectedIndex());
			Condition cond = new Condition();
			cond.setField(IGoods.CATEGORY_IDS);
			cond.setOperator(Condition.CONTAINS);
			cond.setValue(cat);
			criteria.addCondition(cond);
		}
		String type = lstType.getValue(lstType.getSelectedIndex());
		if ("new".equals(type)) {
			Condition cond = new Condition();
			cond.setField(IGoods.IS_NEW);
			cond.setOperator(Condition.EQUALS);
			cond.setValue("true");
			criteria.addCondition(cond);
		} else if ("best".equals(type)) {
			Condition cond = new Condition();
			cond.setField(IGoods.IS_BEST);
			cond.setOperator(Condition.EQUALS);
			cond.setValue("true");
			criteria.addCondition(cond);
		} else if ("hot".equals(type)) {
			Condition cond = new Condition();
			cond.setField(IGoods.IS_HOT);
			cond.setOperator(Condition.EQUALS);
			cond.setValue("true");
			criteria.addCondition(cond);
		}

		String keyword = txtKeyword.getText();
		if (keyword != null && keyword.trim().length() > 0) {
			Condition cond = new Condition();
			cond.setField(IGoods.KEYWORDS);
			cond.setOperator(Condition.CONTAINS);
			cond.setValue(keyword.trim());
			criteria.addCondition(cond);
		}

		toolBar.refresh();
	}

	private void executeAction(final List<BeanObject> items, String action) {
		if (items == null) {
			return;
		}

		final List listeners = new ArrayList();

		for (BeanObject item : items) {
			if ("delete".equals(action)) {
				DeleteListener listener = new DeleteListener();
				listeners.add(listener);
				deleteGoods(item.getString(IGoods.PK_ID), listener);
			} else if ("new".equals(action)) {
				if (!Boolean.TRUE.equals(item.get(IGoods.IS_NEW))) {
					item.set(IGoods.IS_NEW, Boolean.TRUE);
					UpdateListener listener = new UpdateListener();
					listeners.add(listener);
					updateGoods(item, listener);
				}
			} else if ("notnew".equals(action)) {
				if (!Boolean.FALSE.equals(item.get(IGoods.IS_NEW))) {
					item.set(IGoods.IS_NEW, Boolean.FALSE);
					UpdateListener listener = new UpdateListener();
					listeners.add(listener);
					updateGoods(item, listener);
				}
			} else if ("hot".equals(action)) {
				if (!Boolean.TRUE.equals(item.get(IGoods.IS_HOT))) {
					item.set(IGoods.IS_HOT, Boolean.TRUE);
					UpdateListener listener = new UpdateListener();
					listeners.add(listener);
					updateGoods(item, listener);
				}
			} else if ("nothot".equals(action)) {
				if (!Boolean.FALSE.equals(item.get(IGoods.IS_HOT))) {
					item.set(IGoods.IS_HOT, Boolean.FALSE);
					UpdateListener listener = new UpdateListener();
					listeners.add(listener);
					updateGoods(item, listener);
				}
			} else if ("best".equals(action)) {
				if (!Boolean.TRUE.equals(item.get(IGoods.IS_BEST))) {
					item.set(IGoods.IS_BEST, Boolean.TRUE);
					UpdateListener listener = new UpdateListener();
					listeners.add(listener);
					updateGoods(item, listener);
				}
			} else if ("notbest".equals(action)) {
				if (!Boolean.FALSE.equals(item.get(IGoods.IS_BEST))) {
					item.set(IGoods.IS_BEST, Boolean.FALSE);
					UpdateListener listener = new UpdateListener();
					listeners.add(listener);
					updateGoods(item, listener);
				}
			}
		}

		new WaitService(new WaitService.Job() {
			public boolean isReady() {
				if (listeners.size() != items.size()) {
					return false;
				}
				for (int i = 0; i < listeners.size(); i++) {
					if (listeners.get(i) instanceof DeleteListener) {
						if (!((DeleteListener) listeners.get(i)).isFinished()) {
							return false;
						}
					} else if (listeners.get(i) instanceof UpdateListener) {
						if (!((UpdateListener) listeners.get(i)).isFinished()) {
							return false;
						}
					} else {
						throw new RuntimeException("Unknown listener type:"
								+ listeners.get(i));
					}
				}
				return false;
			}

			public void run() {
				toolBar.refresh();
			}
		});
	}

	private native void initJS(GoodsListPanel me) /*-{
	   $wnd.deleteGoods = function (id) {
	       me.@com.jcommerce.gwt.client.panels.GoodsListPanel::deleteGoodsAndRefrsh(Ljava/lang/String;)(id);
	   };
	   $wnd.editGoods = function (id) {
	       me.@com.jcommerce.gwt.client.panels.GoodsListPanel::editGoods(Ljava/lang/String;)(id);
	   };
	   }-*/;

	private void deleteGoods(String id, DeleteService.Listener listener) {
		new DeleteService().deleteBean(ModelNames.GOODS, id, listener);
	}

	private void deleteGoodsAndRefrsh(String id) {
		new DeleteService().deleteBean(ModelNames.GOODS, id,
				new DeleteService.Listener() {
					public void onSuccess(Boolean success) {
						toolBar.refresh();
					}
				});
	}

	private void editGoods(String id) {
		
		GoodsPanel.State newState = new GoodsPanel.State();
		newState.setIsEdit(true);
		newState.setPkId(id);
		newState.execute();
		
//		new ReadService().getBean(ModelNames.GOODS, id,
//				new ReadService.Listener() {
//					public void onSuccess(BeanObject bean) {
//						JCommerceGae.getInstance().displayGoodsPanel(bean);
//					}
//				});
	}

	private void updateGoods(BeanObject goods, UpdateService.Listener listener) {
		new UpdateService().updateBean(goods.getString(IGoods.PK_ID), goods,
				listener);
	}

	class DeleteListener extends DeleteService.Listener {
		private boolean finished = false;

		public void onSuccess(Boolean sucess) {
			finished = true;
		}

		public void onFailure(Throwable caught) {
			finished = true;
		}

		boolean isFinished() {
			return finished;
		}
	}

	class UpdateListener extends UpdateService.Listener {
		private boolean finished = false;

		public void onSuccess(Boolean sucess) {
			finished = true;
		}

		public void onFailure(Throwable caught) {
			finished = true;
		}

		boolean isFinished() {
			return finished;
		}
	}

	public void refresh() {
		System.out.println("refresh GoodsList...");
		lstBrand.addItem(Resources.constants.GoodsList_all_brand(), "all");
		new ListService().listBeans(ModelNames.BRAND,
				new ListService.Listener() {
					public synchronized void onSuccess(List<BeanObject> result) {
						for (Iterator<BeanObject> it = result.iterator(); it
								.hasNext();) {
							BeanObject brand = it.next();
							lstBrand.addItem(brand.getString(IBrand.BRAND_NAME),
									brand.getString(IBrand.PK_ID));
						}
					}
				});

		lstCategory
				.addItem(Resources.constants.GoodsList_all_category(), "all");
		new ListService().listBeans(ModelNames.CATEGORY,
				new ListService.Listener() {
					public void onSuccess(List<BeanObject> result) {
						List<String> pids = new ArrayList<String>();
						for (Iterator<BeanObject> it = result.iterator(); it
								.hasNext();) {
							BeanObject cat = it.next();
							String name = cat.getString(ICategory.CAT_NAME);
							String id = cat.getString(ICategory.PK_ID);
//							String _pid = cat.getString(ICategory.PARENT);
							String _pid = cat.getString(ICategory.PARENT_ID);
							if (_pid == null) {
								pids.clear();
							} else if (!pids.contains(_pid)) {
								pids.add(_pid);
							}
							int level = pids.indexOf(_pid) + 1;
							for (int i = 0; i < level; i++) {
								name = "  " + name;
							}
							lstCategory.addItem(name, id);
						}
					}
				});

		lstType.addItem(Resources.constants.Goods_newAdded(), "new");
		lstType.addItem(Resources.constants.Goods_bestSold(), "best");
		lstType.addItem(Resources.constants.Goods_hotsold(), "hot");		
		
		toolBar.refresh();
	}

}
