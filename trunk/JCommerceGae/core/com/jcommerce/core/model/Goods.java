package com.jcommerce.core.model;

import com.google.appengine.api.datastore.Blob;
import com.jcommerce.core.annotation.IsBlobText;
import com.jcommerce.core.annotation.IsPK;

import java.nio.charset.Charset;
import java.util.HashSet;
import java.util.Set;

import javax.jdo.annotations.Element;
import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;

import org.compass.annotations.Searchable;
import org.compass.annotations.SearchableProperty;

/**  
 * generated with my extension of middleGen 
 * @author <a href="http://code.google.com/p/gcloudshop/">Leon</a>
 */
 
@PersistenceCapable(identityType = IdentityType.APPLICATION , detachable="true") 
@Searchable(alias = "goods")
public class Goods extends ModelObject {


    
    // relations
    @Persistent(mappedBy="goods")
    @Element(dependent = "true")
    private Set<GoodsGallery> galleries = new HashSet<GoodsGallery>();
    @Persistent
    private Set<String>categoryIds = new HashSet<String>();
    
    @Persistent
    @IsPK(myclazz="com.jcommerce.core.model.GoodsType")
    private String goodsTypeId;

    // uni-direction owned ??
    @Persistent(mappedBy="goods")
    @Element(dependent = "true")
    private Set<GoodsAttr> attributes = new HashSet<GoodsAttr>();
    
	@Persistent
	private String image;
    @Persistent
    @IsPK(myclazz="com.jcommerce.core.model.DSFile")
    private String imageFileId;
    @Persistent
    private DSFile imageFile;
	@Persistent
	private String thumb;
	
    @Persistent
    @IsPK(myclazz="com.jcommerce.core.model.DSFile")
    private String thumbFileId;
    
    @Persistent
    private DSFile thumbFile;
    @Persistent
    private String googleBaseDataId;
    
    @Persistent
    private Set<String> keywords = new HashSet<String>(); 	
	
    public String getGoogleBaseDataId() {
		return googleBaseDataId;
	}

	public void setGoogleBaseDataId(String googleBaseDataId) {
		this.googleBaseDataId = googleBaseDataId;
	}

	public Set<GoodsAttr> getAttributes() {
		return attributes;
	}

	public void setAttributes(Set<GoodsAttr> attributes) {
		this.attributes = attributes;
	}
    
public Set<GoodsGallery> getGalleries() {
	return galleries;
}


public void setGalleries(Set<GoodsGallery> galleries) {
	this.galleries = galleries;
}


public Set<String> getCategoryIds() {
	return categoryIds;
}


public void setCategoryIds(Set<String> categoryIds) {
	this.categoryIds = categoryIds;
}





public String getGoodsTypeId() {
	return goodsTypeId;
}


public void setGoodsTypeId(String goodsTypeId) {
	this.goodsTypeId = goodsTypeId;
}    
    
  // fields
  @Persistent
  private java.lang.String goodsId; 

  @Persistent
  @IsPK(myclazz="com.jcommerce.core.model.Category")
  private java.lang.String catId; 

  @Persistent
  private java.lang.String goodsSn; 

  @Persistent
  private java.lang.String goodsName; 

  @Persistent
  private java.lang.String goodsNameStyle; 

  @Persistent
  private java.lang.Long clickCount=0l; 

  @Persistent
  @IsPK(myclazz="com.jcommerce.core.model.Brand")
  private java.lang.String brandId; 

  @Persistent
  private java.lang.String providerName; 

  @Persistent
  private java.lang.Long goodsNumber=0l; 

  @Persistent
  private java.lang.Double goodsWeight=0.0; 

  @Persistent
  private java.lang.Double marketPrice=0.0; 

  @Persistent
  private java.lang.Double shopPrice=0.0; 

  @Persistent
  private java.lang.Double promotePrice=0.0; 

  @Persistent
  private java.lang.Long promoteStartDate=0l; 

  @Persistent
  private java.lang.Long promoteEndDate=0l; 

  @Persistent
  private java.lang.Long warnNumber=0l; 



  @Persistent
  private java.lang.String goodsBrief; 

  @IsBlobText
  @Persistent
  private Blob goodsDesc; 

  @Persistent
  private java.lang.String goodsThumb; 

  @Persistent
  private java.lang.String goodsImg; 

  @Persistent
  private java.lang.String originalImg; 

  @Persistent
  private java.lang.Boolean isReal=false; 

  @Persistent
  private java.lang.String extensionCode; 

  @Persistent
  private java.lang.Boolean isOnSale=false; 

  @Persistent
  private java.lang.Boolean isAloneSale=false; 

  @Persistent
  private java.lang.Long integral=0l; 

  @Persistent
  private java.lang.Long addTime=0l; 

  @Persistent
  private java.lang.Long sortOrder=0l; 

  @Persistent
  private java.lang.Boolean isDelete=false; 

  @Persistent
  private java.lang.Boolean isBest=false; 

  @Persistent
  private java.lang.Boolean isNew=false; 

