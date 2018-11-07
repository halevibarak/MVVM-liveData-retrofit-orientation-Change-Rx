package com.test.halevi.barakapp.activities;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.MutableLiveData;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.test.halevi.barakapp.application.NetworkService;
import com.test.halevi.barakapp.application.RxApplication;
import com.test.halevi.barakapp.model.Contact;
import com.test.halevi.barakapp.model.ContactResponse;

import java.util.List;

import rx.Observable;
import rx.Observer;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;


/**
 * Created by PK on 1/10/2018.
 */

public class ContactModel extends AndroidViewModel {


    @Nullable
    private JsonLiveData movieList;
    private Subscription subscription;


    private MutableLiveData<Integer> refresh = new MutableLiveData<>();

    public ContactModel(@NonNull Application application) {
        super(application);
        if (movieList == null)
            movieList = new JsonLiveData(application);

    }

    public MutableLiveData<List<Contact>> getMovieList() {
        return movieList;
    }

    public class JsonLiveData extends MutableLiveData<List<Contact>> {


        public JsonLiveData(Context context) {
            LoadData(context);
        }


        private void LoadData(Context context) {
            NetworkService service = RxApplication.getInstance().getNetworkService();
            Observable<ContactResponse> contactResponseObservable = service.getAPI().getContacts();

            subscription = contactResponseObservable.observeOn(AndroidSchedulers.mainThread())
                    .subscribeOn(Schedulers.io())

                    .subscribe(new Observer<ContactResponse>() {
                        @Override
                        public void onCompleted() {
                        }

                        @Override
                        public void onError(Throwable e) {
                            setValue(null);
                            refresh.setValue(1);
                        }

                        @Override
                        public void onNext(ContactResponse response) {
                            setValue(response.getContacts());
                            refresh.setValue(1);
                        }
                    });

        }


    }

    @Override
    protected void onCleared() {
        subscription.unsubscribe();
    }
}