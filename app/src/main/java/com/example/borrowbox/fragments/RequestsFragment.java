package com.example.borrowbox.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.borrowbox.R;
import com.example.borrowbox.RequestManager;

public class RequestsFragment extends Fragment {

    private TextView requestBook;
    private TextView requestOwner;
    private TextView requestMessage;
    private TextView requestStatus;

    public RequestsFragment() {
        // Required empty constructor
    }

    @Nullable
    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater,
            @Nullable ViewGroup container,
            @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(
                R.layout.fragment_requests,
                container,
                false
        );

        requestBook = view.findViewById(R.id.requestBook);
        requestOwner = view.findViewById(R.id.requestOwner);
        requestMessage = view.findViewById(R.id.requestMessage);
        requestStatus = view.findViewById(R.id.requestStatus);

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();

        updateRequest();
    }

    private void updateRequest() {

        if (requestBook == null) {
            return;
        }

        if (RequestManager.hasRequest()) {

            requestBook.setText(RequestManager.getBookTitle());
            requestOwner.setText(RequestManager.getBookOwner());
            requestMessage.setText(RequestManager.getMessage());
            requestStatus.setText("Pending approval");

        } else {

            requestBook.setText("No requests yet");
            requestOwner.setText("");
            requestMessage.setText("Your borrowing requests will appear here.");
            requestStatus.setText("");
        }
    }
}