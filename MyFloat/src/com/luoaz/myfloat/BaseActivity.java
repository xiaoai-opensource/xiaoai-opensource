package com.luoaz.myfloat;

import android.app.Activity;

public class BaseActivity extends Activity {
	
	@Override
	protected void onDestroy() {
		super.onDestroy();
		finish();
	}

}
