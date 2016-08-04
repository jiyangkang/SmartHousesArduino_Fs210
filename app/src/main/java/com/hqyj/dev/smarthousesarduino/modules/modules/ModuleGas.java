package com.hqyj.dev.smarthousesarduino.modules.modules;

import com.hqyj.dev.smarthousesarduino.modules.Module;
import com.hqyj.dev.smarthousesarduino.modules.operates.FillGasDatas;
import com.hqyj.dev.smarthousesarduino.modules.operates.FillLightDatas;

/**
 *
 * Created by jiyangkang on 2016/6/10 0010.
 */
public class ModuleGas extends Module{

    private volatile static ModuleGas moduleGas;

    public ModuleGas() {
        setHaveInput(false);
        setHaveSelectBar(false);
        setCtrlCount(0);
        fillDatas = new FillGasDatas();
        makeCmd = null;
        setBitmapToshow(null);
    }

    public static ModuleGas getModuleGas() {
        if (moduleGas == null){
            synchronized (ModuleGas.class){
                if (moduleGas == null){
                    moduleGas = new ModuleGas();
                }
            }
        }
        return moduleGas;
    }
}
