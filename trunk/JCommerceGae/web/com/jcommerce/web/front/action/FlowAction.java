package com.jcommerce.web.front.action;

import java.io.InputStream;
import java.io.StringBufferInputStream;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.struts2.ServletActionContext;
import org.json.JSONException;
import org.json.JSONObject;

import com.jcommerce.core.model.Cart;
import com.jcommerce.core.model.Constants;
import com.jcommerce.core.model.Order;
import com.jcommerce.core.model.Payment;
import com.jcommerce.core.model.Shipping;
import com.jcommerce.core.service.Condition;
import com.jcommerce.core.service.Criteria;
import com.jcommerce.gwt.client.model.ICart;
import com.jcommerce.web.to.Consignee;
import com.jcommerce.web.to.OrderWrapper;
import com.jcommerce.web.to.PaymentWrapper;
import com.jcommerce.web.to.ShippingWrapper;
import com.jcommerce.web.to.Total;
import com.opensymphony.xwork2.ActionContext;

public class FlowAction extends BaseAction {
	public void debug(String s) {
		System.out.println(" in [FlowAction]: "+s );
	}
    public static final String ADD_TO_CART = "addToCart";
    private InputStream jsonRes;


    
    private String addToCart(HttpServletRequest request) throws JSONException{
		setOrder(request);
		setShipping(request);
		setPayment(request);
		
		HttpSession session = request.getSession();
		
		String userId = (String)session.getAttribute(KEY_USER_ID);

		JSONObject goods = getReqAsJSON(request, "goods");
		String goodsId = goods.getString("goods_id");
		debug("in [addToCart]: goodsId="+goodsId);
		
		boolean suc = getWebManager().addToCart(
				goodsId , goods.getInt("number"), null, request.getSession().getId(), userId, null);
		
		JSONObject res = new JSONObject();;
		res.put("error", 0);
		res.put("content", "<B>cart info</B>");
		res.put("one_step_buy", "1");

		String out = res.toString();
		debug("in [addToCart]: out="+out);
		jsonRes = new StringBufferInputStream(out);	
    	return ADD_TO_CART;
    }
    
    private String cart(HttpServletRequest request) {

    	HttpSession session = request.getSession();
    	
    	session.setAttribute(KEY_FLOW_TYPE, Constants.CART_GENERAL_GOODS);
        /* 如果是一步购物，跳到结算中心 */
//        if ($_CFG['one_step_buy'] == '1')
    	if(true) {
    		return checkout(request);
    	}else {
		JSONObject json = new JSONObject();;
		try {
			json.put("error", 0);
			json.put("content", "<B>cart info</B>");
			json.put("one_step_buy", "1");
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String out = json.toString();
		debug("in [addToCart]: out="+out);
		jsonRes = new StringBufferInputStream(out);	
    	return ADD_TO_CART;
    	}
    }
    private Consignee getConsignee(String userId) {
    	Consignee consignee = (Consignee)getSession().getAttribute(KEY_FLOW_CONSIGNEE);
    	if(consignee != null) {
    		
    	} else {
    		
    	}
    	return consignee;
    }
    
    private boolean checkConsigneeInfo(Consignee consignee, int flowType) {
    	return consignee != null;
    }
    private String consignee(HttpServletRequest request) {
    	if(request.getAttribute(KEY_DIRECT_SHOPPING)!=null) {
    		getSession().setAttribute(KEY_DIRECT_SHOPPING, 1);
    	}
    	
		request.setAttribute("step", "consignee");
		
		
    	List<Consignee> consigneeList = (List<Consignee>)getSession().getAttribute(KEY_FLOW_CONSIGNEE);
    	if(consigneeList==null) {
    		consigneeList = new ArrayList<Consignee>();
    		Consignee consignee = new Consignee();
    		consignee.put("country", getShopConfigWrapper().get(CFG_KEY_SHOP_COUNTRY));
    		consigneeList.add(consignee);
    	}
    	request.setAttribute("consigneeList", consigneeList);
    	request.setAttribute("provinceList", new String[0]);
    	request.setAttribute("cityList", new String[0]);
    	request.setAttribute("districtList", new String[0]);
    	request.setAttribute("realGoodsCount", 1);
    	
    	return SUCCESS;
    }
    private String checkout(HttpServletRequest request) {
        /*------------------------------------------------------ */
        //-- 订单确认
        /*------------------------------------------------------ */
    	HttpSession session = request.getSession();
    	String userId = (String)session.getAttribute(KEY_USER_ID);
    	
    	int flowType = (Integer)session.getAttribute(KEY_FLOW_TYPE);
//    	if(flowType==CART_GROUP_BUY_GOODS) 
    	
    	Criteria cr = new Criteria();
    	Condition cond = new Condition();
    	cond.setField(ICart.SESSION_ID);
    	cond.setOperator(Condition.EQUALS);
    	cond.setValue(session.getId());
    	List<Cart> carts = (List<Cart>)getDefaultManager().getList(Cart.class.getName(), cr);
    	
    	Consignee consignee = getConsignee(userId);
    	
    	if(!checkConsigneeInfo(consignee, flowType)) {
    		return consignee(request);
    	}
    	
    	
		JSONObject json = new JSONObject();;
		try {
			json.put("error", 0);
			json.put("content", "<B>cart info</B>");
			json.put("one_step_buy", "1");
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String out = json.toString();
		debug("in [addToCart]: out="+out);
		jsonRes = new StringBufferInputStream(out);	
    	return ADD_TO_CART;
    }
	@Override
	public String execute() throws Exception {
		try {
			super.execute();
			
			ActionContext ctx = ActionContext.getContext();        
	        HttpServletRequest request = (HttpServletRequest)ctx.get(ServletActionContext.HTTP_REQUEST);      
	        
	        includeUrHere(request);
			includeOrderTotal(request);
			includeHelp(request);
			
			String step = request.getParameter("step");
			debug("step: "+step);
			
			

			
			if("add_to_cart".equals(step)) {
				return addToCart(request);
			} 
			else if("cart".equals(step)) {
				return cart(request);
			}
			else {
				return SUCCESS;
			}
			
			
		} catch (Exception ex) {
			ex.printStackTrace();
			throw new RuntimeException(ex);
		}
	}
	
	public void includeOrderTotal(HttpServletRequest request) {
		// TODO
		Total total = new Total();
		total.setAmountFormated("20.00元");
		request.setAttribute("total", total);
	}
	
	public void setOrder(HttpServletRequest request) {
		Order order = new Order();
		OrderWrapper ow = new OrderWrapper(order);
		request.setAttribute("order", ow);
	}
	public void setShipping(HttpServletRequest request) {
		Shipping shipping = new Shipping();
		ShippingWrapper sw = new ShippingWrapper(shipping);
		request.setAttribute("shipping", sw);
	}
	public void setPayment(HttpServletRequest request) {
		Payment payment = new Payment();
		PaymentWrapper pw = new PaymentWrapper(payment);
		request.setAttribute("payment", pw);
	}

	public InputStream getJsonRes() {
		return jsonRes;
	}
}
