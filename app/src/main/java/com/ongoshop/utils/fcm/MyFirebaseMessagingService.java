package com.ongoshop.utils.fcm;

import android.annotation.TargetApi;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.ongoshop.R;
import com.ongoshop.activities.HomeActivity;
import com.ongoshop.activities.NewOrdersActivity;
import com.ongoshop.utils.others.SharedPrefUtil;


public class MyFirebaseMessagingService extends FirebaseMessagingService {
    private static final String TAG = "MyFirebaseMsgService";
    private static int i;

    String message, notification_code = "", title, userType = "";
    String CHANNEL_ID = "";
    String CHANNEL_ONE_NAME = "Channel One";
    NotificationChannel notificationChannel;
    public static NotificationManager notificationManager;
    Notification notification;

    @Override
    public void onNewToken(@NonNull String s) {
        super.onNewToken(s);
        SharedPrefUtil.getInstance().saveDeviceToken(s);
        Log.e("devicetoken:",s);
    }

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        Log.d(TAG, "From: " + remoteMessage.getData());

        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.cancelAll();
        // stopService(new Intent(this, GetCurrentLocation.class));

        getManager();
        CHANNEL_ID = getApplicationContext().getPackageName();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            notificationChannel = new NotificationChannel(CHANNEL_ID, CHANNEL_ONE_NAME, notificationManager.IMPORTANCE_DEFAULT);
            notificationChannel.enableLights(true);
            notificationChannel.setLightColor(Color.RED);
            notificationChannel.setShowBadge(true);
            notificationChannel.setLockscreenVisibility(Notification.VISIBILITY_PUBLIC);
        }

        Log.e(TAG, "Notification Message Body: " + remoteMessage.getData());

        if (remoteMessage.getData().size() > 0) {
            message = remoteMessage.getData().get("msg");
            title = remoteMessage.getData().get("title");


/*
            if (notification_code.equals("1"))
            {
                userType = remoteMessage.getData().get("user_type");
            }
*/

            sendNotification(this, message, notification_code);
        }
    }

    private NotificationManager getManager() {
        if (notificationManager == null) {
            notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        }
        return notificationManager;
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private void sendNotification(Context context, String message, String orderid) {

        TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);

        stackBuilder.addNextIntent(new Intent(this, HomeActivity.class));
        Intent local = new Intent();
        local.setAction("pushstatus");
        Intent launchintent =  new Intent(this, HomeActivity.class);
        Intent openintent = new Intent(this, NewOrdersActivity.class);
        openintent.putExtra("Orders", "0");
        openintent.putExtra("from","notification");
        LocalBroadcastManager.getInstance(this).sendBroadcast(local);

        PendingIntent pendingIntent = PendingIntent.getActivities(context, 21,  new Intent[] {launchintent, openintent}, PendingIntent.FLAG_UPDATE_CURRENT);
        Bitmap icon1 = BitmapFactory.decodeResource(context.getResources(), R.mipmap.ic_launcher);
        Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

        Notification.Builder notificationBuilder = new Notification.Builder(context)
                .setSmallIcon(getNotificationIcon()).setLargeIcon(icon1)
                .setStyle(new Notification.BigTextStyle().bigText(message))
                .setContentTitle(getString(R.string.app_name))
                .setOngoing(false)
                .setContentText(message)
                .setAutoCancel(true)
                .setSound(defaultSoundUri)
                .setContentIntent(pendingIntent);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            notificationBuilder.setChannelId(CHANNEL_ID);
            notificationManager.createNotificationChannel(notificationChannel);
        }

        notification = notificationBuilder.build();
        notificationManager.notify(i++, notification);
    }

    private int getNotificationIcon() {
        boolean useWhiteIcon = (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP);
        return useWhiteIcon ? R.mipmap.ic_launcher : R.mipmap.ic_launcher;
    }

}
