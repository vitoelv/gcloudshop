/**
* Author: Bob Chen
*/

package com.jcommerce.core.model;

import java.util.HashSet;
import java.util.Set;

import javax.jdo.annotations.Extension;
import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

@PersistenceCapable(identityType = IdentityType.APPLICATION)
public class Category extends ModelObject {
    
    @PrimaryKey
    @Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
    @Extension(vendorName="datanucleus", key="gae.encoded-pk", value="true")
    private String id;
    
    @Persistent
    @Extension(vendorName="datanucleus", key="gae.pk-name", value="true")
    private String keyName;
    
    @Persistent
    private String parentId;
    
//    private Category parent;
//    
//    private Set<Category> children = new HashSet<Category>();
//    
//    private Set<Goods> goodsList = new HashSet<Goods>();
    
    @Persistent
    private String name;
    
    @Persistent
    private String keywords;
    
    @Persistent
    private String description;
    
    @Persistent
    private int sortOrder;
    
    @Persistent
    private String templateFile;
    
    @Persistent
    private String measureUnit;
    /**
     * 是否显示在导航栏
     */
    @Persistent
    private boolean showInNavigator;
    
    @Persistent
    private String style;
    
    @Persistent
    private boolean show;
    /**
     * 价格区间个数
     */
    
    @Persistent
    private int grade;
    
//    @Persistent
//    private Attribute filterAttribute;
    

    public String getParentId() {
		return parentId;
	}

	public void setParentId(String parentId) {
		this.parentId = parentId;
	}

//	public Category getParent() {
//        return parent;
//    }
//    
//    public void setParent(Category parent) {
//        if (getParent() != null) {
//            getParent().removeChild(this);
//        }
//        this.parent = parent;
//        if (this.parent != null) {
//            this.parent.addChild(this);
//        }
//    }
//    
//    public Set<Category> getChildren() {
//        return children;
//    }
//
//    public void setChildren(Set<Category> children) {
//        if (children == null) {
//            this.children.clear();
//        } else {
//            this.children = children;
//        }
//    }
//
//    public void addChild(Category child) {
//        children.add(child);
//        child.parent = this;
//    }
//    
//    public void removeChild(Category child) {
//        children.remove(child);
//        child.parent = null;
//    }
//    
//    public Set<Goods> getGoodsList() {
//        return goodsList;
//    }
//
//    public void setGoodsList(Set<Goods> goodsList) {
//        this.goodsList = goodsList;
//    }
//
//    public void addGoods(Goods goods) {
//        if (!goodsList.contains(goods)) {
//            this.goodsList.add(goods);
//            goods.addCategory(this);
//        }
//    }
//
//    public void removeGoods(Goods goods) {
//        if (goodsList.contains(goods)) {
//            this.goodsList.remove(goods);
//            goods.removeCategory(this);
//        }
//    }

    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public String getKeywords() {
        return keywords;
    }
    
    public void setKeywords(String keywords) {
        this.keywords = keywords;
    }
    
    public String getDescription() {
        return description;
    }
    
    public void setDescription(String description) {
        this.description = description;
    }
            
    public int getSortOrder() {
		return sortOrder;
	}

	public void setSortOrder(int sortOrder) {
		this.sortOrder = sortOrder;
	}

	public String getTemplateFile() {
        return templateFile;
    }
    
    public void setTemplateFile(String templateFile) {
        this.templateFile = templateFile;
    }
    
    public String getMeasureUnit() {
        return measureUnit;
    }
    
    public void setMeasureUnit(String measureUnit) {
        this.measureUnit = measureUnit;
    }
    
    public boolean isShowInNavigator() {
        return showInNavigator;
    }
    
    public void setShowInNavigator(boolean showInNavigator) {
        this.showInNavigator = showInNavigator;
    }
    
    public String getStyle() {
        return style;
    }
    
    public void setStyle(String style) {
        this.style = style;
    }
    
    public boolean isShow() {
        return show;
    }
    
    public void setShow(boolean show) {
        this.show = show;
    }
    
    public int getGrade() {
        return grade;
    }
    
    public void setGrade(int grade) {
        this.grade = grade;
    }

//    public Attribute getFilterAttribute() {
//        return filterAttribute;
//    }
//
//    public void setFilterAttribute(Attribute filterAttribute) {
//        this.filterAttribute = filterAttribute;
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
}
