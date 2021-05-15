package com.example.bookreviewer.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import com.example.bookreviewer.Adapters.BooksAdapter;
import com.example.bookreviewer.DataFiles.BookEndPoint;
import com.example.bookreviewer.Models.VolumeModel;
import com.example.bookreviewer.R;

import java.lang.ref.WeakReference;
import java.util.ArrayList;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

// TODO: 15/05/2021 Implement viewmodel to handle screen rotations
public class SearchActivity extends AppCompatActivity {
    private static final String TAG = "SearchActivity";
    private EditText edtTxtSearchBox;
    private ImageView btnSearch;
    private TextView txtSearchByTitle, txtSearchByAuthor, txtSearchByPublisher, txtSearchBySubject, txtProgress, txtNextPage;
    private SeekBar seekBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        setTitle("Search For A Book");

        initViews();

        txtSearchByTitle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String search = edtTxtSearchBox.getText().toString();
                String newSearch = search + " intitle:";
                edtTxtSearchBox.setText(newSearch);
                edtTxtSearchBox.setSelection(newSearch.length());
            }
        });

        txtSearchByAuthor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String search = edtTxtSearchBox.getText().toString();
                String newSearch = search + " inauthor:";
                edtTxtSearchBox.setText(newSearch);
                edtTxtSearchBox.setSelection(newSearch.length());
            }
        });

        txtSearchByPublisher.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String search = edtTxtSearchBox.getText().toString();
                String newSearch = search + " inpublisher:";
                edtTxtSearchBox.setText(newSearch);
                edtTxtSearchBox.setSelection(newSearch.length());
            }
        });

        txtSearchBySubject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String search = edtTxtSearchBox.getText().toString();
                String newSearch = search + " subject:";
                edtTxtSearchBox.setText(newSearch);
                edtTxtSearchBox.setSelection(newSearch.length());
            }
        });

        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String searchTerms = edtTxtSearchBox.getText().toString();
                String progress = String.valueOf(seekBar.getProgress());
                if (!searchTerms.equals("")) {
                    new GetDataTask(SearchActivity.this).execute(searchTerms, progress, "0");
                }
            }
        });

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                txtProgress.setText(seekBar.getProgress() + "/40");
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        // TODO: 16/05/2021 fix this 
        txtNextPage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String searchTerms = edtTxtSearchBox.getText().toString();
                String progress = String.valueOf(seekBar.getProgress());
                if (!searchTerms.equals("")) {
                    new GetDataTask(SearchActivity.this).execute(searchTerms, progress, progress+1);
                }
            }
        });
    }

    private void initViews() {
        edtTxtSearchBox = findViewById(R.id.edtTxtSearchBox);
        txtProgress = findViewById(R.id.txtProgress);
        seekBar = findViewById(R.id.seekBar);
        btnSearch = findViewById(R.id.btnSearch);
        txtSearchByTitle = findViewById(R.id.txtSearchByTitle);
        txtSearchByAuthor = findViewById(R.id.txtSearchByAuthor);
        txtSearchByPublisher = findViewById(R.id.txtSearchByPublisher);
        txtSearchBySubject = findViewById(R.id.txtSearchBySubject);
        txtNextPage = findViewById(R.id.txtNextPage);
    }

    @Override
    public void onBackPressed() {
        Intent mainActivityIntent = new Intent(this, MainActivity.class);
        startActivity(mainActivityIntent);
    }

    private static class GetDataTask extends AsyncTask<String, ArrayList<VolumeModel.Items>, Void> {
        private BookEndPoint endPoint;
        private WeakReference<SearchActivity> activityReference;
        private BooksAdapter adapter;

        GetDataTask(SearchActivity context) {
            activityReference = new WeakReference<>(context);
        }
        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            adapter = new BooksAdapter(activityReference.get());

            OkHttpClient.Builder clientBuilder = new OkHttpClient.Builder();
            HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
            interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
            clientBuilder.addInterceptor(interceptor);

            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl("https://www.googleapis.com")
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(clientBuilder.build())
                    .build();
            endPoint = retrofit.create(BookEndPoint.class);
        }

        @Override
        protected Void doInBackground(String... strings) {
            // TODO: 15/05/2021 Allow user to optimize amount of results, next page of volumes etc as well as other stuff
            Call<VolumeModel> call = endPoint.getVolumes(strings[0], strings[1], Integer.parseInt(strings[2])); // 16 is the next 'page'
            call.enqueue(new Callback<VolumeModel>() {
                @Override
                public void onResponse(Call<VolumeModel> call, Response<VolumeModel> response) {
                    if (response.isSuccessful()) {
                        VolumeModel model = response.body();
                        if (model != null) {
                            ArrayList<VolumeModel.Items> booksSearched = model.getItems();
                            Log.d(TAG, "onResponse: nigger?");
                            publishProgress(booksSearched);
                        }
                    }
                }

                @Override
                public void onFailure(Call<VolumeModel> call, Throwable t) {
                    t.printStackTrace();
                }
            });
            return null;
        }

        @Override
        protected void onProgressUpdate(ArrayList<VolumeModel.Items>... values) {
            super.onProgressUpdate(values);
            
            SearchActivity activity = activityReference.get();
            if (activity == null || activity.isFinishing()) return;

            RecyclerView recView = activityReference.get().findViewById(R.id.recView);
            recView.setLayoutManager(new LinearLayoutManager(activityReference.get()));
            recView.setAdapter(adapter);

            if (values[0] != null) {
                adapter.setBooks(values[0]);
            }
        }
    }

}