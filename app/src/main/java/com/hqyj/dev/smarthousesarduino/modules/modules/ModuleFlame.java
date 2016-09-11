package com.hqyj.dev.smarthousesarduino.modules.modules;

import com.hqyj.dev.smarthousesarduino.modules.Module;
import com.hqyj.dev.smarthousesarduino.modules.operates.FillAlcoholDatas;
import com.hqyj.dev.smarthousesarduino.modules.operates.FillFlameDatas;

/**
 *
 * Created by jiyangkang on 2016/6/10 0010.
 */
public class ModuleFlame extends Module{

    private volatile static ModuleFlame moduleFlame;

    public ModuleFlame() {
        setHaveInput(false);
        setHaveSelectBar(false);
        setCtrlCount(0);
        fillDatas = new FillFlameDatas();
        makeCmd = null;
        setBitmapToshow(null);
    }

    public static ModuleFlame getModuleFlame() {
        if (moduleFlame == null){
            synchronized (ModuleFlame.class){
                if (moduleFlame == null){
                    moduleFlame = new ModuleFlame();
                }
            }
        }
        return moduleFlame;
    }
}
