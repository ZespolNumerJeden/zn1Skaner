package com.example.rick.agileitticket;

import com.example.rick.agileitticket.android.Global;
import com.example.rick.agileitticket.models.ApiResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.PATCH;
import retrofit2.http.POST;
import retrofit2.http.Path;

interface APIInterface {

    @Headers({"Content-Type: application/json", "apiKey: abcd"})

    @GET("/api/ticket/{ticket}")
    Call<ApiResponse> getId(@Path("ticket") String ticketId );

    @PATCH("/api/ticket/")
    Call<ApiResponse> setIsPresent(@Header("IsPresent") boolean is_present);
}