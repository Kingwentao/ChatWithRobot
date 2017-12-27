package com.example.robotinterction.activity;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.robotinterction.R;

/**
 * 注册账号界面
 */
public class RegisteActivity extends AppCompatActivity {

    //注册按钮
    private Button registeButton;
    //取消祖册按钮
    private Button cancelRegisteButton;
    //用户账户
    private EditText registeAccount;
    //用户密码
    private EditText registePassword;
    //用户昵称
    private EditText nickName;
    //取消祖册按钮
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registe);
        registeButton = (Button) findViewById(R.id.button);
        cancelRegisteButton = (Button) findViewById(R.id.cancel_registe);
        registeAccount = (EditText) findViewById(R.id.account);
        registePassword = (EditText) findViewById(R.id.passeord);
        nickName = (EditText) findViewById(R.id.nickName);
        registeButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                SharedPreferences.Editor editor = getSharedPreferences("accountData", MODE_PRIVATE).edit();
                String account=registeAccount.getText().toString();
                String password=registePassword.getText().toString();
                String nickname=nickName.getText().toString();
                editor.putString("name", account);
                editor.putString("password",password);
                editor.putString("nickname",nickname);
                editor.commit();
                if(TextUtils.isEmpty(account)||TextUtils.isEmpty(password)||TextUtils.isEmpty(nickname)){
                    Toast.makeText(getApplicationContext(), "请填入完整信息", Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(getApplicationContext(), "注册成功", Toast.LENGTH_SHORT).show();
                    finish();

                }


            }
        });
        cancelRegisteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "您取消了注册", Toast.LENGTH_SHORT).show();
                finish();
            }
        });
    }
}

