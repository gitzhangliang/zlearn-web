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
        LambdaQuerySqlBuilder builder = new LambdaQuerySqlBuilder();
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
                .orderByAsc(Company::getId, Company::getName)
                .orderByAsc(Coder::getName)
                .orderByDesc(Coder::getId, Coder::getAge)
                .groupBy(Company::getId, Company::getName)
                .groupBy(Coder::getName)
                .sql());

        StringQuerySqlBuilder builder1 = new StringQuerySqlBuilder();
        System.out.println(builder1
                .select("c.name,c.age,co.name as id")
                .from("coder c")
                .leftJoin("company.co")
                .on("c.company_id","co.id")
                .where()
                .eq("c.name", "123")
                .and(v -> v.le("c.name", "123").lt("c.name", "123"))
                .or(v -> v.ge("c.name", "123").or().gt("c.name", "123"))
                .like("c.name", "12")
                .isNotNull("c.name")
                .isNull("c.name")
                .between("c.id",1,2)
                .in("c.id", Arrays.asList(1L,2L))
                .orderByAsc("co.id", "co.name", "c.name")
                .orderByDesc("c.id", "c.age")
                .groupBy("co.id", "co.name", "c.name")
                .sql());
        LambdaUpdateSqlBuilder builder2 = new LambdaUpdateSqlBuilder();
        System.out.println(builder2
                .update(Coder.class,"c")
                .set(Coder::getName,"123")
                .set(Coder::getAge,12)
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
                .sql());

        LambdaUpdateSqlBuilder builder3 = new LambdaUpdateSqlBuilder();
        System.out.println(builder3
                .update(Coder.class)
                .set(Coder::getName,"暂停处理")
                .where()
                .eq(Coder::getAge, 1)
                .eq(Coder::getName, "待盖章")
                .appendSql(" and DATEDIFF(NOW(),"+builder3.column((SqlFunction<Coder, ?>)Coder::getName)+")>30")
                .sqlAndReplacePlaceholder());

    }
}
