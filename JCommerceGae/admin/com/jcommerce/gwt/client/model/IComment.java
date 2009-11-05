package com.jcommerce.gwt.client.model;


/**  
 * generated with my extension of middleGen 
 * @author <a href="http://code.google.com/p/gcloudshop/">Leon</a>
 */
 
public interface IComment extends IModelObject {
   
   // relation
   public static final Long TYPE_ARTICLE = 1l;
   public static final Long TYPE_GOODS = 0l;
	
   public static final Long STATUS_ACTIVE = 1l;
   public static final Long STATUS_INACTIVE = 0l;
  // fields
  public static final String COMMENT_ID = "commentId"; 
  public static final String COMMENT_TYPE = "commentType"; 
  public static final String ID_VALUE = "idValue"; 
  public static final String EMAIL = "email"; 
  public static final String USER_NAME = "userName"; 
  public static final String CONTENT = "content"; 
  public static final String COMMENT_RANK = "commentRank"; 
  public static final String ADD_TIME = "addTime"; 
  public static final String IP_ADDRESS = "ipAddress"; 
  public static final String STATUS = "status"; 
  public static final String PARENT_ID = "parentId"; 
  public static final String USER_ID = "userId"; 


}
