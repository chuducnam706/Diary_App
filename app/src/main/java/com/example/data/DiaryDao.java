package com.example.data;


import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.ArrayList;
import java.util.List;

@Dao
public interface DiaryDao {
    @Query("SELECT * FROM DiaryEntity")
    List<DiaryEntity> getAllDiaryEntities();

    @Insert
    void insertAll(DiaryEntity entity);

    @Delete
    void delete(DiaryEntity entity);

    @Update
    void upDateData(DiaryEntity entity);

    @Query("Select * From DiaryEntity Where title LIKE '%' || :title || '%'  ")
    List<DiaryEntity> search(String title);

    @Query("SELECT * FROM DiaryEntity WHERE id = :id LIMIT 1")
    DiaryEntity getDiaryById(int id);

}
