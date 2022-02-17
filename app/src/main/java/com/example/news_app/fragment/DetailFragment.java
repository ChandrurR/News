package com.example.news_app.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewStructure;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.text.HtmlCompat;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.example.news_app.R;
import com.example.news_app.network.Articles;

public class DetailFragment extends Fragment {

    Articles articles;
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.title_layout, container, false);
        setHasOptionsMenu(true);
        ImageView image = view.findViewById(R.id.imageView3);
        articles = getArguments().getParcelable("key");
        TextView title = view.findViewById(R.id.textView6);
        TextView desc = view.findViewById(R.id.textView7);
        TextView url = view.findViewById(R.id.textView8);

        Glide.with(requireContext())
                .load(articles.urlToImage)
                .centerCrop()
                //.placeholder(R.drawable.)
                .into(image);

        title.setText(articles.title);
        desc.setText(articles.description);
        url.setText(HtmlCompat.fromHtml("<a href='"+articles.description+"'>"+articles.url+"</a>", 0));
        return view;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        // Do something that differs the Activity's menu here
        menu.clear();
        inflater.inflate(R.menu.share, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {        // Share Option to use
        switch (item.getItemId()) {

            case R.id.shareurl:
                Intent sendIntent = new Intent();
                sendIntent.setAction(Intent.ACTION_SEND);
                sendIntent.putExtra(Intent.EXTRA_TEXT,articles.url);
                sendIntent.setType("text/plain");
                Intent shareIntent = Intent.createChooser(sendIntent, null);
                startActivity(shareIntent);
                return true;

            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
