package com.example.bookreviewer.Activities;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.bookreviewer.Adapters.BooksAdapter;
import com.example.bookreviewer.DataFiles.BookEndPoint;
import com.example.bookreviewer.R;
import com.example.bookreviewer.Models.VolumeModel;

import org.jetbrains.annotations.NotNull;

import java.lang.ref.WeakReference;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {
    private Button btnSearchForBooks;
    private Button btnViewFavourites;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnSearchForBooks = findViewById(R.id.btnSearchBooks);
        btnViewFavourites = findViewById(R.id.btnViewFavBooks);

        btnSearchForBooks.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent searchIntent = new Intent(MainActivity.this, SearchActivity.class);
                startActivity(searchIntent);
            }
        });

        btnViewFavourites.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent viewFavouritesIntent = new Intent(MainActivity.this, FavouritesActivity.class);
                startActivity(viewFavouritesIntent);
            }
        });
    }

}
