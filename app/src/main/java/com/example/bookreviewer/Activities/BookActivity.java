package com.example.bookreviewer.Activities;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.example.bookreviewer.Adapters.ListAdapter;
import com.example.bookreviewer.Models.VolumeModel;
import com.example.bookreviewer.R;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;

import static com.example.bookreviewer.Activities.WebsiteActivity.LINK;

public class BookActivity extends AppCompatActivity {
    public static final String BOOK_KEY = "book_key";
    private TextView txtBookName, txtDecBy, txtAuthorNames, txtPubDate, txtPubBy, txtPublisher, txtDesc, txtRatingsCount, txtAverageRating, txtAuthorBy, txtEbookBy, txtRetailPriceBy, txtListedPriceBy, txtMaturityRating, txtPageCount, txtCountryName, txtSaleability, txtListedPrice, txtEbookStatus, txtRetailPrice, txtViewOnGoogleBooks, txtAddToFavourites;
    private LinearLayout pubNameLinLayout, ratingsLinLayout, averageRatingLinLayout, maturityRatingLinLayout, pageCountLinLayout;
    private RelativeLayout categoriesRelLayout, countryRelLayout, saleabilityRelLayout, pubDateRelLayout;
    private RecyclerView categories;
    private ImageView bookImageView;
    private Gson gson = new Gson();
    private Type itemsType = new TypeToken<VolumeModel.Items>() {}.getType();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.book_layout);

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
                        // TODO: 15/05/2021 add to favourites + navigate user
                    }
                });

            }
        }
    }

    private void initViews() {
        txtBookName = findViewById(R.id.bookName);
        txtAuthorBy = findViewById(R.id.txtAuthorBy);
        txtAuthorNames = findViewById(R.id.txtAuthorNames);
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
        // TODO: 14/05/2021 Load image here

        if (book.getVolumeInfo().getTitle() != null) {
            txtBookName.setText(book.getVolumeInfo().getTitle());
        } else {
            txtBookName.setVisibility(View.GONE);
        }

        if (book.getVolumeInfo().getAuthors() != null) {
            String totalAuthors = "";
            for (String s : book.getVolumeInfo().getAuthors()) {
                totalAuthors += " " + s;
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
            String price = book.getSaleInfo().getListPrice().getAmount() + " " + book.getSaleInfo().getListPrice().getCurrencyCode();
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
            String price = book.getSaleInfo().getRetailPrice().getAmount() + " " + book.getSaleInfo().getRetailPrice().getCurrencyCode();
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
}