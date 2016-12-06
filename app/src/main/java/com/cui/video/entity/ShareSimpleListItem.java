package com.cui.video.entity;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.annotation.DrawableRes;
import android.support.annotation.StringRes;
import android.support.v4.content.ContextCompat;

/**
 * Created by lgp on 2015/6/10.
 */
public class ShareSimpleListItem {

    private Builder mBuilder;

    private ShareSimpleListItem(Builder builder) {
        mBuilder = builder;
    }

    public Drawable getIcon() {
        return mBuilder.mIcon;
    }

    public CharSequence getContent() {
        return mBuilder.mContent;
    }

    public static class Builder {

        private Context mContext;
        protected Drawable mIcon;
        protected CharSequence mContent;

        public Builder(Context context) {
            mContext = context;
        }

        public Builder icon(Drawable icon) {
            this.mIcon = icon;
            return this;
        }

        public Builder icon(@DrawableRes int iconRes) {
            if (iconRes == 0)
                return this;
            return icon(ContextCompat.getDrawable(mContext, iconRes));
        }

        public Builder content(CharSequence content) {
            this.mContent = content;
            return this;
        }

        public Builder content(@StringRes int contentRes) {
            return content(mContext.getString(contentRes));
        }

        public ShareSimpleListItem build() {
            return new ShareSimpleListItem(this);
        }
    }

}
