package com.jcommerce.gwt.server;

import java.io.ByteArrayInputStream;
import java.io.ObjectInputStream;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.SortedMap;
import java.util.TreeMap;

import javax.servlet.http.HttpSession;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang.StringUtils;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.extjs.gxt.ui.client.data.BaseListLoadResult;
import com.extjs.gxt.ui.client.data.BasePagingLoadResult;
import com.extjs.gxt.ui.client.data.ListLoadConfig;
import com.extjs.gxt.ui.client.data.ListLoadResult;
import com.extjs.gxt.ui.client.data.PagingLoadConfig;
import com.extjs.gxt.ui.client.data.PagingLoadResult;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.jcommerce.core.model.AreaRegion;
import com.jcommerce.core.model.Attribute;
import com.jcommerce.core.model.Constants;
import com.jcommerce.core.model.Goods;
import com.jcommerce.core.model.GoodsAttr;
import com.jcommerce.core.model.GoodsType;
import com.jcommerce.core.model.ModelObject;
import com.jcommerce.core.model.OrderGoods;
import com.jcommerce.core.model.OrderInfo;
import com.jcommerce.core.model.Payment;
import com.jcommerce.core.model.Shipping;
import com.jcommerce.core.model.ShippingArea;
import com.jcommerce.core.model.ShopConfig;
import com.jcommerce.core.model.UserAddress;
import com.jcommerce.core.service.CustomizedManager;
import com.jcommerce.core.service.IDefaultManager;
import com.jcommerce.core.service.config.IShopConfigManager;
import com.jcommerce.core.service.config.ShopConfigMeta;
import com.jcommerce.core.service.payment.IPaymentMetaManager;
import com.jcommerce.core.service.payment.PaymentConfigFieldMeta;
import com.jcommerce.core.service.payment.PaymentConfigMeta;
import com.jcommerce.core.service.shipping.IShippingMetaManager;
import com.jcommerce.core.service.shipping.ShippingAreaFieldMeta;
import com.jcommerce.core.service.shipping.ShippingAreaMeta;
import com.jcommerce.core.service.shipping.ShippingConfigMeta;
import com.jcommerce.core.service.shipping.impl.BaseShippingMetaPlugin;
import com.jcommerce.core.service.shipping.impl.EMS;
import com.jcommerce.core.util.CommonUtil;
import com.jcommerce.core.util.MyPropertyUtil;
import com.jcommerce.gwt.client.ISpecialService;
import com.jcommerce.gwt.client.ModelNames;
import com.jcommerce.gwt.client.form.BeanObject;
import com.jcommerce.gwt.client.model.IAdminUser;
import com.jcommerce.gwt.client.model.IAttribute;
import com.jcommerce.gwt.client.model.IComment;
import com.jcommerce.gwt.client.model.IOrderAction;
import com.jcommerce.gwt.client.model.IOrderGoods;
import com.jcommerce.gwt.client.model.IPayment;
import com.jcommerce.gwt.client.model.IShipping;
import com.jcommerce.gwt.client.model.IShippingArea;
import com.jcommerce.gwt.client.panels.system.IShopConfigMeta;
import com.jcommerce.gwt.client.panels.system.PaymentConfigFieldMetaForm;
import com.jcommerce.gwt.client.panels.system.PaymentConfigMetaForm;
import com.jcommerce.gwt.client.panels.system.ShippingAreaFieldMetaForm;
import com.jcommerce.gwt.client.panels.system.ShippingAreaMetaForm;
import com.jcommerce.gwt.client.panels.system.ShippingConfigMetaForm;
import com.jcommerce.gwt.client.service.Condition;
import com.jcommerce.gwt.client.service.Criteria;
import com.jcommerce.web.util.LibCommon;

public class SpecialServiceImpl extends RemoteServiceServlet implements ISpecialService{
    
    WebApplicationContext ctx;
    
