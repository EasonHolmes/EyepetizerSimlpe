package com.cui.video.entity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by cuiyang on 2016/11/22.
 */

public class FindListEntity {

    /**
     * itemList : [{"type":"squareCard","data":{"dataType":"SquareCard","id":-1,"title":"","image":"http://img.kaiyanapp.com/eaded016cfcb90a695661e37f2913a6b.jpeg?imageMogr2/quality/60","actionUrl":"eyepetizer://ranklist/","shade":false,"adTrack":null}},{"type":"squareCard","data":{"dataType":"SquareCard","id":0,"title":"","image":"http://img.kaiyanapp.com/d497d12ae02b6b173b7818a94b84a92b.jpeg?imageMogr2/quality/60","actionUrl":"eyepetizer://campaign/list/?title=%E4%B8%93%E9%A2%98","shade":false,"adTrack":null}},{"type":"rectangleCard","data":{"dataType":"RectangleCard","id":1,"title":"","image":"http://img.kaiyanapp.com/f6765ae9bcd4551ce40f10fe342b681c.jpeg?imageMogr2/quality/60","actionUrl":"eyepetizer://tag/658/?title=360%C2%B0%E5%85%A8%E6%99%AF","shade":false,"adTrack":null}},{"type":"squareCard","data":{"dataType":"SquareCard","id":24,"title":"#时尚","image":"http://img.kaiyanapp.com/22192a40de238fe853b992ed57f1f098.jpeg","actionUrl":"eyepetizer://category/24/?title=%E6%97%B6%E5%B0%9A","shade":true,"adTrack":null}},{"type":"squareCard","data":{"dataType":"SquareCard","id":14,"title":"#广告","image":"http://img.kaiyanapp.com/98beab66d3885a139b54f21e91817c4f.jpeg","actionUrl":"eyepetizer://category/14/?title=%E5%B9%BF%E5%91%8A","shade":true,"adTrack":null}},{"type":"squareCard","data":{"dataType":"SquareCard","id":2,"title":"#创意","image":"http://img.kaiyanapp.com/fd56db2b929132b883e9981ab843dfca.jpeg","actionUrl":"eyepetizer://category/2/?title=%E5%88%9B%E6%84%8F","shade":true,"adTrack":null}},{"type":"squareCard","data":{"dataType":"SquareCard","id":6,"title":"#旅行","image":"http://img.kaiyanapp.com/3d874b72aaad089836f3cc4a25b64bb5.jpeg","actionUrl":"eyepetizer://category/6/?title=%E6%97%85%E8%A1%8C","shade":true,"adTrack":null}},{"type":"squareCard","data":{"dataType":"SquareCard","id":20,"title":"#音乐","image":"http://img.kaiyanapp.com/9279c17b4da5ba5e7e4f21afb5bb0a74.jpeg","actionUrl":"eyepetizer://category/20/?title=%E9%9F%B3%E4%B9%90","shade":true,"adTrack":null}},{"type":"squareCard","data":{"dataType":"SquareCard","id":18,"title":"#运动","image":"http://img.kaiyanapp.com/c746d56db089909b1cdd933fa7c98ef8.jpeg","actionUrl":"eyepetizer://category/18/?title=%E8%BF%90%E5%8A%A8","shade":true,"adTrack":null}},{"type":"squareCard","data":{"dataType":"SquareCard","id":36,"title":"#生活","image":"http://img.kaiyanapp.com/924ebc6780d59925c8a346a5dafc90bb.jpeg","actionUrl":"eyepetizer://category/36/?title=%E7%94%9F%E6%B4%BB","shade":true,"adTrack":null}},{"type":"squareCard","data":{"dataType":"SquareCard","id":4,"title":"#开胃","image":"http://img.kaiyanapp.com/5817f1bfdce61130204a24268160b619.jpeg","actionUrl":"eyepetizer://category/4/?title=%E5%BC%80%E8%83%83","shade":true,"adTrack":null}},{"type":"squareCard","data":{"dataType":"SquareCard","id":22,"title":"#记录","image":"http://img.kaiyanapp.com/a2fc6d32ac0b4f2842fb3d545d06f09b.jpeg","actionUrl":"eyepetizer://category/22/?title=%E8%AE%B0%E5%BD%95","shade":true,"adTrack":null}},{"type":"squareCard","data":{"dataType":"SquareCard","id":30,"title":"#游戏","image":"http://img.kaiyanapp.com/2b7ac9d21ca06df7e39e80a3799a3fb6.jpeg","actionUrl":"eyepetizer://category/30/?title=%E6%B8%B8%E6%88%8F","shade":true,"adTrack":null}},{"type":"squareCard","data":{"dataType":"SquareCard","id":26,"title":"#萌宠","image":"http://img.kaiyanapp.com/ac6971c1b9fc942c7547d25fc6c60ad2.jpeg","actionUrl":"eyepetizer://category/26/?title=%E8%90%8C%E5%AE%A0","shade":true,"adTrack":null}},{"type":"squareCard","data":{"dataType":"SquareCard","id":10,"title":"#动画","image":"http://img.kaiyanapp.com/482c741c06644f5566c7218096dbaf26.jpeg","actionUrl":"eyepetizer://category/10/?title=%E5%8A%A8%E7%94%BB","shade":true,"adTrack":null}},{"type":"squareCard","data":{"dataType":"SquareCard","id":32,"title":"#科普","image":"http://img.kaiyanapp.com/0117b9108c7cff43700db8af5e24f2bf.jpeg","actionUrl":"eyepetizer://category/32/?title=%E7%A7%91%E6%99%AE","shade":true,"adTrack":null}},{"type":"squareCard","data":{"dataType":"SquareCard","id":12,"title":"#剧情","image":"http://img.kaiyanapp.com/00d60c80c7fa0db3bacd568b5999d045.jpeg","actionUrl":"eyepetizer://category/12/?title=%E5%89%A7%E6%83%85","shade":true,"adTrack":null}},{"type":"squareCard","data":{"dataType":"SquareCard","id":28,"title":"#搞笑","image":"http://img.kaiyanapp.com/cd74ae49d45ab6999bcd55dbae6d550f.jpeg","actionUrl":"eyepetizer://category/28/?title=%E6%90%9E%E7%AC%91","shade":true,"adTrack":null}},{"type":"squareCard","data":{"dataType":"SquareCard","id":8,"title":"#预告","image":"http://img.kaiyanapp.com/003829087e85ce7310b2210d9575ce67.jpeg","actionUrl":"eyepetizer://category/8/?title=%E9%A2%84%E5%91%8A","shade":true,"adTrack":null}},{"type":"squareCard","data":{"dataType":"SquareCard","id":38,"title":"#综艺","image":"http://img.kaiyanapp.com/9f4c1559d54d4274e5463fba4262b284.jpeg","actionUrl":"eyepetizer://category/38/?title=%E7%BB%BC%E8%89%BA","shade":true,"adTrack":null}},{"type":"squareCard","data":{"dataType":"SquareCard","id":34,"title":"#集锦","image":"http://img.kaiyanapp.com/d7186edff72b6a6ddd03eff166ee4cd8.jpeg","actionUrl":"eyepetizer://category/34/?title=%E9%9B%86%E9%94%A6","shade":true,"adTrack":null}}]
     * count : 22
     * total : 0
     * nextPageUrl : null
     */

    private int count;
    private int total;
    private Object nextPageUrl;
    private List<ItemList> itemList = new ArrayList<>();

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

    public Object getNextPageUrl() {
        return nextPageUrl;
    }

    public void setNextPageUrl(Object nextPageUrl) {
        this.nextPageUrl = nextPageUrl;
    }

    public List<ItemList> getItemList() {
        return itemList;
    }

    public void setItemList(List<ItemList> itemList) {
        this.itemList = itemList;
    }
}
