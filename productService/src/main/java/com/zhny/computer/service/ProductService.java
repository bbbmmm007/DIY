package com.zhny.computer.service;
import com.zhny.computer.entity.Product;
import com.zhny.computer.vo.ProductVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;


public interface ProductService {

    //统计商品数量
    Integer countProducts(ProductVO params);
    // 查询商品信息，根据条件和分页
    List<Product> findProducts(ProductVO params, Integer pageSize, Integer pageNumber);
    //新增商品
    void insertProduct(Integer amid,Product product);
    //更新商品信息
    void updateProduct(Integer amid,Integer id, Product product);
    //删除商品
    void deleteProduct(Integer amid,Integer id);
    //显示商品详细信息
    Product showProduct(Integer id);

}
