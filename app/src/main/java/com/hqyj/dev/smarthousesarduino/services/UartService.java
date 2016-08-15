package com.hqyj.dev.smarthousesarduino.services;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

import com.hqyj.dev.smarthousesarduino.modules.ModuleCtrlList;
import com.hqyj.dev.smarthousesarduino.system.SystemConfig;
import com.hqyj.dev.smarthousesarduino.tools.DataTool;
import com.hqyj.dev.smarthousesarduino.tools.StringTools;
import com.hqyj.dev.smarthousesarduino.tools.UartTools;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by jiyangkang on 2016/6/2 0002.
 */
public class UartService extends Service {

    private final String TAG = UartTools.class.getSimpleName();

    private boolean threadOn = false;
    private UartTools uartTools;
    private OutputStream outputStream;
    private InputStream inputStream;
    private String path = SystemConfig.getSystemConfig().getUartPort();
    private int baudRate = SystemConfig.getSystemConfig().getBaudRate();
    //    private String path = "/dev/ttySAC0";
    //    private int baudRate = 115200;
    private WriteUart writeUart = null;
    private ReadUart readUart = null;
    private SendToSoapThread sendToSoapThread = null;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }


    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        try {

            uartTools = new UartTools(new File(path), baudRate);
            outputStream = uartTools.getFileOutputStream();
            inputStream = uartTools.getFileInputStream();
            threadOn = true;
            if (readUart == null) {
                readUart = new ReadUart();
            }
            if (writeUart == null) {
                writeUart = new WriteUart();
            }
            if (sendToSoapThread == null) {
                sendToSoapThread = new SendToSoapThread();
            }
            readUart.start();
            writeUart.start();
            sendToSoapThread.start();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return super.onStartCommand(intent, flags, startId);
    }

    private class WriteUart extends Thread {
        @Override
        public void run() {
            super.run();
            while (threadOn && !isInterrupted()) {
                try {
                    byte[] datas = DataTool.sends.take();
                    if (datas == DataTool.ENDTHREAD) {
                        break;
                    }
                    StringBuilder s = new StringBuilder();
                    for (byte b : datas) {
                        s.append((char) b);
                    }
                    Log.d(TAG, "sendCmds: " + s.toString());
                    if (s.toString().contains("ERROR")) {
                        Intent i = new Intent();
                        i.setAction(DataTool.ERRORINPUT);
                        int id = (datas[DataTool.DEVICEADDR_H] << 16 & 0x00ff0000) |
                                (datas[DataTool.DEVICEADDR_L] << 8 & 0x00ff00) |
                                (datas[DataTool.DEVICETYPE] & 0x00ff);
                        i.putExtra(DataTool.ERRORINPUT,
                                ModuleCtrlList.getModuleCtrlList().getModuleHashMap().get(id).getName());
                        UartService.this.sendBroadcast(i);
                        continue;
                    }
                    sendCmds(datas);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }
        }
    }

    public boolean sendCmds(byte[] datas) {
        boolean result = true;
        try {
            if (outputStream != null) {
                outputStream.write(datas);
//                Log.d(TAG, "sendCmds: " + StringTools.changeIntoHexString(datas, true));
            } else {
                result = false;
            }
        } catch (IOException e) {
            e.printStackTrace();
            result = false;
        }
        return result;
    }

    private List<byte[]> listByte = new ArrayList<>();

    private class ReadUart extends Thread {
        @Override
        public void run() {
            super.run();
            while (threadOn && !isInterrupted()) {
                try {
//                    Log.d(TAG, "run: in try");
                    if (inputStream == null) {
                        return;
                    }
                    byte[] buffer = new byte[512];
                    int size = inputStream.read(buffer);
//                    Log.d(TAG, "run: after read" + size);
                    if (size <= 0) {
                        sleep(200);
                        continue;
                    }
                    byte[] toShow = new byte[size];
                    System.arraycopy(buffer, 0, toShow, 0, size);
//                    Log.d(TAG, String.format("%s", StringTools.changeIntoHexString(toShow, true)));

                    DataTool.gets.put(toShow);
                    if (listByte == null) {
                        listByte = new ArrayList<>();
                    }
                    listByte.add(toShow);

                } catch (Exception ignored) {
                    Log.d("UartRead", "Read Error occ");
                }
                try {
                    sleep(200);
                } catch (InterruptedException e) {
//                    e.printStackTrace();
                }
            }

        }
    }


    private class SendToSoapThread extends Thread {
        @Override
        public void run() {
            super.run();
            while (threadOn && !isInterrupted()) {
                int op = 0;
                byte[] totalDatas = new byte[2048];
                if (listByte == null || listByte.isEmpty()) {
                    try {
                        sleep(1000);
                    } catch (InterruptedException e) {
//                        e.printStackTrace();
                    }
                    continue;
                }
                for (byte[] datas : listByte) {
                    System.arraycopy(datas, 0, totalDatas, op, datas.length);
                    op += datas.length;
                }
                listByte = null;
                byte[] toSend = new byte[op];
                System.arraycopy(totalDatas, 0, toSend, 0, op);
                try {
                    DataTool.sendSoap.put(toSend);
                } catch (InterruptedException e) {
//                    e.printStackTrace();
                }

                try {
                    sleep(1000);
                } catch (InterruptedException e) {
//                    e.printStackTrace();
                }
            }
        }
    }

    public void closeUartPort() {
        if (uartTools != null) {
            uartTools.uartClose();
        }
        uartTools = null;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        try {
            DataTool.sends.put(DataTool.ENDTHREAD);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        threadOn = false;
        readUart.interrupt();
        writeUart.interrupt();
        sendToSoapThread.interrupt();
        readUart = null;
        writeUart = null;
        sendToSoapThread = null;
        closeUartPort();
    }
}
