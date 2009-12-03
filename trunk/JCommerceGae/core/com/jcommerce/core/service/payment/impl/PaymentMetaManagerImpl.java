package com.jcommerce.core.service.payment.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;

import com.jcommerce.core.dao.DAO;
import com.jcommerce.core.model.OrderInfo;
import com.jcommerce.core.model.Payment;
import com.jcommerce.core.service.payment.IPaymentMetaManager;
import com.jcommerce.core.service.payment.IPaymentMetaPlugin;
import com.jcommerce.core.service.payment.PaymentConfigMeta;
import com.jcommerce.gwt.client.ModelNames;
import com.jcommerce.gwt.client.model.IPayment;
import com.jcommerce.gwt.client.model.IPaymentConfigMeta;

public class PaymentMetaManagerImpl implements IPaymentMetaManager{
    
    private DAO dao;
    private String pluginFolder;
    
    public String getPluginFolder() {
        return pluginFolder;
    }


    public void setPluginFolder(String pluginFolder) {
        this.pluginFolder = pluginFolder;
    }


    public PaymentMetaManagerImpl() {
        super();
        init();
    }
    
    Map<String, IPaymentMetaPlugin> metaRepo;
    public void init() {
        // TODO read pluginFolder and load modules:
//        File folder = new File(pluginFolder);
//        File[] pluginInfos = folder.listFiles();
//        for(File pluginInfo:pluginInfos) {
//            
//        }
        
        metaRepo = new HashMap<String, IPaymentMetaPlugin>();
        AliPay alipay = new AliPay();
        metaRepo.put(alipay.getDefaultConfigMeta().getPayCode(), alipay);
        ChinaBank netbank = new ChinaBank();
        metaRepo.put(netbank.getDefaultConfigMeta().getPayCode(), netbank);
    }
    
    
    public void savePaymentConfig(Map<String, Object> props) {
        try {
            Payment payment = new Payment();
            payment.setIsCod(Boolean.valueOf((String)props.get(IPayment.IS_COD)));
            payment.setPayCode((String)props.get(IPayment.PAY_CODE));
            payment.setPayDesc((String)props.get(IPayment.PAY_DESC));
//        payment.setEnabled((String)props.get(DESC));
            payment.setPayFee((String)props.get(IPayment.PAY_FEE));
            payment.setPkId((String)props.get(IPayment.PK_ID));
            payment.setPayName((String)props.get(IPayment.PAY_NAME));
            payment.setIsOnline(Boolean.valueOf((String)props.get(IPayment.IS_ONLINE)));
            payment.setPayOrder(Long.valueOf((String)props.get(IPayment.PAY_ORDER)));
            
            IPaymentMetaPlugin meta = metaRepo.get(payment.getPayCode());
            
            payment.setPayConfig(meta.serializeConfig(props));
            dao.update(payment);
        } catch (RuntimeException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            throw e;
        }
        
    }

    public List<Map<String, Object>> getCombinedPaymentMetaList() {
        // 已加载的
        
        // 已安装到数据库中的
        List<Payment> listData = dao.getList(ModelNames.PAYMENT, null);
        Map<String, Payment> mapData = new HashMap<String, Payment>();
        for(Payment payment:listData) {
            mapData.put(payment.getPayCode(), payment);
        }
        
        List<Map<String, Object>> res = new ArrayList<Map<String, Object>>();
        Map<String, Object> maps = null;
        
        for(String code:metaRepo.keySet()) {
            IPaymentMetaPlugin plugin = metaRepo.get(code);
            maps = new HashMap<String, Object>();
            
            if(mapData.containsKey(code)) {
                // 已在数据库中，使用数据库中的值
                Payment payment = mapData.get(code);
                maps.put(IPayment.PK_ID, payment.getPkId());
                maps.put(IPayment.PAY_NAME, payment.getPayName());
                maps.put(IPayment.PAY_CODE, payment.getPayCode());
                maps.put(IPayment.PAY_FEE, payment.getPayFee());
                maps.put(IPayment.IS_COD, payment.getIsCod());
                maps.put(IPayment.PAY_DESC, payment.getPayDesc());
                maps.put(IPayment.PAY_ORDER, payment.getPayOrder());
                maps.put(IPaymentConfigMeta.INSTALL, true);
            }
            else {
                maps.put(IPayment.PAY_NAME, plugin.getDefaultConfigMeta().getPayName());
                maps.put(IPayment.PAY_CODE, plugin.getDefaultConfigMeta().getPayCode());
                maps.put(IPayment.PAY_FEE, plugin.getDefaultConfigMeta().getPayFee());
                maps.put(IPayment.PAY_DESC, plugin.getDefaultConfigMeta().getPayDesc());
                maps.put(IPaymentConfigMeta.INSTALL, false);
            }
            
            res.add(maps);
            
        }
        return res;
    }
    
