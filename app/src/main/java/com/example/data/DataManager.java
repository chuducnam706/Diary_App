package com.example.data;

import android.content.Context;

import androidx.room.Room;

public class DataManager {

    private static DataManager dataManager;

    public DataManager(){ }

    public static DataManager getInstance(){
        if(dataManager == null){
            dataManager = new DataManager();
        }
        return dataManager;
    }

    public  AppDatabase createDataBase(Context context){
        return Room.databaseBuilder(context, AppDatabase.class, "db").fallbackToDestructiveMigration().allowMainThreadQueries().build();
    }

}
