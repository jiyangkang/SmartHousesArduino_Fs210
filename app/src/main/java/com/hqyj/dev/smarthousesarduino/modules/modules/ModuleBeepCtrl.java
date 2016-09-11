package com.hqyj.dev.smarthousesarduino.modules.modules;

import com.hqyj.dev.smarthousesarduino.modules.Module;
import com.hqyj.dev.smarthousesarduino.modules.operates.FillOpenCloseDatas;
import com.hqyj.dev.smarthousesarduino.modules.operates.MakeDefaultCmd;

/**
 * Created by jiyangkang on 2016/6/2 0002.
 */
public class ModuleBeepCtrl extends Module{

    private volatile static ModuleBeepCtrl moduleBeepCtrl;


    public ModuleBeepCtrl(){
        setHaveSelectBar(false);
        setHaveInput(true);
        fillDatas = new FillOpenCloseDatas();
        makeCmd = new MakeDefaultCmd();
        setBitmapToshow(null);
    }

    public static ModuleBeepCtrl getModuleBeepCtrl(){
        if (moduleBeepCtrl == null){
            synchronized (ModuleBeepCtrl.class){
                if (moduleBeepCtrl == null){
                    moduleBeepCtrl = new ModuleBeepCtrl();
                }
            }
        }
        return moduleBeepCtrl;
    }

}
