package com.example.standfocus.UI;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.standfocus.R;

public class MainActivity extends AppCompatActivity {
    private static final long TIMER_INTERVAL = 500L;
    private static final long MILLISECONDS_IN_SECOND = 1000L;
    private static final long SECONDS_IN_MINUTE = 60L;
    private static final long SECONDS_IN_HOUR = 3600L;

    private long startTime = 0L;
    private long totalStandTime = 0L;

    private long totalSitTime = 0L;
    private Handler timerHandler = new Handler();
    private TextView standTextView;
    private TextView sitTextView;
    private ImageButton sitPause;
    private ImageButton standPause;
    private Button sitButton;
    private Button standButton;
    private boolean isTimerRunning = false;
    private boolean sitting = false;
    private boolean standing = false;
    private Runnable timerRunnable = new Runnable() {
        @Override
        public void run() {
            if(standing){
                long millis = ((System.currentTimeMillis() - startTime) + totalStandTime);
                int seconds = (int) (millis / MILLISECONDS_IN_SECOND);
                int minutes = seconds / (int) SECONDS_IN_MINUTE;
                int hours = seconds / (int) SECONDS_IN_HOUR;
                standTextView.setText(String.format("%d:%02d:%02d", hours, minutes, seconds % SECONDS_IN_MINUTE));
            }
            else if(sitting){
                long millis = ((System.currentTimeMillis() - startTime) + totalSitTime);
                int seconds = (int) (millis / MILLISECONDS_IN_SECOND);
                int minutes = seconds / (int) SECONDS_IN_MINUTE;
                int hours = seconds / (int) SECONDS_IN_HOUR;
                sitTextView.setText(String.format("%d:%02d:%02d", hours, minutes, seconds % SECONDS_IN_MINUTE));
            }

            timerHandler.postDelayed(this, TIMER_INTERVAL);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        standTextView = findViewById(R.id.standTimeTextView);
        sitTextView = findViewById(R.id.sitTimeTextView);
        standButton = (Button) findViewById(R.id.standButton);
        sitButton = (Button) findViewById(R.id.sitButton);
        sitPause = findViewById(R.id.sitPause);
        standPause = findViewById(R.id.standPause);

    }

    public void startTimer(View view){
        int buttonID = view.getId();
        if (!isTimerRunning) {
            switch(buttonID){
                case R.id.standButton:
                    standButton.setEnabled(false);
                    standButton.setVisibility(View.INVISIBLE);
                    standPause.setEnabled(true);
                    standPause.setVisibility(View.VISIBLE);
                    standing = true;
                    startTime = System.currentTimeMillis();
                    break;
                case R.id.sitButton:
                    sitButton.setEnabled(false);
                    sitButton.setVisibility(View.INVISIBLE);
                    sitPause.setEnabled(true);
                    sitPause.setVisibility(View.VISIBLE);
                    sitting = true;
                    startTime = System.currentTimeMillis();
                    break;
            }

            timerHandler.postDelayed(timerRunnable, 0);
            isTimerRunning = true;
        }
    }

    public void stopTimer(View view){
        int buttonID = view.getId();

        if (isTimerRunning) {
            timerHandler.removeCallbacks(timerRunnable);
            isTimerRunning = false;
            switch(buttonID){
                case R.id.standPause:
                    standPause.setEnabled(false);
                    standPause.setVisibility(View.INVISIBLE);
                    standButton.setEnabled(true);
                    standButton.setVisibility(View.VISIBLE);
                    standing = false;
                    totalStandTime = totalStandTime + (System.currentTimeMillis() - startTime);
                    break;
                case R.id.sitPause:
                    sitPause.setEnabled(false);
                    sitPause.setVisibility(View.INVISIBLE);
                    sitButton.setEnabled(true);
                    sitButton.setVisibility(View.VISIBLE);
                    sitting = false;
                    totalSitTime = totalSitTime + (System.currentTimeMillis() - startTime);
                    break;

            }
        }
    }
}
