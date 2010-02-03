/**
 * Author: Bob Chen
 */

package com.jcommerce.gwt.server;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.jdo.annotations.PrimaryKey;

import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.extjs.gxt.ui.client.data.BasePagingLoadResult;
import com.extjs.gxt.ui.client.data.PagingLoadConfig;
import com.extjs.gxt.ui.client.data.PagingLoadResult;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.jcommerce.core.annotation.IsPK;
import com.jcommerce.core.model.ModelObject;
import com.jcommerce.core.model.Session;
import com.jcommerce.core.service.IDefaultManager;
import com.jcommerce.core.util.MyPropertyUtil;
import com.jcommerce.gwt.client.IDefaultService;
import com.jcommerce.gwt.client.ModelNames;
import com.jcommerce.gwt.client.form.BeanObject;
import com.jcommerce.gwt.client.model.IModelObject;
import com.jcommerce.gwt.client.resources.IShopConstants;
import com.jcommerce.gwt.client.service.Condition;
import com.jcommerce.gwt.client.service.Criteria;

public class DefaultServiceImpl extends RemoteServiceServlet implements IDefaultService {

//    private PagingListAction pagingAction;
//    private RegionManager regionManager;
//    private GoodsTypeManager goodsTypeManager;
//    private AttributeManager attributeManager; 
    
//    private IPaymentMetaManager paymentMetaManager; 
   
	public DefaultServiceImpl() {
    }
	
	WebApplicationContext springContext = null;
	
