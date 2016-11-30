package com.cui.video.entity;

import java.util.List;

public class FeturedListEntity {

    public String nextPageUrl;
    public List<IssueList> issueList;

    public static class IssueList {
        public List<ItemList> itemList;
    }
}
