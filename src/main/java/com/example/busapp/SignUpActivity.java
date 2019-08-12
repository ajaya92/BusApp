package com.example.busapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SignUpActivity extends AppCompatActivity {
    EditText c3_name, c4_email, c6_password, c7_verifypassword;
    FirebaseAuth auth;
    ProgressDialog dialog;
    DatabaseReference databaseReference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        auth = FirebaseAuth.getInstance();
        c3_name = findViewById(R.id.editText7);
        c4_email=findViewById(R.id.editText8);
        c6_password=findViewById(R.id.editText10);
        c7_verifypassword = findViewById(R.id.editText11);
        dialog = new ProgressDialog(this);

    }

    public void signUpUser(View view){
        dialog.setMessage("Registering. Please Wait.");
        dialog.show();
        String name= c3_name.getText().toString();
        String email= c4_email.getText().toString();
        String password= c6_password.getText().toString();
        String vpassword= c7_verifypassword.getText().toString();

        if(name.equals("") && email.equals("") && password.equals("") && vpassword.equals("")){
            Toast.makeText(getApplicationContext(), "Fields empty", Toast.LENGTH_SHORT).show();
        }
        else {
            auth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful()){
                                dialog.hide();
                                Toast.makeText(getApplicationContext(), "User registered successfully", Toast.LENGTH_SHORT).show();
                                databaseReference = FirebaseDatabase.getInstance().getReference().child("Users");
                                Users user_object = new Users(c3_name.getText().toString(), c4_email.getText().toString(), c6_password.getText().toString());
                                FirebaseUser firebaseUser = auth.getCurrentUser();
                                databaseReference.child(firebaseUser.getUid()).setValue(user_object)
                                .addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if(task.isSuccessful()){
                                            Toast.makeText(getApplicationContext(),"User data saved", Toast.LENGTH_SHORT).show();
                                            Intent i = new Intent(SignUpActivity.this, MainPageActivity.class);
                                            startActivity(i);
                                        }
                                        else {
                                            Toast.makeText(getApplicationContext(), "User data could not be saved", Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                });
                                /**/
                            }
                            else {
                                dialog.hide();
                                Toast.makeText(getApplicationContext(), "User could not be registered", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
        }
    }
}
