package com.hqyj.dev.smarthousesarduino.modules.operates;

import android.annotation.SuppressLint;


/**
 * Created by jiyangkang on 2016/6/10 0010.
 */
public class FillOpenCloseDatas implements FillDatas {
    @SuppressLint("DefaultLocale")
    @Override
    public String fillData(byte[] datas) {

        String show = getString(datas);
        if (datas != null) {
            return String.format("开关状态：%s", show);
        }

        return null;
    }

    private String getString(byte[] datas) {
        String show;
        switch (datas[0]){
            case 0x30:
                show = "关";
                break;
            case 0x31:
                show = "开";
                break;
            default:
                show = String.format("%2X", datas[0]);
                break;
        }
        return show;
    }
}
