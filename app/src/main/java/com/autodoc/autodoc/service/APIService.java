package com.autodoc.autodoc.service;

import com.autodoc.autodoc.api.request.FeedBackRequest;
import com.autodoc.autodoc.api.request.LoginRequest;
import com.autodoc.autodoc.api.request.NewUserRequest;
import com.autodoc.autodoc.api.request.ReportRequest;
import com.autodoc.autodoc.api.response.JobResponse;
import com.autodoc.autodoc.api.response.SuccessResponse;
import com.autodoc.autodoc.api.response.TechnicianResponse;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.Response;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by ilabs on 8/17/18.
 */

public interface APIService {


    @POST("users/authenticate")
    Observable<Response<SuccessResponse>> login(@Body LoginRequest loginRequest);

    @POST("users/registercustomer")
    Observable<Response<SuccessResponse>> register(@Body NewUserRequest newUserRequest);

    @GET("technicians")
    Observable<Response<List<TechnicianResponse>>> getTechnicians(@Header("Authorization") String authorization, @Query("type") String type);

    @POST("jobs/")
    Observable<Response<SuccessResponse>> postJob(@Header("Authorization") String authorization, @Body ReportRequest reportRequest);

    @GET("users/alljobs")
    Observable<Response<List<JobResponse>>> getAllJobs(@Header("Authorization") String authorization);

    @PUT("jobs/{jobId}")
    Observable<Response<SuccessResponse>> sendFeedBack(@Header("Authorization") String authorization, @Path("jobId") String technicianId, @Body FeedBackRequest feedBack);
}
