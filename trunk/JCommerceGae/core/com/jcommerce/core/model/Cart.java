package com.jcommerce.core.model;

import javax.jdo.annotations.Extension;
import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;


@PersistenceCapable(identityType = IdentityType.APPLICATION , detachable="true")
public class Cart extends ModelObject {
    public static final int TYPE_GENERAL_GOODS = Constants.CART_GENERAL_GOODS; // 普通商品
    public static final int TYPE_GROUP_BUY_GOODS = Constants.CART_GROUP_BUY_GOODS; // 团购商品
    public static final int TYPE_AUCTION_GOODS = Constants.CART_AUCTION_GOODS; // 拍卖商品
    public static final int TYPE_SNATCH_GOODS = Constants.CART_SNATCH_GOODS; // 夺宝奇兵
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
    private String userId;
    
    @Persistent
    private String sessionId;
    
    @Persistent
    private Long goodsId;
    
    @Persistent
    private String goodsSN;
    
    @Persistent
    private String goodsName;
    
    @Persistent
    private Double marketPrice = 0.0;
    
    @Persistent
    private Double goodsPrice = 0.0;
    
    @Persistent
    private Integer goodsNumber = 0;
    // String goodsAttribute;
    
    @Persistent
    private Boolean isReal = false;
    
    @Persistent
    private String extensionCode;
    
    @Persistent
    private String parentId;
    
    @Persistent
    private Integer recType = 0;   // TYPE_xxx
    
    @Persistent
    private Boolean isGift = false;
    
    @Persistent
    private Boolean canHandsel = false;
    
    @Persistent
    private String goodsAttrId;
    
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

	public String getSessionId() {
		return sessionId;
	}
	public void setSessionId(String sessionId) {
		this.sessionId = sessionId;
	}

	public String getGoodsSN() {
		return goodsSN;
	}
	public void setGoodsSN(String goodsSN) {
		this.goodsSN = goodsSN;
	}
	public String getGoodsName() {
		return goodsName;
	}
	public void setGoodsName(String goodsName) {
		this.goodsName = goodsName;
	}
	public Double getMarketPrice() {
		return marketPrice;
	}
	public void setMarketPrice(Double marketPrice) {
		this.marketPrice = marketPrice;
	}
	public Double getGoodsPrice() {
		return goodsPrice;
	}
	public void setGoodsPrice(Double goodsPrice) {
		this.goodsPrice = goodsPrice;
	}
	public Integer getGoodsNumber() {
		return goodsNumber;
	}
	public void setGoodsNumber(Integer goodsNumber) {
		this.goodsNumber = goodsNumber;
	}

	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public Boolean getIsReal() {
		return isReal;
	}
	public void setIsReal(Boolean isReal) {
		this.isReal = isReal;
	}
	public String getExtensionCode() {
		return extensionCode;
	}
	public void setExtensionCode(String extensionCode) {
		this.extensionCode = extensionCode;
	}
	public String getParentId() {
		return parentId;
	}
	public void setParentId(String parentId) {
		this.parentId = parentId;
	}
	public Integer getRecType() {
		return recType;
	}
	public void setRecType(Integer recType) {
		this.recType = recType;
	}
	public Boolean getIsGift() {
		return isGift;
	}
	public void setIsGift(Boolean isGift) {
		this.isGift = isGift;
	}
	public Boolean getCanHandsel() {
		return canHandsel;
	}
	public void setCanHandsel(Boolean canHandsel) {
		this.canHandsel = canHandsel;
	}
	public String getGoodsAttrId() {
		return goodsAttrId;
	}
	public void setGoodsAttrId(String goodsAttrId) {
		this.goodsAttrId = goodsAttrId;
	}
	public Long getGoodsId() {
		return goodsId;
	}
	public void setGoodsId(Long goodsId) {
		this.goodsId = goodsId;
	}


}