package com.goodmoring;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Date;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {
    public Boolean booleanCheckBox[] = {true, false, true};


    static public long arriveTime = (new Date(2017, 7, 30, 8, 0, 0)).getTime();
    static public String destination = "台北火車站";
    TextView TVArrivalTime;
    ImageView iVSetting, IVTranslit;
    ImageButton iBtnAdd, iBtnCheckAlarm1, iBtnCheckAlarm2, iBtnCheckAlarm3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }


    protected void onStart(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        TVArrivalTime.setText();
        long time = (long) Math.floor((arriveTime / 1000));
        // transitDuration  新增 mode=transit
        new HttpAsyncTask().execute(String.format("https://maps.googleapis.com/maps/api/distancematrix/json?origins=%s&destinations=%s&mode=transit&key=AIzaSyC1DewjeBNznTqfRz_xU6HWGy984yNWkZ8&arrival_time=%d", "花博圓山精豔館", destination, time));
        // motorDuration    新增 avoid=highways
        new HttpAsyncTask().execute(String.format("https://maps.googleapis.com/maps/api/distancematrix/json?origins=%s&destinations=%s&avoid=highways&key=AIzaSyC1DewjeBNznTqfRz_xU6HWGy984yNWkZ8&arrival_time=%d", "花博圓山精豔館", destination, time));


    }


    void initView() {
        iBtnAdd = (ImageButton) findViewById(R.id.iBtn_add);
        iBtnCheckAlarm1 = (ImageButton) findViewById(R.id.iBtn_checkAlarm1);
        iBtnCheckAlarm2 = (ImageButton) findViewById(R.id.iBtn_checkAlarm2);
        iBtnCheckAlarm3 = (ImageButton) findViewById(R.id.iBtn_checkAlarm3);
        TVArrivalTime = (TextView) findViewById(R.id.TV_arrivalTime);
        IVTranslit = (ImageView) findViewById(R.id.IV_translit);
        iVSetting = (ImageView) findViewById(R.id.IV_setting);

        iVSetting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialog();
            }
        });

        iBtnCheckAlarm1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if ((booleanCheckBox[0])) {
                    iBtnCheckAlarm1.setImageResource(R.mipmap.checkbox_off);
                    booleanCheckBox[0] = false;
                } else {
                    iBtnCheckAlarm1.setImageResource(R.mipmap.checkbox_on);
                    booleanCheckBox[0] = true;
                }
            }
        });
        iBtnCheckAlarm2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if ((booleanCheckBox[1])) {
                    iBtnCheckAlarm2.setImageResource(R.mipmap.checkbox_off);
                    booleanCheckBox[1] = false;
                } else {
                    iBtnCheckAlarm2.setImageResource(R.mipmap.checkbox_on);
                    booleanCheckBox[1] = true;
                }
            }
        });
        iBtnCheckAlarm3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if ((booleanCheckBox[2])) {
                    iBtnCheckAlarm3.setImageResource(R.mipmap.checkbox_off);
                    booleanCheckBox[2] = false;
                } else {
                    iBtnCheckAlarm3.setImageResource(R.mipmap.checkbox_on);
                    booleanCheckBox[2] = true;
                }
            }
        });

        iVSetting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setClass(MainActivity.this, SettingActivity.class);
                startActivity(intent);
            }
        });

        IVTranslit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // dirflg=r 公共運輸                     ttype=arr 時間種類(到達時間)  time 時間為
                String uri = String.format(Locale.ENGLISH, "http://maps.google.com/maps?dirflg=r&saddr=%s&daddr=%s", "花博公園精豔館", destination);
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));
                intent.setPackage("com.google.android.apps.maps");
                startActivity(intent);
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

    private class HttpAsyncTask extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... urls) {
            return GET(urls[0]);
        }

        // onPostExecute displays the results of the AsyncTask.
        @Override
        protected void onPostExecute(String result) {
            try {

                // json > rows > array > elements > array > duration
                JSONObject json = new JSONObject(result);
                JSONObject rows = json.getJSONArray("rows").getJSONObject(0);
                JSONObject elements = rows.getJSONArray("elements").getJSONObject(0);
                JSONObject duration = elements.getJSONObject("duration");
                // 費用代表是 transit 、無費用是driving
                if (elements.has("fare")) {
                    Toast.makeText(MainActivity.this, "搭公共運輸預計需" + duration.getString("text"), Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(MainActivity.this, "騎乘機車預計需" + duration.getString("text"), Toast.LENGTH_SHORT).show();
                    // TV_alarmTime
                }
            } catch (JSONException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }

        private String GET(String url) {
            InputStream inputStream = null;
            String result = "";
            try {

                // create HttpClient
                HttpClient httpclient = new DefaultHttpClient();

                // make GET request to the given URL
                HttpResponse httpResponse = httpclient.execute(new HttpGet(url));

                // receive response as inputStream
                inputStream = httpResponse.getEntity().getContent();

                // convert inputstream to string
                if (inputStream != null)
                    result = convertInputStreamToString(inputStream);
                else
                    result = "Did not work!";

            } catch (Exception e) {
                Log.d("InputStream", e.getLocalizedMessage());
            }
            return result;
        }

        private String convertInputStreamToString(InputStream inputStream) throws IOException {
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            String line = "";
            String result = "";
            while ((line = bufferedReader.readLine()) != null)
                result += line;

            inputStream.close();
            return result;

        }
    }
}
