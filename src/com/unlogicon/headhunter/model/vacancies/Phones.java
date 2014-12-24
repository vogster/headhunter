package com.unlogicon.headhunter.model.vacancies;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by nik on 30.10.14.
 */
public class Phones implements Parcelable {

    private String comment;
    private String city;
    private String number;
    private String country;

    public String getComment(){
        return comment;
    }

    public void setComment(String comment){
        this.comment = comment;
    }

    public String getCity(){
        return city;
    }

    public void setCity(String city){
        this.city = city;
    }

    public String getNumber(){
        return number;
    }

    public void setNumber(String number){
        this.number = number;
    }

    public String getCountry(){
        return country;
    }

    public void setCountry(String country){
        this.country = country;
    }

    @Override
    public String toString() {
        return this.comment;
    }

    public Phones(){

    }

    public Phones(Parcel in){
        comment = in.readString();
        city = in.readString();
        number = in.readString();
        country = in.readString();
    }

    @Override
    public int describeContents() {
        return ((Object)this).hashCode();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(comment);
        dest.writeString(city);
        dest.writeString(number);
        dest.writeString(country);

    }
}
