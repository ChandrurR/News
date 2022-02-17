package com.example.news_app;


import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

class ScreenSlidePagerAdapter extends FragmentStateAdapter {
    String[] states;
    public ScreenSlidePagerAdapter(FragmentActivity fa,String[] states) {
        super(fa);
        this.states = states;
    }

    @Override
    public Fragment createFragment(int position) {
        StateWiseNews fra=new StateWiseNews();
        Bundle bundle=new Bundle();
        bundle.putString("key",states[position]);
        fra.setArguments(bundle);
        return fra;
    }

    @Override
    public int getItemCount() {
        return states.length;
    }
}