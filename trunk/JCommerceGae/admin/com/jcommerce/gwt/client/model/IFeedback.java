package com.jcommerce.gwt.client.model;


/**  
 * generated with my extension of middleGen 
 * @author <a href="http://code.google.com/p/gcloudshop/">Leon</a>
 */
 
public interface IFeedback extends IModelObject {
   
   // relation
	public static final int TYPE_LEAVEWORD = 0; // 留言
    public static final int TYPE_COMPLAINT = 1; // 投诉
    public static final int TYPE_ASK = 2; // 询问
    public static final int TYPE_AFTERMARKET = 3; // 售后
    public static final int TYPE_BUY = 4; // 求购  
  // fields
  public static final String MSG_ID = "msgId"; 
  public static final String PARENT_ID = "parentId"; 
  public static final String USER_ID = "userId"; 
  public static final String USER_NAME = "userName"; 
  public static final String USER_EMAIL = "userEmail"; 
  public static final String MSG_TITLE = "msgTitle"; 
  public static final String MSG_TYPE = "msgType"; 
  public static final String MSG_CONTENT = "msgContent"; 
  public static final String MSG_TIME = "msgTime"; 
  public static final String MESSAGE_IMG = "messageImg"; 
  public static final String ORDER_ID = "orderId"; 
  public static final String MSG_AREA = "msgArea"; 


}
