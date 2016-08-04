package com.hqyj.dev.smarthousesarduino.modules.modules;

import com.hqyj.dev.smarthousesarduino.modules.Module;
import com.hqyj.dev.smarthousesarduino.modules.operates.FillAlcoholDatas;
import com.hqyj.dev.smarthousesarduino.modules.operates.FillWarmDatas;

/**
 *
 * Created by jiyangkang on 2016/6/10 0010.
 */
public class ModuleWarm extends Module{

    private volatile static ModuleWarm moduleWarm;

    public ModuleWarm() {
        setHaveInput(false);
        setHaveSelectBar(false);
        setCtrlCount(0);
        fillDatas = new FillWarmDatas();
        makeCmd = null;
        setBitmapToshow(null);
    }

    public static ModuleWarm getModuleWarm() {
        if (moduleWarm == null){
            synchronized (ModuleWarm.class){
                if (moduleWarm == null){
                    moduleWarm = new ModuleWarm();
                }
            }
        }
        return moduleWarm;
    }
}
