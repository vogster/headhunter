package com.unlogicon.headhunter.model.vacancies;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Nik on 27.09.2014.
 */
public class LogoUrls implements Parcelable {

    private String url_90;
    private String url_240;
    private String url_original;

    public String getUrl_90(){
        return url_90;
    }

    public void setUrl_90(String url){
        this.url_90 = url;
    }

    public String getUrl_240(){
        return url_240;
    }

    public void setUrl_240(String url){
        this.url_240 = url;
    }

    public String getUrl_original(){
        return url_original;
    }

    public void setUrl_original(String url){
        this.url_original = url;
    }

    @Override
    public int describeContents() {
        return ((Object)this).hashCode();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(url_90);
        dest.writeString(url_240);
        dest.writeString(url_original);
    }

    public LogoUrls(){

    }

    public LogoUrls(Parcel in){
        url_90 = in.readString();
        url_240 = in.readString();
        url_original = in.readString();
    }

    public static final Parcelable.Creator CREATOR = new Parcelable.Creator() {
        public LogoUrls createFromParcel(Parcel in) {
            return new LogoUrls(in);
        }

        public LogoUrls[] newArray(int size) {
            return new LogoUrls[size];
        }
    };

}
