package com.test.halevi.barakapp.application;

import android.util.LruCache;

import com.test.halevi.barakapp.model.ContactResponse;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Barak Halevi on 07/11/2018.
 */
public class NetworkService {
    private LruCache<Class<?>, Observable<?>> apiObservables = new LruCache<>(10);
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


    public Observable<?> getPreparedObservable(Observable<ContactResponse> unPreparedObservable, Class<ContactResponse> clazz, boolean cacheObservable, boolean useCache){
        Observable<?> preparedObservable = null;
        if(useCache) //this way we don't reset anything in the cache if this is the only instance of us not wanting to use it.
            preparedObservable = apiObservables.get(ContactResponse.class);
        if(preparedObservable!=null)
            return preparedObservable;
        preparedObservable = unPreparedObservable.subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread());

        if(cacheObservable){
            preparedObservable = preparedObservable.cache();
            apiObservables.put(clazz, preparedObservable);
        }
        return preparedObservable;
    }

    public interface ApiInterface {

        @GET("contacts/")
        Observable<ContactResponse> getContacts();
    }
}