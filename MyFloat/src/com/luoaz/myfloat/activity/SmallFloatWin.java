package com.luoaz.myfloat.activity;

import java.lang.reflect.Field;
import java.util.List;

import com.luoaz.myfloat.MyManager;
import com.luoaz.myfloat.R;

import android.R.color;
import android.app.ActivityManager;
import android.content.Context;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;


public class SmallFloatWin extends LinearLayout {
	private String tag = "SmallFloatWin";
	private Context mContext;

	private WindowManager mWindowManager;
	private TextView tvContent;
	private View smallFloat;

	private int rawX;
	private int rawY;
	private int xInView = 0;
	private int yInView = 0;

	private int rawX1,rawX2,rawY1,rawY2;

	private WindowManager.LayoutParams mParams;
	public SmallFloatWin(Context context) {
		super(context);
		mContext = context;
		LayoutInflater.from(context).inflate(R.layout.small_float_layout, this);
		tvContent = (TextView)findViewById(R.id.content);

		smallFloat = findViewById(R.id.small_float_layout);

		viewHeight = smallFloat.getLayoutParams().height;
		viewWidth = smallFloat.getLayoutParams().width;

		tvContent.setText(MyManager.getUsedPercentValue(context));

	}



	/**
	 * 悬浮框触摸事件监听
	 * */
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		switch (event.getAction()) {
		case MotionEvent.ACTION_DOWN:
			xInView = (int) event.getX();
			yInView = (int) event.getY();

			rawX1 = (int) event.getRawX();
			rawY1 = (int) event.getRawY();

			break;

		case MotionEvent.ACTION_MOVE:

			rawX = (int) event.getRawX();
			rawY = (int) (event.getRawY() - getStatusBarHeight());
			MyManager.updatePosition(rawX-xInView, rawY-yInView);
			break;
		case MotionEvent.ACTION_UP:

			rawX2 = (int) event.getRawX();
			rawY2 = (int) event.getRawY();

			if(rawX2 == rawX1 && rawY2 == rawY1){

				smallFloat.setBackgroundResource(R.drawable.bg_small_green);

				ActivityManager mActivityManager = MyManager.getActivityManager(mContext);
				List<ActivityManager.RunningAppProcessInfo> process = mActivityManager.getRunningAppProcesses();

				for(int i=0;i<process.size();i++){
					ActivityManager.RunningAppProcessInfo ar = process.get(i);
					String packageName = ar.processName;
					packageName = packageName.split(":")[0];
					//重要级别大于200，并且不被信任的后台将被杀掉
					if(ar.importance>100 && !MyManager.isTrust(packageName)){
						MyManager.getActivityManager(mContext).killBackgroundProcesses(packageName);
					}
				}
				
				Handler mHandler = new Handler();				
				mHandler.postDelayed(new Runnable() {
					
					@Override
					public void run() {
						smallFloat.setBackgroundResource(R.drawable.bg_small);
					}
				}, 500);
			}

			break;

		default:
			break;
		}

		return super.onTouchEvent(event);
	}


	/** 
	 * 记录大悬浮窗的宽度 
	 */  
	public static int viewWidth;  

	/** 
	 * 记录大悬浮窗的高度 
	 */  
	public static int viewHeight;  

	/** 
	 * 将小悬浮窗的参数传入，用于更新小悬浮窗的位置。 
	 *  
	 * @param params 
	 *            小悬浮窗的参数 
	 */  
	public void setParams(WindowManager.LayoutParams params) {  
		mParams = params;  
	} 
	/**
	 * 更新悬浮小窗口
	 * */
	public void updateContent(String content){
		tvContent = (TextView)findViewById(R.id.content);
		tvContent.setText(content);
	}

	/** 
	 * 用于获取状态栏的高度。 
	 *  
	 * @return 返回状态栏高度的像素值。 
	 */  
	private int getStatusBarHeight() {  
		int statusBarHeight = 0;
		if (statusBarHeight == 0) {  
			try {  
				Class<?> c = Class.forName("com.android.internal.R$dimen");  
				Object o = c.newInstance();  
				Field field = c.getField("status_bar_height");  
				int x = (Integer) field.get(o);  
				statusBarHeight = getResources().getDimensionPixelSize(x);  
			} catch (Exception e) {  
				e.printStackTrace();  
			}  
		}  
		return statusBarHeight;  
	}

}
