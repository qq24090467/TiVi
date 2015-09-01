package com.android.taxivaxi.operator.app.common;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Context;
import android.net.ParseException;
import android.provider.Settings;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

public class CommonMethod {

	public static String dateFormatChange(String dateInput) {
		String str1 = dateInput;
		String str2 = null;

		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss", Locale.getDefault());
		SimpleDateFormat formatter1 = new SimpleDateFormat("MMM dd, yyyy  |  hh:mm aa", Locale.getDefault());
		Date date1;

		try {
			date1 = formatter.parse(str1);
			str2 = (formatter1.format(date1));
		} catch (ParseException e) {
			e.printStackTrace();
		} catch (java.text.ParseException e) {
			e.printStackTrace();
		}

		return str2;
	}

	public static String dateFormatChangeBookTourCab(String dateInput) {
		String str1 = dateInput;
		String str2 = null;

		SimpleDateFormat formatter = new SimpleDateFormat("MMM dd, yyyy", Locale.getDefault());
		SimpleDateFormat formatter1 = new SimpleDateFormat("yyyy/MM/dd", Locale.getDefault());
		Date date1;

		try {
			date1 = formatter.parse(str1);
			str2 = (formatter1.format(date1));
		} catch (ParseException e) {
			e.printStackTrace();
		} catch (java.text.ParseException e) {
			e.printStackTrace();
		}

		return str2;
	}

	public static String dateFormatChangeRegister(String dateInput) {
		String str1 = dateInput;
		String str2 = null;

		SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
		SimpleDateFormat formatter1 = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
		Date date1;

		try {
			date1 = formatter.parse(str1);
			str2 = (formatter1.format(date1));
		} catch (ParseException e) {
			e.printStackTrace();
		} catch (java.text.ParseException e) {
			e.printStackTrace();
		}

		return str2;
	}

	public static String timeFormatChangeBookTourCab(String dateInput) {
		String str1 = dateInput;
		String str2 = null;

		SimpleDateFormat formatter = new SimpleDateFormat("hh:mm aa", Locale.getDefault());
		SimpleDateFormat formatter1 = new SimpleDateFormat("HH:mm", Locale.getDefault());
		Date date1;

		try {
			date1 = formatter.parse(str1);
			str2 = (formatter1.format(date1));
		} catch (ParseException e) {
			e.printStackTrace();
		} catch (java.text.ParseException e) {
			e.printStackTrace();
		}

		return str2;
	}

	public static String dateFormatUpdateProfile(String dateInput) {
		String str1 = dateInput;
		String str2 = null;

		SimpleDateFormat formatter = new SimpleDateFormat("dd-MMM-yyyy", Locale.getDefault());
		SimpleDateFormat formatter1 = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
		Date date1;

		try {
			date1 = formatter.parse(str1);
			str2 = (formatter1.format(date1));
		} catch (ParseException e) {
			e.printStackTrace();
		} catch (java.text.ParseException e) {
			e.printStackTrace();
		}

		return str2;
	}

	public static String dateFormatChangeDOB(String dateInput) {
		String str1 = dateInput;
		String str2 = null;

		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
		SimpleDateFormat formatter1 = new SimpleDateFormat("dd-MMM-yyyy", Locale.getDefault());
		Date date1;

		try {
			date1 = formatter.parse(str1);
			str2 = (formatter1.format(date1));
		} catch (ParseException e) {
			e.printStackTrace();
		} catch (java.text.ParseException e) {
			e.printStackTrace();
		}

		return str2;
	}

	public static void deleteCache(Context context) {
		try {
			File dir = context.getCacheDir();
			if (dir != null && dir.isDirectory()) {
				deleteDir(dir);
			}
		} catch (Exception e) {
		}
	}

	public static boolean deleteDir(File dir) {
		if (dir != null && dir.isDirectory()) {
			String[] children = dir.list();
			for (int i = 0; i < children.length; i++) {
				boolean success = deleteDir(new File(dir, children[i]));
				if (!success) {
					return false;
				}
			}
		}
		return dir.delete();
	}

	public static void setAutoOrientationEnabled(ContentResolver resolver, boolean enabled, Context mContext) {
		Settings.System.putInt(mContext.getContentResolver(), Settings.System.ACCELEROMETER_ROTATION, enabled ? 1 : 0);
	}

	public static void hideKeyboard(Activity activity) {
		// Check if no view has focus:
		View view = activity.getCurrentFocus();
		if (view != null) {
			InputMethodManager inputManager = (InputMethodManager) activity
					.getSystemService(Context.INPUT_METHOD_SERVICE);
			inputManager.hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
		}
	}

}
