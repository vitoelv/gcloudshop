package com.jcommerce.core.model;

import java.io.Serializable;

import javax.jdo.annotations.Extension;
import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.PersistenceAware;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;


/**
 * Base class for Model objects.  This is basically for the toString, equals
 * and hashCode methods.
 *
 */
public abstract class ModelObject implements Serializable {
	

    
    
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
    
    public ModelObject getParent() {
    	return null;
    }
}
