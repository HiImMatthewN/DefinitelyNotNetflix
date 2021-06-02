package com.example.pinayflix.local.database;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.example.pinayflix.local.dao.SavedItemDao;
import com.example.pinayflix.model.datamodel.SavedItem;

@Database(entities = {SavedItem.class},version = 1,exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {
    public abstract SavedItemDao savedItemDao();

}
