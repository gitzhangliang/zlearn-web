package com.zl.dao;

import com.zl.model.datafilter.Page;

import java.util.List;
import java.util.Map;

/**
 * @author tzxx
 */
public interface ISqlDao {

	/**
	 * 查询表结构中数据,T中必须包含所查询字段属性
	 * @param sql
	 * @param c
	 * @param arr
	 * @return
	 */
	<T> List<T> list(String sql, Class<T> c, Object... arr);

	/**
	 * 查询表结构单行数据,T中必须包含所查询字段属性
	 * @param sql
	 * @param c
	 * @param arr
	 * @return
	 */
	<T> T get(String sql, Class<T> c, Object... arr);

	/**
	 * 将查询结果映射为map
	 * @param sql
	 * @param arr
	 * @return
	 */
	List<Map<String, Object>> mappingList(String sql, Object... arr);


	/**
	 * 查询总行数
	 * @param sql
	 * @param c
	 * @param arr
	 * @return
	 */
	<T> T count(String sql, Class<T> c, Object... arr);

	
	/**
	 * 查询表结构中单列数据集合
	 * @param sql
	 * @param c
	 * @param arr
	 * @return
	 */
	<T> List<T> listSingleColumn(String sql, Class<T> c, Object... arr);
	/**
	 * 分页查询,T中必须包含所查询字段属性
	 * @param sql
	 * @param c
	 * @param page
	 * @param arr
	 * @return
	 */
	<T> List<T> list(String sql, Class<T> c, Page page, Object... arr);

}
