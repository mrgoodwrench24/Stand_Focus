package com.example.standfocus.Entity;

import java.time.LocalDate;

public class Log {
    private LocalDate date;

    private long standTime;

    private long standGoal;

    private long sitTime;

    private long totalTime;

    public Log(LocalDate date, long standTime, long standGoal, long sitTime, long totalTime) {
        this.date = date;
        this.standTime = standTime;
        this.standGoal = standGoal;
        this.sitTime = sitTime;
        this.totalTime = totalTime;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public long getStandTime() {
        return standTime;
    }

    public void setStandTime(long standTime) {
        this.standTime = standTime;
    }

    public long getStandGoal() {
        return standGoal;
    }

    public void setStandGoal(long standGoal) {
        this.standGoal = standGoal;
    }

    public long getSitTime() {
        return sitTime;
    }

    public void setSitTime(long sitTime) {
        this.sitTime = sitTime;
    }

    public long getTotalTime() {
        return totalTime;
    }

    public void setTotalTime(long totalTime) {
        this.totalTime = totalTime;
    }
}
