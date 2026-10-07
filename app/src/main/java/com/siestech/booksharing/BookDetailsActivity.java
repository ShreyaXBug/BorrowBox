package com.siestech.booksharing;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.telephony.SmsManager;
import android.text.InputType;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.siestech.booksharing.Practical.BluetoothShareActivity;
import com.siestech.booksharing.Practical.LocationActivity;

import java.util.Locale;

public class BookDetailsActivity extends AppCompatActivity {
    private TextToSpeech tts;
    private String title, author, description, ownerName, ownerEmail, ownerId, bookId, category, condition, imageName;
    private boolean available;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_details);
        title = getIntent().getStringExtra("title");
        author = getIntent().getStringExtra("author");
        description = getIntent().getStringExtra("description");
        ownerName = getIntent().getStringExtra("ownerName");
        ownerEmail = getIntent().getStringExtra("ownerEmail");
        ownerId = getIntent().getStringExtra("ownerId");
        bookId = getIntent().getStringExtra("id");
        category = getIntent().getStringExtra("category");
        condition = getIntent().getStringExtra("condition");
        imageName = getIntent().getStringExtra("imageName");
        available = getIntent().getBooleanExtra("available", true);

        ((ImageView) findViewById(R.id.detailCover)).setImageResource(resolveImage(imageName));
        ((android.widget.TextView)findViewById(R.id.detailTitle)).setText(title);
        ((android.widget.TextView)findViewById(R.id.detailAuthor)).setText(author);
        ((android.widget.TextView)findViewById(R.id.detailCategory)).setText(category);
        ((android.widget.TextView)findViewById(R.id.detailOwner)).setText("Shared by " + safe(ownerName, "BorrowBox member"));
        ((android.widget.TextView)findViewById(R.id.detailCondition)).setText("Condition: " + safe(condition, "Good") + " • " + (available ? "Available" : "Currently borrowed"));
        ((android.widget.TextView)findViewById(R.id.detailDescription)).setText(safe(description, "No description provided."));

        tts = new TextToSpeech(this, status -> { if (status == TextToSpeech.SUCCESS) tts.setLanguage(Locale.ENGLISH); });

        findViewById(R.id.btnRead).setOnClickListener(v -> speak());
        findViewById(R.id.btnSms).setOnClickListener(v -> showSmsDialog());
        findViewById(R.id.btnBluetooth).setOnClickListener(v -> {
            Intent i = new Intent(this, BluetoothShareActivity.class);
            i.putExtra("shareText", buildShareText());
            startActivity(i);
        });
        findViewById(R.id.btnLocation).setOnClickListener(v -> startActivity(new Intent(this, LocationActivity.class)));
        findViewById(R.id.btnShare).setOnClickListener(v -> shareText());
        findViewById(R.id.btnBorrow).setOnClickListener(v -> requestBorrow());
    }

    private void requestBorrow() {
        if (!available) { Toast.makeText(this, "This book is currently unavailable", Toast.LENGTH_SHORT).show(); return; }
        if (FirebaseAuth.getInstance().getCurrentUser() == null) { Toast.makeText(this, "Please login first", Toast.LENGTH_SHORT).show(); return; }
        String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
        String reqId = FirebaseDatabase.getInstance().getReference("Requests").push().getKey();
        if (reqId == null) return;
        java.util.Map<String,Object> request = new java.util.HashMap<>();
        request.put("bookId", bookId);
        request.put("title", title);
        request.put("ownerId", ownerId);
        request.put("ownerName", ownerName);
        request.put("borrowerId", uid);
        request.put("borrowerEmail", FirebaseAuth.getInstance().getCurrentUser().getEmail());
        request.put("status", "Pending");
        request.put("createdAt", System.currentTimeMillis());
        FirebaseDatabase.getInstance().getReference("Requests").child(reqId).setValue(request)
                .addOnSuccessListener(v -> Toast.makeText(this, "Borrow request sent", Toast.LENGTH_SHORT).show())
                .addOnFailureListener(e -> Toast.makeText(this, "Saved locally in demo mode: " + e.getMessage(), Toast.LENGTH_LONG).show());
    }

    private void showSmsDialog() {
        final EditText input = new EditText(this);
        input.setHint("Owner phone number"); input.setInputType(InputType.TYPE_CLASS_PHONE);
        new AlertDialog.Builder(this).setTitle("Send SMS to owner").setView(input)
                .setPositiveButton("Send", (d,w) -> sendSms(input.getText().toString().trim()))
                .setNegativeButton("Cancel", null).show();
    }
    private void sendSms(String number) {
        if (number.isEmpty()) { Toast.makeText(this,"Enter a phone number",Toast.LENGTH_SHORT).show(); return; }
        try {
            String message = "Hi, I am interested in borrowing your book: " + title;
            SmsManager.getDefault().sendTextMessage(number, null, message, null, null);
            Toast.makeText(this,"SMS sent",Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            Intent i = new Intent(Intent.ACTION_SENDTO, Uri.parse("smsto:" + Uri.encode(number)));
            i.putExtra("sms_body", "Hi, I am interested in borrowing your book: " + title);
            startActivity(i);
        }
    }
    private void shareText() {
        Intent i = new Intent(Intent.ACTION_SEND); i.setType("text/plain"); i.putExtra(Intent.EXTRA_TEXT, buildShareText());
        startActivity(Intent.createChooser(i,"Share book via"));
    }
    private String buildShareText() { return title + " by " + author + "\nCategory: " + category + "\nShared by: " + ownerName; }
    private void speak() { if (tts != null) tts.speak(title + " by " + author + ". " + safe(description,""), TextToSpeech.QUEUE_FLUSH, null, "borrowbox-book"); }
    private int resolveImage(String name) { int id=getResources().getIdentifier(name==null?"book1":name,"drawable",getPackageName()); return id==0?R.drawable.book1:id; }
    private String safe(String s,String fallback){return s==null||s.trim().isEmpty()?fallback:s;}
    @Override protected void onDestroy(){ if(tts!=null){tts.stop();tts.shutdown();} super.onDestroy(); }
}
