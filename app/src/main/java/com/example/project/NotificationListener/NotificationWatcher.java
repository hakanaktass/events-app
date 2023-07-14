package com.example.project.NotificationListener;

import android.content.Intent;
import android.os.IBinder;
import android.service.notification.NotificationListenerService;
import android.service.notification.StatusBarNotification;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class NotificationWatcher extends NotificationListenerService {
    public static List<StatusBarNotification[]> notificationsList = new ArrayList<>();
    public static int notificationCount = 0;

    @Override
    public void onCreate() { super.onCreate(); }

    @Override
    public IBinder onBind(Intent intent) {
        return super.onBind(intent);
    }

    @Override
    public void onNotificationPosted(StatusBarNotification sbn) {
        updateCurrentNotifications();
        super.onNotificationPosted(sbn);
    }//public void onNotificationPosted(StatusBarNotification sbn)

    @Override
    public void onNotificationRemoved(StatusBarNotification sbn) {
        updateCurrentNotifications();
        super.onNotificationRemoved(sbn);
    }

    private void updateCurrentNotifications() {
        try {
            StatusBarNotification[] activeNos = getActiveNotifications();

            if (notificationsList.size() == 0) {
                notificationsList.add(null);
            }//if (notificationsList.size() == 0)

            notificationsList.set(0, activeNos);
            notificationCount = activeNos.length;
        }//try
        catch (Exception e) {
            e.printStackTrace();
        }//catch (Exception e)
    }//private void updateCurrentNotifications()

    // getCurrentNotificationString() method in MainActivity calls this method to
    // get currently available notifications
    public static StatusBarNotification[] getCurrentNotifications() {
        if (notificationsList.size() == 0) {
            return null;
        }//if (notificationsList.size() == 0)
        return notificationsList.get(0);
    }//public static StatusBarNotification[] getCurrentNotifications()

}//public class NotificationWatcher extends NotificationListenerService
