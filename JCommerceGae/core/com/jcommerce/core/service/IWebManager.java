package com.jcommerce.core.service;

import java.util.List;

public interface IWebManager {
	
    public boolean addToCart(Long goodsId, int num, List spec, String sessionId, String userId, String parentGoodsId);
    
    
}
