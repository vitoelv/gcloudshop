package com.jcommerce.gwt.client.model;

public interface IAttribute {
    public static final int TYPE_NEEDNOTSELECT = 0; 
    public static final int TYPE_NEEDSELECT = 1; 

    public static final int INPUT_SINGLELINETEXT = 0;
    public static final int INPUT_MULTIPLELINETEXT = 2;
    public static final int INPUT_CHOICE = 1;

	public static final String ID = "id";
	public static final String NAME = "name";
	public static final String GOODSTYPE = "goodsType";
	public static final String GOODSTYPE_NAME = "goodsType_name";
	public static final String INPUTTYPE = "inputType";
    public static final String TYPE = "type";
    public static final String VALUES = "values";
    public static final String INDEX = "index";
    public static final String SORTORDER = "sortOrder";
    public static final String LINKED = "linked";
    public static final String GROUP = "group";
    
    
}
