package com.siestech.booksharing;

import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.siestech.booksharing.Fragment.AddFragment;
import com.siestech.booksharing.Fragment.HomeFragment;
import com.siestech.booksharing.Fragment.NotificationFragment;
import com.siestech.booksharing.Fragment.ProfileFragment;
import com.siestech.booksharing.Fragment.SearchFragment;
import com.siestech.booksharing.Utils.ConnectivityReceiver;

public class MainActivity extends AppCompatActivity {
    private ConnectivityReceiver receiver;
    private TextView banner;

    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        banner = findViewById(R.id.networkBanner);
        open(HomeFragment.class);
        BottomNavigationView nav = findViewById(R.id.bottomNavigationView);
        nav.setOnItemSelectedListener(item -> {
            Fragment f;
            int id = item.getItemId();
            if (id == R.id.nav_notifications) f = new NotificationFragment();
            else if (id == R.id.nav_add) f = new AddFragment();
            else if (id == R.id.nav_search) f = new SearchFragment();
            else if (id == R.id.nav_profile) f = new ProfileFragment();
            else f = new HomeFragment();
            getSupportFragmentManager().beginTransaction().replace(R.id.container, f).commit();
            return true;
        });
        nav.setSelectedItemId(R.id.nav_home);
        receiver = new ConnectivityReceiver();
    }
    private void open(Class<? extends Fragment> c) {
        Fragment f;
        try { f = c.newInstance(); } catch (Exception e) { f = new HomeFragment(); }
        getSupportFragmentManager().beginTransaction().replace(R.id.container, f).commit();
    }
    @Override protected void onStart() { super.onStart(); registerReceiver(receiver, new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION)); }
    @Override protected void onStop() { try { unregisterReceiver(receiver); } catch (Exception ignored) {} super.onStop(); }
    public void setOnline(boolean online) { if (banner != null) banner.setVisibility(online ? View.GONE : View.VISIBLE); }
    public void openTab(int id) { ((BottomNavigationView) findViewById(R.id.bottomNavigationView)).setSelectedItemId(id); }
}
