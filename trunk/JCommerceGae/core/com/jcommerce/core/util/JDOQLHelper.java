package com.jcommerce.core.util;

import java.lang.reflect.Constructor;
import java.util.List;

import com.jcommerce.core.service.Condition;
import com.jcommerce.core.service.Criteria;
import com.jcommerce.core.service.Order;
import com.jcommerce.core.model.ModelObject;

public class JDOQLHelper {
	
    public static String getJdoql(String className, Criteria criteria, List<Object> paras) {
        try {
			StringBuffer jdoql = new StringBuffer("select from "+ className+" "); 
  
			if(criteria!=null) {
				jdoql.append(getWhereClause(className, criteria, paras));
			}
			
			System.out.println("JDOQL: "+jdoql.toString());
			return jdoql.toString();
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new RuntimeException(e);
		}
    }
    
    public String getClassName() {
//    	return dao.getModelClass().getSimpleName().toLowerCase();
//        return dao.getModelClass().getSimpleName();
    	return "";
    	
    }
    
    public static String getWhereClause(String className, Criteria criteria, List<Object> paras) {
        StringBuffer hql = new StringBuffer();
        StringBuffer paraDeclares = new StringBuffer();
//         = new ArrayList<String>(); 
        if (criteria != null && criteria.getConditions().size() > 0) {
            hql.append(" where ");
            paraDeclares.append(" parameters " );
            
            boolean first = true;
            List<Condition> conds = criteria.getConditions();
            for (Condition cond : conds) {
                if (!first) {
                	// TODO support OR among conditions (maybe subquery)
                    hql.append(" && ");
                    paraDeclares.append( " , ");
                }
                first = false;
                addCondtion(hql, paraDeclares,paras, cond, className);
            }
        }
        if (criteria != null && criteria.getOrders().size() > 0) {
            hql.append(" order by ");
            
            boolean first = true;
            List<Order> orders = criteria.getOrders();
            for (Order order : orders) {
                if (!first) {
                    hql.append(", ");
                }
                first = false;
                hql.append(className).append("."+order.getField());
                if (order.isAscend()) {
                    hql.append(" asc");
                } else {
                    hql.append(" desc");
                }
            }
        }
        
        hql.append(paraDeclares);
        return hql.toString();
    }
//    public String getWhereClause(Criteria criteria) {
//    	String objName = getClassName();
//        return getWhereClause(objName, criteria);
//
//    }
    
    public static void addCondtion(StringBuffer hql, StringBuffer paraDeclars, List<Object> paras, Condition cond, String className) {
    	String name = cond.getField();
    	String value = cond.getValue();
    	String paraName = name+"Param";
    	try {
			Class fieldType = Class.forName(className).getDeclaredField(name).getType();

            hql.append(" ").append(cond.getField());

            int op = cond.getOperator();
            if (op == Condition.LIKE) {
//                hql.append(" LIKE '%").append(cond.getValue()).append("%'");
            	throw new RuntimeException("no support!!!");
            } else if (op == Condition.CONTAINS) {
//                hql.append(cond.getValue()).append(" member of ").append(objName).append("."+cond.getField());
            	throw new RuntimeException("no support!!!");
            } else if (op == Condition.GREATERTHAN) {
            	hql.append(" >= ");
            } else if (op == Condition.LESSTHAN) {
                hql.append(" <= ").append(cond.getValue());
            } else if (op == Condition.EQUALS) {
                hql.append(" == ");
            } else {
                throw new RuntimeException("Unknown operator: " + op);
            }
            hql.append(paraName);
            
            paraDeclars.append(" ").append(fieldType.getName()).append(" ").append(paraName);
            if(ModelObject.class.isAssignableFrom(fieldType)) {
            	paras.add(value);
            } else {
            try {
            	Constructor<String> c = fieldType.getConstructor(String.class);
            	paras.add(c.newInstance(value));
            } catch (Exception ex) {
            	// should not happen for simple type...
            	ex.printStackTrace();
            	// if happens, need handle case by case
            	paras.add(value);
            }
            }
        
    	} catch(RuntimeException e) {
    		throw e;
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
			throw new RuntimeException(e1);
		}
    }
}
