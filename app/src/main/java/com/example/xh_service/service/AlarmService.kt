package com.example.xh_service.service

import android.app.*
import android.content.Context
import android.content.Intent
import android.os.IBinder
import android.os.Build
import androidx.annotation.Nullable
import com.blankj.utilcode.BuildConfig
import com.blankj.utilcode.util.LogUtils
import com.blankj.utilcode.util.TimeUtils
import java.util.*


class AlarmService : Service() {

   override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
       if (Build.VERSION.SDK_INT >= 26) {
           val CHANNEL_ID = UUID.randomUUID().toString()
           val CHANNEL_NAME = "XH_SERVICE_TEST${Date().time}"
           val channel = NotificationChannel(CHANNEL_ID, CHANNEL_NAME, NotificationManager.IMPORTANCE_DEFAULT)
           (getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager).createNotificationChannel(channel)
           val notification: Notification = Notification.Builder(this, CHANNEL_ID)
               .setContentTitle("Alarm")
               .setContentText("Alarm").build()
           startForeground(10, notification)
       }

        restartService() // 启动定时器，定时拉活
        return START_STICKY
    }

    override fun onDestroy() {
        super.onDestroy()
        stopForeground(true)
    }


    private fun restartService() {
        val intent = Intent(this, AlarmService::class.java)
        intent.action = "com.example.action.RESTART_SERVICE"
        val pendingIntent = PendingIntent.getService(this, REQUEST_CODE, intent, PendingIntent.FLAG_MUTABLE)
        val alarmManager = getSystemService(ALARM_SERVICE) as AlarmManager?
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            alarmManager?.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, System.currentTimeMillis() + INTERVAL, pendingIntent)
        } else {
            alarmManager?.setExact(AlarmManager.RTC_WAKEUP, System.currentTimeMillis() + INTERVAL, pendingIntent)
        }

        LogUtils.e("${BuildConfig.APPLICATION_ID}_${TimeUtils.millis2String(Date().time,"yyyy-MM-dd HH-mm-ss")}:SDK执行拉活任务")
    }

    @Nullable
   override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    companion object {
        private const val REQUEST_CODE = 1001
        private const val INTERVAL = 10 * 1000 // 拉活间隔时间，单位：毫秒
    }
}
