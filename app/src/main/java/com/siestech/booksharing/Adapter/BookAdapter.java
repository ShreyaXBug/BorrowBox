package com.siestech.booksharing.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.siestech.booksharing.BookDetailsActivity;
import com.siestech.booksharing.Model.Book;
import com.siestech.booksharing.R;

import java.util.ArrayList;

public class BookAdapter extends RecyclerView.Adapter<BookAdapter.BookViewHolder> {

    private final Context context;
    private final ArrayList<Book> books;

    public BookAdapter(Context context, ArrayList<Book> books) {
        this.context = context;
        this.books = books;
    }

    @NonNull
    @Override
    public BookViewHolder onCreateViewHolder(
            @NonNull ViewGroup parent,
            int viewType) {

        View view = LayoutInflater.from(context)
                .inflate(R.layout.book_card, parent, false);

        return new BookViewHolder(view);
    }

    @Override
    public void onBindViewHolder(
            @NonNull BookViewHolder h,
            int position) {

        Book b = books.get(position);

        // Book image
        int imageRes = context.getResources().getIdentifier(
                b.getImageName() == null ? "book1" : b.getImageName(),
                "drawable",
                context.getPackageName()
        );

        if (imageRes == 0) {
            imageRes = R.drawable.book1;
        }

        h.cover.setImageResource(imageRes);

        // Owner profile image
        if ("Snehal".equalsIgnoreCase(b.getOwnerName())) {
            h.ownerAvatar.setImageResource(R.drawable.profile2);
        } else {
            h.ownerAvatar.setImageResource(R.drawable.profile1);
        }

        // Book information
        h.title.setText(
                b.getTitle() == null ? "Book Title" : b.getTitle()
        );

        h.author.setText(
                b.getAuthor() == null ? "Unknown Author" : b.getAuthor()
        );

        String ownerName = b.getOwnerName();

        if (ownerName == null || ownerName.isEmpty()) {
            ownerName = "BorrowBox member";
        }

        h.owner.setText("Shared by " + ownerName);

        h.category.setText(
                b.getCategory() == null ? "General" : b.getCategory()
        );

        // Availability
        if (b.isAvailable()) {
            h.available.setText("Available");
            h.available.setBackgroundResource(
                    R.drawable.bg_available
            );
        } else {
            h.available.setText("Borrowed");
            h.available.setBackgroundResource(
                    R.drawable.bg_unavailable
            );
        }

        // Open Book Details
        View.OnClickListener open = v -> {

            Intent intent = new Intent(
                    context,
                    BookDetailsActivity.class
            );

            intent.putExtra("id", b.getId());
            intent.putExtra("title", b.getTitle());
            intent.putExtra("author", b.getAuthor());
            intent.putExtra("category", b.getCategory());
            intent.putExtra("description", b.getDescription());
            intent.putExtra("ownerName", b.getOwnerName());
            intent.putExtra("ownerEmail", b.getOwnerEmail());
            intent.putExtra("condition", b.getCondition());
            intent.putExtra("imageName", b.getImageName());
            intent.putExtra("available", b.isAvailable());
            intent.putExtra("ownerId", b.getOwnerId());

            context.startActivity(intent);
        };

        h.itemView.setOnClickListener(open);
    }

    @Override
    public int getItemCount() {
        return books.size();
    }

    static class BookViewHolder extends RecyclerView.ViewHolder {

        ImageView cover;
        ImageView ownerAvatar;

        TextView title;
        TextView author;
        TextView owner;
        TextView category;
        TextView available;

        BookViewHolder(@NonNull View itemView) {
            super(itemView);

            cover = itemView.findViewById(R.id.bookCover);
            ownerAvatar = itemView.findViewById(R.id.ownerAvatar);

            title = itemView.findViewById(R.id.bookTitle);
            author = itemView.findViewById(R.id.bookAuthor);
            owner = itemView.findViewById(R.id.bookOwner);
            category = itemView.findViewById(R.id.bookCategory);
            available = itemView.findViewById(R.id.bookAvailability);
        }
    }
}