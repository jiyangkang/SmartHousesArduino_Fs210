package com.hqyj.dev.smarthousesarduino.modules.modules;

import com.hqyj.dev.smarthousesarduino.modules.Module;
import com.hqyj.dev.smarthousesarduino.modules.operates.FillPhotoelectricSwitch;

/**
 *
 * Created by jiyangkang on 2016/6/10 0010.
 */
public class ModulePhotoelectricSwitch extends Module{

    private volatile static ModulePhotoelectricSwitch modulePhotoelectricSwitch;

    public ModulePhotoelectricSwitch() {
        setHaveInput(false);
        setHaveSelectBar(false);
        setCtrlCount(0);
        fillDatas = new FillPhotoelectricSwitch();
        makeCmd = null;
        setBitmapToshow(null);
    }

    public static ModulePhotoelectricSwitch getModulePhotoelectricSwitch() {
        if (modulePhotoelectricSwitch == null){
            synchronized (ModulePhotoelectricSwitch.class){
                if (modulePhotoelectricSwitch == null){
                    modulePhotoelectricSwitch = new ModulePhotoelectricSwitch();
                }
            }
        }
        return modulePhotoelectricSwitch;
    }
}
