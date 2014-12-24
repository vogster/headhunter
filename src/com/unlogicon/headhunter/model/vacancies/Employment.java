package com.unlogicon.headhunter.model.vacancies;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by nik on 29.09.14.
 */
public class Employment implements Parcelable {
    private String id;
    private String name;

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
    public int describeContents() {
        return ((Object)this).hashCode();
    }

    public Employment(){

    }

    public Employment(Parcel in){
        id = in.readString();
        name = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(name);
    }

    public static final Parcelable.Creator CREATOR = new Parcelable.Creator() {
        public Employment createFromParcel(Parcel in) {
            return new Employment(in);
        }

        public Employment[] newArray(int size) {
            return new Employment[size];
        }
    };

}
