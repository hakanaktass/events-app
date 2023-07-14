package com.example.project.NotificationListener;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Notification;
import android.content.ComponentName;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.provider.Settings;
import android.service.notification.StatusBarNotification;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.project.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;

@SuppressWarnings({"FieldMayBeFinal", "ForLoopReplaceableByForEach"})
public class NotificationActivity extends AppCompatActivity implements RecyclerViewInterface{
    private static final String TAG = "MustafaOnurNotificationActivity";
    private static final int EVENT_LIST_CURRENT_NOTIFICATIONS = 0;
    private static final String ENABLED_NOTIFICATION_LISTENERS = "enabled_notification_listeners";
    private static final String ACTION_NOTIFICATION_LISTENER_SETTINGS = "android.settings.ACTION_NOTIFICATION_LISTENER_SETTINGS";

    private boolean isListenerEnabled = false;

    private RecyclerView recyclerView;
    private RecyclerViewAdapter adapter;

    SharedPreferences sharedApplicationList;
    SharedPreferences sharedChatList;

    public ArrayList<String> chatList = new ArrayList<>();
    public ArrayList<String> applicationList = new ArrayList<>();
    public ArrayList<RecyclerViewDataModel> recyclerViewDataModels = new ArrayList<>();

    int chosenAppCount;
    int addedChatsCount;
    int chosenAppLimit = 3;  //This variable has the value 3 by default

    int[] applicationLogos = {R.drawable.discord_logo_80_38, R.drawable.facebook_logo_80_38,
            R.drawable.gmail_icon_80_38, R.drawable.google_logo_80_38,
            R.drawable.instagram_logo_80_38, R.drawable.linkedin_logo_80_38,
            R.drawable.outlook_logo_80_38, R.drawable.tiktok_logo_80_38,
            R.drawable.twitter_logo_80_38, R.drawable.whatsapp_logo_80_38,
            R.drawable.no_notifications, R.drawable.available_notifications};

