package com.zl.domain;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

/**
 * @author tzxx
 * @date 2018/11/29.
 */
@Entity
@Table(name="manager")
@Getter
@Setter
public class Manager {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private long id;
    private String name;
    private int age;
    private long companyId;
}
