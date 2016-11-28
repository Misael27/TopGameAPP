package com.virtualcastle.topgames;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.View;
import android.widget.Toast;

public class ListActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        ListAdapter adapter = new ListAdapter(recyclerView.getContext());
        recyclerView.setAdapter(adapter);
        recyclerView.setHasFixedSize(true);
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
        mostrarToast(getApplicationContext(),"Press item: "+String.valueOf(idx));
    }

    public void mostrarToast(Context context, String msg){
        Toast toast;
        toast = Toast.makeText(context,msg, Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.CENTER|Gravity.CENTER_HORIZONTAL,0,0);
        toast.show();
    }
}
