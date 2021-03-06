package com.virtualcastle.topgames;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

public class MainActivity extends AppCompatActivity {
    DBHelper dbHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        registerClickEvent();
        dbHelper = new DBHelper(this);
    }

    public void registerClickEvent(){
        register(R.id.main_button1);
        register(R.id.main_button2);
        register(R.id.main_button3);
    }

    private void register(int buttonResourceId){
        findViewById(buttonResourceId).setOnClickListener(buttonClickListener);
    }

    private View.OnClickListener buttonClickListener = new View.OnClickListener() {

        @Override
        public void onClick(View v){
            String target = "";
            switch (v.getId()) {
                case R.id.main_button1:
                    target = "/2014.json";
                    break;
                case R.id.main_button2:
                    target = "/2015.json";
                    break;
                case R.id.main_button3:
                    target = "/2016.json";
                    break;
            }
            if(!dbHelper.existType(target.substring(1,5))) { //sleep
                loadGamesFromSplash(getResources().getString(R.string.jsonUrl) + target);
            }
            else{
                Intent intent = new Intent(getApplicationContext(),ListActivity.class);
                intent.putExtra("type",target.substring(1,5));
                startActivity(intent);
            }
        }
    };

    public void loadGamesFromSplash(String url){
        Intent intent = new Intent(getApplicationContext(),SplashActivity.class);
        intent.putExtra("url",url);
        startActivity(intent);
    }


}
