package com.example.robotinterction.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.example.robotinterction.R;

public class ChangeName extends AppCompatActivity {

    private EditText editName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_name);
        editName=(EditText)findViewById(R.id.ed_name);
    }
    //删除输入的姓名
    public void deleteName(View view){
        editName.setText("");
    }
    //确定输入的姓名
    public void sureName(View view){
        String name=editName.getText().toString().trim();
        Intent intent=new Intent();
        intent.putExtra("name_return",name);
        setResult(RESULT_OK,intent);
        finish();
    }

    //返回上一个活动
    public void backName(View view){
        finish();
    }
}
