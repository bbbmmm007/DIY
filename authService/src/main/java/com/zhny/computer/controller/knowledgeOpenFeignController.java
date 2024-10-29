package com.zhny.computer.controller;

import com.zhny.computer.DTO.KnowledgeDTO;
import com.zhny.computer.entity.Knowledge;
import com.zhny.computer.openfeign.KnowledgeOpenfeignService;
import com.zhny.computer.until.JsonResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.List;


@RestController
@RequestMapping("knowledge")
public class knowledgeOpenFeignController extends BaseController{
    @Autowired
    KnowledgeOpenfeignService knowledgeOpenfeignService;
    //增加词条
    @PostMapping("add_knowledge")
    JsonResult<Void> addKnowledge(HttpSession session, @RequestBody Knowledge knowledge){
        Integer amid = (Integer)session.getAttribute("amid");
        return knowledgeOpenfeignService.addKnowledge(amid,knowledge);
    }
    //删除词条
    @DeleteMapping("delete_knowledge/{knid}")
    JsonResult<Void> deleteKnowledge(HttpSession session,@PathVariable("knid") Integer knid){
        Integer amid = (Integer)session.getAttribute("amid");
        return knowledgeOpenfeignService.deleteKnowledge(amid,knid);
    }

    //修改词条
    @PutMapping("update_knowledge/{knid}")
    JsonResult<Void> updateKnowledge(HttpSession session,@RequestBody Knowledge knowledge,@PathVariable("knid") Integer knid){
        Integer amid = (Integer)session.getAttribute("amid");
        return knowledgeOpenfeignService.updateKnowledge(amid,knowledge,knid);
    }
    //展示词条的详细信息
    @PostMapping("show_detail_knowledge/{knid}")
    JsonResult<Knowledge> showDetailKnowledge(@PathVariable("knid") Integer knid){
        return knowledgeOpenfeignService.showDetailKnowledge(knid);
    }

    //分页展示板块词条
    @PostMapping("/show_knowledge/")
    JsonResult<List<Knowledge>> showKnowledge(@RequestBody KnowledgeDTO knowledgeDTO){
        return knowledgeOpenfeignService.showKnowledge(knowledgeDTO);
    }

    //统计该板块词条数量
    @PostMapping("/count_knowledge/")
    JsonResult<Integer> countKnowledge(@RequestBody KnowledgeDTO knowledgeDTO){
        return knowledgeOpenfeignService.countKnowledge(knowledgeDTO);
    }

    //查询所有词条
    @GetMapping("/show_all")
    JsonResult<List<Knowledge>> showAll(){
        return knowledgeOpenfeignService.showAll();
    }

}
