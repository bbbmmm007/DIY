package com.zhny.computer.controller;

import com.zhny.computer.entity.Collection;
import com.zhny.computer.service.CollectionService;
import com.zhny.computer.service.util.JsonResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


import java.util.List;

@RestController
@RequestMapping("/collections")
public class CollectionController extends BaseController{
    @Autowired
    private CollectionService collectionService;
    //加入收藏
    @PostMapping("/add_collection/{id}")
    public JsonResult<Void> addCollection(@RequestParam("uid") Integer uid,@PathVariable("id") Integer id){
        collectionService.addCollection(uid,id);
        return new JsonResult<>(SUCCESS);
    }
    //移除收藏
    @DeleteMapping("/delete_collection/{id}")
    public JsonResult<Void> deleteCollection(@RequestParam("uid") Integer uid,@PathVariable("id") Integer id){
        collectionService.deleteCollection(uid,id);
        return new JsonResult<>(SUCCESS);
    }
    //展示收藏列表
    @GetMapping("/show_collection")
    public JsonResult<List<Collection>> showCollection(@RequestParam("uid") Integer uid){
        List<Collection> collections = collectionService.showCollection(uid);
        return new JsonResult<>(SUCCESS,collections);
    }
    //清空收藏列表
    @DeleteMapping("/clear_collection")
    public JsonResult<Void> clearCollection(@RequestParam("uid") Integer uid){
        collectionService.clearCollection(uid);
        return new JsonResult<>(SUCCESS);
    }
}
