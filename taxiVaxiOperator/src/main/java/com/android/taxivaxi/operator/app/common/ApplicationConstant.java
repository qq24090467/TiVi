package com.android.taxivaxi.operator.app.common;

import android.content.Context;
import android.content.res.Configuration;

public class ApplicationConstant {

	public static final boolean DEVELOPER_MODE = false;

	public static boolean isTablet(Context context) {
		return (context.getResources().getConfiguration().screenLayout & Configuration.SCREENLAYOUT_SIZE_MASK) >= Configuration.SCREENLAYOUT_SIZE_LARGE;
	}

	// debug url
	public static String API_URL = "http://taxivaxi.com/api/operators";

	public static String METHOD_LOGIN = "/login/";
	public static String METHOD_LOGOUT = "/logout/";
	public static String METHOD_CHANGE_PASSWORD = "/changePassword/";
	public static String METHOD_FORGOT_PASSWORD = "/forgetPassword/";

	public static String METHOD_ACCEPT_BOOKING = "/acceptBooking/";
	public static String METHOD_REJECT_BOOKING = "/rejectBooking/";
	public static String METHOD_TIMEOUT_BOOKING = "/timeoutBooking/";

	public static String METHOD_BOOKING_HISTORY = "http://taxivaxi.com/api/tour_bookings/getAll";
	public static String METHOD_ASSIGN_TAXI = "http://taxivaxi.com/api/tour_bookings/assignTaxi";
	public static String METHOD_GET_TAXIS = "http://taxivaxi.com/api/taxis/getAllByModel";
	public static String METHOD_ASSIGN_DRIVER = "http://taxivaxi.com/api/tour_bookings/assignDriver";
	public static String METHOD_GET_DRIVERS = "http://taxivaxi.com/api/drivers/getAllByCity";


}
