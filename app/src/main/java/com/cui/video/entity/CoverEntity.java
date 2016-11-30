package com.cui.video.entity;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by zsj on 2016/10/11.
 */

public class CoverEntity implements Parcelable {

    public String feed;
    public String detail;
    public String blurred;

    protected CoverEntity(Parcel in) {
        feed = in.readString();
        detail = in.readString();
        blurred = in.readString();
    }

    public static final Creator<CoverEntity> CREATOR = new Creator<CoverEntity>() {
        @Override
        public CoverEntity createFromParcel(Parcel in) {
            return new CoverEntity(in);
        }

        @Override
        public CoverEntity[] newArray(int size) {
            return new CoverEntity[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(feed);
        dest.writeString(detail);
        dest.writeString(blurred);
    }
}