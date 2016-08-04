package com.hqyj.dev.smarthousesarduino.modules.modules;

import com.hqyj.dev.smarthousesarduino.modules.Module;
import com.hqyj.dev.smarthousesarduino.modules.operates.FillDefaultDatas;

/**
 * Created by jiyangkang on 2016/6/2 0002.
 */
public class ModuleDefaultVoir extends Module{

    public ModuleDefaultVoir(){
        setHaveInput(false);
        setHaveSelectBar(false);
        setCtrlCount(0);
        fillDatas = new FillDefaultDatas();
        makeCmd = null;
        setBitmapToshow(null);
    }
}
