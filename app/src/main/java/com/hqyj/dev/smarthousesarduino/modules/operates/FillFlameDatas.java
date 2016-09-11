package com.hqyj.dev.smarthousesarduino.modules.operates;

import android.annotation.SuppressLint;

/**
 *
 * Created by jiyangkang on 2016/6/10 0010.
 */
public class FillFlameDatas implements FillDatas {
    @SuppressLint("DefaultLocale")
    @Override
    public String fillData(byte[] datas) {

        if (datas != null) {
            return String.format("火焰：%.2f V", getValue(datas));
        }

        return null;
    }

    private double getValue(byte[] datas) {
        int value = ((datas[0] << 8) & 0xff00) | ((datas[1]) & 0xff);
        double result;
        result = value * 3.30/4096;

        return result;
    }
}
