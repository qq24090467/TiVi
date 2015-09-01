package com.android.taxivaxi.operator.app.model;

import android.os.Parcel;
import android.os.Parcelable;

public class Operator implements Parcelable {

	String id;
	String address;
	String contact_no;
	String name;
	String modified;
	String created;

	String website;
	String email;
	String privacy_policy_url;
	String terms_url;

	public Operator() {
	}

	public Operator(String id, String address, String contact_no, String name, String modified, String created,
			String website, String email, String privacy_policy_url, String terms_url) {
		super();
		this.id = id;
		this.address = address;
		this.contact_no = contact_no;
		this.name = name;
		this.modified = modified;
		this.created = created;
		this.website = website;
		this.email = email;
		this.privacy_policy_url = privacy_policy_url;
		this.terms_url = terms_url;
	}

	public Operator(Parcel in) {
		id = in.readString();
		address = in.readString();
		contact_no = in.readString();
		name = in.readString();
		modified = in.readString();
		created = in.readString();

		website = in.readString();
		email = in.readString();
		privacy_policy_url = in.readString();
		terms_url = in.readString();

	}

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {

		dest.writeString(id);
		dest.writeString(address);
		dest.writeString(contact_no);
		dest.writeString(name);

		dest.writeString(modified);
		dest.writeString(created);

		dest.writeString(website);
		dest.writeString(email);
		dest.writeString(privacy_policy_url);
		dest.writeString(terms_url);

	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getContact_no() {
		return contact_no;
	}

	public void setContact_no(String contact_no) {
		this.contact_no = contact_no;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getModified() {
		return modified;
	}

	public void setModified(String modified) {
		this.modified = modified;
	}

	public String getCreated() {
		return created;
	}

	public void setCreated(String created) {
		this.created = created;
	}

	public String getWebsite() {
		return website;
	}

	public void setWebsite(String website) {
		this.website = website;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPrivacy_policy_url() {
		return privacy_policy_url;
	}

	public void setPrivacy_policy_url(String privacy_policy_url) {
		this.privacy_policy_url = privacy_policy_url;
	}

	public String getTerms_url() {
		return terms_url;
	}

	public void setTerms_url(String terms_url) {
		this.terms_url = terms_url;
	}

	@Override
	public String toString() {
		return "Operator [id=" + id + ", address=" + address + ", contact_no=" + contact_no + ", name=" + name
				+ ", modified=" + modified + ", created=" + created + ", website=" + website + ", email=" + email
				+ ", privacy_policy_url=" + privacy_policy_url + ", terms_url=" + terms_url + "]";
	}

	public static final Parcelable.Creator<Operator> CREATOR = new Parcelable.Creator<Operator>() {
		public Operator createFromParcel(Parcel in) {
			return new Operator(in);
		}

		public Operator[] newArray(int size) {
			return new Operator[size];
		}
	};

}
