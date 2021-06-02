package com.example.pinayflix.di;


import android.content.Context;

import androidx.room.Room;

import com.example.pinayflix.local.dao.SavedItemDao;
import com.example.pinayflix.local.database.AppDatabase;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
import dagger.hilt.android.qualifiers.ApplicationContext;
import dagger.hilt.components.SingletonComponent;

@Module
@InstallIn(SingletonComponent.class)
public class DatabaseModule {

    @Provides
    public SavedItemDao provideSavedItemDao(AppDatabase appDatabase) {
        return appDatabase.savedItemDao();

    }
    @Provides
    @Singleton
    public AppDatabase appDatabase(@ApplicationContext Context context) {
        return Room.databaseBuilder(context, AppDatabase.class, "saved_items_database.db").build();
    }


}
