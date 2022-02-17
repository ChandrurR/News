package com.example.news_app;

import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.news_app.adapter.StateNewsAdapter;
import com.example.news_app.network.ApiClient;
import com.example.news_app.network.ApiInterface;
import com.example.news_app.network.Articles;
import com.example.news_app.network.NewsData;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class StateWiseNews extends Fragment {

    RecyclerView recyclerView;
    Switch aSwitch;
    StateNewsAdapter adapter;
    ArrayList<Articles> articles =new ArrayList<>();
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.state_news, container, false);
        recyclerView = view.findViewById(R.id.recycler);
        aSwitch = view.findViewById(R.id.switch1);
        aSwitch.setText("Switch to Grid");
        setRecyclerViewData(new LinearLayoutManager(requireContext()));
        aSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if(isChecked){
                    aSwitch.setText("Switch to List");
                    setRecyclerViewData(new GridLayoutManager(requireContext(),2));
                }else{
                    aSwitch.setText("Switch to Grid");
                    setRecyclerViewData(new LinearLayoutManager(requireContext()));
                }
            }
        });

        getNewsFromApi();

        return view;
    }
    public void setRecyclerViewData(RecyclerView.LayoutManager manager) {
        adapter = new StateNewsAdapter(requireContext(),articles);
        adapter.isGrid(manager instanceof GridLayoutManager);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(manager);
    }

    public void getNewsFromApi() {
        ApiInterface apiService =
                ApiClient.getClient().create(ApiInterface.class);

        String key = getArguments().getString("key");
        Call<NewsData> call = apiService.getNewsFromApi(key,"583ce4ee4e8848f2829b9ccd6b645355");
        call.enqueue(new Callback<NewsData>() {
            @Override
            public void onResponse(Call<NewsData>call, Response<NewsData> response) {
                NewsData newsData = response.body();
                articles = (ArrayList<Articles>) newsData.articles;
                if(articles.size()==0){
                    Toast.makeText(requireContext(),"No news found",Toast.LENGTH_LONG).show();
                }
                setRecyclerViewData(recyclerView.getLayoutManager());
            }

            @Override
            public void onFailure(Call<NewsData>call, Throwable t) {
                // Log error here since request failed
                Log.e("api_err0r", t.toString());
            }
        });
    }



}
