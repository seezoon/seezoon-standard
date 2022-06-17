package com.seezoon.maven.generator.plan;

import com.google.common.base.CaseFormat;
import com.seezoon.maven.generator.constants.InputType;
import com.seezoon.maven.generator.constants.QueryType;
import com.seezoon.maven.generator.constants.db.ColumnDataType;
import com.seezoon.maven.generator.constants.db.ColumnExtra;
import com.seezoon.maven.generator.constants.db.ColumnKey;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.ArrayUtils;

/**
 * 代码生成每列的生成方案
 *
 * @author hdf 2018年4月27日
 */
@Getter
@Setter
@Builder
public class ColumnPlan implements Comparable<ColumnPlan> {

    private static final String[] DEFAULT_COLUMNS = {"id", "status", "create_by", "create_time", "update_by",
            "update_time", "remarks"};
    /**
     * DB 列名称
     */
    private String dbColumnName;
    /**
     * 标准化候的字段名，如去除前缀
     */
    private String normalizedDbColumnNameName;
    /**
     * 列中文名，默认是DB列备注
     */
    private String fieldName;
    /**
     * 主键类型 如PRI(主键) UNI(唯一) MUL(普通索引) {@link ColumnKey}
     */
    private ColumnKey columnKey;
    /**
     * 额外说明 如auto_increment
     */
    private ColumnExtra extra;
    /**
     * 列类型 eg:varchar(64)
     */
    private String columnType;
    /**
     * 数据类型eg DB:varchar jdbcType:VARCHAR,javaType:String
     */
    private ColumnDataType dataType;

    /**
     * 字段长度eg:64
     */
    private Integer maxLength;

    /**
     * 字段名称
     */
    private String javaFieldName;

    /**
     * 是否可空
     */
    private boolean nullable;
    /**
     * 排序
     */
    private Integer sort;
    /**
     * 是否可插入
     */
    private boolean insert;
    /**
     * 是否可更新
     */
    private boolean update;
    /**
     * 是否列表排序
     */
    private boolean sortable;
    /**
     * 是否搜索条件
     */
    private boolean search;
    /**
     * 查询方式 {@link QueryType}
     */
    private QueryType queryType;
    /**
     * 接收类型 {@link InputType}
     */
    private InputType inputType;

    @Override
    public int compareTo(ColumnPlan o) {
        return this.sort - o.getSort();
    }

    public String getJavaType() {
        return null == dataType ? null : dataType.javaType();
    }

    /**
     * 大量需要判断是否字符串的
     */
    public boolean isStringType() {
        return this.getDataType().javaType().equals(String.class.getSimpleName());
    }

    public boolean isNumberType() {
        return ArrayUtils.contains(new String[]{ColumnDataType.TINYINT.javaType(), ColumnDataType.INT.javaType(),
                        ColumnDataType.INTEGER.javaType(), ColumnDataType.BIGINT.javaType(), ColumnDataType.DOUBLE.javaType(),
                        ColumnDataType.FLOAT.javaType(), ColumnDataType.DECIMAL.javaType(), ColumnDataType.NUMERIC.javaType(),},
                this.getDataType().javaType());
    }

    /**
     * 是否默认字段,会在能忽略的场景忽略，比如默认字段
     */
    public boolean isDefaultField() {
        return ArrayUtils.contains(DEFAULT_COLUMNS, this.getNormalizedDbColumnNameName());
    }

    /**
     * 是否大字段
     */
    public boolean isBlobType() {
        return ColumnDataType.TEXT.jdbcType().equals(this.getDataType().jdbcType());
    }

    public boolean isUniqueField() {
        return ArrayUtils.contains(new ColumnKey[]{ColumnKey.UNI}, this.getColumnKey());
    }

    public String getUnderScoreFieldName() {
        return CaseFormat.LOWER_CAMEL.to(CaseFormat.LOWER_UNDERSCORE, this.getJavaFieldName());
    }
}
