package com.hqyj.dev.smarthousesarduino.modules.operates;

import android.annotation.SuppressLint;


/**
 * Created by jiyangkang on 2016/6/10 0010.
 */
public class FillLightDatas implements FillDatas {
    @SuppressLint("DefaultLocale")
    @Override
    public String fillData(byte[] datas) {

        double show = getlux(datas);
        if (datas != null) {
            return (show == 0) ? String.format("光强%s", ">100lux") : ((show == -1)?String.format("光强%.2f", 0.00):String.format("光强：%.2f lux", show));
        }

        return null;
    }


    private double getlux(byte[] datas) {
        int value = ((datas[0] << 8) & 0xff00) | ((datas[1]) & 0xff);
        double result;
        int oumig = (value * 1000) / (4096 - value);
        if (oumig < 1500) {
            result = 0.00;
            oumig = 0;
        } else if (oumig < 1557) {
            result = 100.00 - (double) (oumig - 1500) * 10.00 / 57.00;
        } else if (oumig < 1657) {
            result = 90.00 - (double) (oumig - 1557) * 10.00 / 100.00;
        } else if (oumig < 1757) {
            result = 80.00 - (double) (oumig - 1657) * 10.00 / 100.00;
        } else if (oumig < 1885) {
            result = 70.00 - (double) (oumig - 1757) * 10.00 / 128.00;
        } else if (oumig < 2000) {
            result = 60.00 - (double) (oumig - 1885) * 10.00 / 115.00;
        } else if (oumig < 2333) {
            result = 50.00 - (double) (oumig - 2000) * 10.00 / 333.00;
        } else if (oumig < 2738) {
            result = 40.00 - (double) (oumig - 2333) * 10.00 / 405.00;
        } else if (oumig < 3555) {
            result = 30 - (double) (oumig - 2738) * 10.00 / 817.00;
        } else if (oumig < 5150) {
            result = 20 - (double) (oumig - 3555) * 10.00 / 1595.00;
        }else if (oumig < 5500) {
            result = 10 - (double) (oumig - 5150) * 1.00 / 350.00;
        }else if (oumig < 6000) {
            result = 9 - (double) (oumig - 5500) * 1.00 / 500.00;
        }else if (oumig < 6340) {
            result = 8 - (double) (oumig - 6000) * 1.00 / 340.00;
        }else if (oumig < 7000) {
            result = 7 - (double) (oumig - 6340) * 1.00 / 660.00;
        } else if (oumig < 7800) {
            result = 6 - (double) (oumig - 7000) * 1.00 / 800.00;
        }else if (oumig < 9000) {
            result = 5 - (double) (oumig - 7800) * 1.00 / 1200.00;
        }else if (oumig < 11000) {
            result = 4 - (double) (oumig - 9000) * 1.00 / 2000.00;
        }else if (oumig < 14000) {
            result = 3 - (double) (oumig - 11000) * 1.00 / 3000.00;
        }else if (oumig < 20000) {
            result = 2 - (double) (oumig - 14000) * 1.00 / 6000.00;
        }else {
            result = -1;
        }
        return result;
    }
}
