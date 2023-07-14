package com.example.project.NotificationListener;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.project.R;

import java.util.ArrayList;
import java.util.HashMap;

public class NotificationSettings extends AppCompatActivity implements RecyclerViewInterface{

    private static final String TAG = "MustafaOnurNotificationSettings";

    private RecyclerView recyclerView;
    private RecyclerViewAdapter adapter;

    EditText chatInput;
    EditText appCountInput;

    Intent openAppAdderActivity;
    Intent openAppDeleterActivity;

    SharedPreferences sharedApplicationList;
    SharedPreferences sharedChatList;
    SharedPreferences.Editor chatListEditor;

    public ArrayList<String> chatList = new ArrayList<>();
    public ArrayList<String> applicationList = new ArrayList<>();
    public ArrayList<RecyclerViewDataModel> recyclerViewDataModels = new ArrayList<>();

    String chatInputString;

    int chosenAppCount;
    int addedChatsCount;
    int chosenAppLimit = 3;  //This variable has the value 3 by default

    int[] applicationLogos = {R.drawable.discord_logo_80_38, R.drawable.facebook_logo_80_38,
            R.drawable.gmail_icon_80_38, R.drawable.google_logo_80_38,
            R.drawable.instagram_logo_80_38, R.drawable.linkedin_logo_80_38,
            R.drawable.outlook_logo_80_38, R.drawable.tiktok_logo_80_38,
            R.drawable.twitter_logo_80_38, R.drawable.whatsapp_logo_80_38,
            R.drawable.no_notifications, R.drawable.available_notifications};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification_settings);

        chatInput = findViewById(R.id.chatEditText);
        appCountInput = findViewById(R.id.appCountInput);

        recyclerView = findViewById(R.id.application_recycler_view);

        adapter = new RecyclerViewAdapter(this, recyclerViewDataModels, this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        appCountInput.setFilters(new AppCountInputFilter[]{new AppCountInputFilter(0, 10)});

        chatList.clear();

        sharedChatList = getSharedPreferences("ComputerProject_1_4", MODE_PRIVATE);
        addedChatsCount = sharedChatList.getInt("addedChatsCount", 0);

        for (int i = 0; i < addedChatsCount; i++) {
            chatList.add(sharedChatList.getString("chat[" + i + "]", ""));

            Log.i(TAG, "addToChatList: " +
                    sharedChatList.getString("chat[" + i + "]", "") + " is added.");
        }

        setButtonOnClickListeners();
    }//protected void onCreate(Bundle savedInstanceState)

    public void setButtonOnClickListeners() {
        Button addChatButton = findViewById(R.id.chatInputButton);
        Button deleteChatButton = findViewById(R.id.chatInputDeleteButton);
        Button listChatsButton = findViewById(R.id.chatListButton);

        Button addApplicationButton = findViewById(R.id.applicationInputButton);
        Button deleteApplicationButton = findViewById(R.id.appInputDeleteButton);
        Button listApplicationsButton = findViewById(R.id.applicationListButton);

        Button submitAppCountButton = findViewById(R.id.appCountSubmitButton);

        addChatButton.setOnClickListener(view -> addToChatList());
        deleteChatButton.setOnClickListener(view -> removeFromChatList());
        listChatsButton.setOnClickListener(view -> displayChatList());

        addApplicationButton.setOnClickListener(view -> goToAppOperationsActivity(R.id.applicationInputButton));
        deleteApplicationButton.setOnClickListener(view -> goToAppOperationsActivity(R.id.appInputDeleteButton));
        listApplicationsButton.setOnClickListener(view -> displayApplicationList());

        submitAppCountButton.setOnClickListener(view -> submitAppCount());
    }//public void notificationsUpdate(View view)

    private void submitAppCount() {
        if(appCountInput.getText().toString() != "") {
            chosenAppLimit = Integer.parseInt(appCountInput.getText().toString());
        }
        else{
            chosenAppLimit = 3;
        }

        appCountInput.getText().clear();
    }//private void submitAppCount()

    private void goToAppOperationsActivity(int buttonId) {
        if(buttonId == R.id.applicationInputButton) {
            openAppAdderActivity = new Intent(this, AddApplicationActivity.class);
            openAppAdderActivity.putExtra("chosenAppLimit", chosenAppLimit);
            startActivity(openAppAdderActivity);
        }

        else if(buttonId == R.id.appInputDeleteButton) {
            openAppDeleterActivity = new Intent(this, DeleteApplicationActivity.class);
            openAppDeleterActivity.putExtra("chosenAppLimit", chosenAppLimit);
            startActivity(openAppDeleterActivity);
        }
    }//private void goToAddAppActivity(int buttonId)

    @SuppressLint("SetTextI18n")
    public void addToChatList() {
        String addedChat;

        chatInputString = chatInput.getText().toString();

        chatList.add(chatInputString);

        chatListEditor = sharedChatList.edit();

        for (int i = 0; i < chatList.size(); i++) {
            addedChat = chatList.get(i);

            chatListEditor.putString("chat[" + i + "]", addedChat);
        }

        addedChatsCount = chatList.size();
        chatListEditor.putInt("addedChatsCount", addedChatsCount);

        chatListEditor.apply();

        for (int i = 0; i < sharedChatList.getInt("addedChatsCount", 0); i++) {
            Log.i(TAG, "addToChatList: " + sharedChatList.getString("conversation[" + i + "]", "") +
                    " is added to chatList.");
        }

        Log.i(TAG, "addToChatList: chatList.size() = " + chatList.size());
        Log.i(TAG, "addToChatList: addedChatsCount = " +
                sharedChatList.getInt("addedChatsCount", 0));

        chatInput.getText().clear();
    }//public void addToChatList()

    @SuppressLint("SetTextI18n")
    public void removeFromChatList() {
        chatInputString = chatInput.getText().toString();

        // First, the chat with the name chatInputString is removed from chatList
        chatList.remove(chatInputString);

        // Then, the removed chat is removed from SharedPreferences too, so the user's choices can be recorded.
        sharedChatList = getSharedPreferences("ComputerProject_1_4", MODE_PRIVATE);
        addedChatsCount = sharedChatList.getInt("addedChatsCount", 0);
        chatListEditor = sharedChatList.edit();

        // Because String keys of values in sharedChatList can not be easily updated after removing one of their values,
        // this for loop deletes every String with key "chat[integer]" in sharedChatList.
        for(int j = 0; j < addedChatsCount; j++) {
            chatListEditor.remove("chat[" + j + "]");

            Log.i(TAG, "removeFromChatList: chat[" + j + "] has been removed");
        }//for(int j = 0; j < addedChatsCount; j++)

        chatListEditor.apply();

        addedChatsCount = chatList.size();
        chatListEditor.putInt("addedChatsCount", addedChatsCount);

        // After cleaning every String with "chat[integer] and hopefully not changing other values,
        // sharedPreferences is updated with new chat[integer] keyed String values
        for(int j = 0; j < addedChatsCount; j++) {
            chatListEditor.putString("chat[" + j + "]", chatList.get(j));

            Log.i(TAG, "removeFromChatList: chat[" + j + "] has been added");
        }//for(int j = 0; j < addedChatsCount; j++)

        chatListEditor.apply();

        Log.i(TAG, "removeFromChatList: addedChatsCount = " + addedChatsCount);

        Log.i(TAG, "removeFromChatList: chatList after removing " + chatInputString + ":");

        for (int index = 0; index < chatList.size(); index++) {
            Log.i(TAG, "removeFromChatList: chatList.get(" + index + ") = " +
                    chatList.get(index));
        }

        chatInput.getText().clear();
    }//public void removeFromChatList(View chatInputDeleteButton)

    @SuppressLint("SetTextI18n")
    public void displayApplicationList() {
        sharedApplicationList = getSharedPreferences("ComputerProject_1_4", MODE_PRIVATE);
        chosenAppCount = sharedApplicationList.getInt("chosenAppCount", 0);

        HashMap<String, String> notificationHashMap = new HashMap<>();

        notificationHashMap.put("com.discord", "Discord");
        notificationHashMap.put("com.facebook.katana", "Facebook");
        notificationHashMap.put("com.google.android.gm", "Gmail");
        notificationHashMap.put("com.google.android.googlequicksearchbox", "Google");
        notificationHashMap.put("com.instagram.android", "Instagram");
        notificationHashMap.put("com.linkedin.android", "LinkedIn");
        notificationHashMap.put("com.microsoft.office.outlook", "Outlook");
        notificationHashMap.put("com.twitter.android", "Twitter");
        notificationHashMap.put("com.zhiliaoapp.musically", "TikTok");
        notificationHashMap.put("com.whatsapp", "WhatsApp");

        HashMap<String, Integer> appNameToLogoIndex = new HashMap<>();

        appNameToLogoIndex.put("Discord", 0);
        appNameToLogoIndex.put("Facebook", 1);
        appNameToLogoIndex.put("Gmail", 2);
        appNameToLogoIndex.put("Google", 3);
        appNameToLogoIndex.put("Instagram", 4);
        appNameToLogoIndex.put("LinkedIn", 5);
        appNameToLogoIndex.put("Outlook", 6);
        appNameToLogoIndex.put("TikTok", 7);
        appNameToLogoIndex.put("Twitter", 8);
        appNameToLogoIndex.put("WhatsApp", 9);

        Log.i(TAG, "displayApplicationList: chosenAppCount = " + chosenAppCount);

        applicationList.clear();

        for (int i = 0; i < chosenAppCount; i++) {
            applicationList.add(sharedApplicationList.getString("chosenApp[" + i + "]", ""));

            Log.i(TAG, "displayApplicationList: " + applicationList.get(i) + " has been added to the list.");
        }

        recyclerViewDataModels.clear();
        adapter.notifyDataSetChanged();

        Log.i(TAG, "displayApplicationList: recyclerViewDataModels ArrayList is cleared.");

        if(applicationList.isEmpty()) {
            setupDataModels(applicationLogos[10], "No Applications Added",
                    "No applications have been added");
        }

        // applicationList.get(index) --> packageName of the app that the user has chosen
        for (int index = 0; index < applicationList.size(); index++) {
            String appName = notificationHashMap.get(applicationList.get(index));
            int appLogoIndex = appNameToLogoIndex.get(appName);

            setupDataModels(applicationLogos[appLogoIndex], appName, "This app has been added to the list.");
        }//for (int index = 0; index < applicationList.size(); index++)
    }//public void displayApplicationList()

    @SuppressLint("SetTextI18n")
    public void displayChatList() {
        sharedChatList = getSharedPreferences("ComputerProject_1_4", MODE_PRIVATE);
        addedChatsCount = sharedChatList.getInt("addedChatsCount", 0);

        chatList.clear();

        for (int i = 0; i < addedChatsCount; i++) {
            chatList.add(sharedChatList.getString("chat[" + i + "]", ""));

            Log.i(TAG, "displayChatList: " +
                    sharedChatList.getString("chat[" + i + "]", "") + " is added.");
        }

        recyclerViewDataModels.clear();
        adapter.notifyDataSetChanged();

        Log.i(TAG, "displayChatList: recyclerViewDataModels ArrayList is cleared.");

        if(chatList.isEmpty()) {
            setupDataModels(applicationLogos[9], "WhatsApp Chat", "No chats have been added");
        }

        for (int index = 0; index < chatList.size(); index++) {
            setupDataModels(applicationLogos[9], "WhatsApp Chat", chatList.get(index));
        }//for (int index = 0; index < applicationList.size(); index++)
    }//public void displayChatList(View chatListButton)

    private void setupDataModels(int applicationLogo, String dataModelTitle, String dataModelText) {
        recyclerViewDataModels.add(new RecyclerViewDataModel(applicationLogo, dataModelTitle, dataModelText));
        adapter.notifyDataSetChanged();
    }//private void setUpNotificationDataModels()

    @Override
    public void onNotificationClick(int position) {
        HashMap<Integer, String> logoIdToName = new HashMap<>();
        HashMap<Integer, String> logoIdToPackageName = new HashMap<>();

        logoIdToName.put(R.drawable.discord_logo_80_38, "Discord");
        logoIdToName.put(R.drawable.facebook_logo_80_38, "Facebook");
        logoIdToName.put(R.drawable.gmail_icon_80_38, "Gmail");
        logoIdToName.put(R.drawable.google_logo_80_38, "Google");
        logoIdToName.put(R.drawable.instagram_logo_80_38, "Instagram");
        logoIdToName.put(R.drawable.linkedin_logo_80_38, "LinkedIn");
        logoIdToName.put(R.drawable.outlook_logo_80_38, "Outlook");
        logoIdToName.put(R.drawable.tiktok_logo_80_38, "TikTok");
        logoIdToName.put(R.drawable.twitter_logo_80_38, "Twitter");
        logoIdToName.put(R.drawable.whatsapp_logo_80_38, "WhatsApp");

        logoIdToPackageName.put(R.drawable.discord_logo_80_38, "com.discord");
        logoIdToPackageName.put(R.drawable.facebook_logo_80_38, "com.facebook.katana");
        logoIdToPackageName.put(R.drawable.gmail_icon_80_38, "com.google.android.gm");
        logoIdToPackageName.put(R.drawable.google_logo_80_38, "com.google.android.googlequicksearchbox");
        logoIdToPackageName.put(R.drawable.instagram_logo_80_38, "com.instagram.android");
        logoIdToPackageName.put(R.drawable.linkedin_logo_80_38, "com.linkedin.android");
        logoIdToPackageName.put(R.drawable.outlook_logo_80_38, "com.microsoft.office.outlook");
        logoIdToPackageName.put(R.drawable.tiktok_logo_80_38, "com.zhiliaoapp.musically");
        logoIdToPackageName.put(R.drawable.twitter_logo_80_38, "com.twitter.android");
        logoIdToPackageName.put(R.drawable.whatsapp_logo_80_38, "com.whatsapp");

        if(recyclerViewDataModels.get(position).getDataModelTitle().equals("WhatsApp Chat")) {
            Toast.makeText(this, recyclerViewDataModels.get(position).getDataModelText() +
                    " has been added to the chat list", Toast.LENGTH_SHORT).show();
        }
        else if(recyclerViewDataModels.get(position).getDataModelText().equals("This app has been added to the list.")){
            Toast.makeText(this, recyclerViewDataModels.get(position).getDataModelTitle() +
                    " has been added to the list", Toast.LENGTH_SHORT).show();
        }
        else if(recyclerViewDataModels.get(position).getDataModelTitle().equals("No Notifications Available")){
            Toast.makeText(this, "No notifications available", Toast.LENGTH_SHORT).show();
        }
        else{
            Toast.makeText(this, recyclerViewDataModels.get(position).getDataModelTitle(), Toast.LENGTH_SHORT).show();
        }
    }//public void onNotificationClick(int position)

}//public class NotificationSettings extends AppCompatActivity implements RecyclerViewInterface