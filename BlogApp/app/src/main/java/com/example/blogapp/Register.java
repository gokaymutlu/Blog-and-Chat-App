package com.example.blogapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class Register extends AppCompatActivity {

    private EditText registerEmail,registerPassword;
    private Button registerBtn;
    private TextView alreadyhaveAnAccount;
    FirebaseAuth mAuth;
    ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        registerEmail=findViewById(R.id.register_email);
        registerPassword=findViewById(R.id.register_password);
        registerBtn=findViewById(R.id.register_btn);
        alreadyhaveAnAccount=findViewById(R.id.already_have_an_account);

        mAuth =FirebaseAuth.getInstance();


        ActionBar actionBar=getSupportActionBar();
        actionBar.setTitle("Register");
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);
        progressDialog= new ProgressDialog(this);


        alreadyhaveAnAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Register.this,MainActivity.class ));
            }
        });

        registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = registerEmail.getText().toString().trim();
                String password=registerPassword.getText().toString().trim();

                if(TextUtils.isEmpty(email)){
                    registerEmail.setError("Email girmelisiniz!!!");
                }else if(TextUtils.isEmpty(password)){
                    registerPassword.setError("Şifre girmelisiniz!!!");
                }else if(password.length() < 6){
                    Toast.makeText(Register.this,"Şifre 6 karakterden fazla olmalı!!",Toast.LENGTH_SHORT).show();
                }else{
                    registerUser(email,password);
                }

            }
        });

    }

    private void registerUser(String email, String password) {
    progressDialog.setTitle("Lütfen Bekleyiniz...");
    progressDialog.show();

    mAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
        @Override
        public void onComplete(@NonNull Task<AuthResult> task) {
            if (task.isSuccessful()){
               progressDialog.dismiss();
               startActivity(new Intent(Register.this,HomeActivity.class));
                Toast.makeText(Register.this,"Giriş başarılı",Toast.LENGTH_SHORT).show();
            }else{
                Toast.makeText(Register.this,"Giriş başarısız",Toast.LENGTH_SHORT).show();
            }



        }
    }).addOnFailureListener(new OnFailureListener() {
        @Override
        public void onFailure(@NonNull Exception e) {
            progressDialog.dismiss();
            Toast.makeText(Register.this,""+e.getMessage(),Toast.LENGTH_SHORT).show();
        }
    });

    }
}