package com.android.taxivaxi.operator.app.activities;

import android.app.NotificationManager;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.CountDownTimer;
import android.os.IBinder;
import android.util.Log;

import com.android.taxivaxi.operator.app.common.ApplicationConstant;
import com.android.taxivaxi.operator.app.controller.TimeOutRequestAsync;

public class MyService extends Service {

	private long milliseconds;
	private SharedPreferences myPref, UserInfo;

	public static final String TAG = "Service";
	public static final String ACTION_MyIntentService = "com.android.taxivaxi.operator.app.activities.RESPONSE";
	public static final String ACTION_MyUpdate = "com.android.taxivaxi.operator.app.activities.UPDATE";
	public static final String EXTRA_KEY_IN = "EXTRA_IN";
	public static final String EXTRA_KEY_OUT = "EXTRA_OUT";
	public static final String EXTRA_KEY_UPDATE = "EXTRA_UPDATE";
	private String msgFromActivity;
	private String extraOut;
	private String booking_id = "";
	private CountDownTimer timerTask;

	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {

		Log.d("myTime", "Service is onStartCommand.");

		myPref = getSharedPreferences("My_PREF", MODE_PRIVATE);
		UserInfo = getSharedPreferences("UserInfo", MODE_PRIVATE);

		if (intent != null && intent.getExtras() != null) {
			msgFromActivity = intent.getStringExtra(EXTRA_KEY_IN);
			booking_id = intent.getStringExtra("booking_id");

			SharedPreferences.Editor edit = myPref.edit();
			edit.putString("booking_id", booking_id);
			edit.commit();

		} else {
			msgFromActivity = "Stopped";
			booking_id = myPref.getString("booking_id", "");
		}

		extraOut = "Timer: " + msgFromActivity;

		milliseconds = myPref.getLong("milliseconds", 0);

		if (!myPref.getBoolean("ServRun", false) || milliseconds > 0) {
			SharedPreferences.Editor edit = myPref.edit();
			edit.putBoolean("ServRun", true);
			edit.commit();

			timerTask = new MyTimer(milliseconds, 1000);
			timerTask.start();
		}
		return START_STICKY;
	}

	class MyTimer extends CountDownTimer {

		public MyTimer(long millisInFuture, long countDownInterval) {
			super(millisInFuture, countDownInterval);
		}

		@Override
		public void onTick(long millisUntilFinished) {

			String flag = myPref.getString("timerstopped", "").toString();
			Log.d("myTime", "flag : " + flag);

			if (flag.trim().equalsIgnoreCase("") && myPref.getLong("milliseconds", 0) > 0) {

				Log.d("myTime", "Time : " + millisUntilFinished);

				milliseconds = millisUntilFinished;

				SharedPreferences.Editor edit = myPref.edit();
				edit.putLong("milliseconds", milliseconds);
				edit.commit();

				// send update
				Intent intentUpdate = new Intent();
				intentUpdate.setAction(ACTION_MyUpdate);
				intentUpdate.addCategory(Intent.CATEGORY_DEFAULT);
				intentUpdate.putExtra(EXTRA_KEY_UPDATE, Integer.parseInt(String.valueOf((milliseconds / 1000))));
				sendBroadcast(intentUpdate);

				Log.d("myTime", "New Time : " + (milliseconds / 1000));

			} else {

				SharedPreferences.Editor edit = myPref.edit();
				edit.putBoolean("ServRun", false);
				edit.putLong("milliseconds", 0);
				edit.putString("timerstopped", "yes");
				edit.putString("launchScreen", "main");
				edit.commit();

				stopSelf();

				Log.d("myTime", "TimerTask Counter Stopoped by User");
			}
		}

		@Override
		public void onFinish() {

			Log.d("myTime", "TimerTask Cancelled.");

			// return result
			Intent intentResponse = new Intent();
			intentResponse.setAction(ACTION_MyIntentService);
			intentResponse.addCategory(Intent.CATEGORY_DEFAULT);
			intentResponse.putExtra(EXTRA_KEY_OUT, extraOut);
			sendBroadcast(intentResponse);

			String flag = myPref.getString("timerstopped", "").toString();

			if (msgFromActivity != null) {

				if (msgFromActivity.equalsIgnoreCase("Stopped") && flag.trim().equalsIgnoreCase("")) {

					String ns = Context.NOTIFICATION_SERVICE;
					NotificationManager nMgr = (NotificationManager) getSystemService(ns);
					nMgr.cancelAll();

					TimeOutRequestAsync requestAsync = new TimeOutRequestAsync(getApplicationContext(),
							ApplicationConstant.API_URL + ApplicationConstant.METHOD_TIMEOUT_BOOKING);
					requestAsync.execute(UserInfo.getString("access_token", ""), booking_id);

				} else {
					Log.d("myTime", "Timeout Counter Stopoped by User");
				}
			}

			SharedPreferences.Editor edit = myPref.edit();
			edit.putBoolean("ServRun", false);
			edit.putLong("milliseconds", 0);
			edit.putString("timerstopped", "");
			edit.putString("launchScreen", "main");
			edit.commit();

		}

	}

	@Override
	public void onDestroy() {
		super.onDestroy();

		Log.d("myTime", "Service is destroyed.");
		// LocalBroadcastManager.getInstance(getApplicationContext()).unregisterReceiver(cancel_timer);

		SharedPreferences.Editor edit = myPref.edit();
		edit.putBoolean("ServRun", false);
		edit.putLong("milliseconds", 0);
		edit.putString("timerstopped", "");
		edit.putString("launchScreen", "main");
		edit.commit();

		timerTask.cancel();
	}

}
