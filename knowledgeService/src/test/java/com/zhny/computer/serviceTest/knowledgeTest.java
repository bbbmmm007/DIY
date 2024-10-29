package com.zhny.computer.serviceTest;


import com.zhny.computer.service.KnowledgeService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest
@RunWith(SpringRunner.class)
public class knowledgeTest {

    @Autowired
    private KnowledgeService knowledgeService;
    @Test
    public void test(){
        knowledgeService.countKnowledge(null);
        System.out.println(knowledgeService.countKnowledge(null));
    }
    @Test
    public void test1(){
        knowledgeService.showKnowledge(1,10,1);
        System.out.println(knowledgeService.showKnowledge(1,10,1));
    }
}
