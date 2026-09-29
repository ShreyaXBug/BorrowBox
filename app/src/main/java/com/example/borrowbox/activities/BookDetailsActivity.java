package com.example.borrowbox;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class BookDetailsActivity extends AppCompatActivity {

    private TextView bookTitle;
    private TextView bookAuthor;
    private TextView bookCategory;
    private TextView bookDescription;
    private TextView bookOwner;
    private Button borrowButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_book_details);

        bookTitle = findViewById(R.id.bookTitle);
        bookAuthor = findViewById(R.id.bookAuthor);
        bookCategory = findViewById(R.id.bookCategory);
        bookDescription = findViewById(R.id.bookDescription);
        bookOwner = findViewById(R.id.bookOwner);
        borrowButton = findViewById(R.id.borrowButton);

        Intent intent = getIntent();

        String title = intent.getStringExtra("book_title");
        String author = intent.getStringExtra("book_author");
        String category = intent.getStringExtra("book_category");
        String description = intent.getStringExtra("book_description");
        String owner = intent.getStringExtra("book_owner");

        if (title != null) {
            bookTitle.setText(title);
        }

        if (author != null) {
            bookAuthor.setText(author);
        }

        if (category != null) {
            bookCategory.setText(category);
        }

        if (description != null) {
            bookDescription.setText(description);
        }

        if (owner != null) {
            bookOwner.setText("Shared by " + owner);
        }

        borrowButton.setOnClickListener(v -> {

            Intent borrowIntent = new Intent(
                    BookDetailsActivity.this,
                    BorrowRequestActivity.class
            );

            borrowIntent.putExtra("book_title", title);
            borrowIntent.putExtra("book_owner", owner);

            startActivity(borrowIntent);
        });
    }
}