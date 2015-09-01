package com.android.taxivaxi.operator.app.common;

import org.apache.http.client.HttpClient;
import org.apache.http.conn.params.ConnManagerParams;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.widget.Toast;

public class NetworkUtilities {

	public static HttpClient mHttpClient;
	public static final int REGISTRATION_TIMEOUT = 2 * 1000;

	public static boolean isInternet(Context context) {
		boolean isInternet = false;

		ConnectivityManager con = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
		if (con.getNetworkInfo(0) != null && con.getNetworkInfo(1) != null) {

			if (con.getNetworkInfo(0).getState() == NetworkInfo.State.CONNECTED
					|| con.getNetworkInfo(0).getState() == NetworkInfo.State.CONNECTING
					|| con.getNetworkInfo(1).getState() == NetworkInfo.State.CONNECTED
					|| con.getNetworkInfo(1).getState() == NetworkInfo.State.CONNECTING) {
				isInternet = true;

			} else if (con.getNetworkInfo(0).getState() == NetworkInfo.State.DISCONNECTED
					|| con.getNetworkInfo(1).getState() == NetworkInfo.State.DISCONNECTED) {
				isInternet = false;

			}
		} else if (con.getNetworkInfo(0) != null) {

			if (con.getNetworkInfo(0).getState() == NetworkInfo.State.CONNECTED
					|| con.getNetworkInfo(0).getState() == NetworkInfo.State.CONNECTING) {
				isInternet = true;

			} else if (con.getNetworkInfo(0).getState() == NetworkInfo.State.DISCONNECTED) {
				Toast.makeText(context, "No network", Toast.LENGTH_LONG).show();
				isInternet = false;

			}
		} else if (con.getNetworkInfo(1) != null) {

			if (con.getNetworkInfo(1).getState() == NetworkInfo.State.CONNECTED
					|| con.getNetworkInfo(1).getState() == NetworkInfo.State.CONNECTING) {
				isInternet = true;

			} else if (con.getNetworkInfo(1).getState() == NetworkInfo.State.DISCONNECTED) {
				isInternet = false;

			}
		}
		return isInternet;
	}

	/**
	 * Configures the httpClient to connect to the URL provided.
	 */
	public static void maybeCreateHttpClient() {
		if (mHttpClient == null) {
			mHttpClient = new DefaultHttpClient();
			final HttpParams params = mHttpClient.getParams();
			HttpConnectionParams.setConnectionTimeout(params, REGISTRATION_TIMEOUT);
			HttpConnectionParams.setSoTimeout(params, REGISTRATION_TIMEOUT);
			ConnManagerParams.setTimeout(params, REGISTRATION_TIMEOUT);
		}
	}

}
