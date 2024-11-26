package com.zhny.computer.controller;


import com.zhny.computer.DTO.*;
import com.zhny.computer.entity.Collection;
import com.zhny.computer.entity.User;
import com.zhny.computer.openfeign.CartOpenfeignService;
import com.zhny.computer.openfeign.CollectionOpenfieService;
import com.zhny.computer.openfeign.UserOpenfeignService;
import com.zhny.computer.until.JsonResult;

import com.zhny.computer.vo.CartVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;


import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("/users")
public class UserOpenFeiController extends BaseController {
    @Autowired
    private RedisTemplate<String, Object> redisTemplate;
    @Autowired
    UserOpenfeignService userOpenfeignService;
    @Autowired
    CartOpenfeignService cartOpenfeignService;
    @Autowired
    CollectionOpenfieService collectionOpenfieService;
    //注册的映射
    @PostMapping("reg")
    JsonResult<Void> reg(@RequestBody User user){
        return userOpenfeignService.reg(user);
    }
    // 登录的映射
    @PostMapping("login")
    JsonResult<User> login(@RequestBody UserLoginDTO userLoginDTO, HttpSession session) {
        // 调用用户服务的登录接口
        JsonResult<User> result = userOpenfeignService.login(userLoginDTO);

        if (result.getState() == 200) {
            User user = result.getData();

            // 在 session 中绑定用户数据
            session.setAttribute("uid", user.getUid());
            session.setAttribute("username", user.getUsername());

            // 将用户信息存入 Redis，提升性能并设置过期时间为 30 分钟
            redisTemplate.opsForValue().set("login:session:" + user.getUid(), user, 30, TimeUnit.MINUTES);

            System.out.println(user.getUid());
            System.out.println(user.getUsername());
        }

        return result;
    }
    @DeleteMapping("logout")
    public JsonResult<Void> logout(HttpSession session) {
        // 从 session 中获取用户的 uid
        Integer uid = getUidFromSession(session);

        // 清除 session 中的用户信息
        if (uid != null) {
            // 删除 Redis 中的用户数据
            redisTemplate.delete("login:session:" + uid);
            System.out.println("用户 " + uid + " 的 Redis 缓存已清除");
        }

        // 清除 session
        session.invalidate(); // 清除 session 中的所有属性
        return new JsonResult<>(SUCCESS);
    }

    //更改密码的映射
    @PutMapping("change_password")
    JsonResult<Void> changePassword(@RequestBody UserUpPasswordDTO userUpPasswordDTO,HttpSession session){
        Integer uid = getUidFromSession(session);
        return userOpenfeignService.changePassword(userUpPasswordDTO,uid);
    }
    //查询显示个人信息的映射(数据回显)
    @GetMapping("get_by_uid")
    JsonResult<User> getByUid(HttpSession session){
        Integer uid = getUidFromSession(session);
        return userOpenfeignService.getByUid(uid);
    }
    //更改个人信息的映射
    @PutMapping("change_info")
    JsonResult<Void> changeInfo(@RequestBody UserUpInfoDTO userUpInfoDTO,HttpSession session){
        Integer uid = (Integer) session.getAttribute("uid");
        return userOpenfeignService.changeInfo(userUpInfoDTO,uid);
    }
    //用户注销
    @DeleteMapping("/delete_long")
    JsonResult<Void> deleteLong(HttpSession session){
        Integer uid = (Integer) session.getAttribute("uid");
        String username = session.getAttribute("username").toString();
        return userOpenfeignService.deleteLong(username,uid);
    }



    //添加商品进入配置车的接口
    @PostMapping("add_carts/{id}")
    JsonResult<Void> addCarts(HttpSession session,@PathVariable("id") Integer id){
        Integer uid = getUidFromSession(session);
        String username = getUsernameFromSession(session);

        return cartOpenfeignService.addCarts(uid,username,id);
    }

    //移除商品进入配置车的接口
    @DeleteMapping("delete_carts/{id}")
    JsonResult<Void> deleteCarts(HttpSession session,@PathVariable("id") Integer id){
        Integer uid = getUidFromSession(session);
        return cartOpenfeignService.deleteCarts(uid,id);
    }

    //清空配置车的接口
    @DeleteMapping("clear_carts")
    JsonResult<Void> clearCarts(HttpSession session){
        Integer uid = getUidFromSession(session);
        return cartOpenfeignService.clearCarts(uid);
    }

    //展示配置车的接口
    @GetMapping("show_carts")
    JsonResult<CartVO> showCarts(HttpSession session){
        Integer uid = getUidFromSession(session);
        return cartOpenfeignService.showCarts(uid);
    }



    //加入收藏
    @PostMapping("/add_collection/{id}")
    JsonResult<Void> addCollection(HttpSession session,@PathVariable("id") Integer id){
        Integer uid = getUidFromSession(session);
        return collectionOpenfieService.addCollection(uid,id);
    }
    //移除收藏
    @DeleteMapping("/delete_collection/{id}")
    JsonResult<Void> deleteCollection(HttpSession session, @PathVariable("id") Integer id){
        Integer uid = getUidFromSession(session);
        return collectionOpenfieService.deleteCollection(uid,id);
    }
    //展示收藏列表
    @GetMapping("/show_collection")
    JsonResult<List<Collection>> showCollection(HttpSession session){
        Integer uid = getUidFromSession(session);
        return collectionOpenfieService.showCollection(uid);
    }
    //清空收藏列表
    @DeleteMapping("/clear_collection")
    JsonResult<Void> clearCollection(HttpSession session){
        Integer uid = getUidFromSession(session);
        return collectionOpenfieService.clearCollection(uid);
    }
}
