package com.hqyj.dev.smarthousesarduino.system;

import java.util.HashMap;

/**
 * Created by jiyangkang on 2016/6/12 0012.
 */
public class SmartControlConfig {
    private final String TAG = SmartControlConfig.class.getSimpleName();

    private volatile static SmartControlConfig smartControlConfig = null;


    private SmartControlConfig (){

    }

    public static SmartControlConfig getSmartControlConfig() {
        if (smartControlConfig == null){
            synchronized (SmartControlConfig.class){
                if (smartControlConfig == null){
                    smartControlConfig = new SmartControlConfig();
                }
            }
        }

        return smartControlConfig;
    }
}
