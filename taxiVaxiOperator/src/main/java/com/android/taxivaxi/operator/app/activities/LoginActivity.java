package com.android.taxivaxi.operator.app.activities;

import java.io.IOException;

import org.json.JSONException;
import org.json.JSONObject;

import android.annotation.SuppressLint;
import android.app.ActionBar.LayoutParams;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnDismissListener;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager.NameNotFoundException;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.Settings.Secure;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.taxivaxi.operator.app.R;
import com.android.taxivaxi.operator.app.common.ApplicationConstant;
import com.android.taxivaxi.operator.app.common.CustomFontsLoader;
import com.android.taxivaxi.operator.app.common.DebugLog;
import com.android.taxivaxi.operator.app.common.NetworkUtilities;
import com.android.taxivaxi.operator.app.common.Validation;
import com.android.taxivaxi.operator.app.controller.ForgotPasswordAsync;
import com.android.taxivaxi.operator.app.controller.LoginAsync;
import com.android.taxivaxi.operator.app.custom.CustomProgressDialog;
import com.android.taxivaxi.operator.app.floatingtextview.FloatingLabelEditText;
import com.android.taxivaxi.operator.app.model.OperatorLoginResponseModel;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.gcm.GoogleCloudMessaging;
import com.google.gson.Gson;

public class LoginActivity extends Activity implements OnClickListener {

	private TextView login_button, btn_forgot_password;
	private FloatingLabelEditText edit_user_email, edit_user_password;

	public static final String EXTRA_MESSAGE = "message";
	public static final String PROPERTY_REG_ID = "registration_id";
	private static final String PROPERTY_APP_VERSION = "appVersion";
	private final static int PLAY_SERVICES_RESOLUTION_REQUEST = 9000;
	private String SENDER_ID = "499789922951";

	private GoogleCloudMessaging gcm;
	private SharedPreferences prefs;
	private Context context;
	private String regid;

