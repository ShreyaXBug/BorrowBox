package com.siestech.booksharing.Fragment;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
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
import java.util.Locale;

public class SearchFragment extends Fragment {
    private final ArrayList<Book> allBooks = new ArrayList<>();
    private final ArrayList<Book> visibleBooks = new ArrayList<>();
    private BookAdapter adapter;
    private TextView resultCount;
    private AutoCompleteTextView searchEt;
    private static final int VOICE_CODE = 81;

    @Nullable @Override public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_search, container, false);
        searchEt = v.findViewById(R.id.searchEt); resultCount = v.findViewById(R.id.resultCount);
        adapter = new BookAdapter(requireContext(), visibleBooks);
        androidx.recyclerview.widget.RecyclerView rv=v.findViewById(R.id.booksRV); rv.setLayoutManager(new LinearLayoutManager(requireContext())); rv.setAdapter(adapter);
        String[] suggestions={"The Alchemist","Atomic Habits","Clean Code","Java Programming","Python","Novel","Programming"};
        searchEt.setAdapter(new ArrayAdapter<>(requireContext(),android.R.layout.simple_dropdown_item_1line,suggestions));
        searchEt.addTextChangedListener(new android.text.TextWatcher(){public void beforeTextChanged(CharSequence s,int st,int c,int a){} public void onTextChanged(CharSequence s,int st,int b,int c){filter(s.toString());} public void afterTextChanged(android.text.Editable e){}});
        ((ImageButton)v.findViewById(R.id.voiceSearchBtn)).setOnClickListener(x->{Intent i=new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH); i.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,RecognizerIntent.LANGUAGE_MODEL_FREE_FORM); i.putExtra(RecognizerIntent.EXTRA_LANGUAGE,Locale.getDefault()); try{startActivityForResult(i,VOICE_CODE);}catch(Exception ignored){}});
        String[] cats={"All","Novel","Self Help","Programming","Education","Science","Biography","Comics"};
        GridView grid=v.findViewById(R.id.categoryGrid); grid.setAdapter(new ArrayAdapter<String>(requireContext(),R.layout.category_item,cats)); grid.setOnItemClickListener((p,view,pos,id)->{String c=cats[pos]; if(c.equals("All")) filter(""); else filter(c);});
        loadBooks(); return v;
    }
    private void loadBooks(){ allBooks.clear(); allBooks.add(new Book("demo1","The Alchemist","Paulo Coelho","Novel","A story about dreams, courage and finding your path.","demo-shreya","Shreya","","Good","book1",true,1)); allBooks.add(new Book("demo2","Atomic Habits","James Clear","Self Help","Small habits can create remarkable results.","demo-snehal","Snehal","","Good","book2",true,2)); allBooks.add(new Book("demo3","Clean Code","Robert C. Martin","Programming","Practical ideas for writing maintainable software.","demo-shreya","Shreya","","Good","book3",true,3)); allBooks.add(new Book("demo4","Java Programming","Herbert Schildt","Education","A practical reference for learning Java programming.","demo-snehal","Snehal","","Fair","book4",true,4)); allBooks.addAll(LocalBookStore.load(requireContext())); adapter.notifyDataSetChanged(); FirebaseDatabase.getInstance().getReference("Books").addListenerForSingleValueEvent(new ValueEventListener(){@Override public void onDataChange(@NonNull DataSnapshot s){if(s.exists()){for(DataSnapshot ds:s.getChildren()){Book b=ds.getValue(Book.class);if(b!=null){b.setId(ds.getKey());allBooks.add(b);}}}visibleBooks.clear();visibleBooks.addAll(allBooks);adapter.notifyDataSetChanged();updateCount();} @Override public void onCancelled(@NonNull DatabaseError e){visibleBooks.clear();visibleBooks.addAll(allBooks);adapter.notifyDataSetChanged();updateCount();}}); visibleBooks.clear(); visibleBooks.addAll(allBooks); updateCount();}
    private void filter(String q){String x=q.toLowerCase().trim();visibleBooks.clear();for(Book b:allBooks){String hay=(b.getTitle()+" "+b.getAuthor()+" "+b.getCategory()+" "+b.getOwnerName()).toLowerCase();if(x.isEmpty()||hay.contains(x))visibleBooks.add(b);}adapter.notifyDataSetChanged();updateCount();}
    private void updateCount(){if(resultCount!=null)resultCount.setText(visibleBooks.size()+" books found");}
    @Override public void onActivityResult(int requestCode,int resultCode,@Nullable Intent data){super.onActivityResult(requestCode,resultCode,data);if(requestCode==VOICE_CODE&&resultCode==Activity.RESULT_OK&&data!=null&&data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS)!=null){ArrayList<String> r=data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);if(!r.isEmpty()){searchEt.setText(r.get(0));searchEt.setSelection(searchEt.length());}}}
}
