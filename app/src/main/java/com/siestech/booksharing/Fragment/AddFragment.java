package com.siestech.booksharing.Fragment;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.RadioGroup;
import android.widget.Toast;
import android.widget.ToggleButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.siestech.booksharing.Model.Book;
import com.siestech.booksharing.R;
import com.siestech.booksharing.Utils.LocalBookStore;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class AddFragment extends Fragment {
    private int selectedImage = 1;
    private String chosenDate = "";
    private String chosenTime = "";

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_add, container, false);
        ImageButton cover = view.findViewById(R.id.coverPreview);
        AutoCompleteTextView category = view.findViewById(R.id.categoryET);
        Button dateBtn = view.findViewById(R.id.dateBtn);
        Button timeBtn = view.findViewById(R.id.timeBtn);
        Button addBtn = view.findViewById(R.id.addBookBtn);
        ProgressBar progress = view.findViewById(R.id.saveProgress);

        String[] categories = {"Novel", "Self Help", "Programming", "Education", "Science", "Biography", "Comics"};
        category.setAdapter(new ArrayAdapter<>(requireContext(), android.R.layout.simple_dropdown_item_1line, categories));

        cover.setOnClickListener(v -> {
            selectedImage = selectedImage % 4 + 1;
            cover.setImageResource(getBookImage(selectedImage));
        });
        Calendar now = Calendar.getInstance();
        dateBtn.setOnClickListener(v -> new DatePickerDialog(requireContext(), (d, year, month, day) -> {
            chosenDate = day + "/" + (month + 1) + "/" + year;
            dateBtn.setText(chosenDate);
        }, now.get(Calendar.YEAR), now.get(Calendar.MONTH), now.get(Calendar.DAY_OF_MONTH)).show());
        timeBtn.setOnClickListener(v -> new TimePickerDialog(requireContext(), (d, hour, minute) -> {
            chosenTime = String.format(java.util.Locale.getDefault(), "%02d:%02d", hour, minute);
            timeBtn.setText(chosenTime);
        }, now.get(Calendar.HOUR_OF_DAY), now.get(Calendar.MINUTE), true).show());
        addBtn.setOnClickListener(v -> saveBook(view, progress, addBtn));
        return view;
    }

    private void saveBook(View view, ProgressBar progress, Button addBtn) {
        AutoCompleteTextView category = view.findViewById(R.id.categoryET);
        String title = text(view, R.id.bookTitleET);
        String author = text(view, R.id.authorET);
        String cat = category.getText().toString().trim();
        String desc = text(view, R.id.descriptionET);
        CheckBox ownership = view.findViewById(R.id.confirmOwnership);
        RadioGroup group = view.findViewById(R.id.conditionGroup);
        ToggleButton availability = view.findViewById(R.id.availabilityToggle);

        if (TextUtils.isEmpty(title) || TextUtils.isEmpty(author) || TextUtils.isEmpty(cat) || TextUtils.isEmpty(desc)) {
            Toast.makeText(requireContext(), "Please fill all book details", Toast.LENGTH_SHORT).show(); return;
        }
        if (!ownership.isChecked()) { Toast.makeText(requireContext(), "Please confirm ownership", Toast.LENGTH_SHORT).show(); return; }

        String condition = group.getCheckedRadioButtonId() == R.id.radioFair ? "Fair" : group.getCheckedRadioButtonId() == R.id.radioNew ? "New" : "Good";
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user == null) { Toast.makeText(requireContext(), "Please login first", Toast.LENGTH_SHORT).show(); return; }

        progress.setVisibility(View.VISIBLE); progress.setProgress(15); addBtn.setEnabled(false);
        String bookId = FirebaseDatabase.getInstance().getReference("Books").push().getKey();
        if (bookId == null) bookId = "local_" + System.currentTimeMillis();
        final String finalBookId = bookId;
        final String ownerId = user.getUid();
        final String ownerEmail = user.getEmail() == null ? "" : user.getEmail();
        final String imageName = "book" + selectedImage;
        loadOwnerNameAndSave(view, progress, addBtn, title, author, cat, desc, condition, availability.isChecked(), finalBookId, ownerId, ownerEmail, imageName);
    }

    private void loadOwnerNameAndSave(View view, ProgressBar progress, Button addBtn, String title, String author, String cat, String desc, String condition, boolean available, String bookId, String ownerId, String ownerEmail, String imageName) {
        FirebaseDatabase.getInstance().getReference("Users").child(ownerId).child("name").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override public void onDataChange(@NonNull DataSnapshot snapshot) {
                progress.setProgress(35);
                String ownerName = snapshot.getValue(String.class);
                if (ownerName == null || ownerName.trim().isEmpty()) ownerName = ownerEmail.contains("@") ? ownerEmail.substring(0, ownerEmail.indexOf('@')) : "You";
                Book book = new Book(bookId, title, author, cat, desc, ownerId, ownerName, ownerEmail, condition, imageName, available, System.currentTimeMillis());
                LocalBookStore.save(requireContext(), book);
                Map<String,Object> map = new HashMap<>();
                map.put("title", title); map.put("author", author); map.put("category", cat); map.put("description", desc);
                map.put("ownerId", ownerId); map.put("ownerName", ownerName); map.put("ownerEmail", ownerEmail);
                map.put("condition", condition); map.put("imageName", imageName); map.put("available", available); map.put("createdAt", System.currentTimeMillis());
                progress.setProgress(60);
                FirebaseDatabase.getInstance().getReference("Books").child(bookId).setValue(map)
                        .addOnSuccessListener(v -> finishSuccess(progress, addBtn, "Book added successfully"))
                        .addOnFailureListener(e -> finishSuccess(progress, addBtn, "Book saved on device; Firebase needs Books write permission"));
            }
            @Override public void onCancelled(@NonNull DatabaseError error) {
                String ownerName = ownerEmail.contains("@") ? ownerEmail.substring(0, ownerEmail.indexOf('@')) : "You";
                Book book = new Book(bookId, title, author, cat, desc, ownerId, ownerName, ownerEmail, condition, imageName, available, System.currentTimeMillis());
                LocalBookStore.save(requireContext(), book);
                finishSuccess(progress, addBtn, "Book saved on this device");
            }
        });
    }

    private void finishSuccess(ProgressBar progress, Button addBtn, String msg) { progress.setProgress(100); progress.postDelayed(() -> { progress.setVisibility(View.GONE); addBtn.setEnabled(true); Toast.makeText(requireContext(), msg, Toast.LENGTH_LONG).show(); }, 250); }
    private String text(View v,int id){return ((android.widget.EditText)v.findViewById(id)).getText().toString().trim();}
    private int getBookImage(int n){return n==2?R.drawable.book2:n==3?R.drawable.book3:n==4?R.drawable.book4:R.drawable.book1;}
}
