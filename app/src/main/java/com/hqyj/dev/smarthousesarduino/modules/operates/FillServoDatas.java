package com.hqyj.dev.smarthousesarduino.modules.operates;

import android.annotation.SuppressLint;
import android.util.Log;

/**
 *
 * Created by jiyangkang on 2016/6/10 0010.
 */
@SuppressLint("DefaultLocale")
public class FillServoDatas implements FillDatas {
    private final String TAG = FillServoDatas.class.getSimpleName();
    @Override
    public String fillData(byte[] datas) {

        if (datas != null) {
            Log.d(TAG, "fillData: " + "servo is found");
            return String.format("当前角度：%s°", getValue(datas));
        }

        return null;
    }

    private String getValue(byte[] datas) {
        String result;
        if (datas!=null){
            result = String.format("%d", datas[0]);
        }else {
            result = "null";
        }

        return result;
    }
}
