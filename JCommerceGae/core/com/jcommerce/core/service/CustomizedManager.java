package com.jcommerce.core.service;

import java.util.List;

import com.jcommerce.core.model.Brand;
import com.jcommerce.core.model.Goods;
import com.jcommerce.core.model.GoodsType;

public interface CustomizedManager {
    
    public int getGoodsTypeListWithAttrCount(List<GoodsType> resultSet, int firstRow, int maxRow, Criteria criteria);
    
    public String addBrand(Brand to);
    
//    public String addPerson(Person to, Address a);
    
    public String addGoods(Goods to);
    public boolean updateGoods(Goods to);
    

}
