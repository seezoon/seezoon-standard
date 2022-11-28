package com.seezoon.demo.domain.sys.repository.mapper;

import org.apache.ibatis.annotations.Mapper;

import com.seezoon.demo.domain.sys.repository.po.SysUserPO;
import com.seezoon.mybatis.repository.mapper.CrudMapper;

/**
 * 用户信息
 * 
 * @author seezoon-generator 2022年6月16日 下午11:12:31
 */
@Mapper
public interface SysUserMapper extends CrudMapper<SysUserPO, Integer> {

}