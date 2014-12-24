package com.unlogicon.headhunter.model.vacancies;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by nik on 30.10.14.
 */
public class Contacts implements Parcelable {

    private String name;
    private String email;
    private Phones[] phones;

    public String getName(){
        return name;
    }

    public void setName(String name){
        this.name = name;
    }

    public String getEmail(){
        return email;
    }

    public void setEmail(String email){
        this.email = email;
    }

    public Phones[] getPhones(){
        return phones;
    }

    public void setPhones(Phones[] phones){
        this.phones = phones;
    }

    public Contacts(){

    }

    public Contacts(Parcel in){
        name = in.readString();
        email = in.readString();
        //phones = in.readParcelable(Phones.class.getClassLoader()); TODO Скорее всего надо убрать
    }

    @Override
    public int describeContents() {
        return ((Object)this).hashCode();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeString(email);
        dest.writeParcelableArray(phones, flags);
    }
}
