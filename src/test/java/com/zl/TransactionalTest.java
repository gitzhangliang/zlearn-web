package com.zl;
import com.zl.domain.Coder;
import com.zl.domain.Company;
import com.zl.domain.Manager;
import com.zl.repository.CoderRepository;
import com.zl.repository.CompanyRepository;
import com.zl.repository.ManagerRepository;
import com.zl.service.ICompanyService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ParameterizedPreparedStatementSetter;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.StopWatch;

import javax.annotation.Resource;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = WebApplication.class)
public class TransactionalTest {

	@Resource
	private ICompanyService companyService;
	@Resource
	private CoderRepository coderRepository;
	@Resource
	private CompanyRepository companyRepository;
	@Resource
	private ManagerRepository managerRepository;
	@Resource
	private JdbcTemplate jdbcTemplate;

	/**前提是：该service类没有@Transactional被修饰
	 在一个Service内部，事务方法之间的嵌套调用，普通方法和事务方法之间的嵌套调用，都不会开启新的事务！
	 为什么会这样呢？
	 动态代理最终都是要调用原始对象的，而原始对象在去调用方法时，是不会再触发代理了！
	 */
	@Test
	public void test1() {
		companyService.save();
	}

	/**事务的传播行为表示整个事务处理过程【所跨越的业务对象】，将以什么样的行为参与事务
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

	@Test
	public void test4() {

		List<Coder> coders = new ArrayList<>(50000);
		Coder coder;
		for (int i = 0;i<50000;i++){
			coder = new Coder();
			coder.setAge(i);
			coder.setName("序员"+i);
			coder.setCompanyId(i);
			coders.add(coder);
		}

		StopWatch stopWatch = new StopWatch();
		stopWatch.start();
		for (Coder coder1 : coders) {
			coderRepository.save(coder1);
		}
		stopWatch.stop();
		System.out.println(stopWatch.getTotalTimeMillis());
	}

	@Test
	public void test5() {

		final List<Manager> managers = new ArrayList<>(50000);
		Manager manager;
		for (int i = 0;i<50000;i++){
			manager = new Manager();
			manager.setAge(i);
			manager.setName("领导"+i);
			manager.setCompanyId(i);
			managers.add(manager);
		}

		StopWatch stopWatch = new StopWatch();
		stopWatch.start();
		jdbcTemplate.batchUpdate(
				"insert into manager (age, company_id, name) values (?, ?, ?)",
				new BatchPreparedStatementSetter() {
					public void setValues(PreparedStatement ps, int i) throws SQLException {
						ps.setInt(1, managers.get(i).getAge());
						ps.setLong(2, managers.get(i).getCompanyId());
						ps.setString(3, managers.get(i).getName());
					}

					public int getBatchSize() {
						return managers.size();
					}
				});
		stopWatch.stop();
		System.out.println(stopWatch.getTotalTimeMillis());

	}

	@Test
	public void test6() {

		final List<Company> companies = new ArrayList<>(50000);
		Company company;
		for (int i = 0;i<50000;i++){
			company = new Company();
			company.setName("公司"+i);
			companies.add(company);
		}

		StopWatch stopWatch = new StopWatch();
		stopWatch.start();
		for (Company company1 : companies) {
			jdbcTemplate.update("insert into company (name) values (?)", new PreparedStatementSetter() {
				@Override
				public void setValues(PreparedStatement ps) throws SQLException {
					ps.setString(1, company1.getName());
				}
			});
		}
		stopWatch.stop();
		System.out.println(stopWatch.getTotalTimeMillis());

	}
}