package com.example.rick.agileitticket;

import com.example.rick.agileitticket.android.Global;

import retrofit2.http.Field;
import com.example.rick.agileitticket.android.Global;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import org.apache.commons.lang3.builder.ToStringBuilder;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.PATCH;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.PartMap;
import com.example.rick.agileitticket.ApiClientResponse;
import com.example.rick.agileitticket.ApiClientCall;

import static android.provider.Settings.Global.getString;

/**
 * Created by rick on 14.11.17.
 */

public interface ApiInterface {
    String id = Global.ticket;
  //  Retrofit retrofit = new Retrofit.Builder()
 //           .baseUrl(url)
  //          .addConverterFactory(GsonConverterFactory.create())
  //          .build();

  //  @FormUrlEncoded
  //  @Headers({
   //         "content-type: application/json",
   //         "apiKey: abcd"
//    })
////    @GET("/api/ticket/{id}")
//    Call<Result> checkLevel(@Field("id") String id);
//}

    String ENDPOINT = "http://ogarnij-agile.azurewebsites.net";


    @GET("/api/ticket")
    Call<ApiClientResponse> checkLevel(@Field("id") String id);



    public static final Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(ApiInterface.ENDPOINT)
            .addConverterFactory(GsonConverterFactory.create())
            .build();

}
