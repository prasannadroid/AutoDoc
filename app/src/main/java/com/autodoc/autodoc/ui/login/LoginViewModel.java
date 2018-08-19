package com.autodoc.autodoc.ui.login;

import android.app.Dialog;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.content.Context;
import android.support.v7.app.AlertDialog;
import android.widget.Toast;

import com.autodoc.App;
import com.autodoc.autodoc.R;
import com.autodoc.autodoc.api.response.SuccessResponse;
import com.autodoc.autodoc.listeners.RxResponseListener;
import com.autodoc.autodoc.rx.LoginRx;
import com.autodoc.autodoc.util.AppUtil;

import retrofit2.Response;

/**
 * Created by ilabs on 8/17/18.
 */

public class LoginViewModel extends ViewModel {

    public MutableLiveData<Boolean> callLoginRx(Context context, String userName, String password) {

        final MutableLiveData<Boolean> responseMutableLiveData = new MutableLiveData<>();
        Dialog dialog = AppUtil.showProgress(context);
        dialog.show();
        new LoginRx(context, userName, password, new RxResponseListener<SuccessResponse>() {
            @Override
            public void serviceResponse(Response<SuccessResponse> response) {
                dialog.dismiss();
                if (response != null && response.body() != null) {
                    if (response.body().success) {
                        boolean isUserSaved = App.getInstance().getUserSession().setAuthToken(response.body().token);
                        if (isUserSaved) {
                            Toast.makeText(context, context.getString(R.string.authenticated), Toast.LENGTH_SHORT).show();
                            if (response.body().user != null) {
                                App.getInstance().getUserSession().saveUser(response.body().user);
                            }
                            responseMutableLiveData.setValue(response.body().success);
                        } else {
                            final AlertDialog dialog = new AlertDialog.Builder(context).create();
                            AppUtil.standardAlert(dialog, context.getString(R.string.msg_error), context.getString(R.string.msg_contact_service_provider), v -> {
                                        dialog.dismiss();
                                    },
                                    context.getResources().getString(R.string.text_ok));
                        }

                    } else {
                        final AlertDialog dialog = new AlertDialog.Builder(context).create();
                        AppUtil.standardAlert(dialog, context.getString(R.string.msg_error), response.body().message, v -> {
                                    dialog.dismiss();
                                },
                                context.getResources().getString(R.string.text_ok));
                    }
                } else {
                    final AlertDialog dialog = new AlertDialog.Builder(context).create();
                    AppUtil.standardAlert(dialog, context.getString(R.string.msg_error), context.getString(R.string.msg_contact_service_provider), v -> {
                                dialog.dismiss();
                            },
                            context.getResources().getString(R.string.text_ok));
                }
            }

            @Override
            public void serviceThrowable(Throwable throwable) {
                final AlertDialog dialog = new AlertDialog.Builder(context).create();
                AppUtil.standardAlert(dialog, context.getString(R.string.msg_error), throwable.getMessage(), v -> {
                            dialog.dismiss();
                        },
                        context.getResources().getString(R.string.text_ok));

            }

            @Override
            public void serviceComplete() {
                dialog.dismiss();
            }
        }).callServiceRXWay();

        return responseMutableLiveData;
    }
}
