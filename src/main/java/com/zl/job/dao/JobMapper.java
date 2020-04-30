package com.zl.job.dao;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zl.job.entity.Job;

import java.util.List;

public interface JobMapper extends BaseMapper<Job> {
	
	List<Job> queryList();
}