package com.example.xh_service.utils;

public class NetWorkCallBackUtils {

    NetWorkCallBack netWorkCallBack;
    private static NetWorkCallBackUtils init;

    public static NetWorkCallBackUtils instance(){ return init == null ?  new NetWorkCallBackUtils() : init; }

    /**
     * @param isAvailable 网络状态---是否可用
     */
    public void changeNetWorkStateCallBack(Boolean isAvailable){
        if (netWorkCallBack != null){
            netWorkCallBack.callBack(isAvailable);
        }
    }

    public void setCallBack(NetWorkCallBack value){
        this.netWorkCallBack = value;
    }
}
