package com.autodoc.autodoc.rx;

import android.content.Context;


import com.autodoc.autodoc.api.request.LoginRequest;
import com.autodoc.autodoc.api.response.SuccessResponse;
import com.autodoc.autodoc.api.retrofit.RetrofitClient;
import com.autodoc.autodoc.service.APIService;
import com.autodoc.autodoc.listeners.RxResponseListener;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Response;


/**
 * Created by ilabs on 8/15/18.
 */

public class LoginRx {

    //constants
    private static final String TAG = LoginRx.class.getSimpleName();

    private Context mContext;
    private String userName;
    private String password;
    //response listener
    private RxResponseListener mRxResponseListener;

    public LoginRx(Context context, String userName, String password, RxResponseListener rxResponseListener) {
        this.mContext = context;
        this.userName = userName;
        this.password = password;
        this.mRxResponseListener = rxResponseListener;
    }

    public void callServiceRXWay() {
        APIService endPointService = RetrofitClient.getInstance().create(APIService.class);

        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setUserName(userName);
        loginRequest.setPassword(password);

        Observable<Response<SuccessResponse>> observable = endPointService.login(loginRequest);

        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .unsubscribeOn(Schedulers.io())
                .subscribe(serviceResponseResponse -> mRxResponseListener.serviceResponse(serviceResponseResponse),
                        throwable -> mRxResponseListener.serviceThrowable(throwable),
                        () -> mRxResponseListener.serviceComplete());
    }
}
