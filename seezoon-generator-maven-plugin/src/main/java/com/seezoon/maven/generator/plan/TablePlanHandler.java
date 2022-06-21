package com.seezoon.maven.generator.plan;

import java.util.List;

import com.seezoon.maven.generator.dto.db.DbTable;
import com.seezoon.maven.generator.dto.db.DbTableColumn;

/**
 *
 * @author hdf
 */
public interface TablePlanHandler {

    public TablePlan generate(DbTable dbTable, List<DbTableColumn> dbTableColumns);
}
