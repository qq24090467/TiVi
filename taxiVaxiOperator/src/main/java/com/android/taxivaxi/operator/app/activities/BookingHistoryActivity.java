package com.android.taxivaxi.operator.app.activities;

import it.neokree.materialtabs.MaterialTab;
import it.neokree.materialtabs.MaterialTabHost;
import it.neokree.materialtabs.MaterialTabListener;

import java.util.ArrayList;

import org.json.JSONException;
import org.json.JSONObject;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.Parcelable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.taxivaxi.operator.app.R;
import com.android.taxivaxi.operator.app.common.ApplicationConstant;
import com.android.taxivaxi.operator.app.common.CustomFontsLoader;
import com.android.taxivaxi.operator.app.common.DebugLog;
import com.android.taxivaxi.operator.app.common.NetworkUtilities;
import com.android.taxivaxi.operator.app.controller.GetBookingHistoryAsync;
import com.android.taxivaxi.operator.app.model.MyBookingHistoryData;
import com.android.taxivaxi.operator.app.model.TourBooking;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

public class BookingHistoryActivity extends BaseActivity implements MaterialTabListener, OnClickListener {

	private MaterialTabHost tabHost;
	private ViewPager mPager;
	private ImagePagerAdapter pagerAdapter;
	private ImageView drawer_toggle;
	private TextView title_text;
	private static Context mContext ;
	private static MyBookingHistoryData mData = null;

	private static final String[] CONTENT = new String[] { "ALL BOOKINGS","ASSIGNED","UNASSIGNED", "COMPLETED" };

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
//		setContentView(R.layout.activity_booking_history);
        LayoutInflater inflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        //inflate your activity layout here!
        View contentView = inflater.inflate(R.layout.activity_booking_history, null, false);
        mDrawerLayout.addView(contentView, 0);
        mContext = getApplicationContext();
		tabHost = (MaterialTabHost) findViewById(R.id.tabHost);
		mPager = (ViewPager) findViewById(R.id.pager);
		title_text = (TextView) findViewById(R.id.title_text);
		drawer_toggle = (ImageView) findViewById(R.id.Drawer_toggle);
//        drawer_toggle = (ImageView) findViewById(R.id.Drawer_toggle);

		drawer_toggle.setOnClickListener(this);
//        drawer_toggle.setOnClickListener(this);

		pagerAdapter = new ImagePagerAdapter();
		mPager.setAdapter(pagerAdapter);

		tabHost.setSelectedNavigationItem(0);

		// insert all tabs from pagerAdapter data
		for (int i = 0; i < pagerAdapter.getCount(); i++) {
			tabHost.addTab(tabHost.newTab().setText(pagerAdapter.getPageTitle(i)).setTabListener(this));
		}

