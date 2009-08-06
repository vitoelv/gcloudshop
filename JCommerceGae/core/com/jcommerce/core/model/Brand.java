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

import com.jcommerce.gwt.client.model.IAttribute;


@PersistenceCapable(identityType = IdentityType.APPLICATION , detachable="true")
public class Brand extends ModelObject {

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
    private String brandName;
    
    @Persistent
    private String brandLogo;

    // TODO leon combine logo and logoFileId to a DSFile instance, 
    // in order to make DSFile a child of Brand, and thus can be put in a DS transaction
    @Persistent
    private String logoFileId;
    
//    @Persistent
//    private DSFile logo;
//    
//    @Persistent
//    private String logoFileName;
//    
//    @Persistent
//    private String logoFileId;
    
    @Persistent
    private String brandDesc;
    
    @Persistent
    private String siteUrl;
    
    @Persistent
    private Boolean show = false;
    
    @Persistent
    private Integer sortOrder = 0;

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

	public String getBrandName() {
		return brandName;
	}

	public void setBrandName(String brandName) {
		this.brandName = brandName;
	}

	public String getBrandLogo() {
		return brandLogo;
	}

	public void setBrandLogo(String brandLogo) {
		this.brandLogo = brandLogo;
	}

	public String getLogoFileId() {
		return logoFileId;
	}

	public void setLogoFileId(String logoFileId) {
		this.logoFileId = logoFileId;
	}

	public String getBrandDesc() {
		return brandDesc;
	}

	public void setBrandDesc(String brandDesc) {
		this.brandDesc = brandDesc;
	}

	public String getSiteUrl() {
		return siteUrl;
	}

	public void setSiteUrl(String siteUrl) {
		this.siteUrl = siteUrl;
	}

	public Boolean getShow() {
		return show;
	}

	public void setShow(Boolean show) {
		this.show = show;
	}

	public Integer getSortOrder() {
		return sortOrder;
	}

	public void setSortOrder(Integer sortOrder) {
		this.sortOrder = sortOrder;
	}
    

    


}
