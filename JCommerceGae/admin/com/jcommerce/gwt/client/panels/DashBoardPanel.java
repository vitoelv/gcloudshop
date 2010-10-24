package com.jcommerce.gwt.client.panels;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import com.extjs.gxt.ui.client.util.Margins;
import com.extjs.gxt.ui.client.widget.ContentPanel;
import com.extjs.gxt.ui.client.widget.Text;
import com.extjs.gxt.ui.client.widget.layout.FlowData;
import com.extjs.gxt.ui.client.widget.layout.RowData;
import com.extjs.gxt.ui.client.widget.layout.TableData;
import com.extjs.gxt.ui.client.widget.layout.TableLayout;
import com.google.gwt.user.client.Element;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.jcommerce.gwt.client.ContentWidget;
import com.jcommerce.gwt.client.ModelNames;
import com.jcommerce.gwt.client.PageState;
import com.jcommerce.gwt.client.model.IGoods;
import com.jcommerce.gwt.client.model.IOrderInfo;
import com.jcommerce.gwt.client.resources.Resources;
import com.jcommerce.gwt.client.service.Condition;
import com.jcommerce.gwt.client.service.CountStatisticValueService;
import com.jcommerce.gwt.client.service.Criteria;
import com.jcommerce.gwt.client.service.RemoteService;


public class DashBoardPanel extends ContentWidget{
    

    public static interface Constants {
        String DashBoard_warning();
        String DashBoard_Order_Stats();
        String DashBoard_Goods_Stats();
        
        String DashBoard_Pending_Orders();
        String DashBoard_UnConfirmed_Orders();
        String DashBoard_UnPaid_Orders();
        
        String DashBoard_Total_Goods();
        String DashBoard_Total_Hot_Goods();
        String DashBoard_Total_New_Goods();
    }
    
    public static class State extends PageState {
        public String getPageClassName() {
                return DashBoardPanel.class.getName();
        }
        public String getMenuDisplayName() {
                return "DashBoard";
        }
    }
    private State curState = new State();
    public State getCurState() {
            return curState;
    }
    public void setCurState(State curState) {
            this.curState = curState;
    }


    @Override
    public String getDescription() {
        return "cwBasicTextDescription";
    }

    @Override
    public String getName() {
        return "DashBoard";
    }
    private static DashBoardPanel instance;
    public static DashBoardPanel getInstance() {
        if(instance == null) {
                instance = new DashBoardPanel();
        }
        return instance;
    }
	Map<String,Map<Text,Criteria>> conds = null;
    ContentPanel warningcp;
    Text warning;
    int i;
    Text totalGoods=null;
    Text numberOfNG=null;
    Text numberOfHG=null;
    Text pendingOrder=null;
    Text unConfirmOrder=null;
    Text unPaidOrder=null;
    
    
    ContentPanel cp =null;
    TableData tdValue =null;
    
    protected void onRender(Element parent, int index) {
        super.onRender(parent, index); 
        
        warningcp = new ContentPanel(); 
        warningcp.setHeaderVisible(false);
        warningcp.setBodyStyle("backgroundColor: yellow;");

        warning = new Text(Resources.constants.DashBoard_warning());  
        warning.setHeight(50);
        warning.addStyleName("pad-text");   
        warning.setBorders(true);
        warningcp.add(warning); 
        
        add(warningcp);
        
        cp = new ContentPanel();
        cp.setHeading(Resources.constants.DashBoard_Order_Stats());
        cp.setLayout(new TableLayout(4));
        Text label2 = new Text(Resources.constants.DashBoard_Pending_Orders());
        pendingOrder = new Text("TODO");
        Text label4 = new Text(Resources.constants.DashBoard_UnConfirmed_Orders());
        unConfirmOrder = new Text("TODO");
        Text label6 = new Text(Resources.constants.DashBoard_UnPaid_Orders());
        unPaidOrder = new Text("TODO");
        TableData tdLabel = new TableData();
        tdLabel.setHeight("30");
        tdLabel.setWidth("20%");
        tdValue = new TableData();
        tdLabel.setHeight("30");
        tdValue.setWidth("40%");
        
        cp.add(label2, tdLabel);
        cp.add(pendingOrder, tdValue);
        cp.add(label4, tdLabel);
        cp.add(unConfirmOrder, tdValue);
        cp.add(label6, tdLabel);
        cp.add(unPaidOrder, tdValue); 
        
        add(cp);
        
        cp = new ContentPanel();
        cp.setHeading(Resources.constants.DashBoard_Goods_Stats());
        cp.setLayout(new TableLayout(4));
        label2 = new Text(Resources.constants.DashBoard_Total_Goods());
        totalGoods = new Text("TODO");
        label4 = new Text(Resources.constants.DashBoard_Total_New_Goods());
        numberOfNG = new Text("TODO");
        label6 = new Text(Resources.constants.DashBoard_Total_Hot_Goods());
        numberOfHG = new Text("TODO");
        
        cp.add(label2, tdLabel);
        cp.add(totalGoods, tdValue);
        cp.add(label4, tdLabel);
        cp.add(numberOfNG, tdValue);
        cp.add(label6, tdLabel);
        cp.add(numberOfHG, tdValue);
         
         add(cp); 
    }
    
    
   
