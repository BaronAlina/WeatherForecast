package com.example.weatherforecast;

import androidx.appcompat.app.AppCompatActivity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {

    private TextView tempText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tempText = (TextView) findViewById(R.id.temp_txt);
        registerReceiver(receiver, new IntentFilter(MyService.CHANNEL));
        Intent intent = new Intent(this, MyService.class);
        startService(intent);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Intent intent = new Intent(this, MyService.class);
        stopService(intent);
    }

    protected BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            try {
                JSONObject json = new JSONObject(intent.getStringExtra(MyService.INFO));
                JSONArray gisArray = json.getJSONArray("gis");
                tempText.setText(gisArray.toString());
            } catch (JSONException e) {
                Toast.makeText(MainActivity.this, "Wring JSON format", Toast.LENGTH_LONG).show();
            }
        }
    };
}