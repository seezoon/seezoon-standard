package com.seezoon.mybatis.repository.po;

import org.apache.commons.lang3.StringUtils;

import com.seezoon.mybatis.repository.sort.SortDirectionMapping;
import com.seezoon.mybatis.repository.sort.SortFieldMapping;

/**
 * 分页条件
 *
 * @author hdf
 */
public class PagePOCondition extends AbstractPOQueryCondition {

    private static final int MAX_PAGE_SIZE = 1000;
    /**
     * 页码(分页请求必传)
     */
    private int page = 1;
    /**
     * 默认每页大小 每页大小(分页请求必传)
     */
    private int pageSize = 20;

    /**
     * 排序字段
     */
    private String sortField;

    /**
     * 排序顺序
     */
    private String sortOrder;

    public PagePOCondition() {
        SortFieldMapping.regiest(this.getClass());
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        if (pageSize > MAX_PAGE_SIZE) {
            pageSize = MAX_PAGE_SIZE;
        }
        this.pageSize = pageSize;
    }

    public String getSortField() {
        if (StringUtils.isNotEmpty(sortField)) {
            return SortFieldMapping.getDbFieldName(this.getClass(), sortField);
        }
        return sortField;
    }

    public void setSortField(String sortField) {
        this.sortField = sortField;
    }

    public String getSortOrder() {
        return SortDirectionMapping.getMapping(sortOrder);
    }

    public void setSortOrder(String sortOrder) {
        this.sortOrder = sortOrder;
    }
}
