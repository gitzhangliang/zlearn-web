package com.zl.dao.impl;

import com.zl.dao.ISqlDao;
import com.zl.model.datafilter.Page;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * @author tzxx
 */
@Repository
public class SqlDaoImpl implements ISqlDao {
    
	@Resource
    private JdbcTemplate jdbcTemplate;

	@Override
	public <T> List<T> list(String sql,Class<T> c,Object... arr){
		RowMapper<T> rowMapper = new BeanPropertyRowMapper<>(c);
		return jdbcTemplate.query(sql, rowMapper,arr);
	}
	
	@Override
	public <T> T get(String sql,Class<T> c,Object... arr){
		RowMapper<T> rowMapper = new BeanPropertyRowMapper<>(c);
		return jdbcTemplate.queryForObject(sql, arr, rowMapper);
	}

	@Override
	public List<Map<String, Object>> mappingList(String sql, Object... arr) {
		return jdbcTemplate.queryForList(sql, arr);
	}
	
	@Override
	public <T> T count(String sql,Class<T> c, Object... arr) {
		return jdbcTemplate.queryForObject(sql, c, arr);
	}

	@Override
	public <T> List<T> listSingleColumn(String sql, Class<T> c, Object... arr) {
		return jdbcTemplate.queryForList(sql, c, arr);
	}
	@Override
	public <T> List<T> list(String sql, Class<T> c, Page page, Object... arr) {
		List<Object> params = Arrays.asList(arr);
		params = new ArrayList<>(params);
		if(page != null){
			params.add((page.getPage()-1)*(page.getSize()));
			params.add(page.getSize());
			arr = params.toArray();
		}
		RowMapper<T> rowMapper=new BeanPropertyRowMapper<>(c);
		return jdbcTemplate.query(sql, rowMapper,arr);
	}
}