	@Override
	public void init() {
		
//      String[] paths = {"../applicationContext.xml"};
//      ApplicationContext ctx = new ClassPathXmlApplicationContext(paths);
		
		springContext = WebApplicationContextUtils.getWebApplicationContext(getServletContext());
		
        Properties beanProps = new Properties();
        InputStream is = getClass().getResourceAsStream("beans.properties");
        try {
            beanProps.load(is);
            is.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        //clear session log
        IDefaultManager defaultManager = (IDefaultManager)springContext.getBean("DefaultManager");
//      defaultManager.txdeleteall(defaultManager.getList(ModelNames.SESSION, null));
        for (Object ele : defaultManager.getList(ModelNames.SESSION, null)) {
			defaultManager.txdelete(ModelNames.SESSION, ((Session)ele).getPkId());
		}
        
//      regionManager = (RegionManager)ctx.getBean("RegionManager");
//      goodsTypeManager = (GoodsTypeManager)ctx.getBean("GoodsTypeManager");
//      attributeManager = (AttributeManager)ctx.getBean("AttributeManager");
//      paymentMetaManager = (IPaymentMetaManager)ctx.getBean("PaymentMetaManager");
	}


	
//    public com.jcommerce.gwt.client.panels.leontest.PaymentConfigMetaForm getPaymentConfigMeta(int paymentId) {
//        PaymentConfigMeta configMeta = paymentMetaManager.getPaymentConfigMeta(paymentId);
//        Map<String, PaymentConfigFieldMeta> fields = configMeta.getFieldMetas();
////        BeanUtils.copyProperties();
//        PaymentConfigMetaForm res = 
//            new PaymentConfigMetaForm();
//        Map<String, PaymentConfigFieldMetaForm> resfields = 
//            new HashMap<String, PaymentConfigFieldMetaForm>();
//        
//        res.setCod(configMeta.isCod());
//        res.setCode(configMeta.getCode());
//        res.setDescription(configMeta.getDescription());
//        res.setEnabled(configMeta.isEnabled());
//        res.setId(configMeta.getPkId());
//        res.setName(configMeta.getName());
//        res.setOnline(configMeta.isOnline());
//        res.setPayFee(configMeta.getPayFee());
//        
//        for(String key:fields.keySet()) {
//            PaymentConfigFieldMeta fieldMeta = fields.get(key);
//            PaymentConfigFieldMetaForm resfield = new PaymentConfigFieldMetaForm();
//            resfield.setLable(fieldMeta.getLable());
//            resfield.setOptions(fieldMeta.getOptions());
//            resfield.setTip(fieldMeta.getTip());
//            resfields.put(key, resfield);
//        }
////        com.jcommerce.gwt.client.panels.leontest.PaymentConfigFieldMeta();
//        res.setFieldMetas(resfields);
//        res.setFieldValues(configMeta.getFieldValues());
//        return res;
//        
//    }
//    
//    
//    public boolean savePayment(Map<String, Object> props) {
//        try {
//            paymentMetaManager.savePaymentConfig(props);
//            return true;
//        } catch (Exception e) {
//            return false;    
//        }
//        
//    }
//    
//    public boolean installPayment(String paymentCode) {
//        try {
//            paymentMetaManager.install(paymentCode);
//            return true;
//        } catch (Exception e) {
//            return false;    
//        }
//
//    }
//    public boolean uninstallPayment(int paymentId) {
//        try {
//            paymentMetaManager.uninstall(paymentId);
//            return true;
//        } catch (Exception e) {
//            return false;    
//        }
//
//    }
//    public ListLoadResult<BeanObject> getPaymentMetaList(ListLoadConfig config) {
//        List<Map<String, Object>> maps = paymentMetaManager.getCombinedPaymentMetaList();
//        List<BeanObject> objs = new ArrayList<BeanObject>();
//        for(Map<String, Object> map:maps) {
//            objs.add(new BeanObject(ModelNames.PAYMENT_META, map));
//        }
//        return new BaseListLoadResult<BeanObject>(objs);
//    }
//    
//    public ListLoadResult<Map<String, Object>> getMyPaymentMetaList(ListLoadConfig config) {
//        List<Map<String, Object>> maps = paymentMetaManager.getCombinedPaymentMetaList();
//        
//        List<Map<String, Object>> objs = new ArrayList<Map<String, Object>>();
//        for(Map<String, Object> map:maps) {
//            objs.add(map);
//        }
//        return new BaseListLoadResult<Map<String, Object>>(objs);
//    }
    
	public IDefaultManager getDefaultManager() {
		return (IDefaultManager)springContext.getBean("DefaultManager");
	}
	
    public boolean deleteObject(String modelName, String id) {
        System.out.println("deleteObject("+modelName+","+id);
//        return deleteAction.delete(modelName, id);

        IDefaultManager manager = getDefaultManager();
        boolean res;
        try {
        	res = manager.txdelete(modelName, id);
        	
        } catch (Exception e) {
        	e.printStackTrace();
        	throw new RuntimeException(e);
        }
        
        
        return res;
        
    }
    public int deleteObjects(String modelName, List<String> ids) {
    	System.out.println("deleteObjects("+modelName+", total:"+ids.size());
        IDefaultManager manager = getDefaultManager();
        int res = 0;
    	for (String id : ids) {
    		boolean r = false;
			try {
				r = manager.txdelete(modelName, id);
			} catch (Exception e) {
				e.printStackTrace();
			}
			if(r) {
				res++;
			}
		}        
        
        return res;
    	
    }
    
    
    public List<BeanObject> getBeans(String modelName, String[] ids) {
        System.out.println("getBeans("+modelName);
//        List<BeanObject> beans = new ArrayList<BeanObject>();
//        if (ids == null || ids.length == 0) {
//            throw new IllegalArgumentException("ids = null");
//        }
//        
//        for (String id : ids) {
//            beans.add(new BeanObject(modelName, readAction.getBean(modelName, id)));
//        }
//        return beans;
        throw new RuntimeException("not supported");
    }
    
    public BeanObject getBean(String modelName, String id) {
        System.out.println("getBean("+modelName+","+id); 
        try {
        	IDefaultManager manager = getDefaultManager();
        	ModelObject obj = manager.get(modelName, id);
        	return new BeanObject(modelName, MyPropertyUtil.to2Form(obj, null));
  
        }catch (RuntimeException e) {
        	throw e;
        }catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new RuntimeException(e);
		}
    }

    public List<BeanObject> getList(String modelName) {        
        return getList(modelName, null, null);
    }