	private Dialog spinningDialog;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login_screen);

		context = LoginActivity.this;

		login_button = (TextView) findViewById(R.id.login_button);
		btn_forgot_password = (TextView) findViewById(R.id.btn_forgot_password);
		edit_user_email = (FloatingLabelEditText) findViewById(R.id.edit_user_email);
		edit_user_password = (FloatingLabelEditText) findViewById(R.id.edit_user_password);

		login_button.setOnClickListener(this);
		btn_forgot_password.setOnClickListener(this);

		// set font to layout widgets
		Typeface typeFace = CustomFontsLoader.getTypeface(LoginActivity.this, 1);

		if (typeFace != null) {
			((TextView) findViewById(R.id.login_text)).setTypeface(typeFace);
			login_button.setTypeface(typeFace);
			btn_forgot_password.setTypeface(typeFace);
		}
	}

	@Override
	public void onClick(View v) {

		switch (v.getId()) {

		case R.id.login_button:

			if (NetworkUtilities.isInternet(getApplicationContext())) {

				if (checkPlayServices()) {

					spinningDialog = CustomProgressDialog.showProgressDialog(LoginActivity.this);
					spinningDialog.setCancelable(false);
					spinningDialog.show();

					gcm = GoogleCloudMessaging.getInstance(this);
					regid = getRegistrationId(context);

					DebugLog.d("auto gcmreg_id :" + regid);
					DebugLog.d("auto gcmreg_id length : " + regid.length());

					if (regid.isEmpty()) {
						registerInBackground();
					} else {
						callLoginRequest();
					}
				} else {
					Log.i("Logindriver", "No valid Google Play Services APK found.");
				}

			}

			break;

		case R.id.btn_forgot_password:

			MyCustomAlertDialog alertDialog = new MyCustomAlertDialog((Activity) context);
			alertDialog.createCustomDialog();
			break;

		default:
			break;
		}

	}

	private boolean checkPlayServices() {
		int resultCode = GooglePlayServicesUtil.isGooglePlayServicesAvailable(this);
		if (resultCode != ConnectionResult.SUCCESS) {
			if (GooglePlayServicesUtil.isUserRecoverableError(resultCode)) {
				GooglePlayServicesUtil.getErrorDialog(resultCode, this, PLAY_SERVICES_RESOLUTION_REQUEST).show();
			} else {
				Log.i("Logindriver", "This device is not supported.");
				finish();
			}
			return false;
		}
		return true;
	}

	private String getRegistrationId(Context context) {
		final SharedPreferences prefs = getGCMPreferences(context);
		String registrationId = prefs.getString(PROPERTY_REG_ID, "");
		if (registrationId.isEmpty()) {
			Log.i("Logindriver", "Registration not found.");
			return "";
		}
		int registeredVersion = prefs.getInt(PROPERTY_APP_VERSION, Integer.MIN_VALUE);
		int currentVersion = getAppVersion(context);
		if (registeredVersion != currentVersion) {
			Log.i("Logindriver", "App version changed.");
			return "";
		}
		return registrationId;
	}

	private SharedPreferences getGCMPreferences(Context context) {
		return getSharedPreferences(LoginActivity.class.getSimpleName(), Context.MODE_PRIVATE);
	}

	private static int getAppVersion(Context context) {
		try {
			PackageInfo packageInfo = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
			return packageInfo.versionCode;
		} catch (NameNotFoundException e) {
			throw new RuntimeException("Could not get package name: " + e);
		}
	}

	private void registerInBackground() {
		new AsyncTask<Object, String, String>() {

			protected void onPostExecute(String msg) {

				DebugLog.d("success gcmreg_id :" + msg);
				callLoginRequest();

			}

			@Override
			protected String doInBackground(Object... params) {

				String msg = "";
				try {
					if (gcm == null) {
						gcm = GoogleCloudMessaging.getInstance(context);
					}
					regid = gcm.register(SENDER_ID);
					msg = regid;

					storeRegistrationId(context, regid);

				} catch (IOException ex) {
					msg = "Error :" + ex.getMessage();
				}
				return msg;

			}
		}.execute(null, null, null);
	}

	private void callLoginRequest() {

		spinningDialog.dismiss();

		if (NetworkUtilities.isInternet(getApplicationContext())) {

			String android_id = Secure.getString(getContentResolver(), Secure.ANDROID_ID);

			prefs = getSharedPreferences(LoginActivity.class.getSimpleName(), Context.MODE_PRIVATE);
			regid = prefs.getString("registration_id", "");

			DebugLog.d("login device id : " + android_id);
			DebugLog.d("login gcm reg_id :" + regid);
			DebugLog.d("login email id : " + edit_user_email.getInputWidgetText().toString().trim());
			DebugLog.d("login password : "+ edit_user_password.getInputWidgetText().toString().trim());


			if (Validation.LoginActivityValidation(edit_user_email.getInputWidgetText().toString().trim(),
					edit_user_password.getInputWidgetText().toString().trim(), LoginActivity.this)) {

				LoginAsync mLoginUserAsync = new LoginAsync(LoginActivity.this, loginHandler,
						ApplicationConstant.API_URL + ApplicationConstant.METHOD_LOGIN);

				mLoginUserAsync.execute(edit_user_email.getInputWidgetText().toString().trim(), edit_user_password
						.getInputWidgetText().toString().trim(), android_id, regid);
			}
		} else {
			Toast.makeText(getApplicationContext(), "No internet connection.", Toast.LENGTH_SHORT).show();
		}
	}

	private void storeRegistrationId(Context context, String regId) {
		final SharedPreferences prefs = getGCMPreferences(context);
		int appVersion = getAppVersion(context);
		Log.i("Logindriver", "Saving regId on app version " + appVersion);
		SharedPreferences.Editor editor = prefs.edit();
		editor.putString(PROPERTY_REG_ID, regId);
		editor.putInt(PROPERTY_APP_VERSION, appVersion);
		editor.commit();
	}

	@SuppressLint("HandlerLeak")
	private Handler loginHandler = new Handler() {
		@Override
		public void handleMessage(Message msg) {

			if (msg.obj != null) {
				try {
					if (((JSONObject) msg.obj).getString("success").toString().equalsIgnoreCase("1")) {

								Toast.makeText(getApplicationContext(),"Json response access_token : "+ ((JSONObject) msg.obj).getJSONObject("response").getString("access_token"),
										Toast.LENGTH_LONG).show();
						DebugLog.d("driver login response : " + ((JSONObject) msg.obj).toString());

						Gson gson = new Gson();
						OperatorLoginResponseModel responseModel = new OperatorLoginResponseModel();

						responseModel = gson.fromJson(((JSONObject) msg.obj).toString(), responseModel.getClass());

						SharedPreferences UserInfo = getSharedPreferences("UserInfo", Context.MODE_PRIVATE);
						SharedPreferences.Editor preferencesEditer = UserInfo.edit();

						preferencesEditer.putBoolean("isLogin", true);
						preferencesEditer.putString("access_token", responseModel.getResponse().getAccess_token()
								.toString());
						preferencesEditer.putString("contact_no",
								String.valueOf(responseModel.getResponse().getOperator().getContact_no().toString()));
						preferencesEditer.putString("id", responseModel.getResponse().getOperator().getId().toString());

						preferencesEditer.putString("address", responseModel.getResponse().getOperator().getAddress()
								.toString());
						preferencesEditer.putString("website", responseModel.getResponse().getOperator().getWebsite()
								.toString());

						preferencesEditer.putString("name", responseModel.getResponse().getOperator().getName()
								.toString());
						preferencesEditer.putString("email", responseModel.getResponse().getOperator().getEmail()
								.toString());

						preferencesEditer.putString("privacy_policy_url", responseModel.getResponse().getOperator()
								.getPrivacy_policy_url().toString());

						preferencesEditer.putString("terms_url", responseModel.getResponse().getOperator()
								.getTerms_url().toString());

						preferencesEditer.commit();

						Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
						intent.putExtra("DRIVER_LOGIN_RESPONSE", responseModel);
						startActivity(intent);
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
		};
	};

	public class MyCustomAlertDialog {

		Activity _Activity;
		public Dialog resource_Dialog;

		public MyCustomAlertDialog(Activity _Activity) {
			this._Activity = _Activity;

		}

		public void createCustomDialog() {

			resource_Dialog = new Dialog(_Activity);
			resource_Dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
			resource_Dialog.setContentView(R.layout.dialog_custom_forgot_password);
			resource_Dialog.getWindow().setLayout(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);

			WindowManager.LayoutParams WMLP = resource_Dialog.getWindow().getAttributes();
			WMLP.dimAmount = (float) 0.4;

			resource_Dialog.getWindow().setAttributes(WMLP);
			resource_Dialog.setOnDismissListener(new OnDismissListener() {

				@Override
				public void onDismiss(DialogInterface dialog) {

				}
			});

			((TextView) resource_Dialog.findViewById(R.id.dialogTitle)).setText("Reset Your Password");

			((Button) resource_Dialog.findViewById(R.id.dialogButtonPositive))
					.setOnClickListener(new OnClickListener() {

						@Override
						public void onClick(View v) {

							if (!Validation.forgotPasswordActivityValidation(
									((TextView) resource_Dialog.findViewById(R.id.forgot_email_text)).getText()
											.toString().trim(), _Activity)) {

							} else {

								ForgotPasswordAsync mAsync = new ForgotPasswordAsync(LoginActivity.this, forgotHandler,
										ApplicationConstant.API_URL + ApplicationConstant.METHOD_FORGOT_PASSWORD);

								mAsync.execute(((TextView) resource_Dialog.findViewById(R.id.forgot_email_text))
										.getText().toString().trim());

							}
							resource_Dialog.dismiss();
						}
					});

			((Button) resource_Dialog.findViewById(R.id.dialogButtonNegative))
					.setOnClickListener(new OnClickListener() {

						@Override
						public void onClick(View v) {
							resource_Dialog.dismiss();
						}
					});

			resource_Dialog.show();
		}
	}

	@SuppressLint("HandlerLeak")
	public Handler forgotHandler = new Handler() {

		@Override
		public void handleMessage(Message msg) {
			if (msg.obj != null) {
				try {
					if (((JSONObject) msg.obj).getString("success").toString().equalsIgnoreCase("1")) {

						Toast.makeText(LoginActivity.this,
								"Password is sent to your email address. Please check and sign in.", Toast.LENGTH_SHORT)
								.show();

					} else {

						Toast.makeText(LoginActivity.this, ((JSONObject) msg.obj).getString("error").toString(),
								Toast.LENGTH_SHORT).show();
					}
				} catch (JSONException e) {
					e.printStackTrace();
				}
			}
		};

	};
}
