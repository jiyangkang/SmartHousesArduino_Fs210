package com.hqyj.dev.smarthousesarduino.services;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

import com.hqyj.dev.smarthousesarduino.system.SystemConfig;
import com.hqyj.dev.smarthousesarduino.tools.DataTool;
import com.hqyj.dev.smarthousesarduino.tools.MathTools;
import com.hqyj.dev.smarthousesarduino.tools.SoapTool;
import com.hqyj.dev.smarthousesarduino.tools.StringTools;

/**
 *
 * Created by jiyangkang on 2016/6/2 0002.
 */
public class SoapService extends Service{
    private boolean threadOn = false;
    private GetThread mGetThread = null;
    private SendThread mSendThread = null;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        threadOn = false;
        try {
            //when stop service, passing the ENDTHREAD byte array
            DataTool.sendSoap.put(DataTool.ENDTHREAD);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        mSendThread.interrupt();
        mGetThread.interrupt();
        mGetThread = null;
        mSendThread = null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        threadOn = true;
        mGetThread = new GetThread();
        mSendThread = new SendThread();
        mGetThread.start();
        mSendThread.start();
        return super.onStartCommand(intent, flags, startId);
    }

    private class GetThread extends Thread {
        String strGet;
        byte[] datas;

        @Override
        public void run() {
            super.run();
            while (threadOn) {
                strGet = SoapTool.getFromServer(SystemConfig.getSystemConfig().getDevice());
                if (strGet != null) {
                    StringBuilder str = new StringBuilder();
                    str.append(strGet);
                    String st = str.substring(0,4);
                    if (st.equalsIgnoreCase("null")) {
                        try {
                            sleep(300);

                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        continue;
                    }
                    datas = MathTools.changeIntoByte(strGet);
                    try {
                        if (datas != null && threadOn) {
                            DataTool.gets.put(datas);
//                            Log.d("DATA_SOAP", "datas: sends===========");
                        } else
                            Log.d("DATA_SOAP", "erro datas");
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                try {
                    sleep(300);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private class SendThread extends Thread {
        @Override
        public void run() {
            super.run();
            while (threadOn) {
                StringBuilder stringBuilder = new StringBuilder();
                try {
                    byte[] datas = DataTool.sendSoap.take();
                    if (datas == DataTool.ENDTHREAD)
                        break;
                    String sendString = StringTools.changeIntoHexString(datas, false);
                    stringBuilder.append(sendString)
                            .append(SystemConfig.getSystemConfig().getDevice());
                    String request = SoapTool.sendToServer(stringBuilder.toString());
//                    Log.e("SOAP", "run: " + stringBuilder.toString());
                    if (request != null && request.equalsIgnoreCase("ok")) {
//                        Log.e("SOAP", "success");
                        Intent i = new Intent();
                        i.setAction(DataTool.OKWEIXIN);
                        i.putExtra(DataTool.OKWEIXIN, DataTool.OKWEIXIN);
                        SoapService.this.sendBroadcast(i);
                    } else {
                        Log.e("SOAP", "Can't Send " + request);
                        Intent i = new Intent();
                        i.setAction(DataTool.ERRORWEIXIN);
                        i.putExtra(DataTool.ERRORWEIXIN, DataTool.ERRORWEIXIN);
                        SoapService.this.sendBroadcast(i);
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

}
