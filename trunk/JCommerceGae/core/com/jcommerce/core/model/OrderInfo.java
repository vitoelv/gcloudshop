package com.jcommerce.core.model;


import java.util.HashSet;
import java.util.Set;
import javax.jdo.annotations.Extension;
import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

/**  
 * generated with my extension of middleGen 
 * @author <a href="http://code.google.com/p/gcloudshop/">Leon</a>
 */
 
@PersistenceCapable(identityType = IdentityType.APPLICATION , detachable="true") 
public class OrderInfo extends ModelObject implements Cloneable{


    
//    // relations
//        @Persistent
//    private Set<OrderAction> orderActions = new HashSet<OrderAction>();
//    
//    @Persistent
//    private Set<OrderGoods> orderGoods = new HashSet<OrderGoods>();
//    
//public Set<OrderAction> getOrderActions() {
//	return orderActions;
//}
//
//
//public void setOrderActions(Set<OrderAction> orderActions) {
//	this.orderActions = orderActions;
//}
//
//
//public Set<OrderGoods> getOrderGoods() {
//	return orderGoods;
//}
//
//
//public void setOrderGoods(Set<OrderGoods> orderGoods) {
//	this.orderGoods = orderGoods;
//}    
    
  // fields
  @Persistent
  private java.lang.String orderId; 

  @Persistent
  private java.lang.String orderSn; 

  @Persistent
  private java.lang.String userId; 

  @Persistent
  private java.lang.Long orderStatus=0l; 

  @Persistent
  private java.lang.Long shippingStatus=0l; 

  @Persistent
  private java.lang.Long payStatus=0l; 

  @Persistent
  private java.lang.String consignee; 

  @Persistent
  private java.lang.Long country=0l; 

  @Persistent
  private java.lang.Long province=0l; 

  @Persistent
  private java.lang.Long city=0l; 

  @Persistent
  private java.lang.Long district=0l; 

  @Persistent
  private java.lang.String address; 

  @Persistent
  private java.lang.String zipcode; 

  @Persistent
  private java.lang.String tel; 

  @Persistent
  private java.lang.String mobile; 

  @Persistent
  private java.lang.String email; 

  @Persistent
  private java.lang.String bestTime; 

  @Persistent
  private java.lang.String signBuilding; 

  @Persistent
  private java.lang.String postscript; 

  @Persistent
  private java.lang.String shippingId; 

  @Persistent
  private java.lang.String shippingName; 

  @Persistent
  private java.lang.String payId; 

  @Persistent
  private java.lang.String payName; 

  @Persistent
  private java.lang.String howOos; 

  @Persistent
  private java.lang.String howSurplus; 

  @Persistent
  private java.lang.String packName; 

  @Persistent
  private java.lang.String cardName; 

  @Persistent
  private java.lang.String cardMessage; 

  @Persistent
  private java.lang.String invPayee; 

  @Persistent
  private java.lang.String invContent; 

  @Persistent
  private java.lang.Double goodsAmount=0.0; 

  @Persistent
  private java.lang.Double shippingFee=0.0; 

  @Persistent
  private java.lang.Double insureFee=0.0; 

  @Persistent
  private java.lang.Double payFee=0.0; 

  @Persistent
  private java.lang.Double packFee=0.0; 

  @Persistent
  private java.lang.Double cardFee=0.0; 

  @Persistent
  private java.lang.Double moneyPaid=0.0; 

  @Persistent
  private java.lang.Double surplus=0.0; 

  @Persistent
  private java.lang.Long integral=0l; 

  @Persistent
  private java.lang.Double integralMoney=0.0; 

  @Persistent
  private java.lang.Double bonus=0.0; 

  @Persistent
  private java.lang.Double orderAmount=0.0; 

  @Persistent
  private java.lang.Long fromAd=0l; 

  @Persistent
  private java.lang.String referer; 

  @Persistent
  private java.lang.Long addTime=0l; 

