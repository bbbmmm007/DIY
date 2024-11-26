package com.zhny.computer.controller;


import com.zhny.computer.DTO.*;
import com.zhny.computer.config.RateLimiter;
import com.zhny.computer.entity.User;
import com.zhny.computer.service.UserService;
import com.zhny.computer.service.ex.ServiceTooSpeedException;
import com.zhny.computer.service.util.JsonResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;


import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.concurrent.TimeUnit;


@RestController
@RequestMapping("users")
public class UserController extends BaseController {
    @Autowired
    private UserService userService;
    @Autowired
    private RedisTemplate<String, Object> redisTemplate;
    @Autowired
    private RateLimiter rateLimiter;
    //注册的映射
    @PostMapping("reg")
    public JsonResult<Void> reg(@RequestBody User user) {
        userService.reg(user);
        return new JsonResult<Void>(SUCCESS);
    }
    // 登录的映射
    @PostMapping("login")
    public JsonResult<User> login(@RequestBody UserLoginDTO userLoginDTO, HttpSession session) {
        if (!rateLimiter.isAllowed("user_login", userLoginDTO.getUsername(), 100, "minute")) {
            throw new ServiceTooSpeedException("请求过快,请稍后再试");
        }

        User data = userService.login(userLoginDTO);
        // 在 session 中绑定用户数据
        session.setAttribute("uid", data.getUid());
        session.setAttribute("username", data.getUsername());

        // 将用户信息存入 Redis，提升性能
        redisTemplate.opsForValue().set("login:session:" + data.getUid(), data, 30, TimeUnit.MINUTES);

        // 获取 session 绑定中的数据
        System.out.println(getUidFromSession(session));
        System.out.println(getUsernameFromSession(session));

        return new JsonResult<>(SUCCESS, data);
    }
    // 退出登录的映射
    @DeleteMapping("logout")
    public JsonResult<Void> logout(HttpSession session) {
        // 获取用户的 uid
        System.out.println(getUidFromSession(session));
        System.out.println(getUsernameFromSession(session));
        Integer uid = getUidFromSession(session);
        String username = getUsernameFromSession(session);
        // 清除 session 中的用户信息
        session.removeAttribute("uid");
        session.removeAttribute("username");
        // 如果 uid 不为 null，清除 Redis 中的数据
        if (uid != null) {
            redisTemplate.delete("login:session:" + uid);
        }

        if (username != null) {
            redisTemplate.delete("login:session:" + username);
        }
        System.out.println("退出成功");
        return new JsonResult<>(SUCCESS);
    }
    //更改密码的映射
    @PutMapping("change_password")
    public JsonResult<Void> changePassword(@RequestBody UserUpPasswordDTO userUpPasswordDTO,@RequestParam("uid") Integer uid) {
        System.out.println(uid);
        userService.changePassword(uid,userUpPasswordDTO);
        System.out.println("密码修改成功");
        return new JsonResult<>(SUCCESS);
    }
    //查询显示个人信息的映射(数据回显)
    @GetMapping("get_by_uid")
    public JsonResult<User> getByUid(@RequestParam("uid") Integer uid) {
        User data = userService.getByUid(uid);
        System.out.println("数据成功显示");
        return new JsonResult<>(SUCCESS, data);

    }
    //更改个人信息的映射
    @PutMapping("change_info")
    public JsonResult<Void> changeInfo(@RequestBody UserUpInfoDTO userUpInfoDTO, @RequestParam("uid") Integer uid) {
        userService.changeInfo(uid,userUpInfoDTO);
        System.out.println("修改成功");
        return new JsonResult<>(SUCCESS);
    }
    //统计用户数量
    @GetMapping("/count")
    public JsonResult<Integer> count(@RequestParam("amid") Integer amid) {
        Integer count = userService.countUsers(amid);
        return new JsonResult<>(SUCCESS, count);
    }
    //分页显示用户
    @PostMapping("/show/{pageNumber}/{pageSize}")
    public JsonResult<List<User>> show(@PathVariable("pageNumber") Integer pageNumber,@PathVariable("pageSize") Integer pageSize,@RequestParam("amid") Integer amid) {
        List<User> users = userService.findUsersByPage(amid,pageNumber,pageSize);
        return new JsonResult<>(SUCCESS, users);
    }
    //显示用户分布情况
    @GetMapping("/show_user")
    public JsonResult<List<UserDistributionDTO>> getUserAgeGenderDistribution(@RequestParam("amid") Integer amid) {
        List<UserDistributionDTO> distribution = userService.getUserAgeGenderDistribution(amid);
        return new JsonResult<>(SUCCESS,distribution);
    }
    //删除用户
    @DeleteMapping("/delete/{uid}")
    public JsonResult<Void> delete(@PathVariable("uid") Integer uid,@RequestParam("amid") Integer amid) {
        userService.deleteUser(amid,uid);
        System.out.println("删除用户成功");
        return new JsonResult<>(SUCCESS);
    }
    //用户注销
    @DeleteMapping("/delete_long")
    public JsonResult<Void> deleteLong(@RequestParam("uid") Integer uid,@RequestParam("username") String username) {
        userService.deleteLongUser(uid,username);
        System.out.println("账号注销成功");
        return new JsonResult<>(SUCCESS);
    }



}

