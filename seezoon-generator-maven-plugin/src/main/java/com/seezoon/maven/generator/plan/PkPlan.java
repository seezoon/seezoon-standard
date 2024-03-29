package com.seezoon.maven.generator.plan;

import com.seezoon.maven.generator.constants.db.ColumnDataType;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/**
 * @author hdf
 */
@Getter
@Setter
@AllArgsConstructor
public class PkPlan {
    private String dbColumnName;
    private String javaFieldName;
    private ColumnDataType dataType;
    // 是否和默认ID名字相等
    private boolean defaultJavaPkName;
    // 是否自增
    private boolean autoIncrement;

}
