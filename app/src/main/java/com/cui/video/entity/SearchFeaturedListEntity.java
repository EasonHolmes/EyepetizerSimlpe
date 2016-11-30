package com.cui.video.entity;

import java.util.List;

/**
 * Created by cuiyang on 2016/11/28.
 */

public class SearchFeaturedListEntity {


    private int count;
    private int total;
    private String nextPageUrl;
    private List<ItemList> itemList;

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public String getNextPageUrl() {
        return nextPageUrl;
    }

    public void setNextPageUrl(String nextPageUrl) {
        this.nextPageUrl = nextPageUrl;
    }

    public List<ItemList> getItemList() {
        return itemList;
    }

    public void setItemList(List<ItemList> itemList) {
        this.itemList = itemList;
    }

}
