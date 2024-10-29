package com.zhny.computer;

import com.zhny.computer.DTO.*;
import com.zhny.computer.entity.User;
import com.zhny.computer.mapper.UserMapper;

import com.zhny.computer.service.UserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@SpringBootTest //表示当前类是一个测试类
@RunWith(SpringRunner.class)
public class UserMapperTest {
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private UserService userService;


    @Test
    public void insert() {
       User user = new User();
       user.setUsername("bbbmmm");
       user.setPassword("000");
       userMapper.insert(user);
    }
    @Test
    public void add(){
        User user = new User();
        user.setUsername("nn");
        user.setPassword("000");
        userService.reg(user);
    }
    @Test
    public void select(){
        UserLoginDTO loginDTO = new UserLoginDTO();
        loginDTO.setUsername("nn");
        loginDTO.setPassword("000");
        userService.login(loginDTO);
    }







}
