package com.siestech.booksharing.Practical;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.siestech.booksharing.R;

public class LayoutDemoActivity extends AppCompatActivity{
 @Override protected void onCreate(Bundle b){super.onCreate(b);setContentView(R.layout.activity_layout_demo);
  findViewById(R.id.customToastBtn).setOnClickListener(v->{
   View view= LayoutInflater.from(this).inflate(R.layout.toast_custom,null);
   Toast t=new Toast(this); t.setDuration(Toast.LENGTH_SHORT); t.setView(view); t.show();
  });
 }
}
