package com.multipz.kc.util;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Base64;
import android.util.Log;

import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by Admin on 23-12-2017.
 */

public class CallConfigDataApi extends AsyncTask<String, Void, String>{

    Shared shared;
    String param = "{\"action\":\"getComponents\"}";

    public CallConfigDataApi(Shared shared){
        this.shared = shared;
    }

    @Override
    protected String doInBackground(String... strings) {
        OkHttpClient client = new OkHttpClient();

        FormBody.Builder formBuilder = new FormBody.Builder()
                .add("json", param);
        RequestBody formBody = formBuilder.build();
        Request request = new Request.Builder().url(Config.MAIN_API).post(formBody).build();
        try {
            Response response = client.newCall(request).execute();
            return response.body().string();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        try {
            JSONObject jsonObject = new JSONObject(s);
            Log.e("Responce", jsonObject.toString());
            int success = jsonObject.getInt("status");

            if (success == 1) {
                JSONObject jsonObject1 = jsonObject.getJSONObject("data");
                shared.putString(Config.Material, jsonObject1.getString("material"));
                shared.putString(Config.VehicleType, jsonObject1.getString("vehicle_type"));
                shared.putString(Config.Project, jsonObject1.getString("project"));
                shared.putString(Config.Vehicle, jsonObject1.getString("vehicle"));
                shared.putString(Config.Bank, jsonObject1.getString("bank"));
                shared.putString(Config.company, jsonObject1.getString("company"));
                shared.putString(Config.Work_type, jsonObject1.getString("work_type"));
                shared.putString(Config.Staff, jsonObject1.getString("staff"));
                shared.putString(Config.AllStaff,jsonObject1.getString("allStaff"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
