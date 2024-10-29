package com.zhny.computer.controller;



import com.zhny.computer.DTO.AdminLoginDTO;
import com.zhny.computer.DTO.AdminUpPasswordDTO;
import com.zhny.computer.DTO.UserDistributionDTO;
import com.zhny.computer.entity.Admin;
import com.zhny.computer.entity.Product;
import com.zhny.computer.entity.User;
import com.zhny.computer.openfeign.AdminOpenfeignService;
import com.zhny.computer.openfeign.ProductfeignService;
import com.zhny.computer.openfeign.UserOpenfeignService;
import com.zhny.computer.until.JsonResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;


import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.concurrent.TimeUnit;


@RestController
@RequestMapping("admin")
public class adminOpenFeignController extends BaseController{
    @Autowired
    private RedisTemplate<String, Object> redisTemplate;
    @Autowired
    AdminOpenfeignService adminOpenfeignService;
    @Autowired
    ProductfeignService productfeignService;
    @Autowired
    UserOpenfeignService userOpenfeignService;
    // 登录的映射
    @PostMapping("login")
    JsonResult<Admin> login(@RequestBody AdminLoginDTO adminLoginDTO,HttpSession session){
        JsonResult<Admin> result =adminOpenfeignService.login(adminLoginDTO);
        if (result.getState() == 200) {
            Admin admin = result.getData();

            // 在 session 中绑定用户数据
            session.setAttribute("amid", admin.getAmid());
            session.setAttribute("adminName", admin.getAdminName());

            // 将用户信息存入 Redis，提升性能并设置过期时间为 30 分钟
            redisTemplate.opsForValue().set("login:session:" + admin.getAmid(), admin, 30, TimeUnit.MINUTES);

            System.out.println(getAmidFromSession(session));
            System.out.println(getAdminNameFromSession(session));
        }

        return result;
    }
    // 退出登录的映射
    @DeleteMapping("logout")
    public JsonResult<Void> logout(HttpSession session) {
        // 从 session 中获取管理员的 amid 和 adminName
        Integer amid = getAmidFromSession(session);

        // 清除 session 中的管理员信息
        if (amid != null) {
            // 删除 Redis 中的管理员数据
            redisTemplate.delete("login:session:" + amid);
            System.out.println("管理员 " + amid + " 的 Redis 缓存已清除");
        }

        // 清除 session
        session.invalidate(); // 清除 session 中的所有属性
        return new JsonResult<>(SUCCESS);
    }

    //管理员注册的接口
    @PostMapping("reg")
    JsonResult<Void> register(@RequestBody Admin admin){
        return adminOpenfeignService.register(admin);
    }
    //注销管理员的接口
    @DeleteMapping("/delete_admin")
    public JsonResult<Void> deleteAdmin(HttpSession session){
        Integer amid = getAmidFromSession(session);
        return adminOpenfeignService.deleteAdmin(amid);
    }
    //管理员更新密码的接口
    @PutMapping("/update")
    JsonResult<Void> updateAdmin(@RequestBody AdminUpPasswordDTO adminUpPasswordDTO,HttpSession session){
        Integer amid = getAmidFromSession(session);
        String adminName = getAdminNameFromSession(session);
        return adminOpenfeignService.updateAdmin(adminUpPasswordDTO,amid,adminName);
    }




    //管理员删除商品
    @DeleteMapping("delete_product/{id}")
    JsonResult<Void> deleteProduct(HttpSession session,@PathVariable("id") Integer id){
        Integer amid = getAmidFromSession(session);
        return productfeignService.delete(amid,id);
    }
    //管理员修改商品信息
    @PutMapping("change_product/{id}")
    JsonResult<Void> changeProduct(HttpSession session,@PathVariable("id") Integer id,@RequestBody Product product){
        Integer amid = getAmidFromSession(session);
        return productfeignService.change(amid,id, product);
    }
    //管理员新增商品
    @PostMapping("add_product")
    JsonResult<Void> addProduct(HttpSession session,@RequestBody Product product){
        Integer amid = getAmidFromSession(session);
        return productfeignService.add(amid,product);
    }





    //分页显示用户
    @PostMapping("/show/{pageNumber}/{pageSize}")
    JsonResult<List<User>> show(@PathVariable("pageNumber") Integer pageNumber,
                                @PathVariable("pageSize") Integer pageSize,
                                HttpSession session){
        Integer amid = getAmidFromSession(session);
        System.out.println(amid);
        return userOpenfeignService.show(pageNumber,pageSize,amid);
    }

    //管理员删除用户
    @DeleteMapping("delete_user/{uid}")
    JsonResult<Void> deleteUser(HttpSession session,@PathVariable("uid") Integer uid){
        Integer amid = getAmidFromSession(session);
        return userOpenfeignService.delete(amid,uid);
    }

    //管理员查看用户分布情况
    @GetMapping("show_user")
    JsonResult<List<UserDistributionDTO>> getUserAgeGenderDistribution(HttpSession session) {
        Integer amid = getAmidFromSession(session);
        return userOpenfeignService.getUserAgeGenderDistribution(amid);
    }
    //统计用户数量
    @GetMapping("/count")
    JsonResult<Integer> count(HttpSession session){
        Integer amid = getAmidFromSession(session);
        return userOpenfeignService.count(amid);
    }
}
