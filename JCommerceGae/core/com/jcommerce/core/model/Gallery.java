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
public class Gallery extends ModelObject {
	
    @Override
    public ModelObject getParent() {
    	return goods;
    }
	
    @PrimaryKey
    @Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
    @Extension(vendorName="datanucleus", key="gae.encoded-pk", value="true")
    private String id;
    
    @Persistent
    @Extension(vendorName="datanucleus", key="gae.pk-name", value="true")
    private String keyName;
    
    @Persistent
	private String image;
    
    @Persistent
	private String imageFileId;
    
    @Persistent
	private String thumb;
    
    @Persistent
	private String thumbFileId;
    
    @Persistent
	private String description;
    
    @Persistent
	private String originalImage;
	
	@Persistent
	private Goods goods;

    public Goods getGoods() {
        return goods;
    }

    public void setGoods(Goods goods) {
        this.goods = goods;
    }



    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getOriginalImage() {
        return originalImage;
    }

    public void setOriginalImage(String originalImage) {
        this.originalImage = originalImage;
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

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public String getImageFileId() {
		return imageFileId;
	}

	public void setImageFileId(String imageFileId) {
		this.imageFileId = imageFileId;
	}

	public String getThumb() {
		return thumb;
	}

	public void setThumb(String thumb) {
		this.thumb = thumb;
	}

	public String getThumbFileId() {
		return thumbFileId;
	}

	public void setThumbFileId(String thumbFileId) {
		this.thumbFileId = thumbFileId;
	}

}
