package com.jcommerce.gwt.client.panels;

import com.extjs.gxt.ui.client.widget.ContentPanel;
import com.extjs.gxt.ui.client.widget.Text;
import com.extjs.gxt.ui.client.widget.layout.TableData;
import com.extjs.gxt.ui.client.widget.layout.TableLayout;
import com.google.gwt.user.client.Element;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.jcommerce.gwt.client.ContentWidget;
import com.jcommerce.gwt.client.PageState;
import com.jcommerce.gwt.client.resources.Resources;
import com.jcommerce.gwt.client.service.RemoteService;


public class DashBoardPanel extends ContentWidget{
    
    public static interface Constants {
        String DashBoard_warning();
        String DashBoard_Order_Stats();
        String DashBoard_Goods_Stats();
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
    
    ContentPanel warningcp;
    Text warning;
    int i;
    
    protected void onRender(Element parent, int index) {
        super.onRender(parent, index);
        
        warningcp = new ContentPanel();
//        RowLayout rl = new RowLayout();
//        rl.setOrientation(Orientation.HORIZONTAL);
//        setLayout(rl);
        warningcp.setHeaderVisible(false);
        warningcp.setBodyStyle("backgroundColor: yellow;");

        warning = new Text(Resources.constants.DashBoard_warning());  
        warning.setHeight(50);
        warning.addStyleName("pad-text");  
//        warning.setStyleAttribute("backgroundColor", "white");  
        warning.setBorders(true);
        warningcp.add(warning);
//        cp.add(warning, new RowData(-1, 1, new Margins(4)));
        
        add(warningcp);
        
        ContentPanel cp = new ContentPanel();
        cp.setHeading(Resources.constants.DashBoard_Order_Stats());
        cp.setLayout(new TableLayout(4));
        Text label2 = new Text("Pending Orders:");
        Text label3 = new Text("TODO");
        Text label4 = new Text("UnConfirmed Orders");
        Text label5 = new Text("TODO");
        Text label6 = new Text("Unpaid Orders");
        Text label7 = new Text("TODO");
        TableData tdLabel = new TableData();
        tdLabel.setHeight("30");
        tdLabel.setWidth("20%");
        TableData tdValue = new TableData();
        tdLabel.setHeight("30");
        tdValue.setWidth("40%");
        
        cp.add(label2, tdLabel);
        cp.add(label3, tdValue);
        cp.add(label4, tdLabel);
        cp.add(label5, tdValue);
        cp.add(label6, tdLabel);
        cp.add(label7, tdValue);
        
//        add(cp, new RowData(1, 1, new Margins(4))); 
        add(cp);
        
        cp = new ContentPanel();
        cp.setHeading(Resources.constants.DashBoard_Goods_Stats());
        cp.setLayout(new TableLayout(4));
        label2 = new Text("Total Goods:");
        label3 = new Text("TODO");
        label4 = new Text("Number of new Goods");
        label5 = new Text("TODO");
        label6 = new Text("Number of hot Goods");
        label7 = new Text("TODO");
        
        cp.add(label2, tdLabel);
        cp.add(label3, tdValue);
        cp.add(label4, tdLabel);
        cp.add(label5, tdValue);
        cp.add(label6, tdLabel);
        cp.add(label7, tdValue);
        
//        add(cp, new RowData(1, 1, new Margins(4))); 
        add(cp);
    }
    
    @Override
    public void refresh(){
        System.out.println("refresh DashBoardPanel.. ");
        
        
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
    
}
