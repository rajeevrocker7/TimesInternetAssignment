package com.rajeev.timesinternetassignment.beans;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;

public class ResponseBean implements Serializable, Parcelable {


    public static final Creator<ResponseBean> CREATOR = new Creator<ResponseBean>() {
        @Override
        public ResponseBean createFromParcel(Parcel in) {
            return new ResponseBean(in);
        }

        @Override
        public ResponseBean[] newArray(int size) {
            return new ResponseBean[size];
        }
    };
    @SerializedName("meta")
    private Meta metaBean;
    @SerializedName("objects")
    private ArrayList<DataBean> dataBeanList = null;

    public ResponseBean() {
    }

    private ResponseBean(Parcel in) {
        metaBean = in.readParcelable(Meta.class.getClassLoader());
        dataBeanList = in.createTypedArrayList(DataBean.CREATOR);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(metaBean, flags);
        dest.writeTypedList(dataBeanList);
    }

    public Meta getMetaBean() {
        return metaBean;
    }

    public void setMetaBean(Meta metaBean) {
        this.metaBean = metaBean;
    }

    public ArrayList<DataBean> getDataBeanList() {
        return dataBeanList;
    }

    public void setDataBeanList(ArrayList<DataBean> dataBeanList) {
        this.dataBeanList = dataBeanList;
    }
}
