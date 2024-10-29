package com.zhny.computer.service;

import com.zhny.computer.DTO.*;
import com.zhny.computer.entity.User;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

public interface UserService {
    //注册接口
    String reg(User user);
    //登录接口
    User login(UserLoginDTO userLoginDTO);
    //更新密码接口
    void changePassword(Integer uid,UserUpPasswordDTO upPasswordDTO);
    //根据用户uid查询用户信息接口
    User getByUid(Integer uid);
    //用户更新信息接口
    void changeInfo(Integer uid,UserUpInfoDTO userUpInfoDTO);
    //用户删除
    void deleteUser(Integer amid,Integer uid);
    //用户注销
    void deleteLongUser(Integer uid,String username);
    //统计用户数量
    Integer countUsers(Integer amid);
    //分页查询用户信息
    List<User> findUsersByPage(Integer amid, Integer pageNumber,Integer pageSize);
    //查看用户的分布情况
    List<UserDistributionDTO> getUserAgeGenderDistribution(Integer amid);

}
