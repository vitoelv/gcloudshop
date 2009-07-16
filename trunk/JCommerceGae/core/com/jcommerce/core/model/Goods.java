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

@PersistenceCapable(identityType = IdentityType.APPLICATION)
public class Goods extends ModelObject {
	
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
    private Set<String>categoryIds = new HashSet<String>();
    
//    private Set<Category> categories = new HashSet<Category>();
    
    @Persistent
    private String mainCategoryId;
    
//    private Category mainCategory;
    
    @Persistent(mappedBy="goods")
    private Set<Gallery> galleries = new HashSet<Gallery>();
    
//    private Set<Article> articles;
//    private Set<GoodsAttribute> attributes = new HashSet<GoodsAttribute>();
    
    @Persistent
    private String brandId;
    
//    private Brand brand;
    
    @Persistent
    private String typeId;
    
//    private GoodsType type;   // GoodsType table list all the types available
    
    @Persistent
    private String SN;
    
    @Persistent
    private String name;
    
    @Persistent
    private String nameStyle;
    
    @Persistent
    private int clickCount;
    

    
    @Persistent
    private String providerName;
    
    @Persistent
    private int number;
    
    @Persistent
    private double weight;
    
    @Persistent
    private double marketPrice;
    
    @Persistent
    private double shopPrice;
    
    @Persistent
    private double promotePrice;
    
    @Persistent
    private Date promoteStart;
    
    @Persistent
    private Date promoteEnd;
    
    @Persistent
    private int warnNumber;
    
    @Persistent
    private String keywords;
    
    @Persistent
    private String brief;
    
    @Persistent
    private String description;
    
    @Persistent
    private String thumb;
    
    @Persistent
    private String thumbFileId;
    
    @Persistent
    private String image;
    
    @Persistent
    private String imageFileId;
    
    @Persistent
    private String originalImage;
    
    @Persistent
    private boolean realGoods;
    
    @Persistent
    private String extensionCode;
    
    @Persistent
    private boolean onSale;
    
    @Persistent
    private boolean aloneSale;
    
    @Persistent
    private int integral;
    
    @Persistent
    private Date addTime;
    
    @Persistent
    private int sortOrder;  // sortOrder value is used in SQL ORDER BY
    
    @Persistent
    private boolean deleted;
    
    @Persistent
    private boolean bestSold;
    
    @Persistent
    private boolean neewAdded;
    
    @Persistent
    private boolean hotSold;
    
    @Persistent
    private boolean promoted;
    
    
//    private BonusType bonusType;
    @Persistent
    private Date lastUpdate;
    

    
    @Persistent
    private String sellerNote;
    
    @Persistent
    private int giveIntegral;
    


    public String getSN() {
        return SN;
    }
    
    public void setSN(String sn) {
        SN = sn;
    }
    
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public String getNameStyle() {
        return nameStyle;
    }
    
    public void setNameStyle(String nameStyle) {
        this.nameStyle = nameStyle;
    }
    
    public int getClickCount() {
        return clickCount;
    }
    
    public void setClickCount(int clickCount) {
        this.clickCount = clickCount;
    }
    

    public String getProviderName() {
        return providerName;
    }

    public void setProviderName(String providerName) {
        this.providerName = providerName;
    }

    public int getNumber() {
        return number;
    }
    
    public void setNumber(int number) {
        this.number = number;
    }
    
    public double getWeight() {
        return weight;
    }
    
    public void setWeight(double weight) {
        this.weight = weight;
    }
    
    public double getMarketPrice() {
        return marketPrice;
    }
    
    public void setMarketPrice(double marketPrice) {
        this.marketPrice = marketPrice;
    }
    
    public double getShopPrice() {
        return shopPrice;
    }
    
    public void setShopPrice(double shopPrice) {
        this.shopPrice = shopPrice;
    }
    
    public double getPromotePrice() {
        return promotePrice;
    }
    
    public void setPromotePrice(double promotePrice) {
        this.promotePrice = promotePrice;
    }
    
    public Date getPromoteStart() {
        return promoteStart;
    }
    
    public void setPromoteStart(Date promoteStart) {
        this.promoteStart = promoteStart;
    }
    
    public Date getPromoteEnd() {
        return promoteEnd;
    }
    
    public void setPromoteEnd(Date promoteEnd) {
        this.promoteEnd = promoteEnd;
    }
    
    public int getWarnNumber() {
        return warnNumber;
    }
    
    public void setWarnNumber(int warnNumber) {
        this.warnNumber = warnNumber;
    }
    
    public String getKeywords() {
        return keywords;
    }
    
    public void setKeywords(String keywords) {
        this.keywords = keywords;
    }
    
    public String getBrief() {
        return brief;
    }
    
