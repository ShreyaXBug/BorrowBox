package com.example.borrowbox;

import android.os.Bundle;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.example.borrowbox.fragments.HomeFragment;
import com.example.borrowbox.fragments.SearchFragment;
import com.example.borrowbox.fragments.RequestsFragment;
import com.example.borrowbox.fragments.ProfileFragment;

public class MainActivity extends AppCompatActivity {

    private LinearLayout navHome;
    private LinearLayout navSearch;
    private LinearLayout navRequests;
    private LinearLayout navProfile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        navHome = findViewById(R.id.navHome);
        navSearch = findViewById(R.id.navSearch);
        navRequests = findViewById(R.id.navRequests);
        navProfile = findViewById(R.id.navProfile);

        // Open Home screen by default
        if (savedInstanceState == null) {
            loadFragment(new HomeFragment());
        }

        navHome.setOnClickListener(v ->
                loadFragment(new HomeFragment()));

        navSearch.setOnClickListener(v ->
                loadFragment(new SearchFragment()));

        navRequests.setOnClickListener(v ->
                loadFragment(new RequestsFragment()));

        navProfile.setOnClickListener(v ->
                loadFragment(new ProfileFragment()));
    }

    private void loadFragment(Fragment fragment) {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragmentContainer, fragment)
                .commit();
    }
}