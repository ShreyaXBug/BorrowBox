package com.example.borrowbox.fragments;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.borrowbox.R;

public class SearchFragment extends Fragment {

    private EditText searchInput;
    private LinearLayout resultContainer;

    private final String[][] books = {
            {"The Alchemist", "Paulo Coelho"},
            {"Atomic Habits", "James Clear"},
            {"The Psychology of Money", "Morgan Housel"},
            {"The 5 AM Club", "Robin Sharma"}
    };

    @Nullable
    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater,
            @Nullable ViewGroup container,
            @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(
                R.layout.fragment_search,
                container,
                false
        );

        searchInput = view.findViewById(R.id.searchInput);
        resultContainer = view.findViewById(R.id.searchResultContainer);

        showBooks("");

        searchInput.addTextChangedListener(new TextWatcher() {

            @Override
            public void beforeTextChanged(
                    CharSequence s,
                    int start,
                    int count,
                    int after) {
            }

            @Override
            public void onTextChanged(
                    CharSequence s,
                    int start,
                    int before,
                    int count) {

                showBooks(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        return view;
    }

    private void showBooks(String query) {

        resultContainer.removeAllViews();

        for (String[] book : books) {

            String title = book[0];
            String author = book[1];

            if (query.isEmpty()
                    || title.toLowerCase().contains(query.toLowerCase())
                    || author.toLowerCase().contains(query.toLowerCase())) {

                LinearLayout bookCard = new LinearLayout(requireContext());

                bookCard.setOrientation(LinearLayout.VERTICAL);
                bookCard.setPadding(20, 20, 20, 20);
                bookCard.setBackgroundColor(
                        android.graphics.Color.WHITE
                );

                LinearLayout.LayoutParams cardParams =
                        new LinearLayout.LayoutParams(
                                ViewGroup.LayoutParams.MATCH_PARENT,
                                ViewGroup.LayoutParams.WRAP_CONTENT
                        );

                cardParams.setMargins(0, 0, 0, 12);

                bookCard.setLayoutParams(cardParams);

                TextView titleView = new TextView(requireContext());
                titleView.setText(title);
                titleView.setTextColor(
                        android.graphics.Color.rgb(41, 36, 43)
                );
                titleView.setTextSize(18);
                titleView.setTypeface(null, android.graphics.Typeface.BOLD);

                TextView authorView = new TextView(requireContext());
                authorView.setText(author);
                authorView.setTextColor(
                        android.graphics.Color.rgb(129, 118, 129)
                );
                authorView.setTextSize(14);
                authorView.setPadding(0, 6, 0, 0);

                bookCard.addView(titleView);
                bookCard.addView(authorView);

                resultContainer.addView(bookCard);
            }
        }
    }
}