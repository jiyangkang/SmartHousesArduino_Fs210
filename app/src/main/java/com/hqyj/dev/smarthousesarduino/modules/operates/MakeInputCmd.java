package com.hqyj.dev.smarthousesarduino.modules.operates;

import android.util.Log;

import com.hqyj.dev.smarthousesarduino.tools.DataTool;
import com.hqyj.dev.smarthousesarduino.tools.MathTools;
import com.hqyj.dev.smarthousesarduino.tools.StringTools;

/**
 * Created by jiyangkang on 2016/6/3 0003.
 */
public class MakeInputCmd implements MakeCmd {

    @Override
    public void makeCmd(byte[] cmd, byte netType,byte[] signture) {
        byte[] value = new byte[DataTool.protocal.length + cmd.length -2];
        value[DataTool.HEAD] = DataTool.HEAD_RECEIVE;
        value[DataTool.LENGTH] = (byte)cmd.length;
        value[DataTool.OFFSET] = 0x08;
        value[DataTool.DATATYPE] = DataTool.CTRLDATA;
        value[DataTool.NETTYPE] = netType;
        value[DataTool.DEVICEADDR_H] = signture[0];
        value[DataTool.DEVICEADDR_L] = signture[1];
        value[DataTool.DEVICETYPE] = signture[2];

        System.arraycopy(cmd, 0, value, value[DataTool.OFFSET],cmd.length);

        byte mata = MathTools.makeMate(value);

        byte[] result = new byte[value.length+1];

        System.arraycopy(value, 0, result, 0, value.length);
        result[value.length] = mata;

        Log.d("CMD+", "makeCmd: "+ StringTools.changeIntoHexString(result, true));
        try {
            DataTool.sends.put(result);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private String TAG = MakeInputCmd.class.getSimpleName();
    @Override
    public void makeCmd(String cmd, byte netType, byte[] signature) {
        if (cmd != null){
            int length = cmd.length();
            Log.d(TAG, "makeCmd: "+cmd);
            if (0< length  && length <= 8){
                byte[] datas = cmd.getBytes();
                makeCmd(datas, netType, signature);
            } else if (length > 8){
                String cmds = cmd.substring(length - 8);
                byte[] datas = cmds.getBytes();
                makeCmd(datas, netType, signature);
            }
        }else {
            makeCmd(DataTool.ERRORCMD, netType, signature);
        }
    }

    @Override
    public void makeCmd(byte[] cmd_b, String cmd_s, byte netType, byte[] signature) {
        makeCmd(cmd_s, netType, signature);
    }
}
