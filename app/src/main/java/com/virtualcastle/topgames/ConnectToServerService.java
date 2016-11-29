package com.virtualcastle.topgames;

import android.app.IntentService;
import android.content.Intent;
import android.content.Context;
import android.util.Log;
import android.view.Gravity;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

public class ConnectToServerService extends IntentService {

    private DBHelper db;

    public int cont;

    public int total;

    private static final String ACTION_GET_JSON = "GET_JSON";

    private static final String EXTRA_URL = "URL";

    public ConnectToServerService() {
        super("ConnectToServerService");
    }

    public static void startActionGetJson(Context context, String url) {
        Intent intent = new Intent(context, ConnectToServerService.class);
        intent.setAction(ACTION_GET_JSON);
        intent.putExtra(EXTRA_URL, url);
        context.startService(intent);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        if (intent != null) {
            db = new DBHelper(this);
            final String action = intent.getAction();
            if (ACTION_GET_JSON.equals(action)) {
                final String url = intent.getStringExtra(EXTRA_URL);
                handleActionGetJson(url);
            }
        }
    }

    private void handleActionGetJson(String url) {
        getJsonFromServer(url,Request.Method.GET);
    }

    void getJsonFromServer(final String url,int method){
        VolleySingleton.GsonRequest<JsonObject> gsonRequest = new VolleySingleton.GsonRequest<>(
                method,
                url,
                null,
                JsonObject.class,
                null,
                new Response.Listener<JsonObject>() {
                    @Override
                    public void onResponse(JsonObject response) {
                        HandleResponse(url,response);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("ERROR", "Error Volley:" + error.getMessage());
                        Toast toast1 = Toast.makeText(getApplicationContext(),"Error al enviar solicitud", Toast.LENGTH_SHORT);
                        toast1.setGravity(Gravity.CENTER|Gravity.CENTER_HORIZONTAL,0,0);
                        toast1.show();
                    }
                }
        );
        VolleySingleton.getInstance(getApplicationContext()).addToRequestQueue(gsonRequest);
    }

    public void HandleResponse(String url,JsonObject response){
        String jsoArrayName = url.substring(url.lastIndexOf("/")+1,url.lastIndexOf("/")+5);
        JsonArray jsonArray = response.getAsJsonArray(jsoArrayName);
        storeJsonInDB(jsonArray, jsoArrayName);
        returnToSplashActivity();
        /*
        String urlImage = url.substring(0,url.lastIndexOf("/")+1);
        urlImage = urlImage + jsoArrayName + "/";
        cont = 0;
        total = jsonArray.size();

        for (JsonElement jsonE : jsonArray) {
            try {
                JsonObject json = jsonE.getAsJsonObject();
                //StoreImage(json.get("position").getAsInt(),urlImage);
            }
            catch (Exception e){
                e.printStackTrace();
            }
        }*/
    }

    public void storeJsonInDB(JsonArray jsonArray, String jsoArrayName){
        db.addGameFromJson(jsonArray,jsoArrayName);
    }
/*
    public void StoreImage(final int posAct, String url){
        url = url + String.valueOf(posAct) + ".jpg";
        ImageRequest ir = new ImageRequest(url, new Response.Listener<Bitmap>() {
            @Override
            public void onResponse(Bitmap response) {
                BitMapSingleton.getInstance().addBinaryImage(posAct, response);
                if (++cont == total) {
                    returnToSplashActivity();
                }
            }

        }, 0, 0, null, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                if (++cont == total) {
                    returnToSplashActivity();
                }
                Log.d("ERROR", "Error Volley:" + error.getMessage());
              //  Toast toast1 = Toast.makeText(getApplicationContext(),"Error al enviar solicitud", Toast.LENGTH_SHORT);
                //toast1.setGravity(Gravity.CENTER|Gravity.CENTER_HORIZONTAL,0,0);
                //toast1.show();
            }
        });
        VolleySingleton.getInstance(getApplicationContext()).addToRequestQueue(ir);
    }
*/
    public void returnToSplashActivity(){ //retorna al splash activity que ya se realizó la carga del json en base de datos
        Intent Return = new Intent();
        Return.setAction("loadIsReady");
        sendBroadcast(Return);
    }

}
