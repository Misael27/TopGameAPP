package com.virtualcastle.topgames;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;

import java.util.List;


/**
 * Adapter to display recycler view.
 */

public class ListAdapter extends RecyclerView.Adapter<ViewHolder>{

    static MyClickListener myClickListener;
    public ImageLoader mImageLoader;
    //DATA
    private static final int LENGTH = 18;
    private List<Game> games;
    private ImageLoader imageLoader;
    private Context context;
    //INIT DATA
    ListAdapter(Context context, List<Game> games) {
        this.games = games;
        this.context = context;
    }

    //CREATE HOLDER
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder( LayoutInflater.from(parent.getContext()), parent);
    }
    //INIT HOLDER
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        mImageLoader = VolleySingleton.getInstance(context.getApplicationContext()).getImageLoader();
        final String url = "http://afagenciadigital.com/jsons/"+games.get(position).getType()+"/"+games.get(position).getPosition()+".jpg";
        mImageLoader.get(url, ImageLoader.getImageListener(holder.image,R.drawable.logo, R.drawable.logo));
        holder.image.setImageUrl(url, mImageLoader);
        holder.name.setText(games.get(position%games.size()).getName());
        holder.position.setText(String.valueOf(games.get(position).getPosition()));
    }

    @Override
    public int getItemCount() {
        return games.size();
    }

    interface MyClickListener {
        void onItemClick(int position, View v);
    }

    void setOnItemClickListener(MyClickListener myClickListener2) {
        ListAdapter.myClickListener = myClickListener2;
    }

}

class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
    NetworkImageView image;
    public TextView name;
    TextView position;

    ViewHolder(LayoutInflater inflater, ViewGroup parent) {
        super(inflater.inflate(R.layout.item_list, parent, false));
        image = (NetworkImageView) itemView.findViewById(R.id.list_avatar);
        name = (TextView) itemView.findViewById(R.id.list_title);
        position = (TextView) itemView.findViewById(R.id.list_desc);
        itemView.setOnClickListener(this);
    }
    @Override
    public void onClick(View v) {
        ListAdapter.myClickListener.onItemClick(getAdapterPosition(), v);
    }
}

