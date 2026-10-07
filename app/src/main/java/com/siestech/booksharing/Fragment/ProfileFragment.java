package com.siestech.booksharing.Fragment;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.siestech.booksharing.LoginActivity;
import com.siestech.booksharing.Practical.CameraActivity;
import com.siestech.booksharing.Practical.PracticalLabActivity;
import com.siestech.booksharing.R;

public class ProfileFragment extends Fragment {
    private de.hdodenhof.circleimageview.CircleImageView profile;
    private static final int CAMERA_CODE = 44;
    private static final int CAMERA_PERMISSION = 45;

    @Override public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View v=inflater.inflate(R.layout.fragment_profile,container,false);
        profile=v.findViewById(R.id.profile_image);
        android.widget.TextView name=v.findViewById(R.id.userName), profession=v.findViewById(R.id.profession), email=v.findViewById(R.id.userEmail);
        FirebaseAuth auth=FirebaseAuth.getInstance();
        if(auth.getCurrentUser()!=null){
            if(auth.getCurrentUser().getDisplayName()!=null) name.setText(auth.getCurrentUser().getDisplayName());
            email.setText(auth.getCurrentUser().getEmail());
            FirebaseDatabase.getInstance().getReference("Users").child(auth.getCurrentUser().getUid()).addListenerForSingleValueEvent(new ValueEventListener(){
                @Override public void onDataChange(@NonNull DataSnapshot s){String n=s.child("name").getValue(String.class), p=s.child("profession").getValue(String.class);if(n!=null&&!n.isEmpty())name.setText(n);if(p!=null&&!p.isEmpty())profession.setText(p);}
                @Override public void onCancelled(@NonNull DatabaseError e){}
            });
        }
        v.findViewById(R.id.practicalLabBtn).setOnClickListener(x->startActivity(new Intent(requireContext(), PracticalLabActivity.class)));
        v.findViewById(R.id.cameraBtn).setOnClickListener(x->capture());
        v.findViewById(R.id.logoutBtn).setOnClickListener(x->{auth.signOut();Intent i=new Intent(requireContext(), LoginActivity.class);i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);startActivity(i);});
        return v;
    }
    private void capture(){if(ActivityCompat.checkSelfPermission(requireContext(), Manifest.permission.CAMERA)!=PackageManager.PERMISSION_GRANTED){requestPermissions(new String[]{Manifest.permission.CAMERA},CAMERA_PERMISSION);return;}Intent i=new Intent(MediaStore.ACTION_IMAGE_CAPTURE);try{startActivityForResult(i,CAMERA_CODE);}catch(Exception e){Toast.makeText(requireContext(),"Camera not available",Toast.LENGTH_SHORT).show();}}
    @Override public void onRequestPermissionsResult(int r,@NonNull String[] p,@NonNull int[] g){super.onRequestPermissionsResult(r,p,g);if(r==CAMERA_PERMISSION&&g.length>0&&g[0]==PackageManager.PERMISSION_GRANTED)capture();}
    @Override public void onActivityResult(int r,int c,@Nullable Intent data){super.onActivityResult(r,c,data);if(r==CAMERA_CODE&&c==android.app.Activity.RESULT_OK&&data!=null&&data.getExtras()!=null){Bitmap b=(Bitmap)data.getExtras().get("data");if(b!=null){profile.setImageBitmap(b);Toast.makeText(requireContext(),"Photo updated for this session",Toast.LENGTH_SHORT).show();}}}
}
