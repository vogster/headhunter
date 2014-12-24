package com.unlogicon.headhunter.model.vacancies;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Nik on 27.09.2014.
 */
public class Metro implements Parcelable {

    private String line_name;
    private String station_id;
    private String line_id;
    private String lat;
    private String station_name;
    private String lng;

    public String getLine_name() {
        return line_name;
    }

    public void setLine_name(String line_name) {
        this.line_name = line_name;
    }

    public String getStation_id() {
        return station_id;
    }

    public void setStation_id(String station_id) {
        this.station_id = station_id;
    }

    public String getLine_id() {
        return line_id;
    }

    public void setLine_id(String line_id) {
        this.line_id = line_id;
    }

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public String getStation_name() {
        return station_name;
    }

    public void setStation_name(String station_name) {
        this.station_name = station_name;
    }

    public String getLng() {
        return lng;
    }

    public void setLng(String lng) {
        this.lng = lng;
    }

    @Override
    public int describeContents() {
        return ((Object)this).hashCode();
    }

    public Metro(){

    }

    public Metro(Parcel in){
        line_name = in.readString();
        station_name = in.readString();
        line_id = in.readString();
        lat = in.readString();
        station_name = in.readString();
        lng = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(line_name);
        dest.writeString(station_name);
        dest.writeString(line_id);
        dest.writeString(lat);
        dest.writeString(station_name);
        dest.writeString(lng);
    }

    public static final Parcelable.Creator CREATOR = new Parcelable.Creator() {
        public Metro createFromParcel(Parcel in) {
            return new Metro(in);
        }

        public Metro[] newArray(int size) {
            return new Metro[size];
        }
    };
}
