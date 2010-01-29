package com.jcommerce.web.to;

import java.util.ArrayList;
import java.util.List;

import com.jcommerce.core.model.ArticleCat;
import com.jcommerce.core.model.ModelObject;

public class ArticleCatWrapper extends BaseModelWrapper {

	List<ArticleCatWrapper> children = new ArrayList<ArticleCatWrapper>();
	
	ArticleCat articleCat;
	@Override
	protected Object getWrapped() {
		return getCategory();
	}
	public ArticleCatWrapper(ModelObject articleCat) {
		super();
		this.articleCat = (ArticleCat)articleCat;
	}
	
	public ArticleCat getCategory() {
		return articleCat;
	}
	
	public String getUrl() {
		return "articleCat.action?id="+articleCat.getPkId();
	}
	public List<ArticleCatWrapper> getChildren() {
		return children;
	}
	public void setChildren(List<ArticleCatWrapper> children) {
		this.children = children;
	}

	public String getName(){
		return articleCat.getCatName();
	}

	
}
