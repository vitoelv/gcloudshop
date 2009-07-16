/**
 * @author KingZhao
 */
package com.jcommerce.core.model;

import javax.jdo.annotations.Extension;
import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

public class Navigation extends ModelObject {

    public ModelObject getParent() {
    	return null;
    }
	
	public static final int CATEGORYTYPE_GOODSCATEGORY=1;//货物分类导航
	public static final int CATEGORYTYPE_ARTICLECATEGORY=2;//文章分类导航
	
	public static final int TYPE_TOP=1;//上部
	public static final int TYPE_MIDDLE=2;//中部
	public static final int TYPE_BOTTOM=3;//下部

    @PrimaryKey
    @Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
    @Extension(vendorName="datanucleus", key="gae.encoded-pk", value="true")
    private String id;
    
    @Persistent
    @Extension(vendorName="datanucleus", key="gae.pk-name", value="true")
    private String keyName;
	
	private int categoryType;//导航類別
	private int categoryId;//所在類別下ID
	private String name;
	private boolean show;//是否显示导航
	private int viewOrder;
	private boolean openNew;//是否在新窗口查看
	private String url;
	private int type;//位置類型


	
	public int getCategoryType() {
		return categoryType;
	}

	public void setCategoryType(int categoryType) {
		this.categoryType = categoryType;
	}

	public int getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(int categoryId) {
		this.categoryId = categoryId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getViewOrder() {
		return viewOrder;
	}

	public void setViewOrder(int viewOrder) {
		this.viewOrder = viewOrder;
	}
	
	public boolean isShow() {
		return show;
	}

	public void setShow(boolean show) {
		this.show = show;
	}

	public boolean isOpenNew() {
		return openNew;
	}

	public void setOpenNew(boolean openNew) {
		this.openNew = openNew;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
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
	
}
