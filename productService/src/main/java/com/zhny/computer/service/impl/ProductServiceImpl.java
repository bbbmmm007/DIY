package com.zhny.computer.service.impl;


import com.zhny.computer.entity.Admin;
import com.zhny.computer.entity.Product;
import com.zhny.computer.mapper.AdminMapper;
import com.zhny.computer.mapper.ProductMapper;
import com.zhny.computer.service.ProductService;
import com.zhny.computer.service.ex.DataAccessException;
import com.zhny.computer.service.ex.DataDeleteException;
import com.zhny.computer.service.ex.DataSelectException;
import com.zhny.computer.service.ex.DataUpdateException;
import com.zhny.computer.vo.ProductVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;



import java.util.Date;
import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {
    @Autowired
    private ProductMapper productMapper;
    @Autowired
    private AdminMapper adminMapper;
    //统计商品数量
    @Override
    public Integer countProducts(ProductVO params) {
        return productMapper.countProducts(params);
    }
    //商品的分页查询
    @Override
    public List<Product> findProducts(ProductVO params, Integer pageSize, Integer pageNumber) {
        Integer offset = (pageNumber - 1) * pageSize;
        return productMapper.findProducts(params, pageSize, offset);
    }
    //新增商品的实现
    @Override
    public void insertProduct(Integer amid,Product product) {
        Admin admin = adminMapper.findByAmid(amid);
        if (admin == null) {
            throw new DataAccessException("非法的数据访问");
        }
        product.setChildId(product.getChildId());
        product.setParentId(product.getParentId());
        product.setAncestorId(product.getAncestorId());
        product.setItemType(product.getItemType());
        product.setPrice(product.getPrice());
        product.setImage(product.getImage());
        product.setPerformanceScore(product.getPerformanceScore());
        product.setSalt(product.getSalt());

        product.setStatus(1);
        //补全四项日志
        product.setCreatedUser("admin");
        product.setModifiedUser("admin");
        product.setCreatedTime(new Date());
        product.setModifiedTime(new Date());

    }
    //更新商品信息
    @Override
    public void updateProduct(Integer amid,Integer id, Product product) {
        Admin admin =  adminMapper.findByAmid(amid);
        if (admin == null) {
            throw new DataAccessException("非法的数据访问");
        }
        Product rusltPr = productMapper.findById(id);
        if(rusltPr ==null){
            throw new DataSelectException("商品不存在");
        }
        product.setId(id);
        product.setStatus(1);
        Integer rows = productMapper.updateProduct(product.getItemType(),product.getPrice(),product.getImage(),product.getSalt(),product.getPerformanceScore(),"admin",new Date(),id);
        if (rows != 1) {
            throw new DataUpdateException("更新数据失败");
        }
    }
    //删除商品
    @Override
    public void deleteProduct(Integer amid,Integer id) {
        Admin admin =  adminMapper.findByAmid(amid);
        if (admin == null) {
            throw new DataAccessException("非法的数据访问");
        }
        Product product = productMapper.findById(id);
        if(product==null){
            throw new DataSelectException("商品不存在");
        }
        Integer rows = productMapper.deleteProduct(id);
        if (rows != 1) {
            throw new DataDeleteException("删除商品数据失败");
        }
    }
    //商品的数据回显
    @Override
    public Product showProduct(Integer id) {
        Product product = productMapper.showProduct(id);
        if (product == null) {
            throw new DataSelectException("商品不存在");
        }
        return product;
    }

}
