package com.android.taxivaxi.operator.app.location;

import java.util.Timer;
import java.util.TimerTask;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.LocationManager;
import android.os.Handler;
import android.os.Message;
import android.provider.Settings;

public class GetGpsStatus {

	static Timer timer;

	public static boolean gpsStatus(Context context) {

		final LocationManager manager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
		// LocationManager manager = (LocationManager)

		if (!manager.isProviderEnabled(LocationManager.GPS_PROVIDER)
				&& !manager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)) {

			return false;
		}
		{

			return true;
		}

	}

	public static boolean gpsStatusWithDilogBox(Context context, String messagedialog) {

		final LocationManager manager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
		// LocationManager manager = (LocationManager)

		if (!manager.isProviderEnabled(LocationManager.GPS_PROVIDER)
				&& !manager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)) {

			buildAlertMessageNoGps(context, messagedialog);

			// CustomDailog.displayErrorDialog(messagedialog, context);

			return false;
		} else {

			return true;
		}

	}

	private static void buildAlertMessageNoGps(final Context context, String msg) {
		final AlertDialog.Builder builder = new AlertDialog.Builder(context);
		builder.setMessage(msg).setCancelable(false).setPositiveButton("Yes", new DialogInterface.OnClickListener() {
			public void onClick(final DialogInterface dialog, final int id) {
				context.startActivity(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS));
			}
		}).setNegativeButton("No", new DialogInterface.OnClickListener() {
			public void onClick(final DialogInterface dialog, final int id) {
				dialog.cancel();
			}
		});
		final AlertDialog alert = builder.create();
		alert.show();
	}

	/**
	 * Gps Time out
	 * 
	 * @param sec
	 *            = time out in second
	 * @param context
	 * @param handler
	 */

	public static void gpsTimer(int sec, Context context, final Handler handler) {
		// Timer timer = new Timer();

		timer = new Timer();
		timer.schedule(new TimerTask() {

			public void run() {

				Message message = new Message();
				message.what = EventsToHandle.FAILURE.ordinal();
				handler.sendMessage(message);
			}
		}, 10 * 1000);

	}

	public static void cancelTimer() {
		if (timer != null) {
			timer.cancel();
			timer = null;
		}

	}

}
