/**
 * Author: Bob Chen
 */

package com.jcommerce.core.model;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.jdo.annotations.Extension;
import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

@PersistenceCapable(identityType = IdentityType.APPLICATION , detachable="true")
public class Order extends ModelObject {
	
    public static final int ORDER_UNCONFIRMED = Constants.ORDER_UNCONFIRMED; // 未确认
    public static final int ORDER_CONFIRMED = Constants.ORDER_CONFIRMED; // 已确认
    public static final int ORDER_CANCELED = Constants.ORDER_CANCELED; // 已取消
    public static final int ORDER_INVALID = Constants.ORDER_INVALID; // 无效
    public static final int ORDER_RETURNED = Constants.ORDER_RETURNED; // 退货

    public static final int SHIPPING_UNSHIPPED = Constants.SHIPPING_UNSHIPPED; // 未发货
    public static final int SHIPPING_SHIPPED = Constants.SHIPPING_SHIPPED; // 已发货
    public static final int SHIPPING_RECEIVED = Constants.SHIPPING_RECEIVED; // 已收货
    public static final int SHIPPING_PREPARING = Constants.SHIPPING_PREPARING; // 备货中

    public static final int PAY_UNPAYED = Constants.PAY_UNPAYED; // 未付款
    public static final int PAY_PAYING = Constants.PAY_PAYING; // 付款中
    public static final int PAY_PAYED = Constants.PAY_PAYED; // 已付款
    
    @Override
    public ModelObject getParent() {
    	return null;
    }
    
    @PrimaryKey
    @Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
    @Extension(vendorName="datanucleus", key="gae.encoded-pk", value="true")
    private String id;
    
    @Persistent
    @Extension(vendorName="datanucleus", key="gae.pk-name", value="true")
    private String keyName;
    
    @Persistent
    private Set<OrderAction> orderActions = new HashSet<OrderAction>();
    
    @Persistent
    private Set<OrderGoods> orderGoods = new HashSet<OrderGoods>();

	@Persistent
    private String sn;
    
    @Persistent
    private User user;
    
    @Persistent
    private int status;         // ORDER_XXX 
    
    @Persistent
    private int shippingStatus; // SHIPPING_XXX
    
    @Persistent
    private int payStatus;      // // PAY_XXX
    
    @Persistent
    private String consignee;
    
    @Persistent
    private String email;
    
//    private Region region;
    
    @Persistent
    private String address;
    
    @Persistent
    private String zip;
    
    @Persistent
    private String phone;
    
    @Persistent
    private String mobile;
    
    @Persistent
    private String signBuilding;
    
    @Persistent
    private String bestTime;
    
    @Persistent
    private String postScript;
    
    @Persistent
    private String packName;
    
    @Persistent
    private String cardName;
    
    @Persistent
    private String cardMessage;
    
    @Persistent
    private String invoicePayee;
    
    @Persistent
    private String invoiceContent;
    
    @Persistent
    private double goodsAmount;
    
    @Persistent
    private double shippingFee;
    
    @Persistent
    private double insureFee;
    
    @Persistent
    private double payFee;
//    private Payment payment;
//    private Shipping shipping;
    
    @Persistent
    private String howOss;
    
    @Persistent
    private String howSurplus;
    
    @Persistent
    private double moneyPaid;
    
    @Persistent
    private double surplus;
    
    @Persistent
    private int integral;
    
    @Persistent
    private double integralMoney;
    
    @Persistent
    private double orderAmount;
    
    @Persistent
    private double bonusMoney;
    
    @Persistent
    private int fromAD;
    
    @Persistent
    private String referer;
    
    @Persistent
    private Date addTime;
    
    @Persistent
    private Date confirmTime;
    
    @Persistent
    private Date payTime;
    
    @Persistent
    private Date shippingTime;
//    private Pack pack;
//    private Card card;
//    private UserBonus userBonus;
    
    @Persistent
    private String invoiceNO;
    
    @Persistent
    private String extensionCode;
    
    @Persistent
    private int extensionId;
    
    @Persistent
    private String toBuyer;
    
    @Persistent
    private String payNote;
//    private Agency agency;
    
    @Persistent
    private String invoiceType;
    
    @Persistent
    private double tax;
    
    @Persistent
    private boolean separate;
    
    @Persistent
    private double discount;
    
    @Persistent
    private Order parentOrder;
    
    @Persistent
    private double cardFee;
    
    @Persistent
    private int packFee;
    
    @Persistent
    private String shippingName;
    
    @Persistent
    private String payName;
    


    public Set<OrderAction> getOrderActions() {
		return orderActions;
	}

	public void setOrderActions(Set<OrderAction> orderActions) {
		this.orderActions = orderActions;
	}

	public Set<OrderGoods> getOrderGoods() {
		return orderGoods;
	}

	public void setOrderGoods(Set<OrderGoods> orderGoods) {
		this.orderGoods = orderGoods;
	}

    public String getSn() {
        return sn;
    }

