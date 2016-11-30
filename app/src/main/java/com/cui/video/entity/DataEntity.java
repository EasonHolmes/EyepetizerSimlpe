package com.cui.video.entity;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by zsj on 2016/10/11.
 */

public class DataEntity implements Parcelable {

    public String dataType;
    public int id;
    public String title;
    public String text;
    public String description;
    public String image;
    public String actionUrl;
    public Object adTrack;
    public boolean shade;
    public CoverEntity cover;
    public String playUrl;
    public String category;
    public int duration;


    protected DataEntity(Parcel in) {
        dataType = in.readString();
        id = in.readInt();
        title = in.readString();
        text = in.readString();
        description = in.readString();
        image = in.readString();
        actionUrl = in.readString();
        shade = in.readByte() != 0;
        cover = in.readParcelable(CoverEntity.class.getClassLoader());
        playUrl = in.readString();
        category = in.readString();
        duration = in.readInt();
    }

    public static final Creator<DataEntity> CREATOR = new Creator<DataEntity>() {
        @Override
        public DataEntity createFromParcel(Parcel in) {
            return new DataEntity(in);
        }

        @Override
        public DataEntity[] newArray(int size) {
            return new DataEntity[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(dataType);
        dest.writeInt(id);
        dest.writeString(title);
        dest.writeString(text);
        dest.writeString(description);
        dest.writeString(image);
        dest.writeString(actionUrl);
        dest.writeByte((byte) (shade ? 1 : 0));
        dest.writeParcelable(cover, flags);
        dest.writeString(playUrl);
        dest.writeString(category);
        dest.writeInt(duration);
    }
}