package com.jcommerce.gwt.client.panels;

import java.util.List;

import com.extjs.gxt.ui.client.event.ButtonEvent;
import com.extjs.gxt.ui.client.event.SelectionListener;
import com.extjs.gxt.ui.client.store.ListStore;
import com.extjs.gxt.ui.client.widget.button.Button;
import com.extjs.gxt.ui.client.widget.form.ComboBox;
import com.extjs.gxt.ui.client.widget.form.NumberField;
import com.extjs.gxt.ui.client.widget.form.Radio;
import com.extjs.gxt.ui.client.widget.form.RadioGroup;
import com.extjs.gxt.ui.client.widget.form.TextField;
import com.jcommerce.gwt.client.ModelNames;
import com.jcommerce.gwt.client.form.BeanObject;
import com.jcommerce.gwt.client.form.CategoryForm;
import com.jcommerce.gwt.client.model.ICategory;
import com.jcommerce.gwt.client.service.ListService;
import com.jcommerce.gwt.client.widgets.MyRadioGroup;

/**
 * Example file.
 */
public class CategoryPanel extends BaseEntityEditPanel {    
	
	@Override
	public String getEntityClassName() {
		return ModelNames.CATEGORY; 
	}
    @Override
    public Button getShortCutButton() {
//    	return btnAdd;
      Button buttonAddClone = new Button("分类列表");
      buttonAddClone.addSelectionListener(new SelectionListener<ButtonEvent>() {
          public void componentSelected(ButtonEvent ce) {
          	onButtonListClicked();
          }
      });
      return buttonAddClone;
    }
    public void onButtonListClicked() {
		CategoryListPanel.State newState = new CategoryListPanel.State();
		newState.execute();
    }
	ListStore<BeanObject> categoryList;
	ComboBox<BeanObject> fParentId;
	RadioGroup mfIsShow;
	RadioGroup mfShowInNav;
	
	public static class State extends BaseEntityEditPanel.State {
		public static final String SELECTED_PARENT_ID = "parentId";
		
		public String getPageClassName() {
			return CategoryPanel.class.getName();
		}
		public String getMenuDisplayName() {
			return "New Category";
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
    public static CategoryPanel getInstance() {
    	if(instance==null) {
    		instance = new CategoryPanel();
    	}
    	return instance;
    }
    private static CategoryPanel instance;
    private CategoryPanel() {
    }
    
    public String getDescription() {
        return "cwBasicTextDescription";
    }

    public String getName() {
    	if(!getCurState().getIsEdit())
			return "添加分类";
		else
			return "编辑分类";    	
    }
    

    
    @Override
    public void setupPanelLayout() {
        System.out.println("----------CategoryPanel");

        TextField<String> fText = CategoryForm.getNameField("分类名称：");
        fText.setFieldLabel("分类名称");
        formPanel.add(fText, sfd());
        
        categoryList = new ListStore<BeanObject>();
        fParentId = CategoryForm.getParentIdField();
        fParentId.setFieldLabel("上级分类");
        fParentId.setStore(categoryList);
        fParentId.setEmptyText("顶级分类");
        formPanel.add(fParentId, sfd());
        
        fText = CategoryForm.getMeasureUnitField();
        fText.setFieldLabel("数量单位");
        formPanel.add(fText, sfd());
        
        NumberField fNum = CategoryForm.getSortOrderField();
        fNum.setFieldLabel("排序");
        formPanel.add(fNum, tfd());

        mfIsShow = new MyRadioGroup();
		formPanel.add(mfIsShow, sfd());
		mfIsShow.setFieldLabel("是否显示");
		mfIsShow.setName(ICategory.IS_SHOW);

		mfIsShow.setSelectionRequired(true);
		Radio yes = new Radio();
		yes.setName(ICategory.IS_SHOW);
		yes.setValueAttribute("true");
		yes.setBoxLabel("是");
		mfIsShow.add(yes);
		
		Radio no = new Radio();
		no.setName(ICategory.IS_SHOW);
		no.setValueAttribute("false");
		no.setBoxLabel("否");
		mfIsShow.add(no);
        
		

		
		mfShowInNav = new MyRadioGroup();
		mfShowInNav.setFieldLabel("是否显示在导航栏");
		mfShowInNav.setName(ICategory.SHOW_IN_NAV);
		mfShowInNav.setSelectionRequired(true);
		yes = new Radio();
		yes.setName(ICategory.SHOW_IN_NAV);
		yes.setValueAttribute("1");
		yes.setBoxLabel("是");
		mfShowInNav.add(yes);
		
		no = new Radio();
		no.setName(ICategory.SHOW_IN_NAV);
		no.setValueAttribute("0");
		no.setBoxLabel("否");
		mfShowInNav.add(no);
        
		formPanel.add(mfShowInNav, sfd());
		
        fText = CategoryForm.getGradeField();
        fText.setFieldLabel("？价格区间个数");
        fText.setToolTip("该选项表示该分类下商品最低价与最高价之间的划分的等级个数，填0表示不做分级，最多不能超过10个。");
        formPanel.add(fText, tfd());
        
        fText = CategoryForm.getStyleField();
        fText.setFieldLabel("？分类的样式表文件");
        fText.setToolTip("您可以为每一个商品分类指定一个样式表文件。例如文件存放在 themes 目录下则输入：themes/style.css");
        formPanel.add(fText, tfd());

    }   
    
//    @Override
//    public void beforeSubmit() {
//
//    	mfIsShow.setName(ICategory.IS_SHOW);
//    	mfShowInNav.setName(ICategory.SHOW_IN_NAV);
//    }
    
    
    @Override
    public void postSuperRefresh() {
    	
		new ListService().listBeans(ModelNames.CATEGORY, new ListService.Listener() {
			@Override
			public void onSuccess(List<BeanObject> beans) {
		    	categoryList.removeAll();
				categoryList.add(beans);
				populateField(fParentId);
			}
		});
		

    }
    
	@Override
	public void gotoSuccessPanel() {
    	Success.State newState = new Success.State();
    	if(!getCurState().getIsEdit()) {
    		newState.setMessage("添加商品分类成功");
    	} else {
    		newState.setMessage("修改商品分类成功");
    	}
    	
    	CategoryListPanel.State choice1 = new CategoryListPanel.State();
    	newState.addChoice(CategoryListPanel.getInstance().getName(), choice1.getFullHistoryToken());
    	
    	newState.execute();
	}
}
