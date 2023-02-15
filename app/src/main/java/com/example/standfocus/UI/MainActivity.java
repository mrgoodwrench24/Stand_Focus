package com.example.standfocus.UI;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.TextView;

import com.example.standfocus.R;

public class MainActivity extends AppCompatActivity {
    private long startTime = 0L;
    private Handler timerHandler = new Handler();
    private TextView standTextView;
    private Runnable timerRunnable = new Runnable() {
        @Override
        public void run() {
            long millis = System.currentTimeMillis() - startTime;
            int seconds = (int) (millis / 1000);
            int minutes = seconds / 60;
            int hours = seconds / 3600;
            standTextView.setText(String.format("%d:%02d", hours, minutes, seconds));
            timerHandler.postDelayed(this,500);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        standTextView = (TextView) findViewById(R.id.standTimeTextView);
    }

    public void startTimer(View view){
        startTime = System.currentTimeMillis();
        timerHandler.postDelayed(timerRunnable, 0);
    }

    public void stopTimer(View view){
        timerHandler.removeCallbacks(timerRunnable);
    }

}