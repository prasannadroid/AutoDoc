package com.autodoc.autodoc.ui.history;

import android.app.Dialog;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.content.Context;
import android.support.v7.app.AlertDialog;

import com.autodoc.autodoc.R;
import com.autodoc.autodoc.api.response.JobResponse;
import com.autodoc.autodoc.api.response.TechnicianResponse;
import com.autodoc.autodoc.listeners.RxResponseListener;
import com.autodoc.autodoc.rx.HistoryRx;
import com.autodoc.autodoc.rx.TechnicianViewRX;
import com.autodoc.autodoc.util.AppUtil;

import java.util.List;

import retrofit2.Response;

/**
 * Created by ilabs on 8/19/18.
 */

public class HistoryViewModel extends ViewModel {

    public MutableLiveData<List<JobResponse>> getJobHistory(Context context) {

        final MutableLiveData<List<JobResponse>> responseMutableLiveData = new MutableLiveData<>();
        Dialog dialog = AppUtil.showProgress(context);
        dialog.show();
        new HistoryRx(new RxResponseListener<List<JobResponse>>() {
            @Override
            public void serviceResponse(Response<List<JobResponse>> listResponse) {
                dialog.dismiss();
                if (listResponse != null && listResponse.body() != null) {
                    if (listResponse.body().size() > 0) {
                        responseMutableLiveData.setValue(listResponse.body());
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
        }).callServiceRXWay();

        return responseMutableLiveData;
    }
}
