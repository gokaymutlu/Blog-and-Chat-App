package com.example.blogapp;

import androidx.annotation.NonNull;
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
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {
        private EditText loginEmail,loginPassword;
        private Button loginBtn;
        private TextView forgotPassword,needAnAccount;
        FirebaseAuth auth;
        ProgressDialog progressDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        loginEmail=findViewById(R.id.login_email);
        loginPassword=findViewById(R.id.login_password);
        loginBtn=findViewById(R.id.login_btn);
        forgotPassword=findViewById(R.id.forgot_password);
        needAnAccount=findViewById(R.id.need_an_account);
        auth=FirebaseAuth.getInstance();
        progressDialog=new ProgressDialog(this);


        forgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this,ForgotPasswordActivity.class));
            }
        });



        needAnAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this,Register.class));
            }
        });

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email= loginEmail.getText().toString().trim();
                String password=loginPassword.getText().toString().trim();

                if (TextUtils.isEmpty(email)){
                    loginEmail.setError("Email gerekli!");
                }else if (TextUtils.isEmpty(password)){
                    loginPassword.setError("Şifre gerekli!");
                }else{
                    Login(email,password);
                }
            }
        });


    }

    private void Login(String email, String password) {
           progressDialog.setTitle("Lütfen Bekleyiniz...");
           progressDialog.show();

            auth.signInWithEmailAndPassword(email,password)
                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful()){
                                progressDialog.dismiss();
                                Toast.makeText(MainActivity.this,"Giriş Başarılı",Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(MainActivity.this,HomeActivity.class));
                            }else{
                                Toast.makeText(MainActivity.this, "Giriş Başarısız!", Toast.LENGTH_SHORT).show();
                                progressDialog.dismiss();
                            }
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(MainActivity.this,""+e.getMessage(),Toast.LENGTH_SHORT).show();
                            progressDialog.dismiss();
                        }
                    });


    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser user=auth.getCurrentUser();


        if(user != null){
            startActivity(new Intent(MainActivity.this,HomeActivity.class));
        }
    }
}