	@Override
    public void refresh(){
        System.out.println("refresh DashBoardPanel.. ");
        
       //
        Map<String,Map<Text,Criteria>> conds = this.compositeConditions();
        //modelnames
        Set modelNames = conds.keySet();
        for (Iterator i = modelNames.iterator(); i.hasNext();) {
          String modelName = (String) i.next();
          Map<Text,Criteria> texts = (Map<Text,Criteria>) conds.get(modelName); 
          
          //texts
          Set textsKeys = texts.keySet();
          for (Iterator h = textsKeys.iterator(); h.hasNext();) {
        	  final Text text = (Text) h.next();
        	  Criteria cia = (Criteria) texts.get(text); 
        	  //update values on pages
        	  new CountStatisticValueService().countValue( modelName , cia , new CountStatisticValueService.Listener() {
      			
      			@Override
      			public void onSuccess(Integer result ) {
      					text.setText(result.toString()); 
      			}
      			
      			@Override
      			public void onFailure(Throwable caught){
      					text.setText("There is a error loading data. Please try again later!");
      					
      			}
      		});              
          }          
        }
        
        
        
        
        
        RemoteService.getSpecialService().isDefaultAdminEnabled(
            new AsyncCallback<Boolean>() {
                public void onFailure(Throwable caught) {
                    Window.alert("exception in getShopConfigMetaValue: cause="+caught.getMessage());
                }

                public void onSuccess(Boolean result) {
                    if(result) {
                        warningcp.show();
                    } else {
                        warningcp.hide();
                    }
                }
                
            });
        
    }
    
    /**
     * Composites conditions for querying states
     * @return Map conditions 
     */
    public Map compositeConditions(){
    	if(conds != null && conds.size()> 0)
    		return conds;
    	
		conds = new HashMap();
		 
		Map<Text,Criteria> texts= new HashMap();
		Criteria cia = new Criteria();
		Condition condition = new Condition();
       
        //Pending Order
		condition.setField(IOrderInfo.ORDER_STATUS);
		condition.setOperator(Condition.EQUALS);
		condition.setValue(Integer.toString(IOrderInfo.SS_SHIPPED));
		cia.addCondition(condition);
		texts.put(pendingOrder, cia);
		
		//UnConfirmed Order 
		cia = new Criteria();
		condition = new Condition();
		condition.setField(IOrderInfo.ORDER_STATUS);
		condition.setOperator(Condition.EQUALS);
		condition.setValue(Integer.toString(IOrderInfo.PS_UNPAYED));
		cia.addCondition(condition);
		texts.put(unConfirmOrder, cia);
		
		
		//Unpaid Order 
		cia = new Criteria();
		condition = new Condition();
		condition.setField(IOrderInfo.ORDER_STATUS);
		condition.setOperator(Condition.EQUALS);
		condition.setValue(Integer.toString(IOrderInfo.OS_UNCONFIRMED));
		cia.addCondition(condition);
		texts.put(unPaidOrder, cia);
		
		conds.put(ModelNames.ORDERINFO, texts);
		
		
		//----------------------------------------------
		
		 texts= new HashMap();
		
		//Total Goods
		cia = new Criteria();
		condition = new Condition();
		condition.setField(IGoods.IS_ON_SALE);
		condition.setOperator(Condition.EQUALS);
		condition.setValue("true");
		cia.addCondition(condition);
		texts.put(totalGoods, cia);
		
		//Number of new Goods
		cia = new Criteria();
		condition = new Condition();
		condition.setField(IGoods.IS_NEW);
		condition.setOperator(Condition.EQUALS);
		condition.setValue("true");
		cia.addCondition(condition);
		texts.put(numberOfNG, cia);
		
		//Number of hot Goods
		cia = new Criteria();
		condition = new Condition();
		condition.setField(IGoods.IS_HOT);
		condition.setOperator(Condition.EQUALS);
		condition.setValue("true");
		cia.addCondition(condition);
		texts.put(numberOfHG, cia);
		
		conds.put(ModelNames.GOODS, texts);
		
		return conds;
	}
	
    
}
