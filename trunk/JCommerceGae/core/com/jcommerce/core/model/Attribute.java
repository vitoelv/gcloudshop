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

@PersistenceCapable(identityType = IdentityType.APPLICATION)
public class Attribute extends ModelObject {
    
	public static final int TYPE_NEEDNOTSELECT = 0; 
    public static final int TYPE_NEEDSELECT = 1; 

    public static final int INPUT_SINGLELINETEXT = 0;
    public static final int INPUT_MULTIPLELINETEXT = 2;
    public static final int INPUT_CHOICE = 1;
    
    @Override
    public ModelObject getParent() {
    	return goodsType;
    }
    
    @PrimaryKey
    @Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
    @Extension(vendorName="datanucleus", key="gae.encoded-pk", value="true")
    private String id;
    
    @Persistent
    @Extension(vendorName="datanucleus", key="gae.pk-name", value="true")
    private String keyName;
    
    @Persistent
    private GoodsType goodsType;
    
    @Persistent
    private String name;
    
    @Persistent
    private int inputType;

    /**
     * 购买商品时是否需要选择该属性的值
     */
    @Persistent
    private int type;
    
    @Persistent
    private String values;
    /**
     * 能否进行检索. 0 - can not search, >0 - can search 
     */
    @Persistent
    private int index;
    
    @Persistent
    private int sortOrder;
    /**
     * 相同属性值的商品是否关联
     */
    @Persistent
    private boolean linked;
    
    @Persistent
    private int group;

    
    public Attribute() {
    	
    }

    public GoodsType getGoodsType() {
        return goodsType;
    }

    public void setGoodsType(GoodsType goodsType) {
        this.goodsType = goodsType;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getInputType() {
        return inputType;
    }

    public void setInputType(int inputType) {
        this.inputType = inputType;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getValues() {
        return values;
    }

    public void setValues(String values) {
        this.values = values;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public int getSortOrder() {
        return sortOrder;
    }

    public void setSortOrder(int sortOrder) {
        this.sortOrder = sortOrder;
    }

    public boolean isLinked() {
        return linked;
    }

    public void setLinked(boolean linked) {
        this.linked = linked;
    }

    public int getGroup() {
        return group;
    }

    public void setGroup(int group) {
        this.group = group;
    }
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
}
