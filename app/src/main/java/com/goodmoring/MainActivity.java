package com.goodmoring;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;

public class MainActivity extends AppCompatActivity {
    public Boolean booleanCheckBox[] = {true,false,true};
    ImageButton iBtnAdd,iBtnCheckAlarm1,iBtnCheckAlarm2,iBtnCheckAlarm3;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }

    void initView(){
        iBtnAdd =  (ImageButton)findViewById(R.id.iBtn_add);
        iBtnCheckAlarm1 =  (ImageButton)findViewById(R.id.iBtn_checkAlarm1);
        iBtnCheckAlarm2 =  (ImageButton)findViewById(R.id.iBtn_checkAlarm2);
        iBtnCheckAlarm3 =  (ImageButton)findViewById(R.id.iBtn_checkAlarm3);

        iBtnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialog();
            }
        });

        iBtnCheckAlarm1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if((booleanCheckBox[0])){
                    iBtnCheckAlarm1.setImageResource(R.mipmap.checkbox_off);
                    booleanCheckBox[0] = false;
                }else{
                    iBtnCheckAlarm1.setImageResource(R.mipmap.checkbox_on);
                    booleanCheckBox[0] = true;
                }
            }
        });
        iBtnCheckAlarm2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if((booleanCheckBox[1])){
                    iBtnCheckAlarm2.setImageResource(R.mipmap.checkbox_off);
                    booleanCheckBox[1] = false;
                }else{
                    iBtnCheckAlarm2.setImageResource(R.mipmap.checkbox_on);
                    booleanCheckBox[1] = true;
                }
            }
        });
        iBtnCheckAlarm3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if((booleanCheckBox[2])){
                    iBtnCheckAlarm3.setImageResource(R.mipmap.checkbox_off);
                    booleanCheckBox[2] = false;
                }else{
                    iBtnCheckAlarm3.setImageResource(R.mipmap.checkbox_on);
                    booleanCheckBox[2] = true;
                }
            }
        });
    }

    private void showDialog() {
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        Fragment prev = getFragmentManager().findFragmentByTag("dialog");
        if (prev != null) {
            ft.remove(prev);
        }
        ft.addToBackStack(null);
        // Create and show the dialog.
        android.app.DialogFragment newFragment = new addDialogFragment();
        newFragment.show(ft, "dialog");
    }

}
