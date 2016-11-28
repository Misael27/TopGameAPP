package com.virtualcastle.topgames;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.ActivityInfo;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Window;

import java.util.Timer;
import java.util.TimerTask;



public class SplashActivity extends AppCompatActivity {

    private BroadcastReceiver bReceiverLoad;

    // Set the duration of the splash screen
    private static final long SPLASH_SCREEN_DELAY = 3000;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Set portrait orientation
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        // Hide title bar
        requestWindowFeature(Window.FEATURE_NO_TITLE);

        setContentView(R.layout.activity_splash);

        bReceiverLoad = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                callListActivity();
            }
        };
        IntentFilter intentFilterLoad = new IntentFilter();
        intentFilterLoad.addAction("loadIsReady");
        registerReceiver(bReceiverLoad, intentFilterLoad);

      //  BitMapSingleton.getInstance().reset(); //TODO: resetear cuando se almacenen las imagenes
        Intent intentReceiver = null;

        intentReceiver = getIntent();
        String url = intentReceiver.getStringExtra("url");
        if(url!=null){ //viene de otra actividad
            ConnectToServerService.startActionGetJson(SplashActivity.this,url);
        }
        else{ //inicio de la app
            TimerTask task = new TimerTask() {
                @Override
                public void run() {
                    callMainActivity();
                }
            };
            Timer timer = new Timer();
            timer.schedule(task, SPLASH_SCREEN_DELAY);
        }


    }

    public void callMainActivity(){
        Intent mainIntent = new Intent().setClass(SplashActivity.this, MainActivity.class);
        startActivity(mainIntent);
        finish();
    }

    public void callListActivity(){
        Intent mainIntent = new Intent().setClass(SplashActivity.this, MainActivity.class);
        //mainIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        getApplicationContext().startActivity(mainIntent);
        finish();
    }

    public boolean isOnline() { //verifica si el dispositivo esta conectado a internet
        ConnectivityManager cm = (ConnectivityManager) this.getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo netInfo = cm.getActiveNetworkInfo();

        if(netInfo != null && netInfo.isConnectedOrConnecting()) {
            return true;
        }
        return false;
    }

}


