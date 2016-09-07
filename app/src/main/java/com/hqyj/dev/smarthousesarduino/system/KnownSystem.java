package com.hqyj.dev.smarthousesarduino.system;

import com.hqyj.dev.smarthousesarduino.modules.Module;
import com.hqyj.dev.smarthousesarduino.modules.modules.ModuleAlcohol;
import com.hqyj.dev.smarthousesarduino.modules.modules.ModuleCompass;
import com.hqyj.dev.smarthousesarduino.modules.modules.ModuleDCCtrl;
import com.hqyj.dev.smarthousesarduino.modules.modules.ModuleDefaultCtrl;
import com.hqyj.dev.smarthousesarduino.modules.modules.ModuleGas;
import com.hqyj.dev.smarthousesarduino.modules.modules.ModuleInputCtrl;
import com.hqyj.dev.smarthousesarduino.modules.modules.ModuleLight;
import com.hqyj.dev.smarthousesarduino.modules.modules.ModulePhotoelectricSwitch;
import com.hqyj.dev.smarthousesarduino.modules.modules.ModuleServoCtrl;
import com.hqyj.dev.smarthousesarduino.modules.modules.ModuleTemperature;
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

//        knownModuleList.put(0x001821, ModuleAlcohol.getModuleAlcohol());
//        ModuleAlcohol.getModuleAlcohol().setId(0x001821);
        //酒精传感器
        knownModuleList.put(0x001321, ModuleAlcohol.getModuleAlcohol());
        ModuleAlcohol.getModuleAlcohol().setId(0x001321);

//        knownModuleList.put(0x002050, ModuleLight.getModuleLight());
//        ModuleLight.getModuleLight().setId(0x002050);
        //光敏传感器
        knownModuleList.put(0x001030, ModuleLight.getModuleLight());
        ModuleLight.getModuleLight().setId(0x001030);

//        knownModuleList.put(0x002147, ModuleGas.getModuleGas());
//        ModuleGas.getModuleGas().setId(0x002147);
        //气体传感器
        knownModuleList.put(0x002147, ModuleGas.getModuleGas());
        ModuleGas.getModuleGas().setId(0x001547);

//        knownModuleList.put(0x001434, ModuleWarm.getModuleWarm());
//        ModuleWarm.getModuleWarm().setId(0x001434);
        //温度传感器
        knownModuleList.put(0x001814, ModuleTemperature.getModuleTemperature());
        ModuleTemperature.getModuleTemperature().setId(0x001814);

//        knownModuleList.put(0x001C93, ModuleServoCtrl.getModuleServoCtrl());
//        ModuleServoCtrl.getModuleServoCtrl().setId(0x001C93);
        //舵机
        knownModuleList.put(0x400873, ModuleServoCtrl.getModuleServoCtrl());
        ModuleServoCtrl.getModuleServoCtrl().setId(0x400873);

//        knownModuleList.put(0x001A6E, ModuleInputCtrl.getModuleInputCtrl());
//        ModuleInputCtrl.getModuleInputCtrl().setId(0x001A6E);
        //矩阵键盘
        knownModuleList.put(0x40060E, ModuleInputCtrl.getModuleInputCtrl());
        ModuleInputCtrl.getModuleInputCtrl().setId(0x40066E);

//        knownModuleList.put(0x001550, ModulePhotoelectricSwitch.getModulePhotoelectricSwitch());
//        ModulePhotoelectricSwitch.getModulePhotoelectricSwitch().setId(0x001550);
        //光电开关
        knownModuleList.put(0x001410, ModulePhotoelectricSwitch.getModulePhotoelectricSwitch());
        ModulePhotoelectricSwitch.getModulePhotoelectricSwitch().setId(0x001410);

        //直流电机
        knownModuleList.put(0x40046d, ModuleDCCtrl.getModuleDCCtrl());
        ModuleDCCtrl.getModuleDCCtrl().setId(0x40046d);

//        knownModuleList.put(0x001623, ModuleCompass.getModuleCompass());
//        ModuleCompass.getModuleCompass().setId(0x001623);
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
