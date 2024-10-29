package com.zhny.computer.service.impl;


import com.zhny.computer.DTO.*;
import com.zhny.computer.entity.Admin;
import com.zhny.computer.entity.User;
import com.zhny.computer.mapper.AdminMapper;
import com.zhny.computer.mapper.UserMapper;
import com.zhny.computer.service.UserService;
import com.zhny.computer.service.ex.*;
import com.zhny.computer.service.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.PathVariable;


import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private RedisTemplate redisTemplate;
    @Autowired
    private AdminMapper adminMapper;

    //用户注册的功能实现
    @Override
    public String reg(User user) {
        // 调用findByUsername的方法
        String username = user.getUsername();
        User result = userMapper.findByUsername(username);

        // 检查用户名是否已被占用
        if (result != null) {
            throw new DataSelectException("该用户名已经被占用！");
        }

        // 密码加密处理：MD5算法实现
        String oldPassword = user.getPassword();
        String salt = UUID.randomUUID().toString().toUpperCase();
        user.setSalt(salt);

        // 将密码和盐值进行加密处理
        String md5Password = getMD5Password(oldPassword, salt);
        user.setPassword(md5Password);

        // 补全用户信息
        user.setIsDelete(0); // 是否删除，0表示未删除
        user.setCreatedUser(username);
        user.setModifiedUser(username);
        Date now = new Date();
        user.setCreatedTime(now);
        user.setModifiedTime(now);

        // 插入用户数据
        Integer rows = userMapper.insert(user);
        if (rows != 1) {
            throw new DataInsertException("用户注册时发生未知错误！");
        }

        // 生成 JWT
        String token = JwtUtil.generateToken(username);

        // 缓存用户信息到 Redis
        redisTemplate.opsForValue().set("USER_SESSION:" + token, user, 30, TimeUnit.MINUTES); // 设置过期时间为30分钟
        System.out.println(token);
        return token;
    }


    //用户登录功能的实现
    @Override
    public User login(UserLoginDTO userLoginDTO) {
        User result = userMapper.findByUsername(userLoginDTO.getUsername());

        // 检测用户是否存在
        if (result == null || result.getIsDelete() == 1) {
            throw new DataSelectException("用户不存在或已被删除！");
        }

        // 检测用户密码是否匹配
        String salt = result.getSalt();
        String newPassword = getMD5Password(userLoginDTO.getPassword(), salt);

        // 判断用户输入的密码是否正确
        if (!newPassword.equals(result.getPassword())) {
            throw new DataMatchException("用户密码错误！");
        }

        // 生成 JWT
        String token = JwtUtil.generateToken(result.getUsername());

        // 缓存用户信息到 Redis
        redisTemplate.opsForValue().set("USER_SESSION:" + token, result, 30, TimeUnit.MINUTES); // 设置过期时间为30分钟

        // 返回用户信息和 JWT
        return result; // 可选择返回用户对象或仅返回 token
    }




    //更改密码的功能实现
    @Override
    public void changePassword(Integer uid,UserUpPasswordDTO upPasswordDTO) {
        User result = userMapper.findByUid(uid);
        //判断uid是否为空，或用户是否被删除
        if(result == null || result.getIsDelete()==1) {
            throw new DataSelectException("用户不存在！");
        }
        //原密码与数据库中的密码进行比较、
        String oldMd5Password = getMD5Password(upPasswordDTO.getOldPassword(), result.getSalt());//获取原来的密码
        if(!result.getPassword().equals(oldMd5Password)) {
            throw new DataMatchException("原密码输入错误！");
        }
        //新密码与原密码进行比较
        String newMd5Password = getMD5Password(upPasswordDTO.getNewPassword(), result.getSalt());//获取需要更改的新密码
        if(newMd5Password.equals(oldMd5Password)) {
            throw new DataMatchException("新密码与原密码不能相同");

        }
        String username=result.getUsername();
        Integer rows = userMapper.updatePasswordByUid(username,newMd5Password,new Date());
        if (rows != 1) {
            throw new DataUpdateException("发生未知错误，更新密码失败！");
        }
    }

    //用户信息显示的功能实现
    @Override
    public User getByUid(Integer uid) {
        User result = userMapper.findByUid(uid);//获取uid
        if (result == null || result.getIsDelete()==1) {//检测用户是否存在
            throw new DataSelectException("用户没有被找到！");
        }
        User user = new User();
        user.setUsername(result.getUsername());
        user.setAge(result.getAge());
        user.setGender(result.getGender());

        return user;
    }


    //修改用户信息的功能实现
    @Override
    public void changeInfo(Integer uid,UserUpInfoDTO userUpInfoDTO) {
        User result = userMapper.findByUid(uid);//获取uid
        if (result == null || result.getIsDelete()==1) {//检测用户是否存在
            throw new DataSelectException("用户没有被找到！");
        }
        User user = new User();
        user.setUid(uid);
        user.setUsername(result.getUsername());
        user.setModifiedTime(new Date());
        user.setAge(userUpInfoDTO.getAge());
        user.setGender(userUpInfoDTO.getGender());

        Integer rows = userMapper.updateInfoByUid(user);
        if (rows != 1) {
            throw new DataUpdateException("更新数据时发生异常");
        }
        else{
            System.out.println("信息修改成功"+user);
        }
    }
    //用户删除
    @Override
    public void deleteUser(Integer amid,Integer uid) {
        Admin admin = adminMapper.findByAmid(amid);
        if (admin == null) {
            throw new DataAccessException("非法的数据访问");
        }
        User result = userMapper.findByUid(uid);
        if (result == null || result.getIsDelete()==1) {
            throw new DataSelectException("用户不存在");
        }
        userMapper.deleteUser(uid);
    }
    //用户注销
    @Override
    public void deleteLongUser(Integer uid, String username) {
        User resultUid = userMapper.findByUid(uid);
        User resultUsername = userMapper.findByUsername(username);
        if (resultUid != null && resultUsername != null) {
            if (resultUid.equals(resultUsername)) {
                userMapper.deleteUser(uid);
            } else {
                throw new DataMatchException("用户数据不匹配！");
            }
        } else {
            throw new DataSelectException("用户不存在");
        }


    }

    //统计用户数量
    @Override
    public Integer countUsers(Integer amid) {
        Admin admin = adminMapper.findByAmid(amid);
        if (admin == null) {
            throw new DataAccessException("非法的数据访问");
        }
        return userMapper.countUsers();
    }


    //分页查询用户
    @Override
    public List<User> findUsersByPage(Integer amid, Integer pageNumber, Integer pageSize) {
        Admin admin = adminMapper.findByAmid(amid);
        if (admin == null) {
            throw new DataAccessException("非法的数据访问");
        }
        Integer offset = (pageNumber - 1) * pageSize;
        return userMapper.findUsersByPage(pageSize,offset);
    }
    //查询用户分布情况
    @Override
    public List<UserDistributionDTO> getUserAgeGenderDistribution(Integer amid) {
        Admin admin = adminMapper.findByAmid(amid);
        if (admin == null) {
            throw new DataAccessException("非法的数据访问");
        }
        return userMapper.getUserAgeGenderDistribution();
    }


    //定义一个md5算法的加密处理
    private String getMD5Password(String password, String salt) {
        for(int i = 0; i<3; i++){
            //进行3次加密
            password = DigestUtils.md5DigestAsHex((salt+password+salt).getBytes()).toUpperCase();

        }
        return password;

    }
}
