package com.hqyj.dev.smarthousesarduino.tools;

import android.util.Log;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by jiyangkang on 2016/6/13 0013.
 */
public class HttpUtil {

    public static void sendHttpRequest(final String addrass, final HttpCallbackListenner listenner){
        new Thread(new Runnable() {
            @Override
            public void run() {
                HttpURLConnection connection = null;

                try {
                    URL url = new URL(addrass);
                    connection = (HttpURLConnection) url.openConnection();
                    connection.setRequestMethod("GET");
                    connection.setConnectTimeout(8000);
                    connection.setReadTimeout(8000);
                    connection.setDoInput(true);
                    connection.setDoOutput(true);

                    InputStream in = connection.getInputStream();
                    BufferedReader reader = new BufferedReader(new InputStreamReader(in));
                    StringBuilder response = new StringBuilder();
                    String line;
                    while ((line = reader.readLine()) != null) {
                        response.append(line);
                    }

                    if (listenner != null) {
                        listenner.onFinish(response.toString());
                    }
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                    if (listenner != null) {
                        listenner.onError(e);
                    }
                } finally {
                    if (connection != null) {
                        connection.disconnect();
                    }
                }

            }

        }).start();
    }

    public static void getsHttpRequest(final String address, final JSONObject js, final HttpCallbackListenner listener) {
        new Thread(new Runnable() {

            @Override
            public void run() {
                HttpURLConnection connection = null;
                String str = String.valueOf(js);
//                Log.d("output--json", str);
//                Log.d("output--address", address);
                int code;

                try {
                    URL url = new URL(address);
                    connection = (HttpURLConnection) url.openConnection();
                    connection.setRequestMethod("POST");
                    connection.setConnectTimeout(8000);
                    connection.setReadTimeout(8000);
                    connection.setDoInput(true);
                    connection.setDoOutput(true);
                    connection.setRequestProperty("ser-Agent", "Fiddler");
                    connection.setRequestProperty("Conten-Type", "application/json");

                    OutputStream out= connection.getOutputStream();
//                    Log.d("output--json", str);
                    out.write(str.getBytes());
                    out.close();

                    code = connection.getResponseCode();
                    if (code == 200) {

                        InputStream in = connection.getInputStream();
                        BufferedReader reader = new BufferedReader(new InputStreamReader(in));
                        StringBuilder response = new StringBuilder();
                        String line;
                        while ((line = reader.readLine()) != null) {
                            response.append(line);
                        }

                        if (listener != null) {
                            listener.onFinish(response.toString());
                        }
                    }

                } catch (IOException e) {
                    listener.onError(e);
                    e.printStackTrace();
                }
            }
        }).start();
    }
}
