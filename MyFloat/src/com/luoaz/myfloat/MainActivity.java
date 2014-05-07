package com.luoaz.myfloat;

import com.luoaz.myfloat.service.FloatWinService;

import android.os.Bundle;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class MainActivity extends BaseActivity {
	private Button startSmallFloat;		//启动悬浮

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		init();
		//finish();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		return true;
	}
	@Override
	protected void onDestroy() {
		super.onDestroy();
	}
	/**
	 * 初始化一些信息
	 * */
	private void init(){
		//启动悬浮框
		startSmallFloat = (Button)findViewById(R.id.startSmallFloat);
		Intent startServiceIntent = new Intent(MainActivity.this, FloatWinService.class);
		startService(startServiceIntent);

		startSmallFloat.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				Intent startServiceIntent = new Intent(MainActivity.this, FloatWinService.class);
				startService(startServiceIntent);
			}
		});		
	}

	

}
