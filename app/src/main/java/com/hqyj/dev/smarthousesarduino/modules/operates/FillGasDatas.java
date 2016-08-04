package com.hqyj.dev.smarthousesarduino.modules.operates;

import android.annotation.SuppressLint;

/**
 *
 * Created by jiyangkang on 2016/6/10 0010.
 */
public class FillGasDatas implements FillDatas{
    @SuppressLint("DefaultLocale")
    @Override
    public String fillData(byte[] datas) {
        if (datas != null) {
            return String.format("烟雾：%.2f ppm",getValue(datas));
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
                result = (139.00 * value) / (455 * 3);
                break;
            case 3:
                result = 139 + (278.00 * (value % 455)) / 455;
                break;
            case 4:
                result = 417 + (333.00 * (value % 455)) / 455;
                break;
            case 5:
                result = 750 + (800.00 * (value % 455)) / 455;
                break;
            case 6:
                result = 1350 + (1150.00 * (value % 455)) / 455;
                break;
            case 7:
                result = 2500 + (1600.00 * (value % 455)) / 455;
                break;
            case 8:
                if (value % 455 < 400){
                    result = 4100 + (3400.00 * (value % 455)) / 455;
                }else {
                    result = 7500 + (1000.00 * (value % 455)) / 455;
                }
                break;
            default:
                result = 8501.00;
                break;
        }

        return result;
    }
}
