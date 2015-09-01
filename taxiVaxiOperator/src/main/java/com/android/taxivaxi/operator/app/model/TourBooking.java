package com.android.taxivaxi.operator.app.model;

import android.os.Parcel;
import android.os.Parcelable;

public class TourBooking implements Parcelable {

	private String id, user_id, taxi_id, driver_id, tour_type, pickup_location, p_lat, p_long, cities, days, hours,
			booking_date;
	private String pickup_datetime, promo_code, no_of_seats, status_id, created, modified, status,status_color;

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getUser_id() {
		return user_id;
	}

	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}

	public String getTaxi_id() {
		return taxi_id;
	}

	public void setTaxi_id(String taxi_id) {
		this.taxi_id = taxi_id;
	}

	public String getDriver_id() {
		return driver_id;
	}

	public void setDriver_id(String driver_id) {
		this.driver_id = driver_id;
	}

	public String getTour_type() {
		return tour_type;
	}

	public void setTour_type(String tour_type) {
		this.tour_type = tour_type;
	}

	public String getPickup_location() {
		return pickup_location;
	}

	public void setPickup_location(String pickup_location) {
		this.pickup_location = pickup_location;
	}

	public String getP_lat() {
		return p_lat;
	}

	public void setP_lat(String p_lat) {
		this.p_lat = p_lat;
	}

	public String getP_long() {
		return p_long;
	}

	public void setP_long(String p_long) {
		this.p_long = p_long;
	}

	public String getCities() {
		return cities;
	}

	public void setCities(String cities) {
		this.cities = cities;
	}

	public String getDays() {
		return days;
	}

	public void setDays(String days) {
		this.days = days;
	}

	public String getHours() {
		return hours;
	}

	public void setHours(String hours) {
		this.hours = hours;
	}

	public String getBooking_date() {
		return booking_date;
	}

	public void setBooking_date(String booking_date) {
		this.booking_date = booking_date;
	}

	public String getPickup_datetime() {
		return pickup_datetime;
	}

	public void setPickup_datetime(String pickup_datetime) {
		this.pickup_datetime = pickup_datetime;
	}

	public String getPromo_code() {
		return promo_code;
	}

	public void setPromo_code(String promo_code) {
		this.promo_code = promo_code;
	}
	public String getNo_of_seats() {
		return no_of_seats;
	}

	public void setNo_of_seats(String no_of_seats) {
		this.no_of_seats = no_of_seats;
	}

	public String getStatus_id() {
		return status_id;
	}

	public void setStatus_id(String status_id) {
		this.status_id = status_id;
	}

	public String getCreated() {
		return created;
	}

	public void setCreated(String created) {
		this.created = created;
	}

	public String getModified() {
		return modified;
	}

	public void setModified(String modified) {
		this.modified = modified;
	}
    public void setStatus_color(String status_color){ this.status_color = status_color;}
    public String getStatus_color(){return status_color;};


	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeString(booking_date);
		dest.writeString(cities);
		dest.writeString(created);
		dest.writeString(days);
		dest.writeString(driver_id);
		dest.writeString(hours);
		dest.writeString(id);
		dest.writeString(modified);
		dest.writeString(no_of_seats);
		dest.writeString(p_lat);
		dest.writeString(p_long);
		dest.writeString(pickup_datetime);
		dest.writeString(pickup_location);
		dest.writeString(promo_code);
		dest.writeString(status_id);
		dest.writeString(tour_type);
		dest.writeString(taxi_id);
		dest.writeString(user_id);
	}

}
