/**
* Author: Bob Chen
*/

package com.jcommerce.core.action;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Map;

import org.springframework.context.ApplicationContext;

import com.jcommerce.core.model.ModelObject;
import com.jcommerce.core.service.IDefaultManager;
import com.jcommerce.gwt.client.resources.IShopConstants;

public class MapUpdateAction extends MapAction {
    public MapUpdateAction(ApplicationContext ctx, BeanConfig config) {
        super(ctx, config);
    }
    

    public boolean updateObject(String modelName, String id, Map<String, Object> props) {
        System.out.println("updateObject("+modelName);
        
        try {
            IDefaultManager manager = getManager(modelName);
            String methodName = config.getGetMethod(modelName);
            Method method = manager.getClass().getMethod(methodName, String.class);
            
            ModelObject model = (ModelObject)method.invoke(manager, id);
            if (model != null) {
                copyProperties(props, model);
                id = saveObject(modelName, model);
                return id != null;
            } else {
                return false;
            }

        } catch (SecurityException e) {
            e.printStackTrace();
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        return false;
    }        
}
