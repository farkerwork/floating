package com.app;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;

public class FloatWindowTouchListener implements View.OnTouchListener {
    
    private WindowManager.LayoutParams layoutParams;
    private WindowManager windowManager;
    private float initialX, initialY;
    private float initialTouchX, initialTouchY;
    
    public FloatWindowTouchListener(WindowManager.LayoutParams layoutParams, 
                                   WindowManager windowManager) {
        this.layoutParams = layoutParams;
        this.windowManager = windowManager;
    }
    
    @Override
    public boolean onTouch(View view, MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                // 记录初始位置
                initialX = layoutParams.x;
                initialY = layoutParams.y;
                initialTouchX = event.getRawX();
                initialTouchY = event.getRawY();
                return true;
                
            case MotionEvent.ACTION_MOVE:
                // 计算移动距离并更新位置
                float deltaX = event.getRawX() - initialTouchX;
                float deltaY = event.getRawY() - initialTouchY;
                layoutParams.x = (int) (initialX + deltaX);
                layoutParams.y = (int) (initialY + deltaY);
                
                // 限制边界（可选）
                int maxX = view.getResources().getDisplayMetrics().widthPixels - view.getWidth();
                int maxY = view.getResources().getDisplayMetrics().heightPixels - view.getHeight();
                layoutParams.x = Math.max(0, Math.min(layoutParams.x, maxX));
                layoutParams.y = Math.max(0, Math.min(layoutParams.y, maxY));
                
                // 更新视图位置
                windowManager.updateViewLayout(view, layoutParams);
                return true;
                
            case MotionEvent.ACTION_UP:
                // 可以在这里添加点击事件判断
                if (Math.abs(event.getRawX() - initialTouchX) < 10 && 
                    Math.abs(event.getRawY() - initialTouchY) < 10) {
                    // 这是点击事件
                    view.performClick();
                }
                return true;
        }
        return false;
    }
}