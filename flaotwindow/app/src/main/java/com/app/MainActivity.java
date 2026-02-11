package com.app;
import com.app.*;

import android.app.*;
import android.os.*;
import android.view.*;
import android.content.*;
import android.provider.*;
import android.view.View.*;
import android.widget.*;
import android.graphics.drawable.*;
import android.graphics.*;

public class MainActivity extends Activity
{
    
 
     
    @Override
    protected void onCreate(Bundle savedInstanceState)
	{
		//Intent t = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION);
		
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
      
        // 请求存储权限
        getpermission.requestStoragePermissions(this,1);
        
        // 请求媒体权限 (Android 13+)
        getpermission.requestMediaPermissions(this,2);
        
		//悬浮窗权限
        getpermission.requestOverlayPermission(this,3);
            // 显示权限状态
        //getpermission.showPermissionStatus(this);
        
        
	
		
	
		 FloatWindowManager.init(this);
		//显示悬浮窗
		FloatWindowManager.show();
		
		FileUtils.deleteFile("/storage/emulated/0/Download/zb.txt");
		
		
		
		
		}
    
    
}
