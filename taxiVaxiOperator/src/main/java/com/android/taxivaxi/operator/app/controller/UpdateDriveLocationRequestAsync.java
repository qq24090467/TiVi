package com.android.taxivaxi.operator.app.controller;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import android.os.AsyncTask;

import com.android.taxivaxi.operator.app.webservice.JSONParser;

public class UpdateDriveLocationRequestAsync extends AsyncTask<String, String, JSONObject> {

	private String URL;
	private JSONParser jsonParser = new JSONParser();

	public UpdateDriveLocationRequestAsync(String url) {

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
		postParameters.add(new BasicNameValuePair("request_id", params[1]));
		postParameters.add(new BasicNameValuePair("latitude", params[2]));
		postParameters.add(new BasicNameValuePair("longitude", params[3]));

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

	}
}
