package com.cui.video.entity.fetured;

import android.os.Parcel;
import android.os.Parcelable;


/**
 * Created by zsj on 2016/10/11.
 */

public class ItemList implements  Parcelable {

    public String type;
    public DataEntity data;

    protected ItemList(Parcel in) {
        type = in.readString();
        data = in.readParcelable(DataEntity.class.getClassLoader());
    }

    public static final Creator<ItemList> CREATOR = new Creator<ItemList>() {
        @Override
        public ItemList createFromParcel(Parcel in) {
            return new ItemList(in);
        }

        @Override
        public ItemList[] newArray(int size) {
            return new ItemList[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(type);
        dest.writeParcelable(data, flags);
    }

}