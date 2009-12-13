package com.jcommerce.web.to;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.jcommerce.core.model.ModelObject;
import com.jcommerce.core.model.OrderInfo;
import com.jcommerce.core.model.Payment;
import com.jcommerce.core.service.Condition;
import com.jcommerce.core.service.Criteria;
import com.jcommerce.core.service.IDefaultManager;
import com.jcommerce.gwt.client.ModelNames;
import com.jcommerce.gwt.client.model.IOrderInfo;
import com.jcommerce.web.util.PrintfFormat;
import com.jcommerce.web.util.WebFormatUtils;

public class OrderInfoWrapper extends BaseModelWrapper {

	OrderInfo orderInfo;
	private IDefaultManager manager = null;
	@Override
	protected Object getWrapped() {
		return getOrderInfo();
	}
	public OrderInfoWrapper(ModelObject order) {
		super();
		this.orderInfo = (OrderInfo)order;
	}
	
	public OrderInfo getOrderInfo() {
		return orderInfo;
	}
	
	
	public String getTotalFee() {
		OrderInfo oi = getOrderInfo();
		double total = oi.getGoodsAmount() + oi.getShippingFee() + 
			oi.getInsureFee() + oi.getPayFee()+ 
			oi.getPackFee() + oi.getCardFee() + 
			oi.getTax() - oi.getDiscount();
		return WebFormatUtils.priceFormat(total);
	}
	
	/*修改，获得订单时间*/
	public String getOrderTime() {
		long addTime = getOrderInfo().getAddTime();
		Date date = new Date(addTime);
		SimpleDateFormat formatter = new SimpleDateFormat ("yyyy-MM-dd HH:mm:ss");
		String orderTime = formatter.format(date);
		return orderTime;
	}
	
    // for template
    public String getOrderId() {
    	return getOrderInfo().getPkId();
    }
    
    public String getPostscript() {
    	if( orderInfo.getPostscript() != null){
			return orderInfo.getPostscript();
	    }
		else{
			return "";
		}
	}
    
    public String getHandler() {
    	long orderStatues = getOrderInfo().getOrderStatus();
    	long shippingStatues = getOrderInfo().getShippingStatus();
    	long payStatues = getOrderInfo().getPayStatus();
    	Lang lang = Lang.getInstance();
    	String handler = null;
    	
    	if(orderStatues == IOrderInfo.OS_UNCONFIRMED) {
    		handler = "<a href=\"user.action?act=cancel_order&orderId=" + getOrderId() + "\" onclick=\"if (!confirm('" + lang.getString("confirmCancel") + "')) return false;\">" + lang.getString("cancel") + "</a>";
    	}
    	else if(orderStatues == IOrderInfo.OS_CONFIRMED) {
    		/* 对配送状态的处理 */
            if (shippingStatues == IOrderInfo.SS_SHIPPED)
            {
                handler = "<a href=\"user.action?act=affirm_received&orderId=" + getOrderId() + "\" onclick=\"if (!confirm('" + lang.getString("confirmReceived") + "')) return false;\">" + lang.getString("received") + "</a>";
            }
            else if (shippingStatues == IOrderInfo.SS_RECEIVED)
            {
            	handler = "<span style='color:red'>" + lang.getString("ssReceived") + "</span>";
            }
            else
            {
                if (payStatues == IOrderInfo.PS_UNPAYED)
                {
                    handler = "<a href=\"user.action?act=order_detail&orderId=" + getOrderId() + ">" + lang.getString("payMoney") + "</a>";
                }
                else
                {
                	handler = "<a href=\"user.action?act=order_detail&orderId=" + getOrderId() + ">" + lang.getString("viewOrder") + "</a>";
                }

            }
    	}
    	else
        {
        	Map map = (Map)lang.get("os");
        	String statues = "";
        	if(orderStatues == 2)
        		statues = (String) map.get("OS_CANCELED");
        	else if(orderStatues == 3)
        		statues = (String) map.get("OS_INVALID");
        	else
        		statues = (String) map.get("OS_RETURNED");
    		handler = "<span style='color:red'>" + statues + "</span>";
        }
    	
    	return handler;
    }
    
    public String getConfirmTime() {
    	long confirmTime = getOrderInfo().getConfirmTime();
    	if(getOrderInfo().getOrderStatus() == IOrderInfo.OS_CONFIRMED && confirmTime > 0) {
    		return new PrintfFormat(Lang.getInstance().getString("confirmTime")).sprintf(new Object[]{confirmTime});
    	}
    	else
    		return "";
    }
    
    public String getPayTime() {
    	long payTime = getOrderInfo().getPayTime();
    	if(getOrderInfo().getPayStatus() != IOrderInfo.PS_UNPAYED && payTime > 0) {
    		return new PrintfFormat(Lang.getInstance().getString("payTime")).sprintf(new Object[]{payTime});
    	}
    	else
    		return "";
    }
    
    public String getShippingTime() {
    	long shippingTime = getOrderInfo().getShippingTime();
    	if((getOrderInfo().getShippingStatus() == IOrderInfo.SS_SHIPPED || getOrderInfo().getShippingStatus() == IOrderInfo.SS_RECEIVED)
    			&& shippingTime > 0) {
    		return new PrintfFormat(Lang.getInstance().getString("shippingTime")).sprintf(new Object[]{shippingTime});
    	}
    	else
    		return "";
    }
    
    public String getInvoiceNo() {
    	return getOrderInfo().getInvoiceNo();
    }
    
    public String getToBuyer() {
    	return getOrderInfo().getToBuyer();
    }
    
    public String getFormatedGoodsAmount() {
    	return WebFormatUtils.priceFormat(getOrderInfo().getGoodsAmount());
    }
    
    public String getFormatedShippingFee() {
    	return WebFormatUtils.priceFormat(getOrderInfo().getShippingFee());
    }
    public String getFormatedInsureFee() {
    	return WebFormatUtils.priceFormat(getOrderInfo().getInsureFee());
    }
    public String getFormatedPayFee() {
    	return WebFormatUtils.priceFormat(getOrderInfo().getPayFee());
    }
    public String getFormatedMoneyPaid() {
    	return WebFormatUtils.priceFormat(getOrderInfo().getMoneyPaid());
    }
    public String getFormatedSurplus() {
    	return WebFormatUtils.priceFormat(getOrderInfo().getSurplus());
    }
    public String getFormatedOrderAmount() {
    	return WebFormatUtils.priceFormat(getOrderInfo().getOrderAmount());
    }
    
    public String getPayDesc() {
    	String payId = getOrderInfo().getPayId();
    	Payment payment = (Payment) manager.get(ModelNames.PAYMENT, payId);
    	return payment.getPayDesc();
    }
    
    public String getPackName() {
    	return getOrderInfo().getPackName();
    }
    public String getCardName() {
    	return getOrderInfo().getCardName();
    }
    public String getCardMessage() {
    	return getOrderInfo().getCardMessage();
    }
    public String getInvPayee() {
    	return getOrderInfo().getInvPayee();
    }
    public String getInvContent() {
    	return getOrderInfo().getInvContent();
    }
    public String getHowOosName() {
    	return getOrderInfo().getHowOos() == null ? "" : getOrderInfo().getHowOos();
    }
    public void setManager(IDefaultManager manager) {
    	this.manager = manager;
    }
}
