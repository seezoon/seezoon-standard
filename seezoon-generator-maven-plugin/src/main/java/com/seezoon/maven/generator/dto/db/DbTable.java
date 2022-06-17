package com.seezoon.maven.generator.dto.db;

import lombok.Getter;
import lombok.Setter;

/**
 * 表基本信息
 *
 * @author hdf
 */
@Getter
@Setter
public class DbTable {

    /**
     * 表名
     */
    private String name;
    /**
     * 备注
     */
    private String comment;

    /**
     * 标准化候的名字，比如去除前缀
     */
    private String normalizedName;

}
