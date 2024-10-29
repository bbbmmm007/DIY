package com.zhny.computer.mapper;

import com.zhny.computer.entity.Knowledge;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

public interface KnowledgeMapper {
    List<Knowledge> showKnowledge(@Param("ancestorId") Integer ancestorId);

    Integer addKnowledge(Knowledge knowledge);

    Integer updateKnowledge(
            @Param("topic") String topic,
            @Param("content") String content,
            @Param("modifiedUser") String modifiedUser,
            @Param("modifiedTime") Date modifiedTime,
            @Param("knid") Integer knid
    );

    List<Knowledge> showAll();

    Integer deleteKnowledge(@Param("knid") Integer knid);

    String showAnName(@Param("ancestorId") Integer ancestorId);

    Knowledge showKnowledgeDetail(@Param("knid") Integer knid);

    List<Knowledge> showKnowledge(@Param("ancestorId") Integer ancestorId, @Param("pageSize") Integer pageSize, @Param("offset") Integer offset);

    Integer countKnowledge(@Param("ancestorId") Integer ancestorId);



}
