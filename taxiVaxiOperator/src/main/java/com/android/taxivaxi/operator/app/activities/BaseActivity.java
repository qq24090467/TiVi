package com.android.taxivaxi.operator.app.activities;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
//import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.app.ActionBarDrawerToggle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.android.taxivaxi.operator.app.R;
import com.android.taxivaxi.operator.app.common.DrawerItem;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * Created by AV6 on 8/28/2015.
 */
public class BaseActivity extends Activity {
    private String[] mTitleList;
    public  Integer[] images;
    protected DrawerLayout mDrawerLayout;
    private ListView mDrawerList;
    List<DrawerItem> drawerItems;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base_activity);
        mTitleList = new String[]{"Home","My Bookings"};
        images = new Integer[]{R.drawable.taxi_vaxi_nav_home_icon,
                R.drawable.taxi_vaxi_nav_my_booking_icon };
        Object localObject1 = new Locale("US");
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerList = (ListView) findViewById(R.id.left_drawer);
        drawerItems = new ArrayList<DrawerItem>();
        for (int i = 0; i < mTitleList.length; i++) {
            DrawerItem item = new DrawerItem(images[i], mTitleList[i]);
            drawerItems.add(item);
        }

        /*LayoutInflater inflater = getLayoutInflater();
        ViewGroup header = (ViewGroup)inflater.inflate(R.layout.header, mDrawerList, false);
        mDrawerList.addHeaderView(header, null, false);*/

        // Set the adapter for the list view
        /*mDrawerList.setAdapter(new ArrayAdapter<CharSequence>(this,
                android.R.layout.simple_spinner_dropdown_item, mTitleList));*/
        DrawerListAdapter adapter = new DrawerListAdapter(this,
                R.layout.drawer_list, drawerItems);
        mDrawerList.setAdapter(adapter);
        // Set the list's click listener
        mDrawerList.setOnItemClickListener(new DrawerItemClickListener());

    }

    private class DrawerItemClickListener implements ListView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            selectItem(position);
        }
    }
    /** Swaps fragments in the main content view */
    private void selectItem(int position) {
        mDrawerList.setItemChecked(position, true);
        mDrawerLayout.closeDrawer(mDrawerList);
        switch(position){
            case 0:
                Intent home = new Intent(getApplicationContext(), HomeActivity.class);
                startActivity(home);
                overridePendingTransition(R.anim.trans_left_in, R.anim.trans_left_out);
                break;
            case 1:
                Intent test = new Intent(getApplicationContext(), BookingHistoryActivity.class);
                startActivity(test);
                overridePendingTransition(R.anim.trans_left_in, R.anim.trans_left_out);
                break;
            case 2:
                Intent profile_intent = new Intent(getApplicationContext(), ProfileScreenActivity.class);
                startActivity(profile_intent);
                overridePendingTransition(R.anim.trans_left_in, R.anim.trans_left_out);
                break;
        }
    }


}
