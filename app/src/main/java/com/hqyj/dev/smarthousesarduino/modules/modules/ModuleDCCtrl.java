package com.hqyj.dev.smarthousesarduino.modules.modules;

import com.hqyj.dev.smarthousesarduino.modules.Module;
import com.hqyj.dev.smarthousesarduino.modules.operates.FillDCCtrlDatas;
import com.hqyj.dev.smarthousesarduino.modules.operates.FillDefaultDatas;
import com.hqyj.dev.smarthousesarduino.modules.operates.MakeDCCtrlCmd;
import com.hqyj.dev.smarthousesarduino.modules.operates.MakeServoCmd;

/**
 * Created by jiyangkang on 2016/6/2 0002.
 */
public class ModuleDCCtrl extends Module{

    private volatile static ModuleDCCtrl moduleDCCtrl;


    public ModuleDCCtrl(){
        setHaveSelectBar(false);
        setCtrlCount(3);
        setHaveInput(true);
        fillDatas = new FillDCCtrlDatas();
        makeCmd = new MakeDCCtrlCmd();
        setBitmapToshow(null);
    }

    public static ModuleDCCtrl getModuleDCCtrl(){
        if (moduleDCCtrl == null){
            synchronized (ModuleDCCtrl.class){
                if (moduleDCCtrl == null){
                    moduleDCCtrl = new ModuleDCCtrl();
                }
            }
        }
        return moduleDCCtrl;
    }

}
