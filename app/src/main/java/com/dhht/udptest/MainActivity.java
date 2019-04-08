package com.dhht.udptest;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.dhht.udptest.threadpool.AppExecutors;

import java.text.SimpleDateFormat;

/**
 * @author dhht
 */
public class MainActivity extends AppCompatActivity {

    TextView tvMsg;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvMsg = findViewById(R.id.tvMsg);

        new ReceiveUDP(AppExecutors.getInstance(), new ReceiveUDP.MsgCallback() {
            @Override
            public void msgBack(String msg) {
                Toast.makeText(MainActivity.this, msg, Toast.LENGTH_SHORT).show();
                String time = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(System.currentTimeMillis());
                tvMsg.setText(time + "\n" + msg + "\n\n" + tvMsg.getText().toString());
            }
        }).waiteMsg(8989);

        findViewById(R.id.btHello).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, "Hello", Toast.LENGTH_SHORT).show();
            }
        });


    }


}
