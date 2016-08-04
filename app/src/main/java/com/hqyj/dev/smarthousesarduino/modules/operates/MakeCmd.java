package com.hqyj.dev.smarthousesarduino.modules.operates;

/**
 *
 * Created by jiyangkang on 2016/6/3 0003.
 */
public interface MakeCmd {
    void makeCmd(byte[] cmd, byte netType,byte[] signature);
    void makeCmd(String cmd, byte netType,byte[] signature);
}
