package com.unipi.ntaf.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class Login extends AppCompatActivity {
    private FirebaseAuth mAuth;
    EditText editText1,editText2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mAuth = FirebaseAuth.getInstance();
        editText1 = findViewById(R.id.editText1);
        editText2 = findViewById(R.id.editText2);
    }

    public void signup(View view){
        try {
            mAuth.createUserWithEmailAndPassword(
                    editText1.getText().toString(),editText2.getText().toString())
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()){
                                Toast.makeText(Login.this,
                                        "Successfull SignUp!", Toast.LENGTH_SHORT).show();
                            }else {
                                Toast.makeText(Login.this,
                                        task.getException().getMessage(),
                                        Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
            editText1.setText("");
            editText2.setText("");
        }
        catch (Exception e) {
            Toast.makeText(Login.this,
                    "Try again", Toast.LENGTH_SHORT).show();
            editText1.setText("");
            editText2.setText("");
        }
    }



    public void signin(View view){
        try {
            mAuth.signInWithEmailAndPassword(
                    editText1.getText().toString(),editText2.getText().toString())
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()){
                                Toast.makeText(Login.this,
                                        "Successfull login!", Toast.LENGTH_SHORT).show();
                                login(null);
                            }else {
                                Toast.makeText(Login.this,
                                        task.getException().getMessage(),
                                        Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
            editText1.setText("");
            editText2.setText("");
        }
        catch (Exception e) {
            Toast.makeText(Login.this,
                    "Try again!", Toast.LENGTH_SHORT).show();
            editText1.setText("");
            editText2.setText("");
        }
    }

    public void login(View view) {
        Intent intent = new Intent(this,Users.class);
        startActivity(intent);
    }
}
