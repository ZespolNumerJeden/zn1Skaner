package com.example.rick.agileitticket;

import com.example.rick.agileitticket.models.ApiResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.PATCH;
import retrofit2.http.Path;

interface APIInterface {

    @Headers({"Content-Type: application/json", "apiKey: abcd"})

    @GET("/api/ticket/{ticket}")
    Call<ApiResponse> getId(@Path("ticket") String ticketId);

    @PATCH("/api/ticket/{ticket}")
    Call<ApiResponse> setIsPresent(@Path("ticket") String ticketId, @Header("IsPresent") boolean is_present);

    @PATCH("/api/ticket/{ticket}")
    Call<ApiResponse> setWasInPast(@Path("ticket") String ticketId, @Header("WasInPast") boolean was_in_past);

}