    public List<Payment> getPaymentList() {
        return (List<Payment>)dao.getList(ModelNames.PAYMENT, null);
    }
    public String getCode(String orderId, String paymentId) {
        System.out.println("getCode: orderId="+orderId+", paymentId="+paymentId);
        OrderInfo order = (OrderInfo)dao.get(ModelNames.ORDERINFO, orderId);
        Payment payment = (Payment)dao.get(ModelNames.PAYMENT, paymentId);
        
        String code = payment.getPayCode();
        IPaymentMetaPlugin meta = metaRepo.get(code);
        
        return meta.getCode(order, payment);
    }
    
    public void install(String paymentCode) {
        try {
            
            IPaymentMetaPlugin plugin = metaRepo.get(paymentCode);
            Payment obj = new Payment();
            obj.setIsCod(plugin.getDefaultConfigMeta().getIsCod());
            obj.setPayCode(plugin.getDefaultConfigMeta().getPayCode());
            obj.setPayConfig(plugin.getDefaultConfigMeta().getPayConfig());
            
            obj.setPayDesc(plugin.getDefaultConfigMeta().getPayDesc());
            obj.setEnabled(true);
            obj.setPayFee(plugin.getDefaultConfigMeta().getPayFee());
            obj.setPayName(plugin.getDefaultConfigMeta().getPayName());
            obj.setIsOnline(plugin.getDefaultConfigMeta().getIsOnline());
            obj.setPayOrder(0l);

            dao.add(obj);
        } catch (RuntimeException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            System.out.println("install error:"+e.getMessage());
            throw e;
        }
    }
    
    
    public void uninstall(String paymentId) {
        try {
        	dao.delete(ModelNames.PAYMENT, paymentId);
        } catch (RuntimeException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            throw e;
        }
    }
    
    public List<PaymentConfigMeta> getInstalledPaymentMetaList() {
		try {
			List<PaymentConfigMeta> res = new ArrayList<PaymentConfigMeta>();
			List<Payment> payments = getPaymentList();
			for (Payment payment : payments) {
				String code = payment.getPayCode();
				IPaymentMetaPlugin plugin = metaRepo.get(code);
				PaymentConfigMeta meta = plugin.deserializeConfig(payment.getPayConfig());
				// copy common fields
				BeanUtils.copyProperties(meta, payment);
				res.add(meta);
			}
			
			return res;
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}
    
    public PaymentConfigMeta getPaymentConfigMeta(String paymentId) {
        try {
            PaymentConfigMeta res = null;
            Payment payment = (Payment)dao.get(ModelNames.PAYMENT, paymentId);
            String code = payment.getPayCode();
            IPaymentMetaPlugin plugin = metaRepo.get(code);
            res = plugin.deserializeConfig(payment.getPayConfig());
            
            // copy common fields
            BeanUtils.copyProperties(res, payment);
            
            return res; 
            
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }
    
    


	public DAO getDao() {
		return dao;
	}


	public void setDao(DAO dao) {
		this.dao = dao;
	}
    

    



    
}
