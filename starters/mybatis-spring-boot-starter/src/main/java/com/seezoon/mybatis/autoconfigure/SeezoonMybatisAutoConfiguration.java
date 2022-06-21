package com.seezoon.mybatis.autoconfigure;

import org.mybatis.spring.boot.autoconfigure.MybatisAutoConfiguration;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.context.annotation.Configuration;

/**
 * <p>
 * 业务使用需要加上，扫码例如
 *
 * @MapperScan(basePackages = "com.seezoon.dao.modules.*",markerInterface = BaseMapper.class)
 */
@Configuration
@AutoConfigureAfter({MybatisAutoConfiguration.class})
public class SeezoonMybatisAutoConfiguration {

}
