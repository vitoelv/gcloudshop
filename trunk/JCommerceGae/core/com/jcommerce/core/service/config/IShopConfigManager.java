package com.jcommerce.core.service.config;

import com.jcommerce.core.model.ShopConfig;
import com.jcommerce.core.service.IDefaultManager;
import com.jcommerce.web.to.ShopConfigWrapper;

import java.util.List;
import java.util.SortedMap;

public interface IShopConfigManager {
        public Boolean isDefaultAdminEnabled();
	public SortedMap<Integer, List<ShopConfigMeta>> getCombinedShopConfigMetaMap(String locale);
	public Boolean saveShopConfig(List<ShopConfig> tos);
	public ShopConfigWrapper getCachedShopConfig(String locale);
}
