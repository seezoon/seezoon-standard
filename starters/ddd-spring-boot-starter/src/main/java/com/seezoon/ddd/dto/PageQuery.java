package com.seezoon.ddd.dto;

public abstract class PageQuery {

    public static final String ASC = "ASC";

    public static final String DESC = "DESC";

    public static final int DEFAULT_PAGE_SIZE_LIMIT = 1000;

    /**
     * 页码
     */
    private int page = 1;
    /**
     * 每页条数
     */
    private int pageSize = 10;
    /**
     * 排序字段，可空
     */
    private String sortField;

    /**
     * 排序顺序，可空
     */
    private String sortOrder;

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
        if (pageSize > this.pageSizeLimit()) {
            throw new IllegalArgumentException("page size limit:" + this.pageSizeLimit() + ",current is " + pageSize);
        }
        this.pageSize = pageSize;
    }

    public String getSortField() {
        return sortField;
    }

    public void setSortField(String sortField) {
        this.sortField = sortField;
    }

    public String getSortOrder() {
        return sortOrder;
    }

    public void setSortOrder(String sortOrder) {
        this.sortOrder = sortOrder;
    }

    /**
     * 每页条数限制
     *
     * @return
     */
    public int pageSizeLimit() {
        return DEFAULT_PAGE_SIZE_LIMIT;
    }

}
