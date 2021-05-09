package com.example.bookreviewer;

import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

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
    private Button btnGetData;
    private TextView txtView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnGetData = findViewById(R.id.btnGetData);
        txtView = findViewById(R.id.txtView);

        btnGetData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               new GetDataTask(MainActivity.this).execute();
            }
        });
    }

    private static class GetDataTask extends AsyncTask<Void, String, Void> {
        private static final String TAG = "GetDataTask";
        private BookEndPoint endPoint;
        private VolumeModel model;
        private WeakReference<MainActivity> activityReference;

        GetDataTask(MainActivity context) {
            activityReference = new WeakReference<>(context);
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

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
        protected Void doInBackground(Void... values) {
            Call<VolumeModel> call = endPoint.getVolumes("flowers inauthor:keyes");

            call.enqueue(new Callback<VolumeModel>() {
                @Override
                public void onResponse(@NotNull Call<VolumeModel> call, @NotNull Response<VolumeModel> response) {
                    if (response.isSuccessful()) {
                        model = response.body();

                        String txtResult = "";
                        for (VolumeModel.Items items: model.getItems()) {
                            txtResult += items.getVolumeInfo().getTitle() + "\n";
                        }
                        publishProgress(txtResult);
                    }
                }

                @Override
                public void onFailure(@NotNull Call<VolumeModel> call, @NotNull Throwable t) {
                    t.printStackTrace();
                }
            });
            return null;
        }

        @Override
        protected void onProgressUpdate(String... values) { //executed on main thread
            super.onProgressUpdate(values);

            MainActivity activity = activityReference.get();
            if (activity == null || activity.isFinishing()) return;

            TextView txtView = activity.findViewById(R.id.txtView);
            txtView.setText(values[0]);
        }
    }

}