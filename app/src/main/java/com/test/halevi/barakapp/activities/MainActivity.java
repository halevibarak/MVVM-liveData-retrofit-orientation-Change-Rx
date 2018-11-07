package com.test.halevi.barakapp.activities;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ProgressBar;

import com.test.halevi.barakapp.R;
import com.test.halevi.barakapp.application.RxApplication;
import com.test.halevi.barakapp.adapter.ContactAdapter;
import com.test.halevi.barakapp.adapter.ContactDecoration;
import com.test.halevi.barakapp.application.NetworkService;
import com.test.halevi.barakapp.interfaces.DescriptionInterface;
import com.test.halevi.barakapp.model.Contact;
import com.test.halevi.barakapp.model.ContactResponse;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import rx.Observable;
import rx.Observer;
import rx.Subscription;

/**
 * Created by Barak on 24/08/2017.
 */
public class MainActivity extends AppCompatActivity implements DescriptionInterface {

    private ContactAdapter contactsAdapter;
    private List<Contact> contacts;
    private static final String TAG = "TAG";
    public static final String DESCRIPTION = "DESCRIPTION";
    private ProgressBar mProgressView;
    private EditText mEditSearch;
    private Subscription subscription;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        contacts = new ArrayList<>();

        mProgressView = findViewById(R.id.determinateBar);
        mEditSearch = findViewById(R.id.edit_text);
        mEditSearch.addTextChangedListener(mTextWatcher);
        mEditSearch.requestFocus();
        InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        inputMethodManager.showSoftInput(mEditSearch, InputMethodManager.SHOW_IMPLICIT);

        NetworkService service = RxApplication.getInstance().getNetworkService();
        Observable<ContactResponse> friendResponseObservable = (Observable<ContactResponse>)service.getPreparedObservable(service.getAPI().getContacts(), ContactResponse.class, true, true);

        subscription = friendResponseObservable.subscribe(new Observer<ContactResponse>() {
            @Override
            public void onCompleted(){ }

            @Override
            public void onError(Throwable e){

            }

            @Override
            public void onNext(ContactResponse response) {
                contacts = response.getContacts();
                Collections.sort(contacts,new Contact());
                updateUI();
            }
        });


//        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
//        Observable<ContactResponse> london = apiService.getContacts();
//        london.subscribeOn(Schedulers.newThread())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(
//                        new Observer<ContactResponse>() {
//                            @Override
//                            public void onCompleted() {
//
//                            }
//
//                            @Override
//                            public void onError(Throwable e) {
//                                mProgressView.setVisibility(View.GONE);
//                            }
//
//                            @Override
//                            public void onNext(ContactResponse dd) {
//                                contacts = dd.getContacts();
//                                Collections.sort(contacts,new Contact());
//                                updateUI();
//                            }
//                        });




    }
    private TextWatcher mTextWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            if (s.length() == 0 || s.length() >= 0) {
                if (contacts.size() > 0){
                    contactsAdapter.search(s.toString().toLowerCase());
                }
            }
        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    };

    public static int dpToPx(int dp){
        return (int)(dp * Resources.getSystem().getDisplayMetrics().density);
    }

    @Override
    public void goToDescription(String desc) {
        Intent intent = new Intent(MainActivity.this, DescriptionActivity.class);
        intent.putExtra(DESCRIPTION,desc);
        startActivity(intent);
    }
    private void makeJsonRequest() {


    }

    private void updateUI() {
        if (contacts != null){
            if (contacts.size() == 0){
                mProgressView.setVisibility(View.GONE);
            }
            else {
                mProgressView.setVisibility(View.GONE);
                contactsAdapter = new ContactAdapter(contacts,this);
                RecyclerView recyclerView = findViewById(R.id.contact_list);
                recyclerView.setVisibility(View.VISIBLE);
                recyclerView.setLayoutManager(new LinearLayoutManager(recyclerView.getContext()));
                recyclerView.addItemDecoration(new ContactDecoration(dpToPx(10)));
                recyclerView.setAdapter(contactsAdapter);
            }
        }


    }
    public void rxUnSubscribe(){
        if(subscription!=null && !subscription.isUnsubscribed())
            subscription.unsubscribe();
    }

    @Override
    protected void onPause() {
        super.onPause();
        rxUnSubscribe();
    }
}
