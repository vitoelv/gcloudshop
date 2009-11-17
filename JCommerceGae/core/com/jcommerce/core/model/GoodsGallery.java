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
public class GoodsGallery extends ModelObject {

    @PrimaryKey
    @Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
    @Extension(vendorName="datanucleus", key="gae.encoded-pk", value="true")
    private String pkId;
    
    @Persistent
    @Extension(vendorName="datanucleus", key="gae.pk-name", value="true")
    private String keyName;
    
    @Persistent
    private Long longId;
    
    // relations
    	@Persistent
	private Goods goods;
    @Persistent
	private DSFile imageFile;
    @Persistent
	private String imageFileId;
    @Persistent
	private DSFile thumbFile;
    @Persistent
	private String thumbFileId;
	@Persistent
	private String image;
	@Persistent
	private String thumb;
	
	
	@Override
  public ModelObject getParent(){
  	return goods;
  }
	
public Goods getGoods() {
	return goods;
}


public void setGoods(Goods goods) {
	this.goods = goods;
}


public DSFile getImageFile() {
	return imageFile;
}


public void setImageFile(DSFile imageFile) {
	this.imageFile = imageFile;
}


public String getImageFileId() {
	return imageFileId;
}


public void setImageFileId(String imageFileId) {
	this.imageFileId = imageFileId;
}


public DSFile getThumbFile() {
	return thumbFile;
}


public void setThumbFile(DSFile thumbFile) {
	this.thumbFile = thumbFile;
}


public String getThumbFileId() {
	return thumbFileId;
}


public void setThumbFileId(String thumbFileId) {
	this.thumbFileId = thumbFileId;
}    
    
  // fields
  @Persistent
  private java.lang.String imgId; 

  @Persistent
  private java.lang.String goodsId; 

  @Persistent
  private java.lang.String imgUrl; 

  @Persistent
  private java.lang.String imgDesc; 

  @Persistent
  private java.lang.String thumbUrl; 

  @Persistent
  private java.lang.String imgOriginal; 



	public GoodsGallery() {
	}


	@Override
	public Long getLongId() {
		return longId;
	}

	@Override
	public void setLongId(Long longId) {
		this.longId = longId;
	}

	public String getPkId() {
		return pkId;
	}

	public void setPkId(String pkId) {
		this.pkId = pkId;
	}

	public String getKeyName() {
		return keyName;
	}

	public void setKeyName(String keyName) {
		this.keyName = keyName;
	}



  public java.lang.String getImgId() {
    return imgId;
  }

  public void setImgId(java.lang.String newImgId) {
    imgId = newImgId;
  }



  public java.lang.String getGoodsId() {
    return goodsId;
  }

  public void setGoodsId(java.lang.String newGoodsId) {
    goodsId = newGoodsId;
  }



  public java.lang.String getImgUrl() {
    return imgUrl;
  }

  public void setImgUrl(java.lang.String newImgUrl) {
    imgUrl = newImgUrl;
  }



  public java.lang.String getImgDesc() {
    return imgDesc;
  }

  public void setImgDesc(java.lang.String newImgDesc) {
    imgDesc = newImgDesc;
  }



  public java.lang.String getThumbUrl() {
    return thumbUrl;
  }

  public void setThumbUrl(java.lang.String newThumbUrl) {
    thumbUrl = newThumbUrl;
  }



  public java.lang.String getImgOriginal() {
    return imgOriginal;
  }

  public void setImgOriginal(java.lang.String newImgOriginal) {
    imgOriginal = newImgOriginal;
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

}