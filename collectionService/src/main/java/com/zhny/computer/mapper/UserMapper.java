package com.zhny.computer.mapper;


import com.zhny.computer.entity.User;

public interface UserMapper {
    //根据uid查询用户信息
    User findByUid(Integer uid);

}

