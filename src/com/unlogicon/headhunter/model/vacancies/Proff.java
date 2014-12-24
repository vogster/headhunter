package com.unlogicon.headhunter.model.vacancies;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by nik on 09.10.14.
 */
public class Proff implements Parcelable {
    private Specializations[] specializations;
    private String id;
    private String name;

    public Specializations[] getSpecializations(){
        return specializations;
    }

    public void setSpecializations(Specializations[] specializations) {
        this.specializations = specializations;
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
    public int describeContents() {
        return ((Object)this).hashCode();
    }

    public Proff(){

    }

    public Proff(Parcel in){
        id = in.readString();
        name = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(name);
    }

    public static final Parcelable.Creator CREATOR = new Parcelable.Creator() {
        public Proff createFromParcel(Parcel in) {
            return new Proff(in);
        }

        public Proff[] newArray(int size) {
            return new Proff[size];
        }
    };

}