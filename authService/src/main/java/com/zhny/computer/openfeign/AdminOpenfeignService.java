package com.zhny.computer.openfeign;


import com.zhny.computer.DTO.AdminLoginDTO;
import com.zhny.computer.DTO.AdminUpPasswordDTO;
import com.zhny.computer.entity.Admin;
import com.zhny.computer.until.JsonResult;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;


@FeignClient(name = "adminService",path = "admin")
public interface AdminOpenfeignService {
    // 登录的映射
    @PostMapping("login")
    JsonResult<Admin> login(@RequestBody AdminLoginDTO adminLoginDTO);
    //管理员注册的接口
    @PostMapping("reg")
    JsonResult<Void> register(Admin admin);
    //注销管理员的接口
    @DeleteMapping("/delete_admin")
    public JsonResult<Void> deleteAdmin(@RequestParam("amid") Integer amid);
    //管理员更新密码的接口
    @PutMapping("/update")
    JsonResult<Void> updateAdmin(@RequestBody AdminUpPasswordDTO adminUpPasswordDTO,
                                 @RequestParam("amid") Integer amid,@RequestParam("adminName") String adminName);

}
