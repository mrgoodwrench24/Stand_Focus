package com.example.standfocus.Database;
import android.app.Application;
import com.example.standfocus.DAO.LogDAO;
import com.example.standfocus.Entity.Log;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


public class Repository {
    private LogDAO mLogDAO;
    private List<Log> mAllLogs;

    private static int NUMBER_OF_THREADS=4;
    static final ExecutorService databaseExecutor= Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    public Repository(Application application){
        LogDatabaseBuilder db = LogDatabaseBuilder.getDatabase(application);
        mLogDAO = db.logDAO();

    }

    public List<Log> getAllLogs(){
        databaseExecutor.execute(()->{
            mAllLogs=mLogDAO.getAllLogs();
        });

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return mAllLogs;
    }

    public void insert(Log log){
        databaseExecutor.execute(()->{
            mLogDAO.insert(log);
        });

        try{
            Thread.sleep(1000);
        }catch(InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void update(Log log){
        databaseExecutor.execute(()->{
            mLogDAO.update(log);
        });

        try{
            Thread.sleep(1000);
        }catch(InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void delete(Log log){
        databaseExecutor.execute(()->{
            mLogDAO.delete(log);
        });

        try{
            Thread.sleep(1000);
        }catch(InterruptedException e) {
            e.printStackTrace();
        }
    }


}
