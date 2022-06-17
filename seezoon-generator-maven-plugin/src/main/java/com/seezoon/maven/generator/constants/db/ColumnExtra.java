package com.seezoon.maven.generator.constants.db;

/**
 * DB 字段的extra,如auto_increment DB默认是小写
 *
 * @author hdf
 */
public enum ColumnExtra {
    // 缺省
    none, // 自增
    auto_increment, //
    ;

    public static ColumnExtra parse(String v) {
        if (ColumnExtra.auto_increment.toString().equals(v)) {
            return auto_increment;
        }
        return none;
    }
}
