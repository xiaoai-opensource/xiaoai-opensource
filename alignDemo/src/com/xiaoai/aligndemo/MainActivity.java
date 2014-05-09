package com.xiaoai.aligndemo;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class MainActivity extends Activity {
	
	private Button btnTextAlign = null;
	private Button btnLinearLayout=null;
	private Button btnRelativeLayout= null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		init();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	
	private void init(){
		btnTextAlign = (Button)findViewById(R.id.btnTextAlign);
		btnTextAlign.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(MainActivity.this, TextAlignActivity.class);
				startActivity(intent);
				
			}
		});
		
		
		btnLinearLayout = (Button)findViewById(R.id.btnLinearLayout);
		btnLinearLayout.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(MainActivity.this, LinearLayoutActivity.class);
				startActivity(intent);
			}
		});
				
		
		btnRelativeLayout = (Button)findViewById(R.id.btnRelativeLayout);
		btnRelativeLayout.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {

				Intent intent = new Intent(MainActivity.this,RelativeLayoutActivity.class);
				startActivity(intent);
			}
		});
	}

}
