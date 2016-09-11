package com.hqyj.dev.smarthousesarduino.modules.modules;

import com.hqyj.dev.smarthousesarduino.modules.Module;
import com.hqyj.dev.smarthousesarduino.modules.operates.FillOpenCloseDatas;
import com.hqyj.dev.smarthousesarduino.modules.operates.FillStepperDatas;
import com.hqyj.dev.smarthousesarduino.modules.operates.MakeDefaultCmd;

/**
 * Created by jiyangkang on 2016/6/2 0002.
 */
public class ModuleStepperCtrl extends Module{

    private volatile static ModuleStepperCtrl moduleStepperCtrl;


    public ModuleStepperCtrl(){
        setHaveSelectBar(false);
        setHaveInput(true);
        fillDatas = new FillStepperDatas();
        makeCmd = new MakeDefaultCmd();
        setBitmapToshow(null);
    }

    public static ModuleStepperCtrl getModuleStepperCtrl(){
        if (moduleStepperCtrl == null){
            synchronized (ModuleStepperCtrl.class){
                if (moduleStepperCtrl == null){
                    moduleStepperCtrl = new ModuleStepperCtrl();
                }
            }
        }
        return moduleStepperCtrl;
    }

}
