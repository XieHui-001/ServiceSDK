package com.example.xh_service.broadcast

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.text.TextUtils
import android.widget.Toast
import android.net.ConnectivityManager
import com.example.xh_service.utils.NetWorkCallBackUtils

class AlarBroadcastReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context?, intent: Intent?) {
        if (intent!!.action == "com.example.AlarBroadcastReceiver") {
            val data = intent.getStringExtra("data")
            // 处理接收到的数据
            if (!TextUtils.isEmpty(data)){
                // TO DO
            }
        }

        // 获取网络状态
        val connectivityManager = context?.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val networkInfo = connectivityManager.activeNetworkInfo
        if (networkInfo != null && networkInfo.isAvailable) {
            // 网络连接成功
            Toast.makeText(context, "网络已连接", Toast.LENGTH_SHORT).show()
        } else {
            // 网络连接断开
            Toast.makeText(context, "网络已断开", Toast.LENGTH_SHORT).show()
        }
        NetWorkCallBackUtils.instance().changeNetWorkStateCallBack(networkInfo != null && networkInfo.isAvailable)
    }
}