package com.zhny.computer.openfeign;


import com.zhny.computer.entity.Collection;
import com.zhny.computer.until.JsonResult;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(name = "collectionService",path = "collections")
public interface CollectionOpenfieService {
    //加入收藏
    @PostMapping("/add_collection/{id}")
    JsonResult<Void> addCollection(@RequestParam("uid") Integer uid, @PathVariable("id") Integer id);
    //移除收藏
    @DeleteMapping("/delete_collection/{id}")
    JsonResult<Void> deleteCollection(@RequestParam("uid") Integer uid, @PathVariable("id") Integer id);
    //展示收藏列表
    @GetMapping("/show_collection")
    JsonResult<List<Collection>> showCollection(@RequestParam("uid") Integer uid);
    //清空收藏列表
    @DeleteMapping("/clear_collection")
    JsonResult<Void> clearCollection(@RequestParam("uid") Integer uid);
}
