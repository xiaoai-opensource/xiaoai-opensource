package com.luoaz.myfloat;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;


import com.luoaz.myfloat.activity.SmallFloatWin;

import android.app.ActivityManager;
import android.content.Context;
import android.graphics.PixelFormat;
import android.view.Gravity;
import android.view.WindowManager;
import android.view.WindowManager.LayoutParams;

public class MyManager {
	private static String tag = "MyManager";
	private static MyManager mMyManager ;
	private static WindowManager mWindowManager;
	private static SmallFloatWin mSmallFloatWin;
	private static LayoutParams smallWindowParams;
	private static ActivityManager mActivityManager;

	//sigleton
	public  MyManager getMyManager(){
		if(mMyManager==null){
			mMyManager = new MyManager();
		}

		return mMyManager;
	}
	/**
	 * create small float window
	 * */
	public static void createSmallFloat(Context context){

		WindowManager windowManager = getWindowManager(context); 
		int screenWidth = windowManager.getDefaultDisplay().getWidth();
		int screenHeight = windowManager.getDefaultDisplay().getHeight();

		if(mSmallFloatWin==null){
			mSmallFloatWin = new SmallFloatWin(context);
			if (smallWindowParams == null) {  
				smallWindowParams = new LayoutParams();  
				smallWindowParams.type = LayoutParams.TYPE_PHONE;  
				smallWindowParams.format = PixelFormat.RGBA_8888;  
				smallWindowParams.flags = LayoutParams.FLAG_NOT_TOUCH_MODAL  
						| LayoutParams.FLAG_NOT_FOCUSABLE;  
				smallWindowParams.gravity = Gravity.LEFT | Gravity.TOP;  
				smallWindowParams.width = SmallFloatWin.viewWidth;  
				smallWindowParams.height = SmallFloatWin.viewHeight;  
				smallWindowParams.x = screenWidth;  
				smallWindowParams.y = screenHeight / 2;  
			}  
			mSmallFloatWin.setParams(smallWindowParams);
			windowManager.addView(mSmallFloatWin, smallWindowParams);  
		}
	}
	
	/**
	 * 更新悬浮框内容
	 * */
	public static void updateSamllFloat(Context context){
		
		mSmallFloatWin.updateContent(getUsedPercentValue(context));
	}
	/**
	 * 获取WindowManager
	 * singleton
	 * */
	public static WindowManager getWindowManager(Context context) {  
		if (mWindowManager == null) {  
			mWindowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);  
		}  
		return mWindowManager;  
	} 

	/** 
	 * 计算已使用内存的百分比，并返回。 
	 *  
	 * @param context 
	 *            可传入应用程序上下文。 
	 * @return 已使用内存的百分比，以字符串形式返回。 
	 */  
	public static String getUsedPercentValue(Context context) {  
		String dir = "/proc/meminfo";  
		try {  
			FileReader fr = new FileReader(dir);  
			BufferedReader br = new BufferedReader(fr, 2048);  
			String memoryLine = br.readLine();  
			String subMemoryLine = memoryLine.substring(memoryLine.indexOf("MemTotal:"));  
			br.close();  
			long totalMemorySize = Integer.parseInt(subMemoryLine.replaceAll("\\D+", ""));  
			long availableSize = getAvailableMemory(context) / 1024;  
			int percent = (int) ((totalMemorySize - availableSize) / (float) totalMemorySize * 100);  
			return percent + "%";  
		} catch (IOException e) {  
			e.printStackTrace();  
		}  
		return "悬浮窗";  
	}  

	/** 
	 * 获取当前可用内存，返回数据以字节为单位。 
	 *  
	 * @param context 
	 *            可传入应用程序上下文。 
	 * @return 当前可用内存。 
	 */  
	public static long getAvailableMemory(Context context) {  
		ActivityManager.MemoryInfo mi = new ActivityManager.MemoryInfo();  
		getActivityManager(context).getMemoryInfo(mi);  
		return mi.availMem;  
	}  

	/** 
	 * 如果ActivityManager还未创建，则创建一个新的ActivityManager返回。否则返回当前已创建的ActivityManager。 
	 *  
	 * @param context 
	 *            可传入应用程序上下文。 
	 * @return ActivityManager的实例，用于获取手机可用内存。 
	 */  
	public static ActivityManager getActivityManager(Context context) {  
		if (mActivityManager == null) {  
			mActivityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);  
		}  
		return mActivityManager;  
	} 
	
   
	/**
     * 更新位置
     * */    
    public static void updatePosition(float x,float y){
    	
    	smallWindowParams.x = (int) x;  
		smallWindowParams.y = (int) y;  
		mWindowManager.updateViewLayout(mSmallFloatWin, smallWindowParams);
    }
    
    /**
     * 信任的后台程序，保证不被杀死
     * */
    public static boolean isTrust(String packageName){
    	
    	boolean isTrust = false;
    	
    	List<String> trustList = new ArrayList<String>();
    	
    	trustList.add("com.luoaz");
    	trustList.add("com.dianxinos.clock");
    	trustList.add("com.sohu.inputmethod.sogou");
    	trustList.add("com.sonyericsson.usbux");
    	
    	for(int i=0;i<trustList.size();i++){
    		if(packageName.startsWith(trustList.get(i))){
    			isTrust = true;
    		}
    	}
    	
    	
    	return isTrust;
    	
    }
    
    /**
	 * 执行Shell命令
	 * @param cmd 要执行的命令
	 * */
	public static String runShell(String[] cmd){
		String strResult = "";
		InputStream in = null;
		StringBuffer result = new StringBuffer(); 
		ProcessBuilder builder = new ProcessBuilder(cmd); 
		builder.redirectErrorStream(true);
		Process process;
		try {
			process = builder.start();
			in = process.getInputStream();
			byte[] re = new byte[1024]; 

			while (in.read(re) != -1) { 
				result = result.append(new String(re)); 
			}
			
		} catch (IOException e) {
			e.printStackTrace();
		}finally{
			
			if(in!=null){
				try {
					in.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}

		return strResult;
	}

}
