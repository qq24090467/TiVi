package com.android.taxivaxi.operator.app.custom;

import org.json.JSONException;
import org.json.JSONObject;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.android.taxivaxi.operator.app.R;
import com.android.taxivaxi.operator.app.activities.LoginActivity;
import com.android.taxivaxi.operator.app.common.ApplicationConstant;
import com.android.taxivaxi.operator.app.common.GlobalData;
import com.android.taxivaxi.operator.app.common.NetworkUtilities;
import com.android.taxivaxi.operator.app.controller.LogoutAsync;

/**
 * 
 * @author Shekhar
 * 
 */
public class AlertDailogLogout extends DialogFragment implements View.OnClickListener {

	private static final String KEY_TITLE = "title";
	private static final String KEY_MESSAGE = "message";
	private static final String KEY_NEGATIVEBUTTON = "negativeButton";
	private static final String KEY_POSITIVEBUTTON = "positiveButton";

	private TextView dialogTitle;
	private TextView dialogMessage;
	private Button dialogNegativeButton;
	private Button dialogPositiveButton;

	public static AlertDailogLogout newInstance(String title, String message, String negativeButton,
			String positiveButton) {
		AlertDailogLogout f = new AlertDailogLogout();

		Bundle args = new Bundle();
		args.putString(KEY_TITLE, title);
		args.putString(KEY_MESSAGE, message);
		args.putString(KEY_NEGATIVEBUTTON, negativeButton);
		args.putString(KEY_POSITIVEBUTTON, positiveButton);
		f.setArguments(args);

		return f;
	}

	public AlertDailogLogout() {
	}

	@SuppressLint("InflateParams")
	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

		LayoutInflater inflater = getActivity().getLayoutInflater();
		View dialogView = inflater.inflate(R.layout.custom_layout_dialog, null);

		dialogTitle = (TextView) dialogView.findViewById(R.id.dialogTitle);
		dialogMessage = (TextView) dialogView.findViewById(R.id.dialogMessage);
		dialogNegativeButton = (Button) dialogView.findViewById(R.id.dialogButtonNegative);
		dialogPositiveButton = (Button) dialogView.findViewById(R.id.dialogButtonPositive);

		dialogTitle.setText(getArguments().getString(KEY_TITLE));
		dialogMessage.setText(getArguments().getString(KEY_MESSAGE));
		dialogNegativeButton.setText(getArguments().getString(KEY_NEGATIVEBUTTON));
		dialogPositiveButton.setText(getArguments().getString(KEY_POSITIVEBUTTON));

		dialogNegativeButton.setOnClickListener(this);
		dialogPositiveButton.setOnClickListener(this);

		builder.setView(dialogView);

		return builder.create();
	}

	@Override
	public void onClick(View v) {

		if (v.getId() == R.id.dialogButtonNegative) {
			dismiss();
		}
		if (v.getId() == R.id.dialogButtonPositive) {

			if (NetworkUtilities.isInternet(getActivity())) {

				SharedPreferences UserInfo = getActivity().getSharedPreferences("UserInfo", Context.MODE_PRIVATE);
				LogoutAsync mAsync = new LogoutAsync(getActivity(), logoutHandler, ApplicationConstant.API_URL
						+ ApplicationConstant.METHOD_LOGOUT);
				mAsync.execute((UserInfo.getString("access_token", "")).toString());
			} else {
				AlertDailogCommon.displayDialog("Please check your internet connection", getActivity());
			}

		}
	}

	@SuppressLint("HandlerLeak")
	private Handler logoutHandler = new Handler() {
		@Override
		public void handleMessage(android.os.Message msg) {

			System.out.println("res = " + ((JSONObject) msg.obj).toString());

			if (msg.obj != null) {
				try {
					if (((JSONObject) msg.obj).getString("success").toString().equalsIgnoreCase("1")) {

						SharedPreferences UserInfo = getActivity().getSharedPreferences("UserInfo",
								Context.MODE_PRIVATE);
						UserInfo.edit().clear().commit();

						GlobalData.mGlobalData = null;

						Intent i = new Intent(getActivity(), LoginActivity.class);
						i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
						startActivity(i);
						getActivity().overridePendingTransition(R.anim.trans_left_in, R.anim.trans_left_out);
						getActivity().finish();

					} else {
						SharedPreferences UserInfo = getActivity().getSharedPreferences("UserInfo",
								Context.MODE_PRIVATE);
						UserInfo.edit().clear().commit();

						GlobalData.mGlobalData = null;

						Intent i = new Intent(getActivity(), LoginActivity.class);
						i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
						startActivity(i);
						getActivity().overridePendingTransition(R.anim.trans_left_in, R.anim.trans_left_out);
						getActivity().finish();
					}
				} catch (JSONException e) {
					e.printStackTrace();
				}
			}

			dismiss();

		};
	};

}