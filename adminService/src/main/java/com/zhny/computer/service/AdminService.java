package com.zhny.computer.service;

import com.zhny.computer.DTO.AdminLoginDTO;
import com.zhny.computer.DTO.AdminUpPasswordDTO;
import com.zhny.computer.entity.Admin;



public interface AdminService {
    //管理员登录
    Admin login(AdminLoginDTO adminLoginDTO);
    //管理员注册
    void register(Admin admin);
    //注销管理员
    void deleteAdmin(Integer amid);
    //更改管理员密码
    void updateAdmin(Integer amid, String adminName, AdminUpPasswordDTO adminUpPasswordDTO);

}
