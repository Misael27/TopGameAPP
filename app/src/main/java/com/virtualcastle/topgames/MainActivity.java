package com.virtualcastle.topgames;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        registerClickEvent();
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
            switch (v.getId()) {
                case R.id.main_button1:
                    // TODO Search game of 2014

                    break;
                case R.id.main_button2:
                    // TODO Search game of 2015

                    break;
                case R.id.main_button3:
                    // TODO Search game of 2016

                    break;
            }
            Intent ListActivity = new Intent(getApplicationContext(), ListActivity.class);
            startActivity(ListActivity);
            overridePendingTransition(R.anim.left_in, R.anim.left_out);
        }
    };


}
