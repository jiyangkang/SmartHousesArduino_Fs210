package com.hqyj.dev.smarthousesarduino.modules.modules;

import com.hqyj.dev.smarthousesarduino.modules.Module;
import com.hqyj.dev.smarthousesarduino.modules.operates.FillDefaultDatas;
import com.hqyj.dev.smarthousesarduino.modules.operates.MakeInputCmd;
import com.hqyj.dev.smarthousesarduino.modules.operates.MakeServoCmd;

/**
 * Created by jiyangkang on 2016/6/2 0002.
 */
public class ModuleInputCtrl extends Module{

    private volatile static ModuleInputCtrl moduleInputCtrl;


    public ModuleInputCtrl(){
        setHaveSelectBar(false);
        setCtrlCount(1);
        setHaveInput(true);
        fillDatas = new FillDefaultDatas();
        makeCmd = new MakeInputCmd();
        setBitmapToshow(null);
    }

    public static ModuleInputCtrl getModuleInputCtrl(){
        if (moduleInputCtrl == null){
            synchronized (ModuleInputCtrl.class){
                if (moduleInputCtrl == null){
                    moduleInputCtrl = new ModuleInputCtrl();
                }
            }
        }
        return moduleInputCtrl;
    }

}
