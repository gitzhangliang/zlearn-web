package com.zl.model.datafilter;

import lombok.Getter;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author tzxx
 */
public class SqlComponent {
	@Getter
	private List<Object> params = new ArrayList<>();
	/**
	 * 原始sql
	 */
	@Getter
	private StringBuilder originalBd = new StringBuilder();

	private StringBuilder sqlBd = new StringBuilder();
		
	private boolean where = false;
	
	private boolean orderBy = false;

	private boolean paged = false;

	private boolean select = false;

	private StringBuilder orderByStr = new StringBuilder();

	private StringBuilder limitStr = new StringBuilder();
	
	public SqlComponent() {}
	
	public SqlComponent(String sql) {
		select = true;
		sqlBd.append(sql);
		originalBd.append(sql);
	}
	
	public StringBuilder getSqlBf() {
		if(where){
			sqlBd = new StringBuilder(sqlBd.toString().replaceAll(" 1=1 and", ""));
		}
		return sqlBd;
	}
	
	public String getSql(){
		return getSqlBf().toString();
	}
	
	/**
	 * @param needJoinWhere
	 * @return
	 */
	public SqlComponent where(boolean needJoinWhere) {
		if(needJoinWhere){
			sqlBd.append(" where 1=1");
			where = true;
		}
		return this;
	}
	
	/**拼接等于
	 * @param column
	 * @param value
	 * @return
	 */
	public SqlComponent equals(String column, Object value) {
		if(value != null && !"".equals(value)){
			sqlBd.append(" and "+column+" = ?");
			params.add(value);
		}
		return this;
	}
	
	/**不等于
	 * @param column
	 * @param value
	 * @return
	 */
	public SqlComponent notEquals(String column, Object value) {
		if(value != null && !"".equals(value)){
			sqlBd.append(" and "+column+" != ?");
			params.add(value);
		}
		return this;
	}
	
	/**为空
	 * @param column
	 * @return
	 */
	public SqlComponent isNull(String column) {
		sqlBd.append(" and "+column+" is Null ");
		return this;
	}


	/**拼接模糊条件
	 * @param column
	 * @param value
	 * @return
	 */
	public SqlComponent like(String column, Object value) {
		if(value != null && !"".equals(value)){
			sqlBd.append(" and "+  column+" like ?");
			params.add("%"+value+"%");
		}
		return this;
	}

	/**拼接大于等于
	 * @param column
	 * @param value
	 * @return
	 */
	public SqlComponent ge(String column, Object value) {
		if(value != null && !"".equals(value)){
			sqlBd.append(" and "+column+" >= ?");
			params.add(value);
		}
		return this;
	}
	/**拼接大于
	 * @param column
	 * @param value
	 * @return
	 */
	public SqlComponent gt(String column, Object value) {
		if(value != null && !"".equals(value)){
			sqlBd.append(" and "+column+" > ?");
			params.add(value);
		}
		return this;
	}
	/**拼接小于等于
	 * @param column
	 * @param value
	 * @return
	 */
	public SqlComponent le(String column, Object value) {
		if(value != null && !"".equals(value)){
			sqlBd.append(" and "+column+" <= ?");
			params.add(value);
		}
		return this;
	}
	/**拼接小于
	 * @param column
	 * @param value
	 * @return
	 */
	public SqlComponent lt(String column, Object value) {
		if(value != null && !"".equals(value)){
			sqlBd.append(" and "+column+" < ?");
			params.add(value);
		}
		return this;
	}
	/**拼接in条件语句
	 * @param column
	 * @param values
	 * @return
	 */
	public <T> SqlComponent in(String column, Collection<T> values) {
		if(values != null && values.size()>0){
			sqlBd.append(" and "+column+" in("+handleIn(values)+")");
			params.addAll(values);
		}
		return this;
	}
	private <T> String handleIn(Collection<T> list){
		StringBuilder builder = new StringBuilder();
		for (T obj : list) {
			builder.append("?,");
		}
		String str = builder.toString();
		return str.substring(0,str.length()-1);
	}
	
	/**拼接关键字查询
	 * @param value
	 * @param columns
	 * @return
	 */
	public SqlComponent keyword(Object value, String... columns) {
		if(value != null && !"".equals(value)){
			sqlBd.append(" and (");
			for (String column : columns) {
				sqlBd.append(column+" like ? or ");
				params.add("%"+value+"%");
			}
			int index = sqlBd.lastIndexOf("or");
			sqlBd = new StringBuilder(sqlBd.substring(0, index-1));
			sqlBd.append(")");
		}
		return this;
	}
	
