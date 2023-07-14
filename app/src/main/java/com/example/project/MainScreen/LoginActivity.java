package com.example.project.MainScreen;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Toast;

import com.example.project.databinding.ActivityLoginBinding;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity {

    private ActivityLoginBinding binding;
    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        auth = FirebaseAuth.getInstance();
        FirebaseUser user = auth.getCurrentUser();

        if (user != null){
            Intent intent = new Intent(LoginActivity.this,MenuActivity.class);
            startActivity(intent);
            finish();
        }
    }


    public void onSignInClick(View view){
        String email = binding.emailText.getText().toString();
        String password = binding.passwordText.getText().toString();

        if (email.isEmpty()){
            binding.emailText.setError("Email is required!");
            binding.emailText.requestFocus();
            return;
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            binding.emailText.setError("Please provide valid email adress.");
            binding.emailText.requestFocus();
            return;
        }

        if (password.isEmpty()){
            binding.passwordText.setError("Password is required!");
            binding.passwordText.requestFocus();
            return;
        }

        if (password.length() < 6){
            binding.passwordText.setError("Min password length should be 6 characters!");
            binding.passwordText.requestFocus();
            return;
        }
        else{
            auth.signInWithEmailAndPassword(email,password).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                @Override
                public void onSuccess(AuthResult authResult) {

                    Intent intent = new Intent(LoginActivity.this, MenuActivity.class);
                    startActivity(intent);
                    Toast.makeText(LoginActivity.this,"Login process is succesfull.",Toast.LENGTH_LONG).show();
                    finish();
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(LoginActivity.this, e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        }






    /*     auth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
           @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){
                    FirebaseUser user=FirebaseAuth.getInstance().getCurrentUser();

                    if (user.isEmailVerified()){
                        Toast.makeText(LoginActivity.this,"Login process is succesfull.",Toast.LENGTH_LONG).show();
                        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                        startActivity(intent);
                    }else{
                        user.sendEmailVerification();
                        Toast.makeText(LoginActivity.this,"You need to verify your email address.Please Check your email to verify account",Toast.LENGTH_LONG).show();
                    }

                }else{
                    count++;
                    int temp=count;
                    temp = 3-temp;
                    Toast.makeText(LoginActivity.this,"Failed to login! Please Try Again. You have "+temp+" login attemps.",Toast.LENGTH_SHORT).show();

                }
            }



        });
        if(count >= 2)
        {
            Intent intent = new Intent(LoginActivity.this, SignInActivity.class);
            startActivity(intent);
        }

     */
    }


    public void onSignUpClick(View view){

        Intent intent = new Intent(LoginActivity.this,SignInActivity.class);
        startActivity(intent);

    }
}