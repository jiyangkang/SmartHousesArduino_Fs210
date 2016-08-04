package com.hqyj.dev.smarthousesarduino.modules.operates;

import com.hqyj.dev.smarthousesarduino.tools.StringTools;

/**
 * Created by jiyangkang on 2016/6/3 0003.
 */
public class FillDefaultDatas implements FillDatas {


    @Override
    public String fillData(byte[] datas) {
        return String.format("%s:%s", "数据位",StringTools.changeIntoHexString(datas, true));
    }

}
