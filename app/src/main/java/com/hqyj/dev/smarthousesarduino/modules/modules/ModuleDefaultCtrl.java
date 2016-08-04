package com.hqyj.dev.smarthousesarduino.modules.modules;

import com.hqyj.dev.smarthousesarduino.modules.Module;
import com.hqyj.dev.smarthousesarduino.modules.operates.FillDefaultDatas;
import com.hqyj.dev.smarthousesarduino.modules.operates.MakeDefaultCmd;

/**
 * Created by jiyangkang on 2016/6/2 0002.
 */
public class ModuleDefaultCtrl extends Module{



    public ModuleDefaultCtrl(){
        setHaveSelectBar(false);
        setCtrlCount(1);
        setHaveInput(true);
        fillDatas = new FillDefaultDatas();
        makeCmd = new MakeDefaultCmd();
        setBitmapToshow(null);
    }


}
