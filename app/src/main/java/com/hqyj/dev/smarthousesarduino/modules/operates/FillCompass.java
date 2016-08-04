package com.hqyj.dev.smarthousesarduino.modules.operates;

import android.annotation.SuppressLint;

/**
 * Created by jiyangkang on 2016/7/24 0024.
 */
public class FillCompass implements FillDatas {
    @Override
    public String fillData(byte[] datas) {

        return getOritation(datas);
    }

    @SuppressLint("DefaultLocale")
    private String getOritation(byte[] datas) {
        int x = ((datas[0] << 8 ) & 0xffffff00)|(datas[1] & 0x00ff);
        int y = ((datas[2] << 8 ) & 0xffffff00)|(datas[3] & 0x00ff);
        //ana
        double angle = (Math.atan2(y, x)) * (180 / Math.PI) + 180;



        String show;
        if (angle >= 0 && angle < 22.5){
            show = String.format("正南 偏%.2f °", angle - 0);
        }else if (angle >= 22.5 && angle < 45){
            show = String.format("西南 偏%.2f °", 45 - angle);
        }else if (angle >= 45 && angle < 67.5){
            show = String.format("西南 偏%.2f °", angle - 45);
        }else if (angle >= 67.5 && angle < 90){
            show = String.format("正西 偏%.2f °", 90 - angle);
        }else if (angle >= 90 && angle < 112.5){
            show = String.format("正西 偏%.2f °", angle - 90);
        }else if (angle >= 112.5 && angle < 135){
            show = String.format("西北 偏%.2f °", 135 - angle);
        }else if (angle >= 135 && angle < 157.5){
            show = String.format("西北 偏%.2f °", angle - 135);
        }else if (angle >= 157.5 && angle < 180){
            show = String.format("正北 偏%.2f °", 180 - angle);
        }else if (angle >= 180 && angle < 202.5){
            show = String.format("正北 偏%.2f °", angle - 180);
        }else if (angle >= 202.5 && angle < 225){
            show = String.format("东北 偏%.2f °", 225 - angle);
        }else if (angle >= 225 && angle < 247.5){
            show = String.format("东北 偏%.2f °", angle - 225);
        }else if (angle >= 247.5 && angle < 270){
            show = String.format("正东 偏%.2f °", 270 - angle);
        }else if (angle >= 270 && angle < 292.5){
            show = String.format("正东 偏%.2f °", angle - 270);
        }else if (angle >= 292.5 && angle < 315){
            show = String.format("东南 偏%.2f °", 315 - angle);
        }else if (angle >= 315 && angle < 337.5){
            show = String.format("东南 偏%.2f °", angle - 315);
        }else if (angle >= 337.5 && angle <= 360){
            show = String.format("正南 偏%.2f °", 360 - angle);
        }else {
            show = String.format("角度 偏%.2f °", angle);
        }
        return show;
    }
}
