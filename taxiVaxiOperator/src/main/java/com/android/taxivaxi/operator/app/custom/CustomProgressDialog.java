package com.android.taxivaxi.operator.app.custom;

import android.app.Dialog;
import android.content.Context;
import android.view.WindowManager;

import com.android.taxivaxi.operator.app.R;

public class CustomProgressDialog {

	public static Dialog showProgressDialog(Context ctx) {

		Dialog dialog = new Dialog(ctx, R.style.Progres_Custom_Dialog);
		dialog.setContentView(R.layout.custom_progress_dialog);
		dialog.setCancelable(true);

		WindowManager.LayoutParams WMLP = dialog.getWindow().getAttributes();
		WMLP.dimAmount = (float) 0.4;

		return dialog;
	}

}
