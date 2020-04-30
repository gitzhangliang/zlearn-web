package com.zl.service;


import com.zl.domain.Company;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author zl
 * @date 2018/11/29.
 */
public interface ICompanyService {

    Company get(long id);

    void save();

    void saveForCheckPropagate();

    void saveForCheckRollback();
}
