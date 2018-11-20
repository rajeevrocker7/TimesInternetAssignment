package com.rajeev.timesinternetassignment.beans;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class DataBean implements Serializable, Parcelable {
    public static final Creator<DataBean> CREATOR = new Creator<DataBean>() {
        @Override
        public DataBean createFromParcel(Parcel in) {
            return new DataBean(in);
        }

        @Override
        public DataBean[] newArray(int size) {
            return new DataBean[size];
        }
    };
    @SerializedName("b")

    private boolean b;
    @SerializedName("c")

    private boolean c;
    @SerializedName("cat")

    private Cat cat;
    @SerializedName("cats")

    private List<Cat_> cats = null;
    @SerializedName("cr")

    private String cr;
    @SerializedName("d")

    private String d;
    @SerializedName("di")

    private String di;
    @SerializedName("di10")

    private java.lang.Object di10;
    @SerializedName("di2")

    private java.lang.Object di2;
    @SerializedName("di3")

    private java.lang.Object di3;
    @SerializedName("di4")

    private java.lang.Object di4;
    @SerializedName("di5")

    private java.lang.Object di5;
    @SerializedName("di6")

    private java.lang.Object di6;
    @SerializedName("di7")

    private java.lang.Object di7;
    @SerializedName("di8")

    private java.lang.Object di8;
    @SerializedName("di9")

    private java.lang.Object di9;
    @SerializedName("dp")

    private String dp;
    @SerializedName("f")

    private boolean f;
    @SerializedName("i")

    private String i;
    @SerializedName("i10")

    private java.lang.Object i10;
    @SerializedName("i2")

    private java.lang.Object i2;
    @SerializedName("i3")

    private java.lang.Object i3;
    @SerializedName("i4")

    private java.lang.Object i4;
    @SerializedName("i5")

    private java.lang.Object i5;
    @SerializedName("i6")

    private java.lang.Object i6;
    @SerializedName("i7")

    private java.lang.Object i7;
    @SerializedName("i8")

    private java.lang.Object i8;
    @SerializedName("i9")

    private java.lang.Object i9;
    @SerializedName("ia")

    private boolean ia;
    @SerializedName("id")

    private long id;
    @SerializedName("md")

    private String md;
    @SerializedName("moq")

    private long moq;
    @SerializedName("opt1")

    private String opt1;
    @SerializedName("opt2")

    private String opt2;
    @SerializedName("opt3")

    private String opt3;
    @SerializedName("p")

    private String p;
    @SerializedName("rs")

    private long rs;
    @SerializedName("rv")

    private long rv;
    @SerializedName("s")

    private long s;
    @SerializedName("sd")

    private String sd;
    @SerializedName("sku")

    private String sku;
    @SerializedName("slug")

    private String slug;
    @SerializedName("so")

    private long so;
    @SerializedName("t")

    private String t;
    @SerializedName("zi")

    private java.lang.Object zi;
    @SerializedName("zi10")

    private java.lang.Object zi10;
    @SerializedName("zi2")

    private java.lang.Object zi2;
    @SerializedName("zi3")

    private java.lang.Object zi3;
    @SerializedName("zi4")

    private java.lang.Object zi4;
    @SerializedName("zi5")

    private java.lang.Object zi5;
    @SerializedName("zi6")

    private java.lang.Object zi6;
    @SerializedName("zi7")

    private java.lang.Object zi7;
    @SerializedName("zi8")

    private java.lang.Object zi8;
    @SerializedName("zi9")

    private java.lang.Object zi9;

    public DataBean() {
    }

    private DataBean(Parcel in) {
        b = in.readByte() != 0;
        c = in.readByte() != 0;
        cat = in.readParcelable(Cat.class.getClassLoader());
        cats = in.createTypedArrayList(Cat_.CREATOR);
        cr = in.readString();
        d = in.readString();
        di = in.readString();
        dp = in.readString();
        f = in.readByte() != 0;
        i = in.readString();
        ia = in.readByte() != 0;
        id = in.readLong();
        md = in.readString();
        moq = in.readLong();
        opt1 = in.readString();
        opt2 = in.readString();
        opt3 = in.readString();
        p = in.readString();
        rs = in.readLong();
        rv = in.readLong();
        s = in.readLong();
        sd = in.readString();
        sku = in.readString();
        slug = in.readString();
        so = in.readLong();
        t = in.readString();
    }

    public boolean isB() {
        return b;
    }

    public void setB(boolean b) {
        this.b = b;
    }

    public boolean isC() {
        return c;
    }

    public void setC(boolean c) {
        this.c = c;
    }

    public Cat getCat() {
        return cat;
    }

    public void setCat(Cat cat) {
        this.cat = cat;
    }

    public List<Cat_> getCats() {
        return cats;
    }

    public void setCats(List<Cat_> cats) {
        this.cats = cats;
    }

    public String getCr() {
        return cr;
    }

    public void setCr(String cr) {
        this.cr = cr;
    }

    public String getD() {
        return d;
    }

    public void setD(String d) {
        this.d = d;
    }

    public String getDi() {
        return di;
    }

    public void setDi(String di) {
        this.di = di;
    }

    public Object getDi10() {
        return di10;
    }

    public void setDi10(Object di10) {
        this.di10 = di10;
    }

    public Object getDi2() {
        return di2;
    }

    public void setDi2(Object di2) {
        this.di2 = di2;
    }

    public Object getDi3() {
        return di3;
    }

    public void setDi3(Object di3) {
        this.di3 = di3;
    }

    public Object getDi4() {
        return di4;
    }

    public void setDi4(Object di4) {
        this.di4 = di4;
    }

    public Object getDi5() {
        return di5;
    }

    public void setDi5(Object di5) {
        this.di5 = di5;
    }

    public Object getDi6() {
        return di6;
    }

    public void setDi6(Object di6) {
        this.di6 = di6;
    }

    public Object getDi7() {
        return di7;
    }

    public void setDi7(Object di7) {
        this.di7 = di7;
    }

    public Object getDi8() {
        return di8;
    }

    public void setDi8(Object di8) {
        this.di8 = di8;
    }

    public Object getDi9() {
        return di9;
    }

    public void setDi9(Object di9) {
        this.di9 = di9;
    }

    public String getDp() {
        return dp;
    }

    public void setDp(String dp) {
        this.dp = dp;
    }

    public boolean isF() {
        return f;
    }

    public void setF(boolean f) {
        this.f = f;
    }

    public String getI() {
        return i;
    }

    public void setI(String i) {
        this.i = i;
    }

    public Object getI10() {
        return i10;
    }

    public void setI10(Object i10) {
        this.i10 = i10;
    }

    public Object getI2() {
        return i2;
    }

    public void setI2(Object i2) {
        this.i2 = i2;
    }

    public Object getI3() {
        return i3;
    }

    public void setI3(Object i3) {
        this.i3 = i3;
    }

    public Object getI4() {
        return i4;
    }

    public void setI4(Object i4) {
        this.i4 = i4;
    }

    public Object getI5() {
        return i5;
    }

    public void setI5(Object i5) {
        this.i5 = i5;
    }

    public Object getI6() {
        return i6;
    }

    public void setI6(Object i6) {
        this.i6 = i6;
    }

    public Object getI7() {
        return i7;
    }

    public void setI7(Object i7) {
        this.i7 = i7;
    }

    public Object getI8() {
        return i8;
    }

    public void setI8(Object i8) {
        this.i8 = i8;
    }

    public Object getI9() {
        return i9;
    }

    public void setI9(Object i9) {
        this.i9 = i9;
    }

    public boolean isIa() {
        return ia;
    }

    public void setIa(boolean ia) {
        this.ia = ia;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getMd() {
        return md;
    }

    public void setMd(String md) {
        this.md = md;
    }

    public long getMoq() {
        return moq;
    }

    public void setMoq(long moq) {
        this.moq = moq;
    }

    public String getOpt1() {
        return opt1;
    }

    public void setOpt1(String opt1) {
        this.opt1 = opt1;
    }

    public String getOpt2() {
        return opt2;
    }

    public void setOpt2(String opt2) {
        this.opt2 = opt2;
    }

    public String getOpt3() {
        return opt3;
    }

    public void setOpt3(String opt3) {
        this.opt3 = opt3;
    }

    public String getP() {
        return p;
    }

    public void setP(String p) {
        this.p = p;
    }

    public long getRs() {
        return rs;
    }

    public void setRs(long rs) {
        this.rs = rs;
    }

    public long getRv() {
        return rv;
    }

    public void setRv(long rv) {
        this.rv = rv;
    }

    public long getS() {
        return s;
    }

    public void setS(long s) {
        this.s = s;
    }

    public String getSd() {
        return sd;
    }

    public void setSd(String sd) {
        this.sd = sd;
    }

    public String getSku() {
        return sku;
    }

    public void setSku(String sku) {
        this.sku = sku;
    }

    public String getSlug() {
        return slug;
    }

    public void setSlug(String slug) {
        this.slug = slug;
    }

    public long getSo() {
        return so;
    }

    public void setSo(long so) {
        this.so = so;
    }

    public String getT() {
        return t;
    }

    public void setT(String t) {
        this.t = t;
    }

    public Object getZi() {
        return zi;
    }

    public void setZi(Object zi) {
        this.zi = zi;
    }

    public Object getZi10() {
        return zi10;
    }

    public void setZi10(Object zi10) {
        this.zi10 = zi10;
    }

    public Object getZi2() {
        return zi2;
    }

    public void setZi2(Object zi2) {
        this.zi2 = zi2;
    }

    public Object getZi3() {
        return zi3;
    }

    public void setZi3(Object zi3) {
        this.zi3 = zi3;
    }

    public Object getZi4() {
        return zi4;
    }

    public void setZi4(Object zi4) {
        this.zi4 = zi4;
    }

    public Object getZi5() {
        return zi5;
    }

    public void setZi5(Object zi5) {
        this.zi5 = zi5;
    }

    public Object getZi6() {
        return zi6;
    }

    public void setZi6(Object zi6) {
        this.zi6 = zi6;
    }

    public Object getZi7() {
        return zi7;
    }

    public void setZi7(Object zi7) {
        this.zi7 = zi7;
    }

    public Object getZi8() {
        return zi8;
    }

    public void setZi8(Object zi8) {
        this.zi8 = zi8;
    }

    public Object getZi9() {
        return zi9;
    }

    public void setZi9(Object zi9) {
        this.zi9 = zi9;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeByte((byte) (b ? 1 : 0));
        dest.writeByte((byte) (c ? 1 : 0));
        dest.writeParcelable(cat, flags);
        dest.writeTypedList(cats);
        dest.writeString(cr);
        dest.writeString(d);
        dest.writeString(di);
        dest.writeString(dp);
        dest.writeByte((byte) (f ? 1 : 0));
        dest.writeString(i);
        dest.writeByte((byte) (ia ? 1 : 0));
        dest.writeLong(id);
        dest.writeString(md);
        dest.writeLong(moq);
        dest.writeString(opt1);
        dest.writeString(opt2);
        dest.writeString(opt3);
        dest.writeString(p);
        dest.writeLong(rs);
        dest.writeLong(rv);
        dest.writeLong(s);
        dest.writeString(sd);
        dest.writeString(sku);
        dest.writeString(slug);
        dest.writeLong(so);
        dest.writeString(t);
    }
}
