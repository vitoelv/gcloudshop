/**
* Author: Bob Chen
*/

package com.jcommerce.core.action;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.context.ApplicationContext;

import com.jcommerce.core.model.ModelObject;

public class MapAction extends Action {
    public MapAction(ApplicationContext ctx, BeanConfig config) {
        super(ctx, config);
    }
    
    protected Map<String, Object> getProperties(ModelObject obj) throws IllegalAccessException, InvocationTargetException, NoSuchMethodException {
    	return getProperties(obj, null);
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
//            	// to skip unwanted fields
//            	continue;
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
                String[] chainedNames = StringUtils.split(wantedField, "_");
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
    
    protected void copyProperties(Map<String, Object> props, ModelObject obj) {
        System.out.println("props:"+props);
        HashMap<String, Object> _props = new HashMap<String, Object>(); 
        Field[] fields = obj.getClass().getDeclaredFields();
        for (int i = 0; i < fields.length; i++) {
            if (Modifier.isStatic(fields[i].getModifiers())) {
                continue;
            }
            
            Field field = fields[i];
            String fn = field.getName();
            Class type = field.getType(); 
            System.out.println("fn:"+fn+" field.getType():"+field.getType());
            if (!props.containsKey(fn)) {
                continue;
            }
            
            Object value = props.get(fn);
            if (value == null) {
            	_props.put(fn, value);
            	continue;
            }

            if (ModelObject.class.isAssignableFrom(type)) {
                String bean = type.getSimpleName();
                if (value instanceof String) {
                    // the value is ID
                    ModelObject mo = getModelObject(bean, (String)value);
                    _props.put(fn, mo);
                } else if (value instanceof Map) {
                    Map<String, Object> cprops = (Map<String, Object>)value;
                    ModelObject mo = createModelObject(bean);
                    copyProperties(cprops, mo);
                    _props.put(fn, mo);             
                } else {
                    throw new RuntimeException("Unknown value type: " + value+","+value.getClass()+" bean:"+bean);
                }    
            } else if (Collection.class.isAssignableFrom(type)) {
                Set set = new HashSet();
                if (value != null) {
                    String bean = config.getItemType(obj.getClass().getName(), fn);
                    if (value instanceof String) {
                        // the value is ID1,ID2,...,IDn
                        String[] ids = ((String)value).split(",");
                        for (int j = 0; j < ids.length; j++) {
                            ModelObject mo = getModelObject(bean, ids[j]);
                            set.add(mo);
                        }
                    } else if (value instanceof Collection) {
                        Collection c = (Collection)value;
                        for (Iterator it = c.iterator(); it.hasNext();) {
                            Map<String, Object> cprops = (Map<String, Object>) it.next();
                            ModelObject mo = createModelObject(bean);
                            copyProperties(cprops, mo);
                            setOwnerTo(mo, obj);
                            set.add(mo);
                        }
                    } else {
                        throw new RuntimeException("Unknown value type: " + value+","+value.getClass()+" bean:"+bean);
                    }
                }
                _props.put(fn, set);
            } else {
                _props.put(fn, value);
            }
        }
        
        System.out.println("_props:"+_props);
        try {
            BeanUtils.populate(obj, _props);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    } 
    
    /**
     * Such as gallery.setGoods(goods)
     */
    private void setOwnerTo(ModelObject obj, ModelObject owner) {
        String methodName = "set" + owner.getClass().getName();
        try {
            Method method = obj.getClass().getMethod(methodName, owner.getClass());
            if (method  == null) {
                System.out.println("Method not found: " + methodName);
                return;
            }
            
            method.invoke(obj, owner);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }
}
