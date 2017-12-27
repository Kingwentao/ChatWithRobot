package com.example.robotinterction.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.robotinterction.R;
import com.example.robotinterction.bean.Headers;

import java.util.List;

/**
 * Created by 金文韬 on 2017/12/15.
 */

public class HeaderAdapter extends ArrayAdapter<Headers> {


    private int resourceId;
    public HeaderAdapter(Context context,int textViewResourceId, List<Headers> objects) {
        super(context, textViewResourceId, objects);
        this.resourceId=textViewResourceId;
    }

    @Override
    public View getView(int position, View convertView,ViewGroup parent) {
        Headers headers= getItem(position);
        View view;
        if (convertView==null){
            view= LayoutInflater.from(getContext()).inflate(resourceId,parent,false);
        }else {
            view=convertView;
        }
        ImageView headImage=(ImageView) view.findViewById(R.id.header_image);
        TextView headName=(TextView) view.findViewById(R.id.header_name);
        headImage.setImageResource(headers.getImageId());
        headName.setText(headers.getHeaderName());
        return view;
    }
}
