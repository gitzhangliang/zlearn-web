package com.zl.domain;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

/**
 * @author tzxx
 * @date 2018/11/29.
 */
@Entity
@Table(name="company")
@Getter
@Setter
public class Company {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private long id;
    private String name;
}
