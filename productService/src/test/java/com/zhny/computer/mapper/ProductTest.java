package com.zhny.computer.mapper;

import com.zhny.computer.entity.Product;
import com.zhny.computer.service.ProductService;
import com.zhny.computer.service.util.ProductSelect;
import com.zhny.computer.vo.ProductVO;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@SpringBootTest
@RunWith(SpringRunner.class)

public class ProductTest {
    @Autowired
    private ProductMapper productMapper;
    @Autowired
    private ProductSelect productSelect;
    @Test
    public void selectAll() {
        Product product = new Product();
        product=productMapper.autoSelectBestValueD4AmdCPU(0,2000,"ASC");
        System.out.println(product);
    }




}
