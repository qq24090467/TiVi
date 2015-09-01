package com.android.taxivaxi.operator.app.activities;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.taxivaxi.operator.app.R;
import com.android.taxivaxi.operator.app.common.CustomFontsLoader;
import com.android.taxivaxi.operator.app.common.DebugLog;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;

/***
 * 
 * @author Administrator
 */
public class RideCompletedActivity extends Activity {

	private TextView button_submit, total_cost_value, total_cost_txt, total_cost_currency, ride_duration,
			user_name_value;
	private String ride_cost = "", ride_time = "", user_name = "", user_image = "";
	private SharedPreferences UserInfo, myPref;
	private ImageView user_img, button_close;
	private ImageLoadingListener animateFirstListener = new AnimateFirstDisplayListener();
	private ImageLoader imageLoader;
	private DisplayImageOptions options;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_ride_completed);

		options = new DisplayImageOptions.Builder().showImageOnLoading(R.drawable.tgl_androidp_profile_config_profile1)
				.showImageForEmptyUri(R.drawable.tgl_androidp_profile_config_profile1).cacheInMemory(true)
				.showImageOnFail(R.drawable.tgl_androidp_profile_config_profile1).considerExifParams(true).build();

		imageLoader = ImageLoader.getInstance();

		UserInfo = this.getSharedPreferences("UserInfo", Context.MODE_PRIVATE);
		myPref = getSharedPreferences("My_PREF", MODE_PRIVATE);

		try {
			if (getIntent().getExtras() != null) {
				ride_cost = getIntent().getExtras().getString("ride_cost");
				ride_time = getIntent().getExtras().getString("ride_time");
				user_name = getIntent().getExtras().getString("user_name");
				user_image = getIntent().getExtras().getString("user_image");
			} else {
				DebugLog.d("Unable to parse response data");
			}
		} catch (Exception e) {

		}

		button_close = (ImageView) findViewById(R.id.Drawer_toggle);
		button_submit = (TextView) findViewById(R.id.button_submit);
		total_cost_value = (TextView) findViewById(R.id.total_cost_value);
		total_cost_txt = (TextView) findViewById(R.id.total_cost_txt);
		total_cost_currency = (TextView) findViewById(R.id.total_cost_currency);
		ride_duration = (TextView) findViewById(R.id.ride_duration);
		user_name_value = (TextView) findViewById(R.id.user_name);

		user_img = (ImageView) findViewById(R.id.user_img);

		user_name_value.setText(user_name);
		total_cost_value.setText(ride_cost);
		ride_duration.setText("Ride Duration : " + ride_time);

		try {
			imageLoader.displayImage(user_image, user_img, options, animateFirstListener);
		} catch (Exception e) {
			e.printStackTrace();
			user_img.setImageResource(R.drawable.tgl_androidp_profile_config_profile1);
		}

		button_close.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				setDriverBusyToAvailable();

				SharedPreferences.Editor preferencesEditer1 = myPref.edit().clear();
				preferencesEditer1.commit();

				Intent i = new Intent(RideCompletedActivity.this, HomeActivity.class);
				i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
				startActivity(i);
				overridePendingTransition(R.anim.slide_out_bottom, R.anim.slide_out_top);
				finish();
			}
		});

		button_submit.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				// if (NetworkUtilities.isInternet(getApplicationContext())) {
				//
				// ReviewUserAsync async = new
				// ReviewUserAsync(RideCompletedActivity.this,
				// HandlerReviewRequest,
				// ApplicationConstant.API_URL +
				// ApplicationConstant.METHOD_REVIEW_USER);
				// async.execute(UserInfo.getString("access_token", ""),
				// request_id,
				// String.valueOf(rating_bar_val.getRating()),
				// comment_text.getText().toString().trim());
				// }
			}
		});

		// set font to layout widgets
		Typeface typeFace = CustomFontsLoader.getTypeface(RideCompletedActivity.this, 1);
		if (typeFace != null) {
			total_cost_txt.setTypeface(typeFace);
			ride_duration.setTypeface(typeFace);
		}

		Typeface typeFace1 = CustomFontsLoader.getTypeface(RideCompletedActivity.this, 2);
		if (typeFace1 != null) {
			total_cost_value.setTypeface(typeFace1);
			total_cost_currency.setTypeface(typeFace1);
		}
	}

	@SuppressLint("HandlerLeak")
	private Handler HandlerReviewRequest = new Handler() {

		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);

			if (msg.obj != null) {
				try {
					if (((JSONObject) msg.obj).getString("success").toString().equalsIgnoreCase("1")) {

						DebugLog.d("ride reveiw user response : " + ((JSONObject) msg.obj).toString());

						setDriverBusyToAvailable();

						Intent i = new Intent(RideCompletedActivity.this, HomeActivity.class);
						i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
						startActivity(i);
						overridePendingTransition(R.anim.slide_out_bottom, R.anim.slide_out_top);
						finish();

					} else {
						Toast.makeText(getApplicationContext(), ((JSONObject) msg.obj).getString("error").toString(),
								Toast.LENGTH_LONG).show();
					}
				} catch (JSONException e) {
					e.printStackTrace();
				}
			}

		}
	};

	private class AnimateFirstDisplayListener extends SimpleImageLoadingListener {

		final List<String> displayedImages = Collections.synchronizedList(new LinkedList<String>());

		@Override
		public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
			if (loadedImage != null) {
				ImageView imageView = (ImageView) view;
				boolean firstDisplay = !displayedImages.contains(imageUri);
				if (firstDisplay) {
					FadeInBitmapDisplayer.animate(imageView, 500);
					displayedImages.add(imageUri);
				}
			}
		}
	}

	@Override
	public void onBackPressed() {
		// super.onBackPressed();

		setDriverBusyToAvailable();

		SharedPreferences.Editor preferencesEditer1 = myPref.edit().clear();
		preferencesEditer1.commit();

		Intent i = new Intent(RideCompletedActivity.this, HomeActivity.class);
		i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
		startActivity(i);
		overridePendingTransition(R.anim.slide_out_bottom, R.anim.slide_out_top);
		finish();
	}

	// change driver flag busy to available.
	public void setDriverBusyToAvailable() {

		SharedPreferences.Editor preferencesEditer = UserInfo.edit();
		preferencesEditer.putString("availibility", "");
		preferencesEditer.putString("ride_detail", "");
		preferencesEditer.putString("ride_status", "");
		preferencesEditer.commit();

		SharedPreferences.Editor preferencesEditer1 = myPref.edit().clear();
		preferencesEditer1.commit();
	}

}
