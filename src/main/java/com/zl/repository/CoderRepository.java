package com.zl.repository;

import com.zl.domain.Coder;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.io.Serializable;

/**
 * @author tzxx
 * @date 2018/12/25.
 */
@Repository
public interface CoderRepository extends CrudRepository<Coder,Serializable> {
    Coder findByName(String name);
}
