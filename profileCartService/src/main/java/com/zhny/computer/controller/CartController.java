package com.zhny.computer.controller;

import com.zhny.computer.entity.Cart;
import com.zhny.computer.service.CartService;
import com.zhny.computer.service.util.JsonResult;
import com.zhny.computer.vo.CartVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

@RestController
@RequestMapping("carts")
public class CartController extends BaseController {
    @Autowired
    private CartService cartService;

    //添加商品进入配置车的接口
    @PostMapping("add_carts/{id}")
    public JsonResult<Void> addCarts(@RequestParam("uid") Integer uid, @RequestParam("username") String username,
                                     @PathVariable("id") Integer id) {
        cartService.addProfileCart(uid,username,id);
        return new JsonResult<Void>(SUCCESS);
    }
    //移除商品进入配置车的接口
    @DeleteMapping("delete_carts/{id}")
    public JsonResult<Void> deleteCarts(@RequestParam("uid") Integer uid,@PathVariable("id") Integer id) {
        cartService.deleteProfileCart(uid,id);
        return new JsonResult<>(SUCCESS);
    }
    //清空配置车的接口
    @DeleteMapping("clear_carts")
    public JsonResult<Void> clearCarts(@RequestParam("uid") Integer uid) {
        cartService.clearProfileCart(uid);
        return new JsonResult<>(SUCCESS);
    }
    //展示配置车的接口
    @GetMapping("show_carts")
    public JsonResult<CartVO> showCarts(@RequestParam("uid") Integer uid) {
        CartVO carts = cartService.showProfileCart(uid);
        return new JsonResult<>(SUCCESS, carts);
    }

}
