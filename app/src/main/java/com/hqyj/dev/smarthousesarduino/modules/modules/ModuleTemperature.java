package com.hqyj.dev.smarthousesarduino.modules.modules;

import com.hqyj.dev.smarthousesarduino.modules.Module;
import com.hqyj.dev.smarthousesarduino.modules.operates.FillAlcoholDatas;
import com.hqyj.dev.smarthousesarduino.modules.operates.FillTemperatureDatas;

/**
 *
 * Created by jiyangkang on 2016/6/10 0010.
 */
public class ModuleTemperature extends Module{

    private volatile static ModuleTemperature moduleTemperature;

    public ModuleTemperature() {
        setHaveInput(false);
        setHaveSelectBar(false);
        setCtrlCount(0);
        fillDatas = new FillTemperatureDatas();
        makeCmd = null;
        setBitmapToshow(null);
    }

    public static ModuleTemperature getModuleTemperature() {
        if (moduleTemperature == null){
            synchronized (ModuleTemperature.class){
                if (moduleTemperature == null){
                    moduleTemperature = new ModuleTemperature();
                }
            }
        }
        return moduleTemperature;
    }
}
