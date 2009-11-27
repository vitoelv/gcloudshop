package com.jcommerce.web.to;

import java.util.ArrayList;
import java.util.List;

import com.jcommerce.core.model.Category;
import com.jcommerce.core.model.ModelObject;

public class CategoryWrapper extends BaseModelWrapper {

	List<CategoryWrapper> children = new ArrayList<CategoryWrapper>();
	
	Category category;
	@Override
	protected Object getWrapped() {
		return getCategory();
	}
	public CategoryWrapper(ModelObject category) {
		super();
		this.category = (Category)category;
	}
	
	public Category getCategory() {
		return category;
	}
	
	public String getUrl() {
		return "category.action?id="+category.getPkId();
	}
	public List<CategoryWrapper> getChildren() {
		return children;
	}
	public void setChildren(List<CategoryWrapper> children) {
		this.children = children;
	}

	

	
}
