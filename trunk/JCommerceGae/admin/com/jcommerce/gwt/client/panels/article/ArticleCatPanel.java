package com.jcommerce.gwt.client.panels.article;

import java.util.List;

import com.extjs.gxt.ui.client.store.ListStore;
import com.extjs.gxt.ui.client.widget.form.ComboBox;
import com.extjs.gxt.ui.client.widget.form.NumberField;
import com.extjs.gxt.ui.client.widget.form.TextArea;
import com.extjs.gxt.ui.client.widget.form.TextField;
import com.jcommerce.gwt.client.ModelNames;
import com.jcommerce.gwt.client.PageState;
import com.jcommerce.gwt.client.form.ArticleCatForm;
import com.jcommerce.gwt.client.form.BeanObject;
import com.jcommerce.gwt.client.panels.BaseEntityEditPanel;
import com.jcommerce.gwt.client.panels.Success;
import com.jcommerce.gwt.client.panels.member.UserListPanel;
import com.jcommerce.gwt.client.resources.Resources;
import com.jcommerce.gwt.client.service.ListService;
import com.jcommerce.gwt.client.widgets.MyRadioGroup;

public class ArticleCatPanel extends BaseEntityEditPanel{
	public static interface Constants {
		String ArticleCat_name();
		String ArticleCat_parentCat();
		String ArticleCat_topCat();
		String ArticleCat_sortOrder();
		String ArticleCat_isInNav();
		String ArticleCat_keyword();
		String ArticleCat_desc();
		String ArticleCat_add_success();
		String ArticleCat_modify_success();
		String ArticleCat_add();
	}
	
	public static class State extends BaseEntityEditPanel.State{

		@Override
		public String getPageClassName() {
			// TODO Auto-generated method stub
			return ArticleCatPanel.class.getName();
		}
		
	}
	private State curState = new State();

	@Override
	protected State getCurState() {
		// TODO Auto-generated method stub
		return curState;
	}
	private static ArticleCatPanel instance;
	public static ArticleCatPanel getInstance(){
		if(instance == null){
			instance = new ArticleCatPanel();
		}
		return instance;
	}

	@Override
	protected String getEntityClassName() {
		// TODO Auto-generated method stub
		return ModelNames.ARTICLE_CAT;
	}
	
	ListStore<BeanObject> articleCat;
	ComboBox<BeanObject> fListArticleCat;

	@Override
	protected void postSuperRefresh() {
		    	
		new ListService().listBeans(ModelNames.ARTICLE_CAT, new ListService.Listener() {
			@Override
			public void onSuccess(List<BeanObject> beans) {
		    	articleCat.removeAll();
		    	articleCat.add(beans);
				populateField(fListArticleCat);
			}
		});
		


	}

	@Override
	protected void setupPanelLayout() {
		System.out.println("=============AticleCatPanel============");
		TextField<String> tf = ArticleCatForm.getNameField(Resources.constants.ArticleCat_name()+": ");
		tf.setFieldLabel(Resources.constants.ArticleCat_name());
		formPanel.add(tf, sfd());
		
		articleCat = new ListStore<BeanObject>();
		fListArticleCat = ArticleCatForm.getParentCatField();
		fListArticleCat.setStore(articleCat);
		fListArticleCat.setEmptyText(Resources.constants.ArticleCat_topCat());
		fListArticleCat.setFieldLabel(Resources.constants.ArticleCat_parentCat());
		formPanel.add(fListArticleCat, sfd());
		
		NumberField nf = ArticleCatForm.getOrderField();
		nf.setFieldLabel(Resources.constants.ArticleCat_sortOrder());
		formPanel.add(nf,tfd());
		
		MyRadioGroup rg = ArticleCatForm.getIsShowInNavField();
		rg.setFieldLabel(Resources.constants.ArticleCat_isInNav());
		formPanel.add(rg,sfd());
		
		TextField<String> keyword = ArticleCatForm.getKeywordField();
		keyword.setFieldLabel(Resources.constants.ArticleCat_keyword());
		formPanel.add(keyword,sfd());
		
		TextArea ta = ArticleCatForm.getDescField();
		ta.setFieldLabel(Resources.constants.ArticleCat_desc());
		formPanel.add(ta,lfd());
		
	}
	@Override
	public void gotoSuccessPanel() {
    	Success.State newState = new Success.State();

    	if(!getCurState().getIsEdit()) {
    		newState.setMessage(Resources.constants.ArticleCat_add_success());
    	} else {
    		newState.setMessage(Resources.constants.ArticleCat_modify_success());
    	}
    	
    	ArticleCatListPanel.State choice1 = new ArticleCatListPanel.State();
    	newState.addChoice(ArticleCatListPanel.getInstance().getName(), choice1.getFullHistoryToken());
    	
    	newState.execute();
	}

	@Override
	public String getDescription() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return Resources.constants.ArticleCat_add();
	}

}
