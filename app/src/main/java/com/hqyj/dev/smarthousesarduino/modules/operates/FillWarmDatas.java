package com.hqyj.dev.smarthousesarduino.modules.operates;

import android.annotation.SuppressLint;

/**
 *
 * Created by jiyangkang on 2016/6/10 0010.
 */
public class FillWarmDatas implements FillDatas{
    @SuppressLint("DefaultLocale")
    @Override
    public String fillData(byte[] datas) {

        if (datas != null){
            return String.format("热敏电压：%.2f V", getOumig(datas));
        }
        return null;
    }

    private double getOumig(byte[] datas){
        int vout = ((datas[0] << 8) & 0x0000ff00) | (datas[1] & 0x00ff);
//        int vin = vout/101;

        return 1.00 * vout * 3.3/ 4096;
    }
}