  @Persistent
  private java.lang.Long confirmTime=0l; 

  @Persistent
  private java.lang.Long payTime=0l; 

  @Persistent
  private java.lang.Long shippingTime=0l; 

  @Persistent
  private java.lang.String packId; 

  @Persistent
  private java.lang.String cardId; 

  @Persistent
  private java.lang.String bonusId; 

  @Persistent
  private java.lang.String invoiceNo; 

  @Persistent
  private java.lang.String extensionCode; 

  @Persistent
  private java.lang.String extensionId; 

  @Persistent
  private java.lang.String toBuyer; 

  @Persistent
  private java.lang.String payNote; 

  @Persistent
  private java.lang.String agencyId; 

  @Persistent
  private java.lang.String invType; 

  @Persistent
  private java.lang.Double tax=0.0; 

  @Persistent
  private java.lang.Boolean isSeparate=false; 

  @Persistent
  private java.lang.String parentId; 

  @Persistent
  private java.lang.Double discount=0.0; 



	public OrderInfo() {
	}






  public java.lang.String getOrderId() {
    return orderId;
  }

  public void setOrderId(java.lang.String newOrderId) {
    orderId = newOrderId;
  }



  public java.lang.String getOrderSn() {
    return orderSn;
  }

  public void setOrderSn(java.lang.String newOrderSn) {
    orderSn = newOrderSn;
  }



  public java.lang.String getUserId() {
    return userId;
  }

  public void setUserId(java.lang.String newUserId) {
    userId = newUserId;
  }



  public java.lang.Long getOrderStatus() {
    return orderStatus;
  }

  public void setOrderStatus(java.lang.Long newOrderStatus) {
    orderStatus = newOrderStatus;
  }



  public java.lang.Long getShippingStatus() {
    return shippingStatus;
  }

  public void setShippingStatus(java.lang.Long newShippingStatus) {
    shippingStatus = newShippingStatus;
  }



  public java.lang.Long getPayStatus() {
    return payStatus;
  }

  public void setPayStatus(java.lang.Long newPayStatus) {
    payStatus = newPayStatus;
  }



  public java.lang.String getConsignee() {
    return consignee;
  }

  public void setConsignee(java.lang.String newConsignee) {
    consignee = newConsignee;
  }



  public java.lang.Long getCountry() {
    return country;
  }

  public void setCountry(java.lang.Long newCountry) {
    country = newCountry;
  }



  public java.lang.Long getProvince() {
    return province;
  }

  public void setProvince(java.lang.Long newProvince) {
    province = newProvince;
  }



  public java.lang.Long getCity() {
    return city;
  }

  public void setCity(java.lang.Long newCity) {
    city = newCity;
  }



  public java.lang.Long getDistrict() {
    return district;
  }

  public void setDistrict(java.lang.Long newDistrict) {
    district = newDistrict;
  }



  public java.lang.String getAddress() {
    return address;
  }

  public void setAddress(java.lang.String newAddress) {
    address = newAddress;
  }



  public java.lang.String getZipcode() {
    return zipcode;
  }

  public void setZipcode(java.lang.String newZipcode) {
    zipcode = newZipcode;
  }



  public java.lang.String getTel() {
    return tel;
  }

  public void setTel(java.lang.String newTel) {
    tel = newTel;
  }



  public java.lang.String getMobile() {
    return mobile;
  }

  public void setMobile(java.lang.String newMobile) {
    mobile = newMobile;
  }



  public java.lang.String getEmail() {
    return email;
  }

  public void setEmail(java.lang.String newEmail) {
    email = newEmail;
  }



  public java.lang.String getBestTime() {
    return bestTime;
  }

  public void setBestTime(java.lang.String newBestTime) {
    bestTime = newBestTime;
  }



  public java.lang.String getSignBuilding() {
    return signBuilding;
  }

  public void setSignBuilding(java.lang.String newSignBuilding) {
    signBuilding = newSignBuilding;
  }



