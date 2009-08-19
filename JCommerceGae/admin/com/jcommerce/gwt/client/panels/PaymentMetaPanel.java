package com.jcommerce.gwt.client.panels;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.google.gwt.user.client.Element;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.ClickListener;
import com.google.gwt.user.client.ui.Hidden;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.TextArea;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;
import com.jcommerce.gwt.client.ContentWidget;
import com.jcommerce.gwt.client.PageState;
import com.jcommerce.gwt.client.widgets.ChoicePanel;
import com.jcommerce.gwt.client.widgets.ColumnPanel;

public class PaymentMetaPanel extends ContentWidget {

    private static PaymentMetaPanel instance;
    public static PaymentMetaPanel getInstance() {
        if(instance == null) {
            instance = new PaymentMetaPanel();
        }
        return instance;
    }
    private PaymentMetaPanel() {
    }
    public static class State extends PageState {
        
        public static final String ID = "ID";
        
        public String getPageClassName() {
            return PaymentMetaPanel.class.getName();
        }
        public void setPkId(int id) {
            setValue(ID, String.valueOf(id));
        }
        public int getPkId() {
            return Integer.valueOf((String)getValue(ID));
        }
        
    }
    private State curState = new State();
    private ColumnPanel contentPanel;
    Hidden isonline;
    Hidden iscod;
    Hidden idhidden;
    Hidden code;
    Hidden order;
    @Override
    public String getDescription() {
        return "cwBasicTextDescription";
    }

    @Override
    public String getName() {
        return "编辑支付方�?";
    }
    Element parent;

    protected void onRender(Element parent, int index) {
        super.onRender(parent, index);
        System.out.println("----- onRender PaymentMeta---");
        
        contentPanel = new ColumnPanel();
        add(contentPanel);
        
//        TextBox tb = new TextBox();
//        tb.setText("abc");
//        contentPanel.createPanel(PaymentConfigMetaForm.NAME, "支付方�?�??称", tb);
//        this.parent = parent;
        
    }
    
    public void refresh() {
        System.out.println("----- refresh PaymentMeta---");
        State state = getCurState();
        int id = state.getPkId();
        System.out.println("id: "+id);
        contentPanel.resetTable();
        
//        getService().getPaymentConfigMeta(id, new AsyncCallback<PaymentConfigMetaForm>() {
//
//            public void onFailure(Throwable caught) {
//                System.out.println("failed!!!! "+caught.getMessage());
//            }
//
//            public void onSuccess(PaymentConfigMetaForm result) {
//                System.out.println("result: \n");
//                System.out.println(result.toString());
//                
//
//                TextBox tb = new TextBox();
//                tb.setText(result.getName());
//                contentPanel.createPanel(PaymentConfigMetaForm.NAME, "支付方�?�??称", tb);
//                
//                TextArea ta = new TextArea();
//                ta.setHeight("180px");
//                ta.setWidth("250px");
//                ta.setText(result.getDescription());
//                contentPanel.createPanel(PaymentConfigMetaForm.DESC, "支付方�?�??述", ta);
//                
//                Map<String, PaymentConfigFieldMetaForm> fieldMetas = result.getFieldMetas();
//                Map<String, String> keyValues = result.getFieldValues();
//                for(String key:fieldMetas.keySet()) {
//                    String value = keyValues.get(key);
//                    System.out.println("key: "+key+", value: "+value);
//                    
//                    PaymentConfigFieldMetaForm meta = fieldMetas.get(key);
//                    Object options = meta.getOptions();
//                    if(options==null) {
//                        TextBox textBox = new TextBox();
//                        textBox.setText(value);
//                        contentPanel.createPanel(key, meta.getLable(), textBox);
//                    }
//                    else {
//                        Map<String, String> optionMap = (Map<String, String>)options;
//                        List<ChoicePanel.Item> typeItems = new ArrayList<ChoicePanel.Item>();
//                        for(String option:optionMap.keySet()) {
//                            String lable = optionMap.get(option);
//                            typeItems.add(new ChoicePanel.Item(lable, option));
//                        }
//                        ChoicePanel choicePanel = new ChoicePanel(value,typeItems); 
//                        contentPanel.createPanel(key, meta.getLable(), choicePanel);
//                    }
//                }
//
//                tb = new TextBox();
//                tb.setText(result.getPayFee());
//                contentPanel.createPanel(PaymentConfigMetaForm.PAYFEE, "支付手续费", tb);
//                
//                
//                Label lb = new Label();
//                if(result.isCod()){
//                    lb.setText("是");
//                }else {
//                    lb.setText("�?�");
//                }
//                
//                contentPanel.createPanel(null, "货到付款？", lb);
//                
//                lb = new Label();
//                if(result.isOnline()){
//                    lb.setText("是");
//                }else {
//                    lb.setText("�?�");
//                }
//                contentPanel.createPanel(null, "在线支付？", lb);
//                
//                Button btnNew = new Button();    
//                Button btnCancel = new Button();    
//                HorizontalPanel panel = new HorizontalPanel();
//                panel.setSpacing(10);
//                btnNew.setText("确定");        
//                btnCancel.setText("�?置");
//                panel.add(btnNew);        
//                panel.add(btnCancel);
//                contentPanel.createPanel(null, null, panel);
//                
//                isonline = new Hidden(PaymentConfigMetaForm.ISONLINE, String.valueOf(result.isOnline()));
//                iscod = new Hidden(PaymentConfigMetaForm.ISCOD, String.valueOf(result.isCod()));
//                idhidden = new Hidden(PaymentConfigMetaForm.ID, String.valueOf(result.getPkId()));
//                code = new Hidden(PaymentConfigMetaForm.CODE, result.getCode());
//                order = new Hidden(PaymentConfigMetaForm.ORDER, "0");
//
//                contentPanel.createPanel(PaymentConfigMetaForm.ISONLINE, null, isonline);
//                contentPanel.createPanel(PaymentConfigMetaForm.ISCOD, null, iscod);
//                contentPanel.createPanel(PaymentConfigMetaForm.ID, null, idhidden);
//                contentPanel.createPanel(PaymentConfigMetaForm.CODE, null, code);
//                contentPanel.createPanel(PaymentConfigMetaForm.ORDER, null, order);
//                
//                
//                btnNew.addClickListener(new ClickListener() {
//                    public void onClick(Widget sender) {
//                            Map<String, Object> v = contentPanel.getValues();
//                            
//                            getService().savePayment(v, new AsyncCallback<Boolean>() {
//
//                                public void onFailure(Throwable caught) {
//                                    System.out.println("save failure: "+caught.getMessage());
//                                    
//                                }
//
//                                public void onSuccess(Boolean result) {
//                                    System.out.println("onSuccess: "+result.toString());
//                                    
//                                    if(result) {
//                                        Success.State newState = new Success.State();
//                                        newState.setMessage("编辑�?功");
//                                    
//                                        PaymentMetaList.State listState = new PaymentMetaList.State();
//                                        newState.addChoice("支付方�?列表", listState.getFullHistoryToken());
//                                    
//                                        newState.execute();
//                                    }
//                                    else {
//                                        Window.alert("�?存结果失败�?�?�?");
//                                    }
//                                   
//                                }
//                                
//                            });
//                           
//                    }
//                });
//
//                btnCancel.addClickListener(new ClickListener() {
//                    public void onClick(Widget sender) {
//                        contentPanel.clearValues();
//                    }            
//                });       
//            }
//             
//        });
        

        
        System.out.println("----- finish PaymentMeta---");
    }
    public State getCurState() {
        return curState;
    }
    public void setCurState(State curState) {
        this.curState = curState;
    }

}
