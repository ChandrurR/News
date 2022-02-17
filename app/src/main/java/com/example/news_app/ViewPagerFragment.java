package com.example.news_app;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

public class ViewPagerFragment extends Fragment {

    String stateNames[] = {"Politics","Government","StockMarket","Hospitals","Omicron","Covid-19","Police","Army","Movies","Sports","Economy","Events","Articles","Technology"};
    ViewPager2 pager;
        @Nullable
        @Override
        public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
            View view = inflater.inflate(R.layout.view_pager,container,false);
            pager = view.findViewById(R.id.viewPager2);
            pager.setAdapter(new ScreenSlidePagerAdapter(requireActivity(),stateNames));

            return view;
        }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        TabLayout tabLayout = view.findViewById(R.id.tabs);
        new TabLayoutMediator(tabLayout,pager,
                (tab, position) -> tab.setText(stateNames[position])
        ).attach();
    }
}
