package com.example.borrowbox.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.borrowbox.BookDetailsActivity;
import com.example.borrowbox.R;

public class HomeFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater,
            @Nullable ViewGroup container,
            @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(
                R.layout.fragment_home,
                container,
                false
        );

        // The Alchemist
        View bookCard1 = view.findViewById(R.id.bookCard1);

        bookCard1.setOnClickListener(v -> {

            Intent intent = new Intent(
                    requireContext(),
                    BookDetailsActivity.class
            );

            intent.putExtra("book_title", "The Alchemist");
            intent.putExtra("book_author", "Paulo Coelho");
            intent.putExtra("book_category", "Fiction");
            intent.putExtra(
                    "book_description",
                    "The Alchemist is a story about following your dreams, discovering your purpose and listening to your heart."
            );
            intent.putExtra("book_owner", "Rahul");

            startActivity(intent);
        });

        // Atomic Habits
        View bookCard2 = view.findViewById(R.id.bookCard2);

        bookCard2.setOnClickListener(v -> {

            Intent intent = new Intent(
                    requireContext(),
                    BookDetailsActivity.class
            );

            intent.putExtra("book_title", "Atomic Habits");
            intent.putExtra("book_author", "James Clear");
            intent.putExtra("book_category", "Self Help");
            intent.putExtra(
                    "book_description",
                    "A practical guide to building good habits and breaking bad ones."
            );
            intent.putExtra("book_owner", "Priya");

            startActivity(intent);
        });

        // Psychology of Money
        View bookCard3 = view.findViewById(R.id.bookCard3);

        bookCard3.setOnClickListener(v -> {

            Intent intent = new Intent(
                    requireContext(),
                    BookDetailsActivity.class
            );

            intent.putExtra(
                    "book_title",
                    "The Psychology of Money"
            );
            intent.putExtra(
                    "book_author",
                    "Morgan Housel"
            );
            intent.putExtra(
                    "book_category",
                    "Finance"
            );
            intent.putExtra(
                    "book_description",
                    "A book about money, behavior and financial decisions."
            );
            intent.putExtra(
                    "book_owner",
                    "Aarav"
            );

            startActivity(intent);
        });

        return view;
    }
}