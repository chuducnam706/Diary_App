package com.example.data;

import androidx.room.Database;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

@Database(entities = {DiaryEntity.class}, version = 1)
@TypeConverters({ConverLocaleDateTime.class})
public abstract class AppDatabase extends RoomDatabase {

 public abstract DiaryDao diaryDao();


}
