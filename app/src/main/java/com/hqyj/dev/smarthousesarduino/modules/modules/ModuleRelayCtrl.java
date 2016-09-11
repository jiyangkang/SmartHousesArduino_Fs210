package com.hqyj.dev.smarthousesarduino.modules.modules;

import com.hqyj.dev.smarthousesarduino.modules.Module;
import com.hqyj.dev.smarthousesarduino.modules.operates.FillOpenCloseDatas;
import com.hqyj.dev.smarthousesarduino.modules.operates.FillServoDatas;
import com.hqyj.dev.smarthousesarduino.modules.operates.MakeDefaultCmd;
import com.hqyj.dev.smarthousesarduino.modules.operates.MakeServoCmd;

/**
 * Created by jiyangkang on 2016/6/2 0002.
 */
public class ModuleRelayCtrl extends Module{

    private volatile static ModuleRelayCtrl moduleRelayCtrl;


    public ModuleRelayCtrl(){
        setHaveSelectBar(false);
        setHaveInput(true);
        fillDatas = new FillOpenCloseDatas();
        makeCmd = new MakeDefaultCmd();
        setBitmapToshow(null);
    }

    public static ModuleRelayCtrl getModuleRelayCtrl(){
        if (moduleRelayCtrl == null){
            synchronized (ModuleRelayCtrl.class){
                if (moduleRelayCtrl == null){
                    moduleRelayCtrl = new ModuleRelayCtrl();
                }
            }
        }
        return moduleRelayCtrl;
    }

}
