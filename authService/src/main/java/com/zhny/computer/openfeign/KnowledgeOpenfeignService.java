package com.zhny.computer.openfeign;


import com.zhny.computer.DTO.KnowledgeDTO;
import com.zhny.computer.entity.Knowledge;
import com.zhny.computer.until.JsonResult;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(name = "knowledgeService",path = "knowledge")
public interface KnowledgeOpenfeignService {
    //增加词条
    @PostMapping("add_knowledge")
    JsonResult<Void> addKnowledge(@RequestParam("amid") Integer amid, @RequestBody Knowledge knowledge);
    //删除词条
    @DeleteMapping("delete_knowledge/{knid}")
    JsonResult<Void> deleteKnowledge(@RequestParam("amid") Integer amid,@PathVariable("knid") Integer knid);

    //修改词条
    @PutMapping("update_knowledge/{knid}")
    JsonResult<Void> updateKnowledge(@RequestParam("amid") Integer amid,@RequestBody Knowledge knowledge,@PathVariable("knid") Integer knid);
    //展示词条的详细信息
    @PostMapping("show_detail_knowledge/{knid}")
    JsonResult<Knowledge> showDetailKnowledge(@PathVariable("knid") Integer knid);


    //分页展示板块词条
    @PostMapping("/show_knowledge/")
    JsonResult<List<Knowledge>> showKnowledge(@RequestBody KnowledgeDTO knowledgeDTO);

    //统计该板块词条数量
    @PostMapping("/count_knowledge/")
    JsonResult<Integer> countKnowledge(@RequestBody KnowledgeDTO knowledgeDTO);

    //查询所有词条
    @GetMapping("show_all")
    JsonResult<List<Knowledge>> showAll();


}