  public java.lang.String getPostscript() {
    return postscript;
  }

  public void setPostscript(java.lang.String newPostscript) {
    postscript = newPostscript;
  }



  public java.lang.String getShippingId() {
    return shippingId;
  }

  public void setShippingId(java.lang.String newShippingId) {
    shippingId = newShippingId;
  }



  public java.lang.String getShippingName() {
    return shippingName;
  }

  public void setShippingName(java.lang.String newShippingName) {
    shippingName = newShippingName;
  }



  public java.lang.String getPayId() {
    return payId;
  }

  public void setPayId(java.lang.String newPayId) {
    payId = newPayId;
  }



  public java.lang.String getPayName() {
    return payName;
  }

  public void setPayName(java.lang.String newPayName) {
    payName = newPayName;
  }



  public java.lang.String getHowOos() {
    return howOos;
  }

  public void setHowOos(java.lang.String newHowOos) {
    howOos = newHowOos;
  }



  public java.lang.String getHowSurplus() {
    return howSurplus;
  }

  public void setHowSurplus(java.lang.String newHowSurplus) {
    howSurplus = newHowSurplus;
  }



  public java.lang.String getPackName() {
    return packName;
  }

  public void setPackName(java.lang.String newPackName) {
    packName = newPackName;
  }



  public java.lang.String getCardName() {
    return cardName;
  }

  public void setCardName(java.lang.String newCardName) {
    cardName = newCardName;
  }



  public java.lang.String getCardMessage() {
    return cardMessage;
  }

  public void setCardMessage(java.lang.String newCardMessage) {
    cardMessage = newCardMessage;
  }



  public java.lang.String getInvPayee() {
    return invPayee;
  }

  public void setInvPayee(java.lang.String newInvPayee) {
    invPayee = newInvPayee;
  }



  public java.lang.String getInvContent() {
    return invContent;
  }

  public void setInvContent(java.lang.String newInvContent) {
    invContent = newInvContent;
  }



  public java.lang.Double getGoodsAmount() {
    return goodsAmount;
  }

  public void setGoodsAmount(java.lang.Double newGoodsAmount) {
    goodsAmount = newGoodsAmount;
  }



  public java.lang.Double getShippingFee() {
    return shippingFee;
  }

  public void setShippingFee(java.lang.Double newShippingFee) {
    shippingFee = newShippingFee;
  }



  public java.lang.Double getInsureFee() {
    return insureFee;
  }

  public void setInsureFee(java.lang.Double newInsureFee) {
    insureFee = newInsureFee;
  }



  public java.lang.Double getPayFee() {
    return payFee;
  }

  public void setPayFee(java.lang.Double newPayFee) {
    payFee = newPayFee;
  }



  public java.lang.Double getPackFee() {
    return packFee;
  }

  public void setPackFee(java.lang.Double newPackFee) {
    packFee = newPackFee;
  }



  public java.lang.Double getCardFee() {
    return cardFee;
  }

  public void setCardFee(java.lang.Double newCardFee) {
    cardFee = newCardFee;
  }



  public java.lang.Double getMoneyPaid() {
    return moneyPaid;
  }

  public void setMoneyPaid(java.lang.Double newMoneyPaid) {
    moneyPaid = newMoneyPaid;
  }



  public java.lang.Double getSurplus() {
    return surplus;
  }

  public void setSurplus(java.lang.Double newSurplus) {
    surplus = newSurplus;
  }



  public java.lang.Long getIntegral() {
    return integral;
  }

  public void setIntegral(java.lang.Long newIntegral) {
    integral = newIntegral;
  }



  public java.lang.Double getIntegralMoney() {
    return integralMoney;
  }

  public void setIntegralMoney(java.lang.Double newIntegralMoney) {
    integralMoney = newIntegralMoney;
  }



  public java.lang.Double getBonus() {
    return bonus;
  }

