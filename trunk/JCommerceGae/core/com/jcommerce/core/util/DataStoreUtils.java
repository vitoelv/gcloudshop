package com.jcommerce.core.util;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

import javax.jdo.annotations.Persistent;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.BeanUtilsBean;
import org.apache.commons.beanutils.ConvertUtilsBean;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.beanutils.PropertyUtilsBean;
import org.apache.commons.beanutils.converters.DateConverter;
import org.apache.commons.lang.StringUtils;

import com.google.appengine.api.datastore.Blob;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.google.appengine.repackaged.com.google.common.base.StringUtil;
import com.jcommerce.core.annotation.IsBlobText;
import com.jcommerce.core.annotation.IsPK;
import com.jcommerce.core.model.AdminUser;
import com.jcommerce.core.model.AreaRegion;
import com.jcommerce.core.model.Article;
import com.jcommerce.core.model.ArticleCat;
import com.jcommerce.core.model.Attribute;
import com.jcommerce.core.model.Brand;
import com.jcommerce.core.model.Cart;
import com.jcommerce.core.model.Category;
import com.jcommerce.core.model.Comment;
import com.jcommerce.core.model.DSFile;
import com.jcommerce.core.model.Goods;
import com.jcommerce.core.model.GoodsAttr;
import com.jcommerce.core.model.GoodsGallery;
import com.jcommerce.core.model.GoodsType;
import com.jcommerce.core.model.LinkGood;
import com.jcommerce.core.model.MemberPrice;
import com.jcommerce.core.model.ModelObject;
import com.jcommerce.core.model.OrderAction;
import com.jcommerce.core.model.OrderGoods;
import com.jcommerce.core.model.OrderInfo;
import com.jcommerce.core.model.Payment;
import com.jcommerce.core.model.Region;
import com.jcommerce.core.model.Shipping;
import com.jcommerce.core.model.ShippingArea;
import com.jcommerce.core.model.ShopConfig;
import com.jcommerce.core.model.User;
import com.jcommerce.core.model.UserAddress;
import com.jcommerce.core.service.IDefaultManager;
import com.jcommerce.gwt.client.model.IModelObject;

public class DataStoreUtils implements IConstants{
	
	
	public static void importDSFromZip(InputStream in, IDefaultManager manager) {
    	try {
			ZipInputStream zin = new ZipInputStream(in);

			Map<String, String> fileNameIdMapping = new HashMap<String, String>();
			while (true) {
				ZipEntry entry = zin.getNextEntry();
				if (entry == null) {
					break;
				}
				String entryName = entry.getName();
				System.out.println("entryName: " + entryName + ", size: "
						+ entry.getSize());

				if (entry.isDirectory()) {
					continue;
				} else {
					// my data
				        // txt must go first
					if (entryName.endsWith(".txt")) {
						fileNameIdMapping = DataStoreUtils.importDSData(zin,manager);
					} else {
						// TODO is it portable on Linux/Unix?
						String fileName = StringUtils.substringAfterLast(entryName, "/");
						String pkId = fileNameIdMapping.get(fileName);
						if (pkId == null) {
							System.out.println("fileName: "+ fileName+ " didn't find match ds record, sth wrong.");
							continue;
						}

						DSFile dsFile = (DSFile) manager.get(DSFile.class
								.getName(), pkId);
						if (dsFile == null) {
							System.out.println("dsFile pkId: " + pkId+ " does not exist, sth wrong.");
							continue;
						}

						ByteArrayOutputStream out = new ByteArrayOutputStream();
						byte[] buffer = new byte[4012];
						while (true) {
							int bytesRead = zin.read(buffer);
							if (bytesRead == -1) {
								break;
							}
							out.write(buffer, 0, bytesRead);
						}
						out.flush();
						out.close();
						dsFile.setContent(new Blob(out.toByteArray()));
						manager.txattach(dsFile);

					}
				}

			}
		} catch (Exception ex) {
			ex.printStackTrace();
			throw new RuntimeException("error in import, cause: " + ex.getMessage());
		}
	}
	
