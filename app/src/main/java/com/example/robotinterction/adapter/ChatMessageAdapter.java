package com.example.robotinterction.adapter;

import android.content.Context;
import android.icu.text.SimpleDateFormat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.robotinterction.R;
import com.example.robotinterction.bean.ChatMessage;
import com.example.robotinterction.bean.Headers;

import java.util.List;

/**
 * Created by 金文韬 on 2017/12/5.
 */

public class ChatMessageAdapter extends BaseAdapter {
    private LayoutInflater mInflater;
    private List<ChatMessage> mData;
    private List<Headers> mHeadList;
    private List<Integer> mHeaders;
    private int mPosition;

    public ChatMessageAdapter() {
      super();
    }


    public ChatMessageAdapter(Context context, List<ChatMessage> mData,List<Integer> mHeaders,int mPosition) {
        mInflater= LayoutInflater.from(context);
        this.mData=mData;
        this.mHeaders=mHeaders;
        this.mPosition=mPosition;
    }

    public void setPostion(int postion){
        mPosition=postion;
    }
    @Override
    public int getCount() {
        return mData.size();
    }

    @Override
    public Object getItem(int position) {
        return mData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getViewTypeCount() {
        return 2;
    }

    /**
     * 获取两种消息的类型
     * @param position
     * @return 0 收到的消息 1 发出去的消息
     */
    @Override
    public int getItemViewType(int position) {
        ChatMessage chatMessage=mData.get(position);
        if (chatMessage.getType()== ChatMessage.Type.InComing){
            return 0;
        }
        return 1;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        ChatMessage chatMessage=mData.get(position);
        TextView mDate;
        TextView mMsg;
        View view=null;
            if (getItemViewType(position)==0){
                view=mInflater.inflate(R.layout.item_from_msg,null,false);
                mDate=(TextView) view.findViewById(R.id.nowtime);
                mMsg=(TextView) view.findViewById(R.id.from_msg);
                ImageView mLeftHeader = (ImageView) view.findViewById(R.id.left_header);
               mLeftHeader.setImageResource(R.drawable.robot);
                //显示日期
                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
                    SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    mDate.setText(df.format(chatMessage.getDate()));
                    //展示消息
                    mMsg.setText(chatMessage.getMsg());
                }
            }else {
                view=mInflater.inflate(R.layout.item_send_msg,null,false);
               mDate=(TextView)view.findViewById(R.id.send_nowtime);
               mMsg=(TextView)view.findViewById(R.id.send_msg);
                //显示日期
                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
                    SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    mDate.setText(df.format(chatMessage.getDate()));
                }
                ImageView mRightHeader = (ImageView) view.findViewById(R.id.right_header);
                mRightHeader.setImageResource(mHeaders.get(mPosition));
                //展示消息
                mMsg.setText(chatMessage.getMsg());
            }
        return view;
    }

   /* @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ChatMessage chatMessage=mData.get(position);
        ViewHolder viewHolder=null;
        if (convertView==null){
            if (getItemViewType(position)==0){
                convertView=mInflater.inflate(R.layout.item_from_msg,null,false);
                viewHolder=new ViewHolder();
                viewHolder.mDate=(TextView) convertView.findViewById(R.id.nowtime);
                viewHolder.mMsg=(TextView) convertView.findViewById(R.id.from_msg);
               //viewHolder.mHeader=(ImageView)convertView.findViewById(R.id.left_header);
               //viewHolder.mHeader.setImageResource(mHeaders.get(0));
            }else {
                convertView=mInflater.inflate(R.layout.item_send_msg,null,false);
                viewHolder=new ViewHolder();
                viewHolder.mDate=(TextView) convertView.findViewById(R.id.send_nowtime);
                viewHolder.mMsg=(TextView) convertView.findViewById(R.id.send_msg);
                //展示头像
                viewHolder.mHeader=(ImageView)convertView.findViewById(R.id.right_header);
                Log.d("headposition", "getView: "+mPosition);
            }
            convertView.setTag(viewHolder);
        }else {
            viewHolder=(ViewHolder) convertView.getTag();
        }

       //显示日期
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            viewHolder.mDate.setText(df.format(chatMessage.getDate()));
        }
        //展示消息
        viewHolder.mMsg.setText(chatMessage.getMsg());
        //展示头像
        //viewHolder.mHeader.setImageResource(mHeaders.get(mPosition));
//        viewHolder.mHeader=(ImageView)convertView.findViewById(R.id.right_header);
//        viewHolder.mHeader.setImageResource(mHeaders.get(mPosition));
//        viewHolder.mFromHeader=(ImageView)convertView.findViewById(R.id.left_header);
//        viewHolder.mFromHeader.setImageResource(mHeaders.get(0));
        viewHolder.mHeader.setImageResource(mHeaders.get(mPosition));
        Log.d("headposition", "getView: "+mPosition);

        return convertView;
    }
    public final class ViewHolder{
        TextView mDate;
        TextView mMsg;
        ImageView mHeader;
        ImageView mFromHeader;
    }*/
}
