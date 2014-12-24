package com.unlogicon.headhunter.model.dictionaries;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Nik on 05.10.2014.
 */
public class Dictionaries implements Parcelable {

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

    public Dictionaries(){

    }

    public Dictionaries(Parcel in){
        id = in.readString();
        name = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(name);
    }

    public static final Parcelable.Creator CREATOR = new Parcelable.Creator() {
        public Dictionaries createFromParcel(Parcel in) {
            return new Dictionaries(in);
        }

        public Dictionaries[] newArray(int size) {
            return new Dictionaries[size];
        }
    };

}
