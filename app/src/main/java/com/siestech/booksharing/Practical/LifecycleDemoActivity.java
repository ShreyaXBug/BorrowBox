package com.siestech.booksharing.Practical;
import android.os.Bundle; import android.widget.TextView; import androidx.appcompat.app.AppCompatActivity; import com.siestech.booksharing.R;
public class LifecycleDemoActivity extends AppCompatActivity{
 private TextView log;
 private void add(String s){if(log!=null)log.append(s+"\n");}
 @Override protected void onCreate(Bundle b){super.onCreate(b);setContentView(R.layout.activity_lifecycle_demo);log=findViewById(R.id.lifecycleLog);add("onCreate() → Activity created");}
 @Override protected void onStart(){super.onStart();add("onStart() → Activity visible");}
 @Override protected void onResume(){super.onResume();add("onResume() → Activity interactive");}
 @Override protected void onPause(){add("onPause() → Activity losing focus");super.onPause();}
 @Override protected void onStop(){add("onStop() → Activity no longer visible");super.onStop();}
 @Override protected void onDestroy(){add("onDestroy() → Activity destroyed");super.onDestroy();}
}
