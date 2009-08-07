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
    private Date addTime;
    
    @Persistent
    private String address;
    
    @Persistent
    private String agencyId;

    @Persistent
    private String bestTime;

    @Persistent
    private Double bonus = 0.0;

    @Persistent
    private String bonusId;

    @Persistent
    private Double cardFee = 0.0;

    @Persistent
    private String cardId;

    @Persistent
    private String cardMessage;

    @Persistent
    private String cardName;

    @Persistent
    private String city;

    @Persistent
    private  Date confirmTime;

    @Persistent
    private String country;

    @Persistent
    private Double discount = 0.0;

    @Persistent
    private String district;

    @Persistent
    private String email;

    @Persistent
    private String extensionCode;

    @Persistent
    private String extensionId;

    @Persistent
    private String fromAd;

    @Persistent
    private Double goodsAmount = 0.0;

    @Persistent
    private String howOos;

    @Persistent
    private String howSurplus;

    @Persistent
    private Double insureFee = 0.0;

    @Persistent
    private Integer integral = 0;

    @Persistent
    private  Double integralMoney = 0.0;

    @Persistent
    private  String invContent;

    @Persistent
    private String invPayee;

    @Persistent
    private String invType;

    @Persistent
    private String invoiceNo;

    @Persistent
    private Boolean isSeparate = false;

    @Persistent
    private String mobile;

    @Persistent
    private Double moneyPaid = 0.0;

    @Persistent
    private Double orderAmount = 0.0;

    @Persistent
    private String orderSn;

    @Persistent
    private Integer orderStatus = 0;

    @Persistent
    private Double packFee = 0.0;

    @Persistent
    private String packId;

    @Persistent
    private String packName;

    @Persistent
    private String parentId;

    @Persistent
    private Double payFee = 0.0;

    @Persistent
    private String payId;

    @Persistent
    private String payName;

    @Persistent
    private String payNote;

    @Persistent
    private Integer payStatus = 0;

    @Persistent
    private Date payTime;

    @Persistent
    private String postScript;

    @Persistent
    private String province;

    @Persistent
    private String referer;

    @Persistent
    private Double shippingFee = 0.0;

    @Persistent
    private String shippingId;

    @Persistent
    private  String shippingName;

    @Persistent
    private Integer shippingStatus = 0;

    @Persistent
    private Date shippingTime;

    @Persistent
    private String signBuilding;

    @Persistent
    private Double surplus= 0.0;

    @Persistent
    private Double tax = 0.0;

    @Persistent
    private String tel;

    @Persistent
    private String toBuyer;

    @Persistent
    private String userId;

    @Persistent
    private String zipCode;

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

	public Date getAddTime() {
		return addTime;
	}

	public void setAddTime(Date addTime) {
		this.addTime = addTime;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getAgencyId() {
		return agencyId;
	}

	public void setAgencyId(String agencyId) {
		this.agencyId = agencyId;
	}

	public String getBestTime() {
		return bestTime;
	}

	public void setBestTime(String bestTime) {
		this.bestTime = bestTime;
	}

	public Double getBonus() {
		return bonus;
	}

	public void setBonus(Double bonus) {
		this.bonus = bonus;
	}

	public String getBonusId() {
		return bonusId;
	}

	public void setBonusId(String bonusId) {
		this.bonusId = bonusId;
	}

	public Double getCardFee() {
		return cardFee;
	}

	public void setCardFee(Double cardFee) {
		this.cardFee = cardFee;
	}

	public String getCardId() {
		return cardId;
	}

	public void setCardId(String cardId) {
		this.cardId = cardId;
	}

	public String getCardMessage() {
		return cardMessage;
	}

	public void setCardMessage(String cardMessage) {
		this.cardMessage = cardMessage;
	}

	public String getCardName() {
		return cardName;
	}

	public void setCardName(String cardName) {
		this.cardName = cardName;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public Date getConfirmTime() {
		return confirmTime;
	}

	public void setConfirmTime(Date confirmTime) {
		this.confirmTime = confirmTime;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public Double getDiscount() {
		return discount;
	}

	public void setDiscount(Double discount) {
		this.discount = discount;
	}

	public String getDistrict() {
		return district;
	}

	public void setDistrict(String district) {
		this.district = district;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getExtensionCode() {
		return extensionCode;
	}

	public void setExtensionCode(String extensionCode) {
		this.extensionCode = extensionCode;
	}

	public String getExtensionId() {
		return extensionId;
	}

	public void setExtensionId(String extensionId) {
		this.extensionId = extensionId;
	}

	public String getFromAd() {
		return fromAd;
	}

	public void setFromAd(String fromAd) {
		this.fromAd = fromAd;
	}

	public Double getGoodsAmount() {
		return goodsAmount;
	}

	public void setGoodsAmount(Double goodsAmount) {
		this.goodsAmount = goodsAmount;
	}

	public String getHowOos() {
		return howOos;
	}

	public void setHowOos(String howOos) {
		this.howOos = howOos;
	}

	public String getHowSurplus() {
		return howSurplus;
	}

	public void setHowSurplus(String howSurplus) {
		this.howSurplus = howSurplus;
	}

	public Double getInsureFee() {
		return insureFee;
	}

	public void setInsureFee(Double insureFee) {
		this.insureFee = insureFee;
	}

	public Integer getIntegral() {
		return integral;
	}

	public void setIntegral(Integer integral) {
		this.integral = integral;
	}

	public Double getIntegralMoney() {
		return integralMoney;
	}

	public void setIntegralMoney(Double integralMoney) {
		this.integralMoney = integralMoney;
	}

	public String getInvContent() {
		return invContent;
	}

	public void setInvContent(String invContent) {
		this.invContent = invContent;
	}

	public String getInvPayee() {
		return invPayee;
	}

	public void setInvPayee(String invPayee) {
		this.invPayee = invPayee;
	}

	public String getInvType() {
		return invType;
	}

	public void setInvType(String invType) {
		this.invType = invType;
	}

	public String getInvoiceNo() {
		return invoiceNo;
	}

	public void setInvoiceNo(String invoiceNo) {
		this.invoiceNo = invoiceNo;
	}

	public Boolean getIsSeparate() {
		return isSeparate;
	}

	public void setIsSeparate(Boolean isSeparate) {
		this.isSeparate = isSeparate;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public Double getMoneyPaid() {
		return moneyPaid;
	}

	public void setMoneyPaid(Double moneyPaid) {
		this.moneyPaid = moneyPaid;
	}

	public Double getOrderAmount() {
		return orderAmount;
	}

	public void setOrderAmount(Double orderAmount) {
		this.orderAmount = orderAmount;
	}

	public String getOrderSn() {
		return orderSn;
	}

	public void setOrderSn(String orderSn) {
		this.orderSn = orderSn;
	}

	public Integer getOrderStatus() {
		return orderStatus;
	}

	public void setOrderStatus(Integer orderStatus) {
		this.orderStatus = orderStatus;
	}

	public Double getPackFee() {
		return packFee;
	}

	public void setPackFee(Double packFee) {
		this.packFee = packFee;
	}

	public String getPackId() {
		return packId;
	}

	public void setPackId(String packId) {
		this.packId = packId;
	}

	public String getPackName() {
		return packName;
	}

	public void setPackName(String packName) {
		this.packName = packName;
	}

	public String getParentId() {
		return parentId;
	}

	public void setParentId(String parentId) {
		this.parentId = parentId;
	}

	public Double getPayFee() {
		return payFee;
	}

	public void setPayFee(Double payFee) {
		this.payFee = payFee;
	}

	public String getPayId() {
		return payId;
	}

	public void setPayId(String payId) {
		this.payId = payId;
	}

	public String getPayName() {
		return payName;
	}

	public void setPayName(String payName) {
		this.payName = payName;
	}

	public String getPayNote() {
		return payNote;
	}

	public void setPayNote(String payNote) {
		this.payNote = payNote;
	}

	public Integer getPayStatus() {
		return payStatus;
	}

	public void setPayStatus(Integer payStatus) {
		this.payStatus = payStatus;
	}

	public Date getPayTime() {
		return payTime;
	}

	public void setPayTime(Date payTime) {
		this.payTime = payTime;
	}

	public String getPostScript() {
		return postScript;
	}

	public void setPostScript(String postScript) {
		this.postScript = postScript;
	}

	public String getProvince() {
		return province;
	}

	public void setProvince(String province) {
		this.province = province;
	}

	public String getReferer() {
		return referer;
	}

	public void setReferer(String referer) {
		this.referer = referer;
	}

	public Double getShippingFee() {
		return shippingFee;
	}

	public void setShippingFee(Double shippingFee) {
		this.shippingFee = shippingFee;
	}

	public String getShippingId() {
		return shippingId;
	}

	public void setShippingId(String shippingId) {
		this.shippingId = shippingId;
	}

	public String getShippingName() {
		return shippingName;
	}

	public void setShippingName(String shippingName) {
		this.shippingName = shippingName;
	}

	public Integer getShippingStatus() {
		return shippingStatus;
	}

	public void setShippingStatus(Integer shippingStatus) {
		this.shippingStatus = shippingStatus;
	}

	public Date getShippingTime() {
		return shippingTime;
	}

	public void setShippingTime(Date shippingTime) {
		this.shippingTime = shippingTime;
	}

	public String getSignBuilding() {
		return signBuilding;
	}

	public void setSignBuilding(String signBuilding) {
		this.signBuilding = signBuilding;
	}

	public Double getSurplus() {
		return surplus;
	}

	public void setSurplus(Double surplus) {
		this.surplus = surplus;
	}

	public Double getTax() {
		return tax;
	}

	public void setTax(Double tax) {
		this.tax = tax;
	}

	public String getTel() {
		return tel;
	}

	public void setTel(String tel) {
		this.tel = tel;
	}

	public String getToBuyer() {
		return toBuyer;
	}

	public void setToBuyer(String toBuyer) {
		this.toBuyer = toBuyer;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getZipCode() {
		return zipCode;
	}

	public void setZipCode(String zipCode) {
		this.zipCode = zipCode;
	}
    
    
    
    
    
    
    
}
