package com.hqyj.dev.smarthousesarduino.system;

import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.support.v4.app.Fragment;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jiyangkang on 2016/6/2 0002.
 */
public class SystemConfig {

    private String projectName;
    private int projectID;
    private String outIP;
    private String nameSpace;
    private String uartPort;
    private int receivePort;
    private int sendPort;
    private String local_net;
    private String device;
    private int baudRate;
    private String userID;
    private Drawable codeBit;

    private boolean isUart = false;
    private boolean isSoap = false;
    private boolean isUdp = false;

    public Drawable getCodeBit() {
        return codeBit;
    }

    public void setCodeBit(Drawable codeBit) {
        this.codeBit = codeBit;
    }

    public void setProjectMode(boolean uart, boolean soap, boolean Udp) {
        isSoap = soap;
        isUart = uart;
        isUdp = Udp;
    }

    public boolean isSoap() {
        return isSoap;
    }

    public boolean isUart() {
        return isUart;
    }

    public boolean isUdp() {
        return isUdp;
    }

    private List<Fragment> fragmentList = new ArrayList<>();

    private volatile static SystemConfig systemConfig;

    private SystemConfig(){

    }

    public static SystemConfig getSystemConfig() {

        if (systemConfig == null){
            synchronized (SystemConfig.class){
                if (systemConfig == null){
                    systemConfig = new SystemConfig();
                }
            }
        }

        return systemConfig;
    }

    public void setFragmentList(List<Fragment> fragmentList) {
        this.fragmentList = fragmentList;
    }


    public void setUserID(String userID) {
        this.userID = userID;
    }

    public int getBaudRate() {
        return baudRate;
    }

    public void setBaudRate(int baudRate) {
        this.baudRate = baudRate;
    }


    public String getProjectName() {
        return projectName;
    }


    public String getOutIP() {
        return outIP;
    }

    public String getNameSpace() {
        return nameSpace;
    }

    public String getUartPort() {
        return uartPort;
    }

    public String getDevice() {
        return device;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public void setProjectID(int projectID) {
        this.projectID = projectID;
    }

    public void setOutIP(String outIP) {
        this.outIP = outIP;
    }

    public void setNameSpace(String nameSpace) {
        this.nameSpace = nameSpace;
    }

    public void setUartPort(String uartPort) {
        this.uartPort = uartPort;
    }

    public void setReceivePort(int receivePort) {
        this.receivePort = receivePort;
    }

    public void setSendPort(int sendPort) {
        this.sendPort = sendPort;
    }

    public void setLocal_net(String local_net) {
        this.local_net = local_net;
    }

    public void setDevice(String device) {
        this.device = device;
    }
}
