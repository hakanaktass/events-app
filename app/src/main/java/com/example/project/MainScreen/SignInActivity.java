package com.example.project.MainScreen;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Toast;

import com.example.project.R;
import com.example.project.databinding.ActivitySignInBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

public class SignInActivity extends AppCompatActivity {

    private ActivitySignInBinding binding;
    private FirebaseAuth auth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySignInBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
        getSupportActionBar().hide();

        auth = FirebaseAuth.getInstance();
    }

    public void onCreateButtonClick (View view){


    String nameSurname = binding.nameSurnameText2.getText().toString();
    String email = binding.emailText2.getText().toString();
    String password = binding.passwordText2.getText().toString();
    String cpassword = binding.cpasswordText2.getText().toString();
    //String phoneNumber = binding.mobileText2.getText().toString();

    if (email.isEmpty()){
            binding.emailText2.setError("Email is required!");
            binding.emailText2.requestFocus();
            return;
    }

    if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            binding.emailText2.setError("Please provide valid email adress.");
            binding.emailText2.requestFocus();
            return;
    }

    if (nameSurname.isEmpty()){
        binding.nameSurnameText2.setError("Full name is required!");
        binding.nameSurnameText2.requestFocus();
        return;
    }


    if (password.isEmpty()){
            binding.passwordText2.setError("Password is required!");
            binding.passwordText2.requestFocus();
            return;
    }

    if (!password.equals(cpassword)){
            binding.passwordText2.setError("Passwords are not matched!");
            binding.passwordText2.requestFocus();
            return;
    }

    if (password.length() < 6){
            binding.passwordText2.setError("Min password length should be 6 characters!");
            binding.passwordText2.requestFocus();
            return;

    }

   // if (phoneNumber.isEmpty()){
        //    binding.mobileText2.setError("Mobile number is required!");
       //     binding.mobileText2.requestFocus();
       //     return;
   // }

    else{
        auth.createUserWithEmailAndPassword(email,password).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
            @Override
            public void onSuccess(AuthResult authResult) {
                Toast.makeText(SignInActivity.this, "Succesfuly.", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(SignInActivity.this,LoginActivity.class);
            startActivity(intent);
            finish();

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(SignInActivity.this, e.getLocalizedMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }



    }

}


   /* auth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
        @Override
 public void onComplete(@NonNull Task<AuthResult> task) {
        if (task.isSuccessful()){
        User user = new com.example.project.MainScreen.User(nameSurname, email, password, phoneNumber);
        FirebaseDatabase.getInstance().getReference("Profiles").child(FirebaseAuth.getInstance().getCurrentUser().getUid())
        setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
@Override
public void onComplete(@NonNull Task<Void> task) {
        if (task.isSuccessful()){
        Toast.makeText(SignInActivity.this,"User has been created.",Toast.LENGTH_LONG).show();
        Intent intent = new Intent(SignInActivity.this,LoginActivity.class);
        startActivity(intent);
        finish();

        }else{
        Toast.makeText(SignInActivity.this,"Failed to Sign Up.Try Again!",Toast.LENGTH_LONG).show();
        }
        }
        });
        }else{
        Toast.makeText(SignInActivity.this,"The email may be already taken!",Toast.LENGTH_LONG).show();
        }
        }
        });


    */