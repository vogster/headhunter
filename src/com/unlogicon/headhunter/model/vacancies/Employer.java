package com.unlogicon.headhunter.model.vacancies;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Nik on 27.09.2014.
 */
public class Employer implements Parcelable {

    private LogoUrls logoUrls;
    private String vacancies_url;
    private String name;
    private String url;
    private String alternate_url;
    private String site_url;
    private String type;
    private String id;
    private String description;

    public LogoUrls getLogoUrls(){
        return logoUrls;
    }

    public void setLogoUrls(LogoUrls logoUrls){
        this.logoUrls = logoUrls;
    }

    public String getVacancies_url(){
        return vacancies_url;
    }

    public void setVacancies_url(String vacancies_url){
        this.vacancies_url = vacancies_url;
    }

    public String getName(){
        return name;
    }

    public void setName(String name){
        this.name = name;
    }

    public String getUrl(){
        return url;
    }

    public void setUrl(String url){
        this.url = url;
    }

    public String getAlternate_url(){
        return alternate_url;
    }

    public void setAlternate_url(String alternate_url){
        this.vacancies_url = alternate_url;
    }

    public String getSite_url() {
        return site_url;
    }

    public void setSite_url(String site_url) {
        this.site_url = site_url;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getId(){
        return id;
    }

    public void setId(String id){
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public int describeContents() {
        return ((Object)this).hashCode();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(logoUrls, flags);
        dest.writeString(vacancies_url);
        dest.writeString(name);
        dest.writeString(url);
        dest.writeString(alternate_url);
        dest.writeString(site_url);
        dest.writeString(type);
        dest.writeString(id);
        dest.writeString(description);
    }

    public Employer(){

    }

    public Employer(Parcel in){
        logoUrls = in.readParcelable(LogoUrls.class.getClassLoader());
        vacancies_url = in.readString();
        name = in.readString();
        url = in.readString();
        alternate_url = in.readString();
        site_url = in.readString();
        type = in.readString();
        id = in.readString();
        description = in.readString();
    }

    public static final Parcelable.Creator CREATOR = new Parcelable.Creator() {
        public Employer createFromParcel(Parcel in) {
            return new Employer(in);
        }

        public Employer[] newArray(int size) {
            return new Employer[size];
        }
    };

}
