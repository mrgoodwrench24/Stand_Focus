package com.example.standfocus.UI;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.TextView;

import com.example.standfocus.R;

public class MainActivity extends AppCompatActivity {
    private static final long TIMER_INTERVAL = 500L;
    private static final long MILLISECONDS_IN_SECOND = 1000L;
    private static final long SECONDS_IN_MINUTE = 60L;
    private static final long SECONDS_IN_HOUR = 3600L;

    private long startTime = 0L;
    private Handler timerHandler = new Handler();
    private TextView standTextView;
    private boolean isTimerRunning = false;
    private Runnable timerRunnable = new Runnable() {
        @Override
        public void run() {
            long millis = System.currentTimeMillis() - startTime;
            int seconds = (int) (millis / MILLISECONDS_IN_SECOND);
            int minutes = seconds / (int) SECONDS_IN_MINUTE;
            int hours = seconds / (int) SECONDS_IN_HOUR;
            standTextView.setText(String.format("%d:%02d:%02d", hours, minutes, seconds % SECONDS_IN_MINUTE));
            timerHandler.postDelayed(this, TIMER_INTERVAL);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        standTextView = findViewById(R.id.standTimeTextView);
    }

    public void startTimer(View view){
        if (!isTimerRunning) {
            startTime = System.currentTimeMillis();
            timerHandler.postDelayed(timerRunnable, 0);
            isTimerRunning = true;
        }
    }

    public void stopTimer(View view){
        if (isTimerRunning) {
            timerHandler.removeCallbacks(timerRunnable);
            isTimerRunning = false;
        }
    }
}
