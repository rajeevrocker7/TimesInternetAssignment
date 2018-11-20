
package com.rajeev.timesinternetassignment.beans;

import java.io.Serializable;
import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Cat_ implements Serializable, Parcelable
{

    @SerializedName("id")
    @Expose
    private long id;
    @SerializedName("n")
    @Expose
    private String n;

    public Cat_() {
    }

    private Cat_(Parcel in) {
        id = in.readLong();
        n = in.readString();
    }

    public static final Creator<Cat_> CREATOR = new Creator<Cat_>() {
        @Override
        public Cat_ createFromParcel(Parcel in) {
            return new Cat_(in);
        }

        @Override
        public Cat_[] newArray(int size) {
            return new Cat_[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(id);
        dest.writeString(n);
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getN() {
        return n;
    }

    public void setN(String n) {
        this.n = n;
    }
}
