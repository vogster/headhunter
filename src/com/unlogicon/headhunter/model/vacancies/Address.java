package com.unlogicon.headhunter.model.vacancies;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Nik on 27.09.2014.
 */
public class Address implements Parcelable {

    private String building;
    private String city;
    private String street;
    private String description;
    private Metro metro;
    private String raw;
    private String lat;
    private String lng;
    private MetroStations metroStations;

    public String getBuilding() {
        return building;
    }

    public void setBuilding(String building) {
        this.building = building;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Metro getMetro() {
        return metro;
    }

    public void setMetro(Metro metro) {
        this.metro = metro;
    }

    public String getRaw() {
        return raw;
    }

    public void setRaw(String raw) {
        this.raw = raw;
    }

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public String getLng() {
        return lng;
    }

    public void setLng(String lng) {
        this.lng = lng;
    }

    public MetroStations getMetroStations() {
        return metroStations;
    }

    public void setMetroStations(MetroStations metroStations) {
        this.metroStations = metroStations;
    }

    @Override
    public int describeContents() {
        return ((Object)this).hashCode();
    }

    public Address(Parcel in){
        building = in.readString();
        city = in.readString();
        street = in.readString();
        description = in.readString();
        metro = in.readParcelable(Metro.class.getClassLoader());
        raw = in.readString();
        lat = in.readString();
        lng = in.readString();
        metroStations = in.readParcelable(MetroStations.class.getClassLoader());
    }

    public Address(){

    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(building);
        dest.writeString(city);
        dest.writeString(street);
        dest.writeString(description);
        dest.writeParcelable(metro, flags);
        dest.writeString(raw);
        dest.writeString(lat);
        dest.writeString(lng);
        dest.writeParcelable(metroStations, flags);
    }

    public static final Parcelable.Creator CREATOR = new Parcelable.Creator() {
        public Address createFromParcel(Parcel in) {
            return new Address(in);
        }

        public Address[] newArray(int size) {
            return new Address[size];
        }
    };
}
