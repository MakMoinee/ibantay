package com.thesis.dogharness.service;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.Build;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;

import com.github.MakMoinee.library.preference.CustomPref;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.thesis.dogharness.DashboardActivity;
import com.thesis.dogharness.R;
import com.thesis.dogharness.models.SensorReadings;
import com.thesis.dogharness.repository.LocalRealDB;

public class HeartRateMonitorService extends Service {

    private static final String CHANNEL_ID_FOREGROUND = "heart_rate_monitor";
    private static final String CHANNEL_ID_ALERT = "heart_rate_alert";
    private static final int NOTIFICATION_ID_FOREGROUND = 1001;
    private static final int NOTIFICATION_ID_ALERT = 1002;

    private LocalRealDB db;
    private SensorReadings previous;
    private ValueEventListener latestListener;
    private DatabaseReference latestRef;

    @Override
    public void onCreate() {
        super.onCreate();
        db = new LocalRealDB();
        createChannels();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        // Only run when notifications are turned on
        int isTurnOn = new CustomPref(this).getIntItem("isTurnOn");
        if (isTurnOn <= 0) {
            stopSelf();
            return START_NOT_STICKY;
        }

        Notification notification = buildForegroundNotification();
        startForeground(NOTIFICATION_ID_FOREGROUND, notification);

        startListeningToLatestReading();
        return START_STICKY;
    }

    private void createChannels() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationManager nm = getSystemService(NotificationManager.class);
            if (nm == null) return;

            NotificationChannel foregroundChannel = new NotificationChannel(
                    CHANNEL_ID_FOREGROUND,
                    getString(R.string.notification_channel_heart_rate_name),
                    NotificationManager.IMPORTANCE_LOW
            );
            foregroundChannel.setDescription(getString(R.string.notification_channel_heart_rate_desc));
            nm.createNotificationChannel(foregroundChannel);

            NotificationChannel alertChannel = new NotificationChannel(
                    CHANNEL_ID_ALERT,
                    getString(R.string.notification_channel_heart_rate_name),
                    NotificationManager.IMPORTANCE_HIGH
            );
            alertChannel.setDescription(getString(R.string.notification_channel_heart_rate_desc));
            nm.createNotificationChannel(alertChannel);
        }
    }

    private Notification buildForegroundNotification() {
        Intent openApp = new Intent(this, DashboardActivity.class);
        openApp.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        PendingIntent pending = PendingIntent.getActivity(
                this, 0, openApp,
                PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE
        );

        return new NotificationCompat.Builder(this, CHANNEL_ID_FOREGROUND)
                .setContentTitle(getString(R.string.notification_foreground_title))
                .setContentText(getString(R.string.notification_foreground_text))
                .setSmallIcon(android.R.drawable.ic_menu_compass)
                .setContentIntent(pending)
                .setOngoing(true)
                .setPriority(NotificationCompat.PRIORITY_LOW)
                .build();
    }

    private void startListeningToLatestReading() {
        latestRef = db.getLatestReadingRef();
        if (latestRef == null) return;

        latestListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (!snapshot.exists()) return;

                SensorReadings current = snapshot.getValue(SensorReadings.class);
                if (current == null) return;

                validateAndNotify(current);
                previous = current;
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.e("HeartRateMonitor", "Latest reading listener cancelled", error.toException());
            }
        };
        latestRef.addValueEventListener(latestListener);
    }

    private void validateAndNotify(SensorReadings current) {
        int isTurnOn = new CustomPref(this).getIntItem("isTurnOn");
        if (isTurnOn <= 0) return;

        int heartRateLimit = new CustomPref(this).getIntItem("heart_rate");
        if (heartRateLimit <= 0) return;

        Integer currentHeartRate = current.getHeart_rate();
        if (currentHeartRate == null) return;

        if (previous != null) {
            Integer prevHeartRate = previous.getHeart_rate();
            if (prevHeartRate != null && currentHeartRate.equals(prevHeartRate)) return;
        }

        if (currentHeartRate > heartRateLimit) {
            showHeartRateAlertNotification(currentHeartRate, heartRateLimit);
        }
    }

    private void showHeartRateAlertNotification(int currentBpm, int limitBpm) {
        String text = getString(R.string.notification_heart_rate_alert_text, currentBpm, limitBpm);

        Intent openApp = new Intent(this, DashboardActivity.class);
        openApp.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        PendingIntent pending = PendingIntent.getActivity(
                this, 0, openApp,
                PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE
        );

        Notification notification = new NotificationCompat.Builder(this, CHANNEL_ID_ALERT)
                .setContentTitle(getString(R.string.notification_heart_rate_alert_title))
                .setContentText(text)
                .setSmallIcon(android.R.drawable.ic_dialog_alert)
                .setContentIntent(pending)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setCategory(NotificationCompat.CATEGORY_ALARM)
                .setAutoCancel(true)
                .build();

        NotificationManager nm = getSystemService(NotificationManager.class);
        if (nm != null) {
            nm.notify(NOTIFICATION_ID_ALERT, notification);
        }
    }

    @Override
    public void onDestroy() {
        if (latestRef != null && latestListener != null) {
            latestRef.removeEventListener(latestListener);
        }
        super.onDestroy();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
