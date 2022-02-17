package com.example.news_app.adapter;

import android.content.Context;
import android.os.Bundle;
import android.os.PowerManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebViewFragment;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.news_app.MainActivity;
import com.example.news_app.R;
import com.example.news_app.fragment.DetailFragment;
import com.example.news_app.fragment.WebFragment;
import com.example.news_app.network.Articles;

import java.util.ArrayList;

public class StateNewsAdapter extends RecyclerView.Adapter<NewsViewHolder> {


    boolean isGrid = false;
    ArrayList<Articles> articles;
    Context context;

    public void isGrid(boolean value) {
        isGrid = value;
    }

    public StateNewsAdapter(Context context,ArrayList<Articles> articles) {
        this.context = context;
        this.articles = articles;
    }

    @Override
    public NewsViewHolder
    onCreateViewHolder(ViewGroup parent,
                       int viewType) {

        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View newsView = null;
        if (!isGrid) {
            newsView = inflater.inflate(R.layout.news_layout, parent, false);
        } else {
            newsView = inflater.inflate(R.layout.news_layout_grid, parent, false);
        }
        NewsViewHolder viewHolder = new NewsViewHolder(newsView);
        return viewHolder;
    }

    @Override
    public void
    onBindViewHolder(final NewsViewHolder viewHolder,final int position) {
        viewHolder.title.setText(articles.get(position).title);
        viewHolder.description.setText(articles.get(position).description);
        Glide.with(viewHolder.itemView.getContext())
                .load(articles.get(position).urlToImage)
                .centerCrop()
                //.placeholder(R.drawable.)
                .into(viewHolder.imageView);
        viewHolder.view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DetailFragment fragment=new DetailFragment();
                Bundle bundle=new Bundle();
               // bundle.putString("key",articles.get(position).url);
                bundle.putParcelable("key",articles.get(position));
                fragment.setArguments(bundle);
                ((MainActivity)context).loadFragment(R.id.frame,fragment);
            }
        });
    }

    @Override
    public int getItemCount() {
        return articles.size();
    }


}

class NewsViewHolder extends RecyclerView.ViewHolder {
    TextView title;
    TextView description;
    ImageView imageView;
    ConstraintLayout view;

    NewsViewHolder(View itemView) {
        super(itemView);
        title = (TextView) itemView.findViewById(R.id.textView4);
        description = (TextView) itemView.findViewById(R.id.textView5);
        imageView = itemView.findViewById(R.id.imageView3);
        view = itemView.findViewById(R.id.layout);
    }
}
