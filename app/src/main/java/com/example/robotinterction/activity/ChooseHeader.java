package com.example.robotinterction.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.robotinterction.R;
import com.example.robotinterction.adapter.ChatMessageAdapter;
import com.example.robotinterction.adapter.HeaderAdapter;
import com.example.robotinterction.bean.Headers;

import java.util.ArrayList;
import java.util.List;

public class ChooseHeader extends AppCompatActivity {
    private List<Headers> headList=new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_header);
        initHeads();
        HeaderAdapter adapter=new HeaderAdapter(ChooseHeader.this,R.layout.header_item,headList);
        ListView listView=(ListView) findViewById(R.id.lv_head);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Headers headers=headList.get(position);
                Toast.makeText(ChooseHeader.this,"您选择了"+headers.getHeaderName(),Toast.LENGTH_SHORT).show();
                Intent intent=new Intent();
                intent.putExtra("head_return",position);
                setResult(RESULT_OK,intent);
                finish();
            }
        });
    }

    //返回按钮
    public void backPre(View v){
        Log.d("test", "backPre: "+"fanhui");
        finish();
    }
    //初始化头像数据
    private void initHeads() {
            Headers boyHead=new Headers("帅气男孩",R.drawable.initheader);
            headList.add(boyHead);
            Headers girlHead=new Headers("可爱女孩",R.drawable.senderhead);
            headList.add(girlHead);
            Headers baqiBoy=new Headers("霸气侧漏男生",R.drawable.baqiboy);
            headList.add(baqiBoy);
            Headers baqiGirl=new Headers("霸气侧漏女生",R.drawable.baqigirl);
            headList.add(baqiGirl);
            Headers keNan=new Headers("柯南",R.drawable.kenan);
            headList.add(keNan);
            Headers aLi=new Headers("阿狸",R.drawable.ali);
            headList.add(aLi);
            Headers girl=new Headers("魅力女神",R.drawable.girl);
            headList.add(girl);
            Headers boy=new Headers("魅力男神",R.drawable.boy);
            headList.add(boy);
    }
}
