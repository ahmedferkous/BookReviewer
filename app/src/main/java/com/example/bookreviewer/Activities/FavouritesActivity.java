package com.example.bookreviewer.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.bookreviewer.Adapters.BooksAdapter;
import com.example.bookreviewer.DataFiles.FavouriteBooksDatabase;
import com.example.bookreviewer.Models.VolumeModel;
import com.example.bookreviewer.R;

import java.lang.ref.WeakReference;
import java.util.ArrayList;

public class FavouritesActivity extends AppCompatActivity implements BooksAdapter.DeleteBook {
    private static final String TAG = "FavouritesActivity";

    @Override
    public void onDeleteBookResult(VolumeModel.Items book) {
        new DeleteBookTask(this).execute(book);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favourites);
        setTitle("Your Favourites");

        new GetFavouritesFromTask(this).execute();
    }

    @Override
    public void onBackPressed() {
        Intent mainActivityIntent = new Intent(this, MainActivity.class);
        startActivity(mainActivityIntent);;
    }

    private static class GetFavouritesFromTask extends AsyncTask<Void, Void, ArrayList<VolumeModel.Items>> {
        private WeakReference<FavouritesActivity> activityReference;
        private FavouriteBooksDatabase db;

        GetFavouritesFromTask(FavouritesActivity context) {
            activityReference = new WeakReference<>(context);
        }
        @Override
        protected ArrayList<VolumeModel.Items> doInBackground(Void... voids) {
            db = FavouriteBooksDatabase.getInstance(activityReference.get());
            return (ArrayList) db.bookDao().getAllFavouriteBooks();
        }

        @Override
        protected void onPostExecute(ArrayList<VolumeModel.Items> items) {
            super.onPostExecute(items);

            FavouritesActivity activity = activityReference.get();
            if (activity == null || activity.isFinishing()) return;

            RecyclerView recView = activityReference.get().findViewById(R.id.recView);
            TextView txtWarning = activityReference.get().findViewById(R.id.txtWarning);
            if (items.size() > 0) {
                recView.setVisibility(View.VISIBLE);
                txtWarning.setVisibility(View.GONE);
                BooksAdapter adapter = new BooksAdapter(activityReference.get());
                recView.setLayoutManager(new LinearLayoutManager(activityReference.get()));
                recView.setAdapter(adapter);
                adapter.setBooks(items);
            } else {
                recView.setVisibility(View.GONE);
                txtWarning.setVisibility(View.VISIBLE);
            }
        }
    }
    private static class DeleteBookTask extends AsyncTask<VolumeModel.Items, Void, Void> {
        private WeakReference<FavouritesActivity> activityReference;
        private FavouriteBooksDatabase db;

        DeleteBookTask(FavouritesActivity context) {
            activityReference = new WeakReference<>(context);
        }
        @Override
        protected Void doInBackground(VolumeModel.Items... items) {
            db = FavouriteBooksDatabase.getInstance(activityReference.get());
            db.bookDao().deleteBook(items[0]);
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);

            FavouritesActivity activity = activityReference.get();
            if (activity == null || activity.isFinishing()) return;

            Toast.makeText(activityReference.get(), "Book removed from your favourites", Toast.LENGTH_SHORT).show();
            new GetFavouritesFromTask(activityReference.get()).execute();
        }
    }
}