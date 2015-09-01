package com.android.taxivaxi.operator.app.activities;

import android.app.Activity;
import android.graphics.Typeface;
import android.location.LocationListener;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

import com.android.taxivaxi.operator.app.R;
import com.android.taxivaxi.operator.app.common.CustomFontsLoader;

/***
 * 
 * @author Administrator
 */
public class EndShiftActivity extends Activity {

	private TextView button_close;

	LocationListener MyLocationListener;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_end_shift);

		button_close = (TextView) findViewById(R.id.button_close);

		button_close.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				finish();
				overridePendingTransition(R.anim.slide_out_bottom, R.anim.slide_out_top);

			}
		});

		// set font to layout widgets
		Typeface typeFace = CustomFontsLoader.getTypeface(EndShiftActivity.this, 1);
		if (typeFace != null) {
			button_close.setTypeface(typeFace);
		}
	}

	@Override
	public void onBackPressed() {
		super.onBackPressed();
		overridePendingTransition(R.anim.slide_out_bottom, R.anim.slide_out_top);
	}

}
