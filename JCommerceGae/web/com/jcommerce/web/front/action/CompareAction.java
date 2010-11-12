package com.jcommerce.web.front.action;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;

import com.jcommerce.core.model.Attribute;
import com.jcommerce.core.model.Brand;
import com.jcommerce.core.model.Comment;
import com.jcommerce.core.model.Goods;
import com.jcommerce.core.model.GoodsAttr;
import com.jcommerce.core.model.GoodsType;
import com.jcommerce.core.service.Condition;
import com.jcommerce.core.service.Criteria;
import com.jcommerce.core.service.IDefaultManager;
import com.jcommerce.gwt.client.ModelNames;
import com.jcommerce.gwt.client.model.IAttribute;
import com.jcommerce.gwt.client.model.IComment;
import com.jcommerce.web.to.GoodsWrapper;
import com.jcommerce.web.to.Lang;
import com.jcommerce.web.util.LibMain;

public class CompareAction extends BaseAction {

	@Override
	public String onExecute() throws Exception {
		
		HttpServletRequest request = getRequest();
		IDefaultManager manager = getDefaultManager();
		String goods = request.getParameter("goods");
		
		if(goods != null) {
			String[] goodsId = goods.split(",");
			
			List<GoodsWrapper> goodsList = new ArrayList<GoodsWrapper>();
			Map<String, String> attribute = new HashMap<String, String> ();
			String typeId = null;
			
			if(goodsId.length > 1) {
				for(int i = 0;i < goodsId.length;i++) {
					Long id = Long.parseLong(goodsId[i]);
					Goods good = (Goods) manager.get(ModelNames.GOODS, id);
					typeId = good.getGoodsTypeId();				
					
					GoodsWrapper wrapper = new GoodsWrapper(good);

					//获得品牌名称
					String brandId = good.getBrandId();
					Brand brand = (Brand) manager.get(ModelNames.BRAND, brandId);
					wrapper.put("brandName", brand.getBrandName());
					
					Set<GoodsAttr> gas = good.getAttributes();
					
					//获得商品属性，以及属性值
					List<Attribute> attributes = manager.getList(ModelNames.ATTRIBUTE, null);
					Map<String, Attribute> attributeMap = new HashMap<String, Attribute>();					
					Map<String, Map<String, String>> properties = new HashMap<String, Map<String, String>>();
					
					for(Attribute a : attributes) {
						attributeMap.put(a.getPkId(), a);
					}
					
					for(GoodsAttr ga:gas) {
						String attributeId = ga.getAttrId();
						Attribute a = attributeMap.get(attributeId);
						if(a == null)
							continue;
						
						String attributeName = a.getAttrName();
						String attributeValue = ga.getAttrValue();
						Map<String, String> attributeDetail = new HashMap<String, String>();
						attributeDetail.put("name", attributeName);
						attributeDetail.put("value", attributeValue);
						properties.put(attributeId, attributeDetail);
					} 					
					wrapper.put("properties", properties);
					
					//获得商品评论等级
			    	Condition condition = new Condition(IComment.ID_VALUE,Condition.EQUALS,good.getPkId());
			        Criteria criteria = new Criteria();
			        criteria.addCondition(condition);
			        List<Comment> comments = (List<Comment>) manager.getList(ModelNames.COMMENT,criteria);
			        Long sum = 0L;
			        int number = comments.size();
			        for(Iterator iterator = comments.iterator();iterator.hasNext();) {
			        	Comment comment = (Comment) iterator.next();
			        	Long rank = comment.getCommentRank();
			        	sum += rank;
			        }
			        String rank = "0";
			        if(number > 0)
			        	rank = ((int)Math.ceil((double)sum / number)) + "";  
			        wrapper.put("commentRank", rank);
			        
			        //从比较立标中除去该商品，传回页面，用于删除比较商品
			        String ids = goods;
			        if(i == 0) {
			        	ids = ids.replace(id + ",", "");
			        } else {
			        	ids = ids.replace("," + id, "");
			        }			        
			        wrapper.put("ids", "goods=" + ids);
			        
					goodsList.add(wrapper);
				}
//				for(GoodsWrapper gw : goodsList) {
//					Map<String, Map<String, String>> p = (Map<String, Map<String, String>>) gw.get("properties");
//					Set<String> keys = p.keySet();
//					for(String key : keys) {
//						System.out.println("key>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
//						System.out.println(key);
//						System.out.println("value>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
//						System.out.println(p.get(key).get("name") + ":" + p.get(key).get("value"));
//						System.out.println("");
//					}
//				}
				//根据商品类型获得属性
		        GoodsType goodsType = (GoodsType) manager.get(ModelNames.GOODSTYPE, typeId);
		        Set<Attribute> attributes = goodsType.getAttributes();
		        for(Attribute a : attributes) {
		        	attribute.put(a.getPkId(), a.getAttrName());
		        }		        				
		        
		        request.setAttribute("goodsList", goodsList);
		        request.setAttribute("attribute", attribute);
		        return SUCCESS;				
			}
		}

		return null;
	}

}
