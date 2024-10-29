package com.zhny.computer.controller;


import com.zhny.computer.DTO.KnowledgeDTO;
import com.zhny.computer.entity.Knowledge;
import com.zhny.computer.service.KnowledgeService;
import com.zhny.computer.service.util.JsonResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


import java.util.List;

@RestController
@RequestMapping("knowledge")
public class KnowledgeController extends BaseController{

    @Autowired
    private KnowledgeService knowledgeService;
    //增加词条
    @PostMapping("add_knowledge")
    public JsonResult<Void> addKnowledge(@RequestParam("amid") Integer amid,@RequestBody Knowledge knowledge){
        knowledgeService.addKnowledge(amid,knowledge);
        return new JsonResult<>(SUCCESS);
    }
    //删除词条
    @DeleteMapping("delete_knowledge/{knid}")
    public JsonResult<Void> deleteKnowledge(@RequestParam("amid") Integer amid,@PathVariable("knid") Integer knid){
        knowledgeService.deleteKnowledge(amid,knid);
        return new JsonResult<>(SUCCESS);
    }
    //修改词条
    @PutMapping("update_knowledge/{knid}")
    public JsonResult<Void> updateKnowledge(@RequestParam("amid") Integer amid,@RequestBody Knowledge knowledge,@PathVariable("knid") Integer knid){
        knowledgeService.updateKnowledge(knowledge,amid,knid);
        return new JsonResult<>(SUCCESS);
    }
    //展示词条的详细信息
    @PostMapping("show_detail_knowledge/{knid}")
    public JsonResult<Knowledge> showDetailKnowledge(@PathVariable("knid") Integer knid){
        Knowledge knowledge = knowledgeService.showKnowledgeDetail(knid);
        return new JsonResult<>(SUCCESS,knowledge);
    }

    //分页展示板块词条
    @PostMapping("/show_knowledge/")
    public JsonResult<List<Knowledge>> showKnowledge(@RequestBody KnowledgeDTO knowledgeDTO){
        Integer pageNumber = knowledgeDTO.getPageNumber();
        Integer pageSize = knowledgeDTO.getPageSize();
        Integer ancestorId = knowledgeDTO.getAncestorId();
        List<Knowledge> knowledgeList = knowledgeService.showKnowledge(ancestorId,pageNumber,pageSize);
        return new JsonResult<>(SUCCESS,knowledgeList);
    }
    //统计该板块词条数量
    @PostMapping("/count_knowledge/")
    public JsonResult<Integer> countKnowledge(@RequestBody KnowledgeDTO knowledgeDTO){
        Integer ancestorId = knowledgeDTO.getAncestorId();
        Integer countKnowledge = knowledgeService.countKnowledge(ancestorId);
        return new JsonResult<>(SUCCESS,countKnowledge);
    }
    //查询所有词条
    @GetMapping("/show_all")
    public JsonResult<List<Knowledge>> showAll(){
        List<Knowledge> knowledgeList = knowledgeService.showAll();
        return new JsonResult<>(SUCCESS,knowledgeList);
    }

}
