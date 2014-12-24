package com.unlogicon.headhunter.model.vacancies;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Nik on 26.09.2014.
 */
public class Area implements Parcelable {

    private String url;
    private int id;
    private String name;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url){
        this.url = url;
    }

    public int getId(){
        return id;
    }

    public void setId(int id){
        this.id = id;
    }

    public String getName(){
        return name;
    }

    public void setName(String name){
        this.name = name;
    }

    @Override
    public int describeContents() {
        return ((Object)this).hashCode();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(url);
        dest.writeInt(id);
        dest.writeString(name);
    }

    public Area(){

    }

    public Area(Parcel in){
        url = in.readString();
        id = in.readInt();
        name = in.readString();
    }

    public static final Parcelable.Creator CREATOR = new Parcelable.Creator() {
        public Area createFromParcel(Parcel in) {
            return new Area(in);
        }

        public Area[] newArray(int size) {
            return new Area[size];
        }
    };
}
