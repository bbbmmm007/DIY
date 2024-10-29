package com.zhny.computer.mapper;

import com.zhny.computer.entity.Admin;
import org.apache.ibatis.annotations.Param;
import java.util.Date;


public interface AdminMapper {

    //管理员注册
    Integer insert(Admin admin);
    //管理员更改密码
    Integer updatePasswordByAmid(@Param("amid") Integer amid,
                                @Param("password") String password,
                                @Param("modifiedUser") String modifiedUser,
                                @Param("modifiedTime") Date modifiedTime);
    //注销管理员账号
    Integer deleteAdmin(Integer amid);
    //通过管理员账号名查询信息
    Admin findByName(String adminName);
    //通过管理员id查询信息
    Admin findByAmid(Integer amid);





}
