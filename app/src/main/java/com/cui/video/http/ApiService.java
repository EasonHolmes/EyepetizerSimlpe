package com.cui.video.http;


import com.cui.video.entity.ClasssDetailEntity;
import com.cui.video.entity.FindListEntity;
import com.cui.video.entity.RankListEntity;
import com.cui.video.entity.SearchFeaturedListEntity;
import com.cui.video.entity.FeturedListEntity;

import retrofit2.http.GET;
import retrofit2.http.Query;
import retrofit2.http.Streaming;
import retrofit2.http.Url;
import rx.Observable;

/**
 * Created by cuiyang on 16/6/1.
 */
public interface ApiService {

//    @FormUrlEncoded
//    @POST("user/enterpriseLogin")
//    Observable<LoginEntity> enterpriseLogin(@Field("userAccount") String userAccount, @Field("password") String password);


    //    @Streaming
//    @GET
//    Observable<JsonObject> getFeaturedMoreList(@Url String url);

    /**
     * 分页精选
     *
     * @param date
     * @return
     */
    @GET("v2/feed?num=2")
    Observable<FeturedListEntity> getDaily(@Query("date") long date);

    /**
     * 第一个精选
     *
     * @return
     */
    @GET("v2/feed?num=2")
    Observable<FeturedListEntity> getDaily();

    /**
     * 发现
     *
     * @param url
     * @return
     */
    @Streaming
    @GET
    Observable<FindListEntity> getFindList(@Url String url);

    /**
     * 搜索精选
     *
     * @param queryStr
     * @param start
     * @return
     */
    @GET("v1/search")
    Observable<SearchFeaturedListEntity> getSearchFeatured(@Query("query") String queryStr, @Query("start") int start);


    @GET("v3/videos?num=10")
    Observable<ClasssDetailEntity> getClasssDeatilList(
            @Query("start") int start, @Query("categoryId") int categoryId,
            @Query("strategy") String strategy);

    @GET("v3/ranklist?num=10")
    Observable<RankListEntity> getRankList(@Query("strategy") String strategy);
}
