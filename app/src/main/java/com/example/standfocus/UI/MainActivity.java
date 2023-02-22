package com.example.standfocus.UI;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.standfocus.Database.Repository;
import com.example.standfocus.Entity.StandLog;
import com.example.standfocus.R;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private static final long TIMER_INTERVAL = 500L;
    private static final long MILLISECONDS_IN_SECOND = 1000L;
    private static final long SECONDS_IN_MINUTE = 60L;
    private static final long SECONDS_IN_HOUR = 3600L;

    private long startTime = 0L;
    private long totalStandTime = 0L;
    private long totalSitTime = 0L;

    private long totalTime = 0L;
    
    private StandLog currentDay = null;

    private int logID = -99;

    List<StandLog> allLogs;

    Repository repository;

    private LocalDate currentDate;

    private String currentDayString;
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
            long total = 0l;
            TextView current = null;
            if(standing){
                total = totalStandTime;
                current = standTextView;
            }
            else if(sitting){
                total = totalSitTime;
                current = sitTextView;
            }


            long millis = ((System.currentTimeMillis() - startTime) + total);
            int seconds = (int) (millis / MILLISECONDS_IN_SECOND);
            int minutes = seconds / (int) SECONDS_IN_MINUTE;
            int hours = seconds / (int) SECONDS_IN_HOUR;
            current.setText(String.format("%d:%02d:%02d", hours, minutes, seconds % SECONDS_IN_MINUTE));

            timerHandler.postDelayed(this, TIMER_INTERVAL);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        repository = new Repository(getApplication());

        standTextView = findViewById(R.id.standTimeTextView);
        sitTextView = findViewById(R.id.sitTimeTextView);
        standButton = (Button) findViewById(R.id.standButton);
        sitButton = (Button) findViewById(R.id.sitButton);
        sitPause = findViewById(R.id.sitPause);
        standPause = findViewById(R.id.standPause);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            currentDate = LocalDate.now();
        }
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            DateTimeFormatter sfc = DateTimeFormatter.ofPattern("MM/dd/yyyy");
            currentDayString = currentDate.format(sfc);
        }

        allLogs = repository.getAllLogs();

        if(allLogs.isEmpty()){
            currentDay = new StandLog(1,currentDayString,0,0,0,0);
            repository.insert(currentDay);
            standTextView.setText("0:00:00");
            sitTextView.setText("0:00:00");
        }
        else{
            for(int i = 0; i < allLogs.size(); i++){
                if(allLogs.get(i).getDate().equals(currentDayString)){
                    currentDay = allLogs.get(i);
                    totalSitTime = currentDay.getSitTime();
                    totalStandTime = currentDay.getStandTime();
                    totalTime = currentDay.getTotalTime();
                    logID = -99;
                    setViews();
                    break;
                }
            }

            if(logID == -99){
                logID = repository.getAllLogs().get(repository.getAllLogs().size() - 1).getLogID() + 1;
                currentDay = new StandLog(logID,currentDayString,0,0,0,0);
                repository.insert(currentDay);
            }

        }
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
                    totalTime = totalSitTime + totalStandTime;
                    currentDay.setStandTime(totalStandTime);
                    currentDay.setTotalTime(totalTime);
                    repository.update(currentDay);
                    break;
                case R.id.sitPause:
                    sitPause.setEnabled(false);
                    sitPause.setVisibility(View.INVISIBLE);
                    sitButton.setEnabled(true);
                    sitButton.setVisibility(View.VISIBLE);
                    sitting = false;
                    totalSitTime = totalSitTime + (System.currentTimeMillis() - startTime);
                    totalTime = totalSitTime + totalStandTime;
                    currentDay.setSitTime(totalSitTime);
                    currentDay.setTotalTime(totalTime);
                    repository.update(currentDay);
                    break;

            }
        }
    }

    private void setViews(){
        long sitTime = currentDay.getSitTime();
        long standTime = currentDay.getStandTime();

        int seconds = (int) (sitTime / MILLISECONDS_IN_SECOND);
        int minutes = seconds / (int) SECONDS_IN_MINUTE;
        int hours = seconds / (int) SECONDS_IN_HOUR;
        sitTextView.setText(String.format("%d:%02d:%02d", hours, minutes, seconds % SECONDS_IN_MINUTE));

        seconds = (int) (standTime / MILLISECONDS_IN_SECOND);
        minutes = seconds / (int) SECONDS_IN_MINUTE;
        hours = seconds / (int) SECONDS_IN_HOUR;
        standTextView.setText(String.format("%d:%02d:%02d", hours, minutes, seconds % SECONDS_IN_MINUTE));

    }
}
