package com.example.bookreviewer.DataFiles;

import com.example.bookreviewer.Models.VolumeModel;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

// TODO: 15/05/2021 optimize endpoints in a fashion that fits what the user wants to do (i.e see next page of results, using keywords like inauthor: do stuff, etc)
public interface BookEndPoint {
    @GET("/books/v1/volumes")
    Call<VolumeModel> getVolumes(@Query("q") String keyword, @Query("maxResults") String maxResults, @Query("startIndex") int startIndex);
}
