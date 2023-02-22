package com.example.standfocus.DAO;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.example.standfocus.Entity.StandLog;

import java.util.List;

@Dao
public interface LogDAO {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(StandLog log);

    @Update
    void update(StandLog log);

    @Delete
    void delete(StandLog log);

    @Query("SELECT * FROM StandLog ORDER BY date ASC")
    List<StandLog> getAllLogs();
}
