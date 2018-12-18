package com.rajeev.timesinternetassignment.utils;


import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.provider.Settings;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.rajeev.timesinternetassignment.R;
import com.rajeev.timesinternetassignment.constants.AppConstants;

import java.util.Objects;

public class NotificationHelper {

    private static final String NOTIFICATION_CHANNEL_ID = "10001";
    private Context mContext;
    private NotificationManager mNotificationManager;
    private NotificationCompat.Builder mBuilder;

    public NotificationHelper(Context context) {
        mContext = context;
        Log.w(AppConstants.kDefaultAppName, "NotificationHelper() ");
    }

    /**
     * Create and push the notification
     */
    public void createNotification(Intent resultIntent, String title, String message) {
        try {
            byte[] bitmap_data = Objects.requireNonNull(resultIntent.getExtras()).getByteArray("bitmap_data");
            Bitmap myBitmap = BitmapFactory.decodeResource(mContext.getResources(),
                    R.mipmap.ic_launcher);
            if (bitmap_data != null) {
                myBitmap = BitmapFactory.decodeByteArray(bitmap_data, 0, bitmap_data.length);
            }

            PendingIntent resultPendingIntent = PendingIntent.getActivity(mContext,
                    0 /* Request code */, resultIntent,
                    PendingIntent.FLAG_UPDATE_CURRENT);

           //BIG PICTURE STYLE
            NotificationCompat.BigPictureStyle bigPictureStyle =
                    new NotificationCompat.BigPictureStyle()
                            .bigPicture(myBitmap)
                            .bigLargeIcon(null);

            mBuilder = new NotificationCompat.Builder(mContext, NOTIFICATION_CHANNEL_ID);
            mBuilder.setSmallIcon(R.mipmap.ic_launcher);
            mBuilder.setLargeIcon(myBitmap);
            mBuilder.setContentTitle(title)
                    .setContentText(message)
                    .setSound(Settings.System.DEFAULT_NOTIFICATION_URI)
                    .setStyle(bigPictureStyle)
                    .setContentIntent(resultPendingIntent);

            mNotificationManager = (NotificationManager) mContext.getSystemService(Context.NOTIFICATION_SERVICE);

            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                Log.w(AppConstants.kDefaultAppName, "NotificationHelper: SDK_INT > 0reo ");

                int importance = NotificationManager.IMPORTANCE_HIGH;
                NotificationChannel notificationChannel = new NotificationChannel(NOTIFICATION_CHANNEL_ID, "com.rajeev.timesinternetassignment", importance);
                notificationChannel.enableLights(true);
                notificationChannel.setLightColor(Color.RED);
                notificationChannel.enableVibration(true);
                notificationChannel.setVibrationPattern(new long[]{100, 200, 300, 400, 500, 400, 300, 200, 400});
                assert mNotificationManager != null;
                mNotificationManager.createNotificationChannel(notificationChannel);
            }

            assert mNotificationManager != null;
            mNotificationManager.notify("NotificationHelper", 0 /* Request Code */, mBuilder.build());

            Log.w(AppConstants.kDefaultAppName, "NotificationHelper: notificationManager notify ");
        }catch (Exception e)
        {e.printStackTrace();}

    }
}