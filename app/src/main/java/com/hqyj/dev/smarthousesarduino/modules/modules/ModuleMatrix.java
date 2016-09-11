package com.hqyj.dev.smarthousesarduino.modules.modules;

import com.hqyj.dev.smarthousesarduino.modules.Module;
import com.hqyj.dev.smarthousesarduino.modules.operates.FillAlcoholDatas;
import com.hqyj.dev.smarthousesarduino.modules.operates.FillMatrixDatas;

/**
 *
 * Created by jiyangkang on 2016/6/10 0010.
 */
public class ModuleMatrix extends Module{

    private volatile static ModuleMatrix moduleMatrix;

    public ModuleMatrix() {
        setHaveInput(false);
        setHaveSelectBar(false);
        fillDatas = new FillMatrixDatas();
        makeCmd = null;
        setBitmapToshow(null);
    }

    public static ModuleMatrix getModuleMatrix() {
        if (moduleMatrix == null){
            synchronized (ModuleMatrix.class){
                if (moduleMatrix == null){
                    moduleMatrix = new ModuleMatrix();
                }
            }
        }
        return moduleMatrix;
    }
}
