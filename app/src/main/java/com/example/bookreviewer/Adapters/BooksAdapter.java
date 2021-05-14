package com.example.bookreviewer.Adapters;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.bookreviewer.Activities.BookActivity;
import com.example.bookreviewer.Models.VolumeModel;
import com.example.bookreviewer.R;
import com.google.android.material.card.MaterialCardView;
import com.google.gson.Gson;

import java.util.ArrayList;

import static com.example.bookreviewer.Activities.BookActivity.BOOK_KEY;

public class BooksAdapter extends RecyclerView.Adapter<BooksAdapter.ViewHolder> {
    private static final String TAG = "BooksAdapter";
    private ArrayList<VolumeModel.Items> books = new ArrayList<>();
    private Gson gson = new Gson();
    private Context context;

    public BooksAdapter(Context context) {
        this.context = context;
    }

    public void setBooks(ArrayList<VolumeModel.Items> books) {
        this.books = books;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.book_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        VolumeModel.Items boundedBook = books.get(position);

        holder.txtBookName.setText(boundedBook.getVolumeInfo().getTitle());

        // TODO: 14/05/2021 Fix image loading issue in adapter
        /*
        String imageUrl = boundedBook.getVolumeInfo().getImageLinks().getThumbnail();

        Glide.with(context)
                .asBitmap()
                .load(imageUrl)
                .into(holder.imageViewBook);
                
         */

        holder.parent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String json = gson.toJson(boundedBook);
                Intent bookIntent = new Intent(context, BookActivity.class);
                bookIntent.putExtra(BOOK_KEY, json);
                context.startActivity(bookIntent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return books.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private MaterialCardView parent;
        private ImageView imageViewBook;
        private TextView txtBookName;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            parent = itemView.findViewById(R.id.parent);
            imageViewBook = itemView.findViewById(R.id.imageBook);
            txtBookName = itemView.findViewById(R.id.txtBookName);
        }
    }

}
