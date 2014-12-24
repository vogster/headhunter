package com.unlogicon.headhunter.model.vacancies;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

/**
 * Created by nik on 04.09.14.
 */
public class Vacancies implements Parcelable {

    private Salary salary;
    private boolean archived;
    private boolean premium;
    private String name;
    private Area area;
    private String url;
    private String created_at;
    private ArrayList<String> relations;
    private Employer employer;
    private boolean response_letter_required;
    private String published_at;
    private Address address;
    private String alternate_url;
    private Type type;
    private String id;


    public Salary getSalary(){
        return salary;
    }

    public void setSalary(Salary salary){
        this.salary = salary;
    }

    public boolean isArchived() {
        return archived;
    }

    public void setArchived(boolean archived) {
        this.archived = archived;
    }

    public boolean isPremium() {
        return premium;
    }

    public void setPremium(boolean premium) {
        this.premium = premium;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Area getArea() {
        return area;
    }

    public void setArea(Area area) {
        this.area = area;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public ArrayList<String> getRelations(){
        return relations;
    }

    public void setRelations(ArrayList<String> relations){
        this.relations = relations;
    }

    public Employer getEmployer() {
        return employer;
    }

    public void setEmployer(Employer employer) {
        this.employer = employer;
    }

    public boolean isResponse_letter_required() {
        return response_letter_required;
    }

    public void setResponse_letter_required(boolean response_letter_required) {
        this.response_letter_required = response_letter_required;
    }

    public String getPublished_at() {
        return published_at;
    }

    public void setPublished_at(String published_at) {
        this.published_at = published_at;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public String getAlternate_url() {
        return alternate_url;
    }

    public void setAlternate_url(String alternate_url) {
        this.alternate_url = alternate_url;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public int describeContents() {
        return ((Object)this).hashCode();
    }

    public Vacancies(){

    }

    public Vacancies(Parcel in){
        salary = in.readParcelable(Salary.class.getClassLoader());
        archived = in.readByte() == 1;
        premium = in.readByte() == 1;
        name = in.readString();
        area = in.readParcelable(Area.class.getClassLoader());
        url = in.readString();
        created_at = in.readString();
        employer = in.readParcelable(Employer.class.getClassLoader());
        response_letter_required = in.readByte() == 1;
        published_at = in.readString();
        relations = in.readArrayList(String.class.getClassLoader());
        address = in.readParcelable(Address.class.getClassLoader());
        alternate_url = in.readString();
        type = in.readParcelable(Type.class.getClassLoader());
        id = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(salary, flags);
        dest.writeByte((byte) (archived ? 1 : 0));
        dest.writeByte((byte) (premium ? 1 : 0));
        dest.writeString(name);
        dest.writeParcelable(area, flags);
        dest.writeString(url);
        dest.writeString(created_at);
        dest.writeArray(relations.toArray());
        dest.writeParcelable(employer, flags);
        dest.writeByte((byte) (response_letter_required ? 1 : 0));
        dest.writeString(published_at);
        dest.writeParcelable(address, flags);
        dest.writeString(alternate_url);
        dest.writeParcelable(type, flags);
        dest.writeString(id);
    }
}
