package com.jcommerce.gwt.client.model;


/**  
 * generated with my extension of middleGen 
 * @author <a href="http://code.google.com/p/gcloudshop/">Leon</a>
 */
 
public interface IRegion extends IModelObject {
   
   // merge
    public static final String OLD_NAME = "old_name";
    public static final Long TYPE_WORLD = -1l;
	public static final Long TYPE_COUNTRY = 0l;
	public static final Long TYPE_PROVINCE = 1l;
	public static final Long TYPE_CITY = 2l;
	public static final Long TYPE_DISTRICT = 3l;
	
	public static final String DEFAULT_AGENCY_ID = "0";
	public static final String COUNTRY_PARENT_ID = "0";   
      
  // fields
  public static final String REGION_ID = "regionId"; 
  public static final String PARENT_ID = "parentId"; 
  public static final String REGION_NAME = "regionName"; 
  public static final String REGION_TYPE = "regionType"; 
  public static final String AGENCY_ID = "agencyId"; 


}
