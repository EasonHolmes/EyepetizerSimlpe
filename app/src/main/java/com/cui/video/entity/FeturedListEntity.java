package com.cui.video.entity;

import java.util.List;

public class FeturedListEntity {

    public String nextPageUrl;
    public List<IssueList> issueList;

    public static class IssueList {
        public long releaseTime;
        public String type;
        public long date;
        public long publishTime;
        public int count;
        public List<ItemList> itemList;
    }
}
