package com.android.taxivaxi.operator.app.controller;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import android.app.Dialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Message;
import android.widget.Toast;

import com.android.taxivaxi.operator.app.common.DebugLog;
import com.android.taxivaxi.operator.app.custom.CustomProgressDialog;
import com.android.taxivaxi.operator.app.webservice.JSONParser;

public class LoginAsync extends AsyncTask<String, String, JSONObject> {

	private Context context;
	private Handler mHandler;
	private String URL;
	private JSONParser jsonParser = new JSONParser();
	private Dialog spinningDialog;

	public LoginAsync(Context context, Handler handler, String url) {

		this.context = context;
		this.mHandler = handler;
		this.URL = url;
	}

	@Override
	protected void onPreExecute() {
		super.onPreExecute();

		spinningDialog = CustomProgressDialog.showProgressDialog(context);
		spinningDialog.setCancelable(false);
		spinningDialog.show();
	}

	@Override
	protected JSONObject doInBackground(String... params) {

		JSONObject json = null;

		List<NameValuePair> postParameters = new ArrayList<NameValuePair>();
		postParameters.add(new BasicNameValuePair("username", params[0]));
		postParameters.add(new BasicNameValuePair("password", params[1]));
		postParameters.add(new BasicNameValuePair("device_id", params[2]));
		postParameters.add(new BasicNameValuePair("gcm_regId", params[3]));
		try {
			json = jsonParser.makeHttpRequest(URL, "POST", postParameters);
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		DebugLog.d(json.toString());
		return json;
	}

	@Override
	protected void onPostExecute(JSONObject result) {
		super.onPostExecute(result);

		spinningDialog.dismiss();

		try {
			if (result != null) {
				Message msg = new Message();
				msg.obj = result;
				mHandler.sendMessage(msg);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
