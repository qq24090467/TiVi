package com.android.taxivaxi.operator.app.activities;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

public class LauncherActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		Intent intent = null;

		SharedPreferences myPref = getSharedPreferences("My_PREF", MODE_PRIVATE);

		if (myPref.getString("launchScreen", "main").equalsIgnoreCase("push")) {
			intent = new Intent(LauncherActivity.this, NewOrderComingActivity.class);
		} else {
			intent = new Intent(LauncherActivity.this, SplashScreenActivity.class);
		}
		startActivity(intent);
		finish();

	}
}
