package com._0myun.minecraft.banitemshower;

import java.util.List;

public class PageUtils<T> {
    /**
     * 总的记录条数
     */
    private int totalRecord;

    /**
     * 记录列表
     */
    private List<T> records;
    /**
     * 页码
     */
    private int pageNo = 1;

    /**
     * 每页显示长度
     */
    private int pageSize = 52;

    /**
     * 分页开始
     */
    private int start;

    /**
     * 分页结束
     */
    private int end;

    public int getEnd() {
        end = pageNo * pageSize;
        return end;
    }

    public int getStart() {
        start = (pageNo - 1) * pageSize;
        return start;
    }

    public int getTotalRecord() {
        return totalRecord;
    }

    public void setTotalRecord(int totalRecord) {
        this.totalRecord = totalRecord;
    }

    public List<T> getRecords() {
        return records;
    }

    public void setRecords(List<T> records) {
        this.records = records;
    }

    public int getPageNo() {
        return pageNo;
    }

    public void setPageNo(int pageNo) {
        this.pageNo = pageNo;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public List<T> getSplitList() {
        return getRecords().subList(this.getStart(), this.getEnd() >= this.records.size() ? this.records.size() - 1 : this.getEnd());
    }

}
