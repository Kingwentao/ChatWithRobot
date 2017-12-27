package com.example.robotinterction.activity;

import android.content.Intent;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;


import com.example.robotinterction.R;
import com.example.robotinterction.adapter.TabFragmentAdapter;
import com.example.robotinterction.bean.TextFragment;
import com.example.robotinterction.view.SlidingTabLayout;

import java.util.ArrayList;
import java.util.List;

public class ChooseChatBg extends FragmentActivity {
    private SlidingTabLayout topTabLayout;
    private ViewPager viewPager;
    private List<String> strings = new ArrayList<String>();
    private List<TextFragment> fragments = new ArrayList<>();
    //选中图片的位置
    private int mTabPosition;
    private String[] tabTitles = {"隔离区系","励志文字","紫色气泡","萌萌小新","可爱猫咪","清新风景"};
    private int[] chatBgs={R.drawable.chat_bg1,R.drawable.chat_bg2,R.drawable.chat_bg3,
            R.drawable.chat_bg4,R.drawable.chat_bg5,R.drawable.chat_bg6};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_chatbg);
        initFragments();
        initView();
    }

    private void initView() {
        topTabLayout = (SlidingTabLayout)findViewById(R.id.tablayout);
        viewPager = (ViewPager)findViewById(R.id.viewpager);
        viewPager.setAdapter(new TabFragmentAdapter(fragments, strings, getSupportFragmentManager(), this));
        topTabLayout.setupWithViewPager(viewPager);
        topTabLayout.setTabTextColors(getResources().getColor(R.color.radiobutton), getResources().getColor(R.color.radiobuttonzhong));

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                topTabLayout.redrawIndicator(position, positionOffset);
            }

            @Override
            public void onPageSelected(int position) {
                mTabPosition = position;
                Log.d("viewpager", "onPageSelected: " + "页面被选中");
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                Log.d("viewpager", "onPageSelected: " + "页面状态被改变");
            }
        });
    }
    private void initFragments() {
        TextFragment fragment;
        for (int chatBg : chatBgs) {
            fragment = new TextFragment();
            fragment.setChatBg(chatBg);
            fragments.add(fragment);
        }
        for (String tabTitle : tabTitles) {
            strings.add(tabTitle);
        }
    }

    //返回图片序号
    public void sureBg(View v)
    {
        Intent intent=new Intent();
        intent.putExtra("bg_return",mTabPosition);
        setResult(RESULT_OK,intent);
        Log.d("test", "sureBg: "+mTabPosition);
        finish();
    }

    //取消选择图片
    public void cancelBg(View v)
    {
        finish();
    }
}
