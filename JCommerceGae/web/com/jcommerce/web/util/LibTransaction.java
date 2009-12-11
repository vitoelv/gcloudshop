package com.jcommerce.web.util;

import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.jcommerce.core.model.OrderInfo;
import com.jcommerce.core.model.User;
import com.jcommerce.core.model.UserAddress;
import com.jcommerce.core.service.Condition;
import com.jcommerce.core.service.Criteria;
import com.jcommerce.core.service.IDefaultManager;
import com.jcommerce.gwt.client.ModelNames;
import com.jcommerce.gwt.client.model.IOrderInfo;
import com.jcommerce.gwt.client.model.IUserAddress;
import com.jcommerce.web.to.OrderInfoWrapper;
import com.jcommerce.web.to.UserAddressWrapper;
import com.jcommerce.web.to.UserWrapper;
import com.jcommerce.web.to.WrapperUtil;

public class LibTransaction {
	
	public static UserWrapper getProfile(String userId, IDefaultManager manager) {
		User user = (User)manager.get(ModelNames.USER, userId);
		UserWrapper uw = new UserWrapper(user);
		
		return uw;
	}
	
	public static List<OrderInfoWrapper> getUserOrders(String userId, int size, int start, IDefaultManager manager) {
		
		Criteria c = new Criteria();
		Condition cond = new Condition(IOrderInfo.USER_ID, Condition.EQUALS, userId);
		c.addCondition(cond);
		
		List<OrderInfo> list = (List)manager.getList(ModelNames.ORDERINFO, c, start, size);
		
		
		return WrapperUtil.wrap(list, OrderInfoWrapper.class);
	}
	
	
	public static List<UserAddressWrapper> getConsigneeList(String userId, IDefaultManager manager) {
		Criteria c = new Criteria();
		Condition cond = new Condition(IUserAddress.USER_ID, Condition.EQUALS, userId);
		c.addCondition(cond);
		
		List<UserAddress> list = (List)manager.getList(ModelNames.USERADDRESS, c ,0 ,5);
		
		return WrapperUtil.wrap(list, UserAddressWrapper.class);
		
		
		
	}
	
	/**
	 * 保存用户的收货人信息
	 * 如果收货人信息中的 id 为 0 则新增一个收货人信息
	 *
	 * @access  public
	 * @param   array   $consignee
	 * @param   boolean $default        是否将该收货人信息设置为默认收货人信息
	 * @return  boolean
	 */
	public static boolean saveConsignee(UserAddressWrapper consignee , boolean isDefault , IDefaultManager manager){
		/* 修改地址 */
		UserAddress ua = consignee.getUserAddress();
		String uaId = ua.getPkId();
		if(StringUtils.isEmpty(uaId)) {
			uaId = manager.txadd(ua);
		} else {
			manager.txupdate(ua);
		}
		
		User user = (User)manager.get(ModelNames.USER,consignee.getUserId());
		user.setAddressId(uaId);
		manager.txattach(user);
		if(isDefault){
			// TODO isDefault
		}
		return true;
	}
}
