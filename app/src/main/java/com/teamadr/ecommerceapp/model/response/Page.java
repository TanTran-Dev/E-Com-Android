package com.teamadr.ecommerceapp.model.response;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Page<T> {
    @SerializedName("pageIndex")
    private int pageIndex;

    @SerializedName("pageSize")
    private int pageSize;

    @SerializedName("totalItems")
    private int totalItem;

    @SerializedName("items")
    private List<T> items;

    public Page(int pageIndex, int pageSize, int totalItem, List<T> items) {
        this.pageIndex = pageIndex;
        this.pageSize = pageSize;
        this.totalItem = totalItem;
        this.items = items;
    }

    public Page() {
    }

    public int getPageIndex() {
        return pageIndex;
    }

    public void setPageIndex(int pageIndex) {
        this.pageIndex = pageIndex;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getTotalItem() {
        return totalItem;
    }

    public void setTotalItem(int totalItem) {
        this.totalItem = totalItem;
    }

    public List<T> getItems() {
        return items;
    }

    public void setItems(List<T> items) {
        this.items = items;
    }

    @Override
    public String toString() {
        return "Page{" +
                "mPageIndex=" + pageIndex +
                ", mPageSize=" + pageSize +
                ", mTotalItem=" + totalItem +
                ", mItems=" + (items == null ? "" : items.toString()) +
                '}';
    }
}
