package com.unlogicon.headhunter.model.vacancies;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Nik on 04.10.2014.
 */
public class Cluster implements Parcelable {

    private String found;
    private String per_page;
    private String alternate_url;
    private String page;
    private String pages;

    public String getFound() {
        return found;
    }

    public void setFound(String found) {
        this.found = found;
    }

    public String getPer_page() {
        return per_page;
    }

    public void setPer_page(String per_page) {
        this.per_page = per_page;
    }

    public String getAlternate_url() {
        return alternate_url;
    }

    public void setAlternate_url(String alternate_url) {
        this.alternate_url = alternate_url;
    }

    public String getPage() {
        return page;
    }

    public void setPage(String page) {
        this.page = page;
    }

    public String getPages() {
        return pages;
    }

    public void setPages(String pages) {
        this.pages = pages;
    }

    @Override
    public int describeContents() {
        return ((Object)this).hashCode();
    }

    public Cluster(){

    }

    public Cluster(Parcel in){
        found = in.readString();
        per_page = in.readString();
        alternate_url = in.readString();
        page = in.readString();
        pages = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(found);
        dest.writeString(per_page);
        dest.writeString(alternate_url);
        dest.writeString(page);
        dest.writeString(pages);
    }

    public static final Parcelable.Creator CREATOR = new Parcelable.Creator() {
        public Cluster createFromParcel(Parcel in) {
            return new Cluster(in);
        }

        public Cluster[] newArray(int size) {
            return new Cluster[size];
        }
    };

}
