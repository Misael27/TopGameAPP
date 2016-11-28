package com.virtualcastle.topgames;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;

import java.util.List;


/**
 * Adapter to display recycler view.
 */

public class ListAdapter extends RecyclerView.Adapter<ViewHolder>{

    static MyClickListener myClickListener;
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
        imageLoader = VolleySingleton.getInstance(context).getImageLoader();
        //imageLoader.get(games.getImageUrl(), ImageLoader.getImageListener(holder.image, R.mipmap.ic_launcher, android.R.drawable.ic_dialog_alert));

      //  holder.image.setImageUrl(superHero.getImageUrl(), imageLoader);


        // / holder.image.setImageBitmap(BitMapSingleton.getInstance().getBinaryImage(position));
        holder.name.setText(games.get(position%games.size()).getName());
        holder.rate.setText(games.get(position%games.size()).getConsole());
    }

    @Override
    public int getItemCount() {
        return BitMapSingleton.getInstance().size();
    }

    interface MyClickListener {
        void onItemClick(int position, View v);
    }

    void setOnItemClickListener(MyClickListener myClickListener2) {
        ListAdapter.myClickListener = myClickListener2;
    }

}

class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
    ImageView image;
    public TextView name;
    TextView rate;
    ViewHolder(LayoutInflater inflater, ViewGroup parent) {
        super(inflater.inflate(R.layout.item_list, parent, false));
        image = (ImageView) itemView.findViewById(R.id.list_avatar);
        name = (TextView) itemView.findViewById(R.id.list_title);
        rate = (TextView) itemView.findViewById(R.id.list_desc);
        itemView.setOnClickListener(this);
    }
    @Override
    public void onClick(View v) {
        ListAdapter.myClickListener.onItemClick(getAdapterPosition(), v);
    }
}

