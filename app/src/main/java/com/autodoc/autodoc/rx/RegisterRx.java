package com.autodoc.autodoc.rx;

import android.content.Context;

import com.autodoc.autodoc.api.request.LoginRequest;
import com.autodoc.autodoc.api.request.NewUserRequest;
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

public class RegisterRx {

    //constants
    private static final String TAG = RegisterRx.class.getSimpleName();

    private Context mContext;
    private NewUserRequest newUserRequest;
    private RxResponseListener mRxResponseListener;

    public RegisterRx(Context context, NewUserRequest newUserRequest, RxResponseListener rxResponseListener) {
        this.mContext = context;
        this.mRxResponseListener = rxResponseListener;
        this.newUserRequest = newUserRequest;
    }

    public void callServiceRXWay() {
        APIService endPointService = RetrofitClient.getInstance().create(APIService.class);

        Observable<Response<SuccessResponse>> observable = endPointService.register(newUserRequest);

        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .unsubscribeOn(Schedulers.io())
                .subscribe(serviceResponseResponse -> mRxResponseListener.serviceResponse(serviceResponseResponse),
                        throwable -> mRxResponseListener.serviceThrowable(throwable),
                        () -> mRxResponseListener.serviceComplete());
    }
}
