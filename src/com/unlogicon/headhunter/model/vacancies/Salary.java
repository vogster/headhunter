package com.unlogicon.headhunter.model.vacancies;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Nik on 26.09.2014.
 */
public class Salary implements Parcelable {

    private int to;
    private int from;
    private String currency;

    public int getTo(){
        return to;
    }

    public void setTo(int to){
        this.to = to;
    }

    public int getFrom(){
        return from;
    }

    public void setFrom(int from){
        this.from = from;
    }

    public String getCurrency(){
        return currency;
    }

    public void setCurrency(String currency){
        this.currency = currency;
    }

    @Override
    public int describeContents() {
        return ((Object)this).hashCode();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(to);
        dest.writeInt(from);
        dest.writeString(currency);
    }

    public Salary(Parcel in){
        to = in.readInt();
        from = in.readInt();
        currency = in.readString();
    }

    public Salary(){

    }

    public static final Parcelable.Creator CREATOR = new Parcelable.Creator() {
        public Salary createFromParcel(Parcel in) {
            return new Salary(in);
        }

        public Salary[] newArray(int size) {
            return new Salary[size];
        }
    };
}
