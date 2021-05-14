package com.example.bookreviewer.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.bookreviewer.Adapters.BooksAdapter;
import com.example.bookreviewer.R;

public class SearchActivity extends AppCompatActivity {
    private EditText edtTxtSearchBox;
    private ImageView btnSearch;
    private TextView txtSearchByTitle, txtSearchByAuthor, txtSearchByPublisher, txtSearchBySubject;
    private RecyclerView recView;
    private BooksAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        initViews();

        adapter = new BooksAdapter(this);
        recView.setLayoutManager(new GridLayoutManager(this, 2));
        recView.setAdapter(adapter);
        // TODO: 14/05/2021 set items for adapter
    }

    private void initViews() {
        edtTxtSearchBox = findViewById(R.id.edtTxtSearchBox);
        btnSearch = findViewById(R.id.btnSearch);
        txtSearchByTitle = findViewById(R.id.txtSearchByTitle);
        txtSearchByAuthor = findViewById(R.id.txtSearchByAuthor);
        txtSearchByPublisher = findViewById(R.id.txtSearchByPublisher);
        txtSearchBySubject = findViewById(R.id.txtSearchBySubject);
        recView = findViewById(R.id.recView);
    }
}