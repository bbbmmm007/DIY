package com.zhny.computer.openfeign;

import com.zhny.computer.entity.Product;
import com.zhny.computer.until.JsonResult;
import com.zhny.computer.vo.ProductVO;
import com.zhny.computer.vo.ProfileCreateVO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@FeignClient(name = "productService",path = "products")
public interface ProductfeignService {
    // 统计商品数量
    @PostMapping("/count")
    JsonResult<Integer> count(@RequestBody ProductVO productVO);


    // 商品分页查询
    @PostMapping("/show")
    JsonResult<List<Product>> show(@RequestBody ProductVO productVO,@RequestParam("pageSize") Integer pageSize,
                                          @RequestParam("pageNumber") Integer pageNumber);
    // 商品删除
    @DeleteMapping("/delete/{id}")
    JsonResult<Void> delete(@RequestParam("amid") Integer amid,@PathVariable("id") Integer id);

    // 商品修改
    @PutMapping("/change/{id}")
    JsonResult<Void> change(@RequestParam("amid") Integer amid,@PathVariable("id") Integer id, @RequestBody Product product);

    // 商品新增
    @PostMapping("/add")
    JsonResult<Void> add(@RequestParam("amid") Integer amid,@RequestBody Product product);

    //商品数据回显
    @PostMapping("/show_details/{id}")
    JsonResult<Product> showProduct(@PathVariable("id") Integer id);
    // 定制化生成配置
    @PostMapping("/auto_createProfileBySelect")
    JsonResult<Map<String, Object>> autoCreateProfileBySelect(@RequestBody ProfileCreateVO request);

}
