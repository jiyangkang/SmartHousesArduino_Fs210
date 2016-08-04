package com.hqyj.dev.smarthousesarduino.modules.modules;

import com.hqyj.dev.smarthousesarduino.modules.Module;
import com.hqyj.dev.smarthousesarduino.modules.operates.FillAlcoholDatas;

/**
 *
 * Created by jiyangkang on 2016/6/10 0010.
 */
public class ModuleAlcohol extends Module{

    private volatile static ModuleAlcohol moduleAlcohol;

    public ModuleAlcohol() {
        setHaveInput(false);
        setHaveSelectBar(false);
        setCtrlCount(0);
        fillDatas = new FillAlcoholDatas();
        makeCmd = null;
        setBitmapToshow(null);
    }

    public static ModuleAlcohol getModuleAlcohol() {
        if (moduleAlcohol == null){
            synchronized (ModuleAlcohol.class){
                if (moduleAlcohol == null){
                    moduleAlcohol = new ModuleAlcohol();
                }
            }
        }
        return moduleAlcohol;
    }
}