    public void setSn(String sn) {
        this.sn = sn;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getShippingStatus() {
        return shippingStatus;
    }

    public void setShippingStatus(int shippingStatus) {
        this.shippingStatus = shippingStatus;
    }


    public int getPayStatus() {
        return payStatus;
    }

    public void setPayStatus(int payStatus) {
        this.payStatus = payStatus;
    }

    public String getConsignee() {
        return consignee;
    }

    public void setConsignee(String consignee) {
        this.consignee = consignee;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getZip() {
        return zip;
    }

    public void setZip(String zip) {
        this.zip = zip;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getSignBuilding() {
        return signBuilding;
    }

    public void setSignBuilding(String signBuilding) {
        this.signBuilding = signBuilding;
    }

    public String getBestTime() {
        return bestTime;
    }

    public void setBestTime(String bestTime) {
        this.bestTime = bestTime;
    }

    public String getPostScript() {
        return postScript;
    }

    public void setPostScript(String postScript) {
        this.postScript = postScript;
    }

    public String getPackName() {
        return packName;
    }

    public void setPackName(String packName) {
        this.packName = packName;
    }

    public String getCardName() {
        return cardName;
    }

    public void setCardName(String cardName) {
        this.cardName = cardName;
    }

    public String getCardMessage() {
        return cardMessage;
    }

    public void setCardMessage(String cardMessage) {
        this.cardMessage = cardMessage;
    }

    public String getInvoicePayee() {
        return invoicePayee;
    }

    public void setInvoicePayee(String invoicePayee) {
        this.invoicePayee = invoicePayee;
    }

    public String getInvoiceContent() {
        return invoiceContent;
    }

    public void setInvoiceContent(String invoiceContent) {
        this.invoiceContent = invoiceContent;
    }

    public double getGoodsAmount() {
        return goodsAmount;
    }

    public void setGoodsAmount(double goodsAmount) {
        this.goodsAmount = goodsAmount;
    }

    public double getShippingFee() {
        return shippingFee;
    }

    public void setShippingFee(double shippingFee) {
        this.shippingFee = shippingFee;
    }

    public double getInsureFee() {
        return insureFee;
    }

    public void setInsureFee(double insureFee) {
        this.insureFee = insureFee;
    }

    public double getPayFee() {
        return payFee;
    }

    public void setPayFee(double payFee) {
        this.payFee = payFee;
    }



    public String getHowOss() {
        return howOss;
    }

    public void setHowOss(String howOss) {
        this.howOss = howOss;
    }

    public String getHowSurplus() {
        return howSurplus;
    }

    public void setHowSurplus(String howSurplus) {
        this.howSurplus = howSurplus;
    }

    public double getMoneyPaid() {
        return moneyPaid;
    }

    public void setMoneyPaid(double moneyPaid) {
        this.moneyPaid = moneyPaid;
    }

    public double getSurplus() {
        return surplus;
    }

    public void setSurplus(double surplus) {
        this.surplus = surplus;
    }

    public int getIntegral() {
        return integral;
    }

    public void setIntegral(int integral) {
        this.integral = integral;
    }

    public double getIntegralMoney() {
        return integralMoney;
    }

    public void setIntegralMoney(double integralMoney) {
        this.integralMoney = integralMoney;
    }

    public double getOrderAmount() {
        return orderAmount;
    }

    public void setOrderAmount(double orderAmount) {
        this.orderAmount = orderAmount;
    }

    public double getBonusMoney() {
        return bonusMoney;
    }

    public void setBonusMoney(double bonus) {
        this.bonusMoney = bonus;
    }

    public int getFromAD() {
        return fromAD;
    }

    public void setFromAD(int fromAD) {
        this.fromAD = fromAD;
    }

    public String getReferer() {
        return referer;
    }

    public void setReferer(String referer) {
        this.referer = referer;
    }

    public Date getAddTime() {
        return addTime;
    }

    public void setAddTime(Date addTime) {
        this.addTime = addTime;
    }

    public Date getConfirmTime() {
        return confirmTime;
    }

    public void setConfirmTime(Date confirmTime) {
        this.confirmTime = confirmTime;
    }

    public Date getPayTime() {
        return payTime;
    }

    public void setPayTime(Date payTime) {
        this.payTime = payTime;
    }

    public Date getShippingTime() {
        return shippingTime;
    }

    public void setShippingTime(Date shippingTime) {
        this.shippingTime = shippingTime;
    }




    public String getInvoiceNO() {
        return invoiceNO;
    }

    public void setInvoiceNO(String invoiceNO) {
        this.invoiceNO = invoiceNO;
    }

    public String getExtensionCode() {
        return extensionCode;
    }

    public void setExtensionCode(String extensionCode) {
        this.extensionCode = extensionCode;
    }

    public int getExtensionId() {
        return extensionId;
    }

    public void setExtensionId(int extensionId) {
        this.extensionId = extensionId;
    }

    public String getToBuyer() {
        return toBuyer;
    }

    public void setToBuyer(String toBuyer) {
        this.toBuyer = toBuyer;
    }

    public String getPayNote() {
        return payNote;
    }

    public void setPayNote(String payNote) {
        this.payNote = payNote;
    }


    public String getInvoiceType() {
        return invoiceType;
    }

    public void setInvoiceType(String invoceType) {
        this.invoiceType = invoceType;
    }

    public double getTax() {
        return tax;
    }

    public void setTax(double tax) {
        this.tax = tax;
    }

    public boolean isSeparate() {
        return separate;
    }

    public void setSeparate(boolean separate) {
        this.separate = separate;
    }

    public double getDiscount() {
        return discount;
    }

    public void setDiscount(double discount) {
        this.discount = discount;
    }



	public double getCardFee() {
		return cardFee;
	}

	public void setCardFee(double cardFee) {
		this.cardFee = cardFee;
	}

	public int getPackFee() {
		return packFee;
	}

	public void setPackFee(int packFee) {
		this.packFee = packFee;
	}

	public String getShippingName() {
		return shippingName;
	}

	public void setShippingName(String shippingName) {
		this.shippingName = shippingName;
	}

	public String getPayName() {
		return payName;
	}

	public void setPayName(String payName) {
		this.payName = payName;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getKeyName() {
		return keyName;
	}

	public void setKeyName(String keyName) {
		this.keyName = keyName;
	}

	public Order getParentOrder() {
		return parentOrder;
	}

	public void setParentOrder(Order parentOrder) {
		this.parentOrder = parentOrder;
	}
     
    
}
