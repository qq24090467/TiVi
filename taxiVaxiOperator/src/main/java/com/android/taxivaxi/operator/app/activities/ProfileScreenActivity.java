package com.android.taxivaxi.operator.app.activities;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.taxivaxi.operator.app.R;
import com.android.taxivaxi.operator.app.common.CustomFontsLoader;
import com.android.taxivaxi.operator.app.custom.AlertDailogLogout;

public class ProfileScreenActivity extends BaseActivity implements OnClickListener {

	private ImageView profile_back,drawer_toggle, logout_button;
	private TextView profile_user_name, profile_user_contact, profile_user_email;
	private RelativeLayout change_passowrd;
	private SharedPreferences UserInfo;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
//		setContentView(R.layout.activity_profile_screen);
        LayoutInflater inflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        //inflate your activity layout here!
        View contentView = inflater.inflate(R.layout.activity_profile_screen, null, false);
        mDrawerLayout.addView(contentView, 0);

		UserInfo = getSharedPreferences("UserInfo", Context.MODE_PRIVATE);

//		profile_back = (ImageView) findViewById(R.id.profile_back);

		logout_button = (ImageView) findViewById(R.id.logout_button);
        drawer_toggle=(ImageView) findViewById(R.id.Drawer_toggle);
		profile_user_name = (TextView) findViewById(R.id.profile_user_name);
		profile_user_contact = (TextView) findViewById(R.id.profile_user_contact);
		profile_user_email = (TextView) findViewById(R.id.profile_user_email);
		change_passowrd = (RelativeLayout) findViewById(R.id.change_passowrd);

//		profile_back.setOnClickListener(this);
        drawer_toggle.setOnClickListener(this);
		logout_button.setOnClickListener(this);
		change_passowrd.setOnClickListener(this);

		profile_user_name.setText((UserInfo.getString("name", "")).toString());
		profile_user_contact.setText((UserInfo.getString("contact_no", "")).toString());
		profile_user_email.setText((UserInfo.getString("email", "")).toString());

		// set font to layout widgets
		Typeface typeFace = CustomFontsLoader.getTypeface(ProfileScreenActivity.this, 1);

		if (typeFace != null) {
			((TextView) findViewById(R.id.login_text)).setTypeface(typeFace);
			profile_user_name.setTypeface(typeFace);
			profile_user_contact.setTypeface(typeFace);
			profile_user_email.setTypeface(typeFace);
		}

	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {

		/*case R.id.profile_back:
			Intent i = new Intent(ProfileScreenActivity.this, HomeActivity.class);
			i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
			startActivity(i);
			finish();
			overridePendingTransition(R.anim.trans_right_in, R.anim.trans_right_out);
			break;*/
            case R.id.Drawer_toggle:
                mDrawerLayout.openDrawer(Gravity.LEFT);
                break;
		case R.id.logout_button:
			AlertDailogLogout.newInstance(getResources().getString(R.string.app_name),
					"Do you really want to sign out ? ", "CANCEL", "YES").show(getFragmentManager(), "");
			break;

		default:
			break;
		}

	}

	@Override
	public void onBackPressed() {
		super.onBackPressed();
		Intent i = new Intent(ProfileScreenActivity.this, HomeActivity.class);
		i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
		startActivity(i);
		overridePendingTransition(R.anim.trans_right_in, R.anim.trans_right_out);
	}

}
