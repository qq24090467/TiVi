package com.android.taxivaxi.operator.app.activities;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.support.v4.content.WakefulBroadcastReceiver;

public class GcmBroadcastReceiver extends WakefulBroadcastReceiver {
	@Override
	public void onReceive(Context context, Intent intent) {

		try {
			ComponentName comp = new ComponentName(context.getPackageName(), GcmIntentService.class.getName());
			
			startWakefulService(context, (intent.setComponent(comp)));
			setResultCode(Activity.RESULT_OK);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}