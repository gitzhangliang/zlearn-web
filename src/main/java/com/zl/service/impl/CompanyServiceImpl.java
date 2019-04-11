package com.zl.service.impl;

import com.zl.domain.Company;
import com.zl.domain.Manager;
import com.zl.repository.CompanyRepository;
import com.zl.repository.ManagerRepository;
import com.zl.service.ICompanyService;
import com.zl.service.IManagerService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.transaction.support.TransactionSynchronizationManager;


import javax.annotation.Resource;

/**
 * @author tzxx
 * @date 2018/11/29.
 */
@Service
public class CompanyServiceImpl implements ICompanyService {
    @Resource
    private CompanyRepository companyRepository;
    @Resource
    private ManagerRepository managerRepository;
    @Resource
    private IManagerService managerService;

    @Override
    public Company get(long id){
        return companyRepository.findById(id).get();
    }

    /**
     * 见TransactionalTest中test1方法
     */
    @Override
    public void save(){
        Company company = new Company();
        company.setName("大公司");
        companyRepository.save(company);
        saveManager(company);
    }
    @Transactional(rollbackFor = RuntimeException.class)
    public void saveManager(Company company){
        System.out.println(TransactionSynchronizationManager.isActualTransactionActive()+"--------");
        //成功(普通方法调用事务方法，不会开启新事务)
        saveManager1(company);
        //失败
        saveManager2(company);
    }
    public void saveManager1(Company company){
        //值为false 普通方法调用事务方法，不会开启新事务,因此此方法并未在一个真实的事务中
        System.out.println(TransactionSynchronizationManager.isActualTransactionActive()+"--------1");
        Manager manager  = new Manager();
        manager.setAge(1);
        manager.setName("上级领导1");
        manager.setCompanyId(company.getId());
        managerRepository.save(manager);
    }

    public void saveManager2(Company company){
        System.out.println(TransactionSynchronizationManager.isActualTransactionActive()+"--------2");
        Manager manager  = new Manager();
        manager.setAge(2);
        manager.setName("上级领导2");
        manager.setCompanyId(company.getId());
        int i = 0/0;
        managerRepository.save(manager);
    }


    /**
     * 见TransactionalTest中test2方法
     */
    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public void saveForCheckPropagate(){
        Company company = new Company();
        company.setName("小公司");
        companyRepository.save(company);
        managerService.save(company);
    }

    /**
     * 见TransactionalTest中test3方法
     */
    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public void saveForCheckRollback(){
        Company company = new Company();
        company.setName("小微公司");
        companyRepository.save(company);
        try {
            saveManager3(company);
        } catch (Exception e) {
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
        }
    }

    public void saveManager3(Company company) throws Exception{
        System.out.println(TransactionSynchronizationManager.isActualTransactionActive()+"--------2");
        Manager manager  = new Manager();
        manager.setAge(3);
        manager.setName("上级领导3");
        manager.setCompanyId(company.getId());
        managerRepository.save(manager);
        int i = 0/0;
    }
}
