package com.example.pinayflix.local.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.pinayflix.model.datamodel.SavedItem;

import java.util.List;

import io.reactivex.rxjava3.core.Single;

@Dao
public interface SavedItemDao {
    @Query("SELECT * FROM savedItems")
    List<SavedItem> getAll();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(SavedItem savedItem);

    @Query("DELETE FROM savedItems WHERE id = :id")
    void delete(int id);

    @Query("SELECT EXISTS(SELECT * FROM savedItems WHERE id = :id)")
    Single<Boolean> checkIfExists(int id);
}
