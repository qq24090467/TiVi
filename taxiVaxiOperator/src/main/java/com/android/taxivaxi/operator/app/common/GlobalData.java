package com.android.taxivaxi.operator.app.common;

import java.util.ArrayList;

import android.app.Application;
import android.content.Context;
import android.os.Build;
import android.os.StrictMode;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;

public class GlobalData extends Application {

	@Override
	public void onCreate() {
		if (ApplicationConstant.DEVELOPER_MODE) {
			if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.GINGERBREAD) {
				StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder().detectAll().penaltyDialog().build());
				StrictMode.setVmPolicy(new StrictMode.VmPolicy.Builder().detectAll().penaltyDeath().build());
			}
		}
		super.onCreate();
		initImageLoader(getApplicationContext());
	}

	public static void initImageLoader(Context context) {
		ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(context)
				.threadPriority(Thread.NORM_PRIORITY - 2).denyCacheImageMultipleSizesInMemory()
				// .diskCacheFileNameGenerator(new Md5FileNameGenerator())
				.tasksProcessingOrder(QueueProcessingType.LIFO).writeDebugLogs().build();
		ImageLoader.getInstance().init(config);
	}

	public static GlobalData mGlobalData;

	private String screen_from = "";
	private boolean selectedACTV = false;
	private String tempPlace = "";

	// users current location details
	private Double Latitude = 0.0;
	private Double Longitude = 0.0;
	private String Address = "";
	private String CurrentCity = "";
	private String PostalCode = "";
	private String Country = "";

	// users login status flag
	private boolean islogin = false;
	private String is_mobile_validated = "0";
	private String access_token;

	// user profile info
	private String contact_no;

	// search screen flags
	private boolean radio_taxi_rode_now = true;
	private boolean tour_taxi_local = true;

	// search parameters
	private String pickup_date = "";
	private String pickup_time = "";
	private String pickup_city = "";
	private String drop_city = "";
	private ArrayList<TextView> trip_cities_layout = new ArrayList<TextView>();
	private String radio_group_trip_selected = "";
	private String duration_hour = "";
	private String duration_days = "";
	private boolean is_duration_hour_selected = true;

	// search filter values
	private String is_filter_applied = "0";
	private String no_of_seat = "";
	private ArrayList<String> taxi_type_ids = null;
	private ArrayList<String> operator_type_ids = null;
	private String taxi_type_id_final = "";
	private String operator_type_id_final = "";

	// -------------------------------
	private String request_id = "";

	public String getRequest_id() {
		return request_id;
	}

	public void setRequest_id(String request_id) {
		this.request_id = request_id;
	}

	public String getDuration_hour() {
		return duration_hour;
	}

	public void setDuration_hour(String duration_hour) {
		this.duration_hour = duration_hour;
	}

	public String getDuration_days() {
		return duration_days;
	}

	public void setDuration_days(String duration_days) {
		this.duration_days = duration_days;
	}

	public boolean isIs_duration_hour_selected() {
		return is_duration_hour_selected;
	}

	public void setIs_duration_hour_selected(boolean is_duration_hour_selected) {
		this.is_duration_hour_selected = is_duration_hour_selected;
	}

	public String getPickup_city() {
		return pickup_city;
	}

	public void setPickup_city(String pickup_city) {
		this.pickup_city = pickup_city;
	}

	public String getDrop_city() {
		return drop_city;
	}

	public void setDrop_city(String drop_city) {
		this.drop_city = drop_city;
	}

	public String getRadio_group_trip_selected() {
		return radio_group_trip_selected;
	}

	public void setRadio_group_trip_selected(String radio_group_trip_selected) {
		this.radio_group_trip_selected = radio_group_trip_selected;
	}

	public String getIs_mobile_validated() {
		return is_mobile_validated;
	}

	public void setIs_mobile_validated(String is_mobile_validated) {
		this.is_mobile_validated = is_mobile_validated;
	}

	public String getContact_no() {
		return contact_no;
	}

	public void setContact_no(String contact_no) {
		this.contact_no = contact_no;
	}

	public ArrayList<TextView> getTrip_cities_layout() {
		return trip_cities_layout;
	}

	public void setTrip_cities_layout(ArrayList<TextView> trip_cities_layout) {
		this.trip_cities_layout = trip_cities_layout;
	}

	public String getOperator_type_id_final() {
		return operator_type_id_final;
	}

	public void setOperator_type_id_final(String operator_type_id_final) {
		this.operator_type_id_final = operator_type_id_final;
	}

	public String getTaxi_type_id_final() {
		return taxi_type_id_final;
	}

	public void setTaxi_type_id_final(String taxi_type_id_final) {
		this.taxi_type_id_final = taxi_type_id_final;
	}

	public String getIs_filter_applied() {
		return is_filter_applied;
	}

	public void setIs_filter_applied(String is_filter_applied) {
		this.is_filter_applied = is_filter_applied;
	}

	public static GlobalData getmGlobalData() {
		return mGlobalData;
	}

	public static void setmGlobalData(GlobalData mGlobalData) {
		GlobalData.mGlobalData = mGlobalData;
	}

	public String getNo_of_seat() {
		return no_of_seat;
	}

	public void setNo_of_seat(String no_of_seat) {
		this.no_of_seat = no_of_seat;
	}

	public ArrayList<String> getTaxi_type_ids() {
		return taxi_type_ids;
	}

	public void setTaxi_type_ids(ArrayList<String> taxi_type_ids) {
		this.taxi_type_ids = taxi_type_ids;
	}

	public ArrayList<String> getOperator_type_ids() {
		return operator_type_ids;
	}

	public void setOperator_type_ids(ArrayList<String> operator_type_ids) {
		this.operator_type_ids = operator_type_ids;
	}

	public boolean isTour_taxi_local() {
		return tour_taxi_local;
	}

	public void setTour_taxi_local(boolean tour_taxi_local) {
		this.tour_taxi_local = tour_taxi_local;
	}

	public boolean isRadio_taxi_rode_now() {
		return radio_taxi_rode_now;
	}

	public void setRadio_taxi_rode_now(boolean radio_taxi_rode_now) {
		this.radio_taxi_rode_now = radio_taxi_rode_now;
	}

	public String getPickup_date() {
		return pickup_date;
	}

	public void setPickup_date(String pickup_date) {
		this.pickup_date = pickup_date;
	}

	public String getPickup_time() {
		return pickup_time;
	}

	public void setPickup_time(String pickup_time) {
		this.pickup_time = pickup_time;
	}

	public String getAccess_token() {
		return access_token;
	}

	public void setAccess_token(String access_token) {
		this.access_token = access_token;
	}

	public boolean isIslogin() {
		return islogin;
	}

	public void setIslogin(boolean islogin) {
		this.islogin = islogin;
	}

	public Double getLatitude() {
		return Latitude;
	}

	public void setLatitude(Double latitude) {
		Latitude = latitude;
	}

	public Double getLongitude() {
		return Longitude;
	}

	public void setLongitude(Double longitude) {
		Longitude = longitude;
	}

	public String getAddress() {
		return Address;
	}

	public void setAddress(String address) {
		Address = address;
	}

	public String getCurrentCity() {
		return CurrentCity;
	}

	public void setCurrentCity(String currentCity) {
		CurrentCity = currentCity;
	}

	public String getPostalCode() {
		return PostalCode;
	}

	public void setPostalCode(String postalCode) {
		PostalCode = postalCode;
	}

	public String getCountry() {
		return Country;
	}

	public void setCountry(String country) {
		Country = country;
	}

	public String getTempPlace() {
		return tempPlace;
	}

	public void setTempPlace(String tempPlace) {
		this.tempPlace = tempPlace;
	}

	public boolean isSelectedACTV() {
		return selectedACTV;
	}

	public void setSelectedACTV(boolean selectedACTV) {
		this.selectedACTV = selectedACTV;
	}

	public String getScreen_from() {
		return screen_from;
	}

	public void setScreen_from(String screen_from) {
		this.screen_from = screen_from;
	}

	public static GlobalData getInstance() {

		if (mGlobalData == null) {
			mGlobalData = new GlobalData();
		}
		return mGlobalData;
	}

}
