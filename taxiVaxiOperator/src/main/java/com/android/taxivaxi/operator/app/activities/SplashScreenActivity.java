package com.android.taxivaxi.operator.app.activities;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.Window;

import com.android.taxivaxi.operator.app.R;

public class SplashScreenActivity extends Activity {

	// Splash screen timer
	private static int SPLASH_TIME_OUT = 3000;

	private SharedPreferences UserInfo, myPref;
	private Boolean isLogin;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_splash_screen);

		UserInfo = this.getSharedPreferences("UserInfo", Context.MODE_PRIVATE);
		myPref = getSharedPreferences("My_PREF", MODE_PRIVATE);

		isLogin = UserInfo.getBoolean("isLogin", false);

		SharedPreferences.Editor edit = myPref.edit();
		edit.putBoolean("ServRun", false);
		edit.putLong("milliseconds", 0);
		edit.putString("timerstopped", "");
		edit.putString("launchScreen", "main");
		edit.putString("request_id", "");
		edit.commit();

		callhandler();

	}

	private void callhandler() {
		new Handler().postDelayed(new Runnable() {

			@Override
			public void run() {

				Intent i = null;
				if (isLogin) {
					i = new Intent(SplashScreenActivity.this, HomeActivity.class);
				} else {
					i = new Intent(SplashScreenActivity.this, LoginActivity.class);
				}
				startActivity(i);
				overridePendingTransition(R.anim.trans_left_in, R.anim.trans_left_out);
				finish();

			}
		}, SPLASH_TIME_OUT);
	}

}
