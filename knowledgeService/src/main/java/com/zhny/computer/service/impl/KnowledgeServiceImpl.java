package com.zhny.computer.service.impl;

import com.zhny.computer.entity.Admin;
import com.zhny.computer.entity.Knowledge;
import com.zhny.computer.mapper.AdminMapper;
import com.zhny.computer.mapper.KnowledgeMapper;
import com.zhny.computer.service.KnowledgeService;
import com.zhny.computer.service.ex.DataDeleteException;
import com.zhny.computer.service.ex.DataSelectException;
import com.zhny.computer.service.ex.DataUpdateException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;



import java.util.Date;
import java.util.List;

@Service
public class KnowledgeServiceImpl implements KnowledgeService {
    @Autowired
    private KnowledgeMapper knowledgeMapper;
    @Autowired
    private AdminMapper adminMapper;
    @Override
    //新增新的词条
    public void addKnowledge(Integer amid,Knowledge knowledge) {
        Admin resultAdmin = adminMapper.findByAmid(amid);
        String name = resultAdmin.getAdminName();
        if (resultAdmin == null) {
            throw new DataSelectException("管理员数据不存在");
        }


        knowledge.setCreatedUser(name);
        knowledge.setCreatedTime(new Date());
        knowledge.setModifiedUser(name);
        knowledge.setModifiedTime(new Date());

        knowledgeMapper.addKnowledge(knowledge);

    }
    //删除词条
    @Override
    public void deleteKnowledge(Integer amid, Integer knid) {
        Admin resultAdmin = adminMapper.findByAmid(amid);
        if (resultAdmin == null) {
            throw new DataSelectException("管理员数据不存在");
        }
        Knowledge resultKnown=knowledgeMapper.showKnowledgeDetail(knid);
        if (resultKnown == null) {
            throw new DataSelectException("数据不存在");
        }
        Integer rows=knowledgeMapper.deleteKnowledge(knid);
        if (rows != 1) {
            throw new DataDeleteException("数据删除失败");
        }


    }

    //更新词条数据
    @Override
    public void updateKnowledge(Knowledge knowledge,Integer amid,Integer knid) {
        Admin resultAdmin = adminMapper.findByAmid(amid);
        if (resultAdmin == null) {
            throw new DataSelectException("管理员数据不存在");
        }
        Knowledge resultKnown=knowledgeMapper.showKnowledgeDetail(knid);
        if (resultKnown == null) {
            throw new DataSelectException("数据不存在");
        }

        knowledge.setAncestorId(resultKnown.getAncestorId());
        knowledge.setKnid(knid);
        knowledge.setAncestorName(resultKnown.getAncestorName());
        Date date = new Date();



        Integer rows = knowledgeMapper.updateKnowledge(knowledge.getTopic(),knowledge.getContent(),resultAdmin.getAdminName(),date,knid);
        if (rows!= 1){
            throw new DataUpdateException("数据更新失败");
        }


    }


    @Override
    public Knowledge showKnowledgeDetail(Integer knid) {
        return knowledgeMapper.showKnowledgeDetail(knid);
    }


    @Override
    public List<Knowledge> showKnowledge(Integer ancestorId, Integer pageSize, Integer pageNumber) {
        Integer offset = (pageNumber - 1) * pageSize;
        return knowledgeMapper.showKnowledge(ancestorId,offset,pageSize);
    }

    @Override
    public Integer countKnowledge(Integer ancestorId) {
        return knowledgeMapper.countKnowledge(ancestorId);
    }

    @Override
    public List<Knowledge> showAll() {
        return knowledgeMapper.showAll();
    }


}
