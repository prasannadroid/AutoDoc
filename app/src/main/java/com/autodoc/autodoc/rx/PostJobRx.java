package com.autodoc.autodoc.rx;

import android.content.Context;

import com.autodoc.App;
import com.autodoc.autodoc.api.request.NewUserRequest;
import com.autodoc.autodoc.api.request.ReportRequest;
import com.autodoc.autodoc.api.response.SuccessResponse;
import com.autodoc.autodoc.api.retrofit.RetrofitClient;
import com.autodoc.autodoc.listeners.RxResponseListener;
import com.autodoc.autodoc.service.APIService;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Response;

/**
 * Created by ilabs on 8/19/18.
 */

public class PostJobRx {

    //constants
    private static final String TAG = RegisterRx.class.getSimpleName();

    private ReportRequest reportRequest;
    private RxResponseListener mRxResponseListener;

    public PostJobRx(ReportRequest reportRequest, RxResponseListener rxResponseListener) {
        this.mRxResponseListener = rxResponseListener;
        this.reportRequest = reportRequest;
    }

    public void callServiceRXWay() {
        APIService endPointService = RetrofitClient.getInstance().create(APIService.class);
        String authToken = App.getInstance().getUserSession().getAuthToken();
        Observable<Response<SuccessResponse>> observable = endPointService.postJob(authToken,reportRequest);

        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .unsubscribeOn(Schedulers.io())
                .subscribe(serviceResponseResponse -> mRxResponseListener.serviceResponse(serviceResponseResponse),
                        throwable -> mRxResponseListener.serviceThrowable(throwable),
                        () -> mRxResponseListener.serviceComplete());
    }
}
