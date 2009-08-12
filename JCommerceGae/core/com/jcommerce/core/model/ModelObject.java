package com.jcommerce.core.model;

import java.io.Serializable;



public abstract class ModelObject implements Serializable, URLConstants {
	

	public Long getLongId() {
		return null;
	}

	public void setLongId(Long longId) {
	}
	
    
//    public String getModelName() {
//        return getClass().getSimpleName();
//    }
//    public String toString() {
//        return ToStringBuilder.reflectionToString(this,
//                ToStringStyle.MULTI_LINE_STYLE);
//    }
//
//    public boolean equals(Object o) {
//        return EqualsBuilder.reflectionEquals(this, o);
//    }

//    public int hashCode() {
//        return HashCodeBuilder.reflectionHashCode(this);
//    }
    
	public abstract void setId(String id);
    public abstract String getId();
    
	public abstract void setKeyName(String kn);
    public abstract String getKeyName();
    
    public abstract ModelObject getParent();
    

}
