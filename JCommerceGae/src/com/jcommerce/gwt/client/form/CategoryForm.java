package com.jcommerce.gwt.client.form;

import java.util.Map;

import com.jcommerce.gwt.client.ValidationException;
import com.jcommerce.gwt.client.model.ICategory;

public class CategoryForm extends BeanObject implements ICategory {
	 
    public CategoryForm(){
    	super();
    }
    
    public CategoryForm (String modelName, Map<String, Object> values) {
        super(modelName, values);
    }
    
	public void validate() throws ValidationException{
		 
	 }
	 
	 
}
