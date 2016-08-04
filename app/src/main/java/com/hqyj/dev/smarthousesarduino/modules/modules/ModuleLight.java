package com.hqyj.dev.smarthousesarduino.modules.modules;

import com.hqyj.dev.smarthousesarduino.modules.Module;
import com.hqyj.dev.smarthousesarduino.modules.operates.FillLightDatas;

/**
 *
 * Created by jiyangkang on 2016/6/10 0010.
 */
public class ModuleLight extends Module{

    private volatile static ModuleLight moduleLight;

    public ModuleLight() {
        setHaveInput(false);
        setHaveSelectBar(false);
        setCtrlCount(0);
        fillDatas = new FillLightDatas();
        makeCmd = null;
        setBitmapToshow(null);
    }

    public static ModuleLight getModuleLight() {
        if (moduleLight == null){
            synchronized (ModuleLight.class){
                if (moduleLight == null){
                    moduleLight = new ModuleLight();
                }
            }
        }
        return moduleLight;
    }
}
