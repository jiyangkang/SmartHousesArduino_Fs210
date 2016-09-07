package com.hqyj.dev.smarthousesarduino.modules.operates;

import android.annotation.SuppressLint;

/**
 *
 * Created by jiyangkang on 2016/6/10 0010.
 */
public class FillTemperatureDatas implements FillDatas {
    @SuppressLint("DefaultLocale")
    @Override
    public String fillData(byte[] datas) {

        if (datas != null) {
            return String.format("温度：%.2f ℃", getValue(datas));
        }

        return null;
    }

    private double getValue(byte[] datas) {
        double result;
        if (datas != null){
            int value = datas[0] << 8 | datas[1];
            value = value >> 5;
            result = value * 0.125;
        }else {
            result = 0.00;
        }

        return result;
    }
}
