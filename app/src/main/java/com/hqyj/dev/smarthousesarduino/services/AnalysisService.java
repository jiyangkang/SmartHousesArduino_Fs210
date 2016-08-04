package com.hqyj.dev.smarthousesarduino.services;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

import com.hqyj.dev.smarthousesarduino.modules.Module;
import com.hqyj.dev.smarthousesarduino.modules.ModuleCtrlList;
import com.hqyj.dev.smarthousesarduino.modules.ModuleVoirList;
import com.hqyj.dev.smarthousesarduino.tools.DataTool;
import com.hqyj.dev.smarthousesarduino.tools.MathTools;
import com.hqyj.dev.smarthousesarduino.tools.StringTools;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by jiyangkang on 2016/6/2 0002.
 */
public class AnalysisService extends Service {

    private final String TAG = AnalysisService.class.getSimpleName();
    private boolean threadOn = false;
    private AnalysisThread analysisThread;


    private byte[] bufferPut = new byte[2048];
    private byte status = DataTool.HEAD;
    private int i_record;
    private int j = 0;
    private ArrayList<Byte> bytes = new ArrayList<>();


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
    public int onStartCommand(Intent intent, int flags, int startId) {
        threadOn = true;
        analysisThread = new AnalysisThread();
        analysisThread.start();
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    private int z = 0;
    private ArrayList<Byte> arrayList = new ArrayList<>();
    private class AnalysisThread extends Thread {
        @Override
        public void run() {
            super.run();
            while (threadOn) {
                try {
                    byte[] datas = DataTool.gets.take();

                    analysisiData(datas);

                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }



    /**
     * seperate Datas which getFrom differents froms
     * @param buffer datas which geted
     */
    private void analysisiData(byte[] buffer) {

        int size = buffer.length;

        Byte[] bufferB = new Byte[size];

        for (int i = 0; i < size; i++) {
            bufferB[i] = buffer[i];
        }
        Collections.addAll(bytes, bufferB);

        for (int i = 0; i < bytes.size(); i++) {
            switch (status) {
                case DataTool.HEAD:
                    if (bytes.get(i) != DataTool.HEAD_RECEIVE) {
                        continue;
                    } else {
                        bufferPut[DataTool.HEAD] = bytes.get(i);
                        status = DataTool.LENGTH;
                        i_record = i;
                    }
                    break;
                case DataTool.LENGTH:
                    bufferPut[DataTool.LENGTH] = bytes.get(i);
                    status = DataTool.OFFSET;
                    break;
                case DataTool.OFFSET:
                    bufferPut[DataTool.OFFSET] = bytes.get(i);
                    status = DataTool.DATATYPE;
                    break;
                case DataTool.DATATYPE:
                    bufferPut[DataTool.DATATYPE] = bytes.get(i);
                    status = DataTool.NETTYPE;
                    break;
                case DataTool.NETTYPE:
                    bufferPut[DataTool.NETTYPE] = bytes.get(i);
                    status = DataTool.DEVICEADDR_H;
                    break;
                case DataTool.DEVICEADDR_H:
                    bufferPut[DataTool.DEVICEADDR_H] = bytes.get(i);
                    status = DataTool.DEVICEADDR_L;
                    break;
                case DataTool.DEVICEADDR_L:
                    bufferPut[DataTool.DEVICEADDR_L] = bytes.get(i);
                    status = DataTool.DEVICETYPE;
                    break;
                case DataTool.DEVICETYPE:
                    bufferPut[DataTool.DEVICETYPE] = bytes.get(i);
                    status = DataTool.DATA;
                    break;
                case DataTool.DATA:

                    bufferPut[DataTool.DATA + j] = bytes.get(i);
                    j++;
                    if (j == bufferPut[DataTool.LENGTH]) {
                        status = DataTool.MATA;
                    }

                    if (j > 128){
                        j = 0;
                        i = i_record;
                        status = DataTool.HEAD;
                    }
                    break;
                case DataTool.MATA:
                    bufferPut[DataTool.MATA + j - 1] = bytes.get(i);

                    byte[] send = new byte[bufferPut[DataTool.LENGTH] + bufferPut[DataTool.OFFSET] + 1];
                    System.arraycopy(bufferPut, 0, send, 0, send.length);

                    j = 0;
                    if (MathTools.checkMata(send)) {
                        //Log.d(TAG, String.format("send %s", StringTools.changeIntoHexString(send, true)));
                        int id = ((bufferPut[DataTool.DEVICEADDR_H] << 16) & 0x00ff0000)|( (bufferPut[DataTool.DEVICEADDR_L] << 8) & 0x00ff00) | (bufferPut[DataTool.DEVICETYPE] & 0x0ff);
                        Module module;
                        if ((module = ModuleVoirList.getModuleVoirList().getModuleHashMap().get(id)) != null ){
                            module.setValue(send);
                        } else if ((module = ModuleCtrlList.getModuleCtrlList().getModuleHashMap().get(id)) != null){
                            module.setValue(send);
//                            Log.d(TAG, String.format("send %s", StringTools.changeIntoHexString(send, true)));
                        } else {
                            Log.d(TAG, "analysisiData: ++"+ StringTools.changeIntoHexString(send,true));
//                            Log.d(TAG, "analysisiData: ++"+ StringTools.changeIntoHexString(send,true));
                        }

                        for (int z = 0; z <= i; z++) {
                            bytes.remove(0);
                        }
                        i = -1;
                    } else {
                        i = i_record;
                    }
                    status = DataTool.HEAD;
                    break;

                default:
                    break;
            }
        }
    }


}
