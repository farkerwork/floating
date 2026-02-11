package com.app;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;
import android.widget.Toast;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import java.util.ArrayList;
import java.util.List;

public class getpermission
 {
 
    
	public static Activity ckActivity = null;
    // 所有权限常量定义
    public static final String[] ALL_PERMISSIONS = {
        // 存储权限
        "Manifest.permission.READ_EXTERNAL_STORAGE",
        "Manifest.permission.WRITE_EXTERNAL_STORAGE",
        "Manifest.permission.MANAGE_EXTERNAL_STORAGE",
        
        // Android 13+ 媒体权限
        "Manifest.permission.READ_MEDIA_AUDIO",
        "Manifest.permission.READ_MEDIA_IMAGES",
        "Manifest.permission.READ_MEDIA_VIDEO",
        
        // 相机权限
        "Manifest.permission.CAMERA",
        
        // 位置权限
        "Manifest.permission.ACCESS_FINE_LOCATION",
        "Manifest.permission.ACCESS_COARSE_LOCATION",
        
        // 联系人权限
        "Manifest.permission.READ_CONTACTS",
        "Manifest.permission.WRITE_CONTACTS",
        
        // 电话权限
        "Manifest.permission.READ_PHONE_STATE",
        "Manifest.permission.CALL_PHONE",
        
        // 短信权限
        "Manifest.permission.SEND_SMS",
        "Manifest.permission.READ_SMS",
        "Manifest.permission.RECEIVE_SMS",
        
        // 日历权限
        "Manifest.permission.READ_CALENDAR",
        "Manifest.permission.WRITE_CALENDAR",
        
        // 传感器权限
        "Manifest.permission.BODY_SENSORS",
        
        // 系统设置权限
        "Manifest.permission.WRITE_SETTINGS",
        
        // 通知权限
        "Manifest.permission.POST_NOTIFICATIONS",
        
        // 网络权限
        "Manifest.permission.ACCESS_WIFI_STATE",
        "Manifest.permission.CHANGE_WIFI_STATE",
        "Manifest.permission.ACCESS_NETWORK_STATE",
        "Manifest.permission.CHANGE_NETWORK_STATE",
        
        // 蓝牙权限
        "Manifest.permission.BLUETOOTH",
        "Manifest.permission.BLUETOOTH_ADMIN",
        "Manifest.permission.BLUETOOTH_CONNECT",
        "Manifest.permission.BLUETOOTH_SCAN",
        
        // NFC权限
        "Manifest.permission.NFC",
        
        // 音频录制权限
        "Manifest.permission.RECORD_AUDIO",
        
        // 活动识别权限
        "Manifest.permission.ACTIVITY_RECOGNITION",
        
        // 悬浮窗权限
        "Manifest.permission.SYSTEM_ALERT_WINDOW"
    };
    
    
    public static final String[] MY_PERMISSIONS = {
        // ==================== 存储权限组 ====================
        /** Android 13以下：读取外部存储 */
        "android.permission.READ_EXTERNAL_STORAGE",
        /** Android 13以下：写入外部存储 */
        "android.permission.WRITE_EXTERNAL_STORAGE",
        
		//以高采样率访问传感器数据
		"android.permission.HIGH_SAMPLING_RATE_SENSORS",
		
		//运行前台服务
		"android.permission.FOREGROUND_SERVICE",
		
		//此应用可显示在其他应用上方 悬浮窗
		"android.permission.SYSTEM_ALERT_WINDOW"
        
   
     
    };
    
    
    
    
    
    	public static void ts(String str)
	{
		Toast.makeText(ckActivity, str, Toast.LENGTH_SHORT).show();
	};
    
    
    
    
    
    /**
     * 检查单个权限是否已授权
     */
    public static boolean hasPermission(Activity activity, String permission)
     {
        ckActivity=activity;
        return ContextCompat.checkSelfPermission(activity, permission) == PackageManager.PERMISSION_GRANTED;
    }
    
    /**
     * 检查多个权限是否已授权
     */
    public static boolean hasPermissions(Activity activity, String[] permissions)
     {
        ckActivity=activity;
        for (String permission : permissions) {
            if (!hasPermission(activity, permission)) {
                return false;
            }
        }
        return true;
    }
    
    /**
     * 请求单个权限
     */
    public static void requestPermission(Activity activity, String permission, int requestCode)
     {
        ckActivity=activity;
        if (!hasPermission(activity, permission)) {
            ActivityCompat.requestPermissions(activity, new String[]{permission}, requestCode);
        } else {
            Toast.makeText(activity, "权限已授权: " + permission, Toast.LENGTH_SHORT).show();
        }
    }
    
    /**
     * 请求多个权限
     */
    public static void requestPermissions(Activity activity, String[] permissions, int requestCode) 
    {
        ckActivity=activity;
        List<String> permissionsToRequest = new ArrayList<>();
        for (String permission : permissions) {
            if (!hasPermission(activity, permission)) {
                permissionsToRequest.add(permission);
            }
        }
        
        if (!permissionsToRequest.isEmpty()) {
            ActivityCompat.requestPermissions(activity, 
                permissionsToRequest.toArray(new String[0]), requestCode);
        } else {
            Toast.makeText(activity, "所有权限均已授权", Toast.LENGTH_SHORT).show();
        }
    }
    
    /**
     * 请求所有权限
     */
    public static void requestAllPermissions(Activity activity, int requestCode) {
         ckActivity=activity;
        requestPermissions(activity, MY_PERMISSIONS, requestCode);
    }
    
    /**
     * 检查并请求存储相关权限
     */
    public static void requestStoragePermissions(Activity activity, int requestCode) {
        ckActivity=activity;
        String[] storagePermissions;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            // Android 11+ 需要 MANAGE_EXTERNAL_STORAGE
            storagePermissions = new String[]{
                Manifest.permission.MANAGE_EXTERNAL_STORAGE,
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE
            };
        } else {
            // Android 10及以下
            storagePermissions = new String[]{
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE
            };
        }
        requestPermissions(activity, storagePermissions, requestCode);
    }
    
    /**
     * 检查并请求媒体相关权限 (Android 13+)
     */
    public static void requestMediaPermissions(Activity activity, int requestCode) {
        ckActivity=activity;
        if (Build.VERSION.SDK_INT >= 33) {
            String[] mediaPermissions = {
                "Manifest.permission.READ_MEDIA_AUDIO",
                "Manifest.permission.READ_MEDIA_IMAGES",
                "Manifest.permission.READ_MEDIA_VIDEO"
            };
            requestPermissions(activity, mediaPermissions, requestCode);
        }
    }
    
    /**
     * 处理权限请求结果
     */
    public static void onRequestPermissionsResult(Activity activity, int requestCode, 
                                                 String[] permissions, int[] grantResults) {
        for (int i = 0; i < permissions.length; i++) {
            if (grantResults[i] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(activity, "权限授权成功: " + permissions[i], Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(activity, "权限被拒绝: " + permissions[i], Toast.LENGTH_SHORT).show();
            }
        }
    }
    
    /**
     * 获取未授权的权限列表
     */
    public static List<String> getDeniedPermissions(Activity activity) {
        ckActivity=activity;
        List<String> deniedPermissions = new ArrayList<>();
        for (String permission : MY_PERMISSIONS) {
            if (!hasPermission(activity, permission)) {
                deniedPermissions.add(permission);
            }
        }
        return deniedPermissions;
    }
    
    /**
     * 显示权限状态信息
     */
    public static void showPermissionStatus(Activity activity) {
        StringBuilder status = new StringBuilder("权限状态:\n");
        for (String permission : MY_PERMISSIONS) {
            status.append(permission)
                  .append(": ")
                  .append(hasPermission(activity, permission) ? "已授权" : "未授权")
                  .append("\n");
        }
        // 添加悬浮窗权限状态
        status.append("悬浮窗权限: ")
              .append(canDrawOverlays(activity) ? "已授权" : "未授权");
        Toast.makeText(activity, status.toString(), Toast.LENGTH_LONG).show();
    }
    
    /**
     * 检查悬浮窗权限是否已授权
     */
    public static boolean canDrawOverlays(Activity activity) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            return Settings.canDrawOverlays(activity);
        }
        return true; // Android 6.0以下默认有悬浮窗权限
    }
    
    /**
     * 请求悬浮窗权限
     */
    public static void requestOverlayPermission(Activity activity, int requestCode)
     {
        ckActivity=activity;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (!canDrawOverlays(activity)) {
                Intent intent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION,
                    Uri.parse("package:" + activity.getPackageName()));
                activity.startActivityForResult(intent, requestCode);
            } else {
                //Toast.makeText(activity, "悬浮窗权限已授权", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(activity, "当前Android版本无需申请悬浮窗权限", Toast.LENGTH_SHORT).show();
        }
    }
    
    /**
     * 处理悬浮窗权限请求结果
     */
    public static void onOverlayPermissionResult(Activity activity, int requestCode) {
        if (canDrawOverlays(activity)) {
            Toast.makeText(activity, "悬浮窗权限授权成功", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(activity, "悬浮窗权限被拒绝", Toast.LENGTH_SHORT).show();
        }
    }
}
