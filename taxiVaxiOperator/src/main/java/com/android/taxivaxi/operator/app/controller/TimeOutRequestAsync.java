package com.android.taxivaxi.operator.app.controller;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import android.content.Context;
import android.os.AsyncTask;

import com.android.taxivaxi.operator.app.common.DebugLog;
import com.android.taxivaxi.operator.app.webservice.JSONParser;

public class TimeOutRequestAsync extends AsyncTask<String, String, JSONObject> {

	private String URL;
	private JSONParser jsonParser = new JSONParser();

	public TimeOutRequestAsync(Context context, String url) {

		this.URL = url;
	}

	@Override
	protected void onPreExecute() {
		super.onPreExecute();

	}

	@Override
	protected JSONObject doInBackground(String... params) {

		JSONObject json = null;

		List<NameValuePair> postParameters = new ArrayList<NameValuePair>();
		postParameters.add(new BasicNameValuePair("access_token", params[0]));
		postParameters.add(new BasicNameValuePair("booking_id", params[1]));

		try {
			json = jsonParser.makeHttpRequest(URL, "POST", postParameters);
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		return json;
	}

	@Override
	protected void onPostExecute(JSONObject result) {
		super.onPostExecute(result);

		try {
			DebugLog.d("Time out resposne : " + result.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
