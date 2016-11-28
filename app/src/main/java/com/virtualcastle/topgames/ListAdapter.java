package com.virtualcastle.topgames;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import static com.virtualcastle.topgames.ListAdapter.myClickListener;

/**
 * Adapter to display recycler view.
 */

public class ListAdapter extends RecyclerView.Adapter<ViewHolder>{

    public static MyClickListener myClickListener;

    //TODO:FALTA LLENAR DATA DINAMICA, CREAR SINGLETON Y LLAMAR AL SERVIDOR
    //DATA
    private static final int LENGTH = 18;
    private final String[] mPlaces;
    private final String[] mPlaceDesc;
    private final Drawable[] mPlaceAvators;

    //INIT DATA
    public ListAdapter(Context context) {
        Resources resources = context.getResources();
        mPlaces = resources.getStringArray(R.array.places);
        mPlaceDesc = resources.getStringArray(R.array.place_desc);
        TypedArray a = resources.obtainTypedArray(R.array.place_avator);
        mPlaceAvators = new Drawable[a.length()];
        for (int i = 0; i < mPlaceAvators.length; i++) {
            mPlaceAvators[i] = a.getDrawable(i);
        }
        a.recycle();
    }

    //CREATE HOLDER
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()), parent);
    }
    //INIT HOLDER
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.avator.setImageDrawable(mPlaceAvators[position % mPlaceAvators.length]);
        holder.name.setText(mPlaces[position % mPlaces.length]);
        holder.description.setText(mPlaceDesc[position % mPlaceDesc.length]);
    }

    @Override
    public int getItemCount() {
        return LENGTH;
    }

    public interface MyClickListener {
        void onItemClick(int position, View v);
    }

    public void setOnItemClickListener(MyClickListener myClickListener) {
        ListAdapter.myClickListener = myClickListener;
    }

}

class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
    public ImageView avator;
    public TextView name;
    public TextView description;
    public ViewHolder(LayoutInflater inflater, ViewGroup parent) {
        super(inflater.inflate(R.layout.item_list, parent, false));
        avator = (ImageView) itemView.findViewById(R.id.list_avatar);
        name = (TextView) itemView.findViewById(R.id.list_title);
        description = (TextView) itemView.findViewById(R.id.list_desc);
    }
    @Override
    public void onClick(View v) {
        myClickListener.onItemClick(getAdapterPosition(), v);
    }
}

