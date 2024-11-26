package com.zhny.computer.mapper;

import com.zhny.computer.entity.Product;
import com.zhny.computer.service.ProductService;
import com.zhny.computer.service.util.ProductSelect;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;
import java.util.List;

@SpringBootTest
@RunWith(SpringRunner.class)

public class ProductTest {
    @Autowired
    private ProductMapper productMapper;
    @Autowired
    private ProductSelect productSelect;
    @Autowired
    private ProductService productService;
    @Test
    public void selectAll() {
        Product product = new Product();
        product=productMapper.autoSelectBestValueD4AmdCPU(0,2000,"ASC");
        System.out.println(product);
    }
    @Test
    public void selectById() {
        Product product = new Product();
        product.setPrice(2100l);
        product.setItemType("ROG龙神三代360水冷");
        product.setStatus(1);
        product.setChildId(323);
        product.setParentId(32);
        product.setAncestorId(3);
        product.setImage("mm");
        product.setSalt(99);
        product.setPerformanceScore(70);
        product.setCountCart(0);
        product.setCountCollect(0);


        productMapper.updateProduct("七彩虹adoc 4060ti显卡",2888l,"mm",88,80,"mm",new Date(),100000468);
    }




}
