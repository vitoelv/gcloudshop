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
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.extjs.gxt.ui.client.data.BasePagingLoadResult;
import com.extjs.gxt.ui.client.data.PagingLoadConfig;
import com.extjs.gxt.ui.client.data.PagingLoadResult;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.jcommerce.core.model.GoodsType;
import com.jcommerce.core.model.ModelObject;
import com.jcommerce.core.model.OrderInfo;
import com.jcommerce.core.service.CustomizedManager;
import com.jcommerce.core.service.IDefaultManager;
import com.jcommerce.core.util.CommonUtil;
import com.jcommerce.core.util.MyPropertyUtil;
import com.jcommerce.gwt.client.CustomizedService;
import com.jcommerce.gwt.client.form.BeanObject;
import com.jcommerce.gwt.client.service.Condition;
import com.jcommerce.gwt.client.service.Criteria;

public class CustomizedServiceImpl extends RemoteServiceServlet implements CustomizedService{
    
    WebApplicationContext ctx;
    
    public CustomizedServiceImpl() {
//        String[] paths = {"/WEB-INF/applicationContext.xml"};
//        ApplicationContext ctx = new ClassPathXmlApplicationContext(paths);
        

        
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
        CustomizedManager manager = (CustomizedManager)ctx.getBean("CustomizedManager");
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
}
