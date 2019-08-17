package com.puyu.mobile.base.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * author : 简玉锋
 * e-mail : yufeng_jian@fpi-inc.com
 * date   : 2019/5/23 21:35
 * desc   : 对象
 * version: 1.0
 */
public class BeanBase implements Parcelable {
    private String id;
    private String name;

    public BeanBase() {
    }

    public BeanBase(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "BeanBase{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.id);
        dest.writeString(this.name);
    }

    protected BeanBase(Parcel in) {
        this.id = in.readString();
        this.name = in.readString();
    }

    public static final Parcelable.Creator<BeanBase> CREATOR = new Parcelable.Creator<BeanBase>() {
        @Override
        public BeanBase createFromParcel(Parcel source) {
            return new BeanBase(source);
        }

        @Override
        public BeanBase[] newArray(int size) {
            return new BeanBase[size];
        }
    };
}
