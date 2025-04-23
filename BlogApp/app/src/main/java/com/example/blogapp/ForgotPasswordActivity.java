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
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class ForgotPasswordActivity extends AppCompatActivity {
    private EditText email;
    private Button recoverPassword;
    FirebaseAuth auth;
    ProgressDialog pd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);
        email=findViewById(R.id.forgotEmail);
        recoverPassword=findViewById(R.id.forgotBtn);

        ActionBar actionBar=getSupportActionBar();
        actionBar.setTitle("Şifremi unuttum");
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);
        pd=new ProgressDialog(this);
        auth=FirebaseAuth.getInstance();

        recoverPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String etEmail=email.getText().toString();
                if (TextUtils.isEmpty(etEmail)){
                    email.setError("Email gerekli");
                }else{
                    recoverPasswords(etEmail);
                }

            }
        });



    }

    private void recoverPasswords(String email) {
        pd.setMessage("Lütfen bekleyiniz..");
        pd.show();

        auth.sendPasswordResetEmail(email).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
            if (task.isSuccessful()){
                Toast.makeText(ForgotPasswordActivity.this, "Şifre yenileme için e-postanızı kontrol ediniz", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(ForgotPasswordActivity.this,MainActivity.class));
                pd.dismiss();
            }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                pd.dismiss();
                Toast.makeText(ForgotPasswordActivity.this, ""+e, Toast.LENGTH_SHORT).show();
            }
        });

    }
}