  public void setBonus(java.lang.Double newBonus) {
    bonus = newBonus;
  }



  public java.lang.Double getOrderAmount() {
    return orderAmount;
  }

  public void setOrderAmount(java.lang.Double newOrderAmount) {
    orderAmount = newOrderAmount;
  }



  public java.lang.Long getFromAd() {
    return fromAd;
  }

  public void setFromAd(java.lang.Long newFromAd) {
    fromAd = newFromAd;
  }



  public java.lang.String getReferer() {
    return referer;
  }

  public void setReferer(java.lang.String newReferer) {
    referer = newReferer;
  }



  public java.lang.Long getAddTime() {
    return addTime;
  }

  public void setAddTime(java.lang.Long newAddTime) {
    addTime = newAddTime;
  }



  public java.lang.Long getConfirmTime() {
    return confirmTime;
  }

  public void setConfirmTime(java.lang.Long newConfirmTime) {
    confirmTime = newConfirmTime;
  }



  public java.lang.Long getPayTime() {
    return payTime;
  }

  public void setPayTime(java.lang.Long newPayTime) {
    payTime = newPayTime;
  }



  public java.lang.Long getShippingTime() {
    return shippingTime;
  }

  public void setShippingTime(java.lang.Long newShippingTime) {
    shippingTime = newShippingTime;
  }



  public java.lang.String getPackId() {
    return packId;
  }

  public void setPackId(java.lang.String newPackId) {
    packId = newPackId;
  }



  public java.lang.String getCardId() {
    return cardId;
  }

  public void setCardId(java.lang.String newCardId) {
    cardId = newCardId;
  }



  public java.lang.String getBonusId() {
    return bonusId;
  }

  public void setBonusId(java.lang.String newBonusId) {
    bonusId = newBonusId;
  }



  public java.lang.String getInvoiceNo() {
    return invoiceNo;
  }

  public void setInvoiceNo(java.lang.String newInvoiceNo) {
    invoiceNo = newInvoiceNo;
  }



  public java.lang.String getExtensionCode() {
    return extensionCode;
  }

  public void setExtensionCode(java.lang.String newExtensionCode) {
    extensionCode = newExtensionCode;
  }



  public java.lang.String getExtensionId() {
    return extensionId;
  }

  public void setExtensionId(java.lang.String newExtensionId) {
    extensionId = newExtensionId;
  }



  public java.lang.String getToBuyer() {
    return toBuyer;
  }

  public void setToBuyer(java.lang.String newToBuyer) {
    toBuyer = newToBuyer;
  }



  public java.lang.String getPayNote() {
    return payNote;
  }

  public void setPayNote(java.lang.String newPayNote) {
    payNote = newPayNote;
  }



  public java.lang.String getAgencyId() {
    return agencyId;
  }

  public void setAgencyId(java.lang.String newAgencyId) {
    agencyId = newAgencyId;
  }



  public java.lang.String getInvType() {
    return invType;
  }

  public void setInvType(java.lang.String newInvType) {
    invType = newInvType;
  }



  public java.lang.Double getTax() {
    return tax;
  }

  public void setTax(java.lang.Double newTax) {
    tax = newTax;
  }



  public java.lang.Boolean getIsSeparate() {
    return isSeparate;
  }

  public void setIsSeparate(java.lang.Boolean newIsSeparate) {
    isSeparate = newIsSeparate;
  }



  public java.lang.String getParentId() {
    return parentId;
  }

  public void setParentId(java.lang.String newParentId) {
    parentId = newParentId;
  }



  public java.lang.Double getDiscount() {
    return discount;
  }

  public void setDiscount(java.lang.Double newDiscount) {
    discount = newDiscount;
  }
  
  public Object clone() {   
	OrderInfo o = null;   
	try {   
		o = (OrderInfo) super.clone();   
	} catch (CloneNotSupportedException e) {   
		e.printStackTrace();   
	}   
	return o;   
  }   

}
