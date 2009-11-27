package com.jcommerce.web.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.StringUtils;

import com.jcommerce.core.model.Attribute;
import com.jcommerce.core.model.Category;
import com.jcommerce.core.model.Goods;
import com.jcommerce.core.model.GoodsAttr;
import com.jcommerce.core.model.GoodsType;
import com.jcommerce.core.service.IDefaultManager;
import com.jcommerce.gwt.client.ModelNames;
import com.jcommerce.gwt.client.model.IAttribute;
import com.jcommerce.web.to.CategoryWrapper;
import com.jcommerce.web.to.Lang;

public class LibGoods {

	/**
	 * 获得指定分类同级的所有分类以及该分类下的子分类
	 * 
	 * @access public
	 * @param integer
	 *            $cat_id 分类编号
	 * @return array
	 */

	public static List<CategoryWrapper> getCategoriesTree(String categoryId,
			IDefaultManager manager) {

		String parentId = null;
		if (categoryId != null) {

		} else {

		}

		// TODO tree query
		// we have to do it in memory
		// index on parentId??

		List<Category> allCats = (List<Category>) manager.getList(ModelNames.CATEGORY, null);

		
		// child->parent
//		Map<String, String> cpMap = new HashMap<String, String>();
		
		// parent->children
		Map<String, List<CategoryWrapper>> pcMap = new HashMap<String, List<CategoryWrapper>>();
		// 1st round loop
		for (Category cat : allCats) {
//			cpMap.put(cat.getPkId(), cat.getParentId());
			List<CategoryWrapper> children = pcMap.get(cat.getParentId());
			if (children == null) {
				children = new ArrayList<CategoryWrapper>();
				pcMap.put(cat.getParentId(), children);
			}
			children.add(new CategoryWrapper(cat));
		}
		
		List<CategoryWrapper> level1 = pcMap.get(parentId);
		if(level1==null) {
			// to overcome NPE in case there is no any category at all
			level1 = new ArrayList<CategoryWrapper>();
		}
		for (CategoryWrapper cw : level1) {
			List<CategoryWrapper> level2 = pcMap.get(cw.getPkId());
			if (level2 != null) {
				cw.getChildren().addAll(level2);
			}
		}

		return level1;
	}

	
	
	/**
	 * 获得商品的属性和规格
	 *
	 * @access  public
	 * @param   integer $goods_id
	 * @return  array
	 */
	public static GoodsPropertiesResult getGoodsProperties(String goodsId, IDefaultManager manager) {
		/* 对属性进行重新排序和分组 */
		
		Goods goods = (Goods)manager.get(ModelNames.GOODS, goodsId);
		String gtId = goods.getGoodsTypeId();
		GoodsType gt = (GoodsType)manager.get(ModelNames.GOODSTYPE, gtId);
		String attrGroup = gt.getAttrGroup();
		String[] attrGroups = StringUtils.split(attrGroup);
		List<String> attrGroupList = attrGroups==null? new ArrayList<String>() : Arrays.asList(attrGroups);
		
		
		GoodsPropertiesResult result = new GoodsPropertiesResult();
		
		
		
		/* 获得商品的规格 */
		// retrieve and loop all attributes in memeory, to avoid many query to DS
		List<Attribute> as = manager.getList(ModelNames.ATTRIBUTE, null);
		Map<String, Attribute> asMap = new HashMap<String, Attribute>();
		for(Attribute a : as) {
			asMap.put(a.getPkId(), a);
		}
		
		Set<GoodsAttr> gas = goods.getAttributes();
		for(GoodsAttr ga:gas) {
			String aId = ga.getAttrId();
			Attribute a = asMap.get(aId);
			if(a==null) {
				debug("in [getGoodsProperties]: attribute: "+aId+" does not exist. Data is not consistent!!!");
				continue;
			}
			
			String group = null;
			int ag = a.getAttrGroup().intValue();
			if(ag>=0 && ag < attrGroupList.size()) {
				group = attrGroupList.get(ag);
			}
			if(group==null) {
				group = Lang.getInstance().getString("goodsAttr");
			}
			
			
			List<Map<String, String>> props = result.getPro().get(group);
			if(props==null) {
				props = new ArrayList<Map<String, String>>();
				result.getPro().put(group, props);
			}
			if(IAttribute.TYPE_ONLY.equals(a.getAttrType())) {
				// it's normal property
				Map<String, String> prop = new HashMap<String, String>();
				prop.put("name", a.getAttrName());
				prop.put("value", ga.getAttrValue());
				props.add(prop);
			}
			/*修改,获得商品规格*/
			else {
				// TODO support non-simple properties
				String name = a.getAttrName();
				Specification spe = result.getSpe().get(name);
				if(spe == null) {
					spe = new Specification();
					result.getSpe().put(name, spe);
				}
				spe.setName(name);
				spe.setAttrType(a.getAttrType());
				List<Value> values = (List<Value>) spe.getValues();
				if(values == null) {
					values = new ArrayList<Value>();
				}
				Value v = new Value();
				v.setId(ga.getLongId()+"");
				v.setLabel(ga.getAttrValue());
				if(ga.getAttrPrice() != null)
					v.setPrice(Math.abs(Double.parseDouble(ga.getAttrPrice())));
				values.add(v);
				spe.setValues(values);
				
			}
			/*修改*/
			
			// TODO property link 
			
			
		}
		return result;
		
		
		
	}
	
	public static class GoodsPropertiesResult {
		private Map<String, List<Map<String, String>>> pro = new HashMap<String, List<Map<String, String>>>();
		private Map<String, Specification> spe = new HashMap<String, Specification>();

		public Map<String, List<Map<String, String>>> getPro() {
			return pro;
		}

		public void setPro(Map<String, List<Map<String, String>>> pro) {
			this.pro = pro;
		}

		public Map<String, Specification> getSpe() {
			return spe;
		}

		public void setSpe(Map<String, Specification> spe) {
			this.spe = spe;
		}
		
	}
	public static class Specification {
		private String name;
		private long attrType;
		public String getName() {
			return name;
		}
		public void setName(String name) {
			this.name = name;
		}
		public long getAttrType() {
			return attrType;
		}
		public void setAttrType(long attrType) {
			this.attrType = attrType;
		}
		public List<Value> getValues() {
			return values;
		}
		public void setValues(List<Value> values) {
			this.values = values;
		}
		private List<Value> values;
	}
	
	public static class Value {
		private String id;
		private String label;
		private double price;
		private String formatPrice;
		public void setId(String id) {
			this.id = id;
		}
		public String getId() {
			return id;
		}
		public void setLabel(String label) {
			this.label = label;
		}
		public String getLabel() {
			return label;
		}
		public double getPrice() {
			return price;
		}
		public void setPrice(double price) {
			this.price = price;
		}
		public String getFormatPrice() {
			return WebFormatUtils.priceFormat(getPrice());
		}
		public void setFormatPrice(String formatPrice) {
			this.formatPrice = formatPrice;
		}
	}
	
	
	public static void debug(String s) {
		System.out.println("in [LibGoods]: "+s);
	}
}
