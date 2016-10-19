package com.hqyj.dev.smarthousesarduino.modules.operates;

import android.annotation.SuppressLint;

/**
 *
 * Created by jiyangkang on 2016/6/10 0010.
 */
@SuppressLint("DefaultLocale")
public class FillDCCtrlDatas implements FillDatas {
    @Override
    public String fillData(byte[] datas) {

        if (datas != null) {
            return String.format("%s", getValue(datas));
        }

        return null;
    }

    private String getValue(byte[] datas) {
        String result;
        if (datas!=null){
            int rate = datas[1];
            String oritation;
            if (datas[0] == 0x05){
                oritation = "正转";
            }else if (datas[0] == 0x06){
                oritation = "反转";
            }else {
                oritation = "停";
            }

            result = String.format("%s, %dr/s", oritation, rate);
        }else {
            result = "null";
        }

        return result;
    }
}
