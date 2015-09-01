package com.android.taxivaxi.operator.app.custom;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Handler;
import android.os.Message;

public class AlertDailogCommon {
	public static boolean isSelectedYes = false;
	static Handler handler;

	public static void displayDialog(String string, Context context) {
		AlertDialog.Builder builder = new AlertDialog.Builder(context);

		builder.setTitle("").setMessage(string).setCancelable(true)
				.setNegativeButton("ok", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int id) {
						dialog.dismiss();
					}
				});
		builder.show();
	}

	/**
	 * 
	 * @param errormessage
	 *            = error message for shown to user
	 * @param context
	 *            = Activity Context
	 */
	public static void displayErrorDialog(String errormessage, Context context) {
		AlertDialog.Builder builder = new AlertDialog.Builder(context);

		builder.setTitle("Taxi Vaxi").setMessage(errormessage).setCancelable(true)
				.setNegativeButton("ok", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int id) {
						dialog.dismiss();
					}
				});
		builder.show();
	}

	public static void diplayuserSelectableDialog(String logoutmessage, Context context, String firstButtonText,
			String SecondButtonText, final Handler mhandler) {
		// boolean isSelectedYes = false;
		handler = mhandler;
		@SuppressWarnings("unused")
		final Message m = new Message();

		AlertDialog.Builder builder = new AlertDialog.Builder(context);

		builder.setTitle("ok").setMessage(logoutmessage).setCancelable(true)
				.setPositiveButton(firstButtonText, new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						// TODO Auto-generated method stub
						System.out.println("YES");

						if (handler == null)
							return;

						// m.what = EventsToHandle.SUCCESS.ordinal();
						// m.what = EventsToHandle.YES.ordinal();
						// handler.sendMessage(m);
						// dialog.dismiss();

					}
				}).setNegativeButton(SecondButtonText, new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int id) {
						System.out.println("NO");

						// m.what = EventsToHandle.NO.ordinal();
						// handler.sendMessage(m);
						// dialog.dismiss();
					}
				});
		builder.show();
		// return isSelectedYes;
	}
}
