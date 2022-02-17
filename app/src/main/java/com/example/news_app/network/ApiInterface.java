package com.example.news_app.network;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiInterface {

    @GET("v2/everything")
    Call<NewsData> getNewsFromApi(@Query("q") String query,@Query("apiKey") String apiKey);

}
