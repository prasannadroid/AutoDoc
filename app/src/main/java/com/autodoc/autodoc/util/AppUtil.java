package com.autodoc.autodoc.util;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import com.autodoc.App;
import com.autodoc.autodoc.R;

import java.util.zip.Inflater;

/**
 * Created by ilabs on 8/18/18.
 */

public class AppUtil {

    // check network connectivity
    public static boolean checkNetworkConnection(Context context) {

        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivityManager == null) {
            return false;
        }
        NetworkInfo activeNetworks = connectivityManager.getActiveNetworkInfo();
        if (activeNetworks != null && activeNetworks.isConnected()) {
            return activeNetworks.isConnectedOrConnecting();
        }
        return false;
    }

    // custom alert dialog box
    public static void standardAlert(final AlertDialog dialog, String title, String message, View.OnClickListener buttonClickListener, String buttonText) {
        try {
            View dialogView = LayoutInflater.from(App.getInstance().getApplicationContext()).inflate(R.layout.dialog_normal_alert, null);
            dialog.setView(dialogView);
            dialog.setCancelable(false);

            TextView tvTitle = dialogView.findViewById(R.id.dialog_custom_alert_tv_title);
            TextView tvMessage = dialogView.findViewById(R.id.dialog_custom_alert_tv_message);
            Button btnCancel = dialogView.findViewById(R.id.dialog_custom_alert_btn_cancel);
            btnCancel.setOnClickListener(buttonClickListener);

            if (buttonClickListener == null)
                btnCancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });
            else {
                btnCancel.setOnClickListener(buttonClickListener);
            }

            if (title.isEmpty()) {
                tvTitle.setVisibility(View.GONE);
            } else {
                tvTitle.setText(title);
            }

            tvMessage.setText(message);
            btnCancel.setText(buttonText);
            dialog.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static Dialog showProgress(Context context) {
        Dialog dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setBackgroundDrawable(
                new ColorDrawable(0));
        dialog.setContentView(R.layout.dialog_progress);
        dialog.setCancelable(false);
        dialog.show();
        return dialog;
    }

    public static boolean checkNetwork(Context context) {
        if (!AppUtil.checkNetworkConnection(context)) {
            final AlertDialog dialog = new AlertDialog.Builder(context).create();

            AppUtil.standardAlert(dialog, context.getString(R.string.msg_error), context.getString(R.string.msg_turn_on_network), view -> {
                        dialog.dismiss();
                    },
                    context.getResources().getString(R.string.text_ok));
            return false;
        }
        return true;
    }

    public static void confirmAlert(final AlertDialog dialog, Context context,
                                    View.OnClickListener yesClickListener, View.OnClickListener noClickListener) {


        final View dialogView = LayoutInflater.from(context).inflate(R.layout.dialog_custom_confirm, null);
        dialog.setView(dialogView);
        dialog.setCancelable(false);

        TextView tvTitle = (TextView) dialogView.findViewById(R.id.dialog_custom_confirm_tv_title);
        TextView tvMessage = (TextView) dialogView.findViewById(R.id.dialog_custom_confirm_tv_message);
        Button btnOk = (Button) dialogView.findViewById(R.id.dialog_custom_confirm_btn_ok);
        Button btnCancel = dialogView.findViewById(R.id.dialog_custom_confirm_btn_cancel);
        btnOk.setOnClickListener(yesClickListener);
        btnCancel.setOnClickListener(noClickListener);

        if (yesClickListener == null)
            btnOk.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                }
            });


        if (noClickListener == null)
            btnCancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                }
            });
        tvTitle.setText(context.getString(R.string.confirm));
        tvMessage.setText(context.getString(R.string.add_job_confirm_message));
        btnOk.setText(context.getString(R.string.text_ok));
        btnCancel.setText(context.getString(R.string.text_cancel));
        dialog.show();
    }
}
