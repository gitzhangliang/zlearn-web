package com.zl.model.sql;

import com.zl.domain.Coder;
import com.zl.domain.Company;

import java.util.Arrays;

/**
 * @author zhangliang
 * @date 2020/7/9.
 */
public class TestSqlBuilder {
    public static void main(String[] args) {
        LambdaSqlBuilder builder = new LambdaSqlBuilder();
        System.out.println(builder
                .select(Coder::getName,Coder::getAge)
                .selectAlias(Company::getName,Company::getId)
                .from(Coder.class,"c")
                .leftJoin(Company.class, "co")
                .on(Coder::getCompanyId, Company::getId)
                .where()
                .eq(Coder::getName, "123")
                .and(v -> v.le(Coder::getName, "123").lt(Coder::getName, "123"))
                .or(v -> v.ge(Coder::getName, "123").or().gt(Coder::getName, "123"))
                .or(v -> v.ne(Coder::getName, null))
                .like(Coder::getName, "12")
                .isNotNull(Coder::getName)
                .isNull(Coder::getName)
                .between(Coder::getId,1,2)
                .in(Coder::getId, Arrays.asList(1L,2L))
                .inSql(Coder::getId,"select id from coder")
                .orderByAsc(Company::getId, Company::getName)
                .orderByAsc(Coder::getName)
                .orderByDesc(Coder::getId, Coder::getAge)
                .groupBy(Company::getId, Company::getName)
                .groupBy(Coder::getName)
                .sql());

        StringSqlBuilder builder1 = new StringSqlBuilder();
        System.out.println(builder1
                .select("c.name,c.age,co.name as id")
                .from("coder c")
                .leftJoin("company.co")
                .on("c.company_id","co.id")
                .where()
                .eq("c.name", "123")
                .and(v -> v.le("c.name", "123").lt("c.name", "123"))
                .or(v -> v.ge("c.name", "123").or().gt("c.name", "123"))
                .or(v -> v.ne("c.name", null))
                .like("c.name", "12")
                .isNotNull("c.name")
                .isNull("c.name")
                .between("c.id",1,2)
                .in("c.id", Arrays.asList(1L,2L))
                .inSql("c.id","select id from coder")
                .orderBy("co.id, co.name, c.name, c.id desc, c.age desc")
                .groupBy("co.id, co.name, c.name")
                .sql());
    }
}
