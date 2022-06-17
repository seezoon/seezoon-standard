package com.seezoon.maven.generator.plan;

import com.seezoon.maven.generator.dto.db.DbTable;
import com.seezoon.maven.generator.dto.db.DbTableColumn;
import java.util.List;

/**
 *
 * @author hdf
 */
public interface TablePlanHandler {

    public TablePlan generate(DbTable dbTable, List<DbTableColumn> dbTableColumns);
}
