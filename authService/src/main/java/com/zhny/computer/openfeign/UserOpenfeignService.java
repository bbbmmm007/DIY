package com.zhny.computer.openfeign;


import com.zhny.computer.DTO.*;
import com.zhny.computer.entity.User;
import com.zhny.computer.until.JsonResult;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;


import java.util.List;


@FeignClient(name = "userService",path = "/users")
public interface UserOpenfeignService {
    //注册的映射
    @PostMapping("reg")
    JsonResult<Void> reg(@RequestBody User user);
    // 登录的映射
    @PostMapping("login")
    JsonResult<User> login(@RequestBody UserLoginDTO userLoginDTO);
    //更改密码的映射
    @PutMapping("change_password")
    JsonResult<Void> changePassword(@RequestBody UserUpPasswordDTO userUpPasswordDTO,@RequestParam("uid") Integer uid);
    //查询显示个人信息的映射(数据回显)
    @GetMapping("get_by_uid")
    JsonResult<User> getByUid(@RequestParam("uid") Integer uid);
    //更改个人信息的映射
    @PutMapping("change_info")
    JsonResult<Void> changeInfo(@RequestBody UserUpInfoDTO userUpInfoDTO,@RequestParam("uid") Integer uid);
    //统计用户数量
    @GetMapping("/count")
    JsonResult<Integer> count(@RequestParam("amid") Integer amid);
    //分页显示用户
    @PostMapping("/show/{pageNumber}/{pageSize}")
    JsonResult<List<User>> show(@PathVariable("pageNumber") Integer pageNumber,
                                @PathVariable("pageSize") Integer pageSize,
                                @RequestParam("amid") Integer amid);
    @GetMapping("/show_user")
    JsonResult<List<UserDistributionDTO>> getUserAgeGenderDistribution(@RequestParam("amid") Integer amid);
    //删除用户
    @DeleteMapping("/delete/{uid}")
    JsonResult<Void> delete(@RequestParam("amid") Integer amid,@PathVariable("uid") Integer uid);
    //用户注销
    @DeleteMapping("/delete_long")
    JsonResult<Void> deleteLong(@RequestParam("username") String username,@RequestParam("uid") Integer uid);
}
