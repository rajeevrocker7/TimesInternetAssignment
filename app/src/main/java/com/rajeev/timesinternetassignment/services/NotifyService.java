package com.rajeev.timesinternetassignment.services;

import android.app.IntentService;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.widget.RemoteViews;

import com.rajeev.timesinternetassignment.R;
import com.rajeev.timesinternetassignment.activities.ItemInfoAct;
import com.rajeev.timesinternetassignment.beans.DataBean;
import com.rajeev.timesinternetassignment.constants.AppConstants;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class NotifyService extends IntentService {


    private Bitmap bitmap_n;
    private DataBean dataBean;

    final Runnable updateRunnable = new Runnable() {
        public void run() {
            //call the activity method that updates the UI
            sendPictureNotify(dataBean);
            ;
        }
    };
    //create an handler
    private Handler myHandler = null;


    /**
     * Creates an IntentService.  Invoked by your subclass's constructor.
     */
    public NotifyService() {
        super("NotifyService");
        myHandler = new Handler();
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        if (intent != null) {
            Bundle b = intent.getExtras();
            if (b != null) {
                dataBean = b.getParcelable("dataBean");
                byte[] bitmap_data = b.getByteArray("bitmap_data");

                String imgUrl = null;
                if (dataBean != null) {
                    imgUrl = dataBean.getI();
                    bitmap_n = getBitmapFromUrl(imgUrl);

                    Log.w(AppConstants.kDefaultAppName, "NotifyService: got bitmap_n ");

                    myHandler.post(updateRunnable);



                }
            }
        }

    }


    private void sendPictureNotify(DataBean dataBean) {
        try {

            Log.w(AppConstants.kDefaultAppName, "NotifyService: in sendPictureNotify ");

            Intent intent = null;
            intent = new Intent(this, ItemInfoAct.class);
            Bundle bundle = new Bundle();
            bundle.putParcelable("dataBean", dataBean);
            intent.putExtras(bundle);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);


            String message = "";
            String title = "";
            title = dataBean.getT();
            message = dataBean.getSd();

            PendingIntent resultPendingIntent = PendingIntent.getActivity(this, 0 /* Request code */, intent,
                    PendingIntent.FLAG_ONE_SHOT);

            int icon = R.mipmap.ic_launcher;
            Bitmap normalLargeIcon = BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher);
            String NOTIFICATION_CHANNEL_ID = "1";

            RemoteViews contentView = new RemoteViews(getPackageName(), R.layout.custom_notify);
            contentView.setImageViewResource(R.id.img_appIcon, R.mipmap.ic_launcher);
            contentView.setTextViewText(R.id.tv_title, title);
            contentView.setTextViewText(R.id.tv_msg, message);
            contentView.setImageViewBitmap(R.id.img_B_notification, bitmap_n);


            Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
            NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(this, NOTIFICATION_CHANNEL_ID)
                    .setSmallIcon(icon)
                    .setContent(contentView)
                    .setSound(defaultSoundUri);

            mBuilder.setContentIntent(resultPendingIntent);

            NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                Log.w(AppConstants.kDefaultAppName, "NotifyService: SDK_INT > 0reo ");
                int importance = NotificationManager.IMPORTANCE_HIGH;

                NotificationChannel notificationChannel
                        = new NotificationChannel(NOTIFICATION_CHANNEL_ID, "com.rajeev.timesinternetassignment", importance);
                notificationChannel.enableLights(true);
                notificationChannel.setLightColor(Color.RED);
                notificationChannel.enableVibration(true);
                notificationChannel.setVibrationPattern(new long[]{100, 200, 300, 400, 500, 400, 300, 200, 400});
                assert notificationManager != null;
                mBuilder.setChannelId(NOTIFICATION_CHANNEL_ID);
                notificationManager.createNotificationChannel(notificationChannel);
            }

            notificationManager.notify("NotifyService", 1 /* notification_ID */, mBuilder.build());

            Log.w(AppConstants.kDefaultAppName, "NotifyService: notificationManager notify ");
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /*
     * To get a Bitmap image from the URL received
     * */
    public Bitmap getBitmapFromUrl(String imageUrl) {
        try {
            URL url = new URL(imageUrl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoInput(true);
            connection.connect();
            InputStream input = connection.getInputStream();
            return BitmapFactory.decodeStream(input);

        } catch (Exception e) {
            e.printStackTrace();
            return null;

        }
    }
}
