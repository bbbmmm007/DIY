package com.zhny.computer.controller;

import com.zhny.computer.DTO.AdminLoginDTO;
import com.zhny.computer.DTO.AdminUpPasswordDTO;
import com.zhny.computer.entity.Admin;
import com.zhny.computer.entity.Product;
import com.zhny.computer.entity.User;
import com.zhny.computer.vo.UserDistributionVO;
import com.zhny.computer.service.AdminService;
import com.zhny.computer.service.util.JsonResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("admin")
public class AdminController extends BaseController {
    @Autowired
    private AdminService adminService;

    // 登录的映射
    @PostMapping("login")
    public JsonResult<Admin> login(@RequestBody AdminLoginDTO adminLoginDTO, HttpSession session) {
        Admin data = adminService.login(adminLoginDTO);
        return new JsonResult<Admin>(SUCCESS, data);
    }

    //管理员注册的接口
    @PostMapping("reg")
    public JsonResult<Void> register(@RequestBody Admin admin) {
        adminService.register(admin);
        return new JsonResult<Void>(SUCCESS);
    }

    //注销管理员的接口
    @DeleteMapping("/delete_admin")
    public JsonResult<Void> deleteAdmin(@RequestParam("amid") Integer amid) {
        adminService.deleteAdmin(amid);
        return new JsonResult<Void>(SUCCESS);
    }
    //管理员更新密码的接口
    @PutMapping("/update")
    public JsonResult<Void> updateAdmin(@RequestBody AdminUpPasswordDTO adminUpPasswordDTO,
                                        @RequestParam("amid") Integer amid,@RequestParam("adminName") String adminName) {
        adminService.updateAdmin(amid,adminName,adminUpPasswordDTO);
        return new JsonResult<Void>(SUCCESS);
    }

}


