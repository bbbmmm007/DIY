package com.zhny.computer.controller;

import com.zhny.computer.entity.Product;
import com.zhny.computer.openfeign.ProductfeignService;
import com.zhny.computer.until.JsonResult;
import com.zhny.computer.vo.ProductVO;
import com.zhny.computer.vo.ProfileCreateVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("products")
public class ProductOpenFeiController extends BaseController {

    @Autowired
    ProductfeignService productfeignService;
    // 统计商品数量
    @PostMapping("/count")
    JsonResult<Integer> count(@RequestBody ProductVO productVO){
        return productfeignService.count(productVO);
    }
    // 商品分页查询
    @PostMapping("/show")
    JsonResult<List<Product>> show(@RequestBody ProductVO productVO,@RequestParam("pageSize") Integer pageSize,
                                   @RequestParam("pageNumber") Integer pageNumber){
        return productfeignService.show(productVO, pageSize, pageNumber);
    }
    //商品数据回显
    @PostMapping("show_details/{id}")
    JsonResult<Product> showDetails(@PathVariable("id") Integer id){
        return productfeignService.showProduct(id);
    }
    // 定制化生成配置
    @PostMapping("/auto_createProfileBySelect")
    JsonResult<Map<String, Object>> autoCreateProfileBySelect(@RequestBody ProfileCreateVO request){
        System.out.println(request);
        return productfeignService.autoCreateProfileBySelect(request);
    }

}
