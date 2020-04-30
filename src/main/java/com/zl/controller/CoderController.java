package com.zl.controller;

import com.zl.domain.Coder;
import com.zl.service.ICoderService;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author zl
 * @date 2018/12/3.
 */
@RestController
@RequestMapping("/coder")
@Slf4j
public class CoderController {

    @Resource
    private ICoderService coderService;


    @GetMapping("/get/{id}")
    public Object get(@PathVariable long id){
        log.info("load...");
        return coderService.get(id);
    }

    @GetMapping("/getForKeyGenerator/{id}")
    public Object getForKeyGenerator(@PathVariable long id){
        log.info("load...");
        return coderService.getForKeyGenerator(id);
    }

    @GetMapping("/find/{id}")
    public Object find(@PathVariable long id){
        return coderService.find(id);
    }

    @GetMapping("/save")
    public Object save(){
        Coder coder = new Coder();
        coder.setName("name");
        coder.setAge(1);
        coder.setCompanyId(1);
        coderService.save(coder);
        return true;
    }
}
