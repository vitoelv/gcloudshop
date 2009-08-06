package com.jcommerce.web.to;

import java.util.HashMap;
import java.util.Map;

public class Lang extends BaseWrapper {
	
	public static Lang getInstance() {
		if(instance==null) {
			instance = new Lang();
		}
		return instance;
	}
	
	private static Lang instance;

	
	private Lang() {
		
        values.put("shopNotice", "商店公告");
        // member_info.ftl
        values.put("welcome", "欢迎光临本店");
        // page_header.ftl
        values.put("processRequest", "正在处理您的请求...");
        values.put("home", "首页");
        values.put("hotSearch", "热门搜索");
        values.put("allCategory", "所有分类");
        values.put("advancedSearch", "高级搜索");
        values.put("promotionInfo", "促销信息");
        values.put("auctionGoods", "拍卖商品");
        values.put("groupBuyGoods", "团购商品");
        // order_query.ftl
        values.put("invalidOrderSn", "xxx");
        values.put("orderQuery", "订单查询");
        values.put("queryOrder", "查询该订单号");
        
        // email_list.ftl
        values.put("emailSubscribe", "邮件订阅");
        values.put("emailListOk", "订阅");
        values.put("emailListCancel", "退订");
        values.put("emailInvalid", "邮件地址非法");
        
        values.put("urHere", "lang.urHere");
        
		// for goods.ftl
        values.put("activity", "activity" );
        values.put("snatch", "snatch" );
        values.put("groupBuy", "groupBuy" );
        values.put("auction", "auction" );
        values.put("favourable", "favourable" );
        Map<String, String> item = new HashMap<String, String>();
        item.put("type", "type");
        values.put("item", item );
        values.put("goodsSn", "goodsSn" );
        values.put("goodsNumber", "goodsNumber" );
        values.put("stockUp", "stockUp" );
        values.put("goodsBrand", "品牌" );
        values.put("goodsWeight", "重量" );
        values.put("addTime", "上架时间" );
        values.put("goodsClickCount", "点击次数" );
        values.put("marketPrice", "市场价" );
        values.put("shopPrice", "商店价" );
        values.put("goodsRank", "商品排名" );
        values.put("promotePrice", "促销价" );
        values.put("residualTime", "residualTime" );
        values.put("pleaseWaiting", "请等待" );
        values.put("amount", "总额" );
        values.put("goodsGiveIntegral", "goodsGiveIntegral" );
        values.put("goodsBonus", "goodsBonus" );
        values.put("number", "数量" );
        values.put("goodsIntegral", "goodsIntegral" );
        values.put("plus", "加" );
        values.put("minus", "减" );
        values.put("goodsBrief", "商品简介" );
        values.put("goodsAttr", "商品属性" );
        values.put("goodsJs", new String[0]);
		
        
		// for flow.ftl
        values.put("goodsList", "商品列表");
        values.put("subtotal", "总计");
        values.put("handle", "处理");
        values.put("accessories", "配件");
        values.put("largess", "largess");
        values.put("dropGoodsConfirm", "dropGoodsConfirm");
        values.put("drop", "drop");
        values.put("dropToCollect", "dropToCollect");
        values.put("clearCart", "清空购物车");
        values.put("updateCart", "更新购物车");
        values.put("favourableName", "favourableName");	
        values.put("favourablePeriod", "favourablePeriod");
        values.put("favourableRange", "favourableRange");
        values.put("farExt", new HashMap<String, String>());
        values.put("favourableAmount", "favourableAmount");
        values.put("favourableType", "favourableType");
        values.put("flowNoPayment", "flowNoPayment");
        values.put("flowNoShipping", "flowNoShipping");
        values.put("modify", "修改");
        
        values.put("consigneeInfo", "consigneeInfo");
        values.put("consigneeName", "consigneeName");
        values.put("emailAddress", "emailAddress");
        values.put("detailedAddress", "detailedAddress");
        values.put("postalcode", "postalcode");
        values.put("phone", "phone");
        values.put("backupPhone", "backupPhone");
        values.put("signBuilding", "signBuilding");
        values.put("deliverGoodsTime", "deliverGoodsTime");
        values.put("shippingMethod", "shippingMethod");
        values.put("deposit", "deposit");
        values.put("deposit", "deposit");
        values.put("deposit", "deposit");		
        
        values.put("flowJs", new String[0]);
	}
	

}
