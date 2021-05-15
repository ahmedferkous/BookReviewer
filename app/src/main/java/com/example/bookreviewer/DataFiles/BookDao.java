package com.example.bookreviewer.DataFiles;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.bookreviewer.Models.VolumeModel;

import java.util.List;

@Dao
public interface BookDao {
    @Insert
    void insertFavouriteBook(VolumeModel.Items Book);

    @Delete
    void deleteBook(VolumeModel.Items Book);

    @Query("SELECT * FROM favourite_books")
    List<VolumeModel.Items> getAllFavouriteBooks();

    @Query("SELECT * FROM favourite_books WHERE primary_key=:primary_id")
    VolumeModel.Items getBookById(int primary_id);
}
