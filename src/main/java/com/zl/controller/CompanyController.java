package com.zl.controller;

import com.zl.service.ICompanyService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author tzxx
 * @date 2018/12/3.
 */
@RestController
@RequestMapping("/company")
public class CompanyController {

    @Resource
    private ICompanyService companyService;

    @GetMapping("/save")
    public Object  save(){
        companyService.save();
        return true;
    }
}
