package com.example.pinayflix.ui.activities;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.example.pinayflix.R;
import com.example.pinayflix.ui.dialogs.NoInternetDialog;
import com.example.pinayflix.viewmodel.MainActivityViewModel;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class MainActivity extends AppCompatActivity {
    private String TAG = "PinayFlixMainActivity";
    private NoInternetDialog noInternetDialog;
    private MainActivityViewModel mainActivityViewModel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mainActivityViewModel = new ViewModelProvider(this).get(MainActivityViewModel.class);
        getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        setContentView(R.layout.activity_main);

        mainActivityViewModel.getHasInternetLiveData().observe(this,hasInternet->{
            if(!hasInternet){
                if (noInternetDialog != null) return;
                noInternetDialog = new NoInternetDialog();
                noInternetDialog.setCancelable(false);
                noInternetDialog.show(getSupportFragmentManager(),"No Internet");
            }else{
                if (noInternetDialog == null) return;
                noInternetDialog.dismiss();
                noInternetDialog = null;

            }



        });


    }

    @Override
    protected void onResume() {
        super.onResume();
        mainActivityViewModel.startInternetListener();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mainActivityViewModel.stopInternetListener();
    }
}