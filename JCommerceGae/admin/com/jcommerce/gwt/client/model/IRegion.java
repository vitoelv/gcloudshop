package com.jcommerce.gwt.client.model;

public interface IRegion {
	
	public static final String ID = "id";
	public static final String REGION_NAME = "regionName";
    public static final String REGION_TYPE = "regionType";
	public static final String PARENTID = "parentId";
    public static final String AGENCYID = "agencyId";

    public static final String OLD_NAME = "old_name";
    public static final String TYPE_WORLD = "-1";
	public static final String TYPE_COUNTRY = "0";
	public static final String TYPE_PROVINCE = "1";
	public static final String TYPE_CITY = "2";
	public static final String TYPE_DISTRICT = "3";
	
	public static final String DEFAULT_AGENCY_ID = "0";
	public static final String COUNTRY_PARENT_ID = "0";
}
