package com.jcommerce.core.service;

import java.util.List;

import com.jcommerce.core.model.Brand;
import com.jcommerce.core.model.GoodsType;

public interface CustomizedManager {
    
    public int getGoodsTypeListWithAttrCount(List<GoodsType> resultSet, int firstRow, int maxRow, Criteria criteria);
    
    public String addBrand(Brand to);
    
}
