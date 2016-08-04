package com.hqyj.dev.smarthousesarduino.tools;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Interpolator;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by jiyangkang on 2016/6/13 0013.
 */
public class CodeTools {

    private final String TAG = CodeTools.class.getSimpleName();

    private final String APPID = "wxb8c8c2f6c423dd0f";
    private final String APPSECRET = "4e53219956e1460f31b25a1e1d780ba0";
    private final String ADDRESSTOKEN = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid="
            + APPID + "&secret=" + APPSECRET;
    private final String ADDRESSTICKET = "https://api.weixin.qq.com/cgi-bin/qrcode/create?access_token=";
    private final String ADDRESSIMGE = "https://mp.weixin.qq.com/cgi-bin/showqrcode?ticket=";

    private String token;
    private String ticket;

    private int retray;

    private Drawable bitmap = null;

    private int id;
    private CodeTokenThread codeTokenThread;
    private GetTicketThread getTicketThread;
    private GetCodeImage getCodeImage;


    public CodeTools(int id){
        this.id = id;
        codeTokenThread = new CodeTokenThread();
        getTicketThread = new GetTicketThread();
        getCodeImage = new GetCodeImage();

        codeTokenThread.start();
    }


    public void setId(int id) {
        this.id = id;
    }

    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case 1:
                    JSONObject jsonObject = new JSONObject();
                    jsonObject = (JSONObject) msg.obj;
                    try {
                        if (jsonObject.getBoolean("isGet")){
                            token = jsonObject.getString("access_token");
                            Log.d(TAG,  "TOKEN"+token);
                            getTicketThread.start();
                        } else {
                            codeTokenThread = new CodeTokenThread();
                            codeTokenThread.start();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    break;
                case 2:
                    JSONObject jsTicket = new JSONObject();
                    jsTicket = (JSONObject) msg.obj;

                    try {
                        if (jsTicket.getBoolean("isGet")){
                            ticket = jsTicket.getString("ticket");
                            Log.d(TAG, "TICKET" + ticket);
                            getCodeImage.start();
                        } else {
                            getTicketThread = new GetTicketThread();
                            getTicketThread.start();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    break;
                case 3:
                    Drawable drawable = (Drawable) msg.obj;
                    if (onBitmapCreate != null){
                        onBitmapCreate.onBitmapCreate(drawable);
                    } else {
                        getCodeImage = new GetCodeImage();
                        getCodeImage.start();
                    }
                    break;
                case 4:
                    break;
            }
        }
    };


    public void setRetray(int retray) {
        this.retray = retray;
    }

    public int getRetray() {
        return retray;
    }

    private class CodeTokenThread extends Thread{
        @Override
        public void run() {
            super.run();
            setRetray(getRetray() + 1);
            if (getRetray() != 1){
                try {
                    sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            } else if (getRetray() > 10){
                setRetray(0);
                handler.sendEmptyMessage(4);
                return;
            }


            HttpUtil.sendHttpRequest(ADDRESSTOKEN, new HttpCallbackListenner() {
                @Override
                public void onFinish(String response) {
                    Message msg = new Message();
                    msg.what = 1;

                    try {
                        JSONObject jsonObject = new JSONObject(response);
                        jsonObject.put("isGet", true);
                        msg.obj = jsonObject;
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    handler.sendMessage(msg);
                    retray = 0;
                }

                @Override
                public void onError(Exception e) {
                    Message msg = new Message();
                    msg.what = 1;
                    JSONObject jsonObject = new JSONObject();
                    try {
                        jsonObject.put("access_token", null);
                        jsonObject.put("isGet", false);
                        msg.obj = jsonObject;
                    } catch (JSONException e1) {
                        e1.printStackTrace();
                    }

                    handler.sendMessage(msg);
                }
            });
        }
    }

    private class GetTicketThread extends Thread{



        @Override
        public void run() {
            super.run();

            setRetray(getRetray() + 1);
            if (getRetray() != 1){
                try {
                    sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            } else if (getRetray() > 10){
                setRetray(0);
                handler.sendEmptyMessage(4);
                return;
            }

            String addrass = ADDRESSTICKET + token;
            JSONObject jsID = new JSONObject();
            try {
                jsID.put("scene_id", id);
            } catch (JSONException e) {
                e.printStackTrace();
            }

            JSONObject jsScene = new JSONObject();
            try {
                jsScene.put("scene", jsID);
            } catch (JSONException e) {
                e.printStackTrace();
            }

            JSONObject jsPost = new JSONObject();
            try {
                jsPost.put("action_name", "QR_LIMIT_SCENE");
                jsPost.put("action_info", jsScene);
            } catch (JSONException e) {
                e.printStackTrace();
            }

            HttpUtil.getsHttpRequest(addrass, jsPost, new HttpCallbackListenner() {
                @Override
                public void onFinish(String response) {
                    Message msg = new Message();
                    msg.what = 2;

                    try {
                        JSONObject jsonObject = new JSONObject(response);
                        jsonObject.put("isGet", true);
                        msg.obj = jsonObject;
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    handler.sendMessage(msg);
                    retray = 0;
                }

                @Override
                public void onError(Exception e) {
                    Message msg = new Message();
                    msg.what = 2;
                    JSONObject jsonObject = new JSONObject();
                    try {
                        jsonObject.put("ticket", null);
                        jsonObject.put("isGet", false);
                        msg.obj = jsonObject;
                    } catch (JSONException e1) {
                        e1.printStackTrace();
                    }
                    handler.sendMessage(msg);
                }
            });
        }
    }

    private class GetCodeImage extends Thread{
        @Override
        public void run() {
            super.run();

            setRetray(getRetray() + 1);
            if (getRetray() != 1){
                try {
                    sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            } else if (getRetray() > 10){
                setRetray(0);
                handler.sendEmptyMessage(4);
                return;
            }

            final String addressImage = ADDRESSIMGE + ticket;

            HttpURLConnection connection = null;

            int code = 0;

            try {
                URL url = new URL(addressImage);
                connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("GET");
                connection.setConnectTimeout(8000);
                connection.setReadTimeout(8000);
                connection.setDoInput(true);
                connection.setDoOutput(true);

                connection.setRequestProperty("Content-Type", "image/jpg");
                code = connection.getResponseCode();

                Drawable drawable;
                Message msg = new Message();
                msg.what = 3;
                if (code == 200){
                    drawable = Drawable.createFromStream(url.openStream(), "image.jpg");
                    msg.obj = drawable;
                    retray = 0;
                } else {
                    msg.obj = null;
                }
                handler.sendMessage(msg);

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private OnBitmapCreate onBitmapCreate;

    public void setOnBitmapCreate(OnBitmapCreate onBitmapCreate) {
        this.onBitmapCreate = onBitmapCreate;
    }

    public interface OnBitmapCreate{
        void onBitmapCreate(Drawable bitmap);
    }
}
