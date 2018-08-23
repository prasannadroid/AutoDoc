package com.autodoc.autodoc.ui.technician;

import android.app.Dialog;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.content.Context;
import android.support.v7.app.AlertDialog;

import com.autodoc.autodoc.R;
import com.autodoc.autodoc.api.request.ReportRequest;
import com.autodoc.autodoc.api.response.SuccessResponse;
import com.autodoc.autodoc.api.response.TechnicianResponse;
import com.autodoc.autodoc.listeners.RxResponseListener;
import com.autodoc.autodoc.rx.PostJobRx;
import com.autodoc.autodoc.rx.TechnicianViewRX;
import com.autodoc.autodoc.util.AppUtil;

import java.util.List;

import retrofit2.Response;

/**
 * Created by ilabs on 8/19/18.
 */

public class TechnicianViewModel extends ViewModel {

    public MutableLiveData<List<TechnicianResponse>> getAllTechnicians(Context context,String type) {

        final MutableLiveData<List<TechnicianResponse>> responseMutableLiveData = new MutableLiveData<>();
        Dialog dialog = AppUtil.showProgress(context);
        dialog.show();
        new TechnicianViewRX(new RxResponseListener<List<TechnicianResponse>>() {
            @Override
            public void serviceResponse(Response<List<TechnicianResponse>> technicianResponseResponse) {
                dialog.dismiss();
                if (technicianResponseResponse != null && technicianResponseResponse.body() != null) {
                    if (technicianResponseResponse.body().size() > 0) {
                        responseMutableLiveData.setValue(technicianResponseResponse.body());
                    } else {
                        final AlertDialog dialog = new AlertDialog.Builder(context).create();
                        AppUtil.standardAlert(dialog, context.getString(R.string.msg_error), context.getString(R.string.msg_contact_service_provider), v -> {
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
        },type).callServiceRXWay();

        return responseMutableLiveData;
    }

    public MutableLiveData<Boolean> postJob(Context context, ReportRequest reportRequest) {

        final MutableLiveData<Boolean> responseMutableLiveData = new MutableLiveData<>();
        Dialog dialog = AppUtil.showProgress(context);
        dialog.show();
        new PostJobRx(reportRequest, new RxResponseListener<SuccessResponse>() {
            @Override
            public void serviceResponse(Response<SuccessResponse> successResponseResponse) {
                dialog.dismiss();
                if (successResponseResponse != null && successResponseResponse.body() != null) {
                    if (successResponseResponse.body().success) {
                        responseMutableLiveData.setValue(successResponseResponse.body().success);
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
