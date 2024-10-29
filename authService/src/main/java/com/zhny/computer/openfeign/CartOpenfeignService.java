package com.zhny.computer.openfeign;



import com.zhny.computer.until.JsonResult;
import com.zhny.computer.vo.CartVO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

@FeignClient(name = "profileCartService",path = "carts")
public interface CartOpenfeignService {

    //添加商品进入配置车的接口
    @PostMapping("add_carts/{id}")
    JsonResult<Void> addCarts(@RequestParam("uid") Integer uid, @RequestParam("username") String username,
                                     @PathVariable("id") Integer id);
    //移除商品进入配置车的接口
    @DeleteMapping("delete_carts/{id}")
    JsonResult<Void> deleteCarts(@RequestParam("uid") Integer uid,@PathVariable("id") Integer id);

    //清空配置车的接口
    @DeleteMapping("clear_carts")
    JsonResult<Void> clearCarts(@RequestParam("uid") Integer uid);

    //展示配置车的接口
    @GetMapping("show_carts")
    JsonResult<CartVO> showCarts(@RequestParam("uid") Integer uid);

}
