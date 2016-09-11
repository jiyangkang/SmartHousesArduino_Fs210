package com.hqyj.dev.smarthousesarduino.modules;

import android.util.Log;

import com.hqyj.dev.smarthousesarduino.modules.operates.FillDatas;
import com.hqyj.dev.smarthousesarduino.modules.operates.MakeCmd;
import com.hqyj.dev.smarthousesarduino.tools.DataTool;

import java.util.HashMap;

/**
 * Created by jiyangkang on 2016/6/2 0002.
 */
public abstract class Module {

    private String name;
    private int id;

    private byte netType = 0x00;

    private byte[] signature = new byte[3];

    private int ctrlCount;
    private byte[] value;

    private int degree = 0;//转动角度--图片

    private boolean isHaveSelectBar;
    private boolean isHaveInput;
    private String valueToShow;


    public FillDatas fillDatas;
    public MakeCmd makeCmd;

    private int[] bitmapToshow;


    private HashMap<String, byte[]> cmdHash;


    public HashMap<String, byte[]> getCmdHash() {
        return cmdHash;
    }

    public void setCmdHash(HashMap<String, byte[]> cmdHash) {
        this.cmdHash = cmdHash;
    }

    public int[] getBitmapToshow() {
        return bitmapToshow;
    }

    public void setBitmapToshow(int[] bitmapToshow) {
        this.bitmapToshow = bitmapToshow;
    }

    public byte[] getValue() {
        return value;
    }

    public void setValue(byte[] datas) {
        this.value = datas;
        int id = ((datas[DataTool.DEVICEADDR_H] & 0x00ff) << 16) |
                ((datas[DataTool.DEVICEADDR_L] & 0x00ff) << 8) |
                ((datas[DataTool.DEVICETYPE] & 0x00ff));
        setId(id);

        setNetType(datas[DataTool.NETTYPE]);
        byte[] valueShow = new byte[datas[DataTool.LENGTH]];
        System.arraycopy(datas, datas[DataTool.OFFSET], valueShow, 0, datas[DataTool.LENGTH]);
        if (datas[DataTool.DATATYPE] == DataTool.CTRLDATA) {
            sendCMD(valueShow);
//            Log.d("Module", "setValue: " + "this is control cmd");
        } else if (datas[DataTool.DATATYPE] == DataTool.NORMALDATA) {
            setValueToShow(valueShow);
        }
    }


    public void setNetType(byte netType) {
        this.netType = netType;
    }


    private OnValueReceived onValueReceived;


    public void setHaveInput(boolean haveInput) {
        isHaveInput = haveInput;
    }

    public void setOnValueReceived(OnValueReceived onValueReceived) {
        this.onValueReceived = onValueReceived;
    }

    public void setValueToShow(byte[] value) {
        this.valueToShow = fillDatas.fillData(value);
//        Log.d("MODULE", "setValueToShow: " + valueToShow);
        if (onValueReceived != null) {
            onValueReceived.onvalueReceived(this.valueToShow, degree);
        }
    }


    public void sendCMD(byte[] CMD) {
        makeCmd.makeCmd(CMD, netType, signature);
//        Log.d("Module", "sendCMD: " + getId());
    }

    public void sendCMD(String CMD) {
        makeCmd.makeCmd(CMD, netType, signature);
//        Log.d("Module", "sendCMD: " + getId());
    }

    public void sendCMD(String CMD, String whichClicked) {
        if (whichClicked != null) {
            makeCmd.makeCmd(cmdHash.get(whichClicked),
                    CMD, netType, signature);
        } else {
            sendCMD(CMD);
        }
    }

    public interface OnValueReceived {
        void onvalueReceived(String value, int degree);
    }

    public boolean isHaveSelectBar() {
        return isHaveSelectBar;
    }

    public void setHaveSelectBar(boolean haveSelectBar) {
        isHaveSelectBar = haveSelectBar;
    }

    public void setCtrlCount(int ctrlCount) {
        this.ctrlCount = ctrlCount;
    }

    public int getCtrlCount() {

        return ctrlCount;
    }

    public String getName() {
        return name;
    }

    public int getId() {
        return id;
    }

    public byte[] getSignature() {
        return signature;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setId(int id) {
        this.id = id;
        signature[0] = (byte) (id >> 16 & 0x00ff);
        signature[1] = (byte) (id >> 8 & 0x00ff);
        signature[2] = (byte) (id & 0x00ff);
    }


}
