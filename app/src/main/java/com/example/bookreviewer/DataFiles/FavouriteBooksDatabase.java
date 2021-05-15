package com.example.bookreviewer.DataFiles;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.bookreviewer.Models.VolumeModel;

@Database(entities = {VolumeModel.Items.class}, version = 1)
public abstract class FavouriteBooksDatabase extends RoomDatabase {
    public abstract BookDao bookDao();
    private static FavouriteBooksDatabase instance;

    public static synchronized FavouriteBooksDatabase getInstance(Context context) {
        if (null == instance) {
            instance = Room.databaseBuilder(context, FavouriteBooksDatabase.class, "fav_books_db")
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return instance;
    }
}
