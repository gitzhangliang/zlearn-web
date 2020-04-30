package com.zl.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zl.domain.Coder;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

/**
 * @author zl
 * @date 2019/5/30.
 */
public interface CoderMapper extends BaseMapper<Coder> {
    @Select("select * from coder where ${column} = #{value}")
    Coder findByColumn(@Param("column") String column, @Param("value") String value);
}
