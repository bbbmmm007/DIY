package com.zhny.computer.controller;

import com.zhny.computer.entity.Product;
import com.zhny.computer.service.ProductService;
import com.zhny.computer.service.util.JsonResult;
import com.zhny.computer.service.util.ProfileGenerator;
import com.zhny.computer.vo.ProductVO;
import com.zhny.computer.vo.ProfileCreateVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("products")
public class ProductController extends BaseController {
    @Autowired
    private ProductService productService;

    @Autowired
    private ProfileGenerator profileGenerator;

    // 统计商品数量
    @PostMapping("/count")
    public JsonResult<Integer> count(@RequestBody ProductVO productVO) {
        Integer count = productService.countProducts(productVO);
        return new JsonResult<>(SUCCESS, count);
    }

    // 商品分页查询
    @PostMapping("/show")
    public JsonResult<List<Product>> show(@RequestBody ProductVO productVO,
                                          @RequestParam Integer pageSize,
                                          @RequestParam Integer pageNumber) {
        List<Product> products = productService.findProducts(productVO, pageSize ,pageNumber);
        return new JsonResult<>(SUCCESS, products);
    }

    // 商品删除
    @DeleteMapping("/delete/{id}")
    public JsonResult<Void> delete(@RequestParam("amid") Integer amid,@PathVariable("id") Integer id) {
        productService.deleteProduct(amid,id);
        return new JsonResult<>(SUCCESS);
    }

    // 商品修改
    @PutMapping("/change/{id}")
    public JsonResult<Void> change(@RequestParam("amid") Integer amid,@PathVariable("id") Integer id, @RequestBody Product product) {
        productService.updateProduct(amid,id, product);
        return new JsonResult<>(SUCCESS);
    }

    // 商品新增
    @PostMapping("/add")
    public JsonResult<Void> add(@RequestParam("amid") Integer amid,@RequestBody Product product) {
        productService.insertProduct(amid,product);
        return new JsonResult<>(SUCCESS);
    }
    //商品数据回显
    @PostMapping("/show_details/{id}")
    public JsonResult<Product> showProduct(@PathVariable("id") Integer id) {
        Product product = productService.showProduct(id);
        return new JsonResult<>(SUCCESS,product);
    }

    // 定制化生成配置
    @PostMapping("/auto_createProfileBySelect")
    public JsonResult<Map<String, Object>> autoCreateProfileBySelect(@RequestBody ProfileCreateVO request) {
        Map<String, Object> config = profileGenerator.generateProfile(request);
        return new JsonResult<>(SUCCESS, config);
    }
}


