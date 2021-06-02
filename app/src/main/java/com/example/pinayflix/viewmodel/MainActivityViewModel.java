package com.example.pinayflix.viewmodel;

import android.app.Application;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkCapabilities;
import android.net.NetworkInfo;
import android.net.NetworkRequest;
import android.os.Build;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

public class MainActivityViewModel extends AndroidViewModel {

    private MutableLiveData<Boolean> hasInternetLiveData = new MutableLiveData<>();
    private ConnectivityManager connectivityManager;
    private static final String TAG = "MainActivityViewModel";
    public MainActivityViewModel(@NonNull Application application) {
        super(application);
    }


    public void startInternetListener(){

         connectivityManager =
                (ConnectivityManager) getApplication().getSystemService(Context.CONNECTIVITY_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            connectivityManager.registerDefaultNetworkCallback(networkCallback);
        } else {
            NetworkRequest request = new NetworkRequest.Builder()
                    .addCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET).build();
            connectivityManager.registerNetworkCallback(request, networkCallback);
        }
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        hasInternetLiveData.postValue(activeNetworkInfo != null && activeNetworkInfo.isConnected());

    }


    public void stopInternetListener(){
        connectivityManager.unregisterNetworkCallback(networkCallback);
    }

    public final ConnectivityManager.NetworkCallback networkCallback = new ConnectivityManager.NetworkCallback() {
        @Override
        public void onAvailable(Network network) {
            Log.d(TAG, "onAvailable: Has Internet");
            hasInternetLiveData.postValue(true);
        }

        @Override
        public void onLost(Network network) {
            Log.d(TAG, "onAvailable: Has No Internet");

            hasInternetLiveData.postValue(false);
        }
    };

    public LiveData<Boolean> getHasInternetLiveData() {
        return hasInternetLiveData;
    }
}