  @Persistent
  private java.lang.Boolean isHot=false; 

  @Persistent
  private java.lang.Boolean isPromote=false; 

  @Persistent
  private java.lang.String bonusTypeId; 

  @Persistent
  private java.lang.Long lastUpdate=0l; 

  @Persistent
  private java.lang.Long goodsType=0l; 

  @Persistent
  private java.lang.String sellerNote; 

  @Persistent
  private java.lang.Long giveIntegral=0l; 

  @Persistent
  private java.lang.Long rankIntegral=0l; 



	public Goods() {
	}





  public java.lang.String getGoodsId() {
    return goodsId;
  }

  public void setGoodsId(java.lang.String newGoodsId) {
    goodsId = newGoodsId;
  }


  @SearchableProperty(name = "catId")
  public java.lang.String getCatId() {
    return catId;
  }

  public void setCatId(java.lang.String newCatId) {
    catId = newCatId;
  }



  @SearchableProperty(name = "SN")
  public java.lang.String getGoodsSn() {
    return goodsSn;
  }

  public void setGoodsSn(java.lang.String newGoodsSn) {
    goodsSn = newGoodsSn;
  }



  @SearchableProperty(name = "name")
  public java.lang.String getGoodsName() {
    return goodsName;
  }

  public void setGoodsName(java.lang.String newGoodsName) {
    goodsName = newGoodsName;
  }



  public java.lang.String getGoodsNameStyle() {
    return goodsNameStyle;
  }

  public void setGoodsNameStyle(java.lang.String newGoodsNameStyle) {
    goodsNameStyle = newGoodsNameStyle;
  }



  public java.lang.Long getClickCount() {
    return clickCount;
  }

  public void setClickCount(java.lang.Long newClickCount) {
    clickCount = newClickCount;
  }



  public java.lang.String getBrandId() {
    return brandId;
  }

  public void setBrandId(java.lang.String newBrandId) {
    brandId = newBrandId;
  }



  public java.lang.String getProviderName() {
    return providerName;
  }

  public void setProviderName(java.lang.String newProviderName) {
    providerName = newProviderName;
  }



  public java.lang.Long getGoodsNumber() {
    return goodsNumber;
  }

  public void setGoodsNumber(java.lang.Long newGoodsNumber) {
    goodsNumber = newGoodsNumber;
  }



  public java.lang.Double getGoodsWeight() {
    return goodsWeight;
  }

  public void setGoodsWeight(java.lang.Double newGoodsWeight) {
    goodsWeight = newGoodsWeight;
  }



  public java.lang.Double getMarketPrice() {
    return marketPrice;
  }

  public void setMarketPrice(java.lang.Double newMarketPrice) {
    marketPrice = newMarketPrice;
  }



  public java.lang.Double getShopPrice() {
    return shopPrice;
  }

  public void setShopPrice(java.lang.Double newShopPrice) {
    shopPrice = newShopPrice;
  }



  public java.lang.Double getPromotePrice() {
    return promotePrice;
  }

  public void setPromotePrice(java.lang.Double newPromotePrice) {
    promotePrice = newPromotePrice;
  }



  public java.lang.Long getPromoteStartDate() {
    return promoteStartDate;
  }

  public void setPromoteStartDate(java.lang.Long newPromoteStartDate) {
    promoteStartDate = newPromoteStartDate;
  }



  public java.lang.Long getPromoteEndDate() {
    return promoteEndDate;
  }

  public void setPromoteEndDate(java.lang.Long newPromoteEndDate) {
    promoteEndDate = newPromoteEndDate;
  }



  public java.lang.Long getWarnNumber() {
    return warnNumber;
  }

  public void setWarnNumber(java.lang.Long newWarnNumber) {
    warnNumber = newWarnNumber;
  }




  
  




  @SearchableProperty(name = "brief")
  public java.lang.String getGoodsBrief() {
    return goodsBrief;
  }

  public void setGoodsBrief(java.lang.String newGoodsBrief) {
    goodsBrief = newGoodsBrief;
  }



//  @SearchableProperty(name = "description")
//  public java.lang.String getGoodsDesc() {
//    return goodsDesc;
//  }
//
//  public void setGoodsDesc(java.lang.String newGoodsDesc) {
//    goodsDesc = newGoodsDesc;
//  }
  @SearchableProperty(name = "description")
  public String getGoodsDesc() {
	  if(goodsDesc == null)
		  return null;
	  else {
		   String desc = new String(goodsDesc.getBytes(),Charset.forName("UTF-8"));
		   System.out.println(desc);
		   return desc;
	  }
//	  return goodsDesc;
  }

  public void setGoodsDesc(String newGoodsDesc) {
    //goodsDesc = newGoodsDesc;
      if(newGoodsDesc==null) {
          
          // TODO a bug on GUI cause submit null value for HtmlEditor
          // goodsDesc = null;
      }else {
          goodsDesc = new Blob(newGoodsDesc.getBytes(Charset.forName("UTF-8")));
      }
  }




