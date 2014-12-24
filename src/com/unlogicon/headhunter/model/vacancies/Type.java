package com.unlogicon.headhunter.model.vacancies;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Nik on 27.09.2014.
 */
public class Type implements Parcelable {

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

    public Type(Parcel in){
        id = in.readString();
        name = in.readString();
    }

    public Type(){

    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(name);
    }
}
