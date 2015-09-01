package com.android.taxivaxi.operator.app.custom;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.android.taxivaxi.operator.app.R;
import com.android.taxivaxi.operator.app.activities.HomeActivity;

/**
 * 
 * @author Shekhar
 * 
 */
public class AlertDailogAfterCancel extends DialogFragment implements View.OnClickListener {

	private static final String KEY_TITLE = "title";
	private static final String KEY_MESSAGE = "message";
	private static final String KEY_NEGATIVEBUTTON = "negativeButton";
	private static final String KEY_POSITIVEBUTTON = "positiveButton";

	private TextView dialogTitle;
	private TextView dialogMessage;
	private Button dialogNegativeButton;
	private Button dialogPositiveButton;

	public static AlertDailogAfterCancel newInstance(String title, String message, String negativeButton,
			String positiveButton) {
		AlertDailogAfterCancel f = new AlertDailogAfterCancel();

		Bundle args = new Bundle();
		args.putString(KEY_TITLE, title);
		args.putString(KEY_MESSAGE, message);
		args.putString(KEY_NEGATIVEBUTTON, negativeButton);
		args.putString(KEY_POSITIVEBUTTON, positiveButton);
		f.setArguments(args);

		return f;
	}

	public AlertDailogAfterCancel() {
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
			dismiss();
			Intent i1 = new Intent(getActivity(), HomeActivity.class);
			startActivity(i1);
			getActivity().overridePendingTransition(R.anim.trans_right_in, R.anim.trans_right_out);
			getActivity().finish();
		}
	}

}