    public List<BeanObject> getList(String modelName, Criteria criteria) {
    	return getList(modelName, criteria, null);
    }

//    public List<BeanObject> getList(String modelName, Criteria criteria, List<String> wantedFields) {
//        try {
//        	System.out.println("getList("+modelName);
//      		
//        		IDefaultManager manager = getDefaultManager();
//        		List<ModelObject> rs =  (List<ModelObject>)manager.getList(modelName, convert(criteria));
//        		List<BeanObject> res = new ArrayList<BeanObject>();
//        		for(ModelObject obj:rs) {
//        			Map<String, Object> map = MyPropertyUtil.to2Form(obj,null);
//        			res.add(new BeanObject(modelName, map));
//        		}
//        		return res;
//        			
// 
//
//		} catch (Exception e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//			throw new RuntimeException(e);
//		}
//    }
    private List<BeanObject> joinModel(String modelName,List<BeanObject> rs,Map<String,List<String>> relationMap) throws SecurityException, ClassNotFoundException{
    	List<BeanObject> joinedRes = rs;
    	if(relationMap!=null&&!relationMap.isEmpty()){
	    	Map<String,Map<String,BeanObject>> relationFields = new HashMap<String,Map<String,BeanObject>>();
			Field[] fields = Class.forName(modelName).getDeclaredFields();
	    	for(Field field:fields){
	    		if(field.getAnnotation(IsPK.class)==null){
	    			continue;
	    		}
	    		else {
	    		    String relationModelName = field.getAnnotation(IsPK.class).myclazz();
	    		    List<String> wantedModelFields = null;
		    		if(relationMap.containsKey(relationModelName)){
		    			if(relationMap.get(relationModelName)!=null&&!relationMap.get(relationModelName).isEmpty()){
		    				wantedModelFields = relationMap.get(relationModelName);
		    			}
		    			relationMap.get(relationModelName).add(IModelObject.PK_ID);
		    			Map<String,BeanObject> res = getResMap(relationModelName,null,wantedModelFields);
		    			relationFields.put(field.getName(), res);
		    		}
	    		}
	    	}
	    	for(BeanObject bean:rs){
	    		for(String key:relationFields.keySet()){
	    			String relationPk = bean.getString(key);
	    			bean.setValues(relationFields.get(key).get(relationPk).getProperties());
	    		}
	    
	    	}
    	}
    	return joinedRes;
    	
    }
    private Map<String,BeanObject> getResMap(String modelName,Criteria criteria,List<String> fields){
    	try{
	    	System.out.println("getList("+modelName);
	    	IDefaultManager manager = getDefaultManager();
			Map<String,BeanObject> res = new HashMap<String,BeanObject>();
			List<ModelObject> rs =  (List<ModelObject>)manager.getList(modelName,convert(criteria));
			for(ModelObject obj:rs){
				Map<String, Object> map = MyPropertyUtil.to2Form(obj,fields);
				String pkid = map.get(IModelObject.PK_ID).toString();
				map.remove(IModelObject.PK_ID);
				res.put(pkid, new BeanObject(modelName,map));
			}
		    return res;
    	}
    	catch(Exception e){
    		e.printStackTrace();
    		throw new RuntimeException(e);
    	}
    	
    }
    public List<BeanObject> getList(String modelName, Criteria criteria, Map<String,List<String>> wantedFields){
        try {
        	System.out.println("getList("+modelName);
    		IDefaultManager manager = getDefaultManager();
    		List<BeanObject> res = new ArrayList<BeanObject>();
    		List<ModelObject> rs =  (List<ModelObject>)manager.getList(modelName, convert(criteria));
    		List<String> mainModelFields = null;
    		if(wantedFields!=null&&!wantedFields.isEmpty()){
    			if(wantedFields.containsKey(modelName)){
        			mainModelFields = wantedFields.get(modelName);
        			wantedFields.remove(modelName);
        		}
    		}
    		
    		for(ModelObject obj:rs) {
    			Map<String, Object> map = MyPropertyUtil.to2Form(obj,mainModelFields);
    			res.add(new BeanObject(modelName, map));
    		}
    		if(wantedFields!=null&&!wantedFields.isEmpty()){
    			res = joinModel(modelName,res,wantedFields);
    		}
    		return res;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new RuntimeException(e);
		}
    }
    
    public PagingLoadResult<BeanObject> getPagingList(String modelName, PagingLoadConfig config) {
    	return getPagingList(modelName, null, null, config);
    }
    
    public PagingLoadResult<BeanObject> getPagingList(String modelName, Criteria criteria, PagingLoadConfig config) {
        return getPagingList(modelName, criteria, null, config);        
    }
    
    public PagingLoadResult<BeanObject> getPagingList(String modelName, Criteria criteria, Map<String,List<String>> wantedFields, PagingLoadConfig plc) {
        try {
        	System.out.println("getPagingList("+modelName);
//			return pagingAction.getPagingList(modelName, convert(criteria), wantedFields, config);
        	IDefaultManager manager = getDefaultManager();
			List<ModelObject> list = new ArrayList<ModelObject>();
			int totalLength = manager.getList(list, modelName, convert(criteria), plc.getOffset(), plc.getLimit());
			List<BeanObject> res = new ArrayList<BeanObject>();
			List<String> mainModelFields = null;
    		if(wantedFields!=null&&!wantedFields.isEmpty()){
    			if(wantedFields.containsKey(modelName)){
    				mainModelFields = wantedFields.get(modelName);
    				wantedFields.remove(modelName);
    			}
    		}
			for(ModelObject model:list) {
				res.add(new BeanObject(modelName, MyPropertyUtil.to2Form(model, mainModelFields)));
			}
			System.out.println("here");
			if(wantedFields!=null&&!wantedFields.isEmpty()){
				res = joinModel(modelName,res,wantedFields);
			}
			return new BasePagingLoadResult(res, plc.getOffset(), totalLength);
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new RuntimeException(e);
		}        
    }
    
