package com.teamadr.ecommerceapp.model.response;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Page<T> {
    @SerializedName("pageIndex")
    private int pageIndex;

    @SerializedName("pageSize")
    private int pageSize;

    @SerializedName("totalItems")
    private int totalItems;

    @SerializedName("items")
    private List<T> items;

    public Page(int pageIndex, int pageSize, int totalItems, List<T> items) {
        this.pageIndex = pageIndex;
        this.pageSize = pageSize;
        this.totalItems = totalItems;
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

    public int getTotalItems() {
        return totalItems;
    }

    public void setTotalItems(int totalItems) {
        this.totalItems = totalItems;
    }

    public List<T> getItems() {
        return items;
    }

    public void setItems(List<T> items) {
        this.items = items;
    }

    public int getMaxPageIndex() {
        if (totalItems == 0) {
            return 0;
        }
        return ((int) Math.ceil(totalItems * 1.0 / pageSize)) - 1;
    }
}
