package com.unlogicon.headhunter.model.vacancies;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Nik on 27.09.2014.
 */
public class MetroStations implements Parcelable {

    private Metro metro;

    public Metro getMetro() {
        return metro;
    }

    public void setMetro(Metro metro) {
        this.metro = metro;
    }

    @Override
    public int describeContents() {
        return ((Object)this).hashCode();
    }

    public MetroStations(){

    }

    public MetroStations(Parcel in){
        metro = in.readParcelable(Metro.class.getClassLoader());
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(metro, flags);
    }

    public static final Parcelable.Creator CREATOR = new Parcelable.Creator() {
        public MetroStations createFromParcel(Parcel in) {
            return new MetroStations(in);
        }

        public MetroStations[] newArray(int size) {
            return new MetroStations[size];
        }
    };
}
