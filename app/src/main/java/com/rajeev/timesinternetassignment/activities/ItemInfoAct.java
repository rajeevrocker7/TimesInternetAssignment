package com.rajeev.timesinternetassignment.activities;

import android.annotation.SuppressLint;
import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.databinding.DataBindingUtil;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.support.annotation.NonNull;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.text.Spanned;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.RemoteViews;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.target.Target;
import com.rajeev.timesinternetassignment.R;
import com.rajeev.timesinternetassignment.beans.Cat_;
import com.rajeev.timesinternetassignment.beans.DataBean;
import com.rajeev.timesinternetassignment.beans.ResponseBean;
import com.rajeev.timesinternetassignment.constants.AppConstants;
import com.rajeev.timesinternetassignment.databinding.ActivityItemInfoBinding;
import com.rajeev.timesinternetassignment.receivers.NotificationBR;
import com.rajeev.timesinternetassignment.retrofit.ApiClientConnection;
import com.rajeev.timesinternetassignment.retrofit.ApiInterface;
import com.rajeev.timesinternetassignment.services.NotifyService;
import com.rajeev.timesinternetassignment.utils.CustomDialog;
import com.rajeev.timesinternetassignment.utils.InternetCheck;
import com.rajeev.timesinternetassignment.utils.MyApplication;
import com.rajeev.timesinternetassignment.utils.ServiceParameters;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ItemInfoAct extends AppCompatActivity implements View.OnClickListener {

    private final String TAG = ItemInfoAct.class.getSimpleName();
    private ActivityItemInfoBinding binding;
    private String imageUrl;
    private long itemId = 0L;
    private DataBean dataBean = null;
    private InternetCheck internetCheck;
    private int targetWidth = 0;
    private int targetHeight = 0;
    private Bitmap bitmap_n;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_item_info);

        internetCheck = new InternetCheck(this);

        Intent i = getIntent();
        setUpData(i);

        binding.imgBack.setOnClickListener(this);

    }



    private void setUpData(Intent i) {
        if (i != null) {
            Bundle b = i.getExtras();
            if (b != null) {
                dataBean = b.getParcelable("dataBean");
                if (dataBean != null) {
                    itemId = dataBean.getId();
                    imageUrl = dataBean.getI();

                    String name = dataBean.getT();
                    binding.tvName.setText(name);

                }
            }
        }
    }


    @Override
    public void onResume() {
        super.onResume();
        Log.w(TAG, "onResume");

        //      REGISTER
        registerReceiver(internetCheck, new IntentFilter("android.net.conn.CONNECTIVITY_CHANGE"));

        // GO TO NEXT WHENEVER CONNECTED NOW
        internetCheck.setInternetCheckInterface(isConnected -> {
            if (isConnected) {
//                    MyApplication.makeASuccessSnack(binding.getRoot(), AppConstants.kWeOnlineInternet);

                Log.w(TAG, "isConnected: " + true);

                if (ItemInfoAct.this.dataBean != null) {
                    getProductInfoService(); //SERVICE HIT..
                }

            } else {
                MyApplication.makeAFailureSnack(binding.getRoot(), AppConstants.kMakeSureInternet);

            }
        });


    }


    @Override
    public void onStop() {
        super.onStop();

        //      UNREGISTER
        unregisterReceiver(internetCheck);
    }

