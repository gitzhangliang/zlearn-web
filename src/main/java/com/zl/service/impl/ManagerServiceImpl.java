package com.zl.service.impl;

import com.zl.domain.Company;
import com.zl.domain.Manager;
import com.zl.repository.ManagerRepository;
import com.zl.service.IManagerService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionSynchronizationManager;

import javax.annotation.Resource;

/**
 * @author tzxx
 * @date 2018/12/4.
 */
@Service
public class ManagerServiceImpl implements IManagerService {
    @Resource
    private ManagerRepository managerRepository;

    @Override
    @Transactional(rollbackFor = RuntimeException.class,propagation = Propagation.NOT_SUPPORTED)
    public void save(Company company) {
        System.out.println(TransactionSynchronizationManager.isActualTransactionActive()+"--------");
        //成功
        saveManager1(company);
        //失败
        saveManager2(company);
    }


    @Transactional(rollbackFor = RuntimeException.class,propagation = Propagation.REQUIRED)
    public void saveManager1(Company company){
        //值为false 普通方法调用事务方法，不会开启新事务,因此此方法并未在一个真实的事务中
        System.out.println(TransactionSynchronizationManager.isActualTransactionActive()+"--------1");
        Manager manager  = new Manager();
        manager.setAge(1);
        manager.setName("下级领导1");
        manager.setCompanyId(company.getId());
        managerRepository.save(manager);
    }

    public void saveManager2(Company company){
        System.out.println(TransactionSynchronizationManager.isActualTransactionActive()+"--------2");
        Manager manager  = new Manager();
        manager.setAge(2);
        manager.setName("下级领导2");
        manager.setCompanyId(company.getId());
        managerRepository.save(manager);
        int i = 0/0;
    }
}
