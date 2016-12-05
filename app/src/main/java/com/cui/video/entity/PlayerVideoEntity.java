package com.cui.video.entity;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import static android.R.attr.category;
import static android.R.attr.description;
import static android.R.attr.duration;
import static android.R.attr.id;
import static android.R.attr.name;

/**
 * Created by cuiyang on 2016/11/20.
 */

public class PlayerVideoEntity implements Parcelable {
    public List<ItemList> list;



    public PlayerVideoEntity(Parcel in) {
        list = in.readArrayList(ItemList.class.getClassLoader());
    }
    public PlayerVideoEntity(){}

    public List<ItemList> getList() {
        return list;
    }

    public void setList(List<ItemList> list) {
        this.list = list;
    }

    public static final Creator<PlayerVideoEntity> CREATOR = new Creator<PlayerVideoEntity>() {
        @Override
        public PlayerVideoEntity createFromParcel(Parcel in) {
            return new PlayerVideoEntity(in);
        }

        @Override
        public PlayerVideoEntity[] newArray(int size) {
            return new PlayerVideoEntity[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeList(list);
    }
}
