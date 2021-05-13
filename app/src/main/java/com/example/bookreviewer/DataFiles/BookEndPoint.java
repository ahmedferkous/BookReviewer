package com.example.bookreviewer.DataFiles;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface BookEndPoint {
    @GET("/books/v1/volumes")
    Call<VolumeModel> getVolumes(@Query("q") String keyword);
}
