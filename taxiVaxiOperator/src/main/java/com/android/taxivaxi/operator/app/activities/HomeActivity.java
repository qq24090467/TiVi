package com.android.taxivaxi.operator.app.activities;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.taxivaxi.operator.app.R;
import com.android.taxivaxi.operator.app.common.CustomFontsLoader;
import com.android.taxivaxi.operator.app.common.DebugLog;


public class HomeActivity extends BaseActivity implements OnClickListener {
	private TextView available_text, busy_text, offline_text;
	private ImageView profile_button, drawer_toggle;

	private SharedPreferences UserInfo;

	private TextView ride_pickup_location_txt, ride_pickup_location, ride_drop_location_txt, ride_drop_location,
			see_detail;

	private TextView button_booking;



	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
//		setContentView(R.layout.activity_home_screen);

        LayoutInflater inflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        //inflate your activity layout here!
        View contentView = inflater.inflate(R.layout.activity_home_screen, null, false);

        mDrawerLayout.addView(contentView, 0);

//		button_booking = (TextView) findViewById(R.id.button_booking);
//		button_booking.setOnClickListener(this);
		UserInfo = this.getSharedPreferences("UserInfo", Context.MODE_PRIVATE);

		available_text = (TextView) findViewById(R.id.available_text);
		busy_text = (TextView) findViewById(R.id.busy_text);
		offline_text = (TextView) findViewById(R.id.offline_text);

		ride_pickup_location_txt = (TextView) findViewById(R.id.ride_pickup_location_txt);
		ride_pickup_location = (TextView) findViewById(R.id.ride_pickup_location);
		ride_drop_location_txt = (TextView) findViewById(R.id.ride_drop_location_txt);
		ride_drop_location = (TextView) findViewById(R.id.ride_drop_location);

		profile_button = (ImageView) findViewById(R.id.profile_button);
        drawer_toggle = (ImageView) findViewById(R.id.Drawer_toggle);

		see_detail = (TextView) findViewById(R.id.see_detail);
		see_detail.setOnClickListener(this);
		profile_button.setOnClickListener(this);
        drawer_toggle.setOnClickListener(this);

		// set font to layout widgets
		Typeface typeFace = CustomFontsLoader.getTypeface(HomeActivity.this, 1);
		if (typeFace != null) {
			((TextView) findViewById(R.id.login_text)).setTypeface(typeFace);
			available_text.setTypeface(typeFace);
			busy_text.setTypeface(typeFace);
			offline_text.setTypeface(typeFace);
			ride_pickup_location_txt.setTypeface(typeFace);
			ride_pickup_location.setTypeface(typeFace);
			ride_drop_location_txt.setTypeface(typeFace);
			ride_drop_location.setTypeface(typeFace);
			see_detail.setTypeface(typeFace);
		}
	}

	@Override
	protected void onStart() {
		super.onStart();
		LocalBroadcastManager.getInstance(getApplicationContext()).registerReceiver(new_order,
				new IntentFilter("new_order"));
		LocalBroadcastManager.getInstance(getApplicationContext()).registerReceiver(cancel_data,
				new IntentFilter("cancel_data"));
	}

	@Override
	protected void onResume() {
		super.onResume();
	}

	@Override
	protected void onPause() {
		super.onPause();
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		LocalBroadcastManager.getInstance(getApplicationContext()).unregisterReceiver(new_order);
		LocalBroadcastManager.getInstance(getApplicationContext()).unregisterReceiver(cancel_data);
	}

	@Override
	public void onClick(View v) {

		switch (v.getId()) {
            case R.id.Drawer_toggle:
                mDrawerLayout.openDrawer(Gravity.LEFT);
                break;

		case R.id.profile_button:

			Intent profile_intent = new Intent(HomeActivity.this, ProfileScreenActivity.class);
			startActivity(profile_intent);
			overridePendingTransition(R.anim.trans_left_in, R.anim.trans_left_out);

			break;

		case R.id.see_detail:

			Intent rid_accepted_screen = new Intent(HomeActivity.this, BookingHistoryActivity.class);
			startActivity(rid_accepted_screen);
			overridePendingTransition(R.anim.trans_left_in, R.anim.trans_left_out);

			break;

			/*case R.id.button_booking:

				Intent test = new Intent(HomeActivity.this, BookingHistoryActivity.class);
				startActivity(test);
				overridePendingTransition(R.anim.trans_left_in, R.anim.trans_left_out);
				break;*/

		case R.id.offline_text:

			break;

		default:
			break;
		}

	}

	private BroadcastReceiver new_order = new BroadcastReceiver() {

		@Override
		public void onReceive(Context context, Intent intent) {

			DebugLog.d("new_order : " + intent.getStringExtra("new_order"));
			// int notification_id = 1;

			try {

				// notification_id = intent.getIntExtra("NOTIFICATION_ID", 1);
				// cancelNotification(getApplicationContext(), notification_id);

				Intent i1 = new Intent(HomeActivity.this, NewOrderComingActivity.class);
				i1.putExtra("isNewOrderPush", intent.getStringExtra("new_order"));
				startActivity(i1);
				overridePendingTransition(R.anim.trans_left_in, R.anim.trans_left_out);
				finish();

			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	};
	private BroadcastReceiver cancel_data = new BroadcastReceiver() {

		@Override
		public void onReceive(Context context, Intent intent) {

			DebugLog.d("cancel_data : " + intent.getStringExtra("cancel_data"));
			// int notification_id = 1;

			JSONObject json = null;
			String notification_for = "";
			String Message = "";

			try {
				json = new JSONObject((intent.getStringExtra("cancel_data")).toString());
				notification_for = json.getJSONObject("response").getString("notification_for").toString();
				Message = json.getJSONObject("response").getString("Message").toString();

				// notification_id = intent.getIntExtra("NOTIFICATION_ID", 1);

			} catch (JSONException e) {
				e.printStackTrace();
			}
//			 AlertDailogAfterCancel.newInstance(getResources().getString(R.string.app_name),
//			 notification_for + "\n" + Message, "",
//			 "Ok").show(getFragmentManager(), "cancel_data");

			Toast.makeText(getApplicationContext(), notification_for + "\n" + Message, Toast.LENGTH_LONG).show();

			setDriverBusyToAvailable();

			Intent i1 = new Intent(HomeActivity.this, HomeActivity.class);
			startActivity(i1);
			overridePendingTransition(R.anim.trans_right_in, R.anim.trans_right_out);
			finish();

			// cancelNotification(getApplicationContext(), notification_id);

		}
	};

	// public static void cancelNotification(Context ctx, int notifyId) {
	// String ns = Context.NOTIFICATION_SERVICE;
	// NotificationManager nMgr = (NotificationManager)
	// ctx.getSystemService(ns);
	// nMgr.cancel(notifyId);
	// }

	// change driver flag busy to available.
	public void setDriverBusyToAvailable() {

		SharedPreferences.Editor preferencesEditer = UserInfo.edit();
		preferencesEditer.putString("availibility", "");
		preferencesEditer.putString("ride_detail", "");
		preferencesEditer.putString("ride_status", "");
		preferencesEditer.commit();

	}

}
