package com.zhny.computer.service.impl;

import com.zhny.computer.DTO.AdminLoginDTO;
import com.zhny.computer.DTO.AdminUpPasswordDTO;
import com.zhny.computer.entity.Admin;
import com.zhny.computer.mapper.AdminMapper;
import com.zhny.computer.service.AdminService;
import com.zhny.computer.service.ex.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.*;


@Service
public class AdminServiceImpl implements AdminService {
    @Autowired
    private AdminMapper adminMapper;



    //管理员登录
    @Override
    public Admin login(AdminLoginDTO adminLoginDTO) {
        Admin result = adminMapper.findByName(adminLoginDTO.getAdminName());
        if (result == null) {
            throw new DataSelectException("没有找到该用户相关信息");
        }
        if (!result.getPassword().equals(adminLoginDTO.getPassword())) {
            throw new DataMatchException("密码错误");
        }
        Admin admin = new Admin();
        admin.setAmid(result.getAmid());
        admin.setAdminName(result.getAdminName());

        return admin;

    }
    //注册管理员的实现
    @Override
    public void register(Admin admin) {
        //调用findusername的方法
        String adminName = admin.getAdminName();
        //通过user.getaname()获取用户名
        Admin result = adminMapper.findByName(adminName);
        if (result != null) {
            //异常抛出
            throw new DataSelectException("用户被占用");
        }


        admin.setCreatedUser(admin.getAdminName());
        admin.setModifiedUser(admin.getAdminName());
        Date now = new Date();
        admin.setCreatedTime(now);
        admin.setModifiedTime(now);

        Integer rows = adminMapper.insert(admin);
        if (rows != 1) {
            throw new DataInsertException("用户注册时发生未知错误");

        }
    }


    //根据amid注销管理员
    @Override
    public void deleteAdmin(Integer amid) {
        Admin result=adminMapper.findByAmid(amid);
        if(result==null){
            throw new DataAccessException("非法的数据访问");
        }
        Integer rows = adminMapper.deleteAdmin(result.getAmid());
        if (rows != 1) {
            throw new DataDeleteException("注销失败");
        }

    }

    //管理员修改密码
    @Override
    public void updateAdmin(Integer amid, String adminName, AdminUpPasswordDTO adminUpPasswordDTO) {
        Admin result = adminMapper.findByAmid(amid);
        if(result==null){
            throw new DataAccessException("非法的数据访问");
        }
        if (adminUpPasswordDTO.getOldPassword().equals(adminUpPasswordDTO.getNewPassword())) {
            throw new DataMatchException("新密码不能与旧密码相同");
        }
        Integer rows = adminMapper.updatePasswordByAmid(amid,adminUpPasswordDTO.getNewPassword(),result.getAdminName(),new Date());
        if (rows != 1) {
            throw new DataUpdateException("更新数据失败");
        }

    }

}


