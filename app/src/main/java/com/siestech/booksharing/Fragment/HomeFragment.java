package com.siestech.booksharing.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.siestech.booksharing.Adapter.BookAdapter;
import com.siestech.booksharing.Model.Book;
import com.siestech.booksharing.R;
import com.siestech.booksharing.Utils.LocalBookStore;

import java.util.ArrayList;

public class HomeFragment extends Fragment {
    private final ArrayList<Book> books = new ArrayList<>();
    private BookAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        androidx.recyclerview.widget.RecyclerView rv = view.findViewById(R.id.dashboardRv);
        adapter = new BookAdapter(requireContext(), books);
        rv.setLayoutManager(new LinearLayoutManager(requireContext()));
        rv.setAdapter(adapter);
        loadBooks();
        return view;
    }

    private void loadBooks() {
        showDemoBooks();
        FirebaseDatabase.getInstance().getReference("Books").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists() && snapshot.getChildrenCount() > 0) {
                    books.clear();
                    for (DataSnapshot ds : snapshot.getChildren()) {
                        Book b = ds.getValue(Book.class);
                        if (b != null) {
                            b.setId(ds.getKey());
                            books.add(b);
                        }
                    }
                    books.addAll(LocalBookStore.load(requireContext()));
                    adapter.notifyDataSetChanged();
                }
            }
            @Override public void onCancelled(@NonNull DatabaseError error) { }
        });
    }

    private void showDemoBooks() {
        books.clear();
        books.add(new Book("demo1", "The Alchemist", "Paulo Coelho", "Novel", "A story about dreams, courage and finding your path.", "demo-shreya", "Shreya", "", "Good", "book1", true, 1));
        books.add(new Book("demo2", "Atomic Habits", "James Clear", "Self Help", "Small habits can create remarkable results.", "demo-snehal", "Snehal", "", "Good", "book2", true, 2));
        books.add(new Book("demo3", "Clean Code", "Robert C. Martin", "Programming", "Practical ideas for writing maintainable software.", "demo-shreya", "Shreya", "", "Good", "book3", true, 3));
        books.add(new Book("demo4", "Java Programming", "Herbert Schildt", "Education", "A practical reference for learning Java programming.", "demo-snehal", "Snehal", "", "Fair", "book4", true, 4));
        books.addAll(LocalBookStore.load(requireContext()));
        adapter.notifyDataSetChanged();
    }
}