    public static Map<String, String> importDSData(InputStream in, IDefaultManager manager) 
    	throws Exception{
    	
    	Map<String, String> fileNameIdMapping = new HashMap<String, String>();


    		BufferedReader reader = new BufferedReader(new InputStreamReader(in, ENCODING));
    		
			String className = null;
			String[] columns = null;
			String[] values = null;
			boolean inComment = false;
			
			String newRecord = null;
			String newline = null;
			ConvertUtilsBean convertUtils = new ConvertUtilsBean();
			DateConverter dateConverter = new DateConverter();
			dateConverter.setPattern("yyyy-MM-dd HH:mm:ss");
			convertUtils.register(dateConverter,Date.class);
			//因为要注册converter,所以不能再使用BeanUtils的静态方法了，必须创建BeanUtilsBean实例
			BeanUtilsBean beanUtils = new BeanUtilsBean(convertUtils,new PropertyUtilsBean());
			
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
    				
    				if(className.equals("com.jcommerce.core.model.Goods")){
    					System.out.println("in com.jcommerce.core.model.Goods");
    				}
    				
    				values = ConvertUtil.split(newRecord, SEP_COLUMNS_VALUES);
    				// won't work in case ,, occur in the sequence
//    				values = StringUtils.split(newline, SEP_COLUMNS_VALUES);
    				if(columns.length!=values.length) {
    					System.out.println("[WARNING!]: skipping line: "+newRecord+
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
        					Annotation isBlobText = f.getAnnotation(IsBlobText.class);
        					if(isPK!=null) {
        						// expect a pk value, need convert from keyName->pk
        						if(StringUtils.isEmpty(value)) {
        							BeanUtils.setProperty(obj, column, null);
        						}
        						else {
        							BeanUtils.setProperty(obj, column, getIdFromChainedKeyName(value));
        						}
        					}
        					else if(Blob.class.isAssignableFrom(f.getType())) {
        						if( isBlobText != null ){
        							BeanUtils.setProperty(obj, column, value);
        						} else {
        							
        							if(StringUtils.isNotEmpty(value)){
            							// keyName always go first, so pkId is generated already
            							fileNameIdMapping.put(value, obj.getPkId());
            						}
        						}
        						
        					}
        					else {
        						if(StringUtil.isEmpty(value)){
    								continue;
    							}
        						beanUtils.setProperty(obj, column, value);
        					}
    					}
    				}
    				
    				manager.txattach(obj);
    			}
    			
    			
    		}
    		
    		return fileNameIdMapping;
    		

    }
    
    public static void allExportDS2Zip (OutputStream out, IDefaultManager manager) {
    	String[] classes = new String[] { 
    			AdminUser.class.getName(), 
    			AreaRegion.class.getName(),
    			Article.class.getName(),
    			ArticleCat.class.getName(),
    			Attribute.class.getName(),
				Brand.class.getName(),
				Cart.class.getName(),
				Category.class.getName(),
				Comment.class.getName(),
				Goods.class.getName(), 
				GoodsAttr.class.getName(), 
				GoodsGallery.class.getName(),
				GoodsType.class.getName(),
				LinkGood.class.getName(),
				DSFile.class.getName(),
				MemberPrice.class.getName(),
				OrderAction.class.getName(),
				OrderGoods.class.getName(),
				OrderInfo.class.getName(),
				Payment.class.getName(), 
				Region.class.getName(),
				Shipping.class.getName(),
				ShippingArea.class.getName(), 
				ShopConfig.class.getName(),
				UserAddress.class.getName(),
//				UserRank.class.getName(),
				User.class.getName(),
				
				
				};    	
    	exportDS2Zip(out,manager,classes);
    }    
    
    public static void standerdExportDS2Zip (OutputStream out, IDefaultManager manager) {
    	String[] classes = new String[] { 
    			AdminUser.class.getName(), 
    			AreaRegion.class.getName(),
    			Article.class.getName(),
    			ArticleCat.class.getName(),
    			Attribute.class.getName(),
				Brand.class.getName(),
				Cart.class.getName(),
				Category.class.getName(),
				Comment.class.getName(),
				Goods.class.getName(), 
				GoodsAttr.class.getName(), 
				GoodsGallery.class.getName(),
				GoodsType.class.getName(),
				LinkGood.class.getName(),
				DSFile.class.getName(),
				MemberPrice.class.getName(),
				OrderAction.class.getName(),
				OrderGoods.class.getName(),
				OrderInfo.class.getName(),
				Payment.class.getName(), 
				Region.class.getName(),
				Shipping.class.getName(),
				ShippingArea.class.getName(), 
				ShopConfig.class.getName(),
				UserAddress.class.getName(),
//				UserRank.class.getName(),
				User.class.getName(),
				
				
				};    	
    	exportDS2Zip(out,manager,classes);
    }
    
    public static void minimumExportDS2Zip (OutputStream out, IDefaultManager manager) {
    	String[] classes = new String[] { 
    			Attribute.class.getName(),
				Brand.class.getName(),
				Cart.class.getName(),
				Category.class.getName(),
				Goods.class.getName(), 
				ArticleCat.class.getName(),
				Article.class.getName(),
				GoodsAttr.class.getName(), 
				GoodsGallery.class.getName(),
				GoodsType.class.getName(),
				LinkGood.class.getName(),
				DSFile.class.getName(),
				MemberPrice.class.getName(),
				OrderAction.class.getName(),
				OrderGoods.class.getName(),
				OrderInfo.class.getName(),
				ShopConfig.class.getName(),
				UserAddress.class.getName(),
//				UserRank.class.getName(),
				User.class.getName(),
				};    	
    	exportDS2Zip(out,manager,classes);
    }
    
    
    public static void exportDS2Zip (OutputStream out, IDefaultManager manager ,String[] classes) {
    	
    	try {
			ZipOutputStream zout = new ZipOutputStream(out);

			zout.putNextEntry(new ZipEntry("mydata.txt"));
			
			Map<String, String> fileIdNameMapping = new HashMap<String, String>();
			for (String cls : classes) {
				exportDSData(zout, cls, manager, fileIdNameMapping);
			}
			zout.closeEntry();
			
			
			for(String pkId : fileIdNameMapping.keySet()) {
				DSFile dsFile = (DSFile)manager.get(DSFile.class.getName(), pkId);
				if(dsFile==null) {
					System.out.println("dsFile pkId: "+pkId + " does not exist. sth wrong");
				}
				String fileName = fileIdNameMapping.get(pkId);
				zout.putNextEntry(new ZipEntry("files/"+fileName));
				zout.write(dsFile.getContent().getBytes());
				zout.closeEntry();
			}
			zout.flush();
			zout.close();
		} catch (Exception ex) {
			ex.printStackTrace();
			throw new RuntimeException("error in export, cause: " + ex.getMessage());
		}
    }
    
    
    public static void exportDSData(OutputStream out, String className, 
    		IDefaultManager manager, Map<String, String> fileIdNameMapping) throws Exception{

    	

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
	            	// if value is null, export as an empty string
	            }
	            else {
					Field field = cls.getDeclaredField(fn);
					Class ft = field.getType();
					 System.out.println("fn: "+fn+", ft: "+ft.getName()+", value: "+value);

					IsPK isPK = field.getAnnotation(IsPK.class);
					if (isPK != null) {
						// convert PK->keyName
					    if(StringUtils.isBlank((String)value)) {
					        // this is an optional relationship ID
					        strValue = "";
					    } else {
						ModelObject target = manager.get(isPK.myclazz(),
								(String) value);
						if(target!=null) {
							StringBuffer buf1 = new StringBuffer();
							Key key = KeyFactory.stringToKey(target.getPkId());
							getChainedKeyName(buf1, key, manager);
							strValue = buf1.toString();
						}else {
							System.out.println("failed to find a target of IsPk(class="+isPK.myclazz()+") field");
						}
					    }
					} else if (Collection.class.isAssignableFrom(ft)) {
						strValue = Arrays.toString(((Collection) value)
								.toArray());
					} else if(Blob.class.isAssignableFrom(ft) && obj instanceof DSFile) {
						// store the blob content to a file then store the file path
						DSFile file = (DSFile)obj;
						String fileName = file.getFileName();
						String ext = StringUtils.substringAfterLast(fileName, ".");
						String tempName = UUIDLongGenerator.newUUID().toString()+"."+ext;
						strValue = tempName;
						
						fileIdNameMapping.put(file.getPkId(), tempName);
						
					} else if (Date.class.isAssignableFrom(ft)) {
						SimpleDateFormat formatter = new SimpleDateFormat ("yyyy-MM-dd HH:mm:ss");
						strValue = formatter.format(value);
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

		
    }
    
    public static final String SEP_NEWLINE = "\r\n";
    public static final String SEP_CLASSNAME = "::";
    public static final String SEP_COLUMNS_VALUES = ",";
    public static final String SEP_CLS_KEYNAME = ":";
    public static final String SEP_PARENT_CHILD = "|";
    public static final String SEP_START_OF_RECORD = "SOR>";
    public static final String SEP_END_OF_RECORD = "<EOR";
//    public static final String SEP_FILE = "TEMPFILEPATH==";
    
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
    

    public static String genKeyName(ModelObject obj) {
    	// instead of using UUID, we try to make the keyname shorter while unique
    	
    	// hashcode append with last 4 number of nanotime
    	String keyName = "_"+String.valueOf(obj.hashCode())+String.valueOf(System.nanoTime()%10000);
    	
    	return keyName;
    	
    	
    }
    
}
