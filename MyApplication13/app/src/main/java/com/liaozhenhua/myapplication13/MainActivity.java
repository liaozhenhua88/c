package com.liaozhenhua.myapplication13;

import android.annotation.SuppressLint;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.liaozhenhua.myapplication13.com.liaozhenhua.utils.DBUtil;

import java.util.HashMap;

public class MainActivity extends AppCompatActivity {

    private Button btn_get_data;
    private TextView tv_data;

    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {

            switch (msg.what){
                case 0x11:
                    String s = (String) msg.obj;
                    tv_data.setText(s);
                    break;
                case 0x12:
                    String ss = (String) msg.obj;
                    tv_data.setText(ss);
                    break;
            }

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // 控件的初始化
        btn_get_data = findViewById(R.id.btn_get_data);
        tv_data = findViewById(R.id.tv_data);

        setListener();
    }

    /**
     * 设置监听
     */
    private void setListener() {

        // 按钮点击事件
        btn_get_data.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // 创建一个线程来连接数据库并获取数据库中对应表的数据
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        // 调用数据库工具类DBUtils的getInfoByName方法获取数据库表中数据
                        HashMap<String, Object> map = DBUtil.getInfoByName("Charger9527");
                        Message message = handler.obtainMessage();
                        if(map != null){
                            String s = "";
                            for (String key : map.keySet()){
                                s += key + ":" + map.get(key) + "\n";
                            }
                            message.what = 0x12;
                            message.obj = s;
                        }else {
                            message.what = 0x11;
                            message.obj = "查询结果为空";
                        }
                        // 发消息通知主线程更新UI
                        handler.sendMessage(message);
                    }
                }).start();

            }
        });

    }
}
