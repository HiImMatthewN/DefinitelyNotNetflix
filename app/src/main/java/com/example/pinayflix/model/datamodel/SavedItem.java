package com.example.pinayflix.model.datamodel;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "savedItems")
public class SavedItem {
    @PrimaryKey
    @ColumnInfo(name ="id")
    private int id;
    @ColumnInfo(name ="name")
    private String name;
    @ColumnInfo(name = "poster_path")
    private String posterPath;

    public SavedItem(int id, String name, String posterPath) {
        this.id = id;
        this.name = name;
        this.posterPath = posterPath;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getPosterPath() {
        return posterPath;
    }
}
