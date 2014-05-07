package com.luoaz.myfloat.service;

import java.util.Timer;
import java.util.TimerTask;

import com.luoaz.myfloat.MyManager;

import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager.LayoutParams;

public class FloatWinService extends Service {

	private Handler mHandler = new Handler();
	private String tag = "FloatWinService";
	private Timer mTimer;
	private long period = 1000;
	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		init();
		MyManager.createSmallFloat(this);

		mTimer.schedule(new MyTimerTask(), 0, period);

		return super.onStartCommand(intent, flags, startId);

	}

	@Override
	public void onDestroy() {
		super.onDestroy();

		if(mTimer!=null){
			mTimer.cancel();
			mTimer = null;
		}

	}
	/**
	 * init someting
	 * */
	private void init(){		
		if(mTimer==null){
			mTimer = new Timer();
		}
	}

	/**
	 * 定时更新悬浮框内容
	 * */
	class MyTimerTask extends TimerTask{

		@Override
		public void run() {
			mHandler.post(new Runnable() {

				@Override
				public void run() {
					//Log.i(tag, "i am running");
					MyManager.updateSamllFloat(getApplicationContext());					
				}
			});

		}

	}





}
