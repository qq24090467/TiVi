package com.android.taxivaxi.operator.app.model;

import java.util.ArrayList;

public class MyBookingHistoryData {

	private String success;
	private String error;
	private Response response;

	public String getSuccess() {
		return success;
	}

	public void setSuccess(String success) {
		this.success = success;
	}

	public String getError() {
		return error;
	}

	public void setError(String error) {
		this.error = error;
	}

	public Response getResponse() {
		return response;
	}

	public void setResponse(Response response) {
		this.response = response;
	}

	public static class Response {

		private String access_token;

		private ArrayList<TourBooking> Bookings;

		public String getAccess_token() {
			return access_token;
		}

		public void setAccess_token(String access_token) {
			this.access_token = access_token;
		}

		public ArrayList<TourBooking> getBookings() {
			return Bookings;
		}

		public void setBookings(ArrayList<TourBooking> Bookings) {
			this.Bookings = Bookings;
		}

	}

}