  public java.lang.String getGoodsThumb() {
    return goodsThumb;
  }

  public void setGoodsThumb(java.lang.String newGoodsThumb) {
    goodsThumb = newGoodsThumb;
  }



  public java.lang.String getGoodsImg() {
    return goodsImg;
  }

  public void setGoodsImg(java.lang.String newGoodsImg) {
    goodsImg = newGoodsImg;
  }



  public java.lang.String getOriginalImg() {
    return originalImg;
  }

  public void setOriginalImg(java.lang.String newOriginalImg) {
    originalImg = newOriginalImg;
  }



  public java.lang.Boolean getIsReal() {
    return isReal;
  }

  public void setIsReal(java.lang.Boolean newIsReal) {
    isReal = newIsReal;
  }



  public java.lang.String getExtensionCode() {
    return extensionCode;
  }

  public void setExtensionCode(java.lang.String newExtensionCode) {
    extensionCode = newExtensionCode;
  }



  public java.lang.Boolean getIsOnSale() {
    return isOnSale;
  }

  public void setIsOnSale(java.lang.Boolean newIsOnSale) {
    isOnSale = newIsOnSale;
  }



  public java.lang.Boolean getIsAloneSale() {
    return isAloneSale;
  }

  public void setIsAloneSale(java.lang.Boolean newIsAloneSale) {
    isAloneSale = newIsAloneSale;
  }



  public java.lang.Long getIntegral() {
    return integral;
  }

  public void setIntegral(java.lang.Long newIntegral) {
    integral = newIntegral;
  }



  public java.lang.Long getAddTime() {
    return addTime;
  }

  public void setAddTime(java.lang.Long newAddTime) {
    addTime = newAddTime;
  }



  public java.lang.Long getSortOrder() {
    return sortOrder;
  }

  public void setSortOrder(java.lang.Long newSortOrder) {
    sortOrder = newSortOrder;
  }



  public java.lang.Boolean getIsDelete() {
    return isDelete;
  }

  public void setIsDelete(java.lang.Boolean newIsDelete) {
    isDelete = newIsDelete;
  }



  public java.lang.Boolean getIsBest() {
    return isBest;
  }

  public void setIsBest(java.lang.Boolean newIsBest) {
    isBest = newIsBest;
  }



  public java.lang.Boolean getIsNew() {
    return isNew;
  }

  public void setIsNew(java.lang.Boolean newIsNew) {
    isNew = newIsNew;
  }



  public java.lang.Boolean getIsHot() {
    return isHot;
  }

  public void setIsHot(java.lang.Boolean newIsHot) {
    isHot = newIsHot;
  }



  public java.lang.Boolean getIsPromote() {
    return isPromote;
  }

  public void setIsPromote(java.lang.Boolean newIsPromote) {
    isPromote = newIsPromote;
  }



  public java.lang.String getBonusTypeId() {
    return bonusTypeId;
  }

  public void setBonusTypeId(java.lang.String newBonusTypeId) {
    bonusTypeId = newBonusTypeId;
  }



  public java.lang.Long getLastUpdate() {
    return lastUpdate;
  }

  public void setLastUpdate(java.lang.Long newLastUpdate) {
    lastUpdate = newLastUpdate;
  }



  public java.lang.Long getGoodsType() {
    return goodsType;
  }

  public void setGoodsType(java.lang.Long newGoodsType) {
    goodsType = newGoodsType;
  }



  @SearchableProperty(name = "sellerNote")
  public java.lang.String getSellerNote() {
    return sellerNote;
  }

  public void setSellerNote(java.lang.String newSellerNote) {
    sellerNote = newSellerNote;
  }



  public java.lang.Long getGiveIntegral() {
    return giveIntegral;
  }

  public void setGiveIntegral(java.lang.Long newGiveIntegral) {
    giveIntegral = newGiveIntegral;
  }



  public java.lang.Long getRankIntegral() {
    return rankIntegral;
  }

  public void setRankIntegral(java.lang.Long newRankIntegral) {
    rankIntegral = newRankIntegral;
  }

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public String getThumb() {
		return thumb;
	}

	public void setThumb(String thumb) {
		this.thumb = thumb;
	}

	public String getImageFileId() {
		return imageFileId;
	}

	public void setImageFileId(String imageFileId) {
		this.imageFileId = imageFileId;
	}

	public DSFile getImageFile() {
		return imageFile;
	}

	public void setImageFile(DSFile imageFile) {
		this.imageFile = imageFile;
	}

	public String getThumbFileId() {
		return thumbFileId;
	}

	public void setThumbFileId(String thumbFileId) {
		this.thumbFileId = thumbFileId;
	}

	public DSFile getThumbFile() {
		return thumbFile;
	}

	public void setThumbFile(DSFile thumbFile) {
		this.thumbFile = thumbFile;
	}

	@SearchableProperty(name = "keywords")
    public Set<String> getKeywords() {
        return keywords;
    }

    public void setKeywords(Set<String> keywords) {
        this.keywords = keywords;
    }

}
