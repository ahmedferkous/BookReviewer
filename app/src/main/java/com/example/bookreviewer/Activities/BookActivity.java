package com.example.bookreviewer.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.bookreviewer.R;

public class BookActivity extends AppCompatActivity {
    private TextView txtBookName, txtAuthorNames, txtPubDate, txtPublisher, txtDesc, txtRatingsCount, txtAverageRating, txtMaturityRating, txtPageCount, txtCountryName, txtEpubAvailability, txtSaleability, txtPdfAvailability, txtEbookStatus, txtListedPrice, txtRetailPrice, txtViewOnGoogleBooks, txtAddToFavourites, txtBuyFromBooks;
    private RecyclerView categories;
    private ImageView bookImageView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.book_layout);

        initViews();
    }

    private void initViews() {
        txtBookName = findViewById(R.id.txtBookName);
        txtAuthorNames = findViewById(R.id.txtAuthorNames);
        txtPubDate = findViewById(R.id.txtPubDate);
        txtPublisher = findViewById(R.id.txtPublisher);
        txtDesc = findViewById(R.id.txtDescription);
        txtRatingsCount = findViewById(R.id.txtRatingsCount);
        txtAverageRating = findViewById(R.id.txtAverageRating);
        txtMaturityRating = findViewById(R.id.txtMaturityRating);
        txtPageCount = findViewById(R.id.txtPageCount);
        txtCountryName = findViewById(R.id.txtCountryName);
        txtEpubAvailability = findViewById(R.id.txtEpubAvailability);
        txtSaleability = findViewById(R.id.txtSaleability);
        txtPdfAvailability = findViewById(R.id.txtPdfAvailability);
        txtEbookStatus = findViewById(R.id.txtEbookStatus);
        txtListedPrice = findViewById(R.id.txtListedPrice);
        txtRetailPrice = findViewById(R.id.txtRetailPrice);
        txtViewOnGoogleBooks = findViewById(R.id.txtViewOnGoogleBooks);
        txtAddToFavourites = findViewById(R.id.txtAddToFavourites);
        txtBuyFromBooks = findViewById(R.id.txtBuyFromBooks);
        categories = findViewById(R.id.categoriesRecView);
        bookImageView = findViewById(R.id.bookImageView);
    }
}