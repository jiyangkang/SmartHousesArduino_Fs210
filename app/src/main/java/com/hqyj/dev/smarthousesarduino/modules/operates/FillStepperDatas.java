package com.hqyj.dev.smarthousesarduino.modules.operates;

import android.annotation.SuppressLint;
import android.util.Log;

/**
 * Created by jiyangkang on 2016/6/10 0010.
 */
@SuppressLint("DefaultLocale")
public class FillStepperDatas implements FillDatas {
    private final String TAG = FillStepperDatas.class.getSimpleName();

    @Override
    public String fillData(byte[] datas) {

        if (datas != null) {
            return String.format("转速：%s", getValue(datas));
        }

        return null;
    }

    private String getValue(byte[] datas) {
        String result;
        if (datas != null) {
            if (datas[0] != 0)
                result = String.format("%d档", datas[0]);
            else
                result = "停止";
        } else {
            result = "null";
        }

        return result;
    }
}
