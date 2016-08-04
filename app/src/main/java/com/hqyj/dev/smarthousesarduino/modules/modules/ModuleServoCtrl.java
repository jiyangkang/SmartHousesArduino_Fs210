package com.hqyj.dev.smarthousesarduino.modules.modules;

import com.hqyj.dev.smarthousesarduino.modules.Module;
import com.hqyj.dev.smarthousesarduino.modules.operates.FillDefaultDatas;
import com.hqyj.dev.smarthousesarduino.modules.operates.MakeDefaultCmd;
import com.hqyj.dev.smarthousesarduino.modules.operates.MakeServoCmd;

/**
 * Created by jiyangkang on 2016/6/2 0002.
 */
public class ModuleServoCtrl extends Module{

    private volatile static ModuleServoCtrl moduleServoCtrl;


    public ModuleServoCtrl(){
        setHaveSelectBar(false);
        setCtrlCount(1);
        setHaveInput(true);
        fillDatas = new FillDefaultDatas();
        makeCmd = new MakeServoCmd();
        setBitmapToshow(null);
    }

    public static ModuleServoCtrl getModuleServoCtrl(){
        if (moduleServoCtrl == null){
            synchronized (ModuleServoCtrl.class){
                if (moduleServoCtrl == null){
                    moduleServoCtrl = new ModuleServoCtrl();
                }
            }
        }
        return moduleServoCtrl;
    }

}
