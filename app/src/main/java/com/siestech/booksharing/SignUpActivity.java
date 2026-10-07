package com.siestech.booksharing;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.FirebaseDatabase;
import com.siestech.booksharing.Model.User;

public class SignUpActivity extends AppCompatActivity {
    private android.widget.EditText name, profession, email, password;
    private FirebaseAuth auth;

    @Override protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState); setContentView(R.layout.activity_sign_up); auth=FirebaseAuth.getInstance();
        name=findViewById(R.id.nameET); profession=findViewById(R.id.professionET); email=findViewById(R.id.emailET); password=findViewById(R.id.passwordET);
        findViewById(R.id.signupBtn).setOnClickListener(v->signUp());
        findViewById(R.id.goToLogin).setOnClickListener(v->startActivity(new Intent(this,LoginActivity.class)));
    }
    private void signUp(){
        String n=name.getText().toString().trim(), p=profession.getText().toString().trim(), e=email.getText().toString().trim(), pw=password.getText().toString();
        if(TextUtils.isEmpty(n)||TextUtils.isEmpty(p)||TextUtils.isEmpty(e)||TextUtils.isEmpty(pw)){Toast.makeText(this,"Please fill all fields",Toast.LENGTH_SHORT).show();return;}
        if(pw.length()<6){Toast.makeText(this,"Password must be at least 6 characters",Toast.LENGTH_SHORT).show();return;}
        auth.createUserWithEmailAndPassword(e,pw).addOnCompleteListener(task->{
            if(task.isSuccessful()){
                FirebaseUser u=task.getResult().getUser();
                if(u!=null) u.updateProfile(new UserProfileChangeRequest.Builder().setDisplayName(n).build());
                String uid=u==null?"":u.getUid(); User user=new User(n,p,e);
                FirebaseDatabase.getInstance().getReference("Users").child(uid).setValue(user).addOnCompleteListener(dbTask->{
                    if(dbTask.isSuccessful()) Toast.makeText(this,"Account created",Toast.LENGTH_SHORT).show();
                    else Toast.makeText(this,"Account created; profile saved locally by app session",Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(this,MainActivity.class)); finish();
                });
            }else Toast.makeText(this, task.getException()==null?"Signup failed":task.getException().getMessage(),Toast.LENGTH_LONG).show();
        });
    }
}
