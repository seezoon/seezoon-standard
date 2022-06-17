package com.seezoon.maven.generator.plan.impl;

import com.seezoon.maven.generator.constants.InputType;
import com.seezoon.maven.generator.constants.QueryType;
import com.seezoon.maven.generator.constants.TemplateType;
import com.seezoon.maven.generator.constants.db.ColumnDataType;
import com.seezoon.maven.generator.constants.db.ColumnExtra;
import com.seezoon.maven.generator.constants.db.ColumnKey;
import com.seezoon.maven.generator.constants.db.DefaultColumns;
import com.seezoon.maven.generator.dto.db.DbTable;
import com.seezoon.maven.generator.dto.db.DbTableColumn;
import com.seezoon.maven.generator.plan.ColumnPlan;
import com.seezoon.maven.generator.plan.PkPlan;
import com.seezoon.maven.generator.plan.TablePlan;
import com.seezoon.maven.generator.plan.TablePlanHandler;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.text.CaseUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * 系统默认生成方案
 *
 * @author hdf
 */
public class SystemTablePlanHandlerImpl implements TablePlanHandler {


    private static final Logger logger = LoggerFactory.getLogger(SystemTablePlanHandlerImpl.class);
    /**
     * DB 及表字段的分隔符
     */
    private static final String DB_DELIMITER = "_";
    private static final String PO = "PO";
    /**
     * sql mapper 生成所在路径
     */
    private String baseSqlMapperPath;

    /**
     * repository 生成所在包
     */
    private String baseRepositoryPackage;

    /**
     * controller 生成所在包
     */
    private String baseControllerPackage;

    public SystemTablePlanHandlerImpl(String baseSqlMapperPath, String baseRepositoryPackage,
            String baseControllerPackage) {
        this.baseSqlMapperPath = baseSqlMapperPath;
        this.baseRepositoryPackage = baseRepositoryPackage;
        this.baseControllerPackage = baseControllerPackage;
    }


    @Override
    public TablePlan generate(DbTable dbTable, List<DbTableColumn> dbTableColumns) {
        TablePlan tablePlan = new TablePlan();
        tablePlan.setBaseSqlMapperPath(baseSqlMapperPath);
        tablePlan.setBaseRepositoryPackage(baseRepositoryPackage);
        tablePlan.setBaseControllerPackage(baseControllerPackage);

        tablePlan.setTableName(dbTable.getName());
        tablePlan.setMenuName(dbTable.getComment());
        // 放入默认的模块名和功能名
        List<String> moduleAndFuntion = extractModuleAndFuntion(dbTable.getNormalizedName());
        tablePlan.setModuleName(moduleAndFuntion.get(0));
        tablePlan.setFunctionName(moduleAndFuntion.get(1));
        tablePlan.setTemplateType(TemplateType.CRUD.ordinal());
        tablePlan.setClassName(CaseUtils.toCamelCase(dbTable.getNormalizedName(), true, DB_DELIMITER.toCharArray()));
        tablePlan.setClassNamePO(tablePlan.getClassName() + PO);
        tablePlan.setColumnPlans(this.createColumnPlan(tablePlan, dbTableColumns));
        return tablePlan;
    }

