package com.hqyj.dev.smarthousesarduino.system;

import com.hqyj.dev.smarthousesarduino.modules.Module;
import com.hqyj.dev.smarthousesarduino.modules.modules.ModuleAlcohol;
import com.hqyj.dev.smarthousesarduino.modules.modules.ModuleCompass;
import com.hqyj.dev.smarthousesarduino.modules.modules.ModuleDefaultCtrl;
import com.hqyj.dev.smarthousesarduino.modules.modules.ModuleGas;
import com.hqyj.dev.smarthousesarduino.modules.modules.ModuleInputCtrl;
import com.hqyj.dev.smarthousesarduino.modules.modules.ModuleLight;
import com.hqyj.dev.smarthousesarduino.modules.modules.ModulePhotoelectricSwitch;
import com.hqyj.dev.smarthousesarduino.modules.modules.ModuleServoCtrl;
import com.hqyj.dev.smarthousesarduino.modules.modules.ModuleWarm;

import java.util.HashMap;

/**
 *
 * Created by jiyangkang on 2016/6/2 0002.
 */
public class KnownSystem {

    private volatile static KnownSystem knownSystem;
    public HashMap<Integer, Module> knownModuleList = new HashMap<>();

    private KnownSystem(){
        knownModuleList.put(0x001821, ModuleAlcohol.getModuleAlcohol());
        ModuleAlcohol.getModuleAlcohol().setId(0x001821);
        knownModuleList.put(0x002050, ModuleLight.getModuleLight());
        ModuleLight.getModuleLight().setId(0x002050);
        knownModuleList.put(0x002147, ModuleGas.getModuleGas());
        ModuleGas.getModuleGas().setId(0x002147);
        knownModuleList.put(0x001434, ModuleWarm.getModuleWarm());
        ModuleWarm.getModuleWarm().setId(0x001434);
        knownModuleList.put(0x001C93, ModuleServoCtrl.getModuleServoCtrl());
        ModuleServoCtrl.getModuleServoCtrl().setId(0x001C93);
        knownModuleList.put(0x001A6E, ModuleInputCtrl.getModuleInputCtrl());
        ModuleInputCtrl.getModuleInputCtrl().setId(0x001A6E);
        knownModuleList.put(0x001550, ModulePhotoelectricSwitch.getModulePhotoelectricSwitch());
        ModulePhotoelectricSwitch.getModulePhotoelectricSwitch().setId(0x001550);
        knownModuleList.put(0x001623, ModuleCompass.getModuleCompass());
        ModuleCompass.getModuleCompass().setId(0x001623);
    }

    public static KnownSystem getKnownSystem() {
        if (knownSystem == null){
            synchronized (KnownSystem.class){
                if (knownSystem == null){
                    knownSystem = new KnownSystem();
                }
            }
        }
        return knownSystem;
    }
}
