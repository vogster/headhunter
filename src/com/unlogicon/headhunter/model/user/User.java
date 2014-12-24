package com.unlogicon.headhunter.model.user;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by nik on 14.11.14.
 */
public class User implements Parcelable {

    //TODO Дописать заккоментированные переменные

    private String last_name;
    private String resumes_url;
    private boolean is_admin;
    private boolean is_employer;
    //  personal_manager
    private String id;
    private String first_name;
    //  middle_name
    private boolean is_in_search;
    private boolean is_anonymous;
    //  employer
    private String negotiations_url;
    private boolean is_applicant;
    //  mid_name
    private String email;
    //  countries


    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public String getResumes_url() {
        return resumes_url;
    }

    public void setResumes_url(String resumes_url) {
        this.resumes_url = resumes_url;
    }

    public boolean isIs_admin() {
        return is_admin;
    }

    public void setIs_admin(boolean is_admin) {
        this.is_admin = is_admin;
    }

    public boolean isIs_employer() {
        return is_employer;
    }

    public void setIs_employer(boolean is_employer) {
        this.is_employer = is_employer;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public boolean isIs_in_search() {
        return is_in_search;
    }

    public void setIs_in_search(boolean is_in_search) {
        this.is_in_search = is_in_search;
    }

    public boolean isIs_anonymous() {
        return is_anonymous;
    }

    public void setIs_anonymous(boolean is_anonymous) {
        this.is_anonymous = is_anonymous;
    }

    public String getNegotiations_url() {
        return negotiations_url;
    }

    public void setNegotiations_url(String negotiations_url) {
        this.negotiations_url = negotiations_url;
    }

    public boolean isIs_applicant() {
        return is_applicant;
    }

    public void setIs_applicant(boolean is_applicant) {
        this.is_applicant = is_applicant;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public User(){

    }

    public User(Parcel in){
        last_name = in.readString();
        resumes_url = in.readString();
        is_admin = in.readByte() == 1;
        is_employer = in.readByte() == 1;
        id = in.readString();
        first_name = in.readString();
        is_in_search = in.readByte() == 1;
        is_anonymous = in.readByte() == 1;
        negotiations_url = in.readString();
        is_applicant = in.readByte() == 1;
        email = in.readString();
    }

    @Override
    public int describeContents() {
        return ((Object)this).hashCode();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(last_name);
        dest.writeString(resumes_url);
        dest.writeByte((byte)(is_admin ? 1 : 0));
        dest.writeByte((byte)(is_employer ? 1 : 0));
        dest.writeString(id);
        dest.writeString(first_name);
        dest.writeByte((byte)(is_in_search ? 1 : 0));
        dest.writeByte((byte)(is_anonymous ? 1 : 0));
        dest.writeString(negotiations_url);
        dest.writeByte((byte)(is_applicant ? 1 : 0));
        dest.writeString(email);
    }
}
