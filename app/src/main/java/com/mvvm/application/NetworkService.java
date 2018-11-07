package com.mvvm.application;

import com.mvvm.model.ContactResponse;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import rx.Observable;

/**
 * Created by Barak Halevi on 07/11/2018.
 */
public class NetworkService {
    private static String baseUrl = "https://api.androidhive.info/";
    private ApiInterface networkAPI;

    public NetworkService() {
        this(baseUrl);
    }

    public NetworkService(String baseUrl) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();

        networkAPI = retrofit.create(ApiInterface.class);
    }

    public ApiInterface getAPI() {
        return networkAPI;
    }



    public interface ApiInterface {

        @GET("contacts/")
        Observable<ContactResponse> getContacts();
    }
}