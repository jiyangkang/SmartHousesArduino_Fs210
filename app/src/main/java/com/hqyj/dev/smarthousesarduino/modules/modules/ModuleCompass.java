package com.hqyj.dev.smarthousesarduino.modules.modules;

import com.hqyj.dev.smarthousesarduino.modules.Module;
import com.hqyj.dev.smarthousesarduino.modules.operates.FillCompass;

/**
 *
 * Created by jiyangkang on 2016/6/10 0010.
 */
public class ModuleCompass extends Module{

    private volatile static ModuleCompass moduleCompass;

    public ModuleCompass() {
        setHaveInput(false);
        setHaveSelectBar(false);
        setCtrlCount(0);
        fillDatas = new FillCompass();
        makeCmd = null;
        setBitmapToshow(null);
    }

    public static ModuleCompass getModuleCompass() {
        if (moduleCompass == null){
            synchronized (ModuleCompass.class){
                if (moduleCompass == null){
                    moduleCompass = new ModuleCompass();
                }
            }
        }
        return moduleCompass;
    }
}
