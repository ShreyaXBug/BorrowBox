package com.siestech.booksharing;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {
    private FirebaseAuth auth;
    private android.widget.EditText email,password;
    @Override protected void onCreate(Bundle b){super.onCreate(b);
        setContentView(R.layout.activity_login);auth=FirebaseAuth.getInstance();email=findViewById(R.id.emailET);
        password=findViewById(R.id.passwordET);
        findViewById(R.id.loginBtn).setOnClickListener(v->login()); 
        findViewById(R.id.goToSignup).setOnClickListener(v->startActivity(new Intent(this,SignUpActivity.class)));
    }
    private void login(){String e=email.getText().toString().trim(),p=password.getText().toString();
        if(TextUtils.isEmpty(e)||TextUtils.isEmpty(p)){Toast.makeText(this,"Enter email and password",Toast.LENGTH_SHORT).show();return;}auth.signInWithEmailAndPassword(e,p).addOnCompleteListener(t->{if(t.isSuccessful()){startActivity(new Intent(this,MainActivity.class));finish();}else Toast.makeText(this,t.getException()==null?"Login failed":t.getException().getMessage(),Toast.LENGTH_LONG).show();});}
    @Override protected void onStart(){super.onStart(); if(auth.getCurrentUser()!=null){startActivity(new Intent(this,MainActivity.class));finish();}}
}
