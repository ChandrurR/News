package com.example.news_app.network;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class NewsData {

    @SerializedName("status")
    public String status;

    @SerializedName("totalResults")
    public int totalResults;

    @SerializedName("articles")
    public List<Articles> articles;

}
