package com.zhny.computer.mapper;


import com.zhny.computer.entity.Admin;

public interface AdminMapper {

    //通过管理员账号名查询信息
    Admin findByname(String adminName);
    //通过管理员id查询信息
    Admin findByAmid(Integer amid);




}
