package com.hqyj.dev.smarthousesarduino.modules;

import android.util.Log;

import java.util.HashMap;

/**
 * Cr
 * eated by jiyangkang on 2016/6/2 0002.
 */
public class ModuleVoirList {

    private final String TAG = ModuleVoirList.class.getSimpleName();
    private volatile static ModuleVoirList moduleVoirList;
    private HashMap<Integer, Module> moduleHashMap;

    private ModuleVoirList(){
        moduleHashMap = new HashMap<>();
    }

    public HashMap<Integer, Module> getModuleHashMap() {
        return moduleHashMap;
    }

    public void setModuleHashMap(Integer id, Module module) {
        moduleHashMap.put(id, module);
    }

    public static ModuleVoirList getModuleVoirList() {
        if (moduleVoirList == null){
            synchronized (ModuleVoirList.class){
                if (moduleVoirList == null){
                    moduleVoirList = new ModuleVoirList();
                }
            }
        }
        return moduleVoirList;
    }
}
