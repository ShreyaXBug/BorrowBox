package com.siestech.booksharing.Practical;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.widget.ImageView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import com.siestech.booksharing.R;

public class CameraActivity extends AppCompatActivity {
    private ImageView image;
    private static final int CAMERA = 55;
    private static final int CAMERA_PERMISSION = 56;
    @Override protected void onCreate(Bundle b){
        super.onCreate(b); setContentView(R.layout.activity_camera); image=findViewById(R.id.capturedImage);
        findViewById(R.id.captureBtn).setOnClickListener(v->capture());
    }
    private void capture(){
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, CAMERA_PERMISSION);
            return;
        }
        Intent i=new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        try{startActivityForResult(i,CAMERA);}catch(Exception e){Toast.makeText(this,"Camera not available",Toast.LENGTH_SHORT).show();}
    }
    @Override public void onRequestPermissionsResult(int r,@NonNull String[] p,@NonNull int[] g){super.onRequestPermissionsResult(r,p,g);if(r==CAMERA_PERMISSION&&g.length>0&&g[0]==PackageManager.PERMISSION_GRANTED)capture();}
    @Override protected void onActivityResult(int r,int c,Intent d){super.onActivityResult(r,c,d);if(r==CAMERA&&c==RESULT_OK&&d!=null&&d.getExtras()!=null){Bitmap b=(Bitmap)d.getExtras().get("data");if(b!=null)image.setImageBitmap(b);}}
}