    public boolean updateObject(String id, BeanObject props) {
        System.out.println("updateObject("+props.getModelName());
        
        if(IShopConstants.useJDO) {
            try {
				IDefaultManager manager = getDefaultManager();
				ModelObject to = (ModelObject) Class.forName(props.getModelName()).newInstance();
				MyPropertyUtil.form2To(to, props.getProperties());
				to.setPkId(id);
				boolean res = manager.txupdate(to);
				return res;
			} catch (Exception e) {
				e.printStackTrace();
				throw new RuntimeException(e);
			}
        }else {
//        	return updateAction.updateObject(props.getModelName(), id, props.getProperties());
        	return false;
        }
    }
    
    public String newObject(BeanObject props) {
        System.out.println("newObject("+props.getModelName());
        IDefaultManager manager = getDefaultManager();
        String res = null;

        try {
        	ModelObject to = (ModelObject)Class.forName(props.getModelName()).newInstance();
            MyPropertyUtil.form2To(to, props.getProperties());
        	res = manager.txadd(to);
        	
        } catch (Exception e) {
        	e.printStackTrace();
        	throw new RuntimeException(e);
        }
        return res;
    }   
    
    private com.jcommerce.core.service.Criteria convert(Criteria criteria) {
        if (criteria == null) {
            return null;
        }
        
        com.jcommerce.core.service.Criteria _criteria = new com.jcommerce.core.service.Criteria();
        List<Condition> conds = criteria.getConditions();
        for (Condition cond : conds) {
            com.jcommerce.core.service.Condition _cond = new com.jcommerce.core.service.Condition();
            _cond.setField(cond.getField());
            _cond.setValue(cond.getValue());
            _cond.setOperator(cond.getOperator());
            _criteria.addCondition(_cond);
        }
        return _criteria;
    }
    
//    public PagingLoadResult<BeanObject> getGoodsTypeListWithAttrCount(String modelName, Criteria criteria, List<String> wantedFields, PagingLoadConfig config) {
//        try {
//            return pagingAction.getPagingList(modelName, convert(criteria), wantedFields, config);
//        } catch (RuntimeException e) {
//            // TODO Auto-generated catch block
//            e.printStackTrace();
//            throw e;
//        }        
//    }
    
//    public PagingLoadResult<BeanObject> getGoodsTypeUnit(boolean needAttrNumber, PagingLoadConfig config){
//    	//Add something you like
//    	Map<String, Object> maps = new HashMap<String, Object>();
//    	if(needAttrNumber){    		
//    		try {
//				List<GoodsType> goodsTypeUnit = goodsTypeManager.getGoodsTypeList();				
//				List<BeanObject> objs = new ArrayList<BeanObject>();
//				com.jcommerce.core.service.Condition cond = new com.jcommerce.core.service.Condition();
//				cond.setField(IAttribute.GOODSTYPE);
//				cond.setOperator(Condition.EQUALS);
//				com.jcommerce.core.service.Criteria criteria = new com.jcommerce.core.service.Criteria();
//
//				for (Iterator<GoodsType> it = goodsTypeUnit.iterator(); it.hasNext();) {
//					GoodsType goodsType = it.next();
//					maps.put(GoodsType.NAME, goodsType.getName());
//					maps.put(GoodsType.ID, goodsType.getPkId());
//					maps.put(GoodsType.ENABLED, goodsType.isEnabled());
//					cond.setValue(goodsType.getPkId() + "");
//					criteria.addCondition(cond);
//					maps.put(GoodsType.ATTRCOUNT, attributeManager.getAttributeCount(criteria)+ "");
//					criteria.removeAllCondition();
//					maps.put(GoodsType.ATTRIBUTEGROUP, goodsType.getAttributeGroup());
//					objs.add(new BeanObject("GoodsType", maps));					
//				}				
//				return new BasePagingLoadResult(objs, config.getOffset(),goodsTypeUnit.size());
//			} catch (Exception ex) {
//				ex.printStackTrace();
//				return null;
//			}
//    	}
//    	else{
//    		return pagingAction.getPagingList("GoodsType", null, config);
//    	}
//    	
//    }
    
//    public List<BeanObject> regionChildList(String parent_id){     	
//        Map<String, Object> maps = new HashMap<String, Object>();
//        List<Region> children= regionManager.getChildList(parent_id);       
//        List<BeanObject> objs = new ArrayList<BeanObject>();
//        for (Iterator<Region> it = children.iterator(); it.hasNext();) {
//        	Region child = it.next();
//        	maps.put("name",child.getName());
//        	maps.put("id",child.getPkId());        	
//            objs.add(new BeanObject("Region", maps));
//        }
//        return objs;  	
//    }
}
