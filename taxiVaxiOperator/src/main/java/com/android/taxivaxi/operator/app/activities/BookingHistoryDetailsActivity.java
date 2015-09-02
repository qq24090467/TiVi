package com.android.taxivaxi.operator.app.activities;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.taxivaxi.operator.app.R;
import com.android.taxivaxi.operator.app.common.ApplicationConstant;
import com.android.taxivaxi.operator.app.common.DebugLog;
import com.android.taxivaxi.operator.app.common.NetworkUtilities;
import com.android.taxivaxi.operator.app.controller.RejectRequestAsync;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by MB on 9/1/2015.
 */
public class BookingHistoryDetailsActivity extends BaseActivity implements View.OnClickListener {
    private String id = "";
    private String pickup_location="";
    private String pickup_datetime="";
    Button button_accept,button_reject,button_assign_driver,button_set_driver,button_cancel_set;
    private String status="";
    private String status_color="";
    private SharedPreferences UserInfo;
    TextView Id,Status,Pickup_Location,Pickup_DateTime;
    ListView driver_list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//		setContentView(R.layout.activity_home_screen);
        LayoutInflater inflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        //inflate your activity layout here!
        View contentView = inflater.inflate(R.layout.activity_booking_history_details, null, false);
        mDrawerLayout.addView(contentView, 0);

        Id= (TextView) findViewById(R.id.booking_ref_number);
        Status= (TextView) findViewById(R.id.booking_status);
        Pickup_Location= (TextView) findViewById(R.id.value_pickup_from);
        Pickup_DateTime= (TextView) findViewById(R.id.value_pickup_time);

        button_accept = (Button) findViewById(R.id.button_accept);
        button_reject = (Button) findViewById(R.id.button_reject);
        button_assign_driver= (Button) findViewById(R.id.assign_driver);

        button_reject.setOnClickListener(this);
        button_accept.setOnClickListener(this);
        button_assign_driver.setOnClickListener(this);

        UserInfo = this.getSharedPreferences("UserInfo", Context.MODE_PRIVATE);

        id=getIntent().getExtras().getString("id");
        pickup_datetime=getIntent().getExtras().getString("pickup_datetime");
        pickup_location=getIntent().getExtras().getString("pickup_location");
        status=getIntent().getExtras().getString("status");
        status_color=getIntent().getExtras().getString("status_color");

        Id.setText("Booking ID: "+id);
        Status.setTextColor(Color.parseColor(status_color));
        Status.setText(status);
        Pickup_DateTime.setText(pickup_datetime);
        Pickup_Location.setText(pickup_location);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.button_reject:
                DebugLog.d("booking_id : " + id);

                if (NetworkUtilities.isInternet(getApplicationContext())) {

                    RejectRequestAsync async = new RejectRequestAsync(this, HandlerRejectRequest,
                            ApplicationConstant.API_URL + ApplicationConstant.METHOD_REJECT_BOOKING);
                    async.execute(UserInfo.getString("access_token", ""), id);
                }
                break;
            case R.id.button_accept:
                if (NetworkUtilities.isInternet(getApplicationContext())) {

                    RejectRequestAsync async = new RejectRequestAsync(this, HandlerAcceptRequest,
                            ApplicationConstant.API_URL + ApplicationConstant.METHOD_ACCEPT_BOOKING);
                    async.execute(UserInfo.getString("access_token", ""), id);
                }
                break;
            case R.id.assign_driver:
                final Dialog d2 = new Dialog(BookingHistoryDetailsActivity.this);
                d2.setTitle("Assign Driver");
                d2.setContentView(R.layout.dialoge_assign_driver);
                driver_list= (ListView) d2.findViewById(R.id.listView_assign_driver);
                button_set_driver= (Button) d2.findViewById(R.id.button_set_driver);
                button_cancel_set= (Button) d2.findViewById(R.id.button_cancel_set);

                String drivernames[]={"Rocco","Dominic",};
                ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, drivernames);
                driver_list.setAdapter(adapter);
                d2.show();

                break;
        }
    }

    @SuppressLint("HandlerLeak")
    private Handler HandlerAcceptRequest = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

            if (msg.obj != null) {
                try {
                    if (((JSONObject) msg.obj).getString("success").toString().equalsIgnoreCase("1")) {

                        Toast.makeText(getApplicationContext(), "Booking Accepted",
                                Toast.LENGTH_LONG).show();
                        Intent i1 = new Intent(BookingHistoryDetailsActivity.this, BookingHistoryActivity.class);
                        i1.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(i1);
                    } else {
                        Toast.makeText(getApplicationContext(), ((JSONObject) msg.obj).getString("error").toString(),
                                Toast.LENGTH_LONG).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }
    };

    @SuppressLint("HandlerLeak")
    private Handler HandlerRejectRequest = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

            if (msg.obj != null) {
                try {
                    if (((JSONObject) msg.obj).getString("success").toString().equalsIgnoreCase("1")) {

                        Toast.makeText(getApplicationContext(), "Booking Rejected",
                                Toast.LENGTH_LONG).show();
                        Intent i1 = new Intent(BookingHistoryDetailsActivity.this, BookingHistoryActivity.class);
                        i1.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(i1);
                    } else {
                        Toast.makeText(getApplicationContext(), ((JSONObject) msg.obj).getString("error").toString(),
                                Toast.LENGTH_LONG).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }
    };
}
