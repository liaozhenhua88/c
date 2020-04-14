package com.liaozhenhua.p204;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private MyBroadcast mb;
    private IntentFilter iftenr;
    private LocalBroadcastManager lbm;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //注册
        mb = new MyBroadcast();
        iftenr = new IntentFilter();
        iftenr.addAction("cctv_1");
        lbm = LocalBroadcastManager.getInstance(this);
        lbm.registerReceiver(mb,iftenr);
        Button button = findViewById(R.id.button);
        //按钮的监听事件
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent("cctv_1");
                //发送
                lbm.sendBroadcast(intent);
            }
        });
    }
    @Override
    protected void onDestroy(){
        super.onDestroy();
        lbm.unregisterReceiver(mb);
    }
    class MyBroadcast extends BroadcastReceiver{
        @Override
        public void onReceive(Context context, Intent intent) {
            Toast.makeText(
                    context,
                    "我接受到了广播",
                    Toast.LENGTH_SHORT
            ).show();
        }
    }
}
