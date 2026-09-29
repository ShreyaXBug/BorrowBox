package com.example.borrowbox;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class BorrowRequestActivity extends AppCompatActivity {

    private TextView bookName;
    private TextView ownerName;
    private EditText messageInput;
    private Button sendRequestButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_borrow_request);

        bookName = findViewById(R.id.borrowBookName);
        ownerName = findViewById(R.id.borrowOwnerName);
        messageInput = findViewById(R.id.borrowMessage);
        sendRequestButton = findViewById(R.id.sendRequestButton);

        String title = getIntent().getStringExtra("book_title");
        String owner = getIntent().getStringExtra("book_owner");

        if (title != null) {
            bookName.setText(title);
        }

        if (owner != null) {
            ownerName.setText("Book owner: " + owner);
        }

        sendRequestButton.setOnClickListener(v -> {

            String message = messageInput.getText().toString().trim();

            if (message.isEmpty()) {
                messageInput.setError("Please enter a message");
                return;
            }

            Toast.makeText(
                    BorrowRequestActivity.this,
                    "Borrow request sent!",
                    Toast.LENGTH_SHORT
            ).show();

            finish();
        });
    }
}