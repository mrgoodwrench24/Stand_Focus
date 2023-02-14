package com.example.standfocus.Database;
import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.standfocus.DAO.LogDAO;
import com.example.standfocus.Entity.Log;
    @Database(entities = {Log.class}, version = 1, exportSchema = false)
    public abstract class LogDatabaseBuilder extends RoomDatabase {
        public abstract LogDAO logDAO();

        private static volatile LogDatabaseBuilder INSTANCE;

        static LogDatabaseBuilder getDatabase(final Context context) {
            if (INSTANCE == null) {
                synchronized (LogDatabaseBuilder.class) {
                    if (INSTANCE == null) {
                        INSTANCE = Room.databaseBuilder(context.getApplicationContext(), LogDatabaseBuilder.class, "LogDatabase.db")
                                .fallbackToDestructiveMigration()
                                .build();
                    }
                }
            }
            return INSTANCE;
        }


    }
