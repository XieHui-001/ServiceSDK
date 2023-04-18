package com.example.xh_service.utils;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.PowerManager;
import android.os.SystemClock;
import android.provider.Settings;

import com.blankj.utilcode.util.ToastUtils;

import java.util.Locale;

public class AlarmUtils {
    private AlarmManager alarmMgr;
    private PowerManager.WakeLock wakeLock;
    private static AlarmUtils init;
    private PendingIntent pendingIntent;
    Integer requestCode = 10565656;

    public static AlarmUtils instance() {
        return init == null ? new AlarmUtils() : init;
    }

    /***
     *
     * @param context 当前上下文
     * @param tClass 类
     * @param intervalMillis  执行间隔时间
     * @return 是否初始化成功
     */
    public Boolean initAlarm(Context context, Class<?> tClass, Long intervalMillis) {
        if (pendingIntent != null && alarmMgr != null) {
            alarmMgr.cancel(pendingIntent);
        }

        alarmMgr = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        if (alarmMgr != null) {
            Intent intent = new Intent(context, tClass);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            pendingIntent = PendingIntent.getActivity(context, requestCode, intent, PendingIntent.FLAG_CANCEL_CURRENT);
            alarmMgr.setRepeating(AlarmManager.RTC_WAKEUP, SystemClock.elapsedRealtime() + intervalMillis, intervalMillis, pendingIntent);
        }
        return alarmMgr != null;
    }

    /***
     * 获取当前 AlarmManager
     * @return
     */
    public AlarmManager getAlarmMgr() {
        return alarmMgr;
    }

    public void initWakeLock(Context context) {
        PowerManager powerManager = (PowerManager) context.getSystemService(context.POWER_SERVICE);
        wakeLock = powerManager.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK, context.getPackageName());
        wakeLock.acquire();
    }

    /***
     * 电池优化策略
     * @param activity
     */
    public void checkPowerOptimization(Activity activity) {
        PowerManager powerManager = (PowerManager) activity.getSystemService(Context.POWER_SERVICE);
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
            Boolean hasIgnored = powerManager.isIgnoringBatteryOptimizations(activity.getPackageName());
            String manufacturer = Build.MANUFACTURER;
            switch (manufacturer) {
                case "huawei":
                    requestHuaweiPowerOptimization(activity);
                    break;
                case "xiaomi":
                    requestXiaomiPowerOptimization(activity);
                    break;
                case "oppo":
                    requestOppoPowerOptimization(activity);
                    break;
                case "vivo":
                    requestVivoPowerOptimization(activity);
                    break;
                case "samsung":
                    requestSamsungPowerOptimization(activity);
                    break;
                // 处理其他厂商的逻辑
                default:
                    requestOtherPowerOptimization(activity);
                    break;
            }
        }
    }

    private void requestHuaweiPowerOptimization(Activity activity) {
        try {
            Intent intent = new Intent();
            intent.setClassName("com.huawei.systemmanager", "com.huawei.systemmanager.appcontrol.activity.StartupAppControlActivity");
            activity.startActivity(intent);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void requestXiaomiPowerOptimization(Activity activity) {
        try {
            Intent intent = new Intent();
            intent.setAction("miui.intent.action.OP_AUTO_START");
            intent.addCategory(Intent.CATEGORY_DEFAULT);
            activity.startActivity(intent);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void requestOppoPowerOptimization(Activity activity) {
        try {
            Intent intent = new Intent();
            intent.setClassName("com.coloros.oppoguardelf", "com.coloros.powermanager.fuelgaue.PowerUsageModelActivity");
            activity.startActivity(intent);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void requestVivoPowerOptimization(Activity activity) {
        try {
            Intent intent = new Intent();
            intent.setComponent(ComponentName.unflattenFromString("com.iqoo.secure/.ui.phoneoptimize.BgStartUpManager"));
            activity.startActivity(intent);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void requestSamsungPowerOptimization(Activity activity) {
        try {
            Intent intent = new Intent();
            intent.setComponent(ComponentName.unflattenFromString("com.samsung.android.sm_cn/.ui.SmartManagerDashBoardActivity"));
            activity.startActivity(intent);
        } catch (Exception e1) {
            try {
                Intent intent = new Intent();
                intent.setComponent(ComponentName.unflattenFromString("com.samsung.android.sm/.ui.SmartManagerDashBoardActivity"));
                activity.startActivity(intent);
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
    }

    private void requestOtherPowerOptimization(Activity activity) {
        try{
            Intent intent = new Intent();
            String packageName = activity.getPackageName();
            intent.setAction(Settings.ACTION_REQUEST_IGNORE_BATTERY_OPTIMIZATIONS);
            intent.setData(Uri.parse("package:" + packageName));
            activity.startActivity(intent);
        }catch (Exception exception){
            exception.printStackTrace();
        }
    }
}
