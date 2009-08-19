package com.jcommerce.core.model;

import javax.jdo.annotations.Extension;
import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

import com.google.appengine.api.datastore.Blob;

@PersistenceCapable(identityType = IdentityType.APPLICATION , detachable="true")
public class DSFile extends ModelObject {
    @Override
    public ModelObject getParent() {
    	return null;
    }
    @PrimaryKey
    @Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
    @Extension(vendorName="datanucleus", key="gae.encoded-pk", value="true")
    private String pkId;
    
    @Persistent
    @Extension(vendorName="datanucleus", key="gae.pk-name", value="true")
    private String keyName;
	
    @Persistent
    private Blob content;
    
    @Persistent
    private String fileName;
    
    @Persistent
    private String mimeType;

    
	@Override
	public String getPkId() {
		return pkId;
	}

	@Override
	public String getKeyName() {
		return keyName;
	}

	@Override
	public void setPkId(String pkId) {
		this.pkId = pkId;
	}

	@Override
	public void setKeyName(String kn) {
		this.keyName = kn;
	}

	public Blob getContent() {
		return content;
	}

	public void setContent(Blob content) {
		this.content = content;
	}



	public String getMimeType() {
		return mimeType;
	}

	public void setMimeType(String mimeType) {
		this.mimeType = mimeType;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

}