    private Handler msgHandler = new Handler(Looper.myLooper()){
        @Override
        public void handleMessage(@NonNull Message msg) {
            if (msg.what == EVENT_LIST_CURRENT_NOTIFICATIONS) {
                listCurrentNotifications();
            }//if (msg.what == EVENT_LIST_CURRENT_NOTIFICATIONS)
        }//public void handleMessage(@NonNull Message msg)
    };//private Handler msgHandler = new Handler(Looper.myLooper())

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification_main);
        Objects.requireNonNull(getSupportActionBar()).setBackgroundDrawable(
                new ColorDrawable(getResources().getColor(R.color.customBackground)));
        getSupportActionBar().setTitle("");

        recyclerView = findViewById(R.id.notification_recycler_view);

        adapter = new RecyclerViewAdapter(this, recyclerViewDataModels, this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        isListenerEnabled = isNotificationListenerEnabled();

        if(!isListenerEnabled) {
            popWarningMessage();
        }//if(!isListenerEnabled)

        msgHandler.sendMessage(msgHandler.obtainMessage(EVENT_LIST_CURRENT_NOTIFICATIONS));

        chatList.clear();

        sharedChatList = getSharedPreferences("ComputerProject_1_4", MODE_PRIVATE);
        addedChatsCount = sharedChatList.getInt("addedChatsCount", 0);

        for (int i = 0; i < addedChatsCount; i++) {
            chatList.add(sharedChatList.getString("chat[" + i + "]", ""));

            Log.i(TAG, "addToChatList: " +
                    sharedChatList.getString("chat[" + i + "]", "") + " is added.");
        }

        setButtonOnClickListener();
    }//protected void onCreate(Bundle savedInstanceState)

    @Override
    protected void onResume() {
        super.onResume();

        isListenerEnabled = isNotificationListenerEnabled();

        if(!isListenerEnabled) {
            popWarningMessage();
        }//if(!isListenerEnabled)

        //These codes are to update applicationList after returning to MainActivity
        sharedApplicationList = getSharedPreferences("ComputerProject_1_4", MODE_PRIVATE);
        chosenAppCount = sharedApplicationList.getInt("chosenAppCount", 0);

        Log.i(TAG, "addToApplicationList: chosenAppCount = " + chosenAppCount);

        listCurrentNotifications();
    }//protected void onResume()

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_notification_activity, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == R.id.settings_option) {
            Intent settingsIntent = new Intent(this, NotificationSettings.class);
            startActivity(settingsIntent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void popWarningMessage() {
        new AlertDialog.Builder(this)
                .setMessage("Please enable NotificationMonitor access")
                .setTitle("Notification Access")
                .setIconAttribute(android.R.attr.alertDialogIcon)
                .setCancelable(true)
                .setPositiveButton(android.R.string.ok, (dialog, id) -> openNotificationListenerSettings())
                .setNegativeButton(android.R.string.cancel, (dialog, id) -> { /* do nothing */})
                .create().show();
    }//private void popWarningMessage()

    public void setButtonOnClickListener() {
        Button updateButton = findViewById(R.id.updateNotificationsButton);
        updateButton.setOnClickListener(view -> msgHandler.sendMessage(msgHandler.obtainMessage(EVENT_LIST_CURRENT_NOTIFICATIONS)));
    }//public void setButtonOnClickListener()

    // This method is used to go to the Settings page for Notification Listener in the device
    private void openNotificationListenerSettings() {
        startActivity(new Intent(ACTION_NOTIFICATION_LISTENER_SETTINGS));
    }//private void openNotificationListenerSettings()

    @SuppressLint("NotifyDataSetChanged")
    private void setupDataModels(int applicationLogo, String dataModelTitle, String dataModelText) {
        recyclerViewDataModels.add(new RecyclerViewDataModel(applicationLogo, dataModelTitle, dataModelText));
        adapter.notifyDataSetChanged();
    }//private void setUpNotificationDataModels()

    // This method returns information about open notifications as a RecyclerView
    @SuppressWarnings({"AccessStaticViaInstance"})
    private void listCurrentNotifications() {
        StatusBarNotification[] currentNotifications = NotificationWatcher.getCurrentNotifications();
        Notification notification;
        HashMap<String, String> notificationHashMap = new HashMap<>();

        notificationHashMap.put("com.discord", "Discord");
        notificationHashMap.put("com.facebook.katana", "Facebook");
        notificationHashMap.put("com.google.android.gm", "Gmail");
        notificationHashMap.put("com.google.android.googlequicksearchbox", "Google");
        notificationHashMap.put("com.instagram.android", "Instagram");
        notificationHashMap.put("com.linkedin.android", "LinkedIn");
        notificationHashMap.put("com.microsoft.office.outlook", "Outlook");
        notificationHashMap.put("com.twitter.android", "Twitter");
        notificationHashMap.put("com.whatsapp", "WhatsApp");
        notificationHashMap.put("com.zhiliaoapp.musically", "TikTok");

        applicationList.clear();

        for (int i = 0; i < chosenAppCount; i++) {
            applicationList.add(sharedApplicationList.getString("chosenApp[" + i + "]", ""));

            Log.i(TAG, "listCurrentNotifications: " + applicationList.get(i) + " has been added to the list.");
        }

        recyclerViewDataModels.clear();

        Log.i(TAG, "listCurrentNotifications: recyclerViewDataModels ArrayList is cleared.");

        for (int i = 0; i < applicationList.size(); i++) {
            Log.i(TAG, "listCurrentNotifications: applicationList[" + i + "] = " + applicationList.get(i));
        }

        if(currentNotifications == null) {
            setupDataModels(applicationLogos[10], "No Notifications Available", "No notifications available");
        }

        if (currentNotifications != null) {
            int notificationCount = NotificationWatcher.notificationCount;

            if(notificationCount == 0) {
                setupDataModels(applicationLogos[10], "No Notifications Available", "No notifications available");
            }
            else {
                setupDataModels(applicationLogos[11],
                        notificationCount + " Notifications Available",
                        notificationCount + " notifications available");
            }

            for (int i = 0; i < currentNotifications.length; i++) {
                notification = currentNotifications[i].getNotification();
                Bundle bundle = notification.extras;

                String appPackageName = currentNotifications[i].getPackageName();

                Log.i(TAG, "listCurrentNotifications: appPackageName = " +
                        currentNotifications[i].getPackageName());

                boolean isRightApplication = applicationList.contains(appPackageName);

                Log.i(TAG, "listCurrentNotifications: isRightApplication = " +
                        applicationList.contains(appPackageName));

                // If appPackageName of a notification is in applicationList and
                // it is equal to the packageName of Discord, this block is entered
                // Objects method is to prevent nullPointerException
                if(isRightApplication && Objects.equals(notificationHashMap.get(appPackageName), "Discord")) {
                    setupDataModels(applicationLogos[0], bundle.getString(notification.EXTRA_TITLE),
                            bundle.getString(notification.EXTRA_TEXT));

                    Log.i(TAG, "listCurrentNotifications: EXTRA_TITLE = " + bundle.getString(notification.EXTRA_TITLE));
                    Log.i(TAG, "listCurrentNotifications: EXTRA_TEXT = " + bundle.getString(notification.EXTRA_TEXT));
                }//if(notificationHashMap.get(appPackageName).equals("Discord"))

                // If appPackageName of a notification is in applicationList and
                // it is equal to the packageName of Facebook, this block is entered
                // Objects method is to prevent nullPointerException
                else if(isRightApplication && Objects.equals(notificationHashMap.get(appPackageName), "Facebook")) {
                    setupDataModels(applicationLogos[1], bundle.getString(notification.EXTRA_TITLE),
                            bundle.getString(notification.EXTRA_TEXT));

                    Log.i(TAG, "listCurrentNotifications: EXTRA_TITLE = " + bundle.getString(notification.EXTRA_TITLE));
                    Log.i(TAG, "listCurrentNotifications: EXTRA_TEXT = " + bundle.getString(notification.EXTRA_TEXT));
                }//else if(notificationHashMap.get(appPackageName).equals("Facebook"))

                // If appPackageName of a notification is in applicationList and
                // it is equal to the packageName of Gmail, this block is entered
                // Objects method is to prevent nullPointerException
                else if(isRightApplication && Objects.equals(notificationHashMap.get(appPackageName), "Gmail")) {
                    setupDataModels(applicationLogos[2],
                            "from: " + bundle.getString(notification.EXTRA_TITLE),
                            "to: " + bundle.getString(notification.EXTRA_SUB_TEXT));

                    Log.i(TAG, "listCurrentNotifications: EXTRA_TITLE = " + bundle.getString(notification.EXTRA_TITLE));
                    Log.i(TAG, "listCurrentNotifications: EXTRA_SUB_TEXT = " + bundle.getString(notification.EXTRA_SUB_TEXT));
                }//else if(notificationHashMap.get(appPackageName).equals("Gmail"))

                // If appPackageName of a notification is in applicationList and
                // it is equal to the packageName of Google, this block is entered
                // Objects method is to prevent nullPointerException
                else if(isRightApplication && Objects.equals(notificationHashMap.get(appPackageName), "Google")) {
                    if(notification.tickerText != null) {
                        setupDataModels(applicationLogos[3], "Google", (String) notification.tickerText);
                    }

                    Log.i(TAG, "listCurrentNotifications: tickerText = " + notification.tickerText);
                }//else if(notificationHashMap.get(appPackageName).equals("Google"))

                // If appPackageName of a notification is in applicationList and
                // it is equal to the packageName of Instagram, this block is entered
                // Objects method is to prevent nullPointerException
                else if(isRightApplication && Objects.equals(notificationHashMap.get(appPackageName), "Instagram")) {
                    setupDataModels(applicationLogos[4], bundle.getString(notification.EXTRA_TITLE),
                            bundle.getString(notification.EXTRA_TEXT));

                    Log.i(TAG, "listCurrentNotifications: EXTRA_TITLE = " + bundle.getString(notification.EXTRA_TITLE));
                    Log.i(TAG, "listCurrentNotifications: EXTRA_TEXT = " + bundle.getString(notification.EXTRA_TEXT));
                }//else if(notificationHashMap.get(appPackageName).equals("Instagram"))

                // If appPackageName of a notification is in applicationList and
                // it is equal to the packageName of LinkedIn, this block is entered
                // Objects method is to prevent nullPointerException
                else if(isRightApplication && Objects.equals(notificationHashMap.get(appPackageName), "LinkedIn")) {
                    setupDataModels(applicationLogos[5], bundle.getString(notification.EXTRA_TITLE),
                            bundle.getString(notification.EXTRA_TEXT));

                    Log.i(TAG, "listCurrentNotifications: EXTRA_TITLE = " + bundle.getString(notification.EXTRA_TITLE));
                    Log.i(TAG, "listCurrentNotifications: EXTRA_TEXT = " + bundle.getString(notification.EXTRA_TEXT));
                }//else if(notificationHashMap.get(appPackageName).equals("LinkedIn"))

                // If appPackageName of a notification is in applicationList and
                // it is equal to the packageName of Outlook, this block is entered
                // Objects method is to prevent nullPointerException
                else if(isRightApplication && Objects.equals(notificationHashMap.get(appPackageName), "Outlook")) {
                    setupDataModels(applicationLogos[6],
                            "from: " + bundle.getString(notification.EXTRA_TITLE),
                            "to: " + bundle.getString(notification.EXTRA_SUB_TEXT));

                    Log.i(TAG, "listCurrentNotifications: EXTRA_TITLE = " + bundle.getString(notification.EXTRA_TITLE));
                    Log.i(TAG, "listCurrentNotifications: EXTRA_SUB_TEXT = " + bundle.getString(notification.EXTRA_SUB_TEXT));
                }//else if(notificationHashMap.get(appPackageName).equals("Outlook"))

                // If appPackageName of a notification is in applicationList and
                // it is equal to the packageName of TikTok, this block is entered
                // Objects method is to prevent nullPointerException
                else if(isRightApplication && Objects.equals(notificationHashMap.get(appPackageName), "TikTok")) {
                    setupDataModels(applicationLogos[7], bundle.getString(notification.EXTRA_TITLE),
                            bundle.getString(notification.EXTRA_TEXT));

                    Log.i(TAG, "listCurrentNotifications: EXTRA_TITLE = " + bundle.getString(notification.EXTRA_TITLE));
                    Log.i(TAG, "listCurrentNotifications: EXTRA_TEXT = " + bundle.getString(notification.EXTRA_TEXT));
                }//else if(notificationHashMap.get(appPackageName).equals("TikTok"))

                // If appPackageName of a notification is in applicationList and
                // it is equal to the packageName of Twitter, this block is entered
                // Objects method is to prevent nullPointerException
                else if(isRightApplication && Objects.equals(notificationHashMap.get(appPackageName), "Twitter")) {
                    setupDataModels(applicationLogos[8], bundle.getString(notification.EXTRA_TITLE),
                            bundle.getString(notification.EXTRA_TEXT));

                    Log.i(TAG, "listCurrentNotifications: EXTRA_TITLE = " + bundle.getString(notification.EXTRA_TITLE));
                    Log.i(TAG, "listCurrentNotifications: EXTRA_TEXT = " + bundle.getString(notification.EXTRA_TEXT));
                }//else if(notificationHashMap.get(appPackageName).equals("Twitter"))

                // If appPackageName of a notification is in applicationList and
                // it is equal to the packageName of WhatsApp, this block is entered
                // Objects method is to prevent nullPointerException
                else if(isRightApplication && Objects.equals(notificationHashMap.get(appPackageName), "WhatsApp")) {
                    int j = 0;
                    boolean isRightChat = false;
                    String extraTitle = bundle.getString(notification.EXTRA_TITLE);
                    String chatTitle = bundle.getString(notification.EXTRA_CONVERSATION_TITLE);

                    Log.i(TAG, "listCurrentNotifications: chatTitle = " +
                            bundle.getString(notification.EXTRA_CONVERSATION_TITLE));

                    // Because EXTRA_CONVERSATION_TITLE of WhatsApp group notifications are often in the
                    // "chatName (messageCount messages)" format, this while loop is used to
                    // check if EXTRA_CONVERSATION_TITLE attribute of a notification has any of the
                    // chat names in chatList
                    if(chatTitle != null) {
                        while(j < chatList.size() && !chatTitle.contains(chatList.get(j))) {
                            Log.i(TAG, "listCurrentNotifications: chatList.get(" + j + ") is " +
                                    chatList.get(j));

                            j++;
                        }
                    }

                    if(j < chatList.size() && chatTitle != null && chatTitle.contains(chatList.get(j))) {
                        isRightChat = true;
                    }

                    if(chatTitle == null && extraTitle != null) {
                        isRightChat = chatList.contains(extraTitle);

                        Log.i(TAG, "listCurrentNotifications: chatList.contains(" + extraTitle + ") = " +
                                chatList.contains(extraTitle));
                    }

                    Log.i(TAG, "listCurrentNotifications: isRightChat = " +
                            isRightChat);

                    // If the notification comes from one of the chosen WhatsApp chats,
                    // the notification is shown on the screen
                    // If the chat is a group chat, EXTRA_CONVERSATION_TITLE will
                    // have the chat's title and unread messages count in it
                    if(isRightChat && chatTitle != null) {
                        setupDataModels(applicationLogos[9], "WhatsApp",
                                bundle.getString(notification.EXTRA_CONVERSATION_TITLE));

                        Log.i(TAG, "listCurrentNotifications: EXTRA_CONVERSATION_TITLE = " +
                                bundle.getString(notification.EXTRA_CONVERSATION_TITLE));
                    }//if(isRightChat && chatTitle != null)

                    // If the chat is a one-to-one chat or a community (topluluk)
                    // chat, EXTRA_TITLE will have the chat's title
                    else if(isRightChat) {
                        setupDataModels(applicationLogos[9], "WhatsApp",
                                bundle.getString(notification.EXTRA_TITLE));

                        Log.i(TAG, "listCurrentNotifications: EXTRA_CONVERSATION_TITLE = " +
                                bundle.getString(notification.EXTRA_TITLE));
                    }//else if(isRightChat) --> (isRightChat && extraTitle != null)
                }//else if(isRightApplication && notificationHashMap.get(appPackageName).equals("Whatsapp"))
            }//for (int i = 0; i < currentNotifications.length; i++)
        }//if (currentNotifications != null)
    }//private String listCurrentNotifications()

    private boolean isNotificationListenerEnabled(){
        String packageName = getPackageName();  //The package name of this app is assigned to packageName variable
        final String flatString = Settings.Secure.getString(getContentResolver(),
                ENABLED_NOTIFICATION_LISTENERS);

        if (!TextUtils.isEmpty(flatString)) {
            final String[] names = flatString.split(":");

            for (int i = 0; i < names.length; i++) {
                final ComponentName cn = ComponentName.unflattenFromString(names[i]);

                if (cn != null && TextUtils.equals(packageName, cn.getPackageName())) {
                    return true;
                }//if (cn != null && TextUtils.equals(packageName, cn.getPackageName()))
            }//for (int i = 0; i < names.length; i++)
        }//if (!TextUtils.isEmpty(flatString))

        return false;
    }//private boolean isNotificationListenerEnabled()

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

        if(logoIdToName.containsKey(recyclerViewDataModels.get(position).getLogoId())) {
            int logoId = recyclerViewDataModels.get(position).getLogoId();

            Log.i(TAG, "onNotificationClick: logoIdToPackageName.get(logoId) = " + logoIdToPackageName.get(logoId));

            String appPackageName = logoIdToPackageName.get(logoId);

            Intent appOpenIntent = getPackageManager().getLaunchIntentForPackage(appPackageName);

            Log.i(TAG, "onNotificationClick: appOpenIntent = " + appOpenIntent);

            if(appOpenIntent != null) {
                startActivity(appOpenIntent);
            }

            Toast.makeText(this, logoIdToName.get(logoId) +
                    " notification has been clicked", Toast.LENGTH_SHORT).show();
        }
        else if(recyclerViewDataModels.get(position).getDataModelTitle().equals("No Notifications Available")){
            Toast.makeText(this, "No notifications available", Toast.LENGTH_SHORT).show();
        }
        else{
            Toast.makeText(this, recyclerViewDataModels.get(position).getDataModelTitle(), Toast.LENGTH_SHORT).show();
        }
    }//public void onNotificationClick(int position)

}//public class MainActivity extends AppCompatActivity