    private List<ColumnPlan> createColumnPlan(TablePlan tablePlan, List<DbTableColumn> dbTableColumns) {
        List<ColumnPlan> columnPlans = new ArrayList<>();
        dbTableColumns.forEach((v) -> {
            // @formatter:off
            ColumnPlan columnPlan = ColumnPlan.builder()
                    .dbColumnName(v.getName())
                    .normalizedDbColumnNameName(v.getNormalizedName())
                    .fieldName(v.getComment())
                    .columnKey(StringUtils.isNotEmpty(v.getColumnKey()) ? ColumnKey.valueOf(v.getColumnKey())
                            : ColumnKey.NONE)
                    .extra(ColumnExtra.parse(v.getExtra()))
                    .columnType(v.getColumnType())
                    .dataType(ColumnDataType.parse(v.getDataType()))
                    .maxLength(v.getMaxlength())
                    .javaFieldName(CaseUtils.toCamelCase(v.getNormalizedName(), false, DB_DELIMITER.toCharArray()))
                    .nullable(v.getNullable())
                    .sort(v.getSort())
                    .queryType(QueryType.NONE)
                    .inputType(InputType.NONE)
                    .insert(true)
                    .update(true)
                    .build();
            // @formatter:on
            // 针对默认字段的处理
            if (columnPlan.isDefaultField()) {
                if (DefaultColumns.id.name().equals(columnPlan.getNormalizedDbColumnNameName())) {
                    columnPlan.setInsert(false);
                    columnPlan.setUpdate(false);
                } else if (DefaultColumns.status.name().equals(columnPlan.getNormalizedDbColumnNameName())) {
                } else if (DefaultColumns.create_time.name().equals(columnPlan.getNormalizedDbColumnNameName())) {
                    columnPlan.setSortable(true);
                    columnPlan.setUpdate(false);
                } else if (DefaultColumns.update_time.name().equals(columnPlan.getNormalizedDbColumnNameName())) {
                } else if (DefaultColumns.create_by.name().equals(columnPlan.getNormalizedDbColumnNameName())) {
                    columnPlan.setUpdate(false);
                } else if (DefaultColumns.update_by.name().equals(columnPlan.getNormalizedDbColumnNameName())) {
                } else if (DefaultColumns.remarks.name().equals(columnPlan.getNormalizedDbColumnNameName())) {
                }
            } else {
                // 时间
                if (ColumnDataType.DATE.dbType().equals(columnPlan.getDataType().dbType())) {
                    columnPlan.setInputType(InputType.DATE);
                } else if (ColumnDataType.DATETIME.dbType().equals(columnPlan.getDataType().dbType())) {
                    columnPlan.setInputType(InputType.DATETIME);
                } else if (ColumnDataType.TIME.dbType().equals(columnPlan.getDataType().dbType())) {
                    columnPlan.setInputType(InputType.TIME);
                } else if (ColumnDataType.TIMESTAMP.dbType().equals(columnPlan.getDataType().dbType())) {
                    columnPlan.setInputType(InputType.DATETIME);
                }
                // 有索引的
                if ((ColumnKey.MUL.equals(columnPlan.getColumnKey()) || ColumnKey.UNI
                        .equals(columnPlan.getColumnKey()))) {
                    columnPlan.setSearch(true);
                    columnPlan.setSortable(true);
                    columnPlan.setQueryType(QueryType.EQUAL);
                }
            }

            // 主键
            if (columnPlan.getColumnKey().equals(ColumnKey.PRI)) {
                if (null != tablePlan.getPkPlan()) {
                    logger.warn("table[{}] must have only one primary key,otherwise generator select first primary key",
                            tablePlan.getTableName());
                } else {
                    tablePlan.setPkPlan(new PkPlan(columnPlan.getDbColumnName(), columnPlan.getJavaFieldName(),
                            columnPlan.getDataType(), DefaultColumns.id.name().equals(columnPlan.getJavaFieldName()),
                            ColumnExtra.auto_increment.equals(columnPlan.getExtra())));
                    // 自增不可插入
                    columnPlan.setInsert(!tablePlan.getPkPlan().isAutoIncrement());
                    // 主键也不可更新
                    columnPlan.setUpdate(false);
                }
            }
            columnPlans.add(columnPlan);
        });
        // 一定要有主键
        if (null == tablePlan.getPkPlan()) {
            throw new IllegalArgumentException(
                    String.format("table[%s] must have primary key", tablePlan.getTableName()));
        }
        return columnPlans;
    }

    /**
     * 通过表名提取模块名和功能名，按{@link SystemTablePlanHandlerImpl#DB_DELIMITER} 拆分为2个，只拆第一个分隔符
     *
     * @param tableName not null
     * @return
     */
    private List<String> extractModuleAndFuntion(String tableName) {
        String[] splitedTableName = tableName.split(DB_DELIMITER, 2);
        return List.of(splitedTableName[0], splitedTableName.length == 2 ? splitedTableName[1] : splitedTableName[0]);
    }
}
