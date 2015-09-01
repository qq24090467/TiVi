package com.android.taxivaxi.operator.app.model;

import android.os.Parcel;
import android.os.Parcelable;

public class OperatorLoginResponseModel implements Parcelable {

	LoginResponse response;

	String error;
	String success;

	public OperatorLoginResponseModel(Parcel in) {

		response = in.readParcelable(LoginResponse.class.getClassLoader());
		error = in.readString();
		success = in.readString();
	}

	public OperatorLoginResponseModel() {
	}

	public OperatorLoginResponseModel(LoginResponse response, String access_token, String error, String success) {
		super();
		this.response = response;

		this.error = error;
		this.success = success;
	}

	public LoginResponse getResponse() {
		return response;
	}

	public void setResponse(LoginResponse response) {
		this.response = response;
	}

	public String getError() {
		return error;
	}

	public void setError(String error) {
		this.error = error;
	}

	public String getSuccess() {
		return success;
	}

	public void setSuccess(String success) {
		this.success = success;
	}

	@Override
	public int describeContents() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		// TODO Auto-generated method stub
		dest.writeParcelable(response, flags);

		dest.writeString(error);
		dest.writeString(success);
	}

	@Override
	public String toString() {
		return "DriverLoginResponseModel [response=" + response + ", error=" + error + ", success=" + success + "]";
	}

	public static final Parcelable.Creator<OperatorLoginResponseModel> CREATOR = new Parcelable.Creator<OperatorLoginResponseModel>() {
		public OperatorLoginResponseModel createFromParcel(Parcel in) {
			return new OperatorLoginResponseModel(in);
		}

		public OperatorLoginResponseModel[] newArray(int size) {
			return new OperatorLoginResponseModel[size];
		}
	};

}
