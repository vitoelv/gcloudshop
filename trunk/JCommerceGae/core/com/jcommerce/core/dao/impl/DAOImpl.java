/**
* Author: Bob Chen
*/

package com.jcommerce.core.dao.impl;

import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.jdo.JDOException;
import javax.jdo.JDOObjectNotFoundException;
import javax.jdo.PersistenceManager;
import javax.jdo.Query;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.datanucleus.exceptions.NucleusObjectNotFoundException;
import org.springframework.orm.jdo.JdoCallback;
import org.springframework.orm.jdo.JdoObjectRetrievalFailureException;
import org.springframework.orm.jdo.JdoTemplate;
import org.springframework.orm.jdo.support.JdoDaoSupport;

import com.jcommerce.core.dao.DAO;
import com.jcommerce.core.model.Goods;
import com.jcommerce.core.model.GoodsType;
import com.jcommerce.core.model.ModelObject;
import com.jcommerce.core.service.Criteria;
import com.jcommerce.core.util.JDOQLHelper;
import com.jcommerce.core.util.MyPropertyUtil;
import com.jcommerce.core.util.UUIDHexGenerator;

public class DAOImpl extends JdoDaoSupport implements DAO {
    protected Log log = LogFactory.getLog(getClass());
    
    public DAOImpl() {
    	setPersistenceManagerFactory(PMF.get());
    }
    
    public String add(ModelObject to) {
    	
    	String id = null;
    	try {
    		// TODO leon temporary solution for case that id is blank in form
    		if(StringUtils.isEmpty(to.getId())) {
    			to.setId(null);
    		}

    		if(StringUtils.isEmpty(to.getId())) {
    			// if id is not set, use keyname, otherwise use ID directly, same as attach
    			to.setKeyName(UUIDHexGenerator.newUUID());
    		}
    		
			getJdoTemplate().makePersistent(to);
			id = to.getId();
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new RuntimeException(e);
		} finally {
		}
    	
    	return id;
    }
    public String attach (ModelObject to) {
    	String id = null;
    	try {
    		// TODO leon temporary solution for case that id is blank in form
    		if(StringUtils.isEmpty(to.getId())) {
    			to.setId(null);
    		}
			getJdoTemplate().makePersistent(to);
			id = to.getId();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new RuntimeException(e);
		} finally {
		}
    	return id;
    }
	public boolean update (ModelObject to) {
		try {
			JdoTemplate jdoTemplate =getJdoTemplate();
			String modelName = to.getClass().getName(); 
			ModelObject po = (ModelObject)jdoTemplate.getObjectById(to.getClass(), to.getId());
			if(po==null) {
				throw new RuntimeException("update: not existed. modelName="+modelName+", id="+to.getId());
			}
			MyPropertyUtil.copySimpleProperties(po, to);
//			jdoTemplate.makePersistent(to);
//			jdoTemplate.attachCopy(to);
//			jdoTemplate.at
    		return true;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new RuntimeException(e);
		} 
	}
	
    public boolean delete (String modelName, String id) {
    	try {
    		JdoTemplate jdoTemplate =getJdoTemplate();
    		
    		Object obj = (Object)jdoTemplate.getObjectById(Class.forName(modelName), id);
    					
//			Brand brand = (Brand)obj;
//			System.out.println("name: "+brand.getName());
//			System.out.println("SiteUrl: "+brand.getSiteUrl());
//			System.out.println("SortOrder: "+brand.getSortOrder());
//			System.out.println("Description: "+brand.getDescription());
			
    		if(obj!=null) {
    			jdoTemplate.deletePersistent(obj);
    		}
//			res = String.valueOf(obj.getId());
			return true;
    	} catch (JDOObjectNotFoundException e) {
    		return true;
    	} catch (NucleusObjectNotFoundException e) {
    		return true;
		} catch( JdoObjectRetrievalFailureException e) {
			return true;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new RuntimeException(e);
		}
    }
    
    public boolean delete (ModelObject obj) {
        if (obj == null) {
            throw new IllegalArgumentException("obj = null");
        }
    	try {
    		JdoTemplate jdoTemplate =getJdoTemplate();
    		jdoTemplate.deletePersistent(obj);
			
//			res = String.valueOf(obj.getId());
			return true;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new RuntimeException(e);
		} 
    }
    
