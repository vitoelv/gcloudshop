package com.jcommerce.core.util;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import javax.jdo.annotations.Persistent;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang.StringUtils;

import com.google.appengine.api.datastore.Blob;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.jcommerce.core.annotation.IsPK;
import com.jcommerce.core.model.Attribute;
import com.jcommerce.core.model.Brand;
import com.jcommerce.core.model.Category;
import com.jcommerce.core.model.GoodsType;
import com.jcommerce.core.model.ModelObject;
import com.jcommerce.core.service.IDefaultManager;
import com.jcommerce.gwt.client.model.IModelObject;

public class DataStoreUtils implements IConstants{
	
	
//    public static void importDS(InputStream in, IDefaultManager manager) {
//
//    	try {
//    		BufferedReader reader = new BufferedReader(new InputStreamReader(in, ENCODING));
//    		
//			String className = null;
//			String[] columns = null;
//			String[] values = null;
//    		while(true) {
//    			String newline = reader.readLine();
//    			System.out.println("newline: "+newline);
//    			if(newline==null) {
//    				break;
//    			}
//    			newline = newline.trim();
//    			if(StringUtils.isBlank(newline)) {
//    				continue;
//    			}
//    			if(newline.startsWith("::")) {
//    				className = newline.substring(2);
//    				newline = reader.readLine().trim();
//    				columns = StringUtils.split(newline, ",");
//    				continue;
//    			}
//    			
//    			if(className!=null) {
//    				ModelObject obj = (ModelObject)Class.forName(className).newInstance();
//    				
//    				values = ConvertUtil.split(newline, ",");
//    				// won't work in case ,, occur in the sequence
////    				values = StringUtils.split(newline, ",");
//    				if(columns.length!=values.length) {
//    					System.out.println("skipping line: "+newline+
//    							", value length: "+values.length+
//    							", column length: "+columns.length);
//    					continue;
//    				}
//    				for(int i=0;i<columns.length;i++) {
//    					String column = columns[i];
//    					String value = values[i];
//    					BeanUtils.setProperty(obj, column, value);
//    				}
//    				
//    				manager.attach(obj);
//    			}
//    			
//    			
//    		}
//    		
//    		
//    		
//    	} catch (Exception ex) {
//    		ex.printStackTrace();
//    		throw new RuntimeException();
//    	}
//    }
//
//    public static void exportDS(OutputStream out, String className, IDefaultManager manager) {
//
//    	try {
//    	Class cls = Class.forName(className);
//    	ModelObject temp = (ModelObject)cls.newInstance();
//    	List<Field> exportableFields = new ArrayList<Field>();
//    	Field[] fields = cls.getDeclaredFields();
//    	StringBuffer buf = new StringBuffer();
//    	buf.append("::").append(className).append("\r\n");
//    	
//    	// ensure id is the first field
//    	exportableFields.add(cls.getDeclaredField("id"));
//    	buf.append("id");
//    	for(Field field:fields) {
//    		
//            String fn = field.getName();
//            Class ft = field.getType();
//            if (Modifier.isStatic(field.getModifiers())) {
//                continue;
//            }
//            if(!PropertyUtils.isReadable(temp, fn)) {
//            	continue;
//            }
//            if("class".equals(fn)) {
//            	continue;
//            }
//            if("id".equals(fn)) {
//            	continue;
//            }
//            if("keyName".equals(fn)) {
//            	continue;
//            }
//            if(ModelObject.class.isAssignableFrom(ft)) {
//            	// ingore owned relationship
//            	continue;
//            }
//            Persistent p = field.getAnnotation(Persistent.class);
//            if(p==null) {
//            	// ingore non-persistable fields
//            	continue;
//            }
//            
//            if(Collection.class.isAssignableFrom(ft)) {
//            	String type = field.getGenericType().toString();
//            	String paraType = type.substring(type.indexOf('<')+1, type.indexOf('>'));
//            	System.out.println("paraType="+paraType);
//            	if(ModelObject.class.isAssignableFrom(Class.forName(paraType))) {
//                	// ingore Collection of ModelObject
//            		continue;
//            	}
//            	else {
//            		// TODO leon
//            		// allow collection of predefined class 
//            		continue;
//            	}
//            }
//            exportableFields.add(field);
//            buf.append(",").append(fn);
//    	}
//    	buf.append("\r\n");
//    	out.write(buf.toString().getBytes(ENCODING));
//    	
//		List res = new ArrayList();
//		manager.getList(res, className, null, -1, -1);
//		for(Iterator it = res.iterator();it.hasNext();) {
//			ModelObject obj = (ModelObject)it.next();
//			buf = new StringBuffer();
//			int i=0;
//			for(Field field:exportableFields) {
//				if(i!=0) {
//					buf.append(",");
//				}
//				i++;
//				String strValue = "";
//	            String fn = field.getName();
//	            Class ft = field.getType();
////	            System.out.println("fn: "+fn+", ft: "+ft.getName());
//	            Object value = PropertyUtils.getProperty(obj, fn);
//	            if(value==null) {
//	            	
//	            }
//	            else if(Collection.class.isAssignableFrom(ft)) {
//	            	strValue = Arrays.toString(((Collection)value).toArray());
//	            }
//	            else {
//	            	strValue = value.toString();
//	            }
//				buf.append(strValue);
////				buf.append(",");
//			}
//			buf.append("\r\n");
//			out.write(buf.toString().getBytes(ENCODING));
////			GoodsType gt = (GoodsType)it.next();
////			buf.append(gt.getClass().getName()).append(",");
////			buf.append(gt.getKeyName()).append(",");
////			buf.append(gt.getName()).append(",");
////			buf.append(gt.getAttributeGroup()).append("\r\n");
//		}
//		
//		out.write("\r\n".getBytes(ENCODING));
//    	} catch (Exception ex) {
//    		ex.printStackTrace();
//    		throw new RuntimeException();
//    	}
//		
//    }
    
    
    public static void importDS2(InputStream in, IDefaultManager manager) {

    	try {
    		BufferedReader reader = new BufferedReader(new InputStreamReader(in, ENCODING));
    		
			String className = null;
			String[] columns = null;
			String[] values = null;
			boolean inComment = false;
			
			String newRecord = null;
			String newline = null;
    		while(true) {
    			newline = reader.readLine();
    			System.out.println("newline: "+newline);
    			if(newline==null) {
    				break;
    			}
    			
    			newline = newline.trim();
    			if(StringUtils.isBlank(newline)) {
    				continue;
    			}
    			if(newline.startsWith(SEP_COMMENT)) {
    				continue;
    			}
    			if(inComment) {
    				if(SEP_END_COMMENT.equals(newline)) {
    					inComment = false;
    				}
					continue;
    			} else {
    				if(SEP_START_COMMENT.equals(newline)) {
    					inComment = true;
    					continue;
    				}
    			}
    			if(newline.startsWith(SEP_CLASSNAME)) {
    				className = newline.substring(2);
    				// columnnames line must be right after classname line
    				newline = reader.readLine().trim();
    				columns = StringUtils.split(newline, SEP_COLUMNS_VALUES);
    				continue;
    			}
    			
    			
    			// TODO using XML
//    			// start of records
    			if(newline.startsWith(SEP_START_OF_RECORD)) {
    				if (newline.endsWith(SEP_END_OF_RECORD)) {
    					newRecord = StringUtils.substringBetween(newline, SEP_START_OF_RECORD, SEP_END_OF_RECORD);
					} else {
	    				// reserve the NEWLINE
	    				StringBuffer buf = new StringBuffer(StringUtils.substringAfter(newline, SEP_START_OF_RECORD)).append(SEP_NEWLINE);
						// read until hit end of record
						while (true) {
							String s = reader.readLine();
							if(s==null) {
								throw new RuntimeException("without EOR");
							}
							if(s.endsWith(SEP_END_OF_RECORD)) {
								buf.append(StringUtils.substringBeforeLast(s, SEP_END_OF_RECORD));
								newRecord = buf.toString();
								break;
							}
							else {
								buf.append(s).append(SEP_NEWLINE);
							}
						}
						
					}
				}    			
    			
    			
    			if(className!=null) {
    				Class clazz = Class.forName(className);
    				ModelObject obj = (ModelObject)clazz.newInstance();
    				
    				values = ConvertUtil.split(newRecord, SEP_COLUMNS_VALUES);
    				// won't work in case ,, occur in the sequence
//    				values = StringUtils.split(newline, SEP_COLUMNS_VALUES);
    				if(columns.length!=values.length) {
    					System.out.println("skipping line: "+newRecord+
    							", value length: "+values.length+
    							", column length: "+columns.length);
    					continue;
    				}
    				for(int i=0;i<columns.length;i++) {
    					String column = columns[i];
    					String value = values[i];
    					if("keyName".equals(column)) {
    						obj.setPkId(getIdFromChainedKeyName(value));
    					}
    					else {
        					Field f = clazz.getDeclaredField(column);
        					Annotation isPK = f.getAnnotation(IsPK.class);
        					if(isPK!=null) {
        						// expect a pk value, need convert from keyName->pk
        						if(StringUtils.isEmpty(value)) {
        							BeanUtils.setProperty(obj, column, null);
        						}
        						else {
        							BeanUtils.setProperty(obj, column, getIdFromChainedKeyName(value));
        						}
        					}
        					else {
        						BeanUtils.setProperty(obj, column, value);
        					}
    					}
    				}
    				
    				manager.txattach(obj);
    			}
    			
    			
    		}
    		
    		
    		
    	} catch (Exception ex) {
    		ex.printStackTrace();
    		throw new RuntimeException();
    	}
    }
    public static void exportDS2(OutputStream out, IDefaultManager manager) {
    	String[] classes = new String[] {
    			GoodsType.class.getName()
    			, Attribute.class.getName()
    			, Brand.class.getName()
    			, Category.class.getName()

    	};
    	
    	for(String cls:classes) {
    		exportDS2(out, cls, manager);
    	}
    	
    }
    public static void exportDS2(OutputStream out, String className, IDefaultManager manager) {

    	try {
    	Class cls = Class.forName(className);
    	ModelObject temp = (ModelObject)cls.newInstance();
    	List<String> exportableFields = new ArrayList<String>();
    	Field[] fields = cls.getDeclaredFields();
    	StringBuffer buf = new StringBuffer();
    	buf.append(SEP_CLASSNAME).append(className).append(SEP_NEWLINE);
    	
    	// ensure id is the first field
    	exportableFields.add("keyName");
    	buf.append("keyName");
    	for(Field field:fields) {
    		
            String fn = field.getName();
            Class ft = field.getType();
            if (Modifier.isStatic(field.getModifiers())) {
                continue;
            }
            if(!PropertyUtils.isReadable(temp, fn)) {
            	continue;
            }
            if("class".equals(fn)) {
            	continue;
            }
            if(IModelObject.PK_ID.equals(fn) 
            		|| IModelObject.KEY_NAME.equals(fn) 
            		|| IModelObject.LONG_ID.equals(fn)) {
            	continue;
            }
            if(ModelObject.class.isAssignableFrom(ft)) {
//            	ModelObject obj = 
            	// ingore owned relationship
            	continue;
            }
            Persistent p = field.getAnnotation(Persistent.class);
            if(p==null) {
            	// ingore non-persistable fields
            	continue;
            }
            
            if(Collection.class.isAssignableFrom(ft)) {
            	if(MyPropertyUtil.isFieldCollectionOfModel(field)) {
                	// ingore Collection of ModelObject
            		continue;
            	}
            	else {
            		// TODO leon
            		// allow collection of predefined class 
            		continue;
            	}
            }
            exportableFields.add(fn);
            buf.append(SEP_COLUMNS_VALUES).append(fn);
    	}
    	buf.append(SEP_NEWLINE);
    	out.write(buf.toString().getBytes(ENCODING));
    	
    	
		List res = new ArrayList();
		manager.getList(res, className, null, -1, -1);
		for(Iterator it = res.iterator();it.hasNext();) {
			ModelObject obj = (ModelObject)it.next();
			buf = new StringBuffer();
			buf.append(SEP_START_OF_RECORD);
			int i=0;
			for(String fn:exportableFields) {
				if(i!=0) {
					buf.append(SEP_COLUMNS_VALUES);
				}
				i++;
				String strValue = "";
	            Object value = PropertyUtils.getProperty(obj, fn);
	            if("keyName".equals(fn)) {
	            	StringBuffer buf1 = new StringBuffer();
	            	Key key = KeyFactory.stringToKey(obj.getPkId());
	            	getChainedKeyName(buf1, key, manager);
	            	strValue = buf1.toString();
	            }
	            else if(value==null) {
	            	
	            }
	            else {
					Field field = cls.getDeclaredField(fn);
					Class ft = field.getType();
					 System.out.println("fn: "+fn+", ft: "+ft.getName());

					IsPK isPK = field.getAnnotation(IsPK.class);
					if (isPK != null) {
						// convert PK->keyName
						ModelObject target = manager.get(isPK.myclazz(),
								(String) value);
						StringBuffer buf1 = new StringBuffer();
						Key key = KeyFactory.stringToKey(obj.getPkId());
						getChainedKeyName(buf1, key, manager);
						strValue = buf1.toString();
					} else if (Collection.class.isAssignableFrom(ft)) {
						strValue = Arrays.toString(((Collection) value)
								.toArray());
					} else if(Blob.class.isAssignableFrom(ft)) {
						// TODO store the blob content to a file then store the file path
						Blob blob = (Blob)value;
						// file operation is not allowed in GAE project, moved to separate project for local testing
						
						
						
					} else {
						strValue = value.toString();
					}
				}
				buf.append(strValue);
//				buf.append(SEP_COLUMNS_VALUES);
			}
			buf.append(SEP_END_OF_RECORD).append(SEP_NEWLINE);
			out.write(buf.toString().getBytes(ENCODING));

		}
		
		out.write(SEP_NEWLINE.getBytes(ENCODING));
    	} catch (Exception ex) {
    		ex.printStackTrace();
    		throw new RuntimeException();
    	}
		
    }
    
