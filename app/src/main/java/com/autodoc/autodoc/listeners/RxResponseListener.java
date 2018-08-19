package com.autodoc.autodoc.listeners;

import retrofit2.Response;

/**
 * Created by ilabs on 8/15/18.
 */

public interface RxResponseListener<T> {
    /**
     * @param serviceResponseResponse this method will give the server response as a string
     */
    void serviceResponse(Response<T> serviceResponseResponse);

    /**
     * @param throwable this will call if any exception occur
     */
    void serviceThrowable(Throwable throwable);

    /**
     * this method will call after server call completed
     */
    void serviceComplete();

}
