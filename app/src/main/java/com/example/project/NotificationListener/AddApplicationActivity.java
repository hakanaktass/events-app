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

@SuppressWarnings("ConstantConditions")
public class AddApplicationActivity extends AppCompatActivity {
    private static final String TAG = "MustafaOnurAddAppActivity";
    private int chosenAppCount;
    private int chosenAppLimit;

    private ArrayList<String> chosenApplicationsList = new ArrayList<>();

    SharedPreferences sharedPreferences;
    SharedPreferences.Editor preferenceEditor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_application);
        Objects.requireNonNull(getSupportActionBar())
                .setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.customBackground)));
        getSupportActionBar().setTitle("");

        Intent starterIntent = getIntent();
        chosenAppLimit = starterIntent.getIntExtra("chosenAppLimit", 3);

        Log.i(TAG, "onCreate: chosenAppLimit = " + chosenAppLimit);

        sharedPreferences = getSharedPreferences("ComputerProject_1_4", Context.MODE_PRIVATE);
        preferenceEditor = sharedPreferences.edit();

        chosenAppCount = sharedPreferences.getInt("chosenAppCount", 0);

        for (int i = 0; i < chosenAppCount; i++) {
            chosenApplicationsList.add(sharedPreferences.getString("chosenApp[" + i + "]", ""));

            Log.i(TAG, "addApplicationToList: " + sharedPreferences.getString("chosenApp[" + i + "]", "") +
                    " has been added to the list");
        }//for (int i = 0; i < chosenAppCount; i++)

        setCheckboxesAsClicked();
    }//protected void onCreate(Bundle savedInstanceState)

    private void setCheckboxesAsClicked() {
        CheckBox appCheckbox;

        HashMap<String, Integer> packageNameToCheckboxId = new HashMap<>();

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

        //In this for loop, all values in chosenApplicationsList is searched and checkbox Id's for
        //already chosen apps are found using packageNameToCheckboxId HashMap data structure.
        //Then the checkbox with that id is set to checked so the user can know that app has been chosen before
        for (int i = 0; i < chosenAppCount; i++) {
            String hashMapKey = chosenApplicationsList.get(i);

            if(packageNameToCheckboxId.containsKey(hashMapKey)) {
                appCheckbox = findViewById(Objects.requireNonNull(packageNameToCheckboxId.get(hashMapKey)));
                appCheckbox.setChecked(true);
            }//if(packageNameToCheckboxId.containsKey(hashMapKey))

        }//for (int i = 0; i < chosenAppCount; i++)

    }//private void setCheckboxesAsClicked()

    public void setOnClickListener(View view) {
        boolean isChecked = ((CheckBox) view).isChecked();

        if(view.getId() == R.id.addDiscordCheckbox) {
            if(isChecked && chosenAppCount < chosenAppLimit) {
                addApplicationToList("com.discord");
            }//if(isChecked && chosenAppCount < chosenAppLimit)

            else if(isChecked && chosenAppCount >= chosenAppLimit) {
                Toast.makeText(this, "Please don't choose more than " +
                        chosenAppLimit + " apps.", Toast.LENGTH_SHORT).show();
                ((CheckBox) view).setChecked(false);
            }//else if(isChecked && chosenAppCount >= chosenAppLimit)

            else {
                Toast.makeText(this, "To remove an application, please use Delete App option.",
                        Toast.LENGTH_SHORT).show();
                ((CheckBox) view).setChecked(true);
            }//else --> !isChecked
        }//if(view.getId() == R.id.addDiscordCheckbox)

        else if(view.getId() == R.id.addFacebookCheckbox) {
            if(isChecked && chosenAppCount < chosenAppLimit) {
                addApplicationToList("com.facebook.katana");
            }//if(isChecked && chosenAppCount < chosenAppLimit)

            else if(isChecked && chosenAppCount >= chosenAppLimit) {
                Toast.makeText(this, "Please don't choose more than " +
                        chosenAppLimit + " apps.", Toast.LENGTH_SHORT).show();
                ((CheckBox) view).setChecked(false);
            }//else if(isChecked && chosenAppCount >= chosenAppLimit)

            else {
                Toast.makeText(this, "To remove an application, please use Delete App option.",
                        Toast.LENGTH_SHORT).show();
                ((CheckBox) view).setChecked(true);
            }//else --> !isChecked
        }//else if(view.getId() == R.id.addFacebookCheckbox)

        else if(view.getId() == R.id.addGmailCheckbox) {
            if(isChecked && chosenAppCount < chosenAppLimit) {
                addApplicationToList("com.google.android.gm");
            }//if(isChecked && chosenAppCount < chosenAppLimit)

            else if(isChecked && chosenAppCount >= chosenAppLimit) {
                Toast.makeText(this, "Please don't choose more than " +
                        chosenAppLimit + " apps.", Toast.LENGTH_SHORT).show();
                ((CheckBox) view).setChecked(false);
            }//else if(isChecked && chosenAppCount >= chosenAppLimit)

            else {
                Toast.makeText(this, "To remove an application, please use Delete App option.",
                        Toast.LENGTH_SHORT).show();
                ((CheckBox) view).setChecked(true);
            }//else --> !isChecked
        }//else if(view.getId() == R.id.addGmailCheckbox)

        else if(view.getId() == R.id.addGoogleCheckbox) {
            if(isChecked && chosenAppCount < chosenAppLimit) {
                addApplicationToList("com.google.android.googlequicksearchbox");
            }//if(isChecked && chosenAppCount < chosenAppLimit)

            else if(isChecked && chosenAppCount >= chosenAppLimit) {
                Toast.makeText(this, "Please don't choose more than " +
                        chosenAppLimit + " apps.", Toast.LENGTH_SHORT).show();
                ((CheckBox) view).setChecked(false);
            }//else if(isChecked && chosenAppCount >= chosenAppLimit)

            else {
                Toast.makeText(this, "To remove an application, please use Delete App option.",
                        Toast.LENGTH_SHORT).show();
                ((CheckBox) view).setChecked(true);
            }//else --> !isChecked
        }//else if(view.getId() == R.id.addGoogleCheckbox)

        else if(view.getId() == R.id.addInstagramCheckbox) {
            if(isChecked && chosenAppCount < chosenAppLimit) {
                addApplicationToList("com.instagram.android");
            }//if(isChecked && chosenAppCount < chosenAppLimit)

            else if(isChecked && chosenAppCount >= chosenAppLimit) {
                Toast.makeText(this, "Please don't choose more than " +
                        chosenAppLimit + " apps.", Toast.LENGTH_SHORT).show();
                ((CheckBox) view).setChecked(false);
            }//else if(isChecked && chosenAppCount >= chosenAppLimit)

            else {
                Toast.makeText(this, "To remove an application, please use Delete App option.",
                        Toast.LENGTH_SHORT).show();
                ((CheckBox) view).setChecked(true);
            }//else --> !isChecked
        }//else if(view.getId() == R.id.addInstagramCheckbox)

        else if(view.getId() == R.id.addLinkedInCheckbox) {
            if(isChecked && chosenAppCount < chosenAppLimit) {
                addApplicationToList("com.linkedin.android");
            }//if(isChecked && chosenAppCount < chosenAppLimit)

            else if(isChecked && chosenAppCount >= chosenAppLimit) {
                Toast.makeText(this, "Please don't choose more than " +
                        chosenAppLimit + " apps.", Toast.LENGTH_SHORT).show();
                ((CheckBox) view).setChecked(false);
            }//else if(isChecked && chosenAppCount >= chosenAppLimit)

            else {
                Toast.makeText(this, "To remove an application, please use Delete App option.",
                        Toast.LENGTH_SHORT).show();
                ((CheckBox) view).setChecked(true);
            }//else --> !isChecked
        }//else if(view.getId() == R.id.addLinkedInCheckbox)

        else if(view.getId() == R.id.addOutlookCheckbox) {
            if(isChecked && chosenAppCount < chosenAppLimit) {
                addApplicationToList("com.microsoft.office.outlook");
            }//if(isChecked && chosenAppCount < chosenAppLimit)

            else if(isChecked && chosenAppCount >= chosenAppLimit) {
                Toast.makeText(this, "Please don't choose more than " +
                        chosenAppLimit + " apps.", Toast.LENGTH_SHORT).show();
                ((CheckBox) view).setChecked(false);
            }//else if(isChecked && chosenAppCount >= chosenAppLimit)

            else {
                Toast.makeText(this, "To remove an application, please use Delete App option.",
                        Toast.LENGTH_SHORT).show();
                ((CheckBox) view).setChecked(true);
            }//else --> !isChecked
        }//else if(view.getId() == R.id.addOutlookCheckbox)

        else if(view.getId() == R.id.addTikTokCheckbox) {
            if(isChecked && chosenAppCount < chosenAppLimit) {
                addApplicationToList("com.zhiliaoapp.musically");
            }//if(isChecked && chosenAppCount < chosenAppLimit)

            else if(isChecked && chosenAppCount >= chosenAppLimit) {
                Toast.makeText(this, "Please don't choose more than " +
                        chosenAppLimit + " apps.", Toast.LENGTH_SHORT).show();
                ((CheckBox) view).setChecked(false);
            }//else if(isChecked && chosenAppCount >= chosenAppLimit)

            else {
                Toast.makeText(this, "To remove an application, please use Delete App option.",
                        Toast.LENGTH_SHORT).show();
                ((CheckBox) view).setChecked(true);
            }//else --> !isChecked
        }//else if(view.getId() == R.id.addTikTokCheckbox)

        else if(view.getId() == R.id.addTwitterCheckbox) {
            if(isChecked && chosenAppCount < chosenAppLimit) {
                addApplicationToList("com.twitter.android");
            }//if(isChecked && chosenAppCount < chosenAppLimit)

            else if(isChecked && chosenAppCount >= chosenAppLimit) {
                Toast.makeText(this, "Please don't choose more than " +
                        chosenAppLimit + " apps.", Toast.LENGTH_SHORT).show();
                ((CheckBox) view).setChecked(false);
            }//else if(isChecked && chosenAppCount >= chosenAppLimit)

            else {
                Toast.makeText(this, "To remove an application, please use Delete App option.",
                        Toast.LENGTH_SHORT).show();
                ((CheckBox) view).setChecked(true);
            }//else --> !isChecked
        }//else if(view.getId() == R.id.addTwitterCheckbox)

        else if(view.getId() == R.id.addWhatsAppCheckbox) {
            if(isChecked && chosenAppCount < chosenAppLimit) {
                addApplicationToList("com.whatsapp");
            }//if(isChecked && chosenAppCount < chosenAppLimit)

            else if(isChecked && chosenAppCount >= chosenAppLimit) {
                Toast.makeText(this, "Please don't choose more than " +
                        chosenAppLimit + " apps.", Toast.LENGTH_SHORT).show();
                ((CheckBox) view).setChecked(false);
            }//else if(isChecked && chosenAppCount >= chosenAppLimit)

            else {
                Toast.makeText(this, "To remove an application, please use Delete App option.",
                        Toast.LENGTH_SHORT).show();
                ((CheckBox) view).setChecked(true);
            }//else --> !isChecked
        }//else if(view.getId() == R.id.addWhatsAppCheckbox)

    }//public void setOnClickListener(View view)

    private void addApplicationToList(String appPackageName) {
        //This variable holds whether an application was added to the applicationList or not
        boolean isAddedToAppList = false;

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

        if (chosenAppCount < chosenAppLimit && !chosenApplicationsList.contains(appPackageName)) {
            chosenApplicationsList.add(appPackageName);
            chosenAppCount++;
            isAddedToAppList = true;

            Toast.makeText(this, packageNameToAppName.get(appPackageName) +
                    " has been added to the list", Toast.LENGTH_SHORT).show();

            Log.i(TAG, "addApplicationToList: packageNameToAppName.get(appPackageName) = " +
                    packageNameToAppName.get(appPackageName));
            Log.i(TAG, "addApplicationToList: chosenApplicationsList.get(chosenApplicationsList.size()-1) = " +
                    chosenApplicationsList.get(chosenApplicationsList.size() - 1));
        }//if(chosenAppCount < chosenAppLimit && !chosenApplicationsList.contains(appPackageName))

        else if (chosenApplicationsList.contains(appPackageName)) {
            Toast.makeText(this, "This app is already added to the list.",
                    Toast.LENGTH_SHORT).show();

            Log.i(TAG, "addApplicationToList: " + packageNameToAppName.get(appPackageName) +
                    " has been added to the list already.");
        }//else if (chosenApplicationsList.contains(appPackageName))

        else {
            Toast.makeText(this, "Please don't choose more than " +
                    chosenAppLimit + " apps.", Toast.LENGTH_SHORT).show();
        }//else --> chosenAppCount >= chosenAppLimit

        Log.i(TAG, "addApplicationToList: chosenAppCount is " + chosenAppCount);

        if (chosenAppCount <= chosenAppLimit && isAddedToAppList) {
            preferenceEditor.putString("chosenApp[" + (chosenAppCount - 1) + "]", appPackageName);
            preferenceEditor.putInt("chosenAppCount", chosenAppCount);

            Log.i(TAG, "addApplicationToList: " + sharedPreferences.getString("chosenApp[" + (chosenAppCount - 1) + "]", "") +
                    " is added to sharedPreferences");
        }//if(chosenAppCount < chosenAppLimit && isAddedToAppList)

        preferenceEditor.apply();

    }//private void addApplicationToList(String appPackageName)

}//public class AddApplicationActivity extends AppCompatActivity