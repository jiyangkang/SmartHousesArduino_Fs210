LOCAL_PATH:=$(call my-dir)

include $(CLEAR_VARS)

LOCAL_MODULE        := uart_smart
LOCAL_SRC_FILES     := uart.cpp

LOCAL_LDLIBS        := -llog

include $(BUILD_SHARED_LIBRARY)
