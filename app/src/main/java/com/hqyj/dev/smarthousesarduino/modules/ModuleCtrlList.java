package com.hqyj.dev.smarthousesarduino.modules;

import java.util.HashMap;

/**
 * Created by jiyangkang on 2016/6/2 0002.
 */
public class ModuleCtrlList {

    private volatile static ModuleCtrlList moduleCtrlList;

    private HashMap<Integer, Module> moduleHashMap;

    private ModuleCtrlList(){
        moduleHashMap = new HashMap<>();
    }

    public HashMap<Integer, Module> getModuleHashMap() {
        return moduleHashMap;
    }

    public void setModuleHashMap(Integer id, Module module) {
        moduleHashMap.put(id,module);
    }



    public static ModuleCtrlList getModuleCtrlList() {
        if (moduleCtrlList == null){
            synchronized (ModuleCtrlList.class){
                if (moduleCtrlList == null) {
                    moduleCtrlList = new ModuleCtrlList();
                }
            }
        }
        return moduleCtrlList;
    }


}
