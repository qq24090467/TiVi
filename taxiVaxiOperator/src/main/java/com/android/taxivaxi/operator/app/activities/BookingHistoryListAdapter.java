package com.android.taxivaxi.operator.app.activities;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.android.taxivaxi.operator.app.R;
import com.android.taxivaxi.operator.app.common.CommonMethod;
import com.android.taxivaxi.operator.app.common.CustomFontsLoader;
import com.android.taxivaxi.operator.app.model.TourBooking;

public class BookingHistoryListAdapter extends BaseAdapter {

	private Context mContext;
	private ArrayList<TourBooking> bookings;

	public BookingHistoryListAdapter(Context mContext, ArrayList<TourBooking> mData) {
		this.mContext = mContext;
		this.bookings = mData;
	}

	@Override
	public int getCount() {
		return bookings.size();
	}

	@Override
	public Object getItem(int arg0) {
		return arg0;
	}

	@Override
	public long getItemId(int arg0) {
		return arg0;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {

		ViewHolder viewHolder = null;

		if (convertView == null) {

			LayoutInflater inflater = LayoutInflater.from(mContext);
			convertView = inflater.inflate(R.layout.custom_list_row_history, parent, false);

			viewHolder = new ViewHolder();

			viewHolder.status_text = (TextView) convertView.findViewById(R.id.status_text);
			viewHolder.date_text = (TextView) convertView.findViewById(R.id.date_text);
			viewHolder.from_text = (TextView) convertView.findViewById(R.id.from_text);
			viewHolder.to_text = (TextView) convertView.findViewById(R.id.to_text);
			viewHolder.operator_name = (TextView) convertView.findViewById(R.id.operator_name);
			viewHolder.cab_name = (TextView) convertView.findViewById(R.id.cab_name);
			viewHolder.cab_type = (TextView) convertView.findViewById(R.id.cab_type);

			convertView.setTag(viewHolder);

		} else {
			viewHolder = (ViewHolder) convertView.getTag();
		}

		viewHolder.status_text.setText(bookings.get(position).getStatus());
        if(bookings.get(position).getStatus_color()!=null)
        viewHolder.status_text.setBackgroundColor(Color.parseColor(bookings.get(position).getStatus_color()));
		viewHolder.date_text.setText(CommonMethod.dateFormatChange(bookings.get(position).getPickup_datetime()));
		viewHolder.from_text.setText("Pickup from : " + bookings.get(position).getPickup_location());
        convertView.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
//                Toast.makeText(mContext,((TourBooking)BookingHistoryListAdapter.this.bookings.get(position)).getId(),Toast.LENGTH_SHORT).show();

                Intent mIntent = new Intent(BookingHistoryListAdapter.this.mContext,BookingHistoryDetailsActivity.class);
                Bundle mBundle = new Bundle();
                mBundle.putString("id", ((TourBooking)BookingHistoryListAdapter.this.bookings.get(position)).getId());
                mBundle.putString("pickup_location", ((TourBooking)BookingHistoryListAdapter.this.bookings.get(position)).getPickup_location());
                mBundle.putString("status", ((TourBooking)BookingHistoryListAdapter.this.bookings.get(position)).getStatus());
                mBundle.putString("status_color", ((TourBooking)BookingHistoryListAdapter.this.bookings.get(position)).getStatus_color());
                mBundle.putString("pickup_datetime", ((TourBooking)BookingHistoryListAdapter.this.bookings.get(position)).getPickup_datetime());
				mBundle.putString("city_id", ((TourBooking)BookingHistoryListAdapter.this.bookings.get(position)).getCity_id());

                mIntent.putExtras(mBundle);
                mIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                BookingHistoryListAdapter.this.mContext.startActivity(mIntent);
            }

        });


		//
		// viewHolder.operator_name.setText(bookings.get(position).getTaxi_details().getOperator_name()
		// + "  | ");
		// viewHolder.cab_name.setText(bookings.get(position).getTaxi_details().getTaxi_model()
		// + "  | ");
		// viewHolder.cab_type.setText(bookings.get(position).getTaxi_details().getTaxi_type());

		// set font to layout widgets
		Typeface typeFace = CustomFontsLoader.getTypeface(mContext, 1);
		if (typeFace != null) {
			viewHolder.status_text.setTypeface(typeFace);
			viewHolder.date_text.setTypeface(typeFace);
			viewHolder.from_text.setTypeface(typeFace);
			viewHolder.to_text.setTypeface(typeFace);
			viewHolder.operator_name.setTypeface(typeFace);
			viewHolder.cab_name.setTypeface(typeFace);
			viewHolder.cab_type.setTypeface(typeFace);
		}

		return convertView;
	}



    static class ViewHolder {

		TextView status_text;
		TextView date_text;
		TextView from_text;
		TextView to_text;
		TextView operator_name;
		TextView cab_name;
		TextView cab_type;;

	}

}