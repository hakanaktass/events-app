package com.example.project.NotificationListener;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.project.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;

public class DeleteApplicationActivity extends AppCompatActivity {

    private final String TAG = "MustafaOnurDeleteAppActivity";
    private int chosenAppCount;
    private int chosenAppLimit = 3;

    private ArrayList<String> chosenApplicationsList = new ArrayList<>();

    SharedPreferences sharedPreferences;
    SharedPreferences.Editor preferenceEditor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete_application);
        Objects.requireNonNull(getSupportActionBar())
                .setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.customBackground)));
        getSupportActionBar().setTitle("");

        Intent starterIntent = getIntent();
        chosenAppLimit = starterIntent.getIntExtra("chosenAppLimit", 3);

        Log.i(TAG, "onCreate(): chosenAppLimit = " + chosenAppLimit);

        sharedPreferences = getSharedPreferences("ComputerProject_1_4", Context.MODE_PRIVATE);
        preferenceEditor = sharedPreferences.edit();

        chosenAppCount = sharedPreferences.getInt("chosenAppCount", 0);

        for (int i = 0; i < chosenAppCount; i++) {
            chosenApplicationsList.add(sharedPreferences.getString("chosenApp[" + i + "]", ""));

            Log.i(TAG, "deleteApplicationFromList: " + sharedPreferences.getString("chosenApp[" + i + "]", "") +
                    " has been added to the list");
        }//for (int i = 0; i < chosenAppCount; i++)

        setCheckboxesAsClicked();
    }//protected void onCreate(Bundle savedInstanceState)

    private void setCheckboxesAsClicked() {
        CheckBox appCheckbox;

        ArrayList<String> packageNamesArrayList = new ArrayList<>();
        HashMap<String, Integer> packageNameToCheckboxId = new HashMap<>();

        packageNamesArrayList.add(0, "com.discord");
        packageNamesArrayList.add(1, "com.facebook.katana");
        packageNamesArrayList.add(2, "com.google.android.gm");
        packageNamesArrayList.add(3, "com.google.android.googlequicksearchbox");
        packageNamesArrayList.add(4, "com.instagram.android");
        packageNamesArrayList.add(5, "com.linkedin.android");
        packageNamesArrayList.add(6, "com.microsoft.office.outlook");
        packageNamesArrayList.add(7, "com.zhiliaoapp.musically");
        packageNamesArrayList.add(8, "com.twitter.android");
        packageNamesArrayList.add(9, "com.whatsapp");

        packageNameToCheckboxId.put("com.discord", R.id.addDiscordCheckbox);
        packageNameToCheckboxId.put("com.facebook.katana", R.id.addFacebookCheckbox);
        packageNameToCheckboxId.put("com.google.android.gm", R.id.addGmailCheckbox);
        packageNameToCheckboxId.put("com.google.android.googlequicksearchbox", R.id.addGoogleCheckbox);
        packageNameToCheckboxId.put("com.instagram.android", R.id.addInstagramCheckbox);
        packageNameToCheckboxId.put("com.linkedin.android", R.id.addLinkedInCheckbox);
        packageNameToCheckboxId.put("com.microsoft.office.outlook", R.id.addOutlookCheckbox);
        packageNameToCheckboxId.put("com.zhiliaoapp.musically", R.id.addTikTokCheckbox);
        packageNameToCheckboxId.put("com.twitter.android", R.id.addTwitterCheckbox);
        packageNameToCheckboxId.put("com.whatsapp", R.id.addWhatsAppCheckbox);

        //In this for loop, all values in packageNamesArrayList is checked to see whether they are in
        //chosenApplicationList or not. If a packageName is not in chosenApplicationList then
        //the checkbox with that id is disabled so the user can know that app cannot be deleted
        for (int i = 0; i < packageNameToCheckboxId.size(); i++) {
            String hashMapKey = packageNamesArrayList.get(i);

            if(chosenApplicationsList.contains(hashMapKey)) {
                appCheckbox = findViewById(Objects.requireNonNull(packageNameToCheckboxId.get(hashMapKey)));
                appCheckbox.setEnabled(true);
            }//if(packageNameToCheckboxId.containsKey(hashMapKey))

            else {
                appCheckbox = findViewById(Objects.requireNonNull(packageNameToCheckboxId.get(hashMapKey)));
                appCheckbox.setEnabled(false);
            }

        }//for (int i = 0; i < chosenAppCount; i++)

    }//private void setCheckboxesAsClicked()

    public void setOnClickListener(View view) {
        boolean isChecked = ((CheckBox) view).isChecked();

        if(view.getId() == R.id.addDiscordCheckbox) {
            if(isChecked) {
                deleteApplicationFromList("com.discord");
            }//if(isChecked)

            else {
                Toast.makeText(this, "To add an application, please use Add App option.",
                        Toast.LENGTH_SHORT).show();
                ((CheckBox) view).setChecked(true);
            }//else --> !isChecked
        }//if(view.getId() == R.id.addDiscordCheckbox && isChecked)

        else if(view.getId() == R.id.addFacebookCheckbox) {
            if(isChecked) {
                deleteApplicationFromList("com.facebook.katana");
            }//if(isChecked)

            else {
                Toast.makeText(this, "To add an application, please use Add App option.",
                        Toast.LENGTH_SHORT).show();
                ((CheckBox) view).setChecked(true);
            }//else --> !isChecked
        }//else if(view.getId() == R.id.addFacebookCheckbox && isChecked)

        else if(view.getId() == R.id.addGmailCheckbox) {
            if(isChecked) {
                deleteApplicationFromList("com.google.android.gm");
            }//if(isChecked)

            else {
                Toast.makeText(this, "To add an application, please use Add App option.",
                        Toast.LENGTH_SHORT).show();
                ((CheckBox) view).setChecked(true);
            }//else --> !isChecked
        }//else if(view.getId() == R.id.addGmailCheckbox && isChecked)

        else if(view.getId() == R.id.addGoogleCheckbox) {
            if(isChecked) {
                deleteApplicationFromList("com.google.android.googlequicksearchbox");
            }//if(isChecked)

            else {
                Toast.makeText(this, "To add an application, please use Add App option.",
                        Toast.LENGTH_SHORT).show();
                ((CheckBox) view).setChecked(true);
            }//else --> !isChecked
        }//else if(view.getId() == R.id.addGoogleCheckbox && isChecked)

        else if(view.getId() == R.id.addInstagramCheckbox) {
            if(isChecked) {
                deleteApplicationFromList("com.instagram.android");
            }//if(isChecked)

            else {
                Toast.makeText(this, "To add an application, please use Add App option.",
                        Toast.LENGTH_SHORT).show();
                ((CheckBox) view).setChecked(true);
            }//else --> !isChecked
        }//else if(view.getId() == R.id.addInstagramCheckbox && isChecked)

        else if(view.getId() == R.id.addLinkedInCheckbox) {
            if(isChecked) {
                deleteApplicationFromList("com.linkedin.android");
            }//if(isChecked)

            else {
                Toast.makeText(this, "To add an application, please use Add App option.",
                        Toast.LENGTH_SHORT).show();
                ((CheckBox) view).setChecked(true);
            }//else --> !isChecked
        }//else if(view.getId() == R.id.addLinkedInCheckbox && isChecked)

        else if(view.getId() == R.id.addOutlookCheckbox) {
            if(isChecked) {
                deleteApplicationFromList("com.microsoft.office.outlook");
            }//if(isChecked)

            else {
                Toast.makeText(this, "To add an application, please use Add App option.",
                        Toast.LENGTH_SHORT).show();
                ((CheckBox) view).setChecked(true);
            }//else --> !isChecked
        }//else if(view.getId() == R.id.addOutlookCheckbox && isChecked)

        else if(view.getId() == R.id.addTikTokCheckbox) {
            if(isChecked) {
                deleteApplicationFromList("com.zhiliaoapp.musically");
            }//if(isChecked)

            else {
                Toast.makeText(this, "To add an application, please use Add App option.",
                        Toast.LENGTH_SHORT).show();
                ((CheckBox) view).setChecked(true);
            }//else --> !isChecked
        }//else if(view.getId() == R.id.addTikTokCheckbox && isChecked)

        else if(view.getId() == R.id.addTwitterCheckbox) {
            if(isChecked) {
                deleteApplicationFromList("com.twitter.android");
            }//if(isChecked)

            else {
                Toast.makeText(this, "To add an application, please use Add App option.",
                        Toast.LENGTH_SHORT).show();
                ((CheckBox) view).setChecked(true);
            }//else --> !isChecked
        }//else if(view.getId() == R.id.addTwitterCheckbox && isChecked)

        else if(view.getId() == R.id.addWhatsAppCheckbox) {
            if(isChecked) {
                deleteApplicationFromList("com.whatsapp");
            }//if(isChecked)

            else {
                Toast.makeText(this, "To add an application, please use Add App option.",
                        Toast.LENGTH_SHORT).show();
                ((CheckBox) view).setChecked(true);
            }//else --> !isChecked
        }//else if(view.getId() == R.id.addWhatsAppCheckbox && isChecked)

    }//public void setOnClickListener(View view)

    private void deleteApplicationFromList(String appPackageName) {
        Log.i(TAG, "deleteChoiceFromApplicationList: chosenAppCount is " + chosenAppCount);

        HashMap<String, String> packageNameToAppName = new HashMap<>();

        packageNameToAppName.put("com.discord", "Discord");
        packageNameToAppName.put("com.facebook.katana", "Facebook");
        packageNameToAppName.put("com.google.android.gm", "Gmail");
        packageNameToAppName.put("com.google.android.googlequicksearchbox", "Google");
        packageNameToAppName.put("com.instagram.android", "Instagram");
        packageNameToAppName.put("com.linkedin.android", "LinkedIn");
        packageNameToAppName.put("com.microsoft.office.outlook", "Outlook");
        packageNameToAppName.put("com.twitter.android", "Twitter");
        packageNameToAppName.put("com.whatsapp", "WhatsApp");
        packageNameToAppName.put("com.zhiliaoapp.musically", "TikTok");

        if (chosenAppCount > 0 && chosenApplicationsList.contains(appPackageName)) {
            chosenApplicationsList.remove(appPackageName);
            chosenAppCount--;

            Toast.makeText(this, packageNameToAppName.get(appPackageName) +
                    " has been removed from the list", Toast.LENGTH_SHORT).show();

            Log.i(TAG, "deleteChoiceFromApplicationList: "
                    + packageNameToAppName.get(appPackageName) + " has been removed from list");
        }//if(chosenAppCount < chosenAppLimit)

        else if (chosenAppCount > 0 && !chosenApplicationsList.contains(appPackageName)) {
            Toast.makeText(this, "This app is not on the list.",
                    Toast.LENGTH_SHORT).show();

            Log.i(TAG, "deleteChoiceFromApplicationList: "
                    + packageNameToAppName.get(appPackageName) + " is not on the list.");
        }//else if (chosenAppCount > 0 && !chosenApplicationsList.contains(appPackageName))

        else if(chosenAppCount <= 0) {
            Toast.makeText(this, "The list is empty.", Toast.LENGTH_SHORT).show();
        }//else if(chosenAppCount <= 0)

        else {
            Toast.makeText(this, "Please don't choose more than " +
                    chosenAppLimit + " apps.", Toast.LENGTH_SHORT).show();
        }//else --> chosenAppCount >= chosenAppLimit && chosenApplicationsList.contains(appPackageName) == true

        Log.i(TAG, "deleteChoiceFromApplicationList: chosenAppCount is " + chosenAppCount);

        if(chosenAppCount >= 0) {
            for (int i = 0; i < chosenAppCount; i++) {
                preferenceEditor.remove("chosenApp[" + i + "]");

                Log.i(TAG, "deleteChoiceFromApplicationList: chosenApp[" + i + "] has been removed");
            }

            for (int i = 0; i < chosenApplicationsList.size(); i++) {
                preferenceEditor.putString("chosenApp[" + i + "]", chosenApplicationsList.get(i));

                Log.i(TAG, "deleteChoiceFromApplicationList: chosenApp[" + i + "] has been re-added");
            }

            preferenceEditor.putInt("chosenAppCount", chosenAppCount);
        }//if(chosenAppCount < chosenAppLimit)

        preferenceEditor.apply();

    }//private void deleteApplicationFromList(String appPackageName)

}//public class AddApplicationActivity extends AppCompatActivity