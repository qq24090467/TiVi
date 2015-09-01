package com.android.taxivaxi.operator.app.activities;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Toast;

import com.android.taxivaxi.operator.app.R;

/**
 * Created by MB on 9/1/2015.
 */
public class BookingHistoryDetailsActivity extends BaseActivity {
    private String id = "";
    private String pickup_location="";
    private String status="";
    private String status_color="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//		setContentView(R.layout.activity_home_screen);

        LayoutInflater inflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        //inflate your activity layout here!
        View contentView = inflater.inflate(R.layout.activity_booking_history_details, null, false);

        mDrawerLayout.addView(contentView, 0);
        id=getIntent().getExtras().getString("id");
        pickup_location=getIntent().getExtras().getString("pickup_location");
        status=getIntent().getExtras().getString("status");
        status_color=getIntent().getExtras().getString("status_color");
        Toast.makeText(this,id,Toast.LENGTH_SHORT).show();
    }
}
