package com.zhny.computer.mapper;

import com.zhny.computer.DTO.UserDistributionDTO;
import com.zhny.computer.entity.User;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;


public interface UserMapper {
    //用户注册
    Integer insert(User user);
    //查询用户用户名是否重复
    User findByUsername(String username);
    //更新密码
    Integer updatePasswordByUid(@Param("username") String username,@Param("password") String password,@Param("modifiedTime") Date modifiedTime);
    //根据uid查询用户信息
    User findByUid(Integer uid);
    //用户更新信息
    Integer updateInfoByUid(User user);
    //分页查询用户信息
    List<User> findUsersByPage(@Param("pageSize") Integer pageSize,@Param("offset") Integer offset);
    //统计用户数量
    Integer countUsers();
    //删除用户
    void deleteUser(Integer uid);
    //查看用户的性别年龄的分布情况
    List<UserDistributionDTO> getUserAgeGenderDistribution();
}

