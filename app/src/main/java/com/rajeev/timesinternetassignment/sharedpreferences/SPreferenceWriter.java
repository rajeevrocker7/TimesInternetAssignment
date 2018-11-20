package com.rajeev.timesinternetassignment.sharedpreferences;

import android.content.Context;
import android.content.SharedPreferences;

import static com.rajeev.timesinternetassignment.constants.AppConstants.kAppPreferences;

public class SPreferenceWriter {

    private static Context context = null;
    private static SPreferenceWriter sharePref = null;
    private static SharedPreferences mPrefs;
    private static SharedPreferences.Editor prefEditor = null;

    private SPreferenceWriter() {
    }

    public static synchronized SPreferenceWriter getInstance(Context c) {

        if (sharePref == null) {

            if (context == null)
                context = c;

            sharePref = new SPreferenceWriter();
            mPrefs = context.getSharedPreferences(kAppPreferences, Context.MODE_PRIVATE);
            prefEditor = mPrefs.edit();

            return sharePref;
        } else {
            return sharePref;
        }

    }

    public void writeStringValue(String key, String value) {

        prefEditor.putString(key, value);
        prefEditor.commit();
    }

    public void writeIntValue(String key, int value) {

        prefEditor.putInt(key, value);
        prefEditor.commit();
    }


    public void writeBooleanValue(String key, boolean value) {

        prefEditor.putBoolean(key, value);
        prefEditor.commit();
    }

    public void writeLongValue(String key, long value) {

        prefEditor.putLong(key, value);
        prefEditor.commit();
    }

    public void clearPreferenceValue(String key, String value) {

        prefEditor.putString(key, "");
        prefEditor.commit();

    }

    public String getString(String key) {

        return mPrefs.getString(key, "");

    }

    public int getInt(String key) {

        return mPrefs.getInt(key, 0);
    }

    public boolean getBoolean(String key) {

        return mPrefs.getBoolean(key, false);
    }

    public long getLong(String key) {

        return mPrefs.getLong(key, (long) 0.0);
    }

    public void clearPreferenceValues(String key) {
        prefEditor.clear();
        prefEditor.commit();

    }


}
