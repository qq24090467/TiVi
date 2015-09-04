package com.android.taxivaxi.operator.app.activities;

import android.app.Activity;
import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.taxivaxi.operator.app.R;
import com.android.taxivaxi.operator.app.common.CustomFontsLoader;
import com.android.taxivaxi.operator.app.common.DrawerItem;

import java.util.List;
import java.util.Locale;

/**
 * Created by MB on 9/1/2015.
 */
public class DrawerListAdapter extends ArrayAdapter<DrawerItem> {

    Context context;

    public DrawerListAdapter(Context context, int resourceId,
                                 List<DrawerItem> items) {
        super(context, resourceId, items);
        this.context = context;
    }

    /*private view holder class*/
    private class ViewHolder {
        ImageView imageView;
        TextView txtTitle;
        TextView txtDesc;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        DrawerItem drawerItem = getItem(position);

        LayoutInflater mInflater = (LayoutInflater) context
                .getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.drawer_list, null);
            holder = new ViewHolder();
            holder.txtDesc = (TextView) convertView.findViewById(R.id.item_new);
            holder.txtTitle = (TextView) convertView.findViewById(R.id.item_text);
            holder.imageView = (ImageView) convertView.findViewById(R.id.item_icon);
            convertView.setTag(holder);
        } else
            holder = (ViewHolder) convertView.getTag();

        //holder.txtDesc.setText(drawerItem.getDesc());
        holder.txtTitle.setText(drawerItem.getTitle());
        Object localObject1 = new Locale("US");

        //localObject1 = CustomFontsLoader.getTypeface(this, 1);
        holder.txtTitle.setTypeface( CustomFontsLoader.getTypeface(getContext(), 1));
        holder.imageView.setImageResource(drawerItem.getImageId());

        return convertView;
    }
}