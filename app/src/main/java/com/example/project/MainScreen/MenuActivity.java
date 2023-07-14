package com.example.project.MainScreen;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.view.View;

import com.example.project.NotificationListener.NotificationActivity;
import com.example.project.R;
import com.example.project.databinding.ActivityMenuBinding;
import com.google.firebase.auth.FirebaseAuth;

import java.util.Objects;

public class MenuActivity extends AppCompatActivity {

    private ActivityMenuBinding binding;

    private FirebaseAuth auth;

    private int notificationID = 7446688;  //"Signout" word coded in numeric keypad
    private String CHANNEL_ID = "Sign Out Notifications";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMenuBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
        Objects.requireNonNull(getSupportActionBar()).setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.customBackground)));
        getSupportActionBar().setTitle("Menu");

        auth = FirebaseAuth.getInstance();

        createNotificationChannel();

       /* backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(MenuActivity.this);

                alertDialogBuilder.setTitle("Quit");

                alertDialogBuilder.setMessage("Are you sure?")
                        .setCancelable(false)
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                auth.signOut();
                                Intent intent = new Intent(MenuActivity.this, LoginActivity.class);
                                startActivity(intent);
                            }
                        }).setNegativeButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.cancel();
                            }
                        });

            }
        });
*/
    }

    private void createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = getString(R.string.channel_name);
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name, importance);

            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }

    public void generalOnClick(View view){

        Intent intent = new Intent(MenuActivity.this, ApiAppsActivity.class);
        startActivity(intent);
    }

    public void backOnClick(View view){
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(MenuActivity.this);

        Intent loginActivityIntent = new Intent(MenuActivity.this, LoginActivity.class);
        loginActivityIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);

        PendingIntent signOutNotificationIntent = PendingIntent.getActivity(this, 0,
                loginActivityIntent, PendingIntent.FLAG_IMMUTABLE);

        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this, CHANNEL_ID)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle("Sign Out Successful")
                .setContentText("Click this notification to login")
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setContentIntent(signOutNotificationIntent)
                .setAutoCancel(true);

        alertDialogBuilder.setMessage("Are you sure you want to Sign Out?")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        MenuActivity.super.onBackPressed();
                        auth.signOut();
                        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(MenuActivity.this);
                        notificationManager.notify(notificationID, notificationBuilder.build());
                        Intent intent = new Intent(MenuActivity.this, LoginActivity.class);
                        startActivity(intent);
                    }
                }).setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.cancel();
                    }
                });

        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }


    public void notificationOnClick(View view){

        Intent intent = new Intent(MenuActivity.this, NotificationActivity.class);
        startActivity(intent);

    }


}