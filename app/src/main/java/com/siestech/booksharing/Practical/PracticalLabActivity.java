package com.siestech.booksharing.Practical;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import com.siestech.booksharing.R;

public class PracticalLabActivity extends AppCompatActivity{
 @Override protected void onCreate(Bundle b){super.onCreate(b);setContentView(R.layout.activity_practical_lab);
  findViewById(R.id.lifecycleBtn).setOnClickListener(v->startActivity(new Intent(this,LifecycleDemoActivity.class)));
  findViewById(R.id.layoutBtn).setOnClickListener(v->startActivity(new Intent(this,LayoutDemoActivity.class)));
  findViewById(R.id.fragmentBtn).setOnClickListener(v->startActivity(new Intent(this,FragmentDemoActivity.class)));
  findViewById(R.id.intentBtn).setOnClickListener(v->startActivity(new Intent(this,IntentLabActivity.class)));
  findViewById(R.id.cameraBtn).setOnClickListener(v->startActivity(new Intent(this,CameraActivity.class)));
  findViewById(R.id.sqliteBtn).setOnClickListener(v->startActivity(new Intent(this,SQLiteLibraryActivity.class)));
  findViewById(R.id.smsBtn).setOnClickListener(v->startActivity(new Intent(this,SmsActivity.class)));
  findViewById(R.id.bluetoothBtn).setOnClickListener(v->startActivity(new Intent(this,BluetoothShareActivity.class)));
  findViewById(R.id.ttsBtn).setOnClickListener(v->startActivity(new Intent(this,TtsActivity.class)));
  findViewById(R.id.locationBtn).setOnClickListener(v->startActivity(new Intent(this,LocationActivity.class)));
 }
}
