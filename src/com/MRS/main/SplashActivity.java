package com.MRS.main;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;

import com.MRS.MyRecipeStory.R;

public class SplashActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		//TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_splash);
		
		Handler hd = new Handler();
		hd.postDelayed(new Runnable() {
			
			@Override
			public void run() {
				finish();
			}
		}, 1500);
	}
}