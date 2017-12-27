package com.example.robotinterction.adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.example.robotinterction.bean.TextFragment;

import java.util.List;

/**
 * Created by 金文韬 on 2017/12/19.
 */

public class TabFragmentAdapter extends FragmentPagerAdapter {
    private Context context;
    private List<TextFragment> fragments;
    private List<String> strings;
    public TabFragmentAdapter(List<TextFragment> fragments, List<String> strings, FragmentManager fragmentManager, Context context) {
        super(fragmentManager);
        this.strings=strings;
        this.fragments=fragments;
        this.context=context;
    }

    @Override
    public Fragment getItem(int position) {
        return fragments.get(position);
    }

    @Override
    public int getCount() {
        return strings.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return strings.get(position);
    }
}