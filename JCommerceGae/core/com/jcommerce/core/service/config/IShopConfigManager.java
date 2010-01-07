package com.jcommerce.core.service.config;

import java.util.List;
import java.util.Locale;
import java.util.SortedMap;

import com.jcommerce.core.model.ShopConfig;
import com.jcommerce.web.to.ShopConfigWrapper;

public interface IShopConfigManager {
	public SortedMap<Integer, List<ShopConfigMeta>> getCombinedShopConfigMetaMap(String locale);
	public Boolean saveShopConfig(List<ShopConfig> tos);
	public ShopConfigWrapper getCachedShopConfig(String locale);
}
