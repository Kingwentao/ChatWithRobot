package com.example.robotinterction.activity;

import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.NavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;


import com.example.robotinterction.adapter.ChatMessageAdapter;
import com.example.robotinterction.R;
import com.example.robotinterction.bean.ChatMessage;
import com.example.robotinterction.utils.HttpUtils;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.iflytek.cloud.RecognizerResult;
import com.iflytek.cloud.SpeechConstant;
import com.iflytek.cloud.SpeechError;
import com.iflytek.cloud.SpeechUtility;
import com.iflytek.cloud.ui.RecognizerDialog;
import com.iflytek.cloud.ui.RecognizerDialogListener;


import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;
import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;


public class MainActivity extends AppCompatActivity {
    private TextView robotName;
    private EditText sendMessage;
    private Button send;
    //消息的列表
    private ListView listView;
    private ChatMessageAdapter mAdapter;
    //聊天信息数据集合
    private List<ChatMessage> mData;
    //聊天背景集合
    private List<Integer> mChatBgList;
    //滑动面板控件
    private DrawerLayout mDrawLayout;
    //ToolBar控件
    private Toolbar toolbar;
    //侧滑面板
    private NavigationView navView;
    //头像
    private List<Integer> mHeaders;
    //侧滑面板的头像
    private CircleImageView mCircleImageView;
    //侧滑面板的用户账户
    private TextView mAccount;
    //侧滑面板的用户昵称
    private TextView mNickName;
    private Handler mHandler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
           //等待子线程返回数据
            ChatMessage fromMessage=(ChatMessage)msg.obj;
            mData.add(fromMessage);
            mAdapter.notifyDataSetChanged();
            //更新信息回滚到最后一行
            listView.setSelection(mAdapter.getCount());
        }
    };

    //选择某个头像后传回的位置
    private int mPosition;
    //通过布局打气筒为了获得子view的控件实例
    private LayoutInflater inflater;
    //返回的背景图片序号
    private int mReturnBgPosition;
    //返回的昵称
    private String mReturnName;
    //存储编辑者
    private SharedPreferences.Editor editor;
    //获取存储内容对象
    private SharedPreferences pref;
    //获取登录界面保存的账户信息
    private SharedPreferences loginPref;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        initData();
        initListener();
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        //取代原本的actionbar
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        pref=getSharedPreferences("data",MODE_PRIVATE);
        loginPref=getSharedPreferences("accountData",MODE_PRIVATE);
        //初始化保存的数据
        robotName.setText(pref.getString("name","小爱同学"));
        mAccount.setText(loginPref.getString("name",""));
        mNickName.setText(loginPref.getString("nickname",""));
        mCircleImageView.setImageResource(mHeaders.get(pref.getInt("header",0)));
        listView.setBackgroundResource(mChatBgList.get(pref.getInt("chatBg",0)));
        mAdapter.setPostion(pref.getInt("header",0));

        //讯飞语音接口配置1
        SpeechUtility.createUtility(this, SpeechConstant.APPID + "=5a2e6b9b");
        //配置录音权限
        if(ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.RECORD_AUDIO)!= PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(MainActivity.this,new String[]{
                    Manifest.permission.RECORD_AUDIO},1);
        }else{

        }
        //选择侧滑面板内部的条目
        switchNavItem();
    }

    /**
     * 实现侧滑面板内部的条目的点击事件
     */
    private void switchNavItem() {
        navView.setCheckedItem(R.id.change_robotname);
        navView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {

            @Override
            public boolean onNavigationItemSelected( MenuItem item) {
                switch (item.getItemId()){
                    case R.id.change_robotname:
                        Intent intentName=new Intent(MainActivity.this,ChangeName.class);
                        startActivityForResult(intentName,3);
                        break;
                    case R.id.change_header:
                        Intent intent=new Intent(MainActivity.this,ChooseHeader.class);
                        startActivityForResult(intent,1);
                        //侧滑面板头像初始化
                        break;
                    case R.id.change_bg:
                        Intent intent1=new Intent(MainActivity.this,ChooseChatBg.class);
                        startActivityForResult(intent1,2);
                        Log.d("test", "onNavigationItemSelected: "+"换背景了...");
                        break;
                    case R.id.clear_data:
                        mData.clear();
                        mAdapter.notifyDataSetChanged();
                        //保存聊天记录
                        saveChatMessage();
                        break;
                    case R.id.exit_app:
                        saveChatMessage();
                        finish();
                        break;
                    case R.id.relogin:
                        Intent intent2=new Intent(MainActivity.this,LoginActivity.class);
                        startActivity(intent2);
                        finish();
                    default:
                        break;
                }
                return true;
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode){
            case 1:
                if (resultCode==RESULT_OK){
                    int returnData=data.getIntExtra("head_return",0);
                    //把传回来的图片位置值保存
                    mPosition=returnData;
                    //把nav里的头像更新
                    mCircleImageView.setImageResource(mHeaders.get(mPosition));
                    //通过更新适配器数据来更新聊天信息里的头像
                    mAdapter.setPostion(returnData);
                    mAdapter.notifyDataSetChanged();
                    Log.d("return", "onActivityResult: "+returnData);
                    editor.putInt("header",mPosition);
                    editor.apply();
                }
                break;
            case 2:
                if (resultCode==RESULT_OK){
                    mReturnBgPosition=data.getIntExtra("bg_return",0);
                    listView.setBackgroundResource(mChatBgList.get(mReturnBgPosition));
                    editor.putInt("chatBg",mReturnBgPosition);
                    editor.apply();
                }
                break;
            case 3:
                if (resultCode==RESULT_OK){
                    mReturnName=data.getStringExtra("name_return");
                    robotName.setText(mReturnName);
                    editor.putString("name",mReturnName);
                    editor.apply();
                }
            default:
                break;
        }
    }

    /**
     * 初始化数据
     */
    private void initData() {
        mData=new ArrayList<ChatMessage>();
        //读取上次保存的信息
        getChatMessage();
        if (mData.size()==0) {
            mData.add(new ChatMessage("您好啊，小萌来和你聊骚了，哈哈哈", new Date(), ChatMessage.Type.InComing));
        }
        //初始化头像数据
        mHeaders=new ArrayList<>();
        mHeaders.add(R.drawable.initheader);
        mHeaders.add(R.drawable.senderhead);
        mHeaders.add(R.drawable.baqiboy);
        mHeaders.add(R.drawable.baqigirl);
        mHeaders.add(R.drawable.kenan);
        mHeaders.add(R.drawable.ali);
        mHeaders.add(R.drawable.girl);
        mHeaders.add(R.drawable.boy);
        //初始化聊天背景头像
        mChatBgList=new ArrayList<>();
        mChatBgList.add(R.drawable.chat_bg1);
        mChatBgList.add(R.drawable.chat_bg2);
        mChatBgList.add(R.drawable.chat_bg3);
        mChatBgList.add(R.drawable.chat_bg4);
        mChatBgList.add(R.drawable.chat_bg5);
        mChatBgList.add(R.drawable.chat_bg6);

        //初始化nav里的头像
        mCircleImageView.setImageResource(mHeaders.get(mPosition));
        //设配数据
        mAdapter=new ChatMessageAdapter(this,mData,mHeaders,mPosition);
        listView.setAdapter(mAdapter);
    }

    /**
     * 初始化控件
     */
    private void initView() {
        mDrawLayout = (DrawerLayout) findViewById(R.id.draw_layout);
        navView = (NavigationView) findViewById(R.id.nav_view);
        send = (Button) findViewById(R.id.sendbtn);
        sendMessage = (EditText) findViewById(R.id.ed_message);
        robotName = (TextView) findViewById(R.id.robot_name);
        listView = (ListView) findViewById(R.id.lv_message);
        //mCircleView不可以直接通过this.findViewById(R.id.circle_img)【空指针异常】;获取到
        //只能通过下面的步骤，先找到这个子布局才能获取到里面的实例
        inflater=(LayoutInflater)getSystemService(LAYOUT_INFLATER_SERVICE);
        //navView不能写null,虽然不报错但是实现不了这个控件拥有的方法，比如说setImageResource
        View view=inflater.inflate(R.layout.nav_header,navView);
        mCircleImageView =(CircleImageView)view.findViewById(R.id.circle_img);
        mAccount=(TextView)findViewById(R.id.maccount);
        mNickName=(TextView)findViewById(R.id.mnickname);
        editor=getSharedPreferences("data",MODE_PRIVATE).edit();
    }

    /**
     * 初始化监听器
     */
    private void initListener() {
        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //获取文本框的信息
                final String sendMsg=sendMessage.getText().toString();
                StringBuffer newSendMsg=new StringBuffer(sendMsg);
                //对字符换行处理
                skipLine(newSendMsg);
                //对要发送的消息处理并更新listView
                if(TextUtils.isEmpty(sendMsg)){
                    Toast.makeText(getApplicationContext(),"输入内容为空，不能发送",Toast.LENGTH_SHORT).show();
                    return;
                }
                sendMessage(newSendMsg);

                new Thread(){
                    @Override
                    public void run() {
                        ChatMessage fromMessage=HttpUtils.sendMessage(sendMsg);
                        StringBuffer fromNewString=new StringBuffer(fromMessage.toString());
                        skipLine(fromNewString);
                        Message m=Message.obtain();
                        m.obj=fromMessage;
                        mHandler.sendMessage(m);
                    }
                }.start();
            }
        });
    }

    /**
     * 启动录音
     */
    public void startAudio(View view){
        Toast.makeText(this,"开启录音...",Toast.LENGTH_SHORT).show();
        startSay();
    }

    /**
     * 语音对话框弹出，开始说话
     */
    private void startSay() {
        RecognizerDialog dialog = new RecognizerDialog(this,null);
        dialog.setParameter(SpeechConstant.LANGUAGE, "zh_cn");
        dialog.setParameter(SpeechConstant.ACCENT, "mandarin");
        dialog.setListener(new RecognizerDialogListener() {
            @Override
            public void onResult(RecognizerResult recognizerResult, boolean b) {
                printResult(recognizerResult);
            }
            @Override
            public void onError(SpeechError speechError) {
            }
        });
        dialog.show();
        Toast.makeText(this, "请开始说话", Toast.LENGTH_SHORT).show();
    }

    //把返回的结果放到消息框里
    private void printResult(RecognizerResult results) {
        String text = parseIatResult(results.getResultString());
        // 自动填写地址
        sendMessage.append(text);

    }
    /**
     * 解析结果返回字符串
     * @param json
     * @return
     */
    public static String parseIatResult(String json) {
        StringBuffer ret = new StringBuffer();
        try {
            JSONTokener tokener = new JSONTokener(json);
            JSONObject joResult = new JSONObject(tokener);
            JSONArray words = joResult.getJSONArray("ws");
            for (int i = 0; i < words.length(); i++) {
                // 转写结果词，默认使用第一个结果
                JSONArray items = words.getJSONObject(i).getJSONArray("cw");
                JSONObject obj = items.getJSONObject(0);
                ret.append(obj.getString("w"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ret.toString();
    }

    /**
     * 处理消息并发送消息
     * @param newSendMsg
     */
    private void sendMessage(StringBuffer newSendMsg) {
        ChatMessage toMessage=new ChatMessage();
        toMessage.setDate(new Date());
        toMessage.setMsg(newSendMsg.toString());
        toMessage.setType(ChatMessage.Type.OutComing);
        mData.add(toMessage);
        mAdapter.notifyDataSetChanged();
        listView.setSelection(mAdapter.getCount());
        sendMessage.setText("");
    }

    /**
     * 对输入的信息换行处理
     * @param s1
     */
    private void skipLine(StringBuffer s1) {
        int index;
        for(index=15;index<s1.length();index+=16){
            s1.insert(index, '\n');
        }
    }

    /**
     * ToolBar的内部控件的点击事件
     * @param item
     * home就是toolBar的菜单键
     * @return
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                mDrawLayout.openDrawer(GravityCompat.START);
                break;
        }
        return true;
    }

    /**
     * 把聊天记录保存下来
     */
   public void saveChatMessage(){
       //创建sp对象
       SharedPreferences sp = getSharedPreferences("SP_CHATMESSAGE_LIST",MODE_PRIVATE);
       Gson gson = new Gson();
       //将List转换成Json
       String jsonStr=gson.toJson(mData);
       SharedPreferences.Editor editor = sp.edit() ;
       editor.putString("KEY_CHAT_LIST_DATA", jsonStr) ; //存入json串
       editor.commit() ;  //提交
   }


    /**
     * 读取聊天记录
     */
    public void getChatMessage(){
        //创建sp对象,如果有key为"SP_CHATMESSAGE_LIST"的sp就取出
        SharedPreferences sp = getSharedPreferences("SP_CHATMESSAGE_LIST",MODE_PRIVATE);
        String chatMessage = sp.getString("KEY_CHAT_LIST_DATA","");  //取出key为"KEY_PEOPLE_DATA"的值，如果值为空，则将第二个参数作为默认值赋值
        if(chatMessage!="")  //防空判断
        {
            Gson gson = new Gson();
            mData=gson.fromJson(chatMessage, new TypeToken<List<ChatMessage>>() {}.getType()); //将json字符串转换成List集合
        }
    }

   //权限请求结果返回
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode){
            case 1:
                if (grantResults.length>0&&grantResults[0]==PackageManager.PERMISSION_GRANTED){

                }else{
                    Toast.makeText(this,"You denied the permission",Toast.LENGTH_SHORT);
                }
                break;
            default:
                break;
        }
    }
}
