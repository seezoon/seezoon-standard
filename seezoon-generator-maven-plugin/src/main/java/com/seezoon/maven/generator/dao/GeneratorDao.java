package com.seezoon.maven.generator.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import com.seezoon.maven.generator.dto.db.DbPk;
import com.seezoon.maven.generator.dto.db.DbTable;
import com.seezoon.maven.generator.dto.db.DbTableColumn;

/**
 * 获取表信息DAO
 *
 * 在web端配置需要引入该模块，所以没有把sql写在xml中，减少一个冗余配置
 *
 * @author hdf
 */
@Mapper
@Repository
public interface GeneratorDao {

    // @formatter:off
    @Select({"<script>",
            "select table_name name, table_comment comment from information_schema.tables",
            "<where>",
            "table_schema = (select database())",
            "<if test='tableName != null'>",
            "and LOWER(table_name) = LOWER(#{tableName})",
            "</if>",
            "</where>",
            "order by create_time desc",
            "</script>"})
    public List<DbTable> findTable(String tableName);

    @Select({"<script>",
            "select",
            "t.column_name name,",
            "if(LOWER(t.is_nullable) = 'yes' , true , false) nullable,",
            "(t.ordinal_position * 10) sort ,",
            "t.column_comment comment ,",
            "t.data_type dataType ,",
            "t.character_maximum_length maxLength,",
            "t.column_type columnType,",
            "t.column_key columnKey,",
            "t.extra extra",
            "from",
            "information_schema.`columns` t",
            "where LOWER(table_name) = LOWER(#{tableName}) and t.table_schema = (select database())",
            "order by t.ordinal_position asc",
            "</script>"})
    public List<DbTableColumn> findColumnByTableName(String tableName);

    @Select({"<script>",
            "select t.column_name name,",
            "t.data_type dataType,",
            "if(LOWER(t.extra) = 'auto_increment' , true , false) autoIncrement",
            "from information_schema.`columns` t",
            "where LOWER(table_name) = LOWER(#{tableName}) and t.table_schema = (select database()) and UPPER(t.column_key) = 'PRI'",
            "order by t.ordinal_position asc limit 0,1",
            "</script>"})
    public DbPk findPk(String tableName);
    // @formatter:on
}
