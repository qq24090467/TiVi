package com.android.taxivaxi.operator.app.location;

import java.util.Timer;
import java.util.TimerTask;

import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

public class GetLocationInfoFromGPS {

	private Context mContext = null;
	public static double mLatitude = 0, mLongitude = 0;
	private Handler mHandler = null;
	Timer timer;
	LocationManager lm;
	boolean gps_enabled = false;
	boolean network_enabled = false;

	public GetLocationInfoFromGPS(Context context, Handler handler) {
		mContext = context;
		mHandler = handler;
	}

	public GetLocationInfoFromGPS(Context context) {
		mContext = context;
	}

	/**
	 * Finds the location through GPS or Network.
	 */
	public void getLocationInfo() {

		lm = (LocationManager) mContext.getSystemService(Context.LOCATION_SERVICE);

		gps_enabled = lm.isProviderEnabled(LocationManager.GPS_PROVIDER);
		network_enabled = lm.isProviderEnabled(LocationManager.NETWORK_PROVIDER);

		gps_enabled = lm.isProviderEnabled(LocationManager.GPS_PROVIDER);
		network_enabled = lm.isProviderEnabled(LocationManager.NETWORK_PROVIDER);

		if (!gps_enabled && !network_enabled) {
			mLatitude = 0.0;
			mLongitude = 0.0;
			Message message = new Message();
			message.what = EventsToHandle.PROVIDER_DISABLE.ordinal();
			Bundle bundle = new Bundle();
			bundle.putDouble("LATITUDE", 0.0);
			bundle.putDouble("LONGITUDE", 0.0);
			message.setData(bundle);
			mHandler.sendMessage(message);

		}

		if (gps_enabled)
			lm.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListenerGps);
		if (network_enabled)
			lm.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, locationListenerNetwork);

		timer = new Timer();
		timer.schedule(new GetLastLocation(), 9000);

	}

	/**
	 * if location found by GPS ,cancel the timer, and remove update and send
	 * location to calling Activity
	 */

	LocationListener locationListenerGps = new LocationListener() {
		public void onLocationChanged(Location location) {
			timer.cancel();
			mLatitude = location.getLatitude();
			mLongitude = location.getLongitude();
			lm.removeUpdates(this);
			lm.removeUpdates(locationListenerNetwork);
			Message message = new Message();
			message.what = EventsToHandle.LATLONG_FOUND.ordinal();
			Bundle bundle = new Bundle();
			bundle.putDouble("LATITUDE", mLatitude);
			bundle.putDouble("LONGITUDE", mLongitude);
			message.setData(bundle);
			mHandler.sendMessage(message);

			Log.d("mLatitudFromGPS", mLatitude + "");

		}

		public void onProviderDisabled(String provider) {
		}

		public void onProviderEnabled(String provider) {
		}

		public void onStatusChanged(String provider, int status, Bundle extras) {
		}
	};

	/**
	 * if location found by Network ,cancel the timer, and remove update and
	 * send location to calling Activity
	 */

	LocationListener locationListenerNetwork = new LocationListener() {
		public void onLocationChanged(Location location) {
			timer.cancel();
			mLatitude = location.getLatitude();
			mLongitude = location.getLongitude();
			lm.removeUpdates(this);
			lm.removeUpdates(locationListenerGps);
			Message message = new Message();
			message.what = EventsToHandle.LATLONG_FOUND.ordinal();
			Bundle bundle = new Bundle();
			bundle.putDouble("LATITUDE", mLatitude);
			bundle.putDouble("LONGITUDE", mLongitude);
			message.setData(bundle);
			mHandler.sendMessage(message);

			Log.d("mLatitudeFromNetwork", mLatitude + "");

		}

		public void onProviderDisabled(String provider) {
		}

		public void onProviderEnabled(String provider) {
		}

		public void onStatusChanged(String provider, int status, Bundle extras) {
		}
	};

	class GetLastLocation extends TimerTask {
		// private static final String EventsToHandle = null;

		@Override
		public void run() {
			lm.removeUpdates(locationListenerGps);
			lm.removeUpdates(locationListenerNetwork);

			Location net_loc = null, gps_loc = null;
			if (gps_enabled)
				gps_loc = lm.getLastKnownLocation(LocationManager.GPS_PROVIDER);
			if (network_enabled)
				net_loc = lm.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
			/**
			 * if location found by both provider, use latest location
			 */

			// if there are both values use the latest one
			if (gps_loc != null && net_loc != null) {
				if (gps_loc.getTime() > net_loc.getTime()) {
					mLatitude = gps_loc.getLatitude();
					mLongitude = gps_loc.getLongitude();
					Message message = new Message();
					message.what = EventsToHandle.LATLONG_FOUND.ordinal();
					Bundle bundle = new Bundle();
					bundle.putDouble("LATITUDE", mLatitude);
					bundle.putDouble("LONGITUDE", mLongitude);
					message.setData(bundle);
					mHandler.sendMessage(message);

					Log.d("mLatitudeGPSTimer", mLatitude + "");
				} else {
					mLatitude = net_loc.getLatitude();
					mLongitude = net_loc.getLongitude();
					Message message = new Message();
					message.what = EventsToHandle.LATLONG_FOUND.ordinal();
					Bundle bundle = new Bundle();
					bundle.putDouble("LATITUDE", mLatitude);
					bundle.putDouble("LONGITUDE", mLongitude);
					message.setData(bundle);
					mHandler.sendMessage(message);

					Log.d("mLatitudeFromNetworkTimer", mLatitude + "");

				}

			} else if (gps_loc != null) {
				{
					mLatitude = gps_loc.getLatitude();
					mLongitude = gps_loc.getLongitude();
					Message message = new Message();
					message.what = EventsToHandle.LATLONG_FOUND.ordinal();
					Bundle bundle = new Bundle();
					bundle.putDouble("LATITUDE", mLatitude);
					bundle.putDouble("LONGITUDE", mLongitude);
					message.setData(bundle);
					mHandler.sendMessage(message);

					Log.d("mLatitudegpsonly", mLatitude + "");
				}

			} else if (net_loc != null) {
				{
					mLatitude = net_loc.getLatitude();
					mLongitude = net_loc.getLongitude();
					Message message = new Message();
					message.what = EventsToHandle.LATLONG_FOUND.ordinal();
					Bundle bundle = new Bundle();
					bundle.putDouble("LATITUDE", mLatitude);
					bundle.putDouble("LONGITUDE", mLongitude);
					message.setData(bundle);
					mHandler.sendMessage(message);

					Log.d("mLatitudeFromNetworkonly", mLatitude + "");

				}
			} else {
				mLatitude = 0.0;
				mLongitude = 0.0;
				Message message = new Message();
				message.what = EventsToHandle.FAILURE.ordinal();
				Bundle bundle = new Bundle();
				bundle.putDouble("LATITUDE", 0.0);
				bundle.putDouble("LONGITUDE", 0.0);
				message.setData(bundle);
				mHandler.sendMessage(message);
				Log.d("mLatitudeNotFound", mLatitude + "");
			}
			// Toast toast = Toast.makeText(context, "no last know avilable",
			// duration);

		}
	}

	// ////////////////////////////////////////

	/**
	 * check status of GPS in device
	 * 
	 */

}