	/**同keywordEnd配合使用，不仅可以达到keyword方法的效果，而且还可以配合其它方法使得查询更丰富
	 * 例：关键字查询包含日期，可以调用keywordLikeForDate方法实现日期模糊查询
	 * @return
	 */
	public SqlComponent keywordStart() {
		sqlBd.append(" and (");
		return this;
	}
	/**关键字拼接模糊条件
	 * @param column
	 * @param value
	 * @return
	 */
	public SqlComponent keywordLike(String column, Object value) {
		if(value != null && !"".equals(value)){
			sqlBd.append(column+" like ? or ");
			params.add("%"+value+"%");
		}
		return this;
	}

	/**关键字拼接in条件
	 * @param column
	 * @param values
	 * @return
	 */
	public SqlComponent keywordIn(String column, List<Object> values) {
		if(values != null && values.size()>0){
			sqlBd.append(column+" in("+handleIn(values)+") or");
			params.addAll(values);
		}
		return this;
	}

    /**关键字拼接regex条件
     * @param column
     * @param regexp
     * @return
     */
    public SqlComponent keywordRegex(String column, String regexp) {
        if(regexp != null && !"".equals(regexp)){
            sqlBd.append(column+" REGEXP  ? or ");
            params.add(regexp);
        }
        return this;
    }
	
	/**针对类型为date的属性拼接模糊条件
	 * @param column
	 * @param value
	 * @return
	 */
	public SqlComponent keywordLikeForDate(String column, Object value) {
		if(value != null && !"".equals(value)){
			if(isChineseIncluded(value.toString())){
				return this;
			}
			sqlBd.append(column+" like ? or ");
			params.add("%"+value+"%");
		}
		return this;
	}
	
	/**字符串是否含有汉字
	 * @param data
	 * @return
	 */
	public  boolean isChineseIncluded(String data) {
		String regEx = "[\\u4e00-\\u9fa5]";
		Pattern p = Pattern.compile(regEx);
		Matcher m = p.matcher(data);
		while (m.find()) {
			return true;
		}
		return false;
	}
	
	public SqlComponent keywordEnd() {
		int index = sqlBd.lastIndexOf("or");
		sqlBd = new StringBuilder(sqlBd.substring(0, index).trim());
		sqlBd.append(")");
		return this;
	}
	/**排序，可多次调用
	 * @param column
	 * @param order
	 * @return
	 */
	public SqlComponent orderBy(String column, String order){
		if(orderBy){
			orderByStr.append(" , "+column+" "+order);
			sqlBd.append(" , "+column+" "+order);
		}else{
			orderByStr.append(" order by "+column+" "+order);
			sqlBd.append(" order by "+column+" "+order);
			orderBy = true;
		}
		return this;
	}

	/**分页，调用此分页时，需要调用sqlDao中带有分页（既带有Page参数）的查询
	 * @return
	 */
	public SqlComponent limit(){
		sqlBd.append(" limit ? ,?");
		limitStr.append(" limit ? ,?");
		return this;
	}
	/**分页，调用此分页时，就不需要调用sqlDao中带有分页的查询
	 * @param page
	 * @return
	 */
	public SqlComponent limit(Page page){
		paged = true;
		sqlBd.append(" limit ? ,?");
		limitStr.append(" limit ? ,?");
		params.add((page.getPage()-1)*(page.getSize()));
		params.add(page.getSize());
		return this;
	}
	
	/**分组
	 * @param column
	 * @return
	 */
	public SqlComponent groupBy(String column){
		sqlBd.append(" group by "+column);
		return this;
	}
	
	/**添加其它条件语句
	 * @param condition
	 * @return
	 */
	public SqlComponent addOtherCondition(String condition){
		sqlBd.append(condition);
		return this;
	}
	
	/**正则
	 * @param column
	 * @param regexp
	 * @return
	 */
	public SqlComponent regex(String column,String regexp){
		if(regexp != null && !"".equals(regexp)){
			sqlBd.append(" and "+column+" REGEXP  ?");
			params.add(regexp);
		}
		return this;
	}

	/**
	 *
	 * @return
	 */
	public SqlComponent count(){
		StringBuilder countSql =  new StringBuilder("select count(*) ");
		String dataSql = getSql();
		if(limitStr.toString().length() > 1){
			dataSql = dataSql.replace(limitStr.toString(),"");
			if(paged){
				params = params.subList(0,params.size()-2);
			}
		}
		String from = from(dataSql);
		countSql.append(from);
		if(orderByStr.toString().length() > 1){
			countSql = new StringBuilder(countSql.toString().replaceAll(orderByStr.toString(),""));
		}
		sqlBd = countSql;
		return this;
	}

	private String from(String sql){
		if(select){
			int fromIndex = sql.indexOf("from");
			return sql.substring(fromIndex);
		}else{
			return  sql;
		}
	}

	/**拼接unionAll
	 * @param sql
	 * @return
	 */
	public SqlComponent unionAll(String sql) {
		sqlBd.append(" union all "+sql);
		return this;
	}

	/**拼接unionAll
	 * @param str
	 * @return
	 */
	public SqlComponent unionAllAsTable(String str) {
		sqlBd.append(str);
		return this;
	}
}
