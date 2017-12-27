package com.example.robotinterction.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.example.robotinterction.R;

public class LoginActivity extends AppCompatActivity {

    private EditText accountEdit;
    private EditText passwordEdit;
    private Button login;
    private SharedPreferences pref;
    private SharedPreferences.Editor editor;
    private CheckBox rememberpass;
    private Button registe;
    private SharedPreferences pre;
    //是否记住密码
    private boolean isRemember;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        //初始化控件
        initView();
        //初始化控件的内容数据，
        initData();
        //登录与注册的响应事件
        clickListener();
    }

    /**
     * 初始化控件的内容数据
     * 是否加载记住密码的内容
     */
    private void initData() {
        pre = getSharedPreferences("accountData", MODE_PRIVATE);
        pref = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        isRemember = pref.getBoolean("remember_password", false);
        if(isRemember){
            //将账号和密码都设置在文本框内
            String account = pre.getString("name", "");
            String password = pre.getString("password", "");
            accountEdit.setText(account);
            passwordEdit.setText(password);
            rememberpass.setChecked(true);
        }
    }

    private void clickListener() {
        registe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, RegisteActivity.class);
                startActivity(intent);
            }
        });

        login.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                String account = accountEdit.getText().toString();
                String password = passwordEdit.getText().toString();
                if(TextUtils.isEmpty(account)||TextUtils.isEmpty(password)){
                    Toast.makeText(getApplication(), "账号或密码不能为空", Toast.LENGTH_SHORT).show();
                }else {
                    if (account.equals(pre.getString("name", "")) && password.equals(pre.getString("password", ""))) {
                        editor = pref.edit();
                        if (rememberpass.isChecked()) {
                            editor.putBoolean("remember_password", true);
                        } else {
                            editor.clear();
                        }
                        editor.commit();

                        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                        startActivity(intent);
                        finish();


                    } else {
                        Toast.makeText(LoginActivity.this, "用户名或密码不对", Toast.LENGTH_LONG).show();
                    }
                }
            }
        });
    }

    //初始化控件
    private void initView() {
        accountEdit = (EditText) findViewById(R.id.account);
        passwordEdit = (EditText) findViewById(R.id.passeord);
        login = (Button) findViewById(R.id.login);
        rememberpass = (CheckBox) findViewById(R.id.remember_password);
        registe = (Button) findViewById(R.id.registe);
    }

}
