package com.jcommerce.web.to;

import java.util.Date;

import com.jcommerce.core.model.CollectGood;
import com.jcommerce.core.model.Goods;
import com.jcommerce.core.model.ModelObject;
import com.jcommerce.core.service.IDefaultManager;
import com.jcommerce.gwt.client.ModelNames;
import com.jcommerce.gwt.client.util.URLConstants;
import com.jcommerce.web.util.WebFormatUtils;

public class CollectGoodWrapper extends BaseModelWrapper implements URLConstants{

	CollectGood collectGood;
	private IDefaultManager manager = null;
	@Override
	protected Object getWrapped() {
		return getCollectGood();
	}
	
	public CollectGoodWrapper(ModelObject collectGood) {
		super();
		this.collectGood = (CollectGood) collectGood;
	}
	
	public CollectGood getCollectGood() {
		return collectGood;
	}
	
	public String getUrl() {
		String goodsId = getCollectGood().getGoodsId();
		return "goods.action?id=" + goodsId;
	}
	
	public String getGoodsName() {
		Goods goods = getGoods();
		return goods.getGoodsName();
	}
	
	public String getPromotePrice() {
		Goods goods = getGoods();	
		if(!goods.getIsPromote()) {
			return "";
		}
		else {
			//判断是否在促销时间内
			Long promoteEndTime = goods.getPromoteEndDate();
			Long promoteStartTime = goods.getPromoteStartDate();
			Long nowTime = new Date().getTime();
			if(nowTime > promoteEndTime || nowTime < promoteStartTime) {
				return "";
			}
			else {
				return WebFormatUtils.priceFormat(goods.getPromotePrice());
			}
		}
	}
	
	public String getShopPrice() {
		Goods goods = getGoods();		
		return WebFormatUtils.priceFormat(goods.getShopPrice());
	}
	
	public String getGoodsId() {
		Goods goods = getGoods();
		return goods.getLongId()+"";
	}
	
	public String getRecId() {
		return getCollectGood().getPkId();
	}
	
	public String getIsAttention() {
		if(!getCollectGood().getIsAttention()) {
			return null;
		}
		else
			return "";
	}
	
	public Goods getGoods() {
		String goodsId = getCollectGood().getGoodsId();
		Goods goods = (Goods) manager.get(ModelNames.GOODS, goodsId);
		return goods;
	}
	
	public void setManager(IDefaultManager manager) {
		this.manager = manager;
	}
}
