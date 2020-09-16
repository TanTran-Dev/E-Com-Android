package com.teamadr.ecommerceapp.event_bus;

import java.util.List;

public class SortEvent {
    private List<String> sortBy;
    private List<String> sortType;
    private Integer trademarkId;

    public SortEvent(List<String> sortBy, List<String> sortType, Integer trademarkId) {
        this.sortBy = sortBy;
        this.sortType = sortType;
        this.trademarkId = trademarkId;
    }

    public List<String> getSortBy() {
        return sortBy;
    }

    public void setSortBy(List<String> sortBy) {
        this.sortBy = sortBy;
    }

    public List<String> getSortType() {
        return sortType;
    }

    public void setSortType(List<String> sortType) {
        this.sortType = sortType;
    }

    public Integer getTrademarkId() {
        return trademarkId;
    }

    public void setTrademarkId(Integer trademarkId) {
        this.trademarkId = trademarkId;
    }
}
