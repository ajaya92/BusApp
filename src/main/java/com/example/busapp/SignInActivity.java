package com.example.busapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class SignInActivity extends AppCompatActivity {
    EditText c1_email, c2_password;
    FirebaseAuth auth;
    ProgressDialog dialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
        auth = FirebaseAuth.getInstance();
        c1_email=findViewById(R.id.editText2);
        c2_password = findViewById(R.id.editText3);
        dialog=new ProgressDialog(this);


    }
    public void signinUser(View v){
        dialog.setMessage("Signing in. Please Wait");
        dialog.show();
        if(c1_email.getText().toString().equals("") && c2_password.getText().toString().equals("")){
            Toast.makeText(getApplicationContext(), "Field empty. Please enter valid email and password", Toast.LENGTH_SHORT).show();;
        }
        else{
            auth.signInWithEmailAndPassword(c1_email.getText().toString(), c2_password.getText().toString())
                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful()){
                                dialog.hide();
                                Toast.makeText(getApplicationContext(), "User successfully signed in",
                                        Toast.LENGTH_SHORT).show();

                                Intent i = new Intent(SignInActivity.this, MainPageActivity.class);
                                startActivity(i);
                                finish();

                            }
                            else{
                                dialog.hide();

                                Toast.makeText(getApplicationContext(), "User not found", Toast.LENGTH_SHORT).show();
                            }

                        }
                    });
        }
    }
}
