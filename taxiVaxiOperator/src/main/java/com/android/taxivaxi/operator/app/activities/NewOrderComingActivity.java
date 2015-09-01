package com.android.taxivaxi.operator.app.activities;

import org.json.JSONException;
import org.json.JSONObject;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.content.LocalBroadcastManager;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.taxivaxi.operator.app.R;
import com.android.taxivaxi.operator.app.common.ApplicationConstant;
import com.android.taxivaxi.operator.app.common.CustomFontsLoader;
import com.android.taxivaxi.operator.app.common.DebugLog;
import com.android.taxivaxi.operator.app.common.NetworkUtilities;
import com.android.taxivaxi.operator.app.controller.AcceptRequestAsync;
import com.android.taxivaxi.operator.app.controller.RejectRequestAsync;

public class NewOrderComingActivity extends Activity implements OnClickListener {

	private ImageView profile_back, user_call;
	private TextView order_accpet_btn, order_reject_btn;
	private SharedPreferences UserInfo, myPref;
	private String isNewOrderPush = "";
	private String booking_id = "", picup_loc = "", drop_loc = "Not specified", name = "", contact_no = "";
	private TextView txtTimerTime;
	private TextView user_name, user_email_id, user_contact_no, ride_pickup_location, ride_drop_location;

	private MyBroadcastReceiver myBroadcastReceiver;
	private MyBroadcastReceiver_Update myBroadcastReceiver_Update;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_new_order_coming);

		UserInfo = this.getSharedPreferences("UserInfo", Context.MODE_PRIVATE);
		myPref = getSharedPreferences("My_PREF", MODE_PRIVATE);

		// parse push json data
		try {
			if (getIntent().getExtras() != null) {
				isNewOrderPush = getIntent().getExtras().getString("isNewOrderPush");
				JSONObject json = new JSONObject(isNewOrderPush);
				System.out.println(json.getJSONObject("response").getString("notification_for").toString());

				booking_id = json.getJSONObject("response").getJSONObject("Details").getString("booking_id").toString();

				picup_loc = json.getJSONObject("response").getJSONObject("Details").getString("pickup_location")
						.toString();
				// drop_loc =
				// json.getJSONObject("response").getJSONObject("Details").getString("booking_id").toString();
				name = json.getJSONObject("response").getJSONObject("Details").getString("name").toString();
				contact_no = json.getJSONObject("response").getJSONObject("Details").getString("contact_no").toString();

				System.out.println("try booking_id :" + booking_id);

				SharedPreferences.Editor edit = myPref.edit();
				edit.putString("booking_id", booking_id);
				edit.putString("picup_loc", picup_loc);
				edit.putString("drop_loc", drop_loc);
				edit.putString("name", name);
				edit.putString("contact_no", contact_no);
				edit.commit();
			} else {
				booking_id = myPref.getString("booking_id", "");
				picup_loc = myPref.getString("picup_loc", "");
				drop_loc = myPref.getString("drop_loc", "Not specified");
				name = myPref.getString("name", "");
				contact_no = myPref.getString("contact_no", "");
			}
		} catch (JSONException e) {
			e.printStackTrace();
			booking_id = myPref.getString("booking_id", "");
		}

		myBroadcastReceiver = new MyBroadcastReceiver();
		myBroadcastReceiver_Update = new MyBroadcastReceiver_Update();

		profile_back = (ImageView) findViewById(R.id.profile_back);
		profile_back.setOnClickListener(this);

		txtTimerTime = (TextView) findViewById(R.id.txtTimerTime);
		order_accpet_btn = (TextView) findViewById(R.id.order_accpet_btn);
		order_reject_btn = (TextView) findViewById(R.id.order_reject_btn);

		user_name = (TextView) findViewById(R.id.user_name);
		user_email_id = (TextView) findViewById(R.id.user_email_id);
		user_contact_no = (TextView) findViewById(R.id.user_contact_no);
		ride_pickup_location = (TextView) findViewById(R.id.ride_pickup_location);
		ride_drop_location = (TextView) findViewById(R.id.ride_drop_location);
		user_call = (ImageView) findViewById(R.id.user_call);

		user_name.setText(name);
		user_contact_no.setText(contact_no);
		ride_pickup_location.setText(picup_loc);
		ride_drop_location.setText(drop_loc);

		order_accpet_btn.setOnClickListener(this);
		order_reject_btn.setOnClickListener(this);
		user_call.setOnClickListener(this);

		// set font to layout widgets
		Typeface typeFace1 = CustomFontsLoader.getTypeface(NewOrderComingActivity.this, 2);
		if (typeFace1 != null) {
			txtTimerTime.setTypeface(typeFace1);
		}

		Typeface typeFace2 = CustomFontsLoader.getTypeface(NewOrderComingActivity.this, 1);
		if (typeFace1 != null) {
			user_name.setTypeface(typeFace2);
			user_email_id.setTypeface(typeFace2);
			user_contact_no.setTypeface(typeFace2);
			ride_pickup_location.setTypeface(typeFace2);
			ride_drop_location.setTypeface(typeFace2);
		}

	}

	@Override
	protected void onStart() {
		super.onStart();
		LocalBroadcastManager.getInstance(getApplicationContext()).registerReceiver(cancel_data,
				new IntentFilter("cancel_data"));

	}

	@Override
	protected void onResume() {
		super.onResume();

		// register BroadcastReceiver
		IntentFilter intentFilter = new IntentFilter(MyService.ACTION_MyIntentService);
		intentFilter.addCategory(Intent.CATEGORY_DEFAULT);
		registerReceiver(myBroadcastReceiver, intentFilter);

		IntentFilter intentFilter_update = new IntentFilter(MyService.ACTION_MyUpdate);
		intentFilter_update.addCategory(Intent.CATEGORY_DEFAULT);
		registerReceiver(myBroadcastReceiver_Update, intentFilter_update);

	}

	@Override
	protected void onPause() {
		super.onPause();

	}

	@Override
	protected void onDestroy() {
		super.onDestroy();

		// un-register BroadcastReceiver
		try {
			unregisterReceiver(myBroadcastReceiver);
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			unregisterReceiver(myBroadcastReceiver_Update);
		} catch (Exception e) {
			e.printStackTrace();
		}

		try {
			LocalBroadcastManager.getInstance(getApplicationContext()).unregisterReceiver(cancel_data);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	@Override
	public void onClick(View v) {

		switch (v.getId()) {

		case R.id.profile_back:
			cancelAllNotification(NewOrderComingActivity.this);
			finish();
			Intent i = new Intent(NewOrderComingActivity.this, HomeActivity.class);
			i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
			startActivity(i);
			overridePendingTransition(R.anim.trans_right_in, R.anim.trans_right_out);
			break;

		case R.id.order_accpet_btn:

			DebugLog.d("booking_id : " + booking_id);
			cancelAllNotification(NewOrderComingActivity.this);

			if (NetworkUtilities.isInternet(getApplicationContext())) {

				AcceptRequestAsync async = new AcceptRequestAsync(NewOrderComingActivity.this, HandlerAcceptRequest,
						ApplicationConstant.API_URL + ApplicationConstant.METHOD_ACCEPT_BOOKING);
				async.execute(UserInfo.getString("access_token", ""), booking_id);
			}
			break;

		case R.id.user_call:

			DebugLog.d("user_name : " + name);
			DebugLog.d("user_email : " + contact_no);

			Intent callIntent = new Intent(Intent.ACTION_CALL);
			callIntent.setData(Uri.parse("tel:+" + contact_no.trim()));
			startActivity(callIntent);

			break;

		case R.id.order_reject_btn:

			DebugLog.d("booking_id : " + booking_id);
			cancelAllNotification(NewOrderComingActivity.this);

			if (NetworkUtilities.isInternet(getApplicationContext())) {

				RejectRequestAsync async = new RejectRequestAsync(NewOrderComingActivity.this, HandlerRejectRequest,
						ApplicationConstant.API_URL + ApplicationConstant.METHOD_REJECT_BOOKING);
				async.execute(UserInfo.getString("access_token", ""), booking_id);
			}

			break;
		default:
			break;
		}

	}

	public class MyBroadcastReceiver extends BroadcastReceiver {

		@Override
		public void onReceive(Context context, Intent intent) {
			String result = intent.getStringExtra(MyService.EXTRA_KEY_OUT);

			System.out.println("result=" + result);
			try {
				Intent i1 = new Intent(NewOrderComingActivity.this, HomeActivity.class);
				i1.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
				startActivity(i1);
				overridePendingTransition(R.anim.trans_right_in, R.anim.trans_right_out);
				finish();
			} catch (Exception e) {
				e.printStackTrace();
			}

			setDriverBusyToAvailable();

		}
	}

	public class MyBroadcastReceiver_Update extends BroadcastReceiver {

		@Override
		public void onReceive(Context context, Intent intent) {
			int update = intent.getIntExtra(MyService.EXTRA_KEY_UPDATE, 0);
			txtTimerTime.setText(update + "");
		}
	}

	private BroadcastReceiver cancel_data = new BroadcastReceiver() {

		@Override
		public void onReceive(Context context, Intent intent) {

			DebugLog.d("cancel_data : " + intent.getStringExtra("cancel_data"));

			JSONObject json = null;
			String notification_for = "";
			String Message = "";

			try {
				json = new JSONObject((intent.getStringExtra("cancel_data")).toString());
				notification_for = json.getJSONObject("response").getString("notification_for").toString();
				Message = json.getJSONObject("response").getString("Message").toString();

			} catch (JSONException e) {
				e.printStackTrace();
			}

			SharedPreferences.Editor edit = myPref.edit();
			edit.putBoolean("ServRun", false);
			edit.putLong("milliseconds", 0);
			edit.putString("timerstopped", "");
			edit.putString("launchScreen", "main");
			edit.commit();

			setDriverBusyToAvailable();

			Toast.makeText(getApplicationContext(), notification_for + "\n" + Message, Toast.LENGTH_LONG).show();

			Intent i1 = new Intent(NewOrderComingActivity.this, HomeActivity.class);
			i1.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
			startActivity(i1);
			overridePendingTransition(R.anim.trans_right_in, R.anim.trans_right_out);
			finish();

		}
	};

	@Override
	public void onBackPressed() {
		// super.onBackPressed();
	}

	@SuppressLint("HandlerLeak")
	private Handler HandlerAcceptRequest = new Handler() {

		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);

			if (msg.obj != null) {
				try {
					if (((JSONObject) msg.obj).getString("success").toString().equalsIgnoreCase("1")) {

						System.out.println("accept request response : " + ((JSONObject) msg.obj).toString());

						// LocalBroadcastManager.getInstance(getApplicationContext()).sendBroadcast(
						// new Intent("cancel_timer"));

						// change driver flag available to busy.
						SharedPreferences.Editor preferencesEditer = UserInfo.edit();
						preferencesEditer.putString("availibility", "busy");
						preferencesEditer.commit();

						SharedPreferences.Editor edit = myPref.edit();
						edit.putString("timerstopped", "yes");
						edit.putString("launchScreen", "main");
						edit.commit();

						Intent i1 = new Intent(NewOrderComingActivity.this, HomeActivity.class);
						i1.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);

						if (isNewOrderPush.trim().length() > 0) {
							i1.putExtra("isNewOrderPush", isNewOrderPush);
						}

						startActivity(i1);
						overridePendingTransition(R.anim.trans_left_in, R.anim.trans_left_out);
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

	@SuppressLint("HandlerLeak")
	private Handler HandlerRejectRequest = new Handler() {

		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);

			if (msg.obj != null) {
				try {
					if (((JSONObject) msg.obj).getString("success").toString().equalsIgnoreCase("1")) {

						System.out.println("reject request response : " + ((JSONObject) msg.obj).toString());

						setDriverBusyToAvailable();

						SharedPreferences.Editor edit = myPref.edit();
						edit.putString("timerstopped", "yes");
						edit.putString("launchScreen", "main");
						edit.commit();

						Intent i1 = new Intent(NewOrderComingActivity.this, HomeActivity.class);
						i1.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
						startActivity(i1);
						overridePendingTransition(R.anim.trans_right_in, R.anim.trans_right_out);
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

	// change driver flag busy to available.
	public void setDriverBusyToAvailable() {

		SharedPreferences.Editor preferencesEditer = UserInfo.edit();
		preferencesEditer.putString("availibility", "");
		preferencesEditer.putString("ride_detail", "");
		preferencesEditer.putString("ride_status", "");
		preferencesEditer.commit();

	}

	public static void cancelAllNotification(Context ctx) {
		String ns = Context.NOTIFICATION_SERVICE;
		NotificationManager nMgr = (NotificationManager) ctx.getSystemService(ns);
		nMgr.cancelAll();
	}

}
