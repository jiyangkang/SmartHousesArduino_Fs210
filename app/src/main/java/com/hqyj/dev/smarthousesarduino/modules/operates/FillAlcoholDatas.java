package com.hqyj.dev.smarthousesarduino.modules.operates;

import android.annotation.SuppressLint;

/**
 *
 * Created by jiyangkang on 2016/6/10 0010.
 */
public class FillAlcoholDatas implements FillDatas {
    @SuppressLint("DefaultLocale")
    @Override
    public String fillData(byte[] datas) {

        if (datas != null) {
            return String.format("酒精：%.2f ppm", getValue(datas));
        }

        return null;
    }

    private double getValue(byte[] datas) {
        int value = ((datas[0] << 8) & 0xff00) | ((datas[1]) & 0xff);
        double result;
        switch (value / 455) {
            case 0:
            case 1:
            case 2:
            case 3:
                result = (36.00 * value) / (455 * 4);
                break;
            case 4:
                result = 36 + (34.00 * (value % 455)) / 455;
                break;
            case 5:
                result = 70 + (30.00 * (value % 455)) / 455;
                break;
            case 6:
                result = 100 + (45.00 * (value % 455)) / 455;
                break;
            case 7:
                if (value % 455 < 273) {
                    result = 145 + (55.00 * (value % 455)) / 455;
                } else {
                    result = 200 + (100.00 * (value % 455)) / 455;
                }
                break;
            case 8:
                if (value % 455 < 220) {
                    result = 300 + (200.00 * (value % 455)) / 455;
                } else {
                    result = 501.00;
                }
                break;
            default:
                result = 501.00;
                break;
        }

        return result;
    }
}
