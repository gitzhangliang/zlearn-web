package com.zl.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @author zl
 * @date 2018/11/29.
 */
@Entity
@Table(name="coder")
@Getter
@Setter
@ToString
public class Coder implements Serializable{

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private long id;
    private String name;
    private int age;
    private BigDecimal salary;
    private long companyId;
    private String mobile;
    private String password;
}
