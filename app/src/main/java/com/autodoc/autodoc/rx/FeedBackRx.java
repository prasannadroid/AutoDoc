package com.autodoc.autodoc.rx;

import com.autodoc.App;
import com.autodoc.autodoc.api.request.FeedBackRequest;
import com.autodoc.autodoc.api.response.SuccessResponse;
import com.autodoc.autodoc.api.retrofit.RetrofitClient;
import com.autodoc.autodoc.listeners.RxResponseListener;
import com.autodoc.autodoc.service.APIService;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Response;

/**
 * Created by ilabs on 8/20/18.
 */

public class FeedBackRx{
        //constants
        private static final String TAG = RegisterRx.class.getSimpleName();

        private FeedBackRequest feedBack;
        private RxResponseListener mRxResponseListener;
        private String technicianId;

        public FeedBackRx(FeedBackRequest feedBack, String technicianId, RxResponseListener rxResponseListener) {
            this.mRxResponseListener = rxResponseListener;
            this.feedBack = feedBack;
            this.technicianId = technicianId;
        }

        public void callServiceRXWay() {
            APIService endPointService = RetrofitClient.getInstance().create(APIService.class);
            String authToken = App.getInstance().getUserSession().getAuthToken();
            Observable<Response<SuccessResponse>> observable = endPointService.sendFeedBack(authToken,technicianId,feedBack);

            observable.subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .unsubscribeOn(Schedulers.io())
                    .subscribe(serviceResponseResponse -> mRxResponseListener.serviceResponse(serviceResponseResponse),
                            throwable -> mRxResponseListener.serviceThrowable(throwable),
                            () -> mRxResponseListener.serviceComplete());
        }
}
