package com.autodoc.autodoc.rx;

import com.autodoc.App;
import com.autodoc.autodoc.api.response.TechnicianResponse;
import com.autodoc.autodoc.api.retrofit.RetrofitClient;
import com.autodoc.autodoc.listeners.RxResponseListener;
import com.autodoc.autodoc.service.APIService;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Response;

/**
 * Created by ilabs on 8/19/18.
 */

public class TechnicianViewRX {

    //constants
    private static final String TAG = TechnicianViewRX.class.getSimpleName();

    private RxResponseListener mRxResponseListener;
    private String type;

    public TechnicianViewRX(RxResponseListener rxResponseListener,String type) {
        this.mRxResponseListener = rxResponseListener;
        this.type = type;

    }

    public void callServiceRXWay() {
        APIService endPointService = RetrofitClient.getInstance().create(APIService.class);
        String authToken = App.getInstance().getUserSession().getAuthToken();
        Observable<Response<List<TechnicianResponse>>> observable = endPointService.getTechnicians(authToken,type);

        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .unsubscribeOn(Schedulers.io())
                .subscribe(serviceResponseResponse -> mRxResponseListener.serviceResponse(serviceResponseResponse),
                        throwable -> mRxResponseListener.serviceThrowable(throwable),
                        () -> mRxResponseListener.serviceComplete());
    }
}
