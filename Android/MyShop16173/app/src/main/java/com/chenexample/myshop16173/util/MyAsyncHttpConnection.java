package com.chenexample.myshop16173.util;

import android.os.AsyncTask;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;


public class MyAsyncHttpConnection extends AsyncTask<URL, Void, JSONObject> {


    @Override
    protected JSONObject doInBackground(URL... params) {
        System.out.println("------------>11111111");
        JSONObject result = null;
        HttpURLConnection conn = null;
        String date = null;
        try {
            conn = (HttpURLConnection) params[0].openConnection();
            conn.setConnectTimeout(3000);
            conn.setDoInput(true);
            conn.setDoOutput(true);
            conn.setRequestMethod("POST");
            OutputStream out = conn.getOutputStream();
//            out.write(data.getBytes());
            out.flush();
            out.close();
            if (conn.getResponseCode() == HttpURLConnection.HTTP_OK) {
                InputStream in = conn.getInputStream();
                BufferedReader br = new BufferedReader(new InputStreamReader(in));
                String str = br.readLine();
                result = new JSONObject(str);
                in.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        } finally {
            conn.disconnect();
        }
        return result;
    }

    @Override
    protected void onPostExecute(JSONObject jsonObject) {
        super.onPostExecute(jsonObject);
        //从这里开始
    }
}