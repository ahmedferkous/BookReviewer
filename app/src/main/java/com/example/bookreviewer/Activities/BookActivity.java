package com.example.bookreviewer.Activities;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.bookreviewer.Adapters.ListAdapter;
import com.example.bookreviewer.DataFiles.FavouriteBooksDatabase;
import com.example.bookreviewer.Models.VolumeModel;
import com.example.bookreviewer.R;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.ref.WeakReference;
import java.lang.reflect.Type;
import java.util.ArrayList;

import static com.example.bookreviewer.Activities.WebsiteActivity.LINK;

// TODO: 16/05/2021 fix back pressed method 
public class BookActivity extends AppCompatActivity {
    private static final String TAG = "BookActivity";
    public static final String BOOK_KEY = "book_key";
    private TextView txtBookName, txtDecBy, txtAuthorNames, txtPubDate, txtPubBy, txtPublisher, txtDesc, txtNoImageWarning, txtRatingsCount, txtAverageRating, txtAuthorBy, txtEbookBy, txtRetailPriceBy, txtListedPriceBy, txtMaturityRating, txtPageCount, txtCountryName, txtSaleability, txtListedPrice, txtEbookStatus, txtRetailPrice, txtViewOnGoogleBooks, txtAddToFavourites;
    private LinearLayout pubNameLinLayout, ratingsLinLayout, averageRatingLinLayout, maturityRatingLinLayout, pageCountLinLayout;
    private RelativeLayout categoriesRelLayout, countryRelLayout, saleabilityRelLayout, pubDateRelLayout;
    private RecyclerView categories;
    private ImageView bookImageView;
    private Gson gson = new Gson();
    private Type itemsType = new TypeToken<VolumeModel.Items>() {
    }.getType();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.book_layout);
        setTitle("Book Details");

        initViews();

        Intent intent = getIntent();
        if (intent != null) {
            String jsonResult = intent.getStringExtra(BOOK_KEY);
            if (null != jsonResult) {
                VolumeModel.Items inboundBook = gson.fromJson(jsonResult, itemsType);
                setData(inboundBook);

                txtViewOnGoogleBooks.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        AlertDialog.Builder builder = new AlertDialog.Builder(BookActivity.this)
                                .setTitle("Navigating...")
                                .setMessage("Visit this book on Google Books?")
                                .setNegativeButton("No", null)
                                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        String url = inboundBook.getVolumeInfo().getInfoLink();
                                        if (url != null) {
                                            Intent websiteIntent = new Intent(BookActivity.this, WebsiteActivity.class);
                                            websiteIntent.putExtra(LINK, url);
                                            startActivity(websiteIntent);
                                        }
                                    }
                                });
                        builder.create().show();
                    }
                });
                txtAddToFavourites.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        AlertDialog.Builder builder = new AlertDialog.Builder(BookActivity.this)
                                .setTitle("Add to favourites?")
                                .setMessage("Add this book to your favourites list?")
                                .setNegativeButton("No", null)
                                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        new InsertIntoDBTask(BookActivity.this).execute(inboundBook);
                                    }
                                });
                        builder.create().show();
                    }
                });

            }
        }
    }

    private void initViews() {
        txtBookName = findViewById(R.id.bookName);
        txtAuthorBy = findViewById(R.id.txtAuthorBy);
        txtAuthorNames = findViewById(R.id.txtAuthorNames);
        txtNoImageWarning = findViewById(R.id.txtNoImageWarning);
        txtPubDate = findViewById(R.id.txtPubDate);
        txtPubBy = findViewById(R.id.txtPubBy);
        txtPublisher = findViewById(R.id.txtPublisher);
        txtDesc = findViewById(R.id.txtDescription);
        txtEbookBy = findViewById(R.id.txtEbookBy);
        txtDecBy = findViewById(R.id.txtDecBy);
        txtRatingsCount = findViewById(R.id.txtRatingsCount);
        txtAverageRating = findViewById(R.id.txtAverageRating);
        txtMaturityRating = findViewById(R.id.txtMaturityRating);
        txtPageCount = findViewById(R.id.txtPageCount);
        txtCountryName = findViewById(R.id.txtCountryName);
        txtListedPriceBy = findViewById(R.id.txtListedPriceBy);
        txtEbookStatus = findViewById(R.id.txtEbookStatus);
        txtSaleability = findViewById(R.id.txtSaleability);
        txtListedPrice = findViewById(R.id.txtListedPrice);
        txtRetailPriceBy = findViewById(R.id.txtRetailPriceBy);
        txtRetailPrice = findViewById(R.id.txtRetailPrice);
        txtViewOnGoogleBooks = findViewById(R.id.txtViewOnGoogleBooks);
        txtAddToFavourites = findViewById(R.id.txtAddToFavourites);
        categories = findViewById(R.id.categoriesRecView);
        bookImageView = findViewById(R.id.bookImageView);
        pubNameLinLayout = findViewById(R.id.pubNameLinLayout);
        ratingsLinLayout = findViewById(R.id.ratingsLinLayout);
        averageRatingLinLayout = findViewById(R.id.averageRatingLinLayout);
        maturityRatingLinLayout = findViewById(R.id.maturityRatingLinLayout);
        pageCountLinLayout = findViewById(R.id.pageCountLinLayout);
        categoriesRelLayout = findViewById(R.id.categoriesRelLayout);
        countryRelLayout = findViewById(R.id.countryRelLayout);
        saleabilityRelLayout = findViewById(R.id.saleabilityRelLayout);
        pubDateRelLayout = findViewById(R.id.pubDateRelLayout);
    }

    private void setData(VolumeModel.Items book) {
        new ExistsInFavouritesDBTask(this).execute(book.getPrimary_key());

        if (book.getVolumeInfo().getImageLinks() != null) {
            Glide.with(this)
                    .asBitmap()
                    .load(book.getVolumeInfo().getImageLinks().getThumbnail())
                    .into(bookImageView);
        } else {
            txtNoImageWarning.setVisibility(View.VISIBLE);
            bookImageView.setVisibility(View.GONE);
        }

        if (book.getVolumeInfo().getTitle() != null) {
            txtBookName.setText(book.getVolumeInfo().getTitle());
        } else {
            txtBookName.setVisibility(View.GONE);
        }

        if (book.getVolumeInfo().getAuthors() != null) {
            String totalAuthors = "";
            int i = 1;
            for (String s : book.getVolumeInfo().getAuthors()) {
                if (i != book.getVolumeInfo().getAuthors().size()) {
                    totalAuthors += s + ", ";
                } else {
                    totalAuthors += s;
                }
                i++;
            }
            txtAuthorNames.setText(totalAuthors);
        } else {
            txtAuthorNames.setVisibility(View.GONE);
            txtAuthorBy.setVisibility(View.GONE);
        }

        if (book.getVolumeInfo().getPublishedDate() != null) {
            txtPubDate.setText(book.getVolumeInfo().getPublishedDate());
        } else {
            pubDateRelLayout.setVisibility(View.GONE);
        }

        if (book.getVolumeInfo().getPublisher() != null) {
            txtPublisher.setText(book.getVolumeInfo().getPublisher());
        } else {
            pubNameLinLayout.setVisibility(View.GONE);
        }

        if (book.getVolumeInfo().getDescription() != null) {
            txtDesc.setText(book.getVolumeInfo().getDescription());
        } else {
            txtDesc.setVisibility(View.GONE);
            txtDecBy.setVisibility(View.GONE);
        }

        if (book.getVolumeInfo().getRatingsCount() != null) {
            txtRatingsCount.setText(book.getVolumeInfo().getRatingsCount());
        } else {
            ratingsLinLayout.setVisibility(View.GONE);
        }

        if (book.getVolumeInfo().getAverageRating() != null) {
            txtAverageRating.setText(book.getVolumeInfo().getAverageRating());
        } else {
            averageRatingLinLayout.setVisibility(View.GONE);
        }

        if (book.getVolumeInfo().getMaturityRating() != null) {
            txtMaturityRating.setText(book.getVolumeInfo().getMaturityRating());
        } else {
            maturityRatingLinLayout.setVisibility(View.GONE);
        }

        if (book.getVolumeInfo().getPageCount() != null) {
            txtPageCount.setText(book.getVolumeInfo().getPageCount());
        } else {
            pageCountLinLayout.setVisibility(View.GONE);
        }

        if (book.getSaleInfo().getCountry() != null) {
            txtCountryName.setText(book.getSaleInfo().getCountry());
        } else {
            countryRelLayout.setVisibility(View.GONE);
        }

        if (book.getSaleInfo().getSaleability() != null) {
            txtSaleability.setText(book.getSaleInfo().getSaleability());
        } else {
            saleabilityRelLayout.setVisibility(View.GONE);
        }

        if (book.getSaleInfo().getListPrice() != null) {
            String price = book.getSaleInfo().getListPrice().getList_price_amount() + " " + book.getSaleInfo().getListPrice().getList_price_currencyCode();
            txtListedPrice.setText(price);
        } else {
            txtListedPrice.setVisibility(View.GONE);
            txtListedPriceBy.setVisibility(View.GONE);
        }

        if (book.getSaleInfo().isEbook()) {
            txtEbookStatus.setText("YES");
        } else {
            txtEbookStatus.setVisibility(View.GONE);
            txtEbookBy.setVisibility(View.GONE);
        }

        if (book.getSaleInfo().getRetailPrice() != null) {
            String price = book.getSaleInfo().getRetailPrice().getRetail_price_amount() + " " + book.getSaleInfo().getRetailPrice().getRetail_price_currencyCode();
            txtRetailPrice.setText(price);
        } else {
            txtRetailPrice.setVisibility(View.GONE);
            txtRetailPriceBy.setVisibility(View.GONE);
        }

        if (book.getVolumeInfo().getCategories() != null) {
            categories.setLayoutManager(new LinearLayoutManager(this));
            ListAdapter listAdapter = new ListAdapter(this);
            categories.setAdapter(listAdapter);
            listAdapter.setListItems(book.getVolumeInfo().getCategories());
        } else {
            categoriesRelLayout.setVisibility(View.GONE);
        }
    }

    private static class ExistsInFavouritesDBTask extends AsyncTask<Integer, Void, Integer> {
        private WeakReference<BookActivity> activityReference;
        private FavouriteBooksDatabase db;

        ExistsInFavouritesDBTask(BookActivity context) {
            activityReference = new WeakReference<>(context);
        }

        @Override
        protected Integer doInBackground(Integer... integers) {
            db = FavouriteBooksDatabase.getInstance(activityReference.get());
            if (db.bookDao().getBookById(integers[0]) == null) {
                return 1;
            }
            return 0;
        }

        @Override
        protected void onPostExecute(Integer integer) {
            super.onPostExecute(integer);

            BookActivity activity = activityReference.get();
            if (activity == null || activity.isFinishing()) return;

            switch (integer) {
                case 0:
                    activityReference.get().findViewById(R.id.txtAddToFavourites).setVisibility(View.GONE);
                    break;
                default:
                    break;
            }
        }
    }

    private static class InsertIntoDBTask extends AsyncTask<VolumeModel.Items, Void, Void> {
        private WeakReference<BookActivity> activityReference;
        private FavouriteBooksDatabase db;

        InsertIntoDBTask(BookActivity context) {
            activityReference = new WeakReference<>(context);
        }

        @Override
        protected Void doInBackground(VolumeModel.Items... items) {
            db = FavouriteBooksDatabase.getInstance(activityReference.get());
            db.bookDao().insertFavouriteBook(items[0]);
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);

            BookActivity activity = activityReference.get();
            if (activity == null || activity.isFinishing()) return;

            activityReference.get().findViewById(R.id.txtAddToFavourites).setVisibility(View.GONE);
            Toast.makeText(activityReference.get(), "Book added to your favourites", Toast.LENGTH_LONG).show();
        }
    }
}