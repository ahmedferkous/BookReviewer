package com.example.bookreviewer.DataFiles;

import com.example.bookreviewer.Models.VolumeModel;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface BookEndPoint {
    @GET("/books/v1/volumes")
    Call<VolumeModel> getVolumes(@Query("q") String keywords, @Query("maxResults") String maxResults, @Query("startIndex") int startIndex);
}
