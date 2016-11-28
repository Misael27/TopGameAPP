package com.virtualcastle.topgames;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.util.ArrayList;
import java.util.List;

public class DBHelper extends SQLiteOpenHelper {
    private SQLiteDatabase db;

    private static final int DATABASE_VERSION = 1;

    private static final String DATABASE_NAME = "topgames.db";

    private static final String CREATE_TABLE_GAME =
            "CREATE TABLE " + Game.GameTable.TABLE_NAME + " (" +
                    Game.GameTable._ID + " INTEGER PRIMARY KEY," +
                    Game.GameTable.COLUMN_POSITION + " TEXT," +
                    Game.GameTable.COLUMN_NAME + " TEXT," +
                    Game.GameTable.COLUMN_CONSOLE + " TEXT," +
                    Game.GameTable.COLUMN_RATE + " TEXT," +
                    Game.GameTable.COLUMN_DATE + " TEXT," +
                    Game.GameTable.COLUMN_TYPE + " TEXT" +
                    ");";

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_GAME);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }


    public List<Game> getAllGames(){
        db = getReadableDatabase();

        List<Game> games = new ArrayList<>();
        String[] project = {
                Game.GameTable.COLUMN_POSITION,
                Game.GameTable.COLUMN_NAME,
                Game.GameTable.COLUMN_CONSOLE,
                Game.GameTable.COLUMN_RATE,
                Game.GameTable.COLUMN_DATE,
                Game.GameTable.COLUMN_TYPE
        };

        Cursor cursor;
        cursor = db.query(Game.GameTable.TABLE_NAME,
                project,
                null,
                null,
                null,
                null,
                null
        );
        if (cursor.moveToFirst()) {
            do {
                Game game = new Game();
                game.setPosition(Integer.parseInt(cursor.getString(cursor.getColumnIndex(Game.GameTable.COLUMN_POSITION))));
                game.setName(cursor.getString(cursor.getColumnIndex(Game.GameTable.COLUMN_NAME)));
                game.setConsole(cursor.getString(cursor.getColumnIndex(Game.GameTable.COLUMN_NAME)));
                game.setRate(Float.parseFloat(cursor.getString(cursor.getColumnIndex(Game.GameTable.COLUMN_NAME))));
                game.setDate(cursor.getString(cursor.getColumnIndex(Game.GameTable.COLUMN_DATE)));
                game.setType(cursor.getString(cursor.getColumnIndex(Game.GameTable.COLUMN_TYPE)));
                games.add(game);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();

        return games;
    }

    public void addGameFromJson(JsonArray jsonA, String type){
        try{
            for (JsonElement jsonE : jsonA){
                try {
                    JsonObject json = jsonE.getAsJsonObject();
                    Game game = new Game();
                    game.setPosition(json.get("position").getAsInt());
                    game.setName(json.get("name").getAsString());
                    game.setConsole(json.get("console").getAsString());
                    game.setRate(json.get("rate").getAsFloat());
                    game.setDate(json.get("date").getAsString());
                    game.setType(type);
                    addGame(game);
                }
                catch(Exception e){
                    e.printStackTrace();
                }
            }
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }

    public void addGame(Game game){
        db = getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(Game.GameTable.COLUMN_POSITION, String.valueOf(game.getPosition()));
        values.put(Game.GameTable.COLUMN_NAME, game.getName());
        values.put(Game.GameTable.COLUMN_CONSOLE, game.getConsole());
        values.put(Game.GameTable.COLUMN_RATE, String.valueOf(game.getRate()));
        values.put(Game.GameTable.COLUMN_DATE, game.getDate());
        values.put(Game.GameTable.COLUMN_TYPE, game.getType());

        db.insert(Game.GameTable.TABLE_NAME, null, values);
        db.close();
    }

    public void deleteAllPoll(){
        db = getWritableDatabase();
        db.delete(Game.GameTable.TABLE_NAME, null, null);
        db.close();
    }
}