	/**
	 * return null if not found
	 */
	public ModelObject get (String modelName, String id) {
		try {
			JdoTemplate jdoTemplate =getJdoTemplate();
			ModelObject obj = (ModelObject)jdoTemplate.getObjectById(Class.forName(modelName), id);
			
			// TODO these are temporary solution to avoid session-closed issue in TestCases
			if(obj instanceof GoodsType) {
				GoodsType gt = (GoodsType)obj;
				Set set = gt.getAttributes();
				System.out.println("size: "+set.size());
			}
			if(obj instanceof Goods) {
				Goods g = (Goods)obj;
				Set set = g.getCategoryIds();
				System.out.println("size: "+(set==null? "null":set.size()));
				
			}
			
    		return obj;
		} catch (org.springframework.orm.ObjectRetrievalFailureException e) {
			return null;
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
			
		} finally {
		}
	}
    public List getList(final String modelName, final Criteria criteria, final int firstRow, final int maxRow) {
        Query query = null;
        try {        	
        	JdoTemplate jdoTemplate =getJdoTemplate();
        	List res = (List)jdoTemplate.execute(new JdoCallback() {
                public Object doInJdo(PersistenceManager pm) throws JDOException {
                    Query query = null;
                    List result = null;
					try {
						List<Object> paras = new ArrayList<Object>();
						String jdoql = JDOQLHelper.getJdoql(modelName, criteria, paras);
						query = pm.newQuery(jdoql);

						if(firstRow>=0 && maxRow>=0) {
							query.setRange(firstRow, firstRow+maxRow);
						}
						
						result = (List)query.executeWithArray(paras.toArray());
						// 	do some further stuff with the result list
						return result;
					} catch (JDOException e) {
						throw e;
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
						throw new RuntimeException(e);
					}
                }
            });

        	Iterator it = res.iterator();
        	if(it.hasNext()) {
        		ModelObject mo = (ModelObject)it.next();
        		System.out.println("id: "+mo.getId());
        	}
//            System.out.println("res.size: "+res.size());
            return res;
        
        } catch (Exception ex) {
        	ex.printStackTrace();
        	throw new RuntimeException(ex);
        } finally {
        	if(query!=null) {
        		query.closeAll();
        	}
//        	pm.close();
        }
        
//        return getHibernateTemplate().executeFind(new HibernateCallback() {
//            public Object doInHibernate(Session s) throws HibernateException,
//                    SQLException {
//                Query query = s.createQuery(hsql);
//                query.setFirstResult(firstRow);
//                query.setMaxResults(maxRow);
//                List list = query.list();
//                return list;
//            }
//        });
    }   
    
    public int getCount(final String modelName, final Criteria criteria){  
//        Number count = (Number)getHibernateTemplate().find("select count(*) "+hql).listIterator().next();
//        return count.intValue();
        Query query = null;
        try {
        	JdoTemplate jdoTemplate =getJdoTemplate();
        	
        	Integer res = (Integer)jdoTemplate.execute(new JdoCallback() {
                public Object doInJdo(PersistenceManager pm) throws JDOException {
                    Query query;
					try {
						List<Object> paras = new ArrayList<Object>();
						String jdoql = JDOQLHelper.getJdoql(modelName, criteria, paras);
						query = pm.newQuery(jdoql);
						query.setResult(" count(this)");
						Integer result = (Integer)query.executeWithArray(paras.toArray());
	                    // do some further stuff with the result list
	                    return result;
	                    
					} catch (JDOException e) {
						throw e;
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
						throw new RuntimeException(e);
					}


                }
            });
        
            return res;
        
        } catch (Exception ex) {
        	ex.printStackTrace();
        	throw new RuntimeException(ex);
        } finally {
        	if(query!=null) {
        		query.closeAll();
        	}
        }
    }

    
    public void save(ModelObject obj) {
        if (obj == null) {
            throw new IllegalArgumentException("obj = null");
        }
        
//        if (getById(getId(obj)) == null) {
//            Serializable id = getHibernateTemplate().save(obj);
//            setId(obj, id);
//        } else {
//            getHibernateTemplate().update(obj);
//        }
//        ModelObject _obj = (ModelObject)getHibernateTemplate().merge(obj);
//        setId(obj, getId(_obj));
    }    
    
    public void deleteAll(Collection<ModelObject> objs) {
        if (objs == null) {
            throw new IllegalArgumentException("objs = null");
        }
        
//        getHibernateTemplate().deleteAll(objs);
    }    

    

}
