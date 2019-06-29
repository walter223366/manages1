package com.ral.manages.util.page;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class PageBean<T> implements Serializable {

    private static final long serialVersionUID = 1L;
    // 每页条数
    private int limit = 10;
    // 当前页码
    private int current = 1;
    // 数据总数
    private int total = 0;
    // 数据
    private List<T> data = new ArrayList<T>();

    public PageBean() {
        super();
    }

    public PageBean(int limit, int current) {
        super();
        this.limit = limit;
        this.current = current;
    }

    public PageBean(int limit, int current, int total, List<T> data) {
        super();
        this.limit = limit;
        this.current = current;
        this.total = total;
        this.data = data;
    }

    /**
     * 获取总页数
     *
     * @return
     */
    public int getPageCount() {
        int pageCount = 0;
        if (total % this.limit == 0) {
            pageCount = total / limit;
        } else {
            pageCount = total / limit + 1;
        }
        return pageCount;
    }

    /***
     * 获取Oracle分页开始点
     *
     * @return
     */
    public int getOracleStart() {
        if (current <= 1) {
            return 1;
        } else {
            return (current - 1) * limit + 1;
        }
    }

    /**
     * 获取Oracle分页结束点
     *
     * @return
     */
    public int getOracleEnd() {
        if (current <= 1) {
            return limit;
        } else {
            return getOracleStart() + limit - 1;
        }
    }

    /**
     * 获取PostgreSql-offset
     *
     * @return
     */
    public int getPgOffset() {
        if (current <= 1) {
            return 0;
        } else {
            return (current - 1) * limit;
        }
    }

    /**
     * 获取Mysql分页开始点
     *
     * @return
     */
    public int getMySqlStart() {
        int a = (current - 1) < 0 ? 0 : getLimit() * (current - 1);
        return a;
    }

    /**
     * 每页条数
     *
     * @return
     */
    public int getLimit() {
        return limit;
    }
    public void setLimit(int limit) {
        this.limit = limit;
    }

    /**
     * 当前页码
     *
     * @return
     */
    public int getCurrent() {
        return current;
    }
    public void setCurrent(int current) {
        this.current = current;
    }

    /**
     * 数据总数
     *
     * @return
     */
    public int getTotal() {
        return total;
    }
    public void setTotal(int total) {
        this.total = total;
    }

    /**
     * 数据
     *
     * @return
     */
    public List<T> getData() {
        return data;
    }
    public void setData(List<T> data) {
        this.data = data;
    }
}

