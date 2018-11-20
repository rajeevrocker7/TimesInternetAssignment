package com.rajeev.timesinternetassignment.utils;

/* *
 * Created by Rajeev Kr. Sharma [rajeevrocker7@gmail.com] on 27/6/18.
 */

import android.app.Dialog;
import android.content.Context;
import android.content.ContextWrapper;
import android.databinding.DataBindingUtil;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Window;
import android.view.WindowManager;

import com.rajeev.timesinternetassignment.R;
import com.rajeev.timesinternetassignment.databinding.CustomDialogProgressBinding;


public class CustomDialog extends Dialog {


    private CustomDialog(Context context, int themeResId) {
        super(context, themeResId);
    }


    public static CustomDialog showDialog(@NonNull Context context) {

        CustomDialog dialog = new CustomDialog(context, R.style.progressDialog);
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        CustomDialogProgressBinding binding = DataBindingUtil.inflate(inflater,
                R.layout.custom_dialog_progress, null, false);
        dialog.setContentView(binding.getRoot());

        Drawable drawable = ContextCompat.getDrawable(context, R.drawable.draw_progress_load);
        binding.progressBar.setProgressDrawable(drawable);

        Window window = dialog.getWindow();
        if (window != null) {
            window.getAttributes().gravity = Gravity.CENTER;
        }

        dialog.setCancelable(false);
        WindowManager.LayoutParams lp = dialog.getWindow().getAttributes();
        lp.dimAmount = 0.2f;
        dialog.getWindow().setAttributes(lp);
        dialog.show();

        return dialog;
    }

    public static CustomDialog showPaymentDialog(@NonNull Context context, String message) {

        CustomDialog dialog = new CustomDialog(context, R.style.progressDialog);
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        CustomDialogProgressBinding binding = DataBindingUtil.inflate(inflater,
                R.layout.custom_dialog_progress, null, false);
        dialog.setContentView(binding.getRoot());

        Drawable drawable = ContextCompat.getDrawable(context, R.drawable.draw_progress_load);
        binding.progressBar.setProgressDrawable(drawable);

        Window window = dialog.getWindow();
        if (window != null) {
            window.getAttributes().gravity = Gravity.CENTER;
        }

        binding.tvCustomDialog.setText(message);

        dialog.setCancelable(false);
        WindowManager.LayoutParams lp = dialog.getWindow().getAttributes();
        lp.dimAmount = 0.2f;
        dialog.getWindow().setAttributes(lp);
        dialog.show();

        return dialog;
    }

    public static void hideCloseDialog(@NonNull CustomDialog dialog) {
        try {
            if (dialog.isShowing()) { //check if dialog is showing.

                //get the Context object that was used to great the dialog
                Context context = ((ContextWrapper) dialog.getContext()).getBaseContext();

                // if the Context used here was an activity AND it hasn't been finished or destroyed
                // then dismiss it
                if (context instanceof AppCompatActivity) {
                    // Api >=17
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
                        if (!((AppCompatActivity) context).isFinishing() && !((AppCompatActivity) context).isDestroyed()) {
                            dismissWithTryCatch(dialog);
                        }
                    } else {
                        // Api < 17. Unfortunately cannot check for isDestroyed()
                        if (!((AppCompatActivity) context).isFinishing()) {
                            dismissWithTryCatch(dialog);
                        }
                    }
                } else {
                    // if the Context used wasn't an Activity, then dismiss it too
                    dismissWithTryCatch(dialog);
                }

            }
            dialog = null;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void dismissWithTryCatch(@NonNull CustomDialog dialog) {
        try {
            dialog.dismiss();
            dialog.cancel();
        } catch (final Exception e) {
            // Do nothing.
            e.printStackTrace();
        } finally {
            dialog = null;
        }
    }
}
