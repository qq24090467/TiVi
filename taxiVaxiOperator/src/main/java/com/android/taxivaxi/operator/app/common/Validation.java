package com.android.taxivaxi.operator.app.common;

import android.content.Context;

import com.android.taxivaxi.operator.app.custom.AlertDailogCommon;

public class Validation {
	public static final String mEmaiValidate = "^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*(\\.[A-Za-z‌​]{2,4})$";

	public Validation(Context context) {

	}

	public static boolean LoginActivityValidation(String emailId, String Password, Context mContext) {

		if (emailId.equals("")) {

			AlertDailogCommon.displayErrorDialog("Please enter Login ID (Email).", mContext);

			return false;
		} else if (!emailId.matches(mEmaiValidate)) {
			AlertDailogCommon.displayErrorDialog("Invalid Email Address.", mContext);

			return false;

		} else if (emailId.trim().toString().length() < 5) {
			AlertDailogCommon.displayDialog("", mContext);
			return false;
		} else if (emailId.trim().toString().length() > 50) {
			AlertDailogCommon.displayDialog("", mContext);
			return false;

		} else if (Password.equals("")) {
			AlertDailogCommon.displayErrorDialog("Please enter Password.", mContext);
			return false;
		}
		return true;

	}

	public static boolean forgotPasswordActivityValidation(String emailId, Context mContext) {

		if (emailId.equals("")) {
			AlertDailogCommon.displayErrorDialog("Enter your email address to reset your account password.", mContext);
			return false;
		} else if (!emailId.trim().matches(mEmaiValidate)) {
			AlertDailogCommon.displayErrorDialog("Invalid Email Address.", mContext);
			return false;
		}
		return true;
	}

}
