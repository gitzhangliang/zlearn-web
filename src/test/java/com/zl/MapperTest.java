package com.zl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.zl.domain.Coder;
import com.zl.mapper.CoderMapper;
import org.apache.ibatis.jdbc.SQL;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author tzxx
 * @date 2019/5/30.
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = WebApplication.class)
public class MapperTest {
    @Resource
    private CoderMapper coderMapper;

    @Test
    public void testSelect() {
        System.out.println(("----- selectAll method test ------"));
        List<Coder> userList = coderMapper.selectList(null);
        userList.forEach(System.out::println);
    }

    @Test
    public void testSelect1() {
        Coder coder = new Coder();
        coder.setName("序员0");
        coder.setAge(1);
        coder.setCompanyId(50999);
        QueryWrapper<Coder> wrapper = new QueryWrapper<>();
        wrapper.lambda().eq(Coder::getName,coder.getName());
        List<Coder> userList = coderMapper.selectList(wrapper);
        userList.forEach(System.out::println);

    }


    @Test
    public void testInsert() {
        Coder coder = new Coder();
        coder.setName("mybatis");
        coder.setAge(1);
        coder.setCompanyId(50999);
        int count = coderMapper.insert(coder);
        System.out.println(count);
        System.out.println(coder.getId());
    }

    @Test
    public void testAnnotationSelectForStringReplace() {
        Coder coder = coderMapper.findByColumn("id","100");
        Assert.assertEquals(100,coder.getId());
    }

    @Test
    public void testSqlConstruct() {
        System.out.println(sqlConstruct());
    }
    private static String sqlConstruct() {
        return new SQL() {{
            SELECT("P.ID, P.USERNAME, P.PASSWORD, P.FULL_NAME");
            SELECT("P.LAST_NAME, P.CREATED_ON, P.UPDATED_ON");
            FROM("PERSON P");
            FROM("ACCOUNT A");
            INNER_JOIN("DEPARTMENT D on D.ID = P.DEPARTMENT_ID");
            INNER_JOIN("COMPANY C on D.COMPANY_ID = C.ID");
            WHERE("P.ID = A.ID");
            WHERE("P.FIRST_NAME like ?");
            OR();
            WHERE("P.LAST_NAME like ?");
            GROUP_BY("P.ID");
            HAVING("P.LAST_NAME like ?");
            OR();
            HAVING("P.FIRST_NAME like ?");
            ORDER_BY("P.ID");
            ORDER_BY("P.FULL_NAME");
        }}.toString();
    }
}
