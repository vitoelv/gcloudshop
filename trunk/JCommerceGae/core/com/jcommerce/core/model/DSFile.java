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


	
    @Persistent
    private Blob content;
    
    @Persistent
    private String fileName;
    
    @Persistent
    private String mimeType;

    

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