//    private void setUpImage(String imageUrl, ImageView imageView) {
//        if (imageUrl != null && !imageUrl.isEmpty()) {
//
//            binding.progressBar.setVisibility(View.VISIBLE);
//
//            Glide.with(ItemInfoAct.this.getApplicationContext())
//                    .load(imageUrl)
//                    .diskCacheStrategy(DiskCacheStrategy.SOURCE)
//                    .into(new SimpleTarget<GlideDrawable>(600, 600) {
//                        @Override
//                        public void onResourceReady(GlideDrawable resource, GlideAnimation<? super GlideDrawable> glideAnimation) {
//
//                            imageView.setVisibility(View.VISIBLE);
//                            imageView.setImageDrawable(resource);
//
//                            binding.progressBar.setVisibility(View.GONE);
//
//
//                        }
//
//                        @Override
//                        public void onLoadFailed(Exception e, Drawable errorDrawable) {
//                            super.onLoadFailed(e, errorDrawable);
//
//                            imageView.setVisibility(View.VISIBLE);
//                            imageView.setImageDrawable(
//                                    ContextCompat.getDrawable(
//                                            ItemInfoAct.this,
//                                            R.drawable.no_product_image));
//
//                            binding.progressBar.setVisibility(View.GONE);
//
//
//                        }
//
//                    });
//
//        } else {
//            imageView.setVisibility(View.VISIBLE);
//            imageView.setImageDrawable(ContextCompat.getDrawable(ItemInfoAct.this,
//                    R.drawable.no_product_image));
//
//            binding.progressBar.setVisibility(View.GONE);
//
//
//        }
//    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.imgBack:
                onBackPressed();
                break;

        }
    }



    private void startLoadImageTask(String imageUri)
    {   binding.progressBar.setVisibility(View.VISIBLE);
        LoadImageTask loadImageTask = new LoadImageTask();
        loadImageTask.execute(imageUri);
    }

    public static int calculateInSampleSize(
            BitmapFactory.Options options, int reqWidth, int reqHeight) {

        // USE THIS METHOD ONLY WHEN YOU KNOW inSampleSize FOR SCALING AND REQ HEIGHT AND WIDTH OF IMAGE AS PER DEVICE

        // Raw height and width of image
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;

        if (height > reqHeight || width > reqWidth) {

            final int halfHeight = height / 2;
            final int halfWidth = width / 2;

            // Calculate the largest inSampleSize value that is a power of 2 and keeps both
            // height and width larger than the requested height and width.
            while ((halfHeight / inSampleSize) >= reqHeight
                    && (halfWidth / inSampleSize) >= reqWidth) {
                inSampleSize *= 2;
            }
        }

        return inSampleSize;
    }


    private Bitmap getBitmapFromURLDownScaleIt(String image_link, int req_height, int req_width)
    {
        URL website;

        try {
            website = new URL(image_link);
            HttpURLConnection connection = (HttpURLConnection) website.openConnection();
//            connection.setDoInput(true);
            connection.connect();
            InputStream is = connection.getInputStream();

            if(req_width == 0)
            {
                return BitmapFactory.decodeStream(is);
            }

            // First decode with inJustDecodeBounds=true to check dimensions
            final BitmapFactory.Options options = new BitmapFactory.Options();
            options.inJustDecodeBounds = true;
            BitmapFactory.decodeStream(is, null, options);

            //GETTING BITMAP FROM URL DONE.

            int srcHeight = options.outHeight;
            int srcWidth = options.outWidth;

            Log.e(TAG, "BitmapFactory.Options source///////////////: \n" );
            Log.w(TAG,  "ORIGINAL Bitmap srcHeight: " + srcHeight);
            Log.w(TAG,  "ORIGINAL Bitmap srcWidth: " + srcWidth);

            is.close();

            //NOW, SCALING DOWN THE BITMAP GOT FROM URL STARTS.

            connection = (HttpURLConnection) website.openConnection();
            connection.setDoInput(true);
            connection.connect();
            is = connection.getInputStream();


            if (srcWidth < req_width && srcHeight < req_height) {
                // USE THIS METHOD ONLY WHEN YOU KNOW inSampleSize FOR SCALING AND REQ HEIGHT AND WIDTH OF IMAGE AS PER DEVICE
                // Calculate inSampleSize
                options.inSampleSize = calculateInSampleSize(options, req_width, req_height);
            }
            else {
                // OR THIS WHEN YOU DON'T KNOW inSampleSize FOR SCALING AND REQ HEIGHT AND WIDTH OF IMAGE AS PER DEVICE
                // AND WANT TO REDUCE RESOLUTION OF BITMAP AS PER DEVICE RESOLUTION

                options.inScaled = true;
                options.inSampleSize = 4;
                options.inDensity = srcWidth;
                options.inTargetDensity = req_width*options.inSampleSize;
            }

//            // USE THIS METHOD ONLY WHEN YOU KNOW inSampleSize FOR SCALING AND REQ HEIGHT AND WIDTH OF IMAGE AS PER DEVICE
//            // Calculate inSampleSize
////            options.inSampleSize = calculateInSampleSize(options, req_width, req_height);
//
//            // OR THIS WHEN YOU DON'T KNOW inSampleSize FOR SCALING AND REQ HEIGHT AND WIDTH OF IMAGE AS PER DEVICE
//            // AND WANT TO REDUCE RESOLUTION OF BITMAP AS PER DEVICE RESOLUTION
//            options.inScaled = true;
//            options.inSampleSize = 4;
//            options.inDensity = srcWidth;
//            options.inTargetDensity = req_width*options.inSampleSize;


            // Decode bitmap with inSampleSize set

            options.inJustDecodeBounds = false;
            return BitmapFactory.decodeStream(is, null , options);

        } catch (Exception  e) {
            e.printStackTrace();
            return null;
        }
    }

    @SuppressLint("StaticFieldLeak")
    class LoadImageTask extends AsyncTask<String, Void, Bitmap> {

        LoadImageTask(){}

        private String imageUrl = "";

        @Override
        protected Bitmap doInBackground(String... strings) {
            if (strings != null) {
                imageUrl = strings[0];

                Bitmap bitmap = null;
                if (imageUrl != null && !imageUrl.isEmpty())
                    bitmap = getBitmapFromURLDownScaleIt(imageUrl, targetHeight, targetWidth);

                bitmap_n = bitmap;

                if (bitmap != null)
                    return bitmap;
                else
                    return null;
            }

            return null;
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            super.onPostExecute(bitmap);

            if (bitmap != null) {

                int h = bitmap.getHeight();
                int w = bitmap.getWidth();

                Log.e(TAG, "\nonPostExecute: *************\n" );

                Log.w(TAG, "Device targetHeight: " + targetHeight);
                Log.w(TAG, "Device targetWidth: " + targetWidth);

                Log.w(TAG, "New Bitmap height: " + h);
                Log.w(TAG, "New Bitmap width: " + w);

                //CONVERT BITMAP TO BYTE ARRAY FOR GLIDE
                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
                byte[] bitmap_data = stream.toByteArray();

                setUpProPic(bitmap_data, binding.imgCover);


                //START SERVICE
                Intent i = new Intent(ItemInfoAct.this,NotifyService.class);
                Bundle bundle = new Bundle();
                bundle.putParcelable("dataBean", dataBean);
                bundle.putByteArray("bitmap_data", bitmap_data);
                i.putExtras(bundle);
                startService(i);

                Log.w(AppConstants.kDefaultAppName, "NotifyService: shoot " );


            }
            else {
                binding.imgCover.setVisibility(View.VISIBLE);
                binding.imgCover.setImageDrawable(ContextCompat.getDrawable(ItemInfoAct.this,
                        R.drawable.no_product_image));
                binding.progressBar.setVisibility(View.GONE);

                MyApplication.makeBottomToast("Image URL Incorrect!");
            }
        }
    }

    //    METHODS: TO SET PROFILE / COVER PICS USING GLIDE
    private void setUpProPic( byte[] bitmap_data, final ImageView imageView) {

        binding.progressBar.setVisibility(View.VISIBLE);
        ServiceParameters.getInstance()
                .setUpProgressbarCover(ItemInfoAct.this, binding.progressBar);

        Glide.with(ItemInfoAct.this.getApplicationContext())
                .load(bitmap_data)
                .dontTransform()
                .into(new SimpleTarget<GlideDrawable>(Target.SIZE_ORIGINAL,Target.SIZE_ORIGINAL) {
                    @Override
                    public void onResourceReady(GlideDrawable resource, GlideAnimation<? super GlideDrawable> glideAnimation) {

                        imageView.setVisibility(View.VISIBLE);
                        imageView.setImageDrawable(resource);

                        binding.progressBar.setVisibility(View.GONE);
                    }

                    @Override
                    public void onLoadFailed(Exception e, Drawable errorDrawable) {
                        super.onLoadFailed(e, errorDrawable);
                        binding.progressBar.setVisibility(View.GONE);

                        setUpProPic(bitmap_data, imageView);
                    }
                });


    }



    /*SERVICES*/
    //    METHOD: TO REQUEST GET getProductInfo SERVICE..
    public void getProductInfoService() {

        final CustomDialog dialog;

//                  REQUESTING GET getProductInfo  SERVICE..
        try {

            if (dataBean != null)
                itemId = dataBean.getId();

            ApiInterface apiInterface = ApiClientConnection.getInstance().createApiInterface();


            Call<ResponseBean> call = apiInterface.getProductInfo("json", String.valueOf(itemId));

            dialog = CustomDialog.showDialog(ItemInfoAct.this);

            ServiceParameters.setUpParameters("json", String.valueOf(itemId));

            call.enqueue(new Callback<ResponseBean>() {
                @Override
                public void onResponse(@NonNull Call<ResponseBean> call, @NonNull Response<ResponseBean> response) {
                    CustomDialog.hideCloseDialog(dialog);
                    if (response.isSuccessful()) {
                        ResponseBean bean = response.body();
                        if (bean != null) {

                            ArrayList<DataBean> list = bean.getDataBeanList();

                            if (list != null && list.size() > 0) {
                                dataBean = list.get(0);

                                if (dataBean != null) {

                                    ArrayList<Cat_> l = (ArrayList<Cat_>) dataBean.getCats();
                                    StringBuilder categories = new StringBuilder();
                                    if (l != null && l.size() > 0) {
                                        for (Cat_ c :
                                                l) {
                                            categories.append(c.getN());
                                            categories.append(",");
                                        }
                                    }

                                    itemId = dataBean.getId();
                                    imageUrl = dataBean.getI();

                                    String name = dataBean.getT();
                                    String md_time = dataBean.getMd();
                                    String shortD = dataBean.getSd();
                                    String fullDesc = dataBean.getD();

                                    if (shortD != null && !shortD.isEmpty()) {
                                        Spanned short_desc = Html.fromHtml(shortD);
                                        binding.tvShortDesc.setText(short_desc);
                                    } else {
                                        binding.tvShortDesc.setText("N.A");
                                    }


                                    if (fullDesc != null && !fullDesc.isEmpty()) {
                                        Spanned short_desc = Html.fromHtml(fullDesc);
                                        binding.tvDescFull.setText(short_desc);

                                    } else {
                                        binding.tvDescFull.setText("N.A");
                                    }



                                    String mrp = dataBean.getP();
                                    String buyPrice = dataBean.getDp();

                                    String p;
                                    if (buyPrice == null)
                                        p = "MRP: Rs. " + mrp;
                                    else
                                        p = "MRP: Rs. " + mrp + ", Now Buy at just Rs. " + buyPrice;

                                    String cats = categories.toString().trim();
                                    binding.tvCategoryName.setText(cats);

                                    binding.tvName.setText(name);
                                    binding.tvTimeDate.setText(md_time);

                                    binding.tvPriceBuy.setText(p);


                                    if (imageUrl != null && !imageUrl.isEmpty()) {

                                        // First, GET THE RESOLUTION OF DEVICE (targetHeight,targetWidth)
                                        WindowManager wm = (WindowManager) getSystemService(Context.WINDOW_SERVICE);
                                        Display display;
                                        if (wm != null) {
                                            display = wm.getDefaultDisplay();
                                            DisplayMetrics metrics = new DisplayMetrics();
                                            display.getMetrics(metrics);
                                            targetHeight = metrics.heightPixels;
                                            targetWidth = metrics.widthPixels;
                                        }

                                        startLoadImageTask(imageUrl); // start ASYNC_TASK

                                    } else {

                                        binding.imgCover.setImageDrawable(ContextCompat.getDrawable(ItemInfoAct.this, R.drawable.no_product_image));

                                    }
                                }

                            }

                        } else {
                            MyApplication.makeCenterToast(AppConstants.kMessageSomeWentWrong);
                        }

                    } else {
                        Toast.makeText(ItemInfoAct.this, AppConstants.kMessageServerNotRespondingError, Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(@NonNull Call<ResponseBean> call, @NonNull Throwable t) {
                    CustomDialog.hideCloseDialog(dialog);
                    t.printStackTrace();
                    MyApplication.showOnFailureMessages(ItemInfoAct.this, t);
                }
            });


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String removeLastChar(String s) {
        if (s == null || s.length() == 0) {
            return s;
        }
        return s.substring(0, s.length()-1);
    }

    /*NOTIFICATION*/


    private void scheduleNotification(Notification notification, int delay) {

        Intent notificationIntent = new Intent(this, NotificationBR.class);
        notificationIntent.putExtra(NotificationBR.NOTIFICATION_ID, 1);
        notificationIntent.putExtra(NotificationBR.NOTIFICATION, notification);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 0, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        long futureInMillis = SystemClock.elapsedRealtime() + delay;
        AlarmManager alarmManager = (AlarmManager)getSystemService(Context.ALARM_SERVICE);
        alarmManager.set(AlarmManager.ELAPSED_REALTIME_WAKEUP, futureInMillis, pendingIntent);
    }

    private Notification getNotification(String content) {
        Notification.Builder builder = new Notification.Builder(this);
        builder.setContentTitle("Scheduled Notification");
        builder.setContentText(content);
        builder.setSmallIcon(R.mipmap.ic_launcher);
        return builder.build();
    }



}