    public SpecialServiceImpl() {
//        String[] paths = {"/WEB-INF/applicationContext.xml"};
//        ApplicationContext ctx = new ClassPathXmlApplicationContext(paths);

    }
    private IDefaultManager getDefaultManager() {
    	IDefaultManager manager = (IDefaultManager)ctx.getBean("DefaultManager");
    	return manager;
    }
    private CustomizedManager getCustomizedManager() {
    	CustomizedManager manager = (CustomizedManager)ctx.getBean("CustomizedManager");
    	return manager;
    }
    private IShopConfigManager getShopConfigManager() {
    	IShopConfigManager manager = (IShopConfigManager)ctx.getBean("ShopConfigManager");
    	return manager;
    }
	private IPaymentMetaManager getPaymentMetaManager() {
		return (IPaymentMetaManager)ctx.getBean("PaymentMetaManager");
	}
	private IShippingMetaManager getShippingMetaManager() {
		return (IShippingMetaManager)ctx.getBean("ShippingMetaManager");
	}
	private BaseShippingMetaPlugin getBaseShippingMetaPlugin() {
		return (BaseShippingMetaPlugin)ctx.getBean("BaseShippingMetaPlugin");
	}
	@Override
	public void init() {
    	ctx = WebApplicationContextUtils.getWebApplicationContext(getServletContext());		
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
    
    protected Map<String, Object> getProperties(ModelObject obj, List<String> wantedFields) throws IllegalAccessException, InvocationTargetException, NoSuchMethodException {
        Map<String, Object> props = new HashMap<String, Object>();
       
        if(wantedFields==null) {
        Field[] fields = obj.getClass().getDeclaredFields();
        for (int i = 0 ; i < fields.length ; i++) {
            if (Modifier.isStatic(fields[i].getModifiers())) {
                continue;
            }
            String name = fields[i].getName();
//            if(wantedFields!=null && !wantedFields.contains(name)) {
//              // to skip unwanted fields
//              continue;
//            }
            Object value = getBeanProperty(obj, name);
            if (value instanceof ModelObject) {
                String id = getId((ModelObject)value);
                props.put(name, id);
            } else if (value instanceof Collection) {
                String s = null;
                for (Iterator itv = ((Collection)value).iterator(); itv.hasNext();) {
                    ModelObject o = (ModelObject) itv.next();
                    String id = getId(o);
                    if (s == null) {
                        s = id;
                    } else {
                        s += "," + id;
                    }
                }
                props.put(name, s);
            } else if (value != null){
                props.put(name, value);
            }
        }
        }
        
        else {
            for(String wantedField : wantedFields) {
                String[] chainedNames = StringUtils.split(wantedField, ".");
                int i=1, noOfNames = chainedNames.length;
                ModelObject curObj = obj;
                for(String name: chainedNames) {
                  Object value = getBeanProperty(curObj, name);
                  if(i == noOfNames) {
                     if(value instanceof ModelObject || value instanceof Collection) {
                         throw new RuntimeException("reached the end of chainedNames but found no simple value");
                     }
                     if(value!=null) {
                         props.put(wantedField, value);
                         break;
                     }
                  }
                  else {
                      if(value instanceof ModelObject) {
                          curObj = (ModelObject)value;
                      }
                      else {
                          throw new RuntimeException("not reached the end of chainedNames but found a simple value");
                      }
                  }
                  i++;
                }
            }
        }

        return props;
    }
    protected String getId(ModelObject obj) {
        try {
            Method m = obj.getClass().getMethod("getId", new Class[0]);
            if (m == null) {
                m = obj.getClass().getMethod("getID", new Class[0]);
            }
            if (m == null) {
                throw new RuntimeException("Method getId() not found: "+obj.getClass().getName());
            }
            
            Object id = m.invoke(obj, new Object[0]);
            return id.toString();
        } catch (SecurityException e) {
            throw new RuntimeException(e.toString());
        } catch (IllegalArgumentException e) {
            throw new RuntimeException(e.toString());
        } catch (NoSuchMethodException e) {
            throw new RuntimeException(e.toString());
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e.toString());
        } catch (InvocationTargetException e) {
            throw new RuntimeException(e.toString());
        }
    }
    protected Object getBeanProperty(ModelObject obj, String field) {
        try {
            Method method = null;
            try {
                method = obj.getClass().getMethod("get" + firstUpperCase(field), new Class[0]);
            } catch (Exception e) {
                method = obj.getClass().getMethod("is" + firstUpperCase(field), new Class[0]);
            }
            
            if (method == null) {
                throw new RuntimeException("Read method not found: " + field);
            }
            
            return method.invoke(obj);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        
        return null;
    }
    protected String firstUpperCase(String s) {
        if (s == null || s.length() < 2) {
            return s;
        }
        return s.substring(0, 1).toUpperCase() + s.substring(1);
    }
    public PagingLoadResult<BeanObject> getGoodsTypeListWithAttrCount(String modelName, Criteria criteria, 
            List<String> wantedFields, PagingLoadConfig pgc) {
        List<GoodsType> res = new ArrayList<GoodsType>();
        CustomizedManager manager = getCustomizedManager();
        int total = manager.getGoodsTypeListWithAttrCount(res, pgc.getOffset(), pgc.getLimit(), convert(criteria));
        List<BeanObject> list = new ArrayList<BeanObject>();
        
        for (Iterator it = res.iterator(); it.hasNext();) {
            ModelObject model = (ModelObject) it.next();
            try {
                Map<String, Object> props = getProperties(model, wantedFields);
                list.add(new BeanObject(model.getClass().getSimpleName(), props));
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            }
        }
        
        
        if (pgc.getSortInfo().getSortField() != null) {
            final String sortField = pgc.getSortInfo().getSortField();
            if (sortField != null) {
              Collections.sort(list, pgc.getSortInfo().getSortDir().comparator(new Comparator() {
                public int compare(Object o1, Object o2) {
                  Object s1 = ((BeanObject)o1).get(sortField);
                  Object s2 = ((BeanObject)o2).get(sortField);
                  if (s1 instanceof Comparable && s2 instanceof Comparable) {
                      return ((Comparable)s1).compareTo((Comparable)s2);
                  }
                  if (s1 != null && s2 != null) {
                      return s1.toString().compareTo(s2.toString());
                  } else if (s2 != null) {
                      return -1;  
                  }
                  return 0;
                }
              }));
            }
          }

          return new BasePagingLoadResult(list, pgc.getOffset(), total);   
    }
    
    
    public PaymentConfigMetaForm getPaymentConfigMeta(String paymentId) {
        PaymentConfigMeta configMeta = getPaymentMetaManager().getPaymentConfigMeta(paymentId);
        Map<String, PaymentConfigFieldMeta> fields = configMeta.getFieldMetas();
//        BeanUtils.copyProperties();
        PaymentConfigMetaForm res = new PaymentConfigMetaForm();
        Map<String, PaymentConfigFieldMetaForm> resfields = new HashMap<String, PaymentConfigFieldMetaForm>();
        
        res.setCod(configMeta.getIsCod());
        res.setCode(configMeta.getPayCode());
        res.setDescription(configMeta.getPayDesc());
        res.setEnabled(configMeta.getEnabled());
        res.setId(configMeta.getPkId());
        res.setPayName(configMeta.getPayName());
        res.setOnline(configMeta.getIsOnline());
        res.setPayFee(configMeta.getPayFee());
        
        for(String key:fields.keySet()) {
            PaymentConfigFieldMeta fieldMeta = fields.get(key);
            PaymentConfigFieldMetaForm resfield = new PaymentConfigFieldMetaForm();
            resfield.setLable(fieldMeta.getLable());
            resfield.setOptions(fieldMeta.getOptions());
            resfield.setTip(fieldMeta.getTip());
            resfields.put(key, resfield);
        }
        res.setFieldMetas(resfields);
        res.setFieldValues(configMeta.getFieldValues());
        return res;
        
    }
    
    public ListLoadResult<BeanObject> getPaymentMetaList(ListLoadConfig config) {
        List<Map<String, Object>> maps = getPaymentMetaManager().getCombinedPaymentMetaList();
        List<BeanObject> objs = new ArrayList<BeanObject>();
        for(Map<String, Object> map:maps) {
            objs.add(new BeanObject(ModelNames.PAYMENT_META, map));
        }
        return new BaseListLoadResult<BeanObject>(objs);
    }
    
    public ListLoadResult<Map<String, Object>> getMyPaymentMetaList(ListLoadConfig config) {
        List<Map<String, Object>> maps = getPaymentMetaManager().getCombinedPaymentMetaList();
        
        List<Map<String, Object>> objs = new ArrayList<Map<String, Object>>();
        for(Map<String, Object> map:maps) {
            objs.add(map);
        }
        return new BaseListLoadResult<Map<String, Object>>(objs);
    }
    
	public String newOrder(BeanObject obj) {
        System.out.println("newObject("+obj.getModelName());
        IDefaultManager manager = (IDefaultManager)ctx.getBean("DefaultManager");
        String res = null;
        Date createDate = new Date();

        try {
        	ModelObject to = (ModelObject)Class.forName(obj.getModelName()).newInstance();
            MyPropertyUtil.form2To(to, obj.getProperties());
            ((OrderInfo)to).setOrderSn(CommonUtil.getOrderSN(createDate));
            ((OrderInfo)to).setAddTime(createDate.getTime());
        	res = manager.txadd(to);
        	
        } catch (Exception e) {
        	e.printStackTrace();
        	throw new RuntimeException(e);
        }
        
        
        return res;
	}

    public Boolean savePayment(Map<String, Object> props) {
        try {
        	getPaymentMetaManager().savePaymentConfig(props);
            return true;
        } catch (Exception e) {
            return false;    
        }
        
    }
    
    public Boolean installPayment(String paymentCode) {
        try {
        	getPaymentMetaManager().install(paymentCode);
            return true;
        } catch (Exception e) {
            return false;    
        }

    }
    public Boolean uninstallPayment(String paymentId) {
        try {
        	getPaymentMetaManager().uninstall(paymentId);
            return true;
        } catch (Exception e) {
            return false;    
        }

    }

    public ListLoadResult<ShippingConfigMetaForm> getCombinedShippingMetaList(ListLoadConfig config) {
        List<ShippingConfigMeta> metas = getShippingMetaManager().getCombinedShippingMetaList();
        
        List<ShippingConfigMetaForm> objs = new ArrayList<ShippingConfigMetaForm>();
        for(ShippingConfigMeta meta:metas) {
        	ShippingConfigMetaForm form = new ShippingConfigMetaForm();
        	form.setPkId(meta.getPkId());
        	form.setShippingCode(meta.getShippingCode());
        	form.setShippingName(meta.getShippingName());
        	form.setShippingDesc(meta.getShippingDesc());
        	form.setSupportCod(meta.getSupportCod());
        	form.setAuthor(meta.getAuthor());
        	form.setWebsite(meta.getWebsite());
        	form.setVersion(meta.getVersion());
        	form.setInstall(meta.getInstall());
        	
            objs.add(form);
        }
        return new BaseListLoadResult<ShippingConfigMetaForm>(objs);
    }
    
    
    public ShippingConfigMetaForm getShippingConfigMeta(String shippingId) {
        ShippingConfigMeta configMeta = getShippingMetaManager().getShippingConfigMeta(shippingId);
        ShippingConfigMetaForm res = new ShippingConfigMetaForm();
        try {
        	BeanUtils.copyProperties(res, configMeta);
        } catch (Exception ex) {
        	ex.printStackTrace();
        	throw new RuntimeException(ex);
        }
        return res;
    }
    
    public Boolean installShipping(String shippingCode) {
    	try {
    		getShippingMetaManager().install(shippingCode);
    		return true;
    	}catch (Exception ex) {
    		ex.printStackTrace();
    		throw new RuntimeException(ex);
    	}
    }
    public Boolean uninstallShipping(String shippingId) {
    	try {
    		getShippingMetaManager().uninstall(shippingId);
    		return true;
    	}catch (Exception ex) {
    		ex.printStackTrace();
    		throw new RuntimeException(ex);
    	}
    }
    public Boolean saveShipping(ShippingConfigMetaForm form) {
        try {
        	ShippingConfigMeta to = new ShippingConfigMeta();
        	BeanUtils.copyProperties(to, form);
        	getShippingMetaManager().saveShippingConfig(to);
            return true;
        } catch (Exception e) {
        	e.printStackTrace();
            return false;    
        }
    }
    public ShippingAreaMetaForm getShippingAreaMeta(String shippingAreaId, String shippingId) {
    	try {
    		ShippingAreaMeta meta = getShippingMetaManager().getShippingAreaMeta(shippingAreaId, shippingId);
    		ShippingAreaMetaForm form = new ShippingAreaMetaForm();
    		
    		form.setPkId(meta.getPkId());
    		form.setName(meta.getShippingAreaName());
//    		form.setShipping(meta.getShipping().getPkId());

    		form.setFieldValues(meta.getFieldValues());
    		
    		Map<String, ShippingAreaFieldMeta> fields = meta.getFieldMetas();
    		Map<String, ShippingAreaFieldMetaForm> resfields = new HashMap<String, ShippingAreaFieldMetaForm>();
            for(String key:fields.keySet()) {
            	ShippingAreaFieldMeta fieldMeta = fields.get(key);
            	ShippingAreaFieldMetaForm resfield = new ShippingAreaFieldMetaForm();
                resfield.setLable(fieldMeta.getLabel());

                resfields.put(key, resfield);
            }
            form.setFieldMetas(resfields);
    		return form;
    		
    		
    	} catch (Exception e) {
    		e.printStackTrace();
    		throw new RuntimeException(e);    
    	}
    }
    public ListLoadResult<BeanObject> getShippingAreaWithRegionNames(String shippingId, ListLoadConfig pgc) {
    	try {
    		CustomizedManager manager = getCustomizedManager();
    		List<ShippingArea> list = new ArrayList<ShippingArea>();
    		manager.getShippingAreaWithRegionName(list, shippingId);
    		List<BeanObject> res = new ArrayList<BeanObject>();
    		for(ModelObject obj:list) {
    			Map<String, Object> map = MyPropertyUtil.to2Form(obj,null);
    			res.add(new BeanObject(ShippingArea.class.getName(), map));
    		}
    		return new BaseListLoadResult<BeanObject>(res);
    		
    		
    	} catch (Exception e) {
    		e.printStackTrace();
    		throw new RuntimeException();    
    	}
    }
    public ListLoadResult<BeanObject> getAreaRegionListWithName(String shippingAreaId){
    	try {
    		CustomizedManager manager = getCustomizedManager();
    		List<AreaRegion> list = new ArrayList<AreaRegion>();
    		manager.getAreaRegionListWithName(list, shippingAreaId);
    		List<BeanObject> res = new ArrayList<BeanObject>();
    		for(ModelObject obj:list) {
    			Map<String, Object> map = MyPropertyUtil.to2Form(obj,null);
    			res.add(new BeanObject(AreaRegion.class.getName(), map));
    		}
    		return new BaseListLoadResult<BeanObject>(res);
    		
    		
    	} catch (Exception e) {
    		e.printStackTrace();
    		throw new RuntimeException();    
    	}
    }
    
    public Boolean saveShippingArea(BeanObject shippingArea) {
    	try {
    		ShippingArea to = new ShippingArea();
    		MyPropertyUtil.form2To(to, shippingArea.getProperties());
    		List<String> areaRegionIds = shippingArea.get(IShippingArea.AREA_REGIONS);
    		for(String rid: areaRegionIds) {
    			AreaRegion ar = new AreaRegion();
    			ar.setRegionId(rid);
//    			ar.setShippingArea(to);
    			to.getAreaRegions().add(ar);
    			
    		}
    		
    		getShippingMetaManager().saveShippingArea(to, shippingArea.getProperties());
    		
    		return true;
    	} catch (Exception e) {
    		e.printStackTrace();
    		throw new RuntimeException();    
    	}
    }
    
    public SortedMap<Integer, List<BeanObject>> getCombinedShopConfigMetaMap() {
    	try {
    		String locale = (String)getThreadLocalRequest().getSession().getAttribute(IAdminConstants.KEY_LOCALE);
    		
    	SortedMap<Integer, List<BeanObject>> resMap = new TreeMap<Integer, List<BeanObject>>();
    	SortedMap<Integer, List<ShopConfigMeta>> map = getShopConfigManager().getCombinedShopConfigMetaMap(locale);
    	for(Integer i : map.keySet()) {
    		List<BeanObject> resList = resMap.get(i);
    		if(resList==null) {
    			resList = new ArrayList<BeanObject>();
    			resMap.put(i, resList);
    		}
    		List<ShopConfigMeta> list = map.get(i);
    		for(ShopConfigMeta scm : list) {
    			BeanObject res = new BeanObject();
    			res.set(IShopConfigMeta.CODE, scm.getCode());
    			res.set(IShopConfigMeta.STORE_RANGE, scm.getStoreRange());
    			res.set(IShopConfigMeta.LABEL, scm.getLabel());
    			res.set(IShopConfigMeta.GROUP, scm.getGroup());
    			res.set(IShopConfigMeta.TYPE, scm.getType());
    			res.set(IShopConfigMeta.PK_ID, scm.getPkId());
    			res.set(IShopConfigMeta.VALUE, scm.getValue());
    			resList.add(res);
    		}
    		
    	}
    	return resMap;
    	} catch (Exception e) {
    		e.printStackTrace();
    		throw new RuntimeException();    
    	}
    }
    
    public Boolean saveShopConfig(Map<String, BeanObject> formData) {
		try {
			// for tx we could move it to a manager method
			List<ShopConfig> tos = new ArrayList<ShopConfig>();
			for (BeanObject bo : formData.values()) {
				String pkId = bo.getString(IShopConfigMeta.PK_ID);
				String code = bo.getString(IShopConfigMeta.CODE);
				String value = bo.getString(IShopConfigMeta.VALUE);
				ShopConfig to = new ShopConfig();
				to.setPkId(pkId);
				to.setCode(code);
				to.setValue(value);
				tos.add(to);
			}
			
			getShopConfigManager().saveShopConfig(tos);
			
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException();
		}
	}
    public Map<String,String> getAdminUserInfo (){
    	try {
    		HttpSession session = this.getThreadLocalRequest().getSession();
    		Map<String,String> adminInfo = new HashMap<String,String>();
    	
    		adminInfo.put( IAdminUser.USER_NAME , (String)session.getAttribute(IAdminConstants.KEY_ADMIN_USERID));
			adminInfo.put( IAdminUser.EMAIL,  (String)session.getAttribute(IAdminConstants.KEY_ADMIN_USEREMAIL));
			adminInfo.put( IComment.IP_ADDRESS,  (String)session.getAttribute(IAdminConstants.KEY_ADMIN_USERIP));
    		return adminInfo;
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException();
		}
    }
    public Map<String, String> deserialize(String serializedConfig) {
    	try {
    		return getBaseShippingMetaPlugin().deserialize(serializedConfig);
    	} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException();
		}
    }
    public double calculate(String shippingCode, Double goodsWeight, Double goodsAmount, Map<String, String> configValues) {
    	try {
    		return getShippingMetaManager().calculate(shippingCode, goodsWeight, goodsAmount, configValues);
    	} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException();
		}
    }
    public Map<String, Map<String, String>> getAttributeMap(String id) {
        IDefaultManager manager = (IDefaultManager)ctx.getBean("DefaultManager");
        Map<String, Map<String, String>> spe = new HashMap<String, Map<String, String>>();
        try {

            Goods goods = (Goods) manager.get(ModelNames.GOODS, id);
            Set<GoodsAttr> gas = goods.getAttributes();            
            List<Attribute> as = manager.getList(ModelNames.ATTRIBUTE, null);
    		Map<String, Attribute> asMap = new HashMap<String, Attribute>();
    		for(Attribute a : as) {
    			asMap.put(a.getPkId(), a);
    		}
    		
    		for(GoodsAttr ga : gas) {
    			String aId = ga.getAttrId();
    			Attribute a = asMap.get(aId);
    			if(a==null) {
    				continue;
    			}
    			
    			if(!IAttribute.TYPE_ONLY.equals(a.getAttrType())) {
    				Map<String, String> value = new HashMap<String, String>();
    				value.put(ga.getAttrValue(), ga.getAttrPrice());
    				spe.put(a.getAttrName(), value);
    			}
    		}
    		return spe;
        	
        } catch (Exception e) {
        	e.printStackTrace();
        	throw new RuntimeException(e);
        }
    }
    public Map<String, String> getShippingConfig(String shippingId) {
    	IDefaultManager manager = (IDefaultManager)ctx.getBean("DefaultManager");
    	Map<String, String> shippingConfig = new HashMap<String, String>();
    	try {
    		Shipping shipping = (Shipping) manager.get(ModelNames.SHIPPING, shippingId);
    		for (ShippingArea shippingArea : shipping.getShippingAreas()) {
    			//shippingConfig = deserialize(shippingArea.getConfigure());
    			ByteArrayInputStream bis = new ByteArrayInputStream(Base64.decodeBase64(shippingArea.getConfigure().getBytes()));
                ObjectInputStream ois;
                ois = new ObjectInputStream(bis);
                shippingConfig = (Map<String, String>)ois.readObject();
    		}
    		return shippingConfig;
    		
    	} catch (Exception e) {
        	e.printStackTrace();
        	throw new RuntimeException(e);
        }
    }
    
    private Map<String, Object> payFee(String paymentId , double orderAmount , String codFee ,IDefaultManager manager) {
    	double payFee = 0 ;
    	Payment payment = paymentInfo( paymentId , manager );
    	String payName = payment.getPayName();
    	String rate = (payment.getIsCod() && (codFee != null )) ? codFee : payment.getPayFee();
    	
    	if(rate.indexOf("%") != -1){
    		/* 支付费用是一个比例 */
    		double val = Double.parseDouble(rate.substring(0,rate.indexOf("%"))) / 100 ;
    		payFee = val > 0 ? orderAmount * val / ( 1 - val ) : 0 ;
    	}
    	else{
    		payFee = Double.parseDouble(rate);
    	}
    	
    	Map<String, Object> payInfo = new HashMap<String, Object>();
    	payInfo.put("payFee", payFee);
    	payInfo.put("payName", payName);
    	return payInfo;
	}
    
    private Payment paymentInfo(String payId , IDefaultManager manager)
    {
    	Criteria criteria = new Criteria();
		Condition condition = new Condition();		
		
		condition.setField(IPayment.PK_ID);
		condition.setOperator(Condition.EQUALS);
		condition.setValue(payId);
		
		criteria.addCondition(condition);
		List<Payment> paymentList = manager.getList(ModelNames.PAYMENT, convert(criteria));
        if( paymentList.size() > 0 ){
        	return (Payment)paymentList.get(0);
        }
        else{
        	return null;
        }
    }
    
    private Double shippingFee( String shippingCode , String configure , double goodsWeight , double goodsAmount ) {
    	Map<String,String> shippingConfig = EMS.deserialize(configure);
    	// TODO 根据商品数量算运费
    	return calculate(shippingCode , goodsWeight , goodsAmount , shippingConfig );
	}
	private Map<String, Object> weightPrice(Long cartGeneralGoods, String orderId, IDefaultManager manager) {
    	
		Map<String,Object> weightPrice = new HashMap<String,Object>();
		double weight = 0;
		double number = 0;
		double amount = 0;
    	Criteria criteria = new Criteria();
    	criteria.addCondition(new Condition(IOrderGoods.ORDER_ID, Condition.EQUALS, orderId));
    	
    	List<OrderGoods> orderGoods = manager.getList(ModelNames.ORDERGOODS, convert(criteria));
		for(OrderGoods og : orderGoods) {
			String goodsId = og.getGoodsId();
			Goods goods = (Goods) manager.get(ModelNames.GOODS, goodsId);
			weight += goods.getGoodsWeight() * og.getGoodsNumber();
			number += og.getGoodsNumber();
			amount += og.getGoodsPrice() * og.getGoodsNumber();
		}
		
    	weightPrice.put("weight", new Double(weight));
    	weightPrice.put("number", new Double(number) );
    	weightPrice.put("amount", new Double(amount) );
    	
    	return weightPrice;
	}
	public Map<String,Object> shippingAreaInfo(String shippingId, List<String> regionIdList,IDefaultManager manager){
    	Map<String,Object> shippingAreaInfo = new HashMap<String,Object>();
		Criteria criteria = new Criteria();
		Condition condition = new Condition();		

		condition.setField(IShipping.PK_ID);
		condition.setOperator(Condition.EQUALS);
		condition.setValue(shippingId);
		
		Condition condition2 = new Condition();		

		condition2.setField(IShipping.ENABLED);
		condition2.setOperator(Condition.EQUALS);
		condition2.setValue("true");
		
		criteria.addCondition(condition);
		criteria.addCondition(condition2);
		List<Shipping> list = manager.getList(ModelNames.SHIPPING, convert(criteria));
		
		if( 0 < list.size() ){
			Shipping shipping = (Shipping)list.get(0);
			for (ShippingArea shippingArea : shipping.getShippingAreas()) {
				for (AreaRegion areaRegion : shippingArea.getAreaRegions()) {
					if(regionIdList.contains(areaRegion.getRegionId())){
						Map<String,String> shippingConfig = BaseShippingMetaPlugin.deserialize(shippingArea.getConfigure());
						if(shippingConfig.containsKey("payFee")){
							shippingAreaInfo.put("payFee", shippingConfig.get("payFee"));
						}
						else{
							shippingAreaInfo.put("payFee", new Double(0));
						}
						shippingAreaInfo.put("shippingCode", shipping.getShippingCode());
						shippingAreaInfo.put("shippingName", shipping.getShippingName());
						shippingAreaInfo.put("shippingDesc", shipping.getShippingDesc());
						shippingAreaInfo.put("insure", shipping.getInsure());
						shippingAreaInfo.put("supportCod", shipping.getSupportCod());
						shippingAreaInfo.put("configure", shippingArea.getConfigure());
					}
				};
			}
					
		}
		return shippingAreaInfo;
    }
	
	public Map<String, Object> getOrderFee(String orderId, String shippingId, String payId) {
		IDefaultManager manager = getDefaultManager();
		Map<String, Object> total = new HashMap<String, Object>();
		total.put("goodsPrice",0.0d);
		total.put("shippingInsurance",0.0d);
		total.put("shippingFee",0.0d);
		total.put("shippingName", "");
		total.put("payName", "");
		try {
			OrderInfo order = (OrderInfo) manager.get(ModelNames.ORDERINFO, orderId);
			if(shippingId != null)
				order.setShippingId(shippingId);
			if(payId != null)
				order.setPayId(payId);
			Criteria c = new Criteria();
			c.addCondition(new Condition(IOrderGoods.ORDER_ID, Condition.EQUALS, orderId));
			List<OrderGoods> orderGoods = manager.getList(ModelNames.ORDERGOODS, convert(c));
			for(OrderGoods og : orderGoods) {
				Double goodsPrice = Double.parseDouble(total.get("goodsPrice").toString());
				total.put("goodsPrice", goodsPrice + og.getGoodsPrice() * og.getGoodsNumber());
			}
			
			String shippingCodFee = null;
			if(StringUtils.isNotEmpty(order.getShippingId())) {
				List<String> regionIdList = new ArrayList<String>();
	        	regionIdList.add( (String)order.getCountry() );
	        	regionIdList.add( (String)order.getProvince() );
	        	regionIdList.add( (String)order.getCity() );
	        	regionIdList.add( (String)order.getDistrict());
	        	
	        	Map<String,Object> shippingInfo = shippingAreaInfo(order.getShippingId(),regionIdList,manager);
	        	if(!shippingInfo.isEmpty()){
	        		total.put("shippingName", shippingInfo.get("shippingName"));
	        		Map<String,Object> weightPrice = weightPrice(Constants.CART_GENERAL_GOODS , orderId , manager );
	        		total.put("shippingFee", shippingFee((String)shippingInfo.get("shippingCode"),(String)shippingInfo.get("configure"),(Double)weightPrice.get("weight"),(Double)weightPrice.get("amount")));
	        		if( ( order.getInsureFee() != 0 ) && (Integer.parseInt((String)shippingInfo.get("insure")) > 0 )){
	        			//total.setShippingInsure(shippingInsureFee( (Double)weightPrice.get("amount") , (String)shippingInfo.get("insure")));
	        			total.put("shippingInsurance", shippingInsureFee( (Double)weightPrice.get("amount") , (String)shippingInfo.get("insure")));
	        		}
	        		else{
	        			total.put("shippingInsurance",0);
	        		}
	        		
	        		if((Boolean)shippingInfo.get("supportCod")){
	        			shippingCodFee = ((Double)shippingInfo.get("payFee")).toString();
	        		}
	        	}
			}
        	
			double moneyPaid = order.getMoneyPaid();
        	total.put("amount", Double.parseDouble(total.get("goodsPrice").toString()) + Double.parseDouble(total.get("shippingFee").toString()) + Double.parseDouble(total.get("shippingInsurance").toString()) - moneyPaid);
        	total.put("payFee", 0);
        	if( StringUtils.isNotEmpty(order.getPayId())){
        		Map<String, Object> payInfo = payFee(order.getPayId(),(Double)total.get("amount"),shippingCodFee,manager);
        		total.put("payFee", payInfo.get("payFee"));
        		total.put("payName", payInfo.get("payName"));
            }
        	total.put("amount", (Double)total.get("amount") + Double.parseDouble(total.get("payFee").toString()));
			return total;
		} catch (Exception e) {
        	e.printStackTrace();
        	throw new RuntimeException(e);
        }
	}
	private double shippingInsureFee(double goodsAmount , String insure) {
		if(insure.indexOf("%") == -1){
    		return Double.parseDouble(insure);
    	}
    	else{
    		return Math.ceil( Double.parseDouble(insure.substring(0,insure.indexOf("%"))) * goodsAmount / 100 );
    	}
	}
	
	//删除订单，订单商品，订单操作
	public boolean deleteOrder(String orderId) {
		IDefaultManager manager = getDefaultManager();
		try {
			manager.txdelete(ModelNames.ORDERINFO, orderId);
			Criteria c = new Criteria();
			c.addCondition(new Condition(IOrderGoods.ORDER_ID, Condition.EQUALS, orderId));
			List<ModelObject> orderGoods = manager.getList(ModelNames.ORDERGOODS, convert(c));
			manager.txdeleteall(orderGoods);
			
			c.removeAllConditions();
			c.addCondition(new Condition(IOrderAction.ORDER_ID, Condition.EQUALS, orderId));
			List<ModelObject> orderAction = manager.getList(ModelNames.ORDERACTION, convert(c));
			manager.txdeleteall(orderAction);
			return true;
		} catch (Exception e) {
        	e.printStackTrace();
        	throw new RuntimeException(e);
        }
	}
	
	//合并订单
	public boolean mergeOrder(String fromId, String toId) {
		IDefaultManager manager = getDefaultManager();
		try {
			OrderInfo fromOrder = (OrderInfo) manager.get(ModelNames.ORDERINFO, fromId);
			OrderInfo toOrder = (OrderInfo) manager.get(ModelNames.ORDERINFO, toId);
			String userId = fromOrder.getUserId();
			
			OrderInfo newOrder = (OrderInfo) toOrder.clone();
			long addTime = new Date().getTime();
			newOrder.setAddTime(addTime);
			newOrder.setPkId("");
			newOrder.setGoodsAmount(newOrder.getGoodsAmount() + fromOrder.getGoodsAmount());
			
			String shippingCodFee = null;
			if(newOrder.getShippingId() != null) {
				// 重新计算配送费用
				Map<String,Object> weightPrice = getWeightPrice(fromOrder.getPkId(), toOrder.getPkId(), getDefaultManager());
				List<String> regionIdList = new ArrayList<String>();
	        	regionIdList.add( (String)toOrder.getCountry() );
	        	regionIdList.add( (String)toOrder.getProvince() );
	        	regionIdList.add( (String)toOrder.getCity() );
	        	regionIdList.add( (String)toOrder.getDistrict());
	        	Map<String,Object> shippingInfo = shippingAreaInfo(newOrder.getShippingId(),regionIdList,getDefaultManager());
	        	if(!shippingInfo.isEmpty()){
		        	newOrder.setShippingFee(shippingFee((String)shippingInfo.get("shippingCode"),(String)shippingInfo.get("configure"),(Double)weightPrice.get("weight"),(Double)weightPrice.get("amount")));
		        	if( ( newOrder.getInsureFee() != 0 ) && (Integer.parseInt((String)shippingInfo.get("insure")) > 0 )){
		        		newOrder.setInsureFee(shippingInsureFee( (Double)weightPrice.get("amount") , (String)shippingInfo.get("insure")));
	        		}
		        	else{
		        		newOrder.setInsureFee(0.0);
	        		}
		        	if((Boolean)shippingInfo.get("supportCod")){
	        			shippingCodFee = ((Double)shippingInfo.get("payFee")).toString();
	        		}
	        	}
			}
			// 合并余额、已付款金额
			newOrder.setSurplus(newOrder.getSurplus() + fromOrder.getSurplus());
			newOrder.setMoneyPaid(newOrder.getMoneyPaid() + newOrder.getMoneyPaid());
			if(newOrder.getPayId()!= ""){
				newOrder.setPayFee((Double) payFee(newOrder.getPayId(),newOrder.getGoodsAmount(),shippingCodFee,getDefaultManager()).get("payFee"));
	        }
			newOrder.setOrderAmount(newOrder.getGoodsAmount() + newOrder.getShippingFee() + newOrder.getInsureFee() + newOrder.getPayFee()
					- newOrder.getSurplus() - newOrder.getMoneyPaid());
			
			//删除原订单
			getDefaultManager().txdelete(ModelNames.ORDERINFO, fromId);
			getDefaultManager().txdelete(ModelNames.ORDERINFO, toId);
			
			getDefaultManager().txadd(newOrder);
			
			//更新订单商品
			Criteria criteria = new Criteria();
			criteria.addCondition(new Condition(IOrderGoods.ORDER_ID, Condition.EQUALS, fromId));
			List<OrderGoods> orderGoods = getDefaultManager().getList(ModelNames.ORDERGOODS, convert(criteria));
			criteria.removeAllConditions();
			criteria.addCondition(new Condition(IOrderGoods.ORDER_ID, Condition.EQUALS, toId));
			orderGoods.addAll(getDefaultManager().getList(ModelNames.ORDERGOODS, convert(criteria)));
			
			for(OrderGoods goods : orderGoods) {
				OrderGoods g = (OrderGoods) goods.clone();
				g.setOrderId(newOrder.getPkId());
				getDefaultManager().txdelete(ModelNames.ORDERGOODS, goods.getPkId());
				getDefaultManager().txadd(g);
				System.out.println(g.getOrderId());
			}
			return true;
		} catch (Exception e) {
        	return false;
        }
	}
	private Map<String, Object> getWeightPrice(String fromId, String toId, IDefaultManager manager){
		Map<String,Object> weightPrice = new HashMap<String,Object>();
    	double weight = 0;
		double number = 0;
		double amount = 0;
		
		Criteria criteria = new Criteria();
		criteria.addCondition(new Condition(IOrderGoods.ORDER_ID, Condition.EQUALS, fromId));
		List<OrderGoods> fromGoods = manager.getList(ModelNames.ORDERGOODS, convert(criteria));
		
		criteria.removeAllConditions();
		criteria.addCondition(new Condition(IOrderGoods.ORDER_ID, Condition.EQUALS, toId));
		List<OrderGoods> toGoods = manager.getList(ModelNames.ORDERGOODS, convert(criteria));
		toGoods.addAll(fromGoods);
		
		for(Iterator iterator = toGoods.iterator(); iterator.hasNext();) {
			OrderGoods orderGoods = (OrderGoods)iterator.next();
			String goodsId = orderGoods.getGoodsId();
			Goods goods = (Goods) manager.get(ModelNames.GOODS, goodsId);
			
			weight += goods.getGoodsWeight();
			number += orderGoods.getGoodsNumber();
			amount += orderGoods.getGoodsPrice() * orderGoods.getGoodsNumber();
		}

		String formatedWeight  = LibCommon.formatedWeight(weight);
		weightPrice.put("weight", new Double(weight));
		weightPrice.put("number", new Double(number) );
		weightPrice.put("amount", new Double(amount) );
		weightPrice.put("formatedWeight", formatedWeight );
    	return weightPrice;
	}
	
}
