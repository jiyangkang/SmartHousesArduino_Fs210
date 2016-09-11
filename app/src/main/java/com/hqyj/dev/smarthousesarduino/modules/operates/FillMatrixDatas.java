package com.hqyj.dev.smarthousesarduino.modules.operates;

import android.annotation.SuppressLint;

/**
 *
 * Created by jiyangkang on 2016/6/10 0010.
 */
@SuppressLint("DefaultLocale")
public class FillMatrixDatas implements FillDatas {
    @Override
    public String fillData(byte[] datas) {

        if (datas != null) {
            return String.format("%s 被按下", getValue(datas));
        }

        return null;
    }

    private String getValue(byte[] datas) {
        int value = datas[0];
        String result;
        switch (value) {
            case 0:
            case 1:
            case 2:
            case 3:
            case 4:
            case 5:
            case 6:
            case 7:
            case 9:
                result = String.format("%d", value);
                break;
            case 0x10:
                result = String.format("%s", "*");
                break;
            case 0x0e:
                result = String.format("%s", "#");
                break;
            case 0x0A:
                result = String.format("%s", "A");
                break;
            case 0x0B:
                result = String.format("%s", "B");
                break;
            case 0x0C:
                result = String.format("%s", "C");
                break;
            case 0x0D:
                result = String.format("%s", "D");
                break;
            default:
                result = "未知";
                break;
        }

        return result;
    }
}
