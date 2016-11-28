package com.virtualcastle.topgames;

import android.provider.BaseColumns;

public class Game {
    int position;
    String name;
    String console;
    float rate;
    String date;
    String type;
    public static abstract class GameTable implements BaseColumns {
        public static final String TABLE_NAME = "game";
        public static final String COLUMN_POSITION = "position";
        public static final String COLUMN_NAME = "name";
        public static final String COLUMN_CONSOLE = "console";
        public static final String COLUMN_RATE = "rate";
        public static final String COLUMN_DATE = "date";
        public static final String COLUMN_TYPE = "type";
    }

    public Game() {
    }


    public String getName() {
        return name;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setConsole(String console) {
        this.console = console;
    }

    public void setRate(float rate) {
        this.rate = rate;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setType(String type){
        this.type = type;
    }

    public String getConsole() {
        return console;
    }

    public float getRate() {
        return rate;
    }

    public String getDate() {
        return date;
    }

    public int getPosition() {
        return position;
    }

    public String getType(){
        return type;
    }
}
