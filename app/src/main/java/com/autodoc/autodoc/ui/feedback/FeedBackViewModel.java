package com.autodoc.autodoc.ui.feedback;

import android.app.Dialog;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.content.Context;
import android.support.v7.app.AlertDialog;

import com.autodoc.autodoc.R;
import com.autodoc.autodoc.api.request.FeedBackRequest;
import com.autodoc.autodoc.api.response.SuccessResponse;
import com.autodoc.autodoc.listeners.RxResponseListener;
import com.autodoc.autodoc.rx.FeedBackRx;
import com.autodoc.autodoc.util.AppUtil;

import retrofit2.Response;

/**
 * Created by ilabs on 8/20/18.
 */

public class FeedBackViewModel extends ViewModel {

    public MutableLiveData<Boolean> sendFeedBack(Context context, FeedBackRequest feedBack, String technicianId) {

        final MutableLiveData<Boolean> responseMutableLiveData = new MutableLiveData<>();
        Dialog dialog = AppUtil.showProgress(context);
        dialog.show();
        new FeedBackRx(feedBack, technicianId, new RxResponseListener<SuccessResponse>() {
            @Override
            public void serviceResponse(Response<SuccessResponse> successResponseResponse) {
                dialog.dismiss();
                if (successResponseResponse != null && successResponseResponse.body() != null) {
                    if (successResponseResponse.body().success) {
                        final AlertDialog dialog = new AlertDialog.Builder(context).create();
                        AppUtil.standardAlert(dialog, context.getString(R.string.msg_error), successResponseResponse.body().message, v -> {
                                    dialog.dismiss();
                                    responseMutableLiveData.setValue(successResponseResponse.body().success);
                                },
                                context.getResources().getString(R.string.text_ok));

                    } else {
                        final AlertDialog dialog = new AlertDialog.Builder(context).create();
                        AppUtil.standardAlert(dialog, context.getString(R.string.msg_error), successResponseResponse.body().message, v -> {
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
