package com.zhny.computer.service;

import com.zhny.computer.entity.Knowledge;

import java.util.List;

public interface KnowledgeService {
    void addKnowledge(Integer amid,Knowledge knowledge);
    void deleteKnowledge(Integer amid,Integer knid);
    void updateKnowledge(Knowledge knowledge,Integer amid,Integer knid);
    Knowledge showKnowledgeDetail(Integer knid);
    List<Knowledge> showKnowledge(Integer ancestorId,Integer pageSize,Integer pageNumber);
    Integer countKnowledge(Integer ancestorId);
    List<Knowledge> showAll();
}
