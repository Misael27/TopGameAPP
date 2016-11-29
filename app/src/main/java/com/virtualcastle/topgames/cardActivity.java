package com.virtualcastle.topgames;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;

import java.util.List;

public class cardActivity extends AppCompatActivity {
    DBHelper dbHelper;
    public ImageLoader mImageLoader;
    NetworkImageView image;
    Game game;
    String type;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_card);
        Intent intentReceiver = null;

        dbHelper = new DBHelper(this);

        intentReceiver = getIntent();
        int idx = intentReceiver.getIntExtra("idx",-1);
        if(idx != -1){
            type = intentReceiver.getStringExtra("type");

            String url = intentReceiver.getStringExtra("url");
            mImageLoader = VolleySingleton.getInstance(getApplicationContext()).getImageLoader();
            image = (NetworkImageView)findViewById(R.id.game_card);
            mImageLoader.get(url, ImageLoader.getImageListener(image,R.drawable.logo, R.drawable.logo));
            image.setImageUrl(url, mImageLoader);
            game = dbHelper.getGameByType(type).get(idx);

            TextView title_card = (TextView)findViewById(R.id.title_card);
            TextView console = (TextView)findViewById(R.id.console);
            TextView rate = (TextView)findViewById(R.id.rate);
            TextView date = (TextView)findViewById(R.id.date);


            title_card.setText(game.getName());
            console.setText(game.getConsole());
            rate.setText(game.getRate());
            date.setText(game.getDate());


        }

    }

    public void goToList(View view){
       // Intent intent = new Intent(getApplicationContext(), ListActivity.class);
        //intent.putExtra("type", type);
        //intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        //startActivity(intent);
        finish();
    }

}
