package com.cui.video.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.cui.video.R;
import com.cui.video.entity.ShareSimpleListItem;


/**
 * Created by lgp on 2015/6/10.
 */
public class ShareSimpleListAdapter extends ArrayAdapter<ShareSimpleListItem> {

    private final int duration = 300;
    private AccelerateInterpolator accelerateInterpolator = new AccelerateInterpolator();

    public ShareSimpleListAdapter(Context context) {
        super(context, R.layout.md_simplelist_item, android.R.id.title);
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final View view = super.getView(position, convertView, parent);
        final ShareSimpleListItem item = getItem(position);
        ImageView ic = (ImageView) view.findViewById(android.R.id.icon);
        if (item.getIcon() != null)
            ic.setImageDrawable(item.getIcon());
        else
            ic.setVisibility(View.GONE);
        TextView tv = (TextView) view.findViewById(android.R.id.title);
        tv.setText(item.getContent());
        view.animate().translationX(1).setDuration(duration).setStartDelay(position*50)
        .setInterpolator(accelerateInterpolator).start();
        return view;
    }
}
