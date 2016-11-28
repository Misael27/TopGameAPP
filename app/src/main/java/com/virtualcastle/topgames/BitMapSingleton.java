package com.virtualcastle.topgames;

import android.graphics.Bitmap;
import java.util.HashMap;

public class BitMapSingleton {

    private static BitMapSingleton  mInstance = null;
    private HashMap<Integer,Bitmap> BinaryImage;


    private BitMapSingleton(){
        BinaryImage = new HashMap<>();
    }

    public static BitMapSingleton getInstance(){
        if(mInstance == null){
            mInstance = new BitMapSingleton();
        }
        return mInstance;
    }

    public Bitmap getBinaryImage(int position){
        if(position < BinaryImage.size()) {
            return BinaryImage.get(position);
        }
        else{
            return null;
        }
    }

    public void addBinaryImage(int posJson, Bitmap image){
        BinaryImage.put(posJson,image);
    }


    public int size(){
        return BinaryImage.size();
    }

    public void destroy(){
        mInstance = null;
    }

    public void reset(){
        BinaryImage.clear();
    }
}