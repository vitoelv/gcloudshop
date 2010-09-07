package com.jcommerce.gwt.client.service;

 
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.jcommerce.gwt.client.IDefaultServiceAsync;

/**
 * 
 * @author qyang 
 *
 */
public class CountStatisticValueService extends RemoteService {	
	
	/**
	 * Count value   
	 * @param modelName
	 * @param criteria
	 * @param listener
	 */
	public void countValue(final String modelName , Criteria criteria ,final Listener listener){
		 if (modelName == null) {
	            throw new RuntimeException("model = null");
	        }
		 Date start = new Date(); 
		 System.out.println(DateTimeFormat.getShortTimeFormat().format(start));
		 final IDefaultServiceAsync ids = this.getDefaultService();
		 ids.getCount(modelName, criteria, 
				 new AsyncCallback<Integer>(){

					 
					public synchronized void onFailure(Throwable caught) {
						 System.out.println("ListService: countValue onFailure(model="+modelName+", error="+caught);
						 if (listener != null) {
							 	listener.onFailure(caught);
			                }
					}

					 
					public synchronized void onSuccess(Integer result) {
						if (listener != null) {
						 	listener.onSuccess(result);
		                }
						
					}
		 }
		 );		 
		 Date end = new Date();
		 System.out.println(DateTimeFormat.getShortTimeFormat().format(end)); 
		 System.out.println("The total time counting : "+ (end.getTime()-start.getTime())+" milliseconds");

	}
	
	 
	
	public static abstract class Listener {
		public abstract void onSuccess(Integer result);
		
		public void onFailure(Throwable caught){
			
		}
	}
	 
	
 
}
