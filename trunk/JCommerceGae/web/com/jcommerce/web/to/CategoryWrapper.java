package com.jcommerce.web.to;

import com.jcommerce.core.model.Category;
import com.jcommerce.core.model.ModelObject;

public class CategoryWrapper extends BaseModelWrapper {


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
	
	public String[] getChildren() {
		// TODO category.children
		return new String[0]; 
	}
}
