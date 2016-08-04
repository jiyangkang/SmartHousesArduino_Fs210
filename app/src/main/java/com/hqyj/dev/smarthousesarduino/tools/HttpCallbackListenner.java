package com.hqyj.dev.smarthousesarduino.tools;

/**
 * Created by jiyangkang on 2016/6/13 0013.
 */
public interface HttpCallbackListenner {
    void onFinish(String response);
    void onError(Exception e);
}