    public static final String SEP_NEWLINE = "\r\n";
    public static final String SEP_CLASSNAME = "::";
    public static final String SEP_COLUMNS_VALUES = ",";
    public static final String SEP_CLS_KEYNAME = ":";
    public static final String SEP_PARENT_CHILD = "|";
    public static final String SEP_START_OF_RECORD = "SOR>";
    public static final String SEP_END_OF_RECORD = "<EOR";
    
    public static final String SEP_START_COMMENT = "<!--";
    public static final String SEP_END_COMMENT = "-->";
    public static final String SEP_COMMENT = "#";
    
    public static void getChainedKeyName(StringBuffer buf, Key key, IDefaultManager manager) {
    	Key pKey = key.getParent();
    	if(pKey != null) {
    		getChainedKeyName(buf, pKey, manager);
//    		if(parent instanceof ModelObject) {
//    			getChainedKeyName(buf, (ModelObject)parent, manager);
//    		} else if(parent instanceof String[]) {
//    			String[] parentInfo = (String[])parent;
//    			if(parentInfo.length!=2) {
//    				throw new RuntimeException("getParent() do not return valid result");
//    			}
//    			ModelObject mo = manager.get(parentInfo[0], parentInfo[1]);
//    			getChainedKeyName(buf, mo, manager);
//    		} else {
//    			throw new RuntimeException("getParent() do not return valid result");
//    		}
    		buf.append(SEP_PARENT_CHILD);
    	} 
    	buf.append(key.getKind()).append(SEP_CLS_KEYNAME).append(key.getName());

    	return;
    }
    public static String getIdFromChainedKeyName(String chained) {
    	String[] nodes = ConvertUtil.split(chained, SEP_PARENT_CHILD);
    	KeyFactory.Builder builder = null;
    	for(String node:nodes) {
    		String[] ss = ConvertUtil.split(node, SEP_CLS_KEYNAME);
    		if(builder == null) {
    			builder = new KeyFactory.Builder(ss[0],ss[1]);
    		}
    		else {
    			builder = builder.addChild(ss[0], ss[1]);
    		}
    	}
    	
    	return KeyFactory.keyToString(builder.getKey());
    }
    

    
    
}
