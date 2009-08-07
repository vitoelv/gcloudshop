package com.jcommerce.gwt.client.panels.order;

import java.util.HashMap;
import java.util.Map;

import com.google.gwt.user.client.Element;
import com.jcommerce.gwt.client.ContentWidget;
import com.jcommerce.gwt.client.ModelNames;
import com.jcommerce.gwt.client.PageState;
import com.jcommerce.gwt.client.form.BeanObject;
import com.jcommerce.gwt.client.form.UserForm;
import com.jcommerce.gwt.client.model.IOrder;
import com.jcommerce.gwt.client.panels.OrderListPanel;
import com.jcommerce.gwt.client.service.CreateService;

import com.extjs.gxt.ui.client.Style.HorizontalAlignment;
import com.extjs.gxt.ui.client.widget.button.Button;
import com.extjs.gxt.ui.client.widget.form.Radio;   
import com.extjs.gxt.ui.client.widget.form.RadioGroup; 
import com.extjs.gxt.ui.client.widget.form.TextField;
import com.extjs.gxt.ui.client.widget.VerticalPanel;   
import com.extjs.gxt.ui.client.widget.HorizontalPanel;
import com.extjs.gxt.ui.client.widget.form.ComboBox;
import com.extjs.gxt.ui.client.event.ButtonEvent;
import com.extjs.gxt.ui.client.event.SelectionListener;
import com.extjs.gxt.ui.client.store.ListStore;

public class OrderUserPanel extends ContentWidget{

	private static OrderUserPanel instance = null;
	private State curState = new State();

	@Override
	protected State getCurState() {
		return curState;
	}

	@Override
	public String getDescription() {
		return "cwBasicTextDescription";
	}

	@Override
	public String getName() {
		return "添加订单";
	}
	
	public static OrderUserPanel getInstance(){
		if (instance == null) {
			instance = new OrderUserPanel();
		}
		return instance;
	}

	public static class State extends PageState {

		public static final String ID = "id";
		
		public void setId(String id){
			setValue(ID, id);
		}
		
		public String getId(){
			return (String)getValue(ID);
		}
		
		@Override
		public String getPageClassName() {
			return OrderUserPanel.class.getName();
		}
		@Override
		public String getMenuDisplayName() {
			return "添加订单";
		}
	}
	
	private Radio rAnonymousUser = null;
	private Radio rOtherUser = null;
	private RadioGroup rgroup = null;
	private TextField<String> searchCondition = null;
	private static final String SEARCH_FIELD_NAME = "searchCondition";
	private Button search = null;
	private ComboBox<UserForm> cbUser = null;
	private Button next = null;
	private Button cancel = null;
	
	@Override
	protected void onRender(Element parent, int index) {
		super.onRender(parent, index);
		
		VerticalPanel vp = new VerticalPanel();
		vp.setSpacing(10);
		rAnonymousUser = new Radio();
		rAnonymousUser.setName("user");
		rAnonymousUser.setBoxLabel("匿名用户");
		vp.add(rAnonymousUser);
		
		HorizontalPanel hp = new HorizontalPanel();
		
		rOtherUser = new Radio();
		rOtherUser.setName("user");
		rOtherUser.setBoxLabel("按会员编号或会员名搜索");
		hp.add(rOtherUser);
		
		rgroup = new RadioGroup();
		rgroup.add(rAnonymousUser);
		rgroup.add(rOtherUser);
		
		searchCondition = new TextField<String>();
		searchCondition.setName(SEARCH_FIELD_NAME);
		hp.add(searchCondition);
		
		search = new Button();
		search.setText("搜索");
		hp.add(search);
		
		cbUser = new ComboBox<UserForm>();
		ListStore<UserForm> store = new ListStore<UserForm>();
		cbUser.setStore(store);
		
		hp.add(cbUser);
		
		vp.add(hp);
		
		super.add(vp);
		
		HorizontalPanel hpButton = new HorizontalPanel();
		hpButton.setHorizontalAlign(HorizontalAlignment.CENTER);
		hpButton.setSpacing(10);
		hpButton.setWidth("100%");
		
		next = new Button();
		next.setText("下一步");
		next.addSelectionListener(new SelectionListener<ButtonEvent>(){
			
			@Override
			public void componentSelected(ButtonEvent ce) {
				createNewOrder();
			}});
		hpButton.add(next);
		
		cancel = new Button();
		cancel.setText("取消");
		cancel.addSelectionListener(new SelectionListener<ButtonEvent>(){

			@Override
			public void componentSelected(ButtonEvent ce) {
				gotoOrderList();
			}});
		hpButton.add(cancel);
		
		super.add(hpButton);
	}
	
	@Override
	public void refresh() {
		
	}
	
	private void createNewOrder(){
		Map<String, Object> props = new HashMap<String, Object>();
		props.put(IOrder.ID, null);
		props.put(IOrder.STATUS, IOrder.ORDER_INVALID);
		props.put(IOrder.SHIPPINGSTATUS, IOrder.SHIPPING_UNSHIPPED);
		props.put(IOrder.PAYSTATUS, IOrder.PAY_UNPAYED);
		
		
		BeanObject form = new BeanObject(ModelNames.ORDER, props);
		new CreateService().createOrder(form, new CreateService.Listener(){

			@Override
			public void onSuccess(String id) {
				// TODO goto orderGoodsPanel

			}});
	}
	
	private void gotoOrderList(){
		OrderListPanel.State newState = new OrderListPanel.State();
		newState.execute();
	}
}
