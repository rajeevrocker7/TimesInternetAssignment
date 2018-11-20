
package com.rajeev.timesinternetassignment.beans;

import java.io.Serializable;
import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class Meta implements Serializable, Parcelable
{

    @SerializedName("limit")
    private long limit;
    @SerializedName("next")
    private String next;
    @SerializedName("offset")
    private long offset;
    @SerializedName("previous")
    private java.lang.Object previous;
    @SerializedName("total_count")
    private long totalCount;

    public Meta() {
    }

    private Meta(Parcel in) {
        limit = in.readLong();
        next = in.readString();
        offset = in.readLong();
        totalCount = in.readLong();
    }

    public static final Creator<Meta> CREATOR = new Creator<Meta>() {
        @Override
        public Meta createFromParcel(Parcel in) {
            return new Meta(in);
        }

        @Override
        public Meta[] newArray(int size) {
            return new Meta[size];
        }
    };

    public long getLimit() {
        return limit;
    }

    public void setLimit(long limit) {
        this.limit = limit;
    }

    public String getNext() {
        return next;
    }

    public void setNext(String next) {
        this.next = next;
    }

    public long getOffset() {
        return offset;
    }

    public void setOffset(long offset) {
        this.offset = offset;
    }

    public Object getPrevious() {
        return previous;
    }

    public void setPrevious(Object previous) {
        this.previous = previous;
    }

    public long getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(long totalCount) {
        this.totalCount = totalCount;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(limit);
        dest.writeString(next);
        dest.writeLong(offset);
        dest.writeLong(totalCount);
    }
}
