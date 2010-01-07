package com.jcommerce.gwt.server;

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
import java.util.Locale;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

import javax.servlet.http.HttpSession;

import org.apache.commons.beanutils.BeanUtils;
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
import com.jcommerce.core.model.GoodsType;
import com.jcommerce.core.model.ModelObject;
import com.jcommerce.core.model.OrderInfo;
import com.jcommerce.core.model.ShippingArea;
import com.jcommerce.core.model.ShopConfig;
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
import com.jcommerce.core.util.CommonUtil;
import com.jcommerce.core.util.MyPropertyUtil;
import com.jcommerce.gwt.client.ISpecialService;
import com.jcommerce.gwt.client.ModelNames;
import com.jcommerce.gwt.client.form.BeanObject;
import com.jcommerce.gwt.client.model.IAdminUser;
import com.jcommerce.gwt.client.model.IComment;
import com.jcommerce.gwt.client.model.IShippingArea;
import com.jcommerce.gwt.client.panels.system.IShopConfigMeta;
import com.jcommerce.gwt.client.panels.system.PaymentConfigFieldMetaForm;
import com.jcommerce.gwt.client.panels.system.PaymentConfigMetaForm;
import com.jcommerce.gwt.client.panels.system.ShippingAreaFieldMetaForm;
import com.jcommerce.gwt.client.panels.system.ShippingAreaMetaForm;
import com.jcommerce.gwt.client.panels.system.ShippingConfigMetaForm;
import com.jcommerce.gwt.client.service.Condition;
import com.jcommerce.gwt.client.service.Criteria;

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
    
    public SortedMap<Integer, List<BeanObject>> getCombinedShopConfigMetaMap(String locale) {
    	try {
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
    	
    		adminInfo.put( IAdminUser.USER_NAME , (String)session.getAttribute(IAdminUser.USER_NAME));
			adminInfo.put( IAdminUser.EMAIL,  (String)session.getAttribute(IAdminUser.EMAIL));
			adminInfo.put( IComment.IP_ADDRESS,  (String)session.getAttribute(IComment.IP_ADDRESS));
    		return adminInfo;
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException();
		}
    }
    public String getLocale(){
    	return (String)this.getThreadLocalRequest().getSession().getAttribute("locale");
    }
}
