package com.zl.repository;

import com.zl.domain.Company;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.io.Serializable;

/**
 * @author tzxx
 * @date 2018/11/29.
 */
@Repository
public interface CompanyRepository extends CrudRepository<Company,Serializable> {
}
