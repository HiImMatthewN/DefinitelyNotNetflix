package com.example.pinayflix.model.datamodel;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "savedItems")
public class SavedItem extends Item {
    @PrimaryKey
    @ColumnInfo(name ="id")
    private int id;
    @ColumnInfo(name ="name")
    private String title;
    @ColumnInfo(name = "poster_path")
    private String posterPath;

    public SavedItem(int id, String title, String posterPath) {
        this.id = id;
        this.title = title;
        this.posterPath = posterPath;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getPosterPath() {
        return posterPath;
    }
}
