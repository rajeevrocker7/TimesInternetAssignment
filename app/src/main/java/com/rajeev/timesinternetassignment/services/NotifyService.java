package com.rajeev.timesinternetassignment.services;

import android.app.IntentService;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.util.Log;

import com.rajeev.timesinternetassignment.activities.ItemInfoAct;
import com.rajeev.timesinternetassignment.beans.DataBean;
import com.rajeev.timesinternetassignment.constants.AppConstants;
import com.rajeev.timesinternetassignment.utils.NotificationHelper;

public class NotifyService extends IntentService {


    private byte[] bitmap_data;
    private DataBean dataBean;

    final Runnable updateRunnable = new Runnable() {
        public void run() {
            //call the activity method that updates the UI
            sendPictureNotify(dataBean);

        }
    };
    //create an handler
    private Handler myHandler;


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
                bitmap_data = b.getByteArray("bitmap_data");

                if (dataBean != null) {
                    Log.w(AppConstants.kDefaultAppName, "NotifyService: got bitmap_n ");

                    myHandler.post(updateRunnable);


                }
            }
        }

    }


    private void sendPictureNotify(DataBean dataBean) {
        try {

            Log.w(AppConstants.kDefaultAppName, "NotifyService: in sendPictureNotify ");

            Intent intent;
            intent = new Intent(this, ItemInfoAct.class);
            Bundle bundle = new Bundle();
            bundle.putParcelable("dataBean", dataBean);
            bundle.putByteArray("bitmap_data", bitmap_data);
            intent.putExtras(bundle);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

            String message = "";
            String title;
            title = dataBean.getT();
            if (dataBean.getSd() != null)
                message = dataBean.getSd();

            //MAKE A NOTIFICATION VIA NOTIFICATION HELPER
            NotificationHelper notificationHelper = new NotificationHelper(this);
            notificationHelper.createNotification(intent, title, message);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /*
     * To get a Bitmap image from the URL received
     * */
//    public Bitmap getBitmapFromUrl(String imageUrl) {
//        try {
//            URL url = new URL(imageUrl);
//            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
//            connection.setDoInput(true);
//            connection.connect();
//            InputStream input = connection.getInputStream();
//            return BitmapFactory.decodeStream(input);
//
//        } catch (Exception e) {
//            Log.e("NotifyService", "Decoding Bitmap stream failed");
//            e.printStackTrace();
//            return null;
//
//        }
//    }
}
