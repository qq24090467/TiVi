package com.android.taxivaxi.operator.app.model;

import android.os.Parcel;
import android.os.Parcelable;

public class LoginResponse implements Parcelable {

	Operator operator;
	String access_token;

	public LoginResponse() {
	}

	@Override
	public String toString() {
		return "Response [operator=" + operator + ", access_token=" + access_token + "]";
	}

	public Operator getOperator() {
		return operator;
	}

	public void setOperator(Operator operator) {
		this.operator = operator;
	}

	public String getAccess_token() {
		return access_token;
	}

	public void setAccess_token(String access_token) {
		this.access_token = access_token;
	}

	public LoginResponse(Operator operator, String access_token) {
		super();
		this.operator = operator;
		this.access_token = access_token;
	}

	public LoginResponse(Parcel in) {
		// TODO Auto-generated constructor stub
		operator = in.readParcelable(Operator.class.getClassLoader());
		access_token = in.readString();
	}

	@Override
	public int describeContents() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		// TODO Auto-generated method stub
		dest.writeParcelable(operator, flags);
		dest.writeString(access_token);

	}

	public static final Parcelable.Creator<LoginResponse> CREATOR = new Parcelable.Creator<LoginResponse>() {
		public LoginResponse createFromParcel(Parcel in) {
			return new LoginResponse(in);
		}

		public LoginResponse[] newArray(int size) {
			return new LoginResponse[size];
		}
	};

}
