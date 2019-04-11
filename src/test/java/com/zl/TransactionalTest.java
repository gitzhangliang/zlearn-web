package com.zl;
import com.zl.service.ICompanyService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = WebApplication.class)
public class TransactionalTest {

	@Resource
	private ICompanyService companyService;

	/**前提是：该service类没有@Transactional被修饰
	 在一个Service内部，事务方法之间的嵌套调用，普通方法和事务方法之间的嵌套调用，都不会开启新的事务！
	 为什么会这样呢？
	 动态代理最终都是要调用原始对象的，而原始对象在去调用方法时，是不会再触发代理了！
	 */
	@Test
	public void test1() {
		companyService.save();
	}

	/**
	 *PROPAGATION_REQUIRED -- 支持当前事务，如果当前没有事务，就新建一个事务。这是最常见的选择。
	 PROPAGATION_SUPPORTS -- 支持当前事务，如果当前没有事务，就以非事务方式执行。
	 PROPAGATION_MANDATORY -- 支持当前事务，如果当前没有事务，就抛出异常。
	 PROPAGATION_REQUIRES_NEW -- 新建事务，如果当前存在事务，把当前事务挂起。
	 PROPAGATION_NOT_SUPPORTED -- 以非事务方式执行操作，如果当前存在事务，就把当前事务挂起。
	 PROPAGATION_NEVER -- 以非事务方式执行，如果当前存在事务，则抛出异常。
	 PROPAGATION_NESTED -- 如果当前存在事务，则在嵌套事务内执行。如果当前没有事务，则进行与PROPAGATION_REQUIRED类似的操作。
	 */
	@Test
	public void test2() {
		companyService.saveForCheckPropagate();
	}

	/**
	 * 回滚
	 */
	@Test
	public void test3() {
		companyService.saveForCheckRollback();
	}


}