		mPager.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
			@Override
			public void onPageSelected(int position) {
				// when user do a swipe the selected tab change
				tabHost.setSelectedNavigationItem(position);

			}
		});

		if (NetworkUtilities.isInternet(BookingHistoryActivity.this)) {

			SharedPreferences UserInfo = getSharedPreferences("UserInfo", Context.MODE_PRIVATE);

			GetBookingHistoryAsync mAsync = new GetBookingHistoryAsync(BookingHistoryActivity.this,
					getMyBookingHandler,ApplicationConstant.METHOD_BOOKING_HISTORY);
			/*Toast.makeText(BookingHistoryActivity.this, "Saved access_token  " + (UserInfo.getString("access_token", "")).toString(), Toast.LENGTH_LONG)
					.show();*/
			DebugLog.d("Test Get All Async  " + (UserInfo.getString("access_token", "")).toString());
			mAsync.execute(UserInfo.getString("access_token", ""));

		} else {
			Toast.makeText(BookingHistoryActivity.this, "Please check your internet connection", Toast.LENGTH_SHORT)
					.show();
		}

		// set font to layout widgets
		Typeface typeFace = CustomFontsLoader.getTypeface(BookingHistoryActivity.this, 1);
		if (typeFace != null) {
			title_text.setTypeface(typeFace);
		}

	}

	@Override
	public void onClick(View v) {

		switch (v.getId()) {
		case R.id.Drawer_toggle:

            mDrawerLayout.openDrawer(Gravity.LEFT);

			break;

		default:
			break;
		}

	}

	@SuppressLint("HandlerLeak")
	Handler getMyBookingHandler = new Handler() {

		@Override
		public void handleMessage(Message msg) {

			mData = new MyBookingHistoryData();
			Gson gson = new Gson();

			if (msg.obj != null) {
				try {
					if (((JSONObject) msg.obj).getString("success").toString().equalsIgnoreCase("1")) {

						try {

//							System.out.println("response my booking list = " + ((JSONObject) msg.obj).toString());

							mData = gson.fromJson( (msg.obj).toString(), mData.getClass());
//                            System.out.println("b size = " + mData.getResponse().getBookings().size());
							/*Toast.makeText(BookingHistoryActivity.this, "mData okay"+mData.getResponse().getBookings().size(), Toast.LENGTH_SHORT)
									.show();*/
							try {

								setTabHostView();

							} catch (Exception e) {

								e.printStackTrace();
							}
						} catch (JsonSyntaxException e) {

							e.printStackTrace();
						}
					} else {

						Toast.makeText(BookingHistoryActivity.this, "No Success", Toast.LENGTH_SHORT)
								.show();
					}
				} catch (JSONException e) {

					e.printStackTrace();
				}
			}
		};
	};

	private void setTabHostView() {

		pagerAdapter = new ImagePagerAdapter();
		mPager.setAdapter(pagerAdapter);

		tabHost.setSelectedNavigationItem(0);

		for (int i = 0; i < pagerAdapter.getCount(); i++) {
			tabHost.addTab(tabHost.newTab().setText(pagerAdapter.getPageTitle(i)).setTabListener(this));
		}

	}

	private class ImagePagerAdapter extends PagerAdapter {

		@SuppressWarnings("unused")
		private LayoutInflater inflater;

		ImagePagerAdapter() {
			try {
				inflater = getLayoutInflater();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		@Override
		public void destroyItem(ViewGroup container, int position, Object object) {
			// container.removeView((View) object);
		}

		@Override
		public Object instantiateItem(ViewGroup view, int position) {

			CustomViewGroup customViewGroup = null;
			if (position < 3) {
				customViewGroup = new CustomViewGroup(BookingHistoryActivity.this, view, position);
				view.addView(customViewGroup, 0);
			}
			return customViewGroup;
		}

		@Override
		public boolean isViewFromObject(View view, Object object) {
			return view.equals(object);
		}

		@Override
		public void restoreState(Parcelable state, ClassLoader loader) {
		}

		@Override
		public Parcelable saveState() {
			return null;
		}

		@Override
		public int getCount() {
			return CONTENT.length;
		}

		@SuppressLint("DefaultLocale")
		@Override
		public CharSequence getPageTitle(int position) {
			return CONTENT[position % CONTENT.length].toUpperCase();
		}
	}

	private class CustomViewGroup extends LinearLayout {

		private LayoutInflater inflater;
		private View layout;
		private LinearLayout no_record_found_ll;
		@SuppressWarnings("unused")
		private TextView no_data_found;

		private CustomViewGroup(Context context, ViewGroup view, final int position) {
			super(context);

			if (position == 0) {

				inflater = ((Activity) context).getLayoutInflater();
				layout = inflater.inflate(R.layout.fragment_my_booking_history_all, view, false);

				ListView result_list_view = (ListView) layout.findViewById(R.id.result_list_view);

				no_record_found_ll = (LinearLayout) layout.findViewById(R.id.no_record_found_ll);
				no_data_found = (TextView) layout.findViewById(R.id.no_data_found);
				try {

//					if (mData != null && mData.getResponse().getBookings().size() > 0) {
					if ((mData != null) && (mData.getResponse().getBookings().size()> 0)) {
						ArrayList<TourBooking> bookings = new ArrayList<TourBooking>();

						for (int i = 0; i < mData.getResponse().getBookings().size(); i++) {
							bookings.add(mData.getResponse().getBookings().get(i));
						}
						no_record_found_ll.setVisibility(View.GONE);

						BookingHistoryListAdapter mAdapter = new BookingHistoryListAdapter(mContext, bookings);
						result_list_view.setAdapter(mAdapter);
					} else {
						no_record_found_ll.setVisibility(View.VISIBLE);
					}

				} catch (Exception e) {
					e.printStackTrace();
					no_record_found_ll.setVisibility(View.VISIBLE);
				}
			} else if (position == 1) {

				inflater = ((Activity) context).getLayoutInflater();
				layout = inflater.inflate(R.layout.fragment_my_booking_history_all, view, false);

				ListView result_list_view = (ListView) layout.findViewById(R.id.result_list_view);

				no_record_found_ll = (LinearLayout) layout.findViewById(R.id.no_record_found_ll);
				no_data_found = (TextView) layout.findViewById(R.id.no_data_found);

				try {
					if (mData != null && mData.getResponse().getBookings().size() > 0) {

						ArrayList<TourBooking> bookings = new ArrayList<TourBooking>();

						for (int i = 0; i < mData.getResponse().getBookings().size(); i++) {
							if (mData.getResponse().getBookings().get(i).getIs_assigned().equalsIgnoreCase("1")) {
								bookings.add(mData.getResponse().getBookings().get(i));
							}
						}
						no_record_found_ll.setVisibility(View.GONE);
						BookingHistoryListAdapter mAdapter = new BookingHistoryListAdapter(mContext, bookings);
						result_list_view.setAdapter(mAdapter);
					} else {
						no_record_found_ll.setVisibility(View.VISIBLE);
					}
				} catch (Exception e) {
					e.printStackTrace();
					no_record_found_ll.setVisibility(View.VISIBLE);
				}

			} else if(position==2){
				inflater = ((Activity) context).getLayoutInflater();
				layout = inflater.inflate(R.layout.fragment_my_booking_history_all, view, false);

				ListView result_list_view = (ListView) layout.findViewById(R.id.result_list_view);

				no_record_found_ll = (LinearLayout) layout.findViewById(R.id.no_record_found_ll);
				no_data_found = (TextView) layout.findViewById(R.id.no_data_found);

				try {
					if (mData != null && mData.getResponse().getBookings().size() > 0) {

						ArrayList<TourBooking> bookings = new ArrayList<TourBooking>();

						for (int i = 0; i < mData.getResponse().getBookings().size(); i++) {
							if (mData.getResponse().getBookings().get(i).getIs_assigned().equalsIgnoreCase("0")) {
								bookings.add(mData.getResponse().getBookings().get(i));
							}
						}
						no_record_found_ll.setVisibility(View.GONE);
						BookingHistoryListAdapter mAdapter = new BookingHistoryListAdapter(mContext, bookings);
						result_list_view.setAdapter(mAdapter);
					} else {
						no_record_found_ll.setVisibility(View.VISIBLE);
					}
				} catch (Exception e) {
					e.printStackTrace();
					no_record_found_ll.setVisibility(View.VISIBLE);
				}

			}
			else
			{
				inflater = ((Activity) context).getLayoutInflater();
				layout = inflater.inflate(R.layout.fragment_my_booking_history_all, view, false);

				ListView result_list_view = (ListView) layout.findViewById(R.id.result_list_view);

				no_record_found_ll = (LinearLayout) layout.findViewById(R.id.no_record_found_ll);
				no_data_found = (TextView) layout.findViewById(R.id.no_data_found);

				try {
					if (mData != null && mData.getResponse().getBookings().size() > 0) {

						ArrayList<TourBooking> bookings = new ArrayList<TourBooking>();

						for (int i = 0; i < mData.getResponse().getBookings().size(); i++) {
							if (mData.getResponse().getBookings().get(i).getStatus().equalsIgnoreCase("completed")) {
								bookings.add(mData.getResponse().getBookings().get(i));
							}
						}
						no_record_found_ll.setVisibility(View.GONE);
						BookingHistoryListAdapter mAdapter = new BookingHistoryListAdapter(mContext, bookings);
						result_list_view.setAdapter(mAdapter);
					} else {
						no_record_found_ll.setVisibility(View.VISIBLE);
					}
				} catch (Exception e) {
					e.printStackTrace();
					no_record_found_ll.setVisibility(View.VISIBLE);
				}

			}

			this.setOrientation(VERTICAL);
			this.addView(layout);
		}
	}

	@Override
	public void onTabSelected(MaterialTab tab) {
		mPager.setCurrentItem(tab.getPosition());
	}

	@Override
	public void onTabReselected(MaterialTab tab) {

	}

	@Override
	public void onTabUnselected(MaterialTab tab) {

	}

	@Override
	public void onBackPressed() {
		super.onBackPressed();
		overridePendingTransition(R.anim.trans_right_in, R.anim.trans_right_out);
	}

	// public void showMyDialog(String title, String message, String
	// negativeButton, String positiveButton) {
	// AlertDialogStandard newDialog = AlertDialogStandard.newInstance(title,
	// message, negativeButton, positiveButton);
	// newDialog.show(getFragmentManager(), "dialog");
	// }

}
