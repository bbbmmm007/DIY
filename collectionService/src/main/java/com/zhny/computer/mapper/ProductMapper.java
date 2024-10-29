package com.zhny.computer.mapper;

import com.zhny.computer.entity.Product;
import com.zhny.computer.vo.ProductVO;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

public interface ProductMapper {
    // 根据商品ID查询商品信息
    Product findById(@Param("id") Integer id);
}



