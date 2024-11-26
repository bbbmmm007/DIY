package com.mapper.computer;


import com.zhny.computer.mapper.ProductMapper;
import com.zhny.computer.profileCartServiceApp_8040;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest(classes = profileCartServiceApp_8040.class)
@RunWith(SpringRunner.class)
public class Profile {
    @Autowired
    private ProductMapper productMapper;
    @Test
    public void test(){
        System.out.println(productMapper.updateProductCartCount(100000465));
    }
}
