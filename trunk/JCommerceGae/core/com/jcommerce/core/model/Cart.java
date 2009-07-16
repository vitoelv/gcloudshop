/**
 * Author: Bob Chen
 */

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
    
    private User user;
    private Session session;
    private Goods goods;
    private String goodsSN;
    private String goodsName;
    private double marketPrice;
    private double goodsPrice;
    private int goodsNumber;
    // String goodsAttribute;
    private boolean realGoods;
    private String extensionCode;
    private Cart parentCart;
    private int type;   // TYPE_xxx
    private boolean gift;
    private boolean handSelectable;
    private GoodsAttribute goodsAttribute;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Session getSession() {
        return session;
    }

    public void setSession(Session session) {
        this.session = session;
    }

    public Goods getGoods() {
        return goods;
    }

    public void setGoods(Goods goods) {
        this.goods = goods;
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

    public double getMarketPrice() {
        return marketPrice;
    }

    public void setMarketPrice(double marketPrice) {
        this.marketPrice = marketPrice;
    }

    public double getGoodsPrice() {
        return goodsPrice;
    }

    public void setGoodsPrice(double goodsPrice) {
        this.goodsPrice = goodsPrice;
    }

    public int getGoodsNumber() {
        return goodsNumber;
    }

    public void setGoodsNumber(int goodsNumber) {
        this.goodsNumber = goodsNumber;
    }

    public boolean isRealGoods() {
        return realGoods;
    }

    public void setRealGoods(boolean realGoods) {
        this.realGoods = realGoods;
    }

    public String getExtensionCode() {
        return extensionCode;
    }

    public void setExtensionCode(String extensionCode) {
        this.extensionCode = extensionCode;
    }



    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public boolean isGift() {
        return gift;
    }

    public void setGift(boolean gift) {
        this.gift = gift;
    }

    public boolean isHandSelectable() {
        return handSelectable;
    }

    public void setHandSelectable(boolean handSelectable) {
        this.handSelectable = handSelectable;
    }

    public GoodsAttribute getGoodsAttribute() {
        return goodsAttribute;
    }

    public void setGoodsAttribute(GoodsAttribute goodsAttribute) {
        this.goodsAttribute = goodsAttribute;
    }

	public String getKeyName() {
		return keyName;
	}

	public void setKeyName(String keyName) {
		this.keyName = keyName;
	}

	public Cart getParentCart() {
		return parentCart;
	}

	public void setParentCart(Cart parentCart) {
		this.parentCart = parentCart;
	}

}
