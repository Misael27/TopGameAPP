package com.virtualcastle.topgames;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.View;
import android.widget.Toast;

import java.util.List;

public class ListActivity extends AppCompatActivity {
    DBHelper dbHelper;
    private List<Game> games;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        dbHelper = new DBHelper(this);

        Intent intentReceiver = null;
        intentReceiver = getIntent();
        String type = intentReceiver.getStringExtra("type");
        games = dbHelper.getGameByType(type);

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        ListAdapter adapter = new ListAdapter(getApplicationContext(),games);
        recyclerView.setAdapter(adapter);
        recyclerView.setHasFixedSize(false);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 1);
        recyclerView.setLayoutManager(gridLayoutManager);

        adapter.setOnItemClickListener(new ListAdapter
                .MyClickListener() {
            @Override
            public void onItemClick(int position, View v) {
                changeActivity(position);
            }
        });
    }
    public void changeActivity(int idx){
        Intent intent = new Intent(getApplicationContext(),cardActivity.class);
        String url = getResources().getString(R.string.jsonUrl) + "/" + games.get(idx).getType() + "/" + String.valueOf(games.get(idx).getPosition()) + ".jpg";
        intent.putExtra("idx",idx);
        intent.putExtra("url",url);
        intent.putExtra("type",games.get(idx).getType());
        startActivity(intent);
    }

}
