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
	private StringBuffer originalBf = new StringBuffer();

	private StringBuffer sqlBf = new StringBuffer();
		
	private boolean where = false;
	
	private boolean orderBy = false;

	private boolean paged = false;

	private boolean select = false;

	private StringBuffer orderByStr = new StringBuffer();

	private StringBuffer limitStr = new StringBuffer();
	
	public SqlComponent() {}
	
	public SqlComponent(String sql) {
		select = true;
		sqlBf.append(sql);
		originalBf.append(sql);
	}
	
	public StringBuffer getSqlBf() {
		if(where){
			sqlBf = new StringBuffer(sqlBf.toString().replaceAll(" 1=1 and", ""));
		}
		return sqlBf;
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
			sqlBf.append(" where 1=1");
			where = true;
		}
		return this;
	}
	
	/**拼接等于
	 * @param column
	 * @param value
	 * @return
	 */
	public SqlComponent addEquals(String column, Object value) {
		if(value != null && !"".equals(value)){
			sqlBf.append(" and "+column+" = ?");
			params.add(value);
		}
		return this;
	}
	
	/**不等于
	 * @param column
	 * @param value
	 * @return
	 */
	public SqlComponent addNotEquals(String column, Object value) {
		if(value != null && !"".equals(value)){
			sqlBf.append(" and "+column+" != ?");
			params.add(value);
		}
		return this;
	}
	
	/**为空
	 * @param column
	 * @return
	 */
	public SqlComponent addIsNull(String column) {
		sqlBf.append(" and "+column+" is Null ");
		return this;
	}


	/**拼接模糊条件
	 * @param column
	 * @param value
	 * @return
	 */
	public SqlComponent addLike(String column, Object value) {
		if(value != null && !"".equals(value)){
			sqlBf.append(" and "+  column+" like ?");
			params.add("%"+value+"%");
		}
		return this;
	}

	/**拼接大于等于
	 * @param column
	 * @param value
	 * @return
	 */
	public SqlComponent addGe(String column, Object value) {
		if(value != null && !"".equals(value)){
			sqlBf.append(" and "+column+" >= ?");
			params.add(value);
		}
		return this;
	}
	/**拼接大于
	 * @param column
	 * @param value
	 * @return
	 */
	public SqlComponent addGt(String column, Object value) {
		if(value != null && !"".equals(value)){
			sqlBf.append(" and "+column+" > ?");
			params.add(value);
		}
		return this;
	}
	/**拼接小于等于
	 * @param column
	 * @param value
	 * @return
	 */
	public SqlComponent addLe(String column, Object value) {
		if(value != null && !"".equals(value)){
			sqlBf.append(" and "+column+" <= ?");
			params.add(value);
		}
		return this;
	}
	/**拼接小于
	 * @param column
	 * @param value
	 * @return
	 */
	public SqlComponent addLt(String column, Object value) {
		if(value != null && !"".equals(value)){
			sqlBf.append(" and "+column+" < ?");
			params.add(value);
		}
		return this;
	}
	/**拼接in条件语句
	 * @param column
	 * @param values
	 * @return
	 */
	public <T extends Object> SqlComponent addIn(String column, Collection<T> values) {
		if(values != null && values.size()>0){
			sqlBf.append(" and "+column+" in("+handleIn(values)+")");
			params.addAll(values);
		}
		return this;
	}
	private <T extends Object> String handleIn(Collection<T> list){
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
			sqlBf.append(" and (");
			for (String column : columns) {
				sqlBf.append(column+" like ? or ");
				params.add("%"+value+"%");
			}
			int index = sqlBf.lastIndexOf("or");
			sqlBf = new StringBuffer(sqlBf.substring(0, index-1));
			sqlBf.append(")");
		}
		return this;
	}
	
	/**同keywordEnd配合使用，不仅可以达到keyword方法的效果，而且还可以配合其它方法使得查询更丰富
	 * 例：关键字查询包含日期，可以调用keywordLikeForDate方法实现日期模糊查询
	 * @return
	 */
	public SqlComponent keywordStart() {
		sqlBf.append(" and (");
		return this;
	}
	/**关键字拼接模糊条件
	 * @param column
	 * @param value
	 * @return
	 */
	public SqlComponent keywordLike(String column, Object value) {
		if(value != null && !"".equals(value)){
			sqlBf.append(column+" like ? or ");
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
			sqlBf.append(column+" in("+handleIn(values)+") or");
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
            sqlBf.append(column+" REGEXP  ? or ");
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
			sqlBf.append(column+" like ? or ");
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
		int index = sqlBf.lastIndexOf("or");
		sqlBf = new StringBuffer(sqlBf.substring(0, index).trim());
		sqlBf.append(")");
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
			sqlBf.append(" , "+column+" "+order);
		}else{
			orderByStr.append(" order by "+column+" "+order);
			sqlBf.append(" order by "+column+" "+order);
			orderBy = true;
		}
		return this;
	}

	/**分页，调用此分页时，需要调用sqlDao中带有分页（既带有Page参数）的查询
	 * @return
	 */
	public SqlComponent limit(){
		sqlBf.append(" limit ? ,?");
		limitStr.append(" limit ? ,?");
		return this;
	}
	/**分页，调用此分页时，就不需要调用sqlDao中带有分页的查询
	 * @param page
	 * @return
	 */
	public SqlComponent limit(Page page){
		paged = true;
		sqlBf.append(" limit ? ,?");
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
		sqlBf.append(" group by "+column);
		return this;
	}
	
	/**添加其它条件语句
	 * @param condition
	 * @return
	 */
	public SqlComponent addOtherCondition(String condition){
		sqlBf.append(condition);
		return this;
	}
	
	/**正则
	 * @param column
	 * @param regexp
	 * @return
	 */
	public SqlComponent regex(String column,String regexp){
		if(regexp != null && !"".equals(regexp)){
			sqlBf.append(" and "+column+" REGEXP  ?");
			params.add(regexp);
		}
		return this;
	}

	/**
	 *
	 * @return
	 */
	public SqlComponent count(){
		StringBuffer countSql =  new StringBuffer("select count(*) ");
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
			countSql = new StringBuffer(countSql.toString().replaceAll(orderByStr.toString(),""));
		}
		sqlBf = countSql;
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
		sqlBf.append(" union all "+sql);
		return this;
	}

	/**拼接unionAll
	 * @param str
	 * @return
	 */
	public SqlComponent unionAllAsTable(String str) {
		sqlBf.append(str);
		return this;
	}
}
