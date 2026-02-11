package com.app;
import android.content.Context;
import android.graphics.Color;
import android.graphics.PixelFormat;
import android.os.Handler;
import android.os.Looper;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;

public class FloatWindowManager {
    
    private static Context ctx; // 应用上下文
    private static WindowManager windowManager;
    private static View floatView; // 悬浮窗根视图
    private static Switch switchControl; // Switch控件引用
    private static TextView statusText; // 状态文本引用
    private static boolean isShowing = false;
    
    // 初始化
    public static void init(Context context) {
        ctx = context.getApplicationContext();
    }
    
    // 显示悬浮窗
    public static void show() {
        if (isShowing || ctx == null) return;
        
        new Handler(Looper.getMainLooper()).post(new Runnable() {
            @Override
            public void run() {
                createFloatView();
                addToWindow();
                isShowing = true;
            }
        });
    }
    
    // 创建悬浮窗视图
    private static void createFloatView() {
        // 创建根布局
        LinearLayout container = new LinearLayout(ctx);
        container.setOrientation(LinearLayout.VERTICAL);
        container.setBackgroundColor(Color.parseColor("#CC1E88E5")); // 半透明蓝色
        container.setPadding(dpToPx(16), dpToPx(16), dpToPx(16), dpToPx(16));
        container.setMinimumWidth(dpToPx(150));
        
        // 创建标题
        TextView title = new TextView(ctx);
        title.setText("悬浮控制面板");
        title.setTextSize(18);
        title.setTextColor(Color.WHITE);
        title.setGravity(Gravity.CENTER);
        title.setPadding(0, 0, 0, dpToPx(8));
        container.addView(title, new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT));
        
        // 创建分割线
        View divider = new View(ctx);
        divider.setBackgroundColor(Color.parseColor("#66FFFFFF"));
        container.addView(divider, new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                dpToPx(1)));
        
        // 创建开关控件并保持对象引用
        switchControl = new Switch(ctx);
        switchControl.setText("启用功能");
        switchControl.setTextColor(Color.WHITE);
        switchControl.setChecked(true);
        switchControl.setPadding(0, dpToPx(8), 0, dpToPx(8));
        
        // 设置开关监听
        switchControl.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                updateStatus(isChecked);
            }
        });
        
        container.addView(switchControl, new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT));
        
        // 创建状态显示文本并保持对象引用
        statusText = new TextView(ctx);
        statusText.setText("状态: 已开启");
        statusText.setTextSize(14);
        statusText.setTextColor(Color.WHITE);
        statusText.setPadding(0, dpToPx(8), 0, 0);
        container.addView(statusText, new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT));
        
        // 创建关闭按钮
        TextView closeBtn = new TextView(ctx);
        closeBtn.setText("× 关闭");
        closeBtn.setTextSize(16);
        closeBtn.setTextColor(Color.WHITE);
        closeBtn.setGravity(Gravity.CENTER);
        closeBtn.setPadding(dpToPx(8), dpToPx(8), dpToPx(8), dpToPx(8));
        closeBtn.setBackgroundColor(Color.parseColor("#FF3F51B5"));
        closeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hide();
            }
        });
        
        LinearLayout.LayoutParams closeParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);
        closeParams.gravity = Gravity.CENTER_HORIZONTAL;
        closeParams.topMargin = dpToPx(12);
        container.addView(closeBtn, closeParams);
        
        floatView = container;
    }
    
    // 更新状态显示
    private static void updateStatus(boolean isEnabled) {
        if (statusText != null) {
            statusText.setText(isEnabled ? "状态: 已开启" : "状态: 已关闭");
            
            // 可以在这里添加更多状态变化的逻辑
            if (switchControl != null) {
                switchControl.setText(isEnabled ? "功能已启用" : "功能已禁用");
            }
        }
    }
    
    // 添加悬浮窗到窗口管理器
    private static void addToWindow() {
        if (windowManager == null) {
            windowManager = (WindowManager) ctx.getSystemService(Context.WINDOW_SERVICE);
        }
        
        // 设置布局参数
        WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams(
                WindowManager.LayoutParams.WRAP_CONTENT,
                WindowManager.LayoutParams.WRAP_CONTENT,
                WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY, // API 26+
                WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE,
                PixelFormat.TRANSLUCENT);
        
        layoutParams.gravity = Gravity.TOP | Gravity.START;
        layoutParams.x = dpToPx(20);
        layoutParams.y = dpToPx(100);
        
        // 设置触摸监听器（拖拽移动功能）
        floatView.setOnTouchListener(new FloatWindowTouchListener(layoutParams, windowManager));
        
        // 添加视图到窗口
        windowManager.addView(floatView, layoutParams);
    }
    
    // 隐藏悬浮窗
    public static void hide() {
        if (!isShowing || floatView == null) return;
        
        new Handler(Looper.getMainLooper()).post(new Runnable() {
            @Override
            public void run() {
                if (windowManager != null && floatView != null) {
                    windowManager.removeView(floatView);
                    isShowing = false;
                    cleanup();
                }
            }
        });
    }
    
    // 切换开关状态（外部调用示例）
    public static void toggleSwitch() {
        if (switchControl != null) {
            boolean newState = !switchControl.isChecked();
            switchControl.setChecked(newState);
            updateStatus(newState);
        }
    }
    
    // 获取当前开关状态
    public static boolean isSwitchEnabled() {
        return switchControl != null && switchControl.isChecked();
    }
    
    // 更新状态文本（外部调用示例）
    public static void setStatusMessage(String message) {
        if (statusText != null) {
            statusText.setText("状态: " + message);
        }
    }
    
    // 清理资源
    private static void cleanup() {
        switchControl = null;
        statusText = null;
        floatView = null;
    }
    
    // dp转px
    private static int dpToPx(int dp) {
        if (ctx == null) return dp;
        float density = ctx.getResources().getDisplayMetrics().density;
        return Math.round(dp * density);
    }
    
    // 是否正在显示
    public static boolean isShowing() {
        return isShowing;
    }
}