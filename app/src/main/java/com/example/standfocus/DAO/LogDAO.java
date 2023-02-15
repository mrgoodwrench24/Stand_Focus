package com.example.standfocus.DAO;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.example.standfocus.Entity.Log;

import java.util.List;

@Dao
public interface LogDAO {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(Log log);

    @Update
    void update(Log log);

    @Delete
    void delete(Log log);

    @Query("SELECT * FROM Log ORDER BY date ASC")
    List<Log> getAllLogs();
}
