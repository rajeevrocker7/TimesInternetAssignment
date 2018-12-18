package com.rajeev.timesinternetassignment.activities;

import android.content.Intent;
import android.content.IntentFilter;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.crashlytics.android.Crashlytics;
import com.google.firebase.FirebaseApp;
import com.google.firebase.iid.FirebaseInstanceId;
import com.rajeev.timesinternetassignment.BuildConfig;
import com.rajeev.timesinternetassignment.R;
import com.rajeev.timesinternetassignment.constants.AppConstants;
import com.rajeev.timesinternetassignment.databinding.ActivitySplashBinding;
import com.rajeev.timesinternetassignment.sharedpreferences.SPreferenceKey;
import com.rajeev.timesinternetassignment.sharedpreferences.SPreferenceWriter;
import com.rajeev.timesinternetassignment.utils.InternetCheck;
import com.rajeev.timesinternetassignment.utils.MyApplication;

public class SplashAct extends AppCompatActivity {


    private InternetCheck internetCheck;
    private Handler handler = null;
    private ActivitySplashBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //      By mention launcher theme in Manifest and then  Doing  this can hide a slow activity launch.
        setTheme(R.style.LoginStyle);

        super.onCreate(savedInstanceState);

        binding = DataBindingUtil.setContentView(this, R.layout.activity_splash);


        String versionName = "Version:" + BuildConfig.VERSION_NAME;
        binding.tvAppVersion.setText(versionName);

        //INITIALIZATION
        FirebaseApp.initializeApp(this);

        handler = new Handler();

        internetCheck = new InternetCheck(getApplicationContext());

    }

    @Override
    protected void onStart() {
        super.onStart();

        registerReceiver(internetCheck, new IntentFilter("android.net.conn.CONNECTIVITY_CHANGE"));

        if (internetCheck != null && !internetCheck.isConnect()) {
            MyApplication.makeASnack(binding.getRoot(), AppConstants.kMakeSureInternet);
        }

        startUpCode();
    }

    private void startUpCode() {
        SPreferenceWriter.getInstance(SplashAct.this.getApplicationContext());

        getDeviceToken();

        gotToNextActivity();

    }

    private void gotToNextActivity() {
        int SPLASH_TIME_OUT = 2000;
        Runnable runnable = () -> {

            if (internetCheck.isConnect()) {
                goToHomeAct();
            } else {

                // GO TO NEXT WHENEVER CONNECTED NOW
                internetCheck.setInternetCheckInterface(isConnected -> {
                    if (isConnected) {

                        startUpCode();
                    }
                });
            }


        };

        handler.removeCallbacks(runnable);
        handler.postDelayed(runnable, SPLASH_TIME_OUT);
    }

    private void goToHomeAct() {

        Intent intent = new Intent(this, MainActivity.class);
        Bundle bundle = new Bundle();
        intent.putExtras(bundle);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);

        this.finish();
    }


    //    METHOD: TO GET DEVICE_TOKEN FROM FCM
    private void getDeviceToken() {

        final String TAG = SplashAct.class.getSimpleName();
        Runnable runnableObj = () -> {

            SPreferenceWriter mPreference = SPreferenceWriter.getInstance(SplashAct.this.getApplicationContext());
            Log.w(TAG, "Previous Device Token : " + mPreference.getString(SPreferenceKey.DEVICE_TOKEN));

            try {
                if (mPreference.getString(SPreferenceKey.DEVICE_TOKEN).isEmpty()) {
                    String device_token = FirebaseInstanceId.getInstance().getToken();
                    Log.e(TAG, "Generated Device Token : " + device_token);
                    if (device_token == null) {
                        getDeviceToken();
                    } else {
                        mPreference.writeStringValue(SPreferenceKey.DEVICE_TOKEN, device_token);
                    }
                }
            } catch (Exception e1) {
                e1.printStackTrace();

                // [START Crashlytics log_and_report]
                Crashlytics.log(Log.ERROR, TAG, "NPE caught!");
                Crashlytics.logException(e1);
            }


        };

        Thread thread = new Thread(runnableObj);
        thread.start();

        // [START crashlytics_log_event]
        Crashlytics.log("Splash Activity created");

    }



    @Override
    protected void onStop() {
        super.onStop();
        unregisterReceiver(internetCheck);
    }

}