    public void setBrief(String brief) {
        this.brief = brief;
    }
    
    public String getDescription() {
        return description;
    }
    
    public void setDescription(String description) {
        this.description = description;
    }
    
    public String getThumb() {
        return thumb;
    }
    
    public void setThumb(String thumb) {
        this.thumb = thumb;
    }
    
    public String getImage() {
        return image;
    }
    
    public void setImage(String image) {
        this.image = image;
    }
    
    public String getOriginalImage() {
        return originalImage;
    }
    
    public void setOriginalImage(String originalImage) {
        this.originalImage = originalImage;
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
    
    public boolean isOnSale() {
        return onSale;
    }
    
    public void setOnSale(boolean onSale) {
        this.onSale = onSale;
    }
    
    public boolean isAloneSale() {
        return aloneSale;
    }
    
    public void setAloneSale(boolean aloneSale) {
        this.aloneSale = aloneSale;
    }
    
    public int getIntegral() {
        return integral;
    }
    
    public void setIntegral(int integral) {
        this.integral = integral;
    }
    
    public Date getAddTime() {
        return addTime;
    }
    
    public void setAddTime(Date addTime) {
        this.addTime = addTime;
    }
    
    public int getSortOrder() {
        return sortOrder;
    }
    
    public void setSortOrder(int sortOrder) {
        this.sortOrder = sortOrder;
    }
    
    public boolean isDeleted() {
        return deleted;
    }
    
    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }
    
    public boolean isBestSold() {
        return bestSold;
    }
    
    public void setBestSold(boolean bestSold) {
        this.bestSold = bestSold;
    }
    

    
    public boolean isHotSold() {
        return hotSold;
    }
    
    public void setHotSold(boolean hotSold) {
        this.hotSold = hotSold;
    }
    
    public boolean isPromoted() {
        return promoted;
    }
    
    public void setPromoted(boolean promoted) {
        this.promoted = promoted;
    }
    
    public Date getLastUpdate() {
        return lastUpdate;
    }
    
    public void setLastUpdate(Date lastUpdate) {
        this.lastUpdate = lastUpdate;
    }
    


    public String getSellerNote() {
        return sellerNote;
    }
    
    public void setSellerNote(String sellerNote) {
        this.sellerNote = sellerNote;
    }
    
    public int getGiveIntegral() {
        return giveIntegral;
    }
    
    public void setGiveIntegral(int giveIntegral) {
        this.giveIntegral = giveIntegral;
    }

	public Set<String> getCategoryIds() {
		return categoryIds;
	}

	public void setCategoryIds(Set<String> categoryIds) {
		this.categoryIds = categoryIds;
	}

	public String getMainCategoryId() {
		return mainCategoryId;
	}

	public void setMainCategoryId(String mainCategoryId) {
		this.mainCategoryId = mainCategoryId;
	}

	public String getBrandId() {
		return brandId;
	}

	public void setBrandId(String brandId) {
		this.brandId = brandId;
	}

	public String getTypeId() {
		return typeId;
	}

	public void setTypeId(String typeId) {
		this.typeId = typeId;
	}


//    public BonusType getBonusType() {
//        return bonusType;
//    }
//
//    public void setBonusType(BonusType bonusType) {
//        this.bonusType = bonusType;
//    }
//
//    public Set<Gallery> getGalleries() {
//        return galleries;
//    }
//
//    public void setGalleries(Set<Gallery> galleries) {
//        this.galleries = galleries;
//    }
//
//    public Set<Article> getArticles() {
//        return articles;
//    }
//
//    public void setArticles(Set<Article> articles) {
//        this.articles = articles;
//    }
//
//    public Set<GoodsAttribute> getAttributes() {
//        return attributes;
//    }
//
//    public void setAttributes(Set<GoodsAttribute> attributes) {
//        this.attributes = attributes;
//    }
	public void setId(String id) {
		this.id = id;
	}
    public String getId() {
    	return id;
    }
    
	public void setKeyName(String kn) {
		this.keyName = kn;
	}
    public String getKeyName() {
    	return keyName;
    }

	public String getThumbFileId() {
		return thumbFileId;
	}

	public void setThumbFileId(String thumbFileId) {
		this.thumbFileId = thumbFileId;
	}

	public String getImageFileId() {
		return imageFileId;
	}

	public void setImageFileId(String imageFileId) {
		this.imageFileId = imageFileId;
	}

	public Set<Gallery> getGalleries() {
		return galleries;
	}

	public void setGalleries(Set<Gallery> galleries) {
		this.galleries = galleries;
	}

	public boolean isNeewAdded() {
		return neewAdded;
	}

	public void setNeewAdded(boolean neewAdded) {
		this.neewAdded = neewAdded;
	}
}
