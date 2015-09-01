package com.android.taxivaxi.operator.app.activities;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.IntentService;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

import com.android.taxivaxi.operator.app.R;
import com.android.taxivaxi.operator.app.common.DebugLog;
import com.google.android.gms.gcm.GoogleCloudMessaging;

public class GcmIntentService extends IntentService {
	public static final int NOTIFICATION_ID = 1;
	private NotificationManager mNotificationManager;

	public GcmIntentService() {
		super("GcmIntentService");
	}

	@Override
	protected void onHandleIntent(Intent intent) {

		Bundle extras = intent.getExtras();
		GoogleCloudMessaging gcm = GoogleCloudMessaging.getInstance(this);
		String messageType = gcm.getMessageType(intent);

		if (!extras.isEmpty()) {

			if (GoogleCloudMessaging.MESSAGE_TYPE_SEND_ERROR.equals(messageType)) {
				sendNotification("Send error: " + extras.toString());

			} else if (GoogleCloudMessaging.MESSAGE_TYPE_DELETED.equals(messageType)) {
				sendNotification("Deleted messages on server: " + extras.toString());

			} else if (GoogleCloudMessaging.MESSAGE_TYPE_MESSAGE.equals(messageType)) {

				Log.i("GCMINTENT", "Completed work @ " + SystemClock.elapsedRealtime());
				try {
					sendNotification(extras.getString("message").toString());
					DebugLog.d("GCMINTENT Received: " + extras.getString("message").toString());
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		GcmBroadcastReceiver.completeWakefulIntent(intent);
	}

	private void sendNotification(String msg) {

		SharedPreferences myPref = getSharedPreferences("My_PREF", MODE_PRIVATE);
		mNotificationManager = (NotificationManager) this.getSystemService(Context.NOTIFICATION_SERVICE);
		String notification_for = "";
		String booking_id = "";

		// parse push json data
		try {
			JSONObject json = new JSONObject(msg);

			DebugLog.d(json.getJSONObject("response").getString("notification_for").toString());
			notification_for = json.getJSONObject("response").getString("notification_for").toString();
			booking_id = json.getJSONObject("response").getJSONObject("Details").getString("booking_id").toString();

		} catch (JSONException e) {
			e.printStackTrace();
		}

		Uri soundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

		// Start MyService
		if (!myPref.getBoolean("ServRun", false)) {
			SharedPreferences.Editor edit = myPref.edit();
			edit.putLong("milliseconds", 30000);
			edit.putString("timerstopped", "");
			edit.putString("launchScreen", "push");
			edit.putString("booking_id", booking_id);
			edit.commit();
		}

		if (notification_for.trim().length() > 0 && notification_for.trim().equalsIgnoreCase("New Request Created")) {

			Intent service = new Intent(getApplicationContext(), MyService.class);
			service.putExtra(MyService.EXTRA_KEY_IN, "Stopped");
			service.putExtra("booking_id", booking_id);
			startService(service);

			Intent i = new Intent("new_order");
			i.putExtra("new_order", msg);
			i.putExtra("NOTIFICATION_ID", NOTIFICATION_ID);
			LocalBroadcastManager.getInstance(getApplicationContext()).sendBroadcast(i);

			DebugLog.d("notification_for : " + notification_for);

			Intent intent = new Intent(this, NewOrderComingActivity.class);
			intent.putExtra("isNewOrderPush", msg);
			PendingIntent contentIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);

			NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(this)
					.setSmallIcon(R.drawable.taxivaxi_ic_launcher).setContentTitle("You have got a new booking!")
					.setStyle(new NotificationCompat.BigTextStyle().bigText("Please respond")).setContentText("")
					.setSound(Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.push_ringing))
					.setAutoCancel(true);

			mBuilder.setContentIntent(contentIntent);
			mNotificationManager.notify(NOTIFICATION_ID, mBuilder.build());

		} else if (notification_for.trim().length() > 0
				&& notification_for.trim().equalsIgnoreCase("Cancelled Request")) {

			Intent i = new Intent("cancel_data");
			i.putExtra("cancel_data", msg);
			i.putExtra("NOTIFICATION_ID", NOTIFICATION_ID);
			LocalBroadcastManager.getInstance(getApplicationContext()).sendBroadcast(i);

			DebugLog.d("notification_for : " + notification_for);

			NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(this)
					.setSmallIcon(R.drawable.taxivaxi_ic_launcher).setContentTitle("Ride cancelled by User.")
					.setStyle(new NotificationCompat.BigTextStyle().bigText("please wait for new order."))
					.setContentText("").setSound(soundUri).setAutoCancel(true);

			// mBuilder.setContentIntent(contentIntent);
			mNotificationManager.notify(NOTIFICATION_ID, mBuilder.build());

		} else {

			Intent intent = new Intent(this, HomeActivity.class);
			intent.putExtra("isNewOrderPush", msg);
			PendingIntent contentIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);

			NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(this)
					.setSmallIcon(R.drawable.taxivaxi_ic_launcher).setContentTitle("Other Notification")
					.setStyle(new NotificationCompat.BigTextStyle().bigText("See detail")).setContentText("")
					.setSound(soundUri).setAutoCancel(true);

			mBuilder.setContentIntent(contentIntent);
			mNotificationManager.notify(NOTIFICATION_ID, mBuilder.build());
		}
	}
}