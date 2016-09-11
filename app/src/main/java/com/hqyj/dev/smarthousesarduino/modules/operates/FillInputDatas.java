package com.hqyj.dev.smarthousesarduino.modules.operates;

import android.annotation.SuppressLint;

/**
 *
 * Created by jiyangkang on 2016/6/10 0010.
 */
public class FillInputDatas implements FillDatas {
    @SuppressLint("DefaultLocale")
    @Override
    public String fillData(byte[] datas) {

        if (datas != null) {
            return String.format("：%s ", getValue(datas));
        }

        return null;
    }

    private String getValue(byte[] datas) {
        if (datas != null){
            StringBuilder s = new StringBuilder();
            for (byte data : datas) {
                s.append(which(data)).append(" ");
            }

            return s.toString();
        }

        return null;
    }

    private String which(int value){
        String result;
        switch (value){
            case 0x30:
            case 0x31:
            case 0x32:
            case 0x33:
            case 0x34:
            case 0x35:
            case 0x36:
            case 0x37:
            case 0x38:
            case 0x39:
                result = (value - 0x30) + "";
                break;
            default:
                result = "?";
                break;

        }
        return result;
    }
}
