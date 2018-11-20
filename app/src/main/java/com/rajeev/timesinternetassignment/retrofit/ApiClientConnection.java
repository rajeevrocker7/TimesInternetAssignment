package com.rajeev.timesinternetassignment.retrofit;

///**
// * Created by Rajeev Sharma [rajeevrocker7@gmail.com] on 13/11/2018.
// */

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

import com.rajeev.timesinternetassignment.activities.SplashAct;
import com.rajeev.timesinternetassignment.sharedpreferences.SPreferenceKey;
import com.rajeev.timesinternetassignment.sharedpreferences.SPreferenceWriter;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


/**************
 * RETROFIT API CLIENT CONNECTION 'SINGLETON' CLASS
 **************/

public class ApiClientConnection {

    private static final String BASE_URL = "https://api.getmeashop.com/seller2/api/v2/";
    private static ApiClientConnection apiClient = null;
    private ApiInterface myApiInterface;

    //  CREATE INSTANCE OF API_CLIENT
    public static synchronized ApiClientConnection getInstance() {
        if (apiClient == null) {
            apiClient = new ApiClientConnection();
        }
        return apiClient;
    }

    //    CREATE INSTANCE OF API_INTERFACE
    public ApiInterface createApiInterface() throws Exception {
        if (myApiInterface == null) {

            //FOR TOKEN AUTH USE:
            // https://stackoverflow.com/questions/32926190/retrofit-custom-client-for-webtokens-authentication/33366231#33366231

            HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
            // set your desired log level
            logging.setLevel(HttpLoggingInterceptor.Level.BODY);

            OkHttpClient.Builder httpClient = new OkHttpClient.Builder()
                    .connectTimeout(100, TimeUnit.SECONDS)
                    .readTimeout(100, TimeUnit.SECONDS);
            // add your other interceptors …

            // add logging as last interceptor
            httpClient.addInterceptor(logging);

            Retrofit.Builder builder = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(httpClient.build());

            Retrofit retrofit = builder.build();

            myApiInterface = retrofit.create(ApiInterface.class);
        }
        return myApiInterface;
    }


    public void checkIfUserInvalid(Activity activity, Context context, String serverMsg) {
        if (serverMsg != null && activity != null) {
            if (serverMsg.equalsIgnoreCase("Invalid User.") || serverMsg.equalsIgnoreCase("Invalid Access token!")
                    || serverMsg.equalsIgnoreCase("Invalid access token ")
                    || serverMsg.equalsIgnoreCase("user not exists")
                    || serverMsg.equalsIgnoreCase("Invalid Token")
                    || serverMsg.equalsIgnoreCase("Token Expire")
                    || serverMsg.equalsIgnoreCase("Invalid Access Token ")
                    || serverMsg.equalsIgnoreCase("Invalid access token")) {


                SPreferenceWriter.getInstance(activity).writeStringValue(SPreferenceKey.IS_LOGIN, "NO");
                SPreferenceWriter.getInstance(activity).writeStringValue(SPreferenceKey.IS_LOGOUT, "YES");
                SPreferenceWriter.getInstance(activity).writeStringValue(SPreferenceKey.USER_ID, "");
                SPreferenceWriter.getInstance(activity).writeStringValue(SPreferenceKey.USER_NAME, "");
                SPreferenceWriter.getInstance(activity).writeStringValue(SPreferenceKey.USER_IMAGE, "");


                activity.finishAffinity();

                Intent intent = new Intent(activity, SplashAct.class);
                context.startActivity(intent);

            }
        }
    }


}
