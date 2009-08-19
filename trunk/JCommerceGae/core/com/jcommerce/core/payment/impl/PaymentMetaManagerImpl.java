package com.jcommerce.core.payment.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;

import com.jcommerce.core.dao.OrderDAO;
import com.jcommerce.core.dao.PaymentDAO;
import com.jcommerce.core.model.Order;
import com.jcommerce.core.model.Payment;
import com.jcommerce.core.payment.IPaymentMetaManager;
import com.jcommerce.core.payment.IPaymentMetaPlugin;
import com.jcommerce.core.payment.PaymentConfigMeta;

public class PaymentMetaManagerImpl implements IPaymentMetaManager{
    

    
    
    private OrderDAO orderDAO;
    private PaymentDAO paymentDAO;
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
        metaRepo.put(alipay.getCode(), alipay);
        ChinaBank netbank = new ChinaBank();
        metaRepo.put(netbank.getCode(), netbank);
    }
    
    
    public void savePaymentConfig(Map<String, Object> props) {
        try {
            Payment payment = new Payment();
            payment.setCod(Boolean.valueOf((String)props.get(PaymentConfigMeta.ISCOD)));
            payment.setCode((String)props.get(PaymentConfigMeta.CODE));
            payment.setDescription((String)props.get(PaymentConfigMeta.DESC));
//        payment.setEnabled((String)props.get(DESC));
            payment.setFee((String)props.get(PaymentConfigMeta.PAYFEE));
            payment.setId(Integer.valueOf((String)props.get(PaymentConfigMeta.ID)));
            payment.setName((String)props.get(PaymentConfigMeta.NAME));
            payment.setOnline(Boolean.valueOf((String)props.get(PaymentConfigMeta.ISONLINE)));
            payment.setOrder(Integer.valueOf((String)props.get(PaymentConfigMeta.ORDER)));
            
            IPaymentMetaPlugin meta = metaRepo.get(payment.getCode());
            
            payment.setConfig(meta.getSerializedConfig(props));
            paymentDAO.savePayment(payment);
        } catch (RuntimeException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            throw e;
        }
        
    }

    public List<Map<String, Object>> getCombinedPaymentMetaList() {
        // 已加载的
        
        // 已安装到数据库中的
        List<Payment> listData = paymentDAO.getPaymentList();
        Map<String, Payment> mapData = new HashMap<String, Payment>();
        for(Payment payment:listData) {
            mapData.put(payment.getCode(), payment);
        }
        
        List<Map<String, Object>> res = new ArrayList<Map<String, Object>>();
        Map<String, Object> maps = null;
        
        for(String code:metaRepo.keySet()) {
            IPaymentMetaPlugin meta = metaRepo.get(code);
            maps = new HashMap<String, Object>();
            
            if(mapData.containsKey(code)) {
                // 已在数据库中，使用数据库中的值
                Payment payment = mapData.get(code);
                maps.put(PaymentConfigMeta.ID, payment.getPkId());
                maps.put(PaymentConfigMeta.NAME, payment.getName());
                maps.put(PaymentConfigMeta.CODE, payment.getCode());
                maps.put(PaymentConfigMeta.PAYFEE, payment.getFee());
                maps.put(PaymentConfigMeta.ISCOD, payment.isCod());
                maps.put(PaymentConfigMeta.DESC, payment.getDescription());
                maps.put(PaymentConfigMeta.ORDER, payment.getOrder());
                maps.put(PaymentConfigMeta.INSTALL, true);
            }
            else {
                maps.put(PaymentConfigMeta.NAME, meta.getName());
                maps.put(PaymentConfigMeta.CODE, meta.getCode());
                maps.put(PaymentConfigMeta.PAYFEE, meta.getPayFee());
                maps.put(PaymentConfigMeta.DESC, meta.getDescription());
                maps.put(PaymentConfigMeta.INSTALL, false);
            }
            
            res.add(maps);
            
        }
        return res;
    }
    
    public List<Payment> getPaymentList() {
        return paymentDAO.getPaymentList();
    }
    public String getCode(int orderId, int paymentId) {
        System.out.println("getCode: orderId="+orderId+", paymentId="+paymentId);
        Order order = orderDAO.getOrder(orderId);
        Payment payment = paymentDAO.getPayment(paymentId);
        
        String code = payment.getCode();
        IPaymentMetaPlugin meta = metaRepo.get(code);
        
        return meta.getCode(order, payment);
    }
    
    public void install(String paymentCode) {
        try {
            
            IPaymentMetaPlugin meta = metaRepo.get(paymentCode);
            Payment obj = new Payment();
            obj.setCod(meta.isCod());
            obj.setCode(meta.getCode());
            obj.setConfig(meta.getDefaultConfig());
            
            obj.setDescription(meta.getDescription());
            obj.setEnabled(false);
            obj.setFee(meta.getPayFee());
            obj.setName(meta.getName());
            obj.setOnline(meta.isOnline());
            obj.setOrder(0);

            paymentDAO.savePayment(obj);
        } catch (RuntimeException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            System.out.println("install error:"+e.getMessage());
            throw e;
        }
    }
    
    
    public void uninstall(int paymentId) {
        try {
            paymentDAO.removePayment(paymentId);
        } catch (RuntimeException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            throw e;
        }
    }
    public PaymentConfigMeta getPaymentConfigMeta(int paymentId) {
        try {
            PaymentConfigMeta res = null;
            Payment payment = paymentDAO.getPayment(paymentId);
            String code = payment.getCode();
            IPaymentMetaPlugin meta = metaRepo.get(code);
            res = meta.getPaymentConfigMeta(payment.getConfig());
            
            // copy common fields
            BeanUtils.copyProperties(res, payment);
            
            return res; 
            
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
        
    }
    

    


    public void setOrderDAO(OrderDAO orderDao) {
        this.orderDAO = orderDao;
    }
    public void setPaymentDAO(PaymentDAO paymentDao) {
        this.paymentDAO = paymentDao;
    }
    
}
