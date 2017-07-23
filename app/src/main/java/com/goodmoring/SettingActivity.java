package com.goodmoring;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

/**
 * Created by Nick on 2017/7/23.
 */

public class SettingActivity extends Activity {
    ImageButton iBtnCheckRoute,iBtnFinish,iBtnCancel;
    EditText ETDestination;
    TextView TVArrivalTime;
    long arrivalTime;
    long durationTransit,durationMotor;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        TVArrivalTime = (TextView)findViewById(R.id.TV_arrivalTime);
        ETDestination = (EditText)findViewById(R.id.ET_destionation);
        iBtnCheckRoute = (ImageButton)findViewById(R.id.iBtn_checkRoute);
        iBtnFinish = (ImageButton)findViewById(R.id.iBtn_finish);
        iBtnCancel = (ImageButton)findViewById(R.id.iBtn_cancel);

        iBtnCheckRoute.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String destination = ETDestination.getText().toString();
                // 騎機車族群
                String url = String.format("http://maps.google.com/maps?saddr=圓山花博爭豔館&daddr=%s&dirflg=h",destination);
                //new一個intent物件，並指定Activity切換的class
                Intent intent = new Intent();
                intent.setClass(SettingActivity.this, MapActivity.class);
                //new一個Bundle物件，並將要傳遞的資料傳入
                Bundle bundle = new Bundle();
                bundle.putString("url", url);
                //將Bundle物件assign給intent
                intent.putExtras(bundle);
                //切換Activity
                startActivity(intent);
            }
        });

        TVArrivalTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        iBtnFinish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String destination = ETDestination.getText().toString();
                long time=System.currentTimeMillis();

                MainActivity.destination = destination;
                MainActivity.arriveTime = time;
                finish();
            }
        });

        iBtnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
}