package com.example.robotinterction.bean;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.robotinterction.R;

/**
 * Created by 金文韬 on 2017/12/19.
 */

public class TextFragment extends Fragment {

    private View rootView;
    private ImageView mChatImage;
    private int chatBg;

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment, container, false);
        mChatImage = (ImageView)rootView.findViewById(R.id.tv_content);
        mChatImage.setImageResource(chatBg);
        return rootView;
    }

    public void setChatBg(int chatBg){
        this.chatBg=chatBg;
    }

}