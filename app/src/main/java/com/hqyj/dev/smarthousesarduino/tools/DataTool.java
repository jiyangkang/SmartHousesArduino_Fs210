package com.hqyj.dev.smarthousesarduino.tools;

import java.util.concurrent.ArrayBlockingQueue;

/**
 *
 * Created by jiyangkang on 2016/6/3 0003.
 */
public class DataTool {

    public static final byte HEAD = 0x00;
    public static final byte LENGTH = 0x01;
    public static final byte OFFSET = 0x02;
    public static final byte DATATYPE = 0x03;
    public static final byte NETTYPE = 0x04;
    public static final byte DEVICEADDR_H = 0x05;
    public static final byte DEVICEADDR_L = 0x06;
    public static final byte DEVICETYPE = 0x07;
    public static final byte DATA = 0x08;
    public static final byte MATA = 0x09;


    public static final byte HEAD_RECEIVE = 0x21;
    public static final byte CTRLDATA = 0x01;
    public static final byte NORMALDATA = 0x00;


    public static final int ERROR = -9999;
    public static final byte[] ENDTHREAD = new byte[]{0x7F,0x7F};

    public static  final byte[] protocal = {HEAD_RECEIVE,LENGTH,OFFSET,DATATYPE,
            NETTYPE, DEVICEADDR_H,DEVICEADDR_L,DEVICETYPE, DATA, MATA};

    public static final byte[] ERRORCMD = new byte[]{'E', 'R', 'R', 'O', 'R'};
    public static final String ERRORINPUT = "ERRORINPUT";

    public static ArrayBlockingQueue<byte[]> gets = new ArrayBlockingQueue<>(512);
    public static ArrayBlockingQueue<byte[]> sends = new ArrayBlockingQueue<>(512);
    public static ArrayBlockingQueue<byte[]> sendSoap = new ArrayBlockingQueue<>(3600);


}
