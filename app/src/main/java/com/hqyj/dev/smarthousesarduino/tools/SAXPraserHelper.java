package com.hqyj.dev.smarthousesarduino.tools;

import android.util.Log;

import com.hqyj.dev.smarthousesarduino.modules.Module;
import com.hqyj.dev.smarthousesarduino.modules.ModuleCtrlList;
import com.hqyj.dev.smarthousesarduino.modules.ModuleVoirList;
import com.hqyj.dev.smarthousesarduino.modules.modules.ModuleDefaultCtrl;
import com.hqyj.dev.smarthousesarduino.modules.modules.ModuleDefaultVoir;
import com.hqyj.dev.smarthousesarduino.system.KnownSystem;
import com.hqyj.dev.smarthousesarduino.system.SystemConfig;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 *
 * Created by jiyangkang on 2016/6/4 0004.
 */
public class SAXPraserHelper extends DefaultHandler {

    private final String TAG = SAXPraserHelper.class.getSimpleName();
    private List<String> project = new ArrayList<>();
    private List<String> ctrlNode = new ArrayList<>();
    private List<String> voirNode = new ArrayList<>();


    public List<String> getProject() {
        return project;
    }

    public List<String> getCtrlNode() {
        return ctrlNode;
    }

    public List<String> getVoirNode() {
        return voirNode;
    }

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
        super.startElement(uri, localName, qName, attributes);
        if (localName.equalsIgnoreCase("project")) {
            for (int i = 0; i < attributes.getLength(); i++) {
                setProject(attributes.getLocalName(i), attributes.getValue(i));
                project.add(attributes.getValue(i));
            }
        } else if (localName.equalsIgnoreCase("module")) {
            HashMap<String, String> modlueList = new HashMap<>();
            for (int i = 0; i < attributes.getLength(); i++) {
                modlueList.put(attributes.getLocalName(i), attributes.getValue(i));
            }
            Module module;
            switch (modlueList.get("moduleState")) {
                case "1":
                    if (KnownSystem.getKnownSystem().knownModuleList.get(MathTools.changeIntoInt(modlueList.get("moduleType"))) != null) {
                        module = KnownSystem.getKnownSystem().knownModuleList.get(MathTools.changeIntoInt(modlueList.get("moduleType")));
                        module.setName(modlueList.get("name") + ":" + modlueList.get("location"));
                    } else {
                        module = new ModuleDefaultCtrl();
                        module.setName(modlueList.get("name") + ":" + modlueList.get("location"));
                        module.setId(MathTools.changeIntoInt(modlueList.get("moduleType")));
                        if (modlueList.get("cmd") != null){
                            HashMap<String, Byte> hashMapCMD = new HashMap<>();
                            String cmd = modlueList.get("cmd");
                            StringBuilder cmdName = new StringBuilder();
                            StringBuilder cmdNum = new StringBuilder();
                            boolean startCmd = false;

                            for (int i =0; i < cmd.length(); i++){
                                if (cmd.charAt(i) != ':' && !startCmd){
                                    cmdName.append(cmd.charAt(i));
                                } else if (cmd.charAt(i) == ':'){
                                    startCmd = true;
                                } else if (cmd.charAt(i) != ';' && startCmd){
                                    cmdNum.append(cmd.charAt(i));
                                } else if (cmd.charAt(i) == ';'){
                                    startCmd = false;
                                    hashMapCMD.put(cmdName.toString(), (byte) (MathTools.changeIntoInt(cmdNum.toString()) & 0x00ff));
                                    cmdName.delete(0,cmdName.length());
                                    cmdNum.delete(0, cmdNum.length());
                                }
                            }
                            module.setCmdHash(hashMapCMD);
                        }
                    }
                    ModuleCtrlList.getModuleCtrlList().setModuleHashMap(module.getId(), module);
                    ctrlNode.add(module.getName());
                    break;
                case "2":
//                    Log.d(TAG, "GridView: " + MathTools.changeIntoInt(modlueList.get("moduleType")));
                    if (KnownSystem.getKnownSystem().knownModuleList.get(MathTools.changeIntoInt(modlueList.get("moduleType"))) != null) {
                        module = KnownSystem.getKnownSystem().knownModuleList.get(MathTools.changeIntoInt(modlueList.get("moduleType")));
                        module.setName(modlueList.get("name") + ":" + modlueList.get("location"));
                    } else {
                        module = new ModuleDefaultVoir();
                        module.setName(modlueList.get("name") + ":" + modlueList.get("location"));
                        module.setId(MathTools.changeIntoInt(modlueList.get("moduleType")));
                    }
                    ModuleVoirList.getModuleVoirList().setModuleHashMap(module.getId(), module);
                    voirNode.add(module.getName());
                    break;
            }
        }
    }

    void setProject(String name, String value) {
        switch (name) {
            case "project_device":
                SystemConfig.getSystemConfig().setDevice(value);
                break;
            case "project_id":
                SystemConfig.getSystemConfig().setProjectID(Integer.parseInt(value));
                break;
            case "project_local_net":
                SystemConfig.getSystemConfig().setLocal_net(value);
                break;
            case "project_name":
                SystemConfig.getSystemConfig().setProjectName(value);
                break;
            case "project_namespace":
                SystemConfig.getSystemConfig().setNameSpace(value);
                break;
            case "project_outIP":
                SystemConfig.getSystemConfig().setOutIP(value);
                break;
            case "project_receiveport":
                SystemConfig.getSystemConfig().setReceivePort(Integer.parseInt(value));
                break;
            case "project_sendport":
                SystemConfig.getSystemConfig().setSendPort(Integer.parseInt(value));
                break;
            case "project_uart":
                SystemConfig.getSystemConfig().setUartPort(value);
                break;
            case "project_baud_rate":
                SystemConfig.getSystemConfig().setBaudRate(Integer.parseInt(value));
                break;
            case "project_user_id":
                SystemConfig.getSystemConfig().setUserID(value);
                break;
            case "project_mode":
                boolean isUart = false;
                boolean isSoap = false;
                boolean isUdp = false;
                isUart = value.charAt(0) == '1';
                isSoap = value.charAt(1) == '1';
                isUdp = value.charAt(2) == '1';

                SystemConfig.getSystemConfig().setProjectMode(isUart,isSoap,isUdp);
                break;
            default:
                Log.d(TAG, "ERROR" + ":" + name + ":" + value);
                break;

        }
    }


}
